/**
 * Created by zhangyi on 2015/10/13.
 */

define(function () {
    var tradeRequestRollbackView = {
          showTradeRequestRollback:function(data){
              var obj = data.result;
              var receivedNumber = (obj.receivedNumber==null||typeof(obj.receivedNumber)=="undefined")?0:obj.receivedNumber;
              var remainNumber = (obj.remainNumber==null||typeof(obj.remainNumber)=="undefined")?0:obj.remainNumber;
              var html='<h4>'+ obj.borrowName +'</h4>'+
                  '<h5 class="font-grey mt5">债权编号：'+ obj.tenderId +'</h5>'+
                  '<h5 class="font-grey mt5">原始投资金额：<span class="font-red">'+ appFunc.fmoney(obj.effectiveMoney,2) +'</span> 元</h5>'+
                  '<h5 class="font-grey mt5">年化收益：<span class="font-red">'+ obj.rateYear +'%</span></h5>'+
                  '<h5 class="font-grey mt5">已收期数/剩余期数：'+ receivedNumber+'/'+remainNumber +'</h5>'+
                  '<h5 class="font-grey mt5">债权价值：<span id="q_repayCapital" class="font-red">'+ appFunc.fmoney(obj.repayCapital,2) +'</span>元</h5>'+
                  '<h5 class="font-grey mt5">转让总额：<span class="font-red">'+appFunc.fmoney(obj.repayCapital-obj.funds,2)+'</span>元</h5>';
              $$('#trrbContext').html(html);
          }
    };
    return tradeRequestRollbackView;
});