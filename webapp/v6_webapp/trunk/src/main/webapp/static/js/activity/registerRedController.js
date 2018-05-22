define(function(){
    var regCtrl = {
        init: function () {
             var binding = [
                 {
                     element: '#registerRedActivity',
                     event: 'click',
                     handler: regCtrl.toReg
                 }
             ];
            appFunc.bindEvents(binding);
        },
        toReg:function(){
            GS.loadPage("user/register.html?path=user");
        }
    };
    return regCtrl;
});