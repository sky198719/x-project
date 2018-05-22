/**
 * Created by pufei on 2015/4/28.
 */
define(['js/card'],function(card){
    var tempProvinceCityArray = [];
    var updBankView = {
        init: function(param){
            appFunc.bindEvents(param.bindings);
        },
        
        getCitys: function(provinceValue){
        	var result = [];
        	var cityCodes = []; 
        	var cityNames = []; 
        	req.callJSON({
                url: 'personal/lowerArea.do',
                data:{
                	"province": provinceValue
                },
                async : false,
                timeout:10000,
                success: function (result) {
                	var list = result.cityList;
                    for (var k = 0; k < list.length; k++) {
                    	cityCodes.push(list[k].code);
                    	cityNames.push(list[k].name);
                    }
                    
                }
            });
        	result.push(cityCodes);
        	result.push(cityNames);
        	var provinceCity = new Object();
        	provinceCity.province = provinceValue;
        	provinceCity.cityArray = result;
        	tempProvinceCityArray.push(provinceCity);
        	return result;
        },
        
        showInfo : function(param){
            $$('#bank_userName').val(param.userName);
            $$('#bank_bankAccount').val('********'+ param.bankAccount.substring(param.bankAccount.length - 4, param.bankAccount.length));
            $$('#bank_Name').val(card.bankpvalue(param.bankCode,param.payBankDic));
            $$('#bank_branch').val(param.branch);
            $$('#bank_province').val(param.province);
            $$('#bank_city').val(param.city);
            
            //处理省市
            var provinceCodes = []; 
            var provinceNames = []; 
            var	provinceList = param.provinceList;
            var showBankprovinceAndCity = "";
            for (var i = 0; i < provinceList.length; i++) {
            	provinceCodes.push(provinceList[i].code);
            	provinceNames.push(provinceList[i].name);
            	if(param.province == provinceList[i].code) {
            		showBankprovinceAndCity = provinceList[i].name;
            	}
            }
            var province = "";
            var hasValue = false;
            var defaultCityArray = [];
            if (param.province!=null && param.city!=null) {
				
            	defaultCityArray = updBankView.getCitys(param.province);
            	for (var i = 0; i < defaultCityArray[0].length; i++) {
            		if(param.city == defaultCityArray[0][i]) {
            			showBankprovinceAndCity += " " + defaultCityArray[1][i];
            		}
            	}
            	hasValue = true;
            	
			}else{
				 defaultCityArray = updBankView.getCitys(provinceCodes[0]);
				 showBankprovinceAndCity = "";
			}
            
            $$("#bank_provinceAndCity").val(showBankprovinceAndCity);
            var isFist = true;
        	var pickerDependent = xxdApp.picker({
        	    input: '#bank_provinceAndCity',
        	    rotateEffect: true,
                toolbarTemplate:'<div class="toolbar">'+
                '<div class="toolbar-inner">'+
                '<div class="left"><a href="#" class="link toolbar-randomize-link">取消</a></div>'+
                '<div class="right">'+
                '<a href="#" class="link close-picker">确定</a>'+
                '</div>' +
                '</div> '+
                '</div> ',
        	    formatValue: function (picker, values,displayValues) {
        	    	$$('#bank_city').val(values[1]);
        	        return province + " " + displayValues[1];
        	    },
        	    cols: [
        	        {
        	            textAlign: 'left',
        	            values: provinceCodes,
        	            displayValues:provinceNames,
        	            onChange: function (picker, provinceCode ,provinceName) {
        	            	province = provinceName;
        	            	$$('#bank_province').val(provinceCode);
        	                if(picker.cols[1].replaceValues){
	        	                var flagContains = false;
	        	                for(var i=0;i < tempProvinceCityArray.length;i++){
	        	                	if (tempProvinceCityArray[i].province == provinceCode ) {
	        	                		picker.cols[1].replaceValues(tempProvinceCityArray[i].cityArray[0],tempProvinceCityArray[i].cityArray[1]);
	        	                		flagContains = true;
	        	                		break;
									}
	        	                }
	        	                if (!flagContains){
            	                	var cityArray = updBankView.getCitys(provinceCode);
            	                    picker.cols[1].replaceValues(cityArray[0],cityArray[1]);
	        	                }
        	                }
        	                
        	            }
        	        },
        	        {
        	            values: defaultCityArray[0],
        	            displayValues: defaultCityArray[1],
        	            width: 160
        	        }
        	    ],
        	    onOpen: function (picker) {  
        	    	if(hasValue){
        	    		if(isFist) {
        	    			picker.setValue([param.province, param.city])
        	    			isFist = false;
        	    		} else {
        	    			picker.setValue([$$('#bank_province').val(), $$('#bank_city').val()])
        	    		}
        	    	}
        	    	
                    picker.container.find('.toolbar-randomize-link').on('click', function () {
                        picker.close();
                    });
                }
        	});
        }
    };
    return updBankView;
});