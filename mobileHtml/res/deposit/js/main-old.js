require(['base', "requirejs", 'json', "juicer","track"], function ($, register, login, requirejs) {

    var intervalArray = [];

    var _token = "";

    function callToken(callback) {
        if (_token) {
            if (callback) callback(_token);
        } else {
            $.ajax({
                url: "/feapi/users/formToken",
                contentType: "application/json",
                dataType: "json",
                type: "get",
                success: function (res) {
                    _token = res.token;
                    if (callback) callback(_token);
                },
                error: function (data) {
                    $.log(data);
                }
            });
        }
    }

    $.ajax({
        url: '/feapi/users/formToken',
        dataType: 'json',
        type: 'GET',
        data: {},
        success: function (result) {
            _token = result.token;
            $("#randImage").attr("src", "/feapi/randCode/createVerifyCode?formtoken=" + _token);
        }
    });

    $("#J_login").on("click", function (ev) {
        if(isLogin()){
            window.location.href = '/m';
        }else{
            callToken(function (token) {
                login.show({
                    token: token,
                    register: register,
                    callback: function (res) {
                        xui.prompt({
                            time: 2000,
                            icon: 'success',
                            msg: '您已登录成功！'
                        });
                        setTimeout(function () {
                            window.location.href = '/m';
                        },2500);

                    }
                });
            });
            ev && event.preventDefault();
        }

    });

    $("#J_register").on("click", function (ev) {
        callToken(function (token) {
            register.show({
                token: token,
                login: login,
                callback: function (res) {
                    xui.prompt({
                        time: 2000,
                        icon: 'success',
                        msg: '您已注册成功！'
                    });
                    setTimeout(function () {
                        window.location.href = '/m';
                    },2500);
                }
            });
        });
        ev && event.preventDefault();
    });

    $(function () {
        $(document).on('touchmove', '.mui-fix-phone-scroll', function () {
            return false;
        });

        // 注册协议
        agreement.init();

        // 关于_
        bulletin.init();

        // 七天大胜
        get7DaysInfo.init();

        // _
        getFundInfo.init();

        // _
        getPlan.init();

        // _
        getMonthFinance.init();

        // 马上赚钱
        $("#mashang").click(function () {
            if (!isLogin()) {
                xui.prompt({
                    time: 2000,
                    icon: 'warning',
                    msg: '请先注册或者登录'
                });
                setTimeout(function () {
                    toTop();
                },2500);
            } else {
                xui.prompt({
                    time: 2000,
                    icon: 'warning',
                    msg: '请选择上面的产品'
                });
            }
        });

        $(document).on('click','.open_app', function () {
            var thisUrl = $(this).data('url');
            openApp(thisUrl);
        });
        $(document).on('click','.down_app', function () {
            setTimeout(function () {
                window.open('http://www.xinxindai.com/m/static/html/download/app.html?model=auto');
            },500);
        });
        function openApp(thisUrl){
            var schemeUrl = thisUrl;
            if (navigator.userAgent.match(/(iPhone|iPod|iPad)/i)) {
                var loadDateTime = new Date();
                window.setTimeout(function() {
                    var timeOutDateTime = new Date();
                    if (timeOutDateTime - loadDateTime < 5000) {
                        showDownApp()
                    } else {
                        alert('无法打开');
                    }
                },100);
                window.location.href = schemeUrl;
            } else if (navigator.userAgent.match(/android/i)) {


                var loadDateTime2 = new Date();
                window.setTimeout(function() {
                    var timeOutDateTime2 = new Date();
                    if (timeOutDateTime2 - loadDateTime2 < 5000) {
                        showDownApp()
                    } else {
                        alert('无法打开');
                    }
                },100);
                window.location.href = schemeUrl;

            }
        }
        function showDownApp(){
            $('body').append('<div id="toapp" class="to-app"><div><div><h1>请先下载客户端</h1><p>拥抱梦想 亦懂得积累</p><ul><li><button class="x_downapp">就看网页版</button></li><li><button class="down_app">下载客户端</button></li></ul></div></div></div>')

            setTimeout(function () {
                closeDownApp()
            },8000)

        }

        $(document).on('click','.x_downapp',function () {
            closeDownApp();
        });
        function closeDownApp() {
            $('#toapp').remove()
        }

        xui.register({
            frame: '#reg2',
            user: '#mobile-no',
            password: '#password',
            vCode: '#img-code',
            vCodeImg: '#randImage',
            mCode: '#rand-code',
            mCodeBtn: '#send-rand-code',
            submit: '#to_submit'
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

    function toTop(){
        $('html, body').animate({scrollTop:0}, 350);
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
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

        if(arr=document.cookie.match(reg))

            return (arr[2]);
        else
            return null;
    }

    function isLogin() {
        var result = false;
        $.ajax({
            url: '/feapi/users/loginInfo?userToken=' + getCookie('userToken'),
            dataType: 'json',
            async: false,
            data: {},
            type:'GET',
            success: function (str) {
                if(str.data.status.code === 200){
                    result = true;
                }
            }
        });
        return result;
    }
// 关于_->运营数据
    var bulletin = {
        init: function () {
            bulletin.data();
        },
        data: function () {
            $.ajax({
                url: "/biz/bulletin/operationData",
                contentType: "application/json",
                dataType: "json",
                type: "get",
                beforeSend: function (request) {
                    request.setRequestHeader("s", "www");
                    request.setRequestHeader("clientId", "001");
                    request.setRequestHeader("clientTime", "001");
                },
                success: function (msg) {
                    if (msg && msg.code == 200000) {
                        for (var i = 0; i < msg.data.items.length; i++) {
                            if (msg.data.items[i].code == 'TOTAL_REGISTER_USER') {
                                var TOTAL_REGISTER_USER = msg.data.items[i].nvalue;
                                TOTAL_REGISTER_USER = TOTAL_REGISTER_USER / 10000;
                                $('#total_register_user').html(TOTAL_REGISTER_USER.toFixed(2));
                            }
                            if (msg.data.items[i].code == 'VENTURE_BALANCE') {
                                var VENTURE_BALANCE = msg.data.items[i].nvalue;
                                VENTURE_BALANCE = VENTURE_BALANCE / 10000;
                                $('#venture_balance').html(VENTURE_BALANCE.toFixed(2));
                            }
                            if (msg.data.items[i].code == 'TOTAL_TRADE') {
                                var TOTAL_TRADE = msg.data.items[i].nvalue;
                                TOTAL_TRADE = TOTAL_TRADE / 100000000;
                                $('#total_trade').html(TOTAL_TRADE.toFixed(2));
                            }
                            if (msg.data.items[i].code == 'TOTAL_INCOME') {
                                var TOTAL_INCOME = msg.data.items[i].nvalue;
                                TOTAL_INCOME = TOTAL_INCOME / 100000000;
                                $("#total_income").html(TOTAL_INCOME.toFixed(2));
                            }
                        }
                    }
                },
                error: function (data) {
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
                        msg: '请先注册或者登录'
                    });
                    setTimeout(function () {
                        toTop();
                    },2500);
                    return;
                }
                window.location.href = '/m/#!/static/html/newHand/sevenDaysDetail.html?path=newHand';
            });
        },
        data: function () {
            $.ajax({
                url: '/m/sevenDays/selectSevenDaysInfo.do',
                data: {},
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data.sevenDays != null) {
                        var apr = parseFloat(data.sevenDays.apr).toFixed(2);
                        var values = new String(apr).split('.');

                        var floatApr = parseFloat(data.sevenDays.floatApr).toFixed(2);
                        var values1 = new String(floatApr).split('.');
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

// _
    var getMonthFinance = {
        init: function () {
            getMonthFinance.data();

            $("#monthBuy").click(function () {
                if (!isLogin()) {
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg: '请先注册或者登录'
                    });
                    setTimeout(function () {
                        toTop();
                    },2500);
                    return;
                }
                window.location.href = '/m/#!/static/html/monthFinance/monthFinanceDetails.html';
            });
        },
        data: function () {
            $.ajax({
                url: '/m/monthFinance/getProudctBaseInfo.do',
                data: {},
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    if (result.resultCode == '0' || result.resultCode == 0) {
                        var apr = result.data.apr;
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

// _
    var getFundInfo = {
        init: function () {
            getFundInfo.data();

            $("#fundBuy").click(function () {
                if (!isLogin()) {
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg: '请先注册或者登录'
                    });
                    setTimeout(function () {
                        toTop();
                    },2500);
                    return;
                }
                window.location.href = '/m/#!/static/html/fund/fundUnInvested.html?path=fund';
            });
        },
        data: function () {
            $.ajax({
                url: '/m/fund/selectFundInfo.do',
                data: {},
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data != null && data.fundApr != null) {
                        var apr = parseFloat(data.fundApr.apr).toFixed(2);
                        var values = new String(apr).split('.');
                        var floatApr = parseFloat(data.fundApr.floatApr).toFixed(2);
                        var values1 = new String(floatApr).split('.');
                        $("#fundApr").html(parseInt(values[0]) + parseInt(values1[0]));

                        var lowestTender = data.fund.lowestTender;
                        $("#fundlowestTender").html(lowestTender);
                    }
                }
            });
        }
    };

// _
    var getPlan = {
        init: function () {
            getPlan.data();

            $("#planBuy").click(function () {
                if (!isLogin()) {
                    xui.prompt({
                        time: 2000,
                        icon: 'warning',
                        msg: '请先注册或者登录'
                    });
                    setTimeout(function () {
                        toTop();
                    },2500);
                    return;
                }
                var planid = $("#planBuy").attr("planid");
                window.location.href = '/m/#!/static/html/plan/planDetailsV2.html?path=plan&planId=' + planid;

            });
        },
        data: function () {
            $.ajax({
                url: '/m/xplan/getLatestSchemeId.do',
                data: {
                    currentPage: 1,
                    closeTerm: 12,
                    pageSize: 10
                },
                dataType: 'json',
                success: function (data) {
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
        prompt: function (conf) {
            if ($('.xui-prompt[dim="true"]').length > 0) {
                this.dim = false;
            } else {
                this.dim = conf.dim || false;
            }
            this.pos = conf.pos || 'cc';
            this.time = conf.time || 3000;
            if (conf.icon) {
                this.icon = '<p class="prompt-icon"><b class="prompt-icon-' + conf.icon + '"></b></p>';
            } else {
                this.icon = '';
            }
            this.msg = conf.msg || '';
            this.id = $('.xui-prompt').length + 1;
            if (this.msg) {
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
        register: function (conf) {
            var regToken,
                $frame = $(conf.frame),
                oUser = conf.user || '.ux_user',
                oPassword = conf.password || 'ux_password',
                oVCode = conf.vCode || '.ux_v_code',
                oVCodeImg = conf.vCodeImg || '.ux_v_code_img',
                oMCode = conf.mCode || '.ux_m_code',
                oMCodeBtn = conf.mCodeBtn || '.ux_m_code_btn',
                oSubmit = conf.submit || '.ux_submit',
                oOpen = conf.open || '.ux_open',
                oClose = conf.close || '.ux_close',
                oShow = conf.show || '.ux_show',
                oHide = conf.hide || '.ux_hide';

            $(oUser).attr('maxlength', '11');

            // 获取token
            $.ajax({
                url: '/feapi/users/formToken',
                dataType: 'json',
                type: 'GET',
                data: {},
                success: function (result) {
                    regToken = result.token;
                    getVCode();
                },
                error: function () {
                    console.log('ajax 出错')
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
                        msg: '请输入注册手机号'
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
                        msg: '手机号格式错误'
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
                        msg: '请输入图形验证码'
                    });
                    $(oVCode).focus();
                    return false;
                }

                $.ajax({
                    url: '/feapi/users/checkMobile',
                    dataType: 'json',
                    type: 'POST',
                    data: {
                        formtoken: regToken,
                        mobile: username
                    },
                    success: function (result) {
                        if(result.code === 0){
                            xui.prompt({
                                time: 2000,
                                icon: 'warning',
                                msg: result.info
                            });
                        }
                        else if (result.code === 1) {
                            $.ajax({
                                url: '/feapi/users/sendSMS',
                                dataType: 'json',
                                type: 'POST',
                                data: {
                                    formtoken: regToken,
                                    imgcode: vCode,
                                    mobile: username
                                },
                                success: function (result) {

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
                                        msg: result.info
                                    });


                                }
                            });
                        }
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
                        msg: '请输入注册手机号'
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
                        msg: '手机号格式错误'
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
                        msg: '请输入图形验证码'
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
                        msg: '请输入手机验证码'
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
                        msg: '请输入密码'
                    });
                    $(oPassword).focus();
                    return;
                }

                // 邀请码
                // var iCode = $('#i_code').val() || '';
                $.ajax({
                    url: '/feapi/users/regV3',
                    dataType: 'json',
                    type: 'POST',
                    data: {
                        formtoken: regToken,
                        mobile: username,
                        password: password,
                        smscode: mCode,
                        // uuid: iCode
                    },
                    success: function (result) {
                        console.log(result);
                        if(result.regResultCode === '200000'){
                            xui.prompt({
                                time: 2000,
                                icon: 'success',
                                msg: result.info
                            });
                            setTimeout(function () {
                                window.location.href = "/m/#!/static/html/account/account.html";
                            },2500);

                            XXD_TRACK._trackEvent({
                                category: "webapp_partner_reg",
                                action: "reg_success_webapp",
                                label: "注册成功",
                                value: result.data.user.userId,
                                custval: "1"
                            });
                            ga('send', 'event',"注册", "注册成功", window.location.href);
                        } else{
                            xui.prompt({
                                time: 2000,
                                icon: 'error',
                                msg: result.info
                            });
                        }

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