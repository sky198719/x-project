define(function(){
    var red108WebappCtrl = {
        init: function () {
             var binding = [
                 {
                     element: '#red108Webapp #red108Button',
                     event: 'click',
                     handler: red108WebappCtrl.red108Button
                 }
             ];
            appFunc.bindEvents(binding);
            $$("title").html("注册即送 108元新手红包");
            if (!appFunc.isLogin()) {
            	$$("#red108Webapp #red108Button").attr("data-destination","user/register.html");
            	$$("#red108Webapp #red108Button").attr("src","static/img/activity/red108/webapp/wap_btn.png");
            }else{
            	$$("#red108Webapp #red108Button").attr("data-destination","account/hongbao.html?path=account");
            	$$("#red108Webapp #red108Button").attr("src","static/img/activity/red108/webapp/btn.png");
            }
        },
        red108Button: function(){
        	var destination = $$("#red108Webapp #red108Button").attr("data-destination");
        	GS.loadPage(destination);
        }
    };
    return red108WebappCtrl;
});