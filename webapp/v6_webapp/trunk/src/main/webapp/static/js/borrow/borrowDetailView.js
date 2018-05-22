define(['js/borrow/borrowUtils','js/utils/date'],function (borrowUtils,DateHandle) {
    var borrowDetailView = {
        init: function () {

        },
        bindEvent: function(params){
            appFunc.bindEvents(params.bindings);
        },
        /**
         * 投标按钮
         * @param data
         */
        setTenderButton:function(data){
            if (data.borrow.ACCOUNT <= data.borrow.ACCOUNTYES) {
                $$("#goTender").addClass("btn-disabled");
                $$("#goTender").attr("disabled", "disabled");
                $$("#goTender").html("已抢完")
            }
        },

        /**
         * 借款标信息
         * @param data
         */
        borrowBaseInfo:function(data){
            var html = "";
            html += '<div class="content-block-inner">';
            html += '<div><span style="font-size: 18px;">';
            if (data.borrow.AWARD == 0) {
                html += data.borrow.NAME ;
            } else if (data.borrow.AWARD == 1) {
                html += data.borrow.NAME + '<span class="icon-tips">奖励:' + data.borrow.FUNDS + '元</span>';
            } else if (data.borrow.AWARD == 2) {
                html += data.borrow.NAME + '<span class="icon-tips">奖励:' + data.borrow.FUNDS + '%</span>';
            }
            html += '</span>';
            html += '<span style="margin-top: 5px;width: 60px;border-radius: 3px;text-align: center;background:#0099ff;color: white;float: right"> '+data.borrow.STATUSVALUE+'</span></div>';
            html += '<h5 class="font-grey mt5">剩余可投金额：<span class="font-red font24">' + appFunc.fmoney(data.borrow.LACKACCOUNT,2) + '</span> 元</h5>';
            html += '<div style="float:left;font-size: 15px;">进度：</div>';
            var borrowPercentage = data.borrow.BORROWPERCENT;
            html += '<div class="progress-borrow" style="float: left;width:70%"><div style="width:'+borrowPercentage+'" class="progress-borrow-bar"></div></div><span style="float: left;">&nbsp;&nbsp;</span><div style="float: left;width:10%;color: #2096e9;">'+borrowPercentage+'</div>';
            html += '<div style="clear:both"></div>';

            html += '<h5 class="font-grey">年化收益：<span class="font-red font18">' + data.borrow.APR + '</span> %</h5>' +
                    '<h5 class="font-grey mt5">标的期限：<span class="font18">' + data.borrow.PERIOD + '</span> ';

            html += borrowUtils.toPeriodunit(data.borrow.PERIODUNIT);

            html += '</h5>' +
                    '<h5 class="font-grey mt5">资金用途：' + data.borrow.USE + '</h5>' +
                    '<h5 class="font-grey mt5">起投金额：' + data.borrow.LOWESTTENDER + ' 元</h5>' +
                    '<h5 class="font-grey mt5">还款方式：' + data.borrow.PAYMENTMETHODVALUE + '</h5>' +
                    '<h5 class="font-grey mt5">预计起息日：满标日计息</h5>' +
                    '</div>';
            $$('#borrowtender').html(html);
            $$("#borrowId").val(data.borrow.BORROWID);
            $$("#borrowType").val(data.borrow.TYPEVALUE);
            $$("#borroyTimeLimit").val(data.borrow.TIMELIMIT);
            $$("#borroyRate").val(data.borrow.APR);

            var companyId = data.borrow1.companyId;
            var isCompany = false;
            if(companyId != undefined && "" != companyId) {
                isCompany = true;
            }
            if(isCompany) {
                $$("#userType").html("借款企业信息");
                $$("#borrowerName").html("企业名称：" + data.companyInfo.companyName);
            } else {
                $$("#userType").html("借款人信息" );
                $$("#borrowerName").html("发布者：" + data.borrowDetail.nickName);
            }
            $$("#userId").val(data.borrow.USERID);
            try{
	            //产品详情
            	var bid = data.borrow.BORROWID;
            	var name = "散标直投"+bid;
            	var borrowApr = data.borrow.APR;
            	var timelimit = data.borrow.TIMELIMIT;
            	var category = "散标直投/"+borrowApr+"%/"+timelimit+"个月/"+data.borrow.TYPEVALUE;
	            product_detail({id:data.borrow.BORROWID,name:"散标直投"+data.borrow.BORROWID,category:category});
            }catch(e){}
        },

        /**
         * 借款人信息
         */
        borrowerInfo: function(data){
            var html = '';
            var realName =  data.baseInfo.realName;
            var idCardNo =  data.baseInfo.idCardNo;
            html += '<h6 class="font-grey mb5">借款人姓名：' + (realName == undefined || realName == '' ? '' : realName) + '</h6>' ;
            html += '<h6 class="font-grey mb5">身份证号码：' + (idCardNo == undefined ||  idCardNo == '' ? '' : idCardNo) + '</h6>' ;
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
                    '<h6 class="font-grey mb5">逾期总金额：' + data.overdueAccount + '元</h6>' +
                    '<h6 class="font-grey mb5">成功借款次数：' + data.borrowCount + '次</h6>' +
                    '<h6 class="font-grey mb5">所在地：' + data.provinceCity + '</h6>'
            if(data.borrow.TYPE != 8){
                html += '<h6 class="font-grey mb10">每月收入：';
                if (data.baseInfo.income == null) {
                    html += '无';
                } else {
                    html += data.baseInfo.income;
                }
                html += '</h6>';
            }
            //var housePurchase = data.isHousePurchase==0 ? "否" : "是";
            var housePurchase = data.isHousePurchase;
            if (housePurchase != undefined && housePurchase == 1) {
                housePurchase = "购房";
            } else {
                housePurchase = "租房";
            }
            var carPurchase = data.isCarPurchase==0 ? "否" : "是";
            html += '<h6 class="font-grey mb5">住房条件：' + housePurchase + '</h6>' ;
            html += '<h6 class="font-grey mb5">是否购车：' + carPurchase + '</h6>' ;
            $$("#borrowerdatum").html(html);
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
                $$("#borrowerdatum").html(html);
            }
            $$("#creditRecordLi").hide();
           // $$("#contractModelLi").hide();
        },

        /**
         * 贷款记录
         * @param data
         */
        loanInfo: function(data){
            var html = '';
            html += '<h6 class="font-grey mb5">累计借款：' + appFunc.fmoney(data.account.LOANSUM,2) + ' 元</h6>' +
                      '<h6 class="font-grey mb5">待还金额：' + appFunc.fmoney(data.repaymentSum,2) + ' 元</h6>' +
                      '<h6 class="font-grey mb10">正常还清：' + data.accountNomalPay + ' 次</h6>';
            $$("#creditb").html(html);
        },

        borrowTenderList: function(list){
            req.callGet({
                url: GC.getHtmlPath() + 'borrow/borrowTenderList.html?' + GC.getVersion(),
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
                                    "<td>" + appFunc.fmoney(obj.effectiveMoney,2) + "</td>" +
                                    "<td>全部成功</td>" +
                                    "</tr>";
                        }
                    }
                    $$("#borrowTenderList").html(html);
                }
            });
        },
        /**
         * 费用信息
         * @param data
         */
        feeInfo: function(data){
            var html = '';
            html += '<h6 class="font-grey mb5">充值费：' + appFunc.fmoney(data.rechargeFee,2) + ' 元</h6>';
            if(data.accountCashFree == 0) {
                html += '<h6 class="font-grey mb5">提现费：0.00 元</h6>' ;
            } else {
                html += '<h6 class="font-grey mb5">提现费：每月可免费提现' + data.accountCashFree + ' 次，超出将按提现金额的' +data.accountCashRate + '% 收取手续费，每笔最低'+data.accountCashMinfee+' 元，最高'+data.accountCashMaxfee+' 元（当月免费提现次数不可叠加至次月使用）</h6>' ;
            }
            html += '<h6 class="font-grey mb5">收益管理费：应收利息的' + data.revenueManagementFee * 100 + '% </h6>' +
                '<h6 class="font-grey mb5">债权转让手续费：VIP用户转让手续费为本金的' + data.tradeRequestVipFee * 100 + '% ，非VIP用户为'+data.tradeRequestFee * 100+'%，最低为人民币'+data.tradeRequestMinFee+' 元</h6>';
            $$("#feeNote").html(html);
        }
    };
    return borrowDetailView;
});