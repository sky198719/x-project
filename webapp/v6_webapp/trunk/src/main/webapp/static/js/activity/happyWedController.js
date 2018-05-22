/**
 * 欢乐周三
 */
define([], function () {
    var happyWedCtrl = {
        init: function () {
            var bindings = [
                {
                    element: '#plan3M',
                    event: 'click',
                    handler: happyWedCtrl.getLatest3MPlan
                },
                {
                    element: '#plan6M',
                    event: 'click',
                    handler: happyWedCtrl.getLatest6MPlan
                },
                {
                    element: '#plan12M',
                    event: 'click',
                    handler: happyWedCtrl.getLatest12MPlan
                }
            ];
            appFunc.bindEvents(bindings);
        },
        getLatest3MPlan:function () {
            happyWedCtrl.getLatestPlanByCloseTerm({closeTerm:3});
        },
        getLatest6MPlan:function () {
            happyWedCtrl.getLatestPlanByCloseTerm({closeTerm:6});
        },
        getLatest12MPlan:function () {
            happyWedCtrl.getLatestPlanByCloseTerm({closeTerm:12});
        },
        getLatestPlanByCloseTerm: function (param) {
            req.callJSON({
                url: "xplan/getLatestSchemeId.do",
                data: {
                    currentPage: 1,
                    closeTerm: param.closeTerm,
                    pageSize: 10
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if(data!=null && data.schemeInfo!=null) {
                        var planId=data.schemeInfo.SCHEMEID;
                        GS.loadPage('plan/planDetailsV2_act.html?previousPage=happywed&planId=' + planId);
                    }
                }
            });
        }
    };
    return happyWedCtrl;
});