/**
 * Created by gaoshanshan_syp on 2017/9/19.
 */
require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float, track, store) {


    start();
    function start() {
        isTime({
            success: function (r) {
                var oDay = new Date(r).getDay(), oTime = new Date(r).getHours();
                if (oDay == 3 && oTime >= 9) {
                    $("#J_leftBtn,#J_rightBtn").removeClass('overtime-btn').addClass('on-btn');
                    //翻牌
                    $(document).on("mouseenter", "div.flip", function () {
                        var $front = $(this).find('.front'), $behind = $(this).find('.behind'), speed = 80, dis = 366;
                        $front.animate({left: dis / 2, width: 0}, speed, function () {
                            $front.hide();
                            $behind.show().animate({left: 0, width: dis}, speed);
                        });
                    });
                    $(document).on("mouseleave", "div.flip", function () {
                        var $front = $(this).find('.front'), $behind = $(this).find('.behind'), speed = 80, dis = 366;
                        $behind.animate({left: dis / 2, width: 0}, speed, function () {
                            $behind.hide();
                            $front.show().animate({left: 0, width: dis}, speed);
                        });
                    });
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