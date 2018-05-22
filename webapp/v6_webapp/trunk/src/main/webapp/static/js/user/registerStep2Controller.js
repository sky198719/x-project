/**
 * 注册第二步
 */
define(['js/user/registerStep2View'], function (registerStep2View) {
	var job = 'default';
    var flag = 0;
    var fundActivityCode = "";
    var pcode = '';
    var mobileNo = '';
    var smsCode= '';
    var vipCode = '';
    var registerStep2Ctrl = {
        init: function (event) {
//            if (appFunc.isLogin()) {
//               xxdApp.alert("请先退出当前账号，再进行注册！","抱歉",function(){
//                   GS.loadPage("index/home.html");
//               });
//               return;
//           }

            var page = appFunc.getEventDetailPageQuery(event);
        	 job = page.job;

            mobileNo = page.mobileNo;
            smsCode = page.smsCode;
            if(mobileNo == '' || mobileNo == undefined || smsCode == '' || smsCode == undefined) {
                xxdApp.alert("您访问的地址不正确","抱歉",function(){
                      GS.loadPage("index/home.html");
                  });
                  return;
            }

            if(page.pcode != undefined) {
                pcode = page.pcode;
            }

            if(page.vipCode != undefined) {
                vipCode = page.vipCode;
            }

            var bindings = [
                {
                    element: '#register-step2 #reg-submit',
                    event: 'click',
                    handler: registerStep2Ctrl.regSubmit
                },
                {
                    element: '#register-step2 #close-reg',
                    event: 'click',
                    handler: registerStep2Ctrl.closeReg
                },
                {
                    element: '#register-step2 #pwdShow',
                    event: 'click',
                    handler: registerStep2Ctrl.pwdShow
                },
                {
                    element: '#register-step2 .reg-popup',
                    event: 'click',
                    handler: registerStep2Ctrl.regPopup
                },
                {
                    element: '#register-step2 .zijin-popup',
                    event: 'click',
                    handler: registerStep2Ctrl.zijinPopup

                }
            ];
            registerStep2View.init({bindings: bindings});

            var uuid = page.uuid;
            if(uuid != undefined) {
                $$("#uuid").val(uuid);
            }

            if(page.fundActivityCode != undefined){
                fundActivityCode = page.fundActivityCode;
            }

        },
        zijinPopup:function(){
            req.callGet({
                url: GC.getHtmlPath() + 'common/tenderAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    $$(".popup-bidhistory").html(result);
                    $$(".close-popup").show();
                    xxdApp.popup('.popup-bidhistory');
                }
            });
        },
        regPopup:function(){
            req.callGet({
                url: GC.getHtmlPath() + 'user/registerAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    $$(".popup-bidhistory").html(result);
                    xxdApp.popup('.popup-bidhistory');
                }
            });
        },

        closeReg: function () {
            GS.loadPage('borrow/borrowListV2.html');
        },
        pwdShow: function () {
            if(flag==0) {
                $$('#register-step2 #pwdShow').attr('src','static/img/xxd/eye-open.png');
                $$('#register-step2 #password').attr('type','text');
                flag=1;
            } else {
                $$('#register-step2 #pwdShow').attr('src','static/img/xxd/eye-close.png');
                $$('#register-step2 #password').attr('type','password');
                flag=0
            }
        },

        checkUserName:function(username) {
            var res = false;
            req.callPost({
                 url:'user/checkUserName.do',
                 data:{
                     userName:username
                 },
                 async:false,
                 dataType: 'text',
                 success:function(result){
                     res = result == '1' ? true : false;
                 }
            });
            return res;
        },

        regSubmit: function () {
            var username = $$('#register-step2 #username').val();
            var password = $$('#register-step2 #password').val();
            var regAgreement = $$('#register-step2 #reg-agreement');
            if (password == '') {
                xxdApp.alert('密码不能为空', '提示');
                return;
            }
            if (username != '') {
                var rName = appFunc.validateName(username);
                if ("true" != rName) {
                    xxdApp.alert(rName, '提示');
                    return;
                }

                var checkUserName = registerStep2Ctrl.checkUserName(username);
                if(checkUserName == 1) {
                    xxdApp.alert("您输入的用户名已被使用，请重新输入","提示");
                    return;
                } else if (checkUserName == 2) {
                    xxdApp.alert("您输入的用户名有敏感词，请重新输入","提示");
                    return;
                }
            }

            if (appFunc.validatePassword(password) != 'true') {
                xxdApp.alert('有效密码为6-16位数字字母组合', '提示');
                return;
            }
            if (username!='' && password.indexOf(username) >= 0) {
                xxdApp.alert("密码不得包括用户名", '提示');
                return;
            }

            if (!$$(regAgreement).is(':checked')) {
                xxdApp.alert('请先阅读并同意使用协议', '提示');
                return;
            }
            var uuid = $$("#uuid").val();

            xxdApp.showIndicator('正在努力注册，请稍后...');
            req.callPost({
                url: 'user/regV3.do',
                data: {
                    phone:mobileNo,
                    userName: username,
                    password: $.md5($.md5(password)),
                    referer: '',
                    uuid:uuid,
                    regsource: 7, //用户来源1：网站注册,2：手机客户端注册,3：合作商户注册,4：合作商户导入,5：微信注册,6：移动网页注册,7:webapp(html5)'
                    job:job,
                    fundActivityCode:fundActivityCode,
                    pCode: pcode,
                    vipCode: vipCode,
                    smsCode:smsCode
                },
                dataType: 'json',
                timeout:20000,
                success:function(result){
                    xxdApp.hideIndicator();
                    if (result.resultCode != 0 ) {
                        xxdApp.alert(result.msg, '提示');
                    } else if( result.regResultCode != 0){
                        xxdApp.alert(result.regDesc, '提示');
                    } else {
                        appFunc.autoUserBind({successCallBack:function(){},failCallBack:function(){
                            xxdApp.alert("注册成功，绑定微信账号失败。<br>如想继续尝试绑定，请重新登录！","温馨提示");
                        }});
                        try{
                    		//XXD_TRACK.track_eventview("reg_success_webapp", "button", "注册成功",result.userid);
                    		//GA部署
                            gaClickEvent_UserId({property1:"注册",property2:"注册成功",property3:"手机注册"});
                    		
                    	}catch(e){}
                    	if(job == 'student'){
                    		 $$("#panelLeft_username").html(result.userName);
                    		 GS.load({
                                 url: 'user/registerStudentReturn.html?path=user&job='+job,
                                 context: {'front_url':result.front_url}
                             });
                            
                    	}else{
                    		 $$("#panelLeft_username").html(result.userName);
                             GS.loadPage('user/registerReturn.html?path=user');
                    	}
                    }
                }
            });
        }
    };
    return registerStep2Ctrl;
});