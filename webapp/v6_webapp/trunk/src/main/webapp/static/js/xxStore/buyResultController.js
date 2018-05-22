define([], function () {
    var buyResultCtrl = {
        init: function (event) {
        	buyResultCtrl.bindEvents();
        	if (!appFunc.isLogin()) {
                GS.loadPage('activityRegAndLogin/register01.html');
                return;
            }
            
            var goodsTitle = appFunc.getEventDetailPageQuery(event).goodsTitle;
            goodsTitle = goodsTitle == undefined ? "" : goodsTitle ;
            $$("#buyResult #goodsTitle").html(goodsTitle);
            
        	buyResultCtrl.getMyCoins();
        },
		bindEvents: function(){
           var bindings = [
                {
                    element: '#buyResult #getMoreCoin',
                    event: 'click',
                    handler: buyResultCtrl.getMoreCoin
                },
                {
                    element: '#buyResult #goXXStore',
                    event: 'click',
                    handler: buyResultCtrl.goXXStore
                },
                {
                    element: '#buyResult #goHome',
                    event: 'click',
                    handler: buyResultCtrl.goHome
                }
            ];
            appFunc.bindEvents(bindings);
        },
        getMoreCoin: function () {
            window.location.href = "webchat/intoHitplane.do";
        },
        goXXStore: function () {
            GS.loadPage('xxStore/goodsList.html');
        },
        goHome: function () {
            GS.loadPage('index/home.html');
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
                		$$("#buyResult #myCoins").html(data.myCoins);
                	}else{
                		xxdApp.alert(data.resultDesc,"提示");
                	}
                }
            });
        }
    };

    return buyResultCtrl;
});