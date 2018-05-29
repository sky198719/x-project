require(['base', "register", 'login','vTicker', "requirejs", 'trackBase', 'json', 'juicer'], function ($, register, login, vTicker,requirejs, track) {
    var intervalArray = [];
    var clientId = 'XXD_ACTIVITY_H5_PAGE',
        clientTime = '',
        activityCode = 'July-17-vip-activity',
        userToken,
        activityStatus,  //活动状态
        remainLotteryTimes,//游戏次数
        usedLotteryTimes, //本次活动可用次数
        thisTime,
        pageid = 1,
        pageall = 0,
        flag = true,
        _token = '',
        xxd_utm_source;

    var itemPrizes = [
        {itmeName: '索尼微单!', itemPrize: '索尼微单', itemId: 1},
        {itmeName: '佳能亲子DV!', itemPrize: '佳能亲子DV', itemId: 1},
        {itmeName: '1000元京东E卡!', itemPrize: '1000元京东E卡', itemId: 1},
        {itmeName: '500元京东E卡!', itemPrize: '500元京东E卡', itemId: 1},
        {itmeName: '200元哈根达斯券!', itemPrize: '200元哈根达斯券', itemId: 1},
        {itmeName: '100元话费直充!', itemPrize: '100元话费直充', itemId: 1},
        {itmeName: '50元话费直充！', itemPrize: '50元话费直充', itemId: 1},
        {itmeName: '60个新新币', itemPrize: '60个新新币', itemId: 1},
        {itmeName: '10个新新币!', itemPrize: '10个新新币', itemId: 1}
    ];
    var xui = {
        /*prompt  : function (conf) {
            if ($('.xui-prompt[dim="true"]').length > 0) {
                this.dim = false;
            } else {
                this.dim = conf.dim || false;
            }
            this.pos  = conf.pos || 'cc';
            this.time = conf.time || 3000;
            if (conf.icon) {
                this.icon = '<p class="prompt-icon"><b class="prompt-icon-' + conf.icon + '"></b></p>';
            } else {
                this.icon = '';
            }
            this.msg = conf.msg || '';
            this.id  = $('.xui-prompt').length + 1;
            if (this.msg) {
                $('.xui-prompt').remove();
                $('body').addClass('prompt-o-hide').append('<div style="display:none" id="prompt' + this.id + '" class="xui-prompt" dim="' + this.dim + '" pos="' + this.pos + '"><div class="xui-prompt-dim"><div class="xui-prompt-content">' + this.icon + '<p>' + this.msg + '</p></div></div></div>');
                var oId = '#prompt' + this.id;

                setTimeout(function () {
                    $(oId).stop().fadeIn(200);
                }, 100);

                setTimeout(function () {
                    $(oId).stop().fadeOut(function () {
                        this.remove();
                    });
                    $('body').removeClass('prompt-o-hide');
                }, parseInt(this.time));
            }
        },*/
        Login   : function (conf) {
            var dataUrl = {
                token  : '/feapi/users/formToken',
                login  : '/feapi/users/login',
                vCode  : '/feapi/randCode/createVerifyCode',
                isLogin: '/feapi/users/loginInfo'

            };

            var loginToken,
                oFrame    = conf.frame,
                uiCode    = conf.uiCode || {auto: false, theme: 'theme'},
                sTheme    = 'theme/xui-login-register@theme=' + uiCode.theme + '.html',
                oUser     = conf.user || '.ux_login_user',
                oPassword = conf.password || '.ux_login_password',
                oVCode    = conf.vCode || '.ux_login_v_code',
                oVCodeImg = conf.vCodeImg || '.ux_login_v_code_img',
                oSubmit   = conf.submit || '.ux_login_submit',
                oOpen     = conf.open || '.ux_open_login',
                oClose    = conf.close || '.ux_close_login',
                oShow     = conf.show || '.ux_show_login',
                oHide     = conf.hide || '.ux_hide_login';

            var ui = {
                inLoginMain: function (onFrame) {
                    var oldMainCode = window.localStorage.getItem(onFrame);

                    if (oldMainCode) {
                        if ($(onFrame).length > 0) {
                            // 不重新打开
                            $(onFrame).attr('theme', uiCode.theme).attr('source', 'js').html(oldMainCode);

                        } else {
                            $('body').append('<div id="' + onFrame.substr(1) + '" class="xui-login" source="js">' + oldMainCode + '</div>');
                        }
                    } else {
                        $.ajax({
                            url     : sTheme,
                            type    : 'get',
                            data    : {},
                            dataType: 'html',
                            success : function (mainCode) {
                                if ($(onFrame).length > 0) {
                                    // 不重新打开

                                    if ($(onFrame).html()) {
                                        //console.log('有了啊？')
                                    } else {
                                        $(onFrame).attr('theme', uiCode.theme).attr('source', 'js').html(mainCode);
                                    }

                                } else {
                                    $('body').append('<div id="' + onFrame.substr(1) + '" class="xui-login" source="js">' + mainCode + '</div>');
                                }
                                dc.getVCode($(onFrame));

                            },
                            error   : function () {

                            }
                        });
                    }


                }
            };

            // 插件标记
            $(oFrame).addClass('xui-login');

            // 插入主体
            if (uiCode.auto === true) {
                ui.inLoginMain(oFrame);
            }

            // 获取token
            $.ajax({
                url     : dataUrl.token,
                dataType: 'json',
                type    : 'GET',
                data    : {},
                success : function (result) {
                    loginToken = result.token;
                    dc.getVCode($(oFrame));
                },
                error   : function () {
                    console.log('网络出错')
                }
            });

            event();
            function event() {
                // 打开登录
                $(document).on('click', oOpen, function () {
                    var onFrame = $(this).data('frame');
                    if (onFrame === oFrame) {
                        ux.openLogin(onFrame);
                    }
                });

                // 关闭登录
                $(document).on('click', oClose, function () {
                    var onFrame = $(this).data('frame');
                    console.log($(onFrame).attr('source') === 'js');
                    if (onFrame === oFrame) {
                        ux.closeLogin(onFrame);
                    }
                });

                // 显示登录
                $(document).on('click', oShow, function () {
                    // var onFrame = $(this).data('frame');
                    // if (onFrame === oFrame) {
                    //     ux.showLogin(onFrame);
                    // }
                    main.hideNoLogin();
                    $('#floater').show(0).addClass('show');
                    $("body").addClass('balance');
                    $('#reg1').stop().fadeOut(0);
                    $('#login3').stop().fadeIn(0);

                });

                // 隐藏登录
                $(document).on('click', oHide, function () {
                    var onFrame = $(this).data('frame');
                    if (onFrame === oFrame) {
                        ux.hideLogin(onFrame);
                    }
                });

                // 提交数据
                $(document).on('click', oFrame + ' ' + oSubmit, function () {
                    var $onFrame = $(this).parents('.xui-login');
                    dc.toSubmit($onFrame);
                });

                // 刷新验证码
                $(document).on('click', oFrame + ' ' + oVCodeImg, function () {
                    var $onFrame = $(this).parents('.xui-login');
                    dc.getVCode($onFrame);

                });
            }

            // 交互层
            var ux = {
                openLogin : function (onFrame) {
                    ui.inLoginMain(onFrame);
                },
                closeLogin: function (onFrame) {
                    var onFrameCode = $(onFrame).html();
                    if ($(onFrame).attr('source') != 'js') {
                        ux.beifen(onFrame, onFrameCode);
                    }
                    $(onFrame).detach();


                },
                showLogin : function (onFrame) {
                    $(onFrame).show(300);
                },
                hideLogin : function (onFrame) {
                    $(onFrame).hide(300);
                },
                beifen    : function (onFrame, onFrameCode) {
                    window.localStorage.setItem(onFrame, onFrameCode);
                },
                success   : function () {
                    // alert('默认的成功事件');
                }
            };

            // 数据层
            var dc = {
                // 获取cookie
                getCookie: function (name) {
                    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

                    if (arr = document.cookie.match(reg))

                        return (arr[2]);
                    else
                        return null;
                },
                // 判断是否登录
                isLogin  : function () {
                    var result = false;
                    $.ajax({
                        url     : dataUrl.isLogin + '?userToken=' + dc.getCookie('userToken'),
                        dataType: 'json',
                        async   : false,
                        data    : {},
                        type    : 'GET',
                        success : function (str) {
                            if (str.data.status.code === 200) {
                                result = true;
                            }
                        }
                    });
                    return result;
                },
                // 提交登录
                toSubmit : function ($onFrame) {
                    var $user = $onFrame.find(oUser), $password = $onFrame.find(oPassword), $vCode = $onFrame.find(oVCode);

                    var username = $.trim($user.val());
                    if (username == '') {
                        // console.log('! 请输入手机号');
                        // xui.prompt({
                        //     time: 1000,
                        //     icon: 'warning',
                        //     msg : '请输入手机号！'
                        // });
                        $('#login_prompt').html("请输入手机号");
                        $user.focus();
                        return false
                    }else {
                        $('#login_prompt').html("");
                    }
                    // var isTel = /^0?(13|15|14|17|18)[0-9]{9}$/;
                    // if (!isTel.test(username)) {
                    // console.log('x 手机号格式错误');
                    // xui.prompt({
                    //     time: 1000,
                    //     icon: 'error',
                    //     msg : '手机号格式错误！'
                    // });
                    // $user.focus();
                    // return false;
                    // }
                    var password = $.trim($password.val());
                    if (password == '') {
                        // console.log('! 请输入密码');
                        // xui.prompt({
                        //     time: 1000,
                        //     icon: 'warning',
                        //     msg : '请输入密码！'
                        // });
                        $('#login_prompt').html("请输入密码");
                        $password.focus();
                        return false;
                    }else {
                        $('#login_prompt').html("");
                    }


                    var vCode = $.trim($vCode.val());
                    if (vCode == '') {
                        // console.log('! 请输入验证码');
                        // xui.prompt({
                        //     time: 1000,
                        //     icon: 'warning',
                        //     msg : '请输入验证码！'
                        // });
                        $('#login_prompt').html("请输入验证码");
                        $vCode.focus();
                        return false;
                    }else {
                        $('#login_prompt').html("");
                    }

                    /*xui.prompt({
                        time: 10000,
                        icon: 'loading',
                        msg : '正在登录，请稍候...'
                    });*/
                    $('#login_prompt').html("正在登录，请稍候...");

                    $.ajax({
                        url     : dataUrl.login,
                        data    : {
                            formtoken: loginToken,
                            username : username,
                            password : password,
                            imgcode  : vCode
                        },
                        dataType: 'json',
                        type    : 'POST',
                        success : function (result) {
                            try {

                                if (conf.callback && typeof(conf.callback) == "function") {
                                    conf.callback(result);
                                } else {
                                    if (result.code === '200000') {
                                        //console.log(result);
                                        // console.log('v ' + result.info);
                                        /*xui.prompt({
                                            time: 2000,
                                            icon: 'success',
                                            msg : '您已登录成功！'
                                        });*/
                                        main.init();
                                        $('#login_prompt').html("您已登录成功!");
                                        $("body").removeClass('balance');
                                        $('#floater').removeClass('show').addClass('hide2');
                                        setTimeout(function () {
                                            $('#floater').hide().removeClass('hide2');
                                        }, 500);
                                        track.init(result.data.user);
                                        ga('send', 'event', '登录', "登录成功", window.location.href);
                                        // setTimeout(function () {
                                        //     window.location.href = "/m";
                                        // }, 2500);

                                        try {

                                            if (conf.success && typeof(conf.success) == "function") {
                                                conf.success(result);
                                            } else {
                                                ux.success();
                                            }
                                        } catch (e) {
                                        }

                                    } else {
                                        if (result.code === -1) {
                                            $user.focus();
                                        } else if (result.code === -2) {
                                            $vCode.val('');
                                            $password.val('');
                                            $password.focus();
                                        } else if (result.code === -40) {
                                            $vCode.val('').focus();
                                        } else if (result.code === -101) {
                                            $vCode.val('').focus();
                                        }
                                        dc.getVCode($onFrame);
                                        // console.log(result);
                                        // console.log('x ' + result.info);
                                        /*xui.prompt({
                                            time: 1000,
                                            icon: 'error',
                                            msg : result.info
                                        });*/
                                        $('#login_prompt').html(result.info);

                                    }
                                }
                            } catch (e) {
                            }

                        },
                        error   : function () {
                            // console.log('ajax 出错')
                            /*xui.prompt({
                                time: 2000,
                                icon: 'error',
                                msg : '请检查您的网络！'
                            });*/
                            $('#login_prompt').html('请检查您的网络！');
                        }
                    });
                },
                // 获取验证码
                getVCode : function ($onFrame) {
                    $onFrame.find(oVCodeImg).css('background-image', 'url(' + dataUrl.vCode + '?formtoken=' + loginToken + '&v=' + Math.random() + ')');
                }
            };

        },
        register: function (conf) {
            var regToken,
                $frame    = $(conf.frame),
                oUser     = conf.user || '.ux_user',
                oPassword = conf.password || 'ux_password',
                oVCode    = conf.vCode || '.ux_v_code',
                oVCodeImg = conf.vCodeImg || '.ux_v_code_img',
                oMCode    = conf.mCode || '.ux_m_code',
                oMCodeBtn = conf.mCodeBtn || '.ux_m_code_btn',
                oSubmit   = conf.submit || '.ux_submit',
                oOpen     = conf.open || '.ux_open',
                oClose    = conf.close || '.ux_close',
                oShow     = conf.show || '.ux_show',
                oHide     = conf.hide || '.ux_hide';

            $(oUser).attr('maxlength', '11');
            $(oPassword).attr('maxlength', '20');

            // 获取token
            $.ajax({
                url     : '/feapi/users/formToken',
                dataType: 'json',
                type    : 'GET',
                data    : {},
                success : function (result) {
                    regToken = result.token;
                    getVCode();
                },
                error   : function () {
                    // console.log('ajax 出错')
                    /*xui.prompt({
                        time: 2000,
                        icon: 'error',
                        msg : '请检查您的网络！'
                    });*/
                    $('#reg_prompt').html('请检查您的网络！');
                }
            });

            event();

            function event() {

                // 打开注册
                $(document).on('click', oOpen, function () {
                    if ($(this).data('open') === $frame.attr('id')) {
                        openReg();
                        console.log($frame);
                    }

                });

                // 关闭注册
                $(document).on('click', oClose, function () {
                    if ($(this).data('close') === $frame.attr('id')) {
                        closeReg()
                    }
                });

                // 显示注册
                $(document).on('click', oShow, function () {
                    if ($(this).data('show') === $frame.attr('id')) {
                        showReg()
                    }

                });

                // 隐藏注册
                $(document).on('click', oHide, function () {
                    if ($(this).data('hide') === $frame.attr('id')) {
                        hideReg()
                    }
                });

                // 刷新验证码
                $(document).on('click', oVCodeImg, function () {
                    getVCode();
                });

                // 发送手机验证码
                $(document).on('click', oMCodeBtn, function () {
                    getMCode();
                });

                // 提交注册
                $(document).on('click', oSubmit, function () {
                    toSubmit();
                });
            }

            function getVCode() {
                $(oVCodeImg).attr("src", "/feapi/randCode/createVerifyCode?formtoken=" + regToken + '&v=' + Math.random());
            }

            function getMCode() {
                var username = $.trim($(oUser).val());

                if (username == '') {
                    // console.log('请输入注册手机号');
                    /*xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : ''
                    });*/
                    $('#reg_prompt').html('请输入注册手机号！');
                    $(oUser).focus();
                    return false;
                }

                var ifUsername = validateMobile(username);
                if (ifUsername === false) {
                    // console.log('手机号格式错误');
                    /*xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : ''
                    });*/
                    $('#reg_prompt').html('手机号格式错误！');
                    $(oUser).focus();
                    return false;
                }

                var vCode = $.trim($(oVCode).val());
                if (vCode == '') {
                    // console.log('请输入图形验证码');
                    /*xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : ''
                    });*/
                    $('#reg_prompt').html('请输入图形验证码！');
                    $(oVCode).focus();
                    return false;
                }

                $.ajax({
                    url     : '/feapi/users/checkMobile',
                    dataType: 'json',
                    type    : 'POST',
                    data    : {
                        formtoken: regToken,
                        mobile   : username
                    },
                    success : function (result) {
                        if (result.code === 0) {
                            /*xui.prompt({
                                time: 2000,
                                icon: 'warning',
                                msg : result.info
                            });*/
                            $('#reg_prompt').html(result.info);
                        }
                        else if (result.code === 1) {
                            $.ajax({
                                url     : '/feapi/users/sendSMS',
                                dataType: 'json',
                                type    : 'POST',
                                data    : {
                                    formtoken: regToken,
                                    imgcode  : vCode,
                                    mobile   : username
                                },
                                success : function (result) {

                                    if (result.code === '200000') {
                                        var tempTime = 60;
                                        $(oMCode).addClass('sending').html('已发送');
                                        intervalArray.push(setInterval(function () {
                                            tempTime--;
                                            if (tempTime < 0) {
                                                $(oMCodeBtn).removeClass('sending').html('发送验证码');
                                                // 清除定时器
                                                // clearIntervalArray();
                                            } else {
                                                //获取短信验证码超过30秒，还未进行验证，显示获取语音验证码按钮
                                                $(oMCodeBtn).addClass('sending').html(tempTime + '秒后重新发送');
                                            }
                                        }, 1000));

                                    } else if (result.code === -40) {
                                        getVCode();
                                        $(oVCode).focus();
                                    }
                                    /*xui.prompt({
                                        time: 2000,
                                        icon: 'warning',
                                        msg : result.info
                                    });*/
                                    $('#reg_prompt').html(result.info);
                                }
                            });
                        }else{
                            $('#reg_prompt').html(result.info);
                        }
                    },
                    error   : function () {
                        // console.log('ajax 出错')
                        /*xui.prompt({
                            time: 2000,
                            icon: 'error',
                            msg : '请检查您的网络！'
                        });*/
                        $('#reg_prompt').html('请检查您的网络!');
                    }
                });

            }


            function validateMobile(arg) {
                var patter = /^0?(13|15|14|17|18)[0-9]{9}$/;
                if (patter.test(arg)) {
                    return true;
                } else {
                    return false;
                }
            }

            function toSubmit() {
                var username = $.trim($(oUser).val());
                if (username == '') {
                    // console.log('请输入注册手机号');
                    /*xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请输入注册手机号'
                    });*/
                    $('#reg_prompt').html('请输入注册手机号!');
                    $(oUser).focus();
                    return;
                }
                var ifUsername = validateMobile(username);
                if (ifUsername === false) {
                    // console.log('手机号格式错误');
                    /*xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '手机号格式错误'
                    });*/
                    $('#reg_prompt').html('手机号格式错误!');
                    $(oUser).focus();
                    return false;
                }
                var password = $.trim($(oPassword).val());
                if (password == '') {
                    // console.log('请输入密码');
                    /*xui.prompt({
                     time: 2000,
                     icon: 'warning',
                     msg : '请输入密码'
                     });*/
                    $('#reg_prompt').html('请输入密码!');
                    $(oPassword).focus();
                    return;
                }
                if (password.toString().length < 8 || password.toString().length > 20) {
                    // console.log('密码需6-16位');
                    $('#reg_prompt').html('密码需8-20个字符!');
                    $(oPassword).focus();
                    return;
                }

                var vCode = $.trim($(oVCode).val());
                if (vCode == '') {
                    // console.log('请输入图形验证码');
                    /*xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请输入图形验证码'
                    });*/
                    $('#reg_prompt').html('请输入图形验证码!');
                    $(oVCode).focus();
                    return;
                }

                var mCode = $.trim($(oMCode).val());
                if (mCode == '') {
                    // console.log('请输入手机验证码');
                    /*xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请输入手机验证码'
                    });*/
                    $('#reg_prompt').html('请输入手机验证码!');
                    $(oMCode).focus();
                    return;
                }

                // if ($('#reg-agreement').find("input").is('.choose')) {
                //     // console.log('请输入图形验证码');
                // }else {
                //     /*xui.prompt({
                //         time: 2000,
                //         icon: 'warning',
                //         msg : '请勾选我已阅读_协议'
                //     });*/
                //     $('#reg_prompt').html('请勾选我已阅读_协议!');
                //     return false;
                // }
                /*xui.prompt({
                    time: 10000,
                    icon: 'loading',
                    msg : '正在注册，请稍候...'
                });*/
                $('#reg_prompt').html('正在注册，请稍候...');

                // 邀请码
                // var iCode = $('#i_code').val() || '';
                $.ajax({
                    url     : '/feapi/users/regV3',
                    dataType: 'json',
                    type    : 'POST',
                    data    : {
                        formtoken: regToken,
                        mobile   : username,
                        password : password,
                        smscode  : mCode,
                        // uuid: iCode
                    },
                    success : function (result) {
                        // console.log(result);
                        if (result.regResultCode === '200000') {
                            /*xui.prompt({
                                time: 2000,
                                icon: 'success',
                                msg : result.info
                            });*/
                            main.init();
                            $('#reg_prompt').html(result.info);
                            $("body").removeClass('balance');
                            $('#floater').removeClass('show').addClass('hide2');
                            setTimeout(function () {
                                $('#floater').hide().removeClass('hide2');
                            }, 500);
                            track.init(result.data.user);
                            // XXD_TRACK._trackEvent({
                            //     category: "webapp_partner_reg",
                            //     action  : "reg_success_webapp",
                            //     label   : "注册成功",
                            //     value   : result.data.user.userId,
                            //     custval : (window.location.href).split('?')[0]
                            // });
                            ga('send', 'event', "注册", "注册成功", window.location.href);
                            //$('#to_login').trigger('click');
                            // setTimeout(function () {
                            //     window.location.href = "//www.xinxindai.com/m/#!/static/html/account/account.html";
                            // }, 2500);
                        } else {
                            /*xui.prompt({
                                time: 2000,
                                icon: 'error',
                                msg : result.info
                            });*/
                            $('#reg_prompt').html(result.info);
                        }

                    },
                    error   : function () {
                        // console.log('ajax 出错')
                        /*xui.prompt({
                            time: 2000,
                            icon: 'error',
                            msg : '请检查您的网络！'
                        });*/
                        $('#reg_prompt').html('请检查您的网络!');
                    }
                });
            }

            function openReg() {
                console.log($frame.attr('id') + 'open');
            }

            function closeReg() {
                console.log($frame.attr('id') + 'close');
                $frame.remove();
            }

            function showReg() {
                console.log($frame.attr('id') + 'show');
                $frame.show();
            }

            function hideReg() {
                console.log($frame.attr('id') + 'hide');
                $frame.hide();
            }
        }
    };
    //实例1
    xui.Login({
        frame  : '#login3'
        // success: function (result) {
        //     alert('只判断' + result.info)
        // }
    });

    xui.register({
        frame   : '#reg1',
        user    : '#aaa1',
        password: '#bbb1',
        vCode   : '#ccc1',
        vCodeImg: '#ddd1',
        mCode   : '#eee1',
        mCodeBtn: '#fff1',
        submit  : '#ggg1'
    });

    xui.register({
        frame   : '#reg2',
        user    : '#mobile-no',
        password: '#password',
        vCode   : '#img-code',
        vCodeImg: '#randImage',
        mCode   : '#rand-code',
        mCodeBtn: '#send-rand-code',
        submit  : '#to_submit'
    });
    var main = {
        init: function () {
            clientTime = new Date().getTime();
            userToken = window["userToken"] || main.getCookie('userToken');
            main.initJuly();
            main.eventBind();
            main.winning();
            main.initprizeID();
            xxd_utm_source = main.GetQueryString("xxd_utm_source") || '';
            if (userToken != null || '') {
                main.SumAnnualAmount();
                main.myPrize();
            }
        },
        eventBind: function () {
            //注册
            $(document).on('click', '#to_reg', function () {
                $('#login3').stop().fadeOut(0);
                $('#reg1').stop().fadeIn(0);
            });
            //登录
            $(document).on('click', '#to_login', function () {
                $('#reg1').stop().fadeOut(0);
                $('#login3').stop().fadeIn(0);
            });
            //关闭登录注册框
            $(document).on('click', '#x_floater', function () {
                $("body").removeClass('balance');
                $('#floater').removeClass('show').addClass('hide2');
                setTimeout(function () {
                    $('#floater').hide().removeClass('hide2');
                }, 500)
            });
            //所有关闭按钮
            $('.close,.c_close').click(function () {
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                return false;
            });
            //立即注册按钮
            $('.c_confirm').click(function () {
                userToken = window["userToken"] || main.getCookie('userToken');
                main.initJuly();
                main.initprizeID();
                main.myPrize();
                main.SumAnnualAmount();
            });
            //每10分钟刷新数据
            setInterval(function () {
                main.winning();
            }, 600000);
            //游戏注意事项
            $('#attention-btn').click(function () {
                main.bodyhidden();
                $('.attention').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                $('.attention >div').addClass('show');
            });
            //奖励规则与注意事项切换
            $("#tab1").click(function () {
               $(".gameRule").removeClass("hide");
               $(".gameAttention").addClass("hide");
            });
            $("#tab2").click(function () {
                $(".gameAttention").removeClass("hide");
                $(".gameRule").addClass("hide");
            });
            //底部我的奖品
            $('#myPrize-btn').click(function () {
                userToken = window["userToken"] || main.getCookie('userToken');
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (userToken == null || '') {
                    $('.toLogin').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                    $('.toLogin div').addClass('show');
                } else {
                    main.bodyhidden();
                    $('.winning-record').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                    $('.winning-record >div').addClass('show');
                    main.myPrize();
                }
            });
            //无游戏机会的时候，点击去投资页面
            $('#invest').click(function () {
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                $('body').animate({'scrollTop': $('.bottom')[0].offsetTop}, 300);
                flag = true;
            });
            //中间开始游戏按钮
            $('#startGame').click(function () {
                userToken = window["userToken"] || main.getCookie('userToken');
                $('body').animate({'scrollTop': $('.middle')[0].offsetTop}, 300);
                main.initJuly();
                main.myPrize();
                if(flag){
                    //活动未开始已结束弹框
                    if (activityStatus == -1) {
                        main.tip('活动未开始');
                        return;
                    } else if (activityStatus == 1) {
                        main.tip('活动已结束');
                        return;
                    }
                    //是否登录 return
                    if (userToken == null || '') {
                        main.showNoLogin();

                    }
                    //无机会
                    if (remainLotteryTimes == 0) {
                        $('.game-times').html('0');
                        main.bodyhidden();
                        if(Number(usedLotteryTimes) >= 6){
                            $('.noChanceEver').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                            $('.noChanceEver div').addClass('show');
                        }else {
                            $('.noChance').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                            $('.noChance div').addClass('show');
                        }
                        return;
                    }else {
                        $('.game-times').html(remainLotteryTimes);
                    }
                    // flag = false;
                    $.ajax({
                        url: '/activityCenter/activityBase/lottery',
                        contentType: "application/json",
                        dataType: "json",
                        type: "post",
                        beforeSend: function (request) {
                            request.setRequestHeader("clientId", clientId);
                            request.setRequestHeader("clientTime", clientTime);
                        },
                        data: JSON.stringify({
                            "data": {
                                "activityCode": activityCode,
                                "userToken": userToken
                            }
                        }),
                        success: function (data) {
                            if (data.data.code == 1) {
                                $('.game-times').html('0');
                                main.bodyhidden();
                                if(Number(usedLotteryTimes) >= 6){
                                    $('.noChanceEver').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                                    $('.noChanceEver div').addClass('show');
                                }else {
                                    $('.noChance').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                                    $('.noChance div').addClass('show');
                                }
                                return;
                            } else if (data.data.code == 0) {
                                $('.game-times').html(data.data.data.remainLotteryTimes);
                                for (var i = 0; i < itemPrizes.length; i++) {
                                    if (itemPrizes[i].itemId == data.data.data.prizeInfo.prizeid) {
                                        thisTime = i;
                                    }
                                }
                                main.bodyhidden();
                                $('.haveprize').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                                //$('.prize-info').html(itemPrizes[thisTime].introduce);
                                $('.prize-name').html(itemPrizes[thisTime].itmeName);
                                $('.haveprize >div').addClass('show');

                            }
                            main.myPrize();
                        },
                        error: function () {
                            main.tip('网络异常请重试');
                        }
                    });
                }
            });

            $('#closegame,.put-pocket').click(function () {
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                flag = true;
                return false;
            });
            //再来一次按钮
            $('.one-more').click(function () {
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                flag = true;
                setTimeout(function () {
                    $('#startGame').trigger("click");
                }, 500);
            });
            //无游戏机会关闭按钮
            $('#nochanceclose').click(function () {
                flag = true;
            });
            //本月无游戏机会关闭按钮
            $('#noChanceEver').click(function () {
                flag = true;
            });
            //登录弹框关闭按钮
            $('#loginClose').click(function () {
                flag = true;
            });
            //底部投资按钮点击
            $('#goldIngot').click(function () {
                userToken = window["userToken"] || main.getCookie('userToken');
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (userToken == null || '') {
                    main.showNoLogin();
                    return;
                }
                //金元宝的产品详情页面
                $.ajax({
                    url: '/m/xplan/getLatestSchemeId.do',
                    type: 'get',
                    data: {closeTerm: 12},
                    success: function (data) {
                        if (data.schemeInfo != null && data.schemeInfo != "") {
                            window.location.href = '/m/#!/static/html/plan/planDetailsV2.html?planId=' + data.schemeInfo.SCHEMEID + '&xxd_utm_source=' + xxd_utm_source;
                        }
                    },
                    error: function () {
                        main.tip('网络异常请重试！');
                    }
                });
            });
            //步步高升
            $('#stepUpward').click(function () {
                userToken = window["userToken"] || main.getCookie('userToken');
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (userToken == null || '') {
                    main.showNoLogin();
                    return;
                }
                window.location.href = '/m/#!/static/html/stepUpward/stepUpwardDetail.html'+ '?xxd_utm_source=' + xxd_utm_source;
            });
            //_
            $('#yyp').click(function () {
                userToken = window["userToken"] || main.getCookie('userToken');
                //console.log("start");
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (userToken == null || '') {
                    main.showNoLogin();
                    return;
                }
                //_的产品详情页面
                $.ajax({
                    url:'/m/yyp/index.do',
                    type:'get',
                    data:{
                        terms:12
                    },
                    success:function (data) {
                        //console.log(data);
                        if(data.resultList !=null &&data.resultList != ''){
                            //console.log("success");
                            window.location.href =  '/m/#!/static/html/yyp/yypDetails.html?yypId=' + data.resultList[0].ID + '&xxd_utm_source=' + xxd_utm_source;
                        }
                    },
                    error:function () {
                        main.tip('网络异常请重试');
                    }
                });
            });

            //webapp端的底部分享浮层
            $("#home_downloadClose").click(function () {
                $("#pop").hide();
            });
            $("#home_download").click(function () {
                window.location.href = '/m/static/html/download/app.html?model=auto';
            });
            try {
                var request = new Object();
                request = main.GetRequest();
                var isdow = request["isdow"];
                if (isdow != undefined && isdow == 'true') {
                    $("#pop").show();
                }
            } catch (e) {
            }

        },
        initJuly: function () {
            $.ajax({
                url: '/activityCenter/activityBase/initial',
                contentType: "application/json",
                dataType: "json",
                type: "get",
                beforeSend: function (request) {
                    request.setRequestHeader("clientId", clientId);
                    request.setRequestHeader("clientTime", clientTime);
                },
                data: {
                    'activityCode': activityCode,
                    'userToken': userToken
                },
                success: function (data) {
                    activityStatus = data.data.data.activityStatus;
                    remainLotteryTimes = data.data.data.remainLotteryTimes;
                    usedLotteryTimes = data.data.data.usedLotteryTimes;
                    $('.game-times').html(data.data.data.remainLotteryTimes);
                },
                error: function () {
                    main.tip('网络异常请重试');
                }
            });
            var sClientId      = 'XXD_ACTIVITY_H5_PAGE';
            var sClientTime    = new Date().getTime();
            $.ajax({
                url     : '/activityCenter/activityBase/getJoinActivityUserAmount?activityCode=' + activityCode,
                dataType: 'json',
                // async   : false,
                headers : {
                    "Accept"    : "application/json;charset=utf-8",
                    "clientId"  : sClientId,
                    "clientTime": sClientTime
                },
                data    : {},
                type    : 'GET',
                success : function (data) {
                    if(data.code == 200000){
                        var investNum = data.data.data;
                        $("#investNum").html(investNum);
                        //console.log(data.data.data);
                        if( Number(investNum) >= 1000){
                            $(".mask").addClass("hide3");
                        }else {
                            $(".mask").removeClass("hide3");
                        }
                    }
                },
                error   : function (data) {
                    main.tip('网络异常请重试');
                }
            });
            userToken = window["userToken"] || main.getCookie('userToken');
            if (userToken == null || '') {
                $('.amountData').hide();
                $('.chanceTip').hide();
                $('.myChance').hide();
                $('.peopleNum').addClass("changeTop");
                $('.middle').removeClass("bgChange");
            } else {
                $('.amountData').show();
                $('.chanceTip').show();
                $('.myChance').show();
                $('.peopleNum').removeClass("changeTop");
                $('.middle').addClass("bgChange");
            }
        },
        initprizeID: function () {
            $.ajax({
                url: '/activityCenter/activityBase/getMaretPrize',
                contentType: "application/json",
                dataType: "json",
                type: "get",
                beforeSend: function (request) {
                    request.setRequestHeader("clientId", clientId);
                    request.setRequestHeader("clientTime", clientTime);
                },
                data: {
                    'activityCode': activityCode
                },
                success: function (data) {
                    if (data.data.data) {
                        for (var j = 0; j < itemPrizes.length; j++) {
                            for (var i = 0; i < data.data.data.length; i++) {
                                var item = data.data.data[i];
                                if (item.prizename == itemPrizes[j].itemPrize) {
                                    itemPrizes[j].itemId = item.id;
                                }
                            }
                        }
                    }
                }
            });
        },
        GetRequest: function () {
            var url = location.search; //获取url中"?"符后的字串
            var theRequest = new Object();
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split("&");
                for (var i = 0; i < strs.length; i++) {
                    theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
                }
            }
            return theRequest;
        },
        GetQueryString: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return decodeURI(r[2]);
            return null;
        },
        bodyhidden: function () {
            $('body').addClass('bodyhidden');
        },
        getCookie: function (name) {
            var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
            if (arr = document.cookie.match(reg))
                return unescape(arr[2]);
            else
                return null;
        },
        SumAnnualAmount: function () {
            $.ajax({
                url: '/activityCenter/activityBase/getUserSumAnnualAmount',
                contentType: "application/json",
                dataType: "json",
                type: "get",
                beforeSend: function (request) {
                    request.setRequestHeader("clientId", clientId);
                    request.setRequestHeader("clientTime", clientTime);
                },
                data: {
                    'activityCode': activityCode,
                    'userToken': userToken
                },
                success: function (data) {
                    var data = (data.data.data.annualAmount).toFixed(2);
                    $('.sumAmount').html(data);
                }
            });
        },
        winning: function () {
            $.ajax({
                url: '/activityCenter/activityBase/getLatest15AwardsList',
                contentType: "application/json",
                dataType: "json",
                type: "get",
                beforeSend: function (request) {
                    request.setRequestHeader("clientId", clientId);
                    request.setRequestHeader("clientTime", clientTime);
                },
                data: {
                    'activityCode': activityCode
                },
                success: function (data) {
                    if (data.data.data) {
                        if (data.data.data.list.length > 0) {
                            $.each(data.data.data.list, function (i, item) {
                                var time = item.addtime.substring(11, 19);
                                $('.winData ul').append("<li><span>" + item.username + "</span><span>" + time + "</span><span>" + item.prizename + "</span></li>");
                            });
                            if($(".winData ul li").length >= 7){
                                $('.myscroll').vTicker({
                                    speed: 500,
                                    pause: 2000,
                                    animation: 'fade',
                                    mousePause: false,
                                    showItems: 6
                                });
                            }
                        }
                    }
                },
                error: function () {
                    main.tip('网络异常请重试!');
                }
            })
        },
        myPrize: function () {
            $.ajax({
                url: '/activityCenter/activityBase/getMyAwardsList',
                contentType: "application/json",
                dataType: "json",
                type: "get",
                beforeSend: function (request) {
                    request.setRequestHeader("clientId", clientId);
                    request.setRequestHeader("clientTime", clientTime);
                },
                data: {
                    'activityCode': activityCode,
                    'userToken': userToken,
                    'currentPage': pageid,
                    'pageSize': 6
                },
                success: function (data) {
                    if (data.data.code == 0) {
                        if (data.data.data) {
                            pageall = data.data.data.pages;
                            pageid = data.data.data.pageNum;
                            $('.winning-con ul').empty();
                            if (data.data.data.list == 0) {
                                $('.winning-con ul').html('').append('<li>您还未获得奖品，赶快投资参与活动吧~!</li>');
                                $('.winning-con ul li').addClass('noprizetip');
                                $('.page').fadeOut(0);
                                return;
                            }
                            $.each(data.data.data.list, function (i, item) {
                                $('.winning-con ul li').removeClass('noprizetip');
                                $('.page').fadeIn(0);
                                var time = new Date(item.addtime);
                                var month = (time.getMonth() + 1) < 10 ? ("0" + (time.getMonth() + 1)) : (time.getMonth() + 1);
                                var day = (time.getDate()) < 10 ? ("0" + time.getDate()) : time.getDate();
                                var hours = (time.getHours()) < 10 ? ("0" + time.getHours()) : time.getHours();
                                var getMinutes = (time.getMinutes()) < 10 ? ("0" + time.getMinutes()) : time.getMinutes();
                                var getSeconds = (time.getSeconds()) < 10 ? ("0" + time.getSeconds()) : time.getSeconds();
                                $('.winning-con ul').append('<li><span>' + hours + ':' + getMinutes + ':' + getSeconds + '</span>抽中了<span>' + item.prizename + '</span></li>');
                            });
                        } else {
                            $('.winning-con ul').html('').append('<li>您还未获得奖品，赶快投资参与活动吧~!</li>');
                            $('.winning-con ul li').addClass('noprizetip');
                            $('.page').fadeOut(0);
                            return;
                        }
                    }
                },
                error: function () {
                    main.tip('网络异常请重试！');
                }
            });
        },
        tip: function (msg) {
            $('.tip').html(msg).fadeIn(200, function () {
                setTimeout(function () {
                    $('.tip').fadeOut(200);
                }, 3000);
            });
        },
        getMyDate: function (str) {
            var oDate = new Date(str),
                oYear = oDate.getFullYear(),
                oMonth = oDate.getMonth()+1,
                oDay = oDate.getDate(),
                oTime = oYear +'-'+ main.getzf(oMonth) +'-'+ main.getzf(oDay);
            return oTime;
        },
        //补0操作
        getzf: function (num){
                if(parseInt(num) < 10){
                    num = '0'+num;
                }
                return num;
        },
        pagetip: function (msg) {
            $('.pagetip').html(msg).fadeIn(200, function () {
                setTimeout(function () {
                    $('.pagetip').fadeOut(200);
                }, 2000);
            });
        },
        // 显示未登录提示框
         showNoLogin:function() {
        $('.toLogin').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
        $('.toLogin div').addClass('show');
        },
        // 隐藏未登录提示框
        hideNoLogin:function() {
        $('.toLogin').hide();
        }
    };
    main.init();
    return main;
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});