define([], function () {
    var state = "";
    var gameLandCtrl = {
        init: function (event) {
            xxdApp.alert("~感谢您参与此次活动，活动已经结束，敬请关注我们下次的活动~");
        	//gameLandCtrl.bindEvents();
        	mainView.hideNavbar();
			$$("#gameLand").css("padding-top", "0px");
            $$("title").html("海岛大作战");
            state = appFunc.getEventDetailPageQuery(event).state;
            state = (state == undefined || state == null) ? "" : state ;
        },
		bindEvents: function(){
           var bindings = [
                {
                    element: '#gameLand .playGame',
                    event: 'click',
                    handler: gameLandCtrl.playGame
                },
                {
                    element: '#gameLand .goExchange',
                    event: 'click',
                    handler: gameLandCtrl.goExchange
                }
            ];
            appFunc.bindEvents(bindings);
        },
        playGame: function () {
        	//try {XXD_TRACK._trackEvent({category:"webapp_game",action:"begin_game_index",label:"开始游戏",value:"",custval:""});}catch(e){}
        	if("" == state){
        		window.location.href = "webchat/intoHitplane.do";
        	}else{
        		window.location.href = "webchat/intoHitplane.do?state=" + state;
        	}
            
        },
        goExchange: function () {
        	//try {XXD_TRACK._trackEvent({category:"webapp_game",action:"go_exchange_gold_index",label:"兑换金币",value:"",custval:""});}catch(e){}
            GS.loadPage('gameExchange/beforeExchange.html');
        }
    };

    return gameLandCtrl;
});