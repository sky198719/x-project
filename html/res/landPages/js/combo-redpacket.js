/**
 * Created by gaoshanshan_syp on 2017/5/27.
 */

require(['base', 'float','dialog', 'trackBase','store', "requirejs"], function ($, float, track, store) {

    var dataUrl={
        commonUrl:'/tradeCenter/investBiz/showStatus/',
        yjdjUrl:'/tradeCenter/YJDJ/brief'
    };
    //读取数据
    xscpData();
    yjdjData();
    bbgsData();

    //固定导航条
    $(window).scroll(scrolls);
    scrolls();
    //DBM布玛
    $(document).on('click', '.regDBM', function () {
        gtag('event', 'purchase', {
            'allow_custom_scripts': true,
            'value': '',
            'transaction_id': '',
            'quantity': '',
            'send_to': 'DC-8281908/sales/65oyhuiy+items_sold'
        });
    });
    function scrolls() {
        var wd_st = $(window).scrollTop();
        if(wd_st>93){
            $('#header-fixed').removeClass('disnone');
        }else{
            $('#header-fixed').addClass('disnone');
        }
    }

  //  新手产品数据

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
                    $("#J_xscpApr").html(result.apr);
                    if(result.floatApr){
                        $("#J_xscpfloatApr").html(result.floatApr);
                        var income = Math.floor(10000*(result.apr/100+result.floatApr/100)/360 *100)/100;
                        $('#J_xscpIncome').html(income);
                    }else{
                        $('.float-add,.float-percent,#J_xscpfloatApr').hide();
                        var income = Math.floor(10000*(result.apr/100)/360 *100)/100;
                        $('#J_xscpIncome').html(income);
                    }
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }

        });
    }

  //  _
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
//_
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
        $('#xyb1m').html(rate3);
        $('#J_bbgsLeastAmount1').html(least);
        //$('#J_bbgsLeastAmount1').html($('#J_bbgsLeastAmount1').html().substring(0,$('#J_bbgsLeastAmount1').html().indexOf('元')));
        $('#J_bbgsMax1').html(rate4);
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