define(['js/utils/date'], function (DateHandle) {
    var fundDetailsListView = {
        showDetails: function (param) {
            switch (param.currentTab) {
                case 'earnings':
                    fundDetailsListView.showEarningsDetails(param);
                    break;
                case 'transferIn':
                    fundDetailsListView.showTransferInDetails(param);
                    break;
                case 'transferOut':
                    fundDetailsListView.showTransferOutDetails(param);
                    break;
            }
        },
        showEarningsDetails: function (param) {
            if (param.dataList.length == 0) {
                $$("#fundEarnings ul").html('<h6 class="font-grey text-center pd10">暂无记录</h6>');
                return;
            }
            var html = '';
            for (var i = 0; i < param.dataList.length; i++) {
                var o = param.dataList[i];
                html += '<li>';
                html += '<div class="item-content">';
                html += '<div class="item-inner">';
                html += '<div class="item-title-row">';
                html += '<div class="item-title"><h4 class="font-grey">收益</h4></div>';
                html += '<div class="item-after"><h4 class="font-green">+' + appFunc.fmoney(o.TRADENUM, 2) + '</h4></div>';
                html += '</div>';
                // html += '<div class="item-subtitle"><span class="font-grey-A9">'+  DateHandle.formatDate('yyyy-MM-dd',o.EARNINGSTIME) +'</span><span class="font-grey-A9 right">'+ appFunc.fmoney(o.APR,2)+'%</span></div>';
                html += '<div class="item-subtitle"><span class="font-grey-A9">' + DateHandle.formatDate('yyyy-MM-dd', o.EARNINGSTIME) + '</span></div>';
                html += '</div>';
                html += '</div>';
                html += '</li>';
            }
            if ("push" == param.refreshType) {
                $$("#fundEarnings ul").append(html);
            } else {
                $$("#fundEarnings ul").html(html);
            }
        },
        showTransferInDetails: function (param) {
            if (param.dataList.length == 0) {
                $$("#fundTransferIn ul").html('<h6 class="font-grey text-center pd10">暂无记录</h6>');
                return;
            }
            var html = "";
            for (var i = 0; i < param.dataList.length; i++) {
                var o = param.dataList[i];
                html += '<li>';
                html += '<a href="' + GC.getHtmlPath() + 'fund/fundTransferInDetails.html?path=fund&tradeId=' + o.TRADEID + '&v='+ GC.getVersion() + '" class="item-link item-content">';
                html += '<div class="item-inner">';
                html += '<div class="item-title-row">';
                if (o.INITIATOR == 3) {
                    html += '<div class="item-title"><h4 class="font-grey">转入日日盈(体验金)</h4></div>';
                } else {
                    html += '<div class="item-title"><h4 class="font-grey">转入日日盈</h4></div>';
                }
                html += '<div class="item-after"><h4 class="font-green">+' + appFunc.fmoney(o.TRADENUM, 2) + '</h4></div>';
                html += '</div>';
                html += '<div class="item-subtitle"><span class="font-grey-A9">' + DateHandle.formatDate('yyyy-MM-dd HH:mm:ss', o.CREATEDATE) + '</span></div>';
                html += '</div>';
                html += '</a>';
                html += '</li>';
            }

            if ("push" == param.refreshType) {
                $$("#fundTransferIn ul").append(html);
            } else {
                $$("#fundTransferIn ul").html(html);
            }
        },
        showTransferOutDetails: function (param) {
            if (param.dataList.length == 0) {
                $$("#fundTransferOut ul").html('<h6 class="font-grey text-center pd10">暂无记录</h6>');
                return;
            }
            var html = '';
            for (var i = 0; i < param.dataList.length; i++) {
                var o = param.dataList[i];
                html += '<li>';
                html += '<div class="item-content">';
                html += '<div class="item-inner">';
                html += '<div class="item-title-row">';
                if (o.INITIATOR == 3) {
                    html += '<div class="item-title"><h4 class="font-grey">转出日日盈(体验金)</h4></div>';
                } else {
                    html += '<div class="item-title"><h4 class="font-grey">转出日日盈</h4></div>';
                }
                html += '<div class="item-after"><h4 class="font-orange">-' + appFunc.fmoney(o.TRADENUM, 2) + '</h4></div>';
                html += '</div>';
                html += '<div class="item-subtitle"><span class="font-grey-A9">' + DateHandle.formatDate('yyyy-MM-dd HH:mm:ss', o.CREATEDATE) + '</span></div>';
                html += '</div>';
                html += '</div>';
                html += '</li>';
            }
            if ("push" == param.refreshType) {
                $$("#fundTransferOut ul").append(html);
            } else {
                $$("#fundTransferOut ul").html(html);
            }
        }
    };
    return fundDetailsListView;
});