/**
 * Created by gaoshanshan_syp on 2017/9/29.
 */
require(['base', 'float','trackBase', 'store', "dialog", "requirejs", "paging"], function ($, float,track, store, dialog) {
    var msg={
        phoneNull:'请输入手机号码！',
        phoneCorrect:'请输入有效的手机号码！',
        pswNull:'请输入密码！',
        pswCorrect:'请输入8-20个字符的密码！',
        verifyNull:'请输入图片验证码！',
        verifyCorrect:'请输入正确的图片验证码！',
        verifyNumberNull:'请输入短信验证码！',
        verifyNumberCorrect:'请输入正确的手机验证码！',
        phoneExistence:'您输入的手机号已被注册！',
        pswPhoneExist:'密码不能包括手机号！',
        errorMsg:'请检查您的网络！',
        argeeInfo:'请勾选《新新贷使用协议》'

    };
    var dataUrl={
        checkMobileUrl:'/feapi/users/checkMobile',
        tokenUrl:'/feapi/users/formToken',
        getVerifyUrl:' /feapi/randCode/createVerifyCode',
        registerUrl:'/feapi/users/regV3',
        isLogin:'/feapi/users/loginInfo',
        sendSMSUrl:'/feapi/users/sendSMS',

        commonUrl:'/tradeCenter/investBiz/showStatus/',
        xybUrl:'/tradeCenter/XYB/brief'


    };

    //循环数据
    $.xxdAjax({
        url: '/activityCenter/activityBase/getLatest15AwardsList',
        dataType: 'json',
        clientId: "XXD_ACTIVITY_H5_PAGE",
        data    : {
            "activityCode": 'October-17-vip-activity'
        },
        type    : 'GET',
        callbacks: function (data) {
            if(data.code == "200000"){
                if (data.data.data.list) {
                    if (data.data.data.list.length > 0) {
                        var aLis = [];
                        $.each(data.data.data.list, function (i, item) {
                            var time = new Date(item.addtime);
                            var hours = (time.getHours()) < 10 ? ("0" + time.getHours()) : time.getHours();
                            var getMinutes = (time.getMinutes()) < 10 ? ("0" + time.getMinutes()) : time.getMinutes();
                            var getSeconds = (time.getSeconds()) < 10 ? ("0" + time.getSeconds()) : time.getSeconds();
                            aLis.push('<li><span class="username">' + item.username + '</span><span class="time">' + hours + ':' + getMinutes + ':' + getSeconds + '</span><span class="prizename">' + item.prizename + '</span></li>');
                        });
                        $('.winData ul').html(aLis);
                        if($(".winData ul li").length >= 6){
                            $('.myscroll').vTicker({
                                speed: 500,
                                pause: 2000,
                                animation: 'fade',
                                mousePause: false,
                                showItems: 5
                            });
                        }
                    }
                }
            }
        },
        error: function () {
            main.tip('网络异常请重试!');
        }
    });

    var timeR;
    clearInterval(timeR);
    timeR = setInterval(function () {
        getData();
    }, 600000);

    getData();
    function getData() {
        $.xxdAjax({
            url: '/activityCenter/activityBase/getLatest15AwardsList',
            dataType: 'json',
            clientId: "XXD_ACTIVITY_H5_PAGE",
            data    : {
                "activityCode": 'October-17-vip-activity'
            },
            type    : 'GET',
            callbacks: function (data) {
                if(data.code == "200000"){
                    if (data.data.data.list) {
                        if (data.data.data.list.length > 0) {
                            var aLis = [];
                            $.each(data.data.data.list, function (i, item) {
                                var time = new Date(item.addtime);
                                var hours = (time.getHours()) < 10 ? ("0" + time.getHours()) : time.getHours();
                                var getMinutes = (time.getMinutes()) < 10 ? ("0" + time.getMinutes()) : time.getMinutes();
                                var getSeconds = (time.getSeconds()) < 10 ? ("0" + time.getSeconds()) : time.getSeconds();
                                aLis.push('<li><span class="username">' + item.username + '</span><span class="time">' + hours + ':' + getMinutes + ':' + getSeconds + '</span><span class="prizename">' + item.prizename + '</span></li>');
                            });
                            $('.winData ul').html(aLis);
                        }
                    }
                }
            },
            error: function () {
                main.tip('网络异常请重试!');
            }
        })
    }
    //固定底部
    $(window).scroll(scrolls);
    scrolls();
    function scrolls() {
        var h = $(document).height();
        var t =document.documentElement.scrollTop || document.body.scrollTop;
        var wh = $(window).height();
        if(t>=675 && h-t-wh>240){
            $("#J_lastRegister").removeClass("disnone");
        }else{
            $("#J_lastRegister").addClass("disnone");
        }
    }

    var checked=true;
    $('#agree .user-check').on('click',function (ev) {
        var ev=ev || event;
        if(checked){
            $(this).removeClass('checked');
            // $("#J_submit").attr("disabled","disabled");
            checked=false;
        }else{
            $(this).addClass('checked');
            // $("#J_submit").removeAttr("disabled");
            checked=true;
        }
        ev.preventDefault();
    });
    //展示数据
    xybData();
    bbgsData();

    //新元宝
    function xybData() {
        $.xxdAjax({
            url      : dataUrl.xybUrl,
            type:'get',
            clientId: "XXD_FRONT_END",
            data     : {},
            dataType : 'json',
            callbacks:function (data) {
                var result;
                if (data.code == "200000"&& (result=data.data)) {
                    fnOperation(result);
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }

        });
    }
    //步步高升数据
    function bbgsData() {
        $.xxdAjax({
            url      : dataUrl.commonUrl+'BBGS',
            type:'get',
            clientId: "XXD_INTEGRATION_PLATFORM",
            data     : {},
            dataType : 'json',
            callbacks:function (data) {
                var result;
                if (data.code == "200000" && (result=data.data.productInfo)) {
                    $("#J_bbgsApr").html(result.plannedAnnualRateFrom);
                    $("#J_bbgsfloatApr").html(result.plannedAnnualRateTo);
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }

        });
    }
    function fnOperation(data) {
        var array = data.items,xybFrom,xybTo,xybAprFrom,xybAprTo;
        xybFrom= getNvalueByCode(array,1);
        xybTo=  getNvalueByCode(array,36);
        xybAprFrom=xybFrom.plannedAnnualRateFrom;
        xybAprTo=xybTo.plannedAnnualRateFrom;
        if(xybFrom.floatingRate){
            xybAprFrom=(xybAprFrom*10+xybFrom.floatingRate*10)/10;
        }
        if(xybAprTo.floatingRate){
            xybAprTo=(xybAprTo*10+xybTo.floatingRate*10)/10;
        }
        $("#J_xybForm").html(xybAprFrom);
        $("#J_xybTo").html(xybAprTo);
    }

    function getNvalueByCode(array, leastPeriod) {
        if (!array || !(array instanceof Array)) {
            return 0;
        }
        var length = array.length;
        for (var i = 0; i < length; i++) {
            if(array[i].leastPeriod == leastPeriod) {
                return array[i];
            }
        }
        return 0;
    }
    //注册代码
    var regToken,inteval,time = 60,token;

    //判断是否登录
    function isLogin() {
        var result=false;
        token=$.readCookie('Token');
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
    //登录成功显示
    if(isLogin()){
        $("#J_registerBottom,#J_specialBtn,#J_xyb,#J_bbgs").attr({'href':'/html/octoberMember/index.html','target':'_self'});
        $("#J_register").addClass('disnone');
    }
    // 获取 regToken ,token
    $.ajax({
        type    : 'GET',
        url     : dataUrl.tokenUrl,
        // async: false,
        data    : {},
        dataType: 'json',
        success : function (result) {
            regToken = result.token;
            loadimage();
        },
        error : function () {
            float.alert({content:msg.errorMsg});
        }
    });
    $("#J_yzmRefresh").on('click',function (ev) {
        loadimage();
    });
    //发送手机验证码
    $("#J_getPhoneCode").on('click',function (ev) {
        var ev=ev || event;
        if(!$(this).hasClass('disfont')){
            checkPhone();
        }
        ev.preventDefault();
    });
    //注册提交
    $("#J_submit").on('click',function (ev) {
        var ev=ev || event;
        var phoneNum=$("#J_num").val();
        var password=$("#J_psw").val();
        var phoneCode=$("#J_phoneyzm").val();
        clearinterval();
        if (doSubmit()) {
            $.ajax({
                type    : 'POST',
                url     : dataUrl.registerUrl,
                data    : {
                    "formtoken": regToken,
                    "mobile"   : phoneNum,
                    "password" : password,
                    "smscode"  : phoneCode
                },
                dataType: 'json',
                success:function (result) {
                    if (result.regResultCode === '200000') {
                        window.location.href="/user/ilogin.html";
                    }else{
                        _prompt(result.info);
                    }
                },
                error:function () {
                    _prompt(msg.errorMsg);
                }


            })
        }
        ev.preventDefault();
    });
    //    注册框判断
    function doSubmit(){
        var bol=true;
        var phoneNum=$("#J_num").val();
        var password=$("#J_psw").val();
        var imageCode=$("#J_verify").val();
        var phoneCode=$("#J_phoneyzm").val();
        var rpass=validatePassword(password);
        if(phoneNum=="" || phoneNum==null){
            _prompt(msg.phoneNull);
            bol=false;
            return bol;
        }else if(!validateMobile(phoneNum)){
            _prompt(msg.phoneCorrect);
            bol=false;
            return bol;
        }else if(password=="" || password==null){
            _prompt(msg.pswNull);
            bol=false;
            return bol;
        }else if(rpass!='true'){
            _prompt(rpass);
            bol=false;
            return bol;
        }else if(imageCode=="" || imageCode==null){
            _prompt(msg.verifyNull);
            bol=false;
            return bol;
        }else if(phoneCode=="" || phoneCode==null){
            _prompt(msg.verifyNumberNull);
            bol=false;
            return bol;
        }else if(rpass!='true' && password.indexOf(phoneNum)>=0){
            _prompt(msg.pswPhoneExist);
            bol=false;
            return bol;
        }else if(!$("#agree .user-check").hasClass('checked')){
            _prompt(msg.argeeInfo);
            bol=false;
            return bol;
        }
        $("#J_cur").removeClass('disblock').addClass('dishidden');
        // $("#J_submit").html('正在注册，请稍候...');
        return bol;

    }
    //判断手机验证码
    function checkPhone(){
        var phoneNum=$("#J_num").val();
        var imageCode=$("#J_verify").val();
        clearinterval();
        if(phoneNum==''){
            _prompt(msg.phoneNull);
            return false;
        }else if(!validateMobile(phoneNum)){
            _prompt(msg.phoneCorrect);
            return false;
        }
        if(imageCode=='' || imageCode==null){
            _prompt(msg.verifyNull);
            return false;
        }
        $.ajax({
            type:'POST',
            url:dataUrl.checkMobileUrl,
            data:{"formtoken":regToken,"mobile":phoneNum},
            dataType:'json',
            success:function(result){
                // $.log(result);
                if(result.code==0){
                    _prompt(result.info);
                    return false;
                }else if(result.code==1){
                    $.ajax({
                        type: 'POST',
                        url: dataUrl.sendSMSUrl,
                        data: {
                            "formtoken": regToken,
                            "imgcode": imageCode,
                            "mobile": phoneNum
                        },
                        dataType: 'json',
                        success: function (result) {
                            if (result.code === '200000') {
                                $("#J_cur").removeClass('disblock').addClass('dishidden');
                                clearInterval(inteval);
                                inteval=setInterval(function(){ setTime(); }, 1000);
                            }else if(result.code === -40){
                                loadimage();
                                $("#J_verify").focus();
                                _prompt(result.info);
                                return false;
                            }else{
                                _prompt(result.info);
                                return false;
                            }
                        },
                        error:function () {
                            _prompt(msg.errorMsg);
                            return false;
                        }
                    })
                }else{
                    _prompt(result.info);
                    return false;
                }
            },
            error:function () {
                _prompt(msg.errorMsg);
                return false;
            }
        });

    }
//    注册框判断
    function doSubmit(){
        var bol=true;
        var phoneNum=$("#J_num").val();
        var password=$("#J_psw").val();
        var imageCode=$("#J_verify").val();
        var phoneCode=$("#J_phoneyzm").val();
        var rpass=validatePassword(password);
        if(phoneNum=="" || phoneNum==null){
            _prompt(msg.phoneNull);
            bol=false;
            return bol;
        }else if(!validateMobile(phoneNum)){
            _prompt(msg.phoneCorrect);
            bol=false;
            return bol;
        }else if(password=="" || password==null){
            _prompt(msg.pswNull);
            bol=false;
            return bol;
        }else if(rpass!='true'){
            _prompt(rpass);
            bol=false;
            return bol;
        }else if(imageCode=="" || imageCode==null){
            _prompt(msg.verifyNull);
            bol=false;
            return bol;
        }else if(phoneCode=="" || phoneCode==null){
            _prompt(msg.verifyNumberNull);
            bol=false;
            return bol;
        }else if(rpass!='true' && password.indexOf(phoneNum)>=0){
            _prompt(msg.pswPhoneExist);
            bol=false;
            return bol;
        }else if(!$("#agree .user-check").hasClass('checked')){
            _prompt(msg.argeeInfo);
            bol=false;
            return bol;
        }
        $("#J_cur").removeClass('disblock').addClass('dishidden');
        return bol;

    }

    function _prompt(msg) {
        $('#J_cur').html("");
        $('#J_cur').removeClass('dishidden').addClass('disblock');
        $('#J_cur').html(msg);

    }
    //判断手机号的格式
    function validateMobile(arg) {
        var patter = /^0?(13|15|14|17|18)[0-9]{9}$/;
        if (patter.test(arg)) {
            return true;
        } else {
            return false;
        }
    }
    //    图形验证码
    function loadimage() {
        $("#J_verify").val('');
        $("#J_getVerify").attr('src',dataUrl.getVerifyUrl+'?formtoken='+regToken+'&v='+Math.random());
    }

    //密码
    function validatePassword(arg){
        var patter = /^([a-zA-Z0-9])*$/;
        var patter1 = /^([a-zA-Z])*$/;
        var patter2 = /^([0-9])*$/;
        if(arg.length<6){
            return '有效密码为6-16位数字,字母组合！';
        }
        if(arg.length>16){
            return '密码长度不得超过16位';
        }
        if(!patter.test(arg) || (patter1.test(arg) || patter2.test(arg))){
            return '有效密码为6-16位数字，字母组合！';
        }
        return 'true';
    }
    //    倒计时
    function setTime() {
        time=time-1;
        if(time<0){
            clearinterval();
        }else{
            $("#J_getPhoneCode").addClass('disfont');
            $('#J_getPhoneCode').html(time+'秒后重新获取');
        }
    }
    //清除时间
    function clearinterval() {
        clearInterval(inteval);
        time=60;
        $("#J_getPhoneCode").removeClass('disfont');
        $('#J_getPhoneCode').html('获取验证码');
    }


}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});