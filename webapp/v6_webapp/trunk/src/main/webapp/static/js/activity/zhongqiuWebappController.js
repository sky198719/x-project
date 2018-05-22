define(function(){
    var zhongqiuWebappCtrl = {
        init: function () {
             var binding = [
                 {
                     element: '.zhongqiuWebapp .goDetail',
                     event: 'click',
                     handler: zhongqiuWebappCtrl.goDetail
                 },
                 {
                     element: '.zhongqiuWebapp_Back',
                     event: 'click',
                     handler: zhongqiuWebappCtrl.goBack
                 }
             ];
            appFunc.bindEvents(binding);
        },

        goBack:function(){
             GS.loadPage("index/home.html");
        },
        goDetail:function(){

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
	                        try {
                             	//XXD_TRACK._trackEvent({category:"webapp_zhongqiuWebapp",action:"activity_buy_once",label:"中秋活动",value:"",custval:""});
		                        var planId=schemeInfo.SCHEMEID;
		                        var apr = schemeInfo.MAXAPR;
		                        var pName = "";
		                   	 	var type =schemeInfo.TYPE;
			       	           	 if(type == 1){
			       	           		  pName = "新元宝3个月 - "+ schemeInfo.PNAME;
			       	           	 }else if(type == 2){
			       	           		 pName = "新元宝6个月 - "+ schemeInfo.PNAME;
			       	           	 }else if(type == 3){
			       	           		 pName = "新元宝12个月 - "+ schemeInfo.PNAME;
			       	           	 }else if(type == 4){
			       		    		 pName = "新元宝1个月 - "+ schemeInfo.PNAME;
			       		    	 }
			       	           	 var category = "新元宝/"+apr+"%/"+schemeInfo.CLOSETERM+"个月";
			       	           	 product_click({id:planId,name:pName,category:category,list:"中秋活动列表"});
                    	 }catch(e){}
	                        
	                        
	                        GS.loadPage('plan/planDetailsV2_act.html?previousPage=zhongqiuWebapp' + '&planId=' + planId);
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
    return zhongqiuWebappCtrl;
});