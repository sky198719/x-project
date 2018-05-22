define([], function () {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var exchangeRMB = "goExchangeRMB";
    var goodsListCtrl = {
        init: function () {
        	mainView.showNavbar();
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: goodsListCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: goodsListCtrl.infiniteScroll
                },
                {
                    element: '#goodsList #getMoreCoin',
                    event: 'click',
                    handler: goodsListCtrl.getMoreCoin
                }
            ];

            appFunc.bindEvents(bindings);
            
            if (!appFunc.isLogin()) {
                GS.loadPage('activityRegAndLogin/register01.html');
                return;
            }
            
            goodsListCtrl.getMyCoins();
            goodsListCtrl.pullToRefresh();
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            goodsListCtrl.selectGoods(1, "pull");
            $$("#currentPage").val(1);
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
            var totalPage = $$("#totalPage").val();
            var currentPage = $$("#currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if(currentPage>totalPage){
                //提示标的已经全部显示
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold:3000,
                    message: '数据已全部加载完毕'
                });
                loading = true;
                return;
            }
            goodsListCtrl.selectGoods(currentPage, "push");
            $$("#currentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function(){
                loading = false;
            },1500);
            //==========================切记此处不能删除============================
        },

        selectGoods: function (currentPage, type) {
            req.callJSON({
                url:"xxStore/getMarketGoods.do",
                data:{
                    currentPage: currentPage,
                    pageSize: pageSize
                },
                indicator:true,
                timeout:15000,
                success: function(data){
                    if (data.resultCode == 200) {
	
	                    $$("#totalPage").val(data.totalPages);
	
	                    var listData = data.listData;
	                    if (listData.length == 0) {
	                        xxdApp.addNotification({
	                            title: '温馨提示',
	                            hold:3000,
	                            message: '抱歉，没有加载到数据，请重新操作'
	                        });
	                        return;
	                    }
						
	                    var list = [];
	                    var showGoods = ["MG2016072600009","MG2016072600010","MG2016072600011"];
	                    for (var j = 0; j < listData.length; j++) {
	                        var gd = listData[j];
	                        for(var i = 0; i < showGoods.length; i++){
	                        	if(showGoods[i] == gd.goodsNum){
	                        		list.push(gd);
	                        		break;
	                        	}
	                        }
	                    }
	                    
	                    var rmb = {"id":exchangeRMB,"goodsNum":exchangeRMB,"goodsName":"人民币1元起兑","xinxinCoinTotalPay":50};
	                    list.push(rmb);
	                    
	                    var goodsList = [];
	                    var good;
	                    var imagePreUrl = data.imagePreUrl;
	                    var goodsListRow = {};
	                    for (var k = 0; k < list.length;) {
	                    	goodsListRow = {};
	                        good = list[k];
	                        
	                        good.imgPath = imagePreUrl + good.imgPath;
	                        goodsListRow.flag1 = true;
	                        goodsListRow.item1 = good;
	                        
	                        good = list[k+1];
	                        if(good == null){
	                        	goodsListRow.flag2 = false;
	                        }else{
		                        good.imgPath = imagePreUrl + good.imgPath;
		                        goodsListRow.flag2 = true;
	                        	goodsListRow.item2 = good;
	                        }
	                        
	                        goodsList.push(goodsListRow);
	                        k = k + 2;
	                    }
	                    try {
	                        req.callGet({
	                            url: GC.getHtmlPath() + 'xxStore/goodsListItem.html?' + GC.getVersion(),
	                            dataType: 'text',
	                            success: function (result) {
	                                
	                                var compiledTemplate = t7.compile(result);
						            var output = compiledTemplate({list: goodsList});
						
						            if (type == 'push') {
						                $$("#goodsListData").append(output);
						            } else {
						                $$("#goodsListData").html(output);
						            }
						
	                                goodsListCtrl.bindListEvent();
	                                // 加载完毕需要重置
	                                xxdApp.pullToRefreshDone();
	                            }
	                        });
	                    } catch (e) {
	                        xxdApp.hideIndicator();
	                    }
                    }else{
                    	xxdApp.alert(result.resultDesc, '提示');
                    }
                }
            });

            setTimeout(function(){
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
           },5000);
        },
		goGoodsDetail: function () {
            var goodsId = $$(this).attr("data-goodsId");
            if(exchangeRMB == goodsId){
            	GS.loadPage('xxb/exchange.html');
            }else{
            	GS.loadPage('xxStore/goodsDetail.html?goodsId=' + goodsId);
            }
            
        },
        getMoreCoin: function () {
            window.location.href = "webchat/intoHitplane.do";
        },
		getMyCoins: function () {
            req.callJSON({
                url:"xxStore/getMyCoins.do",
                data:{
                },
                indicator:true,
                timeout:15000,
                success: function(data){
                	if(data.resultCode == 0){
                		$$("#goodsList #myCoins").html(data.myCoins);
                	}else{
                		xxdApp.alert(data.resultDesc,"提示");
                	}
                }
            });
        },
        bindListEvent: function(){
            var bindings = [
                {
                     element: '#goodsList .goodsItem',
                     event: 'click',
                     handler: goodsListCtrl.goGoodsDetail
                }
            ];
            appFunc.bindEvents(bindings);
        }
    };

    return goodsListCtrl;
});