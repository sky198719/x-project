/**
 * 银行卡管理
 * Created by pufei on 2015/4/2.
 */
define(['js/personal/bankView','js/personal/bankModel','js/utils/bank'], function (bankView,bankModel,bank) {

    var bankCtrl = {
        init: function(){
            bankCtrl.loadBankInfo();
        },

        loadBankInfo:function(){

            req.callJSON({
                url: 'personal/userCheckingBankCardInfo.do',
                indicator:true,
                dataType:'json',
                timeout:10000,
                success: function (result) {
                    if(result.code == 200000 && result.data != undefined && result.data.code == 0) {
                        var existCheckingBankCard = result.data.data.existCheckingBankCard;
                        if(existCheckingBankCard == 1) {
                            var html = '<a href="" class="button button-51 active" style="display: block;background: #999;border-color: #999;color: black;margin-bottom: 20px;">更换审核中</a>';
                            html += "您的更换银行卡申请已提交，正在审核中，最早将于T+1工作日反馈审核结果";
                            $$(".bankText").html(html);
                        }
                    }
                }
            });

            req.callGet({
                url: 'personal/userBandedBankOutSideUse.do',
                indicator:true,
                dataType:'json',
                timeout:10000,
                success: function (result) {

                    if(result.code != 200000) {
                        xxdApp.alert(result.message,"提示");
                        return ;
                    }

                    var data = result.data.data;
                    var bankObj = bank.getBank(data.bankcode);
                    var banklist = [
                        {
                            bankName:bankObj.bankName,
                            bankCode:bankObj.className,
                            bankAccount:data.bankaccount
                        }
                    ];

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() +  'personal/bankList.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (data) {
                                bankView.appendBank({result: data, banklist: banklist});
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }
                }
            });
        },

    };
    return bankCtrl
});
