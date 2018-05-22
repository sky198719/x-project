/**
 * Created by chaihuangqi on 2015/7/7.
 */
define(function () {
    var invitationView = {
        init: function () {

        },
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        showInvitationListItem:function(param){
            if(param.invitationList!=null && param.invitationList.length != 0){
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.invitationList});
                if (param.type == 'push') {
                    $$("#activityUserList").append(output);
                } else {
                    $$("#activityUserList").html(output);
                }
            }else{
                var html='<div class="list-block media-list" ><ul>' +
                    '<h6 class="font-red text-center pd10">亲！您还没有名下客户呢！赶快去推广吧' +
                    '</h6></ul></div>'
                $$("#activityUserList").html(html);
            }
        }
    };
    return invitationView;
});

