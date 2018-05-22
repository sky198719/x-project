require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs",], function ($, echarts, float, track, store) {

    $.ajax({
        url: "/biz/bulletin/productIntro",
        type: "get",
        dataType: "json",
        beforeSend: function (request) {
            request.setRequestHeader("s", "testxx");
            request.setRequestHeader("clientId", "testxx");
            request.setRequestHeader("clientTime", "1459845047000");
        },
        success: function (msg) {
            if (msg.code == "200000") {
                $.each(msg.data.items, function (ind, obj) {
                    if (obj.productType == "1") {
                        //$("#J_proSevenDay").children("li").eq(2).html(obj.lowestTender + "元起投，单人出借上限" + obj.mostTender + "元");
                        //$("#J_proSevenDay").children("li").eq(3).html("限购一次 " + obj.closeTerm + "天期");
                        //$("#J_proSevenDay").children("li").eq(4).html("历史年化收益：老用户" + obj.oldUserAnnualRate + "%、新用户" + obj.newUserAnnualRate + "%");
                    }
                    if (obj.productType == "2") {
                        //$("#J_proMonthGold").children("li").eq(2).html("单人出借上限" + obj.mostTender + "元");
                        //$("#J_proMonthGold").children("li").eq(3).html(obj.closeTerm + "天期");
                        //$("#J_proMonthGold").children("li").eq(4).html("历史年化收益：" + obj.annualRate + "%");
                    }
                    if (obj.productType == "3") {
                        $("#J_proDDGain").children("li").eq(2).html(obj.lowestTender + "元起投 单人出借上限" + obj.mostTender + "元");
                        $("#J_proDDGain").children("li").eq(3).html("历史年化收益：PC端" + obj.pcAnnualRate + "%、移动端" + obj.appAnnualRate + "%");

                    }
                    if (obj.productType == "5") {
                        $("#J_proXYB").children("li").eq(2).html(obj.lowestTender + "元起投");
                        var oneMonthsAnnualRate = '';
                        if (typeof(obj.oneMonthsAnnualRate) == "undefined" || obj.oneMonthsAnnualRate == 0) {

                        } else {
                            oneMonthsAnnualRate = "1月期" + obj.oneMonthsAnnualRate + "%";
                        }
                        var threeMonthsAnnualRate = '';
                        if (typeof(obj.threeMonthsAnnualRate) == "undefined" || obj.threeMonthsAnnualRate == 0) {

                        } else {
                            threeMonthsAnnualRate = "/3月期" + obj.threeMonthsAnnualRate + "%";
                        }
                        var sixMonthsAnnualRate = '';
                        if (typeof(obj.sixMonthsAnnualRate) == "undefined" || obj.sixMonthsAnnualRate == 0) {

                        } else {
                            sixMonthsAnnualRate = "/6月期" + obj.sixMonthsAnnualRate + "%";
                        }
                        var twelveMonthsAnnualRate = '';
                        if (typeof(obj.twelveMonthsAnnualRate) == "undefined" || obj.twelveMonthsAnnualRate == 0) {

                        } else {
                            twelveMonthsAnnualRate = "/12月期" + obj.twelveMonthsAnnualRate + "%";
                        }


                        var eighteenMonthsAnnualRate = '';
                        if (typeof(obj.eighteenMonthsAnnualRate) == "undefined" || obj.eighteenMonthsAnnualRate == 0) {

                        } else {
                            eighteenMonthsAnnualRate = "/18月期" + obj.eighteenMonthsAnnualRate + "%";
                        }

                        var twentyFourMonthsAnnualRate = '';
                        if (typeof(obj.twentyFourMonthsAnnualRate) == "undefined" || obj.twentyFourMonthsAnnualRate == 0) {

                        } else {
                            twentyFourMonthsAnnualRate = "/24月期" + obj.twentyFourMonthsAnnualRate + "%";
                        }

                        var thirtySixMonthsAnnualRate = '';
                        if (typeof(obj.thirtySixMonthsAnnualRate) == "undefined" || obj.thirtySixMonthsAnnualRate == 0) {

                        } else {
                            thirtySixMonthsAnnualRate = "/36月期" + obj.thirtySixMonthsAnnualRate + "%";
                        }

                        $("#J_proXYB").children("li").eq(3).html("历史年化收益：" + oneMonthsAnnualRate + threeMonthsAnnualRate + sixMonthsAnnualRate +
                            twelveMonthsAnnualRate + eighteenMonthsAnnualRate);
                        $("#J_proXYB").children("li").eq(4).html(twentyFourMonthsAnnualRate + thirtySixMonthsAnnualRate);

                    }
                    if (obj.productType == "6") {
                        $("#J_proYYp").children("li").eq(2).html(obj.lowestTender + "元起投");
                        $("#J_proYYp").children("li").eq(3).html("历史年化收益：12月期" + obj.twelveMonthsAnnualRate + "%");

                    }
                });
            } else {
                console.log("errorCode:" + msg.code)
            }
        },
        error: function (msg) {
            console.log("errorMsg：" + JSON.stringify(msg));
        }
    });
    //七天大胜
    $.xxdAjax({
        url: '/tradeCenter/QTDS/brief',
        clientId: 'XXD_FRONT_END',
        type: 'get',
        s:'71824bd75e1b757773d738537f2c9441',
        data: {},
        callbacks: function (data) {
            if ((data.code == "200000")) {
                var res = data.data;
                if (res.floatingRate) {
                    $("#J_proSevenDay").children("li").eq(4).html("历史年化收益：老用户" + res.plannedAnnualRate + "%、新用户" + res.plannedAnnualRate + "%+" + res.floatingRate + "%");
                } else {
                    $("#J_proSevenDay").children("li").eq(4).html("历史年化收益：老用户" + res.plannedAnnualRate + "%、新用户" + res.plannedAnnualRate + "%");
                }
                $("#J_proSevenDay").children("li").eq(2).html(res.leastTenderAmountLabel + "起投，单人出借上限" + res.mostTenderAmountLabel);
                $("#J_proSevenDay").children("li").eq(3).html("限购一次" + res.leastPeriod + "天期");
            }
        },
        error: function () {
        }
    });
    //月进斗金
    $.xxdAjax({
        url: '/tradeCenter/YJDJ/brief',
        clientId: 'XXD_FRONT_END',
        type: 'get',
        s:'71824bd75e1b757773d738537f2c9441',
        data: {},
        callbacks: function (data) {
            if ((data.code == "200000")) {
                var res = data.data;
                $("#J_proMonthGold").children("li").eq(2).html("单人出借上限" + res.mostTenderAmountLabel);
                $("#J_proMonthGold").children("li").eq(3).html(res.leastPeriod + "天期");
                if (res.floatingRate) {
                    $("#J_proMonthGold").children("li").eq(4).html("历史年化收益：" + res.plannedAnnualRate + "%+" + res.floatingRate + "%");
                } else {
                    $("#J_proMonthGold").children("li").eq(4).html("历史年化收益：" + res.plannedAnnualRate + "%");
                }
            }
        },
        error: function () {
        }
    });
});