define(function () {
    var fundNewYearCtrl = {
        init: function () {
            var bindings = [
                {
                    element: 'div[name="newYearJoin"]',
                    event: 'click',
                    handler: fundNewYearCtrl.goFund
                }
            ];
            appFunc.bindEvents(bindings);

            fundNewYearCtrl.getFundInfo();
            fundNewYearCtrl.getFundLimit();
        },
        goFund:function(){
           if($$('div[name="newYearJoin"]').hasClass("fund11Holiday-button-disable")){
               return;
           }
           try {
                   //XXD_TRACK.track_eventview("fundJoinClick", "button", "立即加入");
               } catch (e) {
            }

           //向server查询是否已满额&是否超过个人投资上限
            fundNewYearCtrl.selectFundLimitation();
       },
        selectFundLimitation:function(){
            var fcode = $$('div[name="newYearJoin"]').dataset.fund.fcode;
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
                        $$('div[name="newYearJoin"]').dataset.fund = data.fund;
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
                       $$('#newYear_isbanlance').val(data.isBalance);
                       $$("#newYear_islastbalance").val(data.isLastBalance);
                       $$("#newYear_purchase_switch").val(data.purchaseSwitch);
                   }
                   fundNewYearCtrl.enableButton();
               }
           });
        },

        enableButton: function() {
            var isBalance = $$('#newYear_isbanlance').val();
            var isLastBalance  = $$("#newYear_islastbalance").val();
            var purchaseSwitch = $$('#newYear_purchase_switch').val();
            if(isBalance=="1" || isLastBalance == "1") {
                fundNewYearCtrl.enablePurchase("false");
                $$("#newYear_notice").html("系统正在结算收益，暂不能加入_");
            }else{
                if(purchaseSwitch=="1") {
                    fundNewYearCtrl.enablePurchase("false");
                    $$("#newYear_notice").html("因系统维护，暂不能加入_");
                } else {
                    fundNewYearCtrl.enablePurchase("true");
                }
            }
        },
        enablePurchase: function(enable) {
            if(enable == "true") {
                $$('div[name="newYearJoin"]').removeClass("fund11Holiday-button-disable");
                $$('div[name="newYearJoin"]').addClass("fund11Holiday-button");
            }else  {
                $$('div[name="newYearJoin"]').removeClass("fund11Holiday-button");
                $$('div[name="newYearJoin"]').addClass("fund11Holiday-button-disable");
            }
        }
    };
    return fundNewYearCtrl;
});
