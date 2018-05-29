var job = 'default';
var intervalArray = [];
var fundActivityCode = "";
var time = 60;
var pCode = "";//商户id
var vipCode = "";//vip
var flag = 0;
var uuid = "";
//获取地址栏url参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return  decodeURI(r[2]);
    return null;
}

$(document).ready(function () {

    var vipCodeTmp1 = GetQueryString("vipCode");
    if (vipCodeTmp1 != undefined && vipCodeTmp1 != null && vipCodeTmp1 != '') {
        ajax({
            url: '../../../employee/getEmployee.do',
            data: {
                jobNum: vipCodeTmp1
            },
            dataType: 'json',
            timeout: 10000,
            success: function (result) {
                document.getElementById("licai").style.display = 'block';
                document.getElementById("licai").innerHTML = result.data.data.name + '作为我的财富顾问全程提供服务';
            }
        });
    }

    var pCodeTemp = GetQueryString("pCode");
    try {
        var url = window.location.href + "&utm_source=" + pCodeTemp + "&utm_campaign=商户推广";
        //XXD_TRACK._trackPageview({page_url: url});
    } catch (e) {
    }

    //获取设备高度（软键盘调出来时高度也会变小，所以在点击事件前获取）
    var deviceH = document.documentElement.clientHeight + "px";
    //表单获得焦点后动态改变body和背景图片的大小
    $('select,input').on("click", function () {
        $(".outer").attr("style", "background:url('../../../static/img/activity/partner/qr-bg.png') no-repeat;width:100%;height:" + deviceH + ";background-size: 100% " + deviceH);
    });

    bindingEvent();

    if (pCodeTemp != undefined && pCodeTemp != '') {
        ajax({
            url: '../../../getCache.do',
            data: {
                key: 'WEBAPP_REG_PCODE'
            },
            async: false,
            dataType: 'json',
            timeout: 30000,
            success: function (data) {
                var value = data.value
                if ('N' === value) {
                    $("#Disabled1").show();
                    $("#Disabled2").show();

                    $("#mobile-no").parent().parent().addClass("trans-div-dis");
                    $("#mobile-no").attr("disabled", "disabled");

                    $("#img-code").parent().parent().addClass("trans-div-dis");
                    $("#img-code").attr("disabled", "disabled");

                    $("#rand-code").parent().parent().addClass("trans-div-dis");
                    $("#rand-code").attr("disabled", "disabled");

                    $("#realname").parent().parent().addClass("trans-div-dis");
                    $("#realname").attr("disabled", "disabled");

                    $("#idCardNo").parent().parent().addClass("trans-div-dis");
                    $("#idCardNo").attr("disabled", "disabled");

                    $("#password").parent().parent().addClass("trans-div-dis");
                    $("#password").attr("disabled", "disabled");

                    $("#randImage").unbind();
                    $("#send-rand-code").unbind();
                    $("#reg").unbind();

                    $("#reg").attr("disabled", "disabled");
                    $("#reg").addClass("buttom-dis");

                    $("#voice_sms").hide();
                    $("#reg-agreement").parent().hide();
                }
            }
        });
    }

    $("#animateReg").hide();
    $("#merchantReg").show();
    $("#regDiv").show();
    $("#regDiv").addClass("animated fadeInDown");

    clearIntervalArray();
    //用户职业
    var jobTmep = GetQueryString("job");
    if (jobTmep != undefined) {
        job = jobTmep;
    }

    //_活动码
    var fundActivityCodeTemp = GetQueryString("fundActivityCode")
    if (fundActivityCodeTemp != undefined) {
        fundActivityCode = fundActivityCodeTemp;
    }

    //vip code
    var vipCodeTemp = GetQueryString("vipCode");
    if (vipCodeTemp != undefined) {
        vipCode = vipCodeTemp;
    }

    //推广码
    var uuidTemp = GetQueryString("uuid");
    if (uuidTemp != undefined) {
        uuid = uuidTemp;
    }
    //商户code
    if (pCodeTemp != undefined) {
        pCode = pCodeTemp;
    }
    regBottomImg();
});

