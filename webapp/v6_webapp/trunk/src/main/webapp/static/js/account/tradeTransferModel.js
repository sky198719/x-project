define(function () {
    function tradeTransferModel(values) {
        values = values || {};
        this.isShow = values['isShow'];
        this.borrowId = values['borrowId'];
        this.tenderId = values['tenderId'];
        this.borrowName = values['borrowName'];
        this.borrowTenderTime = values['borrowTenderTime'];
        this.rateYear = values['rateYear'];
        this.remainNumber = values['remainNumber'];
        this.repayCapital = values['repayCapital'] || '0';
        this.nextRepaymentTime = values['nextRepaymentTime'];
        this.effectiveMoney = values['effectiveMoney'] || '0';
        this.borrowTenderStatusName = values['borrowTenderStatusName'];
        this.borrowTenderStatus = values['borrowTenderStatus'] || false;
    }

    return tradeTransferModel;
});
