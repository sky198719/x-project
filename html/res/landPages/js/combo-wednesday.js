require(['base', 'float', 'trackBase', "requirejs"], function ($, float, track) {
    var dataUrl={
        commonUrl:'/tradeCenter/investBiz/showStatus/',
    };
    //注册弹窗
    var close=true,rule=false;
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
        }
    }
    bbgsData();
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
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});