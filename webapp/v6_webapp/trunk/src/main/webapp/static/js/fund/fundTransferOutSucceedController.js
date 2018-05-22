/**
 * Created by chaihuangqi on 2015/8/14.
 */
/**
 * 日日盈转出成功页面
 */
define(function () {
    var fundTransferOutSucCtrl = {
        init: function () {

            var bindings = [
                {
                    element:'#complete',
                    event: 'click',
                    handler: fundTransferOutSucCtrl.complete
                },
                {
                    element:'#checkAccount',
                    event: 'click',
                    handler: fundTransferOutSucCtrl.checkAccount
                },
                {
                    element:'#drawMoney',
                    event: 'click',
                    handler: fundTransferOutSucCtrl.drawMoney
                }
            ];
            appFunc.bindEvents(bindings);
        },
        complete: function () {
            GS.loadPage('account/account.html');
        },
        checkAccount: function() {
            GS.loadPage('account/account.html');
        },
        drawMoney: function() {
            GS.loadPage('account/drawmoney.html?path=account');
        }
    };
    return fundTransferOutSucCtrl;
});
