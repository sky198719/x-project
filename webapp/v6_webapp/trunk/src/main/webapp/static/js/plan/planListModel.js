define(function() {
    function planListModel(values) {
        values = values || {};
        this.schemeid = values['schemeid'];
        this.pname = values['pname'];
        this.account = values['account'] || '0';
        this.min_max_apr = values['min_max_apr'];
        this.closeterm = values['closeterm'] || '0';
        this.schemeStatus = values['schemeStatus'];
        this.userschemeid = values['userschemeid'];
    }
    return planListModel;
});
