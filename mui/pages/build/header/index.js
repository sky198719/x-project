define(["base","headerTpl","pcHeader","trackBase"],function(e,i,n,a){"use strict";i&&(e("#J_header").html(i),n.init());var t=e.readCookie("Token");t?e.ajax({url:"/feapi/userInfo.html",contentType:"application/json",dataType:"json",data:{userToken:t},type:"get",success:function(i){i&&i.status?(e("#J_navLogin,#J_navRegister").addClass("hide"),e(".j_userDropdown").html(i.nickName+"<i></i>").removeClass("hide"),a.init(i)):a.init()},error:function(i){e.log(i),a.init()}}):a.init()});