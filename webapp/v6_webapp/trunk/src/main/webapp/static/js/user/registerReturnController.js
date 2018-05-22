/**
 * 注册
 */
define(['js/user/registerReturnView','js/utils/date'], function (registerReturnView,DateHandle) {
    var registerReturnCtrl = {
        init: function () {
            var bindings = [
                {
                    element: '#personal',
                    event: 'click',
                    handler: registerReturnCtrl.personal
                },
                {
                    element: '#goUrl',
                    event: 'click',
                    handler: registerReturnCtrl.goUrl
                },
                {
                    element: '#goRedPage',
                    event: 'click',
                    handler: registerReturnCtrl.goRedPage
                }
            ];
            registerReturnView.init({bindings: bindings});

            var value = appFunc.getCache({key:'REPACKET_ONOFF'});
            if(value == "Y") {
                $("#hongbaoShow").show();
            }
        },

        personal: function () {
            GS.loadPage('personal/personalInfo.html');
        },

        goUrl: function () {
            GS.loadPage("index/home.html");
        },
        goRedPage: function () {
            GS.loadPage("account/hongbao.html?path=account");
        }
    };

    return registerReturnCtrl;
});