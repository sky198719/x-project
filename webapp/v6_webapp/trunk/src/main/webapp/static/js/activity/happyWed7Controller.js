/**
 * 六月欢乐周三
 */
define(['js/utils/date'], function () {
    var happyWed7Ctrl = {
        init: function () {
            var bindings = [
                {
                    element: '.happyWed7 .toDeail',
                    event: 'click',
                    handler: happyWed7Ctrl.getLatestPlanByCloseTerm
                }
            ];
            appFunc.bindEvents(bindings);

        },

        getLatestPlanByCloseTerm: function () {
            var closeTerm = $$(this).data("closeTerm");
            if(closeTerm == undefined || closeTerm == null || closeTerm == ""){
                xxdApp.alert("系统异常，请稍后重试", '抱歉');
                return ;
            }

            req.callJSON({
                url: "xplan/getLatestSchemeId.do",
                data: {
                    currentPage: 1,
                    closeTerm: closeTerm,
                    pageSize: 10
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if(data!=null && data.schemeInfo!=null) {
                        var planId=data.schemeInfo.SCHEMEID;
                        GS.loadPage('plan/planDetailsV2_act.html?previousPage=happywed7Webapp&planId=' + planId);
                    }
                }
            });
        }
    };
    return happyWed7Ctrl;
});