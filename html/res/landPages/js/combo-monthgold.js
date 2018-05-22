/**
 * Created by gaoshanshan_syp on 2017/7/18.
 */
require(['base', 'float', 'trackBase', "requirejs"], function ($, float, track) {
    var dataUrl={
        commonUrl:'/tradeCenter/investBiz/showStatus/',
        yjdjUrl:'/tradeCenter/YJDJ/brief',
        xybUrl:'/tradeCenter/XYB/brief'
    };
    //展示数据
    yjdjData();
    xscpData();
    xybData();
    bbgsData();
    //  月进斗金
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
                     if(result.floatingRate){
                         rate=rate+result.floatingRate;
                     }
                    var leastTender= result.leastTenderAmountLabel;
                    var lTender=leastTender.split('元')[0];
                    $("#J_yjdjApr").html(rate);
                    $("#J_yjdjPeriod,#J_yjdjDay,#J_Day").html(result.leastPeriod);
                    $("#J_leastPeriodUnit").html(result.leastPeriodUnit);
                    $("#J_lowTender,#J_yjdjStep").html(leastTender);
                    $("#J_yjdjLowestTender").html(lTender);
                    $("#J_mostTender").html(result.mostTenderAmountLabel);
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }

        });
    }
    //新手产品数据
    function xscpData() {
        $.xxdAjax({
            url      : dataUrl.commonUrl+'XSCP30T',
            type:'get',
            clientId: "XXD_INTEGRATION_PLATFORM",
            data     : {},
            dataType : 'json',
            callbacks:function (data) {
                var result;
                if (data.code == "200000" && (result=data.data.productInfo)) {
                    // $("#J_xscpName").html(result.name);
                    if(result.floatApr){
                        $('.J_xscpData').append('<span class="big">'+result.apr+'</span><span class="small">%+</span><span class="big" id="J_xscpfloatApr">'+result.floatApr+'</span><span class="small">%</span>');
                        var income = Math.floor(10000*(result.apr/100+result.floatApr/100)/360 *100)/100;
                        $('.J_xscpIncome').html(income);
                    }else{
                        $('.J_xscpData').append('<span class="big">'+result.apr+'</span><span class="small">%</span>');
                        var income = Math.floor(10000*(result.apr/100)/360 *100)/100;
                        $('.J_xscpIncome').html(income);
                    }
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }

        });
    }
    //新元宝
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
        var array = data.items,xybProduct,xybProductMin,xybMax,xybMin;
        xybProduct=  getNvalueByCode(array,36);
        xybProductMin =getNvalueByCode(array,1);
        xybMax=xybProduct.plannedAnnualRateFrom;
        xybMin = xybProductMin.plannedAnnualRateFrom;
        /*
        if(xybProduct.floatingRate){
            xybMax=xybMax+xybProduct.floatingRate;
        }
        if(xybProductMin.floatingRate){
            xybMin=xybMin+xybProductMin.floatingRate;
        }
        */
        $("#J_xybName").html(data.name);
        $("#J_xybMax").html(xybMax);
        $("#J_xybMin").html(xybMin);
        $("#J_xybAccount").html(xybProduct.leastTenderAmountLabel);
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
                    //$("#J_bbgsName").html(result.name);
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

var timer = new Date();
var myTime = timer.getTime(); //本地时间
//月月派
$.ajax({
    url:'/tradeCenter/investBiz/showStatus/YYP',
    type:'get',
    async:false,
    cache:false,
    beforeSend:function(request){
        request.setRequestHeader('clientId','XXD_FRONT_END');
        request.setRequestHeader('clientTime',myTime);
        request.setRequestHeader('s','s');
    },
    success:function(data){
        $('#J_bbgsMin1').html(data.data.productInfo.apr);
        $('#J_bbgsLeastAmount1').html(data.data.productInfo.lowestTender);
    },
    error:function(){
        alert('网络异常，请重试！');
        return false;
    }
});
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});