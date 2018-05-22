/**
 * Created by gaoshanshan_syp on 2017/11/29.
 */
require(['base', 'float', 'trackBase', "requirejs"], function ($, float, track) {
    var dataUrl={
        isLogin:'/feapi/users/loginInfo',
    };

    var oTop=$("#J_topMain").height();
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
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});