require.config({
    paths: {
        "jquery": "jquery.1.10.2",
        "flexible": "flexible"
    },
    urlArgs: 'v=20161121'
})
require(["jquery", "flexible"], function (jquery, flexible) {
    var link = "../../../../promotion/";
    var linkextra = "?jsonpCallback=?";
    var w;
    var l;
    var P;
    var x;
    var n;
    var i = 0;
    var t1;
    var t7;
    var time1;
    var timer7;
    var timer14;
    var timer20;
    var flag = true;
    var uid;
    var sign;
    var webappUrl;
    var popularizeCode;
    var actCode = 'Thanksgiving-Day';
    var isLogin = 0;
    var main = {
        init: function () {
            window.$ = jquery;
            uid = main.GetQueryString("uid");
            sign = main.GetQueryString("sign");
            //alert("uid=" + uid + ",sign=" + sign);
            main.checkSign();
            main.eventBind();
            main.initThanksGivingDayPage();
        },

        loadpopularize: function () {
            $.ajax({
                url: '../../../../popularize/invitationData.do',
                data: {
                    activityCode: actCode
                },
                dataType: 'json',
                async: false,
                success: function (data) {
                    webappUrl = data.webappUrl;
                    popularizeCode = data.popularizeCode;
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
        checkSign: function () {
            var result = false;
            $.ajax({
                url: '../../../../memberDay922/checkAppSign.do',
                data: {
                    uid: uid,
                    sign: sign
                },
                type: "get",
                async: false,
                dataType: 'json',
                success: function (result) {
                    if (result.resultCode == 0) {
                        result = true;
                    } else if (result.resultCode == 400) {
                        result = false;
                    } else {
                        alert("页面鉴权失败，请返回重新尝试或先登录");
                        //window.location.href = "xxd://login";
                    }
                }
            });
            return result;
        },
        GetQueryString: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return  decodeURI(r[2]);
            return null;
        },
        eventBind: function () {
            //点击已登录时候的按钮开始游戏
            $(".yclick").on("click", function () {
                clearInterval(time1);
                clearInterval(timer7);
                clearInterval(timer14);
                clearInterval(timer20);

                if (isLogin != 1) {
                    window.location.href = "xxd://login";
                    return;
                }

                var actvity = $(".yclick").data("actvity");
                if (actvity != '0') {
                    return;
                }

                var isSub = $(".yclick").attr("data-isSub");
                if (isSub == "true") {
                    return;
                }

                $.Deferred().done(function () {
                    main.gameStart();
                    $(this).unbind("click");
                }, function () {
                    $(".yclick").bind("click");
                }).resolve();
            });
            $(".strategy").on("click", function () {
                $(".strategy-con").show();
            })
            $(".base_close").on("click", function () {
                $(".strategy-con").hide();
            })

            $(".yprize").on("click", function () {
                $(".prize-con").show();
            })
            $(".prize_close").on("click", function () {
                $(".prize-con").hide();
            })
            $(".alert_close").on("click", function () {
                $(".prize-alert").hide();
            })
            $(".friend").on("click", function () {
                main.share_weibo();
            });
            $(".chance_share").click(function () {
                main.share_weibo();
            });

            $(".wprize").on("click", function () {
                main.myPrize();
            });
            $(".chance-close").click(function () {
                $(".no-chance").hide();
            });
        },
        share_weibo: function () {
            if (isLogin != 1) {
                window.location.href = "xxd://login";
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
                        main.share();
                    } else {
                        main.userJoinActivity();
                    }
                }
            });
        },
        share: function () {
            if (popularizeCode == undefined || popularizeCode == '') {
                alert("抱歉，获取不到您的推广码，请返回重新进入页面尝试");
                return false;
            }
            var title = "有只逆天的火鸡在！暴！走！";
            var dec = "我正在这里助它一臂之力，快快加入一起来！";
            var img = webappUrl + "/static/html/activity/thanksgivingWebapp/img/share2.jpg";
            img = escape(img);
            var shareurl = webappUrl + "/static/html/activity/thanksgivingWebapp/invitation.html?uuid=" + popularizeCode + "&actCode=" + actCode;
            shareurl = escape(shareurl);
            var url = "xxd://home/web?url=xxx&isshare=1&title=" + title + "&des=" + dec + "&img=" + img + "&shareurl=" + shareurl;
            // console.log(url);
            //alert(url);
            window.location.href = url;
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
                        main.share();
                    } else {
                        alert(result.message);
                    }
                }
            });
        },
        initThanksGivingDayPage: function () {
            //判断是否登录,最新的15个用户的获奖情况以及排行榜前五名
            $.ajax({
                url: link + "initThanksGivingDayPage.do" + linkextra,
                type: "get",
                dataType: "jsonp",
                jsonp: "jsonpCallback",
                success: function (json) {
                    if (json.resultCode == 0) {
                        $(".yclick").html("点击投掷");
                        $(".yclick").attr("data-actvity", "0");
                    } else if (json.resultCode == -1) {
                        $(".yclick").html("活动未开始");
                        $(".yclick").attr("data-actvity", "1");
                    } else if (json.resultCode == -2) {
                        $(".yclick").html("活动已结束");
                        $(".yclick").attr("data-actvity", "2");
                    }

                    if (uid != undefined && sign != undefined && json.isLogin != undefined && json.isLogin == 1) {
                        isLogin = json.isLogin;
                        $(".rest").html(json.remainChance == undefined ? 0 : json.remainChance);
                        $(".invite").html(json.inviteCount == undefined ? 0 : json.inviteCount);
                        $(".login").html(json.inviteTenderCount == undefined ? 0 : json.inviteTenderCount);
                        $(".chance").show();
                        $(".share").show();
                    }

                    if (json.prizeList != undefined) {
                        var a = 0;
                        $(".kuaier").html("");
                        $.each(json.prizeList, function (index, item) {
                            a++;
                            $(".kuaier").append("<li value=" + item.id + " class='kuaier" + a + "'><i class='singal'></i></li>");
                        })
                    }

                    if (json.curPosition != undefined) {
                        //初始化设置光标所在位置
                        var o = parseInt(json.curPosition);
                        if (o == 0) {
                            $(".singal").hide();
                        } else {
                            $(".kuaier").find("li").eq(o - 1).find(".singal").show();
                        }
                    }

                    if (json.prizeRecordsList != undefined) {
                        //最新15个用户的获奖情况
                        var x = 0;
                        $(".result ul").html("");
                        $.each(json.prizeRecordsList, function (index, item) {
                            $(".result ul").append("<li><span>用户" + item.nickName + "掷中了&nbsp;</span><span>" + item.prizeName + "</span></li>");
                        })
                        if ($('.result ul li').length <= 4) {
                            $('.result ul').html($('.result ul').html());
                        }
                        if ($('.result ul li').length > 4) {
                            $('.result ul').html($('.result ul').html() + $('.result ul').html());
                            setInterval(function () {
                                x++;
                                $('.result ul').animate({'top': -parseFloat(0.55 * parseInt(x)).toFixed(2) + 'rem'}, 500);
                                if (x == $('.result ul li').length / 2) {
                                    x = 0;
                                    $('.result ul').animate({'top': -parseFloat(0.57 * parseInt(x)).toFixed(2) + 'rem'}, 0);
                                }
                            }, 1000);
                        }
                    }
                    if (json.tenderInfoList != undefined) {
                        //投资前五名排行榜
                        var t = 0;
                        $(".rank ul").html("");
                        $.each(json.tenderInfoList, function (index, item) {
                            t++;
                            $(".rank ul").append("<li class='rank-list" + t + "'><span class='rank-li-span1'>" + t + "</span><span class='rank-li-span2'>" + item.nickName + "</span><span>" + item.account + "</span></li>")
                        })
                    }
                }
            });
        },
        // 点击投掷开始游戏
        gameStart: function () {
            $(".yclick").attr("data-isSub", "true");
            $(".yclick").html("正在投掷...");
            $.ajax({
                url: link + "joinTanksGivingDayActivity.do" + linkextra,
                type: "get",
                dataType: "jsonp",
                jsonp: "jsonpCallback",
                success: function (json) {
                    if (json.resultCode == 0) {
                        l = json.resultData.curPosition;//当前光标所在的位置
                        x = json.resultData.afterPosition;//掷筛子之后的位置
                        p = json.resultData.curPoint;//筛子的点数
                        w = l + p;
                        var a = 0;
                        $(".dian").html("");
                        $(".dian").append("<img src='img/" + p + "dian.png' class='dice'>");
                        $(".dice").fadeIn(500).delay(1000).fadeOut(500);
                        i = 0;
                        clearInterval(time1);
                        time1 = setInterval(function () {
                            n = l + i;
                            $(".singal").fadeOut(500);
                            i++;
                            $(".kuaier").find("li").eq(n).find(".singal").fadeIn(500);
                            if (i == p) {
                                clearInterval(time1);
                                main.select();
                                $(".yclick").attr("data-isSub", "false");
                                $(".yclick").html("点击投掷");
                            }
                        }, 500);
                        var num = $(".rest").text();
                        $(".rest").html(num - 1);
                    } else {
                        $(".yclick").attr("data-isSub", "false");
                        $(".yclick").html("点击投掷");
                        $(".no-chance").show();
                    }
                }
            })
        },
        myPrize: function () {
            if (sign != undefined && sign != '' && sign != 'null' && uid != undefined && uid != '' && uid != 'null') {
                if (!main.isLogin()) {
                    if (!main.checkSign()) {
                        return;
                    }
                }
            } else {
                window.location.href = "xxd://login";
                return;
            }

            //我的奖品
            $.ajax({
                url: link + "myPrize.do" + linkextra,
                type: "get",
                dataType: "jsonp",
                jsonp: "jsonpCallback",
                success: function (json) {
                    if (json.resultCode == 0) {
                        var k = json.resultData;
                        if (k == undefined || k.length == 0) {
                            $(".no-prize").show();
                        } else {
                            $(".no-prize").hide();
                            $(".prize-box ul").html("");
                            $.each(json.resultData, function (index, item) {
                                $(".prize-box ul").append("<li><span>" + item.addTime + "&nbsp;&nbsp;</span><span>获得了&nbsp;&nbsp;</span><span>" + item.prizeName + "</span></li>");
                            });

                            var c = json.prizeCount;
                            var q = 0;

                            $(".prize-down").on("click", function () {
                                if (q <= c - 5) {
                                    q++;
                                    console.log(q);
                                    $('.prize-list ul').animate({'top': -parseFloat(0.845 * parseInt(q)).toFixed(2) + 'rem'}, 500);
                                }

                            })
                            $(".prize-up").on("click", function () {
                                if (q > 0) {
                                    q--;
                                    console.log(q);
                                    $('.prize-list ul').animate({'top': -parseFloat(0.845 * parseInt(q)).toFixed(2) + 'rem'}, 500);
                                }
                            });
                        }
                        $(".prize-con").show();
                    } else if (json.resultCode == 100) {
                        window.location.href = "xxd://login";
                    } else {
                        alert("获取您的奖品信息失败，请重新尝试");
                    }
                }
            })
        },
        select: function () {
            $(".alert-base").hide();
            $(".prize-alert").show();
            if (w == 1) {
                $(".money1").show();
                $(".money1 p .number").html("20");
            } else if (w == 2) {
                $(".go").html("");
                $(".go").append("<i>前进1步</i>");
                $(".go").show();
                $(".go-one-close").show();
                $(".go-one-close").on("click", function () {
                    $(".singal").fadeOut(500);
                    $(".kuaier").find("li").eq(2).find(".singal").fadeIn(500);
                    var t1 = setTimeout(function () {
                        $(".alert-base").hide();
                        $(".prize-alert").show();
                        $(".money1").show();
                        $(".money1 p .number").html("");
                        $(".money1").find(".number").html("10");
                        $(".go-one-close").hide();
                    }, 1000);
                })
            } else if (w == 3) {
                $(".money1").show();
                $(".money1 p .number").html("10");
            } else if (w == 4) {
                $(".money1").show();
                $(".money1 p .number").html("5");
            } else if (w == 5) {
                $(".cup").show();
            } else if (w == 6) {
                $(".fifty").show();
            } else if (w == 7) {
                $(".back").html("");
                $(".back").append("<i>后退3步</i>");
                $(".back").show();
                $(".back-three-close").show();
                $(".back-three-close").on("click", function () {
                    clearInterval(timer7);
                    i = 0;
                    timer7 = setInterval(function () {
                        $(".singal").fadeOut(0);
                        i++;
                        $(".kuaier").find("li").eq(w - 1 - i).find(".singal").fadeIn(0, function () {
                            if (i >= 3) {
                                clearInterval(timer7);
                                $(".singal").hide();
                                $(".kuaier").find("li").eq(3).find(".singal").show();
                                $(".alert_close").hide();
                                $(".back-three-close").hide();
                                $(".prize-alert-close1").show();
                                $(".prize-alert-close2").show();
                                $(".alert-base").hide();
                                $(".prize-alert").show();
                                $(".money1").show();
                                $(".money1 p .number").html("5");
                            }
                        });
                    }, 500);
                })
            } else if (w == 8) {
                $(".money1").show();
                $(".money1 p .number").html("28");
            } else if (w == 9) {
                $(".money1").show();
                $(".money1 p .number").html("18");
            } else if (w == 10) {
                $(".money1").show();
                $(".money1 p .number").html("8");
            } else if (w == 11) {
                $(".camera").show();
            } else if (w == 12) {
                $(".return1").show();
            } else if (w == 13) {
                $(".money1").show();
                $(".money1 p .number").html("8");
            } else if (w == 14) {
                $(".back").html("");
                $(".back").append("<i>后退4步</i>");
                $(".back").show();
                $(".back-four-close").show();
                $(".back-four-close").on("click", function () {
                    clearInterval(timer14);
                    i = 0;
                    timer14 = setInterval(function () {
                        $(".singal").fadeOut(0);
                        i++;
                        $(".kuaier").find("li").eq(w - 1 - i).find(".singal").fadeIn(0, function () {
                            if (i >= 4) {
                                clearInterval(timer14);
                                $(".alert_close").hide();
                                $(".back-four-close").hide();
                                $(".prize-alert-close1").show();
                                $(".prize-alert-close2").show();
                                $(".alert-base").hide();
                                $(".prize-alert").show();
                                $(".money1").show();
                                $(".money1 p .number").html("8");
                            }
                        });
                    }, 500);
                })
            } else if (w == 15) {
                $(".money1").show();
                $(".money1 p .number").html("58");
            } else if (w == 16) {
                $(".money1").show();
                $(".money1 p .number").html("28");
            } else if (w == 17) {
                $(".nick").show();
            } else if (w == 18) {
                $(".money1").show();
                $(".money1 p .number").html("10000");
            } else if (w == 19) {
                $(".money1").show();
                $(".money1 p .number").html("288");
            } else if (w == 20) {
                $(".go").html("");
                $(".go").append("<i>前进2步</i>");
                $(".go").show();
                $(".go-two-close").show();
                $(".go-two-close").on("click", function () {
                    clearInterval(timer20);
                    i = 0;
                    timer20 = setInterval(function () {
                        $(".singal").fadeOut(500);
                        i++;
                        $(".kuaier").find("li").eq(w - 1 + i).find(".singal").fadeIn(500);
                        if (i >= 2) {
                            clearInterval(timer20);
                            var t5 = setTimeout(function () {
                                $(".alert-base").hide();
                                $(".prize-alert").show();
                                $(".money1").show();
                                $(".money1 p .number").html("");
                                $(".money1").find(".number").html("58");
                                $(".go-two-close").hide();
                            }, 500);
                        }
                    }, 500)
                })
            } else if (w == 21) {
                $(".money1").show();
                $(".money1 p .number").html("88");
            } else if (w == 22) {
                $(".money1").show();
                $(".money1 p .number").html("58");
            } else if (w == 23) {
                $(".tv").show();
            } else if (w == 24) {
                !function () {
                    $.Deferred().done(function () {
                        $(".money2").show();
                        $(".return2").fadeIn(0).delay(2000).fadeOut(0);
                        $(".money2").delay(2000).fadeIn(0).delay(2000).fadeOut(0, function () {
                            $(".prize-alert").hide();
                        });
                    }, function () {
                        $(".singal").hide();
                    }).resolve();
                }();
            } else if (w > 24) {
                !function () {
                    $.Deferred().done(function () {
                        $(".money2").show();
                        $(".money2").fadeIn(0).delay(1000).fadeOut(0, function () {
                            $(".prize-alert").hide();
                        });
                    }, function () {
                        $(".singal").hide();
                    }).resolve();
                }();
            }
        }
    };

    main.init();
    return main;
});