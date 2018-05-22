(function () {
    require.config({
        baseUrl: "static",
        paths: {
            text: 'lib/text',
            Framework7: 'lib/framework7/framework7.min',
            zepto: 'lib/zepto/zepto.min',
            zeptoMd5: 'lib/zepto/zepto.md5',
            'chart': 'lib/chart/Chart.min',
            'chartDoughnut': 'lib/chart/Chart.Doughnut',
            'GC': 'js/common/globalConfig',
            'GS': 'js/common/globalService',
            'backRouter': 'js/utils/backRouter',
            'share': 'js/utils/share',
            'track': 'js/utils/track',
            'analytics_plugin':'js/utils/analytics_plugin'
        },
        shim: {
            "Framework7": {
                exports: 'Framework7'
            },
            zepto: {
                exports: '$'
            },

            "zeptoMd5": ["zepto"],
            "chartDoughnut": ["chart"]
        },
        urlArgs: 'v=20180108'
    });

    require(['Framework7', 'zepto', 'js/router', 'js/utils/xhr', 'GC', 'GS', 'js/utils/appFunc', 'js/plugins/upscroller', 'js/utils/xxdLog', 'share', 'track','analytics_plugin'], function (Framework7, Zepto, router, xhr, GC, GS, appFunc, upscroller, xxdLog, share, track,analytics_plugin) {
        var app = {
            initialize: function () {
                window.$ = Zepto;
                if(!appFunc.isFreeLogin() && appFunc.isWeixin()){
                    var isShowLogin = appFunc.GetQueryString("isShowLogin");
                    var href = 'weixin/auth.jsp';
                    var referer = window.location.href
                    var param = [];
                    if(referer != undefined && "" != referer) {
                        param.push("referer="+encodeURIComponent(referer));
                    }
                    if(isShowLogin != undefined) {
                        param.push("isShowLogin="+isShowLogin);
                    }
                    if(param.length > 0) {
                        href += "?"+param.join("&");
                    }
                    window.location.href = href;
                } else {
                    this.bindEvents();
                }

            },
            bindEvents: function () {
                if (appFunc.isPhonegap()) {
                    document.addEventListener('deviceready', this.onDeviceReady, false);
                } else {
                    window.onload = this.onDeviceReady();
                }
            },
            onDeviceReady: function () {
                app.receivedEvent('deviceready');
            },
            receivedEvent: function (event) {
                switch (event) {
                    case 'deviceready':
                        app.initMainView();
                        break;
                }
            },
            initMainView: function () {
                window.$$ = Dom7;
                window.t7 = Template7;
                window.req = xhr;

                GC.init();
                window.GC = GC;
                GS.init();
                window.GS = GS;

                window.appFunc = appFunc;
                window.xxdLog = xxdLog;
                router.init();


                window.xxdApp = new Framework7({
                    pushState: true,
                    template7Pages: true,
                    animatePages:false,
                    popupCloseByOutside: false,
                    animateNavBackIcon: true,
                    precompileTemplates: true,
                    smartSelectInPopup: true,
                    hideNavbarOnPageScroll: true,  //页面滚动时隐藏导航栏
                    modalTitle: "系统消息",
                    modalButtonOk: "确定",
                    modalButtonCancel: "取消",
                    modalPasswordPlaceholder: "请输入密码",
                    smartSelectPopupCloseText: "关闭",
                    smartSelectBackText: "返回",
                    swipeout: true, //滑动删除
                    swipePanel: "right",
                    swipeBackPage: false,   //滑动返回上一页
                    swipePanelThreshold: 9999,
                    preroute: function (view, options) {
                        var optionsUrl = options.url;
                        var viewUrl = view.url;
                        /*console.log("view.url=" + view.url);
                        console.log("optionsUrl=" + optionsUrl);
                        console.log("     ");*/

                        var flag = optionsUrl != undefined && optionsUrl != '#';
                        if (flag || (flag && viewUrl.indexOf("http") == 0)) {

                            var forbiddenPage = GC.forbiddenPage();
                            for (var i = 0; i < forbiddenPage.length; i++) {
                                if (optionsUrl.indexOf(forbiddenPage[i]) >= 0) {
                                   return false;
                                }
                            }

                            var urls = GC.noLoginPageUrls();
                            for (var i = 0; i < urls.length; i++) {
                                if (optionsUrl.indexOf(urls[i]) >= 0) {
                                    return true;
                                }
                            }
                            if (!appFunc.isLogin()) {
                                xxdApp.alert("请先登录", '抱歉', function () {
                                    var redirectionUrl = optionsUrl.substring(optionsUrl.indexOf(GC.getHtmlPath())+GC.getHtmlPath().length,optionsUrl.length);
                                    $$(".login-screen").prop("redirectionUrl",redirectionUrl);
                                    xxdApp.loginScreen();
                                });
                                return false; //required to prevent default router action
                            }

                        } else if(optionsUrl == undefined && viewUrl.indexOf("http") == 0) {
                             GS.reloadPage("index/home.html");
                        } else {
                            if (GC.Debug()) {
                                xxdLog.debug("===============view==============");
                                xxdLog.debug(view);
                                xxdLog.debug("===============view==============");
                                xxdLog.debug("===============options==============");
                                xxdLog.debug(options);
                                xxdLog.debug("===============options==============");
                                xxdLog.debug("     ");
                            }
                        }
                    }
                });

                window.loadView = xxdApp.addView('.load-view');

                share.init();
                upscroller.init();
                window.XXD_TRACK = track;
            }
        };

        app.initialize();
        return app;
    });
})();