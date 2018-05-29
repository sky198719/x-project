define(function () {
   var fundD11AwardCtrl = {
       init: function() {
           $$("title").html("_加息1%");
           fundD11AwardCtrl.loadData();
           fundD11AwardCtrl.getFundInfo();
           fundD11AwardCtrl.getFundLimit();
       },

       loadData:function(){
           var bindings = [
               {
                    element:'div[name="fundJoin_d11"]',
                    event:'click',
                    handler:fundD11AwardCtrl.goFund
               },
               {
                   element:'.navbar-inner > .register',
                   event:'click',
                   handler:fundD11AwardCtrl.indexRegister
               }
           ];
           appFunc.bindEvents(bindings);

           if(appFunc.isLogin()){
               $$('.navbar-inner > .right ').show();
               $$('.navbar-inner > .register ').hide();
           } else {
               $$('.navbar-inner > .register ').show();
           }
       },
       goFund:function(){
    	   if($$('div[name="fundJoin_d11"]').hasClass("fund-rightjoin-button-disable")){
               return;
           }
           try {
                   //XXD_TRACK.track_eventview("fundJoin_d11Click", "button", "立即加入");
               } catch (e) {
            }
             
           if (!appFunc.isLogin()) {
               xxdApp.loginScreen();
               return;
           }
           
           //向server查询是否已满额&是否超过个人投资上限
           fundD11AwardCtrl.selectFundLimitation();
               
       },
       
       selectFundLimitation:function(){
           var fcode = $$('a[name="fundJoin_d11"]').dataset.fund.fcode;
           req.callPost({
               url:'fund/selectFundLimitation.do',
               data:{
                   fcode:fcode
               },
               dataType:'json',
               success:function(data){
                   if(data!=null) {
                       if(data.isTotalFull=="true"){
                           xxdApp.alert("已满额，暂不能转入。", '提示');
                           return;
                       }else if(data.isPersonFull=="true"){
                           xxdApp.alert("您累计的转入金额已达"+appFunc.fmoney(data.fund.userMostTender,2)+"投资上限，暂不能转入", '提示');
                           return;
                       }else {
                           GS.loadPage('fund/fundTransferIn.html?path=fund');
                       }
                   }
               }
           });
       },
       
       getFundInfo:function(){
           req.callGet({
               url:'fund/selectFundInfo.do',
               data:{},
               dataType:'json',
               async:false,
               success:function(data){

                   if(data!=null && data.fund!= null) {
                       $$('a[name="fundJoin_d11"]').dataset.fund = data.fund;
                   }

               }
           });
       },
       
       
       getFundLimit:function(){
           req.callGet({
              url:'fund/fundLimit.do',
              data:{
              },
              dataType:'json',
              success:function(data){
                  if(data.resultCode == 0) {
                      $$('#un_v_isbanlance').val(data.isBalance);
                      $$("#un_v_islastbalance").val(data.isLastBalance);
                      $$("#un_v_purchase_switch").val(data.purchaseSwitch);
                  }

                  fundD11AwardCtrl.enableButton();
              }
          });

       },

       enableButton: function() {
           var isBalance = $$('#un_v_isbanlance').val();
           var isLastBalance  = $$("#un_v_islastbalance").val();
           var purchaseSwitch = $$('#un_v_purchase_switch').val();
           if(isBalance=="1" || isLastBalance == "1") {
               fundD11AwardCtrl.enablePurchase("false");
               $$("#notice_join").html("系统正在结算收益，暂不能加入_");
           }else{
               if(purchaseSwitch=="1") {
                   fundD11AwardCtrl.enablePurchase("false");
                   $$("#notice_join").html("因系统维护，暂不能加入_");
               } else {
                   fundD11AwardCtrl.enablePurchase("true");
               }
           }
       },
       enablePurchase: function(enable) {
           if(enable == "true") {
               $$('div[name="fundJoin_d11"]').removeClass("fund-rightjoin-button-disable");
               $$('div[name="fundJoin_d11"]').addClass("zq-rightjoin-button");
           }else  {
               $$('div[name="fundJoin_d11"]').removeClass("zq-rightjoin-button");
               $$('div[name="fundJoin_d11"]').addClass("fund-rightjoin-button-disable");
           }
       },
       
       indexRegister: function () {
           xxdApp.closeModal('.popover');
           mainView.router.loadPage("fundCpaD11Award.html");
       }
   };
    return fundD11AwardCtrl;
});
