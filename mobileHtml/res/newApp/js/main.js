require(['base', "requirejs", "trackBase","echarts_simple","Swiper"], function ($, requirejs,track,echarts,Swiper) {

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});