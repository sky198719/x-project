/**
 * Created by pufei on 2015/3/27.
 */
define(function() {
    function drawmoneyMode(values) {
        values = values || {};
        this.banded = values['banded'];
        this.id = values['id'];
        this.bankCode = values['bankCode'];
        this.bankAccount = values['bankAccount'];
    }
    return drawmoneyMode;
});
