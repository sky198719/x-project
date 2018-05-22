define(['js/plan/planUtils','js/utils/animateNumber'],function(PlanUtils,animateNumber){
    var indexView = {
        render: function(params){
            appFunc.bindEvents(params.bindings);
        },
        indexBanner:function(data){
            var html = '';
            var list = data.pptToplist;
            for (var k = 0; k < list.length; k++) {
                var b = list[k];
                if(k == 0){
                    if(b.url.indexOf('http') == 0){
                        html +='<div class="swiper-slide index-banner-c"><a target="_blank" class="external index-b-a" href='+ b.url +'><img src='+ data.imageUrl +'/'+ b.pptUrl +' style="height:72px"></a><span class="close_myswiper">X</span></div>';
                    }else{
                        html +='<div class="swiper-slide index-banner-c"><a class="index-b-a" href='+ b.url +'><img src='+ data.imageUrl +'/'+ b.pptUrl +'></a><span class="close_myswiper">X</span></div>';
                    }
                }
            }
            $$('div[name="index_banner"]').html(html);
            var mySwiper = xxdApp.swiper('div[name="indexSwiper"]',{
                direction: 'horizontal',
                pagination:'#swiper-pagination',
                autoplay : 2000
            });
            $$('.close_myswiper').on('click', function () {
                if(window.sessionStorage){
                    sessionStorage.setItem("indexBanner","hide");
                }
                $$('div[name="indexSwiper"]').hide();
            });
        },

        indexXyb:function(data){
            $$('span[name="index_maxapr"]').html("0");
            $$('h4[name="index_xybName"]').html(PlanUtils.schemeType(data.scheme.type));
            $$('h6[name="index_closeterm"]').html('锁定期：' + data.scheme.closeterm + '个月');
            $$('input[name="index_schemeId"]').val(data.scheme.schemeId);

            animateNumber.animate({
                element:'span[name="index_maxapr"]',
                from:0,
                to:data.scheme.maxApr,
                steps:10,
                intervalNumber:100
            });

            if(data.scheme.remacount == 0){
                $$('a[name="index_planDetail"]').html('已满额');
            }
            if(data.schemeStatus == 0){
                $$('a[name="index_planDetail"]').html('即将开始');
            }
            if(data.schemeStatus == 4){
                $$('a[name="index_planDetail"]').html('收益中');
                $$('input[name="index_schemeId"]').val("");
            }
        }
    };

    return indexView;
});