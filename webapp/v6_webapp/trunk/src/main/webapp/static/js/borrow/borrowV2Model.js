define(function() {
    function Borrow(values) {
		values = values || {};
		this.borrowId = values['borrowId'];
		this.borrowType = values['borrowType'] || '';
		this.name = values['name'];
		this.apr = values['apr'] || '0%';
		this.borrowApr = values['borrowApr'] || '0%';
        this.account = values['account'] || '0.00';
        this.accountyes = values['accountyes'] || '0.00';
        this.isTender = values['isTender'] || false;
        this.dayType = values['dayType'];

		this.timeLimit = values['timeLimit'] || '0';
		this.status = values['status'] || '';
		this.surplusAccount = values['surplusAccount'] || '0.00';
        this.borrowPercent = values['borrowPercent'] || '0';
    }

    Borrow.prototype.setValues = function(inputValues) {
		for (var i = 0, len = inputValues.length; i < len; i++) {
			var item = inputValues[i];
			if (item.type === 'checkbox') {
				this[item.id] = item.checked;
			}
			else {
				this[item.id] = item.value;
			}
		}
	};

    Borrow.prototype.validate = function() {
		var result = true;
		if (_.isEmpty(this.firstName) && _.isEmpty(this.lastName)) {
			result = false;
		}
		return result;
	};

    return Borrow;
});
