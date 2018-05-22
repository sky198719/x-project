/**
 * Created by pufei on 2015/3/4.
 */
define(function() {
    function Borrowtender(values) {
        values = values || {};
        this.isShow =values['isShow'];
        this.borrowId = values['borrowId'];
        this.tenderTitle = values['tenderTitle'];
        this.borrowApr = values['borrowApr'];
        this.tenderAddtime = values['tenderAddtime'];
        this.status = values['status'];
        this.tenderTotalAmount = values['tenderTotalAmount'] || '0';
        this.tenderRepaymentAccount = values['tenderRepaymentAccount'] || '0';
        this.statusDouble = values['statusDouble'] || false;
    }
    return Borrowtender;
});
