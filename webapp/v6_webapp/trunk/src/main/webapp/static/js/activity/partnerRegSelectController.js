define(['js/activity/partnerRegSelectView', 'js/utils/date'], function (partnerRegSelectView, DateHandle) {
    var pCode = '';
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var stores={};
    var showStoreName = true;
    var partnerInterval;
    var monthNames = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月' , '九月' , '十月', '十一月', '十二月'];
    var dayNames = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];    
    var calendarend = xxdApp.calendar({});
    var calendarBegin = xxdApp.calendar({});
    var paramPhone = "";
    var paramBeginDate = "";
    var paramEndDate = "";
    var paramlowerlevelCode = "";
    var partnerRegSelectCtrl = {
        init: function (event) {            
            
            clearInterval(partnerInterval);
            pCode = appFunc.getEventDetailPageQuery(event).pCode;
            pCode = pCode == undefined ? "" : pCode ;
            partnerRegSelectCtrl.initBeginDate();
            
            partnerRegSelectCtrl.initEndDate();
            
            partnerRegSelectCtrl.getPartnerSumData();
            
            partnerRegSelectCtrl.getStoreList();

            partnerRegSelectCtrl.eventBind();

            partnerInterval = window.setInterval(function () {
                var value = $$("#partnerStoresVal");
                if(value.html() == '') {
                    value.html('<div style="width:50%">-全部分店-</div>'+
                               '<div style="width:50%;text-align: right;">选择其它分店&nbsp;></div>');
                }
             },1500);
        },
        
        initBeginDate:function(){
           calendarBegin.destroy();
           calendarBegin = xxdApp.calendar({
                input: '.partnerRegSelect #beginDate',
                dateFormat: 'yyyy-mm-dd',
                monthNames: monthNames,
                monthNamesShort: monthNames,
                dayNames: dayNames,
                dayNamesShort: dayNames,
                scrollToInput: false,
                closeOnSelect:true,
                toolbar: true,
                inputReadOnly:true,
                onClose:function (p){
                	var value = p.value;
                	if(value != null){
	                    var beginDate = new Date(value[0]);
	                    beginDate = DateHandle.formatDate('yyyy-MM-dd',beginDate);
	                    $$(".partnerRegSelect #beginDate").html(beginDate);
	                    $$(".partnerRegSelect .closeBeginDate").show();
                    }
                },
                onOpen: function(){
                	partnerRegSelectCtrl.clearErrorMsg("date");
                }
            });

        },
        
        initEndDate:function(){
            calendarend.destroy();
            calendarend = xxdApp.calendar({
                input: '.partnerRegSelect #endDate',
                dateFormat: 'yyyy-mm-dd',
                monthNames: monthNames,
                monthNamesShort: monthNames,
                dayNames: dayNames,
                dayNamesShort: dayNames,
                scrollToInput: false,
                closeOnSelect:true,
                toolbar: true,
                inputReadOnly:true,
                onClose:function (p){
                	var value = p.value;
                	if(value != null){
	                    var endDate = new Date(value[0]);
	                    endDate = DateHandle.formatDate('yyyy-MM-dd',endDate);
	                    $$(".partnerRegSelect #endDate").html(endDate);
	                    $$(".partnerRegSelect .closeEndDate").show();
                	}
                },
                onOpen: function(){
                	partnerRegSelectCtrl.clearErrorMsg("date");
                }
            });
            var today = DateHandle.formatDate('yyyy-MM-dd',new Date());
            $$(".partnerRegSelect #endDate").html(today);
            $$(".partnerRegSelect #endDate").val(today);
            $$(".partnerRegSelect .closeEndDate").show();
        },
        
        eventBind: function () {
            var bindings = [
                {
                    element: '.partnerRegSelect.pull-to-refresh-content',
                    event: 'refresh',
                    handler: partnerRegSelectCtrl.pullToRefresh
                },
                {
                    element: '.partnerRegSelect.infinite-scroll',
                    event: 'infinite',
                    handler: partnerRegSelectCtrl.infiniteScroll
                },
                {
                    element: '.partnerRegSelect #refreshSumData',
                    event: 'click',
                    handler: partnerRegSelectCtrl.refresh
                },
                {
                    element: '.partnerRegSelect #queryPartnerListData',
                    event: 'click',
                    handler: partnerRegSelectCtrl.queryPartnerListData
                },
                {
                    element: '.partnerRegSelect .closeBeginDate',
                    event: 'click',
                    handler: partnerRegSelectCtrl.closeBeginDate
                },
                {
                    element: '.partnerRegSelect .closeEndDate',
                    event: 'click',
                    handler: partnerRegSelectCtrl.closeEndDate 
                },
                {
                    element: '.partnerRegSelect .moreInfo',
                    event: 'click',
                    handler: partnerRegSelectCtrl.moreInfo 
                },
                {
                    element: '#partnerRegSelect #phoneNo',
                    event: 'focus',
                    handler: partnerRegSelectCtrl.clearErrorMsg
                },
                {
                    element: '.partnerRegSelect #phoneNo',
                    event: 'keyup',
                    handler: partnerRegSelectCtrl.showClearButton 
                },
                {
                    element: '.partnerRegSelect .clearPhone',
                    event: 'click',
                    handler: partnerRegSelectCtrl.clearPhone 
                }
            ];

            appFunc.bindEvents(bindings);
        },
        
        closeEndDate:function(){
        	partnerRegSelectCtrl.clearErrorMsg("date");
            partnerRegSelectCtrl.initEndDate();
            $$(".partnerRegSelect #endDate").html("截止日期");
            $$(".partnerRegSelect #endDate").val("");
            $$(".partnerRegSelect .closeEndDate").hide();
        },
        
        closeBeginDate:function() {
        	partnerRegSelectCtrl.clearErrorMsg("date");
            partnerRegSelectCtrl.initBeginDate();
            $$(".partnerRegSelect #beginDate").html("开始日期");
            $$(".partnerRegSelect #beginDate").val("");
            $$(".partnerRegSelect .closeBeginDate").hide();
        },
		clearPhone:function() {
        	partnerRegSelectCtrl.clearErrorMsg("phoneNo");
        	$$(".partnerRegSelect #phoneNo").val("");
            $$(".partnerRegSelect .clearPhone").hide();
        },
        showClearButton:function() {
        	var value = $$(".partnerRegSelect #phoneNo").val();
        	if(value.length > 0){
        		$$(".partnerRegSelect .clearPhone").show();
        	}else{
        		$$(".partnerRegSelect .clearPhone").hide();
        	}
        },
        getPartnerSumData: function () {
            if(pCode==null || pCode==""){
                xxdApp.addNotification({
                    title: '抱歉',
                    hold: 3000,
                    message: '商户号为空，请刷新页面重试...'
                });
                return;
            }
            req.callGet({
                url: 'activity/partner/getPartnerSumData.do',
                data: {
                    'pCode': pCode
                },
                dataType: 'json',
                timeout:5000,
                indicator:true,
                success: function (data) {
                	try{
	                    if (data.resultCode == 0) {
	                        $(".partnerRegSelect .pName").html(data.pName);
//	                        $(".partnerRegSelect .sumToday").html(data.daypromotion);
//	                        $(".partnerRegSelect .sumTotal").html(data.cumulativepromotion);
	                        $$(".partnerRegSelect .dayEffectiveNumber").html(data.dayEffectiveNumber);
	                        $$(".partnerRegSelect .cumulativeEffectiveNumber").html(data.cumulativeEffectiveNumber);
	                        $$(".partnerRegSelect .dayInvalidNumber").html(data.dayInvalidNumber);
	                        $$(".partnerRegSelect .cumulativeInvalidNumber").html(data.cumulativeInvalidNumber);
	                    } else {
	                        xxdApp.addNotification({
	                            title: '抱歉',
	                            hold: 3000,
	                            message: '获取商户数据失败，请刷新页面'
	                        });
	                    }
                	}catch (e) {
                		console.log(e.message);
                		xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取商户数据失败，请刷新页面'
                        });
        			}
                },
                error: function(xhr, type){
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '查询失败，请稍候重试...'
                    });
                }
            });
        },
        
 		refresh: function () {
        	$$(".partnerRegSelect #refreshSumData .refresh").toggleClass('show');
            partnerRegSelectCtrl.pullToRefresh();
        },
        
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            try{
	            partnerRegSelectCtrl.getPartnerSumData();
	            
	            partnerRegSelectCtrl.selectPartnerList({data: {
	                pCode:pCode,
	                currentPage: 1,
	                pageSize: pageSize,
	                type: 'pull',
	                beginDate: paramBeginDate,
	                endDate: paramEndDate,
	                phoneNo: paramPhone,
	                lowerlevelCode: paramlowerlevelCode
	            }});
	            $$(".partnerRegSelect .currentPage").val(1);
            }catch (e) {
            	console.log(e.message);
        		xxdApp.addNotification({
                    title: '抱歉',
                    hold: 3000,
                    message: '刷新商户数据失败，请稍后重试'
                });
			}finally{
				// 重置加载flag
	            loading = false;
			}
        },

        /**
         * 查询
         */
        queryPartnerListData: function () {
        	try {
	            paramBeginDate = $$(".partnerRegSelect #beginDate").val();
	            paramEndDate = $$(".partnerRegSelect #endDate").val();
	            paramPhone = $$(".partnerRegSelect #phoneNo").val();
	            var storeNames =  $$("#partnerStoresVal").html();
	            paramlowerlevelCode = partnerRegSelectCtrl.getStoreCodes(storeNames);
	            
	            if (new Date(Date.parse(paramBeginDate)) > new Date(Date.parse(paramEndDate))) {
	                return partnerRegSelectCtrl.showInputErrorMsg({inputId: 'date', msg: '开始时间不能大于截止时间'});
	            }
	            if(!paramPhone.match("^[0-9]*$")){
	            	return partnerRegSelectCtrl.showInputErrorMsg({inputId: 'phoneNo', msg: '请输入正确的手机号后四位'});
	            }
	            
	            partnerRegSelectCtrl.selectPartnerList({data: {
	                pCode:pCode,
	                currentPage: 1,
	                pageSize: pageSize,
	                type: 'pull',
	                beginDate: paramBeginDate,
	                endDate: paramEndDate,
	                phoneNo: paramPhone,
	                lowerlevelCode: paramlowerlevelCode
	            }});
	            $$(".partnerRegSelect .currentPage").val(1);
	            // 重置加载flag
	            loading = false;
			} catch (e) {
				console.log(e.message);
				xxdApp.addNotification({
                    title: '抱歉',
                    hold: 3000,
                    message: '获取数据失败，请稍后重试'
                });
			}
        },

        /**
         * 无限滚动
         */
        infiniteScroll: function () {
        	try {
	            // 如果正在加载，则退出
	            if (loading) {
	                return;
	            }
	            //==========================切记此处不能删除============================
	            loading = true;
	            //==========================切记此处不能删除============================
	            var totalPage = $$(".partnerRegSelect .totalPage").val();
	            var currentPage = $$(".partnerRegSelect .currentPage").val();
	            currentPage = currentPage ? currentPage : 1;
	            currentPage = parseInt(currentPage) + 1;
	            if (currentPage > totalPage) {
	            	console.log("in method infiniteScroll...");
	                //没有更多数据
	                xxdApp.addNotification({
	                    title: '提示',
	                    hold: 3000,
	                    message: '数据已全部加载完毕'
	                });
	                loading = true;
	                return;
	            }
	            
	            partnerRegSelectCtrl.selectPartnerList({data: {
	                pCode:pCode,
	                currentPage: currentPage,
	                pageSize: pageSize,
	                type: 'push',
	                beginDate: paramBeginDate,
	                endDate: paramEndDate,
	                phoneNo: paramPhone,
	                lowerlevelCode: paramlowerlevelCode
	            }});
	
	            $$(".partnerRegSelect .currentPage").val(currentPage);
	            //==========================切记此处不能删除============================
	            setTimeout(function () {
	                loading = false;
	            }, 1500);
	            //==========================切记此处不能删除============================
	        	
			} catch (e) {
				console.log(e.message);
				xxdApp.addNotification({
                    title: '抱歉',
                    hold: 3000,
                    message: '加载数据失败，请稍后重试'
                });
			}
        },

        selectPartnerList: function (options) {
            req.callGet({
                url: 'activity/partner/getPartnerListData.do',
                data: options.data,
                dataType: 'json',
                indicator:true,
                timeout:15000,
                success: function (data) {
                	try {
	                     if (data.resultCode != 0) {
	                        xxdApp.addNotification({
	                            title: '提示',
	                            hold: 3000,
	                            message: '加载数据出错，请重新操作'
	                        });
	                        return;
	                    }
	                    var totalPage = Math.ceil(parseInt(data.resultAmount)/parseInt(pageSize));
	                    $$(".partnerRegSelect .totalPage").val(totalPage);
	                    
	                    if (options.data.currentPage > totalPage && totalPage != 0) {
	                    	console.log("in method selectPartnerList...");
	                        xxdApp.addNotification({
	                            title: '提示',
	                            hold: 3000,
	                            message: '数据已全部加载完成'
	                        });
	                        return;
	                    }
	                    
	                    var sumNumber = data.sumNumber;
//	                    if(sumNumber <= 1){
	                    if(options.data.phoneNo!=null && options.data.phoneNo!=""){
	                    	$$(".partnerRegSelect .queryDataSum").hide();
	                    }else{
		                    var extensionNumber = data.extensionNumber;
		                    var invalidNumber = data.InvalidNumber;
		                    $$(".partnerRegSelect .extensionNumber").html(extensionNumber);
		                    $$(".partnerRegSelect .invalidNumber").html(invalidNumber);
		                    $$(".partnerRegSelect .queryDataSum").show();
	                    }
	                    
	                    //查具体用户
	                    if(options.data.phoneNo!=null && options.data.phoneNo!=""){
	                        
	                        var resultTitle =   '<div style="width: 35%;">'+
			                                        '<div class="item-title">姓名&nbsp;|&nbsp;手机号</div>'+
			                                    '</div>'+
			                                    '<div style="width: 30%;">'+
			                                        '<div class="item-title">时间</div>'+
			                                    '</div>'+
			                                    '<div  style="width: 17.5%;">'+
			                                        '<div class="item-title" style="text-align:center;">状态</div>'+
			                                    '</div>'+
			                                    '<div style="width: 17.5%;">'+
			                                        '<div class="item-title" style="text-align:center;">兑奖</div>'+
			                                    '</div>';
	                                            
	                        $$(".partnerRegSelect #resultTitle").html(resultTitle);                    
	                        var userInfos = data.userInfo;
	                        if (userInfos.length == 0) {
	                            partnerRegSelectView.noResult();
	                            return;
	                        }
	                        
	                        var userList = [];
	                        var user;
	                        for (var i = 0; i < userInfos.length; i++) {
	                            user = userInfos[i];
	                            userList.push({
	                                username: user.username == "null" ? "" : user.username,
	                                phone: user.phone,
	                                regTimeyyyyMMdd:DateHandle.formatDate('yyyy-MM-dd',user.regTime),
	                                regTimehhmmss: DateHandle.formatDate('HH:mm:ss',user.regTime),
	                                isRealName: user.renameStatus == 1 ? true : false,
	                                isExchange: user.exchangeStatus == 1 ? true : false,
	                                showStoreName: showStoreName,
	                                storeName: user.storeName,
	                                storeCode: user.storeCode
	                            });
	                        }
	                        partnerRegSelectView.showUserList({userList: userList, type: options.data.type, callBack: function () {
	                            var bing = [
	                                {
	                                    element: '.partnerRegSelect .partnerSelectList .goToStorePage',
	                                    event: 'click',
	                                    handler: partnerRegSelectCtrl.goToStorePage
	                                }
	                            ];
	    
	                            appFunc.bindEvents(bing);
	                        }});
	                     
	                    //查分店列表    
	                    }else{
	                        var storeInfo = data.storeInfo;
	                        
	                        var resultTitle =   '<div style="width: 15%;">'+
			                                        '<div class="item-title" style="text-align:center;">序号</div>'+
			                                    '</div>'+
			                                    '<div style="width: 45%;">'+
			                                        '<div class="item-title" style="text-align:center;">分店名</div>'+
			                                    '</div>'+
			                                    '<div style="width: 20%;">'+
			                                        '<div class="item-title" style="text-align:center;">有效(人)</div>'+
			                                    '</div>'+
			                                    '<div style="width: 20%;">'+
			                                        '<div class="item-title" style="text-align:center;">无效(人)</div>'+
			                                    '</div>';
	                        $$(".partnerRegSelect #resultTitle").html(resultTitle); 
	                        
	                        var storeDetails = storeInfo.storedetail;
	                        if (storeDetails.length == 0) {
	                            partnerRegSelectView.noResult();
	                            return;
	                        }
	                        
	                        var storeDetailList = [];
	                        var storeDetail;
	                        for (var i = 0; i < storeDetails.length; i++) {
	                            storeDetail = storeDetails[i];
	                            var index = (parseInt(options.data.currentPage)-1)*parseInt(options.data.pageSize)+i+1;
	                            storeDetailList.push({
	                                index: index,
	                                storeName: storeDetail.storeName,
	                                extensionNumber: appFunc.fmoney(storeDetail.extensionNumber),
	                                invalidNumber: appFunc.fmoney(storeDetail.InvalidNumber)
	                            });
	                        }
	    
	                        partnerRegSelectView.showStoreList({storeDetailList: storeDetailList, type: options.data.type});
	                        
	                    }
                	} catch (e) {
        				console.log(e.message);
        				xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '查询失败，请稍候重试...'
                        });
        			}
                },
                error: function(xhr, type){
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '查询失败，请稍候重试...'
                    });
                }
            });
            
            setTimeout(function(){
                if (options.data.type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
           },5000);
        },
        
        //获取分店列表
        getStoreList : function(){
            var listContent ='';
             req.callGet({
                 type:'get',
                 url:'activity/partner/getStoreList.do',
                 data: {
                     'pCode':pCode
                 },
                 dataType: 'json',
                 timeout:8000,
                 indicator:true,
                 success:function(data){
                	 try {
	                    if(data.resultCode == 0){
	                        stores = data.storeList;
	                        if(stores != null){
	                            if(stores.length == 0) {
	                                listContent += '<span>暂无分店</span>';
	                            } else {
	                                for (var i=0; i < stores.length; i++) {
	                                  var store = stores[i];
	                                  listContent += '<option value="'+ store.code +'">'+ store.name +'</option>'; 
	                                }
	                        }}
	                        $('#partnerStores').html(listContent);
	                    }else{
	                        xxdApp.addNotification({
	                            title: '抱歉',
	                            hold: 3000,
	                            message: '获取分店列表失败，请稍候重试...'
	                        });
	                    }
                	 } catch (e) {
         				console.log(e.message);
         				xxdApp.addNotification({
                             title: '抱歉',
                             hold: 3000,
                             message: '加载数据失败，请稍候重试...'
                         });
         			}
                },
                error: function(xhr, type){
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '获取分店列表失败，请稍候重试...'
                    });
                }
                
            });
        },
        //点击用户列表中的分店--> 跳转到相应的分店页面
        goToStorePage : function (){
            var storeCode = $$(this).attr("data-storeCode");
            var phoneNo = $$(this).attr("data-phoneNo");
            // window.location.href ='static/html/activity/storeRegSelect.html?pCode='+storeCode+'&phoneNo='+phoneNo;
            GS.loadPage('activity/storeRegSelect.html?pCode='+storeCode+'&phoneNo='+phoneNo);
        },
        
        getStoreCodes: function (storeNames) {
        	showStoreName = true;
        	try{
	            var storeArray=[];
	            var storeCodes = "";
	            if(storeNames == null || storeNames =="" || storeNames.indexOf("全部分店") != -1){
	                return null;
	            }
	            if(stores != null){
	                if(stores.length == 0) {
	                    return null;
	                } else {
	                	
	                    var names = storeNames.split(",");
	                    
	                    if(names.length == 1){
	                    	showStoreName = false;
	                    }
	                    
	                    for (var i = 0; i < names.length; i++) {
	                        var name = $.trim(names[i]);
	                        if(name != null && name != ""){
	                           for (var j = 0; j < stores.length; j++) {
	                               if(name == stores[j].name){
	                                   storeCodes = storeCodes + stores[j].code + ",";
	                                   break;
	                               }
	                           } 
	                        }
	                    };
	                }
	            }
	            return storeCodes;
	        } catch (e) {
				console.log(e.message);
				xxdApp.addNotification({
	                title: '抱歉',
	                hold: 3000,
	                message: '获取分店数据失败，请稍候重试...'
	            });
			}
        },
        showInputErrorMsg:function(options) {
		    $$(".partnerRegSelect #"+ options.inputId +"ErrorMsg").html(options.msg);
		    $$(".partnerRegSelect #"+ options.inputId +"Error").show();
		    return false;
		},
		clearErrorMsg:function(inputId) {
			if(inputId == null ||inputId == ""){
				inputId = $$(this).attr("id");
			}
		    $$(".partnerRegSelect #"+ inputId +"Error").hide();
		},
		moreInfo:function() {
			$$(".partnerRegSelect .moreInfo").toggleClass('show');
		    $$(".partnerRegSelect #invalidData").toggleClass('displayNone');
		}
    };
    return partnerRegSelectCtrl;
});