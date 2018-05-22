define(function () {
    var tempProvinceCityArray = [];
    var channel = '';
    var xinyedaiCtrl = {
        init: function (event) {
            channel = appFunc.getEventDetailPageQuery(event).channel;

            xinyedaiCtrl.initApply();
            var bindings = [
                {
                    element: '#xinyedai_submit',
                    event: 'click',
                    handler: xinyedaiCtrl.applyLoan_submit
                },
                {
                    element: '#xinyedai_randCode',
                    event: 'click',
                    handler: xinyedaiCtrl.randCode
                },

                {
                    element: '#xinyedai_top',
                    event: 'click',
                    handler: xinyedaiCtrl.xinyedai_top
                },
                {
                	element: '#salary',
                	event: 'click',
                	handler: xinyedaiCtrl.salary
                },
                {
                	element: '#legal_person',
                	event: 'click',
                	handler: xinyedaiCtrl.legal_person
                },
                {
                	element: '#xinyedai_username',
                	event: 'blur',
                	handler: xinyedaiCtrl.xinyedai_username
                },
                {
                	element: '#xinyedai_mobileNo',
                	event: 'blur',
                	handler: xinyedaiCtrl.xinyedai_mobileNo
                },
                {
                	element: '#isRoom_0',
                	event: 'click',
                	handler: xinyedaiCtrl.isRoom_0
                },
                {
                	element: '#isRoom_1',
                	event: 'click',
                	handler: xinyedaiCtrl.isRoom_1
                },
                {
                	element: '#isCar_0',
                	event: 'click',
                	handler: xinyedaiCtrl.isCar_0
                },
                {
                	element: '#isCar_1',
                	event: 'click',
                	handler: xinyedaiCtrl.isCar_1
                },
                {
                	element: '#xinyedai_randCode_value',
                	event: 'blur',
                	handler: xinyedaiCtrl.xinyedai_randCode_value
                },
            ];
            appFunc.bindEvents(bindings);
        },

        xinyedai_top:function(){
            window.location.href = "https://www.xinxindai.com/index.html";
        },
        salary:function(){
        	try{
        		//XXD_TRACK._trackEvent({category:"webapp_xinyedai",action:"xinyedai_identity",label:"身份",value:$$("#salary").val(),custval:""});
        	}catch(e){}
        },
        legal_person:function(){
        	try{
        		//XXD_TRACK._trackEvent({category:"webapp_xinyedai",action:"xinyedai_identity",label:"身份",value:$$("#legal_person").val(),custval:""});
        	}catch(e){}
        },
        xinyedai_username:function(){
        	try{
        		//XXD_TRACK._trackEvent({category:"webapp_xinyedai",action:"xinyedai_username",label:"真实姓名",value:$$("#xinyedai_username").val(),custval:""});
        	}catch(e){}
        },
        xinyedai_mobileNo:function(){
        	try{
        		//XXD_TRACK._trackEvent({category:"webapp_xinyedai",action:"xinyedai_mobileNo",label:"手机号码",value:$$("#xinyedai_mobileNo").val(),custval:""});
        	}catch(e){}
        },
        isRoom_0:function(){
        	try{
        		//XXD_TRACK._trackEvent({category:"webapp_xinyedai",action:"xxinyedai_isRoom",label:"是否有房",value:$$("#isRoom_0").val(),custval:""});
        	}catch(e){}
        },
        isRoom_1:function(){
        	try{
        		//XXD_TRACK._trackEvent({category:"webapp_xinyedai",action:"xxinyedai_isRoom",label:"是否有房",value:$$("#isRoom_1").val(),custval:""});
        	}catch(e){}
        },
        isCar_0:function(){
        	try{
        		//XXD_TRACK._trackEvent({category:"webapp_xinyedai",action:"xinyedai_car",label:"是否有车",value:$$("#isCar_0").val(),custval:""});
        	}catch(e){}
        },
        isCar_1:function(){
        	try{
        		//XXD_TRACK._trackEvent({category:"webapp_xinyedai",action:"xinyedai_car",label:"是否有车",value:$$("#isCar_1").val(),custval:""});
        	}catch(e){}
        },
        xinyedai_randCode_value:function(){
        	try{
        		//XXD_TRACK._trackEvent({category:"webapp_xinyedai",action:"xinyedai_verifycode",label:"验证码",value:"",custval:""});
        	}catch(e){}
        },

        applyLoan_submit: function () {
            var identity  = $("input[name='identity']:checked").val();
            if(identity == undefined) {
                $$("#xinyedai_identity_error .dsd-error").html("请选择身份");
                $$("#xinyedai_identity_error").show();
                return;
            }
            $$("#xinyedai_identity_error").hide();

            var xinyedai_username = $$("#xinyedai_username").val();
            if (xinyedai_username == '') {
                $$("#xinyedai_username_error .dsd-error").html("请输入真实姓名");
                $$("#xinyedai_username_error").show();
                $$("#xinyedai_username").focus();
                return;
            } else if (xinyedai_username.length < 2 || xinyedai_username.length > 8) {
                $$("#xinyedai_username_error .dsd-error").html("真实姓名长度为2到8之间");
                $$("#xinyedai_username_error").show();
                $$("#xinyedai_username").focus();
                return;
            }
            $$("#xinyedai_username_error").hide();

            var xinyedai_mobileNo = $$("#xinyedai_mobileNo").val();
            if (xinyedai_mobileNo == '') {
                $$("#xinyedai_mobileNo_error .dsd-error").html("请输入手机号码");
                $$("#xinyedai_mobileNo_error").show();
                $$("#xinyedai_mobileNo").focus();
                return;
            }

            var vaRe = appFunc.validateMobile(xinyedai_mobileNo);
            if (vaRe != "true") {
                $$("#xinyedai_mobileNo_error .dsd-error").html("您输入的手机号码有误，请重新输入");
                $$("#xinyedai_mobileNo_error").show();
                $$("#xinyedai_mobileNo").focus();
                return;
            }
            $$("#xinyedai_mobileNo_error").hide();

            var addBank_province = $$("#addBank_province").val();
            if (addBank_province == '') {
                $$("#applyLoan_province_error .dsd-error").html("请选择您的居住城市");
                $$("#applyLoan_province_error").show();
                $$("#addBank_province").focus();
                return;
            }
            $$("#applyLoan_province_error").hide();

            var addBank_city = $$("#addBank_city").val();

            var room = $("input[name='isRoom']:checked").val();
            if(room == undefined) {
                $$("#picker_isRoom_error .dsd-error").html("请选择是否有房");
                $$("#picker_isRoom_error").show();
                return;
            }
            $$("#picker_isRoom_error").hide();


            var car = $("input[name='isCar']:checked").val();
            if(car == undefined) {
                $$("#xinyedai_isCar_error .dsd-error").html("请选择是否有车");
                $$("#xinyedai_isCar_error").show();
                return;
            }
            $$("#xinyedai_isCar_error").hide();

            var xinyedai_randCode = $$("#xinyedai_randCode_value").val();
            if (xinyedai_randCode == '') {
                $$("#xinyedai_randCode_error .dsd-error").html("请输入验证码");
                $$("#xinyedai_randCode_error").show();
                $$("#xinyedai_randCode_value").focus();
                return;
            }
            $$("#xinyedai_randCode_error").hide();
            //try{XXD_TRACK._trackEvent({category:"webapp_xinyedai",action:"xinyedai_btn",label:"快速申请",value:"",custval:""});}catch(e){}

            req.callPost({
                url:'borrow/saveBorrowApply.do',
                data:{
                    borrowType:'19',
                    province: addBank_province,
                    city: addBank_city,
                    realName:xinyedai_username,
                    mobileNo:xinyedai_mobileNo,
                    randCode:xinyedai_randCode,
                    identity:identity,
                    ownhouse:room,
                    owncar:car,
                    channel:channel
                },
                dataType:'json',
                preloaderTitle:'请稍等，正在申请...',
                success:function(result){
                     if(result.resultCode == 0) {
                    	 //try{XXD_TRACK._trackEvent({category:"webapp_xinyedai",action:"xinyedai_success",label:"申请成功",value:"",custval:"1"});}catch(e){}
                         var modal = xxdApp.modal({
                             title: '申请成功',
                             text: '<p style="text-align: center;font-size: 12px;">您的借款申请已提交<br>请保持电话畅通，我们将很快与您联系！</p>',
                             afterText:  '<p style="text-align: center;font-size: 12px;">如有问题欢迎致电人工客服<br>4000-169-521<br>（周一至周五9:00-18:00）</p>',
                             buttons: [
                               {
                                 text: '关闭',
                                 bold: true,
                                 onClick:function(){
                                     window.location.reload();
                                 }
                               }
                             ]
                           })
                     } else {
                         xxdApp.alert(result.msg);
                     }
                }
            });
        },

        randCode: function () {
            document.getElementById("xinyedai_randCode").src = "randCode/createVerifyCode.do?" + Math.random();
        },

        initApply: function () {

            req.callJSON({
                url: 'personal/getProvinceList.do',
                data: {},
                indicator: true,
                success: function (result) {
                    //处理省市
                    var provinceCodes = [];
                    var provinceNames = [];
                    var provinceList = result.provinceList;
                    for (var i = 0; i < provinceList.length; i++) {
                        provinceCodes.push(provinceList[i].code);
                        provinceNames.push(provinceList[i].name);
                    }
                    var province = provinceNames[0];

                    var defaultCityArray = xinyedaiCtrl.getCitys(provinceCodes[0]);

                    var pickerDependent = xxdApp.picker({
                        input: '#addBank_provinceAndCity',
                        rotateEffect: true,
                        toolbarTemplate: '<div class="toolbar">' +
                                '<div class="toolbar-inner">' +
                                '<div class="left"><a href="#" class="link toolbar-randomize-link">取消</a></div>' +
                                '<div class="right">' +
                                '<a href="#" class="link close-picker">确定</a>' +
                                '</div>' +
                                '</div> ' +
                                '</div> ',
                        formatValue: function (picker, values, displayValues) {
                            $$('#addBank_city').val(values[1]);
                            return province + " " + displayValues[1];
                        },
                        cols: [
                            {
                                textAlign: 'left',
                                values: provinceCodes,
                                displayValues: provinceNames,
                                onChange: function (picker, provinceCode, provinceName) {
                                    if (picker.cols[1].replaceValues) {
                                        $$('#addBank_province').val(provinceCode);
                                        province = provinceName;
                                        var flagContains = false;
                                        for (var i = 0; i < tempProvinceCityArray.length; i++) {
                                            if (tempProvinceCityArray[i].province == provinceCode) {
                                                picker.cols[1].replaceValues(tempProvinceCityArray[i].cityArray[0], tempProvinceCityArray[i].cityArray[1]);
                                                flagContains = true;
                                                break;
                                            }
                                        }
                                        if (!flagContains) {
                                            var cityArray = xinyedaiCtrl.getCitys(provinceCode);
                                            picker.cols[1].replaceValues(cityArray[0], cityArray[1]);
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
                        },
                        onClose:function(){
                        	// try {XXD_TRACK._trackEvent({category: "webapp_xinyedai", action: "xinyedai_province", label: "居住城市", value: $$("#addBank_provinceAndCity").val(), custval: "" });} catch (e) {}
                        }
                    });
                }
            });


            var pickerCycl = xxdApp.picker({
                input: '#picker-cycle',
                toolbarTemplate: '<div class="toolbar">' +
                        '<div class="toolbar-inner">' +
                        '<div class="left"><a href="#" class="link toolbar-randomize-link">取消</a></div>' +
                        '<div class="right">' +
                        '<a href="#" class="link close-picker">确定</a>' +
                        '</div>' +
                        '</div> ' +
                        '</div> ',
                cols: [
                    {
                        textAlign: 'center',
                        values: ['3个月', '6个月', '12个月']
                    }
                ],
                onOpen: function (picker) {

                    picker.container.find('.toolbar-randomize-link').on('click', function () {
                        picker.close();
                    });
                },
                onClose:function(){
                	//try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_term", label: "借款期限", value: $$("#picker-cycle").val(), custval: "" });} catch (e) {}
                }
            });
        },
        getCitys: function (provinceValue) {
            var result = [];
            var cityCodes = [];
            var cityNames = [];
            req.callJSON({
                url: 'personal/lowerArea.do',
                data: {
                    "province": provinceValue
                },
                async: false,
                timeout: 10000,
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
        }
    };
    return xinyedaiCtrl;
});