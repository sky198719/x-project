/**
 * 友融CPA合作专属注册页面加息1%
 */
define(function () {
    var intervalArray = [];
    var fundCpaAwardCtrl = {
        init: function () {
            $$("title").html("日日盈加息1%-新新贷注册");
            $$.each(intervalArray,function(index,value){
                clearInterval(value);
            });
            intervalArray.length = 0;
            var bindings = [
                {
                    element: '#cpa-d11-send-rand-code',
                    event: 'click',
                    handler: fundCpaAwardCtrl.sendRandCode
                },
                {
                    element: '#cpa-d11_reg_next',
                    event: 'click',
                    handler: fundCpaAwardCtrl.regNext
                },
                {
                    element: '.cpa-d11_login',
                    event: 'click',
                    handler: fundCpaAwardCtrl.isLogin
                }
            ];
            appFunc.bindEvents(bindings);

        },

        sendRandCode: function () {
            var mobileNo = $$('#cpa-d11-mobile-no').val();
            if (mobileNo == '') {
                xxdApp.alert('手机号码不能为空', '提示');
                return false;
            }

            var vaRe = appFunc.validateMobile(mobileNo);
            if (vaRe != "true") {
                xxdApp.alert(vaRe, '提示');
                return false;
            }

            req.callGet({
                url: 'user/sendSMS.do',
                data: {
                    phone: mobileNo,
                    type: 1
                },
                dataType: 'json',
                preloaderTitle:'正在发送短信，请稍后...',
                success:function(data){
                    var time = 120;
                    var result = data.resultCode;
                    if (result == '0') {
                        xxdApp.alert('验证码发送成功，请注意查收', '提示');
                        $$('#cpa-d11-send-rand-code').hide();
                        $$('#cpa-d11-send-rand-code-countdown').show().html(time);
                        intervalArray.push(setInterval(function () {
                            time = time - 1;
                            if (time < 0) {
                                $$('#cpa-d11-send-rand-code-countdown').hide();
                                $$('#cpa-d11-send-rand-code').show();
                                // 清除定时器
                                clearInterval(intervalArray[0]);
                            } else {
                                $$('#cpa-d11-send-rand-code-countdown').html(time);
                            }
                        }, 1000));
                    } else if (result == '-1') {
                        xxdApp.alert('此号码已被注册,请更换后再尝试', '提示');
                        $$('#cpa-d11-send-rand-code-countdown').hide();
                        $$('#cpa-d11-send-rand-code').show();
                    } else if (result == '-2') {
                        xxdApp.alert('短信发送失败', '提示');
                        $$('#cpa-d11-send-rand-code-countdown').hide();
                        $$('#cpa-d11-send-rand-code').show();
                    } else if (result == '-30') {
                        xxdApp.alert('您短信发送太频繁，如果无法收到短信请联系客服或使用语音验证码！', '提示');
                        $$('#cpa-d11-send-rand-code-countdown').hide();
                        $$('#cpa-d11-send-rand-code').show();
                    }
                }
            });
        },
        regNext: function () {
            var mobileNo = $$('#cpa-d11-mobile-no').val();
            if (mobileNo == '') {
                xxdApp.alert('手机号码不能为空', '提示');
                return false;
            }
            var vaRe = appFunc.validateMobile(mobileNo);
            if (vaRe != "true") {
                xxdApp.alert(vaRe);
                return false;
            }

            var randCode = $$('#cpa-d11-rand-code').val();
            if (randCode == '') {
                xxdApp.alert('短信验证码不能为空', '提示');
                return false;
            }
            //try{XXD_TRACK.track_eventview("cpa-d11_reg_next", "button", "立即注册");}catch(e){}
            req.callPost({
                url: 'user/checkSMS.do',
                data: {
                   code: randCode,
                   mobileNo: mobileNo,
                   busiCode: 'BUSICODE_REGISTER_SMS'
                },
                dataType: 'text',
                indicator:true,
                success:function(result){
                    if (result == '0') {
                    	//try{XXD_TRACK.track_eventview("cpa-d11_reg_next_success", "button", "验证成功", mobileNo);}catch(e){}
                        GS.loadPage('user/registerStep2.html?path=user');
                    } else if (result == '1') {
                        xxdApp.alert('验证码错误');
                    }
                }
            });
        },
        isLogin:function(){
            if (!appFunc.isLogin()) {
                $$(".login-screen").prop("redirectionUrl","index/home.html");
                xxdApp.loginScreen();
                return;
            } else {
                GS.reloadPage("index/home.html");
            }
        }
    };

    return fundCpaAwardCtrl;
});