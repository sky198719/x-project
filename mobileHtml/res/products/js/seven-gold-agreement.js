require(['jquery', 'requirejs', 'trackBase', 'com'], function ($, requirejs, track, com) {
    $(function () {
        // 共公部份 sta
        document.addEventListener('touchstart', function () {
        });
        // 共公部份 end

        // 服务协议 STA

        // 测试用
        // var sClientTime = com.getNowTime();

        // 传参
        var sProductId     = com.getUrlValue('productId');
        var sProductCode   = com.getUrlValue('productCode');
        var sProductJoinId = com.getUrlValue('productJoinId');
        var sToken         = com.getUrlValue('token');
        var sClientId      = 'XXD_FRONT_END_H5';
        var sClientTime    = com.getUrlValue('clientTime');
        $.ajax({
            url     : '/tradeCenter/investBiz/agreement' + '?productCode=' + sProductCode + '&productId=' + sProductId + '&productJoinId=' + sProductJoinId,
            dataType: 'json',
            // async   : false,
            headers : {
                "Accept"    : "application/json;charset=utf-8",
                "clientId"  : sClientId,
                "clientTime": sClientTime,
                "token"     : sToken
            },
            data    : {},
            type    : 'GET',
            success : function (str) {
                // 上线前注释掉
                console.log(str);

                if (str.code == 200000) {

                    var oUser = str.data.userInfo, oProduct = str.data.productInfo, oProductJoin = str.data.productJoinInfo;

                    if(oProductJoin){
                        // 甲方投入资金
                        $('.dt_investment_amount').text(oProductJoin.investmentAmount);
                        // $('.dt_use_red_envelope').text(oProduct.useRedEnvelope);
                        // 合约期限
                        $('.dt_frozen_period').text(oProductJoin.frozenPeriod+oProductJoin.frozenPeriodUnit);
                        // 收益处理方式
                        // $('.dt_income_approach').text(oProduct.repayMethod);
                        // 锁定期开始日
                        $('.dt_lock_start_date').text(oProductJoin.addTimeStr);

                        // 违约金
                        $('.dt_penalty_apr').text(oProductJoin.penaltyApr);
                        // 签约日期
                        $('.dt_add_data').text(com.getDateForTime(oProductJoin.addTime));
                    }
                    // 协议编号
                    $('#protocol_number').text(sProductJoinId);
                    // 甲方
                    $('.dt_real_name').text(oUser.realName);
                    // 身份证号
                    $('.dt_id_card_no').text(oUser.idCardNo);
                    // 联系电话
                    $('.dt_mobile').text(oUser.mobile);
                    // _用户名
                    $('.dt_user_name').text(oUser.userName);

                    // 产品名称
                    $('.dt_product_name').text(oProduct.name + '-' + oProduct.period + oProduct.periodUnit);

                    // 预期年化收益率
                    // $('.dt_planned_annual_rate').text((oProduct.plannedAnnualRate*100 + oProduct.floatingRate*100)/100 + '%');

                    // 锁定期结束日
                    // $('.dt_lock_end_date').text(oProduct.closeEndDate);
                    // 整数递增金额
                    $('.dt_step').text(oProduct.step);
                    // 最低加入资金
                    $('.dt_least_invest_amount').text(oProduct.leastInvestAmount);
                    // 最高不得超过
                    $('.dt_most_invest_amount').text(oProduct.mostInvestAmount);
                    // 开放加入阶段
                    $('.dt_start_join_date').text(oProduct.closeStartDate);
                    // 至
                    $('.dt_end_join_date').text(oProduct.closeEndDate);
                    // 结算日
                    $('.dt_settle_date').text(oProduct.settleDate);




                } else {
                    alert('服务协议：' + str.message)
                }
            },
            error   : function (str) {
                alert('服务协议：' + '信息查询失败，请联系客服')
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader("User-Agent", "Apache-HttpClient/4.5.2 (Java/1.7.0_80)");
            }
        });
        // 服务协议 END
    });

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});