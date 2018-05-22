/**
 * Created by chaihuangqi on 2015/8/13.
 */
/**
 * fund unInvested
 */
define(['js/utils/animateNumber','js/fund/fundAnimate','js/utils/date','js/activity/womensDay'], function (animateNumber,fundAnimate,DateHandle,womensDay) {
	  var bid = "";
	  var categorys = "";
    var fundUnInvestedCtrl = {
        init: function () {
            //判断是否投资过跳转日日盈首页
            if (appFunc.isLogin()) {
                fundUnInvestedCtrl.isInvested();
            }
            fundUnInvestedCtrl.eventBind();
            fundUnInvestedCtrl.getFundInfo();
            //fundUnInvestedCtrl.getFundLimit();
            fundAnimate.unInvestedAnimate();

            womensDay.fundUnInvested();
        },
        eventBind: function () {
            var bindings = [
                {
                    element: 'div[name="daydayEarningJoin"]',
                    event: 'click',
                    handler: fundUnInvestedCtrl.transferIn
                },
                {
                    element: 'a[name="ddeHelp"]',
                    event: 'click',
                    handler: fundUnInvestedCtrl.dayHelp
                }
            ];
            appFunc.bindEvents(bindings);
        },

        toFund11Holiday:function(){
            GS.loadPage('activity/fund11Holiday.html');
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
                    if (data != null && data.fundApr != null) {
                        var isActivity = data.activityInfo.isActivity;
                        var apr = parseFloat(data.fundApr.apr).toFixed(2);
                        var millionsEarningsDay = fundUnInvestedCtrl.calculationIncome(10000, apr, 1);
                        var millionsEarningsYear = fundUnInvestedCtrl.calculationIncome(10000, apr, 360);
                        
                        //产品详情
                        bid = data.fundApr.fcode;
                        categorys = "日日盈/"+apr+"%/1天";
                      try{product_detail({id:bid,name:"日日盈：日日盈",category:categorys});}catch(e){} 
                        

                        if(isActivity != undefined && isActivity == 1) {
                            var floatApr = data.fundApr.floatApr;
                            if(floatApr != undefined && floatApr != "") {
                                millionsEarningsDay = parseFloat(millionsEarningsDay) + parseFloat(fundUnInvestedCtrl.calculationIncome(10000, floatApr, 1));
                                millionsEarningsYear = parseFloat(millionsEarningsYear) + parseFloat(fundUnInvestedCtrl.calculationIncome(10000, floatApr, 360));
                            }
                            $$(".fund-forphone_down").show();
                        }

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
                                    html = '<span class="font80">' + values[0] + '</span><span class="font28">%</span>';
                                } else {
                                    html = '<span class="font80">' + values[0] + '</span><span class="font28">.'+values[1]+'</span>';
                                }
                                $$("#daydayEarningsApr").html(html);
                            }
                        });

                        if(isActivity != undefined && isActivity == 1) {
                            var floatApr = data.fundApr.floatApr;
                            $$(".fundAprEnd").prepend('<span class="font28">+'+floatApr+'</span>');
                        }  else {
                            $$(".fund-double11").hide();
                            $$(".fund-forphone_down").hide();
                        }

                        animateNumber.animate({
                            from: 0,
                            to: millionsEarningsDay,
                            precision: 2,
                            intervalNumber: 100,
                            steps: 50,
                            isFloat: true,
                            callBack: function (value) {
                                var values = new String(value).split('.');
                                var html = '<span class="font36 font-red">' + values[0] + '</span><span class="font18 font-red">.' + values[1] + '</span>';
                                $$("#millionsEarningsDay").html(html);
                            }
                        });

                        animateNumber.animate({
                            from: 0,
                            to: millionsEarningsYear,
                            precision: 2,
                            intervalNumber: 100,
                            steps: 10,
                            isFloat: true,
                            callBack: function (value) {
                                var values = new String(value).split('.');
                                var html = '<span class="font36 font-red">' + values[0] + '</span><span class="font18 font-red">.' + values[1] + '</span>';
                                $$("#millionsEarningsYear").html(html);
                            }
                        });
                    }

                    if (data != null && data.fund != null) {
                        $$('a[name="daydayEarningJoin"]').dataset.fund = data.fund;
                    }

                    $$('div[name="daydayEarningJoin"]').removeClass("fund-rightjoin-button");
                    $$('div[name="daydayEarningJoin"]').addClass("fund-rightjoin-button-disable");
                    $$('div[name="daydayEarningJoin"]').html("即将下架");

                    $$("#notice_join").html("日日盈即将下架，请您及时做好安排，建议投资步步高升、新元宝等其他产品");
                    $$("#notice_join").css("color","red");
                    $$("#notice_join").css("text-align","left");
                }
            });
        },

        dayHelp: function () {
            GS.loadPage("fund/fundHelp.html?path=fund");
        },

        /** 日日盈确认转入页 */
        transferIn: function () {
            if ($$('div[name="daydayEarningJoin"]').hasClass("fund-rightjoin-button-disable")) {
                return;
            }
            try {
            	//XXD_TRACK._trackEvent({category: "webpp_fund_in", action: "buyFund_tochange_now", label: "立即加入", value: "", custval: "" });
            	add_to_cart({id:bid,name:"日日盈：日日盈",category:categorys});
            } catch (e) {}
            //向server查询是否已满额&是否超过个人投资上限
            fundUnInvestedCtrl.selectFundLimitation();
        },

        selectFundLimitation: function () {
            var fcode = $$('a[name="daydayEarningJoin"]').dataset.fund.fcode;
            req.callPost({
                url: 'fund/selectFundLimitation.do',
                data: {
                    fcode: fcode
                },
                dataType: 'json',
                success: function (data) {
                    if (data != null) {
                        if (data.isTotalFull == "true") {
                            xxdApp.alert("日日盈已满额，暂不能转入。", '提示');
                        } else if (data.isPersonFull == "true") {
                            xxdApp.alert("您累计的转入金额已达" + appFunc.fmoney(data.fund.userMostTender, 2) + "投资上限，暂不能转入", '提示');
                        } else {
                            var dmp_urlParam = "";
                            dmp_urlParam = dmp_querystring("xxd_utm_source");
                            function dmp_querystring(item) {
                                var pattern = new RegExp("[?&]"+ item +"\=([^&]+)", "g");
                                var matcher = pattern.exec(decodeURIComponent(location.href));
                                var items = null;
                                if(null != matcher){
                                    try{
                                        items = decodeURIComponent(decodeURIComponent(matcher[1]));
                                    }catch(e){
                                        try{
                                            items = decodeURIComponent(matcher[1]);
                                        }catch(e){
                                            items = matcher[1];
                                        }
                                    }
                                }
                                //console.log(items);
                                return items;
                            };
                            if(dmp_urlParam == "" || dmp_urlParam == null) {
                                GS.loadPage('fund/fundTransferIn.html?path=fund');
                            }else {
                                GS.loadPage('fund/fundTransferIn.html?path=fund&xxd_utm_source=' + dmp_urlParam);
                            }
                        }
                    }
                }
            });
        },

        calculationIncome: function (money, apr, dayNum) {
            var resultMoney = 0;
            req.callGet({
                url:'fund/calculationIncome.do',
                data:{
                    money:money,
                    apr:apr,
                    dayNum:dayNum
                },
                dataType:'json',
                async:false,
                success:function(data){
                    if(data.resultCode == 0) {
                        resultMoney = data.resultMoney
                    }
                }
            });
            return resultMoney;
            /*var result = parseFloat(0.00);
            var resultSum = parseFloat(0.00);
            var tempApr = parseFloat(apr / 100).toFixed(4);
            for (var i = 0; i < dayNum; i++) {
                result = parseFloat(( tempApr * money) / 360).toFixed(8);
                resultSum = parseFloat(parseFloat(resultSum) + parseFloat(result)).toFixed(8);
                //console.log("第" + (1+i) + "天，本金=" + money + ",收益="+result+",累计收益="+resultSum);
                money = parseFloat(parseFloat(money) + parseFloat(result)).toFixed(8);
            }
            return resultSum;   */
        },

        isInvested: function () {
            req.callGet({
                url: 'fund/selectIsInvested.do',
                data: {
                },
                dataType: 'json',
                async: true,
                success: function (data) {
                    if (data != null && data.isInvested == 'true') {
                        var dmp_urlParam = "";
                        dmp_urlParam = dmp_querystring("xxd_utm_source");
                        function dmp_querystring(item) {
                            var pattern = new RegExp("[?&]"+ item +"\=([^&]+)", "g");
                            var matcher = pattern.exec(decodeURIComponent(location.href));
                            var items = null;
                            if(null != matcher){
                                try{
                                    items = decodeURIComponent(decodeURIComponent(matcher[1]));
                                }catch(e){
                                    try{
                                        items = decodeURIComponent(matcher[1]);
                                    }catch(e){
                                        items = matcher[1];
                                    }
                                }
                            }
                            //console.log(items);
                            return items;
                        };
                        if(dmp_urlParam == "" || dmp_urlParam == null) {
                            GS.loadPage("fund/fundInvested.html?path=fund");
                        }else {
                            GS.loadPage("fund/fundInvested.html?path=fund&xxd_utm_source=" + dmp_urlParam);
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
                        $$('#un_v_isbanlance').val(data.isBalance);
                        $$("#un_v_islastbalance").val(data.isLastBalance);
                        $$("#un_v_purchase_switch").val(data.purchaseSwitch);
                    }

                    fundUnInvestedCtrl.enableButton();
                }
            });

        },

        enableButton: function () {
            var isBalance = $$('#un_v_isbanlance').val();
            var isLastBalance = $$("#un_v_islastbalance").val();
            var purchaseSwitch = $$('#un_v_purchase_switch').val();
            if (isBalance == "1" || isLastBalance == "1") {
                fundUnInvestedCtrl.enablePurchase("false");
                $$("#notice_join").html("系统正在结算收益，暂不能加入日日盈");
            } else {
                if (purchaseSwitch == "1") {
                    fundUnInvestedCtrl.enablePurchase("false");
                    $$("#notice_join").html("因系统维护，暂不能加入日日盈");
                } else {
                    fundUnInvestedCtrl.enablePurchase("true");
                }
            }
        },
        enablePurchase: function (enable) {
            if (enable == "true") {
                $$('div[name="daydayEarningJoin"]').removeClass("fund-rightjoin-button-disable");
                $$('div[name="daydayEarningJoin"]').addClass("fund-rightjoin-button");
            } else {
                $$('div[name="daydayEarningJoin"]').removeClass("fund-rightjoin-button");
                $$('div[name="daydayEarningJoin"]').addClass("fund-rightjoin-button-disable");
            }
        }
    };
    return fundUnInvestedCtrl;
});

