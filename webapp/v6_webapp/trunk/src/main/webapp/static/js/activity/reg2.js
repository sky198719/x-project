var job = 'default';
var intervalArray = [];
var flag = 0;
var time = 60;
//获取地址栏url参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return  decodeURI(r[2]);
    return null;
}

$(document).ready(function () {
    bindingEvent();
    bulletin();
    clearIntervalArray();
    get7DaysInfo();
    getMonthFinance();
    getFundInfo();
    getPlan();
});

function bulletin(){
     /*var msg = '{"code":"200000","data":{"investUserDate":"2017年02月28日","items":[{"code":"VENTURE_BALANCE","inforName":"质保服务专款（元）","nvalue":"44224965.68"},{"code":"TOTAL_COUNT","inforName":"累计交易总笔数（笔）","nvalue":"26819"},{"code":"TOTAL_FINANCING_USER","inforName":"融资人总数（人）","nvalue":"16562"},{"code":"TOTAL_INVEST_USER","inforName":"投资人总数（人）","nvalue":"120903"},{"code":"TOTAL_REGISTER_USER","inforName":"累计注册人数（人）","nvalue":"1602016"},{"code":"TOTAL_INCOME","inforName":"累计为投资人赚取（元）","nvalue":"440345888.85"},{"code":"TOTAL_TRADE","inforName":"累计交易总额（元）","nvalue":"9626799912.62"}],"registerUserDate":"2017年02月28日","time":"5年23天15小时"},"message":"操作成功"}';
     msg = JSON.parse(msg);
     console.log(msg); */

    $.ajax({
        url: "/biz/bulletin/operationData",
        contentType: "application/json",
        dataType: "json",
        type: "get",
        beforeSend: function (request) {
            request.setRequestHeader("s", "www");
            request.setRequestHeader("clientId", "001");
            request.setRequestHeader("clientTime", "001");
        },
        success: function (msg) {
            if (msg && msg.code == 200000) {
                for (var i = 0; i < msg.data.items.length; i++) {
                    if (msg.data.items[i].code == 'TOTAL_REGISTER_USER') {
                        var TOTAL_REGISTER_USER = msg.data.items[i].nvalue;
                        TOTAL_REGISTER_USER = TOTAL_REGISTER_USER / 10000;
                        $('.total_register_user').html( TOTAL_REGISTER_USER.toFixed(2) + '<span style="font-size: 0.7rem;">万人</span>');
                    }
                    if (msg.data.items[i].code == 'VENTURE_BALANCE') {
                        var VENTURE_BALANCE = msg.data.items[i].nvalue;
                        VENTURE_BALANCE = VENTURE_BALANCE / 10000;
                        $('.venture_balance').html(VENTURE_BALANCE.toFixed(2) + '<span style="font-size: 0.7rem;">万元</span>');
                    }
                    if (msg.data.items[i].code == 'TOTAL_TRADE') {
                        var TOTAL_TRADE = msg.data.items[i].nvalue;
                        TOTAL_TRADE = TOTAL_TRADE / 100000000;
                        $('.total_trade').html(TOTAL_TRADE.toFixed(2) + '<span style="font-size: 0.7rem;">亿元</span>');
                    }
                    if (msg.data.items[i].code == 'TOTAL_INCOME') {
                        var TOTAL_INCOME = msg.data.items[i].nvalue;
                        TOTAL_INCOME = TOTAL_INCOME / 100000000;
                        $(".total_income").html(TOTAL_INCOME.toFixed(2) + '<span style="font-size: 0.7rem;">亿元</span>');
                    }
                }
            }
        },
        error: function (data) {
            console.error(data.code);
        }
    });
}


function isLogin() {
    var result = false;
    ajax({
        url: "../../../user/isLogin.do?" + new Date().getTime(),
        dataType: 'json',
        async: false,
        data: {},
        success: function (data) {
            if (data.isLogin != null) {
                result = data.isLogin;
            }
        }
    });
    return result;
}

function getPlan() {
    ajax({
        url: '../../../xplan/getLatestSchemeId.do',
        data: {
            currentPage: 1,
            closeTerm: 12,
            pageSize: 10
        },
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 0) {
                var apr = data.schemeInfo.MINAPR;
                $("#planApr").html(apr);

                $("#planBuy").attr("planid", data.schemeInfo.SCHEMEID);
            }

        }
    });
}

