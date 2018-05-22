require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float, track, store) {
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
    //合规运营切换
    compliance();
    function compliance() {
        var $tabsli = $(".J_able"), $tabContents = $(".J_content");
        $tabsli.on("click", function (ev) {
            var $me = $(this);
            var index = $tabsli.index($me);
            $('.J_informBox').addClass('hide');
            $tabContents.addClass("hide").eq(index).removeClass("hide");
            $('.J_title').addClass("hide").eq(index).removeClass("hide");
            $('.J_firstTitle,.J_informs').addClass('hide');
            $('body,html').animate({scrollTop: 0}, 0);
        });
        $('.J_back').click(function () {
            $('.J_firstTitle,.J_informBox,.J_informs').removeClass('hide');
            $('.J_title,.J_content').addClass('hide');
        });
    }
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});