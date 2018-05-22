/**
 * Created by chaihuangqi on 2015/1/4.
 * 投资记录
 */
define(['js/utils/date'], function (DateHandle) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var sevenDaysRecCtr = {
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
                    handler: sevenDaysRecCtr.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: sevenDaysRecCtr.infiniteScroll
                },
                {
                    element: '#incoming',
                    event: 'show',
                    handler: sevenDaysRecCtr.initIncoming
                },
                {
                    element: '#completed',
                    event: 'show',
                    handler: sevenDaysRecCtr.initCompleted7d
                }
            ];
            appFunc.bindEvents(bindings);
            sevenDaysRecCtr.pullToRefresh();
        },
        /**
         * 初始化投标记录
         */
        initIncoming: function () {
            $('#currentTab').val('incomingTab');
            sevenDaysRecCtr.pullToRefresh();
        },
        /**
         * 初始化债权转让记录
         */
        initCompleted7d: function () {
            $('#currentTab').val('completedTab');
            sevenDaysRecCtr.pullToRefresh();
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            var currentTab = $('#currentTab').val();
            if (currentTab == 'incomingTab') {
                sevenDaysRecCtr.selectRecord(1, 1, "pull");
            } else if (currentTab == 'completedTab') {
                sevenDaysRecCtr.selectRecord(2, 1, "pull");
            } 
            $$("#bcurrentPage").val(1);
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
            var totalPage = $$("#btotalPage").val();
            var currentPage = $$("#bcurrentPage").val();
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
            var currentTab = $('#currentTab').val();
            if (currentTab == 'incomingTab') {
                sevenDaysRecCtr.selectRecord(1, 1, "pull");
            } else if (currentTab == 'completedTab') {
                sevenDaysRecCtr.selectRecord(2, 1, "pull");
            }
            $$("#bcurrentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        selectRecord: function (status, currentPage, type) {
            req.callGet({
                url:'product/myInvestmentProducts.do',
                data:{
                    pCode:'XSCP30T',
                    currentPage: currentPage,
                    pageSize: pageSize,
                    type:status
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    if(result.code == 200000) {
                        var list = "";
                        if (result.data.list != null) {
                            list = result.data.list;
                        }
                        var completedList = [];

                        if (list != null && list != "") {
                            for (var i = 0; i < list.length; i++) {
                                var b = list[i];
                                var date = new Date(b.addDate);
                                var newTime=date.getTime()+b.period*24*60*60*1000;
                                date.setTime(newTime);
                                var isCompleted = status==2?true:false;

                                completedList.push({
                                    'isCompleted': isCompleted,
                                    'money': b.account,
                                    'aprhtml': b.apr + "%" + (b.floatApr == undefined ? "" : ("+"+b.floatApr + "%")),
                                    'addtime': DateHandle.formatDate('yyyy-MM-dd HH:mm:ss',new Date(b.addDate)),
                                    'interest': appFunc.fmoney(b.interest, 2),
                                    'endtime': DateHandle.formatDate('yyyy-MM-dd',date),
                                    'timelimit': b.period,
                                    'joinId':b.joinId
                                });
                            }
                        }
                        try {
                            req.callGet({
                                url: GC.getHtmlPath() + 'account/thirtyDaysRecList.html?' + GC.getVersion(),
                                dataType: 'text',
                                success: function (data) {
                                    if (completedList.length != 0) {
                                        var compiledTemplate = t7.compile(data);
                                        var output = compiledTemplate({list: completedList});
                                        if (type == 'push') {
                                            if(status==1)
                                                $("#incoming").append(output);
                                            else if(status==2)
                                                $("#completed").append(output);
                                        } else {
                                            if(status==1)
                                                $("#incoming").html(output);
                                            else if(status==2)
                                                $("#completed").html(output);
                                        }
                                    } else {
                                        var html='<div class="list-block media-list" ><ul>' +
                                            '<h6 class="text-center pd10">暂无数据' +
                                            '</h6></ul></div>'
                                        if(status==1)
                                            $("#incoming").html(html);
                                        else if(status==2)
                                            $("#completed").html(html);
                                    }

                                    var bind = [{
                                        element: '.userTradeList',
                                        event: 'click',
                                        handler: sevenDaysRecCtr.userTradeList
                                    }];
                                    appFunc.bindEvents(bind);
                                    // 加载完毕需要重置
                                    xxdApp.pullToRefreshDone();
                                }
                            });
                        } catch (e) {
                            xxdApp.hideIndicator();
                        }
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
        userTradeList:function(){
            var joinId = $$(this).data("joinid");
            GS.loadPage("trade/tradeList.html?joinId="+joinId+"&isTender=true")
        }
    };
    return sevenDaysRecCtr;
});