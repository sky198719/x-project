require(['base', 'float','trackBase', 'store', "dialog",'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float,track, store, dialog) {
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
    $(".getBtn").hover(function () {
        $(".dimension").toggleClass("show");
    });
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});