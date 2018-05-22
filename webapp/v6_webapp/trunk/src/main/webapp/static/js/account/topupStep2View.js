define(function() {
    var topupStep2View = {
        init: function (params) {
            appFunc.bindEvents(params.bindings);
        },
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem:function(param){
            if(param.userBankList!=null && param.userBankList.length != 0){
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.userBankList});
                $$("#userBankList").html(output);
                $$(".media-list").show();
            }else{
                var html='<div class="list-block media-list" >' +
                    '<h6 class="font-red text-center pd10">亲！您还没有绑定银行卡呢！赶快去添加吧' +
                    '</h6><h6 class="text-center pd10"><a href="" id="topupTobank">点击添加银行卡</a></h6></div>'
                $$("#userBankList").html(html);
                $$(".media-list").show();
            }
        }
    };
    return topupStep2View;
});
