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
            },
            data    : {},
            type    : 'GET',
            success : function (str) {
                // 上线前注释掉
                console.log(str);

                if (str.code == 200000) {

                    var oProduct = str.data.productInfo;

                    $('.dt_least_invest_amount').text(oProduct.leastInvestAmount);
                    $('.dt_step').text(oProduct.step);
                    $('.dt_most_invest_amount').text(oProduct.mostInvestAmount);
                    $('.dt_planned_annual_rate').text(oProduct.plannedAnnualRate);
                    $('.dt_period').text(oProduct.period);

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