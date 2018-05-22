/**
 * Created by pufei on 2015/3/6.
 */
define(function() {
    function creditModel(values) {
        values = values || {};
        this.addTime = values['addTime'];
        this.operatorType = values['operatorType'];
        this.moneyType = values['moneyType'];
        this.workMoney = values['workMoney'];
        this.moneyTypeDouble = values['moneyTypeDouble'];
        this.remark = values['remark'];
    }
    return creditModel;
});
