define(function () {
    var xxdLog = {
        debug: function (param) {
            if (!GC.Debug()) {
                return;
            }
            console.log(param);
        }
    };
    return xxdLog;
});
