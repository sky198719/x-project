require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs"], function ($, float, track, store) {

    // 布码init
    var userDO = store && store.get("userDO") || {};
    track.init(userDO);

    $.xxdAjax({
        url: '/integrationPlatform/bids/staticResources/P001',
        clientId: 'XXD_FRONT_END',
        type: 'get',
        data: {'typeKey': 'CONTRACT_MODEL_URL','source':'PC'},
        callbacks: function (data) {
            if ((data.code == "200000") || data.data) {
                $('.container').append(data.data.content);
            }
        }
    });


}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
