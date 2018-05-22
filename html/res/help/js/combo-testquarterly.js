/**
 * Created by gaoshanshan_syp on 2017/6/12.
 */
require(['base', 'float', 'trackBase', "swiper","requirejs"], function ($, float, track, Swiper) {

    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        parallax: true,
        speed: 600
    });
    $('#download').click(function (ev) {
        var ev=ev || event;
        ev.preventDefault();
        printdiv('download-content');
    })
    function printdiv(printpage)
    {
        var headstr = "<html><head><title></title></head><body>";
        var footstr = "</body>";
        var newstr = document.all.item(printpage).innerHTML;
        var iframe = document.createElement('iframe');
        iframe.style.display='none';
        document.body.appendChild(iframe);
        iframe.contentDocument.body.innerHTML =newstr;
        iframe.contentWindow.print();
        document.body.removeChild(iframe);
        return false;
    }

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});