define(['js/utils/date','js/plan/planUtils'], function (DateHandle,PlanUtils) {
    var tenderSuccessCtrl = {
        init: function (event) {
        	var query = appFunc.getEventDetailPageQuery(event);
        	$$(".tenderSuccess .tenderProduct").html(query.tenderProduct);
        	$$(".tenderSuccess .tenderAmount").html(query.tenderAmount);
        	$$(".tenderSuccess .tenderTime").html(query.tenderTime);
        	$$(".tenderSuccess .startDate").html(query.startDate);
        	$$(".tenderSuccess .profitAmount").html(query.profitAmount);
        	$$(".tenderSuccess .arrivalDate").html(query.arrivalDate);
        	if(query.startDayIsToday == "Y"){
        		$$(".tenderSuccess .startDateCal").attr("src","static/img/xxd/calculate-show.png");
        	}
        	
            var bindings = [
                {
                    element:'.tenderSuccess .moreProduct',
                    event: 'click',
                    handler: tenderSuccessCtrl.moreProduct
                },
                {
                    element:'.tenderSuccess .goMyAccount',
                    event: 'click',
                    handler: tenderSuccessCtrl.goMyAccount
                }
            ];
            appFunc.bindEvents(bindings);

            //tenderSuccessCtrl.checkActivity(query.tenderAmount);

        },

        checkActivity:function(tenderAmount){
            req.callJSON({
                 url:'activity/checkActivity.do',
                 data:{
                     code:'april-fools-day-activity'
                 },
                 success: function (result) {
                     if(result.resultCode == 0) {
                         xxdApp.modal({
                                 title: '提示',
                                 afterText: '投资成功！累计投资100元即可参与愚人节活动！',
                                 buttons: [
                                   {
                                       text: '知道了',
                                       onClick: function() {
                                       }
                                   },
                                   {
                                        text: '立即参加',
                                        onClick: function() {
                                             window.location.href = 'static/html/activity/april-fools-day-activity/index.html';
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
        }
    };
    return tenderSuccessCtrl;
});
