define(function () {
    var sinaParam = [];
    var share = {
        init:function(){
            share.initSina();
        },
        initSina:function(){
            sinaParam = {
                appkey:'3921074379',
                source:'_',
                content:'utf-8'
            };
        },
        sinaShare:function(options){
            var shareUrl = [];
            shareUrl.push("http://v.t.sina.com.cn/share/share.php");
            shareUrl.push("?appkey=");
            shareUrl.push(sinaParam.appkey);
            shareUrl.push("&&source=");
            shareUrl.push(sinaParam.source);
            shareUrl.push("&content=");
            shareUrl.push(sinaParam.content);
            shareUrl.push("&url=");
            shareUrl.push(options.url);
            shareUrl.push("&title=");
            shareUrl.push(options.title);
            shareUrl.push("&pic=");
            shareUrl.push(options.pic);
            window.open(shareUrl.join(''));
        }
    };
    return share;
});
