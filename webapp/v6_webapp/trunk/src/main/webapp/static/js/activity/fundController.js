define(function () {
   var fundCtrl = {
       init: function() {
           $$("title").html("日日盈重磅上线");
           fundCtrl.loadData();
           fundCtrl.getFundInfo();
           fundCtrl.getFundLimit();
       },

       loadData:function(){
           var bindings = [
               {
                    element:'div[name="fundJoin"]',
                    event:'click',
                    handler:fundCtrl.goFund
               },
               {
                   element:'.navbar-inner > .register',
                   event:'click',
                   handler:fundCtrl.indexRegister
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
    	   if($$('div[name="fundJoin"]').hasClass("fund-rightjoin-button-disable")){
               return;
           }
           try {
                   //XXD_TRACK.track_eventview("fundJoinClick", "button", "立即加入");
               } catch (e) {
            }
             
           if (!appFunc.isLogin()) {
               xxdApp.loginScreen();
               return;
           }
           
           //向server查询是否已满额&是否超过个人投资上限
           fundCtrl.selectFundLimitation();
       },
       
       selectFundLimitation:function(){
           var fcode = $$('div[name="fundJoin"]').dataset.fund.fcode;
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
                       $$('div[name="fundJoin"]').dataset.fund = data.fund;
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

                  fundCtrl.enableButton();
              }
          });

       },

       enableButton: function() {
           var isBalance = $$('#un_v_isbanlance').val();
           var isLastBalance  = $$("#un_v_islastbalance").val();
           var purchaseSwitch = $$('#un_v_purchase_switch').val();
           if(isBalance=="1" || isLastBalance == "1") {
               fundCtrl.enablePurchase("false");
               $$("#notice_join").html("系统正在结算收益，暂不能加入日日盈");
           }else{
               if(purchaseSwitch=="1") {
                   fundCtrl.enablePurchase("false");
                   $$("#notice_join").html("因系统维护，暂不能加入日日盈");
               } else {
                   fundCtrl.enablePurchase("true");
               }
           }
       },
       enablePurchase: function(enable) {
           if(enable == "true") {
               $$('div[name="fundJoin"]').removeClass("fund-rightjoin-button-disable");
               $$('div[name="fundJoin"]').addClass("zq-rightjoin-button");
           }else  {
               $$('div[name="fundJoin"]').removeClass("zq-rightjoin-button");
               $$('div[name="fundJoin"]').addClass("fund-rightjoin-button-disable");
           }
       },
       
       indexRegister: function () {
           xxdApp.closeModal('.popover');
           GS.loadPage('user/register.html?path=user');
       }
   };
    return fundCtrl;
});
