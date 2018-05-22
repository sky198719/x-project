require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs"], function ($, float, track, store) {

    // 布码init
    var userDO = store && store.get("userDO") || {};
    track.init(userDO);


}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
