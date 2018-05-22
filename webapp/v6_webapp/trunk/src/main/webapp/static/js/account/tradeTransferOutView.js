define(function () {
    var tradeTransferOutView = {
        init: function (params) {
        },
        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem: function (param) {
            if (param.tradeTransferOutList.length != 0) {
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.tradeTransferOutList});
                if (param.type == 'push') {
                    $("#tradeTransferOut").append(output);
                } else {
                    $("#tradeTransferOut").html(output);
                }
            } else {
                var html = '<div class="list-block media-list" ><ul>' +
                    '<h6 class="font-grey text-center pd10">暂无记录' +
                    '</h6></ul></div>';
                $("#tradeTransferOut").html(html);
            }
        }

    };
    return tradeTransferOutView;
});
