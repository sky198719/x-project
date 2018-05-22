/**
 * Created by gaoshanshan_syp on 2017/11/28.
 */
require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float, track, store) {

    var dataUrl= {
        activityCode:'December-17-vip-activity',
        allPrizesUrl:'/activityCenter/activityBase/getLatestMaterailAwardsList', // 所有的中奖列表
        getActivityStatus:'/activityCenter/activityBase/getActivityStatus',   //活动状态
    };
    var item=null;
   var ux={
       allPrizes:function() {
           db.getPrize({
               cb:function(r) {
                   var aLis = '';
                   $.each(r, function (i, item) {
                       aLis+='<li><span class="username">' + item.username + '</span><span class="prizename">' + item.prizename + '</span></li>';
                   });
                   $('#J_winData ul').html(aLis);
                   if($("#J_winData ul li").length >= 8){
                       $('.myscroll').vTicker({
                           speed: 500,
                           pause: 2000,
                           animation: 'fade',
                           mousePause: false,
                           showItems: 8
                       });
                   }
               }

           });
           function getAllPrizes() {
               db.getPrize({
                   cb:function(r) {
                       var aLis = '';
                       $.each(r, function (i, item) {
                           aLis+='<li><span class="username">' + item.username + '</span><span class="prizename">' + item.prizename + '</span></li>';
                       });
                       $('#J_winData ul').html(aLis);
                   }

               })
           }
           getAllPrizes();
           var timeR=null;
           clearInterval(timeR);
           timeR = setInterval(function () {
               getAllPrizes();
           }, 600000);
       },
       //活动状态
       oActiveState:function () {
           db.activeState({
               cb: function (r) {
                   if (r === 0) {
                       ux.allPrizes();
                       ux.choiceClick();
                   }
                   if (r === -1) {
                       $("#J_winData ul").html('<li style="width: 100%;text-align: center">活动未开始</li>');
                   }
                   if (r === 1) {
                       $("#J_winData ul").html('<li style="width: 100%;text-align: center">活动已结束</li>');
                   }

               }
           });
       },
       choiceClick:function () {
         $("#J_choice").on('click','li',function(){
             item=$(this);
         $(this).find('.choices').removeClass('dishidden');
           var timer=setTimeout(function () {
               $('#J_popup').show();
           },700)
         })
       }
   };
    var db={
        //   轮播数据
        getPrize:function (o) {
            $.xxdAjax({
                url: dataUrl.allPrizesUrl,
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                data: {
                    "activityCode": dataUrl.activityCode,
                    "recordNum":189
                },
                type: 'GET',
                callbacks: function (data) {
                    if (data.code == "200000") {
                        if (data.data.prize) {
                            var dataList=data.data.prize;
                            o && o.cb(dataList);
                        }else{
                            $("#J_winData ul").html('<li style="width: 100%;text-align: center">暂无数据</li>')
                        }
                    }
                },
                error: function () {
                    main.tip('网络异常请重试!');
                }
            });
        },
        // 活动状态
        activeState: function (o) {
            $.xxdAjax({
                url        : dataUrl.getActivityStatus+'?activityCode='+dataUrl.activityCode,
                dataType   : "json",
                clientId   : "XXD_ACTIVITY_H5_PAGE",
                type       : "get",
                callbacks : function (r) {
                    if (r && r.code == 200000) {
                        o && o.cb(r.data.data.activityStatus);
                    }
                },
                error      : function (r) {
                    main.tip(r.code);
                }
            });
        },
    }


    $(function(){
        ux.oActiveState();
        //关闭弹窗
        $('#J_close').on('click',function(ev){
            var ev=ev || event;
            $('#J_popup').hide();
            item.find('.choices').addClass('dishidden');
            ev.stopPropagation();
        })
    });


    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});