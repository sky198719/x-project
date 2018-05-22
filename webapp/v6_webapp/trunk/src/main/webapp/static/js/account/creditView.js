/**
 * Created by pufei on 2015/3/6.
 */
define(function () {
    var creditView = {
        init: function (params) {
        },
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem:function(param){
            if(param.creditList.length != 0){
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.creditList});
                if (param.type == 'push') {
                    $$("#creditList").append(output);
                } else {
                    $$("#creditList").html(output);
                }

            }else{
                var html='<div class="list-block media-list" ><ul>' +
                    '<h6 class="font-grey text-center pd10">暂无记录' +
                    '</h6></ul></div>'
                $$("#creditList").html(html);
            }
        }
    };
    return creditView;
});

