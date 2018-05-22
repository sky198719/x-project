define(function () {
    function tradeTransferOutModel(values) {
        values = values || {};
        this.borrowName = values['borrowName'];
        this.requestId = values['requestId'];
        this.outTime = values['outTime'];
        this.amount_funds = values['amount_funds'] || '0';
        this.apr = values['apr'];
        this.statusName = values['statusName'];
        this.status = values['status'] || false;
    }

    return tradeTransferOutModel;
});
