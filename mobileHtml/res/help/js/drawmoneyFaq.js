require(['base', 'requirejs', 'trackBase', 'json', 'xxdBridge', 'com'], function ($, requirejs, track, json, xxdBridge, com) {
    $(function () {
        document.addEventListener('touchstart', function () {
        });

        // 获取URL传参 STA
        // function getUrlValue(name) {
        //     var oValue = new RegExp('(?:[\\?|\\#|\\&]' + name + '=)([\\w\\/]+)(?:[&?|]?)');
        //     var url    = window.location.href.match(oValue);
        //     if (url) {
        //         return url[1];
        //     }
        // }

        // 获取URL传参 END
        // 提现说明 STA
        var sClientId = 'XXD_FRONT_END_H5';
        var sClientTime = com.getUrlValue('clientTime');
        var sToken      = com.getUrlValue('token');
        //调试时使用
        // var sClientTime =  new Date().getTime();
        $.ajax({
            url     : '/accountCenter/account/withdraw/initWithdraw',
            dataType: 'json',
            // async   : false,
            headers : {
                "Accept"    : "application/json;charset=UTF-8",
                "clientId"  : sClientId,
                "clientTime": sClientTime,
                "token"     : sToken
            },
            data    : {},
            type    : 'GET',
            success : function (str) {

                if (str.code == 200000) {
                    var count = str.data.data.configWithdrawCount;
                    if (count > 0) {
                        $("#tip").find("li").eq(3).removeClass("hide");
                        $("#tip").find("li").eq(4).removeClass("hide");
                        $("#drawNum").text(str.data.data.configWithdrawCount);
                    }else if (count == 0) {
                        $("#tip").find("li").eq(3).removeClass("hide").text("4、请联系客服或次月再进行提现。");
                    }
                    if (str.data.data.maxFreeWithdrawCount == 0 &&  str.data.data.maxFeeWithdraw == 0 && str.data.data.minFeeWithdraw == 0 && str.data.data.feeRateWithdraw == 0){
                        $("#tip").find("li").eq(2).text("3、提现免手续费。")
                    }else{
                        $("#tip").find("li").eq(2).text("3、当月提现超过" + str.data.data.maxFreeWithdrawCount + "次后，每次收取" + str.data.data.feeRateWithdraw + "%的手续费，最低收费" + str.data.data.minFeeWithdraw + "元，最高收费" + str.data.data.maxFeeWithdraw + "元（每月免费提现次数不可叠加至下月使用）。")
                    }
                } else {
                    alert('提现说明：' + str.message)
                }
            },
            error   : function (str) {
                alert('提现说明：信息查询失败，请联系客服')
            }
        });
        // 提现说明 END

    });

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});