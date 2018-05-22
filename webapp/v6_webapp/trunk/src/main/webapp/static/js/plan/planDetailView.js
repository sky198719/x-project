define(['js/utils/date','js/plan/planUtils'], function (DateHandle,PlanUtils) {
    var planDetailView = {
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
            if (new Number(new Date().getTime()) - new Number(data.scheme.presaleBegin) < 0) {
                $$("#plan_goTender").addClass("btn-xyb-full");
                $$("#plan_goTender").attr("disabled", "disabled");
                $$("#plan_goTender").html("待发布");
                $$("#plan_goTender").show();
            }

            if (data.scheme.remacount != 0) {
                var status = data.scheme.schemeStatus;
                if (status == 1 || status == 2 || status == 3) {<!-- 预定期 、支付期--><!--开放期-->
                    $$("#plan_goTender").show();
                } else if (status == 4) {<!--锁定期-->
                    $$("#planDetailContent").addClass("xyb-earning");
                    $$("#plan_goTender").parent().hide();
                } else if (status == 5 || status == 8) {<!--结束-->
                    $$("#plan_goTender").addClass("btn-disabled");
                    $$("#plan_goTender").attr("disabled", "disabled");
                    $$("#plan_goTender").html("已结束");
                    $$("#plan_goTender").show();
                } else if (status == 6) {<!--撤销-->
                    $$("#plan_goTender").addClass("btn-disabled");
                    $$("#plan_goTender").attr("disabled", "disabled");
                    $$("#plan_goTender").html("撤销的新元宝，不能操作");
                    $$("#plan_goTender").show();
                } else if (status == 7) {<!--等待公开加入-->
                    $$("#plan_goTender").addClass("btn-xyb-full");
                    $$("#plan_goTender").attr("disabled", "disabled");
                    $$("#plan_goTender").html("等待公开加入");
                    $$("#plan_goTender").show();
                } else if (status == 9) {<!--退出中-->
                    $$("#plan_goTender").addClass("btn-disabled");
                    $$("#plan_goTender").attr("disabled", "disabled");
                    $$("#plan_goTender").html("退出中");
                    $$("#plan_goTender").show();
                }
            } else {
                //$$("#planDetailContent").addClass("xyb-joinfull");
                $$("#plan_goTender").parent().hide();
            }
        },
        investmentDetail:function(joinId) {
            req.callJSON({
                url:'product/optimizeSchemeInvestmentDetail.do',
                data:{
                    joinId:joinId
                },
                success:function(result){
                    if(result.code == 200000 && result.data.redEnvelopes != undefined) {
                        var redText = new Array();
                        for(var i in result.data.redEnvelopes) {
                            var b = result.data.redEnvelopes[i];
                            redText.push(b.sourceDesc + b.amount +'元');
                        }
                        if(redText.length > 0) {
                            $$("#planDetailContent").append( '<h5 class="font-grey mt5">使用优惠券： '+redText.join('，')+'</h5>');
                        }
                    }
                }
            });
        },
        /**
         * 新元宝信息
         * @param data
         */
        planInfo: function (data,joinId) {
        	try{
                //#14302展示用户加入新元宝信息 锁定期、已提前退出、已退出、退出中、开放期
                var status = data.scheme.schemeStatus;
                var displayUserInfo = false;
                if(status == 3||status == 4||status == 5||status == 8||status==9){
                    displayUserInfo = true;
                }
	            var min_max_apr;
	            if (data.scheme.minApr == data.scheme.maxApr) {
	                min_max_apr = data.scheme.minApr + '%';
	            } else {
	                min_max_apr = data.scheme.minApr + '% ~ ' + data.scheme.maxApr + '%';
	            }
	            var html = '';
	            html += '<h4>' + PlanUtils.schemeType(data.scheme.type) + ' - ' + data.scheme.pname + '</h4>';
	            html += '<h5 class="font-grey mt10">';
	            html += '历史年化收益：';
	            html += '<span class="font-red font24">' + min_max_apr + '</span>';
                var increaseApr =  data.scheme.webapp;
                if(increaseApr != undefined && increaseApr != null && increaseApr > 0) {
                    html += '<span class="font-red font18">+' + increaseApr + '%</span>';
                }
	            html += '</h5>';
                if(displayUserInfo) {
                    html += '<h5 class="font-grey mt5">加入金额： <span class="font-red font18">' + appFunc.fmoney(data.userSchemeInfo.account, 2) + '</span> 元</h5>';
                } else {
                    html += '<h5 class="font-grey mt5">剩余可投： <span class="font-red font18">' + appFunc.fmoney(data.scheme.remacount, 2) + '</span> 元</h5>';
                }
	            html += '<h5 class="font-grey mt5">锁定期限： <span class="font-red font18">' + data.scheme.closeterm + '</span> 个月</h5>';
                if(!displayUserInfo) {
                    html += '<h5 class="font-grey mt5">保障范围： <span class="font-green">本金 + 利息</span></h5>';
                }


                $('#planDetailContent').html(html);

                planDetailView.investmentDetail(joinId);
	
	            $('#plan_accountUsable').html(appFunc.fmoney(data.defaultAccount.usable, 2));
        	}catch (e) {
                console.log(e.message);
        	}
        },

        /**
         * 计划介绍
         * @param data
         */
        credita: function (data) {
            var min_max_apr;
            if (data.scheme.minApr == data.scheme.maxApr) {
                min_max_apr = data.scheme.minApr + '%';
            } else {
                min_max_apr = data.scheme.minApr + '% ~ ' + data.scheme.maxApr + '%';
            }
            var html = '';
            html += '<h4 class="font-dark font-bold">历史收益说明</h4>';
            html += '<p>历史收益率：<span class="font-red font18">' + min_max_apr + '</span><br>历史收益率是依据往期计划推出的参考收益率，最终收益由自动投资的借款项目收益决定。</p>';
            html += '<h4 class="font-dark font-bold">收益回收方式</h4>';

            html += '<p>1.收益不循环投资，直接返还账户';
            html += '<br>2.收益循环再投资，随本金一次性返还账户';
            html += '<br><span class="font-red">3.锁定期结束后次日本金及收益自动返还至新新贷账户</span></p>';
            html += '<h4 class="font-dark font-bold">期限说明</h4>';

            html += '<p>投资锁定期：<span class="font-red font18">' + data.scheme.closeterm + '个月</span></p>';

            html += '<p><span class="font-bold">投资退出日期：</span><span class="font-blue font18">' + DateHandle.formatDate('yyyy-MM-dd',new Date(data.endTime)) + '</span></p>';
            html += '<h4 class="font-dark font-bold">费用说明</h4>';

            html += '<p>到期退出费用：不收取';
            html += '<br>提前退出费用：投资本金的<span class="font-red font18">' + data.scheme.forfeitpercent + '%</span></p>';

            $('#plan_credita').html(html);
        }

    };
    return planDetailView;
});
