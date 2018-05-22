var intervalArray = [];
var time = 60;
var pCode;
//获取地址栏url参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return  decodeURI(r[2]);
    return null;
}
$(document).ready(function () {
    pCode = GetQueryString("pCode");
    pCode = pCode == undefined ? "" : pCode;

    try {
        var url = window.location.href + "&utm_source=" + pCode + "&utm_campaign=热跑";
        //XXD_TRACK._trackPageview({page_url: url});
    } catch (e) {
    }

    clearIntervalArray();
    eventBind();
});

function isLogin() {
    var result = false;
    $.ajax({
        url: "../../../user/isLogin.do?" + new Date().getTime(),
        dataType: 'json',
        async: false,
        data: {},
        success: function (data) {
            if (data.isLogin != null) {
                result = data.isLogin;
            }
        }
    });
    return result;
}
function eventBind() {
    $("#loadRegisterAgreement").click(function () {
        $(".xxd-xy").show("slow");
        $(".box").hide()
    });

    //点击立即报名注册页面出现
    $(".enroll").on("click", function () {
        if (isLogin()) {
            if (!isRealName()) {
                $.ajax({
                    url: "../../../activity/rePaoJoinActivity.do",
                    dataType: 'json',
                    success: function (data) {
                        console.log(data);
                        if (data.resultCode == -3 || data.resultCode == -1) {
                            $.dialog({
                                content: data.msg,
                                title: "tip",
                                time: 3000
                            });
                        } else {
                            $(".gray").show();
                            $(".sure").show();
                            $(".register").hide();
                            $(".loginDiv").hide();
                            $(".sureTile").html("未实名认证，请先进行实名认证");
                            $(".no_pass").hide();
                            $(".no_pass").data("flag", "0");
                        }

                        /* if(data.resultCode == -2 || data.resultCode == 0) {

                         }  else {
                         $.dialog({
                         content: data.msg,
                         title: "tip",
                         time: 3000
                         });
                         }  */
                    }
                });
            } else {
                $.ajax({
                    url: "../../../activity/rePaoJoinActivity.do",
                    dataType: 'json',
                    success: function (data) {
                        $.dialog({
                            content: data.msg,
                            title: "tip",
                            time: 4000
                        });
                    }
                });
            }
        } else {
            $(".gray").show();
            $(".register").show();
            $(".reg_success").hide();
            $(".log_success").hide();
            $(".sure").hide();
            $(".loginDiv").hide();
            $(".phone_btn").val("");
            $(".pass_btn").val("");
            $(".code_btn").val("");
            $(".mob_btn").val("");
            $(".s2").css("background", "img/two02.png");

            refVerifyCode();
        }
    });

    //点击关闭注册页面消失
    $(".close").on("click", function () {
        $(".gray").hide();
        $(".s2").css("background", "img/two.png");
    });

    //确认
    $(".of_course").on("click", function () {
        realNameOnline();
    });

    $(".reg_btn").click(function () {
        reg();
    });

    $("#getCode").click(function () {
        getCode();
    });

    $("#agree-back").click(function () {
        $(".xxd-xy").hide();
        $(".box").show()
    });

    $(".login").click(function () {
        $(".register").hide();
        $(".loginDiv").show();
        refVerifyCodeLogin();
    });

    $(".login_btn").click(function () {
        login();
    });

    $(".goRedPack").click(function () {
        window.location.href = "../../../#!/static/html/account/hongbao.html?path=account";
    });

    $('.phone_btn').blur(function () {
        try {
            //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_phone", label: "手机号", value: $(this).val(), custval: "" });
        } catch (e) {
        }
    });
    $('.mob_btn').blur(function () {
        try {
            //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_verifycode", label: "验证码", value: $(this).val(), custval: "" });
        } catch (e) {
        }
    });
    $('.name_btn').blur(function () {
        try {
            //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_rename", label: "姓名", value: $(this).val(), custval: "" });
        } catch (e) {
        }
    });
    $('.id_btn').blur(function () {
        try {
            //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_idcardno", label: "身份证号", value: $(this).val(), custval: "" });
        } catch (e) {
        }
    });

    $('.pass_btn').blur(function () {
        try {
            //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_login_pwd", label: "登录密码", value: "", custval: "" });
        } catch (e) {
        }
    });

}


