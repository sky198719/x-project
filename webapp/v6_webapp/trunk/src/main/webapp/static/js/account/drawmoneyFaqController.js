define(function () {
    var drawmoneyFaqCtrl = {
        init: function (event) {
            req.callJSON({
                url: 'account/initWithdraw.do',
                success: function (result) {
                    var configWithdrawCount = result.data.configWithdrawCount;
                    if(configWithdrawCount < 0) {
                        $$(".drawmoneyFaq4").hide();
                        //$$(".drawmoneyFaq5").html("4.为了您的提现安全，若您在银行提现界面终止提现操作，消耗的可提现次数预计将于2个小时之后返回。");
                        $$(".drawmoneyFaq5").hide();
                    } else if(configWithdrawCount == 0) {
                        $$(".drawmoneyFaq4").html("4.请联系客服或次月再进行提现。");
                        $$(".drawmoneyFaq4").show();
                        $$(".drawmoneyFaq5").hide();
                    } else {
                        $$(".drawmoneyFaq4").html("4.每月可成功提现"+configWithdrawCount+"次，超出请联系客服或次月再进行提现（当月可成功提现次数不可叠加至次月使用）。");
                        $$(".drawmoneyFaq4").show();
                        $$(".drawmoneyFaq5").show();
                    }

                    var feeRateWithdraw = result.data.feeRateWithdraw;
                    var minFeeWithdraw = result.data.minFeeWithdraw;
                    var maxFeeWithdraw = result.data.maxFeeWithdraw;
                    var maxFreeWithdrawCount = result.data.maxFreeWithdrawCount;
                    if(maxFreeWithdrawCount == 0 && maxFeeWithdraw == 0 && feeRateWithdraw == 0 && minFeeWithdraw == 0) {
                        $$(".drawmoneyFaq3").show();
                    } else {
                        $$(".drawmoneyFaq3").show();
                        $$(".drawmoneyFaq3").html("3.当月提现超过"+maxFreeWithdrawCount+"次后，每次收取"+feeRateWithdraw+"%的手续费，最低收费"+minFeeWithdraw+"元，最高收费"+maxFeeWithdraw+"元（每月免费提现次数不可叠加至下月使用）。");
                    }
                }
            });
        }
    };
    return drawmoneyFaqCtrl;
});
