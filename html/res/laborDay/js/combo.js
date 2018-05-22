require(['base', 'float','trackBase', 'store', "dialog",'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float,track, store, dialog) {
    $('#J_start').click(function () {
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
    setInterval(function () {
        getData();
    }, 600000);
    var luckytimer;
    getData();
    function getData() {
        $('.lucky').html('');
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
                if(data.code=="200000"){
                    $('.list').html('');
                    $.each(data.data,function(i,v){
                        $(".list").append("<li><span>"+v.user+"</span><span class='mid'>"+v.reward+"</span><span>"+v.time+"</span></li>")
                    })
                }
                $.each(data.data.items, function (i, item) {
                    $('.list').append("<li><span>" + item.companyName + "</span><span>" + item.contactNumber + "</span><span>" + item.contactNumber + "</span></li>");
                });

                //clearInterval(luckytimer);
                var index = 0;
                setInterval(function () {
                    if (index >= data.data.length - 6) {
                        index = 0;
                        $('.scroll ul').css({'marginTop': 0 + 'px'});
                    } else {
                        index++;
                        $('.scroll ul').animate({'marginTop': -50 * index + 'px'}, 200);
                    }
                }, 2000);

            },
            error: function (data) {
            }
        });
    }
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});