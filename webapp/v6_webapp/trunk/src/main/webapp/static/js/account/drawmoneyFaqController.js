define(function () {
    var drawmoneyFaqCtrl = {
        init: function (event) {
            req.callJSON({
                url: 'account/initWithdraw.do',
                success: function (result) {
                    var configWithdrawCount = result.data.configWithdrawCount;
                    if(configWithdrawCount < 0) {
                        $$(".drawmoneyFaq4").hide();
                        //$$(".drawmoneyFaq5").html("4.Ϊ���������ְ�ȫ���������������ֽ�����ֹ���ֲ��������ĵĿ����ִ���Ԥ�ƽ���2��Сʱ֮�󷵻ء�");
                        $$(".drawmoneyFaq5").hide();
                    } else if(configWithdrawCount == 0) {
                        $$(".drawmoneyFaq4").html("4.����ϵ�ͷ�������ٽ������֡�");
                        $$(".drawmoneyFaq4").show();
                        $$(".drawmoneyFaq5").hide();
                    } else {
                        $$(".drawmoneyFaq4").html("4.ÿ�¿ɳɹ�����"+configWithdrawCount+"�Σ���������ϵ�ͷ�������ٽ������֣����¿ɳɹ����ִ������ɵ���������ʹ�ã���");
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
                        $$(".drawmoneyFaq3").html("3.�������ֳ���"+maxFreeWithdrawCount+"�κ�ÿ����ȡ"+feeRateWithdraw+"%�������ѣ�����շ�"+minFeeWithdraw+"Ԫ������շ�"+maxFeeWithdraw+"Ԫ��ÿ��������ִ������ɵ���������ʹ�ã���");
                    }
                }
            });
        }
    };
    return drawmoneyFaqCtrl;
});
