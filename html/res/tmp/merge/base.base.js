define(["jquery"],function(e){e.extend({trim:function(e){return(e||"").replace(/(^\s*)|(\s*$)/g,"")},email:function(e){var t=/^([a-z0-9A-Z]+[_\-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/;return t.test(e)},log:function(e){window.console&&console.log(e)},error:function(e){window.console&&console.error(e)},debug:function(e){window.console&&console.debug(e)},info:function(e){window.console&&console.info(e)},browerV:function(){var e,t={},n=navigator.userAgent.toLowerCase();return(e=n.match(/msie ([\d.]+)/))?t.ie=e[1]:(e=n.match(/firefox\/([\d.]+)/))?t.firefox=e[1]:(e=n.match(/webkit\/([\d.]+)/))?t.webkit=e[1]:(e=n.match(/opera.([\d.]+)/))?t.opera=e[1]:(e=n.match(/version\/([\d.]+).*safari/))?t.safari=e[1]:0,t},isIE:function(e){var t=document.createElement("b");return t.innerHTML="<!--[if IE "+e+"]><i></i><![endif]-->",1===t.getElementsByTagName("i").length},isDevicePhone:function(){return navigator&&navigator.userAgent&&navigator.userAgent.match(/android|SymbianOS|Windows Phone|iPhone|iPad|iPod/i)},isPlaceholderSupport:function(){return"placeholder"in document.createElement("input")},debounce:function(t,n,r){"function"==typeof t&&(n=n||150,e._timeoutxxd&&window.clearTimeout(e._timeoutxxd),e._timeoutxxd=window.setTimeout(function(){t(r)},n))}}),e.extend({fnObjectKeys:function(e){var t=[];if(Object.keys)t=Object.keys(e);else for(t[t.length]in e);return t},mockAjax:function(t){t&&(t.mock?t.success(t.mock):e.ajax(t))},cachedScript:function(t,n){var r={dataType:"script",cache:!0,url:t,complete:n||function(){}};return e.ajax(r)},parseObject:function(e){return new Function("return "+e)()}}),e.extend({isNumeric:function(e){return!isNaN(parseFloat(e))&&isFinite(e)},getUrlParam:function(e,t){if(t||(t=location.search),"string"==typeof t){var n=t.indexOf("?");n>=0&&(t=t.substring(n+1));for(var r=unescape(t).split("&"),i=0,o=r.length;i<o;i++)if(r[i].indexOf("=")>0&&r[i].split("=")[0].toLowerCase()==e.toString().toLowerCase())return r[i].split("=")[1]}return null},createCookie:function(e,t,n){var r="";if(n){var i=new Date;i.setTime(i.getTime()+24*n*60*60*1e3),r=";expires="+i.toGMTString()}try{document.cookie=e+"="+t+r+"; path=/"}catch(e){}},readCookie:function(e){try{for(var t=e+"=",n=document.cookie.split(";"),r=0;r<n.length;r++){for(var i=n[r];" "==i.charAt(0);)i=i.substring(1,i.length);if(0==i.indexOf(t))return i.substring(t.length,i.length)}}catch(e){return null}return null},eraseCookie:function(t){e.createCookie(t,"",-1)},clone:function(t){var n;return e.support.html5Clone||t.cloneNode?n=t.cloneNode(!0):(fragmentDiv.innerHTML=t.outerHTML,fragmentDiv.removeChild(n=fragmentDiv.firstChild)),n},setLocalStorage:function(e,t,n){try{if(localStorage)localStorage.setItem(e,t);else{var r=t.length;if(r<2e3)this.createCookie(e,t,n);else{var i=r/2e3,o=parseInt(i,10);i>o&&(o+=1);for(var a=0;a<o;a++)val=t.substring(2e3*a,2e3*a+2e3),this.createCookie(e+"_"+a,val,n)}}}catch(e){return-1}},getLocalStorage:function(t){try{if(localStorage)return localStorage.getItem(t);for(var n=0,r="";;){var i=e.readCookie(t+"_"+n);if(!i)break;r+=i,n++}return r}catch(e){}},clearLocalStore:function(t){try{if(localStorage)localStorage.setItem(t,"");else for(var n=0;;){if(!e.readCookie(t+"_"+n))break;e.eraseCookie(t+"_"+n),n++}}catch(e){}}}),e.extend({addEventHandler:function(e,t,n){e.addEventListener?e.addEventListener(t,n,!1):e.attachEvent?e.attachEvent("on"+t,function(){n.call(e)}):e["on"+t]=n},removeEventHandler:function(e,t,n){e.removeEventListener?e.removeEventListener(t,n,!1):e.detachEvent?e.detachEvent("on"+t,n):e["on"+t]=null},_formatEvent:function(t){var n=e.browerV();return n.ie&&(t.charCode="keypress"==t.type?t.keyCode:0,t.eventPhase=2,t.isChar=t.charCode>0,t.pageX=t.clientX+document.body.scrollLeft,t.pageY=t.clientY+document.body.scrollTop,t.preventDefault=function(){this.returnValue=!1},"mouseout"==t.type?t.relatedTarget=t.toElement:"mouseover"==t.type&&(t.relatedTarget=t.fromElement),t.stopPropagation=function(){this.cancelBubble=!0},t.target=t.srcElement,t.time=(new Date).getTime()),t},getEvent:function(){return window.event?e._formatEvent(window.event):e.getEvent.caller.arguments[0]}}),e.extend({Map:function(){return new this._map},StringBuffer:function(){return new this._stringBuffer},_map:function(){this.length=0,this.prefix="hashMap_www_xxd_20160912",this.put=function(e,t){this[this.prefix+e]=t,this.length++},this.get=function(e){return"undefined"==typeof this[this.prefix+e]?null:this[this.prefix+e]},this.keySet=function(){var e=[];for(var t in this)t.substring(0,this.prefix.length)==this.prefix&&e.push(t.substring(this.prefix.length));return this.length=e.length,0==e.length?[]:e},this.values=function(){var e=[];for(var t in this)t.substring(0,this.prefix.length)==this.prefix&&e.push(this[t]);return this.length=e.length,0==e.length?[]:e},this.size=function(){return this.length},this.remove=function(e){delete this[this.prefix+e],this.length--},this.clear=function(){for(var e in this)e.substring(0,this.prefix.length)==this.prefix&&delete this[e]},this.isEmpty=function(){return 0==this.length},this.containKey=function(e){for(var t in this)if(t==this.prefix+e)return!0;return!1},this.toString=function(){var e="";for(var t in this)t.substring(0,this.prefix.length)==this.prefix&&(e+=t.substring(this.prefix.length)+":"+this[t]+"\r\n");return e}},_stringBuffer:function(){this._strings_=new Array,this.append=function(e){this._strings_.push(e)},this.toString=function(){return this._strings_.join("")}}}),e.extend({fnTip:function(t,n,r){function i(r){var o=e.getEvent(r),c=o.target;("false"===s||n[0]!==t[0]&&!n[0].contains(c))&&(n[0]===t[0]||t[0].contains(c)||(a&&a(),n.addClass("hide"),e(document).unbind("mousemove",i)))}var r=r||{},o=r.showCall||void 0,a=r.hideCall||void 0,s=t.attr("overTipShow")||r.isoutshow||void 0;n.removeClass("hide"),o&&o(),e(document).bind("mousemove",i)}}),e.extend({fnDateToString:function(e,t){var n=new Date(e),r=n.getFullYear(),i=n.getMonth()+1;1==(i+"").length&&(i="0"+i);var o=n.getDate();1==(o+"").length&&(o="0"+o);var a=n.getHours();1==(a+"").length&&(a="0"+a);var s=n.getMinutes();1==(s+"").length&&(s="0"+s);var c=n.getSeconds();return 1==(c+"").length&&(c="0"+c),t=t.replace(/yyyy/,r),t=t.replace(/MM/,i),t=t.replace(/dd/,o),t=t.replace(/HH/,a),t=t.replace(/mm/,s),t=t.replace(/ss/,c)},fnStringToDate:function(e,t){function n(e,t){if(r(e,t)){var n,o=new Date,a=k.exec(e),s=i(t),c=s[0]>=0?a[s[0]+1]:o.getFullYear(),l=s[1]>=0?a[s[1]+1]-1:o.getMonth(),u=s[2]>=0?a[s[2]+1]:o.getDate(),h=s[3]>=0?a[s[3]+1]:"",f=s[4]>=0?a[s[4]+1]:"",g=s[5]>=0?a[s[5]+1]:"";if(n=""==h?new Date(c,l,u):new Date(c,l,u,h,f,g),n.getDate()==u)return n}alert("wrong date")}function r(e,t){var n=e;if(""!=n){var r=t;return r=r.replace(/yyyy/,o),r=r.replace(/yy/,a),r=r.replace(/MM/,c),r=r.replace(/M/,l),r=r.replace(/dd/,h),r=r.replace(/d/,f),r=r.replace(/HH/,d),r=r.replace(/H/,p),r=r.replace(/mm/,v),r=r.replace(/m/,x),r=r.replace(/ss/,w),r=r.replace(/s/,b),r=new RegExp("^"+r+"$"),k=r,r.test(n)}}function i(e){var t=new Array,n=0;s=e.search(/yyyy/),s<0&&(s=e.search(/yy/)),s>=0&&(t[n]=s,n++),u=e.search(/MM/),u<0&&(u=e.search(/M/)),u>=0&&(t[n]=u,n++),g=e.search(/dd/),g<0&&(g=e.search(/d/)),g>=0&&(t[n]=g,n++),m=e.search(/HH/),m<0&&(m=e.search(/H/)),m>=0&&(t[n]=m,n++),y=e.search(/mm/),y<0&&(y=e.search(/m/)),y>=0&&(t[n]=y,n++),T=e.search(/ss/),T<0&&(T=e.search(/s/)),T>=0&&(t[n]=T,n++);var r=new Array(s,u,g,m,y,T);for(n=0;n<t.length-1;n++)for(var i=0;i<t.length-1-n;i++)t[i]>t[i+1]&&(temp=t[i],t[i]=t[i+1],t[i+1]=temp);for(n=0;n<t.length;n++)for(var i=0;i<r.length;i++)t[n]==r[i]&&(r[i]=n);return r}var o="([0-9]{4})",a="([0-9]{2})",s=-1,c="(0[1-9]|1[0-2])",l="([1-9]|1[0-2])",u=-1,h="(0[1-9]|[1-2][0-9]|30|31)",f="([1-9]|[1-2][0-9]|30|31)",g=-1,d="([0-1][0-9]|20|21|22|23)",p="([0-9]|1[0-9]|20|21|22|23)",m=-1,v="([0-5][0-9])",x="([0-9]|[1-5][0-9])",y=-1,w="([0-5][0-9])",b="([0-9]|[1-5][0-9])",T=-1,k="";return n(e,t)}}),e.fn.extend({serializeObject:function(){var t={},n=this.serializeArray();return e.each(n,function(){t[this.name]?(t[this.name].push||(t[this.name]=[t[this.name]]),/_time|_date|birthday/g.test(this.name)&&(this.value=new Date(this.value).getTime()/1e3),t[this.name].push(this.value||"")):(/_time|_date|birthday/g.test(this.name)&&(this.value=new Date(this.value).getTime()/1e3),t[this.name]=this.value||"")}),t}}),e.extend({countdown:function(e){var t=parseInt(e/1e3/60/60),n=parseInt(e%36e5/6e4,10),r=parseInt(e%6e4/1e3,10);return 1==(t+"").length&&(t="0"+t),1==(n+"").length&&(n="0"+n),1==(r+"").length&&(r="0"+r),t+":"+n+":"+r}});var t=null;(t=window.console)&&(t.info("%c"+["","一张网页，要经历怎样的过程，才能抵达用户面前？","一位新人，要经历怎样的成长，才能站在技术之巅？","探寻这里的秘密；","体验这里的挑战；","成为这里的主人；","加入新新贷，你，可以影响世界。","如果console前的你热爱前端技术，相信技术改变世界的力量，那么———"].join("\n"),["font-size:14px","line-height:28px","color: #0064b0"].join(";")),t.log(["%c请将简历发送至以下邮箱：\n","%o"].join(""),["font-size:14px","line-height:28px","color: #000"].join(";"),[[[{Email:"liangchen@xinxindai.com"}]]]),t.log("%c邮件标题请以““姓名-应聘XX【前端、产品、java、...】职位-来自console”命名",["font-size:14px","line-height:28px","color: #f00"].join(";")),t.log("%c职位介绍：%o",["font-size:18px","font-weight:bold","line-height:30px","color: #0064b0"].join(";"),"职位介绍：更新中...【你可以影响世界！】"));var n,r=(new Date).getTime();return e.extend({getSystemTime:function(t){e.ajax({url:"/feapi/currentTime?"+(new Date).getTime(),type:"get",data:{},dataType:"json",success:function(e){n=200==e.code?e.data.currentTime:(new Date).getTime(),t()},error:function(){n=(new Date).getTime(),t()}})},xxdAjax:function(t){function i(){e.ajax({url:t.url,contentType:t.contentType||"application/json",dataType:t.dataType||"json",beforeSend:function(i){i.setRequestHeader("clientId",t.clientId||"001");var o=(new Date).getTime();o=n?o+n-r:o,i.setRequestHeader("clientTime",o),t&&t.hasOwnProperty("token")&&i.setRequestHeader("token",t.token||e.readCookie("userToken"))},data:t.data||"",type:t.type||"POST",success:function(n){var r=n.code;if(2e5==r){var i;(i=t.callback)&&i(n.data)}else e.log(n.message);var o;(o=t.callbacks)&&o(n)},error:function(t){e.log(t)}})}n?i():e.getSystemTime(i)}}),e});