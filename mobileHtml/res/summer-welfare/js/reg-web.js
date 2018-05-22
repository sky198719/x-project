require(['jquery', 'requirejs', 'IScroll', 'xui_user_v101', 'com', 'vticker','trackBase'], function ($, requirejs, IScroll, xui_user, com, vticker,track) {

    // 金币发行量
    var aCoins = [6, 2, 1, 5, 2, 4, 3, 7, 1, 2, 4, 1, 6, 3, 5, 8, 5, 2, 7, 4, 2, 5], nToday = null, nDate = null, aMyCoins = [], scrollTop = 0, thePrize = 0, prizeTime = null;

    var maretPrize = [
        '<img style="width:22.8vw" src="imgs/no-ps.png"/><p class="co1-vice">没有机会啦，好运留给下次活动哦！</p>',
        '<img src="imgs/prize/prize1.png"/><p>运气好的不得了！<span class="co1-vice">Apple MacBook Air</span> 是你的啦！</p><button id="x_prompt">再来一次</button>',
        '<img src="imgs/prize/prize2.png"/><p>运气好的不得了！<span class="co1-vice">500元京东E卡</span> 是你的啦！</p><button id="x_prompt">朕记住啦</button>',
        '<img src="imgs/prize/prize3.png"/><p>运气好的不得了！<span class="co1-vice">iPhone7 Plus 128G</span> 是你的啦！</p><button id="x_prompt">朕记住啦</button>',
        '<img src="imgs/prize/prize4.png"/><p>运气好的不得了！<span class="co1-vice">1000元京东E卡</span> 是你的啦！</p><button id="x_prompt">朕记住啦</button>',
        '<img src="imgs/prize/prize5.png"/><p>恭喜获得<span class="co1-vice">10新新币</span>！可兑换再投资，可提现当零花钱哦！</p><button id="x_prompt">朕记住啦</button>',
        '<img src="imgs/prize/prize6.png"/><p>运气好的不得了！<span class="co1-vice">50元话费充值</span> 是你的啦！</p><button id="x_prompt">朕记住啦</button>',
        '<img src="imgs/prize/prize7.png"/><p>运气好的不得了！<span class="co1-vice">索尼微单</span> 是你的啦！</p><button id="x_prompt">朕记住啦</button>',
        '<img src="imgs/prize/prize5.png"/><p>恭喜获得<span class="co1-vice">66新新币</span>！可兑换再投资，可提现当零花钱哦！</p><button id="x_prompt">朕记住啦</button>'
    ];

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
                    $('#login_msg').text(result.msg);
                    ux.xUser();
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

                },
                //仅登录成功回调
                success : function (result) {
                    $('#reg_msg').text(result.msg);
                    ux.xUser();
                    window.location.href = 'index-web.html';
                }
            });
        }
    };

    var ux = {
        drawPrize : function () {
            // 领取金币
            if (xui_user.isLogin()) {
                if ($('#to_draw').hasClass('ed') || $('#to_draw').hasClass('wait')) {

                } else {
                    // alert('101-'+aMyCoins.length);

                    if (aMyCoins.length >= 22) {
                        var sText2 = '<p><span style="color:#c10a0a">领取成功！</span>同时恭喜您登录满22天获得998新新币奖励，新新币将在5个工作日内发放至您的账户中，可兑换提现哦！</p><button id="x_prompt">朕记住啦！</button>';
                        ux.oPrompt(sText2)

                    } else {
                        var sText1 = '<p><span style="color:#c10a0a">领取成功！</span>新新币将在5个工作日内发放至您的账户中，可兑换提现哦！</p><button id="x_prompt">朕记住啦！</button>';
                        ux.oPrompt(sText1)
                    }

                    $('#to_draw').removeClass('show').addClass('ed');

                }
            } else {
                // alert(0)
                ux.oUser('login');
            }
        },
        drawed    : function () {
            $('#to_draw').removeClass('show').addClass('ed');
        },
        oUser          : function (who) {
            scrollTop   = $(document).scrollTop();
            var thisTop = 'translateY(-' + $(document).scrollTop() + 'px)';
            // console.log(scrollTop);
            if (who == 'login') {
                $('#user').removeClass('reg').addClass('login show');
            } else {
                $('#user').removeClass('login').addClass('reg show');
            }

            $('body').addClass('ban');
            $('#middle').css('transform', thisTop);

        },
        oLogin         : function () {
            $('#user').removeClass('reg').addClass('login show');

        },
        oReg           : function () {
            $('#user').removeClass('login').addClass('reg show');
        },
        xUser     : function () {
            $('#user').removeClass('login reg show');
            $('body').removeClass('ban');
            $('#middle').removeAttr('style');
            $(document).scrollTop(scrollTop)
        },
        oPrompt   : function (sText) {
            $('#prompt').addClass('show');
            $('#msg').html(sText);

        },
        lottery   : function () {
            var aDd = $('#lottery').children('dd');
            // console.log(aDd);

            prizeTime = setInterval(function () {
                if (thePrize == 0) {
                    ux.lotterying();
                } else {
                    ux.lotteryed(thePrize);
                    clearInterval(prizeTime);
                }

            }, 480);
        },
        lotterying: function () {
            for (var i = 1; i < 9; i++) {
                !function (_i) {
                    setTimeout(function () {
                        $('#lottery').removeClass('ing1 ing2 ing3 ing4 ing5 ing6 ing7 ing8');
                        $('#lottery').addClass('ing' + _i);
                    }, _i * 60);
                }(i)
            }
        },
        lotteryed : function (thePrize) {
            var time2 = 1000;
            for (var i = 1; i <= thePrize; i++) {
                !function (_i) {
                    setTimeout(function () {
                        $('#lottery').removeClass('ing1 ing2 ing3 ing4 ing5 ing6 ing7 ing8');
                        $('#lottery').addClass('ing' + _i);
                    }, _i * 60);
                    time2 = _i * 60 + 1000;
                }(i)
            }

            setTimeout(function () {
                // console.log(maretPrize[thePrize]);
                ux.oPrompt(maretPrize[thePrize]);
                $('#lottery').removeClass('ing1 ing2 ing3 ing4 ing5 ing6 ing7 ing8');
            }, time2);

        },
        lotteryListPlay: function () {
            if ($('#lottery_list li').length >= 6) {
                $('#lottery_box').vTicker({
                    speed     : 500,
                    pause     : 2000,
                    animation : 'fade',
                    mousePause: false,
                    showItems : 6
                });
            }
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
                timeout : 2000,
                data    : {},
                success : function (result) {
                    token = result.token;
                },
                error   : function () {
                    console.log('网络出错')
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

                        nDate = new Date(str.data.sysdate).getMonth() + '' + com.twoNum(new Date(str.data.sysdate).getDate());
                        // console.log('243-nDate '+nDate);
                        // console.log(229 + '测试用，上线前删掉');
                        // if (com.getUrlValue('nDate')) {
                        //     nDate = com.getUrlValue('nDate');
                        //     console.log('nDate数据库真实数据为-' + new Date(str.data.sysdate).getMonth() + '' + com.twoNum(new Date(str.data.sysdate).getDate()) );
                        //     console.log('nDate前端模块为-' + nDate);
                        // }

                        if (nDate == 808) {
                            $('#mod4').addClass('show');
                            $('#to_draw').addClass('show').removeClass('wait');
                            if (xui_user.isLogin()) {
                                $('#lottery_ps').addClass('show');
                            }
                        }else if(nDate > 808){
                            // alert('活动已结束')
                            $('#ended').addClass('show');
                        }
                    }
                },
                error   : function (str) {
                    alert('数据查询失败，请联系客服')
                }
            });
        },
        lotteryPs : function () {
            /*
             抽奖
             接口文档：http://dev.xxd.com/activityCenter/activityBase/initial?activityCode=August-17-special-actvity
             */

            $.xxdAjax({
                url     : '/activityCenter/activityBase/initial',
                dataType: 'json',
                clientId : "XXD_ACTIVITY_H5_PAGE",
                token : com.getCookie('userToken'),
                data    : {
                    'activityCode': 'August-17-special-actvity',
                    'userToken'   : com.getCookie('userToken')
                },
                type    : 'GET',
                callbacks : function (str) {
                    // console.log(str);
                    if (str.code == 200000) {
                        // str.data.data.remainLotteryTimes = 0;
                        $('#number').text(str.data.data.remainLotteryTimes);
                        if (str.data.data.remainLotteryTimes > 0 && str.data.data.activityStatus == 0) {
                            $('#to_lottery').addClass('can');
                        }

                    }
                },
                error   : function (str) {
                    alert('数据查询失败，请联系客服')
                }
            });
        },
        lotteryLi : function () {
            /*
             中奖名单
             接口文档：http://dev.xxd.com/activityCenter/activityBase/getLatest15AwardsList?activityCode=August-17-special-actvity
             */

            $.xxdAjax({
                url     : '/activityCenter/activityBase/getLatest15AwardsList',
                dataType: 'json',
                clientId : "XXD_ACTIVITY_H5_PAGE",
                data    : {
                    "activityCode": "August-17-special-actvity"
                },
                type    : 'GET',
                callbacks : function (str) {
                    // console.log('中奖名单');
                    // console.log(str);
                    if (str.code == 200000) {
                        if (str.data.code == 0) {
                            // alert(str.data.data.length);
                            if (str.data.data == 0) {

                            }else {
                                var aList = str.data.data.list, sList = '';
                                for (var i = 0; i < str.data.data.list.length; i++) {
                                    var sUserName  = aList[i] ? aList[i].username : '';
                                    var sTime      = aList[i] ? com.twoNum(new Date(aList[i].addtime).getHours()) + ':' + com.twoNum(new Date(aList[i].addtime).getMinutes()) + ':' + com.twoNum(new Date(aList[i].addtime).getSeconds()) : '';
                                    var sPrizeName = aList[i] ? aList[i].prizename : '';
                                    sList += '<li><i>' + sUserName + '</i><b>' + sTime + '</b><em>' + sPrizeName + '</em></li>'
                                }
                                $('#lottery_list').html(sList);
                                ux.lotteryListPlay();
                            }
                        }
                    }
                },
                error   : function (str) {
                    alert('数据查询失败，请联系客服')
                }
            });

        },
        myPrizes  : function () {
            $.xxdAjax({
                url     : '/activityCenter/activityBase/getMyAwardsList',
                dataType: 'json',
                clientId : "XXD_ACTIVITY_H5_PAGE",
                token : com.getCookie('userToken'),
                data    : {
                    "activityCode": "August-17-special-actvity",
                    "currentPage" : 1,
                    "pageSize"    : 6
                },
                type    : 'GET',
                callbacks : function (str) {
                    // console.log('我的奖品');
                    // console.log(str);
                    if (str.code == 200000) {
                        if (str.data.code == 0) {
                            // alert('我的'+str.data.data.length);
                            if (str.data.data == 0) {
                                if (nDate >= 808) {
                                    $('#my_prize').html('<li><i>获奖时间</i><b>所获奖品</b></li><li><i></i><b></b></li><li><i></i><b></b></li><li><i></i><b></b></li><li><i></i><b></b></li><li><i></i><b></b></li><li><i></i><b></b></li>');
                                }
                            } else {

                                var aList = str.data.data.list, sList = '<li><i>获奖时间</i><b>所获奖品</b></li>';
                                // console.log(aList);
                                for (var i = 0; i < 6; i++) {
                                    var sTime      = aList[i] ? com.twoNum(new Date(aList[i].addtime).getHours()) + ':' + com.twoNum(new Date(aList[i].addtime).getMinutes()) + ':' + com.twoNum(new Date(aList[i].addtime).getSeconds()) : '';
                                    var sPrizeName = aList[i] ? aList[i].prizename : '';
                                    sList += '<li><i>' + sTime + '</i><b>' + sPrizeName + '</b></li>'
                                }
                                if (nDate >= 808) {
                                    $('#my_prize').html(sList);
                                }
                            }


                        }
                    }
                },
                error   : function (str) {
                    alert('数据查询失败，请联系客服')
                }
            });
        },
        punchLi   : function () {
            /*
             签到日期
             接口文档：http://dev.xxd.com/activityCenter/swagger-ui.html#!/SpecialActivities/loginStatusUsingGET
             */
            $.xxdAjax({
                url     : '/activityCenter/specialActivities/loginStatus',
                dataType: 'json',
                clientId : "XXD_ACTIVITY_H5_PAGE",
                token : com.getCookie('userToken'),
                data    : {},
                type    : 'GET',
                callbacks : function (str) {
                    // console.log(str);
                    if (str.code == 200000) {
                        var aDay = str.data.items, aLi = $('#date').children('li').slice(5, -8);
                        nToday   = str.data.sysdate;
                        // console.log('420-nToday '+nToday);
                        // console.log(368 + '测试用，上线前删掉');
                        // if (com.getUrlValue('nToday')) {
                        //     nToday = com.getUrlValue('nToday');
                        //     console.log('nToday后台真实-' + str.data.sysdate);
                        //     console.log('nToday前端替换-' + nToday);
                        // }

                        // console.log('nToday= ' + nToday);
                        if (xui_user.isLogin()) {
                            aMyCoins = [];
                            for (var i = 0; i <= nToday; i++) {
                                if (aDay[i] == true) {
                                    $(aLi[i]).addClass('ed').find('b').text(aCoins[i]);
                                    aMyCoins.push(aCoins[i]);
                                } else {
                                    $(aLi[i]).addClass('no');
                                }
                                // if ((Number(nToday) + 1) == aDay.length) {
                                //     $('#to_draw').addClass('show').removeClass('wait');
                                // }

                                // if (i == nToday && xui_user.isLogin()) {
                                //     var sText = '<p>欢迎登录，今天获得<span style="font-size:6.4vw; color:#c10a0a">' + aCoins[i] + '</span>个新新币哦！周五新元宝最高<span style="color:#c10a0a">加息2%</span>，万万不能忘！</p><button id="x_prompt">朕记住啦！</button>';
                                //     ux.oPrompt(sText);
                                // }

                            }

                            $('#lottery_ps').addClass('show');
                        }

                    }
                },
                error   : function (str) {
                    alert('数据查询失败，请联系客服')
                }
            });

        },
        loginPrize: function () {
            $.xxdAjax({
                url     : '/activityCenter/specialActivities/loginPrize',
                dataType: 'json',
                clientId : "XXD_ACTIVITY_H5_PAGE",
                token : com.getCookie('userToken'),
                data    : {
                    "activityCode": "August-17-special-actvity"
                },
                type    : 'GET',
                callbacks : function (str) {
                    // console.log(str);
                    if (str.code == 200000) {
                        if (str.data.code == 1) {
                            // console.log('领取成功');
                            ux.drawPrize();
                        } else {
                            // console.log('领取失败-无操作');
                            // alert('478-测后立即删除');
                            // ux.drawPrize();
                        }

                    }
                },
                error   : function (str) {
                    alert('数据查询失败，请联系客服')
                }
            });

        },
        isDrawed  : function () {
            $.xxdAjax({
                url     : '/activityCenter/specialActivities/receiveXinXinCoin',
                dataType: 'json',
                clientId : "XXD_ACTIVITY_H5_PAGE",
                token : com.getCookie('userToken'),
                data    : {
                    "activityCode": "August-17-special-actvity"
                },
                type    : 'GET',
                callbacks : function (str) {
                    // console.log(str);
                    if (str.code == 200000) {
                        // str.data.code = 1;

                        if (str.data.code == 1) {
                            // str.data.data = true;
                            if (str.data.data) {
                                // console.log('已领取，不让点！');
                                ux.drawed();
                            }
                        } else {
                            // console.log('请求不成功！');
                        }

                    }
                },
                error   : function (str) {
                    alert('数据查询失败，请联系客服')
                }
            });

        },
        lottery   : function () {
            $.xxdAjax({
                url     : '/activityCenter/activityBase/lottery?activityCode=August-17-special-actvity',
                Accept    : "application/json;charset=UTF-8",
                dataType: 'json',
                async   : false,
                clientId : "XXD_ACTIVITY_H5_PAGE",
                token : com.getCookie('userToken'),
                //data    :{"activityCode": "August-17-special-actvity"},
                type    : 'post',
                callbacks : function (str) {
                    // console.log(str);
                    // str.data.code = 1;
                    // str.data.data = true;
                    // alert('427-str.code '+str.code);
                    if (str.code == 200000) {

                        // alert('431-200000成功'+str.data.code);

                        if (str.data.code == 0) {
                            if (str.data.data) {
                                // console.log('抽中');
                                var sPrizeName = str.data.data.prizeInfo.prizename;
                                var prizeId;
                                if (sPrizeName == 'MacBook Air') {
                                    prizeId = 1;
                                } else if (sPrizeName == '500元京东E卡') {
                                    prizeId = 2;

                                } else if (sPrizeName == 'iPhone7 Plus128G') {
                                    prizeId = 3;

                                } else if (sPrizeName == '1000元京东E卡') {
                                    prizeId = 4;

                                } else if (sPrizeName == '10个新新币') {
                                    prizeId = 5;

                                } else if (sPrizeName == '50元话费直充') {
                                    prizeId = 6;

                                } else if (sPrizeName == '索尼微单') {
                                    prizeId = 7;

                                } else if (sPrizeName == '66个新新币') {
                                    prizeId = 8;

                                }
                                setTimeout(function () {
                                    thePrize = prizeId;
                                }, 980);
                                db.lotteryPs();
                                db.lotteryLi();

                            }
                        } else {
                            // console.log('请求不成功！');
                            clearInterval(prizeTime);
                            var sText = '<p>' + str.data.message + '</p><button id="x_prompt">朕知道了</button>';
                            ux.oPrompt(sText);
                            // $('#to_lottery').removeClass('is_ing');
                        }

                    }else {
                        // alert('471-请求不成功！'+str.code);
                        // $('#to_lottery').removeClass('is_ing');
                        clearInterval(prizeTime);
                        // ux.oPrompt('<p>帐号登录已过期</p><button id="x_prompt">朕知道了</button>');
                        ux.oUser('login');
                    }
                },
                error   : function (str) {
                    alert('数据查询失败，请联系客服')
                }
            });
        },
        prizeLi   : function () {
            $.xxdAjax({
                url     : '/activityCenter/activityBase/getMaretPrize',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                data    : {
                    "activityCode": "August-17-special-actvity"
                },
                type    : 'GET',
                callbacks : function (str) {
                    // console.log(str);
                    // str.data.code = 1;
                    // str.data.data = true;
                    if (str.code == 200000) {
                        if (str.data.code == 1) {
                            if (str.data.data) {
                                // console.log('抽中');
                            }
                        } else {
                            // console.log('请求不成功！');
                        }

                    }
                },
                error   : function (str) {
                    alert('数据查询失败，请联系客服')
                }
            });
        }
    };


    $(function () {

        if (xui_user.isLogin()) {
            window.location.href = "index-web.html";
        }

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
            $('html').addClass('logined');
            // console.log('690-' + '已登录');
            db.punchLi();
            db.isDrawed();
            db.lotteryPs();
            db.myPrizes();
	    db.logedUser();
            $('#lottery_ps').addClass('show');
        }

        $('#to_prize').click(function () {
            if (xui_user.isLogin()) {
		db.myPrizes();
                $('#prize').addClass('show');
            } else {
                ux.oUser('login');
            }
        });

        $('#x_prize').click(function () {
            $('#prize').removeClass('show');
        });


        $('#to_rule').click(function () {
            $('#rule').addClass('show');
            scroll1.refresh();
        });

        $('#x_rule').click(function () {
            $('#rule').removeClass('show');
        });

        $('#to_prompt').click(function () {
            $('#prompt').addClass('show');
            scroll1.refresh();
        });

        $(document).on('click', '#x_prompt,.x_prompt', function () {
            $('#prompt').removeClass('show');
        });

        $('#to_user').click(function () {
            $('#user').addClass('show');
            scroll1.refresh();
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

        $(document).on('click', '#to_join', function () {
            if (xui_user.isLogin()) {
                window.location.href = "index-web.html";
            }else {
                ux.oUser('reg');
            }

        });



        /*
         领取金币
         */
        $(document).on('click', '#to_draw', function () {
            db.punchLi();
            if (nDate == 808) {
                // console.log('m22' + '就是今天');
                if(xui_user.isLogin()){
                    db.loginPrize();
                }else {
                    ux.oUser('login');
                }

            } else {
                // console.log('m22' + '时间未到');
            }
        });


        /*
         立即抢购
         */

        $(document).on('click', '.o_new_plan', function () {
            // console.log('点击抢购');
            if (xui_user.isLogin()) {
                // console.log('点击抢购->已登录');
                if (nToday == 0 || nToday == 7 || nToday == 14 || nToday == 21) {
                    // console.log('点击抢购->已登录->是加息日');
                    // console.log('点击抢购->已登录->是加息日->跳到热门理财');
                    window.location.href = '/m/#!/static/html/popular/financesList.html';
                } else {
                    // console.log('点击抢购->已登录->非加息日');
                    var sText = '<p>陛下，加息标仅在8/18、8/25、9/1、9/8每周五9:00发售哦~</p><button id="x_prompt">朕知道了</button>';
                    ux.oPrompt(sText)
                }
            } else {
                // console.log('点击抢购->未登录');
                ux.oUser('login')
            }

        });

     /*   $(document).on('click', '#to_lottery', function () {

            if (xui_user.isLogin()) {
                if ($(this).hasClass('can')) {

                    if($(this).hasClass('is_ing')){

                    }else {
                        $(this).addClass('is_ing');
                        console.log('to_lottery');
                        thePrize = 0;
                        ux.lottery();
                        db.lottery();
                    }
                } else {
                    console.log('没有抽奖机会');
                    ux.oPrompt(maretPrize[0])
                }
            } else {
                ux.oUser('login');
            }
            setTimeout(function () {
                $('#to_lottery').removeClass('is_ing');
            },2000)

        });*/

        var isLotterying = true;
        $('#to_lottery').click(function () {

            if (isLotterying) {
                isLotterying = false;

                if (xui_user.isLogin()) {
                    if ($(this).hasClass('can')) {
                        // console.log('to_lottery');
                        thePrize = 0;
                        ux.lottery();
                        db.lottery();

                    } else {
                        // console.log('没有抽奖机会');
                        ux.oPrompt(maretPrize[0])
                    }
                } else {
                    ux.oUser('login');
                }
                setTimeout(function () {
                    isLotterying = true;
                }, 2000)
            }
        });

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


        ev.funUser();
        db.logedUser();
        db.lotteryLi();



    });


}, function (err) {
    console.log(err);
});