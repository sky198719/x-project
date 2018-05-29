/**
 * _
 */
define(['js/plan/planListViewV2','js/plan/planUtils'], function (planListViewV2,PlanUtils) {
    // 加载flag
    var loading = false;
    var planListV2Ctrl = {
        init: function () {
            /**
             * 事件定义
             *  pullToRefresh 下拉刷新
             *  infiniteScroll 无限刷新
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: planListV2Ctrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: planListV2Ctrl.infiniteScroll
                },
                {
                    element: '#planv2_fundJoin',
                    event: 'click',
                    handler: planListV2Ctrl.toFundJoin
                },
                {
                    element: '#pv2_fundList li',
                    event: 'click',
                    handler: planListV2Ctrl.toFundDetail
                }
            ];
            planListViewV2.bindEvents({
                    bindings: bindings
                }
            );
            planListV2Ctrl.getFundInfo();
            planListV2Ctrl.getFundLimit();
            planListV2Ctrl.pullToRefresh();
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            planListV2Ctrl.selectPlanList("", 1, "pull");
            $$("#planCurrentPage").val(1);
            loading = false;
        },
        /**
         * 无限滚动
         */
        infiniteScroll: function () {
            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            //==========================切记此处不能删除============================
            loading = true;
            //==========================切记此处不能删除============================
            var totalPage = $$("#planTotalPage").val();
            var currentPage = $$("#planCurrentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage > totalPage) {
                //提示标的已经全部显示
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '已经到底了'
                });
                loading = true;
                return;
            }
            planListV2Ctrl.selectPlanList("", currentPage, "push");
            $$("#planCurrentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        /**
         * _列表
         * @param closeTerm  锁定期
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectPlanList: function (closeTerm ,currentPage, type) {
            req.callJSON({
                url: "xplan/schemeList.do",
                data: {
                    currentPage: currentPage,
                    closeTerm: closeTerm,
                    pageSize: 20
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    var list = "";
                    if (data.listData != null) {
                        list = data.listData;
                        $$("#planTotalPage").val(data.pageNums);
                    }
                    var planList = [];
                    if (list != null && list != "") {
                        for (var i = 0; i < list.length; i++) {
                            var b = list[i];
                            // 过滤撤销的_
                            if (b.STATUS == 5) {
                                continue;
                            }


                            var maxapr = '';var maxapr_dec='';
                            var values = new String(b.MAXAPR).split('.');
                            maxapr =values[0];
                            if(values.length != 1) {
                                maxapr_dec = values[1];
                            }
                            var fixedImg='static/img/xxd/dq-gray.png';
                            var planPercent = 0;
                            var buttonClass = '';
                            var buttonName = '立即抢购';
                            var status = b.schemeStatus;
                            var isJoin = false;var isClose = false;
                            if (b.REMACOUNT != 0) {
                                if (status == 1 || status == 2 || status == 3) {<!-- 预定期 、支付期--><!--开放期-->
                                    isJoin = true;
                                    var investAccount = parseFloat(b.ACCOUNT)-parseFloat(b.REMACOUNT);
                                    planPercent = Math.round(parseFloat(investAccount)/parseFloat(b.ACCOUNT)*100);
                                    fixedImg='static/img/xxd/dq-blue.png';
                                    buttonClass = ' qg-btn';
                                } else if (status == 4 || status == 5 || status == 6 || status == 7) {<!--锁定期--><!--结束--><!--撤销-->
                                    isClose = true;
                                } else if (status == 0) {<!--等待公开加入-->
                                    buttonClass = ' qg-btn qg-btn-waiting';
                                    buttonName = '10:00开抢';
                                }
                            } else {
                                isClose = true;
                            }
                            planList.push({
                                'schemeid': b.SCHEMEID,
                                'pname': PlanUtils.schemeType(b.TYPE) + ' - ' + b.PNAME,
                                'account': appFunc.fmoney(b.ACCOUNT/10000, 2),
                                'maxapr': maxapr,
                                'maxapr_dec': maxapr_dec,
                                'closeterm': b.CLOSETERM,
                                'planPercent':planPercent +"%",
                                'isJoin':isJoin,
                                'buttonClass':buttonClass,
                                'buttonName':buttonName,
                                'isClose':isClose,
                                'fixedImg':fixedImg
                            });
                        }
                    }

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'plan/planListItemV2.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                planListViewV2.showListItem({
                                    result: result,
                                    planList: planList,
                                    type: type
                                });
                                planListV2Ctrl.planListBindEvent();
                                // 加载完毕需要重置
                                xxdApp.pullToRefreshDone();
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }
                }
            });

            setTimeout(function () {
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            }, 5000);
        },
        /**
         * 列表点击事件绑定
         */
        planListBindEvent: function () {
            var bindings = [
                {
                    element: '#planListV2  li',
                    event: 'click',
                    handler: planListV2Ctrl.toPlanDetail
                },
                {
                    element: '#planListV2  button[name="立即抢购"]',
                    event: 'click',
                    handler: planListV2Ctrl.toPlanTender
                }
            ];
            planListViewV2.bindEvents({
                    bindings: bindings
                }
            );
        },

        toPlanDetail: function () {
            var planId = $(this).attr("data-id");
            GS.loadPage('plan/planDetail.html?planId=' + planId + "&path=plan");
        },

        toPlanTender: function () {
            var planId = $(this).attr("data-id");
            GS.loadPage('planTender/planTender.html?planId=' + planId);
        },

        toFundJoin: function () {
            $$('input[name="index_fund_join"]').val("true");
            if($(this).hasClass("qg-btn")) {
                GS.loadPage("fund/fundTransferIn.html?path=fund");
            }
        },

        toFundDetail: function () {
            if($$('input[name="pv2_fund_join"]').val()=="true") {
                return;
            }
            req.callGet({
                url:'fund/selectIsInvested.do',
                data:{
                },
                dataType:'json',
                success:function(data){
                    if(data!=null && data.isInvested=='true') {
                        GS.loadPage("fund/fundInvested.html?path=fund");
                    }else {
                        GS.loadPage("fund/fundUnInvested.html?path=fund");
                    }
                }
            });
        },

        getFundInfo: function () {
            req.callGet({
                url: 'fund/selectFundInfo.do',
                data: {},
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data != null && data.fundApr != null) {
                        var apr = parseFloat(data.fundApr.apr).toFixed(2);
                        var millionsEarningsDay = planListV2Ctrl.calculationIncome(10000, apr, 1);
                        var values = new String(apr).split('.');
                        var html = '';
                        if(values.length == 1) {
                            html = '<span class="font24">' + values[0] + '</span>';
                        } else {
                            html = '<span class="font24">' + values[0] + '</span><span class="font13">.'+values[1]+'</span>';
                        }
                        $$("#pv2_fundApr").html(html);
                        $$("#pv2_perMillion").html(millionsEarningsDay);
                        var remAccount = data.fund.remAccount;
                        var leastAmount = data.fund.lowestTender;
                        $$("#pv2_leastAmount").html(leastAmount);
                        if(remAccount<leastAmount) {
                            //已满额;
                            var html="";
                            html += '<div class="game-over-rmlc">';
                            html += '<img src="static/img/xxd/buy-none1.png" width="68px" height="68px">';
                            html += '</div>';
                            $$("#pv2_fundOP").html(html);
                        }
                    }
                }
            });
        },
        getFundLimit: function () {
            req.callGet({
                url: 'fund/fundLimit.do',
                data: {
                },
                dataType: 'json',
                success: function (data) {
                    if (data.resultCode == 0) {
                        var isBalance = data.isBalance;
                        var isLastbalance = data.isLastBalance;
                        var purchaseSwitch = data.purchaseSwitch;
                        if (isBalance == "1" || isLastbalance == "1" || purchaseSwitch == "1") {
                            $$('#planv2_fundJoin').removeClass("qg-btn");
                            $$('#planv2_fundJoin').addClass("qg-btn-disable");
                        }else {
                            $$('#planv2_fundJoin').removeClass("qg-btn-disable");
                            $$('#planv2_fundJoin').addClass("qg-btn");
                        }
                    }

                }
            });
        },
        calculationIncome: function (money, apr, dayNum) {
            var resultMoney = 0;
            req.callGet({
                url:'fund/calculationIncome.do',
                data:{
                    money:money,
                    apr:apr,
                    dayNum:dayNum
                },
                dataType:'json',
                async:false,
                success:function(data){
                    if(data.resultCode == 0) {
                        resultMoney = data.resultMoney
                    }
                }
            });
            return resultMoney;
        }

    };
    return planListV2Ctrl;
});