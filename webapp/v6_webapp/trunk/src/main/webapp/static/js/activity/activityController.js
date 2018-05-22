/**
 * 理财能力测试
 */
define(['js/activity/activityView'], function (activityView) {
   var activityCtrl = {
       init: function() {
           var aswiper = xxdApp.swiper('#questionPage',{
               direction: 'vertical',
               parallax : true,
               pagination : '.swiper-pagination',
               onInit: function(swiper){
                   //Swiper初始化了
                   $$('.navbar').hide();
               }
           });
           /**
            * 事件定义
            *  showResult 查看结果
            * @type {*[]}
            */
           var bindings = [
               {
                   element: '#showResult',
                   event: 'click',
                   handler: activityCtrl.selectResult
               },
               {
                   element: '#fixedbg',
                   event: 'click',
                   handler: activityCtrl.fixedbgHide
               },
               {
                   element: '#gofixedbg',
                   event: 'click',
                   handler: activityCtrl.fixedbgShow
               }
           ];
           activityView.init({
                   bindings: bindings
               }
           );
       },
       selectResult:function(){
           var question1 = $$('input[name="qone"]:checked').val();
           var question2 = $$('input[name="qtwo"]:checked').val()
           var question3 = $$('input[name="qthree"]:checked').val()
           var question4 = $$('input[name="qfour"]:checked').val()
            if(question1 != null && question2 != null && question3 != null && question4 != null){
                var a=0;
                if(question1 == "A"){
                    a=a+1;
                }
                if(question2 == "A"){
                    a=a+1;
                }
                if(question3 == "A"){
                    a=a+1;
                }
                if(question4 == "A"){
                    a=a+1;
                }
                $$('#questionPage').hide();
                activityView.setAtvResult(a);
                $$('#resultPage').show();
            }else{
                xxdApp.alert('请先完成测试','提示')
            }
       },
       fixedbgHide:function(){
           $$("#fixedbg").hide();
       },
       fixedbgShow:function(){
           $$("#fixedbg").show();
       }
   };
    return activityCtrl;
});
