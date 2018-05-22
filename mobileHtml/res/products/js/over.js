require(['base', 'requirejs', 'trackBase', 'json', 'xxdBridge'], function ($, requirejs, track, json, xxdBridge) {
    setTimeout(function () {
        xxdBridge.open({pagename: 'hotproduct'}, function (isTimeout) {
            if (isTimeout) {
                window.location.href = 'http://www.xinxindai.com/m/#!/static/html/popular/financesList.html';
            }
        })
    }, 2000)

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});