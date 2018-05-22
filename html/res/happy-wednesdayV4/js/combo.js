/**
 * Created by gaoshanshan_syp on 2018/1/5.
 */
require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float, track, store) {

    var dataUrl={
        xybUrl:'/activityCenter/doubleDanActivityBase/getXYBSchemeByTerm'      //加息标和普通标接口
    };
    function start() {
        isTime({
            success: function (r) {
                var oDay = new Date(r).getDay(), oTime = new Date(r).getHours();
                if (oDay == 3 && oTime >= 9) {
                    $("#J_leftBtn,#J_rightBtn").removeClass('overtime-btn').addClass('on-btn');
                    //翻牌
                    // $(document).on("mouseenter", "div.flip", function () {
                    //     var $front = $(this).find('.front'), $behind = $(this).find('.behind'), speed = 80, dis = 516;
                    //     $front.animate({left: dis / 2, width: 0}, speed, function () {
                    //         $front.hide();
                    //         $behind.show().animate({left: 0, width: dis}, speed);
                    //     });
                    // });
                    // $(document).on("mouseleave", "div.flip", function () {
                    //     var $front = $(this).find('.front'), $behind = $(this).find('.behind'), speed = 80, dis = 516;
                    //     $behind.animate({left: dis / 2, width: 0}, speed, function () {
                    //         $behind.hide();
                    //         $front.show().animate({left: 0, width: dis}, speed);
                    //     });
                    // });
                } else {
                    var oNow     = new Date(r);
                    var autoTime = new Date(oNow.getFullYear(), oNow.getMonth(), oNow.getDate(),9, 0, 0) - new Date(r);
                    if (autoTime >= 0) {
                        var timer = setTimeout(function () {
                            start();
                            clearTimeout(timer);
                        }, autoTime);
                    }
                }
            }
        });
    }
    var db={
      xybId:function (term,o) {
          $.xxdAjax({
              url      : dataUrl.xybUrl,
              type:'get',
              clientId: "XXD_ACTIVITY_H5_PAGE",
              data     : {
                  term:term
              },
              dataType : 'json',
              callbacks:function (data) {
                  if(data.code=="200000"){
                      o && o.cb(data);
                  }
              },
              error:function () {
                  alert('网络异常请重试！');
              }

          });
      }
    };
     $(function () {
         start();
         $(document).on('click','.on-btn:eq(0)',function () {
             db.xybId(6,{
                 cb:function (r) {
                     if(r.data && r.data.scheme){
                         if(r.data.scheme!=-1){
                             location.href='/xplan/detail/'+r.data.scheme+'.html';
                         }else{
                             float.alert({content:'来晚啦，新元宝已经抢完啦'});
                         }
                     }
                 }
             })
         });
         $(document).on('click','.on-btn:eq(1)',function () {
             db.xybId(12,{
                 cb:function (r) {
                     if(r.data && r.data.scheme){
                         if(r.data.scheme!=-1){
                             location.href='/xplan/detail/'+r.data.scheme+'.html';
                         }else{
                             float.alert({content:'来晚啦，新元宝已经抢完啦'});
                         }
                     }
                 }
             })
         });
     });
    function isTime(o) {
        var time = null;
        $.ajax({
            url     : '/feapi/currentTime',
            type    : 'get',
            dataType: 'json',
            success : function (r) {
                if (r.code == 200 && r.data.currentTime) {
                    time = r.data.currentTime;
                    o.success(time);
                }else{
                    float.alert({content:'网络问题，请重新尝试！'});
                }
            },
            error   : function (r) {
                $.error(r);
            }
        });
    }
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});