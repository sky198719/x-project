require(['base', 'float','trackBase', 'store', "dialog", "requirejs", "paging","md5"], function ($, float,track, store, dialog) {
    //公用变量
    var isLogin = 0; //是否登录 0已登录 1未登录
    var gameTime = 0; //是否开始 0已开始 1未开始 2已结束
    var formToken; //表单token
    var mobileReg = /^(((13[0-9]{1})|(14(0-9){1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/; //验证手机号码是否合法
    var passwordReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,16}$/; //验证密码是否合法

    //_状态
    var xybNewId;
    var xybNewLeft;

    //时间判断
    var newDay;
    var newHour;


    //读取cookie
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg)) {
            return unescape(arr[2]);
        } else {
            return null;
        }
    }

    var realToken;
    if (getCookie('Token') != '' && getCookie('Token') != undefined && getCookie('Token') != null) {
        realToken = getCookie('Token');
    } else {
        realToken = getCookie('userToken');
    }

    //获取formToken
    $.ajax({
        url: '/feapi/users/formToken',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            formToken = data.token;
            //获取图片验证码
            $('#imgUrl').attr('src', '/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
        },
        error: function () {
            // alert('网络异常，请重试！');
            return false;
        }
    });

    //改变图片验证码
    $('#imgUrl').click(function () {
        $('#imgUrl').attr('src', '/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
    });

    //获取短信验证码
    var clickFlag = 0;
    $('#mobileUrl').click(function () {
        if (clickFlag == 0) {
            clickFlag = 1;
            //前端验证
            if (!mobileReg.test($('#mobileNo').val())) {
                $('#imgUrl').attr('src', '/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                $('#voiceCode').html('请输入有效的手机号码！');
                clickFlag = 0;
                return false;
            }
            if (!passwordReg.test($('#password').val())) {
                $('#imgUrl').attr('src', '/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                $('#voiceCode').html('密码必须是6-16位的数字和字母的组合！');
                clickFlag = 0;
                return false;
            }
            if ($('#imgCode').val() == '' || $('#imgCode').val() == null || $('#imgCode').val() == undefined) {
                $('#imgUrl').attr('src', '/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                $('#voiceCode').html('请输入图片验证码！');
                clickFlag = 0;
                return false;
            } else {
                //验证手机号码
                $.ajax({
                    url: '/feapi/users/checkMobile',
                    type: 'post',
                    data: {formtoken: formToken, mobile: $('#mobileNo').val()},
                    success: function (data) {
                        if (data.code != 1) {
                            $('#imgUrl').attr('src', '/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                            $('#voiceCode').html(data.info);
                            clickFlag = 0;
                            return false;
                        } else {
                            //获取短信验证码
                            $.ajax({
                                url: '/feapi/users/sendSMS',
                                type: 'post',
                                data: {
                                    formtoken: formToken,
                                    imgcode: $('#imgCode').val(),
                                    mobile: $('#mobileNo').val()
                                },
                                success: function (data) {
                                    if (data.code != 200000) {
                                        $('#imgUrl').attr('src', '/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                                        $('#voiceCode').html(data.info);
                                        clickFlag = 0;
                                        return false;
                                    } else {
                                        $('#voiceCode').html('');
                                        $('#mobileUrl').addClass('dis');
                                        var t;
                                        var openTime = 60;
                                        $('#mobileUrl').html(openTime + 's重新发送');
                                        t = setInterval(function () {
                                            openTime--;
                                            $('#mobileUrl').html(openTime + 's重新发送');
                                            if (openTime == 0) {
                                                clearInterval(t);
                                                $('#mobileUrl').html('获取验证码');
                                                clickFlag = 0;
                                                $('#mobileUrl').removeClass('dis');
                                            }
                                        }, 1000);
                                    }
                                },
                                error: function () {
                                    // alert('网络异常，请重试！');
                                    return false;
                                }
                            });
                        }
                    },
                    error: function () {
                        // alert('网络异常，请重试！');
                        return false;
                    }
                });
            }
        } else {
            return false;
        }
    });

    //根据QueryString参数名称获取值

    function getQueryStringByName(name) {

        var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));

        if (result == null || result.length < 1) {

            return "";

        }

        return result[1];

    }

    //表单提交
    $('#register').click(function () {
        if (!mobileReg.test($('#mobileNo').val())) {
            $('#voiceCode').html('请输入有效的手机号码！');
            return false;
        }
        if (!passwordReg.test($('#password').val())) {
            $('#voiceCode').html('密码必须是6-16位的数字和字母的组合！');
            return false;
        }
        if (!$('#ruleCheck').is(':checked')) {
            $('#voiceCode').html('请先阅读并同意《_使用协议》！');
            return false;
        }
        if ($('#mobileCode').val() == '' || $('#mobileCode').val() == null || $('#mobileCode').val() == undefined) {
            $('#voiceCode').html('请输入手机验证码！');
            return false;
        } else {
            $.ajax({
                url: '/feapi/users/regV3',
                type: 'post',
                dataType: 'json',
                data: {
                    formtoken: formToken,
                    mobile: $('#mobileNo').val(),
                    password: $('#password').val(),
                    smscode: $('#mobileCode').val()
                },
                success: function (data) {
                    if (data.regResultCode === '200000') {
                        $('#voiceCode').html('');
                        alertBox('注册成功', 'index.html?xxd_utm_source=AnEzUn');
                    } else {
                        $('#voiceCode').html(data.info);
                        return false;
                    }
                },
                error: function () {
                    alert('网络异常，请重试！');
                    return false;
                }
            });
            gtag('event', 'purchase', {
                'allow_custom_scripts': true,
                'value': '',
                'transaction_id': '',
                'quantity': '',
                'send_to': 'DC-8281908/sales/gi0bcxcp+items_sold'
            });
        }
    });


    //登录判断
   if(realToken!==null){
       $.ajax({
           url: '/feapi/users/loginInfo?userToken=' + realToken,
           type: 'get',
           dataType: 'json',
           success: function (data) {
               if (data.code == '200000') {
                   if (data.data.status.code == 200) {
                       isLogin = 0;
                       window.location.href = './index.html';
                       $('.registerBox').fadeOut(0);
                       $('.loginBox').fadeIn(0);
                       $('.bottomLayer').fadeOut(0);
                       $('.footer').css('margin-bottom', '0px');
                       $('.loginBox span').html('亲爱的' + data.data.name);
                       //$('#btn3').css('background','url(img/p5.png) 0 0 no-repeat');
                   }
               } else {
                   noLogin();
               }

           },
           error: function () {
               // alert('网络异常，请重试！');
               return false;
           }
       });
   }else{
       noLogin();
   }

    function noLogin(){
        isLogin = 1;
        $('.registerBox').fadeIn(0);
        $('.loginBox').fadeOut(0);
        $('.bottomLayer').fadeIn(0);
        $('.footer').css('margin-bottom', '110px');
        //$('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
        if ($('body').scrollTop() >= $(window).height()) {
            $('.bottomLayer').fadeIn(0);
        } else {
            $('.bottomLayer').fadeOut(0);
        }
        if ($('html').scrollTop() >= $(window).height()) {
            $('.bottomLayer').fadeIn(0);
        } else {
            $('.bottomLayer').fadeOut(0);
        }
    }


    //登录
    function loginCheck(){
        var loginInitCheck;
        if(realToken!==null){
            $.ajax({
                url:'/feapi/users/loginInfo?userToken=' + realToken,
                type:'get',
                dataType:'json',
                async:false,
                cache:false,
                success:function(data){
                    if(data.code == '200000'){
                        if(data.data.status.code == 200){
                            loginInitCheck = 0;
                            window.location.href='./index.html';
                        }
                    }else{
                        loginInitCheck = 1;
                    }
                },
                error:function(){
                    // alert('网络异常，请重试！');
                    return false;
                }
            });
        }else{
            loginInitCheck=1;
        }

        return loginInitCheck;
    }

    //布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);

    //弹出框
    function alertBox(msg,link){
        $('.toast span').html(msg);
        $('.toast').css('margin-left',-$('.toast').width()/2 - 15 + 'px');
        $('.toast').fadeIn(0);
        //$('.outLayer').fadeIn(0);
        if(link == 1){
            setTimeout(function(){
                $('.toast').fadeOut(0);
            },3000);
        }else{
            setTimeout(function(){
                window.location.href = link;
            },3000);
        }
    }

    //活动弹窗
    $('#rule').click(function(){
        $('.outLayer').fadeIn(0);
        $('.eventRule').fadeIn(0);
    });
    $('.close').click(function(){
        $('.outLayer').fadeOut(0);
        $('.eventRule').fadeOut(0);
    });
    var newFlag = 0;
    $('#newClose').click(function(){
        newFlag = 1;
        $('.rightBox').fadeOut(0);
    });

    //浮层
    $(window).scroll(function(){
        if(isLogin == 1){
            if($('body').scrollTop() >= $(window).height()){
                $('.bottomLayer').fadeIn(0);
            }else{
                $('.bottomLayer').fadeOut(0);
            }
            if($('html').scrollTop() >= $(window).height()){
                $('.bottomLayer').fadeIn(0);
            }else{
                $('.bottomLayer').fadeOut(0);
            }
        }
        if(newFlag == 0){
            if($('body').scrollTop() >= $(window).height()){
                $('.rightBox').fadeIn(0);
            }else{
                $('.rightBox').fadeOut(0);
            }
            if($('html').scrollTop() >= $(window).height()){
                $('.rightBox').fadeIn(0);
            }else{
                $('.rightBox').fadeOut(0);
            }
        }
    });
    if($('body').scrollTop() >= $(window).height()){
        $('.rightBox').fadeIn(0);
    }else{
        $('.rightBox').fadeOut(0);
    }
    if($('html').scrollTop() >= $(window).height()){
        $('.rightBox').fadeIn(0);
    }else{
        $('.rightBox').fadeOut(0);
    }

    //运营数据
    function fmoney(s, n) { 
        n = n > 0 && n <= 20 ? n : 2; 
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
        t = ""; 
        for (i = 0; i < l.length; i++) { 
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
        } 
        return t.split("").reverse().join("") + "." + r; 
    }

    function fpeople(num) {  
        var result = [ ], counter = 0;  
        num = (num || 0).toString().split('');  
        for (var i = num.length - 1; i >= 0; i--) {  
            counter++;  
            result.unshift(num[i]);  
            if (!(counter % 3) && i != 0) { result.unshift(','); }  
        }  
        return result.join('');  
    }

    $.ajax({
        url:'/biz/bulletin/operationData',
        contentType: "application/json",
        dataType: "json",
        type: "get",
        beforeSend:function(request){
            request.setRequestHeader("s", "www");
            request.setRequestHeader("clientId", "001");
            request.setRequestHeader("clientTime", "001");
        },
        success:function(data){
            if(data.code != '200000'){
                // alert(data.message);
                return false;
            }else{
                var eventTime = data.data.time;
                $('#timeYear').html(eventTime.substring(0,eventTime.indexOf('年')));
                $('#timeDay').html(eventTime.substring(eventTime.indexOf('年')+1,eventTime.indexOf('天')));
                $.each(data.data.items,function(index,item){
                    if(item.code == 'TOTAL_REGISTER_USER'){
                        $('#totalRegister').html(fpeople(parseInt(item.nvalue)));
                    }else if(item.code == 'TOTAL_TRADE'){
                        $('#totalCount').html(Math.floor(item.nvalue/100000000*100)/100);
                    }else if(item.code == 'TOTAL_INCOME'){
                        $('#totalIncome').html(Math.floor(item.nvalue/100000000*100)/100);
                    }
                });
            }
        },
        error:function(){
            // alert('网络异常，请重试！');
            return false;
        }
    });

    //游戏动画
    //$('#awardList').find('li').eq(0).fadeIn(0);
    var gameIndex = 0;
    var gameT;
    function gameAnimate(speed,show){
        gameT = setInterval(function(){
            gameIndex++;
            if(gameIndex >= 7){
                gameIndex = 0;
            }
            $('#awardList').find('li').fadeOut(show).eq(gameIndex).fadeIn(show);
        },speed);
    }
    //gameAnimate(2000,500);

    $.ajax({
        url:'/activityCenter/anniversary6th/propagandaPageData',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
            request.setRequestHeader('clientTime',new Date().getTime());
            request.setRequestHeader('s','s');
        },
        success:function(data){
            console.log(data);
            $('#xyb6Rate1').text(data.data.data.xybOfNewInfo.APR);
            $('#xyb6Float1').text(data.data.data.xybOfNewInfo.FLOATAPR);
            $('#xyb12Rate1').text(data.data.data.xybScope.MINAPR);
            $('#xyb12Float1').text(data.data.data.xybScope.MAXAPR);
            var xybOfNewInfoAPR=data.data.data.xybOfNewInfo.APR;
            var xybOfNewInfoF=data.data.data.xybOfNewInfo.FLOATAPR;
            var oneMounAPR=0;
            var threeMounAPR=0;
            for(var i=0;i<data.data.data.xybInfoList.length;i++){
                if(data.data.data.xybInfoList[i]['TERMS']==1){
                    oneMounAPR=data.data.data.xybInfoList[i]['APR'];
                }
                if(data.data.data.xybInfoList[i]['TERMS']==3){
                    threeMounAPR=data.data.data.xybInfoList[i]['APR'];
                }
            }



            $('#xyb6Income').text(((xybOfNewInfoAPR/100+xybOfNewInfoF/100)*10000/12+10000*oneMounAPR/100/12+108).toFixed(2));
            $('#xyb12Income').text(((xybOfNewInfoAPR/100+xybOfNewInfoF/100)*10000/12+50000*threeMounAPR/100/4+108).toFixed(2));

        },
        error:function(){
            return false;
        }
    });
    function SetCookie (name, value) {
        var exp = new Date();
        exp.setTime(exp.getTime() + (30*24*60*60*1000));
        window.document.cookie = name + "=" + escape (value) + "; expires=" + exp.toGMTString()+";path=/";
    }





    function fromartDate(dateParam){
        var durdate=new Date(dateParam);
        var month = durdate.getMonth()+1+'月';
        var date = durdate.getDate()+'日';
        return [month,date].join('');
    }

    function  fromartTime(time){
        var date= new Date(time);
        var h =date.getHours();
        var m=date.getMinutes();
        if(h<10){
            h='0'+h;
        }
        if(m<10){
            m='0'+m;
        }
        return h+':'+m;
    }

    //活动状态
    $.ajax({
        url:'/activityCenter/anniversary6th/activityStatus',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
            request.setRequestHeader('clientTime',new Date().getTime());
        },
        success:function(data){
            //set activity time

            var startDate=data.data.data.startTime;
            var endDate=data.data.data.endTime;

            startDate=fromartDate(startDate);
            endDate=fromartDate(endDate);

            var startTime= fromartTime(data.data.data.startTime);
            var endTime= fromartTime(data.data.data.endTime);

            $(".time-range").html(startDate+" "+startTime+'-'+endDate+endTime);

            // '-1表示活动未开始，-2活动已结束;0活动正常有效
            if(data.data.code == -1){
                $('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
                $('#btn1').click(function(){
                    if(loginCheck() == 0){
                        // alertBox('即将跳转至活动页抽奖','index.html?xxd_utm_source=AnEzUn');
                    }else{
                        window.location.href = '#bg1';
                    }
                });

                $('#btn12').click(function(){
                    if(loginCheck() == 0){
                        // alertBox('即将跳转至活动页抽奖','index.html?xxd_utm_source=AnEzUn');
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                $('#btn2').click(function(){
                    if(loginCheck() == 0){
                        window.location.href = '/';
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                $('#btn4').click(function(){
                    if(loginCheck() == 0){
                        xybCheck(6);
                        if(xybLeft == 0){
                            // alertBox('加息活动未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
                        }else{
                            window.location.href = '/xplan/detail/' + xybNewId + '.html';
                        }
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                $('#btn5').click(function(){
                    if(loginCheck() == 0){
                        xybCheck(12);
                        if(xybLeft == 0){
                            // alertBox('加息活动未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
                        }else{
                            window.location.href = '/xplan/detail/' + xybNewId + '.html';
                        }
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                $('#btn6').click(function(){
                    window.location.href = '#bg1';
                });
                $('#rewardList').html('活动未开始');
            }else if(data.data.code== -2){
                $('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
                $('#btn1').click(function(){
                    if(loginCheck() == 0){
                        // alertBox('即将跳转至活动页抽奖','index.html?xxd_utm_source=AnEzUn');
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                $('#btn12').click(function(){
                    if(loginCheck() == 0){
                        // alertBox('即将跳转至活动页抽奖','index.html?xxd_utm_source=AnEzUn');
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                $('#btn2').click(function(){
                    if(loginCheck() == 0){
                        window.location.href = '/';
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                $('#btn6').css('background','url(img/p7-2.png) 0 0 no-repeat');
                $('#btn4').click(function(){
                    if(loginCheck() == 0){
                        xybCheck(6);
                        if(xybLeft == 0){
                            // alertBox('加息活动已结束，购买普通标','/xplan/detail/' + xybNewId + '.html');
                        }else{
                            window.location.href = '/xplan/detail/' + xybNewId + '.html';
                        }
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                $('#btn5').click(function(){
                    if(loginCheck() == 0){
                        xybCheck(12);
                        if(xybLeft == 0){
                            // alertBox('加息活动已结束，购买普通标','/xplan/detail/' + xybNewId + '.html');
                        }else{
                            window.location.href = '/xplan/detail/' + xybNewId + '.html';
                        }
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                $('#rewardList').html('活动已结束');
            }else if(data.data.code== 0){
                $('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
                $('#btn1').click(function(){
                    window.location.href = '#bg1';
                });
                $('#btn12').click(function(){
                    window.location.href = '#bg1';
                });
                $('#btn2').click(function(){
                    window.location.href = '#bg1';
                });
                $('#btn4').click(function(){
                    window.location.href = '#bg1';
                });
                $('#btn5').click(function(){
                    window.location.href = '#bg1';
                });


                $('#btn6').click(function(){
                    window.location.href = '#bg1';
                });
                $('#btn4').click(function(){
                    if(loginCheck() == 0){
                        xybCheck(6);
                        //if(newDay == 3 || newDay == 5 || newDay == 2 && newHour >= 9 && newHour <24){
                        if(newDay == 3 || newDay == 5){
                            if(newHour < 9 && newHour > 0){
                                if(xybLeft == 0){
                                    window.location.href = '/xplan/detail/' + xybNewId + '.html';
                                }else{
                                    window.location.href = '/xplan/detail/' + xybNewId + '.html';
                                }
                            }else{
                                if(xybNewLeft == true){
                                    window.location.href = '/xplan/detail/' + xybNewId + '.html';
                                }else{
                                    if(xybLeft == 0){
                                        // alertBox('已经抢完啦，购买普通标','/xplan/detail/' + xybNewId + '.html');
                                    }else{
                                        window.location.href = '/xplan/detail/' + xybNewId + '.html';
                                    }
                                }
                            }
                        }else{
                            if(xybLeft == 0){
                                // alertBox('加息未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
                            }else{
                                window.location.href = '/xplan/detail/' + xybNewId + '.html';
                            }
                        }
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                $('#btn5').click(function(){
                    if(loginCheck() == 0){
                        xybCheck(12);
                        //if(newDay == 3 || newDay == 5 || newDay == 2 && newHour >= 9 && newHour <24){
                        if(newDay == 3 || newDay == 5){
                            if(newHour < 9 && newHour > 0){
                                if(xybLeft == 0){
                                    window.location.href = '/xplan/detail/' + xybNewId + '.html';
                                }else{
                                    window.location.href = '/xplan/detail/' + xybNewId + '.html';
                                }
                            }else{
                                if(xybNewLeft == true){
                                    window.location.href = '/xplan/detail/' + xybNewId + '.html';
                                }else{
                                    if(xybLeft == 0){
                                        // alertBox('已经抢完啦，购买普通标','/xplan/detail/' + xybNewId + '.html');
                                    }else{
                                        window.location.href = '/xplan/detail/' + xybNewId + '.html';
                                    }
                                }
                            }
                        }else{
                            if(xybLeft == 0){
                                // alertBox('加息未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
                            }else{
                                window.location.href = '/xplan/detail/' + xybNewId + '.html';
                            }
                        }
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                //获奖列表
                $.ajax({
                    url:'/activityCenter/anniversary6th/getPrizeBroadcastList?activityCode=sixth-anniversary',
                    type:'get',
                    async:false,
                    cache:false,
                    beforeSend:function(request){
                        request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                        request.setRequestHeader('clientTime',new Date().getTime());
                    },
                    success:function(data){
                        if(data.code == '200000'){
                            $.each(data.data.prize,function(index,item){
                                $('#rewardList ul').append('<li><strong>' + item.nickName + '</strong><span>' + item.prizeName + '</span></li>');
                            });
                            $('#rewardList ul').html($('#rewardList ul').html() + $('#rewardList ul').html());
                            var ulWidth = 0;
                            $.each($('#rewardList ul li'),function(){
                                ulWidth += $(this).width();
                            });
                            $('#rewardList ul').css('width',ulWidth + 'px');
                            if($('#rewardList ul li').length >= 4){
                                var leftMargin = 0;
                                var t = setInterval(function(){
                                    leftMargin--;
                                    if(-leftMargin >= ulWidth/2){
                                        leftMargin = 0;
                                    }
                                    $('#rewardList ul').css('margin-left',leftMargin + 'px');
                                },10);
                            }
                        }
                    },
                    error:function(){
                        // alert('网络异常，请重试！');
                        return false;
                    }
                });

            }
        },
        error:function(){
            // alert('网络异常，请重试！');
            return false;
        }
    });

//蜡烛效果
setInterval(function(){
    $.each($('#candleList li'),function(){
        $(this).animate({'margin-left':'-0.5px'},500);
        $(this).animate({'margin-left':'-1px'},500);
        $(this).animate({'margin-left':'0px'},500);
        $(this).animate({'margin-left':'0.5px'},500);
        $(this).animate({'margin-left':'0px'},500);
    });
},3000);

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    // else alert(err);
});