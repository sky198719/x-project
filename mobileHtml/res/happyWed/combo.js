require(['base', "requirejs", 'json', "juicer", "trackBase"], function ($, register, login, requirejs,track) {
    function xybID(obj){
        var month = $(obj).attr('value');
        $.ajax({
            url:'../../../m/xplan/getLatestSchemeId.do?closeTerm=' + month,
            type:'get',
            dataType:'json',
            success:function(data){
                $(obj).parent('a').attr('href','../../../m/#!/static/html/plan/planDetailsV2.html?planId=' + data.schemeInfo.SCHEMEID);
            },
            error:function(){
                alert('网络异常，请重试！');
            }
        });
    }

    function yypID(obj){
        var month = $(obj).attr('value');
        $.ajax({
            url:'../../../m/yyp/index.do?terms=' + month,
            type:'get',
            dataType:'json',
            success:function(data){
                $(obj).parent('a').attr('href','../../../m/#!/static/html/yyp/yypDetails.html?yypId=' + data.resultList[0].ID);
            },
            error:function(){
                alert('网络异常，请重试！');
            }
        });
    }

    xybID('#pro3');
    xybID('#pro4');
    yypID('#pro5');
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
