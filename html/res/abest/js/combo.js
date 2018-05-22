require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float, track, store) {
    // 布码init
    var userDO = store && store.get("userDO") || {};
    track.init(userDO);

    var timer, _recursion = function ($ul, start, end, callback) {
        function _fn() {
            if (start == end) {
                timer = null;
                if (callback) callback($ul, start, end);
                return;
            }
            if (start > end) start -= 252;
            else start += 252;
            $ul.css("marginLeft", start);
            timer = setTimeout(_fn, 100);
        }

        _fn();
    }
    var $partner = $("#J_partner"),
        $ul = $partner.find("ul"),
        countPartner = $ul.find("li").length,
        $leftAndRight = $("#J_partner").find(".left ,.right");

    if (countPartner * 252 - 756 <= 0) $leftAndRight.addClass("hide");

    $leftAndRight.on("click", function (e) {
        if (timer) return;
        var $this = $(this),
            callback;
        console.log("$this:" + $this + "===" + "callback:" + callback)

        var curPosition = $(".wrap-partner ul").css("marginLeft"),
            newPosition = 0;
        curPosition = curPosition ? parseInt(curPosition.replace(/px/g, "")) : 0;

        if ($this.hasClass("left")) {
            if (Math.abs(curPosition) < Math.abs(countPartner * 252 - 756)) {
                newPosition = curPosition - 252;
                $ul.append($ul.find("li").eq(0).clone(true));
                newPosition = curPosition - 252;
                callback = function ($ul, curPosition, newPosition) {
                    $ul.css("marginLeft", newPosition + 252).find("li").eq(0).remove();
                }
            }
        } else {
            if (Math.abs(curPosition) > 0) {
                newPosition = curPosition + 252;
            } else {
                $ul.prepend($ul.find("li").eq(countPartner - 1).clone(true)).css("marginLeft", curPosition - 252);
                newPosition = curPosition;
                curPosition = curPosition - 252;
                callback = function ($ul, curPosition, newPosition) {
                    $ul.css("marginLeft", newPosition).find("li").eq(countPartner).remove();
                }
            }
        }
        if (curPosition != newPosition) {
            _recursion($ul, curPosition, newPosition, callback);
        }
    });

    $.ajax({
        url: "/biz/bulletin/operationData",
        contentType: "application/json",
        dataType: "json",
        type: "get",
        beforeSend: function (request) {
            request.setRequestHeader("s", "www");
            request.setRequestHeader("clientId", "001");
            request.setRequestHeader("clientTime", "001");
        },
        success: function (data) {
            if (data.code == 200000) {
                var data = data.data;
                // console.info(data);
                //安全运营时间
                $(".totalbalace").children("span").html(data.time);
                $.each(data.items, function (idx, obj) {
                    if (obj.code == "TOTAL_TRADE") {
                        //累计成交额
                        var value=(obj.nvalue/100000000).toFixed(2);
                        $(".totaltrade").children("span").html(value);
                    }
                    if (obj.code == "TOTAL_INCOME") {
                        //累计为出借人赚取
                        var value=(obj.nvalue/100000000).toFixed(2);
                        $(".totalincome").children("span").html(value);
                    }
                    if (obj.code == "TOTAL_COUNT") {
                        //累计交易总笔数 微笑用户
                        $(".totalcount").children("span").html(obj.nvalue);
                    }
                    /*if (obj.code == "VENTURE_BALANCE") {
                        //分先准备金余额
                        var value=(obj.nvalue/10000).toFixed(2);
                        $(".totalbalace").children("span").html(value);
                    }*/
                });
            }
        },
        error: function (data) {
            console.error(data.code);
        }
    })
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});