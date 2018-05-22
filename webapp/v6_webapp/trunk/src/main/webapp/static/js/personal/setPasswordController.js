/**
 * 设置支付密码
 * Created by pufei on 2015/2/10.
 */
define(['js/personal/setPasswordView'], function (setPasswordView) {
    var pwdReturnUrl;
    var formToken = {tokenName:'',token:''};
    var setPasswordCtrl = {
        init: function(event){
            var page = appFunc.getEventDetailPageQuery(event);
            var from = page.from;
            pwdReturnUrl = 'personal/personalInfo.html';
            if (from == 'security') {
                pwdReturnUrl = 'security/security.html';
            }
            if (from == 'exchangeXXCoin') {
                pwdReturnUrl = 'xxb/exchange.html';
            }
            
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#sp_payPwd',
                    event: 'click',
                    handler: setPasswordCtrl.submitSetPwd
                }
            ];
            setPasswordView.init({
                    bindings: bindings
            });
            //设置表单token
            formToken = appFunc.setToken({name:"SET_PASSWORD", id: ""});
            if(formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                return;
            }
        },
        submitSetPwd:function(){
            var pwd = $$('#sp_payPassword').val();
            var pwdSec = $$('#sp_payPasswordSec').val();
            if ("" == pwd) {
                xxdApp.alert('请输入支付密码', '提示');
                return;
            } else if (appFunc.validatePassword(pwd) != 'true') {
                xxdApp.alert('有效密码为6-16位数字字母组合', '抱歉');
                return;
            }
            if ("" == pwdSec) {
                xxdApp.alert('请输入确认支付密码', '提示');
                return;
            } else if (appFunc.validatePassword(pwdSec) != 'true') {
                xxdApp.alert('有效密码为6-16位数字字母组合', '抱歉');
                return;
            }
            if (pwd != '' && pwdSec != '') {
                if (pwd != pwdSec) {
                    xxdApp.alert('密码与确认密码不一致，请重新输入', '抱歉');
                    return;
                }
            }
            xxdApp.showIndicator('正在努力设置，请稍后...');
            req.callJSON({
                url:"approve/payPwd.do",
                data:{
                    payPassword: $.md5($.md5(pwd)),
                    tokenName: formToken.data.tokenName,
                    token: formToken.data.token
                },
                success:function(data){
                    $.each(data, function (k, v) {
                        xxdApp.hideIndicator();
                        if (k == "resultCode") {
                            if (v == 0) {
                                xxdApp.alert('设置成功', '成功',function(){
                                    GS.loadPage(pwdReturnUrl);
                                })
                            } else if (v == 400) {
                                xxdApp.alert('设置失败！支付密码不能和登录密码设置相同', '抱歉',function(){
                                    GS.loadPage(pwdReturnUrl);
                                })
                            } else {
                                xxdApp.alert('设置失败！', '抱歉',function(){
                                    GS.loadPage(pwdReturnUrl);
                                })
                            }
                        }
                    })
                }
            });
        }
    };
    return setPasswordCtrl;
});