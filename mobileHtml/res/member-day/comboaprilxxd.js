require(['base', "register", 'login', "requirejs", 'json','juicer'], function ($, register, login, requirejs) {
    var clientId = 'XXD_ACTIVITY_H5_PAGE',
        clientTime = '',
        activityCode = 'April-17-vip-activity',
        uid,
        sign,
        activityStatus,  //活动状态
        remainLotteryTimes,//游戏次数
        grandtimer, // 幸运大奖计时器
        thisTime,
        pageid = 1,
        pageall = 0,
        flag = true,
        timer,
        degs = -1,
        str,
        uidzd,
        signzd,
        xxd_utm_source;
    var itemName = [
        {itmeName: 'Apple 笔记本电脑', itemId: 1},
        {itmeName: '500元京东卡', itemId: 1},
        {itmeName: '10个新新币', itemId: 1},
        {itmeName: '1G流量', itemId: 1},
        {itmeName: '九阳破壁榨汁机', itemId: 1},
        {itmeName: '88个新新币', itemId: 1},
        {itmeName: '100元话费直充', itemId: 1},
        {itmeName: '1W新新币', itemId: 1}
    ];
    var positions = [
        {"transform": "translate(0rem,-8rem)"},
        {"transform": "translate(7rem,-8rem)"},
        {"transform": "translate(7rem,0rem)"},
        {"transform": "translate(7rem,8rem)"},
        {"transform": "translate(0rem,8rem)"},
        {"transform": "translate(-7rem,8rem)"},
        {"transform": "translate(-7rem,0rem)"},
        {"transform": "translate(-7rem,-8rem)"}
    ];
    var main = {
        init: function () {
            clientTime = new Date().getTime();
            uid = main.GetQueryString("uid");
            sign = main.GetQueryString("sign");
            str = window.location.href;
            uidzd = str.indexOf('uid=');
            signzd = str.indexOf('&sign=');
            uid = str.substring(uidzd + 4,signzd) || '';
            sign = str.substr(signzd + 6,str.length) || '';
            xxd_utm_source = main.GetQueryString("xxd_utm_source") || '';
            main.initApril();
            main.eventBind();
            main.grand();
            main.initprizeID();
            if(signzd != -1){
                main.SumAnnualAmount();
                main.myPrize();
            }
        },
        eventBind: function () {
            //所有关闭按钮
            $('.close').click(function () {
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                return false;
            });
            //每10分钟刷新数据
            setInterval(function () {
                main.grand();
            }, 600000);
            //游戏注意事项
            $('.attention-btn').click(function () {
                main.bodyhidden();
                $('.attention').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                $('.attention >div').addClass('show');
                flag = true;
            });
            //底部我的奖品
            $('.myprize-btn').click(function () {
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (signzd == -1) {
                    window.location.href = 'xxd://login';
                    return;
                }else{
                    main.bodyhidden();
                    $('.winning-record').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                    $('.winning-record >div').addClass('show');
                    main.myPrize();
                }
            });
            //我的奖品上一页下一页
            $('.winning-record .pageUp').click(function () {
                pageid--;
                if (pageid <= 0) {
                    pageid = 1;
                    main.pagetip('已经是第一页了');
                } else {
                    main.myPrize();
                }
            });
            $('.winning-record .pageDown').click(function () {
                pageid++;
                if (parseInt(pageid) > parseInt(pageall)) {
                    pageid = pageall;
                    main.pagetip('已经是最后一页');
                } else {
                    main.myPrize();
                }
            });
            //底部开始抽奖
            $('.startbot-btn').click(function () {
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (signzd == -1) {
                    window.location.href = 'xxd://login';
                    return;
                }
                $('body').animate({'scrollTop': $('.container')[0].offsetTop - 100}, 300);
            });
            //无游戏机会的时候，点击去投资页面
            $('#invest').click(function () {
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                $('body').animate({'scrollTop': $('.product')[0].offsetTop}, 300);
                flag = true;
            });
            //中间开始游戏按钮
            $('.ferrule').click(function () {
                main.initApril();
                main.myPrize();
                if (flag) {
                    //活动未开始已结束弹框
                    if (activityStatus == -1) {
                        main.tip('活动未开始');
                        return;
                    } else if (activityStatus == 1) {
                        main.tip('活动已结束');
                        return;
                    }
                    //是否登录 return
                    if (signzd == -1) {
                        window.location.href = 'xxd://login';
                        return;
                    }
                    //无机会
                    if (remainLotteryTimes == 0) {
                        $('.game-times').html('0');
                        main.bodyhidden();
                        $('.noChance').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                        $('.noChance div').addClass('show');
                        return;
                    }
                    flag = false;
                    $.ajax({
                        url:'/activityCenter/activityBase/lottery',
                        contentType: "application/json",
                        dataType: "json",
                        type: "post",
                        beforeSend: function (request) {
                            request.setRequestHeader("clientId", clientId);
                            request.setRequestHeader("clientTime", clientTime);
                        },
                        data: JSON.stringify({
                            "data": {
                                "activityCode": "April-17-vip-activity",
                                "sign": sign,
                                "uid":uid
                            }
                        }),
                        success: function (data) {
                            if (data.data.code == 1) {
                                $('.game-times').html('0');
                                main.bodyhidden();
                                $('.noChance').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                                $('.noChance div').addClass('show');
                                return;
                            }else if (data.data.code == 0) {
                                $('.game-times').html(data.data.data.remainLotteryTimes);
                                for (var i = 0; i < itemName.length; i++) {
                                    if (itemName[i].itemId == data.data.data.prizeInfo.prizeid) {
                                        thisTime = i;
                                    }
                                };
                                timer = setInterval(function () {
                                    degs++;
                                    if (degs >= 8) {
                                        degs = -1;
                                    } else {
                                        $('#circle').css(positions[degs]);
                                    }
                                    if (degs == thisTime) {
                                        clearInterval(timer);
                                        setTimeout(function () {
                                            main.bodyhidden();
                                            $('.haveprize').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                                            $('.prize-name').html(data.data.data.prizeInfo.prizename);
                                            $('.haveprize >div').addClass('show');
                                        }, 400);
                                    }
                                }, 300);
                            }else{
                                window.location.href = 'xxd://login';
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
                $('#circle').css({"transform": "translate(0rem,0rem)"});
                degs = -1;
                flag = true;
                return false;
            });
            //再来一次按钮
            $('.one-more').click(function () {
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                $('#circle').css({"transform": "translate(0rem,0rem)"});
                degs = -1;
                flag = true;
                setTimeout(function () {
                    $('.ferrule').trigger("click");
                }, 500);
            });
            //无游戏机会关闭按钮
            $('#nochanceclose').click(function () {
                flag = true;
            });
            //底部投资按钮点击
            $('.days-income').click(function () {
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (signzd == -1) {
                    window.location.href = 'xxd://login';
                    return;
                }
                window.location.href = 'xxd://list/pdb?selID=1&pdName=ririying&xxd_utm_source=' + xxd_utm_source;
            });
            $('.gold-ingot').click(function () {
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (signzd == -1) {
                    window.location.href = 'xxd://login';
                    return;
                }
                //金元宝的产品详情页面
                $.ajax({
                    url: '/m/xplan/getLatestSchemeId.do',
                    type: 'get',
                    data: {closeTerm: 1},
                    success: function (data) {
                        if (data.schemeInfo != null && data.schemeInfo != "") {
                            window.location.href = 'xxd://list/pdd?selID=1&pdName=xinyuanbao&xybID=' + data.schemeInfo.SCHEMEID + '&xxd_utm_source=' + xxd_utm_source;
                        }
                    },
                    error: function () {
                        main.tip('网络异常请重试！');
                    }
                });
            });
            $('.month-income').click(function () {
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (signzd == -1) {
                    window.location.href = 'xxd://login';
                    return;
                }
                //_的产品详情页面
                $.ajax({
                    url: '/m/yyp/index.do',
                    type: 'get',
                    data: {terms: 12},
                    success: function (data) {
                        if (data.resultList != null && data.resultList != "") {
                            window.location.href = 'xxd://list/pdd?selID=1&pdName=yueyuepai&yypId=' + data.resultList[0].ID + '&xxd_utm_source=' + xxd_utm_source;
                        }
                    },
                    error: function () {
                        main.tip('网络异常请重试！');
                    }
                });
            });
        },
        GetQueryString: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return decodeURI(r[2]);
            return null;
        },
        bodyhidden:function () {
            $('body').addClass('bodyhidden');
        },
        stopDrop:function () {
            var lastY;//最后一次y坐标点
            $(document.body).on('touchstart', function(event) {
                lastY = event.originalEvent.changedTouches[0].clientY;//点击屏幕时记录最后一次Y度坐标。
            });
            $(document.body).on('touchmove', function(event) {
                var y = event.originalEvent.changedTouches[0].clientY;
                var st = $(this).scrollTop(); //滚动条高度
                console.log("st = "+st);
                if (y >= lastY && st <= 0) {//如果滚动条高度小于0，可以理解为到顶了，且是下拉情况下，阻止touchmove事件。
                    lastY = y;
                    event.preventDefault();
                }
                lastY = y;
            });
        },
        initApril: function () {
            $.ajax({
                url:'/activityCenter/activityBase/initial',
                contentType: "application/json",
                dataType: "json",
                type: "get",
                beforeSend: function (request) {
                    request.setRequestHeader("clientId", clientId);
                    request.setRequestHeader("clientTime", clientTime);
                },
                data: {
                    'activityCode': activityCode,
                    "sign": sign,
                    "uid":uid
                },
                success: function (data) {
                    activityStatus = data.data.data.activityStatus;
                    remainLotteryTimes = data.data.data.remainLotteryTimes;
                    $('.game-times').html(data.data.data.remainLotteryTimes);
                },
                error: function () {
                    main.tip('网络异常请重试');
                }
            });
            if(signzd == -1){
                $('.amountdata').hide();
                $('.haveferrule').hide();
            }else{
                $('.amountdata').show();
                $('.haveferrule').show();
            }
        },
        initprizeID: function () {
            $.ajax({
                url:'/activityCenter/activityBase/getMaretPrize',
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
                    if(data.data.data){
                        for (var j = 0; j < data.data.data.length; j++) {
                            for (var i = 0; i < data.data.data.length; i++) {
                                var item = data.data.data[i];
                                if (item.prizename == itemName[j].itmeName) {
                                    itemName[j].itemId = item.id;
                                }
                            }
                        }
                    }
                }
            });
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
                    "sign": sign,
                    "uid":uid
                },
                success: function (data) {
                    var data =(data.data.data.annualAmount).toFixed(2);
                    $('.sumAmount').html(data);
                }
            });
        },
        myPrize: function () {
            $.ajax({
                url:'/activityCenter/activityBase/getMyAwardsList',
                contentType: "application/json",
                dataType: "json",
                type: "get",
                beforeSend: function (request) {
                    request.setRequestHeader("clientId", clientId);
                    request.setRequestHeader("clientTime", clientTime);
                },
                data: {
                    'activityCode': activityCode,
                    "sign": sign,
                    "uid":uid,
                    'currentPage': pageid,
                    'pageSize': 6
                },
                success: function (data) {
                    if (data.data.code == 0) {
                        if (data.data.data) {
                            pageall = data.data.data.pages;
                            pageid =data.data.data.pageNum;
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
                                var month = (time.getMonth()+1) < 10 ? ("0" + (time.getMonth()+1)) : (time.getMonth()+1);
                                var day = (time.getDate()) < 10 ? ("0" + time.getDate()) : time.getDate();
                                var hours = (time.getHours()) < 10 ? ("0" + time.getHours()) : time.getHours();
                                var getMinutes = (time.getMinutes()) < 10 ? ("0" + time.getMinutes()) : time.getMinutes();
                                var getSeconds = (time.getSeconds()) < 10 ? ("0" + time.getSeconds()) : time.getSeconds();
                                $('.winning-con ul').append('<li><span>' + month + '-' + day + '</span><span>' + hours + ':' + getMinutes + ':' + getSeconds + '</span>抽中了<span>' + item.prizename + '</span></li>');
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
        grand: function () {
            $.ajax({
                url:'/activityCenter/activityBase/getLatest15AwardsList',
                contentType: "application/json",
                dataType: "json",
                type: "get",
                beforeSend: function (request) {
                    request.setRequestHeader("clientId", clientId);
                    request.setRequestHeader("clientTime", clientTime);
                },
                data: {
                    'activityCode': activityCode,
                },
                success: function (data) {
                    if(data.data.data){
                        if (data.data.data.list.length > 0) {
                            $.each(data.data.data.list, function (i, item) {
                                var time = item.addtime.substring(0, 10);
                                $('.grand-content ul').append("<li><span>" + item.username + "</span><span>" + time + "</span><span>" + item.prizename + "</span></li>");
                            });
                            clearInterval(grandtimer);
                            var index = 0;
                            grandtimer = setInterval(function () {
                                if (index >= data.data.data.list.length - 6) {
                                    index = 0;
                                    $('.grand-content ul').animate({'scrollTop': 0}, 400);
                                } else {
                                    index++;
                                    $('.grand-content ul').animate({'top': -1.5625 * index + 'rem'}, 200);
                                }
                            }, 2000);
                        }
                    }
                },
                error: function () {
                    main.tip('网络异常请重试!');
                }
            })
        },
        tip: function (msg) {
            $('.tip').html(msg).fadeIn(200, function () {
                setTimeout(function () {
                    $('.tip').fadeOut(200);
                }, 3000);
            });
        },
        pagetip: function (msg) {
            $('.pagetip').html(msg).fadeIn(200, function () {
                setTimeout(function () {
                    $('.pagetip').fadeOut(200);
                }, 2000);
            });
        }
    };
    main.init();
    return main;
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});