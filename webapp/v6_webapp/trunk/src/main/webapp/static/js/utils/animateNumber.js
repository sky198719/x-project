define(function () {
    var defaults = {
        from:         0,
        to:           0,
        precision:    0,
        intervalNumber:    15,
        steps:      200,
        valueType: 'none'
    };
    var animateNumber = {
        animate: function (options) {
            for (var opt in defaults) {
                if (!options.hasOwnProperty(opt)) {
                    options[opt] = defaults[opt];
                }
            }

            var from = options.from;
            var to   = options.to;
            var value = from,
                    toValue = Math.abs(from - to),
                    counter = new Number((Math.ceil(toValue / options.steps)) + new Number(((Math.random() * 0.1 + 0.1).toFixed(options.precision)))),
                    interval;

            if (to < from) {
                counter = -counter;
            }

            var isCallBack = typeof(options.callBack) == 'function' ? true : false;
            var isFloat =  'isFloat' in options ? options.isFloat : false;

            interval = setInterval(
                    function () {
                        value += counter;
                        if (value >= to && counter > 0 || value <= to && counter < 0) {
                            clearInterval(interval);
                            value = to;
                        }
                        var showValue = value;
                        if(isFloat) {
                            showValue = parseFloat(showValue).toFixed(options.precision);
                        }
                        if(isCallBack){
                            options.callBack(showValue);
                        } else {
                            if('money' == options.valueType){
                                showValue = appFunc.fmoney(value, options.precision)
                            }
                            $$(options.element).html(showValue);
                        }
                    },
                    options.intervalNumber
            );
        }
    };
    return animateNumber;
});
