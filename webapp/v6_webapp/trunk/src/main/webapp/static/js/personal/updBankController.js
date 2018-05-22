/**
 * Created by pufei on 2015/4/28.
 */
define(['js/personal/updBankView'], function (updBankView) {
    var bankId = '';
    
    var updBank = {
        init: function (event) {
            var page = appFunc.getEventDetailPageQuery(event);
            bankId = page.bankId
            updBank.loadBackInfo(bankId);
            updBank.bindEvent();
        },
        bindEvent:function(){
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#bank_close',
                    event: 'click',
                    handler: updBank.closeBankUpd
                },
                {
                    element: '#bank_submit',
                    event: 'click',
                    handler: updBank.submitUpd
                }
            ]
            updBankView.init({
                bindings: bindings
            });
        },
        loadBackInfo:function(bankId){
            req.callJSON({
                url: 'personal/bankinfo.do',
                data:{
                    "bankId" : bankId
                },
                indicator:true,
                timeout:10000,
                success: function (result) {
                    updBankView.showInfo(result);
                }
            });
        },
        submitUpd:function(){
            var branch = $$('#bank_branch').val();
            branch = $.trim(branch);
            if("" == branch || "开户支行" == branch ){
                xxdApp.alert('必须填写开户支行，请重新输入！','提示',function(){
                    $$('#bank_branch').focus();
                })
                return
            }
            var province = $$('#bank_province').val();
            var city = $$('#bank_city').val();
            if ("" == province || "" == city) {
            	 xxdApp.alert('请选择开户支行所在省市！','提示',function(){
                 })
                 return
			}
            req.callPost({
                url: 'personal/bankEdit.do',
                data:{
                    "bankId" : bankId,
                    "branch" : branch,
                    "province" : province,
                    "city" : city
                },
                dataType: 'json',
                preloaderTitle:'正在努力修改，请稍等',
                timeout:10000,
                success: function (result) {
                    if(result.resultCode == 0){
                        xxdApp.alert('银行卡修改成功！','提示',function(){
                            GS.loadPage("personal/personalBank.html?path=personal");
                        });
                    }else{
                        xxdApp.alert(result.msg,'抱歉');
                    }
                }
            });
        }
    }
    return updBank;
})