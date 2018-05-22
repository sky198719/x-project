define(function () {
    function TradeRequestListModel(values) {
        values = values || {};
        this.requestId = values['requestId'];
        this.tenderId = values['tenderId'];
        this.borrowName = values['borrowName'];
        this.apr = values['apr'];
        this.tradeApr = values['tradeApr'];
        this.rep_fun = values['rep_fun'] || '0.00';

        this.remainNumber = values['remainNumber'] || '0';
        this.status = values['status'] || '';
    }

    TradeRequestListModel.prototype.setValues = function (inputValues) {
        for (var i = 0, len = inputValues.length; i < len; i++) {
            var item = inputValues[i];
            if (item.type === 'checkbox') {
                this[item.id] = item.checked;
            } else {
                this[item.id] = item.value;
            }
        }
    };

    TradeRequestListModel.prototype.validate = function () {
        var result = true;
        if (_.isEmpty(this.firstName) && _.isEmpty(this.lastName)) {
            result = false;
        }
        return result;
    };

    return TradeRequestListModel;
});
