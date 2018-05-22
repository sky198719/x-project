require(['jquery', 'requirejs', /*'trackBase',*/ 'Swiper'], function ($, requirejs, /*track,*/ Swiper) {

    // 滑屏
    new Swiper('.swiper-container', {
        direction          : 'vertical',
        pagination         : '.swiper-pagination',
        mousewheelControl  : true,
        watchSlidesProgress: true,
        onSlideChangeEnd   : function (swiper) {
            //swiper.activeIndex -> 切换一屏时回调现在是第几个slide
            if (swiper.activeIndex === 1) {
                amNumber('am_number1');
                amNumber('am_number2');
            } else if (swiper.activeIndex === 2) {
                amNumber('am_number3');
                amNumber2('am_number4', 4);
                amNumber('am_number5');
            } else if (swiper.activeIndex === 4) {
                amNumber('am_number6');
                amNumber('am_number7');
                amNumber('am_number8');
                amNumber('am_number9');
                amNumber2('am_number10', 6);
            }

            if (swiper.activeIndex > 0 && swiper.activeIndex < 16) {
                $('#star').css('opacity','.2')
            }else{
                $('#star').css('opacity','.5')
            }
            if (swiper.activeIndex === 16) {
                $('.ui-arrow').addClass('isend');
            } else {
                $('.ui-arrow').removeClass('isend');
            }
        }
    });

    function amNumber(id) {
        var $id     = $('#' + id);
        var data    = $id.data('nb');
        var newDate = parseInt(data.replace(/,/g, ''));

        var i = 16;

        function rNumber() {
            i--;
            if (i > 0) setTimeout(function () {
                $id.html(addCommas(parseInt(newDate / i)));
                rNumber()
            }, 70);
        }

        rNumber();

        function addCommas(nStr) {
            nStr += '';
            x       = nStr.split('.');
            x1      = x[0];
            x2      = x.length > 1 ? '.' + x[1] : '';
            var rgx = /(\d+)(\d{3})/;
            while (rgx.test(x1)) {
                x1 = x1.replace(rgx, '$1' + ',' + '$2');
            }
            return x1 + x2;
        }
    }

    function amNumber2(id, cs) {
        var $id  = $('#' + id);
        var data = $id.data('nb');

        var i = cs;

        function rNumber() {
            i--;
            if (i > -1) setTimeout(function () {
                $id.html(((data * 100) - (i * 100)) / 100);
                rNumber();
            }, 100);
        }

        rNumber();
    }

    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg))

            return (arr[2]);
        else
            return null;
    }

    $.ajax({
        url     : '/feapi/users/loginInfo?userToken=' + getCookie('userToken'),
        dataType: 'json',
        data    : {},
        type    : 'GET',
        success : function (str) {
            /*if (str.data.status.code === 200) {
             //result = true;
             track.init(str.data);
             } else {
             track.init();
             }*/
        },
        error   : function (str) {
            track.init();
        }
    });

}, function (err) {
    console.log(err);
});