function getFundInfo() {
    ajax({
        url: '../../../fund/selectFundInfo.do',
        data: {},
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data != null && data.fundApr != null) {
                var apr = parseFloat(data.fundApr.apr).toFixed(2);
                var values = new String(apr).split('.');
                var floatApr = parseFloat(data.fundApr.floatApr).toFixed(2);
                var values1 = new String(floatApr).split('.');
                $("#fundApr").html(parseInt(values[0]) + parseInt(values1[0]));

                var lowestTender = data.fund.lowestTender;
                $("#fundlowestTender").html(lowestTender);
            }
        }
    });
}

function getMonthFinance() {
    ajax({
        url: '../../../monthFinance/getProudctBaseInfo.do',
        data: {},
        dataType: 'json',
        indicator: true,
        success: function (result) {
            if (result.resultCode == '0' || result.resultCode == 0) {
                var apr = result.data.apr;
                var values = new String(apr).split('.');
                $("#monthApr").html(values[0]);

                var closeTerm = result.data.closeTerm;
                $("#monthcloseTerm").html(closeTerm);

                var monthlowestTender = result.data.lowestTender;
                $("#monthlowestTender").html(monthlowestTender);

            }
        }
    });
}
function get7DaysInfo() {
    ajax({
        url: '../../../sevenDays/selectSevenDaysInfo.do',
        data: {},
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.sevenDays != null) {
                var apr = parseFloat(data.sevenDays.apr).toFixed(2);
                var values = new String(apr).split('.');

                var floatApr =   parseFloat(data.sevenDays.floatApr).toFixed(2);
                var values1 = new String(floatApr).split('.');
                $("#7dayApr").html(parseInt(values[0]) + parseInt(values1[0]));

                var lowestTender = data.sevenDays.lowestTender;
                $("#7daylowestTender").html(lowestTender);

                var closeTerm = data.sevenDays.closeTerm;
                $("#7daycloseTerm").html(closeTerm);
            }
        }
    });

}


function bindingEvent() {

    $(".login-button").click(function () {
        var username = $.trim($("#login-username").val());
        if (username == '') {
            $.dialog({
                content: '请输入用户名/注册手机/邮箱地址',
                title: "alert",
                time: 2000
            });

            $("#login-username").focus();
            return;
        }


        var pwd = $.trim($("#login-pwd").val());
        if (pwd == '') {
            $.dialog({
                content: '请输入密码',
                title: "alert",
                time: 2000
            });
            $("#login-pwd").focus();
            return;
        }
        $(".login-error").hide();
        ajax({
            url: '../../../user/loginActive.do',
            data: {
                username: username,
                password: $.md5($.md5(pwd))
            },
            dataType: 'json',
            success: function (result) {
                $('#login-pwd').val('');
                if (result.resultCode == 0) {
                    $(".login").hide();
                    $("#reg2").show();
                    window.location.href = '../../../';
                } else if (result.resultCode == -1) {
                    $.dialog({
                        content: '用户名称不存在，请重新输入',
                        title: "alert",
                        time: 2000
                    });
                    $("#login-username").focus();
                } else if (result.resultCode == -2) {
                    $.dialog({
                        content: '密码错误，请重新输入',
                        title: "alert",
                        time: 2000
                    });
                    $("#login-pwd").focus();
                } else if (result.resultCode == -3 || result.resultCode == -4) {
                    $.dialog({
                        content: result.msg,
                        title: "alert",
                        time: 2000
                    });
                } else if (result.resultCode == -9) {
                    $.dialog({
                        content: '登录失败，请重新尝试或者联系客服',
                        title: "alert",
                        time: 2000
                    });
                } else {
                    $.dialog({
                        content: result.msg,
                        title: "alert",
                        time: 2000
                    });
                }
            }
        });
    });

    $("#login-close").click(function () {
        $(".login").hide();
        $("#reg2").show();
    });

    $("#login").click(function () {
        if (isLogin()) {
            window.location.href = '../../../';
            return;
        }

        $(".login").show();
        $("#reg2").hide();
    });

    $("#randImage").click(function () {
        refVerifyCode();
    });


    $('#send-rand-code').click(function () {
        sendRandCode();
    });

    $('#reg').click(function () {
        storeReg();
    });

    $('#reg-agreement').click(function () {
        regAgreement();
    });
    $('#agreement #agree-back').click(function () {
        closeAgreement();
    });

    $("#7dayBuy").click(function () {
        if (!isLogin()) {
            $.dialog({
                  content: '请先注册或者登录',
                  title: "alert",
                  time: 2000
              });
            window.scrollTo(0,0);
            return;
        }
        window.location.href = '../../../#!/static/html/newHand/sevenDaysDetail.html?path=newHand';
    });

    $("#monthBuy").click(function () {
        if (!isLogin()) {
            $.dialog({
                  content: '请先注册或者登录',
                  title: "alert",
                  time: 2000
              });
            window.scrollTo(0,0);
            return;
        }
        window.location.href = '../../../#!/static/html/monthFinance/monthFinanceDetails.html';
    });

    $("#fundBuy").click(function () {
        if (!isLogin()) {
            $.dialog({
                  content: '请先注册或者登录',
                  title: "alert",
                  time: 2000
              });
            window.scrollTo(0,0);
            return;
        }
        window.location.href = '../../../#!/static/html/fund/fundUnInvested.html?path=fund';
    });

    $("#planBuy").click(function () {
        if (!isLogin()) {
            $.dialog({
                  content: '请先注册或者登录',
                  title: "alert",
                  time: 2000
              });
            window.scrollTo(0,0);
            return;
        }
        var planid = $("#planBuy").attr("planid");
        window.location.href = '../../../#!/static/html/plan/planDetailsV2.html?path=plan&planId=' + planid;

    });

    $("#mashang").click(function () {
        if (!isLogin()) {
            $.dialog({
                  content: '请先注册或者登录',
                  title: "alert",
                  time: 2000
              });
            window.scrollTo(0,0);
        } else {
            $.dialog({
                  content: '请选择上面的产品',
                  title: "alert",
                  time: 2000
              });
        }
    });
}

