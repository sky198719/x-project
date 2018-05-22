define([], function () {
    var productUtil = {
        getUserStepUpwardInfo:function(){  
        	var userStepAccount = {};
			req.callJSON({
                url: 'stepUpward/getUserStepUpwardInfo.do',
				data: {},               
                dataType: 'json',
                indicator: true,
                async: false,
                success: function (result) {
                	try{
	                	if(result.resultCode == 0){
	                		userStepAccount = result.userStepAccount;
	                	}
	                }catch (e) {
        			}
                },
                error: function (result){
                	console.log("获取用户持有步步高升信息失败,ajax error...");
                }
            });
            return userStepAccount;
		},
        stepUpwardShowOrNot: function(){
        	var showFlag = false;
        	req.callJSON({
                url: 'stepUpward/stepUpwardShowOrNot.do',
                data: {},
                dataType: 'json',
                indicator: true,
                async:false,
                success: function (result) {
                	try{
                		if(result.resultCode != -1){
                			if("Y" == result.resultCode){
                				showFlag = true; 
                			}else{
                				
                			}
                		}else{
	                		console.log("获取步步高升产品展示状态失败，请稍后重试...");
                		}
	                }catch (e) {
                		console.log(e.message);
                		console.log("获取步步高升产品展示状态失败，请稍后重试...");
        			} 
                },
                error: function(xhr, type){
                	console.log("ajax error...");
                	console.log("获取步步高升产品展示状态失败，请稍后重试...");
                }
            });
            return showFlag;
        },
        getUserAccountInfo:function(){
        	var defaultAccount = {};
        	req.callJSON({
                url: 'account/getUserAccountInfo.do',
                data: {}, 
                indicator: true,
                async: false,
                success: function (result) {
                	try{
	                    if(result.resultCode == 0){
	                		defaultAccount = result.defaultAccount;
	                	}else{
	                		xxdApp.addNotification({title: '抱歉', message: '获取用户信息失败，请稍后重试...', hold: 3000 });
	                	}
	                }catch (e) {
                		console.log(e.message);
                		xxdApp.addNotification({title: '抱歉', message: '获取用户信息失败，请稍后重试...', hold: 3000 });
        			}
                },
                error: function(xhr, type){
                	console.log("获取用户信息失败,ajax error...");
                    xxdApp.addNotification({title: '抱歉', message: '获取用户信息失败，请稍后重试...', hold: 3000 });
                }
            });
            return defaultAccount;
        },
        getRedPackets:function(prodType){
        	var redList = [];
        	req.callJSON({
                url: 'account/getRedPackets.do',
                data: {prodType: prodType},
                indicator: true,
                async: false,
                success: function (result) {
                	try{
	                    if(result.resultCode == 0){
	                		redList = result.redPacketList;
	                	}else{
	                		console.log("获取用户红包失败,ajax error...");
	                		//xxdApp.addNotification({title: '温馨提示', message: '获取用户红包失败，请稍后重试...', hold:3000});
	                	}
	                }catch (e) {
                		console.log(e.message);
                		console.log("获取用户红包失败,ajax error...");
                		//xxdApp.addNotification({title: '温馨提示', message: '获取用户红包失败，请稍后重试...', hold:3000});
        			}
                },
                error: function(xhr, type){
                	console.log("获取用户红包失败,ajax error...");
                    //xxdApp.addNotification({title: '温馨提示', message: '获取用户红包失败，请稍后重试...', hold:3000});
                }
            });
            return redList;
        },
        getUserStepUpwardInfo:function(){  
        	var userStepAccount = {};
			req.callJSON({
                url: 'stepUpward/getUserStepUpwardInfo.do',
				data: {},               
                dataType: 'json',
                indicator: true,
                async: false,
                success: function (result) {
                	try{
	                	if(result.resultCode == 0){
	                		userStepAccount = result.userStepAccount;
	                	}
	                }catch (e) {
        			}
                },
                error: function (result){
                	console.log("获取用户持有步步高升信息失败,ajax error...");
                }
            });
            return userStepAccount;
		},
		getUserYypInfo:function(yypId){  
        	var userYypAccount = {};
			req.callJSON({
                url: 'yyp/getUserYypInfo.do',
				data: { yypId: yypId },               
                dataType: 'json',
                indicator: true,
                async: false,
                success: function (result) {
                	try{
	                    if(result.resultCode == 0){
	                		userYypAccount = result.data;
	                	}else{
	                		xxdApp.addNotification({title: '抱歉', message: '获取用户月月派信息失败，请稍后重试...', hold: 3000 });
	                	}
	                }catch (e) {
                		console.log(e.message);
                		xxdApp.addNotification({title: '抱歉', message: '获取用户月月派信息失败，请稍后重试...', hold: 3000 });
        			}
                },
                error: function(xhr, type){
                	console.log("获取用户月月派信息失败,ajax error...");
                    xxdApp.addNotification({title: '抱歉', message: '获取用户月月派信息失败，请稍后重试...', hold: 3000 });
                }
            });
            return userYypAccount;
		}
    };
    return productUtil;
});
