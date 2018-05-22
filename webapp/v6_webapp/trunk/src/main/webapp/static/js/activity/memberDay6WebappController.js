define(function(){
    var memberDayWebappCtrl = {
        init: function () {
             var binding = [
                 {
                     element: '.memberDay6Webapp .goDetail',
                     event: 'click',
                     handler: memberDayWebappCtrl.goDetail
                 }
             ];
            appFunc.bindEvents(binding);
        },
        goDetail:function(){

        	var closeTerm = $$(this).data("closeTerm");
            console.log(closeTerm);
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
	                        GS.loadPage('plan/planDetailsV2_act.html?previousPage=memberDay6Webapp' + '&planId=' + planId);
	                    }else{
	                    	xxdApp.alert("系统异常，请稍后重试", '抱歉');
	                    }
	                 }catch (e) {
                		console.log(e.message);
                		xxdApp.alert("系统异常，请稍后重试", '抱歉');
        			}   
                },
                error: function(xhr, type){
                	console.log("获取新元宝"+ closeTerm +"个月产品信息失败,ajax error...");
                	xxdApp.alert("系统异常，请稍后重试", '抱歉');
                }
            });
        }

    };
    return memberDayWebappCtrl;
});