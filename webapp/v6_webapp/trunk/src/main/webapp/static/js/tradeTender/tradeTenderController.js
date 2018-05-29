/**
 * 投标
 */
define(['js/tradeTender/tradeTenderView'], function (tradeTenderView) {
	var bid = "";
	var ganame = "";
	var category = "";
    var tradeTenderCtrl = {
        init: function (event) {
            var page = appFunc.getEventDetailPageQuery(event);
            var requestId = page.requestId;
            tradeTenderCtrl.loadTenderDetail(requestId);
            tradeTenderCtrl.loadBorrowTenderAgreement();
            tradeTenderCtrl.bindEvent();
        },

        bindEvent:function(){
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#trd_confirmTender',
                    event: 'click',
                    handler: tradeTenderCtrl.affirmTender
                },
                {
                    element: '#trd_topup_step',
                    event: 'click',
                    handler: tradeTenderCtrl.topup
                }
            ];

            tradeTenderView.bindEvent({
                    bindings: bindings
                }
            );
        },

        loadTenderDetail:function(requestId){
            req.callJSON({
                url: "traderequest/requestQuick.do",
                data: {
                    requestId: requestId
                },
                preloaderTitle: '正在加载数据...',
                success: function (data) {
                    if(data.tradeRequestDetail.status == 2) {
                        xxdApp.alert("该债权已成功转让，请选择其他债权", '提示', function () {
                            GS.loadPage("trade/tradeRequestListV2.html");
                        });
                        return false;
                    }

                    tradeTenderView.tenderDetail(data);
                }
            });
        },

        loadBorrowTenderAgreement:function(){
            req.callGet({
                url: GC.getHtmlPath() +  'tradeTender/tradeTenderAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    $$(".popup-bidhistory").html(result);
                }
            });
        },

        /**
         * 确认投标
         */
        affirmTender: function () {
            if(appFunc.isRiskResult()) {
                return false;
            }
        	try {
        		//XXD_TRACK._trackEvent({category: "webapp_trade_in", action: "affirm_buy_trade", label: "确认购买债权", value:$("#q_repayYesAccount").val(), custval: "" });
        		var remainNumber = $$("#q_remainNumber").val();
        		 bid = $$("#q_requestId").val();
        		var tenderApr = $$("#q_tenderApr").val();
        		 ganame = "债权转让"+bid;
        		 category = "债权转让/"+tenderApr+" %/"+remainNumber+"个月";
        		//进入结账		
        		CheckOut({id:bid,name:ganame,category:category});
        	} catch (e) {}
            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }

            var rep_fun = $$("#trd_rep_fun").val();
            var userAccount = $$("#trd_userAccount").val();
            if (parseFloat(userAccount) < parseFloat(rep_fun)) {
                xxdApp.alert('账户余额不足，请充值', '提示');
                return false;
            }
            if (!$$("#trd_agreement").is(':checked')) {
                xxdApp.alert("请阅读并同意《_资金出借风险提示函》！", '提示');
                return false;
            }

            //设置表单token
            var formToken = appFunc.setToken({name:"TRADE_TENDER", id: $("#q_requestId").val()});
            if(formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                return;
            }
            xxdApp.modalPassword('确认从您的账户扣除' + appFunc.fmoney(rep_fun, 2) + '元购买债权，请输入支付密码', '支付确认', function (password) {
           	 //try {XXD_TRACK._trackEvent({category: "webapp_trade_in", action: "popup_affirm_paypass", label: "支付密码确定", value: "", custval: "" });} catch (e) {}
                if (password == null || password == '') {
                    xxdApp.alert('请输入支付密码！');
                    return;
                }
                $$("#trd_confirmTender").attr("disabled", "disabled");
                $$("#trd_confirmTender").html("投标中...");

                xxdApp.showIndicator('正在加载数据...');
                //支付密码正确性、验证码正确性、账户可用资金、体验金、红包等校验
                req.callPost({
                    url: 'tradepack/checkBeforeBuyTrade.do',
                    data: {
                        "payPassword": $.md5($.md5(password)),
                        "requestUserId": $("#q_tradeRequestUserId").val(),
                        "borrowUserId": $("#q_borrowDetailUserId").val(),
                        "repayYesAccount": $("#q_repayYesAccount").val(),
                        "terms": $("#q_remainNumber").val(),
                        "tokenName": formToken.data.tokenName,
                        "token": formToken.data.token
                    },
                    dataType: 'json',
                    async: false,
                    success: function (result) {
                        xxdApp.hideIndicator();
                        if (result.resultCode == -99) {
                            xxdApp.loginScreen();
                            return false;
                        }
                        $$("#trd_confirmTender").removeAttr("disabled");
                        $$("#trd_confirmTender").html("确认购买债权");
                        if (result.resultCode < 0) {
                        	//try {XXD_TRACK._trackEvent({category: "webapp_trade_in", action: "trade_in_success", label: "债权购买失败", value: "", custval: "0" });} catch (e) {}
                            var msg = '';
                            //有错误，弹出错误提示
                            var s = result.msg.split(":");
                            if (s[0] == "verifyCode" || s[0] == "payPassword") {
                                msg = s[1];
                            } else {
                                msg = result.msg;
                            }
                            xxdApp.alert(msg, '提示');
                            return false;
                        }
                        //301为token失效error
                        if (result.resultCode == 301) {
                        	//try {XXD_TRACK._trackEvent({category: "webapp_trade_in", action: "trade_in_success", label: "债权购买失败", value: "", custval: "0" });} catch (e) {}
                            return  false;
                        }
                        tradeTenderCtrl.tenderSuccess();
                    }
                });
            }, function (password) {
              	 //try {XXD_TRACK._trackEvent({category: "webapp_trade_in", action: "popup_affirm_cancel", label: "支付密码取消", value: "", custval: "" });} catch (e) {}
                 if (password == null || password == '') {
                     return;
                 }
             });
        },

        tenderSuccess: function () {
            //校验通过后，保存债权转让数据
            req.callPost({
                url: 'tradepack/buyTradePack.do',
                data: {
                    "requestId": $("#q_requestId").val(),
                    "tenderId": $("#q_tenderId").val(),
                    "experienceAccountUsable": 0,
                    "requestUserId": $("#q_tradeRequestUserId").val()
                },
                dataType: 'json',
                indicator:true,
                timeout:50000,
                success: function (result) {
                    if (result.resultCode == 0) {
                    	try {
                    		//XXD_TRACK._trackEvent({category: "webapp_trade_in", action: "trade_in_success", label: "债权购买成功", value: $("#q_requestId").val(), custval: "1" }); //value值改为交易id
                    		//交易成功
                    		var newRedAmount = "0元红包";
                    		transaction({id:bid,name:ganame,category:category,price:$("#q_repayYesAccount").val(),tradeId:$("#q_requestId").val(),revenue:$("#q_repayYesAccount").val(),coupon:newRedAmount});

                    	} catch (e) {}
                    	
                        xxdApp.alert(result.msg, '提示', function () {
                            GS.loadPage("trade/tradeRequestListV2.html");
                        });
                    } else {
                    	//try {XXD_TRACK._trackEvent({category: "webapp_trade_in", action: "trade_in_success", label: "债权购买失败", value: "", custval: "0" });} catch (e) {}
                        xxdApp.alert(result.msg, '抱歉');
                    }
                }
            });
        },

        topup: function () {
        	//try {XXD_TRACK._trackEvent({category: "webapp_trade_in", action: "trade_recharge", label: "充值", value: "", custval: "" });} catch (e) {}
            GS.goTopup();
        }
    };

    return tradeTenderCtrl;
});
