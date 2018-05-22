/**
 * Created by pufei on 2015/3/3.
 */
define(function () {
    var interestView = {
        init: function (params) {
        },
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        interestShow:function(data){
            if(data.interestSum != '0'){
                $$('#userSum').html(appFunc.fmoney(data.interestSum,2));
            }
            var dueSum = parseFloat(data.loanMap["loan1"].tenderMoneyCount) + parseFloat(data.loanMap["loan2"].tenderMoneyCount) + parseFloat(data.loanMap["loan3"].tenderMoneyCount)
            if(dueSum != '0'){
                $$('#dueSum').html(appFunc.fmoney(dueSum,2));
            }
            if(data.collintest != '0'){
                $$('#collintest').html(appFunc.fmoney(data.collintest,2));
            }
            if(data.tenderSum != '0'){
                $$('#tenderSum').html(appFunc.fmoney(data.tenderSum,2));
            }
            if(data.accounttotal != '0'){
                $$('#accounttotal').html(appFunc.fmoney(data.accounttotal,2));
            }

            $$("#fundTotal").html(appFunc.fmoney(data.fundAccountTotal,2));
        }
    };
    return interestView;
});