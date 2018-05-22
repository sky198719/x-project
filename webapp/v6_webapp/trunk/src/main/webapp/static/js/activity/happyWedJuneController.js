/**
 * 六月欢乐周三
 */
define(['js/utils/date'], function (DateHandle) {
    var happyWedJuneCtrl = {
        init: function () {
            var bindings = [
                {
                    element: '#hw6-plan3M',
                    event: 'click',
                    handler: happyWedJuneCtrl.getLatest3MPlan
                },
                {
                    element: '#hw6-plan6M',
                    event: 'click',
                    handler: happyWedJuneCtrl.getLatest6MPlan
                },
                {
                    element: '#hw6-plan12M',
                    event: 'click',
                    handler: happyWedJuneCtrl.getLatest12MPlan
                }
            ];
            appFunc.bindEvents(bindings);

            happyWedJuneCtrl.is0609();
        },

        is0609:function(){
            req.callGet({
                url:'currentDate.do',
                data: {},
                dataType: 'json',
                success:function(data){
                    var date = DateHandle.parseDate(data.currentDate);
                    var temp = happyWedJuneCtrl.formatyyyyMMdd(date);
                    if('20160609' <=temp) {
                        $("#happywedJune_img1").show();
                        $("#happywedJune_img").hide();
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
        getLatest3MPlan:function () {
            happyWedJuneCtrl.getLatestPlanByCloseTerm({closeTerm:3});
        },
        getLatest6MPlan:function () {
            happyWedJuneCtrl.getLatestPlanByCloseTerm({closeTerm:6});
        },
        getLatest12MPlan:function () {
            happyWedJuneCtrl.getLatestPlanByCloseTerm({closeTerm:12});
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
                        GS.loadPage('plan/planDetailsV2_act.html?previousPage=happywedJune&planId=' + planId);
                    }
                }
            });
        }
    };
    return happyWedJuneCtrl;
});