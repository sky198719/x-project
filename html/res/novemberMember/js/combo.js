/**
 * Created by gaoshanshan_syp on 2017/11/3.
 */
require(['base', 'float','trackBase', 'store', "dialog",'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float,track, store, dialog) {
    var dataUrl={
        activeUrl:"/activityCenter/activityBase/getActivityStatus",
        activityCode:"November-17-vip-activity"
    };
    var timeR=null;
    var db={
        activeState:function activeState(o) {
            $.xxdAjax({
                url        : dataUrl.activeUrl+'?activityCode='+dataUrl.activityCode,
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
                $('.winData ul').html('');
                allDatas();
            }
            if(result===-1){
                clearInterval(timeR);
                $('.winData ul').html('');
                $('.winData ul').html('<li class="active-w" style="text-align: center">活动未开始</li>');
            }
            if(result===1){
                clearInterval(timeR);
                $('.winData ul').html('');
                $('.winData  ul').html('<li class="active-w" style="text-align: center">活动已结束</li>');
            }
        }
    });
    function allDatas() {
        //循环数据
        $.xxdAjax({
            url: '/activityCenter/activityBase/getLatestAwardsList',
            dataType: 'json',
            clientId: "XXD_ACTIVITY_H5_PAGE",
            data    : {
                "activityCode": 'November-17-vip-activity'
            },
            type    : 'GET',
            callbacks: function (data) {
                if(data.code == "200000"){
                    if (data.data.prize) {
                        if (data.data.prize.length > 0) {
                            var aLis = [];
                            $.each(data.data.prize, function (i, item) {
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
                    }else{
                        $('.winData ul').html('<li class="active-w" style="text-align: center">暂无数据</li>');
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
                url: '/activityCenter/activityBase/getLatestAwardsList',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                data    : {
                    "activityCode": 'November-17-vip-activity'
                },
                type    : 'GET',
                callbacks: function (data) {
                    if(data.code == "200000"){
                        if (data.data.prize) {
                            if (data.data.prize.length > 0) {
                                var aLis = [];
                                $.each(data.data.prize, function (i, item) {
                                    var time = new Date(item.addtime);
                                    var hours = (time.getHours()) < 10 ? ("0" + time.getHours()) : time.getHours();
                                    var getMinutes = (time.getMinutes()) < 10 ? ("0" + time.getMinutes()) : time.getMinutes();
                                    var getSeconds = (time.getSeconds()) < 10 ? ("0" + time.getSeconds()) : time.getSeconds();
                                    aLis.push('<li><span class="username">' + item.username + '</span><span class="time">' + hours + ':' + getMinutes + ':' + getSeconds + '</span><span class="prizename">' + item.prizename + '</span></li>');
                                });
                                $('.winData ul').html(aLis);
                            }
                        }else{
                            $('.winData ul').html('<li class="active-w" style="text-align: center">暂无数据</li>');
                        }
                    }
                },
                error: function () {
                    main.tip('网络异常请重试!');
                }
            })
        }
    }


    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});