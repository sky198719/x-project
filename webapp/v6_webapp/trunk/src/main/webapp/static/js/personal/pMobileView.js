/**
 * Created by pufei on 2015/2/10.
 */
define(function(){
    var pMobileView = {
        init: function(param){
            appFunc.bindEvents(param.bindings);
        }
    };
    return pMobileView;
});