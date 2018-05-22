require(['base', "register", 'login', "requirejs",'xxdBridge', 'json', 'juicer'], function ($, register, login, requirejs, xxdBridge) {
    var clientId = 'XXD_ACTIVITY_H5_PAGE',
        clientTime = '',
        activityCode = 'May-vip-activity',
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
        degs = 0,
        str,
        uidzd,
        signzd,
        xxd_utm_source;

    var itemPrizes = [
        {introduce: '亲爱的你很棒！', itmeName: '送你一个啵', itemPrize: '', itemId: 1},
        {introduce: '亲爱的你很棒！', itmeName: '送你一个华为P10手机 ', itemPrize: '华为P10手机', itemId: 1},
        {introduce: '亲爱的你很棒！', itmeName: '送你一个啵', itemPrize: '', itemId: 1},
        {introduce: '可以去来一次浪漫之旅啦，恭喜您获得', itmeName: '1000元携程旅游卡', itemPrize: '1000元携程卡', itemId: 1},
        {introduce: '超级幸运儿，恭喜您获得', itmeName: 'Apple MacBook 笔记本电脑', itemPrize: 'Apple 电脑', itemId: 1},
        {introduce: '亲爱的你很棒！', itmeName: '送你一个啵', itemPrize: '', itemId: 1},
        {introduce: '恭喜你获得22枚新新币', itmeName: '可投资 可兑换 可提现！', itemPrize: '22枚新新币', itemId: 1},
        {introduce: '就猜到你没话费了', itmeName: '30元话费送你啦！', itemPrize: '30元话费', itemId: 1},
        {introduce: '哇，太幸运啦！恭喜您获得', itmeName: '100元京东卡 ', itemPrize: '100元京东卡', itemId: 1},
        {introduce: '恭喜你获得522枚新新币', itmeName: '可投资 可兑换 可提现！', itemPrize: '522枚新新币', itemId: 1},   //位置有问题
        {introduce: '浪漫的迪士尼等你来，恭喜你获得', itmeName: '迪士尼门票卡一张', itemPrize: '迪士尼门票', itemId: 1}
    ];
    var positions = [
        {"transform": "translate(0rem,0rem)"},
        {"transform": "rotate(30deg) translate(-3rem,5rem)"},
        {"transform": "rotate(52deg) translate(-5.5rem, 10.6rem)"},
        {"transform": "rotate(0deg) translate(-11.5rem, 6.5rem)"},
        {"transform": "rotate(-20deg) translate(-5.5rem, 6.5rem)"},
        {"transform": "rotate(-16deg) translate(1.5rem, 9.5rem)"},
        {"transform": "rotate(-20deg) translate(0rem, 11rem)"},
        {"transform": "rotate(30deg) translate(4rem, 13rem)"},
        {"transform": "rotate(0deg) translate(-13rem, 13rem)"},
        {"transform": "rotate(0deg) translate(-10rem, 17rem)"},
        {"transform": "rotate(-12deg) translate(-3rem, 18rem)"}
    ];

    var main = {
        init: function () {
            clientTime = new Date().getTime();
            uid = main.GetQueryString("uid");
            sign = main.GetQueryString("sign");
            main.initMay();
            main.eventBind();
            main.winning();
            main.initprizeID();
            xxd_utm_source = main.GetQueryString("xxd_utm_source") || '';
            if (sign != null || '') {
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
                main.winning();
            }, 600000);
            //游戏注意事项
            $('#attention-btn').click(function () {
                main.bodyhidden();
                $('.attention').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                $('.attention >div').addClass('show');
                flag = true;
            });
            //底部我的奖品
            $('#myPrize-btn').click(function () {
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (sign == null || '') {
                    //window.location.href = 'xxd://login';
                    xxdBridge.open({
                        pagename: 'login',
                    });
                    return;
                } else {
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
            //无游戏机会的时候，点击去投资页面
            $('#invest').click(function () {
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                $('body').animate({'scrollTop': $('.product')[0].offsetTop}, 300);
                flag = true;
            });
            //中间开始游戏按钮
            $('#startGame').click(function () {
                main.initMay();
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
                    if (sign == null || '') {
                        //window.location.href = 'xxd://login';
                        xxdBridge.open({
                            pagename: 'login',
                        });
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
                            } else if (data.data.code == 0) {
                                $('.game-times').html(data.data.data.remainLotteryTimes);
                                for (var i = 0; i < itemPrizes.length; i++) {
                                    if (itemPrizes[i].itemId == data.data.data.prizeInfo.prizeid) {
                                        thisTime = i;
                                    }
                                };
                                timer = setInterval(function () {
                                    degs++;
                                    if (degs >= 11) {
                                        degs = 0;
                                        $('#activeCar').removeClass('transform').addClass('carRight').css({'top':'9rem','left':'13.5rem'});
                                    } else if(degs == 5){
                                        $('#activeCar').css(positions[degs]);
                                        setTimeout(function () {
                                            $('#activeCar').removeClass('carLeft').addClass('carRight');
                                        },450);
                                    }
                                    else if(degs == 2 || degs == 8){
                                        $('#activeCar').css(positions[degs]);
                                        setTimeout(function () {
                                            $('#activeCar').removeClass('carRight').addClass('carLeft');
                                        },450);
                                    }
                                    else {
                                        $('#activeCar').addClass('transform').css(positions[degs]);
                                    }
                                    if (degs == thisTime) {
                                        clearInterval(timer);
                                        setTimeout(function () {
                                            main.bodyhidden();
                                            $('.haveprize').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                                            $('.prize-info').html(itemPrizes[thisTime].introduce);
                                            $('.prize-name').html(itemPrizes[thisTime].itmeName);
                                            $('.haveprize >div').addClass('show');
                                        }, 500);
                                    }
                                }, 350);
                            }else{
                                //window.location.href = 'xxd://login';
                                xxdBridge.open({
                                    pagename: 'login',
                                });
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
                $('#activeCar').removeClass('transform carLeft').removeAttr("style").addClass('carRight').css({'top':'9rem','left':'13.5rem'});
                degs = 0;
                flag = true;
                return false;
            });
            //再来一次按钮
            $('.one-more').click(function () {
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                $('#activeCar').removeClass('transform carLeft').removeAttr("style").addClass('carRight').css({'top':'9rem','left':'13.5rem'});
                degs = 0;
                flag = true;
                setTimeout(function () {
                    $('#startGame').trigger("click");
                }, 500);
            });
            //无游戏机会关闭按钮
            $('#nochanceclose').click(function () {
                flag = true;
            });

            //底部投资按钮点击
            $('#goldIngot').click(function () {
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (sign == null || '') {
                    //window.location.href = 'xxd://login';
                    xxdBridge.open({
                        pagename: 'login',
                    });
                    return;
                }
                //金元宝的产品详情页面
                $.ajax({
                    url: '/m/xplan/getLatestSchemeId.do',
                    type: 'get',
                    data: {closeTerm: 12},
                    success: function (data) {
                        if (data.schemeInfo != null && data.schemeInfo != "") {
                            // window.location.href = 'xxd://list/pdd?selID=1&pdName=xinyuanbao&xybID=' + data.schemeInfo.SCHEMEID + '&xxd_utm_source=' + xxd_utm_source;
                            xxdBridge.open({
                                pagename: 'pddetail',
                                productIndex:'5',
                                productId:data.schemeInfo.SCHEMEID
                            });
                        }
                    },
                    error: function () {
                        main.tip('网络异常请重试！');
                    }
                });
            });
            $('#stepUpward').click(function () {
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (sign == null || '') {
                    //window.location.href = 'xxd://login';
                    xxdBridge.open({
                        pagename: 'login',
                    });
                    return;
                }
                //window.location.href = 'xxd://list/pdi?selID=1&pdName=bubugaosheng' + '&xxd_utm_source=' + xxd_utm_source;
                $.ajax({
                    url: '/tradeCenter/investBiz/showStatus/BBGS',
                    type: 'get',
                    data: {},
                    beforeSend: function(request) {
                        request.setRequestHeader("clientId",'XXD_INTEGRATION_PLATFORM');
                        request.setRequestHeader("clientTime",new Date().getTime());
                    },
                    success: function (data) {
                        if(data.code == "200000"){
                            xxdBridge.open({
                                pagename: 'pddetail',
                                productIndex:'2',
                                productId:data.data.productId
                            });
                        }
                    },
                    error: function () {
                        main.tip('网络异常请重试！');
                    }
                });
            });
        },
        initMay: function () {
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
                    "sign": sign,
                    "uid": uid
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
            if (sign == null || '') {
                $('.amountData').hide();
                $('.chanceTip').hide();
            } else {
                $('.amountData').show();
                $('.chanceTip').show();
                $('.gameRule').css('margin-top','5rem');
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
        GetQueryString: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return decodeURI(r[2]);
            return null;
        },
        bodyhidden: function () {
            $('body').addClass('bodyhidden');
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
                    "uid": uid
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
                    'activityCode': activityCode,
                },
                success: function (data) {
                    if (data.data.data) {
                        if (data.data.data.list.length > 0) {
                            $.each(data.data.data.list, function (i, item) {
                                var time = item.addtime.substring(0, 10);
                                $('.winData ul').append("<li><span>" + item.username + "</span><span>" + time + "</span><span>" + item.prizename + "</span></li>");
                            });
                            clearInterval(grandtimer);
                            var index = 0;
                            grandtimer = setInterval(function () {
                                if (index >= data.data.data.list.length - 6) {
                                    index = 0;
                                    $('.winData ul').animate({'scrollTop': 0}, 400);
                                } else {
                                    index++;
                                    $('.winData ul').animate({'top': -1.6875 * index + 'rem'}, 200);
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
                    "sign": sign,
                    "uid": uid,
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