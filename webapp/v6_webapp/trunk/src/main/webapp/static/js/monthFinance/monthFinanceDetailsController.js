define(['chart', 'chartDoughnut','js/utils/date','js/monthFinance/monthFinanceDetailsView','js/common/ami'], function (Chart, Doughnut, DateHandle,monthFinanceDetailsView,ami) {
	var financeInterval;
	var monthFinanceId = "";
	var introductionParams = {};
	var pageSize = 10;
	var gname = "";
	var categorys = "";
    // 加载flag
    var loading = false;
    var pId;
    var monthFinanceDetailsCtrl = {
        init: function (event) {
            clearInterval(financeInterval);
            monthFinanceId = "";
            introductionParams = {};
            //查询产品信息
            //monthFinanceDetailsCtrl.getProductInfo();

            //获取产品介绍
            //monthFinanceDetailsCtrl.getIntroduction();

            monthFinanceDetailsCtrl.bindingEvents();

            req.callGet({
                url: 'product/getWebappProduct.do',
                data: {
                    pCode:"YJDJ"
                },
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code == 200000) {
                        pId = result.data.items.id;
                    }
                }
            });
            //查询产品信息
            monthFinanceDetailsCtrl.pullToRefresh();
            //设置环形进度条
            //monthFinanceDetailsView.setAnnularProgress();
        },

        bindingEvents: function () {
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: monthFinanceDetailsCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: monthFinanceDetailsCtrl.infiniteScroll
                },
                {
                    element: '.monthFinanceDetails #introduction',
                    event: 'click',
                    handler: monthFinanceDetailsCtrl.getIntroduction
                },
                {
                    element: '.monthFinanceDetails #questions',
                    event: 'click',
                    handler: monthFinanceDetailsCtrl.getQuestions
                },
                {
                    element: '.monthFinanceDetails #joinHistory',
                    event: 'click',
                    handler: monthFinanceDetailsCtrl.getJoinHistory
                },
                {
                    element: '.monthFinanceDetails #fenxiantishi',
                    event: 'click',
                    handler: ami.fenxiantishi
                },
                {
	                 element: '#submitTender',
	                 event: 'click',
	                 handler: monthFinanceDetailsCtrl.submitTender
                },
                {
                    element: '#tradeList',
                    event: 'click',
                    handler: monthFinanceDetailsCtrl.tradeList
                }
            ];
            appFunc.bindEvents(bindings);
        },

        tradeList:function(){
            GS.loadPage('trade/tradeList.html?pId='+pId);
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            introductionParams = {};
            clearInterval(financeInterval);
            monthFinanceDetailsCtrl.getNewProductInfo();
            $$(".monthFinanceDetails #currentPage").val(1);
            loading = false;
        },

        /**
         * 无限滚动
         */
        infiniteScroll: function () {

            // 如果当前tab不是投资记录
            if (!$$(".monthFinanceDetails #joinHistory").hasClass("active")) {
                return;
            }

            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            //==========================切记此处不能删除============================
            loading = true;
            //==========================切记此处不能删除============================
            var currentTab = $('.monthFinanceDetails #currentTab').val();
            var totalPage, currentPage;
            totalPage = $$(".monthFinanceDetails #totalPage").val();
            currentPage = $$(".monthFinanceDetails #currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage > totalPage) {
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '数据已经全部加载完毕...'
                });
                loading = true;
                return;
            }
            $$(".monthFinanceDetails #currentPage").val(currentPage);

            monthFinanceDetailsCtrl.joinHistory(currentPage, pageSize, "push")

            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        //产品信息
        getNewProductInfo: function () {
            req.callJSON({
                url: 'product/getProduct.do',
                data: {
                    pCode: 'YJDJ',
                    pId: pId
                },
                indicator: true,
                success: function (result) {
                    if (result.code == 200000) {
                        var resultData = result.data;
                        var currentClientTime = new Date().getTime();
                        var serverTime = result.serverTime.replace(/\-/g, "/");
                        var currentServerTime = new Date(serverTime).getTime();
                        var clientServerTimeDiff = currentServerTime - currentClientTime;

                        //产品ID
                        monthFinanceId = resultData.productId;
                        gname = "_-" + resultData.periodName;
                        categorys = "_/" + resultData.plannedAnnualRate + "%/" + resultData.leastPeriod + "天";
                        try {
                            product_detail({id: monthFinanceId, name: gname, category: categorys});
                        } catch (e) {
                        }
                        //产品名称 和 期号
                        $('#itemName').html(resultData.name);
                        $('#itemTerm').html(resultData.periodName);

                        //年化利率
                        introductionParams.apr = resultData.plannedAnnualRate;
                        $$(".monthFinanceDetails #tenderApr").html(resultData.plannedAnnualRate);
                        //起投金额
                        var tenderStartAmount = resultData.leastInvestAmount;
                        introductionParams.tenderStartAmount = appFunc.fmoney(tenderStartAmount, 0);
                        $$(".monthFinanceDetails #tenderStartAmount").html(appFunc.fmoney(tenderStartAmount, 0));
                        //锁定期限
                        introductionParams.closeTerm = resultData.leastPeriod;
                        $$(".monthFinanceDetails #lockDays").html(resultData.leastPeriod);
                        //投资期限
                        introductionParams.days = resultData.leastPeriod;
                        //剩余可投
                        $$(".monthFinanceDetails #tenderRemainderAmount").html(appFunc.fmoney(resultData.leftAmount, 2));
                        //开放总额
                        $$(".monthFinanceDetails #tenderTotalAmount").html(appFunc.fmoney(resultData.plannedAmount, 2));
                        //增加基数
                        introductionParams.tenderIncreaseRadix = resultData.step;
                        //最大可投
                        introductionParams.tenderMost = resultData.mostTender;
                        //本人剩余可投
                        var maxTenderAmount = resultData.investableAmount;

                        var startTime = new Date(resultData.activitedStartDate);
                        var endTime = new Date(resultData.activitedEndDate);
                        var startTimeHHmm = DateHandle.formatDate('HH:mm', startTime);
                        var endTimeHHmm = DateHandle.formatDate('HH:mm', endTime);
                        var tenderButton = $$("#submitTender");
                        //已到开始时间 未到结束时间

                        if (currentServerTime >= new Date(startTime).getTime()
                            && currentServerTime <= new Date(endTime).getTime()) {
                            //是否已满额
                            if (parseFloat(resultData.leftAmount) == 0 || parseFloat(resultData.leftAmount) < parseFloat(tenderStartAmount)) {
                                //进度条
                                $(".monthFinanceDetails .progress-ring").data("percent", "100");
                                tenderButton.addClass("disable").html("本场已结束，等待下一场");
                                $$(".monthFinanceDetails #countDownTime").css("display", "none");
                            } else {
                                var monthFinanceProgressBar = ((resultData.plannedAmount - resultData.leftAmount) / resultData.plannedAmount) * 100;

                                //进度条
                                $(".monthFinanceDetails .progress-ring").data("percent", Math.round(monthFinanceProgressBar));

                                //个人额度是否已满
                                if (maxTenderAmount <= 0) {
                                    tenderButton.addClass("disable").html("个人限额已满");
                                } else {
                                    //投标按钮
                                    tenderButton.removeClass("disable").html("立即抢购");
                                }
                                //倒计时
                                financeInterval = window.setInterval(function () {
                                    var date = new Date(new Date().getTime() + clientServerTimeDiff);
                                    var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
                                    var timeStr = appFunc.getCountDownTime(currentDate, DateHandle.formatDate('yyyy-MM-dd HH:mm:ss', new Date(endTime)), {
                                        HH: true,
                                        mm: true,
                                        ss: true
                                    });
                                    $$(".countdown .hour").html(timeStr[0]);
                                    $$(".countdown .minute").html(timeStr[1]);
                                    $$(".countdown .second").html(timeStr[2]);
                                    if (timeStr[0] == "00" && timeStr[1] == "00" && timeStr[2] == "00") {
                                        tenderButton.addClass("disable").html("已抢光");
                                        $$(".monthFinanceDetails #countDownTime").css("display", "none");
                                    }
                                }, 1000);
                            }
                        } else {
                            //已过结束时间
                            if (currentServerTime > new Date(endTime).getTime()) {
                                //进度条
                                $(".monthFinanceDetails .progress-ring").data("percent", "100");

                                tenderButton.addClass("disable").html("本场已结束，等待下一场");
                                $$(".monthFinanceDetails #countDownTime").css("display", "none");
                            }
                            //未到开始时间
                            else {
                                tenderButton.addClass("disable").html("每日" + startTimeHHmm + "-" + endTimeHHmm + "开抢");
                                $$(".monthFinanceDetails #countDownTime").css("display", "none");
                            }
                        }

                    } else {
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: '获取_产品信息失败，请稍后重试...'
                        });
                    }
                    //加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                    //获取产品介绍
                    monthFinanceDetailsCtrl.getIntroduction();

                    //设置环形进度条
                    monthFinanceDetailsView.setAnnularProgress();
                },
                error: function (xhr, type) {
                    console.log("获取_产品加入记录失败,ajax error...");
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '获取_产品信息失败，请稍后重试...'
                    });
                }
            });
        },


        //投资
        submitTender:function(){
        	try {
        		//XXD_TRACK._trackEvent({category: "webapp_monthgold_in", action: "menu_buy_once", label: "立即抢购", value: "", custval: "" });
        		add_to_cart({id:monthFinanceId,name:gname,category:categorys});
        	} catch (e) {}
            if($(this).hasClass("disable")){
            	//xxdApp.alert('该产品暂不可购买', '抱歉');
            	return;
            }
            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }

            if (monthFinanceId != null && monthFinanceId != "") {
                GS.loadPage('monthFinance/monthFinanceTender.html');
            } else {
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '系统异常，请刷新页面重试...'
                });
            }
        },

        //获取产品说明
        getIntroduction: function () {
            monthFinanceDetailsCtrl.setTabSelectedStyle({"selected": "introduction"});
            monthFinanceDetailsView.showIntroduction(introductionParams);
        },

        //获取常见问题
        getQuestions: function () {
            monthFinanceDetailsCtrl.setTabSelectedStyle({"selected": "questions"});
            monthFinanceDetailsView.showQuestions();
        },

        //获取投资记录
        getJoinHistory: function () {
           //monthFinanceDetailsCtrl.joinHistory(1, pageSize, "pull");
            monthFinanceDetailsCtrl.newJoinHistory(1, pageSize, "pull");
        },

        newJoinHistory:function(currentPage, pageSize, type){
            monthFinanceDetailsCtrl.setTabSelectedStyle({"selected": "joinHistory"});
            //产品ID是否为空
            if (monthFinanceId == null || monthFinanceId == "") {
                console.log("_产品ID为空");
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '获取_产品加入记录失败，请稍后重试...'
                });
                return;
            }

            req.callJSON({
                url: 'product/productJoinRecords.do',
                data: {
                    pId: monthFinanceId,
                    pCode:'YJDJ',
                    currentPage: currentPage,
                    pageSize: pageSize
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    try {
                        if (result.code == 200000) {
                            var resultData = result.data;
                            var totalTender = resultData.countJoinTime;
                            var totalPage = Math.floor(totalTender / pageSize);
                            totalPage += (totalTender % pageSize) > 0 ? 1 :0;
                            $$(".monthFinanceDetails #totalPage").val(totalPage);
                            var dataList = resultData.items;
                            var data = [];
                            for (var i = 0; i < dataList.length; i++) {
                                var listItem = dataList[i];
                                data.push({
                                    userName: listItem.nickName,
                                    amount: appFunc.fmoney(listItem.joinAmount, 2),
                                    addTime: DateHandle.formatDate("yyyy-MM-dd HH:mm:ss",new Date(listItem.joinDate))
                                });

                            }
                            monthFinanceDetailsView.showJoinHistory({"dataList": data, "type": type});
                        } else {
                            console.log("获取_产品加入记录失败");
                            $$(".monthFinanceDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                            xxdApp.addNotification({
                                title: '温馨提示',
                                hold: 3000,
                                message: '获取_产品加入记录失败，请稍后重试...'
                            });
                        }
                    } catch (e) {
                        console.log(e.message);
                        xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取_产品加入记录失败，请稍后重试...'
                        });
                    }
                },
                error: function (result) {
                    console.log("获取_产品加入记录失败,ajax error...");
                    $$(".monthFinanceDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                    xxdApp.addNotification({
                        title: '温馨提示',
                        hold: 3000,
                        message: '获取_产品加入记录失败，请稍后重试...'
                    });
                }
            });
        },

        //获取投资记录
        joinHistory: function (currentPage, pageSize, type) {
            monthFinanceDetailsCtrl.setTabSelectedStyle({"selected": "joinHistory"});

            //产品ID是否为空
            if (monthFinanceId == null || monthFinanceId == "") {
                console.log("_产品ID为空");
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '获取_产品加入记录失败，请稍后重试...'
                });
                return;
            }

            //是否已登录
