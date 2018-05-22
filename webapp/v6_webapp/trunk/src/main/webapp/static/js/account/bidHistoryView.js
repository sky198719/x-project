/**
 * Created by pufei on 2015/3/2.
 */
define(function () {
    var bidHistoryView = {
        init: function (params) {
        },
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem:function(param){
            if(param.tenderList.length != 0){
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.tenderList});
                if (param.type == 'push') {
                    $$("#bidHistoryList").append(output);
                } else {
                    $$("#bidHistoryList").html(output);
                }
                $$("#btenderSum").html(appFunc.fmoney(param.tenderSum,2));
            }else{
                var html='<div class="list-block media-list" ><ul>' +
                    '<h6 class="font-grey text-center pd10">暂无记录' +
                    '</h6></ul></div>'
                $$("#bidHistoryList").html(html);
            }
        }

    };
    return bidHistoryView;
});
