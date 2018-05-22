require(['base', "requirejs", 'json', "juicer", "trackBase"], function ($, register, login, requirejs,track) {
    $.ajax({
        url     : '/feapi/users/loginInfo?userToken=' + getCookie('userToken'),
        dataType: 'json',
        async   : false,
        data    : {},
        type    : 'GET',
        success : function (str) {
            if(str.code == 200000){
                if (str.data.status.code === 200) {
                    //result = true;
                    track.init(str.data);
                }else {
                    track.init();
                }
            }else {
                track.init();
            }
        },
        error:function (str) {
            track.init();
        }
    });
    function getCookie(cookieName) {
        var strCookie = document.cookie;
        var arrCookie = strCookie.split("; ");
        for (var i = 0; i < arrCookie.length; i++) {
            var arr = arrCookie[i].split("=");
            if (cookieName == arr[0]) {
                return arr[1];
            }
        }
        return "";
    }
    $(".tab li").on("click",function(){
        var index=$(this).index();
        $(this).parent().siblings("div").css("display","none").eq(index).css("display","block"); //返回上一层，在下面查找css名为box隐藏，然后选中的显示
        $(this).addClass("active").siblings().removeClass("active"); //a标签显示，同辈元素隐藏
    })
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
