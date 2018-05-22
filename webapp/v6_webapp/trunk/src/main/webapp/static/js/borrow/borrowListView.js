define(function () {
    var borrowView = {
        init: function () {
            var html = "";
            html += "<div class='infinite-scroll-preloader'>";
            html += "<div class='preloader'></div>";
            html += "</div>";
            $$(".borrow-list-show").append(html);
        },

        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },

        showListItem:function(param){
            var compiledTemplate = t7.compile(param.result);
            var output = compiledTemplate({list: param.borrowList});

            if (param.type == 'push') {
                $$("#tenderList").append(output);
            } else {
                $$("#tenderList").html(output);
            }

            $$(".borrow-list-show .media-list").show();
        }

    };

    return borrowView;
});