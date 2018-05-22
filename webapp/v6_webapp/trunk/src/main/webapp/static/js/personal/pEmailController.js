/**
 * 邮箱绑定
 * Created by pufei on 2015/2/11.
 */
define(['js/personal/pEmailView'], function (pEmailView) {
    var formToken = {tokenName:'',token:''};
    var pEmailCtrl = {
        init: function (event) {
            var page = appFunc.getEventDetailPageQuery(event);
            if (page.bindEmail != null && page.bindEmail != '') {
                var bindEmail = page.bindEmail;
                $$('#email-address').val(bindEmail);
            }
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#check_email',
                    event: 'click',
                    handler: pEmailCtrl.check_pEmail
                }
            ];
            pEmailView.init({
                bindings: bindings
            });
            //设置表单token
            formToken = appFunc.setToken({name:"EMAIL_ACTIVATE", id: ""});
            if(formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                return;
            }
        },
        check_pEmail: function () {
            //邮箱地址
            var email = $$('#email-address').val()
            if ("" == email) {
                xxdApp.alert("必须输入邮箱地址，请重新输入", "抱歉");
                return;
            }

            var patter = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if (!patter.test(email)) {
                xxdApp.alert("邮箱地址错误，请重新输入", "抱歉");
                return;
            }
            $$('#check_email').attr("disabled", "disabled");

            req.callJSON({
                url: "approve/emailActivate.do",
                data: {
                    email: email,
                    approEmailUrl:'/#!/'+GC.getHtmlPath() +'/personal/emailApproReturn.html',
                    tokenName: formToken.data.tokenName,
                    token: formToken.data.token
                },
                preloaderTitle: '正在发送邮件，请稍后...',
                success: function (data) {
                    if (data.resultCode == 1) {
                        xxdApp.alert("邮件发送成功，请前往邮箱按引导进行操作", "成功", function () {
                            GS.loadPage('personal/personalInfo.html');
                        })
                    } else {
                        $$('#check_email').removeAttr("disabled")
                        xxdApp.alert(data.msg,'抱歉',function(){
                            //设置表单token
                            formToken = appFunc.setToken({name:"EMAIL_ACTIVATE", id: ""});
                            if(formToken.code != 0) {
                                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                                return;
                            }
                        });
                        return;
                    }
                }
            });
        }
    }
    return pEmailCtrl
})