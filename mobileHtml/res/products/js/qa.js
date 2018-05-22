require(['base', 'requirejs', 'trackBase', 'com'], function ($, requirejs, track, com) {
    // console.log('这是基础部份');
    // console.log('↓');
    $(function () {
        document.addEventListener('touchstart', function () {
        });
    });

    //产品介绍接口
    function getProductInfo() {
        var sProductCode = getUrlParam("productCode");
        var sProductId   = getUrlParam("productId");
        var sClientTime  = getUrlParam("clientTime");
        // 调试用
        // var sClientTime = new Date().getTime();
        $.ajax({
            url     : '/tradeCenter/investBiz/' + sProductCode + '/' + sProductId,
            dataType: 'json',
            type    : 'GET',
            headers : {
                "Accept"    : "application/json;charset=UTF-8",
                "clientId"  : "XXD_FRONT_END_H5",
                "clientTime": sClientTime
            },
            success : function (result) {
                // console.log(result);
                if (result.code == 200000) {
                    if (sProductCode == "BBGS") {
                        $('.rateFrom').text(result.data.plannedAnnualRateFrom);
                        $('.rateStep').text(result.data.plannedAnnualStepRate);
                        $('.rateTo').text(result.data.plannedAnnualRateTo);
                    } else if (sProductCode == "XYB" || sProductCode == "YYP") {
                        $('.productRate').text(result.data.plannedAnnualRate);
                        if (result.data.period == "1") {
                            $('.calculate').text("");
                        } else if (result.data.period == "12") {
                            $('.hide').css("display", "none");
                        } else {
                            $('.calculate').text("×" + result.data.period);
                        }

                    }else if(sProductCode == 'QTDS'){
                        $('#floatRate').text(result.data.floatingRate)
                    }
                } else {
                    alert('qa:'+result.message)
                }

            },
            error   : function () {
                alert('qa:产品信息查询失败，请检查网络!')
            }
        });
    }

    getProductInfo();
    function getUrlParam(item) {
        var pattern = new RegExp("[?&]" + item + "\=([^&]+)", "g");
        var matcher = pattern.exec(decodeURIComponent(location.href));
        var items   = null;
        if (null != matcher) {
            try {
                items = decodeURIComponent(decodeURIComponent(matcher[1]));
            } catch (e) {
                try {
                    items = decodeURIComponent(matcher[1]);
                } catch (e) {
                    items = matcher[1];
                }
            }
        }
        //console.log(items);
        return items;
    }

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});