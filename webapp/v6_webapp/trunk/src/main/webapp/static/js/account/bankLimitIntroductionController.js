/**
 * 银行限额说明
 */
define([], function () {
	var bankTelephones = {};
    var bankLimitIntroductionCtrl = {
        init: function (event) {
        	var telephones = GS.getBankTelephone();
            var tempArr = [];
            for(var i in telephones) {
                tempArr.push('"'+telephones[i].bankName+'":"'+telephones[i].telephone+'"');
            }
            var tempStr = '{'+tempArr.join(",") + '}';
            bankTelephones = JSON.parse(tempStr);
            
			bankLimitIntroductionCtrl.getIntroduction();
        },

        getIntroduction: function () {
            req.callPost({
                url: 'mobilePay/getBankLimit.do',
                data: {type:"list"},
                dataType: 'json',
                indicator:true,
                success: function (result) {
                	if(result.resultCode == 0){
                		var resultData = result.data;
                		if(resultData.length != 0){
                			for (var i = 0; i < resultData.length; i++) {
		                        resultData[i].telephone = bankTelephones[resultData[i].bankName];
		                    }
                			
                			bankLimitIntroductionCtrl.showResult(resultData);
                		}else{
                			bankLimitIntroductionCtrl.noResult();
                		}
                	}else{
                		bankLimitIntroductionCtrl.noResult();
                		xxdApp.alert(result.resultDesc);
                	}
                }
            });
        },
        showResult:function (resultData){
        	req.callGet({
                url: GC.getHtmlPath() + 'account/bankLimitIntroductionItem.html?' + GC.getVersion(),
                dataType: 'text',
                indicator: true,
                success: function (result) {
                    var compiledTemplate = t7.compile(result);
                    var output = compiledTemplate({list: resultData});
                    $$("#bankLimitIntroduction #bankLimitIntroductionList").html(output);
		        },
		        error: function (result){
                	console.log("获取银行限额说明失败,ajax error...");
                	xxdApp.addNotification({
	                    title: '温馨提示',
	                    hold:3000,
	                    message: '系统异常，请刷新页面重试...'
	                });
	                bankLimitIntroductionCtrl.noResult();
                }
		    });
        },
        noResult:function (){
        	$$("#bankLimitIntroduction #bankLimitIntroductionList").html('<div style="text-align: center;color:#999999;">暂无内容...</div>');
        }
    };
    return bankLimitIntroductionCtrl;
});
