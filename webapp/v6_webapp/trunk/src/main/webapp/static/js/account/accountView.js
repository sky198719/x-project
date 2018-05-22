define(['js/utils/animateNumber'], function (animateNumber) {
    var accountView = {
        init: function (params) {
            appFunc.bindEvents(params.bindings);
        },
        showAccount: function (result) {

            var interestSum = 0;
            var collectingIncome = 0;
            if(result.overview != undefined && result.overview.code == 200000) {
                interestSum = result.overview.data.accumulatedIncome == undefined ? 0 : result.overview.data.accumulatedIncome;
                collectingIncome = result.overview.data.dueInIncome == undefined ? 0 : result.overview.data.dueInIncome;
            }

			//累计收益
            animateNumber.animate({
               element:"div[name='account_interestSum']",
               from:0,
               to:interestSum,
               precision:2,
               intervalNumber:30,
               steps:50,
               valueType:'money'
           });

			//待收收益
            animateNumber.animate({
               element:"div[name='collintest']",
               from:0,
               to:collectingIncome,
               precision:2,
               intervalNumber:30,
               steps:50,
               valueType:'money'
           });

            //$("div[name='account_interestSum']").html(appFunc.fmoney(interestSum,2));

            var usemoney = result.usemoney.toFixed(2);
           // $("div[name='account_usemoney']").html(appFunc.fmoney(usemoney,2));
			//可用余额
            animateNumber.animate({
               element:"div[name='account_usemoney']",
               from:0,
               to:usemoney,
               precision:2,
               intervalNumber:30,
               steps:50,
               valueType:'money'
           });

            var frozen = result.frozen.toFixed(2);
           // $("div[name='account_frozen']").html(appFunc.fmoney(frozen,2));
			//冻结资金
            animateNumber.animate({
               element:"div[name='account_frozen']",
               from:0,
               to:frozen,
               precision:2,
               intervalNumber:30,
               steps:50,
               valueType:'money'
           });
        }
    };
    return accountView;
});
