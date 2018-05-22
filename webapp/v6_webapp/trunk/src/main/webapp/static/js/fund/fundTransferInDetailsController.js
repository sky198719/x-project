define(['js/utils/date'],function(DateHandle){
    var fundTransferInDetailCtrl = {
        init:function(event){
            var param = appFunc.getEventDetailPageQuery(event);
            var tradeId = param.tradeId;
            fundTransferInDetailCtrl.loadFundTradeInfo(tradeId);
        },
        loadFundTradeInfo:function(tradeId){
            req.callGet({
                url:'fund/selectTradeInfo.do',
                data:{
                    tradeId:tradeId
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    $$("h5[name='createDate']").html(DateHandle.formatDate('yyyy-MM-dd HH:mm',result.data.createDate));
                    $$("#valueDate").html(DateHandle.formatDate('yyyy-MM-dd',result.valueDate));
                    $$("#arrivalDate").html(DateHandle.formatDate('yyyy-MM-dd',result.arrivalDate));
                    $$("#tradeNum").html('+' + appFunc.fmoney(result.data.tradeNum,2));

                    var currentDate = new Date(Date.parse(DateHandle.formatDate('yyyy-MM-dd',result.currentDate).replace(/-/g,"/")));
                    var tempValueDate = new Date(Date.parse(DateHandle.formatDate('yyyy-MM-dd',result.valueDate).replace(/-/g,"/")));
                    if(tempValueDate <= currentDate) {
                        fundTransferInDetailCtrl.enableCalculate("true");
                    }else{
                        fundTransferInDetailCtrl.enableCalculate("false");
                    }

                    var tempArrivelDate = new Date(Date.parse(DateHandle.formatDate('yyyy-MM-dd',result.arrivalDate).replace(/-/g,"/")));
                    if(tempArrivelDate <= currentDate){
                        fundTransferInDetailCtrl.enableArrival("true");
                    }else{
                        fundTransferInDetailCtrl.enableArrival("false");
                    }
                }
            });
        },
        enableCalculate:function(enable) {
            if(enable=="true") {
                $$('#fund_details #img_cal_arrows').attr('src','static/img/xxd/arrows-ok.png');
                $$('#fund_details #img_calculation').attr('src','static/img/xxd/calculate-show.png');
            } else {
                $$('#fund_details #img_cal_arrows').attr('src','static/img/xxd/arrows-no.png');
                $$('#fund_details #img_calculation').attr('src','static/img/xxd/calculate-hide.png');
            }
        },
        enableArrival:function(enable) {
            if(enable=="true") {
                $$('#fund_details #img_arrival_arrows').attr('src','static/img/xxd/arrows-ok.png');
                $$('#fund_details #img_arrival').attr('src','static/img/xxd/earnings-show.png');
            } else {
                $$('#fund_details #img_arrival_arrows').attr('src','static/img/xxd/arrows-no.png');
                $$('#fund_details #img_arrival').attr('src','static/img/xxd/earnings-grey.png');
            }
        }
    };
    return fundTransferInDetailCtrl;
});