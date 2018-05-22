require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float, track, store) {
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
    $.ajax({
        url:"/biz/bulletin/operationData",
        contentType: "application/json",
        dataType:"json",
        type:"get",
        beforeSend: function(request) {
            request.setRequestHeader("s", "www");
            request.setRequestHeader("clientId","001");
            request.setRequestHeader("clientTime","001");
        },
        success:function (msg){
            if (msg && msg.code == 200000){
                for(var i=0;i<msg.data.items.length;i++){
                    if(msg.data.items[i].code == 'TOTAL_REGISTER_USER'){
                        var value=(msg.data.items[i].nvalue/10000).toFixed(2);
                        $('.num-user').html(value);
                    }
                    if(msg.data.items[i].code == 'TOTAL_TRADE'){
                        var value=(msg.data.items[i].nvalue/100000000).toFixed(2);
                        $('.num-trade').html(value);
                    }
                    if(msg.data.items[i].code == 'TOTAL_INCOME'){
                        var value=(msg.data.items[i].nvalue/100000000).toFixed(2);
                        $('.num-income').html(value);
                    }
                }

            }
        },
        error:function (data){
            console.log(data);
        }
    })
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});