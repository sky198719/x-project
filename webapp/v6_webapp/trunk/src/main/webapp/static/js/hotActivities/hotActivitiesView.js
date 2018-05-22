/**
 * Created by chaihuangqi on 2015/5/20.
 */
define(function() {
    var hotActivitiesView = {
        init: function () {
            var html = "";
            html += "<div class='infinite-scroll-preloader'>";
            html += "<div class='preloader'></div>";
            html += "</div>";
            $$(".hotActivity-list-show").append(html);
        },

        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },

        showListItem:function(param){
            var compiledTemplate = t7.compile(param.result);
            var output = compiledTemplate({list: param.hotActivitiesList});

            if (param.type == 'push') {
                $$("#tenderList").append(output);
            } else {
                $$("#tenderList").html(output);
            }
            $$(".hotActivity-list-show .media-list").show();
        },
        viewDetail:function(param){
            var html = '';
            var list = param.hotActivitiesList;
            for (var k = 0; k < list.length; k++) {
                var b = list[k];
                html ='<a class="link shareSina" id='+b.activityId+' shareUrl='+ b.shareUrl+' shareTopic='+ b.shareTopic+' sharePic='+ b.sharePic+'>分享到微博</a>';
                if(b.jumpUrl.indexOf('http') == 0){
                    html +='<a target="_blank" class="external" href='+ b.jumpUrl +'>查看详情</a>';
                }else{
                    html +='<a href='+ b.jumpUrl +'>查看详情</a>';
                }
                $$('div[name="'+b.activityId+'"]').html(html);
            }
        }

    };
    return hotActivitiesView;
});
