define(function () {
    var borrowUtils = {
        toPeriodunit: function (value) {
            if (value == "MONTH") {
                return "个月";
            } else if (value == "DAY") {
                return "天"
            } else if (value == "SEASON") {
                return "个季度"
            } else if (value == "YEAR") {
                return "年"
            }
        }
    };
    return borrowUtils;
});
