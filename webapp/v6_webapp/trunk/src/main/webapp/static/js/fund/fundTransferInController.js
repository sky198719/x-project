/**
 * Created by chaihuangqi on 2015/8/14.
 */
/**
 * 日日盈转入确认投资页面
 */
define(['js/utils/date','js/utils/xxd_dmp'], function (DateHandle,xxd_dmp) {
	  var bid = "";
      var categorys="";
    var fundTransferInCtrl = {
        init: function () {
            fundTransferInCtrl.eventBind();
            fundTransferInCtrl.initTransferInData();

        },

        eventBind: function () {
            var bindings = [
                {
                    element: 'a[name="dde_agreement"]',
                    event: 'click',
                    handler: fundTransferInCtrl.ddeAgreement
                },
                {
                    element: '#confirmButton',
                    event: 'click',
                    handler: fundTransferInCtrl.confirmClick
                },
                {
                    element: '#dde_topup_step',
                    event: 'click',
                    handler: fundTransferInCtrl.topup
                },
                {
                    element: '#trade_num',
                    event: 'keyup',
                    handler: fundTransferInCtrl.enableConfirm
                },
                {
                    element: '#trade_num',
                    event: 'change',
                    handler: fundTransferInCtrl.onTradeNumChange
                }
            ];
            appFunc.bindEvents(bindings);
        },

        initTransferInData: function () {
            req.callJSON({
                url: "fund/initTransferInData.do",
                data: {},
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if (data != null) {
                        $('#dde_accountUsable').html(appFunc.fmoney(data.defaultAccount.usable, 2));
                        $('#dde_arrivalDate').html(DateHandle.formatDate('yyyy-MM-dd', data.arrivalDate));
                        $('#dde_investUsable').html(appFunc.fmoney(data.investUsable, 2));
                        $('#v_accountUsable').val(data.defaultAccount.usable);
                        var investUsable =  data.investUsable;
                        investUsable = (investUsable == undefined || "" == investUsable) ? "0" : investUsable;
                        $('#v_investUsable').val(investUsable);
                        $('#v_leastAmount').val(data.leastAmount);
                        $('#v_mostAmount').val(data.mostAmount);
                        
                         bid = data.fundApr.fcode;
                         categorys="日日盈/"+data.fundApr.apr+"%/1天";
                    }
                }
            });
        },

        /** 日日盈协议 */
        ddeAgreement: function () {
            req.callGet({
                url: GC.getHtmlPath() + 'fund/fundAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    fundTransferInCtrl.showPlanAgreement({
                        result: result
                    });
                }
            });
        },
        showPlanAgreement: function (param) {
            var leastAmount = $('#v_leastAmount').val();
            var mostAmount = $('#v_mostAmount').val();
            var compiledTemplate = t7.compile(param.result);
            var output = compiledTemplate({
                leastAmount: appFunc.fmoney(leastAmount, 2),
                mostAmount: appFunc.fmoney(mostAmount, 2)
            });
            $(".popup-bidhistory").html(output);
            xxdApp.popup('.popup-bidhistory');
        },
        /** 确认转入 */
        confirmClick: function () {
            if(appFunc.isRiskResult()) {
                return false;
            }

            if ($$("#confirmButton").hasClass("button-001-disable")) {
                return;
            }
            try {
                //XXD_TRACK._trackEvent({category: "webpp_fund_in", action: "popup_affirm_tochange", label: "确认转入", value: $('#trade_num').val(), custval: "" });
              //进入结账		
                CheckOut({id:bid,name:"日日盈：日日盈",category:categorys});
            } catch (e) {
            }
            var tradeNum = $('#trade_num').val();
            if (tradeNum == null || tradeNum == 0) {
                xxdApp.alert("转入金额不能为空，请重新输入", '提示');
                return false;
            }

            if(!appFunc.isFloat(tradeNum)){
                xxdApp.alert("请输入正确的转入金额",'提示');
                return false;
            }

            var accountUsable = $('#v_accountUsable').val();
            var investUsable = $('#v_investUsable').val();
            var leastAmount = $('#v_leastAmount').val();
            if (parseFloat(tradeNum) < parseFloat(leastAmount)) {
                xxdApp.alert("您的转入金额小于最低起投金额" + parseFloat(leastAmount) + "元，请重新输入", '提示');
                return false;
            }
            if (parseFloat(tradeNum) > parseFloat(investUsable)) {
                xxdApp.alert("您的转入金额大于剩余可投金额" + parseFloat(investUsable) + "元，请重新输入", '提示');
                return false;
            }
            if (parseFloat(tradeNum) > parseFloat(accountUsable)) {
                xxdApp.alert("您的可用余额不足" + parseFloat(tradeNum) + "元，请先充值", '提示');
                return false;
            }
            if (!$$("#dde_agreementCheck").is(':checked')) {
                xxdApp.alert("请阅读并同意《日日盈协议书》！", '提示');
                return false;
            }

            //设置表单token
            var formToken = appFunc.setToken({name:"FUND_PURCHASE", id: ''});
            if(formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                return;
            }

            xxdApp.modalPassword('确认从您的账户扣除' + appFunc.fmoney(tradeNum, 2) + '元转入日日盈账户，请输入支付密码', '支付确认', function (password) {
                if (password == null || password == '') {
                    xxdApp.alert('请输入支付密码！');
                    return;
                }

                $$("#confirmButton").html("转入中...");
                $$("#confirmButton").removeClass("button-001");
                $$("#confirmButton").addClass("button-001-disable");
                // try {XXD_TRACK._trackEvent({category: "webpp_fund_in", action: "popup_affirm_paypass", label: "支付密码确定", value: tradeNum, custval: "" });} catch (e) {}

                xxdApp.showIndicator('正在转入,请稍后...');
                req.callPost({
                    url: 'fund/fundTrade.do',
                    data: {
                        payPwd: $.md5($.md5(password)),
                        tradeAccount: tradeNum,
                        opType: "1",
                        tokenName: formToken.data.tokenName,
                        token: formToken.data.token
                    },
                    dataType: 'json',
                    timeout:10000,
                    success: function (data) {
                        xxdApp.hideIndicator();
                        if (data.result == 0) {
                            try{
                            	//交易成功
                            	var tradeId = data.tradeId;
                                //XXD_TRACK._trackEvent({category: "webpp_fund_in", action: "popup_fund_success", label: "转入成功", value: tradeId, custval: "1" });  //value值改为交易id
                                var xxd_utm_source = xxd_dmp.getDmpUrlParam("xxd_utm_source") || "";
                                if (xxd_utm_source != "" && xxd_utm_source != null && xxd_utm_source != undefined){
                                    xxd_dmp.clickDmpEvent(xxd_dmp.dmp_data_obj("click","buy_success",$("#confirmButton").attr("dev_id"),$("#confirmButton").attr("target_id"),"","",$(".dmp_content_input").val(),$("#confirmButton").attr("dmp_redBag"),xxd_utm_source));
                                }
                            	var newRedAmount = "0元红包";
                            	transaction({id:bid,name:"日日盈：日日盈",category:categorys,price:tradeNum,tradeId:tradeId,revenue:tradeNum,coupon:newRedAmount});
                            	//growingIO布码
                              var GAUserId = $$("body").attr("data-userId");
                            	growingIOInits({userId:GAUserId});
                            }catch(e){}
                            var tenderAmount = appFunc.fmoney(tradeNum,2);
                            var activity_url = data.activity_url;
                            GS.loadPage("fund/fundTransferInSucceed.html?path=fund&tenderAmount=" + tenderAmount + "&activity_url="+activity_url);
                        } else {
                        	// try{ XXD_TRACK._trackEvent({category: "webpp_fund_in", action: "popup_fund_success", label: "转入失败", value: tradeNum, custval: "0" });}catch(e){}
                           // if(data.result != 301) {
                                xxdApp.alert(data.desc, '提示');
                           // }
                        }
                        $$("#confirmButton").removeClass("button-001-disable");
                        $$("#confirmButton").addClass("button-001");
                        $$("#confirmButton").html("确认转入");
                    }
                });
            }, function (password) {
            	 // try {XXD_TRACK._trackEvent({category: "webpp_fund_in", action: "popup_affirm_cancel", label: "支付密码取消", value: tradeNum, custval: "" });} catch (e) {}
                if (password == null || password == '') {
                    return;
                }
            });

        },
        topup: function () {
            //try{XXD_TRACK._trackEvent({category: "webpp_fund_in", action: "detail_recharge", label: "充值", value: "", custval: "" });}catch(e){}
            GS.goTopup();
        },
        enableConfirm: function (key) {
            var tradeNum = $('#trade_num').val().replace(/[^\d\.]/g,'');
            $('#trade_num').val(tradeNum);
        },
        onTradeNumChange: function () {
            // try {XXD_TRACK._trackEvent({category: "webpp_fund_in", action: "tochange_money", label: "转入金额", value: $('#trade_num').val(), custval: "" });} catch (e) {}
        }
    };
    return fundTransferInCtrl;
});
