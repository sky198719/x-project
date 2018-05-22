/**
 * 债权转让列表
 */
define(['js/trade/tradeRequestListV2View', 'js/trade/tradeRequestListModel'], function (tradeRequestListV2View, tradeRequestListModel) {

    var pageSize = 10;
    // 加载flag
    var loading = false;
    var tradeListV2Ctrl = {
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
                    handler: tradeListV2Ctrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: tradeListV2Ctrl.infiniteScroll
                }
            ];

            tradeRequestListV2View.bindEvents({
                    bindings: bindings
                }
            );
            tradeListV2Ctrl.pullToRefresh();
        },

        showTradeRequestDetail: function () {
        	var tradeRequestId = $(this).parent().attr("data-id");
        	var tradeApr = $(this).parent().attr("data-tradeApr");
        	var remainNumber = $(this).parent().attr("data-remainNumber");
        	try {
        		//XXD_TRACK._trackEvent({category: "webapp_trade_in", action: "buy_now", label: "立即抢购", value: "", custval: "" });
        		var name = "债权转让"+tradeRequestId;
        		var category = "债权转让/"+tradeApr+"%/"+remainNumber+"个月";
        		product_click({id:tradeRequestId,name:name,category:category,list:"债权转让列表"});
        	} catch (e) {}
            GS.loadPage('trade/tradeRequestDetail.html?tradeRequestId=' + tradeRequestId + "&path=trade");
        },

        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            tradeListV2Ctrl.selectTradeRequestList(1, "pull");
            $$("#tradeRequestCurrentPageV2").val(1);
            // 重置加载flag
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
            var totalPage = $$("#tradeRequestTotalPageV2").val();
            var currentPage = $$("#tradeRequestCurrentPageV2").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage > totalPage) {
                //提示标的已经全部显示
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '数据已全部加载完毕'
                });
                loading = true;
                return;
            }
            tradeListV2Ctrl.selectTradeRequestList(currentPage, "push");
            $$("#tradeRequestCurrentPageV2").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },

        /**
         * 查询债权转让列表
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectTradeRequestList: function (currentPage, type) {
            req.callJSON({
                url:'traderequest/queryTradeRequestMore.do',
                data:{
                    currentPage: currentPage,
                    pageSize: pageSize
                },
                dataType:'json',
                indicator:true,
                success:function(data){
                    if (data.resultCode == 100 || data.resultCode == 200) {
                        //提示标的已经全部显示
                        xxdApp.addNotification({
                            title: '提示',
                            hold: 3000,
                            message: '加载数据出错，请重新操作'
                        });
                        return;
                    }

                    if (data.resultList == null || data.resultList == undefined) {
                        //提示标的已经全部显示
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: '抱歉，没有加载到数据，请重新操作'
                        });
                        return;
                    }

                    $$("#tradeRequestTotalPageV2").val(data.pageNums);

                    if (data.resultList.currentPage > data.resultList.pageNums) {
                        //提示标的已经全部显示
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: '数据已全部加载完成'
                        });
                        return;
                    }

                    var list = data.resultList;
                    if (list.length == 0) {
                        //提示标的已经全部显示
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: '抱歉，没有加载到数据，请重新操作'
                        });
                        return;
                    }

                    var tradeRequestList = [];
                    var tempApr;

                    for (var k = 0; k < list.length; k++) {
                        var b = list[k];
                        tempApr = new String(b.apr).split(".");
                        tradeRequestList[k] = new tradeRequestListModel({
                            "requestId": b.requestId,
                            "tenderId": b.tenderId,
                            "tradeApr":b.apr,
                            "borrowName": b.borrowName,
                            "apr": '<span class="font-red font28">'+tempApr[0]+'</span><span class="font-red font16">.'+ (tempApr.length > 1 ? (tempApr[1]) : "00") +'%</span>',
                            "remainNumber": b.remainNumber,
                            "status": b.status,
                            "rep_fun": b.rep_fun < 10000 ? (appFunc.fmoney(b.rep_fun, 2) + "元") : (appFunc.fmoney(Math.floor(b.rep_fun/100)/100,2) + "万")
                        });
                    }

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'trade/tradeRequestListItemV2.html?' + GC.getVersion(),
                            dataType: 'text',
                            success:function(result){
                                tradeRequestListV2View.showListItem({result: result, tradeRequestList: tradeRequestList, type: type});
                                tradeListV2Ctrl.tenderListBindEvent();
                                // 加载完毕需要重置
                                xxdApp.pullToRefreshDone();
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }

                    setTimeout(function(){
                        if (type == 'pull') {
                            // 加载完毕需要重置
                            xxdApp.pullToRefreshDone();
                        }
                   },5000);
                }
            });
        },

        tenderListBindEvent: function () {
            var bindings = [
                {
                    element: '#tradeRequestListV2 .swipeout-content',
                    event: 'click',
                    handler: tradeListV2Ctrl.showTradeRequestDetail
                },
                {
                    element: '#tradeRequestListV2 .swipeout-actions-right',
                    event: 'click',
                    handler: tradeListV2Ctrl.trdExpressBid
                }
            ];
            tradeRequestListV2View.bindEvents({
                    bindings: bindings
                }
            );
        },

        // 快捷投标
        trdExpressBid: function () {
            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            //快捷投标校验
            var quickTender = false;
            req.callJSON({
                url: "borrow/quickTender.do",
                data: {},
                indicator: true,
                timeout: 10000,
                async: false,
                success: function (data) {
                    if(data.resultCode == -505){
                        xxdApp.alert(data.msg,"提示");
                        xxdApp.loginScreen();
                        quickTender = true;
                    }
                    if(data.quicktCode == -34){
                        xxdApp.alert(data.quicktmsg, '提示', function () {
                            GS.loadPage('personal/personalInfo.html');
                        });
                        quickTender = true;
                    }
                    if(data.paypwd == '0'){
                        xxdApp.alert('您还没有设置支付密码，请先设置支付密码', '提示', function () {
                            GS.loadPage('personal/personalInfo.html');
                        });
                        quickTender = true;
                    }
                }
            });
            if(quickTender){
                return;
            }
            var tradeRequestId = $(this).parent().attr("data-id");

            var requestUserId;
            var borrowUserId;
            var repayYesAccount;
            var terms;

            var requestId;
            var tenderId;

            req.callJSON({
                url: 'traderequest/requestDetail/' + tradeRequestId + '.do',
                data: {},
                dataType: 'json',
                async: false,
                preloaderTitle: '正在加载数据...',
                success: function (data) {
                    requestUserId = data.tradeRequestDetail.userId;
                    borrowUserId = data.borrowDetail.userId;
                    repayYesAccount = data.tradeRequestDetail.repayCapital - data.tradeRequestDetail.funds;
                    terms = data.tradeRequestDetail.remainNumber;

                    requestId = data.tradeRequestDetail.requestId;
                    tenderId = data.tradeRequestDetail.tenderId;

                    var userAccount = data.account.USABLE;
                    if (parseFloat(userAccount) < parseFloat(repayYesAccount)) {
                        xxdApp.alert('账户余额不足，请充值', '提示');
                        return;
                    }
                    var fundsHtml = "";
                    var funds = data.tradeRequestDetail.funds;
                    if (funds >= 0) {
                        fundsHtml = '<h5 class="text-left font-bold font-grey">折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;让：<span class="font18 font-red">'+ (funds == 0 ? '0.00' : funds) + ' </span>元</h5>';
                    } else if (funds < 0) {
                        fundsHtml =  '<h5 class="text-left font-bold font-grey">加价：<span class="font18 font-red">'+ (funds == 0 ? '0.00' : (funds * -1)) + ' </span>元</h5>';
                    }
                    //设置表单token
                    var formToken = appFunc.setToken({name:"TRADE_QUICK_TENDER", id: requestId});
                    if(formToken.code != 0) {
                        xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                        return;
                    }

                    xxdApp.modal({
                        title:  '<h4>确认购买债权</h4><h5 class="text-left font-grey">借款标题：'+ data.tradeRequestDetail.borrowName +'</h5>'+
                                '<h5 class="text-left font-grey">剩余期限：'+ data.tradeRequestDetail.remainNumber +
                                ' 个月</h5><h5 class="text-left font-grey">年化收益：<span class="font18 font-red">'+ appFunc.fmoney(data.tradeRequestDetail.apr, 2) + ' </span>%</h5>' + fundsHtml +
                                '<h5 class="text-left font-bold">转让价格：<span class="font18 font-red">'+ appFunc.fmoney(repayYesAccount, 2) +
                                '</span> 元</h5><h5 class="text-left font-bold">实际支付：<span class="font18 font-red">'+ appFunc.fmoney(repayYesAccount, 2) +'</span> 元</h5>',
                        text: '<h6><a href="#" id="tradeRequesAgreement">我同意《资金出借风险提示函》<br>并确认投标</a></h6>'+
                            '<h5>'+
                            '<input type="password" placeholder="请输入支付密码" class="modal_input express_modalinput mt10">'+
                            '</h5>',
                        buttons: [
                            {
                                text: '取消',
                                onClick: function() {
                                    return;
                                }
                            },
                            {
                                text: '确认投标',
                                onClick: function() {
                                    if (!appFunc.isLogin()) {
                                        xxdApp.loginScreen();
                                        return;
                                    }
                                    var password = $$('.modal_input').val();
                                    if(password == null || password == ''){
                                        xxdApp.alert('请输入支付密码！')
                                        return
                                    }
                                    // 支付密码正确性、验证码正确性、账户可用资金、体验金、红包等校验
                                    xxdApp.showIndicator('正在努力购买...');
                                    req.callPost({
                                        url: 'tradepack/checkBeforeBuyTrade.do',
                                        data: {
                                            "payPassword": $.md5($.md5(password)),
                                            "requestUserId": requestUserId,
                                            "borrowUserId": borrowUserId,
                                            "repayYesAccount": repayYesAccount,
                                            "terms": terms,
                                            "tokenName": formToken.data.tokenName,
                                            "token": formToken.data.token
                                        },
                                        dataType: 'json',
                                        async: false,
                                        timeout: 150000,
                                        success: function (result) {
                                            xxdApp.hideIndicator();
                                            if (result.resultCode == -99) {
                                                xxdApp.alert(result.msg);
                                                xxdApp.loginScreen();
                                                return false;
                                            }
                                            $(this).removeAttr("disabled");
                                            $(this).find(".trd_express-bid").first().html("快捷投标");
                                            if(result.resultCode == -505) {
                                                xxdApp.alert(result.msg);
                                                return false;
                                            }

                                            if (result.resultCode < 0) {
                                                var msg = '';
                                                //有错误，弹出错误提示
                                                var s = result.msg.split(":");
                                                if (s[0] == "verifyCode" || s[0] == "payPassword") {
                                                    msg = s[1];
                                                } else {
                                                    msg = result.msg;
                                                }
                                                xxdApp.alert(msg, '提示');
                                                return false;
                                            }
                                            //301为token失效error
                                            if (result.resultCode == 301) {
                                                return  false;
                                            }

                                            //校验通过后，保存债权转让数据
                                            req.callPost({
                                                url: 'tradepack/buyTradePack.do',
                                                data: {
                                                    "requestId": requestId,
                                                    "tenderId": tenderId,
                                                    "experienceAccountUsable": 0,
                                                    "requestUserId": requestUserId
                                                },
                                                dataType: 'json',
                                                success: function (result) {
                                                    if (result.resultCode == 0) {
                                                        xxdApp.alert(result.msg, '提示',function(){
                                                            GS.reloadPage("trade/tradeRequestListV2.html");
                                                        });
                                                    } else {
                                                        xxdApp.alert(result.msg, '抱歉',function(){
                                                            GS.reloadPage("trade/tradeRequestListV2.html");
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        ]
                    });
                    tradeListV2Ctrl.open_popup();
                }
            });
        },
        open_popup: function(){
            var bindings = [
                {
                    element: '#tradeRequesAgreement',
                    event: 'click',
                    handler: tradeListV2Ctrl.tenderAgreement
                }
            ];
            tradeRequestListV2View.bindEvents({
                    bindings: bindings
                }
            );
        },
        tenderAgreement:function(){
            req.callGet({
                url: GC.getHtmlPath() + 'borrowTender/borrowTenderAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    $$(".popup-bidhistory").html(result);
                }
            });

            xxdApp.popup('.popup-bidhistory');
        }
    };

    return tradeListV2Ctrl;
});