function isRealName() {
    var reuslt = 0;
    $.ajax({
        url: '../../../personal/getRealName.do?' + new Date().getTime(),
        dataType: 'json',
        async: false,
        success: function (data) {
            reuslt = data.resultStatus;
        }
    });
    return reuslt;
}

function realNameOnline() {
    if ($(".of_course").data("isSubmit") == 'true') {
        return;
    }
    hide_of_course();
    var name = $.trim($(".name_btn").val());
    if (name == '') {
        $.dialog({
            content: '真实姓名不能为空',
            title: "tip",
            time: 2000
        });
        show_of_course();
        $(".name_btn").focus();
        return false;
    }

    var id = $.trim($(".id_btn").val());
    if (id == '') {
        $.dialog({
            content: '身份证号不能为空',
            title: "tip",
            time: 2000
        });
        show_of_course();
        $(".id_btn").focus();
        return false;
    }

    if (!isCardValidate(id)) {
        $.dialog({
            content: '请输入正确的身份证号码',
            title: "tip",
            time: 2000
        });
        show_of_course();
        $(".id_btn").focus();
        return false;
    }

    var flag = $(".no_pass").data("flag");
    var pathUp = '';
    var pathBack = '';
    if (flag == 1) {
        var formdata = new FormData();
        formdata.append("frontFile", $('#frontFile')[0].files[0]);
        formdata.append("backFile", $('#backFile')[0].files[0]);

        ajaxFileUpload({
            url: '../../../approve/realNameImg.do',
            data: formdata,
            timeout: 120000,
            success: function (result) {
                show_of_course();
                var data = $.parseJSON(result);
                if (data.code == -400 || data.code == -2 || data.code == -3 || data.code == -1) {
                    $.dialog({
                        content: data.info,
                        title: "tip",
                        time: 2000
                    });
                    return false
                } else {
                    pathUp = data.pathUp;
                    pathBack = data.pathBack;
                    realNameApr(name, id, flag, pathUp, pathBack)
                }
            }
        });
    } else {
        realNameApr(name, id, flag, pathUp, pathBack);
    }
}

function ajaxFileUpload(options) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", options.url, true);
    xhr.send(options.data);
    xhr.onload = function (e) {
        if (this.status == 200) {
            options.success(this.responseText);
        }
    };
}

function realNameApr(name, id, uploadIdCardPicFlag, picUp, picDown) {
    $.ajax({
        url: "../../../approve/realNameApr.do",
        type: 'POST',
        data: {
            realname: name,
            idCardNo: id,
            uploadIdCardPicFlag: uploadIdCardPicFlag,
            cardType: 1,
            picUp: picUp,
            picDown: picDown
        },
        dataType: 'json',
        timeout: 120000,
        success: function (data) {
            show_of_course();
            if (data.resultCode == 1) {
                $(".reg_success").show();
                $(".loginDiv").hide();
            } else {
                $.dialog({
                    content: data.msg,
                    title: "alt",
                    okText: '确定',
                    ok: function () {
                        if (data.resultCode != -1) {
                            $(".no_pass").show();
                            $(".no_pass").data("flag", "1");
                        }
                    }
                });
            }
        }
    });
}

