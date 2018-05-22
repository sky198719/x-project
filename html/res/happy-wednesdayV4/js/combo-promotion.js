/**
 * Created by gaoshanshan_syp on 2018/1/5.
 */
require(['base', 'float', 'trackBase', "requirejs"], function ($, float, track) {
    var dataUrl={
        isLogin:'/feapi/users/loginInfo',
    };

    var oTop=$("#J_topMain").height()-160;
    var oFooter=$("#J_footerm").height();
    var oRegisterB=$("#J_suspend").height();
    var maxH=oFooter+oRegisterB;

    mainScroll(oTop,maxH);
    function mainScroll(h,maxH) {
        $(window).scroll(scrolls);
        scrolls();
        function scrolls() {
            var wd_st=document.documentElement.scrollTop || document.body.scrollTop;
            var hAll = $(document).height();
            var wh = $(window).height();
            if(wd_st>=h && hAll-wh-wd_st>maxH){
                $("#J_suspendpop").removeClass("disnone");
            }else{
                $("#J_suspendpop").addClass("disnone");
            }
        }
    }

    if(isLogin()){
        window.location.href = "index.html";
    }
    $('#J_xybBtn1,#J_xybBtn2,#J_suspendBtn,#J_suspendpopBtn').on('click',function (ev) {
        if(!isLogin()){
            var ev=ev || event;
            $('body,html').animate({ scrollTop: 0 }, 500);
            ev.preventDefault();
        }else{
            window.location.href = "index.html";
        }

    });
    //判断是否登录
    function isLogin() {
        var result=false;
        var  token=$.readCookie('Token');
        $.ajax({
            type    : 'GET',
            url     : dataUrl.isLogin+'?userToken='+token,
            async   : false,
            data    : {},
            dataType: 'json',
            success : function (str) {
                if(str.code == "200000"){
                    if (str.data.status.code == 200) {
                        result=true;
                    }
                }
            },
            error:function () {
                float.alert({content:msg.errorMsg});
            }
        });
        return result;
    }
    //兼容不支持placeholder的浏览器[ie浏览器，并且10以下均采用替代方式处理]
    if ((navigator.appName == "Microsoft Internet Explorer") && (document.documentMode < 10 || document.documentMode == undefined)) {
        var $placeholder = $("input[placeholder]");
        for (var i = 0; i < $placeholder.length; i++) {
            if ($placeholder.eq(i).attr("data-auto") == "password") {
                $placeholder.eq(i).siblings('.psw-cur').removeClass('disnone');
            } else {
                $placeholder.eq(i).val($placeholder.eq(i).attr("placeholder"));
            }
        }
        $placeholder.focus(function () {
            if ($(this).attr("data-auto") == "password") {
                $(this).siblings('.psw-cur').addClass('disnone');
            }else{
                if ($(this).val() == $(this).attr("placeholder")) {
                    $(this).val("");
                }
            }
        }).blur(function () {
            if ($(this).attr("data-auto") == "password") {
                if ($(this).val() == "") {
                    $(this).siblings('.psw-cur').removeClass('disnone');
                }
            } else {
                if ($(this).val() == "") {
                    $(this).val($(this).attr("placeholder"));
                }
            }
        });
    }
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});