require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float, track, store) {
    // 布码init
    var userDO = store && store.get("userDO") || {};
    track.init(userDO);

    var object = [
            $('#object1'), $('#object2'), $('#object3'), $('#object4'), $('#object5'), $('#object6'), $('#object7'), $('#object8'), $('#object9'), $('#object10'),
            $('#object11'), $('#object12'), $('#object13'), $('#object14'), $('#object15'), $('#object16'), $('#object17'), $('#object18'), $('#object19'),
            $('#object20'), $('#object21'), $('#object22')
        ],
        number = [6, 2, 1, 5, 2, 4, 3, 7, 1, 2, 4, 1, 6, 3, 5, 8, 5, 2, 7, 4, 2, 5],
        object2 = [];


    var islogin = isLogin();

    //二维码
    $('#product1').click(function () {
        if (islogin) {
            $('.dimension2,.dimension3').removeClass('show');
            $('.dimension1').toggleClass('show');
        } else {
            window.location.href = '/user/ilogin.html';
        }
    });
    $('#product2').click(function () {
        if (islogin) {
            $('.dimension1,.dimension3').removeClass('show');
            $('.dimension2').toggleClass('show');
        } else {
            window.location.href = '/user/ilogin.html';
        }
    });
    $('.to-scan').click(function () {
        $('.dimension1,.dimension2').removeClass('show');
        $('.dimension3').toggleClass('show');
    });

    //判断是否登录
    function isLogin() {
        var result = false;
        $.ajax({
            type: 'GET',
            url: '/feapi/users/loginInfo' + '?userToken=' + getCookie('Token'),
            async: false,
            data: {},
            dataType: 'json',
            success: function (str) {
                if (str.code == "200000") {
                    if (str.data.status.code == 200) {
                        result = true;
                    } else {
                        result = false;
                    }
                } else {
                    result = false;
                }
            },
            error: function () {
                float.alert({content: msg.errorMsg});
            }
        });
        return result;
    }


    //签到
    /*$.ajax({
        url: '/activityCenter/specialActivities/loginStatus',
        type: 'get',
        data: {},
        dataType: 'json',
        beforeSend: function (request) {
            request.setRequestHeader("clientId", 'XXD_ACTIVITY_H5_PAGE');
            request.setRequestHeader("clientTime", new Date().getTime());
            request.setRequestHeader("token", getCookie('Token'));
        },
        success: function (data) {
            if (data.code == '200000') {
                if (isLogin()) {
                    $.each(data.data.items, function (i, v) {
                        if(i <=data.data.sysdate ){
                            $(object[i]).attr('sign', v);
                            if ($(object[i]).attr('sign') == 'true') {
                                $(object[i]).append("<span class='signed-coin'> " +
                                    "<img src='img/coin.png'> " +
                                    "<i>+</i> <i>" + number[i] + "</i>" +
                                    "</span>").addClass('signed');
                                $('#object22').find('.signed-coin').remove();
                            }
                        }
                    });
                    $.each(object, function (i, v) {
                        if ((i > data.data.sysdate) &&  (data.data.sysdate != -1)) {
                            object2.push(v);
                        }
                    });
                    if(data.data.sysdate != -1){
                        $.each(object2, function (i, v) {
                            $(v).append("<span class='signed-coin'> " +
                                "<img src='img/coin.png'> " +
                                "<i>+</i> <i>?</i>" +
                                "</span>");
                            $('#object22 span').remove();
                        });
                    }
                }
            }
        }
    });*/
    $.xxdAjax({
        url: '/activityCenter/specialActivities/loginStatus',
        clientId: 'XXD_ACTIVITY_H5_PAGE',
        type: 'GET',
        token: getCookie('Token'),
        data: {},
        dataType: 'json',
        callbacks: function (data) {
            if (data.code == '200000') {
                if (isLogin()) {
                    $.each(data.data.items, function (i, v) {
                        if(i <=data.data.sysdate ){
                            $(object[i]).attr('sign', v);
                            if ($(object[i]).attr('sign') == 'true') {
                                $(object[i]).append("<span class='signed-coin'> " +
                                    "<img src='img/coin.png'> " +
                                    "<i>+</i> <i>" + number[i] + "</i>" +
                                    "</span>").addClass('signed');
                                $('#object22').find('.signed-coin').remove();
                            }
                        }
                    });
                    $.each(object, function (i, v) {
                        if ((i > data.data.sysdate) &&  (data.data.sysdate != -1)) {
                            object2.push(v);
                        }
                    });
                    if(data.data.sysdate != -1){
                        $.each(object2, function (i, v) {
                            $(v).append("<span class='signed-coin'> " +
                                "<img src='img/coin.png'> " +
                                "<i>+</i> <i>?</i>" +
                                "</span>");
                            $('#object22 span').remove();
                        });
                    }
                }
            }
        },
        error: function () {
            float.alert({content: msg.errorMsg});
        }
    });


    //查询总人数
    getPeople();
    function getPeople() {
        /*$.ajax({
            type: 'GET',
            url: '/activityCenter/specialActivities/todayLogedUserCount',
            data: {},
            dataType: 'json',
            beforeSend: function (request) {
                request.setRequestHeader("clientId", 'XXD_ACTIVITY_H5_PAGE');
                request.setRequestHeader("clientTime", new Date().getTime());
            },
            success: function (data) {
                if (data.code == 200000) {
                    $('#userNumber').html(data.data.todayLogedUserCount);
                }
            },
            error: function () {
                float.alert({content: msg.errorMsg});
            }
        });*/
        $.xxdAjax({
            url: '/activityCenter/specialActivities/todayLogedUserCount',
            clientId: 'XXD_ACTIVITY_H5_PAGE',
            type: 'GET',
            data: {},
            dataType: 'json',
            callbacks: function (data) {
                if (data.code == 200000) {
                    $('#userNumber').html(data.data.todayLogedUserCount);
                }
            },
            error: function () {
                float.alert({content: msg.errorMsg});
            }
        });
    }


    //获取cookie
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg)) {
            return (arr[2]);
        }
        else {
            return null;
        }
    }


}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});