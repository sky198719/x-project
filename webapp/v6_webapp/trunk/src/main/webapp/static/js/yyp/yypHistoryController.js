define(['js/yyp/yypHistoryView'],function (yypHistoryView) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var yypHistoryCtrl = {
        init: function () {
            yypHistoryCtrl.eventBind();
            yypHistoryCtrl.pullToRefresh();
        },
        eventBind: function () {
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: yypHistoryCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: yypHistoryCtrl.infiniteScroll
                }
            ];

            appFunc.bindEvents(bindings);
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            yypHistoryCtrl.selectMyYypList(1, "pull");
            $$("#yypHistory #currentPage").val(1);
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
            var totalPage = $$("#yypHistory #totalPage").val();
            var currentPage = $$("#yypHistory #currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage*10 >= totalPage) {
                xxdApp.addNotification({ title: '温馨提示', message: '数据已经全部加载完毕...', hold: 3000 });
                loading = true;
                return;
            }
            yypHistoryCtrl.selectMyYypList(currentPage, "push");
            $$("#yypHistory #currentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        /**
         * _记录
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectMyYypList: function (currentPage, type) {
            req.callJSON({
                url: "product/myInvestmentProducts.do",
                data: {
                    pCode:'YYP',
                    currentPage: currentPage,
                    pageSize: pageSize,
                    type:3
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    yypHistoryView.showListItem({
                        data: data,
                        type: type
                    });

                    yypHistoryCtrl.myYypListBindEvent();
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            });

            setTimeout(function () {
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            }, 5000);
        },
        /**
         * _列表事件绑定
         */
        myYypListBindEvent: function () {
            var bindings = [
                {
                    element: '#myYypList div[name="showYypDetail"]',
                    event: 'click',
                    handler: yypHistoryCtrl.showYypDetail
                }
            ];
            yypHistoryView.bindEvents({
                    bindings: bindings
                }
            );
        },
        showYypDetail: function () {
            var yypId = $(this).attr("data-id");
            var joinId = $(this).attr("data-joinid");
            GS.loadPage('yyp/paymentPlan.html?yypId=' + yypId+"&joinId="+joinId);
        }
    };
    return yypHistoryCtrl;
});
