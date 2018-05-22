define(function () {
    var safeGuardCtrl = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);

            var borrowId = query.borrowId;

            req.callJSON({
                url: "borrow/detail/" + borrowId + ".do",
                data: {},
                indicator: true,
                timeout: 10000,
                success: function (data) {

                    var type = data.borrow.TYPE;
                    //新商贷
                   if(type == 9) {
                       var account = data.borrow.ACCOUNT;
                       var number = 300000;
                       if(account <= number) {
                            $("#xindai1").show();
                       } else {
                           $("#xindai2").show();
                       }
                   } else if (type == 10) {
                       //10新房贷
                       $("#xinfangdai").show();
                   } else if(type == 14){
                       //14新车贷
                       $("#xinchedai").show();
                   }
                }
            });

        }
    };
    return safeGuardCtrl;
});
