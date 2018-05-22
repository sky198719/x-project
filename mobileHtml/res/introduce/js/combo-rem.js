require(['base', "requirejs"], function ($, register) {
; (function fixRem(window){
	function fixRem(){
    var windowWidth= document.documentElement.clientWidth || window.innerWidth || document.body.clientWidth;
    windowWidth = windowWidth > 800 ? 800 : windowWidth;
    var rootSize= 32* (windowWidth/375);
    // rootSize=rootSize>36 ? 36 : rootSize;
    var htmlNode = document.getElementsByTagName("html")[0];
    htmlNode.style.fontSize = rootSize+'px';
   }
   fixRem();
    window.addEventListener('resize', fixRem, false);
})(window);

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});