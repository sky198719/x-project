define(function () {
    var appFunc = {
        isWeixin: function () {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        },

        getEventDetailPageQuery: function (event) {
            return event.detail.page.query;
        },

        sameWeek:function(now){
            var oneDayTime = 1000*60*60*24;
            var now_other =parseInt(now.getTime()/oneDayTime);
            return parseInt((now_other+4)/7);
        },

        getCache: function (param) {
            var value = "";
            req.callGet({
                url: 'getCache.do',
                dataType: 'json',
                data: {
                    key: param.key
                },
                async: false,
                success: function (result) {
                    if (result.resultCode == 0) {
                        value = result.value;
                    }
                }
            });
            return value;
        },
        getCookie:function(name){
            var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
            if(arr=document.cookie.match(reg))
                return unescape(arr[2]);
            else
                return null;
        },
        setToken: function (param) {
            var result = {};
            req.callJSON({
                url: "setToken.do",
                data: {
                    name: param.name,
                    id: param.id
                },
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data.resultCode == 0) {
                        result.code = 0;
                        result.data = data;
                    } else {
                        result.code = -1;
                    }
                }
            });
            return result;
        },
        isPhonegap: function () {
            return (typeof(cordova) !== 'undefined' || typeof(phonegap) !== 'undefined');
        },

        isEmail: function (str) {
            var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
            return reg.test(str);
        },

        getPageNameInUrl: function (url) {
            url = url || '';
            var arr = url.split('.');
            return arr[0];
        },

        isEmpty: function (obj) {
            for (var prop in obj) {
                if (obj.hasOwnProperty(prop))
                    return false;
            }

            return true;
        },

        fmoney: function (s, n) {
            if (s == null || s === '') {
                return "0.00";
            }
            if (typeof(s) == "undefined") {
                return "0.00";
            }

            if (typeof(n) == "undefined") {
                n = 0;
            }

            n = n >= 0 && n <= 20 ? n : 2;
            s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
            var l = s.split(".")[0].split("").reverse(),
                r = s.split(".")[1];
            var t = "";
            for (var i = 0; i < l.length; i++) {
                t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
            }

            if (n == 0) {
                return t.split("").reverse().join("");
            } else {
                return t.split("").reverse().join("") + "." + r;
            }
        },

        validateName: function (arg) {
            //var patter = /^[a-z|A-Z]\w*$/; //必须以字母开头，
            var patter = /^[a-z|A-Z][^@][\u4E00-\u9FA5\uf900-\ufa2d\w.]{1,16}$/;
            if (appFunc.len(arg) < 5 || appFunc.len(arg) > 16) {
                return '用户名长度应为5-16个字符。';
            }
            var patter1 = /^[^?!@#$%\\^&*()\s]+$/;
            if (!patter1.test(arg)) {
                return '用户名不可以使用特殊字符';
            }
            if (!patter.test(arg)) {
                return '用户名应以字母开头';
            }
            return 'true';
        },

        len: function (s) {
            var l = 0;
            var a = s.split("");
            for (var i = 0; i < a.length; i++) {
                if (a[i].charCodeAt(0) < 299) {
                    l++;
                } else {
                    l += 2;
                }
            }
            return l;
        },
        /**
         * 校验支付密码
         * @param arg
         * @returns {string}
         */
        validatePassword: function (arg) {
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
        },

        validateMobile: function (arg) {
            var patter = /^0?(13|15|14|17|18)[0-9]{9}$/;
            if (patter.test(arg)) {
                return 'true';
            } else {
                return '请输入正确的手机号码';
            }
        },

        isPlusInteger: function (arg) {
            var patter = /^[1-9]\d*$/;
            if (patter.test(arg)) {
                return true;
            } else {
                return false;
            }
        },

        isPlusFloat: function (arg) {
            var patter = /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/;
            if (patter.test(arg)) {
                return true;
            } else {
                return false;
            }
        },

        isFloat: function (value) {
            var regx = /^\d{1,}$|^\d{1,10}\.\d{1,2}$/gi;
            return regx.test(value);
        },

        getCharLength: function (str) {
            var iLength = 0;
            for (var i = 0; i < str.length; i++) {
                if (str.charCodeAt(i) > 255) {
                    iLength += 2;
                }
                else {
                    iLength += 1;
                }
            }
            return iLength;
        },

        matchUrl: function (string) {
            var reg = /((http|ftp|https):\/\/)?[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&;:\/~\+#]*[\w\-\@?^=%&;\/~\+#])?/g;

            string = string.replace(reg, function (a) {
                if (a.indexOf('http') !== -1 || a.indexOf('ftp') !== -1) {
                    return '<a href=\"#\" onclick=\"event.stopPropagation();window.open(\'' + a + '\',\'_blank\')\">' + a + '</a>';
                }
                else {
                    return '<a href=\"#\" onclick=\"event.stopPropagation();window.open(\'http://' + a + '\',\'_blank\')\">' + a + '</a>';
                }
            });
            return string;
        },

        /**
         * 绑定事件和数据
         *
         * @param bindings
         */
        bindEvents: function (bindings) {
            for (var i in bindings) {
                if (bindings[i].selector) {
                    $$(bindings[i].element)
                        .on(bindings[i].event, bindings[i].selector, bindings[i].handler);
                } else {
                    //alert(bindings[i].element + "=" + $$(bindings[i].element));
                    if (bindings[i].attrData) {
                        for (var data in bindings[i].attrData) {
                            $$(bindings[i].element).attr(bindings[i].attrData.name, bindings[i].attrData.value);
                        }
                    }

                    $$(bindings[i].element).off(bindings[i].event, bindings[i].handler);

                    $$(bindings[i].element).on(bindings[i].event, bindings[i].handler);
                }
            }
        },
        isLogin: function () {
            var result = false;

            appFunc.setWeixinUser();

            req.callGet({
                url: "user/isLogin.do?" + new Date().getTime(),
                dataType: 'json',
                async: false,
                data: {},
                indicator: true,
                success: function (data) {
                    if (data.isLogin != null) {
                        result = data.isLogin;
                    }
                }
            });
            return result;
        },
        getCurrentUser: function () {
            var user;
            req.callPost({
                url: 'user/getCurrentUser.do',
                data: {},
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code == '0') {
                        user = result.data.user;
                    }
                }
            });
            return user;
        },

        getCurrentDate: function () {
            var result = '';
            req.callPost({
                url: 'currentDate.do',
                data: {},
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data.resultCode == 0) {
                        result = data.currentDate;
                    } else {
                        console.log("get current time error");
                    }
                }
            });
            return result;
        },
        /**
         * 计算两个时间差，多少天多少小时多少分
         * @param startTime  开始时间
         * @param endTime 结束时间
         * @returns {string}
         * @constructor
         */
        getTime: function (startTime, endTime, fontColor, pattern) {
            //将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式
            startTime = startTime.replace(/\-/g, "/");
            endTime = endTime.replace(/\-/g, "/");
            var sTime = new Date(startTime); //开始时间
            var eTime = new Date(endTime); //结束时间
            var dateM = eTime - sTime;
            if (dateM <= 0) {
                return "0";
            }

            var MinMilli = 1000 * 60;         // 初始化变量。
            var HrMilli = MinMilli * 60;
            var DyMilli = HrMilli * 24;
            //计算出相差天数
            var days = Math.floor(dateM / (DyMilli));

            //计算出小时数
            var leave1 = dateM % (DyMilli); //计算天数后剩余的毫秒数
            var hours = Math.floor(leave1 / (HrMilli));
            //计算相差分钟数
            var leave2 = leave1 % (HrMilli);        //计算小时数后剩余的毫秒数
            var minutes = Math.floor(leave2 / (MinMilli));
            //计算相差秒数
            var leave3 = leave2 % (MinMilli);      //计算分钟数后剩余的毫秒数
            var seconds = Math.round(leave3 / 1000);
            var spanBegin = "<span style='" + fontColor + "'>";
            var spanEnd = "</span>";

            var strArr = [];
            if (pattern.dd == true) {
                strArr.push(spanBegin);
                strArr.push((days > 9 ? days : "0" + days));
                strArr.push(spanEnd + "天");
            }
            if (pattern.HH == true) {
                strArr.push(spanBegin);
                strArr.push((hours > 9 ? hours : "0" + hours));
                strArr.push(spanEnd + "小时");
            }
            if (pattern.mm == true) {
                strArr.push(spanBegin);
                strArr.push((minutes > 9 ? minutes : "0" + minutes));
                strArr.push(spanEnd + "分");
            }
            if (pattern.ss == true) {
                strArr.push(spanBegin);
                strArr.push((seconds > 9 ? seconds : "0" + seconds));
                strArr.push(spanEnd + "秒");
            }
            return strArr.join('');
            //return spanBegin + (days > 9 ? days : "0" + days) + spanEnd + "天" + spanBegin + (hours > 9 ? hours : "0" + hours) + spanEnd + "小时" + spanBegin + (minutes > 9 ? minutes : "0" + minutes) + spanEnd + "分" + spanBegin + (seconds > 9 ? seconds : "0" + seconds) + spanEnd + "秒";
        },
        /**
         * 计算两个时间差，多少天多少小时多少分
         * @param startTime  开始时间
         * @param endTime 结束时间
         * @returns {string}
         * @constructor
         */
        getCountDownTime: function (startTime, endTime, pattern) {
            var strArr = [];
            //将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式
            startTime = startTime.replace(/\-/g, "/");
            endTime = endTime.replace(/\-/g, "/");
            var sTime = new Date(startTime); //开始时间
            var eTime = new Date(endTime); //结束时间
            var dateM = eTime - sTime;
            if (dateM <= 0) {
                if (pattern.dd == true) {
                    strArr.push("00");
                }
                if (pattern.HH == true) {
                    strArr.push("00");
                }
                if (pattern.mm == true) {
                    strArr.push("00");
                }
                if (pattern.ss == true) {
                    strArr.push("00");
                }
                return strArr;
            }

            var MinMilli = 1000 * 60;         // 初始化变量。
            var HrMilli = MinMilli * 60;
            var DyMilli = HrMilli * 24;
            //计算出相差天数
            var days = Math.floor(dateM / (DyMilli));

            //计算出小时数
            var leave1 = dateM % (DyMilli); //计算天数后剩余的毫秒数
            var hours = Math.floor(leave1 / (HrMilli));
            //计算相差分钟数
            var leave2 = leave1 % (HrMilli);        //计算小时数后剩余的毫秒数
            var minutes = Math.floor(leave2 / (MinMilli));
            //计算相差秒数
            var leave3 = leave2 % (MinMilli);      //计算分钟数后剩余的毫秒数
            var seconds = Math.round(leave3 / 1000);


            if (pattern.dd == true) {
                strArr.push((days > 9 ? days : "0" + days));
            }
            if (pattern.HH == true) {
                strArr.push((hours > 9 ? hours : "0" + hours));
            }
            if (pattern.mm == true) {
                strArr.push((minutes > 9 ? minutes : "0" + minutes));
            }
            if (pattern.ss == true) {
                strArr.push((seconds > 9 ? seconds : "0" + seconds));
            }
            return strArr;
        },
        trim: function (s) {
            return s.replace(/(^\s*)|(\s*$)/g, "");
        },

        setWeixinUser: function () {
            if (!appFunc.isWeixin()) {
                return;
            }
            req.callPost({
                url: "weixin/view/setWeixinUser.do",
                dataType: 'json',
                async: false,
                data: {},
                success: function (data) {
                    console.log(data);
                }
            });
        },
        autoUserBind: function (param) {
            if (!appFunc.isWeixin()) {
                return;
            }
            req.callPost({
                url: "weixin/view/autoUserBind.do",
                dataType: 'json',
                async: false,
                data: {},
                success: function (data) {
                    var resultCode = data.resultCode;
                    if (resultCode == 0) {
                        var html = '<div style="position: fixed;height: 100%;top: 0;background: rgba(0,0,0,0.65);z-index: 9999999;text-align: center;" id="wxBind">';
                        html += '<img src="static/img/xxd/home/weixinBind.png" style="width: 78%;height: 17rem;margin-top: 4.5rem;"/>';
                        html += '<img src="static/img/xxd/home/close.png" style="width: 8%;top: 23rem;left: 9.5rem;position: absolute;" id="wxBind_close"/>';
                        html += '</div>';
                        $("body").append(html);

                        $$("#wxBind_close").on("click", function () {
                            $("#wxBind").hide();
                            if (param != undefined) {
                                param.successCallBack();
                            }
                        });
                    } else if (resultCode == 1) {
                        if (param != undefined) {
                            param.failCallBack();
                        }
                    }
                }
            });
        },

        isRiskResult:function(){

            var flg = false;
            req.callGet({
                url:'activity/getRiskResult.do',
                dataType:'json',
                async:false,
                success:function(result){
                    if(result.resultCode == 0 && !result.status) {
                        flg = true;
                        xxdApp.showPreloader("为了不影响您的投资，请先进行风险承受能力评估");
                        setTimeout(function () {
                            xxdApp.hidePreloader();
                            var xxd_utm_source = getDmpUrlParam("xxd_utm_source");
                            if (xxd_utm_source != "" && xxd_utm_source != null){
                                window.location.href = result.activity_url + "/html/riskAssessment/riskAssessment-web.html" + "?xxd_utm_source=" + xxd_utm_source;
                            }else {
                                window.location.href = result.activity_url + "/html/riskAssessment/riskAssessment-web.html";
                            }
                            function getDmpUrlParam(item) {
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
                            }
                        }, 2000);
                    }
                }
            });
            return flg;
        },

        isFreeLogin: function () {
            var result = false;
            $.ajax({
                url: "weixin/view/isFreeLogin.do?" + new Date().getTime(),
                dataType: 'json',
                async: false,
                data: {},
                success: function (data) {
                    if (data.resultCode == 0) {
                        result = true
                    }
                }
            });
            return result;
        },
        //获取地址栏url参数
        GetQueryString: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return decodeURI(r[2]);
            return null;
        },
        /**大写切换*/
        chineseNumber: function (dValue) {
            try {
                var maxDec = 2;
                // 验证输入金额数值或数值字符串：
                dValue = dValue.toString().replace(/,/g, "");
                dValue = dValue.replace(/^0+/, ""); // 金额数值转字符、移除逗号、移除前导零
                if (dValue == "") {
                    return "零元"; //元整
                } // （错误：金额为空！）
                else if (isNaN(dValue)) {
                    return "错误：金额不是合法的数值！";
                }
                var minus = ""; // 负数的符号“-”的大写：“负”字。可自定义字符，如“（负）”。
                var CN_SYMBOL = ""; // 币种名称（如“人民币”，默认空）
                if (dValue.length > 1) {
                    if (dValue.indexOf('-') == 0) {
                        dValue = dValue.replace("-", "");
                        minus = "负";
                    } // 处理负数符号“-”
                    if (dValue.indexOf('+') == 0) {
                        dValue = dValue.replace("+", "");
                    } // 处理前导正数符号“+”（无实际意义）
                }
                // 变量定义：
                var vInt = "";
                var vDec = ""; // 字符串：金额的整数部分、小数部分
                var resAIW; // 字符串：要输出的结果
                var parts; // 数组（整数部分.小数部分），length=1时则仅为整数。
                var digits, radices, bigRadices, decimals; // 数组：数字（0~9——零~玖）；基（十进制记数系统中每个数字位的基是10——拾,佰,仟）；大基（万,亿,兆,京,垓,杼,穰,沟,涧,正）；辅币（元以下，角/分/厘/毫/丝）。
                var zeroCount; // 零计数
                var i, p, d; // 循环因子；前一位数字；当前位数字。
                var quotient, modulus; // 整数部分计算用：商数、模数。
                // 金额数值转换为字符，分割整数部分和小数部分：整数、小数分开来搞（小数部分有可能四舍五入后对整数部分有进位）。
                var NoneDecLen = (typeof (maxDec) == "undefined" || maxDec == null || Number(maxDec) < 0 || Number(maxDec) > 5); // 是否未指定有效小数位（true/false）
                parts = dValue.split('.'); // 数组赋值：（整数部分.小数部分），Array的length=1则仅为整数。
                if (parts.length > 1) {
                    vInt = parts[0];
                    vDec = parts[1]; // 变量赋值：金额的整数部分、小数部分
                    if (NoneDecLen) {
                        maxDec = vDec.length > 5 ? 5 : vDec.length;
                    } // 未指定有效小数位参数值时，自动取实际小数位长但不超5。
                    var rDec = Number("0." + vDec);
                    rDec *= Math.pow(10, maxDec);
                    rDec = Math.round(Math.abs(rDec));
                    rDec /= Math.pow(10, maxDec); // 小数四舍五入
                    var aIntDec = rDec.toString().split('.');
                    if (Number(aIntDec[0]) == 1) {
                        vInt = (Number(vInt) + 1).toString();
                    } // 小数部分四舍五入后有可能向整数部分的个位进位（值1）
                    if (aIntDec.length > 1) {
                        vDec = aIntDec[1];
                    } else {
                        vDec = "";
                    }
                } else {
                    vInt = dValue;
                    vDec = "";
                    if (NoneDecLen) {
                        maxDec = 0;
                    }
                }
                if (vInt.length > 44) {
                    return "错误：金额值太大了！整数位长【" + vInt.length.toString() + "】超过了上限——44位/千正/10^43（注：1正=1万涧=1亿亿亿亿亿，10^40）！";
                }
                // 准备各字符数组 Prepare the characters corresponding to the digits:
                digits = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); // 零~玖
                radices = new Array("", "拾", "佰", "仟"); // 拾,佰,仟
                bigRadices = new Array("", "万", "亿", "兆", "京", "垓", "杼", "穰", "沟", "涧", "正"); // 万,亿,兆,京,垓,杼,穰,沟,涧,正
                decimals = new Array("角", "分", "厘", "毫", "丝"); // 角/分/厘/毫/丝
                resAIW = ""; // 开始处理
                // 处理整数部分（如果有）
                if (Number(vInt) > 0) {
                    zeroCount = 0;
                    for (i = 0; i < vInt.length; i++) {
                        p = vInt.length - i - 1;
                        d = vInt.substr(i, 1);
                        quotient = p / 4;
                        modulus = p % 4;
                        if (d == "0") {
                            zeroCount++;
                        } else {
                            if (zeroCount > 0) {
                                resAIW += digits[0];
                            }
                            zeroCount = 0;
                            resAIW += digits[Number(d)] + radices[modulus];
                        }
                        if (modulus == 0 && zeroCount < 4) {
                            resAIW += bigRadices[quotient];
                        }
                    }
                    resAIW += "元"; //元
                }
                // 处理小数部分（如果有）
                for (i = 0; i < vDec.length; i++) {
                    d = vDec.substr(i, 1);
                    if (d != "0") {
                        resAIW += digits[Number(d)] + decimals[i];
                    }
                }
                // 处理结果
                if (resAIW == "") {
                    resAIW = "零元" + ""; //元
                } // 零元
                if (vDec == "") {
                    resAIW += ""; //整
                } // ...元整
                resAIW = CN_SYMBOL + minus + resAIW; // 人民币/负......元角分/整
                dValue = resAIW;
            } catch (e) {
                console.log(e);
            } finally {
                return dValue;
            }
        }
    };
    return appFunc;
});