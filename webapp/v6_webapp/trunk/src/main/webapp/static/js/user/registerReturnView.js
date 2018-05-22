define(function(){
    var registerView = {
        init: function(param){
            appFunc.bindEvents(param.bindings);
        }
    };
    return registerView;
});