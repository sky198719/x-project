require(['base', "trackBase", 'store', 'side', 'juicer'
    , 'header', 'footer', "dialog", 'backTop', 'json', "requirejs"
], function ($, track, store, side, jui, header, footer, dialog) {

    header.init();
    footer.init();
    //左侧菜单
    side.leftMenu(4);
    side.tabs({
        tabsObject: $("#J_tabs"),//tab 栏
    });
    var token = store && store.get("token") || {};
    var envelopes = [
        {'status': 'envelope1', 'value': 1},
        {'status': 'envelope2', 'value': 2},
        {'status': 'envelope3', 'value': 3}
    ]
    var productType = [
        {'type': 2, 'value': "步步高升"},
        {'type': 3, 'value': "七天大胜"},
        {'type': 4, 'value': "月进斗金"},
        {'type': 5, 'value': "新元宝"},
        {'type': 6, 'value': "月月派"},
        {'type': 7, 'value': "散标"},
        {'type': 8, 'value': "债权"}

    ];


    getedEnvelope(envelopes[0].value, $('.J_enable'), envelopes[0].status, envelopes[0].value);
    getedEnvelope(envelopes[1].value, $('.J_used'), envelopes[1].status, envelopes[1].value);
    getedEnvelope(envelopes[2].value, $('.J_expired'), envelopes[2].status, envelopes[2].value);


    function getedEnvelope(allStatus, object, envelopes, envelopesValue) {
        $.xxdAjax({
            url: '/investmentAPI/prize/redEnvelope',
            type: 'get',
            clientId: 'XXD_FRONT_END',
            token: token,
            data: {
                currentPage: 1,
                pageSize: 4,
                status: allStatus
            },
            callbacks: function (data) {
                if (data && data.code == '200000') {
                    if (data.data.items) {
                        if (data.data.items.length > 0) {
                            envelopesValue += '1';
                            $.each(data.data.items, function (i, v) {
                                object.append('<li class="clearfix"> <div class="goupon-pic"> <p>' + v.title + '</p> </div> ' +
                                    '<div class="goupon-info"> <p>金额：<span>' + v.amount + '元</span></p> <p>使用条件：' +
                                    '<span>投标满' + v.amountLimit + '元</span></p><p>使用范围：<span>' + v.productRange + '</span></p> ' +
                                    '<p class="last">有效期：<span>' + $.fnDateToString(v.validDate, 'yyyy-MM-dd') + '</span></p></div> </li>');
                            });
                        }
                    } else {
                        side.thisDialog(data.message);
                    }
                } else {
                    side.thisDialog(data.message);
                }
                getCoupon(allStatus, object, envelopes, envelopesValue);
            },
            error: function () {
                side.thisDialog('网络异常，请刷新重试');
            }
        });
    }

    function getCoupon(allStatus, object, envelopes, envelopesValue) {
        $.xxdAjax({
            url: '/tradeCenter/coupon/queryCouponRecordsByUserId',
            type: 'get',
            clientId: 'XXD_FRONT_END',
            token: token,
            data: {
                status: allStatus
            },
            callbacks: function (data) {
                if (data && data.code == '200000') {
                    if (data.data.couponList) {
                        if (data.data.couponList.length > 0) {
                            $.each(data.data.couponList, function (i, v) {
                                var termsList,type;
                                $.each(v.productScope,function (j,w) {
                                    $.each(productType,function (h,u) {
                                        if(w.productType == productType[h].type){
                                            type = productType[h].value;
                                        }
                                    })
                                    termsList = w.termsList.join('/') + '个月';
                                });
                                object.append('<li class="clearfix"> <div class="goupon-pic"> <p>' + v.name + '</p> </div> ' +
                                    '<div class="goupon-info"> <p>金额：<span>' + v.amount + '元</span></p> <p>使用条件：' +
                                    '<span>投标满' + v.quota + '元</span></p><p>使用范围：<span>'+type+termsList+'</span></p> ' +
                                    '<p class="last">有效期：<span>' + $.fnDateToString(v.effectiveEndTime, 'yyyy-MM-dd') + '</span></p></div> </li>');
                            });
                        } else {
                            if (envelopes == 'envelope1') {
                                if (envelopesValue == '11') {
                                    return;
                                }
                                object.append('<li class="clearfix"> <div class="goupon-pic"> <p>暂无优惠券</p> </div> ' +
                                    '<div class="goupon-info"> <p><span></span></p> <p><span></span></p><p><span></span></p> ' +
                                    '<p class="last"><span></span></p></div> </li>');
                            }
                            if (envelopes == 'envelope2') {
                                if (envelopesValue == '21') {
                                    return;
                                }
                                object.append('<li class="clearfix"> <div class="goupon-pic"> <p>暂无优惠券</p> </div> ' +
                                    '<div class="goupon-info"> <p><span></span></p> <p><span></span></p><p><span></span></p> ' +
                                    '<p class="last"><span></span></p></div> </li>');
                            }
                            if (envelopes == 'envelope3') {
                                if (envelopesValue == '31') {
                                    return;
                                }
                                object.append('<li class="clearfix"> <div class="goupon-pic"> <p>暂无优惠券</p> </div> ' +
                                    '<div class="goupon-info"> <p><span></span></p> <p><span></span></p><p><span></span></p> ' +
                                    '<p class="last"><span></span></p></div> </li>');
                            }
                        }
                    } else {
                        side.thisDialog(data.message);
                    }
                } else {
                    side.thisDialog(data.message);
                }
            },
            error: function () {
                side.thisDialog('网络异常，请刷新重试');
            }
        });
    }



    //兑换
    $('.J_couponInput').keyup(function () {
        if ($('.J_couponInput').val().length >= 8) {
            $('.J_getCoupon').removeClass('disable');
        }
    });
    $('.J_getCoupon').click(function () {
        if ($(this).hasClass('disable')) {
            return;
        }
        $.xxdAjax({
            url: '/tradeCenter/coupon/conversionCouponRecord',
            type: 'post',
            clientId: 'XXD_FRONT_END',
            token: token,
            data: JSON.stringify({
                "data": {
                    "code": $('.J_couponInput').val()
                }
            }),
            callbacks: function (data) {
                if (data && data.code == '200000') {
                    if (data.data) {
                        side.thisDialog(data.data.message);
                    } else {
                        side.thisDialog(data.message);
                    }
                } else {
                    side.thisDialog(data.message);
                }
            },
            error: function () {
                side.thisDialog('网络异常，请刷新重试');
            }
        });
    });


    side.getLeftHeight();

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});