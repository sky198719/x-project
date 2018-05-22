define(function () {
    var stepUpwardHistoryView = {
        init: function (params) {
        },
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem:function(param){
        	var output = "";
            if(param.tenderList.length != 0){
                var compiledTemplate = t7.compile(param.result);
                output = compiledTemplate({list: param.tenderList});
                
            }else{
                output='<li style="margin: 10px;background-color:white;"><h6 class="font-grey text-center pd10">暂无记录</h6></li>';
            }
            if ("1" == param.currentTab) {
            	if (param.type == 'push') {
                    $$("#progressingList").append(output);
                } else {
                    $$("#progressingList").html(output);
                }
            } else {
            	if (param.type == 'push') {
                    $$("#completedList").append(output);
                } else {
                    $$("#completedList").html(output);
                }
            }
        }

    };
    return stepUpwardHistoryView;
});
