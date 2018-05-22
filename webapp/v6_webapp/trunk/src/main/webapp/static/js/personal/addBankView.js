/**
 * Created by pufei on 2015/4/28.
 */
define(function(){
	var tempProvinceCityArray = [];
    var addBankView = {
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
        
        bankList:function(param){
            if(param != null){
                $$('#addBank_userName').val(param.userName);
            }
            
          //处理省市
            var provinceCodes = []; 
            var provinceNames = []; 
            var	provinceList = param.provinceList;
            for (var i = 0; i < provinceList.length; i++) {
            	provinceCodes.push(provinceList[i].code);
            	provinceNames.push(provinceList[i].name);
            }
            var province = provinceNames[0];
            
            var defaultCityArray = addBankView.getCitys(provinceCodes[0]);

        	var pickerDependent = xxdApp.picker({
        	    input: '#addBank_provinceAndCity',
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
        	    	$$('#addBank_city').val(values[1]);
        	        return province + " " + displayValues[1];
        	    },
        	    cols: [
        	        {
        	            textAlign: 'left',
        	            values: provinceCodes,
        	            displayValues:provinceNames,
        	            onChange: function (picker, provinceCode ,provinceName) {
        	            	if(picker.cols[1].replaceValues){
        	                	$$('#addBank_province').val(provinceCode);
        	                	province = provinceName;
	        	                var flagContains = false;
	        	                for(var i=0;i < tempProvinceCityArray.length;i++){
	        	                	if (tempProvinceCityArray[i].province == provinceCode ) {
	        	                		picker.cols[1].replaceValues(tempProvinceCityArray[i].cityArray[0],tempProvinceCityArray[i].cityArray[1]);
	        	                		flagContains = true;
	        	                		break;
									}
	        	                }
	        	                if (!flagContains){
            	                	var cityArray = addBankView.getCitys(provinceCode);
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
                    $$('#addBank_province').val(provinceCodes[0]);
                    $$('#addBank_city').val(defaultCityArray[0][0]);
                    picker.container.find('.toolbar-randomize-link').on('click', function () {
                        picker.close();
                    });
                }
        	});
            
        }
    };
    return addBankView;
});