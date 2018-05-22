define(function () {
    var hongbaoView = {
        init: function (params) {
        },
        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem: function (param) {
            if (param.hongbaoList.length != 0) {
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.hongbaoList});
                if (param.status == 1) {
                    if (param.type == 'push') {
                        $$("#my_use_coupon").append(output);
                    } else {
                        $$("#my_use_coupon").html(output);
                    }
                } else if (param.status == 2) {
                    if (param.type == 'push') {
                        $$("#my_used_coupon").append(output);
                    } else {
                        $$("#my_used_coupon").html(output);
                    }
                } else if (param.status == 3) {
                    if (param.type == 'push') {
                        $$("#my_out_coupon").append(output);
                    } else {
                        $$("#my_out_coupon").html(output);
                    }
                }

                appFunc.bindEvents([
                    {
                        element: '.redpactTender',
                        event: 'click',
                        handler: hongbaoView.redpactTender
                    }
                ]);
            } else {
                var title = '亲，您暂时还没有红包哦';
                if(param.status == 2) {
                    title = "亲，您还没有使用的红包哦";
                } else if (param.status == 3) {
                    title = "亲，您暂无过期的红包哦";
                }
                var html = '<div class="text-center pd10">' + title + '</div>';
                if (param.status == 1) {
                    $$("#my_use_coupon").html(html);
                } else if (param.status == 2) {
                    $$("#my_used_coupon").html(html);
                } else if (param.status == 3) {
                    $$("#my_out_coupon").html(html);
                }
            }
        },
        redpactTender:function() {
            GS.loadPage('popular/financesList.html');
        }
    };
    return hongbaoView;
});
