define([ 'js/account/tradeTransferingView' , 'js/account/tradeTransferingModel','js/utils/date'], function ( tradeTransferingView, tradeTransferingModel,DateHandle) {
    // 加载flag
    var loading = false;
    var tradeTransferingCtrl = {
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
                    handler: tradeTransferingCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: tradeTransferingCtrl.infiniteScroll
                }
            ];
            tradeTransferingView.bindEvents({
                    bindings: bindings
                }
            );
            tradeTransferingCtrl.pullToRefresh();
        },

        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            tradeTransferingCtrl.selectTradeTransfering(1, "pull");
            $$("#tti_currentPage").val(1);
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
            var totalPage = $$("#tti_totalPage").val();
            var currentPage = $$("#tti_currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage > totalPage) {
                //提示标的已经全部显示
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '已经到底了'
                });
                loading = true;
                return;
            }
            tradeTransferingCtrl.selectTradeTransfering(currentPage, "push");
            $$("#tti_currentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        /**
         * 转让中的债权
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectTradeTransfering: function (currentPage, type) {
            req.callJSON({
                url: "traderequest/queryTradeTransferingList.do",
                data: {
                    currentPage: currentPage,
                    currentTab: 2
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    var list = "";
                    if (data.resultList != null) {
                        list = data.resultList;
                        $$("#tti_totalPage").val(data.pageNums);
                    }
                    var tradeTransferingList = [];
                    if (list != null && list != "") {
                        for (var i = 0; i < list.length; i++) {
                            var b = list[i];
                            tradeTransferingList[i] = new tradeTransferingModel({
                                'borrowName': b.borrowName,
                                'tenderId': b.tenderId,
                                'requestId': b.requestId,
                                'addTime': DateHandle.formatDate('yyyy-MM-dd',new Date(b.addTime)),
                                'repayCapital_funds': appFunc.fmoney(b.repayCapital_funds, 2),
                                'apr': b.apr,
                                'remainNumber': b.remainNumber,
                                'statusName' : b.statusName,
                                'status': b.status
                            });
                        }
                    }

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'account/tradeTransferingList.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                tradeTransferingView.showListItem({
                                    result: result,
                                    tradeTransferingList: tradeTransferingList,
                                    type: type
                                });

                                var binding = [
                                    {
                                        element: '.trade_reqeust_rollback',
                                        event: 'click',
                                        handler: tradeTransferingCtrl.toTradeRequestRollback
                                    }
                                ];

                                appFunc.bindEvents(binding);

                                // 加载完毕需要重置
                                xxdApp.pullToRefreshDone();
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }
                }
            });

            setTimeout(function () {
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            }, 5000);
        },

        toTradeRequestRollback:function(){
            var tenderId = this.getAttribute("data-tenderId");
            var requestId = this.getAttribute("data-requestId");
            GS.loadPage('trade/tradeRequestRollback.html?tenderId='+tenderId+'&requestId='+requestId);
        }
    };
    return tradeTransferingCtrl;
});