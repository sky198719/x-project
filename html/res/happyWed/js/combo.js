require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float, track, store) {
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);

    function xybID(obj){
        var month = $(obj).attr('value');
        $.ajax({
            url:'../../../m/xplan/getLatestSchemeId.do?closeTerm=' + month,
            type:'get',
            dataType:'json',
            success:function(data){
                $(obj).attr('href','../../../xplan/detail/' + data.schemeInfo.SCHEMEID + '.html');
            },
            error:function(){
                alert('网络异常，请重试！');
            }
        });
    }

    xybID('#pro3');
    xybID('#pro4');
    xybID('#pro5');
    xybID('#pro6');
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});