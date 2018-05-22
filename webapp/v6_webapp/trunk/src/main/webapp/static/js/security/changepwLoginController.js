/**
 * 修改登录密码
 */
define(['js/security/changepwLoginView'], function (changepwLoginView) {
    var username = '';
    var changepwLoginCtrl = {
        init: function () {
            var bindings = [
                {
                    element: '#changepwLogin #changepw-login-submit',
                    event: 'click',
                    handler: changepwLoginCtrl.changepwLoginSubmit
                }
            ];
            changepwLoginView.init({bindings: bindings});
        },
        changepwLoginSubmit: function () {
            var oldLoginPW = $.trim($$('#changepwLogin #old-loginPW').val());
            var newLoginPW = $.trim($$('#changepwLogin #new-loginPW').val());
            var confirmNewLoginPW = $.trim($$('#changepwLogin #confirm-new-loginPW').val());
            if (oldLoginPW == '') {
                xxdApp.alert('当前登录密码不能为空', '提示');
                return false;
            }
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
            if (username == '') {
                username = appFunc.getCurrentUser().userName;
            }
            if (newLoginPW.indexOf(username) >= 0) {
                xxdApp.alert("密码不得包括用户名", '提示');
                return false;
            }
            if (newLoginPW != confirmNewLoginPW) {
                xxdApp.alert('新登录密码与确认登录密码不一致，请重新输入', '提示');
                return false;
            }

            req.callPost({
                url: 'approve/changeLoginPW.do',
                data: {
                    oldLoginPW: $.md5($.md5(oldLoginPW)),
                    newLoginPW: $.md5($.md5(newLoginPW))
                },
                dataType: 'json',
                preloaderTitle:'正在努力修改，请稍后...',
                timeout:10000,
                success: function (result) {
                    xxdApp.alert(result.info, '提示', function () {
                        if (result.code == '-1') {
                            xxdApp.loginScreen();
                        } else if (result.code == '0') {
                            GS.loadPage('security/security.html');
                        }
                    });
                }
            });
        }
    };

    return changepwLoginCtrl;
});