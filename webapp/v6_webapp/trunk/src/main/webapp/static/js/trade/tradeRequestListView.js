define(['js/trade/tradeRequestListModel'], function (tradeRequestListModel) {

    var tradeRequestListView = {
        init: function () {
            var html = "";
            html += "<div class='infinite-scroll-preloader'>";
            html += "<div class='preloader'></div>";
            html += "</div>";
            $$(".tradeRequest-list-show").append(html);
        },

        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },

        showListItem: function (param) {
            var compiledTemplate = t7.compile(param.result);
            var output = compiledTemplate({list: param.tradeRequestList});
            if (param.type == 'push') {
                $$("#tradeRequestList").append(output);
            } else {
                $$("#tradeRequestList").html(output);
            }

            $$(".tradeRequest-list-show .media-list").show();
        }

    };

    return tradeRequestListView;
});