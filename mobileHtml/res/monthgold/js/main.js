require(['jquery', "requirejs", 'IScroll', "trackBase", 'xui_user_v102', 'mcLayer_v100', 'com'], function ($, requirejs, IScroll, track, user, mcLayer, com) {

    var dataUrl = {
        isLogin  : '/feapi/users/loginInfo',
        commonUrl: '/tradeCenter/investBiz/showStatus/'
    };
    //部码
    $.ajax({
        url     : '/feapi/users/loginInfo?userToken=' + getCookie('userToken'),
        dataType: 'json',
        async   : false,
        data    : {},
        type    : 'GET',
        success : function (str) {
            if(str.code == "200000"){
                if (str.data.status.code === 200) {
                    //result = true;
                    track.init(str.data);
                }else {
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
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg))

            return (arr[2]);
        else
            return null;
    }

    // 事件
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
        }
    }
    var ev = {
        user    : function () {
            //登录实例
            user.Login({
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
                    // $('#login_msg').text(result.msg).addClass('show');
                    xui.prompt({
                        time: 1000,
                        icon: 'warning',
                        msg : result.msg
                    });
                    $('html').addClass('logined');
                    // 提示识别码的应用示例
                    // if (result.code == 102) {
                    //     alert('用使消息类型识别码做一个判断');
                    // }
                },
                //仅登录成功回调
                success : function (result) {
                    // console.log(result.msg);
                    //
                    xui.prompt({
                        time: 1000,
                        icon: 'warning',
                        msg : result.msg
                    });
                    mcLayer.x({
                        cb: function () {
                            ux.logined();
                        }
                    });
                }
            });

            //注册实例
            user.Register({
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
                    // $('#reg_msg').text(result.msg).addClass('show');
                    xui.prompt({
                        time: 1000,
                        icon: 'warning',
                        msg : result.msg
                    });

                },
                //仅登录成功回调
                success : function (result) {
                    // $('#reg_msg').text(result.msg).addClass('show');
                    xui.prompt({
                        time: 1000,
                        icon: 'warning',
                        msg : result.msg
                    });
                    mcLayer.x({
                        cb: function () {
                            ux.logined();
                        }
                    });
                }
            });
        },
        myScroll: function () {
            var agreement_box = new IScroll('#agreement_box', {
                bounce       : false,
                scrollbars   : true,
                mouseWheel   : true,
                click        : true,
                bindToWrapper: true
            });
        }
    };
    // 交互
    var ux = {
        oUser  : function (who) {
            mcLayer.o({
                url: 'user-' + who + '.html',
                am : 'aa',
                fix: '#main',
                cb : function () {
                    ev.user();
                }
            });
        },
        oLogin : function () {
            $('#user').removeClass('reg').addClass('login show');

        },
        oReg   : function () {
            $('#user').removeClass('login').addClass('reg show');
        },
        logined: function () {
            $("#J_login").css("display","none");
            $("#regBtn").html("马上赚钱");
            $(".fobar-btn").css({"background":"#fe5a32","color":"#fff"});
        },
        agreement : {
            init: function () {
                $(document).on('click','#read-agreement',function () {
                    ux.agreement.open();
                });
                $(document).on('click','#agree-back',function () {
                    ux.agreement.hide();
                });
                $('#reg-agreement').find("input").click(function (e) {
                    // e.preventDefault();
                    $(this).toggleClass("choose");
                });
            },
            open: function () {
                $('#agreement').fadeIn();
                ev.myScroll();
            },
            hide: function () {
                $('#agreement').fadeOut();
            }
        }
    };
    // 数据
    var db = {
        sevenGold   : function () {
            com.ajax({
                url      : dataUrl.commonUrl + 'QTDS',
                type     : 'get',
                dataType : 'json',
                headers  : {
                    "clientId"  : "XXD_INTEGRATION_PLATFORM",
                    "clientTime": new Date().getTime()
                },
                success: function (r) {
                    if (r.code == "200000") {
                        $("#sevenDayTime").html(r.data.productInfo.period);
                        $("#sevenDaylowestTender").html(r.data.productInfo.lowestTender);
                        var sevenDayApr = Number(r.data.productInfo.apr)+Number(r.data.productInfo.floatApr);
                        $("#sevenDayApr").html(sevenDayApr);
                    }
                },
                error    : function () {
                    alert('网络异常请重试！');
                }
            });
        },
        bulletin    : function () {
            com.ajax({
                url        : "/biz/bulletin/operationData",
                contentType: "application/json",
                dataType   : "json",
                type       : "get",
                beforeSend : function (request) {
                    request.setRequestHeader("s", "www");
                    request.setRequestHeader("clientId", "001");
                    request.setRequestHeader("clientTime", "001");
                },
                success    : function (r) {
                    if (r && r.code == 200000) {
                        for (var i = 0; i < r.data.items.length; i++) {
                            if (r.data.items[i].code == 'TOTAL_REGISTER_USER') {
                                var regUser = (r.data.items[i].nvalue / 10000).toFixed(2);
                                $('#total_register_user').html(regUser);
                            }
                            if (r.data.items[i].code == 'VENTURE_BALANCE') {
                                var ventureBalance = (r.data.items[i].nvalue / 10000).toFixed(2);
                                $('#venture_balance').html(ventureBalance);
                            }
                            if (r.data.items[i].code == 'TOTAL_TRADE') {
                                var totalTrade = (r.data.items[i].nvalue / 100000000).toFixed(2);
                                $('#total_trade').html(totalTrade);
                            }
                            if (r.data.items[i].code == 'TOTAL_INCOME') {
                                var totalIncome = (r.data.items[i].nvalue / 100000000).toFixed(2);
                                $("#total_income").html(totalIncome);
                            }
                        }
                    }
                },
                error      : function (data) {
                    console.error(data.code);
                }
            });
        },
        stepInfo    : function () {
            com.ajax({
                url     : dataUrl.commonUrl + 'BBGS',
                type    : 'get',
                dataType: 'json',
                headers : {
                    "clientId"  : "XXD_INTEGRATION_PLATFORM",
                    "clientTime": new Date().getTime()
                },
                success : function (r) {
                    if (r.code == 200000) {
                        $("#stepMoney").html(r.data.productInfo.leastInvestAmount);
                        $("#stepFrom").html(r.data.productInfo.plannedAnnualRateFrom);
                        $("#stepTo").html(r.data.productInfo.plannedAnnualRateTo);
                    }
                },
                error   : function () {
                    alert('网络异常请重试！');
                }
            });
        },
        monthFinance: function () {
            $.ajax({
                url     : '/m/product/getWebappProduct.do?pCode=YJDJ',
                data    : {},
                dataType: 'json',
                success : function (r) {
                    if (r.code == 200000) {
                        $(".monthTime").html(r.data.items.period);
                        $(".lowestTender").html(r.data.items.lowestTender);
                        $(".monthStep").html(r.data.items.step);
                        $(".mostTender").html(r.data.items.mostTender);
                    }
                }
            });
        },
        newPlan     : function () {
            com.ajax({
                url     : '/m/xplan/getLatestSchemeId.do',
                type    : 'get',
                data    : {
                    "currentPage": "1",
                    "closeTerm"  : "36",
                    "pageSize"   : "10"
                },
                dataType: 'json',
                callback : function (r) {
                    if (r.resultCode == '0' || r.resultCode == 0) {
                        var planApr = r.schemeInfo.MAXAPR + r.schemeInfo.WEBAPP;
                        $("#planApr").html(planApr);
                        $("#planMoney").html(r.schemeInfo.LEASTAMOUNT);
                        $("#planBuy").attr("planid", r.schemeInfo.SCHEMEID);
                    }

                }
            });

        }
    };

    $(function () {

        user.isLogin({
            success: function () {
                ux.logined();
            }
        });

        db.sevenGold();
        db.bulletin();
        db.stepInfo();
        db.monthFinance();
        db.newPlan();

        ux.agreement.init();

        // 登录
        $(document).on('click', '#J_login', function () {
            user.isLogin({
                success: function () {
                    alert(0)
                },
                error  : function () {
                    // console.log('未登录');
                    ux.oUser('login');
                }
            });
        });

        // 注册
        $(document).on('click', '#regBtn', function () {
            user.isLogin({
                success: function (r) {
                    window.location.href = '/m/#!/static/html/monthFinance/monthFinanceDetails.html';
                },
                error  : function () {
                    // console.log('未登录');
                    ux.oUser('reg');
                }
            });
        });

        // 登录注册
        $(document).on('click', '#o_login', function () {
            ux.oLogin();
        });
        $(document).on('click', '#o_reg', function () {
            ux.oReg();
        });


        $(document).on('click', '#sevenDayBuy', function () {
            user.isLogin({
                success: function () {
                    window.location.href = '/m/#!/static/html/newHand/sevenDaysDetail.html?path=newHand';
                },
                error  : function () {
                    ux.oUser('reg');
                }
            });
        });

        $(document).on('click', '#stepBuy', function () {
            user.isLogin({
                success: function () {
                    window.location.href = '/m/#!/static/html/stepUpward/stepUpwardDetail.html';
                },
                error  : function () {
                    ux.oUser('reg');
                }
            });
        });

        $(document).on('click', '#planBuy', function () {
            user.isLogin({
                success: function () {
                    var planid           = $("#planBuy").attr("planid");
                    window.location.href = '/m/#!/static/html/plan/planDetailsV2.html?path=plan&planId=' + planid;
                },
                error  : function () {
                    ux.oUser('reg');
                }
            });
        });

        // 什么是月进斗金
        $(document).on('click', '#introduce', function () {
            $('#answer').slideToggle();
            $("#introduce").find("p").toggleClass("arrow-active");
        });

    })

}, function (err) {
    console.log(err)
});