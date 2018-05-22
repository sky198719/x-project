(function () {
    var actCode = 'april-fools-day-activity',
        link = '../../../../',
        linkextra = '?jsonpCallback=?',
        activty = {},  //活动时间判断与内容
        isloginCode = 0, //是否登录
        luckytimer, // 幸运大奖计时器
        pageid = 1,
        pageall = 0,
        flag = false,
        timer,
        $this,
        uid,
        sign,
        xxd_utm_source;
    var itemName = [
        {itmeName: '10个新新币', itemId: 197},
        {itmeName: '88个新新币', itemId: 198},
        {itmeName: '30元话费直充', itemId: 199},
        {itmeName: 'e袋洗60元券', itemId: 200},
        {itmeName: '1GB流量', itemId: 201},
        {itmeName: '100元京东卡', itemId: 202},
        {itmeName: '2W新新币', itemId: 203},
        {itmeName: '1000元京东E卡', itemId: 204},
        {itmeName: '联想Miix5电脑', itemId: 205}
    ];
    var main = {
        init: function () {
            uid = main.GetQueryString("uid");
            sign = main.GetQueryString("sign");
            xxd_utm_source = main.GetQueryString("xxd_utm_source") || '';
            main.eventBind();
            main.initAprilFoolPage();
            main.addEventListener();
            //GA、XA布码
            //XXD_TRACK.track_pageview('AprilFoolsDay');
            gaInits(uid);
            growingIOInits({userId: uid});
        },
        eventBind: function () {
            $('.close').on('click', function () {
                $('body').removeClass('bodyhidden').addClass('bodyauto');
                $('.rule,.pop,.login').removeClass('show').fadeOut(0);
            });
            $('.rule-btn').on('click', function () {
                $('body').removeClass('bodyauto').addClass('bodyhidden');
                $('.rule').addClass('show');
            });
            //每10分钟刷新一次页面
            setInterval(function () {
                location = location + "?" + new Date().getTime();
            }, 600000);
            //我的奖品
            $('.myprize-btn').on('click', function () {
                main.activityTime();
                main.isLogin();
                //活动未开始已结束弹框  return
                if (activty.activitytime != 0) {
                    main.tip(activty.activityinfo);
                    return;
                }
                //是否登录 return
                if (isloginCode != 0 && isloginCode != 2) {
                    window.location.href = 'xxd://login';
                    return;
                }
                $('body').removeClass('bodyauto').addClass('bodyhidden');
                $('.pop-myprize').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                $('.myprize').addClass('show');
            });
            //我的奖品上一页下一页
            $('.page-up').on('click', function () {
                pageid--;
                if (pageid <= 0) {
                    pageid = 1;
                    main.pagetip('已经是第一页了');
                }
                main.myPrize();
            });
            $('.page-down').on('click', function () {
                pageid++;
                if (parseInt(pageid) > parseInt(pageall)) {
                    pageid = pageall;
                    main.pagetip('已经是最后一页');
                }
                main.myPrize();
            })
            //开始游戏底部按钮
            $('.readygo-btn').on('click', function () {
                main.activityTime();
                main.isLogin();
                //活动未开始已结束弹框  return
                if (activty.activitytime != 0) {
                    main.tip(activty.activityinfo);
                    return;
                }
                //是否登录 return
                if (isloginCode != 0 && isloginCode != 2) {
                    window.location.href = 'xxd://login';
                    return;
                }
                //无机会则弹窗
                if (isloginCode == 2) {
                    $('body').removeClass('bodyauto').addClass('bodyhidden');
                    $('.prompt-nochance').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                    $('.nochance').addClass('show');
                    return;
                }
                $('body').animate({'scrollTop': $('.contanier')[0].offsetTop}, 300);
            });
            //无游戏机会的时候，点击去投资页面
            $('.invest').on('click', function () {
                $('body').removeClass('bodyhidden').addClass('bodyauto');
                $('.pop').removeClass('show').fadeOut(0);
                $('body').animate({'scrollTop': $('.product')[0].offsetTop}, 300);
                flag = false;
            });
            $('.turn-over').click(function (e) {
                main.activityTime();
                main.isLogin();
                //反多张牌同时被翻
                if (flag == false) {
                    //活动未开始已结束弹框  return
                    if (activty.activitytime != 0) {
                        main.tip(activty.activityinfo);
                        return;
                    }
                    //是否登录 return
                    if (isloginCode != 0 && isloginCode != 2) {
                        window.location.href = 'xxd://login';
                        return;
                    }
                    //有无抽奖机会
                    if (isloginCode == 2) {
                        $('.game-times').html('0');
                        $('body').removeClass('bodyauto').addClass('bodyhidden');
                        $('.prompt-nochance').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                        $('.nochance').addClass('show');
                        return;
                    }
                    $.ajax({
                        url: link + 'memberDay922/playStart.do' + linkextra,
                        type: 'get',
                        dataType: 'jsonp',
                        jsonp: 'jsoncallback',
                        data: {uid: uid, sign: sign},
                        success: function (data) {
                            clearTimeout(timer);
                            $this = $(e.currentTarget);
                            if (data.resultCode == 10) {
                                $('.game-times').html('0');
                                $('body').removeClass('bodyauto').addClass('bodyhidden');
                                $('.prompt-nochance').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                                $('.nochance').addClass('show');
                                return;
                            }
                            if (data.resultCode != 0 && data.resultCode != 10) {
                                window.location.href = 'xxd://login';
                            }
                            if (data.resultCode == 0) {
                                var prizelist = data.data[0].itemId;
                                $.each(itemName, function (i, v) {
                                    if (prizelist == itemName[i].itemId) {
                                        $(e.currentTarget).find('.img-prize').remove();
                                        $(e.currentTarget).append("<img class='img-prize' src='img/prize" + (i + 1) + ".png' alt='mask'>")
                                        $(e.currentTarget).removeClass('backdown').addClass('backup');
                                        $('.prize-box').css({'background-image': 'url("img/pop-prize' + (i + 1) + '.png")'});
                                        timer = setTimeout(function () {
                                            $('body').removeClass('bodyauto').addClass('bodyhidden');
                                            $('.pop-prizes').css({'background': 'rgba(0, 0, 0, 0.7)'}).show();
                                            $('.prize-box').addClass('show');
                                        }, 700);
                                    }
                                });
                                main.gameEnd();
                            }
                        },
                        error: function () {
                            main.tip('网络异常请重试！');
                        }
                    });
                    flag = true;
                }
            });
            //奖品弹框的关闭和收入囊中按钮
            $('.closeprize,.known').click(function () {
                if (document.body.scrollWidth == 320 || main.support_css3_3d() == false) {
                    $(".turn-over").find('.img-prize').remove();
                    $this.removeClass('backup').addClass('backdown');
                } else {
                    $(".turn-over").removeClass('backup').addClass('backdown');
                }
                $('body').removeClass('bodyhidden').addClass('bodyauto');
                $('.pop').removeClass('show').fadeOut(0);
                flag = false;
            });
            //再来一次触发
            $('.readygo').on('click', function () {
                if (document.body.scrollWidth == 320 || main.support_css3_3d() == false) {
                    $(".turn-over").find('.img-prize').remove();
                    $this.removeClass('backup').addClass('backdown');
                } else {
                    $this.removeClass('backup').addClass('backdown');
                }
                $('body').removeClass('bodyhidden').addClass('bodyauto');
                $('.pop').removeClass('show').fadeOut(0);
                setTimeout(function () {
                    flag = false;
                    $this.trigger("click");
                }, 1000);
                /*$('.pop-prizes').fadeOut(200, function () {
                 $this.removeClass('backup').addClass('backdown');
                 setTimeout(function () {
                 flag = false;
                 $this.trigger("click");
                 }, 1000);
                 });*/
            });
            $('#nochanceclose').click(function () {
                flag = false;
            });
            //验证码
            $("#VerifyCode").click(function () {
                $("#VerifyCode").attr("src", "../../../../randCode/createVerifyCode.do?" + Math.random());
            });
            //底部投资按钮点击
            $('.days').on('click', function () {
                main.activityTime();
                main.isLogin();
                //活动未开始已结束弹框  return
                if (activty.activitytime != 0) {
                    main.tip(activty.activityinfo);
                    return;
                }
                //是否登录 return
                if (isloginCode != 0 && isloginCode != 2) {
                    window.location.href = 'xxd://login';
                    return;
                }
                window.location.href = 'xxd://list/pdb?selID=1&pdName=ririying&xxd_utm_source=123456';
            });
            $('.gold-ingot').on('click', function () {
                main.activityTime();
                main.isLogin();
                //活动未开始已结束弹框  return
                if (activty.activitytime != 0) {
                    main.tip(activty.activityinfo);
                    return;
                }
                //是否登录 return
                if (isloginCode != 0 && isloginCode != 2) {
                    window.location.href = 'xxd://login';
                    return;
                }
                $.ajax({
                    url: link + 'xplan/getLatestSchemeId.do' + linkextra,
                    type: 'get',
                    dataType: 'jsonp',
                    jsonp: 'jsonpCallback',
                    data: {closeTerm: 1},
                    success: function (data) {
                        if (data.schemeInfo != null && data.schemeInfo != "") {
                            window.location.href = 'xxd://list/pdd?selID=1&pdName=xinyuanbao&xybID=' + data.schemeInfo.SCHEMEID;
                        }
                    },
                    error: function () {
                        main.tip('网络异常请重试！');
                    }
                });
            });
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
        GetQueryString: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return decodeURI(r[2]);
            return null;
        },
        isLogin: function () {
            $.ajax({
                url: link + 'memberDay922/getCanPlayTimes.do' + linkextra,
                type: 'get',
                data: {uid: uid, sign: sign},
                dataType: 'jsonp',
                jsonp: 'jsoncallback',
                success: function (data) {
                    isloginCode = data.resultCode;
                    return isloginCode;
                },
                error: function () {
                    main.tip('网络异常请重试！');
                }
            });
        },
        activityTime: function () {
            $.ajax({
                url: link + 'activity/checkActivity.do' + linkextra,
                type: 'get',
                data: {code: actCode},
                dataType: 'jsonp',
                jsonp: 'jsoncallback',
                success: function (data) {
                    activty = {
                        "activitytime": data.resultCode,
                        "activityinfo": data.msg
                    }
                    return activty;
                },
                error: function () {
                    main.tip('网络异常，请重试');
                }
            });
        },
        initAprilFoolPage: function () {
            $.ajax({
                url: link + 'memberDay922/getCanPlayTimes.do' + linkextra,
                type: 'get',
                dataType: 'jsonp',
                data: {uid: uid, sign: sign},
                jsonp: 'jsoncallback',
                success: function (data) {
                    if (data.resultCode == 2) {
                        $('.game-times').html('0');
                        return;
                    } else if (data.resultCode == 0) {
                        $('.game-times').html(data.data.playtime);
                    }
                },
                error: function () {
                    main.tip('网络异常请重试！');
                }
            });
            main.activityTime();
            main.luckyList();
            main.ranking();
            main.myPrize();
            main.isLogin();
        },
        gameEnd: function () {
            $.ajax({
                url: link + 'memberDay922/playEnd.do' + linkextra,
                type: 'get',
                dataType: 'jsonp',
                jsonp: 'jsoncallback',
                data: {uid: uid, sign: sign},
                success: function (data) {
                    $('.game-times').html(data.data[0].playtime);
                    main.initAprilFoolPage();
                    main.addEventListener();
                },
                error: function () {
                    main.tip('网络异常请重试！');
                }
            });
        },
        luckyList: function () {
            $.ajax({
                url: link + 'memberDay922/luckyList.do' + linkextra,
                type: 'get',
                data: {uid: uid, sign: sign},
                dataType: 'jsonp',
                jsonp: "jsoncallback",
                success: function (data) {
                    if (data.length > 0) {
                        $.each(data, function (i, item) {
                            $('.lucky-box ul').append("<li><span>" + item.user + "</span><span>" + item.time + "</span><span>" + item.reward + "</span></li>");
                        });
                        clearInterval(luckytimer);
                        var index = 0;
                        luckytimer = setInterval(function () {
                            if (index >= data.length - 6) {
                                index = 0;
                                $('.lucky ul').animate({'scrollTop': 0}, 400);
                            } else {
                                index++;
                                $('.lucky-box ul').animate({'top': -1.375 * index + 'rem'}, 200);
                            }
                        }, 2000);
                    }
                },
                error: function () {
                    main.tip('网络异常请重试！');
                }
            })
        },
        ranking: function () {
            $.ajax({
                url: link + 'memberDay922/topList.do' + linkextra,
                type: 'get',
                data: {uid: uid, sign: sign},
                dataType: 'jsonp',
                jsonp: "jsoncallback",
                success: function (data) {
                    $('.ranking .rank-data ul').html('');
                    $.each(data, function (i, item) {
                        $('.ranking .rank-data ul').append("<li><span>" + (i + 1) + "</span><span>" + item.user + "</span><span>" + item.amount + "</span></li>");
                    });
                },
                error: function () {
                    main.tip('网络异常请重试！');
                }
            });
        },
        myPrize: function () {
            $.ajax({
                url: link + 'memberDay922/queryUserPrizeRecs.do' + linkextra,
                type: 'get',
                dataType: 'jsonp',
                data: {currentPage: pageid, pageSize: 5, uid: uid, sign: sign},
                jsonp: "jsoncallback",
                success: function (data) {
                    if (data.resultCode == 0) {
                        pageall = data.pageInfo.totalpage;
                        $('.myprize-con ul').empty();
                        if (data.data.length == 0) {
                            $('.myprize-con ul').append('<li>您还未获得奖品，赶快投资参与活动吧~!</li>');
                            $('.myprize-con ul li').css({'margin-top': '1rem'});
                            return;
                        }
                        $.each(data.data, function (i, item) {
                            $('.myprize-con ul').append('<li><span>' + item.time1 + '</span><span>' + item.time + '</span>抽中了<span>' + item.reward + '</span></li>');
                            $('.myprize-con ul li').css({'margin-top': '0'});
                        });
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
    }
    main.init();
    return main;
})
();