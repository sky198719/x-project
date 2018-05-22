/**
 * 充值第一步
 */
define(['js/account/topupView', 'js/personal/bankUtil', 'js/utils/date','js/utils/bank'], function (topupView, bankUtil, DateHandle,bank) {
    var minTopup = 2;
    var singleLimitTemp =0;
    var topupCtrl = {
        init: function (event) {
            var bindings = [
                {
                    element: '#topup .recharge',
                    event: 'click',
                    handler: topupCtrl.recharge
                }
            ];

            topupView.init({bindings: bindings});
            topupCtrl.userBandedBankOutSideUse();

        },
        userBandedBankOutSideUse:function(){
            req.callJSON({
                url:'personal/userBandedBankOutSideUse.do',

                success:function(result){
                    if(result.code != 200000) {
                        xxdApp.alert(result.message,"提示");
                        return;
                    }

                    var data = result.data;
                    if(data.code != 0 ) {
                        xxdApp.alert(data.message,"提示");
                        return;
                    }

                    var d = data.data;
                    var bankObj = bank.getBank(d.bankcode);
                    $$("#topup .bankName").html(bankObj.bankName+"("+d.bankaccount+")");
                    $$("#topup #bankIcon").addClass(bankObj.className);
                    var singleLimit = d.singleLimit;
                    singleLimitTemp = singleLimit;
                    var unit = 10000;
                    if(parseInt(singleLimit) >= unit) {
                        singleLimit = singleLimit / unit;
                        singleLimit = appFunc.fmoney(singleLimit,0) + '万';
                    }
                    var dailyLimit = d.dailyLimit;
                    if(parseInt(dailyLimit) >= unit) {
                        dailyLimit = dailyLimit / unit;
                        dailyLimit = appFunc.fmoney(dailyLimit,0) + '万';
                    }
                    $$("#topup .bankLimit").html("该卡单笔限额"+singleLimit+"，单日限额"+dailyLimit);
                }
            });
        },
        recharge: function () {
            var moneyOrder = $$('#topup #moneyOrder').val();
            if (moneyOrder == '') {
                xxdApp.alert('充值金额不能为空', '提示');
                return false;
            }

            if (!appFunc.isFloat(moneyOrder)) {
                xxdApp.alert("请输入正确的充值金额", '提示');
                return false;
            }

            if (parseFloat(moneyOrder) < minTopup) {
                xxdApp.alert('充值金额最低为'+minTopup+'元', '提示');
                return false;
            }

            if(parseFloat(moneyOrder) > parseFloat(singleLimitTemp)) {
                xxdApp.alert('单笔充值不可大于'+singleLimitTemp+'元', '提示');
                return false;
            }

            $$("#topup .button-51").removeAttr("disabled");
            $$('#topup #recharge-form').attr('action', 'pay/submit.do');
            $$('#topup #recharge-form').submit();
        }
    };
    return topupCtrl;
});
