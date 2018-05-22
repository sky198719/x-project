/**
 * 返回路由控制
 * Created by zhangyi on 2015/5/12.
 */
define(function () {
    var backRouter = {
         regBackRouter:function(options){
             var isBackUrl = 'backUrl' in options ? true : false;
             if(isBackUrl){
                 $$(options.element).on('click', function(){
                    GS.reloadPage(options.backUrl);
                    //mainView.router.loadPage(options.backUrl);
                 });
             } else {
                 $$(options.element).on(options.event, options.handler);
             }
         }
    };
    return backRouter;
});