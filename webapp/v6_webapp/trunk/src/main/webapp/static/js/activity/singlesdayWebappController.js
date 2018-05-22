define(['js/utils/download'],function (download) {
    var isdow;
    var singlesdayWebappCtrl = {
        init: function (event) {
            singlesdayWebappCtrl.eventBind();
            var page = appFunc.getEventDetailPageQuery(event);
            if(page.isdow != undefined && page.isdow == 'true') {
                isdow = page.isdow;
                download.show();
            }
        },

        eventBind: function () {
            var bind = [
                {
                    element: '.singlesdayWebapp .toDeail',
                    event: 'click',
                    handler: singlesdayWebappCtrl.getLatestPlanByCloseTerm
                }
            ];
            appFunc.bindEvents(bind);
        },
        getLatestPlanByCloseTerm: function () {
            var closeTerm = $$(this).data("closeTerm");
            if (closeTerm == undefined || closeTerm == null || closeTerm == "") {
                xxdApp.alert("系统异常，请稍后重试", '抱歉');
                return;
            }
            //try{XXD_TRACK._trackEvent({category:"webapp_1111day",action:"1111day_buy_now",label:"立即抢购",value:closeTerm,custval:closeTerm+"个月新元宝"});}catch(e){}
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
                    if (data != null && data.schemeInfo != null) {
                        var planId = data.schemeInfo.SCHEMEID;
                        var html = 'plan/planDetailsV2_act.html?previousPage=singlesdayWebapp&planId=' + planId;
                        if(isdow != undefined && isdow != "") {
                            html += "&isdow=" + isdow;
                        }
                        GS.loadPage(html);
                    }
                }
            });

        }
    };
    return singlesdayWebappCtrl;
});
