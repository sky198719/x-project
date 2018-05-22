define([], function () {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var goodsInfo = {};
    var goodsId = "";
    var goodsDetailCtrl = {
        init: function (event) {
        	mainView.showNavbar();
        	if (!appFunc.isLogin()) {
                GS.loadPage('activityRegAndLogin/register01.html');
                return;
            }
        	goodsDetailCtrl.bindEvents();
        	goodsId = appFunc.getEventDetailPageQuery(event).goodsId;
            goodsId = goodsId == undefined ? "" : goodsId ;
        	goodsDetailCtrl.getGoodsDetail();
        	
        },
		bindEvents: function(){
           var bindings = [
                {
                     element: '#goodsDetail #minusBtn',
                     event: 'click',
                     handler: goodsDetailCtrl.doMinus
                },
                {
                     element: '#goodsDetail #addBtn',
                     event: 'click',
                     handler: goodsDetailCtrl.doAdd
                },
                {
                     element: '#goodsDetail #goBuy',
                     event: 'click',
                     handler: goodsDetailCtrl.goBuy
                }
            ];
            appFunc.bindEvents(bindings);
        },
        getGoodsDetail: function () {
            req.callJSON({
                url:"xxStore/getGoodsDetail.do",
                data:{
                    goodsId: goodsId
                },
                indicator:true,
                timeout:15000,
                success: function(data){
                	if(data.resultCode == 200){
                		var good = data.data;
                		goodsInfo = good;
                		var imagePreUrl = data.imagePreUrl;
                		$$("#goodsDetail #goodsImg").attr("src","static/img/activity/game/awardPic/" + good.goodsNum + "_D.jpg");
                		$$("#goodsDetail #goodsTitle").html(good.goodsName);
                		$$("#goodsDetail #goodsPrice").html(good.xinxinCoinTotalPay);
                		$$("#goodsDetail #goodsRemark").html(good.remark);
                		$$("#goodsDetail #totalCost").html(good.xinxinCoinTotalPay);
                	}else{
                		xxdApp.addNotification({
                            title: '温馨提示',
                            hold:3000,
                            message: '抱歉，获取礼品详情失败，请稍后重试'
                        });
                	}
                }
            });
        },
		goBuy: function () {
			var buyAmount = $$("#goodsDetail #buyAmount").val();
			req.callJSON({
                url:"xxStore/getGoodsDetail.do",
                data:{
                    goodsId: goodsId
                },
                indicator:true,
                timeout:15000,
                success: function(data){
                	if(data.resultCode == 200){
                		var good = data.data;
                		if( buyAmount > parseFloat(good.count) ){
                			xxdApp.alert("礼品库存不足","提示");
                		}else{
                			goodsDetailCtrl.doBuy(buyAmount);
                		}
                	}else{
                		xxdApp.alert("获取礼品库存失败，请稍后重试","提示");
                	}
                }
            });
        },
		doBuy: function (buyAmount) {
            req.callJSON({
                url:"xxStore/realGoodsOrder.do",
                data:{
                    goodsId: goodsInfo.id,
                    payType: "0",
                    buyAmount: buyAmount
                },
                indicator:true,
                timeout:15000,
                success: function(data){
                	if(data.resultCode == 0){
                		GS.loadPage('xxStore/buyResult.html?goodsTitle=' + goodsInfo.goodsName);
                	}else if(data.resultCode == -2){
					    xxdApp.modal({
			                title: '提示',
			                afterText: '您的新新币不足',
			                buttons: [
						      {
						        text: '赚取更多新新币',
						        onClick: function() {
						          	window.location.href = "weixin/game.htm";
						        }
						      },
						      {
						        text: '知道了',
						        onClick: function() {
						        }
						      }
							]
						});
                	}else{
                		xxdApp.alert(data.resultDesc,"提示");
//                		xxdApp.modal({
//			                title: '提示',
//			                afterText: data.resultDesc,
//			                buttons: [
//						      {
//						        text: '重新兑换',
//						        onClick: function() {
//						          	goodsDetailCtrl.goBuy();
//						        }
//						      },
//						      {
//						        text: '取消',
//						        onClick: function() {
//						        }
//						      }
//							]
//						});
                	}
                }
            });
        },
		doMinus: function () {
			var buyAmount = $$("#goodsDetail #buyAmount").val();
			buyAmount = parseFloat(buyAmount) - 1 ;
			if(buyAmount <= 0){
				xxdApp.alert("至少兑换1件礼品","提示");
				return;
			}
			$$("#goodsDetail #buyAmount").val(buyAmount);
			$$("#goodsDetail #totalCost").html(parseFloat(buyAmount) * parseFloat(goodsInfo.xinxinCoinTotalPay));
        },
		doAdd: function () {
			var buyAmount = $$("#goodsDetail #buyAmount").val();
			buyAmount = parseFloat(buyAmount) + 1 ;
			if(buyAmount > goodsInfo.count){
				xxdApp.alert("购买数量超出库存数量","提示");
				return;
			}
			if( parseFloat(goodsInfo.limitCount) != 0 ){
				if(buyAmount > goodsInfo.limitCount){
					xxdApp.alert("已达最大购买数量","提示");
					return;
				}
			}
			$$("#goodsDetail #buyAmount").val(buyAmount);
			$$("#goodsDetail #totalCost").html(parseFloat(buyAmount) * parseFloat(goodsInfo.xinxinCoinTotalPay));
        }
    };

    return goodsDetailCtrl;
});