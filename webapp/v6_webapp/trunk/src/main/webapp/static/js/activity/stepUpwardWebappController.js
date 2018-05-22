define(function(){
    var stepUpwardWebappCtrl = {
        init: function () {
             var binding = [
                 {
                     element: '.stepUpwardWebapp .goStepUpwardDetail',
                     event: 'click',
                     handler: stepUpwardWebappCtrl.goDetail
                 }
             ];
            appFunc.bindEvents(binding);
        },
        goDetail:function(){
	    	GS.loadPage('stepUpward/stepUpwardDetail.html');
        }

    };
    return stepUpwardWebappCtrl;
});