define(function(){
    var aPlusCtrl = {
        init: function (event) {
        	
        	var type = appFunc.getEventDetailPageQuery(event).type;

            if (type == 'app') {
                mainView.hideNavbar();
                $$(".aPlus").css("padding-top", "0px");
            }
        	
             var binding = [
                 {
                     element: '.aPlus #stepUpward',
                     event: 'click',
                     handler: aPlusCtrl.stepUpward
                 },
                 {
                     element: '.aPlus #plan',
                     event: 'click',
                     handler: aPlusCtrl.plan
                 },
                 {
                     element: '.aPlus #sevenDays',
                     event: 'click',
                     handler: aPlusCtrl.sevenDays
                 },
                 {
                     element: '.aPlus #monthFinance',
                     event: 'click',
                     handler: aPlusCtrl.monthFinance
                 },
                 {
                     element: '.aPlus #fund',
                     event: 'click',
                     handler: aPlusCtrl.fund
                 }
             ];
            appFunc.bindEvents(binding);
            $$("title").html("A+");
            
            aPlusCtrl.getInforMap();
        },
        getInforMap: function(){
        	req.callJSON({
                url: 'getInforMap.do',
                data:{},
                indicator: true,
                success: function (result) {
                	try{
	                    if (result.resultCode == 0 ) {
	                        var inforMap = result.inforMap;
	                        $$(".aPlus #totalTrans").html(inforMap.TOTAL_TRADE[0].nvalue);
	                        $$(".aPlus #totalIncome").html(inforMap.TOTAL_INCOME[0].nvalue);
	                        $$(".aPlus #riskMoney").html(inforMap.VENTURE_BALANCE[0].nvalue);
	                    }
	                 }catch (e) {
                		console.log(e.message);
        			}   
                },
                error: function(xhr, type){
                	console.log("获取披露数据失败,ajax error...");
                }
            });
        },
        stepUpward: function(){
        	GS.loadPage("stepUpward/stepUpwardDetail.html?path=stepUpward");
        },
        plan: function(){
        	req.callJSON({
                url: 'xplan/getLatestSchemeId.do',
                data:{
                	"closeTerm": "12"
                },
                indicator: true,
                success: function (result) {
                	try{
	                    if (result.schemeInfo != null && result.schemeInfo != "") {
	                        var schemeInfo = result.schemeInfo;
	                        var planId = schemeInfo.SCHEMEID;
	                        GS.loadPage('plan/planDetailsV2.html?planId=' + planId);
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
        sevenDays: function(){
        	GS.loadPage("newHand/sevenDaysDetail.html?path=newHand");
        },
        monthFinance: function(){
        	GS.loadPage("monthFinance/monthFinanceDetails.html?path=monthFinance");
        },
        fund: function(){
        	req.callGet({
                 url:'fund/selectIsInvested.do',
                 data:{
                 },
                 dataType:'json',
                 success:function(data){
                     if(data!=null && data.isInvested=='true') {
                         GS.loadPage("fund/fundInvested.html?path=fund");
                     }else {
 					 	 GS.loadPage("fund/fundUnInvested.html?path=fund");
                     }
                 }
             });
        }
    };
    return aPlusCtrl;
});