/**
 * Created by chaihuangqi on 2015/12/22.
 */
define(['js/utils/date', 'js/common/ami', 'js/account/openOnAccount'], function (DateHandle, ami, openOnAccount) {
    var productInfo = {};
    var pId;
    var isMeetTheTermOfPurchase = false;
    var sevenDaysTradeCtrl = {
        init: function () {
            xxdApp.modal({
                title: '温馨提示',
                text: '七天大胜已下架，请选择其他产品',
                buttons: [
                    {
                        text: '确定',
                        bold: true,
                        onClick: function () {
                            GS.loadPage('index/home.html');
                        }
                    }
                ]
            });
            return;

            sevenDaysTradeCtrl.eventBind();
            sevenDaysTradeCtrl.getWebappProduct();
            sevenDaysTradeCtrl.getUserAccountInfo();
        },

        eventBind: function () {
            var bindings = [
                {
                    element: 'a[name="7d_agreement"]',
                    event: 'click',
                    handler: sevenDaysTradeCtrl.sevenDaysAgreement
                },
                {
                    element: '#7d_confirmButton',
                    event: 'click',
                    handler: sevenDaysTradeCtrl.confirmClick
                },
                {
                    element: '#7d_topup_step',
                    event: 'click',
                    handler: sevenDaysTradeCtrl.topup
                },
                {
                    element: '#7d_trade_num',
                    event: 'keyup',
                    handler: sevenDaysTradeCtrl.tradeNumChange
                },
                {
                    element: '#7d_trade_num',
                    event: 'blur',
                    handler: sevenDaysTradeCtrl.blurTradeNumChange
                },
                {
                    element: '.savenDay_Ami',
                    event: 'click',
                    handler: ami.automaticMarkingInstruction
                }
            ];
            appFunc.bindEvents(bindings);
        },
        getWebappProduct: function () {
            req.callJSON({
                url: 'product/getWebappProduct.do',
                data: {
                    pCode: "QTDS"
                },
                dataType: 'json',
                success: function (result) {
                    if (result.code == 200000) {
                        var data = result.data;
                        pId = data.items.id;
                        isMeetTheTermOfPurchase = data.isMeetTheTermOfPurchase;
                        if (isMeetTheTermOfPurchase == false) {
                            $$("#7d_confirmButton").removeClass("button-001");
                            $$("#7d_confirmButton").addClass("button-001-disable");
                            sevenDaysTradeCtrl.sevenDaysModal({text: '您已经是资深用户，更多高手进阶的产品在等着你哦！', title: '提示'});
                        }
                        sevenDaysTradeCtrl.init7DaysTradeData();
                    }
                }
            });
        },
        getUserAccountInfo: function () {
            req.callJSON({
                url: 'account/getUserAccountInfo.do',
                data: {},
                success: function (result) {
                    if (result.resultCode == 0) {
                        var defaultAccount = result.defaultAccount;
                        $('#7d_accountUsable').html(appFunc.fmoney(defaultAccount.usable, 2));
                        $('#v_7dAccount').val(defaultAccount.usable);
                    } else {
                        xxdApp.addNotification({title: '抱歉', message: '获取用户信息失败，请稍后重试...', hold: 3000});
                    }
                }
            });
        },
        init7DaysTradeData: function () {
            req.callJSON({
                url: "product/getProduct.do",
                data: {
                    pCode: "QTDS",
                    pId: pId
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    if (result.code == 200000) {
                        productInfo = result.data;


                        $('#7d_investUsable').html(appFunc.fmoney(productInfo.mostInvestAmount, 2));

                        $('#v_7dMostTender').val(productInfo.mostInvestAmount);
                        $('#v_7dCanBuy').val(productInfo.mostInvestAmount);
                        $('#v_leastAmount').val(productInfo.leastInvestAmount);
                        $('#v_deployid').val(productInfo.productId);
                        $('#v_closeTerm').val(productInfo.leastPeriod);
                        $('#v_7dApr').val(productInfo.plannedAnnualRate);
                        $('#v_7dFloatApr').val(productInfo.floatingRate == undefined ? 0 : productInfo.floatingRate);

                        //$('#v_7dIsNewUser').val(productInfo.isNewUser);


                        $('#v_7dDays').val(productInfo.leastPeriod);
                        var stepsSum = parseFloat(productInfo.step);
                        $('#v_7dSteps').val(stepsSum);
                        $('#7d_trade_num').attr('placeholder', '请输入投资金额 ' + stepsSum + '的整数倍递增');

                    }
                }
            });
        },

        /** 七天大胜协议 */
        sevenDaysAgreement: function () {
            req.callGet({
                url: GC.getHtmlPath() + 'newHand/sevenDaysAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    sevenDaysTradeCtrl.showPlanAgreement({
                        result: result
                    });
                }
            });
        },
        showPlanAgreement: function (param) {
            req.callJSON({
                url: "sevenDays/getServerDate.do",
                data: {},
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if (data.resultCode == 0) {
                        var compiledTemplate = t7.compile(param.result);
                        var output = compiledTemplate({
                            leastAmount: appFunc.fmoney($('#v_leastAmount').val(), 2),
                            mostAmount: appFunc.fmoney($('#v_7dMostTender').val(), 2),
                            days: $('#v_7dDays').val(),
                            steps: appFunc.fmoney($('#v_7dSteps').val(), 2),
                            currentDate: data.currentDate,
                            endDate: data.endDate,
                            valueDate: data.valueDate
                        });
                        $(".popup-bidhistory").html(output);
                        xxdApp.popup('.popup-bidhistory');
                    } else {
                        xxdApp.alert("获取服务协议失败，请稍后重试", '提示');
                    }
                }
            });
        },

        confirmClick: function () {
            openOnAccount.isOpenOnAccount({
                title: '为应监管需要，您需要先开通银行存管账户才能顺利投资哦~',
                callBack: function () {
                    sevenDaysTradeCtrl.confirmClickEnd();
                }
            });
        },
        /** 确认投资 */
        confirmClickEnd: function () {
            if (appFunc.isRiskResult()) {
                return false;
            }
            try {
                //XXD_TRACK._trackEvent({category: "webapp_sevengold_in", action: "detail_confirm_invest", label: "确认投资", value: $('#7d_trade_num').val(), custval: "" });
                var bid = $("#v_deployid").val();
                var gaApr = $("#v_7dApr").val();
                var gaCloseTerm = $("#v_closeTerm").val();
                var ganame = "七天大胜：七天大胜";
                var categorys = "七天大胜/" + gaApr + "%/" + gaCloseTerm + "天";
                //进入结账
                CheckOut({id: bid, name: ganame, category: categorys});
            } catch (e) {
            }
            if ($$("#7d_confirmButton").hasClass("button-001-disable")) {
                return;
            }

            var agreement = $$("#7d_agreementCheck").is(':checked');
            if (!agreement) {
                xxdApp.alert("请先阅读并同意《七天大胜服务协议》、《自动配标委托书》！", "抱歉");
                return;
            }

            var tradeNum = $('#7d_trade_num').val();
            if (tradeNum == null || tradeNum == 0) {
                xxdApp.alert("转入金额不能为空，请重新输入", '提示');
                return false;
            }

            if (!appFunc.isFloat(tradeNum)) {
                xxdApp.alert("请输入正确的转入金额", '提示');
                return false;
            }

            var steps = parseFloat($('#v_7dSteps').val());
            var accountUsable = $('#v_7dAccount').val();
            var investUsable = $('#v_7dCanBuy').val();
            var leastAmount = $('#v_leastAmount').val();
            if (parseFloat(tradeNum) < parseFloat(leastAmount)) {
                xxdApp.alert("您输入的金额小于最低起投金额" + parseFloat(leastAmount) + "元，请重新输入", '提示');
                return false;
            }
            if ((tradeNum - leastAmount) % steps != 0) {
                xxdApp.alert("投资金额必须以" + steps + "的整数倍递增", '提示');
                return false;
            }
            if (parseFloat(tradeNum) > parseFloat(investUsable)) {
                xxdApp.alert("您输入的金额大于可投金额" + parseFloat(investUsable) + "元，请重新输入", '提示');
                return false;
            }
            if (parseFloat(tradeNum) > parseFloat(accountUsable)) {
                xxdApp.alert("您的可用余额不足" + parseFloat(tradeNum) + "元，请先充值", '提示');
                return false;
            }
            if (!$$("#7d_agreementCheck").is(':checked')) {
                xxdApp.alert("请阅读并同意《七天大胜服务协议》！", '提示');
                return false;
            }


            xxdApp.modalPassword('确认从您的账户扣除' + appFunc.fmoney(tradeNum, 2) + '元用以投标，请输入支付密码', '支付确认', function (password) {
                // try {XXD_TRACK._trackEvent({category: "webapp_sevengold_in", action: "popup_password", label: "支付密码确定", value: $('#7d_trade_num').val(), custval: "" });} catch (e) {}
                if (password == null || password == '') {
                    xxdApp.alert('请输入支付密码！');
                    return;
                }

                $$("#7d_confirmButton").html("转入中...");
                $$("#7d_confirmButton").removeClass("button-001");
                $$("#7d_confirmButton").addClass("button-001-disable");

                xxdApp.showIndicator('正在购买,请稍后...');
                req.callPost({
                    url: 'product/investOrder.do',
                    data: {
                        "pId": pId,
                        "pType": 94,
                        "pCategory": 0,
                        "payPwd": $.md5(password),
                        "tenderAmount": tradeNum,
                        "redEnvelopeCode": ""
                    },
                    dataType: 'json',
                    timeout: 10000,
                    success: function (result) {
                        xxdApp.hideIndicator();

                        if (result.code == 200000) {
                            var bizStatus = result.data.bizStatus;
                            if (bizStatus != undefined && bizStatus.code == 'SUCCESS') {
                                try {
                                    //交易成功
                                    var newRedAmount = "0元红包";
                                    var tradeId = result.map.tenddetailid;
                                    XXD_TRACK._trackEvent({
                                        category: "webapp_sevengold_in",
                                        action: "sevengold_success",
                                        label: "投资成功",
                                        value: tradeId,
                                        custval: "1"
                                    }); //value值改为交易id
                                    transaction({
                                        id: bid,
                                        name: ganame,
                                        category: categorys,
                                        price: tradeNum,
                                        tradeId: tradeId,
                                        revenue: tradeNum,
                                        coupon: newRedAmount
                                    });
                                } catch (e) {
                                }
//                            sevenDaysTradeCtrl.sevenDaysSuccModal({text:'恭喜您，抢购成功！', title:'提示'});

                                var data = result.data;

                                var tenderProduct = productInfo.name;
                                var tenderAmount = appFunc.fmoney(tradeNum, 2);
                                var tenderTime = DateHandle.formatDate('yyyy-MM-dd HH:mm:ss', new Date(data.createTime));
                                var startDate = DateHandle.formatDate('yyyy-MM-dd', new Date(data.createTime));
                                var startDayIsToday = "Y"; //今日计息
                                var profitAmount = $$("#7days_income").html();
                                profitAmount = appFunc.fmoney(profitAmount, 2);
                                var arrivalDate = DateHandle.formatDate('yyyy-MM-dd', new Date(data.createTime + productInfo.leastPeriod * 24 * 60 * 60 * 1000));
                                GS.loadPage("common/tenderSuccess.html?tenderProduct=" + tenderProduct + "&tenderAmount=" + tenderAmount + "&tenderTime=" + tenderTime + "&startDayIsToday=" + startDayIsToday + "&startDate=" + startDate + "&profitAmount=" + profitAmount + "&arrivalDate=" + arrivalDate);
                            } else {
                                xxdApp.alert(bizStatus.message,"提示");
                            }
                        } else {
                            try {
                                XXD_TRACK._trackEvent({
                                    category: "webapp_sevengold_in",
                                    action: "sevengold_success",
                                    label: "投资失败",
                                    value: tradeNum,
                                    custval: "0"
                                });
                            } catch (e) {
                            }

                            var msg = result.message;
                            if(result.data != undefined && result.data.bizStatus != undefined && result.data.bizStatus.code != 'SUCCESS') {
                                msg = result.data.bizStatus.message;
                            }


                            xxdApp.alert(msg, '提示');
                        }
                        $$("#7d_confirmButton").removeClass("button-001-disable");
                        $$("#7d_confirmButton").addClass("button-001");
                        $$("#7d_confirmButton").html("确认投资");
                    }
                });
            }, function (password) {
                //try {XXD_TRACK._trackEvent({category: "webapp_sevengold_in", action: "popup_cancel", label: "支付密码取消", value: "", custval: "" });} catch (e) {}

                if (password == null || password == '') {
                    return;
                }
            });

        },
        topup: function () {
            //try {XXD_TRACK._trackEvent({category: "webapp_sevengold_in", action: "detail_recharge", label: "充值", value: "", custval: "" });} catch (e) {}
            GS.goTopup();
        },
        tradeNumChange: function (key) {
            var tradeNum = $('#7d_trade_num').val().replace(/[^\d\.]/g, '');
            $('#7d_trade_num').val(tradeNum);
            sevenDaysTradeCtrl.calIncome(tradeNum);
            $('#7d_affirm_money').html(appFunc.fmoney(tradeNum, 2));
        },

        blurTradeNumChange: function () {
            //try {XXD_TRACK._trackEvent({category: "webapp_sevengold_in", action: "detail_invest_money", label: "投资金额", value: $('#7d_trade_num').val(), custval: "" });} catch (e) {}

        },

        calIncome: function (sum) {
            if (sum != '' && sum > 0) {
                var apr = parseFloat($('#v_7dApr').val());
                var days = parseFloat($('#v_7dDays').val());
                //var isNewUser =  $('#v_7dIsNewUser').val();
                if (isMeetTheTermOfPurchase == true) {
                    var floatAprStr = $('#v_7dFloatApr').val();
                    if (floatAprStr != undefined && floatAprStr != '') {
                        var floatApr = parseFloat(floatAprStr);
                        apr = apr + floatApr;
                    }

                }
                var inCome = Math.floor(sum * apr * days * 100 / (360 * 100)) / 100;
                $('#7days_income').html(appFunc.fmoney(inCome, 2));
            }
        },
        sevenDaysSuccModal: function (options) {
            xxdApp.modal({
                title: options.title,
                text: options.text,
                buttons: [
                    {
                        text: '其他产品',
                        onClick: function () {
                            GS.loadPage('popular/financesList.html?path=popular');
                        }
                    },
                    {
                        text: '投资记录',
                        bold: true,
                        onClick: function () {
                            GS.loadPage('account/sevenDaysRecord.html?path=account');
                        }
                    }
                ]
            })
        },
        sevenDaysModal: function (options) {
            xxdApp.modal({
                title: options.title,
                text: options.text,
                buttons: [
                    {
                        text: '取消',
                        onClick: function () {

                        }
                    },
                    {
                        text: '去选购',
                        bold: true,
                        onClick: function () {
                            GS.loadPage('popular/financesList.html?path=popular');
                        }
                    }
                ]
            })
        }
    };
    return sevenDaysTradeCtrl;
});
