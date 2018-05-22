/**
 * Created by liuyao on 2017/2/8.
 */
require.config({
    paths: {
        'jquery': 'jquery.1.10.2',
        "jqmd5": "jquery.md5",
        'analytics_plugin':'../../../../js/utils/analytics_plugin'
    },
    shim: {
        "jqmd5": ["jquery"]
    },
    urlArgs: 'v=20161121'
});
require(['jquery', 'jqmd5','analytics_plugin'], function (jquery, md5) {
    //所有声明
    var step = 0,
        steps = [0],
        next = 0,
        overstep = false,   //超出一圈
        animateEnd = true,
        gameover = false,   //一圈游戏结束
        gameTimes = 0,    // 游戏机会
        curPosition = 0,   //初始化游戏位置
        initCode = 0,    //  判断活动开始或者结束时间
        timeLimit = false,  // 未开始和已结束的弹框节流阀
        clickPage = 0,
        prize24 = false,  //奖品名称
        bigPage = 0,
        prizeOk = false;
        link = "../../../../promotion/",
        linkextra = "?jsonpCallback=?";
    var timer;
    var actCode = 'goddess-activity';
    var webappUrl;
    var popularizeCode;
    var sinaParam = {
        appkey: '3921074379',
        source: '新新贷',
        content: 'utf-8'
    };
    var dataPosition = [
        {"transform": "translate(4.5rem,8.28rem)", "id": 0},
        {"transform": "translate(4.59rem,7.5rem)", "id": 1},
        {"transform": "translate(6.09rem,6.56rem)", "id": 2},
        {"transform": "translate(7.56rem,5.31rem)", "id": 3},
        {"transform": "translate(8rem,4.13rem)", "id": 4},
        {"transform": "translate(7.66rem,2.91rem)", "id": 5},
        {"transform": "translate(7.72rem,1.56rem)", "id": 6},
        {"transform": "translate(6.34rem,1.41rem)", "id": 7},
        {"transform": "translate(5.19rem,1.19rem)", "id": 8},
        {"transform": "translate(2.97rem,1.5rem)", "id": 9},
        {"transform": "translate(1.28rem,1.5rem)", "id": 10},
        {"transform": "translate(1.53rem,2.81rem)", "id": 11},
        {"transform": "translate(1.34rem,4.16rem)", "id": 12},
        {"transform": "translate(1.56rem,5.41rem)", "id": 13},
        {"transform": "translate(2.88rem,6.59rem)", "id": 14},
        {"transform": "translate(4.53rem,6.41rem)", "id": 15},
        {"transform": "translate(6.25rem,5.31rem)", "id": 16},
        {"transform": "translate(5.88rem,4.16rem)", "id": 17},
        {"transform": "translate(6.25rem,2.88rem)", "id": 18},
        {"transform": "translate(4.59rem,2.88rem)", "id": 19},
        {"transform": "translate(2.81rem,2.88rem)", "id": 20},
        {"transform": "translate(2.81rem,4.13rem)", "id": 21},
        {"transform": "translate(2.97rem,5.31rem)", "id": 22},
        {"transform": "translate(4.47rem,5.31rem)", "id": 23},
        {"transform": "translate(3.91rem,4.16rem)", "id": 24}
    ];
    var main = {
        init: function () {
            window.$ = jquery;
            main.eventBind();
            main.initgoddessPage();
            //GA、XA布码
            $.ajax({
                url: '../../../../user/getCurrentUser.do',
                data: {},
                dataType: 'json',
                success: function (data) {
                    if(data.code == 0){
                        var userId = data.data.user.userId;
                    }else{
                        var userId ="";
                    }
                    // function clickXaEvent(xa_pro,ga_xa_pro) {
                    //     XXD_TRACK._trackEvent({
                    //         category: "webapp_goddess_reg",
                    //         action: xa_pro,
                    //         label: ga_xa_pro,
                    //         value:userId,
                    //         custval: ""
                    //     });
                    // }
                    function clickGaEvent(ga_xa_pro) {
                        ga('send', 'event','女神节活动', ga_xa_pro, window.location.href);
                    }
                    $(".ga-xa-event").on("click", function () {
                        var $this = $(this);
                        var ga_xa_pro = $this.attr('ga_xa_pro');
                        //var xa_pro = $this.attr("xa_pro");
                        clickGaEvent(ga_xa_pro);
                        //clickXaEvent(xa_pro,ga_xa_pro);
                    });
                    //XXD_TRACK.track_pageview('goddess');
                    gaInits(userId);
                    growingIOInits({userId:userId});
                }
            });
        },
        eventBind: function () {
            //移动端弹框弹出的时候底部不可滚动
            $('.all-popup').bind("touchmove",function(e){
                e.preventDefault();
            });
            $('.mask').bind("touchmove",function(e){
                e.preventDefault();
            });
            //立即登录按钮
            $(".register").click(function () {
                main.register();
            });
            //验证码
            $("#VerifyCode").click(function () {
                $("#VerifyCode").attr("src", "../../../../randCode/createVerifyCode.do?" + Math.random());
            });
            /*攻略弹窗*/
            var isNone = false;
            $('.raiders').on('click', function () {
                if (isNone) {
                    $('body').animate({'scrollTop': 0}, 300);
                    $('.popup-raiders').fadeIn(200);
                    $('.attention').fadeOut(200);
                    isNone = false;
                } else {
                    $('.attention').fadeIn(200);
                    isNone = true;
                }
            });
            /*注意事项按钮关闭,如果是多类名，就不能这样写了*/
            $('section,header,.share,.check-price,.ready-go').on('touchstart', function (event) {
                var classList = event.target.className;
                if(classList != 'attention' && classList != 'raiders' ) {
                    $('.attention').fadeOut(200);
                    isNone = false;
                }
            });
            /*注意事项弹窗*/
            $('.attention').on('click', function () {
                $('body').animate({'scrollTop': 0}, 300);
                $('.popup-attention').fadeIn(200);
            });
            /*弹窗关闭按钮*/
            $('.close-popup').on('click', function () {
                $('.all-popup,.popup-attention,.popup-raiders,.userLogin-box').fadeOut(200);
                $(".all-popup>div:not('.mask')").fadeOut(200);
            });
            $('.know-popup').on('click', function () {
                $('.all-popup,.popup-attention,.popup-raiders,.userLogin-box').fadeOut(200);

            });
            //开始游戏按钮
            $('.ready-go').on('click', function () {
                //活动未开始和已结束
                if (initCode == -1) {
                    $('.prize-show').hide(0);
                    main.timesLess();
                    return;
                }
                if (initCode == -2) {
                    $('.prize-show').hide(0);
                    main.timesThan();
                    return;
                }
                if (!main.isLogin()) {
                    main.signUp();
                    return;
                }
                if (gameover == true) {
                    main.praise();
                    return;
                }
                overstep = false;
                prize24 = false;
                if (gameTimes == 0) {
                    main.noChance();
                    return;
                }
                //节流阀
                else if (animateEnd === true) {
                    animateEnd = false;
                    main.gameStart();
                }
            });
            //最后一步弹框按钮, 先出现奖励1钻戒，用户点击任意处再出现奖励88新新币弹窗
            $('body').on('click', function () {
                if (gameover == true) {
                    main.praise();
                }
            });
            //奖品点击按钮
            $('.check-price').on('click', function () {
                //活动未开始和已结束
                if (initCode == -1) {
                    $('.prize-show').hide(0);
                    main.timesLess();
                    return;
                }
                if (initCode == -2) {
                    $('.prize-show').hide(0);
                    main.timesThan();
                    return;
                }
                if (!main.isLogin()) {
                    main.signUp();
                    return;
                } else {
                    main.myPrize();
                }
            });
            //我的奖品中弹框上下页按钮
            var pageFlagtop = true,
                pageFlagbot = true;
            if(pageFlagtop){
                $('.page-up').on('click', function () {
                    pageFlagtop = false;
                    if (clickPage == 0) {
                        if($('.pagebot').css('display','none')){
                            $('.pagetop').fadeIn(200,function () {
                                setTimeout(function () {
                                    $('.pagetop').fadeOut(200,function () {
                                        pageFlagtop = true;
                                        return;
                                    });
                                },1500);
                            });
                        }
                        return;
                    } else {
                        $('.price-list ul').animate({
                            top: 2.34 * ++clickPage + 'rem'
                        }, 300);
                    }
                });
            }
            if(pageFlagbot){
                $('.page-down').on('click', function () {
                    pageFlagbot = false;
                    if (clickPage == (1 - Math.ceil(bigPage / 5))) {
                        if($('.pagetop').css('display','none')){
                            $('.pagebot').fadeIn(200,function () {
                                setTimeout(function () {
                                    $('.pagebot').fadeOut(200,function () {
                                        pageFlagbot = true;
                                        return;
                                    });
                                },1500);
                            })
                        }
                        return;
                    } else {
                        $('.price-list ul').animate({
                            top: 2.34 * --clickPage + 'rem'
                        }, 300);
                    }
                });
            }
            //每10分钟刷新一次页面
            setInterval(function () {
                window.location.reload();
            }, 600000);
            //以及分享的各种按钮
            var isShare = true;
            $('.share').on('click', function () {
                //活动未开始和已结束
                if (initCode == -1) {
                    $('.prize-show').hide(0);
                    main.timesLess();
                    return;
                }
                if (initCode == -2) {
                    $('.prize-show').hide(0);
                    main.timesThan();
                    return;
                }
                if (!main.isLogin()) {
                    main.signUp();
                    return;
                }
                $.ajax({
                    url: '../../../../popularize/isJoinActivity.do',
                    data: {
                        activityCode: actCode
                    },
                    dataType: 'json',
                    type: 'GET',
                    success: function (result) {
                        var isJoinActivity = result.isJoinActivity;
                        if (isJoinActivity != undefined && isJoinActivity == "true") {
                            main.loadpopularize();
                        } else {
                            main.userJoinActivity();
                        }
                    }
                });
                if(isShare){
                    isShare = false;
                    $(".join").show(0,function () {
                        setTimeout(function () {
                            isShare = true;
                        },100);
                    });
                }
            });
            $('.share-popup').on('click',function () {
                $('.all-popup').hide(0);
                $.ajax({
                    url: '../../../../popularize/isJoinActivity.do',
                    data: {
                        activityCode: actCode
                    },
                    dataType: 'json',
                    type: 'GET',
                    success: function (result) {
                        var isJoinActivity = result.isJoinActivity;
                        if (isJoinActivity != undefined && isJoinActivity == "true") {
                            main.loadpopularize();
                        } else {
                            main.userJoinActivity();
                        }
                    }
                });
                $(".join").show();
            });
            $(".join-cancel").on("click", function () {
                $(".join").hide();
            });
            $(".join-wei").on("click", function () {
                if (popularizeCode == undefined || popularizeCode == '') {
                    alert("抱歉，获取不到您的推广码，请返回重新进入页面尝试");
                    return;
                }
                var shareUrl = [];
                shareUrl.push("http://v.t.sina.com.cn/share/share.php");
                shareUrl.push("?appkey=");
                shareUrl.push(sinaParam.appkey);
                shareUrl.push("&source=");
                shareUrl.push(sinaParam.source);
                shareUrl.push("&content=");
                shareUrl.push(sinaParam.content);
                shareUrl.push("&url=");
                var url = webappUrl + "/static/html/activity/goddessWEBAPP/invitation.html?uuid=" + popularizeCode + "&actCode=" + actCode;
                url = escape(url);
                shareUrl.push(url);
                shareUrl.push("&title=");
                shareUrl.push("【新新贷】女神节一起来唤醒女神吧！还有周大福钻戒、香奈儿口红、SK-II等豪礼相送哟！");
                shareUrl.push("&pic=");
                shareUrl.push(webappUrl + "/static/html/activity/goddessWEBAPP/img/share2.jpg");
                window.open(shareUrl.join(''));
            });
            $(".join-copy").on("click", function () {
                var share = webappUrl + "/static/html/activity/goddessWEBAPP/invitation.html?uuid=" + popularizeCode + "&actCode=" + actCode;
                $("#alert2_title").html("我的专属链接");
                $("#alert2_cantent").html(share);
                $("#alert2").show();
                $(".layer").show();
                $(".join").hide();
            });
            $("#alert2_close").click(function () {
                $("#alert2").hide();
                $(".layer").hide();
            });
            $(".join-note").click(function () {
                if (popularizeCode == undefined || popularizeCode == '') {
                    alert("抱歉，获取不到您的推广码，请返回重新进入页面尝试");
                    return;
                }
                var share = webappUrl + "/static/html/activity/goddessWEBAPP/invitation.html?uuid=" + popularizeCode + "&actCode=" + actCode;
                share = escape(share);
                var content = "【新新贷】女神节一起来唤醒女神吧！还有周大福钻戒、香奈儿口红、SK-II等豪礼相送哟！戳>>  " + share;
                var symbol = "";
                var u = navigator.userAgent;
                if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {
                    symbol = "?";
                } else if (u.indexOf('iPhone') > -1) {
                    if (u.match(/OS [4-7]_\d[_\d]* like Mac OS X/i)) {
                        //ios8以下的系统
                        symbol = ";"
                    } else {
                        symbol = "&"
                    }
                }
                var url = "sms:" + symbol + "body=" + content;
                window.location.href = url;
            });
            $(".alert_close").on("click", function () {
                $(".prize-alert").hide();
            });
        },
        userJoinActivity: function () {
            $.ajax({
                url: '../../../../popularize/userJoinActivity.do',
                data: {
                    activityCode: actCode
                },
                dataType: 'json',
                type: 'POST',
                success: function (result) {
                    if (result.resultCode == 0) {
                        main.loadpopularize();
                    } else {
                        //alert(result.message);
                        main.signUp();
                        return;
                    }
                }
            });
        },
        isLogin: function () {
            var result = false;
            $.ajax({
                url: "../../../../user/isLogin.do?" + new Date().getTime(),
                dataType: 'json',
                async: false,
                data: {},
                success: function (data) {
                    if (data.isLogin != null) {
                        result = data.isLogin;
                    }
                }
            });
            return result;
        },
        loadpopularize: function () {
            if (!main.isLogin()) {
                return;
            }
            $.ajax({
                url: '../../../../popularize/invitationData.do',
                data: {
                    activityCode: actCode
                },
                dataType: 'json',
                success: function (data) {
                    webappUrl = data.webappUrl;
                    popularizeCode = data.popularizeCode;
                }
            });
        },
        register: function () {
            var login_username = $.trim($("#login_username").val());
            var login_username_error = $("#login_username_error");
            if (login_username == '') {
                login_username_error.html("请输入您的用户名");
                login_username_error.show();
                return false;
            }
            login_username_error.hide();
            var login_pwd = $.trim($("#login_pwd").val());
            var login_pwd_error = $("#login_pwd_error");
            if (login_pwd == '') {
                login_pwd_error.html("请输入您的登录密码");
                login_pwd_error.show();
                return false;
            }
            login_pwd_error.hide();
            var login_img = $.trim($("#login_img").val());
            var login_img_error = $("#login_img_error");
            if (login_img == '') {
                login_img_error.html("请输入图片验证码");
                login_img_error.show();
                return false;
            }
            login_img_error.hide();
            $.ajax({
                url: '../../../../user/login.do',
                data: {
                    imgCode: login_img,
                    username: login_username,
                    password: $.md5($.md5(login_pwd))
                },
                dataType: 'json',
                type: 'POST',
                timeout: 10000,
                success: function (result) {
                    if (result.resultCode == 0) {
                        window.location.reload();
                    } else {
                        var login_img_error = $("#login_img_error");
                        var msg;
                        if (result.resultCode == -1) {
                            msg = "用户名称不存在，请重新输入";
                        } else if (result.resultCode == -2) {
                            msg = "密码错误，请重新输入";
                        } else if (result.resultCode == -3 || result.resultCode == -4) {
                            msg = result.msg;
                        } else if (result.resultCode == -9) {
                            msg = "登录失败，请重新尝试或者联系客服";
                        } else {
                            msg = result.msg;
                        }
                        login_img_error.html(msg);
                        login_img_error.show();
                    }
                }
            });
        },
        initgoddessPage: function () {
            return new Promise(function (resovle, reject) {
                $.ajax({
                    url: link + "initThanksGivingDayPage.do" + linkextra,
                    type: "get",
                    dataType: "jsonp",
                    jsonp: "jsonpCallback",
                    success: function (json) {
                        initCode = json.resultCode;
                        if (initCode == -1) {
                            $('.prize-show').hide(0);
                        }
                        if (initCode == -2) {
                            $('.prize-show').hide(0);
                        }
                        //有多少次游戏机会、邀请好友的显示隐藏
                        if (!main.isLogin()) {
                            $('.times').hide();
                            $('.inviteFriend').hide();
                        } else {
                            $('.times').show();
                            $('.inviteFriend').show();
                        }
                        //游戏机会次数
                        gameTimes = json.remainChance;
                        if (json.isLogin != undefined && json.isLogin == 1) {
                            $(".game-times").html(json.remainChance == undefined ? 0 : json.remainChance);
                            $(".invite").html(json.inviteCount == undefined ? 0 : json.inviteCount);
                            $(".investment").html(json.inviteTenderCount == undefined ? 0 : json.inviteTenderCount);
                        }
                        //初始化游戏花朵的位置
                        if (json.curPosition != undefined) {
                            //初始化设置光标所在位置
                            curPosition = json.curPosition;
                            step = curPosition;
                            $('.rose').css(dataPosition[step]);
                            steps.push(step);
                        }
                        //最新15个用户的获奖情况
                        if (json.prizeRecordsList != undefined) {
                            $('.prize-show').fadeIn(0);
                            $(".result ul").html("");
                            clearInterval(timer);
                            $.each(json.prizeRecordsList, function (index, item) {
                                $('.prize-show ul').append('<li>用户  ' + item.nickName + '</li>');
                                $('.prize-show ul').append('<li>刚获得  ' + item.prizeName + '</li>');
                            })
                            var index = 0;
                            timer = setInterval(function () {
                                if (index >= json.prizeRecordsList.length - 1) {
                                    index = 0;
                                    $('.prize-show ul').css({'top': 0 + 'rem'});
                                } else {
                                    index++;
                                    $('.prize-show ul').animate({'top': -1.171875 * index + 'rem'}, 200);
                                }
                            }, 2000);
                        }else{
                            $('.prize-show').fadeOut(0);
                        }
                        //投资前五名排行榜
                        if (json.tenderInfoList != undefined) {
                            var t = 0;
                            $(".rank ul").html("");
                            $.each(json.tenderInfoList, function (index, item) {
                                t++;
                                $('.rank ul').append("<li><span>" + t + ".&nbsp;用户" +
                                    item.nickName + "</span><span>年化投资额</span><span>" +
                                    parseFloat(item.account).toFixed(2) + "元</span></li>");
                            })
                        }
                        return resovle();
                    },
                    error: function (e) {
                        return reject(e);
                    }
                })
            });
        },
        gameStart: function () {
            $.ajax({
                url: link + "joinTanksGivingDayActivity.do" + linkextra,
                type: "get",
                dataType: "jsonp",
                jsonp: "jsonpCallback",
                success: function (res) {
                    if (initCode == -1) {
                        $('.prize-show').hide(0);
                        main.timesLess();
                        return;
                    }
                    if (initCode == -2) {
                        $('.prize-show').hide(0);
                        main.timesThan();
                        return;
                    }
                    if(res.resultCode == -1){
                        $('.all-popup').fadeIn(200);
                        $(".all-popup .popup:not('.popup-result')").fadeOut(200);
                        $('.result-msg').html(res.resultMsg);
                        $('.popup-result').fadeIn(0);
                        $('.close-result,.know-result').on('click',function () {
                            $('.all-popup').fadeOut(0,function () {
                                window.location.reload();
                            })
                        });
                        return;
                    }
                    if (res.resultCode == 0) {
                        //每点击一次就再初始化游戏位置
                        curPosition = res.resultData.curPosition;
                        step = curPosition;
                        $('.rose').css(dataPosition[step]);
                        steps.push(step);
                        next = res.resultData.curPoint;
                        step += next;
                        if (step > 24) {
                            step = 24;
                            overstep = true;
                        }
                    }
                    $('body').animate({'scrollTop': $('.content')[0].offsetTop - 25}, 300);
                    main.showDice(next);
                    steps.push(step);
                    $('.popup-dice').fadeIn(200).parent().delay(1000).fadeOut(200, function () {
                        main.gameTo(steps[steps.length - 1], steps[steps.length - 2])
                            .then(function (from) {
                                switch (from) {
                                    case 2:
                                        step += 1;
                                        steps.push(step);
                                        return main.forward('前进1步').then(function () {
                                            return main.gameTo(steps[steps.length - 1], steps[steps.length - 2]);
                                        });
                                    case 7:
                                        step -= 3;
                                        steps.push(step);
                                        return main.forward('后退3步').then(function () {
                                            return main.gameTo(steps[steps.length - 1], steps[steps.length - 2]);
                                        })
                                    case 14:
                                        step -= 4;
                                        steps.push(step);
                                        return main.forward('后退4步').then(function () {
                                            return main.gameTo(steps[steps.length - 1], steps[steps.length - 2]);
                                        })
                                    case 20:
                                        step += 2;
                                        steps.push(step);
                                        return main.forward('前进2步').then(function () {
                                            return main.gameTo(steps[steps.length - 1], from);
                                        })
                                    default:
                                        return Promise.resolve(from);   //其他情况直接全部往前走就行了
                                        break;
                                }
                            })
                            .then(function (from) {
                                animateEnd = true;
                                if (overstep == true) {
                                    step = 0;
                                    steps.push(step);
                                    $('.rose').css(dataPosition[0]);
                                    main.praise();
                                }
                                else if (from == 24) {
                                    gameover = true;
                                    $('.rose').css(dataPosition[0]);
                                    setTimeout(function () {
                                        main.price();
                                    }, 500);
                                    step = 0;
                                    steps.push(step);
                                    prize24 = true;
                                }
                                else {
                                    setTimeout(function () {
                                        main.price();
                                    }, 500);
                                }
                                setTimeout(function () {
                                    main.initgoddessPage();
                                }, 300);
                            });
                    });
                }
            });
        },
        //前进后退奖励框奖励框
        forward: function (stepNumber) {
            return new Promise(function (resovle, reject) {
                $('.all-popup').fadeIn(200)
                    .find('.popup:not(".popup-forward")').hide();
                $('.popup-forward').find('.forword').html(stepNumber);
                $('.popup-forward').fadeIn(200).parent().delay(1000).fadeOut(resovle);
            })
        },
        //奖励框
        price: function () {
            $.ajax({
                url: link + "myPrize.do" + linkextra,
                type: "get",
                dataType: "jsonp",
                jsonp: "jsonpCallback",
                success: function (res) {
                    $('.all-popup').fadeIn(200)
                        .find('.popup:not(".popup-onceMore")').hide();
                    if(prize24){
                        $('.popup-onceMore').fadeIn(200).find('span').html(res.resultData[1].prizeName);
                    }else{
                        $('.popup-onceMore').fadeIn(200).find('span').html(res.resultData[0].prizeName);
                    }
                }
            })
        },
        myPrize: function () {
            $.ajax({
                url: link + "myPrize.do" + linkextra,
                type: "get",
                dataType: "jsonp",
                jsonp: "jsonpCallback",
                success: function (json) {
                    bigPage = json.prizeCount;
                    if (json.resultCode == 0) {
                        $(".price-list ul").html("");
                        if (json.resultData == undefined || json.resultData.length == 0) {
                            $('.all-popup').fadeIn(200);
                            $(".all-popup .popup:not('.popup-price-no')").fadeOut(200);
                            $('.popup-price-no').fadeIn(200);
                        } else {
                            $.each(json.resultData, function (index, item) {
                                $(".price-list ul").append("<li><span>" + item.addTime + "&nbsp;&nbsp;</span><span>获得了</span><span>" + item.prizeName + "</span></li>");
                            });
                            $('.all-popup').fadeIn(200);
                            $(".all-popup .popup:not('.popup-price')").fadeOut(200);
                            $('.popup-price').fadeIn(200);
                        }
                    }
                    prizeOk = true;
                }
            });
        },
        //弹出登录提示框
        signUp: function () {
            $('.all-popup').fadeIn(200)
                .find('.popup:not(".popup-promptLogin")').hide();
            $('.popup-promptLogin').fadeIn(200);
            $('.sign-user').on('click', function () {
                $('body').animate({'scrollTop': 0}, 200);
                $('.all-popup').fadeOut(0);
                $('.userLogin-box').fadeIn(200);
            });
        },
        //游戏前进后退
        gameTo: function (to, from) {
            return new Promise(function (resolve, reject) {
                var step = (to - from) > 0 ? 1 : -1, //1前进，-1后退
                    index = from || 0;    //默认是 0
                var timer = setInterval(function () {
                    if ((to - index) * step > 0) {
                        index += step;
                        $('.rose').css(dataPosition[index]);
                    } else {
                        clearInterval(timer);
                        resolve(to);
                    }
                }, 300);
            })
        },
        //骰子点数弹框
        showDice: function (num) {
            $('.step-number').html(num);
            $('.popup:not(".popup-dice")').hide();
            $('.all-popup').fadeIn(200);
        },
        //一圈结束，获得88新新币弹框
        praise: function () {
            $('.all-popup').fadeIn(200);
            $(".all-popup .popup:not('.popup-praise')").fadeOut(200);
            $('.popup-praise').fadeIn(200);
            gameover = false;
        },
        //没有游戏机会了
        noChance: function () {
            $('.all-popup').fadeIn(200);
            $(".all-popup .popup:not('.popup-noChance')").fadeOut(200);
            $('.popup-noChance').fadeIn(200);
        },
        //活动未开始
        timesLess: function () {
            if (!timeLimit) {
                timeLimit = true;
                $('.times-less').show(100, function () {
                    setTimeout(function () {
                        $('.times-less').hide();
                        timeLimit = false;
                    }, 2000);
                });
            }
        },
        //活动已结束
        timesThan: function () {
            if (!timeLimit) {
                timeLimit = true;
                $('.times-than').show(100, function () {
                    setTimeout(function () {
                        $('.times-than').hide();
                        timeLimit = false;
                    }, 2000);
                });
            }
        },
    };
    main.init();
    return main;
})