require(['base', "register", 'login','vTicker', "requirejs", 'json', 'juicer'], function ($, register, login, vTicker,requirejs) {
    var clientId = 'XXD_ACTIVITY_H5_PAGE',
        clientTime = '',
        activityCode = 'July-17-vip-activity',
        uid,
        sign,
        activityStatus,  //活动状态
        remainLotteryTimes,//游戏次数
        usedLotteryTimes, //本次活动可用次数
        thisTime,
        pageid = 1,
        pageall = 0,
        flag = true,
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
    var main = {
        init: function () {
            clientTime = new Date().getTime();
            uid = main.GetQueryString("uid");
            sign = main.GetQueryString("sign");
            main.initJuly();
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
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if (sign == null || '') {
                    window.location.href = 'xxd://login';
                    return;
                }else{
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
                $('body').animate({'scrollTop': $('.middle')[0].offsetTop}, 300);
                main.initJuly();
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
                        window.location.href = 'xxd://login';
                        return;
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
                    //flag = false;
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
                    window.location.href = 'xxd://login';
                    return;
                }
                //新元宝的产品详情页面
                $.ajax({
                    url: '/m/xplan/getLatestSchemeId.do',
                    type: 'get',
                    data: {closeTerm: 12},
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
                    window.location.href = 'xxd://login';
                    return;
                }
                window.location.href = 'xxd://list/pdi?selID=1&pdName=bubugaosheng' + '&xxd_utm_source=' + xxd_utm_source;
            });
            //月月派
            $('#yyp').click(function () {
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
                    window.location.href = 'xxd://login';
                    return;
                }
                //月月派的产品详情页面
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
                    "sign": sign,
                    "uid": uid
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
            //var myDate      = main.getMyDate(sClientTime);
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
            if (sign == null || '') {
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
            });
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
                                $('.winning-con ul').html('').append('<li>您还未中奖，赶快投资参与活动吧~!</li>');
                                $('.winning-con ul li').addClass('noprizetip');
                                $('.page').fadeOut(0);
                                return;
                            }
                            $.each(data.data.data.list, function (i, item) {
                                $('.winning-con ul li').removeClass('noprizetip');
                                $('.page').fadeIn(0);
                                var time = new Date(item.addtime);
                                // var year = time.getFullYear();
                                // var month = (time.getMonth() + 1) < 10 ? ("0" + (time.getMonth() + 1)) : (time.getMonth() + 1);
                                // var day = (time.getDate()) < 10 ? ("0" + time.getDate()) : time.getDate();
                                var hours = (time.getHours()) < 10 ? ("0" + time.getHours()) : time.getHours();
                                var getMinutes = (time.getMinutes()) < 10 ? ("0" + time.getMinutes()) : time.getMinutes();
                                var getSeconds = (time.getSeconds()) < 10 ? ("0" + time.getSeconds()) : time.getSeconds();
                                $('.winning-con ul').append('<li><span>' + hours + ':' + getMinutes + ':' + getSeconds + '</span>抽中了<span>' + item.prizename + '</span></li>');
                            });
                        } else {
                            $('.winning-con ul').html('').append('<li>您还未中奖，赶快投资参与活动吧~!</li>');
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
        }
    };
    main.init();
    return main;
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});