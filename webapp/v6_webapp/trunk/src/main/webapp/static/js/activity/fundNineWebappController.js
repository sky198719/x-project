define(function () {
    var fundNineCtrl = {
        init: function () {
            var bindings = [
                {
                    element: '.fundNineWebapp .btn',
                    event: 'click',
                    handler: fundNineCtrl.goFund
                },
                {
                    element: '.fundNineWebappback',
                    event: 'click',
                    handler: fundNineCtrl.fundNineWebappback

                }
            ];
            appFunc.bindEvents(bindings);

        },
        fundNineWebappback:function(){
            GS.loadPage("index/home.html");
        },
        goFund:function(){
            try {
                //XXD_TRACK._trackEvent({category:"webapp_fundNineWebapp",action:"activity_buy_once",label:"立即抢购",value:"",custval:""});
            }catch(e){}
            GS.loadPage('fund/fundUnInvested.html?path=fund');
        }

    };
    return fundNineCtrl;
});
