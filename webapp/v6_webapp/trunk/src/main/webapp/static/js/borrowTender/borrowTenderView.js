define(function () {
    var borrowView = {
        init: function () {

        },
        bindEvent: function (params) {
            appFunc.bindEvents(params.bindings);
        },

        tenderDetail: function (data) {
            var html = '';
            if (data.borrow.AWARD == 0) {
                html += '<h4>' + data.borrow.NAME + ''
            } else if (data.borrow.AWARD == 1) {
                html += '<h4>' + data.borrow.NAME + '<span class="icon-tips">奖励:' + appFunc.fmoney(data.borrow.FUNDS,2) + '元</span>'
            } else if (data.borrow.AWARD == 2) {
                html += '<h4>' + data.borrow.NAME + '<span class="icon-tips">奖励:' + appFunc.fmoney(data.borrow.FUNDS,2) + '%</span>'
            }
            html += '</h4>' +
                    '<h5 class="font-grey mt10">剩余可投金额：<span class="font-red font24" id="showBorrowlackaccount">' + appFunc.fmoney(data.borrow.LACKACCOUNT,2) + '</span> 元</h5>' +
                    '<input type="hidden" id="borrowlackaccount" value="' + data.borrow.LACKACCOUNT + '"/>' +
                    '<h5 class="font-grey mt5">投标限额：' + appFunc.fmoney(data.borrow.LOWESTTENDER,2) + ' 元';
            if (data.borrow.MOSTTENDER == 0) {
                html += "以上"
            } else {
                html += " - " + appFunc.fmoney(data.borrow.MOSTTENDER,2) + "元"
            }
            html += '</h5>';
            html += "<input type='hidden' id='borrowId' value='" + data.borrow.BORROWID + "'/>";
            html += "<input type='hidden' id='borroyRate' value='" + data.borrow.APR + "'/>";
            html += "<input type='hidden' id='borroyTimeLimit' value='" + data.borrow.TIMELIMIT + "'/>";
            html += "<input type='hidden' id='borrowType' value='" + data.borrow.TYPEVALUE + "'/>";
            html += "<input type='hidden' id='type' value='" + data.borrow.TYPE + "'/>";
            $$("#quicktender").html(html);
            $$("#showUserAccount").html(appFunc.fmoney(data.account.USABLE,2));
            $$("#userAccount").val(data.account.USABLE);
            $$("#accountnumber").attr("placeholder","请输入投标金额 最低"+appFunc.fmoney(data.borrow.LOWESTTENDER,2)+"元");
        }
    };
    return borrowView;
});