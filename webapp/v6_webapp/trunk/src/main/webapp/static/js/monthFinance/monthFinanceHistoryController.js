define(['js/monthFinance/monthFinanceHistoryModel', 'js/monthFinance/monthFinanceHistoryView','js/utils/date'], function (MonthFinanceHistoryModel, monthFinanceHistoryView, DateHandle) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var monthFinanceHistoryCtrl = {
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
                    handler: monthFinanceHistoryCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: monthFinanceHistoryCtrl.infiniteScroll
                },
                {
                    element: '#monthFinanceHistory #progressingTab',
                    event: 'show',
                    handler: monthFinanceHistoryCtrl.showProgressingTab
                },
                {
                    element: '#monthFinanceHistory #completedTab',
                    event: 'show',
                    handler: monthFinanceHistoryCtrl.showCompletedTab
                }
            ];
            monthFinanceHistoryView.bindEvents({
                        bindings: bindings
                    }
            );
            monthFinanceHistoryCtrl.pullToRefresh();

        },

        /**
         * 收益中
         */
        showProgressingTab: function () {
            $$('#monthFinanceHistory #currentTab').val('progressingTab');
            monthFinanceHistoryCtrl.pullToRefresh();
        },
        /**
         * 已完成
         */
        showCompletedTab: function () {
            $('#monthFinanceHistory #currentTab').val('completedTab');
            monthFinanceHistoryCtrl.pullToRefresh();
        },
        
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            var currentTab = $('#monthFinanceHistory #currentTab').val();
            if (currentTab == 'progressingTab') {
                monthFinanceHistoryCtrl.selectItems(1, "pull","1");
            } else {
                monthFinanceHistoryCtrl.selectItems(1, "pull","2");
            } 
            $$("#monthFinanceHistory #currentPage").val(1);
            
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
            var currentTab = $('#monthFinanceHistory #currentTab').val();
            var totalPage,currentPage,tab;
        	totalPage = $$("#monthFinanceHistory #totalPage").val();
            currentPage = $$("#monthFinanceHistory #currentPage").val();
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
            $$("#monthFinanceHistory #currentPage").val(currentPage);
            
            monthFinanceHistoryCtrl.selectItems(currentPage, "push",tab);
            
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
            req.callGet({
                url:'product/myInvestmentProducts.do',
                data:{
                    pCode:'YJDJ',
                    currentPage: currentPage,
                    pageSize: pageSize,
                    type:currentTab
                },
                dataType:'json',
                indicator: true,
                success: function (result) {
                	try{
	                    if (result.data != null) {
	                        $$("#monthFinanceHistory #totalPage").val(result.data.totalCount);
	                    }

	                    var tenderList = [];
	                    var list = result.data.list;
	                    if (list != null && list != "") {
	                        for (var i = 0; i < list.length; i++) {
	                            var mfh = list[i];
	                            //状态
	                            var isFinished = false;
	                            if ("2" == mfh.status) {
	                            	isFinished = true;
	                            }
	                            var addTime = DateHandle.formatDate('yyyy-MM-dd',new Date(mfh.addDate));
	                            var date=new Date(mfh.addDate);
	                            var timeLimit = mfh.period;
								var newtimems = date.getTime()+(timeLimit*24*60*60*1000);
								date.setTime(newtimems);
		                		var finishTime = DateHandle.formatDate("yyyy-MM-dd", date ,null);
	                            tenderList[i] = new MonthFinanceHistoryModel({
	                                'deployId': mfh.productId,
	                                'name': mfh.name,
	                                'apr': mfh.apr,
	                                'addTime': addTime,
	                                'status': mfh.status,
	                                'effectiveMoney': appFunc.fmoney(mfh.account, 2),
	                                'repayInterest': appFunc.fmoney(mfh.interest, 2),
	                                'terms': mfh.terms,
	                                'timeLimit': timeLimit,
	                                'finishTime': finishTime,
	                                'isFinished': isFinished,
                                    'joinId':mfh.joinId
	                            })
	                        }
	                    }

                        req.callGet({
                            url: GC.getHtmlPath() + 'monthFinance/monthFinanceHistoryListItem.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                monthFinanceHistoryView.showListItem({
                                    result: result,
                                    tenderList: tenderList,
                                    type: type,
                                    currentTab: currentTab
                                });
                                // 加载完毕需要重置
                                xxdApp.pullToRefreshDone();
                            }
                        });
                        
	                  }catch (e) {
	                  	xxdApp.hideIndicator();
                		console.log(e.message);
                		xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取月进斗金投资记录失败，请稍后重试...'
                        });
        			}
                },
                error: function(xhr, type){
                	console.log("获取月进斗金投资记录失败,ajax error...");
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '获取月进斗金投资记录失败，请稍后重试...'
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
        timeyyyyMMddHHmmss2: function (value, stat) {
            if (stat != 4)
                return '-';
            else {
                return value.substr(0, 10);
            }
        }
    };
    return monthFinanceHistoryCtrl;
});