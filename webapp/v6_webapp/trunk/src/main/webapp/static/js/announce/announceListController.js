/**
 * 公告活动
 */
define(['js/announce/announceListView','js/utils/date'], function (announceListView, DateHandle) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var unReadList = [];
    var announceListCtrl = {
        init: function () {
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: announceListCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: announceListCtrl.infiniteScroll
                },
                {
                    element: '.announceList #announceTab',
                    event: 'show',
                    handler: announceListCtrl.showAnnounceTab
                },
                {
                    element: '.announceList #activityTab',
                    event: 'show',
                    handler: announceListCtrl.showActivityTab
                },
                {
                    element: '#readAll',
                    event: 'click',
                    handler: announceListCtrl.readAll
                }
            ];
            announceListView.bindEvents({
                        bindings: bindings
                    }
            );
            announceListCtrl.pullToRefresh();
        },

        /**
         * 公告
         */
        showAnnounceTab: function () {
            $$('.announceList #currentTab').val(1);
            $$("#announce_pageTitle").css("left","0px");
            $$("#readAll").show();
            announceListCtrl.pullToRefresh();
        },
        /**
         * 活动
         */
        showActivityTab: function () {
            $('.announceList #currentTab').val(2);
            $$("#readAll").hide();
            $$("#announce_pageTitle").css("left","-15px");
            announceListCtrl.pullToRefresh();
        },
        
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            var currentTab = $('.announceList #currentTab').val();
            announceListCtrl.selectItems(1, "pull",currentTab);
            $$(".announceList #currentPage").val(1);
            
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
            var currentTab = $('.announceList #currentTab').val();
            var totalPage,currentPage,tab;
        	totalPage = $$(".announceList #totalPage").val();
            currentPage = $$(".announceList #currentPage").val();
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
            $$(".announceList #currentPage").val(currentPage);
            
            announceListCtrl.selectItems(currentPage, "push",currentTab);
            
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },

        selectItems: function (currentPage, type ,tab) {
            req.callJSON({
                url: "announce/getList.do",
                data: {
                    "currentPage": currentPage,
                    "pageSize": pageSize,
                    "type":tab
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                	try{
                		var listItem = "";
                		var listShow = [];
                		if(data.resultCode == 0){
		                    var list = data.data;
		                    $$(".announceList #totalPage").val(Math.ceil(parseInt(data.totalSize)/parseInt(pageSize)));
		                    
							if (1 == tab) {
								listItem = "announceListItem";
								if(type == "pull"){
									unReadList = [];
								}
			                    if (list != null && list != "") {
			                    	var hasRead = announceListCtrl.getHasRead();
			                    	var hasNew = false;
			                        for (var i = 0; i < list.length; i++) {
			                            var al = list[i];
			                            var isNew = true;
			                            for(var j = 0; j < hasRead.length ; j++ ){
			                            	if(hasRead[j] == al.id){
				                            	isNew = false;
				                            	break;
					                        }
			                            }
			                            if(isNew == true){
			                            	if(hasNew == false){
				                            	hasNew = true;
//				                            	$$("#readAll").on('click', function (e) { 
//												  announceListCtrl.readAll(); 
//												});
				                            	$$("#readAll").attr("canClick","true");
												$$("#readAll").css("color","#007aff");
			                            	}
			                            	
			                            	unReadList.push(al.id);
			                            }
			                            al.isNew = isNew;
			                            listShow.push(al);
			                        }
			                    }
				            } else {
				            	listItem = "activityListItem";
				            	listShow = list;
				            }
                            req.callGet({
                                    url: GC.getHtmlPath() + 'announce/'+listItem+'.html?' + GC.getVersion(),
                                    dataType: 'text',
                                    success: function (result) {
                                        announceListView.showListItem({
                                            result: result,
                                            listShow: listShow,
                                            type: type,
                                            tab: tab
                                        });
                                        announceListCtrl.announceListBindEvents();
                                        // 加载完毕需要重置
                                        xxdApp.pullToRefreshDone();
                                    }
                                });
                		}else{
                			xxdApp.addNotification({ title: '抱歉', message: data.desc, hold: 3000 });
                		}
                        
	                  }catch (e) {
	                  	xxdApp.hideIndicator();
                		console.log(e.message);
                		xxdApp.addNotification({ title: '抱歉', message: '获取公告活动失败，请稍后重试...', hold: 3000 });
        			}
                },
                error: function(xhr, type){
                	console.log("获取公告活动失败,ajax error...");
                    xxdApp.addNotification({ title: '抱歉', message: '获取公告活动失败，请稍后重试...', hold: 3000 });
                }
            });

            setTimeout(function () {
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            }, 5000);
        },
        announceListBindEvents:function(){
        	var bindings = [
                {
                    element: '.announceList #announceList li',
                    event: 'click',
                    handler: announceListCtrl.toAnnounceDetail
//                },
//                {
//                    element: '.announceList #activityList li',
//                    event: 'click',
//                    handler: announceListCtrl.toActivityDetail
                }
            ];
            appFunc.bindEvents(bindings);
        },
        toAnnounceDetail:function(){
        	var announceId = $$(this).data("announceId");
        	var hasRead = announceListCtrl.getHasRead();
        	
        	for(var i=0; i < unReadList.length; i++) {
			    if(unReadList[i] == announceId) {
			    	hasRead.push(announceId);
			        unReadList.splice(i, 1);
			        $$("#redDot_" + announceId).hide();
			        break;
			    }
			}
        	localStorage.hasRead = JSON.stringify(hasRead);
			
			if(unReadList.length == 0){
//				$$("#readAll").off('click', function (e) { 
//				  announceListCtrl.readAll(); 
//				});
				$$("#readAll").attr("canClick","false");
				$$("#readAll").css("color","#999999");
			}
			
        	GS.loadPage("announce/announceDetail.html?announceId=" + announceId);
        },
        toActivityDetail:function(){
        	var announceId = $$(this).data("announceId");
        	GS.loadPage("announce/announceDetail.html?announceId=" + announceId);
        },
        readAll:function(){
//        	$$("#readAll").off('click', function (e) { 
//			  announceListCtrl.readAll(); 
//			});
        	var canClick = $$("#readAll").attr("canClick");
        	if(canClick == "false"){
        		return;
        	}
        	$$("#readAll").attr("canClick","false");
        	$$("#readAll").css("color","#999999");
        	var hasRead = announceListCtrl.getHasRead();
        	for(var i = 0; i < unReadList.length;i++){
        		var announceId = unReadList[i];
        		$$("#redDot_" + announceId).hide();
        		hasRead.push(announceId); 
        	}
        	unReadList = [];
    		localStorage.hasRead = JSON.stringify(hasRead); 
//    		mainView.router.refreshPage();
        },
        getHasRead: function(){
        	var hasRead = localStorage.hasRead; 
        	if(hasRead == undefined || hasRead == null){
        		hasRead = [];
        	}else{
        		hasRead = JSON.parse(localStorage.hasRead)
        	}
        	return hasRead;
        }
    };
    return announceListCtrl;
});