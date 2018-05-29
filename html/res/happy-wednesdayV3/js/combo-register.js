/**
 * Created by gaoshanshan_syp on 2017/11/29.
 */
require(['base', 'float','trackBase', 'store', "dialog", "requirejs"], function ($, float,track, store, dialog) {
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
        argeeInfo:'请勾选《_使用协议》'

    };
    var dataUrl={
        checkMobileUrl:'/feapi/users/checkMobile',
        tokenUrl:'/feapi/users/formToken',
        getVerifyUrl:' /feapi/randCode/createVerifyCode',
        registerUrl:'/feapi/users/regV3',
        isLogin:'/feapi/users/loginInfo',
        sendSMSUrl:'/feapi/users/sendSMS',

    };
    // 注册协议的同意
    var checked=true;
    $('.check-style').on('click',function (ev) {
        var ev=ev || event;
        if(checked){
            $(this).removeClass('checked').addClass('no-check');
            checked=false;
        }else{
            $(this).removeClass('no-check').addClass('checked');
            checked=true;
        }
        ev.preventDefault();
    });

    //注册代码
    var regToken,inteval,time = 60;
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
    $("#J_getVerify").on('click',function (ev) {
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
                        //注册成功并登陆操作regResultCode
                        window.location.href = "index.html";
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
        }else if(!$("#agree").hasClass('checked')){
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

    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});