define(function () {
    var planListView = {
        init: function (params) {
        },
        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem: function (param) {
            if (param.planList.length != 0) {
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.planList});
                if (param.closeTerm == 3) {
                    if (param.type == 'push') {
                        $$("#closeTerm3Content").append(output);
                    } else {
                        $$("#closeTerm3Content").html(output);
                    }
                } else if (param.closeTerm == 6) {
                    if (param.type == 'push') {
                        $$("#closeTerm6Content").append(output);
                    } else {
                        $$("#closeTerm6Content").html(output);
                    }
                } else if (param.closeTerm == 12) {
                    if (param.type == 'push') {
                        $$("#closeTerm12Content").append(output);
                    } else {
                        $$("#closeTerm12Content").html(output);
                    }
                }
            } else {
                var html = '<div class="list-block media-list" ><ul>' +
                    '<h6 class="font-grey text-center pd10">暂无记录' +
                    '</h6></ul></div>';
                if (param.closeTerm == 3) {
                    $$("#closeTerm3Content").html(html);
                } else if (param.closeTerm == 6) {
                    $$("#closeTerm6Content").html(html);
                } else if (param.closeTerm == 12) {
                    $$("#closeTerm12Content").html(html);
                }
            }
        }
    };
    return planListView;
});
