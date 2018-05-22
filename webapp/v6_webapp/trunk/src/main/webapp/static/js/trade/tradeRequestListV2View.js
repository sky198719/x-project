define(['js/trade/tradeRequestListModel'], function (tradeRequestListModel) {

    var tradeRequestListView = {
        init: function () {
            var html = "";
            html += "<div class='infinite-scroll-preloader'>";
            html += "<div class='preloader'></div>";
            html += "</div>";
            $$(".tradeRequest-listV2-show").append(html);
        },

        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },

        showListItem: function (param) {
            var compiledTemplate = t7.compile(param.result);
            var output = compiledTemplate({list: param.tradeRequestList});
            if (param.type == 'push') {
                $$("#tradeRequestListV2").append(output);
            } else {
                $$("#tradeRequestListV2").html(output);
            }

            $$(".tradeRequest-listV2-show .media-list").show();
        }

    };

    return tradeRequestListView;
});