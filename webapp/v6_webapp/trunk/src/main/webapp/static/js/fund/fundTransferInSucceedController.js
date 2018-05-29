/**
 * Created by chaihuangqi on 2015/8/14.
 */
/**
 * _转入成功页面
 */
define(['js/utils/date','js/utils/dayController'], function (DateHandle,dayController) {
    var tenderAmount = 0;
    var activity_url;
    var fundTransferInSucCtrl = {
        init: function (event) {
        	var query = appFunc.getEventDetailPageQuery(event);
            activity_url = query.activity_url;
        	$$("#tenderAmount").html(query.tenderAmount);
            tenderAmount = query.tenderAmount;

            var bindings = [
                {
                    element:'#complete',
                    event: 'click',
                    handler: fundTransferInSucCtrl.complete
                }
            ];
            appFunc.bindEvents(bindings);
            fundTransferInSucCtrl.getValueDate();

            if(!appFunc.isWeixin()){
                  $$("#weixinTitle").hide();
            }

            fundTransferInSucCtrl.checkActivity(tenderAmount);


            dayController.memberDay({callBack:function(){

                xxdApp.modal({
                    title: '提示',
                    afterText: '投资成功！累计投资100元即可参与会员日活动！',
                    buttons: [
                        {
                            text: '知道了',
                            onClick: function() {
                            }
                        },
                        {
                            text: '立即参加',
                            onClick: function() {
                                window.location.href = activity_url + 'html/member-day/april.html';
                            }
                        }
                    ]
                });
            }});
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
        getValueDate: function() {
            req.callJSON({
                url:"fund/getValueDate.do",
                data:{
                },
                dataType:'json',
                indicator: true,
                success:function(data){
                    $('#trade_date').html(DateHandle.formatDate('yyyy-MM-dd HH:mm:ss', data.currentDate));
                    $('#value_date').html(DateHandle.formatDate('yyyy-MM-dd', data.valueDate));
                    $('#arrival_date').html(DateHandle.formatDate('yyyy-MM-dd', data.arrivalDate));
                }
            });
        },

        complete: function () {
            GS.loadPage('account/account.html');
        }
    };
    return fundTransferInSucCtrl;
});
