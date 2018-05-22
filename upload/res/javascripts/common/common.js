require.config({
	shim:{
		'jquery.ui.widget':['jquery'],
		'jquery.iframe-transport':['jquery'],
		'jquery.fileupload':['jquery'],
		'jquery.fileupload-process':['jquery'],
		'jquery.fileupload-validate':['jquery']
    },
	paths:{
		'jquery':'../library/jquery',
		'jquery.ui.widget': '../library/jquery.ui.widget',
        'jquery.iframe-transport': '../library/jquery.iframe-transport',
        'jquery.fileupload': '../library/jquery.fileupload',
        'jquery.fileupload-process':'../library/jquery.fileupload-process',
        'jquery.fileupload-validate':'../library/jquery.fileupload-validate',
        'html5media':'../library/html5media.min'
	}
});
var ajaxlink = '../../fileCenter';
var ajaxlink2 = '../../userCenter';
var extra = '?jsonpCallback=?';
define(['jquery','jquery.ui.widget','jquery.iframe-transport','jquery.fileupload','jquery.fileupload-process','jquery.fileupload-validate','html5media'],function($,ui,frame,upload,progress,validate,media){
	
});