function refVerifyCode() {
    document.getElementById("randImage").src = "../../../verify/code.do?" + Math.random();
}

function checkVerifyCode() {
    var bool = false;
    var imgCode = $.trim($("#img-code").val());
    ajax({
        url: '../../../randCode/checkVerifyCode.do',
        data: {
            imgCode: imgCode,
            type:1
        },
        dataType: 'json',
        timeout: 10000,
        async: false,
        success: function (result) {
            if (result.resultCode == 0) {
                bool = true;
            }
        }
    });
    return bool;
}
function validatePassword(arg) {
    var patter = /^([a-zA-Z0-9])*$/;
    var patter1 = /^([a-zA-Z])*$/;
    var patter2 = /^([0-9])*$/;
    if (arg.length < 6)
        return '密码太短，不足6位';
    if (arg.length > 16)
        return '密码长度不得超过16位';
    if (!patter.test(arg) || (patter1.test(arg) || patter2.test(arg)))
        return '密码需由字母和数字组成';

    return 'true';
}
function storeReg() {
    var mobileNo = clearTimar($('#reg2 #mobile-no').val());
    if (mobileNo == '') {
        $.dialog({
            content: '手机号码不能为空',
            title: "alert",
            time: 2000
        });
        return false;
    }
    var vaRe = validateMobile(mobileNo);
    if (vaRe != "true") {
        $.dialog({
            content: vaRe,
            title: "alert",
            time: 2000
        });
        return false;
    }

    if (checkMobileExist(mobileNo)) {
        $.dialog({
            content: '您输入的手机号已被注册',
            title: "alert",
            time: 2000
        });
        return false;
    }

    var imgCode = $.trim($("#img-code").val());
    if (imgCode == undefined || '' == imgCode) {
        $.dialog({
            content: '图片验证码不能为空',
            title: "alert",
            time: 2000
        });
        return false;
    }

    /*if (!checkVerifyCode()) {
        $.dialog({
            content: '请输入正确的图片验证码',
            title: "alert",
            time: 2000
        });
        return false;
    }*/

    var randCode = $.trim($('#reg2 #rand-code').val());
    if (randCode == '') {
        $.dialog({
            content: '短信验证码不能为空',
            title: "alert",
            time: 2000
        });
        return false;
    }

    var password = $.trim($('#reg2 #password').val());
    if (password == '') {
        $.dialog({
            content: '密码不能为空',
            title: "alert",
            time: 2000
        });
        return false;
    }
    if (validatePassword(password) != 'true') {
        $.dialog({
             content: '有效密码为6-16位数字字母组合',
             title: "alert",
             time: 2000
         });
        return false;
    }

    //try{XXD_TRACK._trackEvent({category:"webapp_reg2",action:"reg2_button",label:"注册领取108元",value:clearTimar($('#reg2 #mobile-no').val()),custval:""});}catch(e){}
    ajax({
        url: '../../../user/checkSMS.do',
        data: {
            code: randCode,
            mobileNo: mobileNo,
            busiCode: 'BUSICODE_REGISTER_SMS'
        },
        dataType: 'text',
        indicator: true,
        type: 'POST',
        timeout: 10000,
        success: function (result) {
            if (result == '0') {
                regSubmit();
            } else if (result == '1') {
                $.dialog({
                     content: '验证码错误',
                     title: "alert",
                     time: 2000
                 });
            }
        }
    });
}
function regSubmit() {

    if ($('#reg2 #reg').attr("isSubmit") == "true") {
        $.dialog({
            content: '正在注册请稍后...',
            title: "load",
            lock:true
        });
        return;
    }

    var password = $.trim($('#reg2 #password').val());

    var mobileNo = clearTimar($('#reg2 #mobile-no').val());
    var randCode = $.trim($('#reg2 #rand-code').val());
    var uuid = $.trim($("#uuid").val());
    $('#reg2 #reg').attr("isSubmit", "true");
    $('#reg2 #reg').html("正在注册...");

    ajax({
        type: 'POST',
        url: '../../../user/regV3.do',
        data: {
            phone: mobileNo,
            password: $.md5($.md5(password)),
            referer: '',
            uuid: uuid,
            regsource: 7, //用户来源1：网站注册,2：手机客户端注册,3：合作商户注册,4：合作商户导入,5：微信注册,6：移动网页注册,7:webapp(html5)'
            smsCode: randCode
        },
        dataType: 'json',
        timeout: 120000,
        success: function (result) {
            //console.log(result)
            if (result.resultCode == 0 && result.regResultCode == 0 && result.mobileResultCode == 0) {
            	  //注册成功
                try {
                    //XXD_TRACK._trackEvent({category: "webapp_reg2", action: "reg_success_webapp", label: "注册成功", value: result.userid, custval: "1" });
                  //GA部署
                    gaClickEvent_UserId({property1:"注册",property2:"注册成功",property3:"手机注册"});
                    growingIOInits({userId : result.userid});
                } catch (e) {}

                $.dialog({
                     content: '注册成功！108元新手红包已发放至账户，请在‘我的新新贷’中查看！',
                     title: "ok",
                     time: 3000
                 });

                $('#reg2 #reg').attr("isSubmit", "false");
                $('#reg2 #reg').html("注册领108元");
                setTimeout('Redirect()', 3000);//3秒后执行
            } else {
                refVerifyCode();
                var msg = '';
                if (result.regResultCode != 0) {
                    msg = result.regDesc;
                } else if (result.mobileResultCode != 0) {
                    msg = result.mobileDesc;
                }
                $.dialog({
                     content: msg,
                     title: "alert",
                     time: 2000
                 });
            }
        }
    });
}

