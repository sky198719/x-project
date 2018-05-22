define(['js/fund/fundDetailsListView'], function (fundDetailsListView) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var fcode='';
    var fundDetailsListCtrl = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);
            fcode = query.fcode;
            fundDetailsListCtrl.initBindEvent();
            var tab = query.tab;
            switch(tab) {
                case 'earnings':
                    xxdApp.showTab("#fundEarnings");
                    fundDetailsListCtrl.clickFundEarnings();
                    break;
                case 'transferIn':
                    xxdApp.showTab("#fundTransferIn");
                    fundDetailsListCtrl.clickfundTransferIn();
                    break;
                case 'transferOut':
                    xxdApp.showTab("#fundTransferOut");
                    fundDetailsListCtrl.clickfundTransferOut();
                    break;
                default :
                    xxdApp.showTab("#fundEarnings");
                    fundDetailsListCtrl.clickFundEarnings();
                    break;
            }
        },
        initBindEvent: function () {
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: fundDetailsListCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: fundDetailsListCtrl.infiniteScroll
                },
                {
                    element: '#fundEarnings',
                    event: 'show',
                    handler: fundDetailsListCtrl.clickFundEarnings
                },
                {
                    element: '#fundTransferIn',
                    event: 'show',
                    handler: fundDetailsListCtrl.clickfundTransferIn
                },
                {
                    element: '#fundTransferOut',
                    event: 'show',
                    handler: fundDetailsListCtrl.clickfundTransferOut
                }
            ];

            appFunc.bindEvents(bindings);
        },
        clickFundEarnings: function () {
            $$(".subnavbar.fundTradeList").dataset.currentTab = 'earnings';
            fundDetailsListCtrl.pullToRefresh();
        },
        clickfundTransferIn: function () {
            $$(".subnavbar.fundTradeList").dataset.currentTab = 'transferIn';
            fundDetailsListCtrl.pullToRefresh();
        },
        clickfundTransferOut: function () {
            $$(".subnavbar.fundTradeList").dataset.currentTab = 'transferOut';
            fundDetailsListCtrl.pullToRefresh();
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            var currentTab = $$(".subnavbar.fundTradeList").dataset.currentTab;
            switch (currentTab) {
                case 'earnings':
                    fundDetailsListCtrl.selectTrade(fcode, 1, "pull", 1, 2, currentTab);
                    break;
                case 'transferIn':
                    fundDetailsListCtrl.selectTrade(fcode, 1, "pull", 1, 1, currentTab);
                    break;
                case 'transferOut':
                    fundDetailsListCtrl.selectTrade(fcode, 1, "pull", 2, 1, currentTab);
                    break;
            }

            $$(".subnavbar.fundTradeList").dataset.currentPage = 1;
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
            var totalPage = $$(".subnavbar.fundTradeList").dataset.totalPage;
            var currentPage = $$(".subnavbar.fundTradeList").dataset.currentPage;
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
            var currentTab = $$(".subnavbar.fundTradeList").dataset.currentTab;
            switch (currentTab) {
                case 'earnings':
                    fundDetailsListCtrl.selectTrade(fcode, currentPage, "push", 1, 2, currentTab);
                    break;
                case 'transferIn':
                    fundDetailsListCtrl.selectTrade(fcode, currentPage, "push", 1, 1, currentTab);
                    break;
                case 'transferOut':
                    fundDetailsListCtrl.selectTrade(fcode, currentPage, "push", 2, 1, currentTab);
                    break;
            }

            $$(".subnavbar.fundTradeList").dataset.currentPage = currentPage;
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },

        /**
         * 查询交易记录
         */
        selectTrade: function (fcode, currentPage, refreshType, type, initiator, currentTab) {
            req.callGet({
                url: 'fund/selectTrade.do',
                data: {
                    currentPage: currentPage,
                    pageSize: pageSize,
                    type: type,
                    initiator: initiator,
                    fcode: fcode
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    $$(".subnavbar.fundTradeList").dataset.totalPage = data.totalPage;
                    fundDetailsListView.showDetails({
                        currentTab: currentTab,
                        refreshType: refreshType,
                        dataList: data.dataList
                    });
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            });
            setTimeout(function () {
                if (refreshType == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            }, 5000);
        }
    };

    return fundDetailsListCtrl;
});