define(function(){
    var yypWebappCtrl = {
        init: function () {
             var binding = [
                 {
                     element: '#yypWebapp #yypButton',
                     event: 'click',
                     handler: yypWebappCtrl.yypButton
                 }
             ];
            appFunc.bindEvents(binding);
            $$("title").html("_专题页");
        },
        yypButton: function(){
        	GS.loadPage("popular/financesList.html?path=popular");
        }
    };
    return yypWebappCtrl;
});