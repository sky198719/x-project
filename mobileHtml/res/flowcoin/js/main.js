require(['base', "requirejs", 'json', "juicer", "trackBase"], function ($, register, login, requirejs,track) {
    var countdownSecond = 60;
    var intervalArray = [];
    var pCode;
    $(document).ready(function () {
        pCode = GetQueryString("pCode");
        sevenDays();
        //plan();
        fund();
        monthFinance();

        $("#randImage").click(function () {
            $("#randImage").attr("src", "../../../../m/verify/code.do?" + Math.random());
        });

        $("#login_verifyCode").click(function () {
            $("#login_verifyCode").attr("src", "../../../../m/randCode/createVerifyCode.do?" + Math.random());
        });

        $(".sign-up").click(function () {
            reg();
        });

        $("#sendSMSCode").click(function () {
            sendSMSCode();
        });

        $("#xxdRegisterAgreementShow").click(function () {
            $("#xxdRegisterAgreement").show();
        });

        $("#xxdRegisterAgreementBut").click(function () {
            $("#xxdRegisterAgreement").hide();
        });

        $("#login").click(function () {
            $("#loginDiv").show();
        });

        $("#loginClose").click(function () {
            $("#loginDiv").hide();
        });

        $(".partake").on("click", function () {
            $(".pop-up").show();
        })
        $(".close").on("click", function () {
            $(".pop-up").hide();
        })

        $("#login_submit").click(function () {
            login();
        });

        $(".phoneNo").on("blur", function () {
            checkPhone();
        });

        $(".password-inp").on("blur", function () {
            checkPwd();
        });

        $("#login_username").on("blur",function(){
            var username = $.trim($("#login_username").val());
             var login_username_error = $(".login_username_error");
             if (username == '') {
                 login_username_error.html("请输入您的手机号码");
                 login_username_error.show();
                 return false;
             }
             login_username_error.hide();
        });

        $("#login_pwd").on("blur",function(){
            var login_pwd = $.trim($("#login_pwd").val());
              var login_pwd_error = $(".login_pwd_error");
              if (login_pwd == '') {
                  login_pwd_error.html("请输入你的登录密码");
                  login_pwd_error.show();
                  return false;
              }
              login_pwd_error.hide();
        });
    });

    function plan() {
        $.ajax({
            url: '../../../../m/xplan/getLatestSchemeId.do',
            dataType: 'json',
            data: {
                currentPage: 1,
                pageSize: 1,
                closeTerm: 12
            },
            type: 'GET',
            success: function (result) {
                if (result.resultCode == 0) {
                    $("#xplanApr").html(result.schemeInfo.MAXAPR);
                }
            }
        });
    }

    function sevenDays() {
        $.ajax({
            url: '../../../../m/sevenDays/selectSevenDaysInfo.do',
            dataType: 'json',
            type: 'GET',
            success: function (result) {
                if (result.resultCode == 0) {
                    $("#sevenDaysApr").html(result.sevenDays.apr + result.sevenDays.floatApr);
                }
            }
        });
    }
    function fund() {
        $.ajax({
            url: '../../../../m/fund/selectFundInfo.do',
            dataType: 'json',
            type: 'GET',
            success: function (result) {
                if (result.resultCode == 0) {
                    $("#fundApr").html((result.fundApr.apr + result.fundApr.floatApr) + "%");
                }
            }
        });
    }

    function monthFinance() {
        $.ajax({
            url: '../../../../m/monthFinance/getProudctDetail.do',
            dataType: 'json',
            type: 'GET',
            success: function (result) {
                if (result.resultCode == 0) {
                    $("#monthFinanceApr").html(result.data.apr + "%");
                }
            }
        });
    }

    function login() {
        var username = $.trim($("#login_username").val());
        var login_username_error = $(".login_username_error");
        if (username == '') {
            login_username_error.html("请输入您的手机号码");
            login_username_error.show();
            return false;
        }
        login_username_error.hide();

        var login_pwd = $.trim($("#login_pwd").val());
        var login_pwd_error = $(".login_pwd_error");
        if (login_pwd == '') {
            login_pwd_error.html("请输入你的登录密码");
            login_pwd_error.show();
            return false;
        }
        login_pwd_error.hide();

        var imgCode = $.trim($("#login_img").val());
        var login_img_error = $(".login_img_error");
        if (imgCode == '') {
            login_img_error.html("图片验证码不能为空");
            login_img_error.show();
            return false;
        }
        login_img_error.hide();

        $.ajax({
            url: '../../../../m/user/login.do',
            data: {
                imgCode: imgCode,
                username: username,
                password: $.md5($.md5(login_pwd))
            },
            dataType: 'json',
            type: 'POST',
            timeout: 10000,
            success: function (result) {
                if (result.resultCode == 0) {
                    window.location.href = '../../../../m';
                } else {
                    var login_img_error = $(".login_img_error");
                    var msg;
                    if (result.resultCode == -1) {
                        msg = "用户名称不存在，请重新输入";
                    } else if (result.resultCode == -2) {
                        msg = "密码错误，请重新输入";
                    } else if (result.resultCode == -3 || result.resultCode == -4) {
                        msg = result.msg;
                    } else if (result.resultCode == -9) {
                        msg = "登录失败，请重新尝试或者联系客服";
                    } else {
                        msg = result.msg;
                    }
                    login_img_error.html(msg);
                    login_img_error.show();
                }
            }
        });
    }
    function checkPhone() {
        var phone = $.trim($(".phoneNo").val());
        var phoneError = $(".phone-error");
        if (phone == '') {
            phoneError.html("请输入手机号码");
            phoneError.show();
            return false;
        }
        var vaRe = validateMobile(phone);
        if (vaRe != "true") {
            phoneError.html("手机号错误，请重新确认");
            phoneError.show();
            return false;
        }
        if (checkMobileExist(phone)) {
            phoneError.html("您输入的手机号已被注册");
            phoneError.show();
            return false;
        }
        phoneError.hide();
        return true;
    }
    function checkPwd() {
        var pwd = $.trim($(".password-inp").val());
        var pwdError = $(".pwd-error");
        if (pwd == '') {
            pwdError.html("请输入密码");
            pwdError.show();
            return false;
        }
        if (validatePassword(pwd) != 'true') {
            pwdError.html("有效密码为6-16位数字字母组合");
            pwdError.show();
            return false;
        }
        pwdError.hide();
        return true;
    }
    function reg() {
        $(".reg-error").hide();

        if (!checkPhone()) {
            return false;
        }

        if (!checkPwd()) {
            return false;
        }

        var imgCode = $.trim($(".img-inp").val());
        var imgCodeError = $(".imgCode-error");
        if (imgCode == '') {
            imgCodeError.html("请输入图片验证码");
            imgCodeError.show();
            return false;
        }
        imgCodeError.hide();

        var smsCode = $.trim($(".smsCode").val());
        var smsCodeError = $(".smsCode-error");
        if (smsCode == '') {
            smsCodeError.html("请输入短信验证码");
            smsCodeError.show();
            return false;
        }
        smsCodeError.hide();

        var phone = $.trim($(".phoneNo").val());
        var pwd = $.trim($(".password-inp").val());
        $.ajax({
            url: '../../../../m/user/checkSMS.do',
            data: {
                code: smsCode,
                mobileNo: phone,
                busiCode: 'BUSICODE_REGISTER_SMS'
            },
            dataType: 'text',
            type: 'POST',
            timeout: 10000,
            success: function (result) {
                if (result == '0') {
                    $.ajax({
                        type: 'POST',
                        url: '../../../../m/user/regV3.do',
                        data: {
                            phone: phone,
                            password: $.md5($.md5(pwd)),
                            referer: '',
                            smsCode: smsCode,
                            regsource: 7, //用户来源1：网站注册,2：手机客户端注册,3：合作商户注册,4：合作商户导入,5：微信注册,6：移动网页注册,7:webapp(html5)'
                            pCode: pCode
                        },
                        dataType: 'json',
                        timeout: 120000,
                        success: function (result) {
                            if (result.resultCode != 0) {
                                $(".reg-error").html(result.msg);
                                $(".reg-error").show();
                                return;
                            }
                            if (result.regResultCode == 0 && result.mobileResultCode == 0) {
                                if (pCode != null && pCode != "") {
                                    if (result.partnerResultCode == 0) {
                                        window.location.href = '../../../../m';
                                    } else {
                                        $(".reg-error").html(result.partnerDesc);
                                        $(".reg-error").show();
                                    }
                                } else {
                                    window.location.href = '../../../../m';
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
                                $(".reg-error").html(msg);
                                $(".reg-error").show();
                            }
                        }
                    });
                } else if (result == '1') {
                    //xxdApp.alert('短信验证码错误', '抱歉');
                    smsCodeError.html("验证码错误，请重新确认");
                    smsCodeError.show();
                }
            }
        });
    }

    function sendSMSCode() {
        var phone = $.trim($(".phoneNo").val());
        var phoneError = $(".phone-error");
        if (phone == '') {
            phoneError.html("请输入手机号码");
            phoneError.show();
            return false;
        }
        var vaRe = validateMobile(phone);
        if (vaRe != "true") {
            phoneError.html("手机号错误，请重新确认");
            phoneError.show();
            return false;
        }
        if (checkMobileExist(phone)) {
            phoneError.html("您输入的手机号已被注册");
            phoneError.show();
            return false;
        }
        phoneError.hide();

        var imgCode = $.trim($(".img-inp").val());
        var imgCodeError = $(".imgCode-error");
        if (imgCode == '') {
            imgCodeError.html("请输入图片验证码");
            imgCodeError.show();
            return false;
        }
        imgCodeError.hide();
        $(".smsCode-error").hide();
        $.ajax({
            url: '../../../../m/user/sendSMS.do',
            data: {
                phone: phone,
                type: 1,
                imgCode: imgCode
            },
            dataType: 'json',
            timeout: 30000,
            success: function (result) {
                var tempTime = countdownSecond;
                if (result.resultCode == '0') {
                    clearIntervalArray();
                    $("#sendSMSCode").hide();
                    $('#phoneVerifyCodeCountdown').show(tempTime + '秒后重新发送');
                    intervalArray.push(setInterval(function () {
                        tempTime--;
                        if (tempTime < 0) {
                            $('#phoneVerifyCodeCountdown').hide();
                            $('#sendSMSCode').show();
                            // 清除定时器
                            clearIntervalArray();
                        } else {
                            $('#phoneVerifyCodeCountdown').html(tempTime + '秒后重新发送');
                        }
                    }, 1000));
                } else {
                    $(".smsCode-error").html(result.msg);
                    $(".smsCode-error").show();
                    $('#phoneVerifyCodeCountdown').hide();
                    $('#sendSMSCode').show();
                }
            }
        });
    }

    function clearIntervalArray() {
        $.each(intervalArray, function (index, value) {
            clearInterval(value);
        });
        intervalArray.length = 0;
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

    //检查手机号是否已被使用
    function checkMobileExist(mobileNo) {
        var exist = false;
        $.ajax({
            url: '../../../../m/user/checkMobile.do',
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
    function validateMobile(arg) {
        var patter = /^0?(13|15|14|17|18)[0-9]{9}$/;
        if (patter.test(arg)) {
            return 'true';
        } else {
            return '请输入正确的手机号码';
        }
    }
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)return  decodeURI(r[2]);
        return null;
    }

    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg))

            return (arr[2]);
        else
            return null;
    }
    $.ajax({
        url     : '/feapi/users/loginInfo?userToken=' + getCookie('userToken'),
        dataType: 'json',
        async   : false,
        data    : {},
        type    : 'GET',
        success : function (str) {
            if(str.code == "200000") {
                if (str.data.status.code === 200) {
                    //result = true;
                    track.init(str.data);
                }else{
                    track.init();
                }
            } else {
                track.init();
            }
        },
        error:function (str) {
            track.init();
        }
    });
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});