/**
 * 充值第一步
 */
define(['js/utils/date','js/personal/bankUtil'], function (DateHandle,bankUtil) {
    var isPassPay = true;
    var isPassTitle = "";
    var topupNoCardCtrl = {
        init: function (event) {
            topupNoCardCtrl.isPassTopup();

            var page = appFunc.getEventDetailPageQuery(event);
        	var job = page.job;
      	   	$$('#job').val(job);

            var bindings = [
                {
                    element: '#topupNoCard .recharge-next',
                    event: 'click',
                    handler: topupNoCardCtrl.rechargeNext
                },
                {
                    element: '#topupNoCard .bankLimitIntroduction',
                    event: 'click',
                    handler: topupNoCardCtrl.bankLimitIntroduction
                }
            ];

            appFunc.bindEvents(bindings);
        },

        isPassTopup:function(){
            var result = false;
            var sysConfig = GS.getSysConfig();
            var isPass = sysConfig.pay.isPass;
            if(isPass != null && isPass != "" && isPass == 'false'){
                isPassTitle = result.pay.title;
                isPassPay = false;
                xxdApp.alert(result.pay.title, '提示');
            }
            return result;
        },

        validateRealName: function () {
            var ret = false;
            // 查询是否实名认证
            req.callPost({
                url: 'personal/getRealName.do',
                data: {},
                dataType: 'json',
                async: false,
                indicator:true,
                success: function (result) {
                    var resultData = result.resultData;
                    result = result ? result : {resultCode: -400, msg: "系统返回错误"};
                    if (result.resultCode == 0 && resultData == '') {
                        ret = false;
                    } else if (result.resultCode == -1) {
                        xxdApp.alert("您还未登录，请先登录", '提示');
                        ret = false;
                    } else if (result.resultStatus == 1) {
                        ret = true;
                    }
                }
            });
            return ret;
        },
        
        bankLimitIntroduction: function () {
            GS.loadPage('account/bankLimitIntroduction.html');
        },

        rechargeNext: function () {
            if(!isPassPay){
                xxdApp.alert(isPassTitle,"提示");
                return false;
            }
            var flag = topupNoCardCtrl.validateRealName();
            if (flag) {
            	var cardNo = $$("#topupNoCard #cardNo").val();
	            if (!cardNo) {
	                xxdApp.alert('请输入借记卡卡号', '提示');
	                return false;
	            }
	            var result = bankUtil.bankCardCheck({cardNo:cardNo});
	            if (result.code == 404) {
	                xxdApp.loginScreen();
	                return;
	            }
	            if (result.code != 0) {
	                xxdApp.alert(result.info, "抱歉");
	                return;
	            }
	            var bankInfo = result.data;
	            var bankName = bankInfo.bank_name;
	            var bankCode = bankInfo.bank_code;
	            
                GS.loadPage('account/topup.html?path=account&bankName=' + bankName + "&bankCode=" + bankCode + "&cardNo=" + cardNo);
            } else {
                xxdApp.confirm('尚未实名认证或认证审核中，现在就去', '提示', function () {
                    GS.loadPage('personal/personalInfo.html');
                }, function () {
                    return false;
                });
            }
        }
    };
    return topupNoCardCtrl;
});
