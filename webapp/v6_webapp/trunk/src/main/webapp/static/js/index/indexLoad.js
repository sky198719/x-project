define(['js/user/login', 'js/common/menu'], function (login, menu) {
    var indexLoad = {
        init: function () {

        },
        loadHome: function () {
            if ($$(".view-main").length == 0) {
                GS.loadHtml({
                    url:'index/home.html',
                    callBack:function(data){
                        var viewMainHmtl = '<div class="view view-main tab">';
                        viewMainHmtl += data + '</div>';
                        var appViews = $$('.views');
                        appViews.append(viewMainHmtl);
                        window.mainView = xxdApp.addView('.view-main', {
                            dynamicNavbar: true
                        });
                        homeController.init();
                        $$(".load-view").removeClass("active");
                        $$(".view-main").addClass("active");
                    }
                });
            }

            indexLoad.loadComponent();
        },

        toHome: function () {
            GS.reloadPage("index/home.html");
        },
        loadComponent: function () {
            if($$(".login-screen").length == 0) {
                req.callGet({
                    url: GC.getHtmlPath() + 'index/component.html?' + GC.getVersion(),
                    dataType: 'text',
                    success: function (result) {

                        $$("body").append(result);
                        var spinnerAnimate = $$(".spinnerAnimate").html();
                        $$(".login-screen").html(spinnerAnimate);
                        $$(".panel-right").html(spinnerAnimate);
                        indexLoad.eventBind();
                    }
                });
            }
        },

        eventBind: function () {
            var bindings = [
                {
                    element: '.login-screen',
                    event: 'opened',
                    handler: indexLoad.initLoginScreen
                },
                {
                    element: '.panel-right',
                    event: 'opened',
                    handler: indexLoad.initLeftPanel
                },
                {
                    element: '#index_tradeRequestList',
                    event: 'click',
                    handler: indexLoad.toTradeRequestList
                },
                {
                    element: '#index_borrowList',
                    event: 'click',
                    handler: indexLoad.browseBorrowList
                },
                {
                    element: '#index_planList',
                    event: 'click',
                    handler: indexLoad.toPlanList
                }
            ];
            appFunc.bindEvents(bindings);
        },


        initLoginScreen: function () {
            login.initLogin({callBack: function (data) {
                var loginScreen = $$(".login-screen");
                loginScreen.html(data);
                loginScreen.attr("isLoad", "true");
                login.bindEvents();
            }})
        },

        initLeftPanel: function () {
            if (!appFunc.isLogin()) {
                xxdApp.closePanel();
                xxdApp.loginScreen();
               // xxdLog.debug("indexLoad.initLeftPanel:open left Panel isNoLogin ");
                return;
            }
            var leftPanel = $$(".panel-right");
            menu.loadMenu({callBack: function (data) {
                leftPanel.html(data);
                leftPanel.attr("isLoad", "true");
                menu.eventBind();
                appFunc.setWeixinUser();
                menu.setMenuUserInfo();
            }});
        },
        /**
         * 跳转到债权转让列表
         */
        toTradeRequestList: function () {
            xxdApp.closeModal('.popover');
            GS.loadPage("trade/tradeRequestListV2.html");
        },
        /**
         * 跳转到散标列表
         */
        browseBorrowList: function () {
            xxdApp.closeModal('.popover');
            GS.loadPage("borrow/borrowListV2.html");
        },
        /**
         * 跳转到新元宝列表
         */
        toPlanList: function () {
            xxdApp.closeModal('.popover');
            //GS.loadPage("plan/planSwiper.html?path=plan");
            GS.loadPage("popular/financesList.html");
        }
    };
    return indexLoad;
});
