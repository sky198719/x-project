/**
 * 忘记登录密码-第二步
 */
define(['js/security/forgetpwLoginStep2View'], function (forgetpwLoginStep2View) {
    var username = '';
    var mobileNo;
    var randCode;
    var forgetpwLoginStep2Ctrl = {
        init: function (event) {
        	mainView.showNavbar();
            var page = appFunc.getEventDetailPageQuery(event);
            mobileNo = page.mobileNo;
            randCode = page.randCode;
            var bindings = [
                {
                    element: '#forgetpwLoginStep2 #forgetpwLogin-submit',
                    event: 'click',
                    handler: forgetpwLoginStep2Ctrl.forgetpwLoginStep2Submit
                }
            ];
            forgetpwLoginStep2View.init({bindings: bindings});

            req.callJSON({
                url:'user/queryUserByMobile.do',
                data:{
                    mobile:mobileNo
                },
                dataType:'json',
                success:function(result){
                    if(result.code == 0) {
                        username = result.data.userName;
                    }
                }
            });
        },
        forgetpwLoginStep2Submit: function () {
            var newLoginPW = $.trim($$('#forgetpwLoginStep2 #new-loginPW').val());
            var confirmNewLoginPW = $.trim($$('#forgetpwLoginStep2 #confirm-new-loginPW').val());
            if (newLoginPW == '') {
                xxdApp.alert('新密码不能为空，请重新输入', '提示');
                return false;
            }
            if (confirmNewLoginPW == '') {
                xxdApp.alert('必须填写确认密码，请重新输入', '提示');
                return false;
            }
            if (appFunc.validatePassword(newLoginPW) != 'true') {
                xxdApp.alert('有效密码为6-16位数字字母组合', '提示');
                return false;
            }

            if (username != '' && newLoginPW.indexOf(username) >= 0) {
                xxdApp.alert("密码不得包括用户名", '提示');
                return false;
            }
            if (newLoginPW != confirmNewLoginPW) {
                xxdApp.alert('新登录密码与确认登录密码不一致，请重新输入', '提示');
                return false;
            }

            req.callPost({
                url: 'approve/resetLogPW.do',
                data: {
                    mobileNo: mobileNo,
                    randCode: randCode,
                    newLoginPW: $.md5($.md5(newLoginPW))
                },
                dataType: 'json',
                preloaderTitle:'正在努力修改，请稍后...',
                success: function (result) {
                    if (result.code == '0') {
                        xxdApp.alert('登录密码修改成功', '提示', function () {
                            GS.loadPage("borrow/borrowListV2.html");
                            xxdApp.loginScreen();
                        });
                    } else if (result.code == '-1') {
                        xxdApp.alert('登录密码修改失败,请重试', '提示', function () {
                            GS.loadPage('security/forgetpwLogin.html?path=security');
                        });
                    } else {
                        xxdApp.alert(result.info, '提示');
                    }
                }
            });
        }
    };

    return forgetpwLoginStep2Ctrl;
});