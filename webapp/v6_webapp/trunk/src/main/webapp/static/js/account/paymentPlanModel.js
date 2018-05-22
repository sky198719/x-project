/**
 * Created by pufei on 2015/3/6.
 */
define(function() {
    function paymentPlanModel(values) {
        values = values || {};
        this.tenderTitle = values['tenderTitle'];
        this.repayAccount = values['repayAccount'];
        this.porder = values['porder'];
        this.pname = values['pname'];
        this.repayTime = values['repayTime'];
        this.status = values['status'];
        this.statusDouble = values['statusDouble'];
        this.isYYP = values['isYYP'];
    }
    return paymentPlanModel;
});