function login() {
    var username = $.trim($(".login_username").val());
    if (username == "") {
        $.dialog({
            content: '用户名不能为空',
            title: "tip",
            time: 2000
        });
        $(".login_username").focus();
        return false;
    }

    var pass = $.trim($(".login_pass").val());
    if (pass == "") {
        $.dialog({
            content: '登录密码不能为空',
            title: "tip",
            time: 2000
        });
        $(".login_pass").focus();
        return false;
    }

    var imgCode = $.trim($(".login_imgCode").val());
    if (imgCode == '') {
        $.dialog({
            content: '请输入图片验证码',
            title: "tip",
            time: 2000
        });
        $(".login_imgCode").focus();
        return false;
    }

    $.ajax({
        url: '../../../user/login.do',
        type: 'POST',
        data: {
            username: username,
            password: $.md5($.md5(pass)),
            imgCode: imgCode
        },
        dataType: 'json',
        success: function (result) {
            refVerifyCodeLogin();
            $(".login_imgCode").val("");
            if (result.resultCode == 0) {
                $(".gray").hide();
                $(".s2").css("background", "img/two.png");
            } else if (result.resultCode == -1) {
                $.dialog({
                    content: '用户名称不存在，请重新输入',
                    title: "tip",
                    time: 2000
                });
            } else if (result.resultCode == -2) {
                $.dialog({
                    content: '密码错误，请重新输入',
                    title: "tip",
                    time: 2000
                });
            } else if (result.resultCode == -3 || result.resultCode == -4) {
                $.dialog({
                    content: result.msg,
                    title: "tip",
                    time: 2000
                });
            } else if (result.resultCode == -9) {
                $.dialog({
                    content: '登录失败，请重新尝试或者联系客服',
                    title: "tip",
                    time: 2000
                });
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

function getCode() {
    var mobile = $.trim($(".phone_btn").val());
    if (mobile == '') {
        $.dialog({
            content: '手机号码不能为空',
            title: "tip",
            time: 2000
        });
        $(".phone_btn").focus();
        return false;
    }

    var vaRe = validateMobile(mobile);
    if (vaRe != "true") {
        $.dialog({
            content: vaRe,
            title: "tip",
            time: 2000
        });
        $(".phone_btn").focus();
        return false;
    }

    if (checkMobileExist(mobile)) {
        $.dialog({
            content: '您输入的手机号已被注册',
            title: "tip",
            time: 2000
        });
        $(".phone_btn").focus();
        return false;
    }

    var code = $.trim($(".code_btn").val());
    if (code == '') {
        $.dialog({
            content: '图片验证码不能为空',
            title: "tip",
            time: 2000
        });
        $(".code_btn").focus();
        return false;
    }

    if (!checkVerifyCode(code)) {
        $.dialog({
            content: '请输入正确的图片验证码',
            title: "tip",
            time: 2000
        });
        $(".code_btn").focus();
        return false;
    }

    $.ajax({
        url: '../../../user/sendSMS.do',
        data: {
            phone: mobile,
            type: 1,
            imgCode: code
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
                $('#getCode').hide();
                $('#showCode').show().html(tempTime);
                intervalArray.push(setInterval(function () {
                    tempTime--;
                    if (tempTime < 0) {
                        $('#showCode').hide();
                        $('#getCode').show();
                        // 清除定时器
                        clearIntervalArray();
                    } else {
                        //获取短信验证码超过30秒，还未进行验证，显示获取语音验证码按钮
                        $('#showCode').html(tempTime + '秒后重新发送');
                    }
                }, 1000));
            } else {
                $.dialog({
                    content: result.msg,
                    title: "tip",
                    time: 2000
                });
                $('#showCode').hide();
                $('#getCode').show();
            }
        }
    });
}

function hide_reg_btn() {
    var reg_btn = $(".reg_btn");
    reg_btn.data("isSubmit", "true");
    reg_btn.html("正在注册...");
}
function show_reg_btn() {
    var reg_btn = $(".reg_btn");
    reg_btn.data("isSubmit", "false");
    reg_btn.html("注册报名领取红包");
}

function hide_of_course() {
    var reg_btn = $(".of_course");
    reg_btn.data("isSubmit", "true");
    reg_btn.html("正在认证...");
}
function show_of_course() {
    var reg_btn = $(".of_course");
    reg_btn.data("isSubmit", "false");
    reg_btn.html("确认");
}
function reg() {
    try {
        //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "partner_reg_button", label: "注册按钮", value: "", custval: "" });
    } catch (e) {
    }
    var sub = $(".reg_btn").data("isSubmit");
    if (sub == 'true') {
        return;
    }

    hide_reg_btn();
    var mobile = $.trim($(".phone_btn").val());
    if (mobile == '') {
        $.dialog({
            content: '手机号码不能为空',
            title: "tip",
            time: 2000
        });
        show_reg_btn();
        $(".phone_btn").focus();
        return false;
    }

    var vaRe = validateMobile(mobile);
    if (vaRe != "true") {
        $.dialog({
            content: vaRe,
            title: "tip",
            time: 2000
        });
        show_reg_btn();
        $(".phone_btn").focus();
        return false;
    }

    if (checkMobileExist(mobile)) {
        $.dialog({
            content: '您输入的手机号已被注册',
            title: "tip",
            time: 2000
        });
        show_reg_btn();
        $(".phone_btn").focus();
        return false;
    }

    var pass = $.trim($(".pass_btn").val());
    if (pass == '') {
        $.dialog({
            content: '密码不能为空',
            title: "tip",
            time: 2000
        });
        show_reg_btn();
        $(".pass_btn").focus();
        return false;
    }

    if (validatePassword(pass) != 'true') {
        $.dialog({
            content: '有效密码为6-16位数字字母组合',
            title: "tip",
            time: 2000
        });
        show_reg_btn();
        $(".pass_btn").focus();
        return false;
    }

    var code = $.trim($(".code_btn").val());
    if (code == '') {
        $.dialog({
            content: '图片验证码不能为空',
            title: "tip",
            time: 2000
        });
        show_reg_btn();
        $(".code_btn").focus();
        return false;
    }

    if (!checkVerifyCode(code)) {
        $.dialog({
            content: '请输入正确的图片验证码',
            title: "tip",
            time: 2000
        });
        show_reg_btn();
        $(".code_btn").focus();
        return false;
    }

    var mob_btn = $.trim($(".mob_btn").val());
    if (mob_btn == '') {
        $.dialog({
            content: '请输入短信验证码',
            title: "tip",
            time: 2000
        });
        show_reg_btn();
        $(".mob_btn").focus();
        return false;
    }

    $.ajax({
        url: '../../../user/checkSMS.do',
        data: {
            code: mob_btn,
            mobileNo: mobile,
            busiCode: 'BUSICODE_REGISTER_SMS'
        },
        dataType: 'text',
        indicator: true,
        type: 'POST',
        timeout: 10000,
        success: function (result) {
            if (result == '0') {
                $.ajax({
                    type: 'POST',
                    url: '../../../user/regV3.do',
                    data: {
                        phone: mobile,
                        password: $.md5($.md5(pass)),
                        referer: '',
                        smsCode: mob_btn,
                        regsource: 7, //用户来源1：网站注册,2：手机客户端注册,3：合作商户注册,4：合作商户导入,5：微信注册,6：移动网页注册,7:webapp(html5)'
                        pCode: pCode
                    },
                    dataType: 'json',
                    timeout: 120000,
                    success: function (result) {
                        show_reg_btn();
                        if (result.resultCode == 0 && result.regResultCode == 0 && result.mobileResultCode == 0) {
                            $.ajax({
                                url: "../../../activity/rePaoJoinActivity.do",
                                dataType: 'json',
                                success: function (data) {
                                    if (data.resultCode == -1 || data.resultCode == -3) {
                                        $(".gray").hide();
                                        $(".register").hide();
                                        $(".sure").hide();
                                        $.dialog({
                                            content: data.msg,
                                            title: "tip",
                                            time: 4000
                                        });
                                    } else {
                                        $(".gray").show();
                                        $(".register").hide();
                                        $(".sure").show();
                                    }
                                }
                            });
                            //注册成功
                            try {
                                //XXD_TRACK._trackEvent({category: "webapp_partner_reg", action: "reg_success_webapp", label: "注册成功", value: result.userid, custval: "1" });
                                //GA部署
                                gaClickEvent_UserId({property1: "注册", property2: "注册成功", property3: "手机注册"});

                            } catch (e) {
                            }
                        } else {
                            refVerifyCode();
                            if (result.resultCode != 0) {
                                $.dialog({
                                    content: result.msg,
                                    title: "tip",
                                    time: 2000
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
            } else if (result == '1') {
                $.dialog({
                    content: '短信验证码错误',
                    title: "tip",
                    time: 2000
                });
                show_reg_btn();
            }
        }
    });
}

function refVerifyCode() {
    document.getElementById("randImage").src = "../../../randCode/createVerifyCode.do?" + Math.random();
}
function refVerifyCodeLogin() {
    document.getElementById("randImageLogin").src = "../../../randCode/createVerifyCode.do?" + Math.random();
}

/**清空定时器，隐藏语音验证码*/
function clearIntervalArray() {
    $.each(intervalArray, function (index, value) {
        clearInterval(value);
    });
    intervalArray.length = 0;
}

function checkVerifyCode(imgCode) {
    var bool = false;
    $.ajax({
        url: '../../../randCode/checkVerifyCode.do',
        data: {
            imgCode: imgCode
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
/**
 * 校验支付密码
 * @param arg
 * @returns {string}
 */
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
function validateMobile(arg) {
    var patter = /^0?(13|15|14|17|18)[0-9]{9}$/;
    if (patter.test(arg)) {
        return 'true';
    } else {
        return '请输入正确的手机号码';
    }
}
//检查手机号是否已被使用
function checkMobileExist(mobileNo) {
    var exist = false;
    $.ajax({
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


