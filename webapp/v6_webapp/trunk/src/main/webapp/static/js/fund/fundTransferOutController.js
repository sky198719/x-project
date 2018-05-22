/**
 * Created by chaihuangqi on 2015/8/14.
 */
/**
 * 日日盈转出确认投资页面
 */
define(function () {
    var fundTransferOutCtrl = {
        init: function () {
            fundTransferOutCtrl.eventBind();
            fundTransferOutCtrl.initTransferOutData();
        },
        eventBind: function () {
            var bindings = [
                {
                    element: '#confirmButton',
                    event: 'click',
                    handler: fundTransferOutCtrl.confirmClick
                },
                {
                    element: '#transferOutAll',
                    event: 'click',
                    handler: fundTransferOutCtrl.transferOutAll
                },
                {
                    element: '#trade_num',
                    event: 'keyup',
                    handler: fundTransferOutCtrl.enableConfirm
                },
                {
                    element: '#trade_num',
                    event: 'change',
                    handler: fundTransferOutCtrl.onTradeNumChange
                }
            ];
            appFunc.bindEvents(bindings);
        },
        initTransferOutData: function () {
            req.callJSON({
                url: "fund/initTransferOutData.do",
                data: {},
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if (data != null && data.fundTotal != null) {
                        var maxAvailableRansom = data.fundTotal.maxAvailableRansom;
                        $$("#maxMoney").html('最大可转出<span class="font-orange">' + appFunc.fmoney(maxAvailableRansom, 2) + "</span>元");
                        $('#dde_account').val(maxAvailableRansom);
                    }
                }
            });
        },
        /** 确认转出 */
        confirmClick: function () {

            if ($$("#confirmButton").hasClass("button-001-disable")) {
                return;
            }
            try {
                //XXD_TRACK._trackEvent({category: "webpp_fund_out", action: "affirm_out", label: "确认转出", value: $('#trade_num').val(), custval: "" });
            } catch (e) {
            }
            var tradeNum = $('#trade_num').val();
            if (tradeNum == null || tradeNum == 0) {
                xxdApp.alert("转出金额不能为空，请重新输入", '提示');
                return false;
            }

            if( !appFunc.isFloat(tradeNum)) {
                xxdApp.alert("请输入正确的转出金额","提示");
                return false;
            }

            var account = $('#dde_account').val();
            if (parseFloat(tradeNum) > parseFloat(account)) {
                xxdApp.alert("您的转出金额大于最大可转出金额，请重新输入", '提示');
                return false;
            }
            //设置表单token
            var formToken = appFunc.setToken({name:"FUND_REDEEM", id: ''});
            if(formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                return;
            }
            xxdApp.modalPassword('确认从您的日日盈账户转出' + appFunc.fmoney(tradeNum, 2) + '元至您的账户，请输入支付密码', '支付确认', function (password) {
                if (password == null || password == '') {
                    xxdApp.alert('请输入支付密码！');
                    return;
                }

                $$("#confirmButton").html("转出中...");
                $$("#confirmButton").removeClass("button-001");
                $$("#confirmButton").addClass("button-001-disable");
                //try {XXD_TRACK._trackEvent({category: "webpp_fund_out", action: "popup_affirm_paypass", label: "支付密码确定", value: tradeNum, custval: "" });} catch(e){}
                xxdApp.showIndicator('正在转出,请稍等...');
                req.callPost({
                    url: 'fund/fundTrade.do',
                    data: {
                        payPwd: $.md5($.md5(password)),
                        tradeAccount: tradeNum,
                        opType: "2",
                        tokenName: formToken.data.tokenName,
                        token: formToken.data.token
                    },
                    dataType: 'json',
                    timeout:10000,
                    success: function (data) {
                        xxdApp.hideIndicator();
                        if (data.result == 0) {
                            redemption({id:data.productId,name:"日日盈赎回",category:"日日盈/" + data.apr + "%",price:data.price,tradeId:data.tradeid,affiliation:data.servicenum});
                        	 //try {XXD_TRACK._trackEvent({category: "webpp_fund_out", action: "popup_fund_out_success", label: "转出成功", value: tradeNum, custval: "1" });} catch (e) {}
                            GS.loadPage("fund/fundTransferOutSucceed.html?path=fund");
                        } else {
                        	 //try {XXD_TRACK._trackEvent({category: "webpp_fund_out", action: "popup_fund_out_success", label: "转出失败", value: tradeNum, custval: "0" });} catch (e) {}
                            //if(data.result != 301) {
                                xxdApp.alert(data.desc, '提示');
                            //}
                        }
                        $$("#confirmButton").addClass("button-001");
                        $$("#confirmButton").removeClass("button-001-disable");
                        $$("#confirmButton").html("确认转出");
                    }
                });
            }, function (password) {
            	 //try {XXD_TRACK._trackEvent({category: "webpp_fund_out", action: "popup_affirm_cancel", label: "支付密码取消", value: tradeNum, custval: "" });} catch (e) {}
                if (password == null || password == '') {
                    return;
                }
            });
        },
        transferOutAll: function () {
        	//try {XXD_TRACK._trackEvent({category: "webpp_fund_out", action: "allin_out", label: "全部转出", value: $('#dde_account').val(), custval: "" });}catch (e){}
            var account = $('#dde_account').val();
            $('#trade_num').val(account);
            fundTransferOutCtrl.enableConfirm();
        },
        enableConfirm: function () {
            var tradeNum = $('#trade_num').val().replace(/[^\d\.]/g,'');
            $('#trade_num').val(tradeNum);
        },
        onTradeNumChange: function () {
            //try { XXD_TRACK._trackEvent({category: "webpp_fund_out", action: "out_money", label: "转出金额", value: $('#trade_num').val(), custval: "" });} catch (e) {}
        }
    };
    return fundTransferOutCtrl;
});
