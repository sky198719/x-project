require(['base', 'float','trackBase', 'store', "dialog", "requirejs", "paging"], function ($, float,track, store, dialog) {
    //公用变量
    var isLogin = 0; //是否登录 0已登录 1未登录
    var gameTime = 0; //是否开始 0已开始 1未开始 2已结束
    var timer = new Date();
    var myTime = timer.getTime(); //本地时间
    var formToken; //表单token
    var mobileReg = /^(((13[0-9]{1})|(14(0-9){1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/; //验证手机号码是否合法
    var passwordReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,16}$/; //验证密码是否合法

    //新元宝状态
    var xybNewId;
    var xybNewLeft;

    //时间判断
    var newDay;
    var newHour;

    //读取cookie
    function getCookie(name){
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg)){
            return unescape(arr[2]);
        }else{
            return null;
        }
    }
    var realToken;
    if(getCookie('Token') != '' && getCookie('Token') != undefined && getCookie('Token') != null){
        realToken = getCookie('Token');
    }else{
        realToken = getCookie('userToken');
    }

    //获取formToken
    $.ajax({
        url:'/feapi/users/formToken',
        type:'get',
        dataType:'json',
        success:function(data){
            formToken = data.token;
            //获取图片验证码
            $('#imgUrl').attr('src','/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

    //改变图片验证码
    $('#imgUrl').click(function(){
        $('#imgUrl').attr('src','/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
    });

    //获取短信验证码
    var clickFlag = 0;
    $('#mobileUrl').click(function(){
        if(clickFlag == 0){
            clickFlag = 1;
            //前端验证
            if(!mobileReg.test($('#mobileNo').val())){
                $('#imgUrl').attr('src','/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                $('#voiceCode').html('请输入有效的手机号码！');
                clickFlag = 0;
                return false;
            }if(!passwordReg.test($('#password').val())){
                $('#imgUrl').attr('src','/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                $('#voiceCode').html('密码必须是6-16位的数字和字母的组合！');
                clickFlag = 0;
                return false;
            }if($('#imgCode').val() == '' || $('#imgCode').val() == null || $('#imgCode').val() == undefined){
                $('#imgUrl').attr('src','/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                $('#voiceCode').html('请输入图片验证码！');
                clickFlag = 0;
                return false;
            }else{
                //验证手机号码
                $.ajax({
                    url:'/feapi/users/checkMobile',
                    type:'post',
                    data:{formtoken:formToken,mobile:$('#mobileNo').val()},
                    success:function(data){
                        if(data.code != 1){
                            $('#imgUrl').attr('src','/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                            $('#voiceCode').html(data.info);
                            clickFlag = 0;
                            return false;
                        }else{
                            //获取短信验证码
                            $.ajax({
                                url:'/feapi/users/sendSMS',
                                type:'post',
                                data:{formtoken:formToken,imgcode:$('#imgCode').val(),mobile:$('#mobileNo').val()},
                                success:function(data){
                                    if(data.code != 200000){
                                        $('#imgUrl').attr('src','/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                                        $('#voiceCode').html(data.info);
                                        clickFlag = 0;
                                        return false;
                                    }else{
                                        $('#voiceCode').html('');
                                        $('#mobileUrl').addClass('dis');
                                        var t;
                                        var openTime = 60;
                                        $('#mobileUrl').html(openTime + 's重新发送');
                                        t = setInterval(function(){
                                            openTime--;
                                            $('#mobileUrl').html(openTime + 's重新发送');
                                            if(openTime == 0){
                                                clearInterval(t);
                                                $('#mobileUrl').html('获取验证码');
                                                clickFlag = 0;
                                                $('#mobileUrl').removeClass('dis');
                                            }
                                        },1000);
                                    }
                                },
                                error:function(){
                                    alert('网络异常，请重试！');
                                    return false;
                                }
                            });
                        }
                    },
                    error:function(){
                        alert('网络异常，请重试！');
                        return false;
                    }
                });
            } 
        }else{
            return false;
        }
    });

    //表单提交
    $('#register').click(function(){
        if(!mobileReg.test($('#mobileNo').val())){ 
            $('#voiceCode').html('请输入有效的手机号码！'); 
            return false;
        }if(!passwordReg.test($('#password').val())){
            $('#voiceCode').html('密码必须是6-16位的数字和字母的组合！');
            return false;
        }if(!$('#ruleCheck').is(':checked')){
            $('#voiceCode').html('请先阅读并同意《新新贷使用协议》！');
            return false;
        }if($('#mobileCode').val() == '' || $('#mobileCode').val() == null || $('#mobileCode').val() == undefined){
            $('#voiceCode').html('请输入手机验证码！');
            return false;
        }else{
            $.ajax({
                url:'/feapi/users/regV3',
                type:'post',
                dataType:'json',
                data:{formtoken:formToken,mobile:$('#mobileNo').val(),password:$('#password').val(),smscode:$('#mobileCode').val()},
                success:function(data){
                    if (data.regResultCode === '200000') {
                        $('#voiceCode').html('');
                        alertBox('注册成功','index.html?xxd_utm_source=AnEzUn');
                    }else{
                        $('#voiceCode').html(data.info);
                        return false;
                    }
                },
                error:function(){
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
    $.ajax({
        url:'/feapi/users/loginInfo?userToken=' + realToken,
        type:'get',
        dataType:'json',
        success:function(data){
            if(data.code == '200000'){
                if(data.data.status.code == 200){
                    isLogin = 0;
                    $('.registerBox').fadeOut(0);
                    $('.loginBox').fadeIn(0);
                    $('.bottomLayer').fadeOut(0);
                    $('.footer').css('margin-bottom','0px');
                    $('.loginBox span').html('亲爱的' + data.data.name);
                    //$('#btn3').css('background','url(img/p5.png) 0 0 no-repeat');
                }
            }else{
                isLogin = 1;
                $('.registerBox').fadeIn(0);
                $('.loginBox').fadeOut(0);
                $('.bottomLayer').fadeIn(0);
                $('.footer').css('margin-bottom','110px');
                //$('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
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
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

    //登录
    function loginCheck(){
        var loginInitCheck;
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
                    }
                }else{
                    loginInitCheck = 1;
                }
            },
            error:function(){
                alert('网络异常，请重试！');
                return false;
            }
        });
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
                alert(data.message);
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
            alert('网络异常，请重试！');
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

    //新元宝
    var xybLeft = 0;
    function xybCheck(term){
        timer = new Date();
        myTime = timer.getTime();
        newDay = timer.getDay();
        newHour = timer.getHours();
        $.ajax({
            url:'/activityCenter/doubleDanActivityBase/getXYBSchemeByTerm?term=' + term,
            type:'get',
            async:false,
            cache:false,
            beforeSend:function(request){
                request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                request.setRequestHeader('clientTime',myTime);
            },
            success:function(data){
                if(data.code == '200000'){
                    xybNewId = data.data.scheme;
                    xybNewLeft = data.data.jiaxibiao;
                    if(data.data.end == true){
                        xybLeft = 1;
                    }else{
                        xybLeft = 0;
                    }
                }
            },
            error:function(){
                alert('网络异常，请重试！');
                return false;
            }
        });
    }
    $.ajax({
        url:'/tradeCenter/XYB/brief',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_FRONT_END');
            request.setRequestHeader('clientTime',myTime);
            request.setRequestHeader('s','s');
        },
        success:function(data){
            if(data.code == '200000'){
                $.each(data.data.items,function(index,item){
                    if(item.frozenPeriod == '6'){
                        //$('#xyb6Rate1').html(item.plannedAnnualRateFrom);
                        //$('#xyb6Rate2').html(item.plannedAnnualRateFrom);
                        //$('#xyb6Float1').html(item.floatingRate);
                        //$('#xyb6Float2').html(item.floatingRate);
                        //$('#xyb6Rate').html(item.plannedAnnualRateFrom + item.floatingRate);
                        //$('#xyb6Income').html($('#xyb6Rate').html() * 10000 * 6 / 12 / 100 + 108);
                    }else if(item.frozenPeriod == '12'){
                        //$('#xyb12Rate1').html(item.plannedAnnualRateFrom);
                        //$('#xyb12Rate2').html(item.plannedAnnualRateFrom);
                        //$('#xyb12Float1').html(item.floatingRate);
                        //$('#xyb12Float2').html(item.floatingRate);
                        //$('#xyb12Rate').html(item.plannedAnnualRateFrom + item.floatingRate);
                        //$('#xyb12Income').html($('#xyb12Rate').html() * 10000 * 12 / 12 / 100 + 108);
                    }
                });
            }
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

    //活动状态
    $.ajax({
        url:'/activityCenter/anniversaryActivityBase/getActivityStatus?activityCode=Anniversary-activity',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
            request.setRequestHeader('clientTime',myTime);
        },
        success:function(data){
            if(data.data.data.activityStatus == -1){
                $('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
                $('#btn1').click(function(){
                    if(loginCheck() == 0){
                        alertBox('即将跳转至活动页抽奖','index.html?xxd_utm_source=AnEzUn');
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
                            alertBox('加息活动未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
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
                            alertBox('加息活动未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
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
            }else if(data.data.data.activityStatus == 1){
                $('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
                $('#btn1').click(function(){
                    if(loginCheck() == 0){
                        alertBox('即将跳转至活动页抽奖','index.html?xxd_utm_source=AnEzUn');
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
                            alertBox('加息活动已结束，购买普通标','/xplan/detail/' + xybNewId + '.html');
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
                            alertBox('加息活动已结束，购买普通标','/xplan/detail/' + xybNewId + '.html');
                        }else{
                            window.location.href = '/xplan/detail/' + xybNewId + '.html';
                        }
                    }else{
                        window.location.href = '#bg1';
                    }
                });
                $('#rewardList').html('活动已结束');
            }else if(data.data.data.activityStatus == 0){
                $('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
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
                                        alertBox('已经抢完啦，购买普通标','/xplan/detail/' + xybNewId + '.html');
                                    }else{
                                        window.location.href = '/xplan/detail/' + xybNewId + '.html';
                                    }
                                }
                            }
                        }else{
                            if(xybLeft == 0){
                                alertBox('加息未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
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
                                        alertBox('已经抢完啦，购买普通标','/xplan/detail/' + xybNewId + '.html');
                                    }else{
                                        window.location.href = '/xplan/detail/' + xybNewId + '.html';
                                    }
                                }
                            }
                        }else{
                            if(xybLeft == 0){
                                alertBox('加息未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
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
                    url:'/activityCenter/anniversaryActivityBase/getLatestAwardsList?activityCode=Anniversary-activity',
                    type:'get',
                    async:false,
                    cache:false,
                    beforeSend:function(request){
                        request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                        request.setRequestHeader('clientTime',myTime);
                    },
                    success:function(data){
                        if(data.code == '200000'){
                            $.each(data.data.prize,function(index,item){
                                $('#rewardList ul').append('<li><strong>' + item.username + '</strong><span>' + item.prizename + '</span></li>');
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
                        alert('网络异常，请重试！');
                        return false;
                    }
                });
                $.ajax({
                    url:'/feapi/users/loginInfo?userToken=' + realToken,
                    type:'get',
                    dataType:'json',
                    success:function(data){
                        if(data.code == '200000'){
                            if(data.data.status.code == 200){
                                isLogin = 0;
                                $('#btn1').click(function(){
                                    alertBox('即将跳转至活动页抽奖','index.html?xxd_utm_source=AnEzUn');
                                });
                                $('#btn2').click(function(){
                                    window.location.href = '/';
                                });
                                //初始化信息
                                $.ajax({
                                    url:'/activityCenter/anniversaryActivityBase/initial?activityCode=Anniversary-activity',
                                    type:'get',
                                    async:false,
                                    cache:false,
                                    beforeSend:function(request){
                                        request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                                        request.setRequestHeader('clientTime',myTime);
                                        request.setRequestHeader('token',realToken);
                                    },
                                    success:function(data){
                                        $('#annualAccount').html(Math.round(parseFloat(data.data.data.annualAccount)*100)/100);
                                        if(data.data.data.isNewUser == false){
                                            $('#newReward').fadeOut(0);
                                            $('#bg3').css('height','505px');
                                        }else if(data.data.data.isNewUser == true){
                                            if(data.data.data.annualAccount < 10000){
                                                $('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
                                            }else if(data.data.data.annualAccount >= 10000){
                                                $('#btn3').click(function(){
                                                    alertBox('即将跳转至活动页领卡','index.html?xxd_utm_source=AnEzUn');
                                                });
                                            }
                                        }
                                    },
                                    error:function(){
                                        alert('网络异常，请重试！');
                                        return false;
                                    }
                                });
                            }
                        }else{
                            isLogin = 1;
                            $('#btn1').click(function(){
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
                        }
                    },
                    error:function(){
                        alert('网络异常，请重试！');
                        return false;
                    }
                });
            }
        },
        error:function(){
            alert('网络异常，请重试！');
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
    else alert(err);
});