require(['base', 'requirejs', 'trackBase', 'com'], function ($, requirejs, track, com) {
    console.log('这是基础部份');
    console.log('↓');
    $(function () {
        // 共公部份 sta
        document.addEventListener('touchstart', function () {
        });
        // 共公部份 end

        // 服务协议 STA



        // 传参
        var sToken    = com.getUrlValue('token');
        var sClientId = 'XXD_INTEGRATION_PLATFORM';
        // 时间
        var sClientTime = com.getNowTime();
        // @青松 url传参时用下面的获取uel时间，去掉上面的！
        // var sClientTime = com.getUrlValue('clientTime');

        $.ajax({
            url     : '/tradeCenter/investBiz/agreement',
            dataType: 'json',
            // async   : false,
            headers : {
                "Accept"    : "application/json;charset=utf-8",
                "clientId"  : sClientId,
                "clientTime": sClientTime,
                "s"         : "f1fd61c58ad2e29dc072403144bbe78c",
                "token"     : sToken
            },
            data    : {},
            type    : 'GET',
            success : function (str) {
                console.log(str);
                if (str.code == 200000) {
                    var oUser = str.data.userInfo;

                    // 委托人
                    $('.dt_real_name').text(oUser.realName);
                    // 身份证号
                    $('.dt_id_card_no').text(oUser.idCardNo);
                    // 联系电话
                    $('.dt_mobile').text(oUser.mobile);
                    // 日期

                } else {
                    alert('服务协议：' + str.message)
                }
            },
            error   : function (str) {
                alert('服务协议：' + '信息查询失败，请联系客服')
            }
        });
        // 服务协议 END


    });

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});