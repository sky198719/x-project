define(function () {
    var topupView = {
        init: function (params) {
            appFunc.bindEvents(params.bindings);
        }
    };
    return topupView;
});
