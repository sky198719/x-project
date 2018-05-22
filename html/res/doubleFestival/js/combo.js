require(['base', 'float', 'allRoll', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs"], function ($, float, allRoll, track, store) {

    // 布码init
    var userDO = store && store.get("userDO") || {};
    track.init(userDO);

    var activityCode = 'DoubleDan-activity', clientId = 'XXD_ACTIVITY_H5_PAGE';
    var activityStatus,       //活动状态
        token = $.readCookie('Token'),
        remainLotteryTimes;   //剩余抽奖次数

    //获取抽奖次数
    function getChance() {
        $.xxdAjax({
            url: '/activityCenter/doubleDanActivityBase/initial?activityCode=' + activityCode,
            clientId: clientId,
            type: 'get',
            token: token,
            data: {},
            callbacks: function (data) {
                if (data.code == "200000") {
                    if (data.data.data) {
                        remainLotteryTimes = data.data.data.remainLotteryTimes;
                        if (activityStatus == 0) {
                            $('.J_chance i').html(remainLotteryTimes);
                        }
                    }
                }
            },
            error: function (r) {
                console.error(r.code);
            }
        });
    }

    //获取当前新元宝标的
    $('body').on('click','.J_clickLeft',function () {
        $.xxdAjax({
            url: '/activityCenter/doubleDanActivityBase/getActivitySchemeList',
            clientId: clientId,
            type: 'get',
            data: {},
            callbacks: function (data) {
                if (data.code == "200000") {
                    if (data.data) {
                        if (data.data.oneMonthScheme == -1) {
                            alert('没有可投的新元宝活动标与普通标');
                        } else {
                            window.open('/xplan/detail/' + data.data.oneMonthScheme + '.html');
                        }
                    }
                }
            },
            error: function (r) {
                console.error(r.code);
            }
        });
    });
    $('body').on('click','.J_clickRight',function () {
        $.xxdAjax({
            url: '/activityCenter/doubleDanActivityBase/getActivitySchemeList',
            clientId: clientId,
            type: 'get',
            data: {},
            callbacks: function (data) {
                if (data.code == "200000") {
                    if (data.data) {
                        if (data.data.twelveMonthScheme == -1) {
                            alert('没有可投的新元宝活动标与普通标');
                        } else {
                            window.open('/xplan/detail/' + data.data.twelveMonthScheme + '.html');
                        }
                    }
                }
            },
            error: function (r) {
                console.error(r.code);
            }
        });
    })

    //奖品渲染
    function allPrizes() {
        $.xxdAjax({
            url: '/activityCenter/doubleDanActivityBase/getLatestMaterailAwardsList',
            clientId: clientId,
            type: 'get',
            data: {
                'activityCode': activityCode,
                'recordNum': 35
            },
            callbacks: function (data) {
                if (data && (data.code == "200000")) {
                    var allList = data.data.prize, allPrizestext = '';
                    if (allList != '') {
                        if (allList.length >= 3) {
                            for (var i = 0; i < allList.length; i++) {
                                allPrizestext += '<li><span>' + allList[i].username + '</span>&nbsp;&nbsp;<span>' + allList[i].prizename + '</span></li>'
                            }
                            $("#J_winnerLists").html(allPrizestext);
                            //无缝滚动
                            $("#J_winGamers").marquee({
                                direction: "left",
                                speed: 30
                            });
                        } else {
                            for (var i = 0; i < allList.length; i++) {
                                allPrizestext += '<li><span>' + allList[i].username + '</span>&nbsp;&nbsp;<span>' + allList[i].prizename + '</span></li>'
                            }
                            $("#J_winnerLists").html(allPrizestext);
                        }

                    } else {
                        $("#J_winnerLists").html('<li style="text-align:center;width:100%">暂无数据</li>');
                    }
                }
            },
            error: function (r) {
                console.error(r.code);
            }
        });
    }

    //旋转
    $('.golden-box >div').on('mouseenter', function () {
        var $front = $(this).find('.golden-egg'), $behind = $(this).find('.QR-code'), speed = 80, dis = 210;
        $front.animate({left: '-26px', width: 0}, speed, function () {
            $front.hide();
            $behind.show().animate({left: 0, width: dis}, speed);
        });
    });
    $('.golden-box >div').on('mouseleave', function () {
        var $front = $(this).find('.golden-egg'), $behind = $(this).find('.QR-code'), speed = 80, dis = 210;
        $behind.animate({left: dis / 2, width: 0}, speed, function () {
            $behind.hide();
            $front.show().animate({left: 0, width: dis}, speed);
        });
    });
    //判断是否登录及跳转
    function isLogin() {
        var result = false;
        $.ajax({
            type: 'GET',
            url: '/feapi/users/loginInfo' + '?userToken=' + token,
            async: false,
            data: {},
            dataType: 'json',
            success: function (str) {
                if (str.code == "200000") {
                    if (str.data.status.code == 200) {
                        result = true;
                    }
                }
            },
            error: function () {
                float.alert({content: msg.errorMsg});
            }
        });
        return result;
    }


    getStatus();

    //获取活动状态
    function getStatus() {
        $.xxdAjax({
            url: '/activityCenter/doubleDanActivityBase/getActivityStatus?activityCode=' + activityCode,
            clientId: clientId,
            type: 'get',
            data: {},
            callbacks: function (data) {
                if (data.code == "200000") {
                    if (data.data.data) {
                        activityStatus = data.data.data.activityStatus;
                        if (activityStatus == 0) {
                            //活动中
                            allPrizes();
                            getChance();
                            if (!isLogin()) {
                                $('.J_chanceLogin').removeClass('hide');
                                $('.J_chance').addClass('hide');
                            }
                            $('.J_btnLeft').addClass('J_clickLeft');
                            $('.J_btnRight').addClass('J_clickRight');
                        } else if (activityStatus == 1) {
                            //已结束
                            $('.J_chance').html('活动已结束');
                            $('.J_btnLeft,.J_btnRight').addClass('finish');
                            allPrizes();
                        } else {
                            //未开始
                            $('.J_chance').html('有<i>?</i>次砸蛋机会');
                            $('.J_btnLeft,.J_btnRight').addClass('no-begin');

                            $("#J_winnerLists").html('<li style="text-align:center;width:100%">活动未开始</li>').parent().css('text-align', 'center');
                        }
                    }
                }
            },
            error: function (r) {
                console.error(r.code);
            }
        });
    }

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