function Redirect() {
    window.location.href = "../../../#!/static/html/account/account.html";
}
function clearTimar(value) {
    return value.replace(/\s+/g, '');
}

/**清空定时器，隐藏语音验证码*/
function clearIntervalArray() {
    $.each(intervalArray, function (index, value) {
        clearInterval(value);
    });
    intervalArray.length = 0;
}

function sendRandCode() {
    var mobileNo = clearTimar($('#mobile-no').val());
    if (mobileNo == '') {
        $.dialog({
             content: '手机号码不能为空',
             title: "alert",
             time: 2000
         });
        return false;
    }

    var vaRe = validateMobile(mobileNo);
    if (vaRe != "true") {
        $.dialog({
            content: vaRe,
            title: "alert",
            time: 2000
        });
        return false;
    }

    if (checkMobileExist(mobileNo)) {
        $.dialog({
            content: '您输入的手机号已被注册',
            title: "alert",
            time: 2000
        });
        return false;
    }

    var imgCode = $.trim($("#img-code").val());
    if (imgCode == undefined || '' == imgCode) {
        $.dialog({
            content: '图片验证码不能为空',
            title: "alert",
            time: 2000
        });
        return false;
    }

    ajax({
        url: '../../../user/sendSMS.do',
        data: {
            phone: mobileNo,
            type: 1,
            imgCode: imgCode
        },
        dataType: 'json',
        timeout: 30000,
        success: function (result) {
            var tempTime = time;
            if (result.resultCode == '0') {
                clearIntervalArray();
                $.dialog({
                    content: '验证码发送成功，请注意查收',
                    title: "ok",
                    time: 2000
                });
                refVerifyCode();
                $('#reg2 #send-rand-code').hide();
                $('#reg2 #rand-code-countdown').show().html(tempTime);
                intervalArray.push(setInterval(function () {
                    tempTime--;
                    if (tempTime < 0) {
                        $('#reg2 #rand-code-countdown').hide();
                        $('#reg2 #send-rand-code').show();
                        // 清除定时器
                        clearIntervalArray();
                    } else {
                        //获取短信验证码超过30秒，还未进行验证，显示获取语音验证码按钮
                        $('#reg2 #rand-code-countdown').html(tempTime + '秒后重新发送');
                    }
                }, 1000));
            } else {
                $.dialog({
                   content: result.msg,
                   title: "alert",
                   time: 2000
                });

                if(result.resultCode == -250) {
                    refVerifyCode();
                }
                $('#reg2 #rand-code-countdown').hide();
                $('#reg2 #send-rand-code').show();
            }
        }
    });
}

