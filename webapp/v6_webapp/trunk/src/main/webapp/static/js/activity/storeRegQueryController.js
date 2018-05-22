define(['js/activity/storeRegQueryView', 'js/utils/date'], function (storeRegQueryView, DateHandle) {
    var pCode = '';
    var phoneNo = '';
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var monthNames = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月' , '九月' , '十月', '十一月', '十二月'];
    var dayNames = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];    
    var calendarend = xxdApp.calendar({});
    var calendarBegin = xxdApp.calendar({});
    var paramPhone = "";
    var paramBeginDate = "";
    var paramEndDate = "";
    var storeRegQueryCtrl = {
        init: function (event) {

            pCode = appFunc.getEventDetailPageQuery(event).pCode;
            pCode = pCode == undefined ? "" : pCode ;
            phoneNo = appFunc.getEventDetailPageQuery(event).phoneNo;
            phoneNo = phoneNo == undefined ? "" : phoneNo ;

            $$(".storeRegQuery").attr("data-type", "1");

            storeRegQueryCtrl.initBeginDate();
            
            storeRegQueryCtrl.initEndDate();
            
            storeRegQueryCtrl.getStoreData(pCode);
            
            if(phoneNo!=null && ""!=phoneNo){
                $$(".storeRegQuery .phone").val(phoneNo);
                storeRegQueryCtrl.queryData();
            }

            storeRegQueryCtrl.eventBind();
        },
        
        initBeginDate:function(){
           calendarBegin.destroy();
           calendarBegin = xxdApp.calendar({
                input: '.storeRegQuery .calendar-begin',
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
	                    $$(".storeRegQuery .calendar-begin").html(beginDate);
	                    $$(".storeRegQuery .closeBeginDate").show();
                	}
                },
                onOpen: function(){
                	storeRegQueryCtrl.clearErrorMsg("date");
                }
            });

        },
        
        initEndDate:function(){
            calendarend.destroy();
            calendarend = xxdApp.calendar({
                input: '.storeRegQuery .calendar-end',
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
	                    $$(".storeRegQuery .calendar-end").html(endDate);
	                    $$(".storeRegQuery .closeEndDate").show();
                	}
                },
                onOpen: function(){
                	storeRegQueryCtrl.clearErrorMsg("date");
                }
            });
            var today = DateHandle.formatDate('yyyy-MM-dd',new Date());
            $$(".storeRegQuery .calendar-end").html(today);
            $$(".storeRegQuery .calendar-end").val(today);
            $$(".storeRegQuery .closeEndDate").show();
        },
        eventBind: function () {
            var bindings = [
                {
                    element: '.storeRegQuery.pull-to-refresh-content',
                    event: 'refresh',
                    handler: storeRegQueryCtrl.pullToRefresh
                },
                {
                    element: '.storeRegQuery.infinite-scroll',
                    event: 'infinite',
                    handler: storeRegQueryCtrl.infiniteScroll
                },
                {
                    element: '.storeRegQuery .queryData',
                    event: 'click',
                    handler: storeRegQueryCtrl.queryData
                },
                {
                    element: '.storeRegQuery .storeRefresh',
                    event: 'click',
                    handler: storeRegQueryCtrl.refresh
                },
                {
                    element: '.storeRegQuery .closeBeginDate',
                    event: 'click',
                    handler: storeRegQueryCtrl.closeBeginDate
                },
                {
                    element: '.storeRegQuery .closeEndDate',
                    event: 'click',
                    handler: storeRegQueryCtrl.closeEndDate 
                },
                {
                    element: '.storeRegQuery #phoneNo',
                    event: 'keyup',
                    handler: storeRegQueryCtrl.showClearButton 
                },
                {
                    element: '.storeRegQuery .clearPhone',
                    event: 'click',
                    handler: storeRegQueryCtrl.clearPhone 
                },
                {
                    element: '.storeRegQuery .moreInfo',
                    event: 'click',
                    handler: storeRegQueryCtrl.moreInfo 
                },
                {
                    element: '#storeRegQuery #phoneNo',
                    event: 'focus',
                    handler: storeRegQueryCtrl.clearErrorMsg
                }
            ];

            appFunc.bindEvents(bindings);
        },
        closeEndDate:function(){
        	storeRegQueryCtrl.clearErrorMsg("date");
            storeRegQueryCtrl.initEndDate();
            $$(".storeRegQuery .calendar-end").html("截止日期");
            $$(".storeRegQuery .calendar-end").val("");
            $$(".storeRegQuery .closeEndDate").hide();
        },
        
        closeBeginDate:function() {
        	storeRegQueryCtrl.clearErrorMsg("date");
            storeRegQueryCtrl.initBeginDate();
            $$(".storeRegQuery .calendar-begin").html("开始日期");
            $$(".storeRegQuery .calendar-begin").val("");
            $$(".storeRegQuery .closeBeginDate").hide();
        },
        clearPhone:function() {
        	storeRegQueryCtrl.clearErrorMsg("phoneNo");
        	$$(".storeRegQuery #phoneNo").val("");
            $$(".storeRegQuery .clearPhone").hide();
        },
        showClearButton:function() {
        	var value = $$(".storeRegQuery #phoneNo").val();
        	if(value.length > 0){
        		$$(".storeRegQuery .clearPhone").show();
        	}else{
        		$$(".storeRegQuery .clearPhone").hide();
        	}
        },
        getStoreData: function (pCode) {
        	if(pCode == null || pCode == ""){
                xxdApp.addNotification({
                    title: '抱歉',
                    hold: 3000,
                    message: '分店号为空，请刷新页面重试...'
                });
                return;
            }
            req.callGet({
                url: 'activity/store/getSumData.do',
                data: {
                    'pCode': pCode
                },
                dataType: 'json',
                success: function (data) {

                    if (data.resultCode == 0) {
                        $(".storeRegQuery .pName").html(data.pName);
//                        $(".storeRegQuery .sumToday").html(data.daypromotion);
//                        $(".storeRegQuery .sumTotal").html(data.cumulativepromotion);
                        $$(".storeRegQuery .dayEffectiveNumber").html(data.dayEffectiveNumber);
                        $$(".storeRegQuery .cumulativeEffectiveNumber").html(data.cumulativeEffectiveNumber);
                        $$(".storeRegQuery .dayInvalidNumber").html(data.dayInvalidNumber);
                        $$(".storeRegQuery .cumulativeInvalidNumber").html(data.cumulativeInvalidNumber);
                    } else {
                        xxdApp.addNotification({
                            title: '抱歉',
                            message: '获取门店信息失败，请刷新页面'
                        });
                    }
                }
            });
        },

        refresh: function () {
        	$$(".storeRegQuery .storeRefresh .refresh").toggleClass('show');
            storeRegQueryCtrl.pullToRefresh();
        },
        
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
        	try{
	        	storeRegQueryCtrl.getStoreData(pCode);
//	            storeRegQueryCtrl.queryData();
	        	storeRegQueryCtrl.selectStoreList({data: {
	                pCode: pCode,
	                currentPage: 1,
	                pageSize: pageSize,
	                phoneNo: paramPhone,
	                beginDate: paramBeginDate,
	                endDate: paramEndDate
	            }, type: 'pull'});
	            $$(".storeRegQuery .currentPage").val(1);
            }catch (e) {
            	console.log(e.message);
        		xxdApp.addNotification({
                    title: '抱歉',
                    hold: 3000,
                    message: '刷新数据失败，请稍后重试'
                });
			}finally{
				// 重置加载flag
	            loading = false;
			}
        },

        queryData: function () {
        	
        	paramBeginDate = $$(".storeRegQuery .calendar-begin").val();
            paramEndDate = $$(".storeRegQuery .calendar-end").val();
            if (new Date(Date.parse(paramBeginDate)) > new Date(Date.parse(paramEndDate))) {
                return storeRegQueryCtrl.showInputErrorMsg({inputId: 'date', msg: '开始时间不能大于截止时间'});
            }
            
            paramPhone = $$(".storeRegQuery .phone").val();
            if(!paramPhone.match("^[0-9]*$")){
            	return storeRegQueryCtrl.showInputErrorMsg({inputId: 'phoneNo', msg: '请输入正确的手机号后四位'});
            }
            
            storeRegQueryCtrl.selectStoreList({data: {
                pCode: pCode,
                currentPage: 1,
                pageSize: pageSize,
                phoneNo: paramPhone,
                beginDate: paramBeginDate,
                endDate: paramEndDate
            }, type: 'pull'});
            $$(".storeRegQuery .currentPage").val(1);
            // 重置加载flag
            loading = false;
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
	            var totalPage = $$(".storeRegQuery .totalPage").val();
	            var currentPage = $$(".storeRegQuery .currentPage").val();
	            currentPage = currentPage ? currentPage : 1;
	            currentPage = parseInt(currentPage) + 1;
	            if (currentPage > totalPage) {
	                xxdApp.addNotification({
	                    title: '温馨提示',
	                    hold: 3000,
	                    message: '数据已全部加载完毕'
	                });
	                loading = true;
	                return;
	            }
	            storeRegQueryCtrl.selectStoreList({data: {
	                pCode: pCode,
	                currentPage: currentPage,
	                pageSize: pageSize,
	                phoneNo: paramPhone,
	                beginDate: paramBeginDate,
	                endDate: paramEndDate
	            }, type: 'push'});
	
	            $$(".storeRegQuery .currentPage").val(currentPage);
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

        selectStoreList: function (options) {
        	var params = options.data;
            req.callGet({
                url: 'activity/store/getStoreListData.do',
                data: params,
                dataType: 'json',
                indicator:true,
                timeout:15000,
                success: function (data) {
                    if (data.resultCode != 0) {
                        xxdApp.addNotification({
                            title: '提示',
                            hold: 3000,
                            message: '加载数据出错，请稍后重试'
                        });
                        return;
                    }

                    var sumNumber = data.sumNumber;
                    if(params.phoneNo != null && params.phoneNo != undefined && params.phoneNo != ""){
                    	$$(".storeRegQuery .queryDataSum").hide();
                    }else{
	                    var extensionNumber = data.extensionNumber;
	                    var invalidNumber = data.InvalidNumber;
	                    $$(".storeRegQuery .extensionNumber").html(extensionNumber);
	                    $$(".storeRegQuery .invalidNumber").html(invalidNumber);
	                    $$(".storeRegQuery .queryDataSum").show();
                    }

                    if(sumNumber == 0) {
                        storeRegQueryView.noStoreUser();
                        return;
                    }

                    var tempNum = sumNumber % pageSize;
                    var tempNum1 = parseInt(sumNumber / pageSize);
                    var totalPage = tempNum > 0 ? tempNum1 + 1 : tempNum1;
                    $$(".storeRegQuery .totalPage").val(totalPage);
                    if (data.currentPage > totalPage && totalPage != 0) {
                        //提示标的已经全部显示
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: '数据已全部加载完成'
                        });
                        return;
                    }
                    var userInfo = data.userInfo;
                    if (userInfo.length == 0) {
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: '抱歉，没有加载到数据，请重新操作'
                        });
                        storeRegQueryView.noStoreUser();
                        return;
                    }

                    var userList = [];
                    var user;
                    for (var i = 0; i < userInfo.length; i++) {
                        user = userInfo[i];
                        userList.push({
                            username: user.username == "null" ? "" : user.username,
                            phone: user.phone,
                            regTimeyyyyMMdd: DateHandle.formatDate('yyyy-MM-dd',user.regTime),
                            regTimehhmmss: DateHandle.formatDate('HH:mm:ss',user.regTime),
                            isRealName: user.renameStatus == 1 ? true : false,
                            isExchange: user.exchangeStatus == 1 ? true : false
                        });
                    }
                    storeRegQueryView.showStoreUser({userList: userList, type: options.type, callBack: function () {
                        var bing = [
                            {
                                element: '.storeRegQuery .storeUserList .exchangeEnsure',
                                event: 'click',
                                handler: storeRegQueryCtrl.exchangeEnsure
                            }
                        ];

                        appFunc.bindEvents(bing);
                    }});
                }
            });

            if (options.type == 'pull') {
                setTimeout(function () {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }, 5000);
            }
        },

        exchangeEnsure: function () {
            var phone = $$(this).attr("data-phone");
            if (phone == undefined || '' == phone) {
                xxdApp.alert("抱歉，未获取到手机号码，请刷新页面重试", "提示");
                return false;
            }
            var username = $$(this).attr("data-username");
            xxdApp.confirm('<h5 class="text-left">姓&nbsp;&nbsp;&nbsp;名：' + username + '</h5><h5 class="text-left">手机号：<span class="font-red font18">' + phone + '</span></h5>', '<h4>确认兑换？</h4>', function () {
                req.callPost({
                    url: 'activity/store/exchange.do',
                    data: {
                        'pCode': pCode,
                        'phoneNo': phone
                    },
                    dataType: 'json',
                    timeout: 15000,
                    preloaderTitle: '请稍等，正在努力兑换...',
                    success: function (data) {
                        if (data.resultCode == 0) {
                            xxdApp.alert('兑换成功', '提示');
                        } else {
                            xxdApp.alert('兑换失败，请稍后重试...', '提示');
                        }
                        storeRegQueryCtrl.pullToRefresh();
                    }
                });
            });
        },
        showInputErrorMsg:function(options) {
		    $$(".storeRegQuery #"+ options.inputId +"ErrorMsg").html(options.msg);
		    $$(".storeRegQuery #"+ options.inputId +"Error").show();
		    return false;
		},
		clearErrorMsg:function(inputId) {
			if(inputId == null ||inputId == ""){
				inputId = $$(this).attr("id");
			}
		    $$(".storeRegQuery #"+ inputId +"Error").hide();
		},
		moreInfo:function() {
		    $$(".storeRegQuery .moreInfo").toggleClass('show');
		    $$(".storeRegQuery #invalidData").toggleClass('displayNone');
		}
    };
    return storeRegQueryCtrl;
});