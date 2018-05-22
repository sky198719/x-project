define(['js/account/tradeTransferView', 'js/account/tradeTransferModel'],function (tradeTransferView,tradeTransferModel) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var tradeTransferHistoryCtrl = {
        init: function () {
            tradeTransferHistoryCtrl.eventBind();
        },
        eventBind: function () {
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: tradeTransferHistoryCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: tradeTransferHistoryCtrl.infiniteScroll
                }
            ];

            appFunc.bindEvents(bindings);
            tradeTransferHistoryCtrl.pullToRefresh();
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            tradeTransferHistoryCtrl.selectTradeTransfer(1, "pull");
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
            var totalPage = $$("#tradeTransferHistoryPage").data("totalPage");
            var currentPage = $$("#tradeTransferHistoryPage").data("currentPage");
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
            tradeTransferHistoryCtrl.selectTradeTransfer(currentPage, "push");
            $$("#tradeTransferHistoryPage").data("currentPage", currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        /**
         * 已转出的债权
         */
        toTradeTransferOut: function () {
            GS.loadPage('account/tradeTransferOut.html?path=account');
        },
        /**
         * 转让中的债权
         */
        toTradeTransfering: function () {
            GS.loadPage('account/tradeTransfering.html?path=account');
        },
        /**
         * 债权转让记录
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectTradeTransfer: function (currentPage, type) {
            req.callJSON({
                url: "traderequest/queryTradeTransferList.do",
                data: {
                    currentPage: currentPage,
                    currentTab: 1
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    var list = "";
                    if (data.resultList != null) {
                        list = data.resultList;
                        $$("#tradeTransferHistoryPage").data("totalPage",data.pageNums);
                    }
                    $('#tradeTransferingCount').html(data.transferingCount);
                    $('#tradeTransferOutCount').html(data.transferOutCount);
                    var tradeTransferList = [];
                    if (list != null && list != "") {
                        for (var i = 0; i < list.length; i++) {
                            var b = list[i];
                            var isShow = false;
                            if(b.borrowStatus == 1) {
                                isShow = true;
                            }
                            tradeTransferList[i] = new tradeTransferModel({
                                'isShow':isShow,
                                'tenderId': b.tenderId,
                                'borrowId':b.borrowId,
                                'borrowName': b.borrowName,
                                'borrowTenderTime': b.borrowTenderTime,
                                'rateYear': b.rateYear,
                                'remainNumber': b.remainNumber,
                                'repayCapital': appFunc.fmoney(b.repayCapital, 2),
                                'nextRepaymentTime': b.nextRepaymentTime,
                                'effectiveMoney': appFunc.fmoney(b.effectiveMoney, 2),
                                'borrowTenderStatusName': b.borrowTenderStatusName,
                                'borrowTenderStatus': b.borrowTenderStatus
                            });
                        }
                    }

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'account/tradeTransferList.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                tradeTransferView.showListItem({
                                    result: result,
                                    tradeTransferList: tradeTransferList,
                                    type: type
                                });
                                tradeTransferHistoryCtrl.tradeTransferListBindEvent();
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
         * 当前持有债权列表事件绑定
         */
        tradeTransferListBindEvent: function () {
            var bindings = [
                {
                    element: '#tradeTransfer a[name="tradeTransfering"]',
                    event: 'click',
                    handler: tradeTransferHistoryCtrl.toTradeTransfering
                },
                {
                    element: '#tradeTransfer a[name="tradeTransferOut"]',
                    event: 'click',
                    handler: tradeTransferHistoryCtrl.toTradeTransferOut
                },
                {
                    element: '.trade_ac2',
                    event: 'click',
                    handler: tradeTransferHistoryCtrl.tradeRequest
                },
                {
                    element: '.borrowHistoryContract',
                    event: 'click',
                    handler: tradeTransferHistoryCtrl.borrowHistoryContract
                }
            ];
            tradeTransferView.bindEvents({
                        bindings: bindings
                    }
            );
        },
        borrowHistoryContract:function(){
            var borrowId = $$(this).data("borrowId");
            GS.loadPage('borrow/contractModel.html?borrowId='+borrowId+"&source=4");
        },
        //债券转出
        tradeRequest: function () {
            var tenderId = $$(this).attr("data-id");
            req.callJSON({
                url: "traderequest/checkTradeRequest.do",
                data: {
                    tenderId: tenderId,
                    type: "1"
                },
                dataType: 'json',
                indicator: true,
				timeout:15000,
                success: function (data) {
                    if (data.resultCode >= 0) {
                        var tenderId = data.result.tenderId;
                        GS.loadPage('trade/transfer.html?tenderId=' + tenderId);
                    } else {
                        if (data.resultCode == -99) {
                            //session超时，跳转登陆界面
                            xxdApp.loginScreen();
                            return;
                        } else {
                            xxdApp.alert(data.msg, '抱歉');
                        }
                    }
                }
            })
        }
    };
    return tradeTransferHistoryCtrl;
});