function validateMobile(arg) {
    var patter = /^0?(13|15|14|17|18)[0-9]{9}$/;
    if (patter.test(arg)) {
        return 'true';
    } else {
        return '请输入正确的手机号码';
    }
}

//检查手机号是否已被使用
function checkMobileExist(mobileNo) {
    var exist = false;
    ajax({
        url: '../../../user/checkMobile.do',
        data: {
            mobile: mobileNo

        },
        async: false,
        dataType: 'json',
        timeout: 30000,
        success: function (data) {
            if (data == 0) {
                exist = false;
            } else if (data == 1) {
                exist = true;
            }
        }
    });
    return exist;
}

function ajax(options) {
    var isasync = 'async' in options ? options.async : true;
    var method = 'type' in options ? options.type : 'GET';
    var reqData = 'data' in options ? options.data : {};
    var reqDataType = 'dataType' in options ? options.dataType : 'none';
    var timeoutNum = 'timeout' in options ? options.timeout : 5000;
    try {
        $.ajax({
            type: method,
            url: options.url,
            data: reqData,
            async: isasync,
            dataType: reqDataType,
            timeout: timeoutNum,
            success: function (resp) {
                options.success(resp);
            },
            error: function (xhr, errorType, error) {
                if ('error' in options && $.isFunction(options.error)) {
                    options.error(xhr, errorType, error);
                } else {
                    $.dialog({
                       content: "请求出错[" + xhr.status + "]，请重新尝试或检查您的网络是否正常",
                       title: "alert",
                       time: 2000
                    });
                }
            }
        });
    } catch (e) {
        $.dialog({
           content:  "请求出错[" + xhr.status + "]，请重新尝试或联系客服",
           title: "alert",
           time: 2000
        });
    }
}
function regAgreement() {
    document.getElementById("reg2").style.display = "none";
    document.getElementById("agreement").style.display = "block";
}
function closeAgreement() {
    document.getElementById("reg2").style.display = "block";
    document.getElementById("agreement").style.display = "none";
}
/**
 * 1:手机号、2：图片验证码、3：短信验证码、4：设置登录密码、5：邀请码
 * @param num
 */
