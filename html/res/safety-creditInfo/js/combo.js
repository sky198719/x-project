/**
 * Created by gaoshanshan_syp on 09/04/2018.
 */
require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs"], function ($, float,track, store) {
var run=true,timeOut=null,$wrapBody = $("#J_wrapBody");
    $wrapBody.bind("scroll",scrolls);
    function scrolls() {
        var wd_st = $wrapBody.scrollTop();
        var thirdPart =$('#J_stepThird')[0].offsetTop;
        if(wd_st>=thirdPart && run){
            $('#J_thirdFirst').find('p').removeClass('shade');
            clearTimeout(timeOut);
            showStep();
            run=false;
        }else{
            return;
        }
    }
    function showStep() {
        //    当箭头显示的时候  该项高亮
        var arrowL=$('.arrow').length;
        for(var i=0;i<arrowL;i++){
            (function(item) {
                timeOut=setTimeout(function(){
                    $('.arrow').eq(item).show();
                    $('.circle-1').eq(item+1).find('p').removeClass('shade');
                    if(item==arrowL-1){
                        clearTimeout(timeOut);
                    }
                }, item*700)
            })(i);
        }
    }
   $(function(){
       scrolls();
       //动画
       addAnimate('.top-1','top-move-1');
       addAnimate('.top-2','top-move-2');
       addAnimate('.top-3','top-move-3');
       addAnimate('.top-4','top-move-4');
       addAnimate('.shield','shield-move');
       addAnimate('.lampstand','lampstand-move');
       addAnimate('.lighter','lighter-move');
      $('#J_stepThird').on('click',function () {
          window.open("/promotion/windcontrl.html");
      });
   });
    /**
     * 动画类添加
     */
    function addAnimate(selecter, className) {
        $(selecter).addClass(className);
    }
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});