/**
 * Created by gaoshanshan_syp on 2017/11/20.
 */
require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float, track, store) {
    var dataUrl= {
        activityCode:'friday-activity',
        allPrizesUrl:'/activityCenter/fridayActivityBase/getLatestAwardsList', // 所有的中奖列表
        getActivityStatus:'/activityCenter/fridayActivityBase/getActivityStatus',   //活动状态
        getLotteryPeopleAmount:'/activityCenter/fridayActivityBase/getLotteryPeopleAmount', //查询活动中抽过红包的人数
    };

    var ux= {
        allPrizes:function() {
            db.getPrize({
                cb:function(r) {
                    var aLis = '';
                    $.each(r, function (i, item) {
                        aLis+='<li><span class="username">' + item.username + '</span><span class="prizename">' + item.prizename + '</span></li>';
                    });
                    $('#J_winData ul').html(aLis);
                    if($("#J_winData ul li").length >= 5){
                        $('.myscroll').vTicker({
                            speed: 500,
                            pause: 2000,
                            animation: 'fade',
                            mousePause: false,
                            showItems: 5
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
                    if (r === -1 || r === 1) {
                        $("#J_winData ul").html('<li style="width: 100%;text-align: center">周五09:00开始</li>');
                        if(!$("#J_showPeople").hasClass('disnone')){
                            $("#J_people").addClass('disnone');
                        }
                    }

                }
            });
        },
        getPeoplePrizes:function () {
            db.getPeopleAmount({
                cb:function (r) {
                    if(r>0){
                        $("#J_people").html(r);
                        $("#J_showPeople").removeClass("disnone");
                    }
                }
            })
        },
       activityStart:function () {
           db.activeState({
               cb:function (r) {
                   if(r===0){
                       ux.getPeoplePrizes();
                       ux.allPrizes();
                       ux.flop();
                   }else{
                       db.isTime({
                          cb:function (d) {
                              var oNow     = new Date(d);
                              var oStart= new Date(oNow.getFullYear(), oNow.getMonth(), oNow.getDate(),9, 0, 0);
                              var oEnd=new Date(oNow.getFullYear(), oNow.getMonth(), oNow.getDate(),23, 30, 0);

                              var autoStart=oStart-oNow;
                              var autoEnd=oNow-oEnd;
                              if(autoStart>=0){
                                  var timeStart=setTimeout(function () {
                                      ux.activityStart();
                                      clearTimeout(timeStart);
                                  },autoStart)

                              }else if(autoEnd>=0){
                                  var timeEnd=setTimeout(function () {
                                      ux.activityStart();
                                      clearTimeout(timeEnd);
                                  },autoEnd)
                              }

                          }

                       })
                   }
               }
           });

       },

        flop:function () {
            //  翻牌
            $("#J_start").find('li .shift-front a').removeClass('btn-end');
            $("#J_start").on('mouseenter','li',function (ev) {
                var $front = $(this).find('.shift-front'), $behind = $(this).find('.shift-behind'), speed =100, dis = 175;
                $front.animate({left: dis / 2, width: 0}, speed, function () {
                    $front.hide();
                    $behind.show().animate({left: 0, width: dis}, speed);
                });
            });
            $("#J_start").on('mouseleave','li',function (ev) {
                var $front = $(this).find('.shift-front'), $behind = $(this).find('.shift-behind'), speed =100, dis = 175;
                $behind.animate({left: dis / 2, width: 0}, speed, function () {
                    $behind.hide();
                    $front.show().animate({left: 0, width: dis}, speed);
                });
            });
        }


    };
    //
    var db={
        //   轮播数据
        getPrize:function (o) {
            $.xxdAjax({
                url: dataUrl.allPrizesUrl,
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                data: {
                    "activityCode": dataUrl.activityCode
                },
                type: 'GET',
                callbacks: function (data) {
                    if (data.code == "200000") {
                        if (data.data.prize!='') {
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
                        o && o.cb(r.data.data.activityStatus)
                    }
                },
                error      : function (r) {
                    main.tip(r.code);
                }
            });
        },
        //已经抽过奖的人数
        getPeopleAmount:function(o) {
            $.xxdAjax({
                url: dataUrl.getLotteryPeopleAmount,
                contentType: 'application/json',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                type: 'get',
                callbacks: function (r) {
                    if (r && r.code == 200000) {
                        o.cb && o.cb(r.data.data);
                    }
                },
                error: function (r) {
                    main.tip('网络异常请重试!');
                }

            })

        },
        isTime:function (o) {
            var time = null;
            $.ajax({
                url     : '/feapi/currentTime',
                type    : 'get',
                dataType: 'json',
                success : function (r) {
                    if (r.code == 200 && r.data.currentTime) {
                        time = r.data.currentTime;
                        o && o.cb(time);
                    }else{
                        float.alert({content:'网络问题，请重新尝试！'});
                    }
                },
                error   : function (r) {
                    $.error(r);
                }
            });
        }

    };
    $(function(){
       ux.oActiveState();
       // ux.activityStatus();
       ux.activityStart();


    });





    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});