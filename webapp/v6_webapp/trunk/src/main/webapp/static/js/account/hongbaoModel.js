define(function() {
    function hongbaoModel(values) {
        values = values || {};
        this.name = values['name'];
        this.faceValue = values['faceValue'];
        this.quota = values['quota'];
        this.validDate = values['validDate'];
    }
    return hongbaoModel;
});
