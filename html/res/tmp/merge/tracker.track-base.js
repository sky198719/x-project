!function(e,t){"function"==typeof define&&define.amd?define([],t):"object"==typeof exports?module.exports=t():e.trackBase=t()}(this,function(){"use strict";var e=function(){var e=function(){if("undefined"!=typeof self)return self;if("undefined"!=typeof e)return e;if("undefined"!=typeof global)return global;throw new Error("unable to locate global object")}(),t=e.userId||"",n={createXHR:function(){if(e.XMLHttpRequest)return new XMLHttpRequest;if(e.ActiveXObject)return new ActiveXObject("Microsoft.XMLHTTP");throw new Error("浏览器不支持XHR对象！")},ajax:function(){var e=arguments[0],t={type:e.type||"GET",url:e.url||"",processData:e.processData||!0,async:e.async||"true",data:e.data||null,cache:e.cache||null,contentType:e.contentType,beforeSend:e.beforeSend||function(){},success:e.success||function(){},error:e.error||function(){},done:e.done||function(){}};t.beforeSend();var n=this.createXHR();n.open(t.type,t.url,t.async),t.contentType&&n.setRequestHeader("Content-Type",t.contentType),n.setRequestHeader("cache-control",t.cache),n.send(t.data),n.onreadystatechange=function(){4==n.readyState&&(200==n.status?t.success(n.response):t.error(),t.done())}},convertData:function(e){var t=e.data;e.processData;if("object"!=typeof t)return t;var n="";for(var r in t)n+=r+"="+t[r]+"&";return n=n.substring(0,n.length-1)}},r={createCookie:function(e,t,n){var r="";if(n){var a=new Date;a.setTime(a.getTime()+24*n*60*60*1e3),r=";expires="+a.toGMTString()}try{document.cookie=e+"="+t+r+"; path=/"}catch(e){}},readCookie:function(e){try{for(var t=e+"=",n=document.cookie.split(";"),r=0;r<n.length;r++){for(var a=n[r];" "==a.charAt(0);)a=a.substring(1,a.length);if(0==a.indexOf(t))return a.substring(t.length,a.length)}}catch(e){return null}return null},isEmptyObject:function(e){var t;for(t in e)return!1;return!0},parseObject:function(e){return new Function("return "+e)()},addHandle:function(e,t,n){var r=document.body;r.addEventListener?r.addEventListener(t,function(t){var a=Array.prototype.slice.call(r.querySelectorAll(e)),o=t.target;if(a.indexOf(o)!=-1)return n.apply(o,arguments)},!1):r.attachEvent?r.attachEvent("on"+t,function(t){var a=Array.prototype.slice.call(r.querySelectorAll(e)),o=t.target;if(a.indexOf(o)!=-1)return n.apply(o,arguments)}):e["on"+t]=n}};return{getBrowser:function(){var t=function(e){var t,n=document.getElementsByClassName("_C_wrapTrack");return(t=n.length)?n=n[0]:(n=document.createElement("div"),n.style.cssText="position:absolute;left:-9999px;",n.setAttribute("class","_C_wrapTrack")),n.innerHTML='<div id="_J_track" style="'+e+'border-radius:100px;"></div>',t||document.getElementsByTagName("body")[0].appendChild(n),document.getElementById("_J_track").style.borderRadius};if(e)return e.VBArray?"ie":e.chrome?"chrome":e.updateCommands?"ff":e.openDatabase&&!e.chrome?"safari":t("-o-")?"o":void 0},gaInit:function(n){!function(e,t,n,r,a,o,i){e.GoogleAnalyticsObject=a,e[a]=e[a]||function(){(e[a].q=e[a].q||[]).push(arguments)},e[a].l=1*new Date,o=t.createElement(n),i=t.getElementsByTagName(n)[0],o.async=1,o.src=r,i.parentNode.insertBefore(o,i)}(e,document,"script","//www.google-analytics.com/analytics.js","ga");var r=document.referrer;""==r&&(r=e.location.href),t=n||t,ga("create","UA-55539630-1","auto",{userId:t}),ga("require","linkid","linkid.js"),ga("require","displayfeatures"),ga("require","ec"),ga("set","dimension2",t),ga("set","dimension3","web"),ga("set","dimension4",r),ga(function(e){ga("set","dimension6",e.get("clientId"))}),ga("set","transport","beacon"),ga("send","pageview")},gtmInit:function(){!function(e,t,n,r,a){e[r]=e[r]||[],e[r].push({"gtm.start":(new Date).getTime(),event:"gtm.js"});var o=t.getElementsByTagName(n)[0],i=t.createElement(n),c="dataLayer"!=r?"&l="+r:"";i.async=!0,i.src="https://www.googletagmanager.com/gtm.js?id="+a+c,o.parentNode.insertBefore(i,o)}(e,document,"script","dataLayer","GTM-PRZHDF")},BaiduPush:function(){!function(){var t=document.createElement("script"),n=e.location.protocol.split(":")[0];"https"===n?t.src="https://zz.bdstatic.com/linksubmit/push.js":t.src="http://push.zhanzhang.baidu.com/push.js";var r=document.getElementsByTagName("script")[0];r.parentNode.insertBefore(t,r)}()},BaiduHm:function(){!function(){var e=document.createElement("script");e.src="https://hm.baidu.com/hm.js?189e41b9b5d3f288cad1459403f7a245";var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t)}()},dmpInit:function(){function t(e){for(var t="",n=0;n<e;n++)t+=Math.floor(10*Math.random());return t}function a(e){var t=new RegExp("[?&]"+e+"=([^&]+)","g"),n=t.exec(decodeURIComponent(location.href)),e=null;if(null!=n)try{e=decodeURIComponent(decodeURIComponent(n[1]))}catch(t){try{e=decodeURIComponent(n[1])}catch(t){e=n[1]}}return e}function o(e,t,n,r,a,o,i){var c={};return c.action=e,c.event=t,c.dev_id=n,c.target_id={},null!=r&&""!=r&&void 0!=r&&(c.target_id.id=r),null!=a&&""!=a&&void 0!=a&&(c.target_id.textHref=a),null!=o&&""!=o&&void 0!=o&&(c.target_id.text=o),null!=i&&""!=i&&void 0!=i&&(c.xxd_utm_source=i),c}function i(e,t){var n={};return n.action=e,n.event=t,n}var c="",s={};s.dmp_userId="",s.dmp_userToken="",r.readCookie("ID")&&(s.dmp_userId=r.readCookie("ID")),r.readCookie("Token")?s.dmp_userToken=r.readCookie("Token"):r.readCookie("userToken")&&(s.dmp_userToken=r.readCookie("userToken"));var d=(new Date).getTime()+String(t(4));r.readCookie("_dmp_sessionId")||r.createCookie("_dmp_sessionId",d,"1825");var u=a("utm_source")||"";r.readCookie("dmp_utm_source")&&!u||r.createCookie("dmp_utm_source",u,"1");var l=e.location.href;c="www.xinxindai.com"==e.location.host||"m.xinxindai.com"==e.location.host?l.indexOf("xinxindai.com/jie/")>=0?"https://dmp.xinxindai.com/dmp_jie/collect":"https://dmp.xinxindai.com/dmp_web/collect":l.indexOf("/jie/")>=0?"http://118.178.90.205/dmp_jie/collect":"http://118.178.90.205/dmp_web/collect",s.getReferrer=function(){var t="";try{t=e.top.document.referrer}catch(n){if(e.parent)try{t=e.parent.document.referrer}catch(e){t=document.referrer}}return t},s.sendRequest=function(){if(e.FormData&&"function"==typeof e.FormData){var t=new FormData,r=this;t.append("dmp_default",this.defaultString),t.append("dmp_data",this.dataString),this.settings={async:!0,crossDomain:!0,url:c,type:"POST",cache:"no-cache",contentType:!1,data:t},n.ajax(r.settings)}else{var t={},r=this,a="http://"+e.location.host+"/dmp/dmp_web/collect";t.dmp_default=this.defaultString,t.dmp_data=this.dataString,t=JSON.stringify(t),n.ajax({async:!0,contentType:"application/json",url:a,type:"post",data:t,success:function(e){},error:function(e){}})}},s.clickDmpEvent=function(t){this.defaultJSON={type:"web",uid:this.dmp_userToken,channel:r.readCookie("dmp_utm_source"),sid:r.readCookie("_dmp_sessionId"),ua:e.navigator.userAgent,path:e.location.href,refere:this.getReferrer(),ts:(new Date).getTime(),v:"v1.0.0"},this.defaultString=JSON.stringify(this.defaultJSON),this.dataString=JSON.stringify(t),this.sendRequest()},s.clickDmpEvent(i("browse","refresh")),r.addHandle(".dmp-click","click",function(e){var t=e.target,n=t.getAttribute("eventType")||t.getAttribute("eventtype"),r=t.getAttribute("dev_id"),i=t.getAttribute("target_id"),c=t.getAttribute("textHref"),d=t.getAttribute("dmp_text"),u=a("xxd_utm_source")||"",l=t.getAttribute("dmp_action")||"click";s.clickDmpEvent(o(l,n,r,i,c,d,u))})},eventGATrack:function(e){var t=e,n=t.getAttribute("ga-hitType")||"event",a=t.getAttribute("ga-category"),o=t.getAttribute("ga-action"),i=t.getAttribute("ga-label"),c=t.getAttribute("ga-value"),s={};n&&a&&o&&(s={hitType:n,eventCategory:a,eventAction:o},i&&("ga-url"==i&&(i=location.href),s.eventLabel=i),c&&(s.eventValue=c));for(var d=(t.getAttribute("ga-commnd")||"").split(/,/g),u=0,l=d.length;u<l;u++)if("ec:addPromo"==d[u]){var p=t.getAttribute("ga-ec-addPromo"),f=r.parseObject(p);r.isEmptyObject(f)||ga("ec:addPromo",f)}else if("ec:addProduct"==d[u]){var m=t.getAttribute("ga-ec-addProduct"),g=r.parseObject(m);r.isEmptyObject(g)||ga("ec:addProduct",g)}else if("ec:setAction"==d[u]){var h=t.getAttribute("ga-ec-setAction"),v=t.getAttribute("ga-ec-setAction-command");if(v){var y=r.parseObject(h);y?ga("ec:setAction",v,y):ga("ec:setAction",v)}}r.isEmptyObject(s)||ga("send",s)},showGA:function(e){for(var t=this,n=e||".ga-show",r=document.querySelectorAll(n),a=0;a<r.length;a++)t.eventGATrack(r[a])},clickGA:function(){var e=this;r.addHandle(".ga-click","click",function(t){e.eventGATrack(t.target)})},init:function(e){var t=this,n=e&&e.userId;t.gaInit(n),t.showGA(),t.clickGA(),t.gtmInit(),t.BaiduPush(),t.BaiduHm(),t.dmpInit()}}}();return e});