define(function () {
    var monthFinanceHistoryView = {
        init: function (params) {
        },
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem:function(param){
        	var output = "";
            if(param.listShow.length != 0){
                var compiledTemplate = t7.compile(param.result);
                output = compiledTemplate({list: param.listShow});
                
            }else{
            	if (1 == param.tab) {
	            	output='<li style="margin: 10px;background-color:white;"><h6 class="font-grey text-center pd10">没有新公告</h6></li>';
	            } else {
	            	output='<li style="margin: 10px;background-color:white;"><h6 class="font-grey text-center pd10">没有新活动</h6></li>';
	            }
                
            }
            if (1 == param.tab) {
            	if (param.type == 'push') {
                    $$("#announceList").append(output);
                } else {
                    $$("#announceList").html(output);
                }
            } else {
            	if (param.type == 'push') {
                    $$("#activityList").append(output);
                } else {
                    $$("#activityList").html(output);
                }
            }
        }

    };
    return monthFinanceHistoryView;
});
