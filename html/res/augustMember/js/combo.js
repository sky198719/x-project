require(['base', 'float','trackBase', 'store', "dialog",'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float,track, store, dialog) {
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
    setInterval(function () {
        getData();
    }, 600000);
    var grandtimer,
        clientId='XXD_ACTIVITY_H5_PAGE',
        clientTime='',
        activityCode='Auguest-17-vip-activity';
    getData();
    function getData() {
        $.xxdAjax({
            url: '/activityCenter/activityBase/getLatest15AwardsList',
            dataType: 'json',
            clientId: "XXD_ACTIVITY_H5_PAGE",
            data    : {
                "activityCode": activityCode
            },
            type    : 'GET',
            callbacks: function (data) {
                if(data.code == "200000"){
                    if (data.data.data.list) {
                        if (data.data.data.list.length > 0) {
                            $.each(data.data.data.list, function (i, item) {
                                var time = new Date(item.addtime);
                                var hours = (time.getHours()) < 10 ? ("0" + time.getHours()) : time.getHours();
                                var getMinutes = (time.getMinutes()) < 10 ? ("0" + time.getMinutes()) : time.getMinutes();
                                var getSeconds = (time.getSeconds()) < 10 ? ("0" + time.getSeconds()) : time.getSeconds();
                                $('.getLatest15AwardsList ul').append('<li><span class="username">' + item.username + '</span><span class="time">' + hours + ':' + getMinutes + ':' + getSeconds + '</span><span class="prizename">' + item.prizename + '</span></li>');
                            });
                            if($(".getLatest15AwardsList ul li").length >= 8){
                                $('.myscroll').vTicker({
                                    speed: 500,
                                    pause: 2000,
                                    animation: 'fade',
                                    mousePause: false,
                                    showItems: 7
                                });
                            }
                        }
                    }
                }
            },
            error: function () {
                main.tip('网络异常请重试!');
            }
        })
    }
    //dialog
    $('#startgames').click(function () {
        dialog({
            content: "<div class='dimension center'>"
            + "<p>扫描二维码</p>"
            + "<p class='surprise'>移动端有大惊喜！</p>"
            + "<img src='img/dimension.png' alt='二维码'>"
            + "<div class='c_close'></div>"
            + "</div>",
            id: "",
            confirm: function (art) {
            },
            cancel: function (art) {
                art.remove();
            }
        });
    });
    var dmp_urlParam = "";
    dmp_urlParam = dmp_querystring("xxd_utm_source");
     $('#activity-step').click(function () {
         var stepHref;
         if(dmp_urlParam == "" || dmp_urlParam == null) {
             stepHref = "/step/stepDetail.html";
             $(this).attr("href",stepHref);
         }else {
             stepHref = "/step/stepDetail.html?xxd_utm_source=" + dmp_urlParam;
             $(this).attr("href",stepHref);
         }
     });
    $('#activity-xyb').click(function () {
        var xybHref;
        if(dmp_urlParam == "" || dmp_urlParam == null) {
            xybHref = "/xplan/search/list.html";
            $(this).attr("href",xybHref);
        }else {
            xybHref = "/xplan/search/list.html?xxd_utm_source=" + dmp_urlParam;
            $(this).attr("href",xybHref);
        }
    });
    $('#activity-yyp').click(function () {
        var yypHref;
        if(dmp_urlParam == "" || dmp_urlParam == null) {
            yypHref = "/promotion/yyp.html";
            $(this).attr("href",yypHref);
        }else {
            yypHref = "/promotion/yyp.html?xxd_utm_source=" + dmp_urlParam;
            $(this).attr("href",yypHref);
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
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});