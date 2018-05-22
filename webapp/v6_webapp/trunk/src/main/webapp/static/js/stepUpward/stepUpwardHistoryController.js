define(['js/stepUpward/stepUpwardHistoryView','js/utils/date'], function (stepUpwardHistoryView, DateHandle) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var quitStepUpwardId = "";
    var quitButton;
    var stepUpwardHistoryCtrl = {
        init: function () {

            /**
             * 事件定义
             *  pullToRefresh 下拉刷新
             *  infiniteScroll 无限刷新
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: stepUpwardHistoryCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: stepUpwardHistoryCtrl.infiniteScroll
                },
                {
                    element: '#stepUpwardHistory #progressingTab',
                    event: 'show',
                    handler: stepUpwardHistoryCtrl.showProgressingTab
                },
                {
                    element: '#stepUpwardHistory #completedTab',
                    event: 'show',
                    handler: stepUpwardHistoryCtrl.showCompletedTab
                }
            ];
            stepUpwardHistoryView.bindEvents({
                        bindings: bindings
                    }
            );
            stepUpwardHistoryCtrl.pullToRefresh();
        },
        listItemBindEvent: function () {
            var bindings = [
                {
                    element: '#stepUpwardHistory .quitStepUpward',
                    event: 'click',
                    handler: stepUpwardHistoryCtrl.quitConfirm
                },
                {
                    element: '#stepUpwardHistory .getQuitRecords',
                    event: 'click',
                    handler: stepUpwardHistoryCtrl.getQuitRecords
                },
                {
                    element:'#stepUpwardHistory .setpUpwardUserTradeList',
                    event:'click',
                    handler:stepUpwardHistoryCtrl.setpUpwardUserTradeList
                }
            ];
            stepUpwardHistoryView.bindEvents({
                        bindings: bindings
                    }
            );
        },

        setpUpwardUserTradeList:function(){
            var joinId = $$(this).data("joinid");
            GS.loadPage("trade/tradeList.html?joinId="+joinId+"&isTender=true");
        },
        /**
         * 收益中
         */
        showProgressingTab: function () {
            $$('#stepUpwardHistory #currentTab').val('progressingTab');
            stepUpwardHistoryCtrl.pullToRefresh();
        },
        /**
         * 已完成
         */
        showCompletedTab: function () {
            $('#stepUpwardHistory #currentTab').val('completedTab');
            stepUpwardHistoryCtrl.pullToRefresh();
        },
        
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            var currentTab = $('#stepUpwardHistory #currentTab').val();
            if (currentTab == 'progressingTab') {
                stepUpwardHistoryCtrl.selectItems(1, "pull","1");
            } else {
                stepUpwardHistoryCtrl.selectItems(1, "pull","2");
            } 
            $$("#stepUpwardHistory #currentPage").val(1);
            
            loading = false;
        },
        /**
         * 无限滚动
         */
        infiniteScroll: function () {
            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            //==========================切记此处不能删除============================
            loading = true;
            //==========================切记此处不能删除============================
            var currentTab = $('#stepUpwardHistory #currentTab').val();
            var totalPage,currentPage,tab;
        	totalPage = $$("#stepUpwardHistory #totalPage").val();
            currentPage = $$("#stepUpwardHistory #currentPage").val();
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
            if (currentTab == 'progressingTab') {
	            tab = "1";
            } else {
	            tab = "2";
            }
            $$("#stepUpwardHistory #currentPage").val(currentPage);
            
            stepUpwardHistoryCtrl.selectItems(currentPage, "push",tab);
            
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        /**
         * 投资记录
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectItems: function (currentPage, type ,currentTab) {
            req.callJSON({
                url:'product/myInvestmentProducts.do',
                data:{
                    pCode:'BBGS',
                    currentPage: currentPage,
                    pageSize: pageSize,
                    type:currentTab
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                	try{
                		if(result.code == 200000) {
                            var list = "";
                            if (result.data.list != null && result.data.list.length > 0) {
                                list = result.data.list;
                            }

                            $$("#stepUpwardHistory #totalPage").val(result.data.totalCount);

                            //状态
                            var isFinished = false;
                            if ("2" == currentTab) {
                                isFinished = true;
                            }

                            var tenderList = [];
                            if (list != null && list != "") {
                                for (var i = 0; i < list.length; i++) {
                                    var su = list[i];
                                    tenderList.push({
                                        'stepId': su.productId,
                                        'stepJoinId': su.joinId,
                                        'sname': su.name,
                                        'sjoin': su.joinId.substring(2),
                                        'currentApr': su.currentApr,
                                        'day': su.day,
                                        'outDay':su.outDay,
                                        'remacapital': appFunc.fmoney(su.remaCapital, 2),
                                        'interest': appFunc.fmoney(su.interest, 2),
                                        'collectInterest':appFunc.fmoney(su.collectInterest,2),
                                        'account': appFunc.fmoney(su.account, 2),
                                        'addTime': DateHandle.formatDate('yyyy-MM-dd',new Date(su.addDate)),
                                        'endDate': su.endDate,
                                        'isFinished': isFinished,
                                        'lastQuitApr': su.lastQuitApr,
                                        'isInLocked': su.isInLocked,
                                        'joinId':su.joinId
                                    });
                                }
                            }

                            req.callGet({
                                url: GC.getHtmlPath() + 'stepUpward/stepUpwardHistoryListItem.html?' + GC.getVersion(),
                                dataType: 'text',
                                success: function (result) {
                                    stepUpwardHistoryView.showListItem({
                                        result: result,
                                        tenderList: tenderList,
                                        type: type,
                                        currentTab: currentTab
                                    });
                                    stepUpwardHistoryCtrl.listItemBindEvent();
                                    // 加载完毕需要重置
                                    xxdApp.pullToRefreshDone();
                                }
                            });
						}
                        
	                  }catch (e) {
	                  	xxdApp.hideIndicator();
                		console.log(e.message);
                		xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取步步高升投资记录失败，请稍后重试...'
                        });
        			}
                },
                error: function(xhr, type){
                	console.log("获取步步高升投资记录失败,ajax error...");
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '获取步步高升投资记录失败，请稍后重试...'
                    });
                }
            });

            setTimeout(function () {
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            }, 5000);
        },
        getQuitRecords: function () {
        	var stepJoinId = $$(this).attr("data-stepJoinId");
        	var showStatus = $$(this).attr("data-showStatus");
        	
        	if(showStatus == 1){
        		$$(this).attr("data-showStatus" , 0);
        		return;
        	}else{
        		$$(this).attr("data-showStatus" , 1);
        	}
        	
        	req.callJSON({
                url: "product/investmentDetail.do",
                data: {
                    "joinId": stepJoinId
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                	try{

	                    var stepQuitList = result.data.stepQuitList;
	                    if (stepQuitList != null && stepQuitList.length > 0) {

	                        var quitList = [];
		                    if (stepQuitList != null && stepQuitList != "") {
		                        for (var i = 0; i < stepQuitList.length; i++) {
		                            var su = stepQuitList[i];
		                            //不展示退出失败的数据
		                            if(-1 == su.status){
		                            	continue;
		                            }
		                            var showQuitStatus = false;
		                            var quitStatus = "";
		                            if(0 == su.status){
		                            	showQuitStatus = true;
		                            	quitStatus = "退出中";
		                            }
		                            var quitTime = su.quitDate ;
                                    quitTime = DateHandle.formatDate('yyyy-MM-dd HH:mm:ss',new Date(quitTime));
		                            quitList.push({
		                                'quitTime': quitTime,
		                                'quitAccount': appFunc.fmoney(su.quitCapital, 2),
		                                'quitapr': su.quitApr,
		                                'interest': appFunc.fmoney(su.quitInterest, 2),
		                                'showQuitStatus': showQuitStatus,
		                                'quitStatus': quitStatus
		                            });
		                        }
		                    }
	                        req.callGet({
	                            url: GC.getHtmlPath() + 'stepUpward/stepUpwardHistoryQuitItem.html?' + GC.getVersion(),
	                            dataType: 'text',
	                            success: function (result) {
	                                var compiledTemplate = t7.compile(result);
						            var output = compiledTemplate({list: quitList});
						            $$("#stepUpwardHistory .quitList_" + stepJoinId).html(output); 
	                            }
	                        });
	                    }else{
	                    	output='<li style="background-color:white;"><h6 class="font-grey text-center pd10">暂无记录</h6></li>';
	                    	$$("#stepUpwardHistory .quitList_" + stepJoinId).html(output);
	                    }
	                  }catch (e) {
	                  	xxdApp.hideIndicator();
                		console.log(e.message);
                		xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取步步高升退出记录失败，请稍后重试...'
                        });
        			}
                },
                error: function(xhr, type){
                	console.log("获取步步高升退出记录失败,ajax error...");
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '获取步步高升退出记录失败，请稍后重试...'
                    });
                }
            });
		},
		quitConfirm: function(){
			quitButton= $$(this);
			var qInfo = '退出本金<span id="quitStepUpwardAmount">0</span>元，收益<span id="quitStepUpwardInterest">0</span>元';
			//var qInfo = '退出金额为200的整数倍，每月仅有一次退出机会';
			var isInLocked = quitButton.attr("data-isInLocked");
			if("true" == isInLocked){
				xxdApp.modal({
					title : '提前退出提示',
					text :  '<div style="font-size:13px;text-align:left;padding-top:10px;padding-bottom: 10px;"><span>1) 提前退出会产生违约行为，我们将不结算收益给您，只退还本金</span><br><br>' +
							'<span>2) 如果您投资时使用了新手红包，使用的红包金额也会从本金中扣除</span></div>',
					buttons : 
					[
						{
							text : '取消'
						}, 
						{
							text : '继续',
							onClick : function() {
								//步步高升提前退出GA
								gaClickEvent({property1:"转出",property2: "步步高升提前转出", property3:window.location});
								stepUpwardHistoryCtrl.quitStepUpward("",qInfo);
							}
						}
					]
				});
			}else{
        //步步高升退出GA
				gaClickEvent({property1:"转出",property2: "步步高升转出", property3:window.location});
				stepUpwardHistoryCtrl.quitStepUpward("",qInfo);
			}
		},
		quitStepUpward : function(qAmount,qInfo) {
        	quitStepUpwardId = quitButton.attr("data-stepJoinId");
            var stepId = quitButton.attr("data-stepId");
        	var holdAmount = quitButton.attr("data-holdAmount");
        	var floatAmount = parseFloat(holdAmount.split(',').join(""));
        	var isInLocked = quitButton.attr("data-isInLocked");
        	var title = "退出";
        	if("true" == isInLocked){
        		title = "提前退出";
        	}
			xxdApp.modal({
				title : title,
				text : '<div class="list-block" style="margin-bottom:2px; font-size:14px;">'+
				        '<ul style="background:#E8E8E8;">'+
				          '<li>'+
				            '<div class="item-content" style="padding-left: 0px;">'+
				              '<div class="item-inner" style="padding-right: 0px;">'+ 
				                '<div class="item-title">当前可退出金额<span>' + holdAmount + '</span>元</div>'+
				              '</div>'+
				            '</div>'+
				          '</li>'+
				            '<li>'+
				                '<div class="item-content" style="padding-left: 0px;">'+
				                    '<div class="item-inner" style="padding-right: 0px;">'+
				                        '<div class="item-input">'+
				                            '<input type="tel" placeholder="请输入退出金额" id="quitAmount" value="'+ qAmount + '" style="font-size:14px;padding:0px;">'+
				                        '</div>'+
				                        '<div class="item-after">'+
				                            '<span id="quitAll" style="color:#007aff;font-size:13px;" data-holdAmount="' + floatAmount + '">全退</span>'+
				                        '</div>'+
				                    '</div>'+
				                '</div>'+
				            '</li>'+
				            '<li>'+
					            '<div class="item-content" style="padding-left: 0px;">'+
					              '<div class="item-inner" style="padding-right: 0px;">'+ 
					                '<div class="item-title" style="font-size:13px;color:red;white-space: normal;text-align:left;" id="quitInfo">' + qInfo + '</div>'+
					              '</div>'+
					            '</div>'+
					        '</li>'+
				            '<li>'+
				                '<div class="item-content" style="padding-left: 0px;">'+
				                    '<div class="item-inner" style="padding-right: 0px;">'+
				                        '<div class="item-input">'+
				                            '<input type="password" placeholder="请输入支付密码" id="payPwd" style="font-size:14px;padding:0px;">'+
				                        '</div>'+
				                        '<div class="item-after">'+
//				                            '<span id="forgetPayPassword" style="color:#007aff;font-size:13px;">忘记支付密码</span>'+
				                        '</div>'+
				                    '</div>'+
				                '</div>'+
				            '</li>'+
				          '</ul>'+
				      '</div>',
				buttons : 
				[
					{
						text : '取消'
					}, 
					{
						text : '确认',
						onClick : function() {
							var quitAmount = $$("#quitAmount").val();
							var quitInfo = $$("#quitInfo").html();
							var payPwd = $$("#payPwd").val();

							if (payPwd == null || payPwd == '') {
			                    xxdApp.alert('请输入支付密码！','提示', function(){
			                    	//quitButton.trigger("click", stepUpwardHistoryCtrl.quitStepUpward(quitAmount,quitInfo));
			                    	stepUpwardHistoryCtrl.quitStepUpward(quitAmount,quitInfo);
			                    });
			                    return;
			                }
				        	req.callJSON({
				                url: "product/quit.do",
				                data: {
                                    pType:96,
                                    pId:stepId,
                                    payPwd: $.md5(payPwd),
                                    joinId:quitStepUpwardId,
                                    quitAmount:quitAmount
				                },
				                dataType: 'json',
				                indicator: true,
				                success: function (result) {
				                	try{
				                	    var data = result.data;
				                		if(result.code == 200000 && data.resultCode == 0){
															redemption({id:data.productId,name:"步步高升赎回",category:"步步高升/" + data.apr + "%/" + data.terms + "天",price:data.price,tradeId:data.tradeid,affiliation:data.servicenum});
				                			xxdApp.alert('退出成功','提示',function(){
					                			GS.reloadPage("stepUpward/stepUpwardHistory.html?path=stepUpward");
				                			});
				                		}else{
				                		    var msg = result.message;
				                		    if(data.resultCode != 0) {
                                                msg = data.desc;
                                            }
				                			xxdApp.alert(msg,'提示',function(){
				                				//一个月仅有一次退出机会，请下个月再退出
				                				if(msg.indexOf("仅有一次退出机会") > 0){
					                    		}else{
						                			//quitButton.trigger("click", stepUpwardHistoryCtrl.quitStepUpward(quitAmount,quitInfo));
						                			stepUpwardHistoryCtrl.quitStepUpward(quitAmount,quitInfo);
					                    		}
				                			});
				                		}
					                }catch (e) {
					                  	xxdApp.hideIndicator();
				                		xxdApp.addNotification({
				                            title: '抱歉',
				                            hold: 3000,
				                            message: '步步高升退出失败，请稍后重试...'
				                        });
				        			}
				                },
				                error: function(xhr, type){
				                    xxdApp.addNotification({
				                        title: '抱歉',
				                        hold: 3000,
				                        message: '步步高升退出失败，请稍后重试...'
				                    });
				                }
				            });
						}
					}
				]
			});
			stepUpwardHistoryCtrl.bindModalEvents();
		},
        bindModalEvents: function () {
           var bindings = [
                {
                    element: '#quitAll',
                    event: 'click',
                    handler: stepUpwardHistoryCtrl.quitAll
                },
                {
                    element: '#forgetPayPassword',
                    event: 'click',
                    handler: stepUpwardHistoryCtrl.forgetPayPassword
                },
                {
                    element: '#quitAmount',
                    event: 'blur',
                    handler: stepUpwardHistoryCtrl.getQuitInfo
                }
            ];
            stepUpwardHistoryView.bindEvents({
                        bindings: bindings
                    }
            );
		},
        quitAll: function () {
        	var holdAmount = $$(this).attr("data-holdAmount");
        	$$("#quitAmount").val(holdAmount);
        	stepUpwardHistoryCtrl.getQuitInfo();
		},
        getQuitInfo: function () {
        	var quitAmount = $$("#quitAmount").val();
            if (!quitAmount) {
                $$("#quitInfo").html("请填写退出金额");
                return;
            }
            if (!appFunc.isFloat(quitAmount)) {
                $$("#quitInfo").html("请正确填写退出金额");
                return;
            }
            if (quitAmount <= 0) {
                $$("#quitInfo").html("退出金额须大于零");
                return;
            }
        	req.callJSON({
                url: "stepUpward/getQuitInfo.do",
                data: {
                    "quitAmount": quitAmount,
                    "stepJoinId": quitStepUpwardId
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                	try{
                		if(data.resultCode == 0){
                			if(data.data.isAdvanceQuit){
                				$$("#quitInfo").html("退出本金" + quitAmount + "元，提前退出无利息");
                			}else{
                				$$("#quitStepUpwardAmount").html(quitAmount);
                				$$("#quitStepUpwardInterest").html(data.data.interest);
                			}
                		}else{
                			$$("#quitInfo").html(data.desc);
                		}
	                }catch (e) {
	                  	xxdApp.hideIndicator();
                		xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取退出信息失败，请稍后重试...'
                        });
        			}
                },
                error: function(xhr, type){
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '获取退出信息失败，请稍后重试...'
                    });
                }
            });
		},
        forgetPayPassword: function () {
        	//暂无此功能
		}
    };
    return stepUpwardHistoryCtrl;
});