function bindingEvent() {

    $("#realNameLink").click(function () {
        var flag = $(this).data("flag");
        if (flag == 0) {
            $("#realnameDiv").show("slow");
            $("#idCardNoDiv").show("slow");
            $(this).data("flag", 1);
            $(this).html("取消实名认证-");
            $(".outer-reg").css("height", "140%");
        } else {
            $("#realnameDiv").hide("slow");
            $("#idCardNoDiv").hide("slow");
            $("#realname").val("");
            $("#idCardNo").val("");
            $(this).data("flag", 0);
            $(this).html("实名认证+");
            $(".outer-reg").css("height", "120%");
        }
    });

    $('#mobile-no').blur(function () {
        try {
            //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_phone", label: "手机号", value: $(this).val(), custval: "" });
        } catch (e) {
        }
    });

    $('#mobile-no').keyup(function (e) {
        var keyCode = e.keyCode;
        if (keyCode == 37 || keyCode == 39) {
            return false;
        }

        var val = this.value;
        if (e.keyCode != 8) {
            var len = val.length;
            if (len == 3 || len == 9) {
                val += '  ';
            }
        }
        this.value = val;
    });

    $('#idCardNo').keyup(function (e) {
        var keyCode = e.keyCode;
        if (keyCode == 37 || keyCode == 39) {
            return false;
        }
        var val = this.value;
        if (keyCode != 8) {
            var len = val.length;
            if (len == 3 || len == 8 || len == 14 || len == 20) {
                val += '  ';
            }
        }

        this.value = val;
    });

    $('#rand-code').blur(function () {
        try {
            //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_verifycode", label: "验证码", value: $(this).val(), custval: "" });
        } catch (e) {
        }
    });

    $('#realname').blur(function () {
        try {
            //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_rename", label: "姓名", value: $(this).val(), custval: "" });
        } catch (e) {
        }
    });

    $('#idCardNo').blur(function () {
        try {
            //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_idcardno", label: "身份证号", value: $(this).val(), custval: "" });
        } catch (e) {
        }
    });

    $('#password').blur(function () {
        try {
            //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_login_pwd", label: "登录密码", value: "", custval: "" });
        } catch (e) {
        }
    });

    $("#randImage").click(function () {
        refVerifyCode();
    });

    /* $("#img-code").blur(function () {
     var imgCode = $.trim($("#img-code").val());
     if (imgCode == undefined || imgCode == '') {
     return;
     }
     if (!checkVerifyCode()) {
     $.dialog({
     content: '请输入正确的图片验证码',
     title: "tip",
     time: 1000
     });
     $("#img-code").focus();
     }
     });*/

    $("#disabledButton").click(function () {
        $("#Disabled1").hide();
        $("#Disabled2").hide();
    });

    $('#merchantReg #send-rand-code').click(function () {
        sendRandCode();
    });
    $('#reg').click(function () {
        storeReg();
    });
    $('#merchantReg #voiceCode').click(function () {
        sendVoiceSMSLogout();
    });
    $('#merchantReg #pwdShow').click(function () {
        pwdShow();
    });
    $('#merchantReg #reg-agreement').click(function () {
        regAgreement();
    });
    $('#agreement #agree-back').click(function () {
        closeAgreement();
    });
}

function refVerifyCode() {
    document.getElementById("randImage").src = "../../../verify/code.do?" + Math.random();
}

