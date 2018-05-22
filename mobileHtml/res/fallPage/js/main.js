require(['base', "requirejs", 'json', "juicer", "trackBase"], function ($, register, login, requirejs,track) {

    var intervalArray = [];

    // var _token = "";
    //
    // function callToken(callback) {
    //     if (_token) {
    //         if (callback) callback(_token);
    //     } else {
    //         $.ajax({
    //             url: "/feapi/users/formToken",
    //             contentType: "application/json",
    //             dataType: "json",
    //             type: "get",
    //             success: function (res) {
    //                 _token = res.token;
    //                 if (callback) callback(_token);
    //             },
    //             error: function (data) {
    //                 $.log(data);
    //             }
    //         });
    //     }
    // }
    //
    // $.ajax({
    //     url: '/feapi/users/formToken',
    //     dataType: 'json',
    //     type: 'GET',
    //     data: {},
    //     success: function (result) {
    //         _token = result.token;
    //         $("#randImage").attr("src", "/feapi/randCode/createVerifyCode?formtoken=" + _token);
    //     }
    // });
    //
    // $("#J_login").on("click", function (ev) {
    //     if(isLogin()){
    //         window.location.href = '/m';
    //     }else{
    //         callToken(function (token) {
    //             login.show({
    //                 token: token,
    //                 register: register,
    //                 callback: function (res) {
    //                     xui.prompt({
    //                         time: 2000,
    //                         icon: 'success',
    //                         msg: '您已登录成功！'
    //                     });
    //                     setTimeout(function () {
    //                         window.location.href = '/m';
    //                     },2500);
    //
    //                 }
    //             });
    //         });
    //         ev && event.preventDefault();
    //     }
    //
    // });

    // $("#J_register").on("click", function (ev) {
    //     callToken(function (token) {
    //         register.show({
    //             token: token,
    //             login: login,
    //             callback: function (res) {
    //                 xui.prompt({
    //                     time: 2000,
    //                     icon: 'success',
    //                     msg: '您已注册成功！'
    //                 });
    //                 setTimeout(function () {
    //                     window.location.href = '/m';
    //                 },2500);
    //             }
    //         });
    //     });
    //     ev && event.preventDefault();
    // });

    $(function () {
        $(document).on('touchmove', '.mui-fix-phone-scroll', function () {
            return false;
        });

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
            if(isLogin()){
                window.location.href = '/m';
            }else{
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


        // 注册协议
        agreement.init();

        // 关于新新贷
        bulletin.init();

        // 七天大胜
        get7DaysInfo.init();

        // 日日盈
        getFundInfo.init();

        // 新元宝
        getPlan.init();

        // 月进斗金
        getMonthFinance.init();

        // 马上赚钱
        $("#mashang").click(function () {
            if (!isLogin()) {
                xui.prompt({
                    time: 2000,
                    icon: 'warning',
                    msg : '请先注册或者登录'
                });
                setTimeout(function () {
                    toTop();
                }, 2500);
            } else {
                xui.prompt({
                    time: 2000,
                    icon: 'warning',
                    msg : '请选择上面的产品'
                });
            }
        });

        $(document).on('click', '.open_app', function () {
            var thisUrl = $(this).data('url');
            openApp(thisUrl);
        });
        $(document).on('click', '.down_app', function () {
            var dmp_app_source= getDmpUrlParam("dmp_app_source");
            if(dmp_app_source){
                window.location.href = 'http://' + window.location.host + '/m/static/html/download/app.html?model=auto&dmp_app_source='+dmp_app_source;

            }else{
                window.location.href = 'http://' + window.location.host + '/m/static/html/download/app.html?model=auto';
            }
        });
        function getDmpUrlParam(item){
            var pattern = new RegExp("[?&]"+item+"\=([^&]+)", "g");
            var matcher = pattern.exec(location.href);
            var items = null;
            if(null != matcher){
                try{
                    items = decodeURIComponent(decodeURIComponent(matcher[1]));
                }catch(e){
                    try{
                        items = decodeURIComponent(matcher[1]);
                    }catch(e){
                        items = matcher[1];
                    }
                }
            }
            // console.log(items);
            return items;
        }
        function openApp(thisUrl) {
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
        }

        var thisTime,countdownTime;
        function showDownApp() {

            if($('#toapp').length > 0){
                $('#toapp').addClass('show').fadeIn(200);
            }else{
                $('<div id="toapp" style="display: none" class="to-app show"><div><div><b id="chart1" class="mc-chart" data-sum="360"><i><em></em></i><i><em></em></i><b id="countdown">8</b></b><h1>请先下载客户端</h1><p>拥抱梦想 亦懂得积累</p><ul><li><button class="x_downapp">就看网页版</button></li><li><button class="down_app">下载客户端</button></li></ul></div></div></div>').appendTo('body').stop().fadeIn(200);
            }

            var countdownNb=8;
            countdownTime = setInterval(function () {
                countdownNb--;
                if(countdownNb >= 0){
                    $('#countdown').text(countdownNb)
                }else{
                    clearInterval(countdownTime);
                }

            },1000);

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
            $('#toapp').removeClass('show').fadeOut(200,function () {
                $('#toapp').remove();
            });
        }

        //实例1
        xui.Login({
            frame  : '#login3',
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

    function toTop() {
        $('html, body').animate({scrollTop: 0}, 350);
    }

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

    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg))

            return (arr[2]);
        else
            return null;
    }

    function isLogin() {
        var result = false;
        $.ajax({
            url     : '/feapi/users/loginInfo?userToken=' + getCookie('userToken'),
            dataType: 'json',
            async   : false,
            data    : {},
            type    : 'GET',
            success : function (str) {
                console.log(str);
                if(str.code == 200000){
                    if (str.data.status.code == 200) {
                        //result = true;
                        track.init(str.data);
                    }else {
                        track.init();
                    }
                }else {
                    track.init();
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
            // console.log(str);
            if(str.code == 200000){
                if (str.data.status.code == 200) {
                    //result = true;
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

// 关于新新贷->运营数据
    var bulletin = {
        init: function () {
            bulletin.data();
        },
        data: function () {
            $.ajax({
                url        : "/biz/bulletin/operationData",
                contentType: "application/json",
                dataType   : "json",
                type       : "get",
                beforeSend : function (request) {
                    request.setRequestHeader("s", "www");
                    request.setRequestHeader("clientId", "001");
                    request.setRequestHeader("clientTime", "001");
                },
                success    : function (msg) {
                    if (msg && msg.code == 200000) {
                        for (var i = 0; i < msg.data.items.length; i++) {
                            if (msg.data.items[i].code == 'TOTAL_REGISTER_USER') {
                                var TOTAL_REGISTER_USER = msg.data.items[i].nvalue;
                                TOTAL_REGISTER_USER     = TOTAL_REGISTER_USER / 10000;
                                $('#total_register_user').html(TOTAL_REGISTER_USER.toFixed(2));
                            }
                            if (msg.data.items[i].code == 'VENTURE_BALANCE') {
                                var VENTURE_BALANCE = msg.data.items[i].nvalue;
                                VENTURE_BALANCE     = VENTURE_BALANCE / 10000;
                                $('#venture_balance').html(VENTURE_BALANCE.toFixed(2));
                            }
                            if (msg.data.items[i].code == 'TOTAL_TRADE') {
                                var TOTAL_TRADE = msg.data.items[i].nvalue;
                                TOTAL_TRADE     = TOTAL_TRADE / 100000000;
                                $('#total_trade').html(TOTAL_TRADE.toFixed(2));
                            }
                            if (msg.data.items[i].code == 'TOTAL_INCOME') {
                                var TOTAL_INCOME = msg.data.items[i].nvalue;
                                TOTAL_INCOME     = TOTAL_INCOME / 100000000;
                                $("#total_income").html(TOTAL_INCOME.toFixed(2));
                            }
                        }
                    }
                },
                error      : function (data) {
                    console.error(data.code);
                }
            });
        }
    };

// 七天大胜
    var get7DaysInfo = {
        init: function () {
            get7DaysInfo.data();
            $("#7dayBuy").click(function () {
                if (!isLogin()) {
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请先注册或者登录'
                    });
                    setTimeout(function () {
                        toTop();
                    }, 2500);
                    return;
                }
                window.location.href = '/m/#!/static/html/newHand/sevenDaysDetail.html?path=newHand';
            });
        },
        data: function () {
            $.ajax({
                url     : '/m/sevenDays/selectSevenDaysInfo.do',
                data    : {},
                dataType: 'json',
                async   : false,
                success : function (data) {
                    if (data.sevenDays != null) {
                        var apr    = parseFloat(data.sevenDays.apr).toFixed(2);
                        var values = new String(apr).split('.');

                        var floatApr = parseFloat(data.sevenDays.floatApr).toFixed(2);
                        var values1  = new String(floatApr).split('.');
                        $("#7dayApr").html(parseInt(values[0]) + parseInt(values1[0]));

                        var lowestTender = data.sevenDays.lowestTender;
                        $("#7daylowestTender").html(lowestTender);

                        var closeTerm = data.sevenDays.closeTerm;
                        $("#7daycloseTerm").html(closeTerm);
                    }
                }
            });
        }
    };

// 月进斗金
    var getMonthFinance = {
        init: function () {
            getMonthFinance.data();

            $("#monthBuy").click(function () {
                if (!isLogin()) {
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请先注册或者登录'
                    });
                    setTimeout(function () {
                        toTop();
                    }, 2500);
                    return;
                }
                window.location.href = '/m/#!/static/html/monthFinance/monthFinanceDetails.html';
            });
        },
        data: function () {
            $.ajax({
                url      : '/m/monthFinance/getProudctBaseInfo.do',
                data     : {},
                dataType : 'json',
                indicator: true,
                success  : function (result) {
                    if (result.resultCode == '0' || result.resultCode == 0) {
                        var apr    = result.data.apr;
                        var values = new String(apr).split('.');
                        $("#monthApr").html(values[0]);

                        var closeTerm = result.data.closeTerm;
                        $("#monthcloseTerm").html(closeTerm);

                        var monthlowestTender = result.data.lowestTender;
                        $("#monthlowestTender").html(monthlowestTender);

                    }
                }
            })
        }

    };

// 日日盈
    var getFundInfo = {
        init: function () {
            getFundInfo.data();

            $("#fundBuy").click(function () {
                if (!isLogin()) {
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请先注册或者登录'
                    });
                    setTimeout(function () {
                        toTop();
                    }, 2500);
                    return;
                }
                window.location.href = '/m/#!/static/html/fund/fundUnInvested.html?path=fund';
            });
        },
        data: function () {
            $.ajax({
                url     : '/m/fund/selectFundInfo.do',
                data    : {},
                dataType: 'json',
                async   : false,
                success : function (data) {
                    if (data != null && data.fundApr != null) {
                        var apr      = parseFloat(data.fundApr.apr).toFixed(2);
                        var values   = new String(apr).split('.');
                        var floatApr = parseFloat(data.fundApr.floatApr).toFixed(2);
                        var values1  = new String(floatApr).split('.');
                        $("#fundApr").html(parseInt(values[0]) + parseInt(values1[0]));

                        var lowestTender = data.fund.lowestTender;
                        $("#fundlowestTender").html(lowestTender);
                    }
                }
            });
        }
    };

// 新元宝
    var getPlan = {
        init: function () {
            getPlan.data();

            $("#planBuy").click(function () {
                if (!isLogin()) {
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请先注册或者登录'
                    });
                    setTimeout(function () {
                        toTop();
                    }, 2500);
                    return;
                }
                var planid           = $("#planBuy").attr("planid");
                window.location.href = '/m/#!/static/html/plan/planDetailsV2.html?path=plan&planId=' + planid;

            });
        },
        data: function () {
            $.ajax({
                url     : '/m/xplan/getLatestSchemeId.do',
                data    : {
                    currentPage: 1,
                    closeTerm  : 12,
                    pageSize   : 10
                },
                dataType: 'json',
                success : function (data) {
                    if (data.resultCode == 0) {
                        var apr = data.schemeInfo.MINAPR;
                        $("#planApr").html(apr);

                        $("#planBuy").attr("planid", data.schemeInfo.SCHEMEID);
                    }

                }
            });
        }

    };

    function Redirect() {
        window.location.href = "/m/#!/static/html/account/account.html";
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
                            if(str.code == 200000){
                                if (str.data.status.code === 200) {
                                    result = true;
                                }
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
                                        // console.log(result);
                                        // console.log('v ' + result.info);
                                        xui.prompt({
                                            time: 2000,
                                            icon: 'success',
                                            msg : '您已登录成功！'
                                        });
                                        track.init(result.data.user);
                                        /*XXD_TRACK._trackEvent({
                                            category: "webapp_partner_reg",
                                            action: "login_success_webapp",
                                            label: "登录成功",
                                            value: result.data.user.userId,
                                            custval: (window.location.href).split('?')[0]
                                        });*/
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
                                        // console.log('x ' + result.info);
                                        xui.prompt({
                                            time: 1000,
                                            icon: 'error',
                                            msg : result.info
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
            $(oPassword).attr('maxlength', '16');

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
                    xui.prompt({
                        time: 2000,
                        icon: 'error',
                        msg : '请检查您的网络！'
                    });
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

                var vCode = $.trim($(oVCode).val());
                if (vCode == '') {
                    // console.log('请输入图形验证码');
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请输入图形验证码'
                    });
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
                            xui.prompt({
                                time: 2000,
                                icon: 'warning',
                                msg : result.info
                            });
                        } else if (result.code === 1) {
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
                                                clearIntervalArray();
                                            } else {
                                                //获取短信验证码超过30秒，还未进行验证，显示获取语音验证码按钮
                                                $(oMCodeBtn).addClass('sending').html(tempTime + '秒后重新发送');
                                            }
                                        }, 1000));

                                    } else if (result.code === -40) {
                                        getVCode();
                                        $(oVCode).focus();
                                    }
                                    xui.prompt({
                                        time: 2000,
                                        icon: 'warning',
                                        msg : result.info
                                    });


                                }
                            });
                        }else {
                            xui.prompt({
                                time: 2000,
                                icon: 'warning',
                                msg : result.info
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

                var vCode = $.trim($(oVCode).val());
                if (vCode == '') {
                    // console.log('请输入图形验证码');
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '请输入图形验证码'
                    });
                    $(oVCode).focus();
                    return;
                }

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

                if (password.toString().length<6 || password.toString().length>16) {
                    // console.log('请输入密码');
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg : '密码需6-16位'
                    });
                    $(oPassword).focus();
                    return;
                }

                xui.prompt({
                    time: 10000,
                    icon: 'loading',
                    msg : '正在注册，请稍候...'
                });

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
                            xui.prompt({
                                time: 2000,
                                icon: 'success',
                                msg : result.info
                            });
                            track.init(result.data.user);
                            ga('send', 'event', "注册", "注册成功", window.location.href);
                            setTimeout(function () {
                                window.location.href = "/m";
                            }, 2500);
                        } else {
                            xui.prompt({
                                time: 2000,
                                icon: 'error',
                                msg : result.info
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