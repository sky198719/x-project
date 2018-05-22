define(function () {
    function tradeTransferingModel(values) {
        values = values || {};
        this.borrowName = values['borrowName'];
        this.tenderId = values['tenderId'];
        this.requestId = values['requestId'];
        this.addTime = values['addTime'];
        this.repayCapital_funds = values['repayCapital_funds'] || '0';
        this.apr = values['apr'];
        this.remainNumber = values['remainNumber'];
        this.statusName = values['statusName'];
        this.status = values['status'] || false;
    }

    return tradeTransferingModel;
});
