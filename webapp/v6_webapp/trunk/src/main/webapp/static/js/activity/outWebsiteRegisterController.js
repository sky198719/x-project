/**
 * 站外推广注册
 */
define([ 'zeptoMd5', 'js/activity/outWebsiteRegisterView'], function (zeptoMd5, outWebsiteRegisterView) {
    var intervalArray = [];
    var eyeFlag = 0;//关闭
    var countdownSecond = 60;
    var job = 'default';
    var pCode = "";
    var outWebsiteRegisterCtrl = {
        init: function (event) {
            pCode = appFunc.getEventDetailPageQuery(event).pCode;
            pCode = pCode == undefined ? "" : pCode;
            outWebsiteRegisterCtrl.clearIntervalArray();
            outWebsiteRegisterCtrl.loadData();
            outWebsiteRegisterCtrl.regActivity();

            $$("title").html("注册_");
        },
        loadData: function () {
            outWebsiteRegisterCtrl.outWebsiteRegisterBanner();
            outWebsiteRegisterCtrl.bindEventAll();
        },

        regActivity: function () {
            var value = appFunc.getCache({key: 'REPACKET_ONOFF'});
            if (value == "Y") {
                $$("#submitRegister").html("注册领108元");
            }
        },

        bindEventAll: function () {
            var bindings = [
                {
                    element: '#outWebsiteRegister #phoneNo',
                    event: 'keyup',
                    handler: outWebsiteRegisterCtrl.formatMobileNo
                },
                {
                    element: '#outWebsiteRegister #phoneNo',
                    event: 'focus',
                    handler: outWebsiteRegisterCtrl.clearErrorMsg
                },
                {
                    element: '#outWebsiteRegister #phoneNo',
                    event: 'blur',
                    handler: outWebsiteRegisterCtrl.checkPhoneNo
                },
                {
                    element: '#outWebsiteRegister #imgCode',
                    event: 'focus',
                    handler: outWebsiteRegisterCtrl.clearErrorMsg
                },
                {
                    element: '#outWebsiteRegister #imgCode',
                    event: 'blur',
                    handler: outWebsiteRegisterCtrl.checkImgCode
                },
                {
                    element: '#outWebsiteRegister .reCreateImageCode',
                    event: 'click',
                    handler: outWebsiteRegisterCtrl.reCreateImageCode
                },
                {
                    element: '#outWebsiteRegister #sendSMSCode',
                    event: 'click',
                    handler: outWebsiteRegisterCtrl.sendSMSCode
                },
                {
                    element: '#outWebsiteRegister #sendVoiceCode',
                    event: 'click',
                    handler: outWebsiteRegisterCtrl.sendVoiceCode
                },
                {
                    element: '#outWebsiteRegister #phoneVerifyCode',
                    event: 'focus',
                    handler: outWebsiteRegisterCtrl.clearErrorMsg
                },
                {
                    element: '#outWebsiteRegister #phoneVerifyCode',
                    event: 'blur',
                    handler: outWebsiteRegisterCtrl.checkSmsCode
                },
                {
                    element: '#outWebsiteRegister #userPassword',
                    event: 'focus',
                    handler: outWebsiteRegisterCtrl.clearErrorMsg
                },
                {
                    element: '#outWebsiteRegister #userPassword',
                    event: 'blur',
                    handler: outWebsiteRegisterCtrl.checkUserPassword
                },
                {
                    element: '#outWebsiteRegister #loadRegisterAgreement',
                    event: 'click',
                    handler: outWebsiteRegisterCtrl.loadRegisterAgreement
                },
                {
                    element: '#outWebsiteRegister #switchPasswordEye',
                    event: 'click',
                    handler: outWebsiteRegisterCtrl.switchPasswordEye
                },
                {
                    element: 'a[name="outWebsiteRegisterLogin"]',
                    event: 'click',
                    handler: outWebsiteRegisterCtrl.goLogin
                },
                {
                    element: '#outWebsiteRegister #submitRegister',
                    event: 'click',
                    handler: outWebsiteRegisterCtrl.submitRegister
                }
            ];
            outWebsiteRegisterView.render({
                bindings: bindings
            });
        },

        outWebsiteRegisterBanner: function () {
//            req.callPost({
//                url: 'indexppt.do',
//                data: {
//                    bannerType: 6
//                },
//                dataType: 'json',
//                indicator: true,
//                success: function (result) {
//                    if (result != null) {
//                        outWebsiteRegisterView.getBanner(result);
//                    }
//                }
//            });
            var result = {};
            result.imageUrl = GC.getImgPath();
            var pptToplist = [

                /*{
                    disOrder: 2,
                    keepword1: '#ef1842',
                    pptUrl: 'activity/outWebsiteRegister/banner/02_ef1842.gif',
                    url: '#'
                },
                {
                    disOrder: 3,
                    keepword1: '#018ece',
                    pptUrl: 'activity/outWebsiteRegister/banner/03_018ece.gif',
                    url: '#'
                },
                {
                    disOrder: 4,
                    keepword1: '#ffd511',
                    pptUrl: 'activity/outWebsiteRegister/banner/04_ffd511.gif',
                    url: '#'
                } 
                {
                    disOrder: 2,
                    keepword1: '#191e56',
                    pptUrl: 'activity/outWebsiteRegister/banner/05_191e56.gif',
                    url: '#'
                },
                {
                    disOrder: 3,
                    keepword1: '#7cb204',
                    pptUrl: 'activity/outWebsiteRegister/banner/03_7cb204.jpg',
                    url: '#'
                }*/
                /*{
                      disOrder: 2,
                      keepword1: '#3db8fe',
                      pptUrl: 'activity/outWebsiteRegister/banner/1-3db8fe.jpg',
                      url: '#'
                  },
                {
                    disOrder: 3,
                    keepword1: '#06142b',
                    pptUrl: 'activity/outWebsiteRegister/banner/02_06142b.png',
                    url: '#'
                } */
            ];

            var value = appFunc.getCache({key: 'REPACKET_ONOFF'});
            if (value == "Y") {
                pptToplist.splice(0,0,
                        {
                            disOrder: 1,
                            keepword1: '#fddd94',
                            pptUrl: 'activity/outWebsiteRegister/banner/01_fddd94.gif',
                            url: '#'
                        }
                );
            }

            result.pptToplist = pptToplist;
            outWebsiteRegisterView.getBanner(result);
        },
        checkPhoneNo: function () {
            var phoneNo = outWebsiteRegisterCtrl.clearSpace($('#outWebsiteRegister #phoneNo').val());
            try {
                //XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "out_phone", label: "手机号", value: phoneNo, custval: "" });
            } catch (e) {
            }
            if (phoneNo == '') {
                return outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'phoneNo', msg: '请输入手机号'});
            }
            var vaRe = appFunc.validateMobile(phoneNo);
            if (vaRe != "true") {
                return outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'phoneNo', msg: '手机号错误，请重新确认'});
            }

            if (outWebsiteRegisterCtrl.checkMobileExist(phoneNo)) {
                return outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'phoneNo', msg: '您输入的手机号已被注册'});
            }
            return true;
        },
        checkImgCode: function () {
            var imgCode = $.trim($("#outWebsiteRegister #imgCode").val());

            try {
                //XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "out_verifycode", label: "图片验证码", value: imgCode, custval: "" });
            } catch (e) {
            }


            if (imgCode == undefined || '' == imgCode) {
                return outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'imgCode', msg: '请输入图片验证码'});
            }
           /* if (!outWebsiteRegisterCtrl.checkVerifyCode(imgCode)) {
                return outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'imgCode', msg: '图片验证码错误，请重新确认'});
            }*/
            return true;
        },
        checkSmsCode: function () {
            var smsCode = $.trim($("#outWebsiteRegister #phoneVerifyCode").val());
            try {
                //XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "out_sms_verifycode", label: "短信验证码", value: smsCode, custval: "" });
            } catch (e) {
            }

        },
        checkUserPassword: function () {
            var userPassword = $.trim($("#outWebsiteRegister #userPassword").val());
            try {
                //XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "out_login_pwd", label: "登录密码", value: "", custval: "" });
            } catch (e) {
            }

        },
        sendSMSCode: function () {
            var phoneNo = outWebsiteRegisterCtrl.clearSpace($('#outWebsiteRegister #phoneNo').val());
            if (!outWebsiteRegisterCtrl.checkPhoneNo()) {
                outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '请先输入正确的手机号码'});
                return false;
            }
            var imgCode = $.trim($("#outWebsiteRegister #imgCode").val());
            if (!outWebsiteRegisterCtrl.checkImgCode()) {
                outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '请先输入正确的图片验证码'});
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
                    var tempTime = countdownSecond;
                    if (result.resultCode == '0') {
                        outWebsiteRegisterCtrl.clearIntervalArray();
                        outWebsiteRegisterCtrl.reCreateImageCode();
                        xxdApp.alert('验证码发送成功，请注意查收', '提示');
                        $$('#outWebsiteRegister #voiceCode').hide();
                        $$('#outWebsiteRegister #sendSMSCode').hide();
                        $$('#outWebsiteRegister #phoneVerifyCodeCountdown').show(tempTime + '秒后重新发送');
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#outWebsiteRegister #phoneVerifyCodeCountdown').hide();
                                $$('#outWebsiteRegister #sendSMSCode').show();
                                $$('#outWebsiteRegister #voiceCode').show();
                                // 清除定时器
                                outWebsiteRegisterCtrl.clearIntervalArray();
                            } else {
                                $$('#outWebsiteRegister #phoneVerifyCodeCountdown').html(tempTime + '秒后重新发送');
                            }
                        }, 1000));
                    } else {
                        if(result.resultCode == -250) {
                            outWebsiteRegisterCtrl.reCreateImageCode();
                        }
                        xxdApp.alert(result.msg, '抱歉');
                        $$('#outWebsiteRegister #phoneVerifyCodeCountdown').hide();
                        $$('#outWebsiteRegister #sendSMSCode').show();
                        $$('#outWebsiteRegister #voiceCode').show();
                    }
                }
            });
        },
        //发送语音验证码
        sendVoiceCode: function () {
            var phoneNo = outWebsiteRegisterCtrl.clearSpace($('#outWebsiteRegister #phoneNo').val());
            if (!outWebsiteRegisterCtrl.checkPhoneNo(phoneNo)) {
                outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '请先输入正确的手机号码'});
                return false;
            }
            var imgCode = $.trim($("#outWebsiteRegister #imgCode").val());
            if (!outWebsiteRegisterCtrl.checkImgCode(imgCode)) {
                outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '请先输入正确的图片验证码'});
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
                    var tempTime = countdownSecond;
                    if (result.resultCode == 0) {
                        outWebsiteRegisterCtrl.clearIntervalArray();
                        xxdApp.alert('请接听来自125909888353的电话，为您自动播报语音验证码', '提示');
                        $$('#outWebsiteRegister #voiceCode').hide();
                        $$('#outWebsiteRegister #sendSMSCode').hide();
                        $$('#outWebsiteRegister #phoneVerifyCodeCountdown').show().html(tempTime + '秒后重新发送');
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#outWebsiteRegister #phoneVerifyCodeCountdown').hide();
                                $$('#outWebsiteRegister #voiceCode').show();
                                $$('#outWebsiteRegister #sendSMSCode').show();
                                // 清除定时器
                                outWebsiteRegisterCtrl.clearIntervalArray();
                            } else {
                                $$('#outWebsiteRegister #phoneVerifyCodeCountdown').html(tempTime + '秒后重新发送');
                            }
                        }, 1000));
                    } else {
                        xxdApp.alert(result.msg, '抱歉');
                    }
                }
            });
        },
        switchPasswordEye: function () {
            if (eyeFlag == 0) {
                //set the password visible
                $$('#outWebsiteRegister #switchPasswordEye').removeClass("icon-closeEye").addClass("icon-openEye");
                $$('#outWebsiteRegister #userPassword').attr('type', 'text');
                eyeFlag = 1;
            } else {
                //set the password invisible
                $$('#outWebsiteRegister #switchPasswordEye').removeClass("icon-openEye").addClass("icon-closeEye");
                $$('#outWebsiteRegister #userPassword').attr('type', 'password');
                eyeFlag = 0;
            }

        },
        //加载注册协议
        loadRegisterAgreement: function () {
            req.callGet({
                url: GC.getHtmlPath() + 'activity/xxdRegisterAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                indicator: true,
                success: function (result) {
//                    $$(".popup-bidhistory").html(result);
//		            xxdApp.popup('.popup-bidhistory');

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
            $$("#outWebsiteRegister #randImage").attr("src", "verify/code.do?" + Math.random());
        },
        checkVerifyCode: function (imgCode) {
            var bool = false;
            req.callJSON({
                url: 'randCode/checkVerifyCode.do',
                data: {
                    imgCode: imgCode,
                    type:1
                },
                dataType: 'json',
                timeout: 10000,
                async: false,
                success: function (result) {
                    if (result.resultCode == 0) {
                        bool = true;
                    }
                }
            });
            return bool;
        },

        subButtonSetFalse:function(){
            $$("#outWebsiteRegister #submitRegister").data.isSub = false;
        },
        submitRegister: function () {
            try {
                //XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "out_reg_button", label: "注册按钮", value: "", custval: "" });
            } catch (e) {
            }

            var isSub = $$("#outWebsiteRegister #submitRegister").data.isSub;
            if(isSub) {
                return;
            }

            $$("#outWebsiteRegister #submitRegister").data.isSub = true;

            var mobileNo = outWebsiteRegisterCtrl.clearSpace($('#outWebsiteRegister #phoneNo').val());
            if (!outWebsiteRegisterCtrl.checkPhoneNo()) {
                outWebsiteRegisterCtrl.subButtonSetFalse();
                return false;
            }
            var imgCode = $.trim($("#outWebsiteRegister #imgCode").val());
            if (!outWebsiteRegisterCtrl.checkImgCode(imgCode)) {
                outWebsiteRegisterCtrl.subButtonSetFalse();
                return false;
            }
            var randCode = $.trim($('#outWebsiteRegister #phoneVerifyCode').val());
            if (randCode == '') {
                outWebsiteRegisterCtrl.subButtonSetFalse();
                return outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '请输入短信验证码'});
            }
            var password = $.trim($('#outWebsiteRegister #userPassword').val());
            if (password == '') {
                outWebsiteRegisterCtrl.subButtonSetFalse();
                return outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'userPassword', msg: '请输入密码'});
            }
            if (appFunc.validatePassword(password) != 'true') {
                outWebsiteRegisterCtrl.subButtonSetFalse();
                return outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'userPassword', msg: '有效密码为6-16位数字字母组合'});
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
                timeout: 10000,
                success: function (result) {
                    if (result == '0') {
                        outWebsiteRegisterCtrl.doRegister();
                    } else if (result == '1') {
                        outWebsiteRegisterCtrl.subButtonSetFalse();
                        //xxdApp.alert('短信验证码错误', '抱歉');
                        outWebsiteRegisterCtrl.showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '验证码错误，请重新确认'});
                    }
                }
            });
        },
        doRegister: function () {

            var mobileNo = outWebsiteRegisterCtrl.clearSpace($('#outWebsiteRegister #phoneNo').val());
            var password = $.trim($('#outWebsiteRegister #userPassword').val());
            var smsCode = $.trim($('#outWebsiteRegister #phoneVerifyCode').val());
            //设置表单token
            var formToken = appFunc.setToken({name: "outWebsiteRegister", id: mobileNo});
            if (formToken.code != 0) {
                outWebsiteRegisterCtrl.subButtonSetFalse();
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
                    job: job,
                    pCode: pCode,
                    "tokenName": formToken.data.tokenName,
                    "token": formToken.data.token
                },
                dataType: 'json',
                timeout: 120000,
                success: function (result) {
                    outWebsiteRegisterCtrl.subButtonSetFalse();
                    if (result.resultCode != 0) {
                        xxdApp.alert(result.msg, '抱歉');
                        return;
                    }
                    if (result.regResultCode == 0 && result.mobileResultCode == 0) {
                        if (pCode != null && pCode != "") {
                            if (result.partnerResultCode == 0) {
                                outWebsiteRegisterCtrl.regSuccess(result);
                            } else {
                                xxdApp.alert(result.partnerDesc, '抱歉');
                            }
                        } else {
                            outWebsiteRegisterCtrl.regSuccess(result);
                        }
                    } else {
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
        formatMobileNo: function (e) {
            var keyCode = e.keyCode;
            if (keyCode == 37 || keyCode == 39) {
                return false;
            }
            var val = $$("#outWebsiteRegister #phoneNo").val();
            if (e.keyCode != 8) {
                var len = val.length;
                if (len == 3 || len == 9) {
                    val += '  ';
                }
            }
            $$("#outWebsiteRegister #phoneNo").val(val);
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
        showInputErrorMsg: function (options) {
            $$("#outWebsiteRegister #" + options.inputId + "ErrorMsg").html(options.msg).css("display", "block");
            return false;
        },
        clearErrorMsg: function () {
            var inputId = $$(this).attr("id");
            $$("#outWebsiteRegister #" + inputId + "ErrorMsg").css("display", "none");
            $$("#outWebsiteRegister #phoneVerifyCodeErrorMsg").css("display", "none");
        },
        goLogin: function () {
            $$(".login-screen").prop("isCloseLoginRegister", "true");
            //$$("#outWebsiteRegister #registerPath").val("outWebsite");
            $$(".login-screen").prop("redirectionUrl", "index/home.html");
            xxdApp.loginScreen();
        },
        regSuccess: function (result) {
            xxdApp.alert('注册成功', '恭喜', function () {
                //注册成功
                try {
                    // XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "reg_success_webapp", label: "注册成功", value: result.userid, custval: "1" });
                  //GA部署
                    gaClickEvent_UserId({property1:"注册",property2:"注册成功",property3:"手机注册"});
                } catch (e) {
                }

                GS.loadPage("index/home.html");
            });
        }
    };
    return outWebsiteRegisterCtrl;
});
