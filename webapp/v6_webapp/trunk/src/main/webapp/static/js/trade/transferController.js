/**
 * 债券转出
 * Created by pufei on 2015/7/20.
 */

define([ 'js/trade/transferView'], function (transferView) {

    var voiceSwitch = "false";
    var intervalArray = [];
    var time = 120;
    var formToken = {tokenName:'',token:''};
    var transferCtrl = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);
            transferCtrl.clearInterval();

            var tenderId = query.tenderId;
            transferCtrl.loadData(tenderId);
            transferCtrl.eventBind();
            //设置表单token
            formToken = appFunc.setToken({name:"TRADE_TRANSFER", id: tenderId});
            if(formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                return;
            }

            voiceSwitch = GS.getSysConfig().voiceSwitch;
        },

        /**清空定时器，隐藏语音验证码*/
        clearInterval:function() {
            $$.each(intervalArray, function (index, value) {
                clearInterval(value);
            });
            intervalArray.length = 0;
            // if(options.type == 'voice') {
                // $$('#rand-code-countdown').hide();
                // $$('#transfer #q_code').show();
            // } else {
                // $$('#transfer #voice_sms').hide();
               	// $$('#transfer #voiceCode-countdown').hide();
                // $$('#transfer #voiceCode').show().css('display', 'inline');
            // }
        },

        loadData: function (tenderId) {
            req.callJSON({
                url: "traderequest/selectTradeRequest.do",
                data: {
                    tenderId: tenderId
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if (data.resultCode == 0) {
                        transferView.showTransferInfo(data);
                    } else {
                        if (data.resultCode == -99) {
                            //session超时，跳转登陆界面
                            xxdApp.loginScreen();
                            return;
                        }
                        xxdApp.alert(data.msg, '抱歉', function () {
                            GS.loadPage('account/bid-history.html?path=account');
                        });
                    }
                }
            });

            //var displayValues = ["请选择", "无优惠转让", "让利转让", "加价转让"];
            //var values = ["select", "nodiscount", "none", "fare"];
            var displayValues = ["请选择", "无优惠转让"];
            var values = ["select", "nodiscount"];
            var pickerDevice = xxdApp.picker({
                input: '#q_picker_device',
                rotateEffect: true,
                toolbarTemplate: '<div class="toolbar">' +
                        '<div class="toolbar-inner">' +
                        '<div class="left"><a href="#" class="link toolbar-randomize-link">取消</a></div>' +
                        '<div class="right">' +
                        '<a href="#" class="link close-picker">确定</a>' +
                        '</div>' +
                        '</div> ' +
                        '</div> ',
                formatValue: function (picker, values) {
                    return picker.displayValue;
                },
                cols: [
                    {
                        textAlign: 'center',
                        values: values,
                        displayValues: displayValues
                    }
                ],
                onChange: function (p, value, displayValue) {
                    if (value == "none" || value == "fare") {
                        $$("#li_fundsMoney").show();
                        $$("#li_fundsRate").show();
                    } else{
                        $$("#li_fundsMoney").hide();
                        $$("#li_fundsRate").hide();
                    }

                    $("#fundsMoney").val(0);
                    $$('#q_fundsMoney').val('');
                    $$('#q_fundsRate').val('');
                    $$("#transferpattern").val(value);
                    transferView.calculationTotal();
                },
                onOpen: function (picker) {
                    picker.container.find('.toolbar-randomize-link').on('click', function () {
                        picker.close();
                    });
                }

            });
        },

        eventBind: function () {
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#q_submitButton',
                    event: 'click',
                    handler: transferCtrl.submitButton
                },
                {
                    element: '#q_fundsMoney',
                    event: 'keyup',
                    handler: transferCtrl.calculationTotalAndCounterFee
                },
                {
                    element: '#q_fundsRate',
                    event: 'keyup',
                    handler: transferCtrl.calculationTotalAndCounterFee
                },
                {
                    element: '#transfer #q_code',
                    event: 'click',
                    handler: transferCtrl.sendMobileCode
                },
                {
                    element: '#transfer #voiceCode',
                    event: 'click',
                    handler: transferCtrl.sendVoiceSMS
                },
                {
                    element: '#transferAgreement',
                    event: 'click',
                    handler: transferCtrl.transferAgreement
                }
            ];
            transferView.init({bindings: bindings});
        },

        transferAgreement: function () {
            req.callGet({
                url: GC.getHtmlPath() + 'trade/transferAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                indicator: true,
                success: function (result) {
                    $$(".popup-bidhistory").html(result);
                    xxdApp.popup('.popup-bidhistory');
                }
            });
        },

        /**
         * 确认转出
         */
        submitButton: function () {
            //支付密码非空校验
            var payPassword = $$('#q_paypwd').val();
            if ('' == payPassword) {
                xxdApp.alert('请输入支付密码！', '提示');
                return;
            }

            //验证码非空校验
            var verifyCode = $$('#q_verifyCode').val();
            if ("" == verifyCode) {
                xxdApp.alert('请输入验证码！', '提示');
                return;
            }

            //债权转让让利校验
            if (!transferCtrl.checkoptionsRadios2()) {
                return;
            }

            //校验转让金额是否大于待收本息
            if (!transferCtrl.checkoptionsRadios3()) {
                return;
            }

            if (!$$("#q_agreement").is(':checked')) {
                xxdApp.alert("请阅读并同意《债权转让协议》！", '提示');
                return false;
            }
            //支付密码、验证码正确性校验
            req.callJSON({
                url: "traderequest/checkBeforeTradeRequest.do",
                data: {
                    payPassword: $.md5($.md5(payPassword)),
                    verifyCode: verifyCode,
                    tenderId: $$('#q_tenderId').val(),
                    tokenName: formToken.data.tokenName,
                    token: formToken.data.token
                },
                indicator: true,
                success: function (result) {
                    if (result.resultCode == -99) {
                        xxdApp.loginScreen();
                        return false;
                    }
                    if (result.resultCode < 0) {
                        var msg='';
                        var s = result.msg.split(':');
                        if (s[0] == 'verifyCode') {
                            $$('#q_verifyCode').val('').focus();
                            msg = s[1];
                        } else if (s[0] == 'payPassword') {
                            $$('#q_verifyCode').val('');
                            $$('#q_paypwd').val('').focus();
                            msg = s[1];
                        } else {
                            msg = result.msg;
                        }
                        xxdApp.alert(msg, '提示',function(){
                            //设置表单token
                            formToken = appFunc.setToken({name:"TRADE_TRANSFER", id: $$('#q_tenderId').val()});
                            if(formToken.code != 0) {
                                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                                return;
                            }
                        });
                        return false;
                    }
                    if (result.resultCode == 0) {
                        //校验通过后，保存债权转让数据
                        req.callJSON({
                            url: 'traderequest/saveTradeRequest.do',
                            data: {
                                tenderId: $$('#q_tenderId').val(),
                                fundsMoney: $$('#fundsMoney').val(),
                                repayCapital: parseFloat($$('#repayCapital').val())
                            },
                            indicator: true,
                            success: function (data) {
                                if (data.resultCode == -99) {
                                    //session超时，跳转登陆界面
                                    xxdApp.loginScreen();
                                    return;
                                } else if (data.resultCode < 0) {
                                    xxdApp.alert(data.msg, '抱歉',function(){
                                        //设置表单token
                                        formToken = appFunc.setToken({name:"TRADE_TRANSFER", id: $$('#q_tenderId').val()});
                                        if(formToken.code != 0) {
                                            xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                                            return;
                                        }
                                    });
                                    $$('#transfer #rand-code-countdown').hide();
                                    $$('#transfer #q_code').show();
                                    $$('#q_verifyCode').val('').focus();
                                    // 清除定时器
                                    clearInterval(intervalArray[0]);
                                    intervalArray = [];
                                    return;
                                } else {
                                    //弹出操作成功的提示
                                    xxdApp.alert('转让申请成功！', '提示', function () {
                                        GS.loadPage('account/bid-history.html?path=account');
                                    });
                                }
                            }
                        });
                    }
                }
            });

        },
        /* 发送短信验证码 */
        sendMobileCode: function () {

            req.callJSON({
                url: 'traderequest/sendSMS.do',
                data: {},
                dataType: 'json',
                preloaderTitle: '正在发送短信，请稍后...',
                success: function (data) {
                    var tempTime =time;
                    if (data.resultCode == 0) {
                        transferCtrl.clearInterval();
                        xxdApp.alert('验证码发送成功，请注意查收', '提示');
                        $$('#transfer #q_code').hide();
                        $$('#transfer #voice_sms').hide();
                        $$('#transfer #rand-code-countdown').show().html(tempTime);
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                                $$('#transfer #rand-code-countdown').hide();
                                $$('#transfer #q_code').show();
                                $$('#transfer #voice_sms').show();
                                // 清除定时器
                                transferCtrl.clearInterval();
                                // clearInterval(intervalArray[0]);
                                // intervalArray = [];
                            } else {
                                //获取短信验证码超过30秒，还未进行验证，显示获取语音验证码按钮
                            	if(voiceSwitch == "true" && time-tempTime>30){
                            		$$('#transfer #voice_sms').show();
                            	}
                                $$('#transfer #rand-code-countdown').html(tempTime);
                            }
                        }, 1000));
                    } else{
                        xxdApp.alert(data.msg, '抱歉');
                        $$('#transfer #rand-code-countdown').hide();
                        $$('#transfer #q_code').show();
                        // if(data.resultCode== -30){
                            $$('#transfer #voice_sms').show();
                        // }
                    }
                }
            });
        },
        /**
         * 发送语音验证码
         * */
        sendVoiceSMS: function () {
            
            req.callJSON({
                url: 'traderequest/sendVoiceSMSLogin.do',
                data: {
                },
                preloaderTitle: '正在发送语音短信，请稍后...',
                success: function (result) {
                    var tempTime = time;
                    if (result.resultCode == 0) {
                        transferCtrl.clearInterval();
                        xxdApp.alert('请接听来自4000169521的电话，为您自动播报语音验证码', '发送成功');
                        $$('#transfer #voice_sms').hide();
                        $$('#transfer #q_code').hide();
                        $$('#transfer #rand-code-countdown').show().html(tempTime);
                        intervalArray.push(setInterval(function () {
                            tempTime--;
                            if (tempTime < 0) {
                            	 $$('#transfer #rand-code-countdown').hide();
                                 // $$('#transfer #voiceCode').show().css('display', 'inline');
                                 $$('#transfer #voice_sms').show();
                                 $$('#transfer #q_code').show();
                                // 清除定时器
                                transferCtrl.clearInterval();
                                // clearInterval(intervalArray[0]);
                                // intervalArray = [];
                            } else {
                            	$$('#transfer #rand-code-countdown').html(tempTime);
                            }
                        }, 1000));
                    } else if (result.resultCode == 400) {
                        xxdApp.loginScreen();
                        return;
                    } else if (result.resultCode == -1) {
                        xxdApp.alert(result.msg, '提示', function () {
                            GS.loadPage('personal/personalInfo.html');
                        });
                    } else {
                        xxdApp.alert(result.msg, '提示');
                        // $$('#transfer #voiceCode-countdown').hide();
                        // $$('#transfer #voiceCode').show().css('display', 'inline');
                    }
                }
            });
        },
        /* 计算让利后总价 利息 */
        calculationTotalAndCounterFee: function () {
            var type = this.getAttribute("data-type");

            //转让模式
            var transferpattern = $$('#transferpattern').val();
            var repayCapital = $$("#repayCapital").val();
            if (transferpattern == 'none') {//让利
                if("rate" == type) {
                    var value =  $$("#q_fundsRate").val();
                    value = value == '' ? '0':value;
                    var money = parseFloat(parseFloat(value)/100 * repayCapital).toFixed(2);
                    money = appFunc.isFloat(money) ? money : 0;
                    $("#q_fundsMoney").val(money);
                    $("#fundsMoney").val(money);
                } else {
                    var value = $("#q_fundsMoney").val();
                    value = value == '' ? '0':value;
                    var rate = parseFloat(parseFloat(value)*100/repayCapital).toFixed(2);
                    rate = appFunc.isFloat(rate) ? rate : 0;
                    $("#q_fundsRate").val(rate);
                    $("#fundsMoney").val(value);
                }
                transferView.calculationTotal();
                transferCtrl.checkoptionsRadios2();
            } else if (transferpattern == 'fare') {//加价
                if("rate" == type) {
                    var value =  $$("#q_fundsRate").val();
                    value = value == '' ? '0':value;
                    var money = parseFloat(parseFloat(value)/100*repayCapital).toFixed(2);
                    money = appFunc.isFloat(money) ? money : 0;
                    $("#q_fundsMoney").val(money);
                    $("#fundsMoney").val(money * -1);
                } else {
                    var value = $("#q_fundsMoney").val();
                    value = value == '' ? '0':value;
                    var rate = parseFloat(parseFloat(value)*100/repayCapital).toFixed(2);
                    rate = appFunc.isFloat(rate) ? rate : 0;
                    $("#q_fundsRate").val(rate);
                    $("#fundsMoney").val(parseFloat(value)*-1);
                }
                transferView.calculationTotal();
                transferCtrl.checkoptionsRadios3();
            }
        },

        /**
         * 校验让利转让金额
         */
        checkoptionsRadios2: function () {
            //债权转让让利校验
            var fundsMoney = $$('#fundsMoney').val();
            var lowFundsMoney = $$('#funds').val();
            var receivedNumber = $$('#receivedNumber').val();
            if (receivedNumber == 0 && lowFundsMoney > 0) {
                if (parseFloat(fundsMoney) < parseFloat(lowFundsMoney)) {
                    xxdApp.addNotification({
                        title: '温馨提示',
                        hold: 3000,
                        message: '当前债权已成功转让【' + receivedNumber + '】次，此次转让让利金额不得低于【' + appFunc.fmoney(lowFundsMoney, 2) + '】元！'
                    });
                    return false;
                }
            }

            //债权价值
            var repayCapital = $$('#repayCapital').val();
            repayCapital = parseFloat(repayCapital);
            if (parseFloat(fundsMoney) > repayCapital) {
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '债权转让让利金额不得高于债权价值【' + appFunc.fmoney(repayCapital, 2) + '】元！'
                });
                return false;
            }
            return true;
        },
        /**
         * 校验加价转让金额
         */
        checkoptionsRadios3: function () {
            //校验转让金额是否大于待收本息
            var repaymentAmount = $$('#repaymentAmount').val();  //待收本息
            var totalAmount = parseFloat($$('#totalAmount').val());  //转让价格
            if (repaymentAmount < totalAmount) {
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '转让总价【' + appFunc.fmoney(totalAmount, 2) + '】不能超过债权待收本息【' + appFunc.fmoney(repaymentAmount, 2) + '】！'
                });
                return false;
            }
            return true;
        }
        
    };
    return transferCtrl;
});