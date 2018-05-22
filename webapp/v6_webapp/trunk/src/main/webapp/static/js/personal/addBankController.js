/**
 * Created by pufei on 2015/4/28.
 */
define(['js/personal/addBankView','js/personal/bankUtil'], function (addBankView,bankUtil) {
    var formToken = {tokenName:'',token:''};
    var addBankCtrl = {
        init: function () {
            addBankCtrl.loadBankList();
            addBankCtrl.bindEvent();
            addBankCtrl.setBankToken();

           /* xxdApp.modal({
              title:  '友情提示',
              text: '新新理财移动端遵循同卡进出原则，全程保障您的资金安全',
              buttons: [
                {
                  text: '我知道了',
                  onClick: function() {
                  }
                }
              ]
            });  */
        },
        bindEvent:function(){
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#addBank_submit',
                    event: 'click',
                    handler: addBankCtrl.submit
                },
                {
                    element: '#addBank_submit_def',
                    event: 'click',
                    handler: addBankCtrl.defSubmit
                },
                {
                       element: 'span[name="iknow"]',
                       event: 'click',
                       handler: addBankCtrl.iknow
                   }

            ];
            addBankView.init({
                bindings: bindings
            });
        },
        iknow:function() {
            $("div[name='iknowPar']").hide();
            $("div[name='iknowPar2']").hide();
        },
        loadBankList:function(){
            req.callJSON({
                url: 'personal/bankList.do',
                data:{},
                indicator:true,
                timeout:10000,
                success: function (result) {
                    if(result.userName == null){
                        xxdApp.alert('您未申请实名认证，现在就去', '提示', function () {
                            GS.loadPage('personal/personalBank.html?path=personal');
                        });
                    }
                    var pvalues = result.payBankDic;
                    addBankView.bankList({userName:result.userName,provinceList:result.provinceList});
                    for (var k = 0; k < pvalues.length; k++) {
                        pvalues [k] = pvalues[k].pvalue;
                    }
                    var pickerDevice = xxdApp.picker({
                        input: '#picker-device',
                        rotateEffect:true,
                        toolbarTemplate:'<div class="toolbar">'+
                            '<div class="toolbar-inner">'+
                            '<div class="left"><a href="#" class="link toolbar-randomize-link">取消</a></div>'+
                            '<div class="right">'+
                            '<a href="#" class="link close-picker">确定</a>'+
                            '</div>' +
                            '</div> '+
                            '</div> ',
                        cols: [
                            {
                                textAlign: 'center',
                                values: pvalues
                            }
                        ],
                        onOpen: function (picker) {
                            picker.container.find('.toolbar-randomize-link').on('click', function () {
                                picker.close();
                            });
                        }
                    });
                }
            });
        },
        setBankToken:function(){
            //设置表单token
            formToken = appFunc.setToken({name:"BANK_ADD", id: ''});
            if(formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                return;
            }
        },
        submit:function(){
            addBankCtrl.isNandef('false');
        },
        defSubmit:function(){
            addBankCtrl.isNandef('true');
        },
        isNandef:function(isBind){
            //银行卡号
            var bankAccount = $$('#addBank_bankAccount').val();
            if("" == bankAccount || "银行卡号" == bankAccount){
                xxdApp.alert('必须填写银行卡号，请重新输入','提示',function(){
                    $$('#addBank_bankAccount').focus();
                });
                return;
            }
            if(bankAccount.length < 10){
                xxdApp.alert('请输入正确的银行卡号，请重新输入','提示',function(){
                    $$('#addBank_bankAccount').focus();
                });
                return;
            }
            //开户银行
            var bankName = $$('#picker-device').val();
            if("" == bankName || null == bankName){
                xxdApp.alert('必须填写开户银行，请重新输入','提示',function(){
                    $$('#addBank_Select').focus();
                });
                return;
            }
            //开户支行
            var bankBranch = $$('#addBank_branch').val();
            if("" == bankBranch || "开户支行" == bankBranch){
                xxdApp.alert('必须填写开户支行，请重新输入','提示',function(){
                    $$('#addBank_branch').focus();
                });
                return;
            }
          //开户支行所在省份
            var province = $$('#addBank_province').val();
            var city = $$('#addBank_city').val();
            if ("" == province || "" == city) {
            	 xxdApp.alert('请选择开户支行所在省市！','提示',function(){
                 })
                 return;
			}
            
            //支付密码
            var payPW = $.trim($$('#addBank_password').val());
            if("" == payPW){
                xxdApp.alert('必须输入支付密码，请重新输入','提示',function(){
                    $$('#addBank_password').focus();
                });
                return;
            }

            var result = bankUtil.bankCardCheck({cardNo:bankAccount});
            if (result.code == 404) {
                xxdApp.loginScreen();
                return;
            }

            if (result.code != 0) {
                xxdApp.alert(result.info, "抱歉");
                return;
            }

            //根据开户银行名称获取银行code
            var bankCode = '';
            req.callJSON({
                url: 'personal/bankList.do',
                indicator:true,
                timeout:10000,
                async: false,
                success: function (result) {
                    var payBankDic = result.payBankDic;
                    for(var i = 0;i < payBankDic.length;i++)
                    {
                        var p = payBankDic[i];
                        if(p.pvalue == bankName){
                            bankCode = p.pkey;
                            return
                        }
                    }
                }
            });
            if(result.data.bank_code != bankCode) {
                xxdApp.alert("您选择的开户银行与银行卡号归属银行不相符，请选择正确的开户银行","抱歉");
                return;
            }
            xxdApp.showIndicator('正在努力添加，请稍等...');
            req.callPost({
                url: 'personal/bankAdd.do',
                data:{
                    "payPW" : $.md5($.md5(payPW)),
                    "bankInfo":JSON.stringify({
                        bankAccount:bankAccount,
                        bankCode:bankCode,
                        branch:bankBranch,
                        province:province,
                        city:city
                    }),
                    "isBind" : isBind,
                    "tokenName": formToken.data.tokenName,
                    "token": formToken.data.token
                },
                dataType:'json',
                timeout:10000,
                success: function (result) {
                    xxdApp.hideIndicator();
                    if (result.resultCode == 0) {
                        xxdApp.alert('添加成功！','恭喜',function(){
                            GS.loadPage("personal/personalBank.html?path=personal");
                        });
                    } else if (result.resultCode == 2) {
                        xxdApp.alert('您设置过银行卡号为：' + bankAccount + '的银行卡，请重新输入','抱歉',function(){
                            $$('#addBank_bankAccount').focus();
                            addBankCtrl.setBankToken();
                        });
                    } else if (result.resultCode == 3) {
                        xxdApp.alert('支付密码错误，请重新输入','抱歉',function(){
                            $$('#addBank_password').focus();
                            addBankCtrl.setBankToken();
                        });
                    } else if (result.resultCode == 6) {
                        xxdApp.alert('支付密码与登录密码一致，为了资金安全，请进行修改。','抱歉',function(){
                            $$('#addBank_password').focus();
                            addBankCtrl.setBankToken();
                        });
                    } else if (result.resultCode == 5) {
                        $('#atitle_mini').html("抱歉");
                        xxdApp.alert('您还未设置支付密码，请前往个人资料设置','抱歉',function(){
                            GS.loadPage("personal/personalInfo.html?path=personal");
                        });
                    } else {
                        var msg = result.desc;
                        if(result.resultCode == -3) {
                            msg = "该银行卡已被其他账号绑定，请修改，如有疑问，可咨询在线客服！";
                        }
                        xxdApp.alert(msg,'抱歉',function(){
                            GS.loadPage("personal/personalBank.html?path=personal");
                        });
                    }
                }
            });
        }
    };
    return addBankCtrl
});