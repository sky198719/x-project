define(["base"],function(e){var t=window.userId||"";return{growingIO:function(e){var t=t||[];if(window._vds=t,t.push(["setAccountId","900b0a2db8f09121"]),t.push(["enableHT",!0]),e){var n=e.userId;n&&t.push(["setCS1","用户id",n])}var a=document.createElement("script");a.type="text/javascript",a.async=!0,a.src="//dn-growing.qbox.me/vds.js";var o=document.getElementsByTagName("script")[0];o.parentNode.insertBefore(a,o)},gaInit:function(e){!function(e,t,n,a,o,i,r){e.GoogleAnalyticsObject=o,e[o]=e[o]||function(){(e[o].q=e[o].q||[]).push(arguments)},e[o].l=1*new Date,i=t.createElement(n),r=t.getElementsByTagName(n)[0],i.async=1,i.src=a,r.parentNode.insertBefore(i,r)}(window,document,"script","//www.google-analytics.com/analytics.js","ga");var n=document.referrer;""==n&&(n=window.location.href),t=e||t,ga("create","UA-55539630-1","auto",{userId:t}),ga("require","linkid","linkid.js"),ga("require","displayfeatures"),ga("require","ec"),ga("set","dimension2",t),ga("set","dimension3","web"),ga("set","dimension4",n),ga(function(e){ga("set","dimension6",e.get("clientId"))}),ga("set","transport","beacon"),ga("send","pageview")},gtmInit:function(){!function(e,t,n,a,o){e[a]=e[a]||[],e[a].push({"gtm.start":(new Date).getTime(),event:"gtm.js"});var i=t.getElementsByTagName(n)[0],r=t.createElement(n),c="dataLayer"!=a?"&l="+a:"";r.async=!0,r.src="https://www.googletagmanager.com/gtm.js?id="+o+c,i.parentNode.insertBefore(r,i)}(window,document,"script","dataLayer","GTM-PRZHDF")},BaiduPush:function(){!function(){var e=document.createElement("script"),t=window.location.protocol.split(":")[0];"https"===t?e.src="https://zz.bdstatic.com/linksubmit/push.js":e.src="http://push.zhanzhang.baidu.com/push.js";var n=document.getElementsByTagName("script")[0];n.parentNode.insertBefore(e,n)}()},BaiduHm:function(){!function(){var e=document.createElement("script");e.src="https://hm.baidu.com/hm.js?189e41b9b5d3f288cad1459403f7a245";var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t)}()},dmpInit:function(){function t(e){for(var t="",n=0;n<e;n++)t+=Math.floor(10*Math.random());return t}function n(e){var t=new RegExp("[?&]"+e+"=([^&]+)","g"),n=t.exec(decodeURIComponent(location.href)),e=null;if(null!=n)try{e=decodeURIComponent(decodeURIComponent(n[1]))}catch(t){try{e=decodeURIComponent(n[1])}catch(t){e=n[1]}}return e}function a(e,t,n,a,o,i,r){var c={};return c.action=e,c.event=t,c.dev_id=n,c.target_id={},null!=a&&""!=a&&void 0!=a&&(c.target_id.id=a),null!=o&&""!=o&&void 0!=o&&(c.target_id.textHref=o),null!=i&&""!=i&&void 0!=i&&(c.target_id.text=i),null!=r&&""!=r&&void 0!=r&&(c.xxd_utm_source=r),c}function o(e,t){var n={};return n.action=e,n.event=t,n}var i="",r={};r.dmp_userId="",r.dmp_userToken="",e.readCookie("ID")&&(r.dmp_userId=e.readCookie("ID")),e.readCookie("userToken")&&(r.dmp_userToken=e.readCookie("userToken"));var c=(new Date).getTime()+String(t(4));e.readCookie("_dmp_sessionId")||e.createCookie("_dmp_sessionId",c,"1825");var s=n("utm_source")||"";e.readCookie("dmp_utm_source")&&!s||e.createCookie("dmp_utm_source",s,"1"),i="www.xinxindai.com"==window.location.host||"m.xinxindai.com"==window.location.host?"https://dmp.xinxindai.com/dmp_web/collect":"http://118.178.90.205/dmp_web/collect",r.getReferrer=function(){var e="";try{e=window.top.document.referrer}catch(t){if(window.parent)try{e=window.parent.document.referrer}catch(t){e=document.referrer}}return e},r.sendRequest=function(){if(window.FormData&&"function"==typeof window.FormData){var t=new FormData,n=this;t.append("dmp_default",this.defaultString),t.append("dmp_data",this.dataString),this.settings={async:!0,crossDomain:!0,url:i,method:"POST",headers:{"cache-control":"no-cache"},processData:!1,contentType:!1,mimeType:"multipart/form-data",data:t},e.ajax(n.settings).done(function(e){})}else{var t={},n=this,a="http://"+window.location.host+"/dmp/dmp_web/collect";t.dmp_default=this.defaultString,t.dmp_data=this.dataString,t=JSON.stringify(t),e.ajax({async:!0,processData:!1,contentType:"application/json",url:a,type:"post",data:t,success:function(e){},error:function(e){}})}},r.clickDmpEvent=function(t){this.defaultJSON={type:"web",uid:this.dmp_userToken,channel:e.readCookie("dmp_utm_source"),sid:e.readCookie("_dmp_sessionId"),ua:window.navigator.userAgent,path:window.location.href,refere:this.getReferrer(),ts:(new Date).getTime(),v:"v1.0.0"},this.defaultString=JSON.stringify(this.defaultJSON),this.dataString=JSON.stringify(t),this.sendRequest()},r.clickDmpEvent(o("browse","refresh")),e(document).delegate(".dmp-click","click",function(){var t=e(this),o=t.attr("eventType"),i=t.attr("dev_id"),c=t.attr("target_id"),s=t.attr("textHref"),d=t.attr("dmp_text"),u=n("xxd_utm_source")||"";r.clickDmpEvent(a("click",o,i,c,s,d,u))})},oneApmInit:function(){var t=null;"stage.xxd.com"!=(t=location.host)&&"test.xxd.com"!=t||(window.BWEUM||(BWEUM={}),BWEUM.info={stand:!0,agentType:"browser",agent:"bi-collector.oneapm.com/static/js/bw-send-411.4.9.js",beaconUrl:"bi-collector.oneapm.com/beacon",licenseKey:"JzamM~qERRUB3AuQ",applicationID:2291026},e.cachedScript("//bi-collector.oneapm.com/static/js/bw-loader-411.4.9.js",function(){e.log("oneApm loaded...")}))},eventGATrack:function(t){var n=t.attr("ga-hitType")||"event",a=t.attr("ga-category"),o=t.attr("ga-action"),i=t.attr("ga-label"),r=t.attr("ga-value"),c={};n&&a&&o&&(c={hitType:n,eventCategory:a,eventAction:o},i&&("ga-url"==i&&(i=location.href),c.eventLabel=i),r&&(c.eventValue=r));for(var s=(t.attr("ga-commnd")||"").split(/,/g),d=0,u=s.length;d<u;d++)if("ec:addPromo"==s[d]){var m=t.attr("ga-ec-addPromo"),p=e.parseObject(m);e.isEmptyObject(p)||ga("ec:addPromo",p)}else if("ec:addProduct"==s[d]){var l=t.attr("ga-ec-addProduct"),g=e.parseObject(l);e.isEmptyObject(g)||ga("ec:addProduct",g)}else if("ec:setAction"==s[d]){var f=t.attr("ga-ec-setAction"),h=t.attr("ga-ec-setAction-command");if(h){var w=e.parseObject(f);w?ga("ec:setAction",h,w):ga("ec:setAction",h)}}e.isEmptyObject(c)||ga("send",c)},showGA:function(t){var n=this,a=t||".ga-show";e(a).each(function(t,a){n.eventGATrack(e(a))})},clickGA:function(){var t=this;e(document).delegate(".ga-click","click",function(){t.eventGATrack(e(this))})},init:function(e){var t=this,n=e&&e.userId;t.gaInit(n),t.showGA(),t.clickGA(),t.growingIO(e),t.gtmInit(),t.BaiduPush(),t.BaiduHm(),t.dmpInit(),t.oneApmInit()}}});