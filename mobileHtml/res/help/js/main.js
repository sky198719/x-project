require(['base', 'requirejs', 'trackBase', 'json', 'Swiper', 'xxdBridge', 'com'], function ($, requirejs, track, json, Swiper, xxdBridge, com) {
    // console.log('这是基础部份');
    // console.log('↓');
    $(function () {
        document.addEventListener('touchstart', function () {
        });

        $(function () {
            // 打开大图
            $(document).on('click', '.o_img', function () {
                var img  = $(this).data('img');
                var text = $(this).children('p').text();
                console.log(img, text);
                ux.oImg(img, text);
            });
            // 关闭大图
            $(document).on('touchstart', '.x_img', function () {
                ux.xImg();
            });
        });

        // 马上投标
        $(document).on('click', '#to_bid', function () {
            ux.toBid();
        });

        var ux = {
            toBid: function () {

            },
            oImg : function (img, text) {
                var _code = '<div class="full-img hvc" style="display: none"><div><div><div class="x_img"><i class="multiply"></i></div><img src="' + img + '" /><p>' + text + '</p></div></div></div>';
                $(_code).appendTo('body').fadeIn(200);
                $('body').addClass('pf');
            },
            xImg : function () {
                $('body').removeClass('pf');
                $('.full-img').fadeOut(200);
                setTimeout(function () {
                    $('.full-img').remove();
                }, 300);
            }
        };

        // 列表开合
        $(document).on('touchend', '.list_switch', function () {
            var oLi = $(this).parent('li');
            if (oLi.hasClass('open')) {
                $(this).next('.more_info').stop().slideUp(250);
                setTimeout(function () {
                    oLi.removeClass('open');
                },300);

            } else {
                $(this).next('.more_info').stop().slideDown(250);
                var $this = $(this);
                setTimeout(function () {
                    var liH = ($('body').scrollTop()+$this.next('.more_info').height()).toFixed(0);
                    oLi.addClass('open');
                    if($this.hasClass('last')){
                        $('body').animate({scrollTop: liH}, 250);
                    }
                },300);
            }
        });

        // 打开投标记录
        $(document).on('click', '#o_record', function () {
            console.log('open record')
        });

        // 关闭投标记录
        $(document).on('click', '#x_record', function () {
            console.log('close record')
        });

        $(document).on('click', '#to_contact', function () {
            xxdBridge.open({pagename: 'contactus'})
        });

        // 常见问题  提现说明
        var sClientId = 'XXD_FRONT_END_H5';
        var sClientTime = com.getUrlValue('clientTime');
        var sToken      = com.getUrlValue('token');
        $.ajax({
            url     : '/accountCenter/account/withdraw/initWithdraw',
            dataType: 'json',
            // async   : false,
            headers : {
                "Accept"    : "application/json;charset=UTF-8",

                "clientId"  : sClientId,
                "clientTime": sClientTime,
                "token"     : sToken
            },
            data    : {},
            type    : 'GET',
            success : function (str) {

                if (str.code == 200000) {
                    $("#drawNum").text(str.data.data.configWithdrawCount);
                }
            },
            error   : function (str) {
                alert('信息查询失败，请联系客服')
            }
        });

    });
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});