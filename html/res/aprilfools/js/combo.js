require(['base', 'float','trackBase', 'store', "dialog",'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float,track, store, dialog) {
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
    setInterval(function () {
        getData();
    }, 600000);
    var luckytimer;
    getData();
    function getData() {
        //lucky
        $.ajax({
            url: "/biz/activity/luckyList",
            contentType: "application/json",
            dataType: "json",
            type: "get",
            beforeSend: function (request) {
                request.setRequestHeader("s", "www");
                request.setRequestHeader("clientId", "001");
                request.setRequestHeader("clientTime", "001");
            },
            success: function (data) {
                var data = data.data;
                if (data.length > 0) {
                    $.each(data, function (i, item) {
                        $('.lucky ul').append("<li><span class='span1'>" + item.user + "</span><span class='span2'>" + item.time + "</span><span>" + item.reward + "</span></li>");
                    });
                    clearInterval(luckytimer);
                    var index = 0;
                    luckytimer = setInterval(function () {
                        if (index >= data.length - 6) {
                            index = 0;
                            $('.lucky ul').css({'top': 0 + 'px'});
                        } else {
                            index++;
                            $('.lucky ul').animate({'top': -52 * index + 'px'}, 200);
                        }
                    }, 2000);
                }
            },
            error: function (data) {
            }
        });
        //ranking
        $.ajax({
            url: "/biz/activity/topList",
            contentType: "application/json",
            dataType: "json",
            type: "get",
            beforeSend: function (request) {
                request.setRequestHeader("s", "www");
                request.setRequestHeader("clientId", "001");
                request.setRequestHeader("clientTime", "001");
            },
            success: function (data) {
                var data = data.data;
                $('.ranking ul').html('');
                $.each(data, function (i, item) {
                    $('.ranking ul').append("<li><span class='span1'>" + (i + 1) + "</span><span class='span3'>" + item.user + "</span><span>" + item.amount + "</span></li>");
                });
            },
            error: function (data) {
            }
        })
    }
    //dialog
    $('.mask').click(function () {
        dialog({
            content: "<div class='dimension center'>"
            + "<p>扫描二维码  揭开神秘面具</p>"
            + "<img src='img/dimension.png' alt='二维码'>"
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
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});