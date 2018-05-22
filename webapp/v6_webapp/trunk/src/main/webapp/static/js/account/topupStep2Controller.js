/**
 * 充值第二步
 */
define(['js/account/topupStep2View','js/personal/bankUtil'], function (topupStep2View,bankUtil) {
    var topupStep2Ctrl = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);
            var moneyOrder = query.moneyOrder;
            $$('#topupStep2 #money-order').val(moneyOrder);

            // 获取用户银行卡列表
            this.getUserBankList();

            var bindings = [
                {
                    element: '#topupStep2 .button-51',
                    event: 'click',
                    handler: topupStep2Ctrl.recharge
                }
            ];

            topupStep2View.init({bindings: bindings});
        },

        recharge: function () {

            var moneyOrder = $$('#topupStep2 #money-order').val();
            var cardNo = $$('#topupStep2 #card-no').val();
            var bankId = $$('#topupStep2 #v_bank_id').val();
            var bankCode = $$('#topupStep2 #v_bank_code').val();
            if (moneyOrder == '') {
                xxdApp.alert('充值金额不能为空', '提示');
                return false;
            }
            if(cardNo=='') {
                if (bankId == undefined||bankId == '') {
                    xxdApp.alert('请先选择银行卡或添加一张新卡', '提示');
                    return false;
                }
            }

            $$("#topupStep2 .button-51").attr("disabled","disabled");
            if(cardNo=='') {
                topupStep2Ctrl.getMobilePayChannel(bankCode,moneyOrder);
            }else{
                var result = bankUtil.bankCardCheck({cardNo:cardNo});

                if (result.code == 404) {
                    $$("#topupStep2 .button-51").removeAttr("disabled");
                    xxdApp.loginScreen();
                    return;
                }

                if (result.code != 0) {
                    $$("#topupStep2 .button-51").removeAttr("disabled");
                    xxdApp.alert(result.info, "抱歉");
                    return;
                }
                req.callPost({
                    url: 'mobilePay/getBankCodeByCardNo.do',
                    data: {
                        card_no:cardNo
                    },
                    dataType: 'json',
                    success: function (result) {
                        var code = result.data;
                        topupStep2Ctrl.getMobilePayChannel(code,moneyOrder)
                    }
                });
            }

        },
        getMobilePayChannel: function (bankCode,moneyOrder) {
            // 获取充值通道---38:连连50:富友
            req.callPost({
                url: 'mobilePay/getMobilePayChannel.do',
                data: {
                    bankCode:bankCode,
                    amount:moneyOrder
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    $$("#topupStep2 .button-51").removeAttr("disabled");
                    var data = result.data;
                    if (result.code == 0 && data!=null) {
                        if (data == "50") {
                           $$('#topupStep2 #recharge-form').attr('action', 'fuiou/paySubmit.do');
                        } else if (data == "38") {
                           $$('#topupStep2 #recharge-form').attr('action', 'lianlian/paySubmit.do');
                        } else {
                            xxdApp.alert("抱歉，充值现在不可用，请重新尝试或者联系客服。", "提示");
                        }
                        $$('#topupStep2 #recharge-form').submit();
                    } else {
                        xxdApp.alert(result.info, "提示");
                    }
                }
            });
        },
        getUserBankList: function () {

            req.callPost({
                url: 'personal/userBankList.do',
                data: {},
                dataType: 'json',
                success: function (result) {
                    var resultData = result.resultData;
                    result = result ? result : {resultCode: -400, msg: "系统返回错误"};
                    if (result.resultCode == 0) {
                        var userBankList = [];
                        for (var k = 0; k < resultData.length; k++) {
                            var b = resultData[k];
                            userBankList.push({
                                "bankId": b.bankId,
                                "bankCode": b.bankCode,
                                "bankName": b.bankName,
                                "bankAccount": b.bankAccount
                            });
                        }
                    } else if (result.data == -1) {
                        xxdApp.alert("未登录");
                    }
                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'account/topupStep2List.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                topupStep2View.showListItem({result:result,userBankList:userBankList});
                                topupStep2Ctrl.userBankListBindEvent();
                                // 加载完毕需要重置
                                xxdApp.pullToRefreshDone();
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }
                }
            });

        },
        userBankListBindEvent: function () {
            var bindings = [
                {
                    element: '#topupStep2 .topup_bankList',
                    event: 'click',
                    handler: topupStep2Ctrl.clickUserBank
                },
                {
                    element:'#topupTobank',
                    event:'click',
                    handler:topupStep2Ctrl.addBank
                }
            ];

            topupStep2View.bindEvents({
                    bindings: bindings
                }
            );
        },

        addBank:function(){
            GS.loadPage("personal/addBank.html?path=personal");
        },
        clickUserBank: function () {
            var bankId = $$(this).attr('data-id');
            var bankCode = $$(this).attr('data-code');
            $('#v_bank_id').val(bankId);
            $('#v_bank_code').val(bankCode);
            topupStep2Ctrl.recharge();
        }
    };
    return topupStep2Ctrl;
});
