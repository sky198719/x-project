require(['jquery', 'requirejs', /*'trackBase',*/ 'Swiper'], function ($, requirejs, /*track,*/ Swiper) {

    // 滑屏
    new Swiper('.swiper-container', {
        direction          : 'vertical',
        pagination         : '.swiper-pagination',
        mousewheelControl  : true,
        watchSlidesProgress: true,
        onSlideChangeEnd   : function (swiper) {
            //swiper.activeIndex -> 切换一屏时回调现在是第几个slide
            if (swiper.activeIndex === 1) {
            } else if (swiper.activeIndex === 2) {
            } else if (swiper.activeIndex === 4) {
            }

            if (swiper.activeIndex === 16) {
                $('.ui-arrow').addClass('isend');
            } else {
                $('.ui-arrow').removeClass('isend');
            }
        }
    });


}, function (err) {
    console.log(err);
});