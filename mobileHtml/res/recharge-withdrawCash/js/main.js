require(['base', 'requirejs', 'trackBase', 'xxdBridge'], function ($, requirejs, track, xxdBridge) {
    $(function () {
        document.addEventListener('touchstart', function () {
        });

        // 提现成功确定按钮
        $(document).on('click', '#to_withdraw', function () {
            xxdBridge.open({pagename: 'postal'})
        });

        // 充值成功继续充值
        $(document).on('click', '#to_recharge', function () {
            xxdBridge.open({pagename: 'poppage'})
        });
        // 充值成功立即投资
        $(document).on('click', '#to_investment', function () {
            xxdBridge.open({pagename: 'hotproduct'})
        });

        //签约成功确认按钮
        $(document).on('click', '#to_applydetail', function () {
            xxdBridge.open({pagename: 'applydetail'});
        });

    });

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});