define(['js/utils/date','js/plan/planUtils','js/utils/dayController'], function (DateHandle,PlanUtils,dayController) {
    var planTenderView = {
        init: function () {

        },
        bindEvent: function (params) {
            appFunc.bindEvents(params.bindings);
        },

        tenderDetail: function (data) {
            var min_max_apr;
            if (data.scheme.minApr == data.scheme.maxApr) {
                min_max_apr = data.scheme.minApr + '%';
            } else {
                min_max_apr = data.scheme.minApr + '% ~ ' + data.scheme.maxApr + '%';
            }
            var html = '';
            html += '<h4 id="planActivityTip_tender">' + PlanUtils.schemeType(data.scheme.type) + ' - ' + data.scheme.pname + '</h4>';
            html += '<h5 class="font-grey mt10">';
            html += '历史年化收益：';
            html += '<span class="font-red font18">' + min_max_apr + '</span>';

            var increaseApr =  data.scheme.webapp;
           if(increaseApr != undefined && increaseApr != null && increaseApr > 0) {
               html += '<span class="font-red">+' + increaseApr + '%</span>';
           }

            html += '</h5>';
            html += '<h5 class="font-grey mt5">剩余可投： <span class="font-red font18">' + appFunc.fmoney(data.scheme.remacount, 2) + '</span> 元</h5>';
            html += '<h5 class="font-grey mt5">加入限额： <span>' + appFunc.fmoney(data.scheme.leastamount, 2) + '</span> - <span>' + appFunc.fmoney(data.scheme.mostamount, 2) + '</span> 元</h5>';
            html += '<h5 class="font-grey mt5">解锁日期：'+ DateHandle.formatDate('yyyy-MM-dd',new Date(data.endTime)) + '</h5>';


            try {

                req.callJSON({
                    url: 'product/activityLabel.do',
                    data: {
                        productId: data.scheme.schemeId
                    },
                    dataType: 'json',
                    async:false,
                    success:function(result1) {
                        if(result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                            var remark = result1.data.data.remark;
                            html += "<h5 class='font-grey mt5'>活动奖励：<span style='background-color: #ff7365;padding: 2px 10px;width: 79%;text-align: center;border-radius:2px;color:#fff;'>"+ remark + "</span></h5>";
                        }
                    }
                });


            }catch (e) {
                console.log(e);
            }


            $('#plan_schemeId').val(data.scheme.schemeId);
            $('#xpanType').val(data.scheme.type);
            $('#xpanName').val(data.scheme.pname);
            $('#xpanApr').val(min_max_apr);
            $('#xpanCloseterm').val(data.scheme.closeterm);
            
            

            $('#plan_step').val(data.scheme.step);
            $('#plan_default_account_usable').val(data.defaultAccount.usable);
            $$("#accountUsable").html(appFunc.fmoney(data.defaultAccount.usable,2));
            $('#plan_mostamount').val(data.scheme.mostamount);
            if (data.userSchemeInfo != null) {
                $('#plan_userSchemeAccount').val(data.userSchemeInfo.account);
            }
            $('#plan_leastamount').val(data.scheme.leastamount);
            $('#plan_remacount').val(data.scheme.remacount);
            // 预定期、支付期，支付剩余金额
            var status = data.scheme.status;
            if ((status == 1 || status == 2) && data.userSchemeInfo != null && data.userSchemeInfo.surplusMoney > 0) {
                if (data.userSchemeInfo.collectiontype == 1) {
                    $('#picker-interest').val('收益再投标').attr("disabled", "disabled");
                } else if (data.userSchemeInfo.collectiontype == 2) {
                    $('#picker-interest').val('提至新新贷账户').attr("disabled", "disabled");
                }
                $('#presaleMoney').val(data.userSchemeInfo.account).attr("disabled", "disabled");
                $('#presale_affirm_money_show').html(data.userSchemeInfo.surplusMoney);
                $('#presale_affirm_money').val(data.userSchemeInfo.surplusMoney);

                $('#plan_surplusMoney').val(data.userSchemeInfo.surplusMoney);

                html += '<h5 class="font-grey mt5">已付定金：' + appFunc.fmoney(data.userSchemeInfo.earnestMoney, 2) + '</h5>';

                $('#pay_type').val(1);
                $('#plan_confirmTender').html('确认支付剩余金额');
            } else if ((status == 1 || status == 2 || status == 3) && (data.userSchemeInfo == null || (data.userSchemeInfo != null && data.userSchemeInfo.surplusMoney == null))) { // 全额购买
                $('#pay_type').val(2);
                $('#presaleMoney').attr("placeholder", appFunc.fmoney(data.scheme.step, 2) + "元整数倍");
                $('#plan_confirmTender').html('确认全额支付');
            }
            $('#planTenderContent').html(html);
            var dmp_clickVal = "4." + data.scheme.closeterm;
            var dmp_devId = "xsb_" + data.scheme.closeterm;
            var dmp_targetId = PlanUtils.schemeType(data.scheme.type) + ' - ' + data.scheme.pname;
            $("#plan_confirmTender").attr("dmp_target_id", dmp_clickVal);
            $("#plan_confirmTender").attr("dev_id", dmp_devId);
            $("#plan_confirmTender").attr("target_id", dmp_targetId);
            //dayController.planTenderMemberDay();
        },

        showPlanAgreement: function (param) {
            var compiledTemplate = t7.compile(param.result);
            var output = compiledTemplate({
                userSchemeInfo: param.data.userSchemeInfo,
                realName: param.data.realName,
                user: param.data.user,
                scheme: param.data.scheme,
                endTime: param.data.endTime,
                endTime_y: param.data.endTime_y,
                endTime_m: param.data.endTime_m,
                endTime_d: param.data.endTime_d
            });

            $(".popup-bidhistory").html(output);
            xxdApp.popup('.popup-bidhistory');
        }
    };
    return planTenderView;
});
