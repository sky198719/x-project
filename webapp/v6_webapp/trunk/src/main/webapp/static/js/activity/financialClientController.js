define(function () {
    var intervalArray = [];
    var countdownSecond = 60;
    var pCode = '';
    var job = 'default';
    var financialCtrl = {
        init: function (event) {
            var page = appFunc.getEventDetailPageQuery(event);

            if (page.pcode != undefined) {
                pCode = page.pcode;
            }

            var binding = [
                {
                    element: '#financialClient .reCreateImageCode',
                    event: 'click',
                    handler: financialCtrl.reCreateImageCode
                },
                {
                    element: '#financialClient #sendSMSCode',
                    event: 'click',
                    handler: financialCtrl.sendSMSCode
                },
                {
                    element: '#financialClient #submitRegister',
                    event: 'click',
                    handler: financialCtrl.submitRegister
                }
            ];
            appFunc.bindEvents(binding);
        },
        checkUserName: function (username) {
            var res = false;
            req.callPost({
                url: 'user/checkUserName.do',
                data: {
                    userName: username
                },
                async: false,
                dataType: 'text',
                success: function (result) {
                    res = result == '1' ? true : false;
                }
            });
            return res;
        },
        submitRegister: function () {
            var isSub = $$("#financialClient #submitRegister").data("isSub");
            if(isSub == 'true') {
                return false;
            }

            $$("#financialClient #submitRegister").css("background-color","#C5CFDA");
            $$("#financialClient #submitRegister").css("border","1px solid #C5CFDA");
            $$("#financialClient #submitRegister").data("isSub","true");
            $$("#financialClient #submitRegister").html("正在提交...");

            var userName = $.trim($('#financialClient #userName').val());
            if (userName == '') {
                return financialCtrl.showInputErrorMsg({inputId: 'userName', msg: '请输入真实姓名'});
            }

            userName = "xxd" + userName;
            if (!appFunc.validateName(userName) == 'true') {
                return financialCtrl.showInputErrorMsg({inputId: 'userName', msg: '请输入正确的真实姓名'});
            }

            var phoneNo = $.trim($('#financialClient #phoneNo').val());
            if (!financialCtrl.checkUserName(userName)) {
                userName += phoneNo;
                if(appFunc.len(userName) > 16) {
                    userName = userName.substring(0,15);
                }
            }
            financialCtrl.hideInputErrorMsg({inputId: 'userName'});


            if (phoneNo == '') {
                return financialCtrl.showInputErrorMsg({inputId: 'phoneNo', msg: '请输入手机号码'});
            }

            var vaRe = appFunc.validateMobile(phoneNo);
            if (vaRe != "true") {
                return financialCtrl.showInputErrorMsg({inputId: 'phoneNo', msg: '手机号错误，请重新确认'});
            }
            if (financialCtrl.checkMobileExist(phoneNo)) {
                return financialCtrl.showInputErrorMsg({inputId: 'phoneNo', msg: '您输入的手机号码已经提交过'});
            }
            financialCtrl.hideInputErrorMsg({inputId: 'phoneNo'});

            var imgCode = $.trim($("#financialClient #imgCode").val());
            if (imgCode == undefined || '' == imgCode) {
                return financialCtrl.showInputErrorMsg({inputId: 'imgCode', msg: '请输入图片验证码'});
            }
            financialCtrl.hideInputErrorMsg({inputId: 'imgCode'});

            var phoneVerifyCode = $.trim($("#financialClient #phoneVerifyCode").val());
            if (phoneVerifyCode == '') {
                return financialCtrl.showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '请输入短信验证码'});
            }
            financialCtrl.hideInputErrorMsg({inputId: 'phoneVerifyCode'});

            req.callJSON({
                url: 'user/checkSMS.do',
                data: {
                    code: phoneVerifyCode,
                    mobileNo: phoneNo,
                    busiCode: 'BUSICODE_REGISTER_SMS'
                },
                dataType: 'text',
                indicator: true,
                type: 'POST',
                timeout: 10000,
                success: function (result) {
                    if (result == '0') {
                        financialCtrl.doRegister(userName);
                    } else if (result == '1') {
                        financialCtrl.showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '验证码错误，请重新确认'});
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
        doRegister: function (userName) {

            var phoneNo = $.trim($('#financialClient #phoneNo').val());
            var phoneVerifyCode = $.trim($("#financialClient #phoneVerifyCode").val());
            var Num = "";
            for (var i = 0; i < 6; i++) {
                Num += Math.floor(Math.random() * 10);
            }
            //设置表单token
            var formToken = appFunc.setToken({name: "financialClient", id: phoneNo});
            if (formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入", "抱歉");
                return;
            }
            req.callJSON({
                type: 'POST',
                url: 'user/regV3.do',
                data: {
                    userName: userName,
                    phone: phoneNo,
                    password: $.md5($.md5(Num)),
                    referer: '',
                    smsCode: phoneVerifyCode,
                    regsource: 7, //用户来源1：网站注册,2：手机客户端注册,3：合作商户注册,4：合作商户导入,5：微信注册,6：移动网页注册,7:webapp(html5)'
                    job: job,
                    pCode: pCode,
                    "tokenName": formToken.data.tokenName,
                    "token": formToken.data.token
                },
                dataType: 'json',
                timeout: 120000,
                indicator: true,
                success: function (result) {
                    $$("#financialClient #submitRegister").css("background-color","#3f9bff");
                    $$("#financialClient #submitRegister").css("border","1px solid #3f9bff");
                    $$("#financialClient #submitRegister").data("isSub","false");
                    $$("#financialClient #submitRegister").html("提交");
                    if (result.resultCode != 0) {
                        xxdApp.alert("提交失败", '抱歉');
                        return;
                    }
                    if (result.regResultCode == 0 && result.mobileResultCode == 0) {
                        if (pCode != null && pCode != "") {
                            if (result.partnerResultCode == 0) {
                                xxdApp.alert("感谢您的参与", "提交成功");
                                financialCtrl.clearInput();
                            } else {
                                xxdApp.alert("提交失败", '抱歉');
                            }
                        } else {
                            xxdApp.alert("感谢您的参与", "提交成功");
                            financialCtrl.clearInput();
                        }
                    } else {
                        xxdApp.alert("提交失败", '抱歉');
                    }
                }
            });
        },
        reCreateImageCode: function () {
            $$("#financialClient #randImage").attr("src", "randCode/createVerifyCode.do?" + Math.random());
        },
        showInputErrorMsg: function (options) {
            $$("#financialClient #" + options.inputId + "ErrorMsg").html(options.msg).show();
            $$("#financialClient #submitRegister").css("background-color","#3f9bff");
            $$("#financialClient #submitRegister").css("border","1px solid #3f9bff");
            $$("#financialClient #submitRegister").data("isSub","false");
            $$("#financialClient #submitRegister").html("提交");
            return false;
        },
        hideInputErrorMsg: function (options) {
            $$("#financialClient #" + options.inputId + "ErrorMsg").hide();
        },
        sendSMSCode: function () {
            var phoneNo = $.trim($('#financialClient #phoneNo').val());
            if (phoneNo == '') {
                return financialCtrl.showInputErrorMsg({inputId: 'phoneNo', msg: '请输入手机号码'});
            }

            var vaRe = appFunc.validateMobile(phoneNo);
            if (vaRe != "true") {
                return financialCtrl.showInputErrorMsg({inputId: 'phoneNo', msg: '手机号错误，请重新确认'});
            }
            if (financialCtrl.checkMobileExist(phoneNo)) {
                return financialCtrl.showInputErrorMsg({inputId: 'phoneNo', msg: '您输入的手机号码已经提交过'});
            }
            financialCtrl.hideInputErrorMsg({inputId: 'phoneNo'});

            var imgCode = $.trim($("#financialClient #imgCode").val());
            if (imgCode == undefined || '' == imgCode) {
                return financialCtrl.showInputErrorMsg({inputId: 'imgCode', msg: '请输入图片验证码'});
            }
            financialCtrl.hideInputErrorMsg({inputId: 'imgCode'});

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
                        financialCtrl.clearIntervalArray();
                        xxdApp.alert('验证码发送成功，请注意查收', '提示');
                        $$('#financialClient #sendSMSCode').hide();
                        $$('#financialClient #phoneVerifyCodeCountdown').show(tempTime + '秒后重新发送');
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#financialClient #phoneVerifyCodeCountdown').hide();
                                $$('#financialClient #sendSMSCode').show();
                                // 清除定时器
                                financialCtrl.clearIntervalArray();
                            } else {
                                $$('#financialClient #phoneVerifyCodeCountdown').html(tempTime + '秒后重新发送');
                            }
                        }, 1000));
                    } else {
                        xxdApp.alert(result.msg, '抱歉');
                        $$('#financialClient #phoneVerifyCodeCountdown').hide();
                        $$('#financialClient #sendSMSCode').show();
                    }
                }
            });
        },
        clearIntervalArray: function () {
            $.each(intervalArray, function (index, value) {
                clearInterval(value);
            });
            intervalArray.length = 0;
        },
        clearInput: function () {
            $$("#financialClient #userName").val("");
            $$("#financialClient #phoneNo").val("");
            $$("#financialClient #imgCode").val("");
            $$("#financialClient #phoneVerifyCode").val("");

            financialCtrl.reCreateImageCode();
            $$('#financialClient #phoneVerifyCodeCountdown').hide();
            $$('#financialClient #sendSMSCode').show();
        }
    };
    return financialCtrl;
});