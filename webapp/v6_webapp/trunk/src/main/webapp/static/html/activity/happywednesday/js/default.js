$(document).ready(function(){
	try{
		var request = new Object();
		request = GetRequest();
		var gauserId = request["gaid"];
		//XXD_TRACK.track_pageview('memberDayWebApp0922');
		gaInits(gauserId);
		growingIOInits({userId:gauserId});
	}catch(e){

	}
});

(function(doc,win,fontSize) {
	var docEl = doc.documentElement,
	resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
	recalc = function(){
		var clientWidth = docEl.clientWidth;
		if (!clientWidth){
			return;
		}
		docEl.style.fontSize = fontSize * (clientWidth / 320) + 'px';
	};
	if(!doc.addEventListener){
		return;
	}
	win.addEventListener(resizeEvt,recalc,false);
	doc.addEventListener('DOMContentLoaded',recalc,false);
})(document,window,16);

var link = 'http://stage.xxd.com/m/';
// var link = '../../../../';
var linkextra = '?jsonpCallback=?';