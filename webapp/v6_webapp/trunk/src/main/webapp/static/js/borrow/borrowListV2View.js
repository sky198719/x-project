define(function () {
    var borrowViewV2 = {
        init: function () {
            var html = "";
            html += "<div class='infinite-scroll-preloader'>";
            html += "<div class='preloader'></div>";
            html += "</div>";
            $$(".borrow-list-showV2").append(html);
        },

        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },

        showListItem:function(param){
            var compiledTemplate = t7.compile(param.result);
            var output = compiledTemplate({list: param.borrowList});

            if (param.type == 'push') {
                $$("#tenderListV2").append(output);
            } else {
                $$("#tenderListV2").html(output);
            }

            $$(".borrow-list-showV2 .media-list").show();
        }

    };

    return borrowViewV2;
});