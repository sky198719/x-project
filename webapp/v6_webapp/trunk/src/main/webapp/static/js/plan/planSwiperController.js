/**
 * 新元宝轮播图片
 */
define(['js/plan/planSwiperView'], function (planSwiperView) {
    var planSwiperCtrl = {
        init: function () {
            planSwiperCtrl.initXplanSwiper();

            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: planSwiperCtrl.pullToRefresh
                },
                {
                    element: '#plan_swiper_to_list',
                    event: 'click',
                    handler: planSwiperCtrl.toList
                }
            ];
            planSwiperView.bindEvents({
                    bindings: bindings
                }
            );
        },
        toList: function () {
            GS.loadPage("popular/financesList.html");
        },
        /**
         * 幻灯片轮播
         */
        initXplanSwiper: function () {
            req.callJSON({
                url: 'xplan/schemeList.do',
                data: {
                    currentPage: 1,
                    pageSize: 6,
                    exceptStatus: 5
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if (data != null) {
                        planSwiperView.initXplanSwiper(data);
                        planSwiperCtrl.planListBindEvent();
                        // 加载完毕需要重置
                        xxdApp.pullToRefreshDone();
                    }
                }
            });
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            planSwiperCtrl.initXplanSwiper();
            setTimeout(function () {
                // 加载完毕需要重置
                xxdApp.pullToRefreshDone();
            }, 5000);
        },
        /**
         * 列表点击事件绑定
         */
        planListBindEvent: function () {
            var bindings = [
                {
                    element: 'div[name="xyb-swiper-wrapper"] a',
                    event: 'click',
                    handler: planSwiperCtrl.toPlanDetail
                }
            ];
            planSwiperView.bindEvents({
                    bindings: bindings
                }
            );
        },

        toPlanDetail: function () {
            var planId = $(this).attr("data-id");
            GS.loadPage('plan/planDetail.html?planId=' + planId + "&path=plan");
        }

    };
    return planSwiperCtrl;
});