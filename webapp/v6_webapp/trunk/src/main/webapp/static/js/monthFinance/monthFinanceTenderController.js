/**
 * 投标
 */
define(['js/borrowTender/borrowTenderView', 'js/utils/date', 'js/utils/dayController', 'js/common/ami', 'js/account/openOnAccount'], function (BorrowTenderView, DateHandle, dayController, ami, openOnAccount) {
    var itemName = "";
    var applyJoinTime = "";
    var joinAmount = "";
    var itemDays = "";
    var itemLockDays = "";
    var itemMinAmount = "";
    var increaseRadix = "";
    var itemMaxAmount = "";
    var limitTimeDate = "";
    var calcDate = "";
    var productInfo = {};
    var pId;
    var monthFinanceTenderCtrl = {
        init: function (event) {
            monthFinanceTenderCtrl.getWebappProduct();
            monthFinanceTenderCtrl.getUserAccountInfo();
            monthFinanceTenderCtrl.borrowBindEvent();
        },

        borrowBindEvent: function () {
            var bindings = [
                {
                    element: '#confirmTender',
                    event: 'click',
                    handler: monthFinanceTenderCtrl.confirmTender
                },
                {
                    element: '#tenderAmount',
                    event: 'keyup',
                    handler: monthFinanceTenderCtrl.calcAmount
                },
                {
                    element: '#tenderAmount',
                    event: 'blur',
                    handler: monthFinanceTenderCtrl.blurCalcAmount
                },
                {
                    element: '#topup_step',
                    event: 'click',
                    handler: monthFinanceTenderCtrl.topup
                },
                {
                    element: '.monthFinanceTender .monthFinanceAgreementPopup',
                    event: 'click',
                    handler: monthFinanceTenderCtrl.loadMonthFinanceAgreement
                },
                {
                    element: '.monthFinancedAmi',
                    event: 'click',
                    handler: ami.automaticMarkingInstruction

                }
            ];

            BorrowTenderView.bindEvent({
                    bindings: bindings
                }
            );
        },

        getWebappProduct: function () {
            req.callJSON({
                url: 'product/getWebappProduct.do',
                data: {
                    pCode: "YJDJ"
                },
                dataType: 'json',
                success: function (result) {
                    if (result.code == 200000) {
                        var data = result.data;
                        pId = data.items.id;
                        monthFinanceTenderCtrl.getNewProductInfo();
                    }
                }
            });
        },
        getNewProductInfo: function () {
            req.callJSON({
                url: 'product/getProduct.do',
                data: {
                    pCode: 'YJDJ',
                    pId: pId
                },
                indicator: true,
                success: function (result) {
                    if (result.code == 200000) {
                        var resultData = result.data;
                        productInfo = resultData;
                        //产品ID
                        $('.monthFinanceTender #monthFinanceId').val(resultData.productId);
                        //产品名称 和 期号
                        $('.monthFinanceTender #itemName').html(resultData.name + "-" + resultData.periodName);
                        //本人剩余可投
                        var maxTenderAmount = resultData.investableAmount;
                        $('.monthFinanceTender #availableLimit').val(maxTenderAmount);
                        $('.monthFinanceTender #availableLimitShow').html(appFunc.fmoney(maxTenderAmount, 2));
                        //产品剩余可投
                        $$(".monthFinanceTender #tenderRemainderAmount").val(resultData.leftAmount);
                        //产品最小投标金额
                        $$(".monthFinanceTender #tenderMinAmount").val(resultData.leastInvestAmount);
                        //产品最大投标金额
                        $$(".monthFinanceTender #tenderMaxAmount").val(resultData.mostTender);
                        //开放总额
                        $$(".monthFinanceTender #tenderTotalAmount").val(resultData.plannedAmount);
                        //增加基数
                        $$(".monthFinanceTender #tenderIncreaseRadix").val(resultData.step);
                        //利率
                        $$(".monthFinanceTender #tenderApr").val(resultData.plannedAnnualRate);
                        //期限
                        $$(".monthFinanceTender #tenderDays").val(resultData.leastPeriod);
                        //投资金额输入框提示语
                        $(".monthFinanceTender #tenderAmount").attr("placeholder", "请输入投资金额 " + resultData.step + "的整数倍递增");

                        itemName = resultData.name + " - " + resultData.periodName;
                        applyJoinTime = DateHandle.formatDate("yyyy/MM/dd", new Date(), null);
                        joinAmount = "";
                        itemDays = resultData.leastPeriod;
                        itemLockDays = resultData.leastPeriod;
                        itemMinAmount = resultData.leastInvestAmount;
                        increaseRadix = resultData.step;
                        itemMaxAmount = resultData.mostTender;
                        var date = new Date();
                        var newtimems = date.getTime() + ((itemDays - 1) * 24 * 60 * 60 * 1000);
                        date.setTime(newtimems);
                        limitTimeDate = DateHandle.formatDate("yyyy年MM月dd日", new Date(), null) + "至" + DateHandle.formatDate("yyyy年MM月dd日", date, null);
                        newtimems = newtimems + 1 * 24 * 60 * 60 * 1000;
                        date.setTime(newtimems);
                        calcDate = DateHandle.formatDate("yyyy年MM月dd日", date, null); //结算日

                        var currentClientTime = new Date().getTime();
                        var serverTime = result.serverTime.replace(/\-/g, "/");
                        var currentServerTime = new Date(serverTime).getTime();
                        var startTime = resultData.activitedStartDate;
                        var endTime = resultData.activitedEndDate;

                        var tenderButton = $$("#confirmTender");
                        //未到开始时间
                        if (currentServerTime <= new Date(startTime).getTime()) {
                            tenderButton.removeClass("button-51");
                            tenderButton.addClass("button-51-disable");
                            tenderButton.html("等待发售");
                        }
                        //已到结束时间
                        else if (currentServerTime >= new Date(endTime).getTime()) {
                            tenderButton.removeClass("button-51");
                            tenderButton.addClass("button-51-disable");
                            tenderButton.html("已抢光");
                        }
                        //已到开始时间 未到结束时间
                        else {
                            //是否已满额
                            if (parseFloat(resultData.leftAmount) == 0 || parseFloat(resultData.leftAmount) < parseFloat(resultData.leastInvestAmount)) {
                                tenderButton.removeClass("button-51");
                                tenderButton.addClass("button-51-disable");
                                tenderButton.html("已抢光");
                            } else {
                                //个人额度是否已满
                                if (maxTenderAmount <= 0) {
                                    tenderButton.removeClass("button-51");
                                    tenderButton.addClass("button-51-disable");
                                    tenderButton.html("个人限额已满");
                                }
                            }
                        }

                    } else {
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: '获取月进斗金产品信息失败，请稍后重试...'
                        });
                    }
                    //monthFinanceTenderCtrl.loadMonthFinanceAgreement();
                }
            });
        },

        getUserAccountInfo: function () {
            req.callGet({
                url: 'account/getUserAccountInfo.do',
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result.resultCode == 0) {
                        //账户余额
                        if (result.defaultAccount != null && result.defaultAccount != "") {
                            $$(".monthFinanceTender #showUserAccount").html(appFunc.fmoney(result.defaultAccount.usable, 2));
                            $$(".monthFinanceTender #userAccount").val(result.defaultAccount.usable);
                        }
                    }
                }
            });
        },


        //加载服务协议
        loadMonthFinanceAgreement: function () {
            req.callGet({
                url: GC.getHtmlPath() + 'monthFinance/monthFinanceAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                indicator: true,
                success: function (result) {
                    $$(".popup-bidhistory").html(result);
                    //            var tenderAmount = $$("#tenderAmount").val();
                    $$("#monthFinanceAgreement .itemName").html(itemName); 				//计划名称					---->	月进斗金
                    //            $$("#monthFinanceAgreement .applyJoinTime").html(applyJoinTime); 	//申请加入时间（年月日）		---->	2015/11/25
                    //            $$("#monthFinanceAgreement .joinAmount").html(tenderAmount);		//加入金额（单位：人民币/元）	---->	5000
                    $$("#monthFinanceAgreement .itemDays").html(itemDays);			//合约期限（日;包含节假日）	---->	31
                    $$("#monthFinanceAgreement .itemLockDays").html(itemLockDays);			//锁定期（日;包含节假日）		---->	30
                    $$("#monthFinanceAgreement .itemMinAmount").html(itemMinAmount);	//最低加入金额				---->	100
                    $$("#monthFinanceAgreement .increaseRadix").html(increaseRadix);	//**的整数倍递增				---->	100
                    $$("#monthFinanceAgreement .itemMaxAmount").html(itemMaxAmount);	//最高投资金额				---->	5000
                    $$("#monthFinanceAgreement .limitTimeDate").html(limitTimeDate);	//合约期限					---->	2015年11月25日至 2015年12月25日
                    $$("#monthFinanceAgreement .calcDate").html(calcDate);				//结算日						---->	2015年12月26日

                    xxdApp.popup('.popup-bidhistory');
                }
            });
        },

        confirmTender: function () {
            openOnAccount.isOpenOnAccount({
                title: '应监管需要，您需要先开通银行存管账户才能顺利投资哦~',
                callBack: function () {
                    monthFinanceTenderCtrl.confirmTenderEnd();
                }
            });
        },


        /**
         * 确认投标
         */
        confirmTenderEnd: function () {
            if (appFunc.isRiskResult()) {
                return false;
            }
            try {
                //XXD_TRACK._trackEvent({category: "webapp_monthgold_in", action: "detail_confirm_invest", label: "确认投资", value: $$("#tenderAmount").val(), custval: "" });

                //GA部署
                var bid = $$("#monthFinanceId").val();
                var timelimit = $$("#tenderDays").val();
                var apr = $$(".monthFinanceTender #tenderApr").val();
                var terms = $('.monthFinanceTender #itemName').html();
                var name = terms;
                var category = "月进斗金/" + apr + "%/" + timelimit + "天";
                //进入结账
                CheckOut({id: $$("#monthFinanceId").val(), name: name, category: category});
            } catch (e) {
            }
            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            if ($(this).hasClass("button-51-disable")) {
                //xxdApp.alert('该产品暂不可购买', '抱歉');
                return;
            }

            var agreement = $$("#agreement").is(':checked');
            if (!agreement) {
                xxdApp.alert("请先阅读并同意《月进斗金服务协议》、《自动配标委托书》！", "抱歉");
                return;
            }
            var monthFinanceId = $$("#monthFinanceId").val();

            //投标金额
            var tenderAmount = $$("#tenderAmount").val();
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

            if (monthFinanceTenderCtrl.checkTenderAmount(tenderAmount) == false) {
                return false;
            }

            //最小投资金额
            var tenderMinAmount = $('.monthFinanceTender #tenderMinAmount').val();
            //**的整数倍递增
            var tenderIncreaseRadix = $('.monthFinanceTender #tenderIncreaseRadix').val();
            var tempTenderAmount = tenderAmount - tenderMinAmount;
            if (tenderIncreaseRadix != "" && tenderIncreaseRadix != 0) {
                if (tempTenderAmount % tenderIncreaseRadix != 0) {
                    xxdApp.alert("投资金额须为" + tenderIncreaseRadix + "的整数倍！", '提示');
                    return false;
                }
            }

            //实际支付
            var realPayAmount = $$(".monthFinanceTender #realPayAmount").val();

            xxdApp.modalPassword('确认从您的账户扣除' + appFunc.fmoney(realPayAmount, 2) + '元用以投标，请输入支付密码', '支付确认', function (password) {
                // try {XXD_TRACK._trackEvent({category: "webapp_monthgold_in", action: "popup_affirm_paypass", label: "支付密码确定", value: $('#7d_trade_num').val(), custval: "" });} catch (e) {}
                if (password == null || password == '') {
                    xxdApp.alert('请输入支付密码！');
                    return;
                }
                $$("#confirmTender").attr("disabled", "disabled");
                $$("#confirmTender").html("投标中...");


                xxdApp.showIndicator('正在努力投标...');
                req.callPost({
                    url: 'product/investOrder.do',
                    data: {
                        "pId": pId,
                        "pType": 95,
                        "pCategory": 0,
                        "payPwd": $.md5(password),
                        "tenderAmount": tenderAmount,
                        "redEnvelopeCode": ""
                    },
                    dataType: 'json',
                    timeout: 20000,
                    success: function (result) {
                        xxdApp.hideIndicator();
                        $$("#confirmTender").removeAttr("disabled");
                        $$("#confirmTender").html("确认投标");


                        if (result.code == 200000) {
                            var bizStatus = result.data.bizStatus;
                            if (bizStatus != undefined && bizStatus.code == 'SUCCESS') {
                                try {
                                    var tradeId = result.map.tenddetailid;
                                    var newRedAmount = "0元红包";
                                    XXD_TRACK._trackEvent({
                                        category: "webapp_monthgold_in",
                                        action: "monthgold_success",
                                        label: "投标成功",
                                        value: tradeId,
                                        custval: "1"
                                    });  //value值改为交易id
                                    transaction({
                                        id: monthFinanceId,
                                        name: name,
                                        category: category,
                                        price: realPayAmount,
                                        tradeId: tradeId,
                                        revenue: realPayAmount,
                                        coupon: newRedAmount
                                    });
                                } catch (e) {
                                }

                                var data = result.data;
                                var tenderProduct = productInfo.name;
                                var tenderAmount1 = appFunc.fmoney(tenderAmount, 2);
                                var tenderTime = DateHandle.formatDate('yyyy-MM-dd HH:mm:ss', new Date(data.createTime));
                                var startDate = DateHandle.formatDate('yyyy-MM-dd', new Date(data.createTime));
                                var startDayIsToday = "Y"; //今日计息
                                var profitAmount = $$(".monthFinanceTender #expectedRevenue").html();
                                profitAmount = appFunc.fmoney(profitAmount, 2);
                                var arrivalDate = DateHandle.formatDate('yyyy-MM-dd', new Date(data.createTime + productInfo.leastPeriod * 24 * 60 * 60 * 1000));
                                GS.loadPage("common/tenderSuccess.html?tenderProduct=" + tenderProduct + "&tenderAmount=" + tenderAmount1 + "&tenderTime=" + tenderTime + "&startDayIsToday=" + startDayIsToday + "&startDate=" + startDate + "&profitAmount=" + profitAmount + "&arrivalDate=" + arrivalDate);
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
                        //try {XXD_TRACK._trackEvent({category: "webapp_monthgold_in", action: "monthgold_success", label: "投标失败", value: realPayAmount, custval: "0" });} catch (e) {}
                        console.log("投标可能发生异常，请查看投标记录或稍后再试,ajax error...");
                        xxdApp.hideIndicator();
                        $$("#confirmTender").removeAttr("disabled");
                        $$("#confirmTender").html("确认投标");
                        xxdApp.hidePreloader();
                        xxdApp.alert('系统无响应，请查看投标记录是否投标成功或者重新投标', '抱歉', function () {
                            GS.loadPage("borrow/borrowListV2.html");
                        });
                    }
                });

            }, function (password) {
                //try {XXD_TRACK._trackEvent({category: "webapp_monthgold_in", action: "popup_affirm_cancel", label: "支付密码取消", value: "", custval: "" });} catch (e) {}

                if (password == null || password == '') {
                    return;
                }
            });
        },

        checkTenderAmount: function (tenderAmount) {
            //账户余额
            var userAccount = $$(".monthFinanceTender #userAccount").val();
            //最小投资金额
            var tenderMinAmount = $('.monthFinanceTender #tenderMinAmount').val();
            //本人剩余可投金额
            var availableLimit = $('.monthFinanceTender #availableLimit').val();
            //最大投资金额
            var tenderMaxAmount = $('.monthFinanceTender #tenderMaxAmount').val();
            //产品剩余可投金额
            var tenderRemainderAmount = $('.monthFinanceTender #tenderRemainderAmount').val();

            //账户余额
            if (parseFloat(userAccount) < parseFloat(tenderAmount)) {
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
            if (parseFloat(tenderAmount) > parseFloat(availableLimit)) {
                xxdApp.alert("投资金额须小于最大投资金额！", '提示');
                return false;
            }
            //产品剩余可投金额
            if (parseFloat(tenderAmount) > parseFloat(tenderRemainderAmount)) {
                xxdApp.alert("投资金额大于该产品的剩余可投金额！", '提示');
                return false;
            }

            if (!$$(".monthFinanceTender #agreement").is(':checked')) {
                xxdApp.alert("请阅读并同意《月进斗金服务协议》！", '提示');
                return false;
            }
        },

        //计算历史收益和实际支付金额
        calcAmount: function () {
            //投标金额
            var tenderAmount = $$("#tenderAmount").val();
            if (!appFunc.isFloat(tenderAmount)) {
                return false;
            }
            //利率
            var apr = $$(".monthFinanceTender #tenderApr").val();
            //期限
            var days = $$(".monthFinanceTender #tenderDays").val();
            //历史收益
            var expectedRevenue = Math.floor(tenderAmount * (apr / 100 / 360) * days * 100) / 100;
            $$(".monthFinanceTender #expectedRevenue").html(appFunc.fmoney(expectedRevenue, 2));
            $$(".monthFinanceTender #realPayAmount").val(tenderAmount);
            $$(".monthFinanceTender #realPayAmountShow").html(appFunc.fmoney(tenderAmount, 2));
        },

        blurCalcAmount: function () {
            //try {XXD_TRACK._trackEvent({category: "webapp_monthgold_in", action: "detail_invest_money", label: "投资金额", value: $$("#tenderAmount").val(), custval: "" });} catch (e) {}
        },

        topup: function () {
            //try {XXD_TRACK._trackEvent({category: "webapp_monthgold_in", action: "detail_recharge", label: "充值", value: "", custval: "" });} catch (e) {}
            GS.goTopup();
        }
    };

    return monthFinanceTenderCtrl;
});
