/**
 * 债权转让标的详情
 */
define(['js/trade/tradeRequestDetailView'], function (tradeRequestDetailView) {
    var formToken = {tokenName:'',token:''};
    var bid =  "";
    var ganame = "";
    var category = "";
    var tradeRequestDetailCtrl = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);
            $$("#trd_goTender").hide();
            var requestId = query.tradeRequestId;
            bid = requestId;
            tradeRequestDetailCtrl.loadRequestDetail(requestId);
            //设置表单token
            formToken = appFunc.setToken({name:"TRADE_DETAIL", id: requestId});
            if(formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                return;
            }
        },

        loadRequestDetail:function(requestId){
            req.callJSON({
                url: 'traderequest/requestDetail/' + requestId + '.do',
                data: {},
                dataType: 'json',
                preloaderTitle: '正在加载数据...',
                success: function (data) {
                    $$("#trd_goTender").show();
                    if(data.resultCode == -404) {
                        xxdApp.loginScreen();
                        return false;
                    }

                    //var userType = data.baseInfo.userType;
                    var companyId = data.borrow.companyId;
                    var isCompany = false;
                    if(companyId != undefined && "" != companyId) {
                        isCompany = true;
                    }
                    var account = data.borrowDetail.account;
                    //个人
                    if(!isCompany && account <= 200000) {
                         $$("#quotaMange").show();
                         $$("#quotaMange_con").html("该自然人在本平台借款余额未超过人民币20万元，在不同网络借贷信息中介机构平台借款总余额未超过人民币100万元。");
                    } else if(isCompany && account <= 1000000){
                        $$("#quotaMange").show();
                        $$("#quotaMange_con").html("该法人或其他组织在本平台借款余额未超过人民币100万元，在不同网络借贷信息中介机构平台借款总余额未超过人民币500万元。");
                    }

                    //投标按钮
                    tradeRequestDetailView.setTenderButton(data);
                    //借款标信息
                    tradeRequestDetailView.borrowBaseInfo(data);
                    //if 自然人 borrowerInfo else 企业 companyInfo
                    if(isCompany) {
                        tradeRequestDetailView.companyInfos(data);
                    } else {
                        //借款人信息
                        tradeRequestDetailView.borrowerInfo(data);
                    }
                    //贷款记录
                    tradeRequestDetailView.loanInfo(data);
                    //费用信息
                    if(data.feeInfo != null) {
                        tradeRequestDetailView.feeInfos(data.feeInfo);
                    }
                    
                    try{
        	            //产品详情
        	            var apr =  $$("#tradeRate").val();
        	            var remainNumber = $$("#remainNumber").val(); 
        	             ganame = "债权转让"+bid;
        	             category = "债权转让/"+apr+" %/"+remainNumber+"个月";
        	            product_detail({id:bid,name:ganame,category:category});
                    }catch(e){}
                    //给投标详情页面带参数
                    /* var funds = "";
                    if (data.tradeRequestDetail.funds >= 0) {
                        funds = '<h4>' + data.tradeRequestDetail.tenderId + '<span class="icon-tips">已折让：' + data.tradeRequestDetail.funds + '元</span></h4>';
                    } else if (data.tradeRequestDetail.funds < 0) {
                        funds = '<h4>' + data.tradeRequestDetail.tenderId + '<span class="icon-tips">已加价：' + (data.tradeRequestDetail.funds * -1) + '元</span></h4>';
                    }

                   var resultJson = {
                        funds: funds,
                        rep_fun: appFunc.fmoney(data.tradeRequestDetail.repayCapital - data.tradeRequestDetail.funds, 2),
                        usable: data.defaultAccount.USABLE,
                        requestId: requestId
                    };      */

                    /**
                     * 事件定义
                     * @type {*[]}
                     */
                    var bindings = [
                        {
                            element: '#trd_checkTenderRecord',
                            event: 'click',
                            handler: tradeRequestDetailCtrl.checkTenderRecord
                        },
                        {
                            element: '#trd_safeGuard',
                            event: 'click',
                            handler: tradeRequestDetailCtrl.safeGuard
                        },
                        {
                            element: '#trd_riskTip',
                            event: 'click',
                            handler: tradeRequestDetailCtrl.riskTip
                        },
                        {
                            element: '#trd_contractModel',
                            event: 'click',
                            handler: tradeRequestDetailCtrl.contractModel
                        },
                        {
                            element: '#trd_goTender',
                            event: 'click',
                            handler: tradeRequestDetailCtrl.goTender,
                           // dataContext: JSON.stringify(resultJson),
                            attrData: {
                                name: "requestId",
                                value: requestId
                            }
                        }
                    ];

                    tradeRequestDetailView.bindEvent({
                            bindings: bindings
                        }
                    );
                }
            });
        },

        /**
         * 跳转投标页面
         */
        goTender: function () {
        	try {
        		//XXD_TRACK._trackEvent({category: "webapp_trade_in", action: "buy_trade", label: "购买此债权", value: "", custval: "" });
        		//加入购物车
        		add_to_cart({id:bid,name:ganame,category:category});
        	} catch (e) {}
            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            //校验转让人是否为当前用户、账户金额是否充足、剩余期数是否为0
            var requestId = $$(this).attr("requestId");
            xxdApp.showIndicator('正在加载数据...');
            req.callJSON({
                url: 'tradepack/checkBeforeBuyTrade.do',
                data: {
                    "requestUserId": $("#tradeRequestUserId").val(),
                    "borrowUserId": $("#borrowDetailUserId").val(),
                    "repayYesAccount": $("#repayYesAccount").val(),
                    "terms": $("#remainNumber").val(),
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
                    if (result.resultCode < 0) {
                        //有错误，弹出错误提示
                        var msg='';
                        var s = result.msg.split(":");
                        if (s[0] == "verifyCode" || s[0] == "payPassword") {
                            msg = s[1];
                        } else {
                            msg = result.msg;
                        }
                        xxdApp.alert(msg, '提示',function(){
                            //设置表单token
                            formToken = appFunc.setToken({name:"TRADE_DETAIL", id: requestId});
                            if(formToken.code != 0) {
                                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                                return;
                            }
                        });
                        return false;
                    }
                    //301为token失效error
                    if (result.resultCode == 301) {
                        return  false;
                    }

                    GS.loadPage('tradeTender/tradeTender.html?requestId=' + requestId);
                }
            });
        },

        /**
         * 查看投标记录详情调用
         */
        checkTenderRecord: function () {
            $$(".popup-bidhistory").html("");
            var borrowId = $$("#trd_borrowId").val();

            req.callJSON({
                url:'borrow/borrowTenderList.do',
                data:{
                    borrowId: borrowId,
                    currentPage: 1
                },
                dataType:'json',
                preloaderTitle:'正在加载数据...',
                success:function(data){
                    if (data.resultCode != 0) {
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: data.msg
                        });
                        return;
                    }

                    var list = data.resultData;
                    tradeRequestDetailView.borrowTenderList(list);
                }
            });
        },
        safeGuard: function () {
            var borrowId = $$("#trd_borrowId").val();
            GS.loadPage('borrow/safeGuard.html?borrowId='+borrowId);
        },
        riskTip : function () {
            GS.loadPage('borrow/riskTip.html');
        },
        contractModel : function () {
            var borrowId = $$("#trd_borrowId").val();
            GS.loadPage('borrow/contractModel.html?borrowId='+borrowId+"&source=2");
        }
    };
    return tradeRequestDetailCtrl;
});
