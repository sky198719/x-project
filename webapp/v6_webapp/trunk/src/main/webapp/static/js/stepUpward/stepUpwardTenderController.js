/**
 * 投标
 */
define(['js/utils/date', 'js/common/productUtil', 'js/common/ami', 'js/utils/dayController', 'js/utils/xxd_dmp', 'js/account/openOnAccount'], function (DateHandle, productUtil, ami, dayController, xxd_dmp, openOnAccount) {
    var stepId = "";
    var stepName = "";
    var category = "";
    var productInfo = {};
    var choosedRedPacket = null;
    var serverTime;
    var activityStartDate;
    var activityEndDate;
    var stepUpwardTenderCtrl = {
        init: function (event) {
            if (productUtil.stepUpwardShowOrNot()) {
                //stepUpwardTenderCtrl.getProductInfo();
                req.callGet({
                    url: 'product/getWebappProduct.do',
                    data: {
                        pCode: "BBGS"
                    },
                    dataType: 'json',
                    async: false,
                    success: function (result) {
                        if (result.code == 200000) {
                            var data = result.data;
                            stepId = data.items.stepid;
                            stepUpwardTenderCtrl.getNewProductInfo();
                            serverTime = result.serverTime;
                            activityStartDate = result.data.items.activityStartDate;
                            activityEndDate = result.data.items.activityEndDate;
                        }
                    }
                });

            } else {
                $$("#confirmTender").removeClass("active").addClass("button-51-disable");
                xxdApp.alert('步步高升产品暂已停售', '提示');
            }

            stepUpwardTenderCtrl.bindEvent();
        },

        bindEvent: function () {
            var bindings = [
                {
                    element: '#confirmTender',
                    event: 'click',
                    handler: stepUpwardTenderCtrl.newConfirmTender
                },
                {
                    element: '.stepUpwardTender #tenderAmount',
                    event: 'keyup',
                    handler: stepUpwardTenderCtrl.calcAmount
                },
                {
                    element: '#topup_step',
                    event: 'click',
                    handler: stepUpwardTenderCtrl.topup
                },
                {
                    element: '.stepUpwardTender .stepUpwardAgreementPopup',
                    event: 'click',
                    handler: stepUpwardTenderCtrl.loadMonthFinanceAgreement
                },
                {
                    element: '#tenderAmount',
                    event: 'blur',
                    handler: stepUpwardTenderCtrl.blurtenderAmount
                },
                {
                    element: '.stepUpwardTender .stepUpwardAmi',
                    event: 'click',
                    handler: ami.automaticMarkingInstruction
                }
            ];

            appFunc.bindEvents(bindings);
        },
        blurtenderAmount: function () {
            //XXD_TRACK._trackEvent({category:"webapp_step_in",action:"invest_amount",label:"投资金额",value:$$(".stepUpwardTender #tenderAmount").val(),custval:""});
        },

        newConfirmTender: function () {
            openOnAccount.isOpenOnAccount({
                title: '为应监管需要，您需要先开通银行存管账户才能顺利投资哦~',
                callBack: function () {
                    if (appFunc.isRiskResult()) {
                        return false;
                    }
                    stepUpwardTenderCtrl.newConfirmTenderEnd();
                }
            });
        },

        newConfirmTenderEnd: function () {
            if ($(this).hasClass("button-51-disable")) {
                return;
            }

            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            var stepUpwardId = $$(".stepUpwardTender #stepUpwardId").val();

            //投标金额
            var tenderAmount = $$(".stepUpwardTender #tenderAmount").val();
            if (!tenderAmount) {
                xxdApp.alert('请填写投标金额', '提示');
                return false;
            }
            if (!appFunc.isFloat(tenderAmount)) {
                xxdApp.alert('请正确填写投标金额', '提示');
                return false;
            }
            if (tenderAmount <= 0) {
                xxdApp.alert('投标金额须大于零', '提示');
                return false;
            }

            if (stepUpwardTenderCtrl.checkTenderAmount(tenderAmount) == false) {
                return false;
            }

            //最小投资金额
            var tenderMinAmount = $('.stepUpwardTender #tenderMinAmount').val();
            //**的整数倍递增
            var tenderIncreaseRadix = $('.stepUpwardTender #tenderIncreaseRadix').val();
            var tempTenderAmount = tenderAmount - tenderMinAmount;
            if (tenderIncreaseRadix != "" && tenderIncreaseRadix != 0) {
                if (tempTenderAmount % tenderIncreaseRadix != 0) {
                    xxdApp.alert("投资金额须为" + tenderIncreaseRadix + "的整数倍！", '提示');
                    return false;
                }
            }

            //实际支付
            var realPayAmount = $$(".stepUpwardTender #realPayAmount").val();

            var redCode = "";
            if (choosedRedPacket != null) {
                redCode = choosedRedPacket.redCode;
            }
            try {
                XXD_TRACK._trackEvent({
                    category: "webapp_step_in",
                    action: "detail_confirm_invest",
                    label: "确认投资",
                    value: $$(".stepUpwardTender #tenderAmount").val(),
                    custval: ""
                });
                CheckOut({id: stepId, name: stepName, category: category});
            } catch (e) {
            }
            xxdApp.modalPassword('确认从您的账户扣除' + appFunc.fmoney(realPayAmount, 2) + '元用以购买产品，请输入支付密码', '支付确认', function (password) {
                try {
                    XXD_TRACK._trackEvent({
                        category: "webapp_step_in",
                        action: "popup_affirm_paypass",
                        label: "支付密码确定",
                        value: "",
                        custval: ""
                    });
                } catch (e) {
                }
                if (password == null || password == '') {
                    xxdApp.alert('请输入支付密码！', '提示');
                    return;
                }
                $$("#confirmTender").attr("disabled", "disabled");
                $$("#confirmTender").html("投标中...");


                xxdApp.showIndicator('正在努力投标...');
                req.callPost({
                    url: 'product/investOrder.do',
                    data: {
                        "pId": stepUpwardId,
                        "pType": 96,
                        "pCategory": 0,
                        "payPwd": $.md5(password),
                        "tenderAmount": tenderAmount,
                        "redEnvelopeCode": redCode
                    },
                    dataType: 'json',
                    timeout: 20000,
                    success: function (result) {
                        xxdApp.hideIndicator();
                        $$("#confirmTender").removeAttr("disabled");
                        $$("#confirmTender").html("确认投资");


                        if (result.code == 200000) {
                            var bizStatus = result.data.bizStatus;
                            if (bizStatus != undefined && bizStatus.code == 'SUCCESS') {
                                try {
                                    //交易成功
                                    var tradeId = result.data;
                                    var newRedAmount = "0元红包";
                                    XXD_TRACK._trackEvent({
                                        category: "webapp_step_in",
                                        action: "step_in_success",
                                        label: "投资成功",
                                        value: tradeId,
                                        custval: "1"
                                    });  //value值改为交易id
                                    transaction({
                                        id: stepId,
                                        name: stepName,
                                        category: category,
                                        price: tenderAmount,
                                        tradeId: tradeId,
                                        revenue: tenderAmount,
                                        coupon: newRedAmount
                                    });
                                } catch (e) {
                                }

                                var data = result.data;

                                var tenderProduct = productInfo.name;
                                var tenderAmount1 = appFunc.fmoney(tenderAmount, 2);
                                var tenderTime = DateHandle.formatDate('yyyy-MM-dd HH:mm:ss', new Date(data.startDate));
                                var startDate = DateHandle.formatDate('yyyy-MM-dd', new Date(data.startDate));
                                var startDayIsToday = "Y"; //今日计息
                                var floatapr = productInfo.floatingRate;
                                var minApr = parseFloat(productInfo.plannedAnnualRateFrom) + parseFloat(floatapr);
                                var maxApr = parseFloat(productInfo.plannedAnnualRateTo) + parseFloat(floatapr);
                                var activity_url = result.activity_url;
                                GS.loadPage("stepUpward/tenderSuccess.html?tenderProduct=" + tenderProduct + "&tenderAmount=" + tenderAmount1 + "&tenderTime=" + tenderTime + "&startDayIsToday=" + startDayIsToday + "&startDate=" + startDate + "&minApr=" + minApr + "&maxApr=" + maxApr+'&activity_url='+activity_url);
                            } else {
                                xxdApp.alert(bizStatus.message,"提示");
                            }
                        } else {
                            var msg = result.message;
                            if(result.data != undefined && result.data.bizStatus != undefined && result.data.bizStatus.code != 'SUCCESS') {
                                msg = result.data.bizStatus.message;
                            }
                            xxdApp.alert(msg, '提示');
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        console.log("投标可能发生异常，请查看投标记录或稍后再试,ajax error...");
                        xxdApp.hideIndicator();
                        $$("#confirmTender").removeAttr("disabled");
                        $$("#confirmTender").html("确认投资");
                        xxdApp.hidePreloader();
                        xxdApp.alert('系统无响应，请查看投标记录是否投标成功或者重新投标', '抱歉', function () {
                            GS.loadPage("popular/financesList.html");
                        });
                    }
                });

            }, function (password) {
                try {
                    XXD_TRACK._trackEvent({
                        category: "webapp_step_in",
                        action: "popup_affirm_cancel",
                        label: "支付密码取消",
                        value: "",
                        custval: ""
                    });
                } catch (e) {
                }
                if (password == null || password == '') {
                    return;
                }
            });
        },
        getNewProductInfo: function () {
            req.callJSON({
                url: 'product/getProduct.do',
                data: {
                    pCode: 'BBGS',
                    pId: stepId
                },
                indicator: true,
                success: function (result) {
                    if (result.code == 200000) {
                        var resultData = result.data;
                        productInfo = resultData;
                        //产品ID
                        $$('.stepUpwardTender #stepUpwardId').val(resultData.productId);
                        //产品名称 和 期号
                        $$('.stepUpwardTender #itemName').html(resultData.name);
                        //产品剩余可投
                        $$(".stepUpwardTender #tenderRemainderAmount").val(resultData.leftAmount);
                        $$(".stepUpwardTender #tenderRemainderAmountShow").html(appFunc.fmoney(resultData.leftAmount, 2));
                        //产品最小投标金额
                        $$(".stepUpwardTender #tenderMinAmount").val(resultData.leastInvestAmount);
                        $$(".stepUpwardTender #tenderMinAmountShow").html(resultData.leastInvestAmount);
                        //产品最大投标金额
                        $$(".stepUpwardTender #tenderMaxAmount").val(resultData.mostInvestAmount);
                        $$(".stepUpwardTender #tenderMaxAmountShow").html(resultData.mostInvestAmount / 10000);
                        //开放总额
                        $$(".stepUpwardTender #tenderTotalAmount").val(resultData.plannedAmount);
                        //增加基数
                        $$(".stepUpwardTender #tenderIncreaseRadix").val(resultData.step);
                        //利率
                        var minApr = parseFloat(resultData.plannedAnnualRateFrom);
                        var maxApr = parseFloat(resultData.plannedAnnualRateTo);
                        $$(".stepUpwardTender #tenderMinAprShow").html(minApr);
                        $$(".stepUpwardTender #tenderMaxAprShow").html(maxApr);
                        //投资金额输入框提示语
                        $(".stepUpwardTender #tenderAmount").attr("placeholder", "请输入投资金额 " + resultData.step + "的整数倍递增");

                        stepId = resultData.stepId;
                        stepName = "步步高升：步步高升";
                        category = "步步高升/" + minApr + "%~" + maxApr + "%/1个月";

                        var tenderButton = $$("#confirmTender");
                        //是否已满额
                        if (parseFloat(resultData.leftAmount) == 0 || parseFloat(resultData.leftAmount) < parseFloat(resultData.leastInvestAmount)) {
                            tenderButton.removeClass("button-51");
                            tenderButton.addClass("button-51-disable");
                            tenderButton.html("已抢光");
                        }
                        stepUpwardTenderCtrl.getUserStepUpwardInfo();
                        stepUpwardTenderCtrl.getUserAccountInfo();


                        try {
                            var serverTime = serverTime == undefined ? new Date().getTime() : DateHandle.parseDate(serverTime).getTime();

                            if(activityStartDate < serverTime && serverTime < activityEndDate ){
                                req.callJSON({
                                    url: 'product/activityLabel.do',
                                    data: {
                                        productId:resultData.productId
                                    },
                                    dataType: 'json',
                                    success:function(result1) {
                                        if(result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                                            var remark = result1.data.data.remark;
                                            $$(".activityLabel_step_tender").html("活动奖励：<span style='background-color: #ff7365;padding: 2px 10px;width: 79%;text-align: center;border-radius:2px;color:#fff;'>"+remark+'</span>');
                                            $$(".activityLabel_step_tender").show();
                                        }
                                    }
                                });
                            }

                        }catch (e) {
                            console.log(e);
                        }

                    } else {
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: '获取步步高升产品信息失败，请稍后重试...'
                        });
                    }
                }
            });
        },

        getUserAccountInfo: function () {
            req.callJSON({
                url: 'stepUpward/getUserAccountInfo.do',
                data: {},
                indicator: true,
                success: function (result) {
                    try {
                        if (result.resultCode == 0) {
                            var defaultAccount = result.defaultAccount;
                            var redList = result.redList;
                            //账户余额
                            if (defaultAccount != null && defaultAccount != "") {
                                $$(".stepUpwardTender #showUserAccount").html(appFunc.fmoney(defaultAccount.usable, 2));
                                $$(".stepUpwardTender #userAccount").val(defaultAccount.usable);
                            }
                            stepUpwardTenderCtrl.setRedPacket(redList);
                        } else {
                            xxdApp.addNotification({
                                title: '温馨提示',
                                hold: 3000,
                                message: '获取用户信息失败，请稍后重试...'
                            });
                        }
                    } catch (e) {
                        console.log(e.message);
                        xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取用户信息失败，请稍后重试...'
                        });
                    }
                },
                error: function (xhr, type) {
                    console.log("获取用户信息失败,ajax error...");
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '获取用户信息失败，请稍后重试...'
                    });
                }
            });
        },
        getUserStepUpwardInfo: function () {
            var userStepAccount = productUtil.getUserStepUpwardInfo();
            //本人剩余可投
            var userMostTender = $$(".stepUpwardTender #tenderMaxAmount").val();
            var maxTenderAmount = parseFloat(userMostTender) - parseFloat(userStepAccount.remaCapitalTotal);
            var tenderButton = $$("#confirmTender");
            //个人额度是否已满
            if (maxTenderAmount <= 0) {
                $$('.stepUpwardTender #availableLimit').val(0);
                $$('.stepUpwardTender #availableLimitShow').html(appFunc.fmoney(0, 2));
                tenderButton.removeClass("button-51");
                tenderButton.addClass("button-51-disable");
                tenderButton.html("个人限额已满");
            } else {
                $$('.stepUpwardTender #availableLimit').val(maxTenderAmount);
                $$('.stepUpwardTender #availableLimitShow').html(appFunc.fmoney(maxTenderAmount, 2));
            }
        },
        setRedPacket: function (redList) {
            var redKeys = [];
            var redDisplayValues = [];
            redKeys[0] = "";
            redDisplayValues[0] = "不使用红包";
            for (var i = 0; i < redList.length; i++) {
                var b = redList[i];
                redKeys[i + 1] = b.redCode;
                redDisplayValues[i + 1] = b.name;
            }

            var pickerRed = xxdApp.picker({
                input: '#chooseRedPacket',
                rotateEffect: true,
                toolbarTemplate: '<div class="toolbar">' +
                '<div class="toolbar-inner">' +
                '<div class="left"><a href="#" class="link toolbar-randomize-link">取消</a></div>' +
                '<div class="right">' +
                '<a href="#" class="link close-picker">确定</a>' +
                '</div>' +
                '</div> ' +
                '</div> ',
                cols: [
                    {
                        textAlign: 'center',
                        values: redDisplayValues
                    }
                ],
                onChange: function (p, value, displayValue) {
                    for (var i = 0; i < redDisplayValues.length; i++) {
                        if (redDisplayValues[i] == value) {
                            $$("#chooseRedPacket").val(redDisplayValues[i]);
                            if (i == 0) {
                                choosedRedPacket = null;
                            } else {
                                choosedRedPacket = redList[i - 1];
                            }
                            stepUpwardTenderCtrl.calcAmount();
                            break;
                        }
                    }
                },
                onOpen: function (picker) {
                    picker.container.find('.toolbar-randomize-link').on('click', function () {
                        picker.close();
                    });
                }
            });

            choosedRedPacket = null;
        },
        //加载服务协议
        loadMonthFinanceAgreement: function () {
            req.callGet({
                url: GC.getHtmlPath() + 'stepUpward/stepUpwardAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                indicator: true,
                success: function (result) {
                    $$(".popup-bidhistory").html(result);
                    var floatapr = productInfo.floatapr;
                    var minApr = parseFloat(productInfo.minApr) + parseFloat(floatapr);
                    var maxApr = parseFloat(productInfo.maxApr) + parseFloat(floatapr);
                    //$$("#stepUpwardAgreement .agreementItemName").html(productInfo.sname); 					//计划名称					---->	步步高升
                    $$("#stepUpwardAgreement .agreementTenderStartAmount").html(productInfo.userLowestTender);		//最低加入金额				---->	100
                    $$("#stepUpwardAgreement .agreementTenderIncreaseRadix").html(productInfo.addSteps);	//**的整数倍递增				---->	100
                    $$("#stepUpwardAgreement .agreementTenderMost").html(productInfo.userMostTender / 10000);			//最高投资金额				---->	100000
                    $$("#stepUpwardAgreement .agreementMinApr").html(minApr);				//
                    $$("#stepUpwardAgreement .agreementTwoMonthApr").html(minApr + productInfo.stepApr);				//
                    $$("#stepUpwardAgreement .agreementStepApr").html(productInfo.stepApr);					//
                    $$("#stepUpwardAgreement .agreementMaxApr").html(maxApr);						//
                    $$("#stepUpwardAgreement .agreementQuitIncreaseRadix").html(productInfo.quitSteps);						//

                    xxdApp.popup('.popup-bidhistory');
                }
            });
        },

        checkTenderAmount: function (tenderAmount) {
            //账户余额
            var userAccount = $$(".stepUpwardTender #userAccount").val();
            //最小投资金额
            var tenderMinAmount = $('.stepUpwardTender #tenderMinAmount').val();
            //本人剩余可投金额
            var availableLimit = $('.stepUpwardTender #availableLimit').val();
            //最大投资金额
            var tenderMaxAmount = $('.stepUpwardTender #tenderMaxAmount').val();
            //产品剩余可投金额
            var tenderRemainderAmount = $('.stepUpwardTender #tenderRemainderAmount').val();

            var realPayAmount = 0;
            if (choosedRedPacket == null) {
                realPayAmount = tenderAmount;
            } else {
                realPayAmount = parseFloat(tenderAmount) - parseFloat(choosedRedPacket.faceValue);
                //投资金额须大于红包金额
                if (parseFloat(realPayAmount) == 0) {
                    xxdApp.alert('投资金额须大于红包金额', '提示');
                    return false;
                }
                if (parseFloat(tenderAmount) < choosedRedPacket.quota) {
                    xxdApp.alert('使用所选红包，投资金额须大于等于' + choosedRedPacket.quota + "元", '提示');
                    return false;
                }
            }

            //账户余额
            if (parseFloat(userAccount) < parseFloat(realPayAmount)) {
                xxdApp.alert('账户余额不足，请充值', '提示');
                return false;
            }

            //最小投资金额
            //当剩余可投金额小于投标限额，投资金额可以小于投标限额
            //if (parseFloat(tenderRemainderAmount) >= parseFloat(tenderMinAmount)) {
            if (parseFloat(tenderAmount) < parseFloat(tenderMinAmount)) {
                xxdApp.alert(tenderMinAmount + "元起投，请修改金额", '提示');
                return false;
            }
            //}
            //本人剩余可投金额
            if (parseFloat(tenderAmount) > parseFloat(availableLimit)) {
                xxdApp.alert("最大可投" + availableLimit + "元，请修改金额", '提示');
                return false;
            }
            //最大投资金额
            if (parseFloat(tenderAmount) > parseFloat(tenderMaxAmount)) {
                xxdApp.alert("最大投资金额不超过" + appFunc.fmoney(tenderMaxAmount, 2) + "元", '提示');
                return false;
            }
            //产品剩余可投金额
            if (parseFloat(tenderAmount) > parseFloat(tenderRemainderAmount)) {
                xxdApp.alert("投资金额大于该产品的剩余可投金额！", '提示');
                return false;
            }

            if (!$$(".stepUpwardTender #agreement").is(':checked')) {
                xxdApp.alert("请阅读并同意《步步高升服务协议》！", '提示');
                return false;
            }
        },

        //计算历史收益和实际支付金额
        calcAmount: function () {
            //投标金额
            var tenderAmount = $$(".stepUpwardTender #tenderAmount").val();
            var realPayAmount = 0;
            if (!appFunc.isFloat(tenderAmount)) {
                $$(".stepUpwardTender #realPayAmount").val(realPayAmount);
                $$(".stepUpwardTender #realPayAmountShow").html(appFunc.fmoney(realPayAmount, 2));
                return false;
            }
            if (choosedRedPacket == null) {
                realPayAmount = tenderAmount;
            } else {
                realPayAmount = parseFloat(tenderAmount) - parseFloat(choosedRedPacket.faceValue);
            }

            if (realPayAmount < 0) {
                realPayAmount = 0;
            }
            //实际支付
            $$(".stepUpwardTender #realPayAmount").val(realPayAmount);
            $$(".stepUpwardTender #realPayAmountShow").html(appFunc.fmoney(realPayAmount, 2));
        },

        topup: function () {
            GS.goTopup();
        }
    };

    return stepUpwardTenderCtrl;
});
