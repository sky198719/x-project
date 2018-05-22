define(['js/account/bidHistoryModel'], function (Borrowtender) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var borrowHistoryCtrl = {
        init: function () {
            borrowHistoryCtrl.eventBind();
        },

        eventBind: function () {
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: borrowHistoryCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: borrowHistoryCtrl.infiniteScroll
                }
            ];

            appFunc.bindEvents(bindings);
            borrowHistoryCtrl.pullToRefresh();
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            borrowHistoryCtrl.selectTender(1, "pull");
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
            var totalPage = $$("#borrowHistoryPage").data("totalPage");
            var currentPage = $$("#borrowHistoryPage").data("currentPage");
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
            borrowHistoryCtrl.selectTender(currentPage, "push");
            $$("#borrowHistoryPage").data("currentPage",currentPage);
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
        selectTender: function (currentPage, type) {
            req.callJSON({
                url: "account/tenderQuery.do",
                data: {
                    currentPage: currentPage,
                    pageSize: pageSize
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    var list = "";
                    var tenderSum = "";
                    if (data.tenderSum != null) {
                        tenderSum = parseFloat(data.tenderSum);
                    }
                    if (data.tenderQuery != null) {
                        list = data.tenderQuery.resultList;
                        $$("#borrowHistoryPage").data("totalPage",data.tenderQuery.totalPages);
                    }
                    var tenderList = [];
                    if (list != null && list != "") {
                        for (var i = 0; i < list.length; i++) {
                            var b = list[i];

                            var isShow = false;
                            if((b.statusNumber == "4" || b.statusNumber == "5") && (b.type == 9 || b.type == 10 || b.type == 14) ) {
                                isShow = true;
                            }

                            tenderList[i] = new Borrowtender({
                                'isShow':isShow,
                                'borrowId': b.borrowId,
                                'tenderTitle': b.tenderTitle,
                                'borrowApr': b.borrowApr,
                                'tenderAddtime': borrowHistoryCtrl.timeyyyyMMddHHmmss2(b.tenderAddtime, 4),
                                'status': b.status,
                                'tenderTotalAmount': appFunc.fmoney(b.tenderTotalAmount, 2),
                                'tenderRepaymentAccount': appFunc.fmoney(b.tenderRepaymentAccount, 2),
                                'statusDouble': b.statusDouble
                            })
                        }
                    }

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'account/tenderList.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                borrowHistoryCtrl.showListItem({
                                    result: result,
                                    tenderList: tenderList,
                                    type: type,
                                    tenderSum: tenderSum == 0 ? 0.00 : tenderSum
                                });

                                var bindings = [
                                    {
                                        element: '.borrowHistoryContract',
                                        event: 'click',
                                        handler: borrowHistoryCtrl.borrowHistoryContract
                                    }
                                ];
                                appFunc.bindEvents(bindings);

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
        borrowHistoryContract:function(){
            var borrowId = $$(this).data("borrowId");
            GS.loadPage('borrow/contractModel.html?borrowId='+borrowId+"&source=3");
        },
        timeyyyyMMddHHmmss2: function (value, stat) {
            if (stat != 4)
                return '-';
            else {
                return value.substr(0, 10);
            }
        },
        showListItem: function (param) {
            if (param.tenderList.length != 0) {
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.tenderList});
                if (param.type == 'push') {
                    $$("#bidHistoryList").append(output);
                } else {
                    $$("#bidHistoryList").html(output);
                }

            } else {
                var html = '<div class="list-block media-list" ><ul>' +
                        '<h6 class="font-grey text-center pd10">暂无记录' +
                        '</h6></ul></div>'
                $$("#bidHistoryList").html(html);
            }
        }
    };
    return borrowHistoryCtrl;
});