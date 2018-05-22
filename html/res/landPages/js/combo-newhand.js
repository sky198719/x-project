/**
 * Created by gaoshanshan_syp on 2017/5/27.
 */

require(['base', 'float', 'trackBase', "requirejs"], function ($, float, track) {

    //读取cookie
    function getCookie(name){
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg)){
            return unescape(arr[2]);
        }else{
            return null;
        }
    }
    function setCookie(name,value){
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days*24*60*60*1000);
        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
    }

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
    //月进斗金数据
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

    //    新元宝
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
        var array = data.items,xybProduct,xybApr;
        xybProduct=  getNvalueByCode(array,36);
        xybApr=xybProduct.plannedAnnualRateFrom;
        if(xybProduct.floatingRate){
            xybApr=xybApr+xybProduct.floatingRate;
        }
        $("#J_xybName").html(data.name);
        $("#J_xybApr").html(xybApr);
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
    //点击注册框进行布玛
    $("#J_submit").on('click',function(){
        dmpGtag();
    });
    //布玛
    function dmpGtag() {
        gtag('event', 'purchase', {
            'allow_custom_scripts': true,
            'value': '',
            'transaction_id': '',
            'quantity': '',
            'send_to': 'DC-8281908/sales/efbhdljz+items_sold'
        });
    }
//修改
function returnFloat(value){
    var value=Math.floor(parseFloat(value)*100)/100;
    var xsd=value.toString().split(".");
    if(xsd.length==1){
     value=value.toString()+".00";
        return value;
    }
    if(xsd.length>1){
        if(xsd[1].length<2){
            value=value.toString()+"0";
        }
    }
    return value;
}
//新手专享30天
    var timer = new Date();
    var myTime = timer.getTime(); //本地时间
    $.ajax({
        url:'/tradeCenter/investBiz/showStatus/XSCP30T',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_FRONT_END');
            request.setRequestHeader('clientTime',myTime);
            request.setRequestHeader('s','s');
        },
        success:function(data){
            var apr = data.data.productInfo.apr;
            var floatApr;
            if(data.data.productInfo.floatApr == '' || data.data.productInfo.floatApr == undefined || data.data.productInfo.floatApr == null){
                floatApr = 0;
                $('#J_qtdsApr2').fadeOut(0);
                $('#plusins').fadeOut(0);
                $('#centerins').fadeOut(0);
            }else{
                floatApr = data.data.productInfo.floatApr;
            }
            $('#J_qtdsApr1').html(apr);
            $('#J_qtdsApr2').html(floatApr);
            // $('#J_qtdsPeriod1').html(data.data.productInfo.period);
            $('#J_qtdsTender1').html(data.data.productInfo.lowestTender);
            $.ajax({
                url:'/tradeCenter/XYB/brief',
                type:'get',
                async:false,
                cache:false,
                beforeSend:function(request){
                    request.setRequestHeader('clientId','XXD_FRONT_END');
                    request.setRequestHeader('clientTime',myTime);
                    request.setRequestHeader('s','s');
                },
                success:function(data2){
                    var rate1;
                    var rate2;
                    $.each(data2.data.items,function(index,item){
                        if(item.frozenPeriod == 1){
                            rate1 = item.plannedAnnualRateFrom;
                        }
                        if(item.frozenPeriod == 3){
                            rate2 = item.plannedAnnualRateFrom;
                        }
                    });
                    $('#most2').html(10000 * (apr + floatApr) / 12 / 100 + 10000 * rate1 / 12 / 100 + 108);
                    $('#most2').html(returnFloat($('#most2').html()));
                    $('#most5').html(10000 * (apr + floatApr) / 12 / 100 + 50000 * rate2 / 4 / 100 + 108);
                    $('#most5').html(returnFloat($('#most5').html()));
                },
                error:function(){
                    alert('网络异常，请重试！');
                    return false;
                }
            });
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

//新元宝
    $.ajax({
        url:'/tradeCenter/XYB/brief',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_FRONT_END');
            request.setRequestHeader('clientTime',myTime);
            request.setRequestHeader('s','s');
        },
        success:function(data){
            var rate3;
            var rate4;
            var least;
            $.each(data.data.items,function(index,item){
                if(item.frozenPeriod == 1){
                    rate3 = item.plannedAnnualRateFrom;
                    least = item.leastTenderAmountLabel;
                }
                if(item.frozenPeriod == 36){
                    rate4 = item.plannedAnnualRateFrom;
                }
            });
            $('#J_bbgsMin1').html(rate3);
            $('#J_bbgsLeastAmount1').html(least);
            $('#J_bbgsLeastAmount1').html($('#J_bbgsLeastAmount1').html().substring(0,$('#J_bbgsLeastAmount1').html().indexOf('元')));
            $('#J_bbgsMax1').html(rate4);
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });
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
            $('#J_xybMin1').html(data.data.productInfo.apr);
            $('#J_xybAccount1').html(data.data.productInfo.lowestTender);
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