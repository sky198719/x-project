/**
 * _
 */
define(['js/plan/planListView', 'js/plan/planListModel','js/plan/planUtils'], function (planListView, planListModel,PlanUtils) {
    // 加载flag
    var loading = false;
    var planListCtrl = {
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
                    handler: planListCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: planListCtrl.infiniteScroll
                },
                {
                    element: '#closeTerm3',
                    event: 'show',
                    handler: planListCtrl.initCloseTerm3
                },
                {
                    element: '#closeTerm6',
                    event: 'show',
                    handler: planListCtrl.initCloseTerm6
                },
                {
                    element: '#closeTerm12',
                    event: 'show',
                    handler: planListCtrl.initCloseTerm12
                }
            ];
            planListView.bindEvents({
                    bindings: bindings
                }
            );
            planListCtrl.pullToRefresh();
        },

        /**
         * 锁定期3个月
         */
        initCloseTerm3: function () {
            $('#planCurrentTab').val('closeTerm3Tab');
            planListCtrl.pullToRefresh();
        },
        /**
         * 锁定期6个月
         */
        initCloseTerm6: function () {
            $('#planCurrentTab').val('closeTerm6Tab');
            planListCtrl.pullToRefresh();
        },
        /**
         * 锁定期12个月
         */
        initCloseTerm12: function () {
            $('#planCurrentTab').val('closeTerm12Tab');
            planListCtrl.pullToRefresh();
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            var currentTab = $('#planCurrentTab').val();
            if (currentTab == 'closeTerm3Tab') {
                planListCtrl.selectPlanList(3, 1, "pull");
            } else if (currentTab == 'closeTerm6Tab') {
                planListCtrl.selectPlanList(6, 1, "pull");
            } else if (currentTab == 'closeTerm12Tab') {
                planListCtrl.selectPlanList(12, 1, "pull");
            }
            $$("#planCurrentPage").val(1);
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
            var totalPage = $$("#planTotalPage").val();
            var currentPage = $$("#planCurrentPage").val();
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
            var currentTab = $('#planCurrentTab').val();
            if (currentTab == 'closeTerm3Tab') {
                planListCtrl.selectPlanList(3, currentPage, "push");
            } else if (currentTab == 'closeTerm6Tab') {
                planListCtrl.selectPlanList(6, currentPage, "push");
            } else if (currentTab == 'closeTerm12Tab') {
                planListCtrl.selectPlanList(12, currentPage, "push");
            }
            $$("#planCurrentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        /**
         * _列表
         * @param closeTerm  锁定期
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectPlanList: function (closeTerm ,currentPage, type) {
            req.callJSON({
                url: "xplan/schemeList.do",
                data: {
                    currentPage: currentPage,
                    closeTerm: closeTerm,
                    pageSize: 20
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    var list = "";
                    if (data.listData != null) {
                        list = data.listData;
                        $$("#planTotalPage").val(data.pageNums);
                    }
                    var planList = [];
                    if (list != null && list != "") {
                        for (var i = 0; i < list.length; i++) {
                            var b = list[i];
                            // 过滤撤销的_
                            if (b.STATUS == 5) {
                                continue;
                            }
                            var min_max_apr;
                            if (b.MINAPR == b.MAXAPR) {
                                min_max_apr = b.MINAPR + '%';
                            } else {
                                min_max_apr = b.MINAPR + '% ~ ' + b.MAXAPR + '%';
                            }
                            planList.push(new planListModel({
                                'schemeid': b.SCHEMEID,
                                'pname': PlanUtils.schemeType(b.TYPE) + ' - ' + b.PNAME,
                                'account': appFunc.fmoney(b.ACCOUNT, 2),
                                'min_max_apr': min_max_apr,
                                'closeterm': b.CLOSETERM,
                                'schemeStatus': PlanUtils.schemeToShowStatus(b.schemeStatus)
                            }));
                        }
                    }

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'plan/planListItem.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                planListView.showListItem({
                                    result: result,
                                    planList: planList,
                                    type: type,
                                    closeTerm : closeTerm
                                });
                                planListCtrl.planListBindEvent(closeTerm);
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
        /**
         * 列表点击事件绑定
         */
        planListBindEvent: function (closeTerm) {
            var bindings = [
                {
                    element: '#closeTerm' + closeTerm + ' li',
                    event: 'click',
                    handler: planListCtrl.toPlanDetail
                }
            ];
            planListView.bindEvents({
                    bindings: bindings
                }
            );
        },

        toPlanDetail: function () {
            var planId = $(this).attr("data-id");
            GS.loadPage('plan/planDetail.html?planId=' + planId + "&path=plan");
        }

    };
    return planListCtrl;
});