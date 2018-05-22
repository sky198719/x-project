define(function () {
    var tradeTransferView = {
        init: function (params) {
        },
        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem: function (param) {
            if (param.tradeTransferList.length != 0) {
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.tradeTransferList});
                if (param.type == 'push') {
                    $("#tradeTransferList").append(output);
                } else {
                    $("#tradeTransferList").html(output);
                }
                //$$("#btenderSum").html(appFunc.fmoney(param.tenderSum, 2));
            } else {
                var html = '<div class="list-block media-list" ><ul>' +
                    '<h6 class="font-grey text-center pd10">暂无记录' +
                    '</h6></ul></div>';
                $("#tradeTransferList").html(html);
            }
        }

    };
    return tradeTransferView;
});
