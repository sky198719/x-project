require(['jquery', 'requirejs'], function ($, requirejs) {
    $(function () {
        document.addEventListener('touchstart', function () {
        });

        $(function () {
            // 打开大图
            $(document).on('click', '.o_img', function () {
                var img  = $(this).data('img');
                var text = $(this).children('p').text();
                // console.log(img, text);
                ux.oImg(img, text);
            });
            // 关闭大图
            $(document).on('click', '.x_img', function () {
                ux.xImg();
            });
        });

        var ux = {
            oImg: function (img, text) {
                var _code = '<div class="full-img hvc" style="display:none;pointer-events:auto"><div><div><div class="x_img"><i class="multiply"></i></div><img src="' + img + '" /><p>' + text + '</p></div></div></div>';
                $(_code).appendTo('body').stop().fadeIn(200);
            },
            xImg: function () {
                $('.full-img').stop().fadeOut(200);
                setTimeout(function () {
                    $('.full-img').remove();
                }, 300);
            }
        };
    });


}, function (err) {
    console.log(err);
});