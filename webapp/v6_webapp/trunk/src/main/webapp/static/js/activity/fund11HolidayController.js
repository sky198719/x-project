define(function () {
    var fund11HolidayCtrl = {
        init: function () {
            var bindings = [
                {
                    element: 'div[name="11HolidayJoin"]',
                    event: 'click',
                    handler: fund11HolidayCtrl.goFund
                }
            ];
            appFunc.bindEvents(bindings);

            fund11HolidayCtrl.getFundInfo();
            fund11HolidayCtrl.getFundLimit();
        },
        goFund:function(){
           if($$('div[name="11HolidayJoin"]').hasClass("fund11Holiday-button-disable")){
               return;
           }
           try {
                   //XXD_TRACK.track_eventview("fundJoinClick", "button", "立即加入");
               } catch (e) {
            }

           //向server查询是否已满额&是否超过个人投资上限
            fund11HolidayCtrl.selectFundLimitation();
       },
        selectFundLimitation:function(){
            var fcode = $$('div[name="11HolidayJoin"]').dataset.fund.fcode;
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
                        $$('div[name="11HolidayJoin"]').dataset.fund = data.fund;
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
                       $$('#11Holiday_isbanlance').val(data.isBalance);
                       $$("#11Holiday_islastbalance").val(data.isLastBalance);
                       $$("#11Holiday_purchase_switch").val(data.purchaseSwitch);
                   }
                   fund11HolidayCtrl.enableButton();
               }
           });
        },

        enableButton: function() {
            var isBalance = $$('#11Holiday_isbanlance').val();
            var isLastBalance  = $$("#11Holiday_islastbalance").val();
            var purchaseSwitch = $$('#11Holiday_purchase_switch').val();
            if(isBalance=="1" || isLastBalance == "1") {
                fund11HolidayCtrl.enablePurchase("false");
                $$("#11Holiday_notice").html("系统正在结算收益，暂不能加入日日盈");
            }else{
                if(purchaseSwitch=="1") {
                    fund11HolidayCtrl.enablePurchase("false");
                    $$("#11Holiday_notice").html("因系统维护，暂不能加入日日盈");
                } else {
                    fund11HolidayCtrl.enablePurchase("true");
                }
            }
        },
        enablePurchase: function(enable) {
            if(enable == "true") {
                $$('div[name="11HolidayJoin"]').removeClass("fund11Holiday-button-disable");
                $$('div[name="11HolidayJoin"]').addClass("fund11Holiday-button");
            }else  {
                $$('div[name="11HolidayJoin"]').removeClass("fund11Holiday-button");
                $$('div[name="11HolidayJoin"]').addClass("fund11Holiday-button-disable");
            }
        }
    };
    return fund11HolidayCtrl;
});
