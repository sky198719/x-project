define(function() {
    function monthFinanceHistoryModel(values) {
        values = values || {};
        this.addTime = values['addTime'];
        this.apr = values['apr'];
        this.joinId = values['joinId'];
        this.deployId = values['deployId'];
        this.effectiveMoney = values['effectiveMoney'] || '0';
        this.name = values['name'];
        this.repayInterest = values['repayInterest'] || '0';
        this.status = values['status'] || '0';
        this.terms = values['terms'];
        this.timeLimit = values['timeLimit'];
        this.finishTime = values['finishTime'];
        this.isFinished = values['isFinished'];
    }
    return monthFinanceHistoryModel;
});
