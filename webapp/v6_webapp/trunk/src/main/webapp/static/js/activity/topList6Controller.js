/**
 * 月度排行榜
 */
define(['js/utils/date'], function (DateHandle) {
    var type = '';
    var topListCtrl = {
        init: function (event) {
            type = appFunc.getEventDetailPageQuery(event).type;

            if (type == 'app') {
                mainView.hideNavbar();
                $$("#activity_topList").css("padding-top", "0px");
                $$("#tl6_rules").attr("src", "static/img/activity/topList/june/06_app.jpg");
            } else {
                $$("#tl-fund").show();
                $$("#tl-plan12m").show();
                $$("#tl-plan3m").show();
                $$("#tl-plan6m").show();
            }

            var bindings = [
                {
                    element: '#tl-plan3m',
                    event: 'click',
                    handler: topListCtrl.getLatest3MPlan
                },
                {
                    element: '#tl-plan6m',
                    event: 'click',
                    handler: topListCtrl.getLatest6MPlan
                },
                {
                    element: '#tl-plan12m',
                    event: 'click',
                    handler: topListCtrl.getLatest12MPlan
                },
                {
                    element: '#tl-fund',
                    event: 'click',
                    handler: topListCtrl.goFund
                }
            ];
            appFunc.bindEvents(bindings);
            topListCtrl.getTop10UserTender();

            topListCtrl.is0609();
        },

        is0609:function(){
            req.callGet({
                url:'currentDate.do',
                data: {},
                dataType: 'json',
                success:function(data){
                    var date = DateHandle.parseDate(data.currentDate);
                    var temp = topListCtrl.formatyyyyMMdd(date);
                    if('20160609' <=temp) {
                        $("#topList_plan_img1").show();
                        $("#topList_plan_img").hide();
                    }
                }
            });
        },
        formatyyyyMMdd:function(data){
            var year = data.getFullYear();
            var month = data.getMonth()+1;
            var day = data.getDate();
            return year + '' + (month < 10 ? "0" + month : month) + "" + (day < 10 ? "0" + day : day);
        },
        getTop10UserTender: function () {
            req.callJSON({
                url: "xplan/getSumTop10UserTender.do",
                data: {},
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if (data != null && data.investTop10s != null) {
                        var list = data.investTop10s;
                        var userList = [];
                        for (var k = 0; k < list.length; k++) {
                            var b = list[k];
                            var oddClass = (k % 2 == 0) ? "" : "bg_054280";
                            userList.push({
                                "no": k + 1,
                                "username": b.NICKNAME,
                                "sumaccount":appFunc.fmoney(b.SUMACCOUNT, 2),
                                "oddClass": oddClass
                            });
                        }
                        try {
                            req.callGet({
                                url: GC.getHtmlPath() + 'activity/topList6UserTenderList.html?' + GC.getVersion(),
                                dataType: 'text',
                                success: function (result) {
                                    topListCtrl.showListItem({result: result, userList: userList});
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
        },
        showListItem: function (param) {
            var compiledTemplate = t7.compile(param.result);
            var output = compiledTemplate({list: param.userList});
            $$("#tl-usertender").html(output);
            $$(".toplist").show();
        },
        getLatestPlan: function (param) {
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
                    if (data != null && data.schemeInfo != null) {
                        var planId = data.schemeInfo.SCHEMEID;
                        GS.loadPage('plan/planDetailsV2_act.html?previousPage=topList6&planId=' + planId);
                    }
                }
            });
        },
        getLatest3MPlan: function () {
            topListCtrl.getLatestPlan({closeTerm: 3});
        },
        getLatest6MPlan: function () {
            topListCtrl.getLatestPlan({closeTerm: 6});
        },
        getLatest12MPlan: function () {
            topListCtrl.getLatestPlan({closeTerm: 12});
        },
        goFund: function () {
            GS.loadPage('fund/fundUnInvested.html?path=fund');
        }
    };
    return topListCtrl;
});