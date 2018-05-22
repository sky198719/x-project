define(function () {
    var intervalArray = [];
    var fundAnimate = {
        investedAnimate: function () {
            if (!GC.isAnimate()) {
                return false;
            }
            try {
                $$.each(intervalArray, function (index, value) {
                    clearInterval(value);
                });
                intervalArray.length = 0;

                var isSupporIos = GS.isIosSysAnimate();
                var isSupporAndroid = GS.isAnroidSysAnimate();
                if (isSupporIos || isSupporAndroid) {
                    $$(".fund-moneybag").addClass("animated slideInDown");
                }

                if (isSupporIos) {
                    $$(".day-invest-bid").addClass("animated zoomIn");
                    $$("#earningsYesterday").addClass("animated zoomIn");
                    $$(".fundSumMoneyDiv").addClass("animated zoomIn");
                    $$(".fundTitle3").addClass("animated slideInLeft");
                    $$(".day-footer-optbutton .transferOut").addClass("animated fadeInLeft");
                    $$(".day-footer-optbutton .transferIn").addClass("animated fadeInRight");
                }

                intervalArray.push(setInterval(function () {
                    if (isSupporIos || isSupporAndroid) {
                        if ($$('.fund-moneybag').hasClass('slideInDown')) {
                            $$('.fund-moneybag').removeClass('slideInDown');
                        }
                        $$('.fund-moneybag').toggleClass('swing');
                        //$$('.fund-forphone-left').toggleClass('swing');
                    }
                }, 1000));
            } catch (e) {
                console.log(e);
            }
        },
        unInvestedAnimate: function () {
            if (!GC.isAnimate()) {
                return false;
            }
            try {
                $$.each(intervalArray, function (index, value) {
                    clearInterval(value);
                });
                intervalArray.length = 0;
                var isSupporIos = GS.isIosSysAnimate();
                var isSupporAndroid = GS.isAnroidSysAnimate();

                if (isSupporIos) {
                    $$(".day-top-lefttile").addClass("animated slideInLeft");
                    $$(".day-top-apr .fundTitle1").addClass("animated zoomIn");
                    $$(".day-top-apr .fundTitle2").addClass("animated zoomIn");

                    $$(".day-invest-bid").addClass("animated zoomIn");
                    $$(".day-footer .index-item-link").addClass("animated zoomIn");
                    $$("#fundMoneyImg").addClass("animated slideInLeft");
                }

                if (isSupporIos || isSupporAndroid) {
                    $$('.fund-double11').addClass('animated slideInRight');
                    $$(".fund-forphone_down").addClass("animated fadeInDown");
                }
            } catch (e) {
                xxdLog.debug(e)
            }
        }
    };
    return fundAnimate;
});
