define(function () {
    var homeAnimate = {
        animate: function () {
           /* if (!GS.isSupportAnimate()) {
                return;
            }      */

            try {
                $$(".fund-index-11Holiday").addClass("animated fadeInLeft");

               /* $$(".day-entrance").addClass("animated zoomIn");
                $$(".xyb-entrance").addClass("animated zoomIn");
                $$(".bg_newindex_top").addClass("animated zoomIn");
                $$(".index-footer").addClass("animated fadeInLeft");
                $$("a[name='index_borrowListUrl']").addClass("animated fadeInLeft");
                $$("a[name='index_tradeRequestListUrl']").addClass("animated fadeInRight");
                $$("a[name='index_dataAnalysisUrl']").addClass("animated fadeInLeft");
                $$("a[name='index_hotActivitiesUrl']").addClass("animated fadeInRight");  */
            } catch (e) {
            }
        }
    };
    return homeAnimate;
});