function reg2BM(num){
	var category= "webapp_reg2",action="",label="",value="",custval="";
	try{
		if(num == 1){
			action = "reg2_phone";
			value = $("#mobile-no").val();
			label = "手机号";
		}else if(num ==2){ 
			action = "reg2_verifycode";
			label = "图片验证码";
		}else if(num == 3){
			action = "reg2_sms_verifycode";
			label = "短信验证码";
		}else if(num == 4){
			action = "reg2_password";
			label = "设置登录密码";
		}else if(num == 5){
			action = "reg2_code";
			label = "邀请码";
		}
		//XXD_TRACK._trackEvent({category:category,action:action,label:label,value:value,custval:custval});
	}catch(e){}
}
//XXD-DMP
function randomNum(n){
    var t='';
    for(var i=0;i<n;i++){
        t+=Math.floor(Math.random()*10);
    }
    return t;
}
function setCookie(name, value) {
    var Days = 1825;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
function getCookie(cookieName) {
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    for (var i = 0; i < arrCookie.length; i++) {
        var arr = arrCookie[i].split("=");
        if (cookieName == arr[0]) {
            return arr[1];
        }
    }
    return "";
}
function getDmpUrlParam(item){
    var pattern = new RegExp("[?&]"+item+"\=([^&]+)", "g");
    var matcher = pattern.exec(decodeURIComponent(location.href));
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
function  dmp_default_obj(action,event) {
    var dmp_default_obj = {};
    dmp_default_obj.action = action;
    dmp_default_obj.event = event;
    //console.log(dmp_data);
    return dmp_default_obj;
}
window.view = (function(){
    var ret = {};
    ret.dmp_userId ="";
    ret.dmp_userToken ="";
    ret.getReferrer = function() {
        var referrer = '';
        try {
            referrer = window.top.document.referrer;
        } catch (e) {
            if (window.parent) {
                try {
                    referrer = window.parent.document.referrer;
                } catch (e2) {
                    referrer = document.referrer;
                }
            }
        }
        return referrer;
    };
    if (document.cookie.indexOf("_dmp_sessionId") == -1) {
        ret._dmp_sessionId = new Date().getTime() + String(randomNum(4));
        setCookie("_dmp_sessionId", ret._dmp_sessionId,{ expires: 1825, path: '/' })
    }
    if (document.cookie.indexOf("ID") != -1) {
        ret.dmp_userId = getCookie('ID')
    }
    if (document.cookie.indexOf("userToken") != -1) {
        ret.dmp_userToken = getCookie('userToken')
    }
    var dmp_utm_source = getDmpUrlParam("utm_source") || "";
    if(dmp_utm_source != "" && dmp_utm_source != null && dmp_utm_source != undefined){
        setCookie("dmp_utm_source", dmp_utm_source,{ expires: 1825, path: '/' })
    }
    var interfacePath = "";
    if(window.location.host == "www.xinxindai.com" || "m.xinxindai.com"){
        interfacePath = "https://dmp.xinxindai.com/dmp_web/collect";
    }else{
        interfacePath = "http://118.178.90.205/dmp_web/collect";
    }
    ret.sendRequest = function() {
        if(window.FormData && typeof window.FormData == 'function') {
            var form = new FormData(),
                self = this;
            form.append('dmp_default', this.defaultString);
            form.append("dmp_data", this.dataString);
            // console.log(form);
            var oldForm = new FormData();
            oldForm.append('default', this.oldDefaultString);
            oldForm.append('data', this.oldDataString);
            $.ajax({
                // "async": false,
                "crossDomain": true,
                "url": interfacePath,
                "type": "POST",
                "headers": {
                    "cache-control": "no-cache"
                },
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "timeout":1000,
                success: function (data) {

                }
            });
        }
    };
    ret.clickDmpEvent = function(dmp_data) {
        this.defaultJSON = {
            'type':'web',
            'uid':this.dmp_userToken,
            'channel':getCookie('dmp_utm_source'),
            'sid':getCookie('_dmp_sessionId'),
            'ua':window.navigator.userAgent,
            'path':window.location.href,
            'refere':this.getReferrer(),
            'ts':new Date().getTime(),
            'v':'v1.0.0'
        };
        this.defaultString = JSON.stringify(this.defaultJSON);
        this.dataString = JSON.stringify(dmp_data);
        this.sendRequest();
    };
    ret.clickDmpEvent(dmp_default_obj("browse","refresh"));
    return ret;
})();