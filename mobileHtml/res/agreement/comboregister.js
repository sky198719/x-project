require(['base',"register",'login' , "requirejs",'json'], function ($  ,register,login, requirejs) {

    var _token = "";
    function callToken (callback){
        if (_token) {
            if (callback) callback (_token);
        } else {
            $.ajax({
                url:"/feapi/users/formToken",
                contentType: "application/json",
                dataType:"json",
                type:"get",
                success:function (res){
                    _token = res.token;
                    if (callback) callback(_token);
                },
                error:function (data){
                    $.log (data);
                }
            });
        }
    }
    $("#J_login").on ("click" , function (ev){
        callToken(function (token){
            login.show({
                token:token,
                register:register,
                callback:function (res){
                    alert("login ok");
                }
            });
        });
        ev && event.preventDefault();
    });

    $("#J_register").on ("click" , function (ev){
        callToken(function (token){
            register.show({
                token:token,
                login:login,
                callback:function (res) {
                    alert("register ok");
                }
            });
        });
        ev && event.preventDefault();
    });
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
