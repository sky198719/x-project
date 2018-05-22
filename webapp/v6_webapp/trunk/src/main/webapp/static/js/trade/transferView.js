/**
 * Created by pufei on 2015/7/20.
 */
define(function () {
    var transferView = {
        init: function (params) {
            appFunc.bindEvents(params.bindings);
        },
        showTransferInfo:function(data){
            var obj = data.result;
            var receivedNumber = (obj.receivedNumber==null||typeof(obj.receivedNumber)=="undefined")?0:obj.receivedNumber;
            var remainNumber = (obj.remainNumber==null||typeof(obj.remainNumber)=="undefined")?0:obj.remainNumber;
            var html='<h4>'+ obj.borrowName +'</h4>'+
                '<h5 class="font-grey mt5">债权编号：'+ obj.tenderId +'</h5>'+
                '<h5 class="font-grey mt5">原始投资金额：<span class="font-red">'+ appFunc.fmoney(obj.effectiveMoney,2) +'</span> 元</h5>'+
                '<h5 class="font-grey mt5">债权价值：<span id="q_repayCapital">'+ appFunc.fmoney(obj.repayCapital,2) +'</span></h5>'+
                '<h5 class="font-grey mt5">年化收益：'+ obj.rateYear +'%</h5>'+
                '<h5 class="font-grey mt5">已收期数/剩余期数：'+ receivedNumber+'/'+remainNumber +'</h5>';
            $$("#repayCapital").val(obj.repayCapital);
            $$('#q_form').html(html);
            //设置隐藏值
            $$('#q_tenderId').val(obj.tenderId);
            $$('#rate').val(obj.rate);
            $$('#transferLowerFee').val(obj.transferLowerFee);
            //最低转让奖励金额
            $$('#funds').val(obj.funds);
            $$('#receivedNumber').val(receivedNumber);
            $$('#repaymentAmount').val(obj.repaymentAmount);  //待收本息
            transferView.calculationTotal();
            //如果已还期数为0且有最低转让奖励金额，则页面控制必须为[奖励转让]
            if(receivedNumber==0 && obj.funds > 0){
                $$('#q_picker_device').val('让利转让').attr("disabled",true);
                $$('#transferpattern').val("none");
            }
        },
        /**
         * 计算转让总价及手续费
         */
        calculationTotal:function (){
            //转让总价=债权价值-奖励
            var repayCapital = $$('#repayCapital').val();
            var totalAmount = parseFloat(repayCapital)-parseFloat($$('#fundsMoney').val());
            $$('#q_totalAmount').html(appFunc.fmoney(totalAmount,2));
            $$("#totalAmount").val(totalAmount);

            //转让手续费率为千分位
            var fee = (totalAmount*(parseFloat($$('#rate').val())/1000)).toFixed(2);
            //如果手续费小于最低收费，则按最低收费收取债权转让手续费
            if(parseFloat(fee)< parseFloat($$('#transferLowerFee').val())){
                fee = parseFloat($$('#transferLowerFee').val());
            }
            $$('#q_totalFee').html('手续费：<span class="font-red">'+fee+'</span>元');
        }
    };
    return transferView;
});