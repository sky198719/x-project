require(['base',"register",'login' , "requirejs",'json'], function ($  ,register,login, requirejs) {





}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
