/**
 * Created by gaoshanshan_syp on 2017/9/14.
 */
require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'requirejs', 'jqueryui', 'fullPage'], function ($, jqueryui, fullPage) {
    $('#fullpage').fullpage({
        sectionsColor: ['#141414', '#141414', '#141414', '#141414', '#141414',
            '#141414', '#141414', '#141414', '#141414', '#141414',
            '#141414', '#141414', '#141414',"#141414","#141414"],
        anchors: ['1Page', '2Page', '3Page', '4Page', '5Page',
            '6Page', '7Page', '8Page', '9Page', '10Page',
            '11Page', '12Page', '13Page', '14Page', '15Page','16Page','17Page'],
        menu: '#menu',
        scrollingSpeed: 600
    });
    

    if(navigator.userAgent.indexOf("MSIE")>0){
        if(navigator.userAgent.indexOf("MSIE 6.0")>0){
            $("#J_specialBkg,#J_lastBkg").addClass("bkg-special");
            $(".all-stars").addClass("disnone");
        }
        if(navigator.userAgent.indexOf("MSIE 7.0")>0){
            $("#J_specialBkg,#J_lastBkg").addClass("bkg-special");
            $(".all-stars").addClass("disnone");
        }
        if(navigator.userAgent.indexOf("MSIE 9.0")>0 && !window.innerWidth){
           $("#J_specialBkg,#J_lastBkg").addClass("bkg-special");
            $(".all-stars").addClass("disnone");
        }
    }

});