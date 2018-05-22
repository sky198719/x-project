define(['js/plan/planUtils','js/utils/animateNumber', 'js/utils/date'],function(PlanUtils,animateNumber,DateHandle){
    var homeView = {
        render: function(params){
            appFunc.bindEvents(params.bindings);
        },
        indexBanner:function(data){
            var html = '';
            var list = data.pptToplist;
            for (var k = 0; k < list.length; k++) {
                var b = list[k];
                var imageUrl = data.imageUrl + '/' + b.pptUrl ;

                html += '<div class="swiper-slide index-banner-c">';
                if(b.url.indexOf('http') == 0){
                    html += '<a onclick="promo_click(\''+b.fileName+ '\');" class="external" href='+ b.url +'>';
                }else{
                    html += '<a onclick="promo_click(\''+b.fileName+ '\');" href='+ b.url +'>';
                }
                var banner_id = 'banner'+ k;
                html += '<div class="bannerbox" style="background-color:'+b.keepword1+'"><div class="banner"><img data-src='+imageUrl+' eventType = "jump" dev_id='+ banner_id +' target_id ='+ b.id +' textHref='+ b.url +' dmp_text='+ b.fileName +'  class="swiper-lazy dmp-click" height="150px;"/><div class="preloader"></div></div></div>';
                html += '</a>';
                html += '</div>';
            }
            $$('div[name="index_banner"]').html(html);
            var mySwiper = xxdApp.swiper('div[name="indexSwiper"]',{
                touchMoveStopPropagation:false,
                direction: 'horizontal',
                pagination:'.swiper-pagination',
                autoplay : 2000,
                preloadImages: false,
                lazyLoading: true
            });
        },

        indexXyb:function(data){
        	
        }
    };

    return homeView;
});