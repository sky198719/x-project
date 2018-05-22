define(['chart', 'chartDoughnut','js/utils/date','js/plan/planDetailsV2View','js/plan/planUtils','js/utils/dayController','js/common/ami'], function (Chart, Doughnut, DateHandle,planDetailsV2View,PlanUtils,dayControl,ami) {
	var financeInterval;
	var planId = "";
	var pname = "";
	var category = "";
	var previousPage = "";
	var introductionParams = {};
	var pageSize = 10;
	// 加载flag
    var loading = false;
	var tabs=["introduction","questions","joinHistory"];
	var selectedTab = "";
    var isdow;
    var planDetailsV2Ctrl = {
        init: function (event) {
            clearInterval(financeInterval);
            var query = appFunc.getEventDetailPageQuery(event);
            planId = query.planId;
            planId = (planId == undefined || planId == null) ? "" : planId ;

            isdow = query.isdow;
            isdow = (isdow == undefined || isdow == null) ? "" : isdow;

            previousPage = query.previousPage;
            selectedTab = "";
            introductionParams = {};
            
            planDetailsV2Ctrl.bindingEvents();
            //查询产品信息
            planDetailsV2Ctrl.pullToRefresh();
            //dayControl.planDetailMemberDay();
        },
        
        bindingEvents:function(){
            var bindings = [
            	{
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: planDetailsV2Ctrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: planDetailsV2Ctrl.infiniteScroll
                },
                {
                    element: '.planDetailsV2 #introduction',
                    event: 'click',
                    handler: planDetailsV2Ctrl.getIntroduction
                },
                {
                    element: '.planDetailsV2 #questions',
                    event: 'click',
                    handler: planDetailsV2Ctrl.getQuestions
                },
                {
                    element: '.planDetailsV2 #joinHistory',
                    event: 'click',
                    handler: planDetailsV2Ctrl.getJoinHistory
                },
                {
	                 element: '#goTender',
	                 event: 'click',
	                 handler: planDetailsV2Ctrl.goTender
                },
                {
	                 element: '.goBackPrevious',
	                 event: 'click',
	                 handler: planDetailsV2Ctrl.goBackPrevious
                },
                {
                    element: '#goTender',
                    event: 'click',
                    handler: planDetailsV2Ctrl.goTender
                }
            ];
            appFunc.bindEvents(bindings);
        },
        
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
        	introductionParams = {};
        	clearInterval(financeInterval);
            planDetailsV2Ctrl.getProductInfo();
            $$(".planDetailsV2 #currentPage").val(1);
            
            loading = false;
        },
        
        /**
         * 无限滚动
         */
        infiniteScroll: function () {
        	
            // 如果当前tab不是投资记录
            if (!$$(".planDetailsV2 #joinHistory").hasClass("active")) {
                return;
            }
            
            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            //==========================切记此处不能删除============================
            loading = true;
            //==========================切记此处不能删除============================
            var currentTab = $$('.planDetailsV2 #currentTab').val();
            var totalPage,currentPage;
        	totalPage = $$(".planDetailsV2 #totalPage").val();
            currentPage = $$(".planDetailsV2 #currentPage").val();
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
            $$(".planDetailsV2 #currentPage").val(currentPage);
            
            planDetailsV2Ctrl.joinHistory(currentPage,pageSize,"push")
            
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        
        //产品信息
        getProductInfo:function(){
        	if(planId == ""){
        		xxdApp.addNotification({
                    title: '抱歉',
                    hold: 3000,
                    message: '获取数据失败，请刷新页面重试...'
                });
                return;
        	}
            req.callJSON({
                url: 'xplan/detail/' + planId + '.do',
                data: {},
                indicator: true,
                success: function (result) {
                	try{
	                    if(result != null){
	                		var resultData = result.scheme;
	                		//产品名称 和 期号
		                    $$('.planDetailsV2 #planItemName').html(PlanUtils.schemeType(resultData.type));
		                    $$('.planDetailsV2 #planItemTerm').html(resultData.pname);
	                		//年化利率
		                    introductionParams.apr = resultData.minApr;
	                		$$(".planDetailsV2 #tenderApr").html(resultData.minApr);

                            var increaseApr =  resultData.webapp;
                            if(increaseApr != undefined && increaseApr != null && increaseApr > 0) {
                               $$(".planDetailsV2 .increaseApr").html("+"+increaseApr+"%");
                               $$(".planDetailsV2 .increaseApr").css("display","inline");
                            }

	                		//锁定期限
	                		introductionParams.closeTerm = resultData.closeterm;
	                		$$(".planDetailsV2 #closeTerm").html(resultData.closeterm); 
	                		//提前退出
	                		introductionParams.forfeitpercent = resultData.forfeitpercent;
	                		//结束日期
	                		introductionParams.endTime = DateHandle.formatDate('yyyy年MM月dd日',new Date(result.endTime)) 
	                		//剩余可投
	                		$$(".planDetailsV2 #remainderAmount").html(appFunc.fmoney(resultData.remacount, 2)); 
	                		

	                		//产品详情
	                		var xplanType = resultData.type;
	                		if(xplanType == 1){
	                			pname = "新元宝3个月";
	                		}else if(xplanType == 2){
	                			pname = "新元宝6个月";
	                		}else if(xplanType == 3){
	                			pname = "新元宝12个月";
	                		}else if(xplanType == 4){
	                			pname = "新元宝1个月 ";
	       		    	 	}
	                		 pname = pname+"-"+resultData.pname;
	                		 category = "新元宝/"+resultData.minApr+"%/"+resultData.closeterm+"个月";
	                		try{product_detail({id:planId,name:pname,category:category});}catch(e){}
	                		

                            //0待发布,1已发布,2开放购买中,3.已锁定，4已结束，5已撤销
	                		var status = resultData.status;
	                		
			                var tenderButton = $$("#goTender");
			                //是否已满额
							if(resultData.remacount <= 0) {
				                //显示已抢完
					    		tenderButton.addClass("disable").html("已抢光");
					    		$$(".planDetailsV2 #remainderAmount").html(0);
				            }else{
                                if (status == 0 || status == 1 || status == 7) {
                                    tenderButton.addClass("disable").html("等待发售");
                                } else if (status == 2) {
                                    tenderButton.removeClass("disable").html("立即抢购");
                                } else if(status == 3){
                                    tenderButton.addClass("disable").html("收益中");
                                } else {
                                    tenderButton.addClass("disable").html("已抢光");
					                $$(".planDetailsV2 #remainderAmount").html(0);
					            }
				            }

                            try {

                                req.callJSON({
                                    url: 'product/activityLabel.do',
                                    data: {
                                        productId:resultData.schemeId
                                    },
                                    dataType: 'json',
                                    success:function(result1) {
                                        if(result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                                            var remark = result1.data.data.remark;
                                            if(remark.length > 7) {
                                                $$(".activityLabel_plan_detail_text").html("活动奖励：<marquee  scrollamount='1' style='background-color: #ff7365;width:100px;text-align: center;border-radius:2px;color:#fff;vertical-align: bottom;'>"+remark+'</marquee>');
                                            } else {
                                                $$(".activityLabel_plan_detail_text").html("活动奖励：<span style='background-color: #ff7365;padding: 2px 10px;width: 79%;text-align: center;border-radius:2px;color:#fff;vertical-align: bottom;'>"+remark+'</span>');
                                            }

                                            $$(".activityLabel_plan_detail").show();
                                        }
                                    }
                                });

                            }catch (e) {
                                console.log(e);
                            }
	                	}else{
	                		xxdApp.addNotification({
			                    title: '温馨提示',
			                    hold:3000,
			                    message: '获取新元宝产品信息失败，请稍后重试...'
			                });
	                	}
	                    if(selectedTab == null || selectedTab == ""){
		                    //获取产品介绍
	            			planDetailsV2Ctrl.getIntroduction();
	                    }else{
	                    	if(selectedTab == "joinHistory"){
	                    		planDetailsV2Ctrl.getJoinHistory();
	                    	}
	                    }
	                 }catch (e) {
                		console.log(e.message);
                		xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取新元宝产品信息失败，请稍后重试...'
                        });
        			}finally{
	                    // 加载完毕需要重置
	                    xxdApp.pullToRefreshDone();
					}   
                },
                error: function(xhr, type){
                	console.log("获取新元宝产品加入记录失败,ajax error...");
	                // 加载完毕需要重置
	                xxdApp.pullToRefreshDone();
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '获取新元宝产品信息失败，请稍后重试...'
                    });
                }
            });
        },
        
        //投资
        goTender:function(){
            if($$(this).hasClass("disable")){
            	return;
            }
        	if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            
            if(planId != ""){
            	try {
            		//XXD_TRACK._trackEvent({category:"webapp_xplan_in",action:"now_join",label:"立即抢购",value:"",custval:""});
            		//加入购物车
            		add_to_cart({id:planId,name:pname,category:category});
            	} catch (e) {}
            	
	            GS.loadPage('planTender/planTender.html?planId=' + planId);
            }else{
            	xxdApp.addNotification({
                    title: '抱歉',
                    hold:3000,
                    message: '系统异常，请刷新页面重试...'
                });
            }
        },
        
        //返回
        goBackPrevious:function(){
//        	mainView.router.back({url:"activity/memberDayWebapp.html"});
            var html = "activity/"+previousPage+".html";
            console.log(isdow);
            if(isdow != "") {
                html += "?isdow="+isdow;
            }
        	GS.loadPage(html);
        },
        
        //获取产品说明
        getIntroduction:function(){
        	planDetailsV2Ctrl.setTabSelectedStyle({"selected":"introduction"});
        	planDetailsV2View.showIntroduction(introductionParams);
        },
        
        //获取常见问题
        getQuestions:function(){
            planDetailsV2Ctrl.setTabSelectedStyle({"selected":"questions"});
        	planDetailsV2View.showQuestions();
        },
        
        //获取投资记录
        getJoinHistory: function () {
           planDetailsV2Ctrl.setTabSelectedStyle({"selected":"joinHistory"});
           planDetailsV2Ctrl.joinHistory(1,pageSize,"pull");
        },
        
        //获取投资记录
        joinHistory:function(currentPage,pageSize,type){
        	//产品ID是否为空
        	if (planId == null || planId == "") {
        		console.log("新元宝产品ID为空");
        		xxdApp.addNotification({
                    title: '温馨提示',
                    hold:3000,
                    message: '获取新元宝产品加入记录失败，请稍后重试...'
                });
                return;
        	}
        	
        	//是否已登录
//        	if (!appFunc.isLogin()) {
//                xxdApp.loginScreen();
//                return;
//            }
            
            req.callJSON({
                url: 'xplan/userSchemeList.do',
				data: {"schemeId":planId,"currentPage":currentPage,"pageSize":pageSize,"queryType": "join"},               
                dataType: 'json',
                indicator: true,
                async:false,
                success: function (result) {
                	try{
	                	if(result.resultList != null){
	                		var resultData = result.resultList;
	                		
//	                		var totalPage = Math.ceil(parseInt(resultData.length)/parseInt(pageSize));
	                		if(resultData.length == 0){
	                			totalPage = currentPage - 1;
	                		}else{
	                			totalPage = currentPage + 1;
	                		}
		                    $$(".planDetailsV2 #totalPage").val(totalPage);
		                    
		                    if (currentPage > totalPage && totalPage != 0) {
		                    	console.log("in method plan joinHistory: no more data to display...");
//		                        xxdApp.addNotification({
//		                            title: '提示',
//		                            hold: 3000,
//		                            message: '数据已全部加载完成'
//		                        });
		                        return;
		                    }
	                		
	                		var data = [];
				    		for (var i = 0; i < resultData.length; i++) {
				    			var listItem = resultData[i];
				    			data.push({
	                                userName: listItem.NICKNAME,
	                                amount: appFunc.fmoney(listItem.ACCOUNT,2),
	                                addTime:listItem.ADDTIME
	                            });
	                            
				    		}
	                		planDetailsV2View.showJoinHistory({"dataList":data,"type":type});
	                	}else{
	                		console.log("获取新元宝产品加入记录失败");
	                		$$(".planDetailsV2 #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
	                		xxdApp.addNotification({
			                    title: '温馨提示',
			                    hold:3000,
			                    message: '获取新元宝产品加入记录失败，请稍后重试...'
			                });
	                	}
	                }catch (e) {
                		console.log(e.message);
                		xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取新元宝产品加入记录失败，请稍后重试...'
                        });
        			}
                },
                error: function (result){
                	console.log("获取新元宝产品加入记录失败,ajax error...");
                	$$(".planDetailsV2 #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                	xxdApp.addNotification({
	                    title: '温馨提示',
	                    hold:3000,
	                    message: '获取新元宝产品加入记录失败，请稍后重试...'
	                });
                }
            });
        },
        
        //格式化时间 hh:mm:ss
        formathhmmss:function(data){
            var hours = data.getHours();
            var minutes = data.getMinutes();
            var seconds = data.getSeconds();
            return (hours < 10 ?  "0" + hours : hours) + ':' + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
        },
        
		//设置tab标签选中状态
        setTabSelectedStyle:function(param){
        	for(var i=0;i<tabs.length;i++){
        		var item = tabs[i];
        		if(item == param.selected){
        			$$(".planDetailsV2 #"+item).addClass("active");
        			selectedTab = item;
        		}else{
        			$$(".planDetailsV2 #"+item).removeClass("active");
        		}
        	}
        }
        
    };
    return planDetailsV2Ctrl;
});
