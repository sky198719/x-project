require(['base', 'requirejs', 'trackBase'], function ($, requirejs, track) {
    $(function () {
        document.addEventListener('touchstart', function () {
        });
    });

    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r   = window.location.search.substr(1).match(reg);
        if (r != null)return decodeURI(r[2]);
        return null;
    }

    $(document).ready(function () {
        var borrowId = GetQueryString("borrowId");

        //标详情
        borrowDetail(borrowId);
        accountCost();
    });

    function accountCost() {
        $.ajax({
            url     : '/m/account/cost.do',
            dataType: 'json',
            success : function (data) {

                console.log(data);

                //充值费
                var rechargeFee = data.accountCost.rechargeFee;
                $("#rechargeFee").html(rechargeFee.toFixed(2)  + '元');

                //提现费率手续费
                var accountCashRate = data.accountCost.accountCashRate;
                $("#accountCashRate").html(accountCashRate);

                //免费提现次数
                var accountCashFree = data.accountCost.accountCashFree;
                $("#accountCashFree").html(accountCashFree);

                //最低手续费:2元
                var accountCashMinfee = data.accountCost.accountCashMinfee;
                accountCashMinfee     = accountCashMinfee == undefined ? 0 : accountCashMinfee;

                //最高手续费
                var accountCashMaxfee = data.accountCost.accountCashMaxfee;
                accountCashMaxfee     = accountCashMaxfee == undefined ? 0 : accountCashMaxfee;

                if (accountCashFree == 0) {
                    $("#accountCash").html("0.00元");
                } else {
                    $("#accountCash").addClass('aj').html("每月可免费提现" + accountCashFree + "次，超出将按提现金额的" + accountCashRate + "%收取手续费，每笔最低" + accountCashMinfee + "元，最高" + accountCashMaxfee + "元(当月免费提现次数不可叠加至次月使用)");

                }

                //收益管理费
                var revenueManagementFee = data.accountCost.revenueManagementFee;
                revenueManagementFee     = revenueManagementFee * 100;
                $("#revenueManagementFee").html("应收利息的" + revenueManagementFee + "%");

                //债权转让手续费VIP
                var tradeRequestVipFee = data.accountCost.tradeRequestVipFee;
                tradeRequestVipFee     = tradeRequestVipFee * 100;

                //债权转让手续费非vip
                var tradeRequestFee = data.accountCost.tradeRequestFee;
                tradeRequestFee     = tradeRequestFee * 100;

                //债权转让手续费最低
                var tradeRequestMinFee = data.accountCost.tradeRequestMinFee;

                $("#tradeRequest").html("VIP用户转让手续费为本金的" + tradeRequestVipFee + "%，非VIP用户为" + tradeRequestFee + "%，最低为人民币" + tradeRequestMinFee + "元");
            }
        });
    }

    function borrowDetail(borrowId) {
        $.ajax({
            url     : "/m/borrow/detail/" + borrowId + ".do",
            data    : {},
            dataType: 'json',
            success : function (data) {

                console.log(data);

                // 起投金额
                $("#lowesttender").html(data.borrow.LOWESTTENDER);

                var use = data.borrow.USE;
                $("#use").html(use);

                //还款方式
                var repayment = data.borrow.PAYMENTMETHODVALUE;
                $("#repayment").html(repayment);

                var userType = data.baseInfo.userType;
                var account  = data.borrow.ACCOUNT;
                if(userType == 1){
                    $("#personal").show();
                    if(account <= 200000){
                        //个人
                        $("#quotaMange").show();
                        $("#quotaMange_1_com").html("该自然人在本平台借款余额未超过人民币20万元，在不同网络借贷信息中介机构平台借款总余额未超过人民币100万元。");

                    }
                }else if(userType == 2){
                    $("#enterprise").show();
                    if(account <= 1000000){
                        //企业
                        $("#quotaMange").show();
                        $("#quotaMange_1_com").html("该法人或其他组织在本平台借款余额未超过人民币100万元，在不同网络借贷信息中介机构平台借款总余额未超过人民币500万元。");
                    }
                }

                var userType = data.baseInfo.userType;
                if (userType == 1) {
                    var nickName = data.borrowDetail.nickName;
                    $("#nickName").html(nickName);

                    var realName = data.baseInfo.realName;
                    $("#realName").html(realName);

                    var idCardNo = data.baseInfo.idCardNo;
                    $("#idCardNo").html(idCardNo);

                    var creditLevel = data.borrowDetail.creditLevel;
                    $("#creditLevel").html(creditLevel);

                    var borrowCount = data.borrowCount;
                    borrowCount     = borrowCount == undefined ? 0 : borrowCount;
                    $("#borrowCount").html(borrowCount + '次');

                    var overdue = data.overdue;
                    overdue     = overdue == undefined ? 0 : overdue;
                    $("#overdue").html(overdue + '次');

                    var overdueAccount = data.overdueAccount;
                    overdueAccount     = overdueAccount == undefined ? 0 : overdueAccount;
                    $("#overdueAccount").html(overdueAccount + '元');

                    var nativePlace = data.provinceCity;
                    $("#nativePlace").html(nativePlace);

                    var income = data.baseInfo.income;
                    income     = income == undefined ? '无' : income;
                    $("#income").html(income);

                    var isHousePurchase = data.isHousePurchase;
                    if (isHousePurchase != undefined && isHousePurchase == 1) {
                        $("#isHousePurchase").html("购房")
                    } else {
                        $("#isHousePurchase").html("租房")
                    }

                    var isCarPurchase = data.isCarPurchase;
                    if (isCarPurchase != undefined && isCarPurchase == 1) {
                        $("#isCarPurchase").html("是");
                    } else {
                        $("#isCarPurchase").html("否");
                    }

                    var content = data.borrowDetail.content;
                    $("#content").html(content);

                    $("#userType_1_1").show();
                    $("#userType_1_2").show();
                } else {

                    var companyName = data.companyInfo.companyName;
                    $("#companyName").html(companyName);

                    var registeredAddress = data.companyInfo.registeredAddress;
                    $("#registeredAddress").html(registeredAddress);

                    var registeredCapital = data.companyInfo.registeredCapital;
                    registeredCapital     = registeredCapital == undefined ? 0 : registeredCapital;
                    $("#registeredCapital").html(registeredCapital + '万元');

                    var registrationTime = data.companyInfo.registrationTime;
                    $("#registrationTime").html(getMyDate(registrationTime));

                    var comRepName = data.companyInfo.comRepName;
                    $("#comRepName").html(comRepName);

                    var companyAddress = data.companyInfo.companyAddress;
                    $("#companyAddress").html(companyAddress);
                    $("#userType_2_1").show();
                    $("#userType_2_2").show();
                }
            }
        });
    }

    function getMyDate(str) {
        var oDate  = new Date(str),
            oYear  = oDate.getFullYear(),
            oMonth = oDate.getMonth() + 1,
            oDay   = oDate.getDate(),
            oHour  = oDate.getHours(),
            oMin   = oDate.getMinutes(),
            oSen   = oDate.getSeconds(),
            oTime  = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay);//最后拼接时间
        return oTime;
    }

    //补0操作
    function getzf(num) {
        if (parseInt(num) < 10) {
            num = '0' + num;
        }
        return num;
    }


}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});