require(['base', "trackBase", 'store', 'detail', 'juicer'
    , 'header', 'footer', "dialog", 'backTop', 'json', 'md'
], function ($, track, store, detail, jui, header, footer, dialog, md) {
    header.init();
    footer.init();
    var userDO = store && store.get("userDO") || {};
    track.init(userDO);
    var tenderType = $.readCookie("tenderType");

    var historyUrl = $.readCookie("historyUrl");

    //获得时间
    Date.prototype.toLocaleString = function () {
        return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日 ";
    };
    var startDate = $.readCookie("startDate"),
        expireDate = $.readCookie("expireDate");
    startDate = new Date(parseInt(startDate));
    expireDate = new Date(parseInt(expireDate));
    $('.start-date').html(startDate.toLocaleString());
    $('.end-date').html(expireDate.toLocaleString());
    var referrerUrl;
    if (document.referrer) {
        referrerUrl = document.referrer.split('/');
    } else {
        referrerUrl = historyUrl.split('/');
    }
    var url = referrerUrl[referrerUrl.length - 1];
    var consumptionUrl = referrerUrl[referrerUrl.length - 2];  //消费贷地址不一样
    $('.record').click(function () {
        if (url.indexOf('monthgold') >= 0) {
            window.location.href = '/usercenter/tender/monthgold.html';
        } else if (url.indexOf('sevengold') >= 0) {
            window.location.href = '/usercenter/tender/sevengold.html';
        } else if (url.indexOf('newtender') >= 0) {
            window.location.href = '/usercenter/tender/newtender.html';
        } else if (url.indexOf('thirtytender') >= 0) {
            window.location.href = '/usercenter/tender/thirtytender.html';
        } else if (consumptionUrl.indexOf('consumption') >= 0) {
            window.location.href = '/usercenter/tender/investment.html';
        }
    });


    //继续出借
    $('.purchase').click(function () {
        if (consumptionUrl.indexOf('consumption') >= 0) {
            window.location.href ='/detail/consumptionList.html';
        }else{
            if (document.referrer) {
                window.location.href = document.referrer;
            } else {
                window.location.href = historyUrl;
            }
        }
    });

    //不是消费贷的时候就请求
    if (consumptionUrl.indexOf('consumption') < 0) {
        $.xxdAjax({
            url: '/tradeCenter/investBiz/recommend',
            type: 'get',
            clientId: 'XXD_FRONT_END',
            data: {
                'tenderType': tenderType
            },
            callbacks: function (data) {
                if (data.code == 200000) {
                    if (data.data.floatApr) {
                        $.each(data.data, function (i, item) {
                            $('.recommend-focus').append("<div class='recommend-list'>"
                                + "<div class='clearfix'>"
                                + "<table>"
                                + "<tr>"
                                + "<th class='first'>产品名称</th>"
                                + "<th class='second'>年化收益</th>"
                                + "<th class='third'>借款期限</th>"
                                + "</tr>"
                                + "<tr>"
                                + "<td class='first'>" + item.name + "</td>"
                                + "<td class='second'>" + item.apr + item.floatApr + "%</td>"
                                + "<td class='third'>" + item.term + "</td>"
                                + "</tr>"
                                + "</table>"
                                + "<a class='visit-product' target='_blank' href='" + item.url + "'>立即查看</a>"
                                + "</div>"
                                + "</div>")
                        })
                    } else {
                        $.each(data.data, function (i, item) {
                            $('.recommend-focus').append("<div class='recommend-list'>"
                                + "<div class='clearfix'>"
                                + "<table>"
                                + "<tr>"
                                + "<th class='first'>产品名称</th>"
                                + "<th class='second'>年化收益</th>"
                                + "<th class='third'>借款期限</th>"
                                + "</tr>"
                                + "<tr>"
                                + "<td class='first'>" + item.name + "</td>"
                                + "<td class='second'>" + item.apr + "</td>"
                                + "<td class='third'>" + item.term + "</td>"
                                + "</tr>"
                                + "</table>"
                                + "<a class='visit-product' href='" + item.url + "'>立即查看</a>"
                                + "</div>"
                                + "</div>")
                        })
                    }
                } else {
                    $('.recommend').hide();
                }
            }
        });
    }

    //在散标页面上是没有时间的，要去掉
    if (consumptionUrl.indexOf('consumption') >= 0) {
        $('.start-date').html('等待满标');
        $('.end-date').html('开始回款');
        $('.start-info').hide();
        $('.end-info').hide();
        $('.recommend').hide();
    };

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
