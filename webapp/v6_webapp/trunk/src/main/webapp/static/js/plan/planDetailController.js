/**
 * _详情
 */
define(['js/plan/planDetailView','js/activity/womensDay'], function (planDetailView,womensDay) {
    var joinId;
    var planDetailCtrl = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);
            $$("#plan_goTender").hide();

            var planId = query.planId;
            planDetailCtrl.loadPlanDetail(planId);
            joinId = query.joinId;

            womensDay.planDetail();
        },

        loadPlanDetail:function(planId){
            req.callJSON({
                url: 'xplan/detail/' + planId + '.do',
                data: {},
                dataType: 'json',
                preloaderTitle: '正在加载数据...',
                success: function (data) {
                    // 投标按钮
                    planDetailView.setTenderButton(data);
                    // _信息
                    planDetailView.planInfo(data,joinId);
                    // 计划介绍
                    planDetailView.credita(data);

                    /**
                     * 事件定义
                     * @type {*[]}
                     */
                    var bindings = [
                        {
                            element: '#plan_goTender',
                            event: 'click',
                            handler: planDetailCtrl.goTender,
                            attrData: {
                                name: "planId",
                                value: planId
                            }
                        },
                        {
                            element: '#plan_topup_step',
                            event: 'click',
                            handler: planDetailCtrl.topup
                        }
                    ];
                    planDetailView.bindEvent({
                            bindings: bindings
                        }
                    );
                }
            });
        },

        /**
         * 跳转投标页面
         */
        goTender: function () {
            //var dataContext = JSON.parse($$(this).attr("data-context"));
            var planId = $$(this).attr("planId");
            GS.loadPage('planTender/planTender.html?planId=' + planId);
        },

        topup: function () {
            GS.goTopup();
        }

    };
    return planDetailCtrl;
});
