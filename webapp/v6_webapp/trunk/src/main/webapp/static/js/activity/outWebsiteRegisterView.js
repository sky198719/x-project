define(['js/utils/animateNumber', 'js/utils/date'],function(animateNumber,DateHandle){
    var outWebsiteRegisterView = {
        render: function(params){
            appFunc.bindEvents(params.bindings);
        },
        getBanner:function(data){
            var html = '';
            var list = data.pptToplist;
            for (var k = 0; k < list.length; k++) {
                var b = list[k];
                var imageUrl = data.imageUrl + '/' + b.pptUrl ;

                html += '<div class="swiper-slide index-banner-c">';
                if(b.url.indexOf('http') == 0){
                    html += '<a target="_blank" class="external" href='+ b.url +'>';
                }else{
                    html += '<a href='+ b.url +'>';
                }
                html += '<div class="bannerbox" style="background-color:'+b.keepword1+'"><div class="banner"><img data-src='+imageUrl+'  class="swiper-lazy" height="150px;"/><div class="preloader"></div></div></div>';
                html += '</a>';
                html += '</div>';
            }
            $$('div[name="outWebsiteRegister_banner"]').html(html);
            var mySwiper = xxdApp.swiper('div[name="outWebsiteRegisterSwiper"]',{
                touchMoveStopPropagation:false,
                direction: 'horizontal',
                pagination:'.swiper-pagination',
                autoplay : 2000,
                preloadImages: false,
                lazyLoading: true
            });
        }
    };
    return outWebsiteRegisterView;
});