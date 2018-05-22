require(['base', 'requirejs', 'json', 'juicer', 'trackBase', 'IScroll', 'md5'], function ($, requirejs, json, juicer, track, IScroll, md5) {

    var intervalArray = [];
// 获取网址传参
    function getDmpUrlParam(item) {
        var pattern = new RegExp("[?&]" + item + "\=([^&]+)", "g");
        var matcher = pattern.exec(location.href);
        var items   = null;
        if (null != matcher) {
            try {
                items = decodeURIComponent(decodeURIComponent(matcher[1]));
            } catch (e) {
                try {
                    items = decodeURIComponent(matcher[1]);
                } catch (e) {
                    items = matcher[1];
                }
            }
        }
        return items;
    }

    $(function () {
        $(document).on('touchmove', '.mui-fix-phone-scroll', function () {
            return false;
        });

        // 注册协议自定义滚动条
        agreement_box = new IScroll('#agreement_box', {
            bounce       : false,
            scrollbars   : true,
            mouseWheel   : true,
            click        : true,
            bindToWrapper: true
        });

        /*
         $(document).on('click', '#to_reg', function () {
         $('#login3').stop().fadeOut(250);
         setTimeout(function () {
         $('#reg1').stop().fadeIn();
         }, 300);

         });
         $(document).on('click', '#to_login', function () {
         $('#reg1').stop().fadeOut(250);
         setTimeout(function () {
         $('#login3').stop().fadeIn();
         }, 300);
         });

         $(document).on('click', '#J_login', function () {
         if (isLogin()) {
         window.location.href = '/m';
         } else {
         $('#floater').show(0).addClass('show');
         setTimeout(function () {
         $('#middle').hide(0);
         }, 500);
         }

         });


         $(document).on('click', '#x_floater', function () {
         $('#middle').show(0);
         $('#floater').removeClass('show').addClass('hide2');
         setTimeout(function () {
         $('#floater').hide(0).removeClass('hide2');
         }, 500)
         });
         */

        // 注册协议
        agreement.init();

        // 下载app按钮

        // $("#mashang").click(function () {
        //     window.location.href = 'http://download-cdn.xinxindai.com/app-release_201705171419.apk';
        // });
        //下载app
        var iosDownLoadUrl='http://download-cdn.xinxindai.com/app-release_201705171419.apk';
        var androidDownLoadUrl='http://download-cdn.xinxindai.com/app-release_201705171419.apk';
        $("#mashang").click(function () {
            if(isWeixin()){
                $("#disabled1").show();
                $("#disabled2").show();
            }else{
                if (iosDownLoadUrl == '' || androidDownLoadUrl == '') {
                    alert("抱歉，APP的下载地址为空，请刷新页面重新尝试");
                    return;
                }
                if (ios()) {
                    window.location.href = iosDownLoadUrl;
                } else {
                    window.location.href = androidDownLoadUrl;
                }
            }
        });
        //遮罩start
        $("#disabled1").click(function () {
            $("#disabled1").hide();
            $("#disabled2").hide();
        });
        $("#disabled2").click(function () {
            $("#disabled1").hide();
            $("#disabled2").hide();
        });
        //判断是否微信打开;
        openWeChat();
        function openWeChat() {
            var androidType = GetQueryString("androidType");
            if ('yilabao' == androidType) {
                androidDownLoadUrl = 'http://download-cdn.xinxindai.com/app-release_201705171419.apk';
            }
            if (isWeixin()) {
                $("#disabled1").show();
                $("#disabled2").show();
            } else {
                var model = GetQueryString("model");
                if (model == 'auto') {
                    download();
                }
            }
        }
        function isWeixin() {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        }
        function ios() {
            var ua = navigator.userAgent;
            var ipad = ua.match(/(iPad).*OS\s([\d_]+)/);
            var ipod = ua.match(/(iPod)(.*OS\s([\d_]+))?/);
            var iphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/);
            if (ipad || iphone || ipod) {
                return true;
            } else {
                return false;
            }
        }
        //获取地址栏url参数
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return decodeURI(r[2]);
            return null;
        }
        //遮罩end
        /*
         $(document).on('click', '.open_app', function () {
         var thisUrl = $(this).data('url');
         openApp(thisUrl);
         });
         $(document).on('click', '.down_app', function () {
         var dmp_app_source = getDmpUrlParam("dmp_app_source");
         if (dmp_app_source) {
         window.location.href = 'http://' + window.location.host + '/m/static/html/download/app.html?model=auto&dmp_app_source=' + dmp_app_source;

         } else {
         window.location.href = 'http://' + window.location.host + '/m/static/html/download/app.html?model=auto';
         }
         });
         */

        // 打开app
        /*function openApp(thisUrl) {
         var schemeUrl = thisUrl;
         if (navigator.userAgent.match(/(iPhone|iPod|iPad)/i)) {
         var loadDateTime = new Date();
         window.setTimeout(function () {
         var timeOutDateTime = new Date();
         if (timeOutDateTime - loadDateTime < 5000) {
         showDownApp()
         } else {
         alert('无法打开');
         }
         }, 100);
         window.location.href = schemeUrl;
         } else if (navigator.userAgent.match(/android/i)) {


         var loadDateTime2 = new Date();
         window.setTimeout(function () {
         var timeOutDateTime2 = new Date();
         if (timeOutDateTime2 - loadDateTime2 < 5000) {
         showDownApp()
         } else {
         alert('无法打开');
         }
         }, 100);
         window.location.href = schemeUrl;

         }
         }*/

        // 下载app
        var thisTime, countdownTime;

        function showDownApp() {

            if ($('#toapp').length > 0) {
                $('#toapp').addClass('show').fadeIn(200);
            } else {
                $('<div id="toapp" style="display: none" class="to-app show"><div><div><b id="chart1" class="mc-chart" data-sum="360"><i><em></em></i><i><em></em></i><b id="countdown">8</b></b><h1>请先下载客户端</h1><p>拥抱梦想 亦懂得积累</p><ul><li><button class="x_downapp">就看网页版</button></li><li><button class="down_app">下载客户端</button></li></ul></div></div></div>').appendTo('body').stop().fadeIn(200);
            }

            var countdownNb = 8;
            countdownTime   = setInterval(function () {
                countdownNb--;
                if (countdownNb >= 0) {
                    $('#countdown').text(countdownNb)
                } else {
                    clearInterval(countdownTime);
                }

            }, 1000);

            thisTime = setTimeout(function () {
                closeDownApp()
            }, 8000)

        }

        $(document).on('click', '.x_downapp', function () {
            closeDownApp();
        });
        function closeDownApp() {
            clearTimeout(thisTime);
            clearInterval(countdownTime);
            $('#toapp').removeClass('show').fadeOut(200, function () {
                $('#toapp').remove();
            });
        }

        //实例1
        /*
         xui.Login({
         frame: '#login3',
         // success: function (result) {
         //     alert('只判断' + result.msg)
         // }
         });
         */
        /*
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
         */
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

    });

    // $("#randImage").click(function () {
    //     refVerifyCode();
    //
    // });
    // $('#send-rand-code').click(function () {
    //     sendRandCode();
    // });
    // $('#reg').click(function () {
    //     storeReg();
    // });

    /*function toTop() {
     $('html, body').animate({scrollTop: 0}, 350);
     }*/

