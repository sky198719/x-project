require(['base', "requirejs", 'xxdBridge', 'json', "juicer", "track"], function ($, requirejs, xxdBridge) {

    $(function () {
        var pathName = window.location.pathname
        if(pathName.indexOf('accountError.html') != -1){
            // alert('111')
            // 错误页面
        }else if(pathName.indexOf('accountSuccess.html') != -1){
            // alert('222')
            // 成功页面
        }else{
            // alert('333')
            // 首页
        }
        // getUserInfo();
        if(getQuery('mxcode')){
            if(getQuery('mxcode') == 1 || getQuery('mxcode') == 2){
                $('.dis-icon1').removeClass('icon-error').addClass('icon-success')
                $('.mt1e').html('认证成功')
            }
        }

    });


    function getQuery(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r != null) return decodeURI(r[2]); return null; 
    }


    var baseUrl = 'http://stage.xxd.com/'

    var token = 'hqB+6NnSabFXgMFRTQ2Qd5pHxgXf4XEerWJ1D8Hq1bSCcO6GfI1GLG0nBQJ1X0o7d8aMg6U8DdRzJZek0c5icy0XdlZ5kACarT3bwXDb1Ug=';
    // var token = getCookie('userToken');
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg)) {
            return (arr[2])
        } else {
            return null;
        }
    }

    $(document).on('click', '#to_account', function () {
        toAccount();
    });

    $(document).on('click', '.back-history', function() {
        window.history.back()
    })

    $(document).on('click', '.prorocol-url', function() {
        window.location.href = 'protocal.html'
    })

    $(document).on('click', '.btn-fail', function() {
        xxdBridge.open({
            pagename: 'poppage',
            result: 0
        })
    })

    $(document).on('click', '.btn-success', function() {
        xxdBridge.open({
            pagename: 'poppage',
            result: 1
        })
    })

    $(document).on('click', '.btn-webfail', function() {
        //window.location.href = window.location.origin + '/webh5/usercenter/openAccount'
        window.location.href = 'http://m.xinxindai.com/webh5/usercenter/openAccount'
    })

    $(document).on('click', '.btn-websuccess', function() {
        window.location.href = window.location.origin + '/m/#!/static/html/personal/personalInfo.html'
        // window.location.href = 'http://stage.xxd.com/m/#!/static/html/personal/personalInfo.html'
    })

    function getUserInfo() {
        $.ajax({
            url     : baseUrl + '/dev/userCenter/user/capitalAccount/userInfo4openAccount',
            dataType: 'json',
            type    : 'GET',
            headers : {
                "Accept"    : "application/json;charset=UTF-8",
                "clientId"  : "XXD_INTEGRATION_PLATFORM",
                "clientTime": new Date().getTime(),
                "s"         : "de42212bdc77b66092a9211cc08b2313",
                "token"     : token
            },
            data    : {
                userId: 155
            },
            success : function (result) {
                console.log(result);
                if (result.code == 200000) {
                    $('#dt_bank_name').text(result.data.data.bankAccount);
                    $('#dt_user_name').text(result.data.data.realName);
                    $('#dt_user_id_card').text(result.data.data.idCardNo);
                    $('#dt_user_mobile').text(result.data.data.mobile);
                }

            },
            error   : function () {
                // console.log('ajax error');
                xui.Prompt({
                    icon: 'error',
                    msg : '用户信息查询失败，请检查网络!'
                })
            }
        });
    }

    function toAccount() {

        console.log($("#checkout1").prop('checked'))
        debugger
        if(!$("#checkout1").prop('checked')){
            xui.Prompt({
                icon: 'error',
                msg : '请勾选协议!'
            })

            return 
        }

        

            $.ajax({
                url     : baseUrl + '/dev/userCenter/user/capitalAccount/openFuiouOpenAccountPage',
                dataType: 'json',
                type    : 'POST',
                headers : {
                    "Accept"    : "application/json;charset=UTF-8",
                    "clientId"  : "XXD_INTEGRATION_PLATFORM",
                    "clientTime": new Date().getTime(),
                    "s"         : "de42212bdc77b66092a9211cc08b2313",
                    "token"     : "hqB+6NnSabFXgMFRTQ2Qd77LbpgLZbFVdsS4weCBLOOmUTaABLb+PgGlUG8FLMJrAZcicykeRbiYJYHiJtJKcll3hC9eDlhY8Ae0nGwvO6g="
                },
                success : function (result) {
                    $("#to_submit").submit()
                    console.log(result);
                    if (result.code == 20000) {

                    }

                },
                error   : function () {
                    // console.log('ajax error');
                    xui.Prompt({
                        icon: 'error',
                        msg : '用户信息提交失败，请检查网络!'
                    })
                }
            });
        

    }


    var xui = {
        Prompt: function (conf) {
            var id   = conf.id,
                dim  = conf.dim || false,
                pos  = conf.pos || 'cc',
                time = conf.time || 2000,
                icon = conf.icon,
                msg  = conf.msg || '',
                mod  = '.xui-prompt';

            if ($(mod + '[dim="true"]').length > 0) {
                dim = false;
            }
            if (icon) {
                icon = '<p class="prompt-icon" icon="' + icon + '"><b></b></p>';
            } else {
                icon = '';
            }
            if (!id) {
                id = '#prompt' + ( $(mod).length * 1 + 1);
            }
            if (msg) {
                $('.xui-prompt').remove();
                var code = '<div style="display:none" id="' + id + '" class="xui-prompt" dim="' + dim + '" pos="' + pos + '"><div class="xui-prompt-dim"><div class="xui-prompt-content">' + icon + '<p>' + msg + '</p></div></div></div>';
                var $id  = $(code);

                if ($(mod).length === 0) {
                    $('body').addClass('prompt-o-hide').append($id);
                }

                setTimeout(function () {
                    $id.stop().fadeIn(200);
                }, 100);

                setTimeout(function () {
                    $id.stop().fadeOut(function () {
                        $('.xui-prompt').remove();
                        $('body').removeClass('prompt-o-hide');
                    });

                }, parseInt(time));

                return $id;
            }

        }
    };

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});