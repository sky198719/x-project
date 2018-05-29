require(['base', 'float', 'trackBase', 'store', "requirejs", "paging"], function ($, float, track, store, dialog) {
    // 布码init
    var userDO = store && store.get("userDO") || {};
    track.init(userDO);

    //注册用到的数据
    var msg = {
        phoneNull: '请输入手机号码！',
        phoneCorrect: '请输入有效的手机号码！',
        pswNull: '请输入密码！',
        pswCorrect: '请输入8-20个字符的密码！',
        verifyNull: '请输入图片验证码！',
        verifyCorrect: '请输入正确的图片验证码！',
        verifyNumberNull: '请输入短信验证码！',
        verifyNumberCorrect: '请输入正确的手机验证码！',
        phoneExistence: '您输入的手机号已被注册！',
        pswPhoneExist: '密码不能包括手机号！',
        errorMsg: '请检查您的网络！',
        argeeInfo: '请勾选《_使用协议》'

    };
    var dataUrl = {
        checkMobileUrl: '/feapi/users/checkMobile',
        tokenUrl: '/feapi/users/formToken',
        getVerifyUrl: ' /feapi/randCode/createVerifyCode',
        registerUrl: '/feapi/users/regV3',
        isLogin: '/feapi/users/loginInfo',
        sendSMSUrl: '/feapi/users/sendSMS',
        login: '/feapi/users/login'
    };
    var object = [
            $('#object1'), $('#object2'), $('#object3'), $('#object4'), $('#object5'), $('#object6'), $('#object7'), $('#object8'), $('#object9'), $('#object10'),
            $('#object11'), $('#object12'), $('#object13'), $('#object14'), $('#object15'), $('#object16'), $('#object17'), $('#object18'), $('#object19'),
            $('#object20'), $('#object21'), $('#object22')
        ],
        number = [6, 2, 1, 5, 2, 4, 3, 7, 1, 2, 4, 1, 6, 3, 5, 8, 5, 2, 7, 4, 2, 5],
        object2 = [];

    //注册代码
    var regToken, inteval, time = 60;
    // 获取 regToken ,token
    $.ajax({
        type: 'GET',
        url: dataUrl.tokenUrl,
        async: false,
        data: {},
        dataType: 'json',
        success: function (result) {
            regToken = result.token;
            loadimage();
        },
        error: function () {
            float.alert({content: msg.errorMsg});
        }
    });

    //刷新验证码
    $("#J_getVerify").on('click', function (ev) {
        var ev = ev || event;
        loadimage();
        ev.preventDefault();
    });

    //注册提交
    $("#J_submit").on('click', function (ev) {
        var ev = ev || event;
        var phoneNum = $("#J_num").val();
        var password = $("#J_psw").val();
        var phoneCode = $("#J_phoneyzm").val();
        // var imageCode=$("#J_verify").val();
        if (doSubmit()) {
            clearinterval();
            $.ajax({
                type: 'POST',
                url: dataUrl.registerUrl,
                async: false,
                data: {
                    "formtoken": regToken,
                    "mobile": phoneNum,
                    "password": password,
                    "smscode": phoneCode
                },
                dataType: 'json',
                success: function (result) {
                    if (result.regResultCode === '200000') {
                        //注册成功并登陆操作regResultCode
                        //float.alert({content: '注册成功，恭喜您已获得108元红包'});
                        setTimeout(function () {
                            window.location.href = "/user/ilogin.html";
                        }, 3000);

                    } else {
                        clearinterval();
                        _prompt(result.info);
                    }
                },
                error: function () {
                    clearinterval();
                    _prompt(msg.errorMsg);
                }


            })
        }
        ev.preventDefault();
    });

    //发送手机验证码
    $("#J_getPhoneCode").on('click', function (ev) {
        var ev = ev || event;
        if (!$(this).hasClass('disfont')) {
            checkPhone();
        }
        ev.preventDefault();
    });


//    注册框判断
    function doSubmit() {
        var bol = true;
        var phoneNum = $("#J_num").val();
        var password = $("#J_psw").val();
        var imageCode = $("#J_verify").val();
        var phoneCode = $("#J_phoneyzm").val();
        var rpass = validatePassword(password);
        if (phoneNum == "" || phoneNum == null) {
            _prompt(msg.phoneNull);
            bol = false;
            return bol;
        } else if (!validateMobile(phoneNum)) {
            _prompt(msg.phoneCorrect);
            bol = false;
            return bol;
        } else if (password == "" || password == null) {
            _prompt(msg.pswNull);
            bol = false;
            return bol;
        } else if (rpass != 'true') {
            _prompt(rpass);
            bol = false;
            return bol;
        } else if (imageCode == "" || imageCode == null) {
            _prompt(msg.verifyNull);
            bol = false;
            return bol;
        } else if (phoneCode == "" || phoneCode == null) {
            _prompt(msg.verifyNumberNull);
            bol = false;
            return bol;
        } else if (rpass != 'true' && password.indexOf(phoneNum) >= 0) {
            _prompt(msg.pswPhoneExist);
            bol = false;
            return bol;
        } else if (!$("#agree").hasClass('checked')) {
            _prompt(msg.argeeInfo);
            bol = false;
            return bol;
        }
        $("#J_cur").removeClass('disblock').addClass('dishidden');
        // $("#J_submit").html('正在注册，请稍候...');
        return bol;

    }

    //判断手机验证码
    function checkPhone() {
        var phoneNum = $("#J_num").val();
        var imageCode = $("#J_verify").val();

        if (phoneNum == '') {
            _prompt(msg.phoneNull);
            return false;
        } else if (!validateMobile(phoneNum)) {
            _prompt(msg.phoneCorrect);
            return false;
        }
        if (imageCode == '' || imageCode == null) {
            _prompt(msg.verifyNull);
            return false;
        }
        $.ajax({
            type: 'POST',
            url: dataUrl.checkMobileUrl,
            async: false,
            data: {"formtoken": regToken, "mobile": phoneNum},
            dataType: 'json',
            success: function (result) {
                // $.log(result);
                if (result.code == 0) {
                    _prompt(result.info);
                    return false;
                } else if (result.code == 1) {
                    $.ajax({
                        type: 'POST',
                        url: dataUrl.sendSMSUrl,
                        async: false,
                        data: {
                            "formtoken": regToken,
                            "imgcode": imageCode,
                            "mobile": phoneNum
                        },
                        dataType: 'json',
                        success: function (result) {
                            if (result.code === '200000') {
                                $("#J_cur").removeClass('disblock').addClass('dishidden');
                                clearinterval(inteval);
                                inteval = setInterval(function () {
                                    setTime();
                                }, 1000);
                            } else if (result.code === -40) {
                                loadimage();
                                // $("#J_submit").html("立即注册");
                                $("#J_verify").focus();
                                _prompt(result.info);
                                return false;
                            } else {
                                // $("#J_submit").html("立即注册");
                                _prompt(result.info);
                                return false;
                            }
                        },
                        error: function () {
                            // $("#J_submit").html("立即注册");
                            _prompt(msg.errorMsg);
                            return false;
                        }
                    })
                } else {
                    _prompt(result.info);
                    // $("#J_submit").html("立即注册");
                    return false;
                }
            },
            error: function () {
                // $("#J_submit").html("立即注册");
                _prompt(msg.errorMsg);
                return false;
            }
        });

    }

//    倒计时
    function setTime() {
        time = time - 1;
        if (time < 0) {
            time = 60;
            // $("#J_getPhoneCode").removeAttr("disabled");
            // $("#J_getPhoneCode").removeClass('disfont');
            // $('#J_getPhoneCode').html('获取验证码');
            clearinterval();
        } else {
            $("#J_getPhoneCode").addClass('disfont');
            $('#J_getPhoneCode').html(time + '秒后重新获取');
        }
    }

    //清除时间
    function clearinterval() {
        clearInterval(inteval);
        $("#J_getPhoneCode").removeClass('disfont');
        $('#J_getPhoneCode').html('获取验证码');
        // $("#J_submit").html("立即注册");

    }

    //判断手机号的格式
    function validateMobile(arg) {
        var patter = /^0?(13|15|14|17|18)[0-9]{9}$/;
        if (patter.test(arg)) {
            return true;
        } else {
            return false;
        }
    }

    //   提示
    function _prompt(msg) {
        $('#J_cur').html("");
        $('#J_cur').removeClass('dishidden').addClass('disblock');
        $('#J_cur').html(msg);
        loadimage();
    }

    //    图形验证码
    function loadimage() {
        $("#J_verify").val('');
        $("#J_getVerify").attr('src', dataUrl.getVerifyUrl + '?formtoken=' + regToken + '&v=' + Math.random());
    }

    //密码
    function validatePassword(arg) {
        var patter = /^([a-zA-Z0-9])*$/;
        var patter1 = /^([a-zA-Z])*$/;
        var patter2 = /^([0-9])*$/;
        if (arg.length < 6) {
            return '有效密码为6-16位数字,字母组合！';
        }
        if (arg.length > 16) {
            return '密码长度不得超过16位';
        }
        if (!patter.test(arg) || (patter1.test(arg) || patter2.test(arg))) {
            return '有效密码为6-16位数字，字母组合！';
        }
        return 'true';
    }

    var islogin = isLogin();

    //点击参加活动
    $('#toJoin').click(function () {
        if (!islogin) {
            $('html,body').animate({'scrollTop': '300px'}, 300);
        } else {
            window.location.href = '/html/redAugust/redAugust.html';
        }
    });
    //产品点击
    $('.land-product').on('click',function () {
        if (!islogin) {
            $('html,body').animate({'scrollTop': $('.bg1')[0].offsetTop}, 300);
        } else {
            window.location.href = '/html/redAugust/redAugust.html';
        }
    });
    //立即去注册
    $('#toSubmit').click(function () {
        $('html,body').animate({'scrollTop': $('.bg1')[0].offsetTop}, 300);
    });

    $(window).scroll(function () {
        if($('.bg6')){
            if((document.documentElement.scrollTop>= $('.bg5')[0].offsetTop) || (document.body.scrollTop >= $('.bg5')[0].offsetTop)) {
                $('.bg6').removeClass('to-fixed');
                $('.land-footer').removeClass('to-bottom');
            }else if ((document.documentElement.scrollTop>= $('.bg2')[0].offsetTop) || (document.body.scrollTop >= $('.bg2')[0].offsetTop)) {
                $('.bg6').addClass('to-fixed');
                $('.land-footer').addClass('to-bottom');
            } else{
                $('.bg6').removeClass('to-fixed');
                $('.land-footer').removeClass('to-bottom');
            }
        }
    });

    $('.check-style').click(function () {
        $(this).toggleClass('checked');
    });

    //签到
    /*$.ajax({
        url: '/activityCenter/specialActivities/loginStatus',
        type: 'get',
        data: {},
        dataType: 'json',
        beforeSend: function (request) {
            request.setRequestHeader("clientId", 'XXD_ACTIVITY_H5_PAGE');
            request.setRequestHeader("clientTime", new Date().getTime());
            request.setRequestHeader("token", getCookie('Token'));
        },
        success: function (data) {
            if (data.code == '200000') {
                if (isLogin()) {
                    $.each(data.data.items, function (i, v) {
                        if(i<=data.data.sysdate){
                            $(object[i]).attr('sign', v);
                            if ($(object[i]).attr('sign') == 'true') {
                                $(object[i]).append("<span class='signed-coin'> " +
                                    "<img src='img/coin.png'> " +
                                    "<i>+</i> <i>" + number[i] + "</i>" +
                                    "</span>").addClass('signed');
                                $('#object22').find('.signed-coin').remove();
                            }
                        }
                    });
                    $.each(object, function (i, v) {
                        if ((i > data.data.sysdate) &&  (data.data.sysdate != -1)) {
                            object2.push(v);
                        }
                    });
                    if(data.data.sysdate != -1){
                        $.each(object2, function (i, v) {
                            $(v).append("<span class='signed-coin'> " +
                                "<img src='img/coin.png'> " +
                                "<i>+</i> <i>?</i>" +
                                "</span>");
                            $('#object22 span').remove();
                        });
                    }
                }
            }
        }
    });*/
    $.xxdAjax({
        url: '/activityCenter/specialActivities/loginStatus',
        clientId: 'XXD_ACTIVITY_H5_PAGE',
        type: 'GET',
        token: getCookie('Token'),
        data: {},
        dataType: 'json',
        callbacks: function (data) {
            if (data.code == '200000') {
                if (isLogin()) {
                    $.each(data.data.items, function (i, v) {
                        if(i <=data.data.sysdate ){
                            $(object[i]).attr('sign', v);
                            if ($(object[i]).attr('sign') == 'true') {
                                $(object[i]).append("<span class='signed-coin'> " +
                                    "<img src='img/coin.png'> " +
                                    "<i>+</i> <i>" + number[i] + "</i>" +
                                    "</span>").addClass('signed');
                                $('#object22').find('.signed-coin').remove();
                            }
                        }
                    });
                    $.each(object, function (i, v) {
                        if ((i > data.data.sysdate) &&  (data.data.sysdate != -1)) {
                            object2.push(v);
                        }
                    });
                    if(data.data.sysdate != -1){
                        $.each(object2, function (i, v) {
                            $(v).append("<span class='signed-coin'> " +
                                "<img src='img/coin.png'> " +
                                "<i>+</i> <i>?</i>" +
                                "</span>");
                            $('#object22 span').remove();
                        });
                    }
                }
            }
        },
        error: function () {
            float.alert({content: msg.errorMsg});
        }
    });
    //查询总人数
    getPeople();
    function getPeople() {
        /*$.ajax({
            type: 'GET',
            url: '/activityCenter/specialActivities/todayLogedUserCount',
            data: {},
            dataType: 'json',
            beforeSend: function (request) {
                request.setRequestHeader("clientId", 'XXD_ACTIVITY_H5_PAGE');
                request.setRequestHeader("clientTime", new Date().getTime());
            },
            success: function (data) {
                if (data.code == 200000) {
                    $('#userNumber').html(data.data.todayLogedUserCount);
                }
            },
            error: function () {
                float.alert({content: msg.errorMsg});
            }
        });*/
        $.xxdAjax({
            url: '/activityCenter/specialActivities/todayLogedUserCount',
            clientId: 'XXD_ACTIVITY_H5_PAGE',
            type: 'GET',
            data: {},
            dataType: 'json',
            callbacks: function (data) {
                if (data.code == 200000) {
                    $('#userNumber').html(data.data.todayLogedUserCount);
                }
            },
            error: function () {
                float.alert({content: msg.errorMsg});
            }
        });
    }

    //判断是否登录
    function isLogin() {
        var result = false;
        $.ajax({
            type: 'GET',
            url: dataUrl.isLogin + '?userToken=' + getCookie('Token'),
            async: false,
            data: {},
            dataType: 'json',
            success: function (str) {
                if (str.code == "200000") {
                    if (str.data.status.code == 200) {
                        result = true;
                        //已登录状态左边显示不一样
                        $('.register').addClass('logged').html("<div class='login-done'> " +
                            "<p>您好，<span id='username'>"+str.data.name+"</span></p> " +
                            "<p>欢迎使用_！</p> " +
                            "<a href='/xplan/search/list.html'>马上投标</a></div> ");
                        $('.bg6').hide();
                    } else {
                        result = false;
                    }
                } else {
                    result = false;
                }
            },
            error: function () {
                float.alert({content: msg.errorMsg});
            }
        });
        return result;
    }

    //获取cookie
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg)) {
            return (arr[2]);
        }
        else {
            return null;
        }
    }

    //设置cookie
    function SetCookie(name, value) {
        var exp = new Date();
        exp.setTime(exp.getTime() + (1 * 24 * 60 * 60 * 1000));
        window.document.cookie = name + "=" + escape(value) + "; expires=" + exp.toGMTString() + ";path=/";
    }


}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});