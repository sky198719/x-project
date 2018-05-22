require(['base', 'requirejs', 'trackBase', 'xxdBridge', 'com'], function ($, requirejs, track, xxdBridge, com) {
    $(function () {
        document.addEventListener('touchstart', function () {
        });

        // 还款保障措施 STA
        var sProductId   = com.getUrlValue('productId');
        var sProductCode = com.getUrlValue('productCode');
        var sClientId    = "XXD_FRONT_END_H5";
        var sClientTime  = com.getUrlValue('clientTime');
        $.ajax({
            url     : '/investmentAPI/financialProduct/' + sProductCode + "/" + sProductId,
            dataType: 'json',
            // async   : false,
            headers : {
                "Accept"    : "application/json;charset=UTF-8",
                "clientId"  : sClientId,
                "clientTime": sClientTime
            },
            data    : {},
            type    : 'GET',
            success : function (str) {
                console.log(str);
                if (str.code == 200000) {

                    var sType          = str.data.type;
                    var sPlannedAmount = str.data.plannedAmount;

                    if (sType == 9) {
                        console.log('新商贷');
                        if (sPlannedAmount <= 300000) {
                            console.log('小于等于30万');
                            $('#repayment1').show();
                        } else {
                            console.log('大于30万');
                            $('#repayment2').show();
                        }
                    }
                    if (sType == 10) {
                        console.log('房主贷');
                        $('#repayment3').show();
                    }
                    if (sType == 14) {
                        console.log('车主贷');
                        $('#repayment4').show();
                    }

                } else {
                    $('body').html('<p class="n1e ac">数据加载失败，请重试！</p>');
                    alert('还款保障措施：' + str.message)
                }
            },
            error   : function (str) {
                $('body').text('<p class="n1e ac">数据加载失败，请重试！</p>');
                alert('还款保障措：' + '信息查询失败，请联系客服')
            }
        });
        // 还款保障措施 END

    });


}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});