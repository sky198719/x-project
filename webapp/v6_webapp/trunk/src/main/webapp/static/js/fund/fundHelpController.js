/**
 * Created by chaihuangqi on 2015/11/6.
 */
/**
 * _帮助页面
 */
define(function () {
    var fundHelpCtrl = {
        init: function () {
            fundHelpCtrl.getFundInfo();
        },
        getFundInfo: function () {
            req.callGet({
                url: 'fund/selectFundInfo.do',
                data: {},
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data != null && data.fund != null) {
                        var leastAmount = data.fund.lowestTender;
                        $$("span[name='help_leastAmount']").html(appFunc.fmoney(leastAmount, 2));
                        var mostTender = data.fund.userMostTender;
                        $$("span[name='help_mostAmount']").html(appFunc.fmoney(mostTender, 2));
                        
                        var isActivity = data.activityInfo.isActivity;
                    	var apr = data.fundApr.apr;
                    	var millionsEarningsDay = fundHelpCtrl.calculationIncome(10000, apr, 1);
	                    if(isActivity != undefined && isActivity == 1) {
	                        var floatApr = data.fundApr.floatApr;
	                        millionsEarningsDay = parseFloat(millionsEarningsDay) + parseFloat(fundHelpCtrl.calculationIncome(10000, floatApr, 1));
	                        apr = parseFloat(apr) + parseFloat(floatApr);
	                    }
	                    $$("span[name='help_millionsEarningsDay']").html(appFunc.fmoney(millionsEarningsDay, 2));
	                    $$("span[name='help_calcApr']").html(apr);
	                    
                    }
                }
            });
        },
        calculationIncome: function (money, apr, dayNum) {
            var resultMoney = 0;
            req.callGet({
                url:'fund/calculationIncome.do',
                data:{
                    money:money,
                    apr:apr,
                    dayNum:dayNum
                },
                dataType:'json',
                async:false,
                success:function(data){
                    if(data.resultCode == 0) {
                        resultMoney = data.resultMoney
                    }
                }
            });
            return resultMoney;
        }
    };
    return fundHelpCtrl;
});