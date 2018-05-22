define(['js/utils/date'], function (DateHandle){
    var tradeRequestDetailView = {
        init: function () {

        },
        bindEvent: function (params) {
            appFunc.bindEvents(params.bindings);
        },
        /**
         * 投标按钮
         * @param data
         */
        setTenderButton: function (data) {
            if (data.tradeRequestDetail.status == 2) {
                $$("#trd_goTender").addClass("btn-disabled");
                $$("#trd_goTender").attr("disabled", "disabled");
                $$("#trd_goTender").html("已抢完");
            }
        },

        /**
         * 借款标信息
         * @param data
         */
        borrowBaseInfo: function (data) {
            if (!data.tradeRequestDetail.nextRepaymentTime) {
                data.tradeRequestDetail.nextRepaymentTime = '';
            }

            var html = "";
            html += '<div class="content-block-inner">';
            if (data.tradeRequestDetail.funds>=0) {
                funds = '<h4>' + data.tradeRequestDetail.borrowName + '<a class="icon-tips">已折让：' + data.tradeRequestDetail.funds + '元</a></h4>';
            } else if (data.tradeRequestDetail.funds<0) {
                funds = '<h4>' + data.tradeRequestDetail.borrowName + '<a class="icon-tips bg-orange">已加价：' + (data.tradeRequestDetail.funds*-1) + '元</a></h4>';
            }
            html += funds;
            html += '<h5 class="font-grey mt5">转让价格：<span class="font-red font24">' + appFunc.fmoney(data.tradeRequestDetail.repayCapital - data.tradeRequestDetail.funds, 2) + '</span> 元</h5>' +
            '<h5 class="font-grey mt5">待收本息：' + appFunc.fmoney(data.tradeRequestDetail.repaymentAmount, 2) + '元</h5>' +
            '<h5 class="font-grey mt5">下一还款日：' + data.tradeRequestDetail.nextRepaymentTime + '</h5>' +
            '</div>' +
            '<div class="content-block-inner border-top">' +
            '<h4 class="font-grey mt5">原始标的借款详情</h4>' +
            '<div><span style="font-size: 15px;" class="font-grey mt5">年化收益：<span class="font-red font18">' + appFunc.fmoney(data.tradeRequestDetail.apr, 2) + '</span> %' +
                '<span style="margin-top: 5px;width: 60px;border-radius: 3px;text-align: center;background:#ff7365;color: white;float: right"> '+data.borrowDetail.borrowStatus+'</span></div>' +
            '<h5 class="font-grey mt5">剩余期限：<span class="font18">' + data.tradeRequestDetail.remainNumber + '</span> ';
            html += '个月';

            html += '</h5>' +
            '<h5 class="font-grey mt5">债权转让编号：' + data.tradeRequestDetail.tenderId + '</h5>' +
            '<h5 class="font-grey mt5">资金用途：' + data.borrowUse + '</h5>' +
            '<h5 class="font-grey mt5">起投金额：' + data.borrowDetail.lowestTender + ' 元</h5>' +
            '<h5 class="font-grey mt5">还款方式：' + data.borrowPaymentmeyhod + '</h5>' +
            '<h5 class="font-grey mt5">预计起息日：满标日计息</h5>' +
            '</div>';
            $$('#trd_borrowtender').html(html);
            $$("#trd_borrowId").val(data.borrowDetail.borrowId);
            var companyId = data.borrow.companyId;
            var isCompany = false;
            if(companyId != undefined && "" != companyId) {
                isCompany = true;
            }

            if(isCompany) {
                $$("#trd_userType").html("借款企业信息");
                $$("#trd_borrowerName").html("企业名称：" + data.companyInfo.companyName);
            } else {
                $$("#trd_userType").html("借款人信息" );
                $$("#trd_borrowerName").html("发布者：" + data.borrowDetail.nickName);
            }

            $$("#tradeRequestUserId").val(data.tradeRequestDetail.userId);
            $$("#borrowDetailUserId").val(data.borrowDetail.userId);
            $$("#repayYesAccount").val(data.tradeRequestDetail.repayCapital - data.tradeRequestDetail.funds);
            $$("#remainNumber").val(data.tradeRequestDetail.remainNumber);
            $$("#tradeRate").val(appFunc.fmoney(data.tradeRequestDetail.apr, 2));
            $$("#tradeTenderId").val(data.tradeRequestDetail.tenderId);
            //$$("#userId").val(data.userId);
        },

        /**
         * 借款人信息
         */
        borrowerInfo: function (data) {
            var html = '';
            html += '<h6 class="font-grey mb5">借款人姓名：' + data.baseInfo.realName + '</h6>' ;
            html += '<h6 class="font-grey mb5">身份证号码：' + data.baseInfo.idCardNo + '</h6>' ;
            html += '<h6 class="font-grey mb5">性别：' + data.baseInfoGender + '</h6>' +
            '<h6 class="font-grey mb5">信用等级：';
            if (data.borrowCreditLevels >= 126 && data.borrowCreditLevels <= 150) {
                html += "A";
            } else if (data.borrowCreditLevels >= 151) {
                html += "AA";
            } else if (data.borrowCreditLevels >= 101 && data.borrowCreditLevels <= 125) {
                html += "B";
            } else if (data.borrowCreditLevels >= 81 && data.borrowCreditLevels <= 100) {
                html += "C";
            } else if (data.borrowCreditLevels >= 61 && data.borrowCreditLevels <= 80) {
                html += "D";
            } else if (data.borrowCreditLevels >= 40 && data.borrowCreditLevels <= 60) {
                html += "E";
            } else if (data.borrowCreditLevels <= 39) {
                html += "HR";
            }
            html += '</h6>' +
            '<h6 class="font-grey mb5">逾期次数：' + data.overdue + '次</h6>' +
            '<h6 class="font-grey mb5">成功借款次数：' + data.borrowCount + '次</h6>' +
            '<h6 class="font-grey mb5">逾期总金额：' + data.overdueAccount + '元</h6>' +
            '<h6 class="font-grey mb5">所在地：' + data.provinceCity + '</h6>';
            /*if (data.borrow.TYPE != 8) {
                html += '<h6 class="font-grey mb10">每月收入：';
                if (data.baseInfo.income == null) {
                    html += '无';
                } else {
                    html += data.baseInfo.income;
                }
                html += '</h6>';
            }*/

            var housePurchase = data.isHousePurchase;
            if (housePurchase != undefined && housePurchase == 1) {
                housePurchase = "购房";
            } else {
                housePurchase = "租房";
            }
            var carPurchase = data.isCarPurchase==0 ? "否" : "是";
            html += '<h6 class="font-grey mb5">住房条件：' + housePurchase + '</h6>' ;
            html += '<h6 class="font-grey mb5">是否购车：' + carPurchase + '</h6>' ;
            $$("#trd_borrowerdatum").html(html);
        },
        /**
         * 借款人企业信息
         */
        companyInfos: function(data) {
            if(data.companyInfo != null) {
                var html = '';
                html += '<h6 class="font-grey mb5">注册资本：' + data.companyInfo.registeredCapital + ' 万元</h6>';
                html += '<h6 class="font-grey mb5">注册地址：' + data.companyInfo.registeredAddress + '</h6>';
                html += '<h6 class="font-grey mb5">成立时间：' + DateHandle.formatDate('yyyy-MM-dd', new Date(data.companyInfo.registrationTime)) + '</h6>';
                html += '<h6 class="font-grey mb5">法定代表人：' + data.companyInfo.comRepName + '</h6>';
                html += '<h6 class="font-grey mb5">办公地点：' + data.companyInfo.companyAddress + '</h6>';
                $$("#trd_borrowerdatum").html(html);
            }
            $$("#trd_creditRecordLi").hide();
           // $$("#trd_contractModelLi").hide();
        },

        /**
         * 贷款记录
         * @param data
         */
        loanInfo: function (data) {
            var html = '';
            html += '<h6 class="font-grey mb5">累计借款：' + appFunc.fmoney(data.account.LOANSUM, 2) + ' 元</h6>' +
            '<h6 class="font-grey mb5">待还金额：' + appFunc.fmoney(data.repaymentSum, 2) + ' 元</h6>' +
            '<h6 class="font-grey mb10">正常还清：' + data.accountNomalPay + ' 次</h6>';
            $$("#trd_creditb").html(html);
        },
        /**
         * 费用信息
         * @param data
         */
        feeInfos: function(data){
            var html = '';
            html += '<h6 class="font-grey mb5">充值费：' + appFunc.fmoney(data.rechargeFee,2) + ' 元</h6>';
            if(data.accountCashFree == 0) {
                html += '<h6 class="font-grey mb5">提现费：0.00 元</h6>' ;
            } else {
                html += '<h6 class="font-grey mb5">提现费：每月可免费提现' + data.accountCashFree + ' 次，超出将按提现金额的' +data.accountCashRate + '% 收取手续费，每笔最低'+data.accountCashMinfee+' 元，最高'+data.accountCashMaxfee+' 元（当月免费提现次数不可叠加至次月使用）</h6>' ;
            }
            html += '<h6 class="font-grey mb5">收益管理费：应收利息的' + data.revenueManagementFee * 100 + '% </h6>' +
                '<h6 class="font-grey mb5">债权转让手续费：VIP用户转让手续费为本金的' + data.tradeRequestVipFee * 100 + '% ，非VIP用户为'+data.tradeRequestFee * 100+'%，最低为人民币'+data.tradeRequestMinFee+' 元</h6>';
            $$("#trd_feeNote").html(html);
        },

        borrowTenderList: function (list) {
            req.callGet({
                url: GC.getHtmlPath() + 'trade/borrowTenderList.html?'+ GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    $$(".popup-bidhistory").html('<div class="content-block">' + result + '</div>');
                    var html = "";
                    if (list.length == 0) {
                        html += "<tr><th colspan='3'>还没有人投呢</th></tr>";
                    } else {
                        for (var k = 0; k < list.length; k++) {
                            var obj = list[k];
                            html += "<tr>" +
                            "<td>" + obj.nickName + "</td>" +
                            "<td>" + appFunc.fmoney(obj.effectiveMoney, 2) + "</td>" +
                            "<td>全部成功</td>" +
                            "</tr>";
                        }
                    }
                    $$("#trd_borrowTenderList").html(html);
                }
            });
        }
    };
    return tradeRequestDetailView;
});
