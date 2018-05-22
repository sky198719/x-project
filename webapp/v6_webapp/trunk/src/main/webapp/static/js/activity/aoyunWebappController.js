define(['js/utils/date', 'js/common/menu'], function (DateHandle, menu) {

    var aoyunWebappCtrl = {
        init: function () {
            aoyunWebappCtrl.eventBind();
            aoyunWebappCtrl.initData();

        },
        initData: function () {
            if (appFunc.isLogin()) {
                req.callGet({
                    url: "aoyun/getUserActivityInfo.do",
                    dataType: 'json',
                    success: function (result) {
                        if (result.resultCode == 0) {
                            var userRingInfoMap = result.userRingInfoMap;
                            var size = aoyunWebappCtrl.JSONLength(userRingInfoMap);
                            if (size > 0) {
                                var ouzhou = userRingInfoMap.NUM1;
                                if (ouzhou != undefined && ouzhou > 0) {
                                    $$("#aoyun-ouzhou-img").attr("src", "static/img/activity/aoyun/a1_c.png");
                                    $$("#aoyun-ouzhou-text").html("欧洲环x" + ouzhou);
                                    $$("#aoyun-ouzhou-text").css("background","#077ae8");
                                }

                                var feizhou = userRingInfoMap.NUM2;

                                if (feizhou != undefined && feizhou > 0) {
                                    $$("#aoyun-feizhou-img").attr("src", "static/img/activity/aoyun/a2_c.png");
                                    $$("#aoyun-feizhou-text").html("非洲环x" + feizhou);
                                    $$("#aoyun-feizhou-text").css("background","#2d3539");
                                }

                                var meizhou = userRingInfoMap.NUM3;
                                if (meizhou != undefined && meizhou > 0) {
                                    $$("#aoyun-meizhou-img").attr("src", "static/img/activity/aoyun/a3_c.png");
                                    $$("#aoyun-meizhou-text").html("美洲环x" + meizhou);
                                    $$("#aoyun-meizhou-text").css("background","#e84523");
                                }

                                var yazhou = userRingInfoMap.NUM4;
                                if (yazhou != undefined && yazhou > 0) {
                                    $$("#aoyun-yazhou-img").attr("src", "static/img/activity/aoyun/a4_c.png");
                                    $$("#aoyun-yazhou-text").html("亚洲环x" + yazhou);
                                    $$("#aoyun-yazhou-text").css("background","#eb7e01");
                                }

                                var dayangzhou = userRingInfoMap.NUM5;
                                if (dayangzhou != undefined && dayangzhou > 0) {
                                    $$("#aoyun-dayangzhou-img").attr("src", "static/img/activity/aoyun/a5_c.png");
                                    $$("#aoyun-dayangzhou-text").html("大洋洲环x" + dayangzhou);
                                    $$("#aoyun-dayangzhou-text").css("background","#015c09");
                                }

                            }

                            if (result.userExchRecMap != undefined) {
                                $$("#exchangeOk").show();
                                $$("#exchangeOkText").show();
                                $$("#aoyunNoLogin").hide();
                                $$("#exchangeOkText").html("我的奖品：" + result.userExchRecMap.PRIZENAME);
                            } else {
                                $$("#aoyunNoLogin").hide();
                                $$("#exchange").show();
                            }
                        }
                    }
                });
            }


            var status = aoyunWebappCtrl.isAvitityStatus();

            switch (status) {
                case 0:
                    $$("#lists").html("<li>活动未开始~</li>");
                    break;
                case 1:
                    req.callGet({
                        url: 'aoyun/getOlympicLast14List.do',
                        dataType: 'json',
                        success: function (result) {
                            if (result.resultCode == 0) {
                                var html = '';
                                for (var key in result.last14lists) {
                                    var obj = result.last14lists[key];
                                    html += '<li>用户' + obj.NICKNAME + '获得' + obj.CHOICE1TOTAL + '个';
                                    if (obj.CHOICEID1 == 1) {
                                        html += '欧洲环';
                                    } else if (obj.CHOICEID1 == 2) {
                                        html += '非洲环';
                                    } else if (obj.CHOICEID1 == 3) {
                                        html += '美洲环';
                                    } else if (obj.CHOICEID1 == 4) {
                                        html += '亚洲环';
                                    } else {
                                        html += '大洋洲环';
                                    }
                                    html += '</li>';
                                }
                                $$("#lists").html(html);
                            }
                        }
                    });
                    break;
                default :
                    $$("#lists").html("<li>活动已结束~</li>");
                    $$("#activityEnd").show();
                    $$("#aoyunNoLogin").hide();
                    $$("#exchange").hide();
                    $$("#exchangeOk").hide();
            }

        },
        eventBind: function () {
            var bind = [
                {
                    element: '#aoyunNoLogin',
                    event: 'click',
                    handler: aoyunWebappCtrl.aoyunNoLogin
                },
                {
                    element: '#exchange',
                    event: 'click',
                    handler: aoyunWebappCtrl.exchange
                },
                {
                    element: '.aoyunWebapp .toDeail',
                    event: 'click',
                    handler: aoyunWebappCtrl.getLatestPlanByCloseTerm
                }
            ];
            appFunc.bindEvents(bind);
        },
        getLatestPlanByCloseTerm: function () {
            var status = aoyunWebappCtrl.isAvitityStatus();
            switch (status) {
                case 0:
                    xxdApp.modal({
                        title: '提示',
                        text: '活动尚未开始～',
                        verticalButtons: true,
                        buttons: [
                            {
                                text: '确定',
                                onClick: function () {
                                    xxdApp.closeModal();
                                }
                            }
                        ]
                    });
                    break;
                case 1:

                    if (!appFunc.isLogin()) {
                        xxdApp.modal({
                            title: '提示',
                            text: '您还没有登录，请登录后参加活动',
                            verticalButtons: true,
                            buttons: [
                                {
                                    text: '立刻登录',
                                    onClick: function () {
                                        xxdApp.closeModal();
                                        menu.toLogin();
                                    }
                                }
                            ]
                        });
                    }

                    var closeTerm = $$(this).data("closeTerm");
                    if (closeTerm == undefined || closeTerm == null || closeTerm == "") {
                        xxdApp.alert("系统异常，请稍后重试", '抱歉');
                        return;
                    }
                    //try{XXD_TRACK._trackEvent({category:"webapp_aoyun",action:"aoyun_buy_now",label:"立即抢购",value:closeTerm,custval:closeTerm+"个月新元宝"});}catch(e){}
                    req.callJSON({
                        url: "xplan/getLatestSchemeId.do",
                        data: {
                            currentPage: 1,
                            closeTerm: closeTerm,
                            pageSize: 10
                        },
                        dataType: 'json',
                        indicator: true,
                        success: function (data) {
                            if (data != null && data.schemeInfo != null) {
                                var planId = data.schemeInfo.SCHEMEID;
                                GS.loadPage('plan/planDetailsV2_act.html?previousPage=aoyunWebapp&planId=' + planId);
                            }
                        }
                    });
                    break;
                default:
                    xxdApp.modal({
                        title: '提示',
                        text: '活动已结束～',
                        verticalButtons: true,
                        buttons: [
                            {
                                text: '确定',
                                onClick: function () {
                                    xxdApp.closeModal();
                                }
                            }
                        ]
                    });
            }

        },
        exchange: function () {
            var status = aoyunWebappCtrl.isAvitityStatus();
            switch (status) {
                case 0:
                    xxdApp.alert("活动未开始~");
                    break;
                case 1:
                    req.callGet({
                        url: "aoyun/getUserActivityInfo.do",
                        dataType: 'json',
                        success: function (result) {
                            console.log(result);
                            if (result.resultCode == 0) {
                                var size = aoyunWebappCtrl.JSONLength(result.userRingInfoMap);
                                if (size == 0) {
                                    xxdApp.modal({
                                        title: '提示',
                                        text: '您还没有奥运五环哦，马上投资获取五环吧！',
                                        verticalButtons: true,
                                        buttons: [
                                            {
                                                text: '立即投资',
                                                onClick: function () {

                                                }
                                            }
                                        ]
                                    });

                                } else if (result.userExchRecMap == undefined && size > 0) {

                                    req.callGet({
                                        url: 'aoyun/queryExchangeInfo.do',
                                        dataType: 'json',
                                        success: function (data) {
                                            var text = '活动期间仅有一次兑换机会，现在兑换将获得<span style="color: red">' + data.prizeName + '</span>， 确认兑换？<br><br>';


                                            if (data.actPrizeId == 11) {
                                                text += '<span style="color:#21ea11">1000元耐克礼品卡(' + data.prizePromptMap.PRIZEID11 + ')</span><br>';
                                            } else {
                                                text += "1000元耐克礼品卡(" + data.prizePromptMap.PRIZEID11 + ')<br>';
                                            }
                                            if (data.actPrizeId == 10) {
                                                text += '<span style="color:#21ea11">200元哈根达斯券(' + data.prizePromptMap.PRIZEID10 + ')</span><br>';
                                            } else {
                                                text += "200元哈根达斯券(" + data.prizePromptMap.PRIZEID10 + ')<br>';
                                            }

                                            if (data.actPrizeId == 9) {
                                                text += '<span style="color:#21ea11">50元京东E卡(' + data.prizePromptMap.PRIZEID9 + ')</span><br>';
                                            } else {
                                                text += "50元京东E卡(" + data.prizePromptMap.PRIZEID9 + ')<br>';
                                            }

                                            if (data.actPrizeId == 8) {
                                                text += '<span style="color:#21ea11">10元话费奖励(' + data.prizePromptMap.PRIZEID8 + ')</span><br>';
                                            } else {
                                                text += '10元话费奖励(' + data.prizePromptMap.PRIZEID8 + ')<br>';
                                            }

                                            if (data.actPrizeId == 7) {
                                                text += '<span style="color:#21ea11">300个新新币(' + data.prizePromptMap.PRIZEID7 + ')</span><br>';
                                            } else {
                                                text += "300个新新币(" + data.prizePromptMap.PRIZEID7 + ')<br>';
                                            }

                                            xxdApp.modal({
                                                title: '提示',
                                                text: text,
                                                verticalButtons: true,
                                                buttons: [
                                                    {
                                                        text: '确认兑换',
                                                        onClick: function () {
                                                       	 //try{XXD_TRACK._trackEvent({category:"webapp_aoyun",action:"aoyun_exchange",label:"立即兑换",value:data.prizeName,custval:""});}catch(e){}
                                                            aoyunWebappCtrl.reqExchange();
                                                        }
                                                    },
                                                    {
                                                        text: '取消',
                                                        onClick: function () {
                                                            xxdApp.closeModal();
                                                        }
                                                    }
                                                ]
                                            });
                                        }
                                    });
                                } else if(result.userExchRecMap != undefined && size > 0) {
                                    xxdApp.modal({
                                       title: '抱歉',
                                       text: '您已兑换过，不能再进行兑换',
                                       verticalButtons: true,
                                       buttons: [
                                           {
                                               text: '确认',
                                               onClick: function () {
                                                   aoyunWebappCtrl.initData();
                                               }
                                           }
                                       ]
                                   });
                                }
                            }
                        }
                    });
                    break;
                default:
                    xxdApp.alert("活动已结束~");
            }
        },
        reqExchange: function () {
            req.callPost({
                url: 'aoyun/exchange.do',
                dataType: 'json',
                preloaderTitle: '正在兑换，请稍等...',
                timeout: 10000,
                success: function (data) {
                    if (data.resultCode == 0) {
                        xxdApp.alert("兑换成功，工作人员将在两周内与您联系，请保持电话畅通");
                        aoyunWebappCtrl.initData();
                    } else {
                        xxdApp.alert("兑换失败，请刷新后重试");
                    }
                }
            });
        },
        JSONLength: function (obj) {
            var size = 0, key;
            for (key in obj) {
                if (obj.hasOwnProperty(key)) size++;
            }
            return size;
        },
        aoyunNoLogin: function () {
            menu.toLogin();
        },
        isAvitityStatus: function () {
            //未开始
            var status = 0;
            req.callGet({
                url: 'aoyun/getActivityInfo.do',
                dataType: 'json',
                async: false,
                success: function (result) {
                    var currentDateStr = result.currentDate;
                    var currentDate = DateHandle.parseDate(currentDateStr);

                    var starttime = result.activity.starttime;
                    var endtime = DateHandle.parseDate(result.activity.endtime);
                    if (currentDate > new Date(starttime) && currentDate < endtime) {
                        //活动中
                        status = 1;
                    } else if (currentDate > endtime) {
                        //活动结束
                        status = 2;
                    }
                }
            });
            return status;
        }

    };
    return aoyunWebappCtrl;
});
