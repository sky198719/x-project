define(function () {

    var xhr = {
        //获取url中的参数
        getUrlParam:function(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
        },

        callFileUpload:function(options){
            var xhr = new XMLHttpRequest();
            xhr.open("POST",options.url, true);
            xhr.send(options.data);
            xhr.onload = function(e) {
                if (this.status == 200) {
                    options.success(this.responseText);
                }
            };
        },
        callPost: function (options) {
            options.type = 'POST';
            return xhr.ajax(options);
        },
        callGet:function(options){
            return xhr.ajax(options);
        },
        callJSON:function(options){
            options.dataType = 'json';
            return xhr.ajax(options);
        },
        ajax: function (options) {
            var isasync = 'async' in options ? options.async : true;
            var method = 'type' in options ? options.type : 'GET';
            var isPreloader = 'preloaderTitle' in options ? true : false;
            if(isPreloader) {
                xxdApp.showPreloader(options.preloaderTitle);
            }
            var isIndicator = 'indicator' in options && options.indicator ? true : false;
            if(isIndicator) {
                xxdApp.showIndicator();
            }
            var reqData = 'data' in options ? options.data:{};
            var reqDataType = 'dataType' in options ? options.dataType : 'none';
            var timeoutNum = 'timeout' in options ? options.timeout : 20000;

            var isResp = false;
            try{
                $.ajax({
                    type: method,
                    url: options.url,
                    data: reqData,
                    async: isasync,
                    dataType: reqDataType,
                    timeout: timeoutNum,
                    success: function (resp) {
                        isResp = true;
                        if(isIndicator) {
                            xxdApp.hideIndicator();
                        }
                        if(isPreloader){
                            xxdApp.hidePreloader();
                        }
                        options.success(resp);
                    },
                    error: function (xhr, errorType, error) {
                        isResp = true;
                        if('error' in options && $.isFunction(options.error)){
                            options.error(xhr, errorType, error);
                        } else {
                            xxdApp.alert("请求出错[" + xhr.status + "]，请重新尝试或检查您的网络是否正常", "提示");
                        }
                        xxdApp.hidePreloader();
                        xxdApp.hideIndicator();
                    }
                });
            }catch(e) {
                isResp = true;
                xxdApp.alert("请求出错[" + e + "]，请重新尝试或联系客服", "提示");
                xxdApp.hidePreloader();
                xxdApp.hideIndicator();
            }

            setTimeout(function(){
                if(isResp){
                    return false;
                }
                if(isPreloader){
                    xxdApp.hidePreloader();
                }
               if(isIndicator) {
                   xxdApp.hideIndicator();
               }
            },timeoutNum);
        }
    };

    return xhr;
});
