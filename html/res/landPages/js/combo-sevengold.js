/**
 * Created by gaoshanshan_syp on 2017/5/27.
 */

require(['base', 'float', 'trackBase', "requirejs"], function ($, float, track) {

    var dataUrl={
        commonUrl:'/tradeCenter/investBiz/showStatus/',
        yjdjUrl:'/tradeCenter/YJDJ/brief',
        xybUrl:'/tradeCenter/XYB/brief'
    };
// 七天大胜读取数据
    qtdsData();
    yjdjData();
    bbgsData();
    xybData();
    function qtdsData() {
        $.xxdAjax({
            url      : dataUrl.commonUrl+'QTDS',
            type:'get',
            clientId: "XXD_INTEGRATION_PLATFORM",
            data     : {},
            dataType : 'json',
            callbacks:function (data) {
                var result;
                if (data.code == "200000" && (result=data.data.productInfo)) {
                    var apr=result.apr;
                    if(result.floatApr){
                        var floatApr=result.floatApr;
                        apr=apr+floatApr;
                        $("#J_qtdsFloatApr").html(floatApr);
                    }
                    $("#J_qtdsApr").html(apr);
                    $("#J_qtdsAprOne,#J_qtdsAprTwo").html(result.apr);
                    $("#J_qtdsPeriod").html(result.period);
                    $("#J_qtdsTender,#J_qtdsLow").html(result.lowestTender);
                    $("#J_qtdsHigh").html(result.mostTender);
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }

        });
    }
    //_数据
    function yjdjData() {
        $.xxdAjax({
            url      : dataUrl.yjdjUrl,
            type:'get',
            clientId: "XXD_FRONT_END",
            s: "71824bd75e1b757773d738537f2c9441",
            data     : {},
            dataType : 'json',
            callbacks:function (data) {
                var result;
                if (data.code == "200000" && (result=data.data)) {
                    var rate=result.plannedAnnualRate;
                    if(result.floatApr){
                        rate=rate+result.floatingRate;
                    }
                    $("#J_yjdjName").html(result.name);
                    $("#J_yjdjApr").html(rate);
                    $("#J_yjdjPeriod").html(result.leastPeriod);
                    $("#J_leastPeriodUnit").html(result.leastPeriodUnit);
                    $("#J_yjdjLowestTender").html(result.leastTenderAmountLabel);
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }

        });
    }

    //步步高升数据
    function bbgsData() {
        $.xxdAjax({
            url      : dataUrl.commonUrl+'BBGS',
            type:'get',
            clientId: "XXD_INTEGRATION_PLATFORM",
            data     : {},
            dataType : 'json',
            callbacks:function (data) {
                var result;
                if (data.code == "200000" && (result=data.data.productInfo)) {
                    $("#J_bbgsName").html(result.name);
                    $("#J_bbgsMin").html(result.plannedAnnualRateFrom);
                    $("#J_bbgsMax").html(result.plannedAnnualRateTo);
                    $("#J_bbgsLeastAmount").html(result.leastInvestAmount);
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }

        });
    }

    //    _
    function xybData() {
        $.xxdAjax({
            url      : dataUrl.xybUrl,
            type:'get',
            clientId: "XXD_FRONT_END",
            s: "71824bd75e1b757773d738537f2c9441",
            data     : {},
            dataType : 'json',
            callbacks:function (data) {
                var result;
                if (data.code == "200000"&& (result=data.data)) {
                    fnOperation(result);
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }

        });
    }

    function fnOperation(data) {
        var array = data.items,xybProduct,xybApr,xybMpro,xybMin;
        xybProduct=  getNvalueByCode(array,36);
        xybApr=xybProduct.plannedAnnualRateFrom;
        xybMpro=  getNvalueByCode(array,1);
        xybMin=xybMpro.plannedAnnualRateFrom;
        if(xybProduct.floatingRate){
            xybApr=xybApr+xybProduct.floatingRate;
        }
        $("#J_xybName").html(data.name);
        $("#J_xybApr").html(xybApr);
        $("#J_xybAccount").html(xybProduct.leastTenderAmountLabel);
        $('#J_xybMin').html(xybMin);
    }

    function getNvalueByCode(array, leastPeriod) {
        if (!array || !(array instanceof Array)) {
            return 0;
        }
        var length = array.length;
        for (var i = 0; i < length; i++) {
            if(array[i].leastPeriod == leastPeriod) {
                return array[i];
            }
        }
        return 0;
    }

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});