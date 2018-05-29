define(['js/utils/date'],function(DateHandle){
	var inActivityTime = false;
    var memberDayWebappCtrl = {
        init: function () {
        	memberDayWebappCtrl.checkActivityTime();
             var binding = [
                 {
                     element: '.memberDayWebapp .goDetail',
                     event: 'click',
                     handler: memberDayWebappCtrl.goDetail
                 }
             ];
            appFunc.bindEvents(binding);
        },
        goDetail:function(){
        	if(inActivityTime == false || $$(".memberDayWebapp .goDetail").hasClass("hyr_pre_btn")){
        		return ;
        	}
        	var closeTerm = $$(this).data("closeTerm");
        	if(closeTerm == undefined || closeTerm == null || closeTerm == ""){
        		xxdApp.alert("系统异常，请稍后重试", '抱歉');
        		return ;
        	}
            req.callJSON({
                url: 'xplan/getLatestSchemeId.do',
                data:{
                	"closeTerm": closeTerm
                },
                indicator: true,
                success: function (result) {
                	try{
	                    if (result.schemeInfo != null && result.schemeInfo != "") {
	                        var schemeInfo = result.schemeInfo;
	                        var planId = schemeInfo.SCHEMEID;
	                        GS.loadPage('plan/planDetailsV2_act.html?previousPage=memberDayWebapp' + '&planId=' + planId);
	                    }else{
	                    	xxdApp.alert("系统异常，请稍后重试", '抱歉');
	                    }
	                 }catch (e) {
                		console.log(e.message);
                		xxdApp.alert("系统异常，请稍后重试", '抱歉');
        			}   
                },
                error: function(xhr, type){
                	console.log("获取_"+ closeTerm +"个月产品信息失败,ajax error...");
                	xxdApp.alert("系统异常，请稍后重试", '抱歉');
                }
            });
        },
        checkActivityTime: function (param) {
            req.callGet({
                url: 'currentDate.do',
                dataType: 'json',
                success: function (result) {
                    if (result.resultCode == 0) {
                        var currentDate = DateHandle.parseDate(result.currentDate);
                        var beginDate = DateHandle.parseDate("2016-05-22 10:00:00");
                        var endDate = DateHandle.parseDate("2016-05-22 23:59:59");
                        if (beginDate <= currentDate && currentDate <= endDate) {
                        	inActivityTime = true;
                        	$$(".memberDayWebapp .goDetail").removeClass("hyr_pre_btn").addClass("hyr_act_btn").html("赚取1.5%返现");
                        }else if(currentDate < beginDate){
                        	inActivityTime = false;
                        	$$(".memberDayWebapp .goDetail").removeClass("hyr_act_btn").addClass("hyr_pre_btn").html("敬请期待");
                        }else if(currentDate > endDate){
                        	inActivityTime = false;
                        	$$(".memberDayWebapp .goDetail").removeClass("hyr_act_btn").addClass("hyr_pre_btn").html("活动已结束");
                        }
                    }
                }
            });
            return inActivityTime;
        }
    };
    return memberDayWebappCtrl;
});