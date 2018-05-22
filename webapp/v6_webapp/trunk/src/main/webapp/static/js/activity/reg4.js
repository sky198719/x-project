//获取地址栏url参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return  decodeURI(r[2]);
    return null;
}
function ajax(options) {
    var isasync = 'async' in options ? options.async : true;
    var method = 'type' in options ? options.type : 'GET';
    var reqData = 'data' in options ? options.data : {};
    var reqDataType = 'dataType' in options ? options.dataType : 'none';
    var timeoutNum = 'timeout' in options ? options.timeout : 5000;
    try {
        $.ajax({
            type: method,
            url: options.url,
            data: reqData,
            async: isasync,
            dataType: reqDataType,
            timeout: timeoutNum,
            success: function (resp) {
                options.success(resp);
            },
            error: function (xhr, errorType, error) {
                if ('error' in options && $.isFunction(options.error)) {
                    options.error(xhr, errorType, error);
                } else {
                    $.dialog({
                         content: '请求出错[' + xhr.status + ']，请重新尝试或检查您的网络是否正常',
                         title: "抱歉",
                         time: 3000
                     });
                }
            }
        });
    } catch (e) {
        console.log(e);
    }
}
var countdownSecond = 60;
var pCode = "";
var intervalArray = [];
var eyeFlag = 0;//关闭
$(document).ready(function () {
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        lazyLoading: true,
        autoplay: 5000
    });

    pCode = GetQueryString("pCode");
    pCode = pCode == undefined ? "" : pCode;

    clearIntervalArray()
    eventBind();
});
function refVerifyCode(){
    $("#randImage").attr("src", "../../../verify/code.do?" + Math.random());
}
function eventBind() {
    $("#phoneNo").bind('keyup', function (e) {
        var keyCode = e.keyCode;
        if (keyCode == 37 || keyCode == 39) {
            return false;
        }
        var val = $("#phoneNo").val();
        if (e.keyCode != 8) {
            var len = val.length;
            if (len == 3 || len == 9) {
                val += '  ';
            }
        }
        $("#phoneNo").val(val);
    });

    $("#phoneNo").bind('focus', function () {
        var inputId = $(this).attr("id");
        $("#" + inputId + "ErrorMsg").css("display", "none");
        $("#phoneVerifyCodeErrorMsg").css("display", "none");
    });

    $("#phoneNo").bind('blur', function () {
        checkPhoneNo();
    });

    $("#imgCode").bind('focus', function () {
        var inputId = $(this).attr("id");
        $("#" + inputId + "ErrorMsg").css("display", "none");
        $("#phoneVerifyCodeErrorMsg").css("display", "none");
    });

    $("#imgCode").bind('blur', function () {
        checkImgCode()
    });

    $(".reCreateImageCode").click(function () {
        refVerifyCode();
    });

    $("#sendSMSCode").click(function () {
        var phoneNo = clearSpace($('#phoneNo').val());
        if (!checkPhoneNo()) {
            showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '请先输入正确的手机号码'});
            return false;
        }
        var imgCode = $.trim($("#imgCode").val());
        if (!checkImgCode()) {
            showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '请先输入正确的图片验证码'});
            return false;
        }

        ajax({
            url: '../../../user/sendSMS.do',
            data: {
                phone: phoneNo,
                type: 1,
                imgCode: imgCode
            },
            dataType: 'json',
            timeout: 30000,
            success: function (result) {
                var tempTime = countdownSecond;
                if (result.resultCode == '0') {
                    clearIntervalArray();
                    $.dialog({
                        content: '验证码发送成功，请注意查收',
                        title: "提示",
                        time: 2000
                    });
                    refVerifyCode();
                    $('#voiceCode').hide();
                    $('#sendSMSCode').hide();
                    $('#phoneVerifyCodeCountdown').show(tempTime + '秒后重新发送');
                    intervalArray.push(setInterval(function () {
                        tempTime--;
                        if (tempTime < 0) {
                            $('#phoneVerifyCodeCountdown').hide();
                            $('#sendSMSCode').show();
                            $('#voiceCode').show();
                            // 清除定时器
                            clearIntervalArray();
                        } else {
                            $('#phoneVerifyCodeCountdown').html(tempTime + '秒后重新发送');
                        }
                    }, 1000));
                } else {
                    $.dialog({
                        content: result.msg,
                        title: "抱歉",
                        time: 2000
                    });
                    if(result.resultCode == -250) {
                        refVerifyCode();
                    }
                    $('#phoneVerifyCodeCountdown').hide();
                    $('#sendSMSCode').show();
                    $('#voiceCode').show();
                }
            }
        });
    });


    $("#sendVoiceCode").click(function () {
        var phoneNo = clearSpace($('#phoneNo').val());
        if (!checkPhoneNo(phoneNo)) {
            showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '请先输入正确的手机号码'});
            return false;
        }
        var imgCode = $.trim($("#imgCode").val());
        if (!checkImgCode(imgCode)) {
            showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '请先输入正确的图片验证码'});
            return false;
        }

        ajax({
            url: '../../../user/sendVoiceSMSLogout.do',
            data: {
                mobileNo: phoneNo,
                busiCode: 'BUSICODE_REGISTER_SMS',
                imgCode: imgCode
            },
            dataType: 'json',
            timeout: 30000,
            success: function (result) {
                var tempTime = countdownSecond;
                if (result.resultCode == 0) {
                    clearIntervalArray();
                    $.dialog({
                        content: '请接听来自125909888353的电话，为您自动播报语音验证码',
                        title: "提示",
                        time: 2000
                    });
                    refVerifyCode();
                    $('#voiceCode').hide();
                    $('#sendSMSCode').hide();
                    $('#phoneVerifyCodeCountdown').show().html(tempTime + '秒后重新发送');
                    intervalArray.push(setInterval(function () {
                        tempTime--;
                        if (tempTime < 0) {
                            $('#phoneVerifyCodeCountdown').hide();
                            $('#voiceCode').show();
                            $('#sendSMSCode').show();
                            // 清除定时器
                            clearIntervalArray();
                        } else {
                            $('#phoneVerifyCodeCountdown').html(tempTime + '秒后重新发送');
                        }
                    }, 1000));
                } else {
                    $.dialog({
                        content: result.msg,
                        title: "抱歉",
                        time: 2000
                    });
                }
            }
        });
    });

    $("#login-close").click(function(){
        $(".login").hide();
    });

    $(".login-button").click(function () {
            var username = $.trim($("#login-username").val());
            if (username == '') {
                $.dialog({
                    content: '请输入用户名/注册手机/邮箱地址',
                    title: "提示",
                    time: 2000
                });
                $("#login-username").focus();
                return;
            }

            var pwd = $.trim($("#login-pwd").val());
            if (pwd == '') {
                $.dialog({
                   content: '请输入密码',
                   title: "提示",
                   time: 2000
               });
                $("#login-pwd").focus();
                return;
            }

            ajax({
                url: '../../../user/loginActive.do',
                data: {
                    username: username,
                    password: $.md5($.md5(pwd))
                },
                dataType: 'json',
                success: function (result) {
                    $('#login-pwd').val('');
                    if (result.resultCode == 0) {
                       // $(".login").hide();
                        window.location.href = '../../../';
                    } else if (result.resultCode == -1) {
                        $.dialog({
                           content: '用户名称不存在，请重新输入',
                           title: "提示",
                           time: 2000
                       });
                        $("#login-username").focus();
                    } else if (result.resultCode == -2) {
                        $.dialog({
                           content: '密码错误，请重新输入',
                           title: "提示",
                           time: 2000
                       });
                        $("#login-pwd").focus();
                    } else if (result.resultCode == -3 || result.resultCode == -4) {
                        $.dialog({
                           content: result.msg,
                           title: "提示",
                           time: 2000
                       });
                    } else if (result.resultCode == -9) {
                        $.dialog({
                           content: '登录失败，请重新尝试或者联系客服',
                           title: "提示",
                           time: 2000
                       });
                    } else {
                        $.dialog({
                           content: result.msg,
                           title: "提示",
                           time: 2000
                       });
                    }
                }
            });
        });

    $("#phoneVerifyCode").bind('focus', function () {
        var inputId = $(this).attr("id");
        $("#" + inputId + "ErrorMsg").css("display", "none");
        $("#phoneVerifyCodeErrorMsg").css("display", "none");
    })

    $("#phoneVerifyCode").bind('blur',function(){
        var smsCode = $.trim($("#phoneVerifyCode").val());
       try {
           //XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "out_sms_verifycode", label: "短信验证码", value: smsCode, custval: "" });
       } catch (e) {
       }
    });

    $("#userPassword").bind('focus',function(){
        var inputId = $(this).attr("id");
        $("#" + inputId + "ErrorMsg").css("display", "none");
        $("#phoneVerifyCodeErrorMsg").css("display", "none");
    });

    $("#userPassword").bind('blur',function(){
        var userPassword = $.trim($("#userPassword").val());
        try {
            //XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "out_login_pwd", label: "登录密码", value: "", custval: "" });
        } catch (e) {
        }
    });

    $("#loadRegisterAgreement").click(function(){
        $("#xxdRegisterAgreement").show();
    });

    $("#xxdRegisterAgreementBut").click(function(){
        $("#xxdRegisterAgreement").hide();
    });

    $("#switchPasswordEye").click(function(){
        if (eyeFlag == 0) {
            //set the password visible
            $('#switchPasswordEye').removeClass("icon-closeEye").addClass("icon-openEye");
            $('#userPassword').attr('type', 'text');
            eyeFlag = 1;
        } else {
            //set the password invisible
            $('#switchPasswordEye').removeClass("icon-openEye").addClass("icon-closeEye");
            $('#userPassword').attr('type', 'password');
            eyeFlag = 0;
        }
    });

    $("#outWebsiteRegisterLogin").click(function(){
        $(".login").show();
    });

    $("#submitRegister").click(function(){
        submitRegisterHide();

        try {
            //XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "out_reg_button", label: "注册按钮", value: "", custval: "" });
        } catch (e) {
        }

        var mobileNo = clearSpace($('#phoneNo').val());
        if (!checkPhoneNo()) {
            submitRegisterShow();
            return false;
        }
        hideInputErrorMsg({inputId: 'phoneNo'});

        var imgCode = $.trim($("#imgCode").val());
        if (!checkImgCode(imgCode)) {
            submitRegisterShow();
            return false;
        }
        hideInputErrorMsg({inputId: 'imgCode'});

        var randCode = $.trim($('#phoneVerifyCode').val());
        if (randCode == '') {
            submitRegisterShow();
            return showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '请输入短信验证码'});
        }
        hideInputErrorMsg({inputId: 'phoneVerifyCode'});

        var password = $.trim($('#userPassword').val());
        if (password == '') {
            submitRegisterShow();
            return showInputErrorMsg({inputId: 'userPassword', msg: '请输入密码'});
        }
        if (validatePassword(password) != 'true') {
            submitRegisterShow();
            return showInputErrorMsg({inputId: 'userPassword', msg: '有效密码为6-16位数字字母组合'});
        }
        hideInputErrorMsg({inputId: 'userPassword'});

        ajax({
           url: '../../../user/checkSMS.do',
            data: {
                code: randCode,
                mobileNo: mobileNo,
                busiCode: 'BUSICODE_REGISTER_SMS'
            },
            dataType: 'text',
            type: 'POST',
            timeout: 10000,
            success: function (result) {
                if (result == '0') {
                    doRegister();
                } else if (result == '1') {
                    submitRegisterShow();
                    //xxdApp.alert('短信验证码错误', '抱歉');
                    showInputErrorMsg({inputId: 'phoneVerifyCode', msg: '验证码错误，请重新确认'});
                } else {
                    submitRegisterShow();
                    $.dialog({
                        content: '验证短信异常',
                        title: "抱歉",
                        time: 2000
                    });
                }
            }
        });
    });
}
function validatePassword(arg) {
    var patter = /^([a-zA-Z0-9])*$/;
    var patter1 = /^([a-zA-Z])*$/;
    var patter2 = /^([0-9])*$/;
    if (arg.length < 6)
        return '密码太短，不足6位';
    if (arg.length > 16)
        return '密码长度不得超过16位';
    if (!patter.test(arg) || (patter1.test(arg) || patter2.test(arg)))
        return '密码需由字母和数字组成';

    return 'true';
}
function doRegister() {
    var mobileNo = clearSpace($('#phoneNo').val());
    var password = $.trim($('#userPassword').val());
    var smsCode = $.trim($('#phoneVerifyCode').val());
    //设置表单token
    var formToken = setToken({name: "outWebsiteRegister", id: mobileNo});
    if (formToken.code != 0) {
        submitRegisterShow();
        $.dialog({
            content: '初始化表单失败，请返回重新进入',
            title: "抱歉",
            time: 2000
        });
        return;
    }
    ajax({
        type: 'POST',
        url: '../../../user/regV3.do',
        data: {
            phone: mobileNo,
            password: $.md5($.md5(password)),
            referer: '',
            smsCode: smsCode,
            regsource: 7, //用户来源1：网站注册,2：手机客户端注册,3：合作商户注册,4：合作商户导入,5：微信注册,6：移动网页注册,7:webapp(html5)'
            pCode: pCode,
            "tokenName": formToken.data.tokenName,
            "token": formToken.data.token
        },
        dataType: 'json',
        timeout: 120000,
        success: function (result) {

            if (result.resultCode != 0) {
                $.dialog({
                     content: result.msg,
                     title: "抱歉",
                     time: 2000
                 });
                submitRegisterShow();
                return;
            }
            if (result.regResultCode == 0 && result.mobileResultCode == 0) {
                if (pCode != null && pCode != "") {
                    if (result.partnerResultCode == 0) {
                        regSuccess(result);
                    } else {
                        submitRegisterShow();
                        $.dialog({
                             content: result.partnerDesc,
                             title: "抱歉",
                             time: 2000
                         });
                    }
                } else {
                    regSuccess(result);
                }
            } else {
                submitRegisterShow();
                var msg = "注册失败";
                if (result.regResultCode != 0) {
                    msg = result.regDesc;
                } else if (result.mobileResultCode != 0) {
                    msg = result.mobileDesc;
                }
                if (msg == null || msg == "" || msg == undefined) {
                    msg = "注册失败";
                }
                $.dialog({
                     content:msg,
                     title: "抱歉",
                     time: 2000
                 });
            }
        }
    });
}
function setToken(param) {
    var result = {};
    ajax({
        url: "../../../setToken.do",
        data: {
            name: param.name,
            id: param.id
        },
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.resultCode == 0) {
                result.code = 0;
                result.data = data;
            } else {
                result.code = -1;
            }
        }
    });
    return result;
}
function regSuccess(){
    $.dialog({
            content : '注册成功',
            title : 'ok',
            ok : function() {
                submitRegisterShow();
                 //注册成功
                try {
                    //XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "reg_success_webapp", label: "注册成功", value: result.userid, custval: "1" });
                  //GA部署
                    gaClickEvent_UserId({property1:"注册",property2:"注册成功",property3:"手机注册"});
                } catch (e) {
                }

                window.location.href="../../../";
                return false;
            },
            lock : true
        });

}
function submitRegisterHide(){
    $("#submitRegisterLoad").show();
    $("#submitRegister").hide();
}
function submitRegisterShow(){
    $("#submitRegisterLoad").hide();
    $("#submitRegister").show();
}
function clearIntervalArray() {
    $.each(intervalArray, function (index, value) {
        clearInterval(value);
    });
    intervalArray.length = 0;
}
function checkImgCode() {
    var imgCode = $.trim($("#imgCode").val());

    try {
        //XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "out_verifycode", label: "图片验证码", value: imgCode, custval: "" });
    } catch (e) {
    }

    if (imgCode == undefined || '' == imgCode) {
        return showInputErrorMsg({inputId: 'imgCode', msg: '请输入图片验证码'});
    }
    /*if (!checkVerifyCode(imgCode)) {
        return showInputErrorMsg({inputId: 'imgCode', msg: '图片验证码错误，请重新确认'});
    }*/
    hideInputErrorMsg({inputId: 'imgCode'});

    return true;
}
function checkPhoneNo() {
    var phoneNo = clearSpace($('#phoneNo').val());
    try {
        //XXD_TRACK._trackEvent({category: "webapp_out_reg", action: "out_phone", label: "手机号", value: phoneNo, custval: "" });
    } catch (e) {
    }
    if (phoneNo == '') {
        return showInputErrorMsg({inputId: 'phoneNo', msg: '请输入手机号'});
    }
    var vaRe = validateMobile(phoneNo);
    if (vaRe != "true") {
        return showInputErrorMsg({inputId: 'phoneNo', msg: '手机号错误，请重新确认'});
    }

    if (checkMobileExist(phoneNo)) {
        return showInputErrorMsg({inputId: 'phoneNo', msg: '您输入的手机号已被注册'});
    }
    hideInputErrorMsg({inputId: 'phoneNo'});
    return true;
}
function checkVerifyCode(imgCode) {
    var bool = false;
    ajax({
        url: '../../../randCode/checkVerifyCode.do',
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
}
//检查手机号是否已被使用
function checkMobileExist(mobileNo) {
    var exist = false;
    ajax({
        url: '../../../user/checkMobile.do',
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
}
function clearSpace(value) {
    return value.replace(/\s+/g, '');
}
function showInputErrorMsg(options) {
    $("#" + options.inputId + "ErrorMsg").html(options.msg).css("display", "block");
    return false;
}
function hideInputErrorMsg(options) {
    $("#" + options.inputId + "ErrorMsg").css("display", "none");
    return false;
}
function validateMobile(arg) {
    var patter = /^0?(13|15|14|17|18)[0-9]{9}$/;
    if (patter.test(arg)) {
        return 'true';
    } else {
        return '请输入正确的手机号码';
    }
}

