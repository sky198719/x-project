/**
 * Created by pufei on 2015/3/27.
 */
define(function() {
    function bankMode(values) {
        values = values || {};
        this.pkey = values['pkey'];
        this.pvalue = values['pvalue'];
    }
    return bankMode;
});
