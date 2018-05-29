/**
 * Created by gaoshanshan_syp on 2017/9/28.
 */
require(['base', 'float','trackBase', 'store', "dialog",'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float,track, store, dialog) {
    var dataUrl={
        commonUrl:'/tradeCenter/investBiz/showStatus/',
        xybUrl:'/tradeCenter/XYB/brief'
    };

    //展示数据
    xybData();
    bbgsData();
    //循环数据
    $.xxdAjax({
        url: '/activityCenter/activityBase/getLatest15AwardsList',
        dataType: 'json',
        clientId: "XXD_ACTIVITY_H5_PAGE",
        data    : {
            "activityCode": 'October-17-vip-activity'
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
                                showItems: 5
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

    var timeR;
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
                "activityCode": 'October-17-vip-activity'
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
    // _
    function xybData() {
        $.xxdAjax({
            url      : dataUrl.xybUrl,
            type:'get',
            clientId: "XXD_FRONT_END",
            data     : {},
            dataType : 'json',
            callbacks:function (data) {
                var result;
                if (data.code == "200000"&& (result=data.data)) {
                    fnOperation(result);
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }

        });
    }
    //步步高升数据
    function bbgsData() {
        $.xxdAjax({
            url      : dataUrl.commonUrl+'BBGS',
            type:'get',
            clientId: "XXD_INTEGRATION_PLATFORM",
            data     : {},
            dataType : 'json',
            callbacks:function (data) {
                var result;
                if (data.code == "200000" && (result=data.data.productInfo)) {
                    $("#J_bbgsApr").html(result.plannedAnnualRateFrom);
                    $("#J_bbgsfloatApr").html(result.plannedAnnualRateTo);
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }

        });
    }
    function fnOperation(data) {
        var array = data.items,xybFrom,xybTo,xybAprFrom,xybAprTo;
        xybFrom= getNvalueByCode(array,1);
        xybTo=  getNvalueByCode(array,36);
        xybAprFrom=xybFrom.plannedAnnualRateFrom;
        xybAprTo=xybTo.plannedAnnualRateFrom;
        if(xybFrom.floatingRate){
            xybAprFrom=(xybAprFrom*10+xybFrom.floatingRate*10)/10;
        }
        if(xybAprTo.floatingRate){
            xybAprTo=(xybAprTo*10+xybTo.floatingRate*10)/10;
        }
        $("#J_xybForm").html(xybAprFrom);
        $("#J_xybTo").html(xybAprTo);
    }

    function getNvalueByCode(array, leastPeriod) {
        if (!array || !(array instanceof Array)) {
            return 0;
        }
        var length = array.length;
        for (var i = 0; i < length; i++) {
            if(array[i].leastPeriod == leastPeriod) {
                return array[i];
            }
        }
        return 0;
    }
    //布玛及跳转链接
    var dmp_urlParam = "";
    dmp_urlParam = dmp_querystring("xxd_utm_source");
    $('#J_xyb').on('click',function () {
        if(dmp_urlParam == "" || dmp_urlParam == null) {
            window.location.href = "/xplan/search/list.html";
        }else {
            window.location.href = "/xplan/search/list.html?xxd_utm_source=" + dmp_urlParam;
        }
    });
    $('#J_bbgs').on('click',function () {
        if(dmp_urlParam == "" || dmp_urlParam == null) {
            window.location.href = "/step/stepDetail.html";
        }else {
            window.location.href = "/step/stepDetail.html?xxd_utm_source=" + dmp_urlParam;
        }
    });
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