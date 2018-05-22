require(['jquery', 'requirejs', 'IScroll', 'xui_user_v101', 'com','trackBase'], function ($, requirejs, IScroll, xui_user, com,track) {

    // 金币发行量
    var aCoins = [6, 2, 1, 5, 2, 4, 3, 7, 1, 2, 4, 1, 6, 3, 5, 8, 5, 2, 7, 4, 2, 5], aMyCoins = [], scrollTop = 0;
    // 获取cookie
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg))
            return (arr[2]);
        else
            return null;
    }
    //部码
    $.ajax({
        url     : '/feapi/users/loginInfo' + '?userToken=' + getCookie('userToken'),
        dataType: 'json',
        data    : {},
        async   : false,
        type    : 'GET',
        success : function (str) {
            if(str.code == "200000"){
                if (str.data.status.code == 200) {
                    track.init(str.data);
                }else {
                    track.init();
                }
            }else {
                track.init();
            }

        },
        error:function (str) {
            track.init();
        }
    });

    var ev = {
        funUser: function () {
            //登录实例
            xui_user.Login({
                //容器*必写（多个实例时容器不可同一个CLASS）
                frame   : '#login',
                //用户名输入框
                user    : '#login_user',
                //密码输入框
                password: '#login_password',
                //验证码输入框
                vCode   : '#login_v_code',
                //验证码图片
                vCodeImg: '#login_v_img',
                //提交登录按钮
                submit  : '#login_submit',
                //提示回调
                prompt  : function (result) {
                    // console.log(result);
                    $('#login_msg').text(result.msg);
                    $('html').addClass('logined');
                    // 提示识别码的应用示例
                    // if (result.code == 102) {
                    //     alert('用使消息类型识别码做一个判断');
                    // }
                },
                //仅登录成功回调
                success : function (result) {
                    // console.log(result.msg);
                    $('#login_msg').text(result.msg);
                    ux.xUser();
                    if (xui_user.isLogin()) {
                         $('.fobar-reg').addClass('hide');
                    }
                    window.location.href = 'index-web.html';
                }
            });

            //注册实例
            xui_user.Register({
                //容器*必写（多个实例时容器不可同一个CLASS）
                frame   : '#reg',
                //用户名输入框
                user    : '#reg_user',
                //密码输入框
                password: '#reg_password',
                //验证码输入框
                vCode   : '#reg_v_code',
                //验证码图片
                vCodeImg: '#reg_v_img',
                //手机验证码
                mCode   : '#reg_m_code',
                //手机验证码发送按钮
                mCodeBtn: '#reg_m_btn',
                //提交注册按钮
                submit  : '#reg_submit',
                prompt  : function (result) {
                    // console.log(result);
                    $('#reg_msg').text(result.msg);
                    // 提示识别码的应用示例
                    // if (result.code == 200) {
                    //     alert('用使消息类型识别码自定义提示消息：如：亲，可以给我手机号嘛！么么哒！')
                    // }
                },
                //仅登录成功回调
                success : function (result) {
                    // console.log(result.msg);console.log
                    $('#reg_msg').text(result.msg);
                    ux.xUser();
                    if (xui_user.isLogin()) {
                        $('.fobar-reg').addClass('hide');
                    }
                }
            });
        }
    };

    var ux = {
        oLogin      : function () {
            scrollTop   = $(document).scrollTop();
            var thisTop = 'translateY(-' + $(document).scrollTop() + 'px)';
            // console.log(scrollTop);
            $('#user').removeClass('reg').addClass('login show');
            $('body').addClass('ban');
            $('#middle').css('transform', thisTop);

        },
        oReg        : function () {
            $('#user').removeClass('login').addClass('reg show');
        },
        xUser       : function () {
            $('#user').removeClass('login reg show');
            $('body').removeClass('ban');
            $('#middle').removeAttr('style');
            $(document).scrollTop(scrollTop)
        }
    };

    var db = {
        getToken  : function () {
            // 获取token
            var token = null;
            $.ajax({
                url     : '/feapi/users/formToken',
                dataType: 'json',
                type    : 'GET',
                async   : false,
                data    : {},
                success : function (result) {
                    token = result.token;
                },
                error   : function () {
                    // console.log('网络出错')
                }
            });
            return token;
        },
        logedUser : function () {
            /*
             登录人数
             接口文档：http://dev.xxd.com/activityCenter/swagger-ui.html#!/SpecialActivities/todayLogedUserCountUsingGET
             */
            $.xxdAjax({
                url     : '/activityCenter/specialActivities/todayLogedUserCount',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                data    : {},
                type    : 'GET',
                callbacks : function (str) {
                    // console.log(str);
                    if (str.code == 200000) {
                        $('#people').text(str.data.todayLogedUserCount);
                    }
                },
                error   : function (str) {
                    alert('数据查询失败，请联系客服')
                }
            });
        }
    };


    $(function () {

        $(document).on('touchmove', '#prize,#rule,#prompt', function () {
            return false;
        });

        var scroll1 = new IScroll('#scroll1', {
            bounce       : false,
            scrollbars   : true,
            mouseWheel   : true,
            click        : true,
            bindToWrapper: true
        });
        if (xui_user.isLogin()) {
            $('.fobar-reg').addClass('hide');
        }
        $(document).on('click', '.to-login', function () {
            if (xui_user.isLogin()) {
                window.location.href = "/html/summer-welfare/index-web.html";
            }else {
                ux.oReg();
            }
        });
        $('#x_user').click(function () {
            ux.xUser();
        });

        /*
         登录注册
         */
        $(document).on('click', '#o_login', function () {
            ux.oLogin();
        });

        $(document).on('click', '#o_reg', function () {
            ux.oReg();
        });

        /*
         领取金币
         */
        $(document).on('click', '#to_draw', function () {

            db.loginPrize();

        });


        ev.funUser();

        db.logedUser();

    });


}, function (err) {
    // console.log(err);
    alert(err)
});