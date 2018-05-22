// /**
//  * Created by gaoshanshan_syp on 2017/6/5.
//  */
require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs"], function ($, float, track, store) {
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
    //   tab
    $('#achieve-list li').each(function () {
        $(this).click(function () {
            $(this).addClass("active").siblings().removeClass("active");
            var project=$(this).attr("project");
            $("."+project).removeClass('disnone').siblings().addClass('disnone');
        })
    });
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
