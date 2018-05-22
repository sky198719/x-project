/**
 * Created by wangzhijie on 22/01/2018.
 */
require(['base', 'float','trackBase', 'store', 'header', 'footer', 'backTop', "requirejs","allRoll"], function ($, float,track, store) {
    var dataUrl= {
        activityUrl: '/activityCenter/activityBase/getActivityStatus',
        activityCode:'February-18-vip-activity',
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
                url: dataUrl.getLatestAwardsList+'?activityCode='+dataUrl.activityCode + '&recordNum=189',
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
                        // $("#J_rules").addClass('rules-rock');
                        // $("#J_person").addClass('person-rock');
                        ux.allPrizes();
                        // ux.personClick();
                    }
                    if(result===-1){
                        $("#lotteryStart").removeClass("drawShake");
                        clearInterval(timeR);
                        $('#J_winnerLists').html('');
                        $('#J_winnerLists').addClass("active-w");
                        $('#J_winnerLists').html('<li>活动未开始</li>');
                    }
                    if(result===1){
                        $("#lotteryStart").removeClass("drawShake");
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

        // personClick:function () {
        //     // $(document).on('click','#J_person,#J_rules',function(){
        //     //     timer=setTimeout(function () {
        //     //         $('#J_popup').show();
        //     //     },300)
        //     // })
        // }
    };

    //翻牌
    $(document).on("mouseenter", ".mask", function () {
        var $this = $(this);
        db.activeState({
            cb:function (result) {
                if(result===0){
                    $("#lotteryStart").removeClass("drawShake");
                    var $front = $this.siblings('.front'), $behind = $this.siblings('.behind'), speed = 80, dis = 470;
                    $front.stop().animate({left: dis / 2, width: 0}, speed, function () {
                        $front.hide();
                        $behind.show().stop().animate({left: 0, width: dis}, speed);
                    });
                }
            }
        });
    });
    $(document).on("mouseleave", ".mask", function () {
        var $this = $(this);
        db.activeState({
            cb:function (result) {
                if(result===0){
                    var $front = $this.siblings('.front'), $behind = $this.siblings('.behind'), speed = 80, dis = 470;
                    $behind.stop().animate({left: dis / 2, width: 0}, speed, function () {
                        $behind.hide();
                        $front.show().stop().animate({left: 0, width: dis}, speed);
                        $("#lotteryStart").addClass("drawShake");
                    });
                }
            }
        });
    });

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