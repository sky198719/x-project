/**
 * 热门活动列表
 * Created by chaihuangqi on 2015/5/20.
 */
define(['js/hotActivities/hotActivitiesView', 'share', 'js/utils/date'], function (hotActivitiesView, share, DateHandle) {

    var pageSize = 4;
    // 加载flag
    var loading = false;
    var totalPages = 0;
    var hotActivitiesCtrl = {
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
                    handler: hotActivitiesCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: hotActivitiesCtrl.infiniteScroll
                }
            ];

            hotActivitiesView.bindEvents({
                    bindings: bindings
                }
            );
            hotActivitiesCtrl.pullToRefresh();

        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            hotActivitiesCtrl.selectHotActivities(1, "pull");
            $$("#currentPage").val(1);
            // 重置加载flag
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
            //var totalPages = $$("#totalPages").val();
            var currentPage = $$("#currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage > totalPages) {
                //提示标的已经全部显示
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '数据已全部加载完毕'
                });
                loading = true;
                return;
            }
            hotActivitiesCtrl.selectHotActivities(currentPage, "push");
            $$("#currentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },

        /**
         * 查询热门活动
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectHotActivities: function (currentPage, type) {

            req.callJSON({
                url: "hotActivities/search/list.do",
                data: {
                    currentPage: currentPage,
                    pageSize: pageSize
                },
                indicator: true,
                timeout: 10000,
                success: function (data) {
                    var currentDate = new Date(Date.parse(DateHandle.formatDate('yyyy-MM-dd HH:mm:ss',new Date(data.currentDate)).replace(/-/g,"/")));
                    var list = data.hotActivitiesList;
                    if (list == null || list == undefined) {
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: '数据已全部加载完成'
                        });
                        return;
                    }

                    totalPages = data.totalPages;
                    if (data.currentPage > data.totalPages) {
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: '数据已全部加载完成'
                        });
                        return;
                    }

                    if (list.length == 0) {
                        //提示活动已经全部显示
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: '数据已全部加载完成'
                        });
                        return;
                    }

                    var hotActivitiesList = [];
                    for (var k = 0; k < list.length; k++) {
                        var b = list[k];
                        var picPath = data.imageUrl + '/' + b.picPath;
                        var statusClass='';
                        var statusValue='';
                        var beginDate = new Date(Date.parse(DateHandle.formatDate('yyyy-MM-dd HH:mm:ss',new Date(b.beginDate)).replace(/-/g,"/")));
                        var endDate = new Date(Date.parse(DateHandle.formatDate('yyyy-MM-dd HH:mm:ss',new Date(b.endDate)).replace(/-/g,"/")));
                        if (currentDate < beginDate) {
                            statusClass='activity_ready';
                            statusValue='即将开始';
                        } else if (currentDate > endDate) {
                            statusClass='activity_finished';
                            statusValue='已结束';
                        } else {
                            statusClass='activity_started';
                            statusValue='进行中';
                        }

                        hotActivitiesList.push({
                            "activityId": b.activityId,
                            "topic": b.topic,
                            "picPath": picPath,
                            "beginDate": DateHandle.formatDate('yyyy-MM-dd', new Date(b.beginDate)),
                            "endDate": DateHandle.formatDate('yyyy-MM-dd', new Date(b.endDate)),
                            "jumpUrl": b.jumpUrl,
                            "shareUrl": b.shareUrl,
                            "sharePic": picPath,
                            "shareTopic": b.shareTopic,
                            "statusClass":statusClass,
                            "statusValue":statusValue
                        });
                    }
                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'hotActivities/hotActivitiesList.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                hotActivitiesView.showListItem({result: result, hotActivitiesList: hotActivitiesList, type: type});
                                hotActivitiesView.viewDetail({result: result, hotActivitiesList: hotActivitiesList, type: type});
                                hotActivitiesCtrl.tenderListBindEvent();
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
        tenderListBindEvent: function () {
            var bindings = [
                {
                    element: '.shareSina',
                    event: 'click',
                    handler: hotActivitiesCtrl.sinaShare
                }
            ];

            hotActivitiesView.bindEvents({
                        bindings: bindings
                    }
            );
        },

        sinaShare: function () {
            share.sinaShare({
                url: $$(this).attr('shareUrl'),
                title: $$(this).attr('shareTopic'),
                pic: $$(this).attr('sharePic')
            });
        }
    };

    return hotActivitiesCtrl;
});
