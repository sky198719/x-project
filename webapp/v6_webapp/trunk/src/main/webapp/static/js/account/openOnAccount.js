define([],function(){
    var open = {
        isOpenOnAccount:function(param){
            req.callGet({
                url: 'isOpenOnAccount.do',
                async:false,
                dataType: 'json',
                success: function (data) {
                    if (data.resultCode == 0) {
                        xxdApp.modal({
                            title: '提示',
                            afterText: param.title,
                            buttons: [
                                {
                                    text: '取消',
                                    onClick: function() {
                                    }
                                },
                                {
                                    text: '立即开户',
                                    onClick: function() {
                                        GS.loadPage('personal/personalInfo.html');
                                    }
                                }
                            ]
                        });
                    } else {
                        param.callBack();
                    }
                }
            });
        },
        indexIsOpenOnAccount:function(){
            if(window.location.href.indexOf("isShowOpenAccount") > -1) {
                return false;
            }
            if(!appFunc.isLogin()) {
                return;
            }
            var user = appFunc.getCurrentUser();

            var userId = 0;
            if(user != undefined ) {
                userId = user.userId;
            }
            var flag;
            var key = userId + '_' + appFunc.sameWeek(new Date()) ;
            if(window.localStorage){
                flag = window.localStorage.getItem(key);
            }

            if(flag == 1) {
                return;
            }
            if(flag == undefined) {
                if(window.localStorage) {
                    window.localStorage.setItem(key, 0);
                }
            }

            req.callGet({
                url:'isOpenOnAccount.do',
                dataType:'json',
                success:function(data){
                    if(data.resultCode == 0) {
                        xxdApp.modal({
                            title: '提示',
                            afterText: '为提升您的资金安全，建议您立即开通银行存管账户',
                            buttons: [
                                {
                                    text: '取消',
                                    onClick: function() {
                                        if(window.localStorage){
                                            window.localStorage.setItem(key,1);
                                        }
                                    }
                                },
                                {
                                    text: '立即开户',
                                    onClick: function() {
                                        if(window.localStorage){
                                            window.localStorage.setItem(key,1);
                                        }
                                        if(window.location.href.indexOf('personal/personalInfo.html') == -1 ) {
                                            GS.loadPage('personal/personalInfo.html');
                                        }
                                    }
                                }
                            ]
                        });
                    }
                }
            });
        }
    };
    return open;
});