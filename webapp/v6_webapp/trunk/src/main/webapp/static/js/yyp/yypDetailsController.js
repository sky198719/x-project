define(['js/yyp/yypDetailsView','js/common/productUtil','js/utils/date','js/utils/dayController','js/common/ami'],function (yypDetailsView,productUtil,DateHandle,dayController,ami) {
    var yypId= "";
    var pageSize = 10;
    var activeTab = "";
    // 加载flag
    var loading = false;
    var productInfo;
    var pId;
    var yypDetailsCtrl = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);
            pId = query.yypId;

            yypDetailsCtrl.bindingEvents();
            // yypDetailsCtrl.getProductInfo();
            // req.callGet({
            //     url: 'product/getWebappProduct.do',
            //     data: {
            //         pCode:"YYP",
            //         // currentPage:1,
            //         // pageSize:40
            //     },
            //     dataType: 'json',
            //     // async: false,
            //     success: function (result) {
            //         if (result.code == 200000) {
            //             // pId = result.data.items.id;
            //             var data = result.data;
            //             pId = data.items.id;
            //             yypDetailsCtrl.getProductInfo();
            //         }
            //     }
            // });
            //查询产品信息
            yypDetailsCtrl.pullToRefresh();


            //dayController.planDetailMemberDay();
        },

        bindingEvents: function () {
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: yypDetailsCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: yypDetailsCtrl.infiniteScroll
                },
                {
                    element: '.yypDetails #introduction',
                    event: 'click',
                    handler: yypDetailsCtrl.getIntroduction
                },
                {
                    element: '.yypDetails #questions',
                    event: 'click',
                    handler: yypDetailsCtrl.getQuestions
                },
                {
                    element: '.yypDetails #joinHistory',
                    event: 'click',
                    handler: yypDetailsCtrl.getJoinHistory
                },
                {
                    element: '.yypDetails #fenxiantishi',
                    event: 'click',
                    handler: ami.fenxiantishi
                },
                {
                    element: '.yypDetails #goTender',
                    event: 'click',
                    handler: yypDetailsCtrl.goTender
                },
                {
                    element: '#tradeList',
                    event: 'click',
                    handler: yypDetailsCtrl.tradeList
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
            yypDetailsCtrl.getProductInfo();
            $$(".yypDetails #currentPage").val(1);
            loading = false;
        },

        /**
         * 无限滚动
         */
        infiniteScroll: function () {

            // 如果当前tab不是投资记录
            if (activeTab != "joinHistory") {
                return;
            }

            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            //==========================切记此处不能删除============================
            loading = true;
            //==========================切记此处不能删除============================
            var currentTab = $$('.yypDetails #currentTab').val();
            var totalPage, currentPage;
            totalPage = $$(".yypDetails #totalPage").val();
            currentPage = $$(".yypDetails #currentPage").val();
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
            $$(".yypDetails #currentPage").val(currentPage);
            yypDetailsCtrl.joinHistory(currentPage,pageSize,"push")
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },

        //产品信息
        getProductInfo:function(){
            req.callJSON({
                url: 'product/getProduct.do',
                data: {
                    pCode: 'YYP',
                    pId: pId
                },
                indicator: true,
                success: function (result) {
                    try{
                        if(result.code == 200000){
                            var resultData = result.data;
                            productInfo = resultData;

                            //产品ID
                            pId = resultData.productId;
                            //产品名称 和 期号
                            $$(".yypDetails #yypItemName").html(resultData.periodName);
                            $$(".yypDetails #terms").html(resultData.leastPeriod);

                            $$(".yypDetails #yypItemTerm").html(resultData.leastPeriod);
                            //年化利率
                            var apr = parseFloat(resultData.plannedAnnualRate);
                            $$(".yypDetails #yypApr").html(apr);

                            $$(".yypDetails #remainderAmount").html(appFunc.fmoney(resultData.leftAmount,2));
                            //起投金额
                            var tenderStartAmount = resultData.leastInvestAmount;
                            $$(".yypDetails #tenderStartAmount").html(appFunc.fmoney(tenderStartAmount, 2));

                            var tenderButton = $$("#goTender");
                            var status = resultData.status;
                            if(status == 236) {
                                tenderButton.addClass("disable").html("等待发售");
                            } else if(status == 237){
                                tenderButton.removeClass("disable").html("我要投资");
                                if(productInfo.leftAmount ==0) {
                                    tenderButton.addClass("disable").html("已抢光");
                                }
                            } else if(status == 238){
                                tenderButton.addClass("disable").html("已抢光");
                            }else {
                                tenderButton.addClass("disable").html("收益中");
                            }

                        }else{
                            xxdApp.addNotification({ title: '温馨提示', message: '获取月月派产品信息失败，请稍后重试...', hold:3000});
                        }

                        mainView.showToolbar();

                        // 加载完毕需要重置
                        xxdApp.pullToRefreshDone();

                        if(activeTab == "joinHistory"){
                            yypDetailsCtrl.getJoinHistory();
                        }else if(activeTab == "questions"){
                            yypDetailsCtrl.getQuestions();
                        }else{
                            yypDetailsCtrl.getIntroduction();
                        }

                        try {
                            var bidType = resultData.activitedType;
                            if(2==bidType) {
                                req.callJSON({
                                    url: 'product/activityLabel.do',
                                    data: {
                                        productId:resultData.productId
                                    },
                                    dataType: 'json',
                                    success:function(result1) {
                                        if(result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                                            var remark = result1.data.data.remark;
                                            if(remark.length > 7) {
                                                $$(".activityLabel_yyp_detail_text").html("活动奖励：<marquee  scrollamount='1' style='background-color: #ff7365;width: 100px;text-align: center;border-radius:2px;color:#fff;vertical-align: bottom;'>"+remark+'</marquee>');
                                            } else {
                                                $$(".activityLabel_yyp_detail_text").html("活动奖励：<span style='background-color: #ff7365;padding: 2px 10px;width: 79%;text-align: center;border-radius:2px;color:#fff;vertical-align: bottom;'>"+remark+'</span>');
                                            }

                                            $$(".activityLabel_yyp_detail").show();
                                        }
                                    }
                                });
                            }
                        }catch (e) {
                            console.log(e);
                        }
                    }catch (e) {
                        console.log(e.message);
                        xxdApp.addNotification({ title: '抱歉', message: '获取月月派产品信息失败，请稍后重试...', hold: 3000 });
                    }
                },
                error: function(xhr, type){
                    console.log("获取月月派产品加入记录失败,ajax error...");
                    xxdApp.addNotification({ title: '抱歉', message: '获取月月派产品信息失败，请稍后重试...', hold: 3000 });
                }
            });
        },

        goTender:function(){
            if($(this).hasClass("disable")){
                //xxdApp.alert('该产品暂不可购买', '抱歉');
                return;
            }
            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            var userStepAccount = productUtil.getUserStepUpwardInfo();
            if(parseFloat(userStepAccount.remaCapitalTotal) >= parseFloat(productInfo.userMostTender)){
                xxdApp.alert('您的个人可购买额度已满额', '提示');
                return;
            }
            if(pId != null && pId != ""){
                var dmp_urlParam = "";
                dmp_urlParam = dmp_querystring();
                function dmp_querystring() {
                    var pattern = new RegExp("[?&]"+ "xxd_utm_source" +"\=([^&]+)", "g");
                    var matcher = pattern.exec(decodeURIComponent(location.href));
                    var items = null;
                    if(null != matcher){
                        try{
                            items = decodeURIComponent(decodeURIComponent(matcher[1]));
                        }catch(e){
                            try{
                                items = decodeURIComponent(matcher[1]);
                            }catch(e){
                                items = matcher[1];
                            }
                        }
                    }
                    //console.log(items);
                    return items;
                };
                if(dmp_urlParam == "" || dmp_urlParam == null) {
                    GS.loadPage("yyp/yypTender.html?yypId=" + pId);
                }else {
                    GS.loadPage("yyp/yypTender.html?yypId=" + pId + "&xxd_utm_source=" + dmp_urlParam);
                }
            }else{
                xxdApp.addNotification({ title: '温馨提示', message: '系统异常，请刷新页面重试...', hold:3000 });
            }
        },

        //获取产品说明
        getIntroduction:function(){
            yypDetailsCtrl.setTabSelectedStyle({"selected":"introduction"});
            yypDetailsView.showIntroduction(productInfo);
        },

        //获取常见问题
        getQuestions:function(){
            yypDetailsCtrl.setTabSelectedStyle({"selected":"questions"});
            yypDetailsView.showQuestions(productInfo);
            yypDetailsView.getCommonInfo(DateHandle.formatDate('yyyy-MM-dd',productInfo.openTime));
        },

        //获取投资记录
        getJoinHistory: function () {
            // yypDetailsCtrl.setTabSelectedStyle({"selected":"joinHistory"});
            // $$(".yypDetails #currentPage").val(1);
            // loading = false;
            // yypDetailsCtrl.joinHistory(1,pageSize,"pull");
            yypDetailsCtrl.newJoinHistory(1, pageSize, "pull");
        },

        //获取投资记录
        newJoinHistory:function(currentPage,pageSize,type){
            yypDetailsCtrl.setTabSelectedStyle({"selected":"joinHistory"});
            //产品ID是否为空
            if (pId == null || pId == "") {
                console.log("月月派产品ID为空");
                xxdApp.addNotification({ title: '温馨提示', message: '获取月月派产品加入记录失败，请稍后重试...', hold:3000 });
                $$(".yypDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                return;
            }

            //是否已登录
//        	if (!appFunc.isLogin()) {
//                xxdApp.loginScreen();
//                return;
//            }

            req.callJSON({
                url: 'product/productJoinRecords.do',
                data: {
                    pId: pId,
                    pCode:'YYP',
                    currentPage: currentPage,
                    pageSize: pageSize
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    console.log(result);
                    try{
                        if(result.code == 200000){
                            var resultData = result.data;
                            var totalTender = resultData.countJoinTime;
                            var totalPage = Math.floor(totalTender / pageSize);
                            totalPage += (totalTender % pageSize) > 0 ? 1 :0;
                            $$(".yypDetails #totalPage").val(totalPage);
                            var dataList = resultData.items;
                            var data = [];
                            for (var i = 0; i < dataList.length; i++) {
                                var listItem = dataList[i];
                                data.push({
                                    userName: listItem.nickName,
                                    amount: appFunc.fmoney(listItem.joinAmount,2),
                                    addTime:DateHandle.formatDate("yyyy-MM-dd",new Date(listItem.joinDate))
                                });
                            }
                            yypDetailsView.showJoinHistory({"dataList":data,"type":type});
                        }else{
                            $$(".yypDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                        }
                    }catch (e) {
                        console.log(e.message);
                        $$(".yypDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                        xxdApp.addNotification({ title: '温馨提示', message: '获取月月派产品加入记录失败，请稍后重试...', hold:3000 });
                    }
                },
                error: function (result){
                    console.log("获取月月派产品加入记录失败,ajax error...");
                    $$(".yypDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                    xxdApp.addNotification({ title: '温馨提示', message: '获取月月派产品加入记录失败，请稍后重试...', hold:3000 });
                }
            });
        },

        //获取投资记录
        joinHistory:function(currentPage,pageSize,type){
            //产品ID是否为空
            if (pId == null || pId == "") {
                console.log("月月派产品ID为空");
                xxdApp.addNotification({ title: '温馨提示', message: '获取月月派产品加入记录失败，请稍后重试...', hold:3000 });
                $$(".yypDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                return;
            }

            //是否已登录
//        	if (!appFunc.isLogin()) {
//                xxdApp.loginScreen();
//                return;
//            }

            req.callJSON({
                url: 'yyp/joinHistory.do',
                data: {"yypId":pId,"currentPage":currentPage,"pageSize":pageSize},
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    try{
                        if(result.resultCode == 0){
                            $$(".yypDetails #totalPage").val(result.totalPages);
                            var resultData = result.resultList;
                            var data = [];
                            for (var i = 0; i < resultData.length; i++) {
                                var listItem = resultData[i];
                                data.push({
                                    userName: listItem.NICKNAME,
                                    amount: appFunc.fmoney(listItem.USERACCOUNT,2),
                                    addTime:listItem.JOINTIME
                                });
                            }
                            yypDetailsView.showJoinHistory({"dataList":data,"type":type});
                        }else{
                            $$(".yypDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                        }
                    }catch (e) {
                        console.log(e.message);
                        $$(".yypDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                        xxdApp.addNotification({ title: '温馨提示', message: '获取月月派产品加入记录失败，请稍后重试...', hold:3000 });
                    }
                },
                error: function (result){
                    console.log("获取月月派产品加入记录失败,ajax error...");
                    $$(".yypDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
                    xxdApp.addNotification({ title: '温馨提示', message: '获取月月派产品加入记录失败，请稍后重试...', hold:3000 });
                }
            });
        },
        //设置tab标签选中状态
        setTabSelectedStyle:function(param){
            var tabs=["introduction","questions","joinHistory","fenxiantishi"];
            for(var i=0;i<tabs.length;i++){
                var item = tabs[i];
                if(item == param.selected){
                    activeTab = item;
                    $$(".yypDetails #"+item).addClass("active");
                }else{
                    $$(".yypDetails #"+item).removeClass("active");
                }
            }
        }
    };
    return yypDetailsCtrl;
});

