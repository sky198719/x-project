define(['js/utils/date','js/stepUpward/stepUpwardDetailView','js/common/productUtil','js/common/ami','js/utils/dayController','js/utils/xxd_dmp'], function (DateHandle,stepUpwardDetailView,productUtil,ami,dayController,xxd_dmp) {
	var stepUpwardId = "";
	var productInfo = {};
	var activeTab = "";
	var pageSize = 10;
	var stepId = "";
	var stepName = "";
	var category = "";
    // 加载flag
    var loading = false;
    var serverTime;
    var activityStartDate;
    var activityEndDate;
    var stepUpwardDetailCtrl = {
        init: function (event) {
            stepUpwardId = "";
            activeTab = "";
            
            stepUpwardDetailCtrl.bindingEvents();
            stepUpwardDetailCtrl.pullToRefresh();

            //dayController.planDetailMemberDay();
        },
        
        bindingEvents:function(){
            var bindings = [
            	{
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: stepUpwardDetailCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: stepUpwardDetailCtrl.infiniteScroll
                },
                {
                    element: '.stepUpwardDetail #introduction',
                    event: 'click',
                    handler: stepUpwardDetailCtrl.getIntroduction
                },
                {
                    element: '.stepUpwardDetail #questions',
                    event: 'click',
                    handler: stepUpwardDetailCtrl.getQuestions
                },
                {
                    element: '.stepUpwardDetail #joinHistory',
                    event: 'click',
                    handler: stepUpwardDetailCtrl.getJoinHistory
                },
                {
                    element: '.stepUpwardDetail #fenxiantishi',
                    event: 'click',
                    handler: ami.fenxiantishi
                },
                {
	                 element: '#stepUpwardCalc',
	                 event: 'click',
	                 handler: stepUpwardDetailCtrl.stepUpwardCalc
                },
                {
	                 element: '#detail_submitTender',
	                 event: 'click',
	                 handler: stepUpwardDetailCtrl.submitTender
                },
                {
                    element: '#tradeList',
                    event: 'click',
                    handler: stepUpwardDetailCtrl.tradeList
                }
            ];
            appFunc.bindEvents(bindings);
        },
        tradeList:function(){
            GS.loadPage('trade/tradeList.html?pId='+stepId);
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
        	if(productUtil.stepUpwardShowOrNot()){
	            //stepUpwardDetailCtrl.getProductInfo();

                req.callGet({
                    url: 'product/getWebappProduct.do',
                    data: {
                        pCode:"BBGS"
                    },
                    dataType: 'json',
                    async: false,
                    success: function (result) {
                        if (result.code == 200000) {
                            var data = result.data;
                            stepUpwardId = data.items.stepid;
                            stepUpwardDetailCtrl.getNewProductInfo();
                            serverTime = result.serverTime;
                            activityStartDate = result.data.items.activityStartDate;
                            activityEndDate = result.data.items.activityEndDate;
                        }
                    }
                });


//	            stepUpwardDetailCtrl.getUserStepUpwardInfo();
        	}else{
        		$$("#detail_submitTender").addClass("disable");
        		xxdApp.alert('步步高升产品暂已停售', '提示');
        	}
        	
            $$(".stepUpwardDetail #currentPage").val(1);
            
            loading = false;
        },
        
        /**
         * 无限滚动
         */
        infiniteScroll: function () {
        	
            // 如果当前tab不是投资记录
            if (!$$(".stepUpwardDetail #joinHistory").hasClass("active")) {
                return;
            }
            
            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            //==========================切记此处不能删除============================
            loading = true;
            //==========================切记此处不能删除============================
            var currentTab = $('.stepUpwardDetail #currentTab').val();
            var totalPage,currentPage;
        	totalPage = $$(".stepUpwardDetail #totalPage").val();
            currentPage = $$(".stepUpwardDetail #currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage > totalPage) {
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '数据已经全部加载完毕...'
                });
                loading = true;
                return;
            }
            $$(".stepUpwardDetail #currentPage").val(currentPage);

            stepUpwardDetailCtrl.newJoinHistory(currentPage,pageSize,"push")
            
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },

        getNewProductInfo:function(){
            req.callJSON({
                url: 'product/getProduct.do',
                data: {
                	pCode:'BBGS',
					pId:stepUpwardId
				},
                indicator: true,
                success: function (result) {
                    try{
                        if(result.code == 200000){
                            var resultData = result.data;
                            productInfo = resultData;

                            //产品ID
                            //stepUpwardId = resultData.productId;
                            //产品名称 和 期号
                            $('#itemName').html(resultData.name);

                            //年化利率
                            var marginTop = 0;
                            var screenWidth=window.screen.width;

                            var minApr = parseFloat(resultData.plannedAnnualRateFrom);
                            var maxApr = parseFloat(resultData.plannedAnnualRateTo);
                            $$(".stepUpwardDetail #tenderMinApr").html(minApr);
                            $$(".stepUpwardDetail #tenderMaxApr").html(maxApr);
                            stepId = stepUpwardId;
                            stepName="步步高升:步步高升";
                            category = "步步高升/"+resultData.minApr+"%~"+resultData.maxApr+"%/1个月";
                            try{product_detail({id:stepId,name:stepName,category:category});}catch(e){}
                            var aprLength = minApr.toString().length + maxApr.toString().length;
                            if(aprLength > 4){
                                $$(".stepUpwardDetail .detailApr").css("font-size","23px").css("padding-top","20px");
                                if(screenWidth <= 360) {
                                    $$(".stepUpwardDetail .detailApr").css("font-size","20px").css("padding-top","23px");
                                }
                            }else{
                                $$(".stepUpwardDetail .detailApr").css("padding-top", 6 + marginTop + "px");
                                if(screenWidth <= 360) {
                                    $$(".stepUpwardDetail .detailApr").css("font-size","24px").css("padding-top","15px");
                                }
                            }
                            //起投金额
                            var tenderStartAmount = resultData.leastInvestAmount;
                            $$(".stepUpwardDetail #tenderStartAmount").html(appFunc.fmoney(tenderStartAmount, 2));

                            var tenderButton = $$("#detail_submitTender");
                            //是否已满额
                            if (parseFloat(resultData.leftAmount) == 0 || parseFloat(resultData.leftAmount) < parseFloat(tenderStartAmount)) {
                                tenderButton.addClass("disable").html("已抢光");
                            }else{
                                tenderButton.removeClass("disable").html("立即抢购");
                            }
                            stepUpwardDetailCtrl.getTotalJoin();


                            try {
                                var serverTime = serverTime == undefined ? new Date().getTime() : DateHandle.parseDate(serverTime).getTime();

                                if(activityStartDate < serverTime && serverTime < activityEndDate ){
                                    req.callJSON({
                                        url: 'product/activityLabel.do',
                                        data: {
                                            productId:resultData.productId
                                        },
                                        dataType: 'json',
                                        success:function(result1) {
                                            if(result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                                                var remark = result1.data.data.remark;
                                                if(remark.length > 6) {
                                                    $$(".activityLabel_step_detail_text").html("活动奖励：<marquee  scrollamount='1' style='background-color: #ff7365;width:100px;text-align: center;border-radius:2px;color:#fff;vertical-align: bottom;'>"+remark+'</marquee>');
                                                } else {
                                                    $$(".activityLabel_step_detail_text").html("活动奖励：<span style='background-color: #ff7365;padding: 2px 10px;width: 79%;text-align: center;border-radius:2px;color:#fff;vertical-align: bottom;'>"+remark+'</span>');
                                                }

                                                $$(".activityLabel_step_detail").show();
                                            }
                                        }
                                    });
                                }

                            }catch (e) {
                                console.log(e);
                            }


                        }else{
                            xxdApp.addNotification({
                                title: '温馨提示',
                                hold:3000,
                                message: '获取步步高升产品信息失败，请稍后重试...'
                            });
                        }
                        // 加载完毕需要重置
                        xxdApp.pullToRefreshDone();

                        if(activeTab == "joinHistory"){
                            stepUpwardDetailCtrl.getJoinHistory();
                        }else if(activeTab == "questions"){
                            stepUpwardDetailCtrl.getQuestions();
                        }else{
                            stepUpwardDetailCtrl.getIntroduction();
                        }


                    }catch (e) {
                        console.log(e.message);
                        xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取步步高升产品信息失败，请稍后重试...'
                        });
                    }
                }
            });
		},
        
        //产品信息
        getProductInfo:function(){
            req.callJSON({
                url: 'stepUpward/getStepUpwardInfo.do',
                data: {},
                indicator: true,
                success: function (result) {
                	try{
	                    if(result.resultCode == 0){
	                		var resultData = result.data;
	                		productInfo = resultData;
	                		
	                		//产品ID
	                		stepUpwardId = resultData.stepId;
	                		//产品名称 和 期号
		                    $('#itemName').html(resultData.sname);
	                		
	                		//年化利率
		                    var floatapr = resultData.floatapr;
	                    	var marginTop = 0;
                			var screenWidth=window.screen.width;
//		                    if(floatapr > 0){
//		                    	if(screenWidth <= 360) {
//			                        $$(".stepUpwardDetail .suActivity").css("margin-left","60px");
//			                    }
//	                			$$(".stepUpwardDetail .suActivity").show();
//                        		$$(".stepUpwardDetail .suActivity").addClass("animated fadeInLeft");
//                        		marginTop = 5;	                		
//                        	}
				            var minApr = parseFloat(resultData.minApr) + parseFloat(floatapr);
				            var maxApr = parseFloat(resultData.maxApr) + parseFloat(floatapr);
	                		$$(".stepUpwardDetail #tenderMinApr").html(minApr); 	  
	                		$$(".stepUpwardDetail #tenderMaxApr").html(maxApr); 
	                		stepId = stepUpwardId;
	                		stepName="步步高升:步步高升";
	                		category = "步步高升/"+resultData.minApr+"%~"+resultData.maxApr+"%/1个月";
	                		try{product_detail({id:stepId,name:stepName,category:category});}catch(e){}
	                		var aprLength = minApr.toString().length + maxApr.toString().length;
	                		if(aprLength > 4){
	                			$$(".stepUpwardDetail .detailApr").css("font-size","23px").css("padding-top","20px");
			                    if(screenWidth <= 360) {
			                        $$(".stepUpwardDetail .detailApr").css("font-size","20px").css("padding-top","23px");
			                    }
	                		}else{
	                			$$(".stepUpwardDetail .detailApr").css("padding-top", 6 + marginTop + "px");
	                			if(screenWidth <= 360) {
			                        $$(".stepUpwardDetail .detailApr").css("font-size","24px").css("padding-top","15px");
			                    }
	                		}
	                		//起投金额
	                		var tenderStartAmount = resultData.userLowestTender;
	                		$$(".stepUpwardDetail #tenderStartAmount").html(appFunc.fmoney(tenderStartAmount, 2)); 	  
	                		//本人剩余可投
	                		var maxTenderAmount = resultData.maxMount;
	                		
			                var tenderButton = $$("#detail_submitTender");
                			//是否已满额
	                		if (parseFloat(resultData.remAccount) == 0 || parseFloat(resultData.remAccount) < parseFloat(tenderStartAmount)) {
		                		tenderButton.addClass("disable").html("已抢光");
	                		}else{
			                	tenderButton.removeClass("disable").html("立即抢购");
	                		}
	                		stepUpwardDetailCtrl.getTotalJoin();
	                		
	                	}else{
	                		xxdApp.addNotification({
			                    title: '温馨提示',
			                    hold:3000,
			                    message: '获取步步高升产品信息失败，请稍后重试...'
			                });
	                	}
	                	// 加载完毕需要重置
	                    xxdApp.pullToRefreshDone();
	                    
	                    if(activeTab == "joinHistory"){
            				stepUpwardDetailCtrl.getJoinHistory();
	                    }else if(activeTab == "questions"){
	                    	stepUpwardDetailCtrl.getQuestions();
	                    }else{
            				stepUpwardDetailCtrl.getIntroduction();
	                    }
	                   

	                 }catch (e) {
                		console.log(e.message);
                		xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取步步高升产品信息失败，请稍后重试...'
                        });
        			}   
                },
                error: function(xhr, type){
                	console.log("获取步步高升产品加入记录失败,ajax error...");
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '获取步步高升产品信息失败，请稍后重试...'
                    });
                }
            });
        },
        
        //投资
        submitTender:function(){
            if($(this).hasClass("disable")){
            	//xxdApp.alert('该产品暂不可购买', '抱歉');
            	return;
            }
        	if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            var userStepAccount = productUtil.getUserStepUpwardInfo();
            if(parseFloat(userStepAccount.remaCapitalTotal) >= parseFloat(productInfo.userMostTender)){
            	xxdApp.alert('您的个人可购买额度已满额', '提示');
            	return;
            }
            if(stepUpwardId != null && stepUpwardId != ""){
            	try {
            		//XXD_TRACK._trackEvent({category: "webapp_step_in", action: "detail_buy_once", label: "立即抢购", value: "", custval: "" });
            		//加入购物车
            		add_to_cart({id:stepId,name:stepName,category:category});
            	} catch (e) {}
                var xxd_utm_source = xxd_dmp.getDmpUrlParam("xxd_utm_source") || "";
                if (xxd_utm_source != "" && xxd_utm_source != null){
                    GS.loadPage('stepUpward/stepUpwardTender.html' + '?xxd_utm_source=' + xxd_utm_source);
                }else{
                    GS.loadPage('stepUpward/stepUpwardTender.html');
                }
            }else{
            	xxdApp.addNotification({
                    title: '温馨提示',
                    hold:3000,
                    message: '系统异常，请刷新页面重试...'
                });
            }
        },
        
        //计算器
        stepUpwardCalc:function(){
        	GS.loadPage('stepUpward/stepUpwardCalc.html');
        },
        //获取产品说明
        getIntroduction:function(){
        	stepUpwardDetailCtrl.setTabSelectedStyle({"selected":"introduction"});
        	stepUpwardDetailView.showIntroduction(productInfo);
        },
        
        //获取常见问题
        getQuestions:function(){
            stepUpwardDetailCtrl.setTabSelectedStyle({"selected":"questions"});
        	stepUpwardDetailView.showQuestions(productInfo);
        },
        
        //获取投资记录
        getJoinHistory: function () {
        	stepUpwardDetailCtrl.setTabSelectedStyle({"selected":"joinHistory"});
        	$$(".stepUpwardDetail #currentPage").val(1);
        	loading = false;
            stepUpwardDetailCtrl.newJoinHistory(1,pageSize,"pull");
        },
        newJoinHistory:function(currentPage,pageSize,type){
            //产品ID是否为空
            if (stepUpwardId == null || stepUpwardId == "") {
                console.log("步步高升产品ID为空");
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold:3000,
                    message: '获取步步高升产品加入记录失败，请稍后重试...'
                });
                $$(".stepUpwardDetail #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                return;
            }

            req.callJSON({
                url: 'product/productJoinRecords.do',
                data: {
                    pId:stepUpwardId,
                    pCode:'BBGS',
                    currentPage:currentPage,
                    pageSize:pageSize
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    try{
                        if(result.code == 200000){
                        	var data = result.data;

                        	var totalTender = data.countJoinTime;
                        	var totalPage = Math.floor(totalTender / pageSize);
                        	totalPage += (totalTender % pageSize) > 0 ? 1 :0;
                            $$(".stepUpwardDetail #totalPage").val(totalPage);

                            //小屏手机投资总人数展示不下就不展示，360以上的屏展示
                            var screenWidth=window.screen.width;
                            if(screenWidth>=360) {
                                totalTender = totalTender > 9999 ? Math.round((totalTender / 10000) * 100) / 100 + "万": totalTender;
                                $$(".stepUpwardDetail .joinTimes").html("("+totalTender+")");
                            }

                            var resultData = data.items;
                            var data = [];
                            for (var i = 0; i < resultData.length; i++) {
                                var listItem = resultData[i];
                                data.push({
                                    userName: listItem.nickName,
                                    amount: appFunc.fmoney(listItem.joinAmount,2),
                                    addTime:DateHandle.formatDate("yyyy-MM-dd HH:mm:ss",new Date(listItem.joinDate))
                                });

                            }
                            stepUpwardDetailView.showJoinHistory({"dataList":data,"type":type});
                        }else{
                            $$(".stepUpwardDetail #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                        }
                    }catch (e) {
                        console.log(e.message);
                        $$(".stepUpwardDetail #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                        xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取步步高升产品加入记录失败，请稍后重试...'
                        });
                    }
                }
            });
		},

        //获取投资记录
        getTotalJoin:function(){
        	
        	//产品ID是否为空
        	if (stepUpwardId == null || stepUpwardId == "") {
        		console.log("步步高升产品ID为空");
        		xxdApp.addNotification({
                    title: '温馨提示',
                    hold:3000,
                    message: '获取步步高升产品加入记录失败，请稍后重试...'
                });
                return;
        	}
        	//小屏手机投资总人数展示不下就不展示，360以上的屏展示
	        var screenWidth=window.screen.width;
	        if(screenWidth>=360) {
	            req.callJSON({
                    url: 'product/investOrderList.do',
                    data: {"pId":stepUpwardId,"currentPage":currentPage,"pageSize":pageSize},
                    dataType: 'json',
	                indicator: true,
	                success: function (result) {
	                	try{
	                		var totalTender = 0;
		                	if(result.code == 200000){
		                        totalTender = result.data.totalPage;
		                        totalTender = totalTender > 9999 ? Math.round((totalTender / 10000) * 100) / 100 + "万": totalTender;
		                	}
		                    $$(".stepUpwardDetail .joinTimes").html("("+totalTender+")");
		                }catch (e) {
	                		$$(".stepUpwardDetail .joinTimes").html("(0)");
	        			}
	                },
	                error: function (result){
	                	console.log("获取步步高升产品加入记录失败,ajax error...");
	                	$$(".stepUpwardDetail .joinTimes").html("(0)");
	                }
	            });
            }
        },
		//设置tab标签选中状态
        setTabSelectedStyle:function(param){
        	var tabs=["introduction","questions","joinHistory","fenxiantishi"];
        	for(var i=0;i<tabs.length;i++){
        		var item = tabs[i];
        		if(item == param.selected){
        			activeTab = item;
        			$$(".stepUpwardDetail #"+item).addClass("active");
        		}else{
        			$$(".stepUpwardDetail #"+item).removeClass("active");
        		}
        	}
        }
        
    };
    return stepUpwardDetailCtrl;
});
