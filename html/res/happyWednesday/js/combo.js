/**
 * Created by gaoshanshan_syp on 2017/9/19.
 */
require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float, track, store) {

    //滑动元素显示二维码
    $("#J_immediateBtn a").hover(function(){
        $("#J_qtCode").show();
    },function(){
        $("#J_qtCode").hide();
    });

    start();
    function start() {
        isTime({
            success: function (r) {
                var oDay = new Date(r).getDay(), oTime = new Date(r).getHours();
                if (oDay == 3 && oTime >= 9) {
                    $("#J_leftBtn,#J_rightBtn").removeClass('overtime-btn').addClass('on-btn');
                } else {
                    var oNow     = new Date(r);
                    var autoTime = new Date(oNow.getFullYear(), oNow.getMonth(), oNow.getDate(),9, 0, 0) - new Date(r);
                    if (autoTime >= 0) {
                        var timer = setTimeout(function () {
                            start();
                            clearTimeout(timer);
                        }, autoTime);
                    }
                }
            }
        });
    }


    function isTime(o) {
        var time = null;
        $.ajax({
            url     : '/feapi/currentTime',
            type    : 'get',
            dataType: 'json',
            success : function (r) {
                if (r.code == 200 && r.data.currentTime) {
                    time = r.data.currentTime;
                    o.success(time);
                }else{
                    float.alert({content:'网络问题，请重新尝试！'});
                }
            },
            error   : function (r) {
                $.error(r);
            }
        });
    }
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});