// 注册协议
    var agreement = {
        init: function () {
            $('#reg-agreement').click(function () {
                agreement.open();
            });
            $('#agree-back').click(function () {
                agreement.hide();
            });
        },
        open: function () {
            $('#agreement').fadeIn();
            agreement_box.refresh();
        },
        hide: function () {
            $('#agreement').fadeOut();
        }
    };

    /*function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg))

            return (arr[2]);
        else
            return null;
    }*/

    /*function isLogin() {
        var result = false;
        $.ajax({
            url     : '/feapi/users/loginInfo?userToken=' + getCookie('userToken'),
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
    }

    $.ajax({
        url     : '/feapi/users/loginInfo?userToken=' + getCookie('userToken'),
        dataType: 'json',
        async   : false,
        data    : {},
        type    : 'GET',
        success : function (str) {
            if (str.data.status.code === 200) {
                //result = true;
                track.init(str.data);
            } else {
                track.init();
            }
        },
        error   : function (str) {
            track.init();
        }
    });


    function Redirect() {
        window.location.href = "/m/#!/static/html/account/account.html";
    }

    function clearTimar(value) {
        return value.replace(/\s+/g, '');
    }

    /!**清空定时器，隐藏语音验证码*!/
    function clearIntervalArray() {
        $.each(intervalArray, function (index, value) {
            clearInterval(value);
        });
        intervalArray.length = 0;
    }*/


    /*
     * 模块：提示框
     * 模块类型：P(独立插件)
     * 精度：*
     * 分级：*
     * 适配：平板 | 移动
     * 兼容：
     * 版本：v0.0.1
     * 备注：
     * dim (true 开 | false 关)；是否开启暗层（默认关闭）
     * pos (tl 上左 | tc 上中 | tr 上右 | cl 中左 |cc 中中 | cr 中右 | bl 下左 | bc 下中 | br 下右)；提示框位置（默认中中）
     * time (number 显示时间) 设计一个数字（默认3秒）
     * icon (success 成功 | warning 警告 | error 错误 | loading 加载中) 非必需默认无图标
     * msg (提示内容)
     *
     * 用法：
     * xui.prompt({msg:'要提示的内容'})
     * 还有一个bug，当开启暗层时，后面的提示框一律不再开启暗层，但是，如果前面开启暗层的提示框关闭后，后面原本需要暗层的提示框会没有暗层，有解决方案，所有提示框共用一个容器，共用一个暗层，待有时间更新！
     */
    var xui = {
        prompt  : function (conf) {
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
        },
        /*Login   : function (conf) {
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
                                        console.log('有了啊？')
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
                    var onFrame = $(this).data('frame');
                    if (onFrame === oFrame) {
                        ux.showLogin(onFrame);
                    }
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
                        xui.prompt({
                            time: 1000,
                            icon: 'warning',
                            msg : '请输入手机号！'
                        });
                        $user.focus();
                        return false
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
                        xui.prompt({
                            time: 1000,
                            icon: 'warning',
                            msg : '请输入密码！'
                        });
                        $password.focus();
                        return false;
                    }
                    var vCode = $.trim($vCode.val());
                    if (vCode == '') {
                        // console.log('! 请输入验证码');
                        xui.prompt({
                            time: 1000,
                            icon: 'warning',
                            msg : '请输入验证码！'
                        });
                        $vCode.focus();
                        return false;
                    }

                    xui.prompt({
                        time: 10000,
                        icon: 'loading',
                        msg : '正在登录，请稍候...'
                    });

                    $.ajax({
                        url     : dataUrl.login,
                        data    : {
                            formtoken: loginToken,
                            username : username,
                            password : password,
                            imgcode  : vCode,
                            invite_id: getDmpUrlParam('invite_id')
                        },
                        dataType: 'json',
                        type    : 'POST',
                        success : function (result) {
                            try {

                                if (conf.callback && typeof(conf.callback) == "function") {
                                    conf.callback(result);
                                } else {
                                    if (result.code === '200000') {
                                        // console.log(result);
                                        // console.log('v ' + result.msg);
                                        xui.prompt({
                                            time: 2000,
                                            icon: 'success',
                                            msg : '您已登录成功！'
                                        });
                                        track.init(result.data.user);
                                        XXD_TRACK._trackEvent({
                                            category: "webapp_partner_reg",
                                            action  : "login_success_webapp",
                                            label   : "登录成功",
                                            value   : result.data.user.userId,
                                            custval : (window.location.href).split('?')[0]
                                        });
                                        ga('send', 'event', '登录', "登录成功", window.location.href);
                                        setTimeout(function () {
                                            window.location.href = '/m';
                                        }, 2500);

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
                                        // console.log('x ' + result.msg);
                                        xui.prompt({
                                            time: 1000,
                                            icon: 'error',
                                            msg : result.msg
                                        });
                                    }
                                }
                            } catch (e) {
                            }

                        },
                        error   : function () {
                            // console.log('ajax 出错')
                            xui.prompt({
                                time: 2000,
                                icon: 'error',
                                msg : '请检查您的网络！'
                            });
                        }
                    });
                },
                // 获取验证码
                getVCode : function ($onFrame) {
                    $onFrame.find(oVCodeImg).css('background-image', 'url(' + dataUrl.vCode + '?formtoken=' + loginToken + '&v=' + Math.random() + ')');
                }
            };

        },*/
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

            // 获取token
            /*$.ajax({
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
                    xui.prompt({
                        time: 2000,
                        icon: 'error',
                        msg : '请检查您的网络！'
                    });
                }
            });*/

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
                /*$(document).on('click', oVCodeImg, function () {
                    getVCode();
                });*/

                // 发送手机验证码
                $(document).on('click', oMCodeBtn, function () {
                    getMCode();
                });

                // 提交注册
                $(document).on('click', oSubmit, function () {
                    toSubmit();
                });
            }

            /*function getVCode() {
                $(oVCodeImg).attr("src", "/feapi/randCode/createVerifyCode?formtoken=" + regToken + '&v=' + Math.random());
            }*/

            function getMCode() {
                var username = $.trim($(oUser).val());

                if (username == '') {
                    // console.log('请输入注册手机号');
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请输入注册手机号'
                    });
                    $(oUser).focus();
                    return false;
                }

                var ifUsername = validateMobile(username);
                if (ifUsername === false) {
                    // console.log('手机号格式错误');
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '手机号格式错误'
                    });
                    $(oUser).focus();
                    return false;
                }

                $.ajax({
                    url     : '/xxd_hedai/Home/Api/getRegisterSmsCode',
                    dataType: 'json',
                    type    : 'POST',
                    data    : {
                        key   : $.md5('http://118.178.92.72/Home/Api'),
                        mobile: username
                    },
                    success : function (result) {

                        if (result.code == '0') {
                            var tempTime = 60;
                            $(oMCode).addClass('sending').html('已发送');
                            intervalArray.push(setInterval(function () {
                                tempTime--;
                                if (tempTime < 0) {
                                    $(oMCodeBtn).removeClass('sending').html('发送验证码');
                                    // 清除定时器
                                    clearIntervalArray();
                                } else {
                                    //获取短信验证码超过30秒，还未进行验证，显示获取语音验证码按钮
                                    $(oMCodeBtn).addClass('sending').html(tempTime + '秒后重新发送');
                                }
                            }, 1000));

                        } else{
                            xui.prompt({
                                time: 2000,
                                icon: 'warning',
                                msg : result.msg
                            });
                        }

                    },
                    error   : function () {
                        xui.prompt({
                            time: 2000,
                            icon: 'error',
                            msg : '验证码发送失败，请联系客服！'
                        });
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
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请输入注册手机号'
                    });
                    $(oUser).focus();
                    return;
                }
                var ifUsername = validateMobile(username);
                if (ifUsername === false) {
                    // console.log('手机号格式错误');
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '手机号格式错误'
                    });
                    $(oUser).focus();
                    return false;
                }

                /*var vCode = $.trim($(oVCode).val());
                 if (vCode == '') {
                 // console.log('请输入图形验证码');
                 xui.prompt({
                 time: 2000,
                 icon: 'warning',
                 msg : '请输入图形验证码'
                 });
                 $(oVCode).focus();
                 return;
                 }*/

                var mCode = $.trim($(oMCode).val());
                if (mCode == '') {
                    // console.log('请输入手机验证码');
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请输入手机验证码'
                    });
                    $(oMCode).focus();
                    return;
                }

                var password = $.trim($(oPassword).val());
                if (password == '') {
                    // console.log('请输入密码');
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请输入密码'
                    });
                    $(oPassword).focus();
                    return;
                }

                xui.prompt({
                    time: 10000,
                    icon: 'loading',
                    msg : '正在注册，请稍候...'
                });

                $.ajax({
                    url     : '/xxd_hedai/Home/Api/register',
                    dataType: 'json',
                    type    : 'POST',
                    data    : {
                        mobile   : username,
                        password : $.md5(password),
                        sms_code : mCode,
                        invite_id: getDmpUrlParam('invite_id'),
                        key   : $.md5('http://118.178.92.72/Home/Api')
                    },
                    success : function (result) {
                        if (result.code == '0') {
                            xui.prompt({
                                time: 2000,
                                icon: 'success',
                                msg : result.msg
                            });
                            /*setTimeout(function () {
                                window.location.href = "//www.xinxindai.com/m/#!/static/html/account/account.html";
                            }, 2500);*/
                        } else {
                            xui.prompt({
                                time: 2000,
                                icon: 'error',
                                msg : result.msg
                            });
                        }

                    },
                    error   : function () {
                        // console.log('ajax 出错')
                        xui.prompt({
                            time: 2000,
                            icon: 'error',
                            msg : '请检查您的网络！'
                        });
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

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});