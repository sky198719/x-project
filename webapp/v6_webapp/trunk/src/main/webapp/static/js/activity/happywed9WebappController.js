/**
 * 六月欢乐周三
 */
define(['js/utils/date'], function () {
    var happyWed9Ctrl = {
        init: function () {
            var bindings = [
                {
                    element: '.happywed9 .toDeail',
                    event: 'click',
                    handler: happyWed9Ctrl.getLatestPlanByCloseTerm
                },
                {
                    element: '.happywed9Webappback',
                    event: 'click',
                    handler: happyWed9Ctrl.happywed9Webappback

                }
            ];
            appFunc.bindEvents(bindings);

        },

        happywed9Webappback:function(){
             GS.loadPage("index/home.html");
        },
        getLatestPlanByCloseTerm: function () {
            var closeTerm = $$(this).data("closeTerm");
            if(closeTerm == undefined || closeTerm == null || closeTerm == ""){
                xxdApp.alert("系统异常，请稍后重试", '抱歉');
                return ;
            }

            req.callJSON({
                url: "xplan/getLatestSchemeId.do",
                data: {
                    currentPage: 1,
                    closeTerm: closeTerm,
                    pageSize: 10
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if(data!=null && data.schemeInfo!=null) {
                    	 try {
                             	//XXD_TRACK._trackEvent({category:"webapp_happywed9Webapp",action:"activity_buy_once",label:"星期三活动立即抢购",value:"",custval:""});
		                        var planId=data.schemeInfo.SCHEMEID;
		                        var apr = data.schemeInfo.MAXAPR;
		                        var pName = "";
		                   	 	var type =data.schemeInfo.TYPE;
			       	           	 if(type == 1){
			       	           		  pName = "_3个月 - "+ data.schemeInfo.PNAME;
			       	           	 }else if(type == 2){
			       	           		 pName = "_6个月 - "+ data.schemeInfo.PNAME;
			       	           	 }else if(type == 3){
			       	           		 pName = "_12个月 - "+ data.schemeInfo.PNAME;
			       	           	 }else if(type == 4){
			       		    		 pName = "_1个月 - "+ data.schemeInfo.PNAME;
			       		    	 }
			       	           	 var category = "_/"+apr+"%/"+data.schemeInfo.CLOSETERM+"个月";
			       	           	 product_click({id:planId,name:pName,category:category,list:"星期三活动列表"});
                    	 }catch(e){}
                        GS.loadPage('plan/planDetailsV2_act.html?previousPage=happywed9Webapp&planId=' + planId);
                    }
                }
            });
        }
    };
    return happyWed9Ctrl;
});