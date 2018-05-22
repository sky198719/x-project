/**
 * Created by gaoshanshan_syp on 2017/7/18.
 */

require(['base', 'float', 'trackBase', 'store', 'swiper2','dialog', "requirejs"], function ($,float, track, store,Swiper,dialog) {
  var close=true;
    //注册用到的数据
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
        login  : '/feapi/users/login',
        infoUrl:'/feapi/users/accountInfo',

        bbgsUrl:'/step/stepDetail.html',
        yjdjUrl:'/detail/monthgold.html',
        xybUrl:'/xplan/search/list.html',
        xscpUrl:'/detail/thirtytender.html',
        yypUrl:'/promotion/yyp.html',
        //动态数据
        operationDataUrl:'/biz/bulletin/operationData'

    };
    //运营数据
    operationData();
    function operationData() {
        $.ajax({
            url: dataUrl.operationDataUrl,
            contentType: "application/json",
            dataType: "json",
            type: "get",
            beforeSend: function(request) {
                request.setRequestHeader("s", "www");
                request.setRequestHeader("clientId","001");
                request.setRequestHeader("clientTime","001");
            },
            success:function (result) {
                if (result && result.code == 200000){
                    fnOperation(result.data);
                }
            },
            error:function (data){
                $.log (data);
            }
        })


    }
    function fnOperation(data) {
        var array = data.items;

        //注册人数
        var tradeName = getNvalueByCode(array, 'TOTAL_REGISTER_USER');
        if (tradeName != '') {
            tradeName=parseFloat(tradeName).toLocaleString();
            $('#J_registerPeople').html(tradeName);
            $('#J_userNum').html(tradeName);
        }
    //成交金额
        var  totalTrade=getNvalueByCode(array,'TOTAL_TRADE');
        if(totalTrade!=''){
            totalTrade=(totalTrade/Math.pow(10,8)).toFixed(2);
            $("#J_totalTrade").html(totalTrade);
        }
    //质保服务专款余额
        /*var ventureBalance=getNvalueByCode(array,'VENTURE_BALANCE');
        if(ventureBalance!=''){
            ventureBalance=(ventureBalance/Math.pow(10,4)).toFixed(2);
            $("#J_ventureBalance").html(ventureBalance);
        }*/

        var totalIncome=getNvalueByCode(array,'TOTAL_INCOME');
        if(totalIncome!=''){
            totalIncome=(totalIncome/Math.pow(10,8)).toFixed(2);
            $("#J_totalIncome").html(totalIncome);
        }
    //   时间
        var time=data.time;

        if(time !=''){
            time=time.match(/\d+/g);
            $("#J_year").html(time[0]);
            $("#J_totalDay").html(time[1]);
        }
    // 截止日期
        var cutTime=data.registerUserDate;
        if(cutTime!=''){
            $("#J_registerUserDate").html(cutTime);
        }

    }

    function getNvalueByCode(array, code) {
        if (!array || !(array instanceof Array)) {
            return 0;
        }
        var length = array.length;
        for (var i = 0; i < length; i++) {
            if(array[i].code == code) {
                return array[i].nvalue;
            }
        }
        return 0;
    }


    // 轮播
    var mySwiper = new Swiper('.swiper-container',{
        // prevButton:'.swiper-button-prev',
        // nextButton:'.swiper-button-next',
        slidesPerView : 3,
        autoplay : 4000,
        loop:true
    });
    $('.swiper-button-prev').on('click',function (ev) {
        var ev=ev || event;
        mySwiper.swipePrev();
        ev.preventDefault();
    });
    $('.swiper-button-next').on('click',function (ev) {
        var ev=ev || event;
        mySwiper.swipeNext();
        ev.preventDefault();
    });
    $('.swiper-container').mouseenter(function () {
        mySwiper.stopAutoplay();
    }).mouseleave(function () {
        mySwiper.startAutoplay();
    });

    //preview  放大图片
    float.preview({});

   // 注册协议的同意
    var checked=true;
    $('.check-style').on('click',function (ev) {
        var ev=ev || event;
        if(checked){
            $(this).removeClass('checked').addClass('no-check');
            // $("#J_submit").attr("disabled","disabled");
            checked=false;
        }else{
            $(this).removeClass('no-check').addClass('checked');
            // $("#J_submit").removeAttr("disabled");
            checked=true;
        }
        ev.preventDefault();
    });

    //七天大胜
    //if($("#J_wrapBody").attr('data-page')=="sevengold"){
    if($("#J_wrapBody").attr('data-page')=="newhand"){


        //$('#J_invest').html('马上加入');

        $('#J_invest').html('马上加入');

        mainScroll(754);
        $('#J_invest,#J_joinup,#J_sideRegister,.snap-up').on('click',function (ev) {

            if(!isLogin()) {
                var ev=ev || event;
                dialogStyle($(this),271);
                ev.preventDefault();
            }else{
                return;
            }

        });
        if(isLogin()){
            $('#J_invest').html('立即出借');
            $("#J_yjdj").attr('href',dataUrl.yjdjUrl);
            $("#J_bbgs").attr('href',dataUrl.bbgsUrl);
            $("#J_xyb").attr('href',dataUrl.xybUrl);
            $("#J_invest").attr('href',dataUrl.xscpUrl);
            $("#J_yyp").attr('href',dataUrl.yypUrl);
            //侧边栏
            $("#J_sideRegister").addClass('get-disnone');
            $("#J_getMoney").removeClass('get-disnone');
            //底部
            $("#J_joinup").removeClass('disblock').addClass('disnone');
            $("#J_joinMoney").removeClass('disnone').addClass('disblock');
        }
    }
    //出借送流量
    if($("#J_wrapBody").attr('data-page')=="givedata"){
        var lastTime=new Date("2017/12/31 23:59:59").getTime();
        var newTime=getSystemTime();
        $('#J_investl,#J_investr,#J_yjdj,#J_bbgs,#J_sideRegister,#J_suspend,#J_suspendpop').on('click',function (ev) {
            if(newTime<=lastTime){
                if(!isLogin()){
                    var ev=ev || event;
                    dialogStyle($(this),271);
                    ev.preventDefault();
                }else{
                    return;
                }
            }else{
                var ev=ev || event;
                dialog({
                    content: "<div class='activity-time'>"
                    + "<div class='c_close'>X</div>"
                    + "<p class='activity-content'>来晚啦，活动已结束。</p>"
                    +"<a class='btn_center c_confirm' id='J_submitApply' target='_blank'>去官网看看</a>"
                    + "</div>",
                    id: "",
                    confirm: function (art) {
                        window.location.href='/';
                    },
                    cancel: function (art) {
                        art.close();
                    }
                });
                ev.preventDefault();
            }
        });
        if(isLogin()){
            if(newTime<=lastTime){
                $("#J_investl").attr('href',dataUrl.xscpUrl);
                $("#J_investr").attr('href',dataUrl.xybUrl);
                $("#J_yjdj").attr('href',dataUrl.yjdjUrl);
                $("#J_bbgs").attr('href',dataUrl.bbgsUrl);
                $("#J_sideRegister").addClass('get-disnone');
                $("#J_getMoney").removeClass('get-disnone');
                $("#J_suspend,#J_suspendpop").attr('href','#J_investflow');
            }else{
                // $.log('活动过期了');
                $('#J_investl,#J_investr,#J_yjdj,#J_bbgs,#J_sideRegister,#J_suspend,#J_suspendpop').on('click',function (ev) {
                    var ev=ev || event;
                     dialog({
                            content: "<div class='activity-time'>"
                            + "<div class='c_close'>X</div>"
                            + "<p class='activity-content'>来晚啦，活动已结束。</p>"
                            +"<a class='btn_center c_confirm' id='J_submitApply' target='_blank'>去官网看看</a>"
                            + "</div>",
                            id: "",
                            confirm: function (art) {
                                window.location.href='/';
                            },
                            cancel: function (art) {
                                art.close();
                            }
                        });
                        ev.preventDefault();


                });

            }

        }


        //请求系统时间
        function getSystemTime() {
            var systemtime;
            $.ajax({
                url:'/feapi/currentTime',
                type: 'get',
                data: {},
                dataType: 'json',
                async: false,
                success:function (data) {
                    if(data.code == 200){
                        systemtime =  data.data.currentTime;

                    }
                }
            });
            return systemtime;
        }

    }
    //月进斗金
    if($("#J_wrapBody").attr('data-page')=="monthgold"){
        mainScroll(500);
        $('#J_invest,#J_bbgs,#J_xscp,#J_xyb,#J_joinup,#J_sideRegister,#J_yyp').on('click',function (ev) {
           if(!isLogin()){
               var ev=ev || event;
               dialogStyle($(this),271);
               ev.preventDefault();
           }
        });
        if(isLogin()){
            $("#J_invest").attr({'href':dataUrl.yjdjUrl,'target':'_blank'});
            $("#J_xscp").attr({'href':dataUrl.xscpUrl,'target':'_blank'});
            $("#J_bbgs").attr({'href':dataUrl.yypUrl,'target':'_blank'});
            $("#J_xyb").attr({'href':dataUrl.xybUrl,'target':'_blank'});
            $("#J_yyp").attr({'href':dataUrl.yypUrl,'target':'_blank'});
            $("#J_joinup").removeClass('disblock').addClass('disnone');
            $("#J_joinmoney").removeClass('disnone').addClass('disblock');
            //侧边栏
            $("#J_sideRegister").addClass('get-disnone');
            $("#J_getMoney").removeClass('get-disnone');
        }
    }
    //新手送红包
    if($("#J_wrapBody").attr('data-page')=="redpacket"){
        $('#detail-get,#register-getred,#J_snapOne,#J_snapTwo').on('click',function (ev) {
            if(!isLogin()){
                var ev=ev || event;
                dialogStyle($(this),225);
                ev.preventDefault();
            }else{
                return;
            }

        });
        if(isLogin()){
            // //新手红包
            $("#register-getred").removeClass("disblock").addClass("disnone");
            $("#J_usered").removeClass("disnone").addClass("disblock");

            $("#detail-get").attr({'href':'/coupon/gomycoupon.html','target':'_blank'});
            $("#J_snapOne").attr({'href':dataUrl.yjdjUrl,'target':'_blank'});
            $("#J_snapTwo").attr({'href':dataUrl.xybUrl,'target':'_blank'});
        }
    }
    //欢乐星期三
    if($("#J_wrapBody").attr('data-page')=="wednesday"){
        if(isLogin()){
            window.location.href = "/html/happy-wednesdayV2/index.html";
        }
        $('#xyb1m,#xyb12m,#suspendBtn,#suspendpopBtn').on('click',function (ev) {
            if(!isLogin()){
                var ev=ev || event;
                $('body,html').animate({ scrollTop: 0 }, 500);
                ev.preventDefault();
            }else{
                window.location.href = "/html/happy-wednesdayV2/index.html";
            }

        });
    }

    //弹窗的样式
    function dialogStyle(elem,elemTop) {
        var tops = elem.offset().top - elemTop;
        $("#J_cur").removeClass('disblock').addClass('dishidden');
        $("#form")[0].reset();
        $('#disgray').show();
        $('#agree').removeClass("no-checked").addClass('checked');
        // $("#J_submit").removeAttr("disabled");
        // $("#J_submit").removeClass('no-checkBkg');
        $('#J_register').addClass('register-special');
        $('body').scrollTop(tops);
    }


    //关闭弹窗
    $('#close').on('click',function (ev) {
        var ev=ev || event;
        $('#disgray').hide();
        $("#form")[0].reset();
        $("#J_cur").removeClass('disblock').addClass('dishidden');
        $('#agree').removeClass("no-checked").addClass('checked');
        // $("#J_submit").removeAttr("disabled");
        $('#J_register').removeClass('register-special');
        ev.preventDefault();
    });

    //左侧导航栏+导航条
    function mainScroll(h) {
        $(window).scroll(scrolls);
        scrolls();
        function scrolls() {
            var wd_st = document.documentElement.scrollTop || document.body.scrollTop;
            if(wd_st>93){
                $('#header-fixed').removeClass('disnone');
            }else{
                $('#header-fixed').addClass('disnone');
            }
            if(wd_st>=h && close){
                $("#J_sidebar").removeClass("disnone");
            }else{
                $("#J_sidebar").addClass("disnone");
            }

            showLighter(wd_st);
        }
        //显示颜色 函数
        function showLighter(slidetop) {
            var content=document.documentElement.scrollHeight || document.body.scrollHeight,winH=$(window).height();
            $('#J_siderlist li a').each(function (item,val) {
                var J_item=$(val).attr('href');
                var $t=$(J_item).offset().top;
                var $h=$(J_item).height();
                if(slidetop>=$t && slidetop<$t+$h){
                    $(val).parent().siblings('li').removeClass('lighter-location');
                    $(val).parent().addClass('lighter-location');
                }
            });
            if(content-winH<=slidetop){
                $("#J_last").siblings('li').removeClass('lighter-location');
                $("#J_last").addClass('lighter-location');
                // $("#J_top").siblings('li').removeClass('lighter-location');
                // $("#J_top").addClass('lighter-location');
            }
        }

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
        $.ajax({
            type    : 'get',
            url     : dataUrl.infoUrl+'?token='+token,
            data:{},
            dataType: 'json',
            success:function (result) {
                if(result.code==="200000"){
                    var dataInfo=result.data;
                    $("#J_register").addClass('disnone');
                    $("#J_user").html('尊敬的'+dataInfo.name+'用户');
                    $("#J_money").html(result.usableAmount);
                    $("#J_registered").removeClass('disnone');
                }
            },
            error:function () {
                float.alert({content:msg.errorMsg});
            }

        });
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


    //刷新验证码
    $("#J_getVerify").on('click',function (ev) {
        var ev=ev || event;
        loadimage();
        ev.preventDefault();
    });

    //注册提交
    $("#J_submit").on('click',function (ev) {
        var ev=ev || event;
        var phoneNum=$("#J_num").val();
        var password=$("#J_psw").val();
        var phoneCode=$("#J_phoneyzm").val();
        // var imageCode=$("#J_verify").val();
        clearinterval();
        if (doSubmit()) {
            $.ajax({
                type    : 'POST',
                url     : dataUrl.registerUrl,
                data    : {
                    "formtoken": regToken,
                    "mobile"   : phoneNum,
                    "password" : password,
                    "smscode"  : phoneCode,
                    "channel"  : $.readCookie('dmp_utm_source') || ''
                },
                dataType: 'json',
                success:function (result) {
                    if (result.regResultCode === '200000') {
                        //注册成功并登陆操作regResultCode
                        // $.log('注册成功'+result);
                        track.init(result.data.user);
                        if($("#J_wrapBody").attr('data-page')=="wednesday"){
                            window.location.href = "/html/happy-wednesdayV2/index.html";
                        }else{
                            float.alert({content:'注册成功，恭喜您已获得108元红包'});
                            setTimeout(function () {
                                window.location.href="/user/ilogin.html";
                            },3000);
                        }

                        // $.ajax({
                        //     type    : 'POST',
                        //     url:dataUrl.login,
                        //     async: false,
                        //     data    : {
                        //         "formtoken": regToken,
                        //         "username"   : phoneNum,
                        //         "password" : password,
                        //         "imgcode"  : imageCode
                        //     },
                        //     dataType: 'json',
                        //     success:function (data) {
                        //         $.log('登录'+data.code);
                        //         if (data.code === '200000'){
                        //             var usertoken=data.data.userToken;
                        //             clearinterval();
                        //             float.alert({content:'注册成功，恭喜您已获得108元红包'});
                        //             setTimeout(function () {
                        //                 SetCookie('userToken',usertoken);
                        //                 window.location.reload();
                        //             },3000);
                        //
                        //         }else{
                        //             clearinterval();
                        //             _prompt(data.info);
                        //         }
                        //     },
                        //     error:function () {
                        //         clearinterval();
                        //         _prompt(msg.errorMsg);
                        //     }
                        // });


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



    //发送手机验证码
    $("#J_getPhoneCode").on('click',function (ev) {
        var ev=ev || event;
        if(!$(this).hasClass('disfont')){
            checkPhone();
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
        }else if(!$("#agree").hasClass('checked')){
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
                                // $("#J_submit").html("立即注册");
                                $("#J_verify").focus();
                                _prompt(result.info);
                                return false;
                            }else{
                                // $("#J_submit").html("立即注册");
                                _prompt(result.info);
                                return false;
                            }
                        },
                        error:function () {
                            // $("#J_submit").html("立即注册");
                            _prompt(msg.errorMsg);
                            return false;
                        }
                    })
                }else{
                    _prompt(result.info);
                    // $("#J_submit").html("立即注册");
                    return false;
                }
            },
            error:function () {
                // $("#J_submit").html("立即注册");
                _prompt(msg.errorMsg);
                return false;
            }
        });

    }
//    倒计时
    function setTime() {
        time=time-1;
        if(time<0){
            // time=60;
            // $("#J_getPhoneCode").removeAttr("disabled");
            // $("#J_getPhoneCode").removeClass('disfont');
            // $('#J_getPhoneCode').html('获取验证码');
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
        // $("#J_submit").html("立即注册");

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

    //   提示
    function _prompt(msg) {
        $('#J_cur').html("");
        $('#J_cur').removeClass('dishidden').addClass('disblock');
        $('#J_cur').html(msg);
        loadimage();
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


    //设置cookie
    function SetCookie (name, value) {
        var exp = new Date();
        exp.setTime(exp.getTime() + (1*24*60*60*1000));
        window.document.cookie = name + "=" + escape (value) + "; expires=" + exp.toGMTString()+";path=/";
    }
    //注册结束
    //关闭左侧导航
    $("#J_siderClose").on('click',function (ev) {
        var ev=ev || event;
        $("#J_sidebar").addClass('disnone');
        close=false;
        ev.preventDefault();
    });
    //返回顶部
    $("#J_top").on('click',function (ev) {
        var ev=ev || event;
        $(this).siblings('li').removeClass('lighter-location');
        $(this).addClass('lighter-location');
        $('body,html').animate({scrollTop: 0}, 1000);
        ev.preventDefault();
    });
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});