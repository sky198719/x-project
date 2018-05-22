/**
 * 修改支付密码
 * Created by pufei on 2015/2/10.
 */
define(['js/personal/changepwPayView'], function (changepwPayView) {
    var userName="";
    var from;
    var returnUrl;
    var changepwPayCtrl = {
        init: function(event){
            var page = appFunc.getEventDetailPageQuery(event);
            userName = page.userName;
            from = page.from;
            returnUrl = 'personal/personalInfo.html';
            if (from == 'security') {
                returnUrl = 'security/security.html';
            }
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#cw_pay_payPwd',
                    event: 'click',
                    handler: changepwPayCtrl.submitUpdPwd
                }
            ];
            changepwPayView.init({
                bindings: bindings
            });
        },
        submitUpdPwd:function(){
            //当前支付密码
            var oldPayPW = $.trim($$('#cw_pay_oldPayPW').val());
            if("" == oldPayPW){
                xxdApp.alert('当前支付密码不能为空','提示');
                return false;
            }
            //新密码
            var newPayPW = $.trim($$('#cw_pay_newPayPW').val());
            if("" == newPayPW){
                xxdApp.alert('新密码不能为空，请重新输入','提示');
                return false;
            }
            if(appFunc.validatePassword(newPayPW) !=  'true'){
                xxdApp.alert('有效密码为6-16位数字字母组合','提示');
                return false;
            }
            if(newPayPW == oldPayPW){
                xxdApp.alert('新支付密码不能与现有支付密码相同，请重新输入','提示');
                return false;
            }
            if(newPayPW.indexOf(userName)>=0){
                xxdApp.alert('新支付密码不能包含用户名，请重新输入','提示');
                return false;
            }
            //确认密码
            var affirmPayPW = $.trim($$('#cw_pay_affirmPayPW').val());
            if('' == affirmPayPW){
                xxdApp.alert('必须填写密码确认，请重新输入','提示');
                return false;
            }
            if(appFunc.validatePassword(affirmPayPW) !=  'true'){
                xxdApp.alert('有效密码为6-16位数字字母组合','提示');
                return false;
            }
            if (affirmPayPW != newPayPW ){
                xxdApp.alert('新支付密码与确认支付密码不一致，请重新输入','提示');
                return false;
            }

            req.callJSON({
                url:'approve/updatePW.do',
                data:{
                    oldPayPW: $.md5($.md5(oldPayPW)),
                    newPayPW:$.md5($.md5(newPayPW))
                },
                preloaderTitle:'正在努力修改，请稍后...',
                success:function(data){
                    $.each(data, function(k, v) {
                        if (k == 'resultCode') {
                            if (v == 0) {
                                xxdApp.alert('支付密码修改成功','成功',function(){
                                    GS.loadPage(returnUrl);
                                })
                            } else if (v == 100) {
                                xxdApp.alert('当前支付密码错误，请重新输入','提示');
                            } else if (v == 101) {
                                xxdApp.alert('必须填写当前支付密码，请重新输入','提示');
                            } else if (v == 200) {
                                xxdApp.alert('新支付密码与旧支付密码相同，请重新输入','提示');
                            } else if (v == 400) {
                                xxdApp.alert('新支付密码与登录密码相同，请重新输入','提示');
                            } else if (v == 500){
                                xxdApp.alert('请登录','提示',function(){
                                    xxdApp.loginScreen();
                                })
                            }
                        } else {
                            xxdApp.alert('支付密码修改失败','抱歉',function(){
                                GS.loadPage(returnUrl);
                            })
                        }
                    })
                }
            });
        }
    }
    return changepwPayCtrl
})
