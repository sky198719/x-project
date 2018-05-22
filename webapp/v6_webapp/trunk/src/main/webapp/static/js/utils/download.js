define(function () {
    var download = {
        bind: function () {
            var bindings = [
                {
                    element: 'img[name="home_downloadClose"]',
                    event: 'click',
                    handler: download.downloadClose
                },
                {
                    element: 'button[name="home_download"]',
                    event: 'click',
                    handler: download.homeDownload
                },
            ];
            appFunc.bindEvents(bindings);
        },
        show:function(){
            download.bind();
            $$(".index_download").show();
        },
        downloadClose: function () {
            $$(".index_download").hide();
        },
        homeDownload: function () {
            try {
                gaClickEvent({property1: "App下载", property2: "下载App", property3: "下载App"});
            } catch (e) {
            }
            window.location.href = 'static/html/download/app.html?model=auto';
        }
    };
    return download;
});
