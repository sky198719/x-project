require(['base','vTicker', "requirejs", 'trackBase', 'xui_user_v101','com','xxdBridge'], function ($, vTicker,requirejs, track,xui_user,com,xxdBridge) {
    var intervalArray = [];
    var clientId = 'XXD_ACTIVITY_H5_PAGE',
        clientTime = '',
        userToken,
        uid,
        activityCode = 'Auguest-17-vip-activity',
        activityStatus,  //活动状态
        remainLotteryTimes,//游戏次数
        usedLotteryTimes, //本次活动可用次数
        thisTime,
        pageid = 1,
        pageall = 0,
        flag = false,
        timer,
        xxd_utm_source;
    var itemPrizes = [
        {introduce: '你好棒棒哦！恭喜获得<span class="prizeStyle">Apple iPad Pro！</span>', itmeName: 'imgsAugust/pop-prize1.png', itemPrize: 'Apple iPad Pro',  itemId: 1},
        {introduce: '你好棒棒哦！恭喜获得<span class="prizeStyle">SK-II"神仙水"晶透修护礼盒！</span>', itmeName: 'imgsAugust/pop-prize5.png', itemPrize: 'SK-II"神仙水"', itemId: 1},
        {introduce: '你好棒棒哦！恭喜获得<span class="prizeStyle">Beats EP 头戴式耳机！</span>', itmeName: 'imgsAugust/pop-prize9.png', itemPrize: 'Beats EP 头戴式耳机', itemId: 1},
        {introduce: '恭喜获得<span class="prizeStyle">30000个新新币！</span><br/>可兑换再投资，可提现当零花钱呢！', itmeName: 'imgsAugust/pop-prize4.png', itemPrize: '30000新新币', itemId: 1},
        {introduce: '你好棒棒哦！恭喜获得<span class="prizeStyle">500元京东E卡！</span>', itmeName: 'imgsAugust/pop-prize2.png', itemPrize: '500元京东E卡', itemId: 1},
        {introduce: '你好棒棒哦！恭喜获得<span class="prizeStyle">100元话费直充！</span>', itmeName: 'imgsAugust/pop-prize3.png', itemPrize: '100元话费直充',  itemId: 1},
        {introduce: '你好棒棒哦！恭喜获得<span class="prizeStyle">50元话费直充！</span>', itmeName: 'imgsAugust/pop-prize7.png', itemPrize: '50元话费直充',  itemId: 1},   //位置有问题
        {introduce: '恭喜获得<span class="prizeStyle">66个新新币！</span><br/>可兑换再投资，可提现当零花钱呢！', itmeName: 'imgsAugust/pop-prize6.png', itemPrize: '66个新新币',  itemId: 1},
        {introduce: '恭喜获得<span class="prizeStyle">10个新新币！</span><br/>可兑换再投资，可提现当零花钱呢！', itmeName: 'imgsAugust/pop-prize8.png', itemPrize: '10个新新币',  itemId: 1}
    ];

    var main = {
        init: function () {
            clientTime = new Date().getTime();
            // userToken = com.getCookie('userToken');
            uid = main.GetQueryString("uid");
            main.initAugust();
            main.eventBind();
            main.winning();
            main.initprizeID();
            main.addEventListener(); //动画事件监听
            xxd_utm_source = main.GetQueryString("xxd_utm_source") || '';
            if (uid == "" || uid == null) {
                track.init();
            }else {
                main.SumAnnualAmount();
                main.myPrize();
                track.init(uid);
            }

        },
        eventBind: function () {
            //所有关闭按钮
            $('.close,.c_close').click(function () {
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                flag = false;
                return false;
            });
            //每10分钟刷新数据
            setInterval(function () {
                main.winning();
            }, 600000);
            //活动规则
            $('#attention-btn').click(function () {
                main.bodyhidden();
                $('.attention').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                $('.attention >div').addClass('show');
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
                if(uid == "" || uid == null){
                    xxdBridge.open({pagename: 'poppage'});
                    xxdBridge.open({
                        pagename: 'login',
                    });
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
                flag = false;
            });
            //开始翻牌
            $('#startGame').on('click', function () {
                //活动未开始已结束弹框
                if (activityStatus == -1) {
                    main.tip('活动未开始');
                    return;
                } else if (activityStatus == 1) {
                    main.tip('活动已结束');
                    return;
                }
                //是否登录 return
                if(uid == "" || uid == null){
                    xxdBridge.open({pagename: 'poppage'});
                    xxdBridge.open({
                        pagename: 'login',
                    });
                    return;
                }else{
                    $('body').animate({'scrollTop': $('.middle')[0].offsetTop}, 300);
                }
            });
            //翻牌点击
            $('.turn-over').click(function (e) {

                main.initAugust();
                main.myPrize();
                //反多张牌同时被翻
                if (flag == false) {
                    main.initAugust();
                    main.myPrize();
                    //活动未开始已结束弹框
                    if (activityStatus == -1) {
                        main.tip('活动未开始');
                        return;
                    } else if (activityStatus == 1) {
                        main.tip('活动已结束');
                        return;
                    }
                    //是否登录 return
                    if(uid == "" || uid == null){
                        xxdBridge.open({pagename: 'poppage'});
                        xxdBridge.open({
                            pagename: 'login',
                        });
                        return;
                    }
                    //无机会
                    if (remainLotteryTimes == 0) {
                        $('.game-times').html('0');
                        main.bodyhidden();
                        if (Number(usedLotteryTimes) >= 6) {
                            $('.noChanceEver').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                            $('.noChanceEver div').addClass('show');
                        } else {
                            $('.noChance').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                            $('.noChance div').addClass('show');
                        }
                    } else {
                        $('.game-times').html(remainLotteryTimes);
                    }
                     flag = false;
                    $.xxdAjax({
                        url     : '/activityCenter/activityBase/lottery?activityCode=' + activityCode,
                        dataType: 'json',
                        clientId: "XXD_ACTIVITY_H5_PAGE",
                        token   : com.getUrlValue('token'),
                        type    : 'POST',
                        callbacks: function (data) {
                            clearTimeout(timer);
                            $this = $(e.currentTarget);
                            if(data.code == "200000"){
                                if (data.data.code == 1) {
                                    $('.game-times').html('0');
                                    main.bodyhidden();
                                    if (Number(usedLotteryTimes) >= 6) {
                                        $('.noChanceEver').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                                        $('.noChanceEver div').addClass('show');
                                    } else {
                                        $('.noChance').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                                        $('.noChance div').addClass('show');
                                    }
                                    return;
                                } else if (data.data.code == 0) {
                                    $('.game-times').html(data.data.data.remainLotteryTimes);
                                    for (var i = 0; i < itemPrizes.length; i++) {
                                        if (itemPrizes[i].itemId == data.data.data.prizeInfo.prizeid) {
                                            thisTime = i;
                                            $(e.currentTarget).find('.img-prize').remove();
                                            $(e.currentTarget).append("<img class='img-prize' src='imgsAugust/prize" + (thisTime + 1) + ".png' alt='flop'>");
                                            $(e.currentTarget).removeClass('backdown').addClass('backup');
                                            timer = setTimeout(function () {
                                                $('.haveprize').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                                                $('.prize-info').html(itemPrizes[thisTime].introduce);
                                                $('.prize-name').css("backgroundImage",'url("'+itemPrizes[thisTime].itmeName+'")');
                                                $('.haveprize >div').addClass('show');

                                            }, 1000);
                                        }
                                    }
                                 }
                                main.addEventListener();
                                main.bodyhidden();

                            }else if(data.code == "200301" || data.code == "200302" || data.code == "200303" || data.code == "200304" || data.code == "200305"){
                                xxdBridge.open({pagename: 'poppage'});
                                xxdBridge.open({
                                    pagename: 'login',
                                });
                                return;
                            }
                            main.myPrize();
                        },
                        error: function () {
                            main.tip('网络异常请重试');
                        }
                    });
                    flag = true;
                }
            });
            //奖品弹框的关闭
            $('#closegame').click(function () {
                if (document.body.scrollWidth == 320 || main.support_css3_3d() == false) {
                    $(".turn-over").find('.img-prize').remove();
                    $this.removeClass('backup').addClass('backdown');
                } else {
                    $(".turn-over").removeClass('backup').addClass('backdown');
                }
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                flag = false;
            });
            //再来一次触发
            $('.one-more').on('click', function () {
                if (document.body.scrollWidth == 320 || main.support_css3_3d() == false) {
                    $(".turn-over").find('.img-prize').remove();
                    $this.removeClass('backup').addClass('backdown');
                } else {
                    $this.removeClass('backup').addClass('backdown');
                }
                $('body').removeClass('bodyhidden');
                $('.pop').removeClass('show').fadeOut(0);
                setTimeout(function () {
                    flag = false;
                    $this.trigger("click");
                }, 1000);
            });
            //无游戏机会关闭按钮
            $('#nochanceclose').click(function () {
                flag = false;
            });
            //本月无游戏机会关闭按钮
            $('#noChanceEver').click(function () {
                flag = false;
            });
            //底部投资按钮点击
            //新元宝
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
                if(uid == "" || uid == null){
                    xxdBridge.open({pagename: 'poppage'});
                    xxdBridge.open({
                        pagename: 'login',
                    });
                    return;
                }
                //理财列表页
                xxdBridge.open({
                    pagename: 'hotproduct',
                });
            });
            //步步高升
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
                if(uid == "" || uid == null){
                    xxdBridge.open({pagename: 'poppage'});
                    xxdBridge.open({
                        pagename: 'login',
                    });
                    return;
                }
                $.xxdAjax({
                    url: '/tradeCenter/investBiz/showStatus/BBGS',
                    dataType: 'json',
                    clientId: "XXD_INTEGRATION_PLATFORM",
                    type: 'get',
                    data: {},
                    callbacks: function (data) {
                        if(data.code == "200000"){
                            xxdBridge.open({
                                pagename: 'pddetail',
                                productIndex:'2',
                                productId:data.data.productId
                            });
                        }else if(data.code == "200301" || data.code == "200302" || data.code == "200303" || data.code == "200304" || data.code == "200305"){
                            xxdBridge.open({pagename: 'poppage'});
                            xxdBridge.open({
                                pagename: 'login',
                            });
                            return;
                        }
                    },
                    error: function () {
                        main.tip('网络异常请重试！');
                    }
                });
            });
            //月月派
            $('#yyp').click(function () {
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
                if(uid == "" || uid == null){
                    xxdBridge.open({pagename: 'poppage'});
                    xxdBridge.open({
                        pagename: 'login',
                    });
                    return;
                }
                //月月派的产品详情页面
                $.xxdAjax({
                    url: '/tradeCenter/investBiz/showStatus/YYP',
                    dataType: 'json',
                    clientId: "XXD_INTEGRATION_PLATFORM",
                    data    : {},
                    type    : 'GET',
                    callbacks: function (data) {
                        if (data.code == "200000") {
                            xxdBridge.open({
                                pagename: 'pddetail',
                                productIndex: '6',
                                productId: data.data.productId
                            });
                        }
                    },
                    error: function () {
                        main.tip('网络异常请重试！');
                    }
                });
            });
        },
        initAugust: function () {
            $.xxdAjax({
                url     : '/activityCenter/activityBase/initial',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                token   : com.getUrlValue('token'),
                data    : {
                    'activityCode': activityCode
                    // 'userToken'   : main.getToken()
                },
                type    : 'GET',
                callbacks: function (data) {
                    if(data.code == "200000"){
                        if(data.data.data){
                            activityStatus = data.data.data.activityStatus;
                            remainLotteryTimes = data.data.data.remainLotteryTimes;
                            usedLotteryTimes = data.data.data.usedLotteryTimes;
                            $('.game-times').html(data.data.data.remainLotteryTimes);
                        }
                    }
                },
                error: function () {
                    main.tip('网络异常请重试');
                }
            });
            $.xxdAjax({
                url     : '/activityCenter/activityBase/getJoinActivityUserAmount',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                data    : {
                    "activityCode": activityCode
                },
                type    : 'GET',
                callbacks : function (data) {
                    //console.log(data);
                    if(data.code == 200000){
                        var investNum = data.data.data;
                        $("#investNum").html(investNum);
                        //console.log(data.data.data);
                    }
                },
                error   : function (data) {
                    main.tip('网络异常请重试');
                }
            });

            //是否登录 return
            if(uid == "" || uid == null){
                $('.amountData').hide();
                $('.myChance').hide();
            }else{
                $('.amountData').show();
                $('.myChance').show();
            }
        },
        initprizeID: function () {
            $.xxdAjax({
                url     : '/activityCenter/activityBase/getMaretPrize',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                data    : {
                    "activityCode": activityCode
                },
                type    : 'GET',
                callbacks: function (data) {
                    if(data.code == "200000"){
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
            $.xxdAjax({
                url     : '/activityCenter/activityBase/getUserSumAnnualAmount',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                token   : com.getUrlValue('token'),
                data    : {
                    'activityCode': activityCode
                    // 'userToken'   : main.getToken()
                },
                type    : 'GET',
                callbacks: function (data) {
                    if(data.code == "200000"){
                        var data = (data.data.data.annualAmount).toFixed(2);
                        $('.sumAmount').html(data);
                    }

                }
            });
        },
        winning: function () {
            $.xxdAjax({
                url: '/activityCenter/activityBase/getLatest15AwardsList',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                data    : {
                    "activityCode": activityCode
                },
                type    : 'GET',
                callbacks: function (data) {
                    if(data.code == "200000"){
                        if (data.data.data.list) {
                            if (data.data.data.list.length > 0) {
                                $.each(data.data.data.list, function (i, item) {
                                    var time = new Date(item.addtime);
                                    var hours = (time.getHours()) < 10 ? ("0" + time.getHours()) : time.getHours();
                                    var getMinutes = (time.getMinutes()) < 10 ? ("0" + time.getMinutes()) : time.getMinutes();
                                    var getSeconds = (time.getSeconds()) < 10 ? ("0" + time.getSeconds()) : time.getSeconds();
                                    $('.winData ul').append('<li><span>' + item.username + '</span><span>' + hours + ':' + getMinutes + ':' + getSeconds + '</span><span>' + item.prizename + '</span></li>');
                                });
                                if($(".winData ul li").length >= 6){
                                    $('.myscroll').vTicker({
                                        speed: 500,
                                        pause: 2000,
                                        animation: 'fade',
                                        mousePause: false,
                                        showItems: 5
                                    });
                                }
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
            $.xxdAjax({
                url: '/activityCenter/activityBase/getMyAwardsList',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                token   : com.getUrlValue('token'),
                data    : {
                    "activityCode": activityCode,
                    "currentPage" : 1,
                    "pageSize"    : 6
                },
                type    : 'GET',
                callbacks: function (data) {
                    if (data.code == "200000") {
                        if (data.data.data) {
                            pageall = data.data.data.pages;
                            pageid = data.data.data.pageNum;
                            $('.winning-con ul').empty();
                            if (data.data.data.length == 0) {
                                $('.winning-con ul').html('').append('<li>您还未获得奖品，赶快投资参与活动吧~!</li>');
                                $('.winning-con ul li').addClass('noprizetip');
                                $('.page').fadeOut(0);
                                return;
                            }
                            $.each(data.data.data.list, function (i, item) {
                                $('.winning-con ul li').removeClass('noprizetip');
                                $('.page').fadeIn(0);
                                var time = new Date(item.addtime);
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
        oLogin: function () {
            scrollTop   = $(document).scrollTop();
            var thisTop = 'translateY(-' + $(document).scrollTop() + 'px)';
            // console.log(scrollTop);
            $('#user').removeClass('reg').addClass('login show');
            $('body').addClass('ban');
            $('#middle').css('transform', thisTop);

        },
        oReg: function () {
            $('#user').removeClass('login').addClass('reg show');
        },
        xUser: function () {
            $('#user').removeClass('login reg show');
            $('body').removeClass('ban');
            $('#middle').removeAttr('style');
            $(document).scrollTop(scrollTop)
        },
        //动画事件监听
        addEventListener: function () {
            for (var i = 0; i < $('.turn-over').length; i++) {
                $('.turn-over')[i].addEventListener("webkitTransitionEnd", function () {
                    if ($(this).hasClass('backdown')) {
                        $(this).find('.img-prize').remove();
                    }
                }, false)
            }
        },
        support_css3_3d: function () {
            var docElement = document.documentElement;
            var support = main.support_css3('perspective');
            var body = document.body;
            if (support && 'webkitPerspective' in docElement.style) {
                var style = document.createElement('style');
                style.type = 'text/css';
                style.innerHTML = '@media (transform-3d),(-webkit-transform-3d){#css3_3d_test{left:9px;position:absolute;height:3px;}}';
                body.appendChild(style);
                var div = document.createElement('div');
                div.id = 'css3_3d_test';
                body.appendChild(div);
                support = div.offsetLeft === 9 && div.offsetHeight === 3;
            }
            return support;
        },
        support_css3: function () {
            var div = document.createElement('div'),
                vendors = 'Ms O Moz Webkit'.split(' '),
                len = vendors.length;
            return function (prop) {
                if (prop in div.style) return true;
                prop = prop.replace(/^[a-z]/, function (val) {
                    return val.toUpperCase();
                });
                while (len--) {
                    if (vendors[len] + prop in div.style) {
                        return true;
                    }
                }
                return false;
            };
        }
    };
    main.init();
    return main;
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});