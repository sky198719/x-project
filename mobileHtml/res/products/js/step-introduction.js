require(['jquery', 'requirejs', 'trackBase', 'com'], function ($, requirejs, track, com) {
    $(function () {
        // 共公部份 sta
        document.addEventListener('touchstart', function () {
        });
        // 共公部份 end

        // 服务协议 STA

        // 测试用
        // var sClientTime = com.getNowTime();

        // 传参
        var sProductId   = com.getUrlValue('productId');
        var sProductCode = com.getUrlValue('productCode');
        var sClientId    = 'XXD_FRONT_END_H5';
        var sClientTime = com.getUrlValue('clientTime');
        $.ajax({
            url     : '/tradeCenter/investBiz/productDetail' + '?productCode=' + sProductCode + '&productId=' + sProductId,
            dataType: 'json',
            // async   : false,
            headers : {
                "Accept"    : "application/json;charset=utf-8",
                "clientId"  : sClientId,
                "clientTime": sClientTime,
                "s"         : "f1fd61c58ad2e29dc072403144bbe78c"
            },
            data    : {},
            type    : 'GET',
            success : function (str) {
                // 上线前注释掉
                console.log(str);

                if (str.code == 200000) {

                    var oProduct = str.data.productInfo;

                    // 最低年化利率：		productInfo.plannedAnnualRateFrom
                    $('.dt_planned_annual_rate_from').text(oProduct.plannedAnnualRateFrom);
                    // 逐月增加利率：		productInfo.plannedAnnualStepRate
                    $('.dt_planned_annual_step_rate').text(oProduct.plannedAnnualStepRate);
                    // 封顶利率：		productInfo.plannedAnnualRateTo
                    $('.dt_planned_annual_rate_to').text(oProduct.plannedAnnualRateTo);
                    // 起投金额：		productInfo.leastInvestAmount
                    $('.dt_least_invest_amount').text(oProduct.leastInvestAmount);
                    // 购买单位：		productInfo.step
                    $('.dt_step').text(oProduct.step);
                    // 投资上限：		productInfo.mostInvestAmount
                    $('.dt_most_invest_amount').text(oProduct.mostInvestAmount);
                    // 退出金额：		productInfo.quitStep
                    $('.dt_quit_step').text(oProduct.quitStep);


                } else {
                    alert('服务协议：' + str.message)
                }
            },
            error   : function (str) {
                alert('服务协议：' + '信息查询失败，请联系客服')
            }
        });
        // 服务协议 END


    });

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});