function checkVerifyCode() {
    var bool = false;
    var imgCode = $.trim($("#img-code").val());
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
function storeReg() {
    var mobileNo = clearTimar($('#merchantReg #mobile-no').val());
    if (mobileNo == '') {
        $.dialog({
            content: '手机号码不能为空',
            title: "tip",
            time: 2000
        });
        $("#merchantReg #mobile-no").focus();
        return false;
    }
    var vaRe = validateMobile(mobileNo);
    if (vaRe != "true") {
        $.dialog({
            content: vaRe,
            title: "tip",
            time: 2000
        });
        $("#merchantReg #mobile-no").focus();
        return false;
    }

    if (checkMobileExist(mobileNo)) {
        $.dialog({
            content: '您输入的手机号已被注册',
            title: "tip",
            time: 2000
        });
        $("#merchantReg #mobile-no").focus();
        return false;
    }

    var imgCode = $.trim($("#img-code").val());
    if (imgCode == undefined || '' == imgCode) {
        $.dialog({
            content: '图片验证码不能为空',
            title: "tip",
            time: 2000
        });
        $("#img-code").focus();
        return false;
    }

    /*if (!checkVerifyCode()) {
        $.dialog({
            content: '请输入正确的图片验证码',
            title: "tip",
            time: 2000
        });
        $("#img-code").focus();
        return false;
    }*/

    var randCode = $.trim($('#merchantReg #rand-code').val());
    if (randCode == '') {
        $.dialog({
            content: '短信验证码不能为空',
            title: "tip",
            time: 2000
        });
        $("#merchantReg #rand-code").focus();
        return false;
    }

    var niname = $.trim($("#merchantReg #niname").val());
    if (niname == '') {
        $.dialog({
            content: '请输入您的称呼',
            title: "tip",
            time: 2000
        });
        $("#merchantReg #niname").focus();
        return false;
    }

    if (!isFullwidthChar(niname)) {
        $.dialog({
            content: '请输入正确的昵称，比如输入您的真实姓名或者姓氏',
            title: "tip",
            time: 2000
        });
        $("#merchantReg #niname").focus();
        return false;
    }
    if (checkUserName(niname)) {
        $.dialog({
            content: '您输入的称呼已存在，请重新输入',
            title: "tip",
            time: 2000
        });
        $("#merchantReg #niname").focus();
        return false;
    }

    var password = $.trim($('#merchantReg #password').val());
    if (password == '') {
        $.dialog({
            content: '密码不能为空',
            title: "tip",
            time: 2000
        });
        $("#merchantReg #password").focus();
        return false;
    }

    if (validatePassword(password) != 'true') {
        $.dialog({
            content: '有效密码为6-16位数字字母组合',
            title: "tip",
            time: 2000
        });
        $("#merchantReg #password").focus();
        return false;
    }

    var flag = $("#realNameLink").data("flag");
    if (flag == 1) {
        var realname = $.trim($('#merchantReg #realname').val());
        var idCardNo = clearTimar($('#merchantReg #idCardNo').val());
        if ('' == realname) {
            $.dialog({
                content: '必须输入真实姓名',
                title: "tip",
                time: 2000
            });
            $("#merchantReg #realname").focus();
            return false;
        }
        if ('' == idCardNo) {
            $.dialog({
                content: '必须输入证件号码',
                title: "tip",
                time: 2000
            });
            $("#merchantReg #idCardNo").focus();
            return false;
        }

        if (!isCardValidate(idCardNo)) {
            $.dialog({
                content: '请输入正确的身份证号码',
                title: "tip",
                time: 2000
            });
            $("#merchantReg #idCardNo").focus();
            return false;
        }
    }

    ajax({
        url: '../../../user/checkSMS.do',
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
                regSubmit();
            } else if (result == '1') {
                $.dialog({
                    content: '验证码错误',
                    title: "tip",
                    time: 2000
                });
            }
        }
    });
}
function isFullwidthChar(str) {
    var reg = new RegExp("^([\u4e00-\u9fa5])*$")
    return reg.test(str);
}
function checkUserName(username) {
    var res = false;
    ajax({
        url: '../../../user/checkUserName.do',
        data: {
            userName: username
        },
        async: false,
        type: 'POST',
        dataType: 'text',
        success: function (result) {
            res = result == '1' ? true : false;
        }
    });
    return res;
}
function regSubmit() {
    try {
        //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_reg_button", label: "注册按钮", value: "", custval: "" });
    } catch (e) {
    }

    if ($('#merchantReg #reg').attr("isSubmit") == "true") {
        $.dialog({
            content: '正在注册请稍后...',
            title: "tip"
        });
        return;
    }
    var password = $.trim($('#merchantReg #password').val());
    var mobileNo = clearTimar($('#merchantReg #mobile-no').val());
    var randCode = $.trim($('#merchantReg #rand-code').val());
    var uuid = $.trim($("#uuid").val());
    var niname = $.trim($("#merchantReg #niname").val());

    var paramData = {};
    paramData["userName"] = "x" + niname;
    paramData["phone"] = mobileNo;
    paramData["password"] = $.md5($.md5(password));
    paramData["referer"] = '';
    paramData["uuid"] = uuid;
    paramData["regsource"] = 7;//用户来源1：网站注册,2：手机客户端注册,3：合作商户注册,4：合作商户导入,5：微信注册,6：移动网页注册,7:webapp(html5)'
    paramData["job"] = job;
    paramData["fundActivityCode"] = fundActivityCode;
    paramData["pCode"] = pCode;
    paramData["vipCode"] = vipCode;
    paramData["smsCode"] = randCode;

    var flag = $("#realNameLink").data("flag");
    if (flag == 1) {
        var realname = $.trim($('#merchantReg #realname').val());
        var idCardNo = clearTimar($('#merchantReg #idCardNo').val());
        paramData["realname"] = realname;
        paramData["idCardNo"] = idCardNo;
    }
    $('#merchantReg #reg').attr("isSubmit", "true");
    $('#merchantReg #reg').html("正在注册...");

    ajax({
        type: 'POST',
        url: '../../../user/regV3.do',
        data: paramData,
        dataType: 'json',
        timeout: 120000,
        success: function (result) {
            if (result.resultCode == 0 && result.regResultCode == 0 && result.mobileResultCode == 0 && result.realNameResultCode == 0 && result.partnerResultCode == 0) {
                $('#merchantReg #reg').attr("isSubmit", "false");
                $('#merchantReg #reg').html("注册");
                //注册成功
                try {
                    //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "reg_success_webapp", label: "注册成功", value: result.userid, custval: "1" });
                    //GA部署
                    gaClickEvent_UserId({property1: "注册", property2: "注册成功", property3: "手机注册"});

                } catch (e) {
                }
                window.location.href = "../activity/merchantRegSucc.html?name=" + realname + "&phone=" + mobileNo + "&pCode=" + pCode;
            } else if (result.resultCode == 0 && result.regResultCode == 0 && result.mobileResultCode == 0 && result.vipResultCode == 0) {
                var flag = $("#realNameLink").data("flag");
                if (flag == 1 && result.realNameResultCode != 0) {
                    $.dialog({
                        content: result.realNameDesc,
                        title: "tip",
                        okText:'重新认证',
                        ok:function(){
                            window.location.href = "../../../#!/static/html/personal/idCertified.html?realType=1&path=personal";
                        }
                    });
                } else {
                    $.dialog({
                        content: '注册成功',
                        title: "ok",
                        okText: '确定',
                        ok: function () {
                            try {
                                //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "reg_success_webapp", label: "注册成功", value: result.userid, custval: "0" });
                            } catch (e) {
                            }
                            window.location.href = "../../../";
                        }
                    });
                }

            } else {
                refVerifyCode();
                $('#merchantReg #reg').attr("isSubmit", "false");
                $('#merchantReg #reg').html("注册");
                if (result.resultCode != 0) {
                    $.dialog({
                        content: result.msg,
                        title: "tip",
                        time: 2000
                    });
                } else if (result.realNameResultCode != 0) {
                    //实名认证失败，跳转至webapp个人身份认证页面
                    $.dialog({
                        content: '证件号码认证失败，请上传身份证照片，申请快速认证',
                        title: "tip",
                        time: 2000,
                        okText: '去认证',
                        ok: function () {
                            try {
                                //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "reg_success_webapp", label: "注册成功", value: result.userid, custval: "0" });
                            } catch (e) {
                            }
                            window.location.href = "../../../#!/static/html/personal/idCertified.html?realType=1&path=personal";
                        }
                    });

                } else if (result.vipResultCode != 0) {
                    $.dialog({
                        content: result.vipDesc,
                        title: "tip",
                        time: 2000,
                        okText: '去认证',
                        ok: function () {
                            try {
                                //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "reg_success_webapp", label: "注册成功", value: result.userid, custval: "0" });
                            } catch (e) {
                            }
                            window.location.href = "../../../#!/static/html/personal/vipCertified.html?path=personal";
                        }
                    });
                } else {

                    var msg = '';
                    if (result.regResultCode != 0) {
                        msg = result.regDesc;
                    } else if (result.mobileResultCode != 0) {
                        msg = result.mobileDesc;
                    } else if (pCode != null && '' != pCode && result.partnerResultCode != 0) {
                        msg = result.partnerDesc;
                    }
                    $.dialog({
                        content: msg,
                        title: "tip",
                        time: 2000
                    });
                }
            }
        }
    });
}
function pwdShow() {
    if (flag == 0) {
        //set the password visible
        $('#merchantReg #pwdShow').removeClass("ico-closeeye");
        $('#merchantReg #pwdShow').addClass("ico-eye");
        $('#merchantReg #password').attr('type', 'text');
        flag = 1;
    } else {
        //set the password invisible
        $('#merchantReg #pwdShow').removeClass("ico-eye");
        $('#merchantReg #pwdShow').addClass("ico-closeeye");
        $('#merchantReg #password').attr('type', 'password');
        flag = 0;
    }

}

