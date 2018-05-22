/**
 * 新元宝购买投标
 */
define(['js/utils/date', 'js/planTender/planTenderView', 'js/plan/planUtils', 'js/activity/womensDay', 'js/utils/xxd_dmp', 'js/common/ami', 'js/account/openOnAccount','js/account/selectHongbao'], function (DateHandle, planTenderView, PlanUtils, womensDay, xxd_dmp, ami, openOnAccount,selectHongbao) {
    var redPacketList = [];
    var planId = "";
    var planInfo = {};
    var redCode = "";
    var couponId = "";
    var planTenderCtrl = {
        init: function (event) {
            var page = appFunc.getEventDetailPageQuery(event);
            planId = page.planId;
            planTenderCtrl.loadPlanDetail(planId);
            planTenderCtrl.initPlanPicker();
            planTenderCtrl.bindEvent();

            womensDay.planTender();
            selectHongbao.init();
        },

        bindEvent: function () {
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#presaleMoney',
                    event: 'keyup',
                    handler: planTenderCtrl.calMoney
                },
                {
                    element: '#presaleMoney',
                    event: 'blur',
                    handler: planTenderCtrl.blueCalMoney
                },
                {
                    element: '#plan_confirmTender',
                    event: 'click',
                    handler: planTenderCtrl.affirmTender
                },
                {
                    element: '#goCharge',
                    event: 'click',
                    handler: planTenderCtrl.goCharge
                },
                {
                    element: '.planAgreementPopup',
                    event: 'click',
                    handler: planTenderCtrl.loadPlanAgreement
                },
                {
                    element: '.plan_Ami',
                    event: 'click',
                    handler: ami.automaticMarkingInstruction
                },
                {
                    element:'.selectHongbao',
                    event:'click',
                    handler:planTenderCtrl.selectHongbao
                }
            ];

            planTenderView.bindEvent({
                    bindings: bindings
                }
            );
        },

        selectHongbao:function(){
            var selectHongbaoBtn = $$(".selectHongbao");
            var isShow = selectHongbaoBtn.attr("data-isShow");
            if(isShow != undefined && isShow == 'true') {
                selectHongbao.show({
                    callBack:function(code,value,type,couponid){
                        if(code == 0){
                            $$(".selectHongbao").html("不使用优惠券");
                            redCode = "";
                        } else {
                            console.log("callback couponId="+couponid);
                            $$(".selectHongbao").html("优惠券抵扣"+value+"元");
                            var presaleMoney = $.trim($('#presaleMoney').val());
                            var presale_affirm_money = 0.00;
                            var pay_type = $('#pay_type').val();
                            if (pay_type == 1) { // 支付剩余金额
                                var surplusMoney = $('#plan_surplusMoney').val();
                                presale_affirm_money = parseFloat(surplusMoney) - parseFloat(value);
                            } else if (pay_type == 2) { // 全额支付
                                if (presaleMoney) {
                                    presale_affirm_money = parseFloat(presaleMoney) - parseFloat(value);
                                }
                            }

                            $('#presale_affirm_money_show').html(appFunc.fmoney(presale_affirm_money, 2));
                            $('#presale_affirm_money').val(presale_affirm_money);
                            redCode = code;
                            couponId = couponid;
                        }
                    },
                    code:redCode
                });
            }
        },

        loadPlanAgreement: function () {
            // 新元宝协议
            req.callJSON({
                url: "xplan/agreement/" + planId + ".do",
                data: {},
                timeout: 10000,
                success: function (data) {
                    data.scheme.pname = PlanUtils.schemeType(data.scheme.type) + ' - ' + data.scheme.pname;

                    if (data.scheme.minApr != data.scheme.maxApr) {
                        data.scheme.maxApr = data.scheme.minApr + '% ~ ' + data.scheme.maxApr + '%';
                    } else {
                        data.scheme.maxApr = data.scheme.minApr + '%';
                    }

                    data.scheme.leastamount = appFunc.fmoney(data.scheme.leastamount, 2);
                    data.scheme.mostamount = appFunc.fmoney(data.scheme.mostamount, 2);

                    var presaleBegin = new Date(data.scheme.presaleBegin);
                    data.scheme.presaleBegin = presaleBegin.getFullYear() + "年" + (presaleBegin.getMonth() + 1) + "月" + presaleBegin.getDate() + "日";
                    data.scheme.presaleBegin_y = presaleBegin.getFullYear();
                    data.scheme.presaleBegin_m = presaleBegin.getMonth() + 1;
                    data.scheme.presaleBegin_d = presaleBegin.getDate();

                    var opensaleEnd = new Date(data.scheme.opensaleEnd);
                    data.scheme.opensaleEnd = opensaleEnd.getFullYear() + "年" + (opensaleEnd.getMonth() + 1) + "月" + opensaleEnd.getDate() + "日";
                    data.scheme.opensaleEnd_y = opensaleEnd.getFullYear();
                    data.scheme.opensaleEnd_m = opensaleEnd.getMonth() + 1;
                    data.scheme.opensaleEnd_d = opensaleEnd.getDate();

                    var endTime = new Date(data.endTime);
                    data.endTime = endTime.getFullYear() + "年" + (endTime.getMonth() + 1) + "月" + endTime.getDate() + "日";
                    data.endTime_y = endTime.getFullYear();
                    data.endTime_m = endTime.getMonth() + 1;
                    data.endTime_d = endTime.getDate();

                    if (data.userSchemeInfo) {
                        if (data.userSchemeInfo.collectiontype == 1) {
                            data.userSchemeInfo.collectiontype = '收益再投标';
                        } else if (data.userSchemeInfo.collectiontype == 2) {
                            data.userSchemeInfo.collectiontype = '提至新新贷账户';
                        }
                        data.userSchemeInfo.account = appFunc.fmoney(data.userSchemeInfo.account, 2);
                    }

                    data.user = {};
                    data.user.signedTime = "";

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'planTender/planAgreement.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                planTenderView.showPlanAgreement({
                                    result: result,
                                    data: data
                                });
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }
                }
            });
        },
        initPlanPicker: function () {
            var pickerInterest = xxdApp.picker({
                input: '#picker-interest',
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
                        values: ['收益再投标']
                    }
                ],
                onOpen: function (picker) {
                    //try {XXD_TRACK._trackEvent({category: "webapp_xplan_in", action: "profit_handle", label: "收益处理", value: $('#picker-interest').val(), custval: "" });} catch (e) {}
                    picker.container.find('.toolbar-randomize-link').on('click', function () {
                        picker.close();
                    });
                }
            });
        },
        loadPlanDetail: function (planId) {
            req.callJSON({
                url: "xplan/requestQuick.do",
                data: {
                    planId: planId
                },
                indicator: true,
                success: function (data) {
                    planInfo = data;
                    planTenderView.tenderDetail(data);
                }
            });
        },

        calMoney: function () {
            var presaleMoney = $.trim($('#presaleMoney').val());
            if (presaleMoney == null || presaleMoney == '') {
                $('#presale_affirm_money_show').html("0.00");
                $('#presale_affirm_money').val(0);
                return;
            }
            var presale_affirm_money = 0.00;
            var pay_type = $('#pay_type').val();
            if (pay_type == 1) { // 支付剩余金额
                var surplusMoney = $('#plan_surplusMoney').val();
                presale_affirm_money = parseFloat(surplusMoney);
            } else if (pay_type == 2) { // 全额支付
                if (presaleMoney) {
                    presale_affirm_money = parseFloat(presaleMoney);
                }
            }

            $('#presale_affirm_money_show').html(appFunc.fmoney(presale_affirm_money, 2));
            $('#presale_affirm_money').val(presale_affirm_money);

            //planTenderCtrl.showRedPacke(presaleMoney);

            var isUsr = selectHongbao.filter(presaleMoney,'wap',planInfo.scheme.closeterm,5);
            var selectHongbaoBtn = $$(".selectHongbao");
            if(isUsr) {
                selectHongbaoBtn.html("无可用优惠券");
                selectHongbaoBtn.attr("data-isShow",false);
            } else {
                selectHongbaoBtn.html("<span style='color:#007aff;text-decoration: underline;' >有可用优惠券</span>");
                selectHongbaoBtn.attr("data-isShow",true);
            }
        },

        blueCalMoney: function () {
            var presaleMoney = $('#presaleMoney').val();
            $("#plan_confirmTender").attr("dmp_money", presaleMoney);
        },


        affirmTender: function () {
            openOnAccount.isOpenOnAccount({
                title: '为应监管需要，您需要先开通银行存管账户才能顺利投资哦~',
                callBack: function () {
                    planTenderCtrl.affirmTenderEnd();
                }
            });
        },

        /**
         * 确认投标
         */
        affirmTenderEnd: function () {
            if (appFunc.isRiskResult()) {
                return false;
            }
            //大数据部码
            try {
                var xpanType = $("#xpanType").val();
                var bid = $("#plan_schemeId").val();
                var pname = "";
                if (xpanType == 1) {
                    pname = "新元宝3个月 -" + $("#xpanName").val();
                } else if (xpanType == 2) {
                    pname = "新元宝6个月 -" + $("#xpanName").val();
                } else if (xpanType == 3) {
                    pname = "新元宝12个月 -" + $("#xpanName").val();
                } else if (xpanType == 4) {
                    pname = "新元宝1个月 -" + $("#xpanName").val();
                }
                var categorys = "新元宝/" + $("#xpanApr").val() + "/" + $("#xpanCloseterm").val() + "个月";

                CheckOut({id: bid, name: pname, category: categorys});
            } catch (e) {
            }
            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }

            var agreement = $$("#plan_agreement").is(':checked');
            if (!agreement) {
                xxdApp.alert("请先阅读并同意《新元宝用户协议书》、《自动配标委托书》！", "抱歉");
                return;
            }

            var pay_collectiontype = 1;
            var pay_collection = $('#picker-interest').val();
            if (pay_collection == '') {
                xxdApp.alert("请选择收益处理方式！", '提示');
                return false;
            } else {
                if (pay_collection == '收益再投标') {
                    pay_collectiontype = 1;
                } else if (pay_collection == '提至新新贷账户') {
                    pay_collectiontype = 2;
                }
            }

            var payType = $('#pay_type').val();
            var presaleMoney = $('#presaleMoney').val();
            var presale_affirm_money = $('#presale_affirm_money').val();
            var default_account_usable = $("#plan_default_account_usable").val();
            var surplusMoney = $('#plan_surplusMoney').val();
            var ext_msg = '';
            if (payType == 1) {
                // 新元宝账户可用余额
                if (parseFloat(presale_affirm_money) > parseFloat(default_account_usable)) {
                    xxdApp.alert("您的账户可用余额，不够支付本次加入新元宝的金额，请充值", '提示');
                    return false;
                }
                ext_msg = '支付剩余金额';
            } else if (payType == 2) {
                if (presaleMoney == null || presaleMoney == 0 || !appFunc.isFloat(presaleMoney)) {
                    xxdApp.alert("加入金额不能为空，请重新输入", '提示');
                    return false;
                }
                // 预定金额为step元的整数倍递增
                var step = $("#plan_step").val();
                if (presaleMoney % step > 0) {
                    xxdApp.alert("您输入的加入金额必须是[" + step + "元]的整数倍", '提示');
                    return false;
                }
                // 新元宝账户可用余额
                if (parseFloat(presale_affirm_money) > parseFloat(default_account_usable)) {
                    xxdApp.alert("您的账户可用余额，不够支付本次加入新元宝的金额，请充值", '提示');
                    return false;
                }
                // 最大购买金额
                var mostamount = $("#plan_mostamount").val();
                if (parseFloat(presaleMoney) > parseFloat(mostamount)) {
                    xxdApp.alert("加入金额不能大于加入上限【" + mostamount + "元】，请重新输入", '提示');
                    return false;
                }
                // 用户已加入金额
                var userSchemeAccount = $("#plan_userSchemeAccount").val();
                if (userSchemeAccount != null && userSchemeAccount != 0) {
                    if (parseFloat(userSchemeAccount) >= parseFloat(mostamount)) {
                        xxdApp.alert("您加入总金额已达加入上限【" + mostamount + "元】", '提示');
                        return false;
                    } else if (parseFloat(userSchemeAccount) + parseFloat(presaleMoney) > parseFloat(mostamount)) {
                        xxdApp.alert("您输入的加入金额【" + presaleMoney + "元】加上以加入金额【" + userSchemeAccount + "元】以达加入上限【" + mostamount + "元】，还可以加入：" + (mostamount - userSchemeAccount) + "元", '提示');
                        return false;
                    }
                }
                // 最小购买金额
                var leastamount = $("#plan_leastamount").val();
                if (parseFloat(presaleMoney) < parseFloat(leastamount)) {
                    xxdApp.alert("加入金额不能小于最小加入金额【" + leastamount + "元】，请重新输入", '提示');
                    return false;
                }
                // 计划剩余金额
                var remacount = $("#plan_remacount").val();
                if (parseFloat(presaleMoney) > parseFloat(remacount)) {
                    xxdApp.alert("您输入的加入金额大于当前新元宝的剩余金额【" + remacount + "元】，请重新输入", '提示');
                    return false;
                }
            }

            if (!$$("#plan_agreement").is(':checked')) {
                xxdApp.alert("请阅读并同意《新元宝用户协议书》！", '提示');
                return false;
            }

            xxdApp.modalPassword('确认从您的账户扣除' + appFunc.fmoney(presale_affirm_money, 2) + '元购买新元宝' + ext_msg + '，请输入支付密码', '支付确认', function (password) {
                if (password == null || password == '') {
                    xxdApp.alert('请输入支付密码！');
                    return;
                }
                //try {XXD_TRACK._trackEvent({category: "webapp_xplan_in", action: "popup_affirm_paypass", label: "支付密码确定", value: "", custval: "" });} catch (e) {}
                $$("#plan_confirmTender").attr("disabled", "disabled");
                $$("#plan_confirmTender").html("投标中...");
                var account = 0.0;
                var operationType = 1;
                var confirmTenderValue = '确认全额支付';
                if (payType == 1) { // 支付剩余金额
                    account = surplusMoney;
                    operationType = 2;
                    confirmTenderValue = '确认支付剩余金额';
                } else if (payType == 2) { // 全额支付
                    account = presaleMoney;
                    operationType = 1;
                    confirmTenderValue = '确认全额支付';
                }
                xxdApp.showIndicator('正在加载数据...');
                req.callPost({
                    url: 'product/investOrder.do',
                    data: {
                        "pId": $('#plan_schemeId').val(),
                        "pType": 98,
                        "pCategory": 0,
                        "payPwd": $.md5(password),
                        "tenderAmount": presaleMoney,
                        "redEnvelopeCode": redCode,
                        "couponId":couponId
                    },
                    dataType: 'json',
                    timeout: 20000,
                    success: function (result) {
                        xxdApp.hideIndicator();


                        if (result.code == 200000) {
                            var bizStatus = result.data.bizStatus;
                            if (bizStatus != undefined && bizStatus.code == 'SUCCESS') {

                                var tenderAmount = appFunc.fmoney(result.data.amount, 2);
                                var tenderTime = DateHandle.formatDate('yyyy-MM-dd HH:mm:ss', new Date(result.data.createTime));
                                var startDate = DateHandle.formatDate('yyyy-MM-dd', new Date(result.data.startDate));
                                var arrivalDate = DateHandle.formatDate('yyyy-MM-dd', new Date(result.data.expireDate));

                                var interest = result.data.plannedInterest;//历史收益
                                var activity_url = result.activity_url;
                                var tenderPlanType = planInfo.scheme.type;
                                GS.loadPage("planTender/planTenderSuccess.html?tenderPlanType=" + tenderPlanType + "&tenderAmount=" + tenderAmount + "&tenderTime=" + tenderTime + "&startDate=" + startDate + "&interest=" + interest + "&arrivalDate=" + arrivalDate + "&activity_url=" + activity_url);
                            } else {
                                xxdApp.alert(bizStatus.message,"提示");
                                $$("#plan_confirmTender").removeAttr("disabled");
                                $$("#plan_confirmTender").html(confirmTenderValue);
                            }
                        } else {
                            $$("#plan_confirmTender").removeAttr("disabled");
                            $$("#plan_confirmTender").html(confirmTenderValue);

                            var msg = result.message;
                            if(result.data != undefined && result.data.bizStatus != undefined && result.data.bizStatus.code != 'SUCCESS') {
                                msg = result.data.bizStatus.message;
                            }

                            xxdApp.alert(msg, '提示');
                        }
                    }
                });
            }, function (password) {
                if (password == null || password == '') {
                    return;
                }
            });
        },
        goCharge: function () {
            GS.goTopup();
        }
    };

    return planTenderCtrl;
});
