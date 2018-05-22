define(function () {
    var dmp_userId = "";
    var dataString;
    var defaultString;
    var oldDataString;
    var oldDefaultString;
    var xxd_dmp = {
        init: function () {
            xxd_dmp.clickDmpEvent(dmp_default_obj("browse","refresh"));
            if (document.cookie.indexOf("_dmp_sessionId") == -1) {
                xxd_dmp._dmp_sessionId = new Date().getTime() + String(randomNum(4));
                xxd_dmp.setCookie("_dmp_sessionId", xxd_dmp._dmp_sessionId,"1825")
            }
            var dmp_utm_source = xxd_dmp.getDmpUrlParam("utm_source") || "";
            if (document.cookie.indexOf("dmp_utm_source") == -1 || dmp_utm_source) {
                xxd_dmp.setCookie("dmp_utm_source",dmp_utm_source ,"1");
            }
            function  dmp_default_obj(action,event) {
                var dmp_default_obj = {};
                dmp_default_obj.action = action;
                dmp_default_obj.event = event;
                //console.log(dmp_data);
                return dmp_default_obj;
            }
            function randomNum(n){
                var t='';
                for(var i=0;i<n;i++){
                    t+=Math.floor(Math.random()*10);
                }
                return t;
            }
        },
        getReferrer: function () {
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
        },
        getDmpUrlParam: function (item) {
            var pattern = new RegExp("[?&]"+ item +"\=([^&]+)", "g");
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
            //console.log(items);
            return items;
        },
        setCookie: function (name, value,Days) {
            //var Days = 1825;
            var exp = new Date();
            exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
            document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
        },

        getCookie: function (cookieName) {
            var strCookie = document.cookie;
            var arrCookie = strCookie.split("; ");
            for (var i = 0; i < arrCookie.length; i++) {
                var arr = arrCookie[i].split("=");
                if (cookieName == arr[0]) {
                    return arr[1];
                }
            }
            return "";
        },
        sendRequest: function () {
            if(window.FormData){
                var form = new FormData(),
                    self = this;
                form.append('dmp_default', defaultString);
                form.append("dmp_data", dataString);
                if(GC.Debug()){
                    console.log(form.get("default"));
                    console.log(form.get("data"));
                }
                var interfacePath = "",
                    oldPath = "";
                if(window.location.host == "www.xinxindai.com"|| window.location.host =="m.xinxindai.com"){
                    interfacePath = "https://dmp.xinxindai.com/dmp_web/collect";
                }else{
                    interfacePath = "http://118.178.90.205/dmp_web/collect";
                }
                $.ajax({
                    "crossDomain": true,
                    "url": interfacePath,
                    "type": "POST",
                    // "headers": {
                    //     "cache-control": "no-cache"
                    // },
                    "processData": false,
                    "contentType": false,
                    "mimeType": "multipart/form-data",
                    "data": form,
                    "timeout":1000,
                    success: function (data) {
                        if(GC.Debug()){
                            console.log(data);
                        }
                    }
                });
            }
        },
        clickDmpEvent: function (dmp_data) {
            $.ajax({
                url: 'user/getCurrentUser.do',
                async: false,
                data: {},
                dataType: 'json',
                timeout:1000,
                success: function (data) {
                    if (data.code == 0) {
                        dmp_userId = data.data.user.userId;
                    } else {
                        dmp_userId = "";
                    }
                    var defaultJSON = {
                        'type':'web',
                        'uid':xxd_dmp.getCookie('userToken'),
                        'channel':xxd_dmp.getCookie('dmp_utm_source'),
                        'sid':xxd_dmp.getCookie('_dmp_sessionId'),
                        'ua':window.navigator.userAgent,
                        'path':window.location.href,
                        'refere':xxd_dmp.getReferrer(),
                        'ts':new Date().getTime(),
                        'v':'v1.0.0'
                    };
                    defaultString = JSON.stringify(defaultJSON);
                    dataString = JSON.stringify(dmp_data);
                    xxd_dmp.sendRequest();
                }
            })
        },
        dmp_data_obj:function(action,event,dev_id,target_id,textHref,dmp_text,dmp_money,dmp_redBag,xxd_utm_source) {
            var dmp_data_obj = {};
            dmp_data_obj.action = action;
            dmp_data_obj.event = event;
            dmp_data_obj.dev_id = dev_id;
            dmp_data_obj.target_id = {};
            if(target_id != null && target_id!= "" && target_id != undefined){
                dmp_data_obj.target_id.id = target_id;
            }
            if(textHref != null && textHref!= "" && textHref != undefined){
                dmp_data_obj.target_id.textHref = textHref;
            }
            if(dmp_text != null && dmp_text!= "" && dmp_text != undefined){
                dmp_data_obj.target_id.text = dmp_text;
            }
            if(dmp_money != null && dmp_money!= "" && dmp_money != undefined){
                dmp_data_obj.target_id.money = dmp_money;
            }
            if(dmp_redBag != null && dmp_redBag!= "" && dmp_redBag != undefined){
                dmp_data_obj.target_id.redBag = dmp_redBag;
            }
            if(xxd_utm_source != null && xxd_utm_source!= "" && xxd_utm_source != undefined){
                dmp_data_obj.xxd_utm_source = xxd_utm_source;
            }
            //console.log(dmp_data);
            return dmp_data_obj;
        }
    };
    return xxd_dmp;
});
