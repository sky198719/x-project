/**
 * 新手红包
 */
define(function () {
    var newbieRedenvelopeCtrl = {
        init: function () {
            var bindings = [
                {
                    element: '#newbie_redimg_register',
                    event: 'click',
                    handler: newbieRedenvelopeCtrl.goRegister
                }
            ];
            appFunc.bindEvents(bindings);
        },

        goRegister: function () {
            try {
                //XXD_TRACK.track_eventview("newbie_redimg_register", "button", "注册领取红包");
            } catch (e) {
            }
            $$(".login-screen").prop("redirectionUrl","index/home.html");
            GS.loadPage('user/register.html?path=user');
        }
    };

    return newbieRedenvelopeCtrl;
});