define(function () {
    var tradeTransferingView = {
        init: function (params) {
        },
        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem: function (param) {
            if (param.tradeTransferingList.length != 0) {
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.tradeTransferingList});
                if (param.type == 'push') {
                    $("#tradeTransfering").append(output);
                } else {
                    $("#tradeTransfering").html(output);
                }
            } else {
                var html = '<div class="list-block media-list" ><ul>' +
                    '<h6 class="font-grey text-center pd10">暂无记录' +
                    '</h6></ul></div>';
                $("#tradeTransfering").html(html);
            }
        }

    };
    return tradeTransferingView;
});
