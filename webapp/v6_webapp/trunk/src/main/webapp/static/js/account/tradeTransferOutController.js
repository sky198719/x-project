define([ 'js/account/tradeTransferOutView' , 'js/account/tradeTransferOutModel'], function ( tradeTransferOutView, tradeTransferOutModel) {
    // 加载flag
    var loading = false;
    var tradeTransferOutCtrl = {
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
                    handler: tradeTransferOutCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: tradeTransferOutCtrl.infiniteScroll
                }
            ];
            tradeTransferOutView.bindEvents({
                    bindings: bindings
                }
            );
            tradeTransferOutCtrl.pullToRefresh();
        },

        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            tradeTransferOutCtrl.selecttradeTransferOut(1, "pull");
            $$("#tto_currentPage").val(1);
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
            var totalPage = $$("#tto_totalPage").val();
            var currentPage = $$("#tto_currentPage").val();
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
            tradeTransferOutCtrl.selecttradeTransferOut(currentPage, "push");
            $$("#tto_currentPage").val(currentPage);
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
        selecttradeTransferOut: function (currentPage, type) {
            req.callJSON({
                url: "traderequest/queryTradeTransferOutList.do",
                data: {
                    currentPage: currentPage,
                    currentTab: 3
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    var list = "";
                    if (data.resultList != null) {
                        list = data.resultList;
                        $$("#tto_totalPage").val(data.pageNums);
                    }
                    var tradeTransferOutList = [];
                    if (list != null && list != "") {
                        for (var i = 0; i < list.length; i++) {
                            var b = list[i];
                            tradeTransferOutList[i] = new tradeTransferOutModel({
                                'borrowName': b.borrowName,
                                'requestId': b.requestId,
                                'outTime': b.outTime,
                                'amount_funds': appFunc.fmoney(b.amount_funds, 2),
                                'apr': b.apr,
                                'statusName' : b.statusName,
                                'status': b.status
                            });
                        }
                    }

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'account/tradeTransferOutList.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                xxdApp.hideIndicator();
                                tradeTransferOutView.showListItem({
                                    result: result,
                                    tradeTransferOutList: tradeTransferOutList,
                                    type: type
                                });
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
        }
    };
    return tradeTransferOutCtrl;
});