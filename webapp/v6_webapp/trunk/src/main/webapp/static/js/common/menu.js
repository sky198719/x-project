define(['js/utils/date','js/utils/animateNumber','js/common/productUtil'],function (DateHandle,animateNumber,productUtil) {
    var menu = {
        loadMenu: function (options) {
           var isLoad = $$(".panel-right").attr("isLoad");

           if (isLoad == "true") {
	           menu.checkNewAnnounce();
               appFunc.setWeixinUser();
               menu.setMenuUserInfo();
               return;
           }
           GS.loadHtml({
               url:"common/leftmenu.html",
               callBack:function(data){
                   options.callBack(data);
                   menu.checkNewAnnounce();
                   if(productUtil.stepUpwardShowOrNot()){
			   			$$("#menu_stepUpward").show();
		       	   }
               }
           });
        },

        eventBind:function(){
            var bindings = [
                    {
                        element: '#menu_reg',
                        event: 'click',
                        handler: menu.toReg
                    },
                    {
                        element: '#menu_login',
                        event: 'click',
                        handler: menu.toLogin
                    },
                    {
                        element: '#menu_home',
                        event: 'click',
                        handler: menu.toHome
                    },
                    {
                        element:'#menu_FinancesList',
                        event:'click',
                        handler:menu.toFinancesList
                    },
                    {
                        element:'#menu_sevenDays',
                        event:'click',
                        handler:menu.toSevenDays
                    },
                    {
                        element:'#menu_monthFinance',
                        event:'click',
                        handler:menu.toMonthFinance
                    },
                    {
                        element: '#menu_account',
                        event: 'click',
                        handler: menu.toAccount
                    },
                    {
                        element: '#menu_security',
                        event: 'click',
                        handler: menu.toSecurity
                    },
                    {
                        element: '#menu_borrowList',
                        event: 'click',
                        handler: menu.browseBorrowList
                    },
                    {
                        element: '#menu_tradeRequestList',
                        event: 'click',
                        handler: menu.toTradeRequestList
                    },
                    {
                        element: '#menu_planSwiper',
                        event: 'click',
                        handler: menu.toPlanSwiper
                    },
                    {
                        element: '#menu_stepUpward',
                        event: 'click',
                        handler: menu.toStepUpward
                    },
                    {
                        element: '#menu_yyp',
                        event: 'click',
                        handler: menu.toYypList
                    },
                    {
                        element: '.menu_announceIcon',
                        event: 'click',
                        handler: menu.toAnnounceList
                    },
                    {
                        element:'#menu_xiaofeidai',
                        event:'click',
                        handler:menu.xiaofeidai
                    }
                ];
            appFunc.bindEvents(bindings);
        },
        xiaofeidai:function(){
            req.callJSON({
                url:'properties.do',
                success:function(result) {
                    window.location.href =  result.activity_url + 'consumes';
                }
            });
        },
        toReg:function(){
            xxdApp.closePanel();
            GS.loadPage("user/register.html");
        },
        toLogin: function () {
            xxdApp.closeModal('.popover');
            xxdApp.loginScreen();
        },

        setMenuUserInfo: function () {
            if(!appFunc.isLogin()) {
                $$(".menu-main").css("top","100px");
                $$(".menu_head_nologin").show();
                $$(".menu_head_login").hide();
                $$(".current_money").hide();
                $$("#menu_sevenDays").show();
                $$("#headimg").attr("src", "static/img/xxd/head_logo.png");
                return;
            }
            $$(".menu-main").css("top","145px");

            req.callPost({
                url: 'user/getCurrentUser.do',
                data: {},
                dataType: 'json',
                success: function (result) {
                    if (result.code == '0') {
                        $$("#panelLeft_username").html(result.data.user.userName);
                        var headimg = result.data.front_url + '/' + result.data.user.headImg;
                        $$("#headimg").attr("src", headimg);
                        $$(".menu_head_login").show();
                        $$(".menu_head_nologin").hide();
                        $$(".current_money").css("display","inline-block");

                        var currentDate = DateHandle.parseDate(result.data.currentDate);
                        var hours = currentDate.getHours();
                        var menuGreet = '';
                        if(hours >= 0 && hours <= 6) {
                            menuGreet = '凌晨好';
                        } else if (hours > 6 && hours <= 12) {
                            menuGreet = '上午好';
                        } else if (hours > 12 && hours <= 18) {
                            menuGreet = '下午好';
                        } else if (hours > 18 && hours <= 24) {
                            menuGreet = '晚上好';
                        }
                        $$("#menu_greet").html(menuGreet);
                    }

                }
            });

            //获取用户本月收益
            req.callPost({
                url: 'account/earningsMonth.do',
                data: {},
                dataType: 'json',
                success: function (result) {
                    if(result.code == '-100'){
                        xxdApp.alert(result.msg,'抱歉');
                        return false
                    }
                    if(result.curmonintest != null){
                        animateNumber.animate({
                            element:'#menu_earningsMonth',
                            from:0,
                            to:result.curmonintest,
                            precision:2,
                            intervalNumber:30,
                            steps:50,
                            valueType:'money'
                        });
                    }
                }
            });
        },

        toHome: function () {
            GS.reloadPage("index/home.html");
        },
        toAccount: function () {
            GS.loadPage('account/account.html');
        },
        toSecurity: function () {
            GS.loadPage('security/security.html');
        },
        toFinancesList :function(){
            GS.loadPage('popular/financesList.html');
        },
        toSevenDays:function(){
            GS.loadPage('newHand/sevenDaysDetail.html');
        },
        toMonthFinance:function(){
            GS.loadPage('monthFinance/monthFinanceDetails.html');
        },
        toStepUpward:function(){
            GS.loadPage('stepUpward/stepUpwardDetail.html');
        },
        /**
         * 跳转到散标列表
         */
        browseBorrowList: function () {
            GS.loadPage("borrow/borrowListV2.html");
        },
        /**
         * 跳转到债权转让列表
         */
        toTradeRequestList: function () {
            GS.loadPage("trade/tradeRequestListV2.html");
        },
        /**
         * 跳转到_
         */
        toPlanSwiper: function () {
            GS.loadPage('popular/financesList.html');
        },
        toYypList: function () {
            GS.loadPage('popular/financesList.html');
        },
        /**
         * 跳转公告活动
         */
        toAnnounceList: function () {
        	xxdApp.closePanel();
            GS.loadPage('announce/announceList.html');
        },
        //检查是否有新公告
        checkNewAnnounce:function(){
        	var hasNewAnnounce = false;
        	
        	req.callJSON({
                url: "announce/getList.do",
                data: {
                    "currentPage": 1,
                    "pageSize": 10,
                    "type":"1"
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                	try{
                		if(data.resultCode == 0){
		                    var list = data.data;
                		}
	                    if (list != null && list != "") {
	                    	var hasRead = localStorage.hasRead; 
				        	if(hasRead == undefined || hasRead == null){
				        		hasRead = [];
				        	}else{
				        		hasRead = JSON.parse(localStorage.hasRead)
				        	}
	                        for (var i = 0; i < list.length; i++) {
	                            var al = list[i];
	                            var isNew = true;
	                            for(var j = 0; j < hasRead.length ; j++ ){
	                            	if(hasRead[j] == al.id){
		                            	isNew = false;
		                            	break;
			                        }
	                            }
	                            if(isNew == false){
	                            	continue;
	                            }
	                            hasNewAnnounce = true;
	                            if(hasNewAnnounce == true){
	                            	break;
	                            }
	                        }
	                        
	                        if(hasNewAnnounce){
				        		$$(".menu_announceIcon").attr("src","static/img/xxd/announce_new.png");
				        	}else{
				        		$$(".menu_announceIcon").attr("src","static/img/xxd/announce.png");
				        	}
	                    }
                        
	                  }catch (e) {
	                  	xxdApp.hideIndicator();
                		console.log("获取公告活动失败，请稍后重试...");
        			}
                },
                error: function(xhr, type){
                	console.log("获取公告活动失败,ajax error...");
                }
            });
        }
    };
    return menu;
});