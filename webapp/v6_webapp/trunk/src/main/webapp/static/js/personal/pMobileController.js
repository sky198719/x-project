/**
 *
 * 手机绑定
 * Created by pufei on 2015/2/10.
 */
define(['js/personal/pMobileView'], function (pMobileView) {
    var time = 120;
    var intervalArray = [];
    var voiceSwitch = "false";
    var pMobileCtrl = {
        init: function () {
            pMobileCtrl.clearInterval();
            pMobileCtrl.eventBind();
            voiceSwitch = GS.getSysConfig().voiceSwitch;
        },
        /**清空定时器，隐藏语音验证码*/
        clearInterval: function () {
            $$.each(intervalArray, function (index, value) {
                clearInterval(value);
            });
            intervalArray.length = 0;
            // if (options.type == "voice") {
                // $$('#pMobile #rand-code-countdown').hide();
                // $$('#pMobile #sendMobileCode').show();
            // } else {
                // $$('#pMobile #voice_sms').hide();
                // $$('#pMobile #rand-code-countdown').hide();
                // $$('#pMobile #voiceCode').show().css('display', 'inline');
            // }
        },
        eventBind: function () {
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#mobileAppro',
                    event: 'click',
                    handler: pMobileCtrl.mobileAppro
                },
                {
                    element: '#pMobile #sendMobileCode',
                    event: 'click',
                    handler: pMobileCtrl.sendMobileCode
                },
                {

                    element: '#pMobile #voiceCode',
                    event: 'click',
                    handler: pMobileCtrl.sendVoiceSMSLogin
                }
            ];
            pMobileView.init({
                bindings: bindings
            });
        },
        mobileAppro: function () {
            var approveMobile = $$('#mobile-number').val();
            var mobileSMSCode = $$('#check').val();
            if ("" == approveMobile) {
                xxdApp.alert("必须填写手机号码，请重新输入", "温馨提示");
                return;
            }
            if (approveMobile.length != 11 || isNaN(approveMobile)) {
                xxdApp.alert("请输入正确的手机号码", "抱歉");
                return;
            }

            //验证码
            var mobileSMSCode = $$("#check").val();
            if ("" == mobileSMSCode) {
                xxdApp.alert("必须填写验证码，请重新输入", "抱歉");
                return;
            }

            req.callPost({
                url: "approve/mobileAppro.do",
                data: {
                    mobileSMSCode: mobileSMSCode,
                    approveMobile: approveMobile
                },
                dataType: 'json',
                preloaderTitle: '正在认证，请稍后...',
                success: function (data) {
                    if (data.resultCode == 2) {
                        xxdApp.alert("验证成功!", "成功", function () {
                            $$("#check_mobile_daojishi").html('<a href="#" id="sendMobileCode" class="ic-blue">发送验证码</a>');
                            //清除定时器
                            pMobileCtrl.clearInterval();
                            GS.loadPage('personal/personalInfo.html');
                        });
                    } else {
                        xxdApp.alert(data.msg, "抱歉");
                    }
                }
            });
        },
        sendMobileCode: function () {

            var approveMobile = $.trim($$("#mobile-number").val());
            if ("" == approveMobile) {
                //alert("必须填写手机号码，请重新输入");
                xxdApp.alert("必须填写手机号码，请重新输入", "提示");
                return;
            }
            if (approveMobile.length != 11 || isNaN(approveMobile)) {
                xxdApp.alert("请输入正确的手机号码", "提示");
                return;
            }
            var vaRe = appFunc.validateMobile(approveMobile);
            if (vaRe != "true") {
                xxdApp.alert(vaRe, '提示');
                return false;
            }

            req.callJSON({
                url: "approve/checkMobileAndSendSMS.do",
                data: {
                    "phone": approveMobile,
                    "sendType": '0'//发送短信验证码
                },
                preloaderTitle: '正在发送短信，请稍后...',
                success: function (data) {
                    var tempTime = time;
                    if (data.resultCode == 0) {
                        pMobileCtrl.clearInterval();
                        xxdApp.alert("验证码发送成功！请注意查收...", "成功");
                        $$('#pMobile #sendMobileCode').hide();
                        $$('#pMobile #voice_sms').hide();
                        $$('#pMobile #rand-code-countdown').show().html(tempTime);
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#pMobile #rand-code-countdown').hide();
                                $$('#pMobile #sendMobileCode').show();
                                $$('#pMobile #voice_sms').show();
                                //清除定时器
                                pMobileCtrl.clearInterval();
                                // clearInterval(intervalArray[0]);
                                // intervalArray = [];
                            } else {
                                //获取短信验证码超过30秒，还未进行验证，显示获取语音验证码按钮
                                if (voiceSwitch == "true" && time - tempTime > 60) {
                                    $$('#pMobile #voice_sms').show();
                                }
                                $$('#pMobile #rand-code-countdown').html(tempTime);
                            }
                        }, 1000));
                    } else {
                        xxdApp.alert(data.msg, "提示");
                        $$('#pMobile #rand-code-countdown').hide();
                        $$('#pMobile #sendMobileCode').show();
                        // if(data.resultCode == -30){
                            $$('#pMobile #voice_sms').show();
                        // }
                    }
                }
            });

        },

        /**
         * 发送语音验证码
         * */
        sendVoiceSMSLogin: function () {

            var mobileNo = $$('#mobile-number').val();
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
                url: 'approve/checkMobileAndSendSMS.do',
                data: {
                    "phone": mobileNo,
                    "sendType": '1'
                },
                preloaderTitle: '正在发送语音短信，请稍后...',
                success: function (result) {
                    var tempTime = time;
                    if (result.resultCode == 0) {
                        pMobileCtrl.clearInterval();
                        xxdApp.alert('请接听来自125909888353的电话，为您自动播报语音验证码', '发送成功');
                        $$('#pMobile #voice_sms').hide();
                        $$('#pMobile #sendMobileCode').hide();
                        // $$('#pMobile #rand-code-countdown').show().css('display', 'inline').css('color', 'grey').html(tempTime);
                        $$('#pMobile #rand-code-countdown').show().html(tempTime);
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#pMobile #rand-code-countdown').hide();
                                $$('#pMobile #voice_sms').show();
                                $$('#pMobile #sendMobileCode').show();
                                // $$('#pMobile #voiceCode').show().css('display', 'inline');
                                // 清除定时器
                                pMobileCtrl.clearInterval();
                                // clearInterval(intervalArray[0]);
                                // intervalArray = [];
                            } else {
                                $$('#pMobile #rand-code-countdown').html(tempTime);
                            }
                        }, 1000));
                    } else if (result.resultCode == 400) {
                        xxdApp.alert('会话失效，请重新登录', '提示', function () {
                            xxdApp.loginScreen();
                        });
                        return;
                    } else {
                        xxdApp.alert(result.msg, '提示');
                        // $$('#pMobile #rand-code-countdown').hide();
                        // $$('#pMobile #voiceCode').show().css('display', 'inline');
                    }
                }
            });
        },

        //检查手机号是否已被使用
        checkMobileExist: function (mobileNo) {
            var exist = false;
            req.callJSON({
                url: 'user/checkMobile.do',
                data: {
                    mobile: mobileNo
                },
                async: false,
                preloaderTitle: '正在验证手机号，请稍后...',
                success: function (data) {
                    if (data == 0) {
                        exist = false;
                    } else if (data == 1) {
                        exist = true;
                    }
                }
            });
            return exist;
        }
    };
    return pMobileCtrl;
});
