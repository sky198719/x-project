define(['zeptoMd5','js/home/homeController'], function (zeptoMd5,homeController) {
	var score = "";
    var login01Ctrl = {
        init: function (event) {
			mainView.hideNavbar();
			$$("#login01").css("padding-top", "0px");
            $$("title").html("登录新新贷");
            login01Ctrl.bindEvents();
            
            score = appFunc.getEventDetailPageQuery(event).score;
            score = score == undefined ? "" : score ;
        },

        bindEvents: function () {
            var bindings = [
                {
                    element: '#login01_submit',
                    event: 'click',
                    handler: login01Ctrl.login
                },
                {
                    element: '#to-register01',
                    event: 'click',
                    handler: login01Ctrl.toRegister
                },
                {
                    element: '#to-forgetpwLogin01',
                    event: 'click',
                    handler: login01Ctrl.toForgetpwLogin
                }
            ];
            appFunc.bindEvents(bindings);
        },

        toForgetpwLogin: function () {
            GS.loadPage('security/forgetpwLogin.html?path=security');
        },
        toRegister: function () {
            GS.loadPage('activityRegAndLogin/register01.html?score=' + score);
        },
        /**
         * 登录
         */
        login: function () {
            var loginName = $$('#login01 #login01_username').val();
            var password = $$('#login01 #login01_password').val();
            if (loginName === '' || password === '') {
                xxdApp.alert('用户名或密码不能为空',"提示");
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
                    $$('#login01 #login01_password').val('');
                    if (result.resultCode == 0) {
                        $$("#panelLeft_username").html(loginName);
                        var headimg = result.front_url + "/" + result.user.headImg;
                        $$("#headimg").attr("src", headimg);
                        
                        GS.loadPage('gameExchange/exchangeResult.html?score=' + score);
                        
                    } else if (result.resultCode == -1) {
                        xxdApp.alert("用户名称不存在，请重新输入","提示");
                    } else if (result.resultCode == -2) {
                        xxdApp.alert("密码错误，请重新输入","提示");
                    } else if (result.resultCode == -3 || result.resultCode == -4) {
                        xxdApp.alert(result.msg,"提示");
                    } else if (result.resultCode == -9) {
                        xxdApp.alert("登录失败，请重新尝试或者联系客服","提示");
                    } else {
                        xxdApp.alert(result.msg,"提示");
                    }
                }
            });
        }
    };
    return login01Ctrl;
});