//        	if (!appFunc.isLogin()) {
//                xxdApp.loginScreen();
//                return;
//            }

            req.callJSON({
                url: 'monthFinance/monthFinanceJoinHistory.do',
                data: {"monthFinanceId": monthFinanceId, "currentPage": currentPage, "pageSize": pageSize},
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    try {
                        if (result.resultCode == '0' || result.resultCode == 0) {
                            var resultData = result.data;
                            $$(".monthFinanceDetails #totalPage").val(resultData.totalPages);
                            var dataList = resultData.resultList;
                            var data = [];
                            for (var i = 0; i < dataList.length; i++) {
                                var listItem = dataList[i];
                                data.push({
                                    userName: listItem.USERNAME,
                                    amount: appFunc.fmoney(listItem.EFFECTIVEMONEY, 2),
                                    addTime: listItem.ADDTIME
                                });

                            }
                            monthFinanceDetailsView.showJoinHistory({"dataList": data, "type": type});
                        } else {
                            console.log("获取_产品加入记录失败");
                            $$(".monthFinanceDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                            xxdApp.addNotification({
                                title: '温馨提示',
                                hold: 3000,
                                message: '获取_产品加入记录失败，请稍后重试...'
                            });
                        }
                    } catch (e) {
                        console.log(e.message);
                        xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取_产品加入记录失败，请稍后重试...'
                        });
                    }
                },
                error: function (result) {
                    console.log("获取_产品加入记录失败,ajax error...");
                    $$(".monthFinanceDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                    xxdApp.addNotification({
                        title: '温馨提示',
                        hold: 3000,
                        message: '获取_产品加入记录失败，请稍后重试...'
                    });
                }
            });
        },

        //格式化时间 hh:mm:ss
        formathhmmss: function (data) {
            var hours = data.getHours();
            var minutes = data.getMinutes();
            var seconds = data.getSeconds();
            return (hours < 10 ? "0" + hours : hours) + ':' + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
        },

        //设置tab标签选中状态
        setTabSelectedStyle: function (param) {
            var tabs = ["introduction", "questions", "joinHistory"];
            for (var i = 0; i < tabs.length; i++) {
                var item = tabs[i];
                if (item == param.selected) {
                    $$(".monthFinanceDetails #" + item).addClass("active");
                } else {
                    $$(".monthFinanceDetails #" + item).removeClass("active");
                }
            }
        }

    };
    return monthFinanceDetailsCtrl;
});
