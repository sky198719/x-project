/**
 * Created by pufei on 2015/4/2.
 */
define(function() {
    function bank(values) {
        values = values || {};
        this.banded = values['banded'];
        this.bankAccount = values['bankAccount'];
        this.bankCode = values['bankCode'];
        this.id = values['id'];
        this.bankName = values['bankName']
    }
    return bank ;
})
