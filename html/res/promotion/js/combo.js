/**
 * Created by gaoshanshan_syp on 2017/9/29.
 */
require(['base', 'float','trackBase', 'store', "dialog",'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float,track, store, dialog) {

    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});