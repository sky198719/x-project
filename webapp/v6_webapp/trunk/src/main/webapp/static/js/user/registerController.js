/**
 * 注册
 */
define(['js/user/registerView', 'js/utils/date'], function (registerView, DateHandle) {
    var job = 'default';
    var intervalArray = [];
    var fundActivityCode = "";
    var time = 60;
    var voiceSwitch = "false";
    var smsEnableFlag = true;
    var voiceEnableFlag = true;
    var lastPhoneNo = "";
    var pcode = '';
    var vipCode = '';

    var utm_source = '';
    var utm_medium = '';
    var utm_term = '';
    var utm_content = '';
    var utm_campaign = '';

    var registerCtrl = {
        init: function (event) {
            registerCtrl.eventBind();
            registerCtrl.clearInterval();

            if (appFunc.isLogin()) {
                xxdApp.alert("请先退出当前账号，再进行注册！","提示",function(){
                    GS.loadPage("index/home.html");
                });
                return;
            }

            //用户职业
            var page = appFunc.getEventDetailPageQuery(event);

            if(page.pcode != undefined){
                pcode = page.pcode;
            }

            if(page.vipCode != undefined) {
                vipCode = page.vipCode;
            }

            try{
                if(page.utm_source != undefined && '' != page.utm_source) {
                    utm_source = page.utm_source;
                }
                if(page.utm_medium != undefined && '' != page.utm_medium) {
                    utm_medium = page.utm_medium;
                }
                if(page.utm_term != undefined && '' != page.utm_term) {
                    utm_term = page.utm_term;
                }
                if(page.utm_content != undefined && '' != page.utm_content) {
                    utm_content = page.utm_content;
                }
                if(page.utm_campaign != undefined && '' != page.utm_campaign) {
                    utm_campaign = page.utm_campaign;
                }
            }catch (e){}

            job = page.job;
            $$('#job').val(job);

            //日日盈活动码
            if (page.fundActivityCode != undefined) {
                fundActivityCode = page.fundActivityCode;
            }

            //推广码
            var uuid = page.uuid;
            if (uuid != undefined) {
                $$("#uuid").val(uuid);
            }
            registerCtrl.registerTopImg();
            voiceSwitch = GS.getSysConfig().voiceSwitch;
        },

        eventBind:function(){
            var bindings = [
                {
                    element: '#register #send-rand-code',
                    event: 'click',
                    handler: registerCtrl.sendRandCode
                },
                {
                    element: '#register #reg_next',
                    event: 'click',
                    handler: registerCtrl.regNext
                },
                {
                    element: '#register #register-close',
                    event: 'click',
                    handler: registerCtrl.registerClose
                },
                {
                    element: '.open-login-screen',
                    event: 'click',
                    handler: registerCtrl.isLogin
                },
                {
                    element: '#voiceCode',
                    event: 'click',
                    handler: registerCtrl.sendVoiceSMSLogout
                },
                {
                    element: '#register #mobile-no',
                    event: 'keyup',
                    handler: registerCtrl.resetVerifyCodeFlag
                },
                {
                    element:'.reg_img_code',
                    event:'click',
                    handler:registerCtrl.refImgCode
                }
            ];
            registerView.init({bindings: bindings});
        },
        refImgCode:function(){
            $$(".reg_img_code").attr("src","verify/code.do?"+Math.random());
        },
        /**清空定时器，隐藏语音验证码*/
        clearInterval:function(){
            $$.each(intervalArray, function (index, value) {
                clearInterval(value);
            });
            intervalArray.length = 0;
            // if(options.type == "voice") {
                // $$('#register #rand-code-countdown').hide();
                // $$('#register #send-rand-code').removeClass('disable');
            // } else {
                // $$('#register #voice_sms').hide();
                // $$('#register #rand-code-countdown').hide();
                // $$('#register #voiceCode').show().css('display', 'inline');
                // $$('#register #voiceCode').removeClass('disable');
            // }
        },

        registerTopImg: function () {
            var value = appFunc.getCache({key: 'REPACKET_ONOFF'});
            if (value == "Y") {
                $$("#regTopImg").show();
            }
        },

        registerClose: function () {
            GS.reloadPage("index/home.html");
        },

        sendRandCode: function () {
            if($$(this).hasClass("disable") || !smsEnableFlag){
        		return false;
        	}
        	
            var mobileNo = $$('#register #mobile-no').val();
            if (mobileNo == '') {
                xxdApp.alert('手机号码不能为空', '提示');
                return false;
            }

            var vaRe = appFunc.validateMobile(mobileNo);
            if (vaRe != "true") {
                xxdApp.alert(vaRe, '提示');
                return false;
            }

            if (registerCtrl.checkMobileExist(mobileNo)) {
                xxdApp.alert('您输入的手机号已被注册', '提示');
                return false;
            }

            var imgCode = $$(".reg_imgCode").val();
            if(imgCode == '') {
                xxdApp.alert('请输入图片验证码', '提示');
                return false;
            }
            
			lastPhoneNo = mobileNo;
			
            req.callGet({
                url: 'user/sendSMS.do',
                data: {
                    phone: mobileNo,
                    type: 1,
                    imgCode:imgCode
                },
                dataType: 'json',
                preloaderTitle: '正在发送短信，请稍后...',
                success: function (result) {
                    var tempTime = time;
                    if (result.resultCode == '0') {
                        registerCtrl.clearInterval();
                        registerCtrl.refImgCode();
                        xxdApp.alert('验证码发送成功，请注意查收', '提示');
                        $$('#register #send-rand-code').hide();
                        $$('#register #voiceCode').addClass("span-link-disable");
                        $$('#register #rand-code-countdown').show().html(tempTime);
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#register #rand-code-countdown').hide();
                                $$('#register #send-rand-code').show();
                                $$('#register #voiceCode').removeClass("span-link-disable");
                                // 清除定时器
                                registerCtrl.clearInterval();
                                // clearInterval(intervalArray[0]);
                                // intervalArray = [];
                            } else {
                                //获取短信验证码超过30秒，还未进行验证，显示获取语音验证码按钮
//                                if (voiceSwitch == "true" && time - tempTime > 30) {
//                                   $$('#register #voiceCode').removeClass("span-link-disable");
//                                }
                                $$('#register #rand-code-countdown').html(tempTime);
                            }
                        }, 1000));
                    } else {
                        xxdApp.alert(result.msg, '抱歉');
                        $$('#register #rand-code-countdown').hide();
                        $$('#register #send-rand-code').show();
                        
                        if(voiceEnableFlag){
                        	$$('#register #voiceCode').removeClass("span-link-disable");
                        }
                        if(result.resultCode == -250){
                            registerCtrl.refImgCode();
                        }
                        if(result.resultCode == -31){
                            $$('#register #send-rand-code').addClass("disable");
                            smsEnableFlag = false;
                        }
                    }
                }
            });
        },
        //发送语音验证码
        sendVoiceSMSLogout: function () {

        	if($$(this).hasClass("span-link-disable") || !voiceEnableFlag){
        		return false;
        	}
        	
            var mobileNo = $$('#register #mobile-no').val();
            if (mobileNo == '') {
                xxdApp.alert('手机号码不能为空', '提示');
                return false;
            }

            var vaRe = appFunc.validateMobile(mobileNo);
            if (vaRe != "true") {
                xxdApp.alert(vaRe, '提示');
                return false;
            }

            if (registerCtrl.checkMobileExist(mobileNo)) {
                xxdApp.alert('您输入的手机号已被注册', '提示');
                return false;
            }

            lastPhoneNo = mobileNo;
            
            req.callJSON({
                url: 'user/sendVoiceSMSLogout.do',
                data: {
                    mobileNo: mobileNo,
                    busiCode: 'BUSICODE_REGISTER_VOICE'
                },
                preloaderTitle: '正在发送语音短信，请稍后...',
                success: function (result) {
                    var tempTime = time;
                    if (result.resultCode == 0) {
                        registerCtrl.clearInterval();
                        xxdApp.alert('请接听来自125909888353的电话，为您自动播报语音验证码', '发送成功');
                        $$('#register #voiceCode').addClass("span-link-disable");
                        $$('#register #send-rand-code').hide();
                        // $$('#register #rand-code-countdown').show().css('display', 'inline').css('color', 'grey').html(tempTime);
                        $$('#register #rand-code-countdown').show().html(tempTime);
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#register #rand-code-countdown').hide();
                                // $$('#register #voiceCode').show().css('display', 'inline');
                                $$('#register #voiceCode').removeClass("span-link-disable");
                                $$('#register #send-rand-code').show();
                                // 清除定时器
                                registerCtrl.clearInterval();
                                // clearInterval(intervalArray[0]);
                                // intervalArray = [];
                                //frequency = true;
                            } else {
                                $$('#register #rand-code-countdown').html(tempTime);
                                /*if (time < 60) {
                                    frequency = true;
                                } else {
                                    frequency = false;
                                }  */
                            }
                        }, 1000));
                    } else if (result.resultCode == -31) {
                        xxdApp.alert(result.msg, '抱歉');
                        $$('#register #voiceCode').addClass("span-link-disable");
		                voiceEnableFlag = false;
                    } else {
                        xxdApp.alert(result.msg, '提示');
                        // $$('#register #rand-code-countdown').hide();
                        // $$('#register #voiceCode').show().css('display', 'inline');
                        // $$('#register #voiceCode').show();
                    }
                }
            });
        },
        regNext: function () {
            var mobileNo = $$('#register #mobile-no').val();
            if (mobileNo == '') {
                xxdApp.alert('手机号码不能为空', '提示');
                return false;
            }
            var vaRe = appFunc.validateMobile(mobileNo);
            if (vaRe != "true") {
                xxdApp.alert(vaRe);
                return false;
            }

            var randCode = $$('#register #rand-code').val();
            if (randCode == '') {
                xxdApp.alert('短信验证码不能为空', '提示');
                return false;
            }

            req.callPost({
                url: 'user/checkSMS.do',
                data: {
                    code: randCode,
                    mobileNo: mobileNo,
                    busiCode: 'BUSICODE_REGISTER_SMS'
                },
                dataType: 'text',
                indicator: true,
                success: function (result) {
                    if (result == '0') {
                        var uuid = $$("#uuid").val();
                        var path = [];
                        path.push('user/registerStep2.html?path=user');
                        if(uuid != '') {
                            path.push("uuid=" + uuid);
                        }
                        if (fundActivityCode != "") {
                            path.push("fundActivityCode=" + fundActivityCode);
                        }
                        if (job == 'student') {
                            path.push("job=" + job);
                        }
                        if(pcode != ''){
                            path.push("pcode="+pcode);
                        }
                        if(vipCode != '') {
                            path.push("vipCode="+vipCode);
                        }
                        path.push("mobileNo="+mobileNo);
                        path.push("smsCode="+randCode);

                        if(utm_source != '') {
                            path.push("utm_source="+utm_source);
                        }
                        if(utm_medium != '') {
                            path.push("utm_medium="+utm_medium);
                        }
                        if(utm_term != '') {
                            path.push("utm_term="+utm_term);
                        }
                        if(utm_content != '') {
                            path.push("utm_content="+utm_content);
                        }
                        if(utm_campaign != '') {
                            path.push("utm_campaign="+utm_campaign);
                        }
                        GS.loadPage(path.join("&"));
                    } else if (result == '1') {
                        xxdApp.alert('验证码错误');
                    }
                }
            });
        },
        isLogin: function () {
            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
        },
        
        resetVerifyCodeFlag: function () {
        	var mobileNo = $$('#register #mobile-no').val();
        	if(mobileNo != lastPhoneNo){
        		smsEnableFlag = true;
	    		voiceEnableFlag = true;
	    		$$('#register #send-rand-code').removeClass("disable");
	    		$$('#register #voiceCode').removeClass("span-link-disable");
        	}
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

    return registerCtrl;
});