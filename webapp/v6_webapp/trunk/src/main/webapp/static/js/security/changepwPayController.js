/**
 * 修改支付密码
 */
define(['js/security/changepwPayView'], function (changepwPayView) {
    var changepwPayCtrl = {
        init: function() {
            var bindings =[
                {
                    element: '#changepw-pay-submit',
                    event: 'click',
                    handler: changepwPayCtrl.changepwPaySubmit
                }
            ];
            changepwPayView.init({bindings:bindings});
        },
        changepwPaySubmit: function(){
            xxdApp.alert('0000');
        }

    };

    return changepwPayCtrl;
});