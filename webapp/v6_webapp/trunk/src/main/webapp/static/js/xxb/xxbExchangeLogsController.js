define(function(){
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var xxbExhangeLogsCtl = {
        init:function(){
        	mainView.showNavbar();
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: xxbExhangeLogsCtl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: xxbExhangeLogsCtl.infiniteScroll
                }
            ];
            appFunc.bindEvents(bindings);
            xxbExhangeLogsCtl.pullToRefresh();
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            xxbExhangeLogsCtl.selectExchangeLogs(1, "pull");
            $$("#el-currentPage").val(1);
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
            var totalPage = $$("#el-totalPage").val();
            var currentPage = $$("#el-currentPage").val();
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
            xxbExhangeLogsCtl.selectExchangeLogs(currentPage, "push");
            $$("#el-currentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        selectExchangeLogs:function(currentPage, type) {
            req.callJSON({
                url: 'xxb/exchangeLogs.do',
                data: {
                    currentPage:currentPage,
                    pageSize:pageSize
                },
                dataType: 'json',
                success: function (result) {
                    if(result !=null&&result.levelsLog!=null) {
                        $$("#el-totalPage").val(result.maxPage);
                        var list = result.levelsLog;
                        if(list.length == 0) {
                            $$("#exchangeLogsList ul").html('<h6 class="font-grey text-center pd10">暂无记录</h6>');
                            $$(".list-block").show();
                            return;
                        }
                        var html = '';
                        for (var i = 0; i < list.length; i++) {
                            var o = list[i];
                            html += '<li>';
                            html += '<div class="item-content">';
                            html += '<div class="item-inner">';
                            html += '<div class="item-title-row">';
                            html += '<div class="item-title"><span class="font-grey">' + o.remark + '</span>';
                            html += '<span class="font-grey" style="margin-left: 80px;">' + appFunc.fmoney(o.changeNum/50,2) + '元</span></div>';
                            html += '<div class="item-after"><h4 class="font-red">-' + o.changeNum + '个</h4></div>';
                            html += '</div>';
                            html += '<div class="item-subtitle"><span class="font-grey-A9">' + o.changeTime + '</span></div>';
                            html += '</div>';
                            html += '</div>';
                            html += '</li>';
                        }
                        if (type == 'push') {
                            $$("#exchangeLogsList ul").append(html);
                        } else {
                            $$("#exchangeLogsList ul").html(html);
                        }
                    }
                }
            });
        }
    };
    return xxbExhangeLogsCtl;
});