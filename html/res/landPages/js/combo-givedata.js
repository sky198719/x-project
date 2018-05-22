/**
 * Created by gaoshanshan_syp on 2017/7/18.
 */
require(['base', 'float', 'trackBase', "requirejs"], function ($, float, track) {
    var dataUrl={
        commonUrl:'/tradeCenter/investBiz/showStatus/',
        yjdjUrl:'/tradeCenter/YJDJ/brief',
        xybUrl:'/tradeCenter/XYB/brief'
    };
    //注册弹窗
    var close=true,rule=false;
    // var screen=window.screen.width;
    mainScroll(580);
    function mainScroll(h) {
        $(window).scroll(scrolls);
        scrolls();
        function scrolls() {
            var wd_st=document.documentElement.scrollTop || document.body.scrollTop;
            var j_suspend=$("#J_suspend").offset().top;
            var hAll = $(document).height();
            var wh = $(window).height();
            if(wd_st>=h && close){
                $("#J_sidebar").removeClass("disnone");
            }else{
                $("#J_sidebar").addClass("disnone");
            }

            if(wd_st>=h && hAll-wh-wd_st>304){
                $("#J_suspendpop").removeClass("disnone");
            }else{
                $("#J_suspendpop").addClass("disnone");
            }
            //底部
            // if(rule){
            //     var ruleL=$("#J_ruledetails").height();
            //     if(wd_st>=3000+ruleL){
            //         $("#J_suspendpop").addClass("disnone");
            //
            //     }else{
            //         if(wd_st>=h){
            //             $("#J_suspendpop").removeClass("disnone");
            //         }else{
            //             $("#J_suspendpop").addClass("disnone");
            //         }
            //     }
            // }else{
            //     if(wd_st>=3000){
            //         $("#J_suspendpop").addClass("disnone");
            //
            //     }else{
            //         if(wd_st>=h){
            //             $("#J_suspendpop").removeClass("disnone");
            //         }else{
            //             $("#J_suspendpop").addClass("disnone");
            //         }
            //     }
            // }
            showLighter(wd_st);
        }
        //显示颜色 函数
        function showLighter(slidetop) {
            var content=document.documentElement.scrollHeight || document.body.scrollHeight,winH=$(window).height();

            $('#J_siderlist li a').each(function (item,val) {
                var J_item=$(val).attr('href');
                var $t=$(J_item).offset().top;
                var $h=$(J_item).height();

                if(slidetop>=$t && slidetop<$t+$h){
                    $(val).parent().siblings('li').removeClass('lighter-location');
                    $(val).parent().addClass('lighter-location');
                }
            });
            if(content-winH<=slidetop){
                $("#J_last").siblings('li').removeClass('lighter-location');
                $("#J_last").addClass('lighter-location');
                $("#J_suspendpop").addClass("disnone");
            }
        }

    }

    //展示数据
    xscpData();
    xybData();
    yjdjData();
    bbgsData();
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
                if (data.code == "200000" &&(result=data.data.productInfo)) {
                    $("#J_xscpApr").html(result.apr);
                    $("#J_xscpPeriod").html(result.period);
                    if(result.floatApr){
                        $("#J_xscpfloatApr").html(result.floatApr);
                        var total=result.apr+result.floatApr;
                        $("#J_xscpTotalApr").html(total);
                    }else{
                        $('.J_addFloat,#J_xscpfloatApr,.J_floatPercent').hide();
                        $("#J_xscpTotalApr").html(result.apr);
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
        var array = data.items,xybProduct,xybApr;
        xybProduct=  getNvalueByCode(array,36);
        xybApr=xybProduct.plannedAnnualRateFrom;
        if(xybProduct.floatingRate){
            xybApr=xybApr+xybProduct.floatingRate;
        }
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
                if (data.code == "200000"&& (result=data.data)) {
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
    $("#J_siderClose").on('click',function (ev) {
        var ev=ev || event;
        $("#J_sidebar").addClass('disnone');
        close=false;
        ev.preventDefault();
    });
//    规则展开
    $("#J_rule").on('click',function (ev) {
        var ev=ev || event;
        $("#J_rule").addClass('disnone');
        $('#J_ruledetails').removeClass('disnone');
        // rule=true;
        mainScroll(580);
        ev.preventDefault();
    });
    $("#J_ruleBtn").on('click',function (ev) {
        var ev=ev || event;
        $("#J_rule").removeClass('disnone');
        $('#J_ruledetails').addClass('disnone');
        // rule=false;
        mainScroll(580);
        ev.preventDefault();
    });
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});