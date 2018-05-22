define([], function () {
    var score = "";
    var exchangeResultCtrl = {
        init: function (event) {
			mainView.hideNavbar();
			$$("#exchangeResult").css("padding-top", "0px");
            $$("title").html("兑换结果");
            exchangeResultCtrl.bindEvents();
            
            score = appFunc.getEventDetailPageQuery(event).score;
            score = score == undefined ? "" : score ;
            
//            if (!appFunc.isLogin()) {
//                GS.loadPage('activityRegAndLogin/register01.html?score=' + score);
//            }else{
//            	if(score == "" || score == null){
//            		$$("#exchangeResult #exchangeStatusIcon").attr("src","static/img/activity/game/fail.png");
//            		$$("#exchangeResult #exchangeStatusText").html("兑换失败");
//            	}else{
            		exchangeResultCtrl.doExchange();
//            	}
//            }
            
        },

        bindEvents: function () {
            var bindings = [
                {
                    element: '#exchangeResult #xxbUseIntroduction',
                    event: 'click',
                    handler: exchangeResultCtrl.openIntroduction
                },
                {
                    element: '#exchangeResult #getMoreCoin',
                    event: 'click',
                    handler: exchangeResultCtrl.getMoreCoin
                },
                {
                    element: '#exchangeResult #goXXStore',
                    event: 'click',
                    handler: exchangeResultCtrl.goXXStore
                }
            ];
            appFunc.bindEvents(bindings);
        },

        openIntroduction: function () {
			  if ($$('.picker-modal.modal-in').length > 0) {
			    xxdApp.closeModal('.picker-modal.modal-in');
			  }
			  xxdApp.pickerModal(
			    '<div class="picker-modal" style="height: 200px;">' +
			      '<div class="toolbar">' +
			        '<div class="toolbar-inner" style="background-color:#C1C1C1">' +
			          '<div class="left"><a href="#" class="close-picker"><img src="static/img/activity/game/close.png" style="padding-top: 4px;width:44px;"/></a></div>' +
			          '<div class="center" style="margin-left:-44px;">如何使用新新币</div>' +
			          '<div class="right"></div>' +
			        '</div>' +
			      '</div>' +
			      '<div class="picker-modal-inner">' +
			        '<div class="content-block" style="color:#8D8D8D;">' +
				          '<p>新新币即在新新贷网站可兑换相应现金的虚拟币。<br>' +
					       '新新币兑换人民币的比例为50:1，即50个新新币可兑换1元人民币。<br>' +
					       '新新币主要在新新商城进行消费。' +
					      '</p>' +
			        '</div>' +
			      '</div>' +
			    '</div>'
			  );
        },
        getMoreCoin: function () {
        	window.location.href = "webchat/intoHitplane.do";
        },
        goXXStore: function () {
            GS.loadPage('xxStore/goodsList.html');
        },
        doExchange: function () {
            req.callJSON({
                url: 'gameBWNH/scoreExchange.do',
                data: {
                    score: score
                },
                async: false,
                dataType: 'json',
                timeout: 30000,
                indicator: true,
                success: function (data) {
                    if (data.resultCode == 0) {
                    	//恭喜，您成功兑换100个新新币
                    	//您今日已累计兑换 500 个新新币（每日限额兑换500个）
						//游戏积分剩余 XXX，请明日再来兑换
                    	var exchangeStatusText = "";
                    	if(data.xxMoney > 0){
	                    	exchangeStatusText = '恭喜，您成功兑换 <span class="f-red-b" id="exchangeCoins">' + data.xxMoney + '</span> 个新新币</span><br>';
                    	}
                    	exchangeStatusText = exchangeStatusText + '<span style="color:#8D8D8D;font-size:15px;">您今日已累计兑换 ' + data.todaySumxxMoney + ' 个新新币<br>（每日限额兑换500个）<br>';
                    	
                		exchangeStatusText = exchangeStatusText + "游戏积分剩余 " + data.remainPoint + "分";
                    	if(data.remainPoint > 0 && data.todaySumxxMoney >= 500){
                    		 exchangeStatusText = exchangeStatusText + "，请明日再来兑换"
                    	}
                    	
                    	exchangeStatusText = exchangeStatusText + "</span>";
                    	
                    	if(data.todaySumxxMoney >= 500){
                    		$$("#exchangeResult #exchangeStatusIcon").attr("src","static/img/activity/game/info.png");
                    	}else{
                    		$$("#exchangeResult #exchangeStatusIcon").attr("src","static/img/activity/game/success.png");
                    	}
                    		
            			$$("#exchangeResult #exchangeStatusText").html(exchangeStatusText);
//            			$$("#exchangeResult #exchangeCoins").html(data.xxMoney);
                    } else if(data.resultCode == -2){
                    	GS.loadPage('activityRegAndLogin/register01.html?score=' + score);
                    }else{
                        $$("#exchangeResult #exchangeStatusIcon").attr("src","static/img/activity/game/fail.png");
//            			$$("#exchangeResult #exchangeStatusText").html("抱歉，兑换失败<br>" + data.resultDesc);
            			$$("#exchangeResult #exchangeStatusText").html("抱歉，" + data.resultDesc);
                    }
                },
                error: function(xhr, type){
                        $$("#exchangeResult #exchangeStatusIcon").attr("src","static/img/activity/game/fail.png");
            			$$("#exchangeResult #exchangeStatusText").html('<span style="font-size:15px;">抱歉，兑换失败</span>');
                }
            });
        }
    };
    return exchangeResultCtrl;
});
