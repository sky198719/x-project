/**
 * Created by pufei on 2015/3/27.
 */
define(['js/card'], function (card) {
    var drawmoneyView = {
        init: function (params) {
        },
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        showListcard:function(param){
            var html='';
            if(param.cardList != undefined && param.cardList.length > 0) {
                for (var i = 0; i < param.cardList.length; i++) {
                    var c = param.cardList[i];
                    html += '<option value="'+ c.id +'"  '
                    if(c.banded == 1 || param.cardList.length == 1){
                        html +='selected="selected"'
                        $$("#defaultBank").html(card.bankpvalue(c.bankCode, param.payBankDic) +' 尾号： '+c.bankAccount.substring(c.bankAccount.length - 4, c.bankAccount.length));
                    }
                    html +='>'+ card.bankpvalue(c.bankCode, param.payBankDic) +' 尾号： '+ c.bankAccount.substring(c.bankAccount.length - 4, c.bankAccount.length) +'</option>';
                }

                $$('#fruits').html(html);
                if(param.appro.realName == '1'){
                    if(param.rnAppro != null ){
                        $$('#realName').html('持卡人：'+  param.rnAppro.realName);
                    }
                }
            }
        }

    };
    return drawmoneyView;
});
