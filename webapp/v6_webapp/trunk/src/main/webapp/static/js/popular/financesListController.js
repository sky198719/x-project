/**
 * _
 */
define(['js/popular/financesListView', 'js/plan/planUtils', 'js/utils/date', 'js/activity/womensDay', 'js/utils/dayController', 'js/common/productUtil'], function (financesListView, PlanUtils, DateHandle, womensDay, dayController, productUtil) {
    // 加载flag
    var loading = false;
    var financesPlanInterval;
    var monthFinanceInterval;
    var enableFlag = {monthFinance: 0, stepUpward: 0, xyb: 0, yyp: 0};	//0:开抢之前；1:开抢；2:抢购结束
    var clickType = {monthFinance: 0, stepUpward: 0, xyb: 0, yyp: 0};	//0:DIV；1:BUTTON
    // 当前时间
    var currentDateTime = new Date().getTime();
    var currentDateInterval;
    var toDayArr = [];
    var deployId = "";
    var terms = "";
    var fcode = "";
    var fundApr = "";
    var sevenDeployId = "";
    var sevenApr = "";
    var sevenCloseTerm = "";
    var stepUpwardInfo = {};
    var XSCP30TisMeetTheTermOfPurchase = false;
    var financesListCtrl = {
        init: function () {
            enableFlag = {monthFinance: 0, stepUpward: 0, xyb: 0, yyp: 0};
            clickType = {monthFinance: 0, stepUpward: 0, xyb: 0, yyp: 0};
            clearInterval(financesPlanInterval);
            clearInterval(monthFinanceInterval);
            toDayArr.length = 0;
            stepUpwardInfo = {};

            financesListCtrl.eventBind();

            financesListCtrl.pullToRefresh();

            womensDay.financesList();
        },
        eventBind: function () {
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
                    handler: financesListCtrl.pullToRefresh
                },
                {
                    element: '.finances_7dJoin',
                    event: 'click',
                    handler: financesListCtrl.to7dJoin
                },
                {
                    element: '.financesList #finances7d li',
                    event: 'click',
                    handler: financesListCtrl.to7dDetail
                },
                {
                    element: '.financesList #monthFinanceList li',
                    event: 'click',
                    handler: financesListCtrl.toMonthFinanceDetail
                },
                {
                    element: '.financesList #monthFinanceButton',
                    event: 'click',
                    handler: financesListCtrl.toMonthFinanceTender
                },
                {
                    element: '.financesList #stepUpward li',
                    event: 'click',
                    handler: financesListCtrl.toStepUpwardDetail
                },
                {
                    element: '.financesList #stepUpwardButton',
                    event: 'click',
                    handler: financesListCtrl.toStepUpwardTender
                }
            ];
            financesListView.bindEvents({
                    bindings: bindings
                }
            );
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            clearInterval(financesPlanInterval);
            clearInterval(monthFinanceInterval);

            financesListCtrl.get30DaysInfo();
            //_
            financesListCtrl.getMonthFinance();
            financesListCtrl.getStepUpward();

            financesListCtrl.selectPlanList("", 1, "pull");

            financesListCtrl.selectYypList("", 1, "pull");
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
            var totalPage = $$("#financesPlanTotalPage").val();
            var currentPage = $$("#financesPlanCurrentPage").val();
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
            financesListCtrl.selectPlanList("", currentPage, "push");
            $$("#financesPlanCurrentPage").val(currentPage);
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
        selectPlanList: function (closeTerm, currentPage, type) {
            req.callJSON({
                url: "xplan/schemeList.do",
                data: {
                    currentPage: currentPage,
                    closeTerm: closeTerm,
                    pageSize: 40
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    var list = "";
                    if (data.listData != null) {
                        list = data.listData;
                        //$$("#financesPlanTotalPage").val(data.pageNums);
                    }

                    if (list != null && list != "") {
                        var b;

                        //更新当前时间
                        currentDateTime = DateHandle.parseDate(data.currentDate).getTime();
                        currentDateInterval = window.setInterval(function () {
                            currentDateTime = new Number(currentDateTime) + 1000;
                        }, 1000);
                        var tempList = [];
                        for (var i = 0; i < list.length; i++) {
                            b = list[i];
                            // 过滤撤销的_
                            if (b.STATUS == 5) {
                                continue;
                            }

                            var OPENSALEBEGIN = DateHandle.parseDate(b.OPENSALEBEGIN);


                            var fixedImg = 'static/img/xxd/dq-gray.png';
                            var planPercent = 0;
                            if (b.REMACOUNT == b.ACCOUNT) {
                                planPercent = 0;
                            } else if (b.REMACOUNT == 0) {
                                planPercent = 100;
                            } else {
                                var investAccount = parseFloat(b.ACCOUNT) - parseFloat(b.REMACOUNT);
                                planPercent = Math.floor(parseFloat(investAccount) / parseFloat(b.ACCOUNT) * 100);
                            }
                            var buttonClass = 'popular-btn popular-btn-waiting';
                            var buttonName = '立即抢购';
                            var status = b.schemeStatus;  // 0:待发布,1:预定期,2:支付期,3:开放期,4:锁定期,5:退出,6:撤销,7:等待公开加入
                            var isJoin = false, isClose = false;
                            var startTime = '';
                            var showStatus = 1;
                            var isCountdown = false;

                            if (b.REMACOUNT == 0) {
                                //已抢完
                                isClose = true;
                                enableFlag.xyb = 2;
                                if (b.CLOSETERM == 12) {
                                    showStatus = 1;
                                } else if (b.CLOSETERM == 6) {
                                    showStatus = 2;
                                } else if (b.CLOSETERM == 3) {
                                    showStatus = 3;
                                } else if (b.CLOSETERM == 1) {
                                    showStatus = 4;
                                }
                            }
                            //0待发布,1已发布,2开放购买中,3.已锁定，4已结束，5已撤销
                            if (b.STATUS == 0 || b.STATUS == 1) {
                                fixedImg = 'static/img/xxd/dq-blue.png';
                                buttonName = '等待发售';
                                enableFlag.xyb = 0;
                                if (b.CLOSETERM == 12) {
                                    showStatus = 5;
                                } else if (b.CLOSETERM == 6) {
                                    showStatus = 6;
                                } else if (b.CLOSETERM == 3) {
                                    showStatus = 7;
                                } else if (b.CLOSETERM == 1) {
                                    showStatus = 8;
                                }
                            } else if (b.REMACOUNT > 0 && (b.STATUS == 2)) {
                                isJoin = true;
                                fixedImg = 'static/img/xxd/dq-blue.png';
                                buttonClass = ' popular-btn';

                                enableFlag.xyb = 1;
                                if (b.CLOSETERM == 12) {
                                    showStatus = 10;
                                } else if (b.CLOSETERM == 6) {
                                    showStatus = 11;
                                } else if (b.CLOSETERM == 3) {
                                    showStatus = 12;
                                } else if (b.CLOSETERM == 1) {
                                    showStatus = 13;
                                }
                            } else {
                                isClose = true;
                                enableFlag.xyb = 2;
                            }

                            var tempMinApr = new String(b.MINAPR).split('.');
                            var minApr = '<span class="font-red font28">' + tempMinApr[0] + '</span><span class="font-red font18">' + (tempMinApr.length > 1 ? "." + tempMinApr[1] : "") + '%</span>';

                            var increaseApr = b.WEBAPP;
                            var increaseAprText = '';
                            if (increaseApr != undefined && increaseApr != null && increaseApr > 0) {
                                increaseAprText = "<span class='font-red font18'>+" + increaseApr + '%</span>';
                            }

                            var activityLabel = '';
                            var is_activityLabel = false;
                            try {
                                var bidType = b.BIDTYPE;
                                if(2 == bidType) {
                                    req.callJSON({
                                        url: 'product/activityLabel.do',
                                        data: {
                                            productId:b.SCHEMEID
                                        },
                                        dataType: 'json',
                                        async:false,
                                        success:function(result1) {
                                            if(result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                                                activityLabel = result1.data.data.remark;
                                                if(activityLabel.length > 0) {
                                                    is_activityLabel = true;
                                                }
                                            }
                                        }
                                    });
                                }

                            }catch (e) {
                                console.log(e);
                            }


                            var planObj = {
                                'is_activityLabel':is_activityLabel,
                                'activityLabel':activityLabel,
                                'schemeid': b.SCHEMEID,
                                'pname': PlanUtils.schemeType(b.TYPE) + ' - ' + b.PNAME,
                                'account': appFunc.fmoney(b.ACCOUNT / 10000, 2),
                                'minapr': minApr,
                                'apr': b.MINAPR,
                                'closeterm': b.CLOSETERM,
                                'planPercent': planPercent + '%',
                                'isJoin': isJoin,
                                'buttonClass': buttonClass,
                                'buttonName': buttonName,
                                'isClose': isClose,
                                'fixedImg': fixedImg,
                                'presalebegin': b.PRESALEBEGIN,
                                'showStatus': showStatus,
                                'isCountdown': isCountdown,
                                'xybEnableFlag': enableFlag.xyb,
                                'increaseApr': increaseAprText
                            }

                            //只展现当日的
                            if (financesListCtrl.formatyyyyMMdd(OPENSALEBEGIN) != financesListCtrl.formatyyyyMMdd(new Date(new Number(currentDateTime)))) {
                                if (tempList.length < 10) {
                                    tempList.push(planObj);
                                }
                            } else {
                                financesListCtrl.addToDayArr(planObj);
                            }
                        }
                    }

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'popular/financesListItem.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                if (toDayArr.length == 0) {
                                    toDayArr = tempList;
                                }
                                if (toDayArr.length == 0) {
                                    return;
                                }

                                financesListView.showListItem({
                                    result: result,
                                    planList: toDayArr
                                });
                                financesListCtrl.planListBindEvent();
                                $$(".financesPlanList").show();
                                $$('.financesList .planList  button[name="等待发售"]').css("color", "#666");
                                //financesListCtrl.countdown();
                                // 加载完毕需要重置
                                xxdApp.pullToRefreshDone();
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }
                    //dayController.financesListMemberDay();
                }
            });

            setTimeout(function () {
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            }, 5000);
        },

        addToDayArr: function (plan) {
            var isThere = false;
            for (var i in toDayArr) {
                if (toDayArr[i].schemeid == plan.schemeid) {
                    isThere = true;
                }
            }

            if (!isThere) {
                toDayArr.push(plan);
            }

            financesListCtrl.sortToDayArr();
        },

        sortToDayArr: function () {
            toDayArr.sort(function (a, b) {
                return b.showStatus - a.showStatus;
            });
        },

        formathhmm: function (data) {
            var hours = data.getHours();
            var minutes = data.getMinutes();
            return (hours < 10 ? "0" + hours : hours) + ':' + (minutes < 10 ? "0" + minutes : minutes);
        },

        formathhmmss: function (data) {
            var hours = data.getHours();
            var minutes = data.getMinutes();
            var seconds = data.getSeconds();
            return (hours < 10 ? "0" + hours : hours) + ':' + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
        },

        formatyyyyMMdd: function (data) {
            var year = data.getFullYear();
            var month = data.getMonth() + 1;
            var day = data.getDate();
            return year + ':' + (month < 10 ? "0" + month : month) + ":" + (day < 10 ? "0" + day : day);
        },


        formatDate: function (date) {
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            m = m < 10 ? '0' + m : m;
            var d = date.getDate();
            d = d < 10 ? ('0' + d) : d;
            return y + ':' + m + ':' + d;
        },

    /**倒计时*/
        countdown: function () {
            var isCountdownList = $$("button[data-isCountdown='true']");

            isCountdownList.each(function () {
                var tempThis = $(this);
                var presalebegin = tempThis.attr("data-presalebegin");
                var presalebeginDate = DateHandle.parseDate(presalebegin);
                if (financesListCtrl.formathhmm(presalebeginDate) == '10:00') {
                    financesPlanInterval = window.setInterval(function () {
                        var date = new Date(new Number(currentDateTime));
                        var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
                        var timeStr = appFunc.getTime(currentDate, presalebegin, 'color:#fff;font-size:15px;', {
                            mm: true,
                            ss: true
                        });
                        if (timeStr == "0") {
                            tempThis.html("立即抢购");
                            tempThis.removeClass("popular-btn-waiting");
                            tempThis.addClass("popular-btn");
                        } else {
                            tempThis.html(timeStr);
                        }
                    }, 1000);
                }
            });
        },

        /**
         * 列表点击事件绑定
         */
        planListBindEvent: function () {
            var bindings = [
                {
                    element: '.financesList .planList  li',
                    event: 'click',
                    handler: financesListCtrl.toPlanDetail
                },
                {
                    element: '.financesList .planList  button[name="立即抢购"]',
                    event: 'click',
                    handler: financesListCtrl.toPlanTender
                }
            ];
            financesListView.bindEvents({
                    bindings: bindings
                }
            );
        },

        toPlanDetail: function () {
            var flag = $$(this).attr("data-xybEnableFlag");
            if (flag == 0) {
                return;
            }
            var planId = $(this).attr("data-id");
            try {
                var pname = $(this).attr("data-pname");
                var closeterm = $(this).attr("data-closeterm");
                var apr = $(this).attr("data-minapr");
                var category = "_/" + apr + "%/" + closeterm + "个月";
                product_click({id: planId, name: pname, category: category, list: "热门理财_"});
            } catch (e) {
            }
            GS.loadPage('plan/planDetailsV2.html?planId=' + planId + "&path=plan");
        },

        toPlanTender: function (even) {
        	//try {XXD_TRACK._trackEvent({category: "webapp_xplan_in", action: "hot_now_join", label: "立即抢购", value: "", custval: "" });} catch (e) {}
        	var flag = $$(this).attr("data-xybEnableFlag");
        	if(flag == 0){
        		return;
        	}
            var planId = $(this).attr("data-id");
            try {
                var pname = $(this).attr("data-pname");
                var closeterm = $(this).attr("data-closeterm");
                var apr = $(this).attr("data-minapr");
                var category = "_/" + apr + "%/" + closeterm + "个月";
                add_to_cart({id: planId, name: pname, category: category});
            } catch (e) {
            }
            GS.loadPage('planTender/planTender.html?planId=' + planId);
            //阻止事件冒泡
            event.stopPropagation();
        },

        to7dJoin: function (even) {
        	//try {XXD_TRACK._trackEvent({category: "webapp_sevengold_in", action: "hot_buy_once", label: "立即抢购", value: "", custval: "" });} catch (e) {}
            if (!$$('.finances_7dJoin').hasClass("popular-btn")) {
                return;
            }

            if (XSCP30TisMeetTheTermOfPurchase) {
                console.log("XSCP30TisMeetTheTermOfPurchase");
                GS.loadPage("newHand/thirtyDaysTrade.html?path=newHand");
            }
            even.stopPropagation();
        },

        to7dDetail: function () {
            GS.loadPage("newHand/thirtyDaysDetail.html?path=newHand");
        },

        getWebappProduct: function (param) {
            req.callGet({
                url: 'product/getWebappProduct.do',
                data: {
                    pCode: param.pCode
                },
                dataType: 'json',
                success: function (result) {
                    param.callBack(result);
                }
            });
        },
        get30DaysInfo: function () {
            financesListCtrl.getWebappProduct({
                pCode: "XSCP30T",
                callBack: function (result) {
                    if (result.code == 200000) {
                        XSCP30TisMeetTheTermOfPurchase = result.data.isMeetTheTermOfPurchase;
                        if (XSCP30TisMeetTheTermOfPurchase == false) {
                            $$(".financesList #finances7d").insertAfter($$(".financesList .yypList"));
                        }

                        if(result.data.items == undefined) {
                            $$("#finances7d").hide();
                            return false;
                        }
                        var pId = result.data.items.id;

                        var status = result.data.items.status;

                        if(status == 1) {
                            financesListCtrl.disable7dButton({value: '等待发售'});
                        } else if(status == 2) {
                            financesListCtrl.recovery7dButton({value:'立即抢购'});
                        } else if(status == 3) {
                            financesListCtrl.disable7dButton({value:'已售罄'});
                        } else {
                            financesListCtrl.disable7dButton({value:'已结束'});
                        }

                        req.callGet({
                            url: 'product/getProduct.do',
                            data: {
                                pCode: 'XSCP30T',
                                pId: pId
                            },
                            dataType: 'json',
                            success: function (result) {
                                if (result.code == 200000) {
                                    var data = result.data;
                                    var apr = parseFloat(data.plannedAnnualRate).toFixed(2);
                                    var values = new String(apr).split('.');
                                    var floatApr = data.floatingRate;
                                    var html = '';
                                    if (floatApr != null && floatApr != undefined) {
                                        html = '<span class="font28">' + values[0] + '</span><span class="font18">';
                                        if (values.length > 1 && parseInt(values[1]) > 0) {
                                            html += '.' + values[1];
                                        }

                                        sevenDeployId = data.productId;
                                        sevenApr = data.plannedAnnualRate;
                                        sevenCloseTerm = data.leastPeriod;
                                        html += '%+' + floatApr;
                                        $$(".newhandActivityFinances").show();
                                        $$(".newhandActivityFinances").addClass("animated fadeInLeft");
                                    } else {
                                        html = '<span class="font28">' + values[0] + '</span><span class="font18">';
                                        if (values.length > 1 && parseInt(values[1]) > 0) {
                                            html += '.' + values[1];
                                        }
                                    }
                                    html += '%</span>';
                                    $$("#finances_7dApr").html(html);
                                    var closeTerm = data.leastPeriod;
                                    $$("#finances_7d_closeTerm").html(closeTerm);
                                    $$(".pro30_mostInvestAmount").html(data.mostInvestAmount);



                                    /*var currentDate = new Date(Date.parse(DateHandle.formatDate('yyyy-MM-dd HH:mm:ss', new Date(result.serverTime)).replace(/-/g, "/")));
                                    var sd = new Date(data.activitedStartDate);
                                    var ed = new Date(data.activitedEndDate);
                                    if (currentDate > ed) {
                                        financesListCtrl.disable7dButton({value: '已结束'});
                                    }
                                    if (currentDate < sd) {
                                        financesListCtrl.disable7dButton({value: '等待发售'});
                                    }
                                    if (parseFloat(data.leftAmount) < parseFloat(data.leastInvestAmount)) {
                                        financesListCtrl.disable7dButton({value: '已售罄'});
                                    }*/

                                    $$("#finances7d").show();
                                }
                            }
                        });
                    }
                }
            });
        },

        calculationIncome: function (money, apr, dayNum) {
            var resultMoney = 0;
            req.callGet({
                url: 'fund/calculationIncome.do',
                data: {
                    money: money,
                    apr: apr,
                    dayNum: dayNum
                },
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data.resultCode == 0) {
                        resultMoney = data.resultMoney
                    }
                }
            });
            return resultMoney;
        },
        disable7dButton: function (options) {
            $$('.finances_7dJoin').removeClass("popular-btn");
            $$('.finances_7dJoin').addClass("popular-btn-disable");
            if (options.value != null)
                $$('.finances_7dJoin').html(options.value);
        },
        recovery7dButton: function (options) {
            $$('.finances_7dJoin').addClass("popular-btn");
            $$('.finances_7dJoin').removeClass("popular-btn-disable");
            if (options.value != null)
                $$('.finances_7dJoin').html(options.value);
        },
        getMonthFinance: function () {
            financesListCtrl.getWebappProduct({
                pCode: 'YJDJ',
                callBack: function (result) {
                    if (result.code == 200000 && result.data.items) {
                        var pId = result.data.items.id;
                        req.callJSON({
                            url: 'product/getProduct.do',
                            data: {
                                pCode: 'YJDJ',
                                pId: pId
                            },
                            dataType: 'json',
                            success: function (result) {
                                try {
                                    if (result.code == 200000) {
                                        var resultData = result.data;
                                        var currentClientTime = new Date().getTime();
                                        var serverTime = result.serverTime.replace(/\-/g, "/");
                                        var currentServerTime = new Date(serverTime).getTime();
                                        // 服务器与本地时间差
                                        var clientServerTimeDiff = currentServerTime - currentClientTime;
                                        //产品名称
                                        $$("#monthFinanceList #itemName").html(resultData.name);
                                        //产品ID
                                        deployId = resultData.productId;
                                        //产品期数
                                        terms = resultData.periodName;
                                        //年化利率
                                        $$("#monthFinanceList #monthFinanceApr").html(resultData.plannedAnnualRate);
                                        //期限
                                        $$("#monthFinanceList #monthFinanceLockDays").html(resultData.leastPeriod);
                                        //抢购时间
                                        var startTime = new Date(resultData.activitedStartDate);
                                        var endTime = new Date(resultData.activitedEndDate);
                                        $$("#monthFinanceList #startTime").html(DateHandle.formatDate('HH:mm', startTime));
                                        $$("#monthFinanceList #endTime").html(DateHandle.formatDate('HH:mm', endTime));
                                        //已到开始时间 未到结束时间
                                        if (currentServerTime >= new Date(startTime).getTime()
                                            && currentServerTime <= new Date(endTime).getTime()) {
                                            //是否已满额
                                            if (parseFloat(resultData.leftAmount) == 0 || parseFloat(resultData.leftAmount) < parseFloat(resultData.leastInvestAmount)) {
                                                //进度条宽度
                                                $$("#monthFinanceList #monthFinanceProgressBar").css("width", "100%");
                                                //显示百分数
                                                $$("#monthFinanceList #monthFinancePercent").html("100%");
                                                //无倒计时，显示抢光提示
                                                $$("#monthFinanceList #monthFinanceCountDown").html("本场已结束，等待下一场");
                                                $$("#monthFinanceList li").css("margin-top", "31px");
                                                $$("#monthFinanceList li").css("overflow", "visible");
                                                $$("#monthFinanceList #monthFinanceCountDownDiv").css("display", "block");
                                                //进度条隐藏
                                                $$("#monthFinanceList #progressBarDiv").css("display", "none");
                                                //显示已抢完
                                                $$("#monthFinanceList #monthFinanceButtonDiv").html('<img src="static/img/xxd/buy-none.png" style="width:50px;height:50px;" class="game-over-rmlc">');
                                                $$("#monthFinanceList #monthFinanceInfoDiv").removeClass("mt5").addClass("mt10");
                                                enableFlag.monthFinance = 2;
                                            } else {
                                                enableFlag.monthFinance = 1;

                                                var monthFinanceProgressBar = ((resultData.plannedAmount - resultData.leftAmount) / resultData.plannedAmount) * 100;
                                                //进度条宽度
                                                $$("#monthFinanceList #monthFinanceProgressBar").css("width", monthFinanceProgressBar + "%");
                                                //显示百分数
                                                $$("#monthFinanceList #monthFinancePercent").html(Math.round(monthFinanceProgressBar) + "%");
                                                //按钮
                                                $$("#monthFinanceList #monthFinanceButton").html("立即抢购").removeClass("disable");
                                                //倒计时
                                                var endTime = resultData.activitedEndDate;
                                                monthFinanceInterval = window.setInterval(function () {
                                                    var date = new Date(new Date().getTime() + clientServerTimeDiff);
                                                    var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
                                                    var timeStr = appFunc.getCountDownTime(currentDate, DateHandle.formatDate('yyyy-MM-dd HH:mm:ss', new Date(endTime)), {
                                                        HH: true,
                                                        mm: true,
                                                        ss: true
                                                    });
                                                    $$("#monthFinanceList #monthFinanceCountDown").html("距离抢购结束：" + timeStr[0] + ":" + timeStr[1] + ":" + timeStr[2]);
                                                    //倒计时结束
                                                    if (timeStr[0] == "00" && timeStr[1] == "00" && timeStr[2] == "00") {
                                                        $$("#monthFinanceList #monthFinanceCountDown").html("本场已结束，等待下一场");
                                                        //进度条隐藏
                                                        $$("#monthFinanceList #progressBarDiv").css("display", "none");
                                                        //显示已抢完
                                                        $$("#monthFinanceList #monthFinanceButtonDiv").html('<img src="static/img/xxd/buy-none.png" style="width:50px;height:50px;" class="game-over-rmlc">');
                                                        $$("#monthFinanceList #monthFinanceInfoDiv").removeClass("mt5").addClass("mt10");
                                                        enableFlag.monthFinance = 2;
                                                    }
                                                }, 1000);

                                                $$("#monthFinanceList li").css("margin-top", "31px");
                                                $$("#monthFinanceList li").css("overflow", "visible");
                                                $$("#monthFinanceList #monthFinanceCountDownDiv").css("display", "block");
                                            }
                                        } else {
                                            //已过结束时间
                                            if (currentServerTime > endTime.getTime()) {
                                                //进度条宽度
                                                $$("#monthFinanceList #monthFinanceProgressBar").css("width", "100%");
                                                //显示百分数
                                                $$("#monthFinanceList #monthFinancePercent").html("100%");
                                                //无倒计时，显示抢光提示
                                                $$("#monthFinanceList #monthFinanceCountDown").html("本场已结束，等待下一场");
                                                $$("#monthFinanceList li").css("margin-top", "31px");
                                                $$("#monthFinanceList li").css("overflow", "visible");
                                                $$("#monthFinanceList #monthFinanceCountDownDiv").css("display", "block");
                                                //进度条隐藏
                                                $$("#monthFinanceList #progressBarDiv").css("display", "none");
                                                //显示已抢完
                                                $$("#monthFinanceList #monthFinanceButtonDiv").html('<img src="static/img/xxd/buy-none.png" style="width:50px;height:50px;" class="game-over-rmlc">');
                                                $$("#monthFinanceList #monthFinanceInfoDiv").removeClass("mt5").addClass("mt10");
                                                enableFlag.monthFinance = 2;
                                            }
                                            //未到开始时间
                                            else {
                                                //无倒计时，显示抢购尚未开始提示
                                                $$("#monthFinanceList #monthFinanceCountDown").html("抢购还未开始，请耐心等待");
                                                $$("#monthFinanceList li").css("margin-top", "31px");
                                                $$("#monthFinanceList li").css("overflow", "visible");
                                                $$("#monthFinanceList #monthFinanceCountDownDiv").css("display", "block");
                                                //按钮（灰色可点击）
                                                $$("#monthFinanceList #monthFinanceButton").html("等待发售").addClass("disable");
                                                enableFlag.monthFinance = 0;
                                            }
                                        }
                                    } else {
                                        xxdApp.addNotification({
                                            title: '温馨提示',
                                            hold: 3000,
                                            message: '获取_产品信息失败，请稍后重试...'
                                        });
                                    }
                                } catch (e) {
                                    console.log(e.message);
                                    xxdApp.addNotification({
                                        title: '抱歉',
                                        hold: 3000,
                                        message: '获取_产品信息失败，请稍后重试...'
                                    });
                                }
                            },
                            error: function (xhr, type) {
                                xxdApp.addNotification({
                                    title: '抱歉',
                                    hold: 3000,
                                    message: '获取_产品信息失败，请稍后重试...'
                                });
                            }
                        });

                        $$("#monthFinanceList").show();
                    }
                }
            });
        },
        //_详情页
        toMonthFinanceDetail: function () {
            if (clickType.monthFinance == 1) {
                clickType.monthFinance = 0;
                return;
            }

            try {
                var monthFinanceButton = $$("#monthFinanceList #monthFinanceButton").html();
                if (monthFinanceButton == '立即抢购') {
                    //加入购物车
                    var bid = deployId;
                    var name = "_-" + terms;
                    var apr = $$("#monthFinanceList #monthFinanceApr").html();
                    var closeTerm = $$("#monthFinanceList #monthFinanceLockDays").html();
                    var category = "_/" + apr + "%/" + closeTerm + "天";
                    product_click({id: bid, name: name, category: category, list: "热门理财大显身手"});
                }
            } catch (e) {
            }

            GS.loadPage('monthFinance/monthFinanceDetails.html');
        },
        //_购买页
        toMonthFinanceTender: function () {
        	//try {XXD_TRACK._trackEvent({category: "webapp_monthgold_in", action: "hot_buy_once", label: "立即抢购", value: "", custval: "" });} catch (e) {}
        	clickType.monthFinance = 1;
        	if(enableFlag.monthFinance == 0 || enableFlag.monthFinance == 2){
        		clickType.monthFinance = 0;
        		financesListCtrl.toMonthFinanceDetail();
        	}else if(enableFlag.monthFinance == 1){
        		try{
        			var monthFinanceButton =  $$("#monthFinanceList #monthFinanceButton").html();
        			if(monthFinanceButton =='立即抢购'){
	        			//加入购物车
	        			var bid = deployId;
	        			var name = "_-"+terms;
	        			var apr = $$("#monthFinanceList #monthFinanceApr").html(); 	
	        			var closeTerm = $$("#monthFinanceList #monthFinanceLockDays").html();
	        			var category = "_/"+apr+"%/"+closeTerm+"天";
	        			add_to_cart({id:bid,name:name,category:category});
        			}
        		}catch(e){}
        		
        		GS.loadPage('monthFinance/monthFinanceTender.html');
        	}
        },
        getStepUpward: function () {
            if (productUtil.stepUpwardShowOrNot()) {
                $("#stepUpward").css("margin-top","10px");
                $$("#stepUpward").show();

                financesListCtrl.getWebappProduct({
                    pCode:'BBGS',
                    callBack:function(result) {
                        if(result.code == 200000) {
                            var pId = result.data.items.stepid;
                            req.callJSON({
                                url: 'product/getProduct.do',
                                data: {
                                    pCode: 'BBGS',
                                    pId: pId
                                },
                                success: function (result) {
                                    try {
                                        if (result.code == 200000) {
                                            var resultData = result.data;
                                            stepUpwardInfo = resultData;
                                            var tenderStartAmount = resultData.leastInvestAmount;
                                            $$("#stepUpward .tenderStartAmount").html(tenderStartAmount);
                                            //年化利率
                                            var floatapr = resultData.floatingRate;
                                            var minApr = parseFloat(resultData.plannedAnnualRateFrom);
                                            var maxApr = parseFloat(resultData.plannedAnnualRateTo);
                                            if(floatapr != undefined) {
                                                minApr += parseFloat(floatapr);
                                                maxApr += parseFloat(floatapr);
                                            }

                                            var aprLength = minApr.toString().length + maxApr.toString().length;
                                            var screenWidth = window.screen.width;
                                            if (aprLength > 6) {
                                                $$("#stepUpward .suApr").css("font-size", "14px");
                                                $$("#stepUpward .aprPercent").css("font-size", "13px");
                                                if (screenWidth <= 360) {
                                                    $$("#stepUpward .suApr").css("font-size", "12px");
                                                    $$("#stepUpward .aprPercent").css("font-size", "12px");
                                                }
                                            } else if (aprLength > 4) {
                                                $$("#stepUpward .suApr").css("font-size", "17px");
                                                $$("#stepUpward .aprPercent").css("font-size", "15px");
                                                if (screenWidth <= 360) {
                                                    $$("#stepUpward .suApr").css("font-size", "14px");
                                                    $$("#stepUpward .aprPercent").css("font-size", "13px");
                                                }
                                            } else {
                                                if (screenWidth <= 360) {
                                                    $$("#stepUpward .suApr").css("font-size", "16px");
                                                    $$("#stepUpward .aprPercent").css("font-size", "13px");
                                                }
                                            }
                                            $$("#stepUpward .minApr").html(minApr);
                                            $$("#stepUpward .maxApr").html(maxApr);
                                            //是否已满额
                                            if (parseFloat(resultData.leftAmount) == 0 || parseFloat(resultData.leftAmount) < parseFloat(tenderStartAmount)) {
                                                //显示已抢完
                                                $$("#stepUpward #stepUpwardButtonDiv").html('<img src="static/img/xxd/buy-none.png" style="width:50px;height:50px;" class="game-over-rmlc">');
                                                enableFlag.stepUpward = 2;
                                            } else {
                                                enableFlag.stepUpward = 1;
                                                //按钮
                                                $$("#stepUpward #stepUpwardButton").html("立即抢购").removeClass("disable");
                                            }


                                            try {
                                                var bidType = resultData.activitedType;
                                                if (2 == bidType) {
                                                    req.callJSON({
                                                        url: 'product/activityLabel.do',
                                                        data: {
                                                            productId: resultData.productId
                                                        },
                                                        dataType: 'json',
                                                        async: false,
                                                        success: function (result1) {
                                                            if (result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                                                                var remark = result1.data.data.remark;
                                                                $(".activityLabel_list_step").css("margin-top","10px");
                                                                $("#stepUpward").css("margin-top","0px");
                                                                $$(".activityLabel_list_step").show();
                                                                $$(".activityLabel_list_step_text").html(remark)
                                                            }
                                                        }
                                                    });
                                                }
                                            }catch (e) {
                                                console.log(e);
                                            }

                                        } else {
                                            xxdApp.addNotification({
                                                title: '温馨提示',
                                                hold: 3000,
                                                message: '获取步步高升产品信息失败，请稍后重试...'
                                            });
                                        }
                                    } catch (e) {
                                        console.log(e.message);
                                        xxdApp.addNotification({
                                            title: '抱歉',
                                            hold: 3000,
                                            message: '获取步步高升产品信息失败，请稍后重试...'
                                        });
                                    }
                                },
                                error: function (xhr, type) {
                                    xxdApp.addNotification({
                                        title: '抱歉',
                                        hold: 3000,
                                        message: '获取步步高升产品信息失败，请稍后重试...'
                                    });
                                }
                            });
                        }
                    }
                });

            }
        },
        //步步高升详情页
        toStepUpwardDetail: function () {
            if (clickType.stepUpward == 1) {
                clickType.stepUpward = 0;
                return;
            }
            GS.loadPage('stepUpward/stepUpwardDetail.html');
        },
        //步步高升购买页
        toStepUpwardTender: function () {
            clickType.stepUpward = 1;
            if (enableFlag.stepUpward == 0 || enableFlag.stepUpward == 2) {
                clickType.stepUpward = 0;
                financesListCtrl.toStepUpwardDetail();
            } else if (enableFlag.stepUpward == 1) {
                var userStepAccount = productUtil.getUserStepUpwardInfo();
                if (parseFloat(userStepAccount.remaCapitalTotal) >= parseFloat(stepUpwardInfo.userMostTender)) {
                    xxdApp.alert('您的个人可购买额度已满额', '提示');
                    return;
                } else {
                    GS.loadPage('stepUpward/stepUpwardTender.html');
                }
            }
        },
        /**
         * _列表
         * @param closeTerm  锁定期
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectYypList: function (closeTerm, currentPage, type) {
            req.callJSON({
                url: "product/getWebappProduct.do",
                data: {
                    currentPage: currentPage,
                    pageSize: 40,
                    pCode:"YYP"
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {

                    // if (result.code != 200000) {
                    //     xxdApp.addNotification({
                    //         title: '抱歉',
                    //         hold: 3000,
                    //         message: '获取_产品列表失败，请稍后重试...'
                    //     });
                    //     return;
                    // }
                    var list = "";
                    if (result.data.items != null) {
                        list = result.data.items;
                    }

                    if (list != null && list != "") {
                        var b;
                        var showList = [];
                        for (var i = 0; i < list.length; i++) {
                            b = list[i];
                            var aprHtml = '<span class="font-red font28">' + b.apr + '</span><span class="font-red font18">%</span>';
                            var status = b.status;
                            var openTime=new Date(b.openTime);
                            var closeTime=new Date(b.closeTime);
                            var startDate=financesListCtrl.formatDate(openTime);
                            var closeDate=financesListCtrl.formatDate(closeTime);
                            var flog1;
                            //处于撤销，结束都不展现
                            if (status == 5 || status == 4) {
                                continue;
                            }

                            var showStatus = 1;
                            var buttonName;
                            var buttonClass = 'popular-btn popular-btn-waiting';
                            var remAccount = b.remAccount;

                            if (status == 1) {
                                showStatus = 5;
                                if (b.terms == 3) {
                                    showStatus = 6;
                                } else if (b.terms == 6) {
                                    showStatus = 7;
                                } else if (b.terms == 12) {
                                    showStatus = 8;
                                }
                                buttonName = '等待发售';
                            } else if (status == 2) {
                                buttonName = '立即抢购';
                                buttonClass = 'popular-btn';
                                if (b.terms == 3) {
                                    showStatus = 2;
                                } else if (b.terms == 6) {
                                    showStatus = 3;
                                } else if (b.terms == 12) {
                                    showStatus = 4;
                                }
                                // if (remAccount == 0) {
                                //     showStatus = 9;
                                //     if (b.terms == 3) {
                                //         showStatus = 10;
                                //     } else if (b.terms == 6) {
                                //         showStatus = 11;
                                //     } else if (b.terms == 12) {
                                //         showStatus = 12;
                                //     }
                                //     buttonName = '已抢光';
                                // }
                            }else if(status==3){
                                // var startTime=new Date(b.openTime);
                                // var closeTime=new Date(b.closeTime);
                                //var startDate=financesListCtrl.formatDate(openTime);
                                //var closeDate=financesListCtrl.formatDate(closeTime);
                                flog1 = (startDate <= financesListCtrl.formatyyyyMMdd(DateHandle.parseDate(result.serverTime))&&startDate==closeDate&&closeDate>=financesListCtrl.formatyyyyMMdd(DateHandle.parseDate(result.serverTime)));
                                if (flog1) {
                                    showStatus = 9;
                                        if (b.terms == 3) {
                                            showStatus = 10;
                                        } else if (b.terms == 6) {
                                            showStatus = 11;
                                        } else if (b.terms == 12) {
                                            showStatus = 12;
                                        }
                                        buttonName = '已抢光';
                                }else{
                                    continue;
                                }
                            }


                            var isClose = false;
                            if (remAccount == 0) {
                                isClose = true;
                            }

                            var activityLabel = '';
                            var is_activityLabel = false;
                            try {
                                var bidType = b.bidType;
                                if(2 == bidType) {
                                    req.callJSON({
                                        url: 'product/activityLabel.do',
                                        data: {
                                            productId:b.id
                                        },
                                        dataType: 'json',
                                        async:false,
                                        success:function(result1) {
                                            if(result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                                                activityLabel = result1.data.data.remark;
                                                if(activityLabel.length > 0) {
                                                    is_activityLabel = true;
                                                }
                                            }
                                        }
                                    });
                                }

                            }catch (e) {
                                console.log(e);
                            }

                            var planObj = {
                                'is_activityLabel':is_activityLabel,
                                'activityLabel':activityLabel,
                                'yypId': b.id,
                                'name': b.name,
                                'apr': aprHtml,
                                'terms': b.terms,
                                'account': appFunc.fmoney(b.account / 10000, 2),
                                'fixedImg': 'static/img/xxd/dq-blue.png',
                                'buttonName': buttonName,
                                'buttonClass': buttonClass,
                                'isClose': isClose,
                                'status': status,
                                'showStatus': showStatus
                            };
                            // var openTime=new Date(b.openTime);
                            var openDate=financesListCtrl.formatDate(openTime);
                            var flog = (openDate <= financesListCtrl.formatyyyyMMdd(DateHandle.parseDate(result.serverTime)));
                            if (flog) {
                                showList.push(planObj);
                            }
                        }

                        if (showList.length == 0) {
                            return;
                        }

                        showList.sort(function (a, b) {
                            return a.showStatus - b.showStatus;
                        });
                        try {
                            req.callGet({
                                url: GC.getHtmlPath() + 'popular/yypListItem.html?' + GC.getVersion(),
                                dataType: 'text',
                                success: function (result) {
                                    financesListView.showYypList({
                                        result: result,
                                        yypList: showList
                                    });

                                    var bindings = [
                                        {
                                            element: '.financesList .yypList  li',
                                            event: 'click',
                                            handler: financesListCtrl.toYypDetail
                                        },
                                        {
                                            element: '.financesList .yypList  button',
                                            event: 'click',
                                            handler: financesListCtrl.toTenderYYP
                                        }
                                    ];

                                    appFunc.bindEvents(bindings);

                                    $$(".financesYypList").show();
                                }
                            });
                        } catch (e) {
                            xxdApp.hideIndicator();
                        }
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
        toTenderYYP: function () {
            var yypId = $$(this).data("id");
            var status = $$(this).data("status");
            if (status == 2) {
                GS.loadPage("yyp/yypTender.html?yypId=" + yypId);
            }
            //阻止事件冒泡
            event.stopPropagation();
        },
        toYypDetail: function () {
            var yypId = $(this).attr("data-id");
            GS.loadPage("yyp/yypDetails.html?yypId=" + yypId);
        }
    };
    return financesListCtrl;
});