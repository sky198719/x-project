require(['base', "register", 'login', "requirejs", 'json', 'juicer'], function ($, register, login, requirejs) {
    var clientId = 'XXD_ACTIVITY_H5_PAGE',
        clientTime = '',
        activityCode = 'June-vip-activity',
        userToken,
        activityStatus,  //活动状态
        remainLotteryTimes,//游戏次数
        thisTime,
        pageid = 1,
        pageall = 0,
        flag = true,
        _token = '',
        xxd_utm_source;

    var itemPrizes = [
        {introduce: '哇，超级幸运的你 偷到一个珍稀大西瓜啦！恭喜你获得', itmeName: 'imgsJune/prize1.png', itemPrize: 'Apple Air笔记本电脑',  itemId: 1},
        {introduce: '亲爱的你很棒，偷到一个超甜的西瓜！送你一个ipad mini2!', itmeName: 'imgsJune/prize2.png', itemPrize: 'Ipad mini2', itemId: 1},
        {introduce: '恭喜你偷到一个小西瓜，送你88个新新币，可投资可兑换可提现！', itmeName: 'imgsJune/prize3.png', itemPrize: '88个新新币', itemId: 1},
        {introduce: '好幸运啊，你偷到一个又大又甜西瓜，并且获得了一张500元京东E卡哟！', itmeName: 'imgsJune/prize4.png', itemPrize: '500元京东卡', itemId: 1},
        {introduce: '恭喜你偷到一个小西瓜，送你500M流量，请收好！', itmeName: 'imgsJune/prize5.png', itemPrize: '500M流量直充',  itemId: 1},
        {introduce: '恭喜你偷到一个大西瓜，送你100元话费，请收好!', itmeName: 'imgsJune/prize6.png', itemPrize: '100元话费充值',  itemId: 1},   //位置有问题
        {introduce: '幸运的你不仅偷到一个珍贵的西瓜，还收获了1000元携程卡哟!', itmeName: 'imgsJune/prize7.png', itemPrize: '1000元携程卡',  itemId: 1},
        {introduce: '阿欧，被狗狗咬了，送你10个新新币再接再厉！', itmeName: 'imgsJune/prize8.png', itemPrize: '10个新新币',  itemId: 1}
    ];
    var main = {
        init: function () {
            clientTime = new Date().getTime();
            userToken = window["userToken"] || main.getCookie('userToken');
            main.initJune();
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
            //所有关闭按钮
            $('.close,.c_close').click(function () {
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                return false;
            });
            //立即注册按钮
            $('.c_confirm').click(function () {
                userToken = window["userToken"] || main.getCookie('userToken');
                main.initJune();
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
                $("#homeIndex").removeClass("toUp");
                $(".wrap-record").removeClass("toDown");
                $(".wrap-rule").removeClass("zIndex-1").addClass("zIndex-3").addClass("toDown");
                $('.wrap-rule').animate({'scrollTop': $('.wrap-rule')[0].offsetTop}, 0);

            });
            //摘西瓜
            $('#index-btn').click(function () {
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
                    $(".wrap-record").removeClass("toDown");
                    $(".wrap-rule").removeClass("toDown");
                    $("#homeIndex").removeClass("zIndex-1").addClass("zIndex-3").addClass("toUp");
                    main.toligin();
                    return;
                }else{
                    $(".wrap-record").removeClass("toDown");
                    $(".wrap-rule").removeClass("toDown");
                    $("#homeIndex").removeClass("zIndex-1").addClass("zIndex-3").addClass("toUp");
                    $('body').animate({'scrollTop': $('.container')[0].offsetTop}, 300);
                }
            });
            //关闭游戏规则
            $(".close").click(function () {
                $("#homeIndex").removeClass("zIndex-1").addClass("zIndex-3").addClass("toUp");
                $(".wrap-rule").removeClass("toDown");
            });
            //关闭中奖纪录
            $("#closeRecord").click(function () {
                $("#homeIndex").removeClass("zIndex-1").addClass("zIndex-3").addClass("toUp");
                $(".wrap-record").removeClass("toDown");
            });
            //底部中奖纪录
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
                    $(".wrap-record").removeClass("toDown");
                    $(".wrap-rule").removeClass("toDown");
                    $("#homeIndex").removeClass("zIndex-1").addClass("zIndex-3").addClass("toUp");
                    main.toligin();
                    return;
                } else {
                    main.bodyhidden();
                    $("#homeIndex").removeClass("toUp");
                    $(".wrap-rule").removeClass("toDown");
                    $(".wrap-record").removeClass("zIndex-1").addClass("zIndex-3").addClass("toDown");
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
                userToken = window["userToken"] || main.getCookie('userToken');
                main.initJune();
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
                        main.toligin();
                        return;
                    }
                    //无机会
                    if (remainLotteryTimes == 0) {
                        $('.game-times').html('0');
                        main.bodyhidden();
                        $('.noChance').addClass('show');
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
                                "userToken": userToken
                            }
                        }),
                        success: function (data) {
                            if (data.data.code == 1) {
                                $('.game-times').html('0');
                                main.bodyhidden();
                                $('.noChance').addClass('show');
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
                                $('.prize-info').html(itemPrizes[thisTime].introduce);
                                $('.prize-name').css("backgroundImage",'url("'+itemPrizes[thisTime].itmeName+'")');
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

            $('#closegame').click(function () {
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
                // setTimeout(function () {
                //     $('#startGame').trigger("click");
                // }, 500);
            });
            //无游戏机会关闭按钮
            $('#nochanceclose').click(function () {
                flag = true;
            });

            //底部投资按钮点击
            //新元宝
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
                    main.toligin();
                    return;
                }
                //新元宝的产品详情页面
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
                    main.toligin();
                    return;
                }
                window.location.href = '/m/#!/static/html/stepUpward/stepUpwardDetail.html'+ '?xxd_utm_source=' + xxd_utm_source;
            });
            //月月派
            $('#yyp').click(function () {
                userToken = window["userToken"] || main.getCookie('userToken');
                console.log("start");
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
                    main.toligin();
                    return;
                }
                //月月派的产品详情页面
                $.ajax({
                    url:'/m/yyp/index.do',
                    type:'get',
                    data:{
                        terms:12
                    },
                    success:function (data) {
                        console.log(data);
                        if(data.resultList !=null &&data.resultList != ''){
                            console.log("success");
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
        initJune: function () {
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
                    $('.game-times').html(data.data.data.remainLotteryTimes);
                },
                error: function () {
                    main.tip('网络异常请重试');
                }
            });
            userToken = window["userToken"] || main.getCookie('userToken');
            if (userToken == null || '') {
                $('.amountData').hide();
                $('.chanceTip').hide();
            } else {
                $('.amountData').show();
                $('.chanceTip').show();
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
        toligin: function (ev) {
            main.callToken(function (token) {
                login.show({
                    token: token,
                    register: register,
                    isMove: true,
                    callback: function (res) {
                        $('body').addClass('bodyauto');
                        userToken = window["userToken"] || main.getCookie('userToken');
                        main.initJune();
                        main.initprizeID();
                        main.myPrize();
                        main.SumAnnualAmount();
                    }
                });
            });
            ev && event.preventDefault();
        },
        callToken: function (callback) {
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
                                var time = item.addtime.substring(0, 10);
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
                                $('.winning-con ul').html('').append('<li>您还未中奖，赶快投资参与活动吧~!</li>');
                                $('.winning-con ul li').addClass('noprizetip');
                                $('.page').fadeOut(0);
                                return;
                            }
                            $.each(data.data.data.list, function (i, item) {
                                $('.winning-con ul li').removeClass('noprizetip');
                                $('.page').fadeIn(0);
                                var time = new Date(item.addtime);
                                var year = time.getFullYear();
                                var month = (time.getMonth() + 1) < 10 ? ("0" + (time.getMonth() + 1)) : (time.getMonth() + 1);
                                var day = (time.getDate()) < 10 ? ("0" + time.getDate()) : time.getDate();
                                $('.winning-con ul').append('<li><span>' + year + '.'+ month + '.' + day + '</span><span>' + item.prizename + '</span></li>');
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
        }
    };
    main.init();
    return main;
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});