/**
 * 公告活动详情
 */
define(['js/utils/date'], function (DateHandle) {
    var announceDetailCtrl = {
        init: function (event) {
			var announceId = appFunc.getEventDetailPageQuery(event).announceId;
			announceDetailCtrl.getDetail(announceId);
        },

        getDetail: function (announceId) {
            req.callJSON({
                url: "announce/getDetail.do",
                data: {
                    "announceId": announceId
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
					try{
						if(data.resultCode == 0){
							var detail = data.data;
							$$(".announceDetail .announceTitle").html(detail.title);
							$$(".announceDetail .announceTime").html(detail.updateTime);
							$$(".announceDetail .announceContent").html(detail.context);
						}else{
							xxdApp.addNotification({
	                            title: '抱歉',
	                            hold: 3000,
	                            message: '获取公告活动详情失败，请稍后重试...'
                       		});
						}
	                }catch (e) {
	                  	xxdApp.hideIndicator();
                		console.log(e.message);
                		xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取公告活动详情失败，请稍后重试...'
                        });
        			}
                },
                error: function(xhr, type){
                	console.log("获取公告活动详情失败,ajax error...");
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '获取公告活动详情失败，请稍后重试...'
                    });
                }
            });
        }
    };
    return announceDetailCtrl;
});