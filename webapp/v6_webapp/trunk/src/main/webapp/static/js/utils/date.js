/**
 * Created by zhangyi on 2015/7/2.
 */
define(function () {
    /* 默认语言 */
    var defaults = {
        lang: 'en'
    };
    /* 默认语言值 */
    var lang = {
        'zh-cn': {
            aMonths: ['一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月'],
            aLongMonths: ['一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月'],
            aWeeks: ['日', '一', '二', '三', '四', '五', '六'],
            aLongWeeks: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            a: ['上午', '下午']
        },
        'en': {
            aMonths: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            aLongMonths: ['January', 'February', 'March', 'April', 'May', 'June', 'July',
                'August', 'September', 'October', 'November', 'December'],
            aWeeks: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            aLongWeeks: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
            a: ['AM', 'PM']
        }
    };
    /* 匹配正则 */
    var RegExpObject = /^(y+|M+|d+|H+|h+|m+|s+|E+|S|a)/;
    /* 参数 */
    var _options;
    /* 匹配值处理 */
    var patternValue = {
        y: function (date) {
            return date.getFullYear().toString().length > 1 ? dateObj.toFixedWidth(date.getFullYear(), 2) : dateObj.toFixedWidth(date.getFullYear(), 1);
        },
        yy: function (date) {
            return dateObj.toFixedWidth(date.getFullYear(), 2);
        },
        yyy: function (date) {
            return dateObj.toFixedWidth(date.getFullYear(), 3);
        },
        yyyy: function (date) {
            return date.getFullYear().toString();
        },
        M: function (date) {
            return date.getMonth() + 1;
        },
        MM: function (date) {
            return dateObj.toFixedWidth(date.getMonth() + 1, 2);
        },
        MMM: function (date) {
            return _options.aMonths[date.getMonth()];
        },
        MMMM: function (date) {
            return _options.aLongMonths[date.getMonth()];
        },
        d: function (date) {
            return date.getDate();
        },
        dd: function (date) {
            return dateObj.toFixedWidth(date.getDate(), 2);
        },
        E: function (date) {
            return _options.aWeeks[date.getDay()];
        },
        EE: function (date) {
            return _options.aLongWeeks[date.getDay()];
        },
        H: function (date) {
            return date.getHours();
        },
        HH: function (date) {
            return dateObj.toFixedWidth(date.getHours(), 2);
        },
        h: function (date) {
            return date.getHours() % 12;
        },
        hh: function (date) {
            return dateObj.toFixedWidth(date.getHours() > 12 ? date.getHours() - 12 : date.getHours(), 2);
        },
        m: function (date) {
            return date.getMinutes();
        },
        mm: function (date) {
            return dateObj.toFixedWidth(date.getMinutes(), 2);
        },
        s: function (date) {
            return date.getSeconds();
        },
        ss: function (date) {
            return dateObj.toFixedWidth(date.getSeconds(), 2);
        },
        S: function (date) {
            return dateObj.toFixedWidth(date.getMilliseconds(), 3);
        },
        a: function (date) {
            return _options.a[date.getHours() < 12 ? 0 : 1];
        }
    };
    var dateObj = {
        formatDate: function (pattern, date, options) {
            _options = $.extend({}, defaults, lang[$.extend({}, defaults, options).lang], options);
            /* 未传入时间,设置为当前时间 */
            if (date == undefined)
                date = new Date();
            /* 传入时间为字符串 */
            if ($.type(date) === "string") {
                if (date == "") date = new Date();
                else date = new Date(date.replace(/-/g, "/"));
            }

            var result = [];
            while (pattern.length > 0) {
                RegExpObject.lastIndex = 0;
                var matched = RegExpObject.exec(pattern);
                if (matched) {
                    result.push(patternValue[matched[0]](date));
                    pattern = pattern.slice(matched[0].length);
                } else {
                    result.push(pattern.charAt(0));
                    pattern = pattern.slice(1);
                }
            }
            return result.join('');
        },
        parseDate:function(dateValue){
            return new Date(Date.parse(dateValue.replace(/-/g,"/")));
        },
        /* 补全 */
        toFixedWidth: function (value, length) {
            var result = "00" + value.toString();
            return result.substr(result.length - length);
        },
        addDay:function (date,days){ 
	       if ($.type(date) === "string") {
	           if (date == "") date = new Date();
	           else date = new Date(date.replace(/-/g, "/"));
	       }
	       date.setDate(date.getDate() + days); 
	       return date; 
	    }, 
		addMonth:function (date,months){ 
	       if ($.type(date) === "string") {
	           if (date == "") date = new Date();
	           else date = new Date(date.replace(/-/g, "/"));
	       }
	       date.setMonth(date.getMonth() + months); 
	       return date; 
	    }
    };
    return dateObj;
});
