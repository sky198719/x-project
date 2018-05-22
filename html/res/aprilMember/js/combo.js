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
        activityCode='April-17-vip-activity';
    getData();
    function getData() {
        clientTime = new Date().getTime();
        $.ajax({
            url: '/activityCenter/activityBase/getLatest15AwardsList',
            contentType: "application/json",
            dataType: "json",
            type: "get",
            beforeSend: function (request) {
                request.setRequestHeader("clientId", clientId);
                request.setRequestHeader("clientTime", clientTime);
            },
            data: {
                'activityCode': activityCode,
            },
            success: function (data) {
                if(data.data.data){
                    if (data.data.data.list.length > 0) {
                        $.each(data.data.data.list, function (i, item) {
                            var time = item.addtime.substring(0, 10);
                            $('.getLatest15AwardsList ul').append("<li><span>" + item.username + "</span><span>" + time + "</span><span>" + item.prizename + "</span></li>");
                        });
                        clearInterval(grandtimer);
                        var index = 0;
                        grandtimer = setInterval(function () {
                            if (index >= data.data.data.list.length - 6) {
                                index = 0;
                                $('.getLatest15AwardsList ul').animate({'scrollTop': 0}, 400);
                            } else {
                                index++;
                                $('.getLatest15AwardsList ul').animate({'top': -48 * index + 'px'}, 200);
                            }
                        }, 2000);
                    }
                }
            },
            error: function () {

            }
        })
    }
    //dialog
    $('.startgames').click(function () {
        dialog({
            content: "<div class='dimension center'>"
            + "<img src='img/dimension.png' alt='二维码'>"
            + "<p>扫描二维码  开始参与套圈</p>"
            + "<div class='c_close'></div>"
            + "</div>",
            id: "",
            confirm: function (art) {
            },
            cancel: function (art) {
                art.close();
            }
        });
    });
    var dmp_urlParam = "";
    dmp_urlParam = dmp_querystring("xxd_utm_source");
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
     $('#activity-rry').click(function () {
         var rryHref;
         if(dmp_urlParam == "" || dmp_urlParam == null) {
             rryHref = "/fund/buyFund.html";
             $(this).attr("href",rryHref);
         }else {
             rryHref = "/fund/buyFund.html?xxd_utm_source=" + dmp_urlParam;
             $(this).attr("href",rryHref);
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

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});