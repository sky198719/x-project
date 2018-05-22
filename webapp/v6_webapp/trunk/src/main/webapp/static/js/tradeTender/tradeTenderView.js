define(function () {
    var tradeTenderView = {
        init: function () {

        },
        bindEvent: function (params) {
            appFunc.bindEvents(params.bindings);
        },

        tenderDetail: function (data) {
            var html = '';
            if (data.tradeRequestDetail.funds>=0) {
                html += '<h4>' + data.tradeRequestDetail.borrowName + '<span class="icon-tips">已折让：' + data.tradeRequestDetail.funds + '元</span></h4>';
            } else if (data.tradeRequestDetail.funds<0) {
                html += '<h4>' + data.tradeRequestDetail.borrowName + '<span class="icon-tips">已加价：' + (data.tradeRequestDetail.funds*-1) + '元</span></h4>';
            }
            html += '</h4>' +
            '<h5 class="font-grey mt10">转让价格：<span class="font-red font24" id="trd_show_rep_fun">' + appFunc.fmoney(data.tradeRequestDetail.repayCapital - data.tradeRequestDetail.funds, 2) + '</span> 元</h5>' +
            '<h5 class="font-grey mt5">待收本息：' + appFunc.fmoney(data.tradeRequestDetail.repaymentAmount, 2) + '元</h5>' +
            '<h5 class="font-grey mt5">剩余期限：<span class="font18">' + data.tradeRequestDetail.remainNumber + '</span> ';
            html += '个月';
            html +=
            '<h5 class="font-grey mt5">债权转让编号：' + data.tradeRequestDetail.tenderId + '</h5>' +
            '<h5 class="font-grey mt5">下一还款日：' + data.tradeRequestDetail.nextRepaymentTime + '</h5>' +
            '<input type="hidden" id="trd_rep_fun" value="' + (data.tradeRequestDetail.repayCapital - data.tradeRequestDetail.funds) + '"/>';
            html += "<input type='hidden' id='q_tradeRequestUserId' value='" + data.tradeRequestDetail.userId + "'/>";
            html += "<input type='hidden' id='q_borrowDetailUserId' value='" + data.borrowDetail.userId + "'/>";
            html += "<input type='hidden' id='q_repayYesAccount' value='" + (data.tradeRequestDetail.repayCapital - data.tradeRequestDetail.funds) + "'/>";
            html += "<input type='hidden' id='q_remainNumber' value='" + data.tradeRequestDetail.remainNumber + "'/>";

            html += "<input type='hidden' id='q_requestId' value='" + data.tradeRequestDetail.requestId + "'/>";
            html += "<input type='hidden' id='q_tenderId' value='" + data.tradeRequestDetail.tenderId + "'/>";
            html += "<input type='hidden' id='q_tenderApr' value='" + appFunc.fmoney(data.tradeRequestDetail.apr, 2) + "'/>";

            $$("#trd_quicktender").html(html);
            $$("#trd_real_rep_fun").html(appFunc.fmoney(data.tradeRequestDetail.repayCapital - data.tradeRequestDetail.funds, 2));
            $$("#trd_showUserAccount").html(appFunc.fmoney(data.defaultAccount.USABLE, 2));
            $$("#trd_userAccount").val(data.defaultAccount.USABLE);
        }
    };
    return tradeTenderView;
});