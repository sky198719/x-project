define(['js/utils/date','js/plan/planUtils','js/utils/dayController'], function (DateHandle,PlanUtils,dayController) {
    var tenderAmount = 0;
    var activity_url;
    var planTenderSuccessCtrl = {
        init: function (event) {
        	var query = appFunc.getEventDetailPageQuery(event);
        	var tenderPlanType = query.tenderPlanType;
            activity_url = query.activity_url;
        	$$(".planTenderSuccess .tenderItem").html(PlanUtils.schemeType(tenderPlanType));
        	$$(".planTenderSuccess .tenderAmount").html(query.tenderAmount);
            tenderAmount =  query.tenderAmount;
        	$$(".planTenderSuccess .tenderTime").html(query.tenderTime);
        	$$(".planTenderSuccess .startDate").html(query.startDate);
        	$$(".planTenderSuccess .profitAmount").html(query.interest);
        	$$(".planTenderSuccess .arrivalDate").html(query.arrivalDate);
        	
            var bindings = [
                {
                    element:'.planTenderSuccess .moreProduct',
                    event: 'click',
                    handler: planTenderSuccessCtrl.moreProduct
                },
                {
                    element:'.planTenderSuccess .goMyAccount',
                    event: 'click',
                    handler: planTenderSuccessCtrl.goMyAccount
                }
            ];
            appFunc.bindEvents(bindings);

            planTenderSuccessCtrl.checkActivity(tenderAmount);

            dayController.memberDay({callBack:function(){

                xxdApp.modal({
                     title: '提示',
                     afterText: '投资成功！ _投资满800元可抽奖赢空气净化器！',
                     buttons: [
                         {
                          text: '知道了',
                          onClick: function() {
                          }
                        },
                       {
                         text: '立即参加',
                         onClick: function() {
                              window.location.href = activity_url + 'html/vipDay/january.html?xxd_utm_source=F7BnYn';
                         }
                       }
                     ]
                 });
            }});
        },

        checkActivity:function(tenderAmount) {
            req.callJSON({
                url: 'activity/checkActivity.do',
                data: {
                    code: 'DoubleDan-activity'
                },
                success: function (result) {
                    if (result.resultCode == 0) {
                        xxdApp.modal({
                            title: '提示',
                            afterText: '投资成功！_满100元可抽奖赢iPhoneX！',
                            buttons: [
                                {
                                    text: '知道了',
                                    onClick: function () {
                                    }
                                },
                                {
                                    text: '立即参加',
                                    onClick: function () {
                                        window.location.href = activity_url + 'html/doubleFestival/index.html';
                                    }
                                }
                            ]
                        });
                    }
                }
            });
        },

        moreProduct: function () {
            GS.loadPage("popular/financesList.html?path=popular");
        },
        goMyAccount: function () {
            GS.loadPage('account/account.html');
        },

        checkActivityStatus:function(){
        	var flag = false;
        	req.callJSON({
                url: "eurocup/activityStatus.do",
                dataType: 'json',
                indicator: true,
                async: false,
                success: function (data) {
                    if(data != true){
                    	flag = false;
                    }else{
                    	flag = true;
                    }
                }
            });
            return flag;
        }
    };
    return planTenderSuccessCtrl;
});
