define(['js/utils/date'],function(DateHandle){
	var userTenderFlag = false;
	var hintMsg = "加载出错，请稍后重试...";

    var monthFinanceActivityCtrl = {
        init: function () {
        	 userTenderFlag = false;
             var binding = [
                 {
                     element: '#monthFinanceGoTender',
                     event: 'click',
                     handler: monthFinanceActivityCtrl.monthFinanceGoTender
                 }
             ];
            appFunc.bindEvents(binding);
        },
        monthFinanceGoTender:function(){
        	if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            monthFinanceActivityCtrl.getProductInfo();
            if(userTenderFlag == false){
            	xxdApp.alert(hintMsg, '抱歉');
                return false;
            }
            GS.loadPage('monthFinance/monthFinanceTender.html');
        },
        //产品信息
        getProductInfo:function(){
            req.callJSON({
                url: 'monthFinance/getProudctDetail.do',
                data: {},
                indicator: true,
                async: false,
                success: function (result) {
                	try{
	                    if(result.resultCode == '0' || result.resultCode == 0){
	                		var resultData = result.data;
	                		var currentClientTime = new Date().getTime();
	                		var serverTime = resultData.currTime.replace(/\-/g, "/");
	                		var currentServerTime = new Date(serverTime).getTime();
	                		var clientServerTimeDiff = currentServerTime - currentClientTime;
	                		
	                		
	                		//起投金额
	                		var tenderStartAmount = resultData.lowestTender;
	                		//剩余可投
	                		$$(".monthFinanceDetails #tenderRemainderAmount").html(appFunc.fmoney(resultData.untendSum, 2)); 
	                		//开放总额
	                		$$(".monthFinanceDetails #tenderTotalAmount").html(appFunc.fmoney(resultData.amount, 2)); 
	                		//本人剩余可投
	                		var maxTenderAmount = resultData.maxMount;
	                		
	                		var startTime = resultData.startDate.replace(/\-/g, "/");
	                		var endTime = resultData.endDate.replace(/\-/g, "/");
	                		var startTimeHHmm = DateHandle.formatDate('HH:mm',startTime);
	                		var endTimeHHmm = DateHandle.formatDate('HH:mm',endTime);
			                
                			//已到开始时间 未到结束时间
	                		if(	currentServerTime >= new Date(startTime).getTime() 
	                			&& currentServerTime <= new Date(endTime).getTime()){
								//是否已满额
		                		if (parseFloat(resultData.untendSum) == 0 || parseFloat(resultData.untendSum) < parseFloat(tenderStartAmount)) {
			                		userTenderFlag = false;
									hintMsg = "今日已抢光，明日再来~";
		                		}else{
			                		//个人额度是否已满
			                		if (maxTenderAmount <= 0) {
			                			userTenderFlag = false;
										hintMsg = "您的抢购限额已满";
			                		} else {
				                		userTenderFlag = true;
			                		}
		                		}
	                		}else{
	                			//已过结束时间
	                			if(currentServerTime > new Date(endTime).getTime()){
	                				//是否已满额
			                		if (parseFloat(resultData.untendSum) == 0 || parseFloat(resultData.untendSum) < parseFloat(tenderStartAmount)) {
				                		userTenderFlag = false;
										hintMsg = "本场已结束，等待下一场 ";
			                		}else{
		                				userTenderFlag = false;
										hintMsg = "今日投资已结束，明日再来~";
			                		}
	                			}
	                			//未到开始时间
	                			else{
			                		userTenderFlag = false;
									hintMsg = "今日投资尚未开始，不要着急哦~";
	                			}
	                		}
	                		
	                	}else{
			                userTenderFlag = false;
			                hintMsg = "加载出错，请稍后重试..."
	                	}
	                 }catch (e) {
                		console.log(e.message);
                		userTenderFlag = false;
			            hintMsg = "加载出错，请稍后重试..."
        			}   
                },
                error: function(xhr, type){
                	console.log("获取月进斗金产品加入记录失败,ajax error...");
                    userTenderFlag = false;
			        hintMsg = "加载出错，请稍后重试..."
                }
            });
        }
    };
    return monthFinanceActivityCtrl;
});