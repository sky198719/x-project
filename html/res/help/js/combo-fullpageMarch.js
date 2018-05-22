require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'requirejs', 'jqueryui', 'fullPage'], function ($, jqueryui, fullPage) {
    $('#fullpage').fullpage({
        sectionsColor: ['#191919', '#191919', '#191919', '#191919', '#191919',
            '#191919', '#191919', '#191919', '#191919', '#191919',
            '#191919', '#191919', '#191919',"#191919","#191919","#191919"],
        anchors: ['1Page', '2Page', '3Page', '4Page', '5Page',
            '6Page', '7Page', '8Page', '9Page', '10Page',
            '11Page', '12Page', '13Page', '14Page', '15Page', '16Page'],
        menu: '#menu',
        scrollingSpeed: 600
    });

})