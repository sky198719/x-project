define(function(){
    var personalView = {
        init: function(param){
            appFunc.bindEvents(param.bindings);
        }
    };
    return personalView;
});