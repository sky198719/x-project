/**
 * 充值选择银行卡
 */
define(['js/account/topupChooseCardView','js/personal/bankUtil'], function (topupChooseCardView,bankUtil) {
	var bankLimits = {};
    var topupChooseCardCtrl = {
        init: function (event) {

        	var page = appFunc.getEventDetailPageQuery(event);
        	var bankId = page.bankId;
        	
        	
            topupChooseCardCtrl.getBankLimit();
            // 获取用户银行卡列表
            topupChooseCardCtrl.getUserBankList(bankId);

        },

        getUserBankList: function (bankId) {
            req.callPost({
                url: 'personal/userBankList.do',
                data: {},
                dataType: 'json',
                success: function (result) {
                    var resultData = result.resultData;
                    result = result ? result : {resultCode: -400, msg: "系统返回错误"};
                    if (result.resultCode == 0) {
                        var userBankList = [];
                        for (var k = 0; k < resultData.length; k++) {
                            var b = resultData[k];
                            var bankLimit = bankLimits[b.bankName];
                            var checked = false;
                            if(bankId == b.bankId){
                            	checked = true;
                            }
                            if(bankLimit == undefined) {
                                bankLimit = {
                                    "bankCode":b.bankCode,
                                    "dayLimit": "0",
                                    "limit":"0",
                                    "monthLimit":"0"
                                };
                            }
                            userBankList.push({
                                "bankId": b.bankId,
                                "bankCode": b.bankCode,
                                "bankName": b.bankName,
                                "bankAccount": b.bankAccount,
                                "bankLimit" : bankLimit,
                                "checked" : checked
                            });
                        }
                    } else if (result.data == -1) {
                        xxdApp.alert("未登录");
                    }
                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'account/topupChooseCardItem.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                topupChooseCardView.showListItem({result:result,userBankList:userBankList});
                                topupChooseCardCtrl.userBankListBindEvent();
                                // 加载完毕需要重置
                                xxdApp.pullToRefreshDone();
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }
                }
            });

        },
        userBankListBindEvent: function () {
            var bindings = [
                {
                    element: '#topupChooseCard .topup_bankList',
                    event: 'click',
                    handler: topupChooseCardCtrl.clickUserBank
                },
                {
                    element:'#topupChooseCard #addNewCard',
                    event:'click',
                    handler:topupChooseCardCtrl.addNewCard
                }
            ];

            topupChooseCardView.bindEvents({
                    bindings: bindings
                }
            );
        },

        clickUserBank: function () {
            var bankId = $$(this).data('id');
            var bankCode = $$(this).data('code');
            var bankName = $$(this).data('bankName');
            var bankAccount = $$(this).data('bankAccount');
            $$('#topup #bankId').val(bankId);
            $$('#topup #bankCode').val(bankCode);
            $$('#topup #cardNo').val("");
            
            $$("#topup #bankIcon").removeAttr("class");
            $$("#topup #bankIcon").addClass("bank").addClass(bankCode);
            $$("#topup .bankName").html(bankName+'('+bankAccount+')');
            var bankLimit = bankLimits[bankName];
            var bankLimitHtml = '';
            if(bankLimit == undefined) {
                bankLimitHtml = "该卡单笔限额0，单日限额0";
            } else {
                bankLimitHtml = "该卡单笔限额"+bankLimit.limit+"，单日限额"+ bankLimit.dayLimit;
            }
            $$("#topup .bankLimit").html(bankLimitHtml);
        },
        getBankLimit: function () {
            var ret = false;
            req.callPost({
                url: 'mobilePay/getBankLimit.do',
                data: {type:"map"},
                dataType: 'json',
                async: false,
                indicator:true,
                success: function (result) {
                	if(result.resultCode == 0){
                		bankLimits = result.data.map;
                	}else{
                		xxdApp.addNotification({
		                    title: '抱歉',
		                    hold: 2000,
		                    message: '加载银行限额信息失败，请稍后重试'
		                });
                	}
                }
            });
            return ret;
        },
        addNewCard:function(){
            GS.loadPage("personal/addBank.html?path=personal");
        }
    };
    return topupChooseCardCtrl;
});
