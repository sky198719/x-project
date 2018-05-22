/**
 * Created by gaoshanshan_syp on 02/01/2018.
 */
require(['base', 'float','trackBase', 'store', 'header', 'footer', 'backTop', "requirejs","allRoll"], function ($, float,track, store) {
    var dataUrl= {
        activityUrl: '/activityCenter/activityBase/getActivityStatus',
        activityCode:'January-18-vip-activity',
        getLatestAwardsList:'/activityCenter/activityBase/getLatestMaterailAwardsList'
    };
    var timeR=null,timer=null;
    var db={
        activeState:function activeState(o) {
            $.xxdAjax({
                url        : dataUrl.activityUrl+'?activityCode='+dataUrl.activityCode,
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

        },
        //   轮播数据
        getPrize:function (o) {
            $.xxdAjax({
                url: dataUrl.getLatestAwardsList+'?activityCode='+dataUrl.activityCode,
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                type    : 'GET',
                callbacks: function (data) {
                    if (data.code == "200000") {
                        if (data.data.prize && data.data.prize.length > 0) {
                                var dataList=data.data.prize;
                                o && o.cb(dataList);
                        }else{
                            $('#J_winnerLists').addClass("active-w");
                            $("#J_winnerLists").html('<li>暂无数据</li>');
                        }
                    }
                },
                error: function () {

                    main.tip('网络异常请重试!');
                }
            });
        },

    };
    var ux={
        oActiveState:function () {
            db.activeState({
                cb:function (result) {
                    if(result===0){
                        $('#J_winnerLists').removeClass("active-w");
                        $('#J_winnerLists').html('');
                        $("#J_rules").addClass('rules-rock');
                        $("#J_person").addClass('person-rock');
                        ux.allPrizes();
                        ux.personClick();
                    }
                    if(result===-1){
                        clearInterval(timeR);
                        $('#J_winnerLists').html('');
                        $('#J_winnerLists').addClass("active-w");
                        $('#J_winnerLists').html('<li>活动未开始</li>');
                    }
                    if(result===1){
                        clearInterval(timeR);
                        $('#J_winnerLists').html('');
                        $('#J_winnerLists').addClass("active-w");
                        $('#J_winnerLists').html('<li>活动已结束</li>');
                    }
                }
            });
        },
        allPrizes:function() {
            db.getPrize({
                cb:function(r) {
                    var aLis = '';
                    $.each(r, function (i, item) {
                        aLis+='<li><span class="username">' + item.username + '</span><span class="prizename">' + item.prizename + '</span></li>';
                    });
                    $('#J_winnerLists').html(aLis);
                    if($("#J_winnerLists li").length >= 3){
                        $('#J_WinData').marquee({
                            direction:'left',
                            speed:'20'
                        })
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
                        $('#J_winnerLists').html(aLis);
                    }

                })
            }
            getAllPrizes();
            clearInterval(timeR);
            timeR = setInterval(function () {
                getAllPrizes();
            }, 600000);
        },

        personClick:function () {
            $(document).on('click','#J_person,#J_rules',function(){
                timer=setTimeout(function () {
                    $('#J_popup').show();
                },300)
            })
        }
    };

    $(function () {
        ux.oActiveState();
        $('#J_close').on('click',function(ev){
            var ev=ev || event;
            clearInterval(timer);
            $('#J_popup').hide();
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