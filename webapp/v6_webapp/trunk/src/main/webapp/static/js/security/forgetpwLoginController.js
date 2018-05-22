/**
 * 忘记登录密码-第一步
 */
define(['js/security/forgetpwLoginView'], function (forgetpwLoginView) {
    var intervalArray = [];
    var time = 120;
    var voiceSwitch = "false";
    var forgetpwLoginCtrl = {
        init: function () {
        	mainView.showNavbar();
            forgetpwLoginCtrl.clearInterval();
            forgetpwLoginCtrl.eventBind();
            voiceSwitch = GS.getSysConfig().voiceSwitch;
        },
        /**清空定时器，隐藏语音验证码*/
        clearInterval: function () {
            $$.each(intervalArray, function (index, value) {
                clearInterval(value);
            });
            intervalArray.length = 0;
            // if (options.type == "voice") {
                // $$('#forgetpwLogin #rand-code-countdown').hide();
                // $$('#forgetpwLogin #send-rand-code').show();
            // } else {
                // $$('#forgetpwLogin #voice_sms').hide();
                // $$('#forgetpwLogin #voiceCode-countdown').hide();
                // $$('#forgetpwLogin #voiceCode').show().css('display', 'inline');
            // }
        },

        eventBind: function () {
            var bindings = [
                {
                    element: '#forgetpwLogin #send-rand-code',
                    event: 'click',
                    handler: forgetpwLoginCtrl.sendRandCode
                },
                {
                    element: '#forgetpwLogin #to-forgetpwLoginStep2',
                    event: 'click',
                    handler: forgetpwLoginCtrl.toForgetpwLoginStep2
                },
                {
                    element: '#voiceCode',
                    event: 'click',
                    handler: forgetpwLoginCtrl.sendVoiceSMSLogout
                }
            ];
            forgetpwLoginView.init({bindings: bindings});
        },

        sendRandCode: function () {
            var mobileNo = $$('#forgetpwLogin #mobile-no').val();
            if (mobileNo == '') {
                xxdApp.alert('手机号码不能为空', '提示');
                return false;
            }

            var vaRe = appFunc.validateMobile(mobileNo);
            if (vaRe != "true") {
                xxdApp.alert(vaRe);
                return false;
            }

            req.callJSON({
                url: 'user/sendSMSForFindPwd.do',
                data: {
                    phone: mobileNo,
                    type: 2
                },
                dataType: 'json',
                preloaderTitle: '正在发送短信，请稍后...',
                success: function (result) {
                    var tempTime = time;
                    if (result.resultCode == '0') {
                        forgetpwLoginCtrl.clearInterval();
                        xxdApp.alert('验证码发送成功，请注意查收', '提示');
                        $$('#forgetpwLogin #send-rand-code').hide();
                        $$('#forgetpwLogin #voice_sms').hide();
                        $$('#forgetpwLogin #rand-code-countdown').show().html(tempTime);
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#forgetpwLogin #rand-code-countdown').hide();
                                $$('#forgetpwLogin #send-rand-code').show();
                                $$('#forgetpwLogin #voice_sms').show();
                                // 清除定时器
                                forgetpwLoginCtrl.clearInterval();
                                // clearInterval(intervalArray[0]);
                                // intervalArray = [];
                            } else {
                                //获取短信验证码超过30秒，还未进行验证，显示获取语音验证码按钮
                                if (voiceSwitch == "true" && time - tempTime > 30) {
                                    $$('#forgetpwLogin #voice_sms').show();
                                }
                                $$('#forgetpwLogin #rand-code-countdown').html(tempTime);
                            }
                        }, 1000));
                    // } else if (result.resultCode == '-30') {
                        // xxdApp.alert('您短信发送太频繁，如果无法收到短信请联系客服或使用语音验证码！', '提示');
                        // $$('#forgetpwLogin #rand-code-countdown').hide();
                        // $$('#forgetpwLogin #send-rand-code').show();
                        // $$('#forgetpwLogin #voice_sms').show();
                    } else {
                        xxdApp.alert(result.msg, '提示');
                        $$('#forgetpwLogin #rand-code-countdown').hide();
                        $$('#forgetpwLogin #send-rand-code').show();
                        $$('#forgetpwLogin #voice_sms').show();
                    }
                }
            });
        },

        //发送语音验证码
        sendVoiceSMSLogout: function () {

            var mobileNo = $$('#forgetpwLogin #mobile-no').val();
            if (mobileNo == '') {
                xxdApp.alert('手机号码不能为空', '提示');
                return false;
            }

            var vaRe = appFunc.validateMobile(mobileNo);
            if (vaRe != "true") {
                xxdApp.alert(vaRe, '提示');
                return false;
            }

            req.callJSON({
                url: 'user/sendVoiceSMSLogout.do',
                data: {
                    mobileNo: mobileNo,
                    busiCode: 'BUSICODE_RETRIEVE_PASSWORD'
                },
                preloaderTitle: '正在发送语音短信，请稍后...',
                success: function (result) {
                    var tempTime = time;
                    if (result.resultCode == 0) {
                        forgetpwLoginCtrl.clearInterval();
                        xxdApp.alert('请接听来自125909888353的电话，为您自动播报语音验证码', '发送成功');
                        $$('#forgetpwLogin #voice_sms').hide();
                        $$('#forgetpwLogin #send-rand-code').hide();
                        $$('#forgetpwLogin #rand-code-countdown').show().html(tempTime);
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#forgetpwLogin #rand-code-countdown').hide();
                                // $$('#forgetpwLogin #voiceCode').show().css('display', 'inline');
                                $$('#forgetpwLogin #voice_sms').show();
                                $$('#forgetpwLogin #send-rand-code').show();
                                // 清除定时器
                                forgetpwLoginCtrl.clearInterval();
                                // clearInterval(intervalArray[0]);
                                // intervalArray = [];
                            } else {
                                $$('#forgetpwLogin #rand-code-countdown').html(tempTime);
                            }
                        }, 1000));
                    } else {
                        xxdApp.alert(result.msg, '提示');
                        // $$('#forgetpwLogin #voiceCode-countdown').hide();
                        // $$('#forgetpwLogin #voiceCode').show().css('display', 'inline');
                    }
                }
            });
        },

        toForgetpwLoginStep2: function () {
            var mobileNo = $$('#forgetpwLogin #mobile-no').val();
            if (mobileNo == '') {
                xxdApp.alert('手机号码不能为空', '提示');
                return false;
            }
            var vaRe = appFunc.validateMobile(mobileNo);
            if (vaRe != "true") {
                xxdApp.alert(vaRe);
                return false;
            }

            var randCode = $$('#forgetpwLogin #rand-code').val();
            if (randCode == '') {
                xxdApp.alert('短信验证码不能为空', '提示');
                return false;
            }

            req.callPost({
                url: 'user/checkSMS.do',
                data: {
                    code: randCode,
                    mobileNo: mobileNo,
                    busiCode: 'BUSICODE_RETRIEVE_PASSWORD'
                },
                dataType: 'text',
                preloaderTitle: '正在验证，请稍后...',
                success: function (result) {
                    if (result == '0') {
                        GS.loadPage('security/forgetpwLoginStep2.html?path=security&mobileNo=' + mobileNo + '&randCode=' + randCode);
                    } else if (result == '1') {
                        xxdApp.alert('短信验证码错误', '提示');
                    }
                }
            });
        }
    };

    return forgetpwLoginCtrl;
});