function clearTimar(value) {
    return value.replace(/\s+/g, '');
}

/**清空定时器，隐藏语音验证码*/
function clearIntervalArray() {
    $.each(intervalArray, function (index, value) {
        clearInterval(value);
    });
    intervalArray.length = 0;
}

function sendRandCode() {
    var mobileNo = clearTimar($('#mobile-no').val());
    if (mobileNo == '') {
        $.dialog({
            content: "手机号码不能为空",
            title: "tip",
            time: 2000
        });
        $("#mobile-no").focus();
        return false;
    }

    var vaRe = validateMobile(mobileNo);
    if (vaRe != "true") {
        $.dialog({
            content: vaRe,
            title: "tip",
            time: 2000
        });
        $("#mobile-no").focus();
        return false;
    }

    if (checkMobileExist(mobileNo)) {
        $.dialog({
            content: "您输入的手机号已被注册",
            title: "tip",
            time: 2000
        });
        $("#mobile-no").focus()
        return false;
    }

    var imgCode = $.trim($("#img-code").val());
    if (imgCode == undefined || '' == imgCode) {
        $.dialog({
            content: "图片验证码不能为空",
            title: "tip",
            time: 2000
        });
        $("#img-code").focus();
        return false;
    }

    ajax({
        url: '../../../user/sendSMS.do',
        data: {
            phone: mobileNo,
            type: 1,
            imgCode: imgCode
        },
        dataType: 'json',
        timeout: 30000,
        success: function (result) {
            var tempTime = time;
            if (result.resultCode == '0') {
                clearIntervalArray();
                $.dialog({
                    content: "验证码发送成功，请注意查收",
                    title: "tip",
                    time: 2000
                });
                refVerifyCode();
                $('#merchantReg #send-rand-code').hide();
                $('#merchantReg #voice_sms').hide();
                $('#merchantReg #rand-code-countdown').show().html(tempTime);
                intervalArray.push(setInterval(function () {
                    tempTime--;
                    if (tempTime < 0) {
                        $('#merchantReg #rand-code-countdown').hide();
                        $('#merchantReg #send-rand-code').show();
                        $('#merchantReg #voice_sms').show();
                        // 清除定时器
                        clearIntervalArray();
                    } else {
                        //获取短信验证码超过30秒，还未进行验证，显示获取语音验证码按钮
                        $('#merchantReg #rand-code-countdown').html(tempTime + '秒后重新发送');
                    }
                }, 1000));
            } else {
                $.dialog({
                    content: result.msg,
                    title: "tip",
                    time: 2000
                });
                if(result.resultCode == -250) {
                    refVerifyCode();
                }

                $('#merchantReg #rand-code-countdown').hide();
                $('#merchantReg #send-rand-code').show();
                // if(result.resultCode == -30){
                $('#merchantReg #voice_sms').show();
                // }
            }
        }
    });
}
//发送语音验证码
function sendVoiceSMSLogout() {
    var mobileNo = clearTimar($('#merchantReg #mobile-no').val());
    if (mobileNo == '') {
        $.dialog({
            content: '手机号码不能为空',
            title: "tip",
            time: 2000
        });
        $("#merchantReg #mobile-no").focus();
        return false;
    }

    var vaRe = validateMobile(mobileNo);
    if (vaRe != "true") {
        $.dialog({
            content: vaRe,
            title: "tip",
            time: 2000
        });
        $("#merchantReg #mobile-no").focus();
        return false;
    }

    if (checkMobileExist(mobileNo)) {
        $.dialog({
            content: "您输入的手机号已被注册",
            title: "tip",
            time: 2000
        });
        $("#merchantReg #mobile-no").focus();
        return false;
    }

    var imgCode = $.trim($("#img-code").val());
    if (imgCode == undefined || '' == imgCode) {
        $.dialog({
            content: "图片验证码不能为空",
            title: "tip",
            time: 2000
        });
        $("#img-code").focus();
        return false;
    }

    ajax({
        url: '../../../user/sendVoiceSMSLogout.do',
        data: {
            mobileNo: mobileNo,
            busiCode: 'BUSICODE_REGISTER_SMS',
            imgCode: imgCode
        },
        dataType: 'json',
        timeout: 30000,
        success: function (result) {
            var tempTime = time;
            if (result.resultCode == 0) {
                clearIntervalArray();
                $.dialog({
                    content: "请接听来自125909888353的电话，为您自动播报语音验证码",
                    title: "tip",
                    time: 2000
                });
                $('#merchantReg #voice_sms').hide();
                $('#merchantReg #send-rand-code').hide();
                $('#merchantReg #rand-code-countdown').show().html(tempTime);
                intervalArray.push(setInterval(function () {
                    tempTime--;
                    if (tempTime < 0) {
                        $('#merchantReg #rand-code-countdown').hide();
                        $('#merchantReg #voice_sms').show();
                        $('#merchantReg #send-rand-code').show();
                        // 清除定时器
                        clearIntervalArray();
                    } else {
                        $('#merchantReg #rand-code-countdown').html(tempTime + '秒后重新发送');
                    }
                }, 1000));
            } else {
                $.dialog({
                    content: result.msg,
                    title: "tip",
                    time: 2000
                });
            }
        }
    });
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

function regBottomImg() {
    ajax({
        url: '../../../getCache.do',
        dataType: 'json',
        data: {
            key: 'REPACKET_ONOFF'
        },
        timeout: 10000,
        success: function (result) {
            if (result.resultCode == 0) {
                if (result.value == "Y") {
                    $("#reg").html("立即领取108元红包");
                }
            }
        }
    });
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
                        content: "请求出错[" + xhr.status + "]，请重新尝试或检查您的网络是否正常",
                        title: "tip",
                        time: 2000
                    });
                }
            }
        });
    } catch (e) {
        $.dialog({
            content: "请求出错[" + e + "]，请重新尝试或检查您的网络是否正常",
            title: "tip",
            time: 2000
        });
    }
}
function regAgreement() {
    document.getElementById("merchantReg").style.display = "none";
    document.getElementById("agreement").style.display = "block";
}
function closeAgreement() {
    document.getElementById("merchantReg").style.display = "block";
    document.getElementById("agreement").style.display = "none";
}