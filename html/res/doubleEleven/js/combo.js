/**
 * Created by gaoshanshan_syp on 2017/10/18.
 */
require(['base', 'float','trackBase', 'store', "dialog",'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float,track, store, dialog) {
    var timeR=null;
    //循环数据

   var db={
       activeState:function activeState(o) {
           $.xxdAjax({
               url        : "/activityCenter/doubleSeriesActivityBase/getActivityStatus?activityCode=Double-11-activity",
               dataType   : "json",
               type       : "get",
               clientId  : "XXD_ACTIVITY_H5_PAGE",
               callbacks : function (r) {
                   if (r && r.code == 200000) {
                       o && o.cb(r.data.data.activityStatus);
                   }
               },
               error      : function (r) {
                   console.error(r.code);
               }

           });

       }

   };
   db.activeState({
       cb:function (result) {
           if(result===0){
               $('#J_winData ul').html('');
               allDatas();
           }
           if(result===-1){
               clearInterval(timeR);
               $('#J_winData ul').html('');
               $('#J_winData ul').html('<li class="active-w">活动未开始</li>');
           }
           if(result===1){
               clearInterval(timeR);
               $('#J_winData ul').html('');
               $('#J_winData ul').html('<li class="active-w">活动已结束</li>');
           }
       }
   });
  function allDatas() {
      $.xxdAjax({
          url: '/activityCenter/activityBase/getLatest15AwardsList',
          dataType: 'json',
          clientId: "XXD_ACTIVITY_H5_PAGE",
          data    : {
              "activityCode": 'Double-11-activity'
          },
          type    : 'GET',
          callbacks: function (data) {
              if(data.code == "200000"){
                  if (data.data.data.list) {
                      if (data.data.data.list.length > 0) {
                          var aLis = [];
                          $.each(data.data.data.list, function (i, item) {
                              var time = new Date(item.addtime);
                              var hours = (time.getHours()) < 10 ? ("0" + time.getHours()) : time.getHours();
                              var getMinutes = (time.getMinutes()) < 10 ? ("0" + time.getMinutes()) : time.getMinutes();
                              var getSeconds = (time.getSeconds()) < 10 ? ("0" + time.getSeconds()) : time.getSeconds();
                              aLis.push('<li><span class="username">' + item.username + '</span><span class="time">' + hours + ':' + getMinutes + ':' + getSeconds + '</span><span class="prizename">' + item.prizename + '</span></li>');
                          });
                          $('.winData ul').html(aLis);
                          if($(".winData ul li").length >= 6){
                              $('.myscroll').vTicker({
                                  speed: 500,
                                  pause: 2000,
                                  animation: 'fade',
                                  mousePause: false,
                                  showItems: 6
                              });
                          }
                      }
                  }
              }
          },
          error: function () {
              main.tip('网络异常请重试!');
          }
      });
      clearInterval(timeR);
      timeR = setInterval(function () {
          getData();
      }, 600000);
      getData();
      function getData() {
          $.xxdAjax({
              url: '/activityCenter/activityBase/getLatest15AwardsList',
              dataType: 'json',
              clientId: "XXD_ACTIVITY_H5_PAGE",
              data    : {
                  "activityCode": 'Double-11-activity'
              },
              type    : 'GET',
              callbacks: function (data) {
                  if(data.code == "200000"){
                      if (data.data.data.list) {
                          if (data.data.data.list.length > 0) {
                              var aLis = [];
                              $.each(data.data.data.list, function (i, item) {
                                  var time = new Date(item.addtime);
                                  var hours = (time.getHours()) < 10 ? ("0" + time.getHours()) : time.getHours();
                                  var getMinutes = (time.getMinutes()) < 10 ? ("0" + time.getMinutes()) : time.getMinutes();
                                  var getSeconds = (time.getSeconds()) < 10 ? ("0" + time.getSeconds()) : time.getSeconds();
                                  aLis.push('<li><span class="username">' + item.username + '</span><span class="time">' + hours + ':' + getMinutes + ':' + getSeconds + '</span><span class="prizename">' + item.prizename + '</span></li>');
                              });
                              $('.winData ul').html(aLis);
                          }
                      }
                  }
              },
              error: function () {
                  main.tip('网络异常请重试!');
              }
          })
      }

  }




    var dmp_urlParam = dmp_querystring("xxd_utm_source");

    if(dmp_urlParam == "" || dmp_urlParam == null) {
        $("#J_xybInfo").attr({"href":"/xplan/search/list.html","target":"_blank"});
    }else {
        $("#J_xybInfo").attr({"href":"/xplan/search/list.html?xxd_utm_source=" + dmp_urlParam,"target":"_blank"});
    }

    function dmp_querystring(item) {
        var pattern = new RegExp("[?&]"+ item +"\=([^&]+)", "g");
        var matcher = pattern.exec(location.href);
        var items = null;
        if(null != matcher){
            try{
                items = decodeURIComponent(decodeURIComponent(matcher[1]));
            }catch(e){
                try{
                    items = decodeURIComponent(matcher[1]);
                }catch(e){
                    items = matcher[1];
                }
            }
        }
        //console.log(items);
        return items;
    };


    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});