/**
 * Created by gaoshanshan_syp on 2017/9/4.
 */
require(['base', "requirejs", "trackBase",'json',"juicer", ], function ($, requirejs, track) {
    var n = 30;
    var iosNumber = n;
    var iosInterval;
    var androidNumber = n;
    var androidInterval;
    $(document).ready(function () {
        $("#download").click(function () {
            var isDow = $(this).data("isDow");
            if (isDow != undefined && isDow == true) {
                return;
            }else{
                $(this).data("isDow", true);
            }

            if(isWeixin()) {
                $("#J_wx").show();
                return;
            }
            if(navigator.userAgent.match(/(iPhone|iPad|iPod)/i)) {
                iosInterval = setInterval(function () {
                    if (iosNumber == 0) {
                        clearInterval(iosInterval);
                        $("#download").data("isDow", false);
                        iosNumber = n;
                    } else {
                        iosNumber--;
                    }
                }, 1000);
                window.location.href = 'itms-services://?action=download-manifest&url=https://download-cdn.xinxindai.com/iostmp/manifest.plist';
            }else if(navigator.userAgent.match(/android/i)) {
                androidInterval = setInterval(function () {
                    if (androidNumber == 0) {
                        clearInterval(androidInterval);
                        $("#download").data("isDow", false);
                        androidNumber = n;
                    } else {
                        androidNumber--;
                    }
                }, 1000);
                window.location.href = 'http://download-cdn.xinxindai.com/apktmp/app-debug.apk';
            } else {
                alert("手机专属下载");
            }
        });
    });
    function isWeixin() {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    }

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});