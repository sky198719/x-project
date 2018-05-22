/**
 * Created by pufei on 2015/3/2.
 * 投资记录
 */
define(['js/account/bidHistoryView'], function (bidHistoryView) {
    var bidHistoryCtrl = {
        init: function (event) {
        	var totalTender = appFunc.getEventDetailPageQuery(event).totalTender;
            totalTender = (totalTender == undefined || totalTender == null) ? 0 : totalTender ;
        	$$("#btenderSum").html(appFunc.fmoney(totalTender, 2));
            //bidHistoryCtrl.selectTender();
            bidHistoryCtrl.eventBind();

            req.callGet({
                url: 'fund/selectIsInvested.do',
                data: {
                },
                dataType: 'json',
                success: function (data) {
                    if (data != null && data.isInvested != 'true') {
                        $$("#daydayEarnings").parent().hide();
                    }
                }
            });
            var XSCP30TisMeetTheTermOfPurchase = false;
            var QTDSTisMeetTheTermOfPurchase = false;
                req.callGet({
                url: 'product/getWebappProduct.do',
                data: {
                    pCode: 'XSCP30T'
                },
                dataType: 'json',
                async:false,
                success: function (result) {
                    if (result.code == 200000) {
                        XSCP30TisMeetTheTermOfPurchase = result.data.isMeetTheTermOfPurchase;

                    }
                }
            });

            req.callGet({
                url: 'product/getWebappProduct.do',
                data: {
                    pCode: 'QTDS'
                },
                dataType: 'json',
                async:false,
                success: function (result) {
                    if (result.code == 200000) {
                        QTDSTisMeetTheTermOfPurchase = result.data.isMeetTheTermOfPurchase;

                    }
                }
            });

            var XSCP30Tistender = false;
            req.callGet({
                url: 'product/myInvestmentProducts.do',
                data: {
                    pCode: 'XSCP30T',
                    currentPage: 1,
                    pageSize: 10,
                    type: 1
                },
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code == 200000) {
                        console.log(result.data.list.length);
                        if (result.data.list != null && result.data.list.length > 0) {
                            XSCP30Tistender = true;
                        }

                    }
                }
            });

            req.callGet({
                url: 'product/myInvestmentProducts.do',
                data: {
                    pCode: 'XSCP30T',
                    currentPage: 1,
                    pageSize: 10,
                    type: 2
                },
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code == 200000) {
                        console.log(result.data.list.length);
                        if (result.data.list != null && result.data.list.length > 0) {
                            XSCP30Tistender = true;
                        }

                    }
                }
            });

            var qtdsistender = false;
            req.callGet({
                url: 'product/myInvestmentProducts.do',
                data: {
                    pCode: 'QTDS',
                    currentPage: 1,
                    pageSize: 10,
                    type: 1
                },
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code == 200000) {
                        if (result.data.list != null && result.data.list.length > 0) {
                            qtdsistender = true;
                        }

                    }
                }
            });
            req.callGet({
                url: 'product/myInvestmentProducts.do',
                data: {
                    pCode: 'QTDS',
                    currentPage: 1,
                    pageSize: 10,
                    type: 2
                },
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code == 200000) {
                        if (result.data.list != null && result.data.list.length > 0) {
                            qtdsistender = true;
                        }

                    }
                }
            });


            if(XSCP30TisMeetTheTermOfPurchase || XSCP30Tistender) {
                $("#thirtyDays").parent().show();
                $("#sevenDays").parent().hide();
            } else if (qtdsistender) {
                $("#thirtyDays").parent().hide();
                $("#sevenDays").parent().show();
            }
        },

        eventBind: function () {
            var bindings = [
                {
                    element: '#monthFinance',
                    event: 'click',
                    handler: bidHistoryCtrl.monthFinance
                },
                {
                    element: '#stepUpward',
                    event: 'click',
                    handler: bidHistoryCtrl.stepUpward
                },
                {
                    element: '#yypHistory',
                    event: 'click',
                    handler: bidHistoryCtrl.yypHistory
                },
                {
                    element: '#sevenDays',
                    event: 'click',
                    handler: bidHistoryCtrl.sevenDays
                },
                {
                    element: '#thirtyDays',
                    event: 'click',
                    handler: bidHistoryCtrl.thirtyDays
                },
                {
                    element: '#daydayEarnings',
                    event: 'click',
                    handler: bidHistoryCtrl.daydayEarnings
                },
                {
                    element:'a[name="borrowHistory"]',
                    event:'click',
                    handler:bidHistoryCtrl.borrowHistory
                } ,
                {
                   element:'a[name="planHistory"]',
                   event:'click',
                   handler:bidHistoryCtrl.planHistory
               },
                {
                   element:'a[name="tradeTransfer"]',
                   event:'click',
                   handler:bidHistoryCtrl.tradeTransfer
               },
                {
                    element:'a[name="xinshou"]',
                    event:'click',
                    handler:bidHistoryCtrl.xinshou
                }
            ];
            bidHistoryView.bindEvents({
                        bindings: bindings
                    }
            );
        },
        xinshou:function() {
            req.callGet({
                url: 'currentDate.do',
                data: {
                },
                dataType: 'json',
                success: function (result) {
                    window.location.href = result.activity_url + "newbid/bidrecord";
                }
            });
        },
        tradeTransfer:function(){
            GS.loadPage('account/tradeTransferHistory.html?path=account');
        },
        planHistory:function(){
            GS.loadPage('account/planHistory.html?path=account');
        },
        borrowHistory:function(){
            GS.loadPage('account/borrowHistory.html?path=account');
        },
        daydayEarnings: function () {
            req.callGet({
                url: 'fund/selectIsInvested.do',
                data: {
                },
                dataType: 'json',
                success: function (data) {
                    if (data != null && data.isInvested == 'true') {
                        GS.loadPage('fund/fundDetailsList.html?tab=earnings&path=fund');
                    } else {
                        GS.loadPage('fund/fundUnInvested.html?path=fund');
                    }
                }
            });
        },
        sevenDays: function () {
            GS.loadPage('account/sevenDaysRecord.html?path=account');
        },
        thirtyDays:function() {
            GS.loadPage('account/thirtyDaysRecord.html?path=account');
        },
        monthFinance: function () {
            GS.loadPage("monthFinance/monthFinanceHistory.html?path=monthFinance");
        },
        stepUpward: function () {
            GS.loadPage("stepUpward/stepUpwardHistory.html?path=stepUpward");
        },
        yypHistory: function () {
            GS.loadPage("yyp/yypHistory.html?path=yyp");
        },
        selectTender: function () {
            req.callJSON({
                url: "account/tenderQuery.do",
                data: {
                    currentPage: 1,
                    pageSize: 1
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    var tenderSum = 0.00;
                    if (data.tenderSum != null) {
                        tenderSum = parseFloat(data.tenderSum);
                    }
                    $$("#btenderSum").html(appFunc.fmoney(tenderSum, 2));
                }
            });
        }
    };
    return bidHistoryCtrl;
});