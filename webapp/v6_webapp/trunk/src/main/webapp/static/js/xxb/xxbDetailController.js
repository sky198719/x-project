define(function(){
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var flag = 0;
    var currentTab="all";
    var xxbDetailCtl = {
        init:function(event){
            var query = appFunc.getEventDetailPageQuery(event);
            xxbDetailCtl.initBindEvents();
            switch(query.tab) {
                case 'all':
                    xxbDetailCtl.goAllList();
                    break;
                case 'exchange':
                    xxbDetailCtl.goExchangeList();
                    break;
                case 'reward':
                    xxbDetailCtl.goRewardList();
                    break;
                default :
                    xxbDetailCtl.goAllList();
                    break;
            }
        },
        initBindEvents: function (){
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: xxbDetailCtl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: xxbDetailCtl.infiniteScroll
                },
                {
                    element:'#xxbAllBtn',
                    event:'click',
                    handler:xxbDetailCtl.goAllList
                },
                {
                    element:'#xxbExchangeBtn',
                    event:'click',
                    handler:xxbDetailCtl.goExchangeList
                },
                {
                    element:'#xxbRewardBtn',
                    event:'click',
                    handler:xxbDetailCtl.goRewardList
                },
                {
                    element: '.right',
                    event: 'click',
                    handler: xxbDetailCtl.changeArrow
                }
            ];
            appFunc.bindEvents(bindings);
        },
        changeArrow: function () {
            if(flag==0) {
                $$('#arrow').attr('src','static/img/xxd/arrow-up.png');
                $$("#tabBlock").show();
                flag=1;
            } else {
                $$('#arrow').attr('src','static/img/xxd/arrow-down.png');
                $$("#tabBlock").hide();
                flag=0;
            }
        },
        goAllList: function () {
            currentTab = 'all';
            xxbDetailCtl.setNavbarTitle("新新币明细");
            xxbDetailCtl.enableTabAll("true");
            xxbDetailCtl.enableTab2("false");
            xxbDetailCtl.enableTab3("false");
            xxbDetailCtl.pullToRefresh();
            flag=1;
            xxbDetailCtl.changeArrow();
        },
        goExchangeList: function () {
            currentTab = 'exchange';
            xxbDetailCtl.setNavbarTitle("新新币兑换");
            xxbDetailCtl.enableTabAll("false");
            xxbDetailCtl.enableTab2("true");
            xxbDetailCtl.enableTab3("false");
            xxbDetailCtl.pullToRefresh();
            flag=1;
            xxbDetailCtl.changeArrow();
        },
        goRewardList: function () {
            currentTab = 'reward';
            xxbDetailCtl.setNavbarTitle("新新币奖励");
            xxbDetailCtl.enableTabAll("false");
            xxbDetailCtl.enableTab2("false");
            xxbDetailCtl.enableTab3("true");
            xxbDetailCtl.pullToRefresh();
            flag=1;
            xxbDetailCtl.changeArrow();
        },
        enableTabAll: function(enable) {
            if (enable == "true") {
                $$(".xxb-button1").removeClass("button-xxb-inactive");
                $$(".xxb-button1").addClass("button-xxb-active");
            } else {
                $$(".xxb-button1").removeClass("button-xxb-active");
                $$(".xxb-button1").addClass("button-xxb-inactive");
            }
        },
        enableTab2: function (enable) {
            if (enable == "true") {
                $$(".xxb-button2").removeClass("button-xxb-inactive");
                $$(".xxb-button2").addClass("button-xxb-active");
            } else {
                $$(".xxb-button2").removeClass("button-xxb-active");
                $$(".xxb-button2").addClass("button-xxb-inactive");
            }
        },
        enableTab3: function (enable) {
            if (enable == "true") {
                $$(".xxb-button3").removeClass("button-xxb-inactive");
                $$(".xxb-button3").addClass("button-xxb-active");
            } else {
                $$(".xxb-button3").removeClass("button-xxb-active");
                $$(".xxb-button3").addClass("button-xxb-inactive");
            }
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            //这里要判断是哪个tab下的infinite scroll
            if(currentTab == "all") {
                xxbDetailCtl.selectAllList(1, "pull");
            }else if(currentTab == "exchange"){
                xxbDetailCtl.selectExchangeList(1, "pull");
            }else if(currentTab == "reward"){
                xxbDetailCtl.selectRewardList(1, "pull");
            }
            $$("#el-currentPage").val(1);
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
            var totalPage = $$("#el-totalPage").val();
            var currentPage = $$("#el-currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage > totalPage) {
                //提示标的已经全部显示(UED不用提示)
                /*xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '已经到底了'
                });*/
                loading = true;
                return;
            }
            //这里要判断是哪个tab下的infinite scroll
            if(currentTab == "all") {
                xxbDetailCtl.selectAllList(currentPage, "push");
            }else if(currentTab == "exchange"){
                xxbDetailCtl.selectExchangeList(currentPage, "push");
            }else if(currentTab == "reward"){
                xxbDetailCtl.selectRewardList(currentPage, "push");
            }
            $$("#el-currentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        selectAllList:function(currentPage, type) {
            req.callJSON({
                url: 'xxb/exchangeLogs.do',
                data: {
                    type:0,
                    currentPage:currentPage,
                    pageSize:pageSize
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    if(result !=null&&result.levelsLog!=null) {
                        $$("#el-totalPage").val(result.maxPage);
                        var list = result.levelsLog;
                        if(list.length == 0) {
                            //下拉刷新时后台没有数据不需要提示暂无记录
                            if (type == 'pull') {
                                $$("#xxbAllList ul").html('<h6 class="font-grey text-center pd10">暂无记录</h6>');
                                $$(".list-block").show();
                            }
                            return;
                        }
                        var html = '';
                        for (var i = 0; i < list.length; i++) {
                            var o = list[i];
                            var changeHow = o.changeHow;
                            var remark = o.remark;
                            var numHtml = "";
                            if(changeHow == 1){
                                remark = "系统增加新新币"+o.changeNum+"个";
                                numHtml = '<div class="item-after"><h4 class="font-red">+' + o.changeNum + '个</h4></div>';
                            }else if(changeHow == 2){
                                numHtml = '<div class="item-after"><h4 class="font-green">-' + o.changeNum + '个</h4></div>';
                            }else if(changeHow == 3){
                                remark = "兑换获得"+ appFunc.fmoney(o.changeNum/50,2) + "元";
                                numHtml = '<div class="item-after"><h4 class="font-green">-' + o.changeNum + '个</h4></div>';
                            }else if(changeHow == 4){
                                //新新币我们有个扣除功能,changeHow==4,changeNumber为负数
                                numHtml = '<div class="item-after"><h4 class="font-red">+' + o.changeNum + '个</h4></div>';
                            }else if(changeHow == 5){
                                numHtml = '<div class="item-after"><h4 class="font-green">-' + o.changeNum + '个</h4></div>';
                            }
                            html += '<li>';
                            html += '<div class="item-content">';
                            html += '<div class="item-inner">';
                            html += '<div class="item-title-row">';
                            html += '<div class="item-title"><span class="font-grey">' + remark + '</span></div>';
                            html += numHtml;
                            html += '</div>';
                            html += '<div class="item-subtitle"><span class="font-grey-A9">' + o.changeTime + '</span></div>';
                            html += '</div>';
                            html += '</div>';
                            html += '</li>';
                        }
                        if (type == 'push') {
                            $$("#xxbAllList ul").append(html);
                        } else {
                            $$("#xxbAllList ul").html(html);
                            // 加载完毕需要重置
                            xxdApp.pullToRefreshDone();
                        }
                    }
                }
            });
        },
        setNavbarTitle:function(title) {
            $$("#xxbDetail-title").html(title);
        },
        selectExchangeList:function(currentPage, type) {
            req.callJSON({
                url: 'xxb/exchangeLogs.do',
                data: {
                    type:2,
                    currentPage:currentPage,
                    pageSize:pageSize
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    if(result !=null&&result.levelsLog!=null) {
                        $$("#el-totalPage").val(result.maxPage);
                        var list = result.levelsLog;
                        if(list.length == 0) {
                            if (type == 'pull') {
                                $$("#xxbExchangeList ul").html('<h6 class="font-grey text-center pd10">暂无记录</h6>');
                                $$(".list-block").show();
                            }
                            return;
                        }
                        var html = '';
                        for (var i = 0; i < list.length; i++) {
                            var o = list[i];
                            var remark = o.remark;
                            if(o.changeHow == 3){
                                remark = "兑换获得"+ appFunc.fmoney(o.changeNum/50,2) + "元";
                            }
                            html += '<li>';
                            html += '<div class="item-content">';
                            html += '<div class="item-inner">';
                            html += '<div class="item-title-row">';
                            html += '<div class="item-title"><span class="font-grey">' + remark + '</span></div>';
                            html += '<div class="item-after"><h4 class="font-green">-' + o.changeNum + '个</h4></div>';
                            html += '</div>';
                            html += '<div class="item-subtitle"><span class="font-grey-A9">' + o.changeTime + '</span></div>';
                            html += '</div>';
                            html += '</div>';
                            html += '</li>';
                        }
                        if (type == 'push') {
                            $$("#xxbExchangeList ul").append(html);
                        } else {
                            $$("#xxbExchangeList ul").html(html);
                            // 加载完毕需要重置
                            xxdApp.pullToRefreshDone();
                        }
                    }
                }
            });
        },
        selectRewardList:function(currentPage, type) {
            req.callJSON({
                url: 'xxb/exchangeLogs.do',
                data: {
                    type:1,
                    currentPage:currentPage,
                    pageSize:pageSize
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    if(result !=null&&result.levelsLog!=null) {
                        $$("#el-totalPage").val(result.maxPage);
                        var list = result.levelsLog;
                        if(list.length == 0) {
                            if (type == 'pull') {
                                $$("#xxbRewardList ul").html('<h6 class="font-grey text-center pd10">暂无记录</h6>');
                                $$(".list-block").show();
                            }
                            return;
                        }
                        var html = '';
                        for (var i = 0; i < list.length; i++) {
                            var o = list[i];
                            var remark = o.remark;
                            if(o.changeHow == 1){
                                remark = "系统增加新新币"+o.changeNum+"个";
                            }
                            html += '<li>';
                            html += '<div class="item-content">';
                            html += '<div class="item-inner">';
                            html += '<div class="item-title-row">';
                            html += '<div class="item-title"><span class="font-grey">' + remark + '</span></div>';
                            html += '<div class="item-after"><h4 class="font-red">+' + o.changeNum + '个</h4></div>';
                            html += '</div>';
                            html += '<div class="item-subtitle"><span class="font-grey-A9">' + o.changeTime + '</span></div>';
                            html += '</div>';
                            html += '</div>';
                            html += '</li>';
                        }
                        if (type == 'push') {
                            $$("#xxbRewardList ul").append(html);
                        } else {
                            $$("#xxbRewardList ul").html(html);
                            // 加载完毕需要重置
                            xxdApp.pullToRefreshDone();
                        }
                    }
                }
            });
        }
    };
    return xxbDetailCtl;
});