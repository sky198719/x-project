define(function(){
    var securityView = {
        init: function(param){
            appFunc.bindEvents(param.bindings);
        }
    };
    return securityView;
});