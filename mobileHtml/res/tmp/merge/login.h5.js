define(["base","dialog","loginTpl","trackBase","juicer"],function(e,o,i,a,n){function t(e,o){e.attr("src","/feapi/randCode/createVerifyCode?formtoken="+o+"&s="+(new Date).getTime())}var s=window.userToken||e.readCookie("userToken"),r=!1,l="",c="";e(document).delegate("#J_loginImgCode","click",function(){t(e(this),c)}),e.ajax({url:"/feapi/dmp/userinfo.html ",contentType:"application/json",dataType:"json",data:{userToken:s},type:"get",success:function(e){e&&(status=e.status)&&200==status.code?(a.init(e),r=!0):a.init()},error:function(o){e.log(o),a.init()}});var d;return{show:function(n){if(!r){var n=n||{};c=n.token;var s=juicer.to_html(i,{});callback=n.callback;if(d)return e(".mui-login-msg").addClass("hide").html(""),d.show(),void t(e("#J_loginImgCode"),c);d=o({content:s,id:"J_muiLogin",isMove:n.isMove||"",customClass:"mui-dialog-login-custom",confirm:function(o){var i={};i.username=e("#J_username").val()||"",i.password=e("#J_password").val()||"",i.imgcode=e("#J_imgCode").val()||"",i.formtoken=c;var n="";i.username?i.password?i.imgcode||(n="请填写验证码"):n="请填写密码":n="请填写用户名",n?e(".mui-login-msg").removeClass("hide").html(n):e.ajax({url:l+"/feapi/users/login",dataType:"json",data:i,type:"POST",success:function(i){var n,t,s=i.code;2e5==s&&(n=i.data)&&(t=n.user)?(r=!0,a.init(t),ga("send","event","登录","登录成功",window.location.href),window.userToken=n.userToken,callback&&callback(n),o.close()):e(".mui-login-msg").removeClass("hide").html(i.info)},error:function(o){e.log(o)}})}}),t(e("#J_loginImgCode"),c),window.dialogMap||(window.dialogMap=e.Map()),window.dialogMap.put("loginDialog",d),e("#J_muiLoginToMuiRegister").on("click",function(o){"use strict";var i=window.dialogMap,a=i.get("registerDialog");if(d.close(),a)a&&a.show(),t(e("#J_registerImgCode"),c);else{var s;(s=n.register)&&s.show(n)}o&&event.preventDefault()})}}}});