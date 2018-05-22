define(function () {
    var bankUtil = {
        bankCardCheck: function (param) {
            var result = "";
            req.callPost({
                url: 'mobilePay/bankCardCheck.do',
                data: {
                    cardNo: param.cardNo,
                    bankId: param.bankId
                },
                async: false,
                timeout: 15000,
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    result = data;
                }
            });
            return result;
        }
    };
    return bankUtil;
});
