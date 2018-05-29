/**
 * _已投资页面
 */
define(['js/utils/animateNumber', 'js/fund/fundAnimate','js/utils/date','js/activity/womensDay'], function (animateNumber, fundAnimate,DateHandle,womensDay) {
    var fcode = '';
    var bid = "";
    var gname = "";
    var categorys = "";
    var fundInvestedCtrl = {
        init: function () {
            fundInvestedCtrl.eventBind();
            fundInvestedCtrl.getFundInfo();
            fundInvestedCtrl.getFundLimit();
            fundAnimate.investedAnimate();

            womensDay.fundInvested();

            $$("a[name='index_transferIn']").html("即将下架");

        },

        eventBind: function () {
            var bindings = [
                {
                    element: 'a[name="index_transferIn"]',
                    event: 'click',
                    handler: fundInvestedCtrl.transferIn
                },
                {
                    element: 'a[name="index_transferOut"]',
                    event: 'click',
                    handler: fundInvestedCtrl.transferOut
                },
                {
                    element: '#earningsYesterday',
                    event: 'click',
                    handler: fundInvestedCtrl.goEarningsDetailsList
                },
                {
                    element: '#investMoney',
                    event: 'click',
                    handler: fundInvestedCtrl.goPurchaseDetailsList
                },
                {
                    element: "a[name='earningsUrl']",
                    event: 'click',
                    handler: fundInvestedCtrl.goEarningsDetailsList
                },
                {
                    element: 'a[name="ddeHelp"]',
                    event: 'click',
                    handler: fundInvestedCtrl.dayHelp
                }
            ];
            appFunc.bindEvents(bindings);
        },

        toFund11Holiday:function(){
            GS.loadPage("activity/fund11Holiday.html");
        },

        toFundNewYear:function(){
            GS.loadPage('activity/fundNewYear.html');
        },

        getFundInfo: function () {
            req.callGet({
                url: 'fund/selectFundInfo.do',
                data: {},
                dataType: 'json',
                async: false,
                success: function (data) {
                    var isActivity = data.activityInfo.isActivity;
                    if (data != null && data.fundApr != null) {
                        var apr = data.fundApr.apr;
                        animateNumber.animate({
                            from: 0,
                            to: apr,
                            steps: 10,
                            intervalNumber: 100,
                            precision: 2,
                            isFloat: true,
                            callBack: function (value) {
                                var values = new String(value).split('.');
                                var html = '';
                                if(values.length == 1 || (isActivity != undefined && isActivity == 1)) {
                                    html = '<span class="font36 font-red">' + values[0] + '</span>';
                                } else {
                                    html = '<span class="font36 font-red">' + values[0] + '</span><span class="font18 font-red">.' + values[1] + '</span>';
                                }
                                $$("#yield").html(html);
                            }
                        });
                    }

                    //产品详情
                    bid = data.fundApr.fcode;
                    gname = "_：_";
                    categorys = "_/"+apr+"%/1天";
                    try{product_detail({id:bid,name:gname,category:categorys});}catch(e){}

                    if(isActivity != undefined && isActivity == 1) {
                        var floatApr = data.fundApr.floatApr;
                        $$("#investedFundApr").append('<span class="font18 font-red">+'+floatApr+'</span><img src="static/img/activity/fund/forphone_left.png" height="25" class="fund-forphone-left"/>');

                        /*var binding = [
                         {
                         element:'.fund-moneybag',
                         event:'click',
                         handler:fundInvestedCtrl.toFund11Holiday
                         },
                         {
                         element:'.fund-forphone-left',
                         event:'click',
                         handler:fundInvestedCtrl.toFund11Holiday
                         }
                         ];  */
                        /* var currentDate = data.currentDate;
                         if(GC.getNewYear() <= DateHandle.formatDate('yyyyMMddHHmm', currentDate)){
                         binding = [
                         {
                         element:'.fund-moneybag',
                         event:'click',
                         handler:fundInvestedCtrl.toFundNewYear
                         },
                         {
                         element:'.fund-forphone-left',
                         event:'click',
                         handler:fundInvestedCtrl.toFundNewYear
                         }
                         ];
                         }   */

                        //appFunc.bindEvents(binding);

                        //$$(".fund-moneybag").show();
                    }

                    if (data != null && data.fund != null) {
                        $$('a[name="index_transferIn"]').dataset.fund = data.fund;
                        fcode = data.fund.fcode;
                        fundInvestedCtrl.initInvestedData();
                    }
                }
            });
        },

        dayHelp: function () {
            GS.loadPage("fund/fundHelp.html?path=fund");
        },
        goEarningsDetailsList: function () {
            GS.loadPage("fund/fundDetailsList.html?fcode=" + fcode + "&tab=earnings&path=fund");
        },
        goPurchaseDetailsList: function () {
            GS.loadPage("fund/fundDetailsList.html?fcode=" + fcode + "&tab=transferIn&path=fund");
        },

        /** _确认转入页 */
        transferIn: function () {
            if ($$('a[name="index_transferIn"]').hasClass("button-51-disable")) {
                return;
            }
            try {
                //XXD_TRACK._trackEvent({category: "webpp_fund_in", action: "fund_tochange", label: "转入", value: "", custval: "" });
                //加入购物车
                add_to_cart({id:bid,name:gname,category:categorys});
            } catch (e) {}
            //向server查询是否已满额&是否超过个人投资上限
            fundInvestedCtrl.selectFundLimitation();
        },

        selectFundLimitation: function () {
            req.callPost({
                url: 'fund/selectFundLimitation.do',
                data: {
                    fcode: fcode
                },
                dataType: 'json',
                success: function (data) {
                    if (data != null) {
                        if (data.isTotalFull == "true") {
                            xxdApp.alert("_已满额，暂不能转入。", '提示');
                        } else if (data.isPersonFull == "true") {
                            xxdApp.alert("您累计的转入金额已达" + appFunc.fmoney(data.fund.userMostTender, 2) + "投资上限，暂不能转入", '提示');
                        } else {
                            var dmp_urlParam = "";
                            dmp_urlParam = dmp_querystring();
                            function dmp_querystring() {
                                var pattern = new RegExp("[?&]"+ "xxd_utm_source" +"\=([^&]+)", "g");
                                var matcher = pattern.exec(decodeURIComponent(location.href));
                                var items = null;
                                if(null != matcher){
                                    try{
                                        items = decodeURIComponent(decodeURIComponent(matcher[1]));
                                    }catch(e){
                                        try{
                                            items = decodeURIComponent(matcher[1]);
                                        }catch(e){
                                            items = matcher[1];
                                        }
                                    }
                                }
                                //console.log(items);
                                return items;
                            };
                            if(dmp_urlParam == "" || dmp_urlParam == null){
                                GS.loadPage('fund/fundTransferIn.html?path=fund');
                            }else {
                                GS.loadPage('fund/fundTransferIn.html?path=fund&xxd_utm_source=' + dmp_urlParam);
                            }

                        }
                    }
                }
            });
        },
        getFundLimit: function () {
            req.callGet({
                url: 'fund/fundLimit.do',
                data: {
                },
                dataType: 'json',
                success: function (data) {
                    if (data.resultCode == 0) {
                        $$('#v_isbanlance').val(data.isBalance);
                        $$("#v_islastbalance").val(data.isLastBalance);
                        $$("#v_purchase_switch").val(data.purchaseSwitch);
                        $$("#v_ransom_switch").val(data.ransomSwitch);
                    }

                    fundInvestedCtrl.enableButton();
                    $$('a[name="index_transferIn"]').removeClass("button-51");
                    $$('a[name="index_transferIn"]').addClass("button-51-disable");
                    $$('a[name="index_transferIn"]').html("即将下架");

                }
            });
        },

        initInvestedData: function () {
            req.callPost({
                url: 'fund/initInvestedData.do',
                data: {
                    fcode: fcode
                },
                dataType: 'json',
                success: function (data) {
                    if (data != null) {
                        if (data.fundTrade != null) {
                            var earningsYesterday = data.fundTrade.tradeNum;
                            animateNumber.animate({
                                from: 0,
                                to: earningsYesterday,
                                precision: 2,
                                intervalNumber: 100,
                                steps: 10,
                                isFloat: true,
                                callBack: function (value) {
                                    var values = new String(value).split('.');
                                    var html = '<span class="font80">' + values[0] + '</span><span class="font28">.' + values[1] + '</span>';
                                    $$("#earningsYesterday").html(html);
                                }
                            });
                        }

                        if (data.fundTotal != null) {
                            var investMoney = data.fundTotal.account;
                            investMoney = investMoney == 0 ? "0.00" : investMoney;
                            animateNumber.animate({
                                element: '#investMoney',
                                from: 0,
                                to: investMoney,
                                precision: 2,
                                intervalNumber: 100,
                                steps: 10,
                                valueType: 'money'
                            });
                            var totalEarnings = data.fundTotal.totalEarnings;
                            animateNumber.animate({
                                from: 0,
                                to: totalEarnings,
                                precision: 2,
                                intervalNumber: 100,
                                steps: 10,
                                isFloat: true,
                                callBack: function (value) {
                                    var values = new String(value).split('.');
                                    var html = '<span class="font36 font-red">' + values[0] + '</span><span class="font18 font-red">.' + values[1] + '</span>';
                                    $$("#totalEarnings").html(html);
                                }
                            });
                        }
                    }
                }
            });
        },

        /** 转出 */
        transferOut: function () {
            if ($$('a[name="index_transferOut"]').hasClass("button-50-disable")) {
                return;
            }
            try {
                //XXD_TRACK._trackEvent({category: "webpp_fund_out", action: "turn_out", label: "转出", value: "", custval: "" });
                //_立即转出GA
                gaClickEvent({property1:"转出",property2: "_转出", property3:window.location});
            } catch (e) {
            }
            GS.loadPage('fund/fundTransferOut.html?path=fund');
            //mainView.router.load({url: GC.getHtmlPath() + 'fund/fundTransferOut.html?path=fund'});
            xxdApp.closeModal('.popover');
        },

        enableButton: function () {
            var isBalance = $('#v_isbanlance').val();
            if (isBalance == 1) {
                var html = '<span class="font28"">钱在路上，一会就来</span>';
                $$(".fundBackground").removeClass("day-top");
                $$(".fundBackground").addClass("fund-top");
                $$("#earningsYesterday").html(html);
            }
            var isLastbalance = $('#v_islastbalance').val();
            var purchaseSwitch = $('#v_purchase_switch').val();
            var ransomSwitch = $('#v_ransom_switch').val();
            if (isBalance == "1" || isLastbalance == "1") {
                fundInvestedCtrl.enablePurchase("false");
                fundInvestedCtrl.enableRansom("false");
                $$("#dde_notice").html("收益正在结算中，暂不能转入和转出，请稍候");
            } else {
                if (purchaseSwitch == "1") {
                    if (ransomSwitch == "1") {
                        fundInvestedCtrl.enablePurchase("false");
                        fundInvestedCtrl.enableRansom("false");
                        $$("#dde_notice").html("因系统维护，暂不能转入转出");
                    } else {
                        fundInvestedCtrl.enablePurchase("false");
                        fundInvestedCtrl.enableRansom("true");
                        $$("#dde_notice").html("因系统维护，暂不能转入");
                    }
                } else {
                    if (ransomSwitch == "1") {
                        fundInvestedCtrl.enablePurchase("true");
                        fundInvestedCtrl.enableRansom("false");
                        $$("#dde_notice").html("因系统维护，暂不能转出");
                    } else {
                        fundInvestedCtrl.enablePurchase("true");
                        fundInvestedCtrl.enableRansom("true");
                    }
                }
            }
        },
        enablePurchase: function (enable) {
            if (enable == "true") {
                $$('a[name="index_transferIn"]').removeClass("button-51-disable");
                $$('a[name="index_transferIn"]').addClass("button-51");
            } else {
                $$('a[name="index_transferIn"]').removeClass("button-51");
                $$('a[name="index_transferIn"]').addClass("button-51-disable");
            }
        },
        enableRansom: function (enable) {
            if (enable == "true") {
                $$('a[name="index_transferOut"]').removeClass("button-50-disable");
                $$('a[name="index_transferOut"]').addClass("button-50");
            } else {
                $$('a[name="index_transferOut"]').removeClass("button-50");
                $$('a[name="index_transferOut"]').addClass("button-50-disable");
            }
        }
    };
    return fundInvestedCtrl;
});

