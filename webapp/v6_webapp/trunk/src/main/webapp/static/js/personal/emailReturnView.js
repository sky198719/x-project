/**
 * Created by pufei on 2015/2/12.
 */
define(function(){
    var emailReturnView = {
        init: function(param){
            appFunc.bindEvents(param.bindings);
        }
    };
    return emailReturnView;
});