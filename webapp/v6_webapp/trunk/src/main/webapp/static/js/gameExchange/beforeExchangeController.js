define([], function () {
    var score = "";
    var beforeExchangeCtrl = {
        init: function (event) {
			mainView.hideNavbar();
			$$("#beforeExchange").css("padding-top", "0px");
            $$("title").html("兑换中");
            
            score = appFunc.getEventDetailPageQuery(event).score;
            score = score == undefined ? "" : score ;
            
            if (!appFunc.isLogin()) {
                GS.loadPage('activityRegAndLogin/register01.html?score=' + score);
            }else{
        		GS.loadPage('gameExchange/exchangeResult.html?score=' + score);
            }
            
        }
    };
    return beforeExchangeCtrl;
});
