/**
 * Created by pufei on 2015/4/2.
 */
define(function(){
    var bankView = {
        init: function(){
        },
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        appendBank:function(param){
            if(param.banklist.length != 0 ){
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.banklist});
                $$('#bankList').html(output);
            }else{
                var html='<h6 class="font-grey text-center pd10">' +
                    '暂无银行卡' +
                    '</h6>'
                $$('#bankList').html(html);
            }
        }
    };
    return bankView;
});