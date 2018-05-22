define(function () {
    var defaults = {

    };
    var animateCss = {
        animateList: function (options) {
            for (var opt in defaults) {
                if (!options.hasOwnProperty(opt)) {
                    options[opt] = defaults[opt];
                }
            }

            var dataSize = options.dataList.length;
            var index = 0;
            var interval = setInterval(
                    function () {
                        if(dataSize == index) {
                            return;
                        }
                        options.callback(options.dataList[index],interval);
                        index++;
                    },
                    options.intervalNumber
            );
        }
    };
    return animateCss;
});