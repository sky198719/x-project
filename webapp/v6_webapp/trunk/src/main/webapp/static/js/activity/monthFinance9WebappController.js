define(['js/utils/date'],function(DateHandle){
	var inActivityTime = false;
	var bid = "";
	var name = "";
	var category = "";
    var monthFinance9WebappCtrl = {
        init: function () {
             var binding = [
                 {
                     element: '.monthFinance9Webapp .goDetail',
                     event: 'click',
                     handler: monthFinance9WebappCtrl.goDetail
                 }
             ];
            appFunc.bindEvents(binding);
            monthFinance9WebappCtrl.loadData();
        },
        
        loadData:function(){
            //月进斗金
        	monthFinance9WebappCtrl.getMonthFinance();
        },
        goDetail:function(){
        	try {
	        		//XXD_TRACK._trackEvent({category: "webapp_monthgold_in", action: "index_buy_once", label: "立即抢购", value: "", custval: "" });
	        		//GA部署
	            	product_click({id:bid,name:name,category:category,list:"中秋活动-月进斗金"});
        	} catch (e) {}
    	
        	
              GS.loadPage("monthFinance/monthFinanceDetails.html");
        },
        //月进斗金
        getMonthFinance: function () {
            req.callJSON({
                url: 'monthFinance/getProudctBaseInfo.do',
                data: {},
                dataType: 'json',
                indicator: true,
                success: function (result) {
                	try{
	                	if(result.resultCode == '0' || result.resultCode == 0){
	                		var resultData = result.data;
	                		//年化利率
	                		var apr = resultData.apr;
		            		 bid = resultData.deployId;
		            		 name = "月进斗金-"+resultData.terms;
		            		 category = "月进斗金/"+apr+"%/"+resultData.closeTerm+"天";
	                		
	                	}else{
	                		console.log("获取月进斗金产品信息失败，请稍后重试...");
	                	}
	                }catch (e) {
                		console.log(e.message);
                		console.log("获取月进斗金产品信息失败，请稍后重试...");
        			} 
                },
                error: function(xhr, type){
                	console.log("ajax error...");
                	console.log("获取月进斗金产品信息失败，请稍后重试...");
                }
            });
        },
    };
    return monthFinance9WebappCtrl;
});