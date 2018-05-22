define(function(){
    var sevenDaysActivityCtrl = {
        init: function () {
             var binding = [
                 {
                     element: '#sevenDaysGoTender',
                     event: 'click',
                     handler: sevenDaysActivityCtrl.sevenDaysGoTender
                 }
             ];
            appFunc.bindEvents(binding);
        },
        sevenDaysGoTender:function(){
        	if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            GS.loadPage('newHand/sevenDaysTrade.html?path=newHand');
        }
    };
    return sevenDaysActivityCtrl;
});