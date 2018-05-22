define([],function(){
    var bankList = [
        {'bankCode':'0102','bankName':'中国工商银行','className':'icbc'},
        {'bankCode':'0103','bankName':'中国农业银行','className':'abc'},
        {'bankCode':'0104','bankName':'中国银行','className':'boc'},
        {'bankCode':'0105','bankName':'中国建设银行','className':'ccb'},
        {'bankCode':'0301','bankName':'交通银行','className':'bcm'},
        {'bankCode':'0302','bankName':'中信银行','className':'citic'},
        {'bankCode':'0303','bankName':'中国光大银行','className':'ceb'},
        {'bankCode':'0304','bankName':'华夏银行','className':'hxb'},
        {'bankCode':'0305','bankName':'中国民生银行','className':'cmbc'},
        {'bankCode':'0306','bankName':'广东发展银行','className':'cgb'},
        {'bankCode':'0307','bankName':'平安银行股份有限公司','className':'pingan'},
        {'bankCode':'0308','bankName':'招商银行','className':'cmb'},
        {'bankCode':'0309','bankName':'兴业银行','className':'cib'},
        {'bankCode':'0310','bankName':'上海浦东发展银行','className':'spdb'},
        /*{'bankCode':'0319','bankName':'徽商银行','className':''},
        {'bankCode':'0314','bankName':'其他农村商业银行','className':''},
        {'bankCode':'0315','bankName':'恒丰银行','className':''},*/
        {'bankCode':'0403','bankName':'中国邮政储蓄银行股份有限公司','className':'psbc'}
    ]
    var bank = {
        getBank:function(bancCode){
            var bankObj;
            for(var i in bankList) {
                if(bancCode == bankList[i].bankCode){
                    bankObj = bankList[i];
                }
            }
            return bankObj;
        }
    };
    return bank;
});