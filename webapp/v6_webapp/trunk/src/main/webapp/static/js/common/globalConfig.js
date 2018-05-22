define(function () {
    var CONFIG = null;
    var globalConfig = {
        init: function () {
            if (!CONFIG) {
                CONFIG = {};
                CONFIG.ver = '20180108';
                CONFIG.html = 'static/html/';
                CONFIG.data = 'static/data/';
                CONFIG.img = 'static/img/';
                CONFIG.isAnimate = true;
                CONFIG.isAndroidAnimate = true;
                CONFIG.isIosAnimate = true;
                CONFIG.debug = false;
                CONFIG.newYear = '201512301400';

                var pattern = req.getUrlParam("p");
                if (pattern == 'debug') {
                    CONFIG.debug = true;
                }

                CONFIG.isUpscroller = false;
                CONFIG.forbiddenPage = [
                    "/activity/xinyedai.html",
                    "consumes"
                ];
                CONFIG.noLoginPage = [
                    "index/home.html",
                    "user/register.html",
                    "personal/emailApproReturn.html",
                    "user/registerStep2.html",
                    "user/registerReturn.html",
                    "trade/tradeRequestList.html",
                    "trade/tradeRequestListV2.html",
                    "borrow/borrowList.html",
                    "borrow/borrowListV2.html",
                    "plan/planSwiper.html",
                    "plan/planList.html",
                    "popular/financesList.html",
                    "fund/fundUnInvested.html",
                    "fund/fundHelp.html",
                    "security/forgetpwLogin.html",
                    "security/forgetpwLoginStep2.html",
                    "hotActivities/hotActivities.html",
                    "/activity/",
                    "/activityRegAndLogin/",
                    "/xxStore/",
                    "/gameExchange/",
                    "common/help.html",
                    "dataAnalysis/dataAnalysis.html",
                    "common/about-us.html",
                    "newHand/sevenDaysDetail.html",
                    "monthFinance/monthFinanceDetails.html",
                    "plan/planDetail.html",
                    "plan/planDetailsV2.html",
                    "plan/planDetailsV2_act.html",
                    "stepUpward/stepUpwardDetail.html",
                    "stepUpward/stepUpwardCalc.html",
                    "yyp/yypDetails.html",
                    "yyp/paymentPlan.html",
                    "account/drawmoneyFaq.html",
                    "announce/announceList.html",
                    "announce/announceDetail.html",
                    "borrow/safeGuard.html",
                    "borrow/riskTip.html",
                    "borrow/contractModel.html",
                    "trade/tradeList.html",
                    "newHand/thirtyDaysDetail.html"
                ];
            }
        },

        getNewYear:function(){
            return CONFIG.newYear;
        },
        getVersion: function () {
            return CONFIG.ver;
        },
        getHtmlPath: function () {
            return CONFIG.html;
        },
        getDataPath: function () {
            return CONFIG.data;
        },
        getImgPath: function () {
            return CONFIG.img;
        },
        isAnimate: function () {
            return CONFIG.isAnimate;
        },
        Debug: function () {
            return CONFIG.debug;
        },
        isAndroidAnimate: function () {
            return CONFIG.isAndroidAnimate;
        },
        isIosAnimate: function () {
            return CONFIG.isIosAnimate;
        },
        isUpscroller: function () {
            return CONFIG.isUpscroller;
        },
        noLoginPageUrls: function () {
            return CONFIG.noLoginPage;
        },
        forbiddenPage:function(){
            return CONFIG.forbiddenPage;
        }
    };

    return globalConfig;
});