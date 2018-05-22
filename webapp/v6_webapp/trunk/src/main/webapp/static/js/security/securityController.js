/**
 * 安全设置
 */
define(['js/security/securityView'], function (securityView) {
    var securityCtrl = {
        init: function () {

            req.callJSON({
                url:"personal/info.do",
                data:{},
                preloaderTitle:'正在加载数据...',
                timeout:8000,
                success:function(data){
                    var html = "";
                    // 支付密码
                    if (data.paypwd != 0) {
                        html = '<div class="item-title-row">' +
                        '<div class="item-title"><h5>支付密码</h5></div>' +
                        '<div class="item-after"><h6>修改密码</h6></div>' +
                        '</div>' +
                        '<div class="item-text mt10" style="height:auto;">已设置</div>';
                        $$("#securityPaypwdUrl").attr("href", GC.getHtmlPath() + "personal/changepwPay.html?path=personal&from=security&userName=" + data.user.userName);
                        $$("#securityPaypwd").html(html);
                    } else {
                        $$("#securityPaypwdUrl").attr("href", GC.getHtmlPath() + "personal/setPassword.html?path=personal&from=security");
                    }
                }
            });

            var bindings = [
                {
                    element: '#changepw-login',
                    event: 'click',
                    handler: securityCtrl.changepwLogin
                }
            ];
            securityView.init({bindings: bindings});
        },

        changepwLogin: function () {
            GS.loadPage('security/changepwLogin.html?path=security');
        }

    };

    return securityCtrl;
});