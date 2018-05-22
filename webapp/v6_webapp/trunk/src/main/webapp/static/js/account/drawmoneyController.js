/**
 * 提现
 * Created by pufei on 2015/3/27.
 */

define(['js/account/drawmoneyView', 'js/utils/bank'], function (drawmoneyView, bank) {
    var bankid = '';
    var realName = true;;
    var userWithdrawCount = 0;
    var configWithdrawCount = 0;
    var isWhiteCash = 0;
    var drawmoneyCtrl = {
        init: function () {
            drawmoneyCtrl.eventBind();
            drawmoneyCtrl.initDrawmoney();
            //drawmoneyCtrl.checkCashWhiteList();
            drawmoneyCtrl.couldCashMoney();
            drawmoneyCtrl.userBandedBankOutSideUse();

            req.callJSON({
                url:'personal/info.do',
                success:function(result) {
                    $$(".drawmoneyReName").html(result.realname.realName);
                }
            });
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
                    $$("#drawMoney #defaultBank").html(bankObj.bankName+"("+d.bankaccount+")");
                }
            });
        },
        checkCashWhiteList: function () {
            req.callJSON({
                url: 'account/checkCashWhiteList.do',
                dataType: 'json',
                success: function (result) {
                    if (result.resultCode == 0) {
                        $$(".drawmoneyRateCharge").data("checkWhiteList",result.data);
                        if(result.data == 0) {
                            //该用户不在白名单上，有提现收费
                            drawmoneyCtrl.getDrawMoneyCountMonthly();
                        }
                    }
                }
            });
        },

        getDrawMoneyCountMonthly: function () {
            req.callJSON({
                url: 'account/getDrawMoneyCountMonthly.do',
                dataType: 'json',
                success: function (data) {
                    if (data.resultCode == 0) {
                        var accountCashRate = data.accountCashRate;
                        if(accountCashRate == 0) {
                            $(".drawmoneyRateCharge").addClass("item-inner");
                            return;
                        }

                        var cashFae = $$(".cashFae");
                        var temp = (data.accountCashFer - data.drawCountMonthly);
                        temp = temp < 0 ? 0 : temp;
                        //var text = "当月剩余可提现：" + temp + "次，此次提现收取手续费0.00元";
                    //、cashFae.html(text);
                        cashFae.data("drawCountMonthly", data.drawCountMonthly);
                        cashFae.data("accountCashRate", accountCashRate);
                        cashFae.data("accountCashFer", data.accountCashFer);
                        cashFae.data("accountCashMinfee",data.accountCashMinfee);
                        cashFae.data("accountCashMaxfee",data.accountCashMaxfee);
                        $(".drawmoneyRateCharge").removeClass("item-inner");

                    }
                }
            });
        },

        accountCashRate: function () {
            var checkWhiteList = $$(".drawmoneyRateCharge").data("checkWhiteList");
            if (checkWhiteList == 1) {
                //用户在白名单内，没有提现收费
                return;
            }
            /*var cashFae = $$(".cashFae");
            var accountCashRate = cashFae.data("accountCashRate");
            if(accountCashRate == 0) {
                return;
            }
            var drawCountMonthly = cashFae.data("drawCountMonthly");
            var accountCashFer = cashFae.data("accountCashFer");
            var accountCashMinfee = cashFae.data("accountCashMinfee");
            accountCashMinfee = parseFloat(accountCashMinfee);
            var accountCashMaxfee = cashFae.data("accountCashMaxfee");
            accountCashMaxfee = parseFloat(accountCashMaxfee);
            var drawmoneyNum = $("#drawmoneyNum").val();
            var temp = accountCashFer - drawCountMonthly;
            temp = temp < 0 ? 0 : temp;
            if (drawCountMonthly != undefined && accountCashFer != undefined && temp == 0 && drawmoneyNum != undefined && drawmoneyNum > 0) {
                var cost = (parseFloat(accountCashRate) / 100) * parseFloat(drawmoneyNum);
                cost = cost < accountCashMinfee ? accountCashMinfee : cost;
                cost = cost > accountCashMaxfee ?  accountCashMaxfee : cost;
                var text = "本月可免费提现剩余：" + temp + "次，此次提现收取手续费" + appFunc.fmoney(cost,2) + "元";
                cashFae.html(text);
            } else {
                var text = "本月可免费提现剩余：" + temp + "次，此次提现收取手续费0.00元";
                cashFae.html(text);
            }*/


            var drawmoneyNum = $("#drawmoneyNum").val();
            if(drawmoneyNum == undefined || drawmoneyNum == '') {
                return;
            }
            req.callJSON({
                url:'product/withdrawCashTrial.do',
                data:{
                    withdrawCashAmount:drawmoneyNum
                },
                success:function(result){
                    if(result.code == 200000) {
                        if(result.data.code == 0) {
                            var text = "本月可免费提现剩余：" + userWithdrawCount + "次，此次提现收取手续费" + result.data.data.cashFee + "元";
                            if(configWithdrawCount <= 0) {
                                text = "此次提现收取手续费"+ result.data.data.cashFee + "元";
                            }

                            if(isWhiteCash == 1) {
                                text = "此次提现收取手续费0.00元";
                            }

                            $$(".cashFae").html(text);
                        }
                    }

                }
            });
        },

        drawmoneyTip: function () {
            GS.loadPage("account/drawmoneyFaq.html");
        },

        initDrawmoney: function () {
            req.callJSON({
                url: "account/initWithdraw.do",
                data: {},
                dataType: 'json',
                success: function (result) {
                    $$('#useMoney').html('可用余额：' + appFunc.fmoney(result.data.balanceAmount, 2));
                    $$('#couldCashMoney').html('此卡可提现余额：' + appFunc.fmoney(result.data.withdrawAmount, 2));
                    $$('#ccmValue').val(result.data.withdrawAmount);

                    configWithdrawCount = result.data.configWithdrawCount;

                    userWithdrawCount = result.data.userWithdrawCount;
                    var text = "当月剩余可提现：" + userWithdrawCount + "次，此次提现收取手续费0.00元";
                    if(configWithdrawCount <= 0) {
                        text = "此次提现收取手续费0.00元";
                    }

                    isWhiteCash = result.data.isWhiteCash;
                    if(isWhiteCash == 1) {
                        text = "此次提现收取手续费0.00元";
                    }

                    $$(".cashFae").html(text);
                    $(".cashFaeLi").show();
                }
            });
        },


        eventBind: function () {
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                /*{
                    element: '#fruits',
                    event: 'change',
                    handler: drawmoneyCtrl.couldCashMoney
                },*/
                {
                    element: '#draw_money',
                    event: 'click',
                    handler: drawmoneyCtrl.drawMoney
                },
                {
                    element: '#drawmoneyNum',
                    event: 'keyup',
                    handler: drawmoneyCtrl.accountCashRate
                },
                {
                    element: '.drawmoneyTip',
                    event: 'click',
                    handler: drawmoneyCtrl.drawmoneyTip
                }
            ];

            drawmoneyView.bindEvents({
                        bindings: bindings
                    }
            );
        },

        couldCashMoney: function () {
            bankid = $$('#fruits').val();
            if (bankid != '' && bankid != null) {
                req.callJSON({
                    url: 'account/loadDrawmoney.do',
                    data: {bankId: bankid},
                    dataType: 'json',
                    success: function (data) {
                        if (data.account != null) {
                            $$('#useMoney').html('可用余额：' + appFunc.fmoney(data.account.usable, 2));
                            $$('#couldCashMoney').html('此卡可提现余额：' + appFunc.fmoney(data.couldCashMoney, 2));
                            $$('#ccmValue').val(data.couldCashMoney);
                        }
                    }
                });
            }
        },

        drawMoney: function () {
            if(isWhiteCash == 0 && configWithdrawCount == 0) {
                xxdApp.alert("请联系客服或次月再进行提现","提示");
                return;
            }

            if(isWhiteCash == 0 && userWithdrawCount == 0 && configWithdrawCount > 0) {
                xxdApp.alert("您当月最大可提现"+configWithdrawCount+"次，剩余次数不足，不能进行提现，请联系客服","提示");
                return;
            }

            //可提现金额
            var couldCashMoney = $$('#ccmValue').val();
            //提现金额
            var drawmoney = $$('#drawmoneyNum').val();
            if ("" == drawmoney) {
                xxdApp.alert('必须填写提现金额，请重新输入', '提示');
                return;
            } else if (!appFunc.isFloat(drawmoney)) {
                xxdApp.alert('提现金额只能包含数字和小数点(小数点后只能包含两位)，请重新输入', '提示');
                return;
            } else if (parseFloat(drawmoney) < 5) {
                xxdApp.alert('单笔提现金额不得少于5元', '提示');
                return;
            } else if (parseFloat(drawmoney) == 0) {
                xxdApp.alert('请填写正确的提现金额', '提示');
                return;
            } else if (parseFloat(drawmoney) > parseFloat(couldCashMoney)) {
                xxdApp.alert('您目前的可提现金额为：' + couldCashMoney + '，提现金额不能超过可提现金额，请重新输入', '提示');
                return;
            }


            $$(".drawmoneylayers").hide();
            $$("#draw_money").attr("disabled", "disabled");
            $$("#draw_money").html("正在提交");

            $$('#drawMoney #drawmoney-form').attr('action', 'account/doWithdrawCash.do');
            $$('#drawMoney #drawmoney-form').submit();

        }
    };
    return drawmoneyCtrl;
});
