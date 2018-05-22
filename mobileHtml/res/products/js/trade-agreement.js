require(['base', 'requirejs', 'trackBase', 'com'], function ($, requirejs, track, com) {
    $(function () {
        // 共公部份 sta
        document.addEventListener('touchstart', function () {
        });
        // 共公部份 end

        // 债权转让协议 STA

        // 传参
        var sTenderId = com.getUrlValue('tenderId');
        var sClientId = 'XXD_FRONT_END_H5';
        var sClientTime    = com.getUrlValue('clientTime');
        // 测试用
        // var sClientTime = com.getNowTime();

        if(sTenderId){
            getUserInfo();
        }

        function getUserInfo(){
            $.ajax({
                url     : '/tradeCenter/borrowTender/agreement/' + sTenderId,
                dataType: 'json',
                // async   : false,
                headers : {
                    "Accept"    : "application/json;charset=utf-8",
                    "s"         : "f1fd61c58ad2e29dc072403144bbe78c",
                    "clientId"  : sClientId,
                    "clientTime": sClientTime
                },
                data    : {},
                type    : 'GET',
                success : function (str) {
                    // 上线前注释掉
                    console.log(str);
                    if (str.code == 200000) {
                        var oBorrow = str.data.borrowInfo, oPackUser = str.data.tradePackUserInfo, oRequest = str.data.tradeRequestInfo, oRequestUser = str.data.tradeRequestUserInfo;


                        // --债权受让人信息
                        // 甲方		tradeRequestUserInfo.realname
                        if (oRequestUser) {
                            $('.dt_a_real_name').text(oRequestUser.realName);
                            // 证件类型	tradeRequestUserInfo.idCardDesc
                            $('.dt_a_id_card_desc').text(oRequestUser.idCardDesc);
                            // 证件号码	tradeRequestUserInfo.idcardno
                            $('.dt_a_id_card_no').text(oRequestUser.idCardNo);
                            // 新新贷用户名	tradeRequestUserInfo.userName
                            $('.dt_a_user_name').text(oRequestUser.userName);
                        }

                        // 乙方		tradePackUserInfo.realname
                        if (oPackUser) {
                            $('.dt_b_real_name').text(oPackUser.realName);
                            // 证件类型	tradePackUserInfo.idCardDesc
                            $('.dt_b_id_card_desc').text(oPackUser.idCardDesc);
                            // 证件号码	tradePackUserInfo.idcardno
                            $('.dt_b_id_card_no').text(oPackUser.idCardNo);
                            // 新新贷用户名	tradePackUserInfo.userName
                            $('.dt_b_user_name').text(oPackUser.userName);
                        }

                        // --甲方标的债权主要信息。
                        // 借款本金	borrowInfo. borrowAmount
                        if (oBorrow) {
                            $('.dt_borrow_amount').text(oBorrow.borrowAmount);
                            // 借款年化利率	borrowInfo. borrowApr
                            $('.dt_borrow_apr').text(oBorrow.borrowApr);
                            // 借款期限	borrowInfo. borrowPeriod
                            $('.dt_borrow_period').text(oBorrow.borrowPeriod);
                            // borrowInfo. borrowStartTime
                            $('.dt_borrow_start_time').text(oBorrow.borrowStartTime);
                            // borrowInfo. borrowEndTime
                            $('.dt_borrow_end_time').text(oBorrow.borrowEndTime);
                            // 还款日		borrowInfo. repaymentDay
                            $('.dt_repayment_day').text(oBorrow.repaymentDay);
                            // 剩余借款本金	borrowInfo. remainingAmount
                            $('.dt_remaining_amount').text(oBorrow.remainingAmount);
                            // 剩余还款期数	borrowInfo. remainingRepayPeriod
                            $('.dt_remaining_repay_period').text(oBorrow.remainingRepayPeriod);
                            // borrowInfo. repayStartTime
                            $('.dt_repay_start_time').text(oBorrow.repayStartTime);
                            // borrowInfo. repayEndTime
                            $('.dt_repay_end_time').text(oBorrow.repayEndTime);
                        }

                        // --标的债权转让信息
                        // 标的债权金额	tradeRequestInfo. tenderAmount
                        if (oRequest) {
                            $('.dt_tender_amount').text(oRequest.tenderAmount);
                            // 转让总额	tradeRequestInfo. requestAmount
                            $('.dt_request_amount').text(oRequest.requestAmount);
                            // 转让服务费	tradeRequestInfo. requestServiceFee
                            $('.dt_request_service_fee').text(oRequest.requestServiceFee);
                            // 剩余还款期数	tradeRequestInfo. remainingRepayPeriod
                            $('.dt_trade_remaining_repay_period').text(oRequest.remainingRepayPeriod);
                            // tradeRequestInfo. repayStartTime
                            $('.dt_trade_repay_start_time').text(oRequest.repayStartTime);
                            // tradeRequestInfo. repayEndTime
                            $('.dt_trade_repay_end_time').text(oRequest.repayEndTime);
                            // 转让日期	tradeRequestInfo. requestTime
                            $('.dt_request_time').text(oRequest.requestTime);
                        }
                    } else {
                        alert('服务协议：' + str.message)
                    }
                },
                error   : function (str) {
                    alert('服务协议：' + '信息查询失败，请联系客服')
                }
            });
        }
        // 债权转让协议 END
    });

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});