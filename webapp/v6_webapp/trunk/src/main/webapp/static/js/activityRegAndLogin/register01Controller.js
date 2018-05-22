/**
 * 打飞机游戏注册
 */
define([ 'zeptoMd5'], function (zeptoMd5) {
    var intervalArray = [];
    var countdownSecond = 60;
    var countdownFinish = true;
    var score = "";
    var register01Ctrl = {
        init: function (event) {
            register01Ctrl.clearIntervalArray();
			register01Ctrl.bindEventAll();
			mainView.hideNavbar();
			$$("#register01").css("padding-top", "0px");
            $$("title").html("注册成功即可获得新新币");
            
            register01Ctrl.regActivity();
            
            score = appFunc.getEventDetailPageQuery(event).score;
            score = score == undefined ? "" : score ;
            
        },

        bindEventAll: function () {
            var bindings = [
                {
                    element: '#register01 #phoneNo',
                    event: 'keyup',
                    handler: register01Ctrl.formatPhoneNo
                },
                {
                    element: '#register01 .reCreateImageCode',
                    event: 'click',
                    handler: register01Ctrl.reCreateImageCode
                },
                {
                    element: '#register01 #sendSMSCode',
                    event: 'click',
                    handler: register01Ctrl.sendSMSCode
                },
                {
                    element: '#register01 #sendVoiceCode',
                    event: 'click',
                    handler: register01Ctrl.sendVoiceCode
                },
                {
                    element: '#register01 #loadRegisterAgreement',
                    event: 'click',
                    handler: register01Ctrl.loadRegisterAgreement
                },
                {
                    element: '#register01 #switchPasswordEye',
                    event: 'click',
                    handler: register01Ctrl.switchPasswordEye
                },
                {
                    element: '#register01 #toLogin01',
                    event: 'click',
                    handler: register01Ctrl.goLogin
                },
                {
                    element: '#register01 #submitRegister',
                    event: 'click',
                    handler: register01Ctrl.submitRegister
                },
                {
                	element: '#register01 #phoneNo',
                	event: 'blur',
                	handler: register01Ctrl.blurPhoneNo
                },
                {
                	element: '#register01 #imgCode',
                	event: 'blur',
                	handler: register01Ctrl.blurImgCode
                },
                {
                	element: '#register01 #phoneVerifyCode',
                	event: 'blur',
                	handler: register01Ctrl.blurPhoneVerifyCode
                },
                {
                	element: '#register01 #userPassword',
                	event: 'blur',
                	handler: register01Ctrl.blurUserPassword
                }
            ];
            appFunc.bindEvents(bindings);
        },
        blurPhoneNo: function () {
        	// try {XXD_TRACK._trackEvent({category:"webapp_game_reg",action:"game_phone",label:"手机号",value:register01Ctrl.clearSpace($('#register01 #phoneNo').val()),custval:""});}catch(e){}
        },
        blurImgCode: function () {
        	//try {XXD_TRACK._trackEvent({category:"webapp_game_reg",action:"game_verifycode",label:"图片验证码",value:"",custval:""});}catch(e){}
        },
        blurPhoneVerifyCode: function () {
        	//try {XXD_TRACK._trackEvent({category:"webapp_game_reg",action:"game_sms_verifycode",label:"短信验证码",value:"",custval:""});}catch(e){}
        },
        blurUserPassword: function () {
        	//try {XXD_TRACK._trackEvent({category:"webapp_game_reg",action:"game_login_pwd",label:"登录密码",value:"",custval:""});}catch(e){}
        },
        checkPhoneNo: function () {
            var phoneNo = register01Ctrl.clearSpace($('#register01 #phoneNo').val());
            if (phoneNo == '') {
                return {flag: false, msg: '请输入手机号'};
            }
            var vaRe = appFunc.validateMobile(phoneNo);
            if (vaRe != "true") {
            	return {flag: false, msg: '手机号错误，请重新确认'};
            }

            if (register01Ctrl.checkMobileExist(phoneNo)) {
            	return {flag: false, msg: '您输入的手机号已被注册'};
            }
            return {flag: true, msg: '手机号校验通过'};
        },
        checkImgCode: function () {
            var imgCode = $.trim($("#register01 #imgCode").val());
            if (imgCode == undefined || '' == imgCode) {
            	return {flag: false, msg: '请输入图片验证码'};
            }
            if (!register01Ctrl.checkVerifyCode(imgCode)) {
            	return {flag: false, msg: '图片验证码错误，请重新确认'};
            }
            return {flag: true, msg: '图片验证码校验通过'};
        },
        sendSMSCode: function () {
        	
        	if(countdownFinish == false){
        		return;
        	}
        	
            var phoneNo = register01Ctrl.clearSpace($('#register01 #phoneNo').val());
            var checkPhoneResult = register01Ctrl.checkPhoneNo();
            if (checkPhoneResult.flag == false) {
            	xxdApp.alert(checkPhoneResult.msg, '提示');
                return false;
            }
            var imgCode = $.trim($("#register01 #imgCode").val());
            var checkImgCodeResult = register01Ctrl.checkImgCode();
            if (checkImgCodeResult.flag == false) {
            	xxdApp.alert(checkImgCodeResult.msg, '提示');
                return false;
            }

            req.callJSON({
                url: 'user/sendSMS.do',
                data: {
                    phone: phoneNo,
                    type: 1,
                    imgCode: imgCode
                },
                dataType: 'json',
                timeout: 30000,
                indicator: true,
                success: function (result) {
                	countdownFinish = false;
                    var tempTime = countdownSecond;
                    if (result.resultCode == '0') {
                        register01Ctrl.clearIntervalArray();
                        xxdApp.alert('验证码发送成功，请注意查收', '提示');
                        $$('#register01 #voiceCode').hide();
                        $$('#register01 #sendSMSCode').hide();
                        $$('#register01 #phoneVerifyCodeCountdown').show(tempTime + '秒后重新发送');
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#register01 #phoneVerifyCodeCountdown').hide();
                                $$('#register01 #sendSMSCode').show();
                                countdownFinish = true;
                                // 清除定时器
                                register01Ctrl.clearIntervalArray();
                            } else {
                                $$('#register01 #phoneVerifyCodeCountdown').html(tempTime + '秒后重新发送');
                            }
                        }, 1000));
                    } else {
                    	countdownFinish = true;
                        xxdApp.alert(result.msg, '抱歉');
                        $$('#register01 #phoneVerifyCodeCountdown').hide();
                        $$('#register01 #sendSMSCode').show();
                        $$('#register01 #voiceCode').show();
                    }
                }
            });
        },
        //发送语音验证码
        sendVoiceCode: function () {
        	
        	if(countdownFinish == false){
        		return;
        	}
        	
        	var phoneNo = register01Ctrl.clearSpace($('#register01 #phoneNo').val());
            var checkPhoneResult = register01Ctrl.checkPhoneNo();
            if (checkPhoneResult.flag == false) {
            	xxdApp.alert(checkPhoneResult.msg, '提示');
                return false;
            }
            var imgCode = $.trim($("#register01 #imgCode").val());
            var checkImgCodeResult = register01Ctrl.checkImgCode();
            if (checkImgCodeResult.flag == false) {
            	xxdApp.alert(checkImgCodeResult.msg, '提示');
                return false;
            }
            
            req.callJSON({
                url: 'user/sendVoiceSMSLogout.do',
                data: {
                    mobileNo: phoneNo,
                    busiCode: 'BUSICODE_REGISTER_SMS',
                    imgCode: imgCode
                },
                dataType: 'json',
                timeout: 30000,
                indicator: true,
                success: function (result) {
                	countdownFinish = false;
                    var tempTime = countdownSecond;
                    if (result.resultCode == 0) {
                        register01Ctrl.clearIntervalArray();
                        xxdApp.alert('请接听来自125909888353的电话，为您自动播报语音验证码', '提示');
                        $$('#register01 #sendSMSCode').hide();
                        $$('#register01 #phoneVerifyCodeCountdown').show().html(tempTime + '秒后重新发送');
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#register01 #phoneVerifyCodeCountdown').hide();
                                $$('#register01 #sendSMSCode').show();
                                countdownFinish = true;
                                // 清除定时器
                                register01Ctrl.clearIntervalArray();
                            } else {
                                $$('#register01 #phoneVerifyCodeCountdown').html(tempTime + '秒后重新发送');
                            }
                        }, 1000));
                    } else {
                    	countdownFinish = true;
                        xxdApp.alert(result.msg, '抱歉');
                    }
                }
            });
        },
        //加载注册协议
        loadRegisterAgreement: function () {
            req.callGet({
                url: GC.getHtmlPath() + 'activityRegAndLogin/xxdRegisterAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                indicator: true,
                success: function (result) {

                    var deviceH = document.documentElement.clientHeight - 8 * 2 - 8 * 2 - 43 + "px";
                    result = result.replace("customClientHeight", deviceH);
                    var buttons = [
                        {
                            text: result,
                            label: true
                        },
                        {
                            text: '继续注册',
                            color: 'red'
                        }
                    ];
                    xxdApp.actions(buttons);

                }
            });
        },
        reCreateImageCode: function () {
            $$("#register01 #randImage").attr("src", "randCode/createVerifyCode.do?" + Math.random());
        },
        checkVerifyCode: function (imgCode) {
            var bool = false;
            req.callJSON({
                url: 'randCode/checkVerifyCode.do',
                data: {
                    imgCode: imgCode
                },
                dataType: 'json',
                timeout: 10000,
                indicator: true,
                async: false,
                success: function (result) {
                    if (result.resultCode == 0) {
                        bool = true;
                    }
                }
            });
            return bool;
        },
        submitRegister: function () {
        	// try {XXD_TRACK._trackEvent({category:"webapp_game_reg",action:"game_reg_button",label:"注册按钮",value:register01Ctrl.clearSpace($('#register01 #phoneNo').val()),custval:""});}catch(e){}
            var mobileNo = register01Ctrl.clearSpace($('#register01 #phoneNo').val());
            var checkPhoneResult = register01Ctrl.checkPhoneNo();
            if (checkPhoneResult.flag == false) {
            	xxdApp.alert(checkPhoneResult.msg, '提示');
                return false;
            }
            var imgCode = $.trim($("#register01 #imgCode").val());
            var checkImgCodeResult = register01Ctrl.checkImgCode();
            if (checkImgCodeResult.flag == false) {
            	xxdApp.alert(checkImgCodeResult.msg, '提示');
                return false;
            }
            
            var randCode = $.trim($('#register01 #phoneVerifyCode').val());
            if (randCode == '') {
            	xxdApp.alert("请输入短信验证码", '提示');
                return false;
            }
            var password = $.trim($('#register01 #userPassword').val());
            if (password == '') {
            	xxdApp.alert("请输入密码", '提示');
                return false;
            }
            if (appFunc.validatePassword(password) != 'true') {
            	xxdApp.alert("有效密码为6-16位数字字母组合", '提示');
                return false;
            }

            req.callJSON({
                url: 'user/checkSMS.do',
                data: {
                    code: randCode,
                    mobileNo: mobileNo,
                    busiCode: 'BUSICODE_REGISTER_SMS'
                },
                dataType: 'text',
                indicator: true,
                type: 'POST',
                timeout: 100000,
                success: function (result) {
                    if (result == '0') {
                        register01Ctrl.doRegister();
                    } else if (result == '1') {
                    	register01Ctrl.reCreateImageCode();
                        xxdApp.alert('验证码错误，请重新确认', '提示');
                    }
                }
            });
        },
        doRegister: function () {

            var mobileNo = register01Ctrl.clearSpace($('#register01 #phoneNo').val());
            var password = $.trim($('#register01 #userPassword').val());
            var smsCode = $.trim($('#register01 #phoneVerifyCode').val());
            //设置表单token
            var formToken = appFunc.setToken({name: "register01", id: mobileNo});
            if (formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入", "抱歉");
                return;
            }
            req.callJSON({
                type: 'POST',
                url: 'user/regV3.do',
                data: {
                    phone: mobileNo,
                    password: $.md5($.md5(password)),
                    referer: '',
                    smsCode: smsCode,
                    regsource: 7, //用户来源1：网站注册,2：手机客户端注册,3：合作商户注册,4：合作商户导入,5：微信注册,6：移动网页注册,7:webapp(html5)'
                    "tokenName": formToken.data.tokenName,
                    "token": formToken.data.token
                },
                dataType: 'json',
                timeout: 120000,
                success: function (result) {
                    if (result.resultCode != 0) {
                    	// try {XXD_TRACK._trackEvent({category:"webapp_game_reg",action:"reg_success_webapp",label:"注册失败",value:register01Ctrl.clearSpace($('#register01 #phoneNo').val()),custval:"0"});}catch(e){}
                    	register01Ctrl.reCreateImageCode();
                        xxdApp.alert(result.msg, '抱歉');
                        return;
                    }
                    if (result.regResultCode == 0 && result.mobileResultCode == 0) {
                    	register01Ctrl.reCreateImageCode();
                    	$$("#register01 #phoneNo").val("");
                    	$$("#register01 #imgCode").val("");
                    	$$("#register01 #phoneVerifyCode").val("");
                    	$$("#register01 #userPassword").val("");
                        register01Ctrl.addGameChances();
                        register01Ctrl.regSuccess(result);
                    } else {
                    	// try {XXD_TRACK._trackEvent({category:"webapp_game_reg",action:"reg_success_webapp",label:"注册失败",value:register01Ctrl.clearSpace($('#register01 #phoneNo').val()),custval:"0"});}catch(e){}
                    	register01Ctrl.reCreateImageCode();
                        var msg = "注册失败";
                        if (result.regResultCode != 0) {
                            msg = result.regDesc;
                        } else if (result.mobileResultCode != 0) {
                            msg = result.mobileDesc;
                        }
                        if (msg == null || msg == "" || msg == undefined) {
                            msg = "注册失败";
                        }
                        xxdApp.alert(msg, '抱歉');
                    }
                }
            });
        },
        addGameChances :function(){
            req.callJSON({
                type: 'POST',
                url:'gameBWNH/addGameChances.do',
                data:{
                    num:3
                },
                timeout: 5000,
                success: function (result) {
                    console.log(result)
                }
            });
        },
        formatPhoneNo: function (e) {
            var keyCode = e.keyCode;
            if (keyCode == 37 || keyCode == 39) {
                return false;
            }
            var val = $$("#register01 #phoneNo").val();
            if (e.keyCode != 8) {
                var len = val.length;
                if (len == 3 || len == 9) {
                    val += '  ';
                }
            }
            $$("#register01 #phoneNo").val(val);
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
                dataType: 'json',
                timeout: 30000,
                indicator: true,
                success: function (data) {
                    if (data == 0) {
                        exist = false;
                    } else if (data == 1) {
                        exist = true;
                    }
                }
            });
            return exist;
        },
        clearIntervalArray: function () {
            $.each(intervalArray, function (index, value) {
                clearInterval(value);
            });
            intervalArray.length = 0;
        },
        clearSpace: function (value) {
            return value.replace(/\s+/g, '');
        },
        goLogin: function () {
        	GS.loadPage("activityRegAndLogin/login01.html?score=" + score);
        },
        regSuccess: function (result) {
        	try {
        		//XXD_TRACK._trackEvent({category:"webapp_game_reg",action:"reg_success_webapp",label:"注册成功",value:result.userid,custval:"1"});
        		//GA部署
                gaClickEvent_UserId({property1:"注册",property2:"注册成功",property3:"手机注册"});	
        	}catch(e){}
        	
            
            xxdApp.alert('注册成功', '恭喜', function () {
                GS.loadPage('gameExchange/exchangeResult.html?score=' + score);
            });
        },
        regActivity: function () {
            var value = appFunc.getCache({key: 'REPACKET_ONOFF'});
            if (value == "Y") {
                $$("#register01 #submitRegister").html("注册再送108元红包");
            }
        }
    };
    return register01Ctrl;
});
