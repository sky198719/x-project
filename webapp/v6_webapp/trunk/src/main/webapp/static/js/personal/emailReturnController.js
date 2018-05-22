/**
 * 邮箱激活结果
 * Created by pufei on 2015/2/12.
 */
define([ 'js/personal/emailReturnView'], function (emailReturnView) {
    var bankUrl="";
    var emailReturnCtrl = {
        init: function (event) {
            var page = appFunc.getEventDetailPageQuery(event);
            var uid = page.uid
            $$("#emailReturnUrl").attr("disabled", "disabled");

            req.callPost({
                url: 'approve/activateEmail.do',
                data:{
                    uid:uid
                },
                dataType:'json',
                preloaderTitle:'正在验证邮箱，请稍后...',
                success:function(result){
                    $$("#emailReturnUrl").removeAttr("disabled");
                    bankUrl=result.bankUrl
                    $$("#emailResult").html(result.msg);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown){
                    $$("#emailReturnUrl").removeAttr("disabled");
                    xxdApp.hidePreloader();
                    xxdApp.alert('系统无响应，请查看个人资料邮箱是否认证成功或重新认证', '抱歉',function (){
                        GS.loadPage("personal/personalInfo.html");
                    });
                }
            });

            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#emailReturnUrl',
                    event: 'click',
                    handler: emailReturnCtrl.emaulBack
                }
            ];
            emailReturnView.init({
                bindings: bindings
            });
        },
        emaulBack:function(){
            GS.loadPage("personal/personalInfo.html");
        }
    };
    return emailReturnCtrl
})