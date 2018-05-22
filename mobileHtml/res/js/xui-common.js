// 常用函数
define(function () {
    var com = {
        inWX          : function () {
            // 是否在微信打开？
            return window.navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == 'micromessenger' ? true : false;
        },
        inQQ          : function () {
            // 是否在QQ打开？
            return window.navigator.userAgent.toLowerCase().match(/QQ/i) == 'qq' ? true : false;
        },
        getUrlValue   : function (name) {
            // 获取URL传参 STA
            var oValue = new RegExp('(?:[\\?|\\#|\\&]' + name + '=)([-_=\\w\\/]+)(?:[&?|]?)');
            var url    = window.location.href.match(oValue);
            return url ? url[1] : '';
        },
        getCookie     : function (name) {
            // 获取Cookie
            var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
            if (arr = document.cookie.match(reg)) {
                return (arr[2])
            } else {
                return null
            }
        },
        getNowTime    : function () {
            // 获取传参
            return new Date().getTime();
        },
        getDateForTime: function (tm) {
            //根据时间戳生成的年月日时间对象
            var d    = new Date(tm);
            var date = (d.getFullYear()) + "-" + (d.getMonth() + 1) + "-" + (d.getDate());
            return date;
        },
        setLS         : function (name, val) {
            window.localStorage.setItem(name, val);
        },
        getLS         : function (name) {
            return window.localStorage.getItem(name);
        },
        twoNum        : function (num) {
            return num > '' + 9 ? '' + num : '0' + num;
        },
        ajax          : function (o) {
            var max = 3, sub = 0;

            function a(o) {
                $.ajax({
                    url        : o && o.url || '',
                    contentType: o && o.contentType || 'application/json',
                    dataType   : o && o.dataType || 'json',
                    type       : o && o.type || 'GET',
                    headers    : o && o.headers || {},
                    data       : o && o.data || {},
                    beforeSend : function (r) {
                        o.beforeSend && o.beforeSend(r)
                    },
                    complete   : function (r) {
                        o.callback && o.callback(r.responseJSON);
                    },
                    success    : function (r) {
                        if (r && r.code == 200000) {
                            o.success && o.success(r);
                        } else if (r.code == 200400) {
                            if (sub <= max) {
                                sTime(o);
                            } else {
                                console.log('重试超时');
                            }
                            sub++;
                        } else {
                            o.error && o.error(r)
                        }
                    },
                    error      : function (data) {
                        console.error(data.code);
                    }
                });
            }

            a(o);
            function sTime(o) {
                var time = null;
                $.ajax({
                    url     : '/feapi/currentTime',
                    type    : 'get',
                    dataType: 'json',
                    success : function (r) {
                        if (r.code == 200) {
                            var timer = setInterval(function () {
                                time = r.data.currentTime;
                                if (time) {
                                    clearInterval(timer);
                                    o.headers ? o.headers.clientTime = time : o.headers = {clientTime: time};
                                    a(o);
                                }
                            }, 10);
                            setTimeout(function () {
                                clearInterval(timer);
                            }, 10000)
                        }
                    },
                    error   : function (r) {
                        o && o.error && o.error(r);
                    }
                });
            }
        }
    };
    return com;
});