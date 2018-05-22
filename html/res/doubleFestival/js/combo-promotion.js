require(['base', 'float','trackBase', 'store', "dialog", "requirejs", "paging"], function ($, float,track, store, dialog) {
    //公用变量
    var isLogin = 0; //是否登录 0已登录 1未登录
    var gameTime = 0; //是否开始 0已开始 1未开始 2已结束
    var timer = new Date();
    var myTime = timer.getTime(); //本地时间
    var formToken; //表单token
    var mobileReg = /^(((13[0-9]{1})|(14(0-9){1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //验证手机号码是否合法
    var passwordReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/; //验证密码是否合法

    $(window).scroll(function(){
        if(isLogin == 1){
            if($('body').scrollTop() >= $(window).height()){
                $('.bottomBox').fadeIn(0);
            }else{
                $('.bottomBox').fadeOut(0);
            }
            if($('html').scrollTop() >= $(window).height()){
                $('.bottomBox').fadeIn(0);
            }else{
                $('.bottomBox').fadeOut(0);
            }
        }
    });

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
        url:'../../../feapi/users/formToken',
        type:'get',
        dataType:'json',
        success:function(data){
            formToken = data.token;
            //获取图片验证码
            $('#imgUrl').attr('src','../../../feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

    //改变图片验证码
    $('#imgUrl').click(function(){
        $('#imgUrl').attr('src','../../../feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
    });

    //获取短信验证码
    var clickFlag = 0;
    $('#mobileUrl').click(function(){
        if(clickFlag == 0){
            clickFlag = 1;
            //前端验证
            if(!mobileReg.test($('#mobileNo').val())){
                $('#imgUrl').attr('src','../../../feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                $('#voiceCode').html('请输入有效的手机号码！');
                clickFlag = 0;
                return false;
            }if(!passwordReg.test($('#password').val())){
                $('#imgUrl').attr('src','../../../feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                $('#voiceCode').html('密码必须是8-20位的数字和字母的组合！');
                clickFlag = 0;
                return false;
            }if($('#imgCode').val() == '' || $('#imgCode').val() == null || $('#imgCode').val() == undefined){
                $('#imgUrl').attr('src','../../../feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                $('#voiceCode').html('请输入图片验证码！');
                clickFlag = 0;
                return false;
            }else{
                //验证手机号码
                $.ajax({
                    url:'../../../feapi/users/checkMobile',
                    type:'post',
                    data:{formtoken:formToken,mobile:$('#mobileNo').val()},
                    success:function(data){
                        if(data.code != 1){
                            $('#imgUrl').attr('src','../../../feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                            $('#voiceCode').html(data.info);
                            clickFlag = 0;
                            return false;
                        }else{
                            //获取短信验证码
                            $.ajax({
                                url:'../../../feapi/users/sendSMS',
                                type:'post',
                                data:{formtoken:formToken,imgcode:$('#imgCode').val(),mobile:$('#mobileNo').val()},
                                success:function(data){
                                    if(data.code != 200000){
                                        $('#imgUrl').attr('src','../../../feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
                                        $('#voiceCode').html(data.info);
                                        clickFlag = 0;
                                        return false;
                                    }else{
                                        $('#voiceCode').html('');
                                        var t;
                                        var openTime = 60;
                                        t = setInterval(function(){
                                            openTime--;
                                            $('#mobileUrl').html(openTime + 's重新发送');
                                            if(openTime == 0){
                                                clearInterval(t);
                                                $('#mobileUrl').html('获取验证码');
                                                clickFlag = 0;
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
            $('#voiceCode').html('密码必须是8-20位的数字和字母的组合！');
            return false;
        }if(!$('#ruleCheck').is(':checked')){
            $('#voiceCode').html('请先阅读并同意《新新贷使用协议》！');
            return false;
        }if($('#mobileCode').val() == '' || $('#mobileCode').val() == null || $('#mobileCode').val() == undefined){
            $('#voiceCode').html('请输入手机验证码！');
            return false;
        }else{
            $.ajax({
                url:'../../../feapi/users/regV3',
                type:'post',
                dataType:'json',
                data:{formtoken:formToken,mobile:$('#mobileNo').val(),password:$('#password').val(),smscode:$('#mobileCode').val()},
                success:function(data){
                    if (data.regResultCode === '200000') {
                        $('#voiceCode').html('注册成功');
                        window.location.reload();
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
        }
    });

    //登录判断
    $.ajax({
        url:'../../../feapi/users/loginInfo?userToken=' + realToken,
        type:'get',
        dataType:'json',
        success:function(data){
            if(data.code == '200000'){
                if(data.data.status.code == 200){
                    isLogin = 0;
                    $('.loginBoxold').fadeIn(0);
                    $('.loginBoxold span').html('尊敬的' + data.data.name + '用户');
                    $('.bottomBox').fadeOut(0);
                    $('.footer').css('margin-bottom','0');
                    //查询抽奖次数
                    $.ajax({
                        url:'../../../activityCenter/doubleDanActivityBase/initial?activityCode=DoubleDan-activity',
                        type:'get',
                        async:false,
                        cache:false,
                        beforeSend:function(request){
                            request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                            request.setRequestHeader('clientTime',myTime);
                            request.setRequestHeader('token',realToken);
                        },
                        success:function(data){
                            if(data.code != '200000'){
                                alert(data.message);
                                return false;
                            }else{
                                var totalTimes = data.data.data.remainLotteryTimes;
                                //活动是否开始
                                $.ajax({
                                    url:'../../../activityCenter/doubleDanActivityBase/getActivityStatus?activityCode=DoubleDan-activity',
                                    type:'get',
                                    async:false,
                                    cache:false,
                                    beforeSend:function(request){
                                        request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                                        request.setRequestHeader('clientTime',myTime);
                                    },
                                    success:function(data){
                                        if(data.code != '200000'){
                                            alert(data.message);
                                            return false;
                                        }else{
                                            gameTime = data.data.data.activityStatus;
                                            if(gameTime == -1){
                                                $('.gameChance').html('有<a>？</a>次砸蛋机会');
                                            }else if(gameTime == 1){
                                                $('.gameChance').html('<a>活动已结束</a>');
                                            }else{
                                                $('.gameChance').html('有<a>' + totalTimes + '</a>次砸蛋机会');
                                            }
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
                isLogin = 1;
                $('.gameChance').html('<a href="/user/ilogin.html">【登录】</a>查看砸蛋机会');
                $('.registerBox').fadeIn(0);
                if($('body').scrollTop() >= $(window).height()){
                    $('.bottomBox').fadeIn(0);
                }else{
                    $('.bottomBox').fadeOut(0);
                }
                if($('html').scrollTop() >= $(window).height()){
                    $('.bottomBox').fadeIn(0);
                }else{
                    $('.bottomBox').fadeOut(0);
                }
            }
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

    var t1;
    //活动是否开始
    $.ajax({
        url:'../../../activityCenter/doubleDanActivityBase/getActivityStatus?activityCode=DoubleDan-activity',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
            request.setRequestHeader('clientTime',myTime);
        },
        success:function(data){
            if(data.code != '200000'){
                alert(data.message);
                return false;
            }else{
                gameTime = data.data.data.activityStatus;
                if(gameTime == -1){
                    $('#oneMonth1').fadeIn(0);
                    $('#twelveMonth1').fadeIn(0);
                    $('#oneMonth1').attr('src','img/promotion8.png');
                    $('#twelveMonth1').attr('src','img/promotion8.png');
                    $('.bottomBox img').click(function(){
                        window.location.href = "#bg1";
                    });
                    $('.userList ul').html('活动未开始');
                }else if(gameTime == 1){
                    $('#oneMonth1').fadeIn(0);
                    $('#twelveMonth1').fadeIn(0);
                    $('#oneMonth1').attr('src','img/promotion9.png');
                    $('#twelveMonth1').attr('src','img/promotion9.png');
                    $('.bottomBox img').attr('src','img/promotion6.png');
                    //获奖列表信息
                    $.ajax({
                        url:'../../../activityCenter/doubleDanActivityBase/getLatestMaterailAwardsList?activityCode=DoubleDan-activity&recordNum=35',
                        type:'get',
                        async:false,
                        cache:false,
                        beforeSend:function(request){
                            request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                            request.setRequestHeader('clientTime',myTime);
                        },
                        success:function(data){
                            if(data.code != '200000'){
                                alert(data.message);
                                return false;
                            }else{
                                $.each(data.data.prize,function(index,item){
                                    $('.userList ul').append('<li><strong>' + item.username + '</strong><em>' + item.prizename + '</em></li>');
                                });
                                if($('.userList ul li').length >= 3){
                                    var count = 0;
                                    var ulWidth = 0;
                                    $('.userList ul').html($('.userList ul').html() + $('.userList ul').html());
                                    $.each($('.userList ul li'),function(){
                                        ulWidth+=$(this).width();
                                    });
                                    $('.userList ul').css('width',ulWidth + 'px');
                                    t1 = setInterval(function(){
                                        count++;
                                        if(count >= $('.userList ul').width()/2){
                                            count = 0;
                                            $('.userList ul').css('margin-left',-count + 'px');
                                        }else{
                                            $('.userList ul').css('margin-left',-count + 'px');
                                        }
                                    },10);
                                    $('.userList').hover(function(){
                                        clearInterval(t1);
                                    },function(){
                                        t1 = setInterval(function(){
                                            count++;
                                            if(count >= $('.userList ul').width()/2){
                                                count = 0;
                                                $('.userList ul').css('margin-left',-count + 'px');
                                            }else{
                                                $('.userList ul').css('margin-left',-count + 'px');
                                            }
                                        },10);
                                    });
                                }
                            }
                        },
                        error:function(){
                            alert('网络异常，请重试！');
                            return false;
                        }
                    });
                }else{
                    $('#oneMonth').fadeIn(0);
                    $('#twelveMonth').fadeIn(0);
                    $('.bottomBox img').click(function(){
                        window.location.href = "#bg1";
                    });
                    //获奖列表信息
                    $.ajax({
                        url:'../../../activityCenter/doubleDanActivityBase/getLatestMaterailAwardsList?activityCode=DoubleDan-activity&recordNum=35',
                        type:'get',
                        async:false,
                        cache:false,
                        beforeSend:function(request){
                            request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                            request.setRequestHeader('clientTime',myTime);
                        },
                        success:function(data){
                            if(data.code != '200000'){
                                alert(data.message);
                                return false;
                            }else{
                                $.each(data.data.prize,function(index,item){
                                    $('.userList ul').append('<li><strong>' + item.username + '</strong><em>' + item.prizename + '</em></li>');
                                });
                                if($('.userList ul li').length >= 3){
                                    var count = 0;
                                    var ulWidth = 0;
                                    $('.userList ul').html($('.userList ul').html() + $('.userList ul').html());
                                    $.each($('.userList ul li'),function(){
                                        ulWidth+=$(this).width();
                                    });
                                    $('.userList ul').css('width',ulWidth + 'px');
                                    t1 = setInterval(function(){
                                        count++;
                                        if(count >= $('.userList ul').width()/2){
                                            count = 0;
                                            $('.userList ul').css('margin-left',-count + 'px');
                                        }else{
                                            $('.userList ul').css('margin-left',-count + 'px');
                                        }
                                    },10);
                                    $('.userList').hover(function(){
                                        clearInterval(t1);
                                    },function(){
                                        t1 = setInterval(function(){
                                            count++;
                                            if(count >= $('.userList ul').width()/2){
                                                count = 0;
                                                $('.userList ul').css('margin-left',-count + 'px');
                                            }else{
                                                $('.userList ul').css('margin-left',-count + 'px');
                                            }
                                        },10);
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
            }
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

    //运营数据
    $.ajax({
        url:'../../../biz/bulletin/operationData',
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
                $('.market').html(data.data.time);
                $('.market').html($('.market').html().substr(0,$('.market').html().indexOf('天') + 1));
                $.each(data.data.items,function(index,item){
                    if(item.code == 'TOTAL_REGISTER_USER'){
                        $('.user').html(item.nvalue + '人');
                    }
                    if(item.code == 'TOTAL_TRADE'){
                        $('.amount').html(item.nvalue/100000000);
                        $('.amount').html(parseFloat($('.amount').html()).toFixed(2) + '亿元');
                    }
                    if(item.code == 'TOTAL_INCOME'){
                        $('.earn').html(item.nvalue/100000000);
                        $('.earn').html(parseFloat($('.earn').html()).toFixed(2) + '亿元');
                    }
                });
            }
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

    //新元宝状态
    $.ajax({
        url:'../../../activityCenter/doubleDanActivityBase/getActivitySchemeList',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
            request.setRequestHeader('clientTime',myTime);
        },
        success:function(data){
            if(data.data.oneMonthScheme == -1){
                $('#oneMonth').click(function(){
                    alert('没有可投的新元宝活动标与普通标！');
                });
            }else{
                $('#oneMonth').click(function(){
                    window.open('/xplan/detail/' + data.data.oneMonthScheme + '.html');
                });
            }
            if(data.data.twelveMonthScheme == -1){
                $('#twelveMonth').click(function(){
                    alert('没有可投的新元宝活动标与普通标！');
                });
            }else{
                $('#twelveMonth').click(function(){
                    window.open('/xplan/detail/' + data.data.twelveMonthScheme + '.html');
                });
            }
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

    //通用关闭
    $('.close,.outLayer').click(function(){
        $('.outLayer').fadeOut(0);
        $('.gameRule').fadeOut(0);
    });

    //活动规则弹窗
    $('.eventRule').click(function(){
        $('.outLayer').fadeIn(0);
        $('.gameRule').fadeIn(0);
    });

    //翻蛋
    $('.eggs').hover(function(){
        $(this).find('.egg1').stop().animate({'width':'0','left':'96px'},100);
        $(this).find('.egg2').stop().delay(100).animate({'width':'192px','left':'0'},100);
    },function(){
        $(this).find('.egg2').stop().animate({'width':'0','left':'96px'},100);
        $(this).find('.egg1').stop().delay(100).animate({'width':'192px','left':'0'},100);
    });

    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});