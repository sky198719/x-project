/**
 * 新元宝购买投标
 */
define(['js/utils/date', 'js/common/productUtil', 'js/utils/dayController', 'js/utils/xxd_dmp', 'js/common/ami', 'js/account/openOnAccount'], function (DateHandle, productUtil, dayController, xxd_dmp, ami, openOnAccount) {
    var step;
    var pId;
    var yypInfo = {};
    var choosedRedPacket = null;
    var yypTenderCtrl = {
        init: function (event) {
            var page = appFunc.getEventDetailPageQuery(event);
            pId = page.yypId;

            yypTenderCtrl.initTenderInfo();
            yypTenderCtrl.getUserAccountInfo();

            yypTenderCtrl.bindEvent();

        },

        bindEvent: function () {
            var binding = [
                {
                    element: '.yypTender #goCharge',
                    event: 'click',
                    handler: GS.goTopup
                },
                {
                    element: '.yypTender #confirmTender',
                    event: 'click',
                    handler: yypTenderCtrl.confirmTender
                },
                {
                    element: '.yypTender #tenderAmount',
                    event: 'keyup',
                    handler: yypTenderCtrl.calcAmount
                },
                {
                    element: '.yypTender .agreementPopup',
                    event: 'click',
                    handler: yypTenderCtrl.loadAgreement
                },
                {
                    element: '.yypTender .yypAmi',
                    event: 'click',
                    handler: ami.automaticMarkingInstruction
                }
            ];
            appFunc.bindEvents(binding);
        },

        getWebappProduct: function () {
            req.callJSON({
                url: 'product/getWebappProduct.do',
                data: {
                    pCode: "YYP"
                },
                dataType: 'json',
                success: function (result) {
                    if (result.code == 200000) {
                        var data = result.data;
                        pId = data.items.id;
                        yypTenderCtrl.initTenderInfo();
                    }
                }
            });
        },


        initTenderInfo: function () {
            req.callJSON({
                url: 'product/getProduct.do',
                data: {
                    pCode: 'YYP',
                    pId: pId
                },
                indicator: true,
                success: function (result) {
                    if (result.resultCode == 0) {
                        xxdApp.addNotification({title: '抱歉', hold: 3000, message: '获取月月派产品信息失败.'});
                        return;
                    }
                    var yyp = result.data;
                    step = result.data.items[0].step;
                    yypInfo = yyp;
                    var html = '';
                    html += '<h4 class="yyName" >月月派' + yyp.leastPeriod + '个月 - ' + yyp.periodName + '</h4>';
                    html += '<h5 class="font-grey mt10">';
                    html += '历史年化收益：';
                    html += '<span class="font-red font18">' + yyp.plannedAnnualRate + '%</span>';
                    html += '</h5>';
                    html += '<h5 class="font-grey mt5">剩余可投： <span class="font-red font18">' + appFunc.fmoney(yyp.leftAmount, 2) + '</span> 元</h5>';
                    html += '<h5 class="font-grey mt5">加入限额： <span>' + appFunc.fmoney(yyp.leastInvestAmount, 2) + '</span> - <span>' + appFunc.fmoney(yyp.mostInvestAmount, 2) + '</span> 元</h5>';
                    html += '<h5 class="font-grey mt5">解锁日期： ' + DateHandle.formatDate('yyyy-MM-dd', yyp.EXPIRINGDATE) + '</h5>';

                    try {
                        var activitedType = yyp.activitedType;
                        if(2==activitedType) {
                            req.callJSON({
                                url: 'product/activityLabel.do',
                                data: {
                                    productId: yyp.productId
                                },
                                dataType: 'json',
                                async:false,
                                success:function(result1) {
                                    if(result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                                        var remark = result1.data.data.remark;
                                        html += "<h5 class='font-grey mt5'>活动奖励：<span style='background-color: #ff7365;padding: 2px 10px;width: 79%;text-align: center;border-radius:2px;color:#fff;'>"+ remark + "</span></h5>";
                                    }
                                }
                            });
                        }

                    }catch (e) {
                        console.log(e);
                    }


                    var dmp_click_yypbuy = "5." + yyp.leastPeriod;
                    var dmp_devId = "yyp_" + yyp.TEleastPeriodRMS;
                    var dmp_targetId = "月月派" + yyp.leastPeriod + "个月-" + yyp.leastPeriod;
                    $("#confirmTender").attr({
                        "dmp_target_id": dmp_click_yypbuy,
                        "dev_id": dmp_devId,
                        "target_id": dmp_targetId
                    });
                    $$('.yypTender #yypTenderContent').html(html);

                    $$('.yypTender #p_minAmount').val(yypInfo.leastInvestAmount);
                    $$('.yypTender #p_maxAmount').val(yypInfo.mostInvestAmount);
                    $$('.yypTender #p_totalAmount').val(yypInfo.ACCOUNT);
                    $$('.yypTender #p_availableAmount').val(yypInfo.leftAmount);
                    $$('.yypTender #p_increaseRadix').val(step);
                    //投资金额输入框提示语
                    $$(".yypTender #tenderAmount").attr("placeholder", yypInfo.leastInvestAmount + "起投 " + step + "的整数倍递增");

                    var status = yypInfo.status;
                    var tenderButton = $$(".yypTender #confirmTender");
                    if (status == 1) {
                        tenderButton.removeClass("button-51");
                        tenderButton.addClass("button-51-disable");
                        tenderButton.html("等待发售");
                    } else if (status == 237) {
                        tenderButton.removeClass("button-51-disable");
                        tenderButton.addClass("button-51");
                        tenderButton.html("确认投资");
                        if (yypInfo.leftAmount == 0) {
                            tenderButton.removeClass("button-51");
                            tenderButton.addClass("button-51-disable");
                            tenderButton.html("已抢光");
                        }
                    } else {
                        tenderButton.removeClass("button-51");
                        tenderButton.addClass("button-51-disable");
                        tenderButton.html("收益中");
                    }
                    yypTenderCtrl.getUserYypInfo();
                    tenderButton.show();

                    //dayController.planTenderMemberDay();
                }
            });
        },
        getUserAccountInfo: function () {
            var defaultAccount = productUtil.getUserAccountInfo();
            //账户余额
            if (defaultAccount != null && defaultAccount != "" && !yypTenderCtrl.isEmptyObject(defaultAccount)) {
                $$(".yypTender #u_accountUsableShow").html(appFunc.fmoney(defaultAccount.usable, 2));
                $$(".yypTender #u_accountUsable").val(defaultAccount.usable);
            }
            yypTenderCtrl.getRedPackets();
        },
        getUserYypInfo: function () {
            var userYypAccount = productUtil.getUserYypInfo(pId);
            if (userYypAccount != null && userYypAccount != "" && !yypTenderCtrl.isEmptyObject(userYypAccount)) {
                //本人剩余可投
                var userMostTender = $$('.yypTender #p_maxAmount').val();
                var maxTenderAmount = parseFloat(userMostTender) - parseFloat(userYypAccount.EFFECTIVEMONEY);
                var tenderButton = $$(".yypTender #confirmTender");

                if (maxTenderAmount <= 0) {
                    $$('.yypTender #u_availableAmount').val(0);
                    tenderButton.removeClass("button-51");
                    tenderButton.addClass("button-51-disable");
                    tenderButton.html("个人限额已满");
                } else {
                    $$('.yypTender #u_availableAmount').val(maxTenderAmount);
                }
            } else {
                var u_availableAmount = $$('.yypTender #p_maxAmount').val();
                $$('.yypTender #u_availableAmount').val(u_availableAmount);
            }
        },
        getRedPackets: function () {
            var redList = productUtil.getRedPackets(5);
            yypTenderCtrl.setRedPacket(redList);

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
                            yypTenderCtrl.calcAmount();
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
        loadAgreement: function () {
            req.callGet({
                url: GC.getHtmlPath() + 'yyp/yypAgreement.html?v=' + GC.getVersion(),
                dataType: 'text',
                indicator: true,
                success: function (result) {

                    var compiledTemplate = t7.compile(result);
                    var productInfo = $.extend(true, {}, yypInfo);
                    productInfo.PNAME = '月月派' + yypInfo.leastPeriod + '个月';// - ' + yypInfo.NAME ;
                    productInfo.APR = yypInfo.plannedAnnualRate + '%';
                    productInfo.TERMS = yypInfo.leastPeriod;
                    productInfo.LEASTAMOUNT = appFunc.fmoney(yypInfo.leastInvestAmount, 2);
                    productInfo.MOSTAMOUNT = appFunc.fmoney(yypInfo.mostInvestAmount, 2);
                    productInfo.OPENTIME = DateHandle.formatDate('yyyy年MM月dd日', yypInfo.OPENTIME);
                    productInfo.CLOSETIME = DateHandle.formatDate('yyyy年MM月dd日', yypInfo.CLOSETIME);

                    var userInfo = {};
                    var realName = {};
                    userInfo.realName = realName;

                    var userJoinInfo = {};

                    var noContent = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

                    var output = compiledTemplate({
                        productInfo: productInfo,
                        userJoinInfo: userJoinInfo,
                        userInfo: userInfo,
                        noContent: noContent
                    });

                    $$(".popup-bidhistory").html(output);
                    xxdApp.popup('.popup-bidhistory');
                }
            });
        },
        //计算历史收益和实际支付金额
        calcAmount: function () {
            //投标金额
            var tenderAmount = $$(".yypTender #tenderAmount").val();
            var realPayAmount = 0;
            if (!appFunc.isFloat(tenderAmount)) {
                $$(".yypTender #realPayAmount").val(realPayAmount);
                $$(".yypTender #realPayAmountShow").html(appFunc.fmoney(realPayAmount, 2));
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
            $$(".yypTender #realPayAmount").val(realPayAmount);
            $$(".yypTender #realPayAmountShow").html(appFunc.fmoney(realPayAmount, 2));
            var dmp_redBag = "";
            if (choosedRedPacket == null) {
                dmp_redBag = 0;
            } else {
                dmp_redBag = choosedRedPacket.faceValue;
            }
            $("#confirmTender").attr({"dmp_money": tenderAmount, "dmp_redBag": dmp_redBag});

        },

        confirmTender: function () {
            openOnAccount.isOpenOnAccount({
                title: '为应监管需要，您需要先开通银行存管账户才能顺利投资哦~',
                callBack: function () {
                    yypTenderCtrl.confirmTenderEnd();
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
            if ($(this).hasClass("button-51-disable")) {
                return;
            }
            var agreement = $$("#agreement").is(':checked');
            if (!agreement) {
                xxdApp.alert("请先阅读并同意《月月派用户协议书》、《自动配标委托书》！", "抱歉");
                return;
            }

            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }

            //投标金额
            var tenderAmount = $$(".yypTender #tenderAmount").val();

            if (yypTenderCtrl.checkTenderAmount(tenderAmount) == false) {
                return false;
            }

            //实际支付
            var realPayAmount = $$(".yypTender #realPayAmount").val();

            var redCode = "";
            if (choosedRedPacket != null) {
                redCode = choosedRedPacket.redCode;
            }
            xxdApp.modalPassword('确认从您的账户扣除' + appFunc.fmoney(realPayAmount, 2) + '元用以购买产品，请输入支付密码', '支付确认', function (password) {
                if (password == null || password == '') {
                    xxdApp.alert('请输入支付密码！', '提示');
                    return;
                }
                $$(".yypTender #confirmTender").attr("disabled", "disabled");
                $$(".yypTender #confirmTender").html("投标中...");

                xxdApp.showIndicator('正在努力投标...');
                req.callPost({
                    url: 'product/investOrder.do',
                    data: {
                        "pId": pId,
                        "pType": 97,
                        "pCategory": 0,
                        "payPwd": $.md5(password),
                        "tenderAmount": tenderAmount,
                        "redEnvelopeCode": redCode
                    },
                    dataType: 'json',
                    timeout: 20000,
                    success: function (result) {
                        xxdApp.hideIndicator();
                        $$(".yypTender #confirmTender").removeAttr("disabled");
                        $$(".yypTender #confirmTender").html("确认投资");


                        if (result.code == 200000) {
                            var bizStatus = result.data.bizStatus;
                            if (bizStatus != undefined && bizStatus.code == 'SUCCESS') {
                                try {
                                    var tradeId = result.map.tenddetailid;
                                    var newRedAmount = "0元红包";
                                    XXD_TRACK._trackEvent({
                                        category: "webapp_yyp_in",
                                        action: "yyp_success",
                                        label: "投标成功",
                                        value: tradeId,
                                        custval: "1"
                                    });  //value值改为交易id
                                    transaction({
                                        id: pId,
                                        name: name,
                                        category: category,
                                        price: realPayAmount,
                                        tradeId: tradeId,
                                        revenue: realPayAmount,
                                        coupon: newRedAmount
                                    });
                                    var xxd_utm_source = xxd_dmp.getDmpUrlParam("xxd_utm_source");
                                    if (xxd_utm_source != "" && xxd_utm_source != null && xxd_utm_source != undefined) {
                                        xxd_dmp.clickDmpEvent("click", "buy_success", "1", $("#confirmTender").attr("dmp_target_id"), $("#confirmTender").attr("dmp_money"), xxd_utm_source, xxd_dmp.dmp_data_obj("click", "buy_success", $("#confirmTender").attr("dev_id"), $("#confirmTender").attr("target_id"), "", "", $("#confirmTender").attr("dmp_money"), $("#confirmTender").attr("dmp_redBag"), xxd_utm_source));
                                    }
                                } catch (e) {
                                }

                                var data = result.data;
                                var tenderProduct = "月月派" + yypInfo.leastPeriod + '个月 - ' + yypInfo.name;
                                var tenderAmount1 = appFunc.fmoney(data.amount, 2);
                                var tenderTime = DateHandle.formatDate('yyyy-MM-dd HH:mm:ss', new Date(data.createTime));
                                var startDayIsToday = "Y"; //非今日计息，但这里图标要显示蓝色
                                var startDate = DateHandle.formatDate('yyyy-MM-dd', new Date(data.startDate));

                                var interestAmount = appFunc.fmoney(data.plannedInterest, 2);
                                var interestTime = new Date(data.endDate).getDate();
                                var repayTime = DateHandle.formatDate('yyyy-MM-dd', new Date(data.expireDate));
                                var activity_url = data.activity_url;
                                GS.loadPage("yyp/tenderSuccess.html?tenderProduct=" + tenderProduct + "&tenderAmount=" + tenderAmount1 + "&tenderTime=" + tenderTime + "&startDayIsToday=" + startDayIsToday + "&startDate=" + startDate + "&interestAmount=" + interestAmount + "&interestTime=" + interestTime + "&repayTime=" + repayTime + "&activity_url=" + activity_url);

                            } else {
                                xxdApp.alert(bizStatus.message,"提示");
                            }
                        } else {
                            try {
                                XXD_TRACK._trackEvent({
                                    category: "webapp_monthgold_in",
                                    action: "monthgold_success",
                                    label: "投标失败",
                                    value: realPayAmount,
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
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        console.log("投标可能发生异常，请查看投标记录或稍后再试,ajax error...");
                        xxdApp.hideIndicator();
                        $$(".yypTender #confirmTender").removeAttr("disabled");
                        $$(".yypTender #confirmTender").html("确认投资");
                        xxdApp.hidePreloader();
                        xxdApp.alert('系统无响应，请查看投标记录是否投标成功或者重新投标', '抱歉', function () {
                            GS.loadPage("popular/financesList.html");
                        });
                    }
                });

            }, function (password) {
                //try {XXD_TRACK._trackEvent({category:"webapp_step_in",action:"popup_affirm_cancel",label:"支付密码取消",value:"",custval:"" });}catch(e){}
                if (password == null || password == '') {
                    return;
                }
            });
        },
        checkTenderAmount: function (tenderAmount) {
            //账户余额
            var userAccount = $$(".yypTender #u_accountUsable").val();
            //最小投资金额
            var tenderMinAmount = $$('.yypTender #p_minAmount').val();
            //本人剩余可投金额
            var availableLimit = $$('.yypTender #u_availableAmount').val();
            //最大投资金额
            var tenderMaxAmount = $$('.yypTender #p_maxAmount').val();
            //产品剩余可投金额
            var tenderRemainderAmount = $$('.yypTender #p_availableAmount').val();

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
                xxdApp.alert('余额不足，请先充值', '提示', function () {
                    GS.goTopup();
                });
                return false;
            }

            //最小投资金额
            //当剩余可投金额小于投标限额，投资金额可以小于投标限额
            //if (parseFloat(tenderRemainderAmount) >= parseFloat(tenderMinAmount)) {
            if (parseFloat(tenderAmount) < parseFloat(tenderMinAmount)) {
                xxdApp.alert("起投金额为" + tenderMinAmount + "元哦，请调整购买金额", '提示');
                return false;
            }
            //}

            //**的整数倍递增
            var tenderIncreaseRadix = step;
            //var tempTenderAmount = tenderAmount - tenderMinAmount;
            if (tenderIncreaseRadix != "" && tenderIncreaseRadix != 0) {
                if (tenderAmount % tenderIncreaseRadix != 0) {
                    xxdApp.alert("请按产品加入规则输入投资金额", '提示');
                    return false;
                }
            }

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

            if (!$$(".yypTender #agreement").is(':checked')) {
                xxdApp.alert("请阅读并同意《月月派用户协议书》！", '提示');
                return false;
            }
        },
        isEmptyObject: function (e) {
            var t;
            for (t in e)
                return !1;
            return !0
        }
    };

    return yypTenderCtrl;
});
