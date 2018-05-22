require(['jquery', 'requirejs','trackBase'], function ($, requirejs,track) {
    // 获取cookie
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg))
            return (arr[2]);
        else
            return null;
    }
    //部码
    $.ajax({
        url     : '/feapi/users/loginInfo' + '?userToken=' + getCookie('userToken'),
        dataType: 'json',
        data    : {},
        async   : false,
        type    : 'GET',
        success : function (str) {
            if(str.code == "200000"){
                if (str.data.status.code == 200) {
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

    $.xxdAjax({
        url     : '/activityCenter/specialActivities/todayLogedUserCount',
        dataType: 'json',
        clientId: "XXD_ACTIVITY_H5_PAGE",
        data    : {},
        type    : 'GET',
        callbacks : function (str) {
            // console.log(str);
            if (str.code == 200000) {
                $('#people').text(str.data.todayLogedUserCount);
            }
        },
        error   : function (str) {
            alert('数据查询失败，请联系客服')
        }
    });
    $(document).on('click', '.open_app', function () {
        var thisUrl = $(this).data('url');
        openApp(thisUrl);
    });
    $(document).on('click', '.down_app', function () {
        var dmp_app_source= getDmpUrlParam("dmp_app_source");
        if(dmp_app_source){
            window.location.href = 'http://' + window.location.host + '/m/static/html/download/app.html?model=auto&dmp_app_source='+dmp_app_source;

        }else{
            window.location.href = 'http://' + window.location.host + '/m/static/html/download/app.html?model=auto';
        }
    });
    function getDmpUrlParam(item){
        var pattern = new RegExp("[?&]"+item+"\=([^&]+)", "g");
        var matcher = pattern.exec(location.href);
        var items = null;
        if(null != matcher){
            try{
                items = decodeURIComponent(decodeURIComponent(matcher[1]));
            }catch(e){
                try{
                    items = decodeURIComponent(matcher[1]);
                }catch(e){
                    items = matcher[1];
                }
            }
        }
        // console.log(items);
        return items;
    }
    function openApp(thisUrl) {
        var schemeUrl = thisUrl;
        if (navigator.userAgent.match(/(iPhone|iPod|iPad)/i)) {
            var loadDateTime = new Date();
            window.setTimeout(function () {
                var timeOutDateTime = new Date();
                if (timeOutDateTime - loadDateTime < 5000) {
                    showDownApp()
                } else {
                    alert('无法打开');
                }
            }, 100);
            window.location.href = schemeUrl;
        } else if (navigator.userAgent.match(/android/i)) {


            var loadDateTime2 = new Date();
            window.setTimeout(function () {
                var timeOutDateTime2 = new Date();
                if (timeOutDateTime2 - loadDateTime2 < 5000) {
                    showDownApp()
                } else {
                    alert('无法打开');
                }
            }, 100);
            window.location.href = schemeUrl;

        }
    }

    var thisTime,countdownTime;
    function showDownApp() {

        if($('#toapp').length > 0){
            $('#toapp').addClass('show').fadeIn(200);
        }else{
            $('<div id="toapp" style="display: none" class="to-app show"><div><div><b id="chart1" class="mc-chart" data-sum="360"><i><em></em></i><i><em></em></i><b id="countdown">8</b></b><h1>请先下载客户端</h1><p>拥抱梦想 亦懂得积累</p><ul><li><button class="x_downapp">就看网页版</button></li><li><button class="down_app">下载客户端</button></li></ul></div></div></div>').appendTo('body').stop().fadeIn(200);
        }

        var countdownNb=8;
        countdownTime = setInterval(function () {
            countdownNb--;
            if(countdownNb >= 0){
                $('#countdown').text(countdownNb)
            }else{
                clearInterval(countdownTime);
            }

        },1000);

        thisTime = setTimeout(function () {
            closeDownApp()
        }, 8000)

    }

    $(document).on('click', '.x_downapp', function () {
        closeDownApp();
    });
    function closeDownApp() {
        clearTimeout(thisTime);
        clearInterval(countdownTime);
        $('#toapp').removeClass('show').fadeOut(200,function () {
            $('#toapp').remove();
        });
    }
}, function (err) {
    console.log(err);
});