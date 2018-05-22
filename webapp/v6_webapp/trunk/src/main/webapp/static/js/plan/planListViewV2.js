define(function () {
    var planListViewV2 = {
        init: function (params) {
        },
        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem: function (param) {
            if (param.planList.length != 0) {
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.planList});
                if (param.type == 'push') {
                    $$("#planListV2").append(output);
                } else {
                    $$("#planListV2").html(output);
                }
            } else {
                var html = '<div class="list-block media-list" ><ul>' +
                    '<h6 class="font-grey text-center pd10">暂无记录' +
                    '</h6></ul></div>';
                $$("#planListV2").html(html);
            }
        }
    };
    return planListViewV2;
});
