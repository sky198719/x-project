define(['js/activity/womensDay'],function (womensDay) {
    var financesListView = {
        init: function (params) {
        },
        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem: function (param) {
            if (param.planList.length != 0) {
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.planList});
                $$(".financesList .planList").html(output);

                womensDay.financesList();
            }
        },
        showYypList:function(param){
            if (param.yypList.length != 0) {
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.yypList});
                $$(".financesList .yypList").html(output);
            }
        }
    };
    return financesListView;
});
