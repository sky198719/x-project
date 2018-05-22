require(['base', 'requirejs', 'trackBase'], function ($, requirejs, track) {
    track.init();
    var uid,
        openStatus;
    uid = GetQueryString("uid");
    openStatus = GetQueryString("openStatus");
    //是否登录且开户
    if (uid != "" && openStatus == "1") {
        //$("#toUrl").text("立即投资");
        $("#toUrl").css("backgroundImage",'url("imgs/investBtn.png")');
    }
    //点击按钮
    $("#toUrl").click(function () {

        //是否登录 return
        if (uid == "" || uid == null) {
            window.location.href = 'xxd://pagename=login';
        } else {
            //开户状态
          if(openStatus == "1"){
              //已开户
              window.location.href = 'xxd://pagename=hotproduct';
          }else {
              //未开户
              window.location.href = 'xxd://pagename=manageaccount';

          }
        }
    });
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)return decodeURI(r[2]);
        return null;
    }

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});

