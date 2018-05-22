/**
 * Created by gaoshanshan_syp on 2017/6/13.
 */
require(['base', "requirejs", 'json', "juicer", "trackBase","xxdBridge"], function ($, register, login, requirejs,track,xxdBridge) {
    $('#submit').on('click',function (ev) {
        var ev=ev || event;
        ev.preventDefault();

        $.ajax({
            url:'/tradeCenter/investBiz/showStatus/QTDS',
            dataType:'json',
            type:'get',
            beforeSend: function (request) {
                request.setRequestHeader("Accept", "application/json;charset=UTF-8");
                request.setRequestHeader("clientId", "XXD_INTEGRATION_PLATFORM");
                request.setRequestHeader("clientTime", new Date().getTime());
            },
            success:function (result) {
                if(result.data.status==true){
                    // location.href=' xxd://pagename=pddetail?pdname=qitiandasheng';
                    xxdBridge.open({
                        pagename: 'pddetail',
                        pdname: 'qitiandasheng',
                        productIndex:3,
                        productId:result.data.productId,
                        productType:result.data.productType
                    })
                }else{
                    location.href='http://m.xinxindai.com/html/products/over.html';
                }
            },
            error:function () {
              alert("访问出错啦，请检查网络");
            }
        })
    })
    //新手红包
    $('#getred').on('click',function (ev) {
        var ev=ev || event;
        ev.preventDefault();
        xxdBridge.open({
            pagename:'regist'
        })
    })


}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});