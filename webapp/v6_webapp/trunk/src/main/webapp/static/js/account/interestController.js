/**
 * Created by pufei on 2015/3/3.
 * 用户收益
 */
define(['js/account/interestView'], function (interestView) {
    var interestCtrl = {
        init: function () {
            req.callPost({
                url:"account/myincome.do",
                dataType:'json',
                preloaderTitle:'正在加载数据...',
                success:function (data) {
                    interestView.interestShow(data);
                }
            });
        }
    }
    return interestCtrl;
})