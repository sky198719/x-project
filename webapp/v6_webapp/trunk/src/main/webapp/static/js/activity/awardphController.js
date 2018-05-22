/**
 *
 */
define(['js/activity/awardphView'], function (awardphView) {
   var awardphCtrl = {
       init: function() {
           /**
            * 事件定义
            *  showResult 查看结果
            * @type {*[]}
            */
           var bindings = [
               {
                   element:'a[name="awardph"]',
                   event:'click',
                   handler:awardphCtrl.awardph
               }
           ];
           awardphView.init({bindings: bindings});
       },
       awardph:function(){
           if (!appFunc.isLogin()) {
               GS.loadPage("user/register.html?path=user");
               return;
           } else {
               xxdApp.closeModal('.popover');
               GS.loadPage("borrow/borrowListV2.html");
           }
       }
   };
    return awardphCtrl;
});
