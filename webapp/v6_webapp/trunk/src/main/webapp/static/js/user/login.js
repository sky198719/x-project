define(['zeptoMd5','js/home/homeController'], function (zeptoMd5,homeController) {
    var login = {
        initLogin: function (options) {
            var isLoad = $$(".login-screen").attr("isLoad");
            if (isLoad == "true") {
                return;
            }

            GS.loadHtml({
                url: "user/login.html",
                callBack: function (data) {
                    options.callBack(data);
                }
            });
        },

        bindEvents: function () {
            var bindings = [
                {
                    element: '#login_submit',
                    event: 'click',
                    handler: login.login
                },
                {
                    element: '.close-login-screen',
                    event: 'click',
                    handler: login.closeLogin
                },
                {
                    element: '#to-register',
                    event: 'click',
                    handler: login.toRegister
                },
                {
                    element: '#to-forgetpwLogin',
                    event: 'click',
                    handler: login.toForgetpwLogin
                }
            ];
            appFunc.bindEvents(bindings);
        },
        endWith: function (sourceStr, endStr) {
            var d = sourceStr.length - endStr.length;
            return ( d >= 0 && sourceStr.lastIndexOf(endStr) == d)
        },
        closeLogin: function () {
            /*var pathname = window.location.pathname;
            console.log("pathname="+pathname);
            if (pathname.lastIndexOf("home.html") >= 0 || login.endWith(pathname, "/")) {
                var hash = window.location.hash;
                if (hash != "") {
                    GS.reloadPage("index/home.html");
                }
            }    */
            xxdApp.closePanel();
        },

        toForgetpwLogin: function () {
            xxdApp.closeModal('.login-screen');
            xxdApp.closePanel();
            GS.loadPage('security/forgetpwLogin.html?path=security');
        },
        toRegister: function () {
            xxdApp.closeModal('.login-screen');
            xxdApp.closePanel();
            var job = $$('#job').val();
            if (job == 'student') {
                GS.loadPage('user/register.html?path=user&job=student');
            } else {
                var isCloseLoginRegister = $$(".login-screen").prop("isCloseLoginRegister");
                if(isCloseLoginRegister != undefined && isCloseLoginRegister == 'true') {
                   // GS.loadPage(toRegisterUrl);
                } else  {
                    GS.loadPage('user/register.html?path=user');
                }
            }
        },
        /**
         * 登录
         */
        login: function () {
            var loginName = $.trim($$('#login #login_username').val());
            var password = $.trim($$('#login #login_password').val());
            if (loginName === '' || password === '') {
                xxdApp.alert('用户名或密码不能为空');
                return false;
            }

            req.callPost({
                url: 'user/loginActive.do',
                preloaderTitle: "正在登录...",
                data: {
                    username: loginName,
                    password: $.md5($.md5(password))
                },
                dataType: 'json',
                success: function (result) {
                    //result = result ? JSON.parse(result) : {resultCode: -400, msg: "系统返回错误"};
                    $$('#login #login_password').val('');
                    if (result.resultCode == 0) {
                    	$$("body").attr("data-userId",result.user.userId);
                        $$("#panelLeft_username").html(loginName);
                        var headimg = result.front_url + "/" + result.user.headImg;
                        $$("#headimg").attr("src", headimg);
                        //if(XXDAPP_CONSTANT)XXDAPP_CONSTANT.USER_ID=result.user.userId;
                        try {
                            //XXD_TRACK.track_eventview("login_success_webapp", "button", "登陆成功", result.user.userId);
                          //GA部署
                            gaInits(result.user.userId);
                            gaClickEvent_UserId({property1:"登录",property2:"登录成功",property3:"登录成功"});

                        } catch (e) {
                        }

                        var redirectionUrl = $$(".login-screen").prop("redirectionUrl");
                        if (redirectionUrl != undefined) {
                            GS.reloadPage(redirectionUrl);
                            //mainView.router.reloadPage(redirectionUrl);
                        } else {
                            var view = xxdApp.getCurrentView();
                            if(view.url.indexOf("http") == 0) {
                                homeController.init();
                            } else {
                                mainView.router.refreshPage();
                            }
                        }
                        //登录成功，关闭登录Modal
                        xxdApp.closeModal('.login-screen');
                        xxdApp.closePanel();
                        appFunc.autoUserBind({successCallBack:function(){
                            //登录成功，关闭登录Modal
                            xxdApp.closeModal('.login-screen');
                            xxdApp.closePanel();
                        },failCallBack:function(){
                            xxdApp.alert("登录成功，绑定微信账号失败。<br>如想继续尝试绑定，请重新登录！","温馨提示");
                        }});
                    /*} else if (result.resultCode == -1) {
                        xxdApp.alert("用户名称不存在，请重新输入");
                    } else if (result.resultCode == -2) {
                        xxdApp.alert("密码错误，请重新输入");
                    } else if (result.resultCode == -3 || result.resultCode == -4) {
                        xxdApp.alert(result.msg);
                    } else if (result.resultCode == -9) {
                        xxdApp.alert("登录失败，请重新尝试或者联系客服");*/
                    } else {
                        xxdApp.alert(result.msg);
                    }
                }
            });
        }
    };
    return login;
});
