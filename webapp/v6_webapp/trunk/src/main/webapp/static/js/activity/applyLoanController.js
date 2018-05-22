define(function () {
    var tempProvinceCityArray = [];
    var channel = '';
    var applyLoanCtrl = {
        init: function (event) {

            channel = appFunc.getEventDetailPageQuery(event).channel;

            applyLoanCtrl.initApply();
            var bindings = [
                {
                    element: '#applyLoan_submit',
                    event: 'click',
                    handler: applyLoanCtrl.applyLoan_submit
                },
                {
                    element: '#applyLoan_randCode',
                    event: 'click',
                    handler: applyLoanCtrl.randCode
                },
                {
                    element: '#applyLoan_money',
                    event: 'keyup',
                    handler: applyLoanCtrl.CheckInputIntFloat
                },
                {
                    element: '#applyLoan_income',
                    event: 'keyup',
                    handler: applyLoanCtrl.CheckInputIntFloat
                },
                {
                    element: '#applyLoan_top',
                    event: 'click',
                    handler: applyLoanCtrl.applyLoan_top
                },
                {
                	element: '#applyLoan_username',
                	event: 'blur',
                	handler: applyLoanCtrl.blurApplyLoan_username
                },
                {
                	element: '#applyLoan_mobileNo',
                	event: 'blur',
                	handler: applyLoanCtrl.blurApplyLoan_mobileNo
                },
                {
                	element: '#sex_1',
                	event: 'click',
                	handler: applyLoanCtrl.clickApplyLoan_sex_1
                },
                {
                	element: '#sex_0',
                	event: 'click',
                	handler: applyLoanCtrl.clickApplyLoan_sex_0
                },
                {
                	element: '#applyLoan_money',
                	event: 'blur',
                	handler: applyLoanCtrl.blurApplyLoan_money
                },
                {
                	element: '#applyLoan_income',
                	event: 'blur',
                	handler: applyLoanCtrl.blurPpplyLoan_income
                },
                {
                	element: '#applyLoan_randCode_value',
                	event: 'blur',
                	handler: applyLoanCtrl.blurApplyLoan_randCode_value
                }

            ];
            appFunc.bindEvents(bindings);
        },
        
        blurApplyLoan_username:function(){
        	// try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_username", label: "真实姓名", value: $$("#applyLoan_username").val(), custval: "" });} catch (e) {}
        },
        blurApplyLoan_mobileNo:function(){
        	// try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_mobileNo", label: "手机号码", value: $$("#applyLoan_mobileNo").val(), custval: "" });} catch (e) {}
        },
        clickApplyLoan_sex_1:function(){
        	//try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_sex", label: "称调", value: $$("#sex_1").val(), custval: "" });} catch (e) {}
        },
        clickApplyLoan_sex_0:function(){
        	//try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_sex", label: "称调", value: $$("#sex_0").val(), custval: "" });} catch (e) {}
        },
        blurApplyLoan_money:function(){
        	//try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_amount", label: "借款金额", value: $$("#applyLoan_money").val(), custval: "" });} catch (e) {}
        },
        blurPpplyLoan_income:function(){
        	//try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_income", label: "月交易额", value: $$("#applyLoan_income").val(), custval:"" });} catch (e) {}
        },
        blurApplyLoan_randCode_value:function(){
        	//try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_verifycode", label: "验证码", value:$$("#applyLoan_randCode_value").val(), custval:"" });} catch (e) {}
        },
       

        applyLoan_top:function(){
            window.location.href = "https://www.xinxindai.com/index.html";
        },
        CheckInputIntFloat: function () {
            var oInput = this;
            if ('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,2}/, '')) {
                oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,2}/) == null ? '' : oInput.value.match(/\d{1,}\.{0,1}\d{0,2}/);
            }
        },

        applyLoan_submit: function () {
        	// try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_btn", label: "快速申请", value:$$("#applyLoan_money").val(), custval:"" });} catch (e) {}
            var applyLoan_username = $$("#applyLoan_username").val();
            if (applyLoan_username == '') {
                $$("#applyLoan_username_error .dsd-error").html("请输入真实姓名");
                $$("#applyLoan_username_error").show();
                $$("#applyLoan_username").focus();
                return;
            } else if (applyLoan_username.length < 2 || applyLoan_username.length > 8) {
                $$("#applyLoan_username_error .dsd-error").html("真实姓名长度为2到8之间");
                $$("#applyLoan_username_error").show();
                $$("#applyLoan_username").focus();
                return;
            }
            $$("#applyLoan_username_error").hide();

            var applyLoan_mobileNo = $$("#applyLoan_mobileNo").val();
            if (applyLoan_mobileNo == '') {
                $$("#applyLoan_mobileNo_error .dsd-error").html("请输入手机号码");
                $$("#applyLoan_mobileNo_error").show();
                $$("#applyLoan_mobileNo").focus();
                return;
            }

            var vaRe = appFunc.validateMobile(applyLoan_mobileNo);
            if (vaRe != "true") {
                $$("#applyLoan_mobileNo_error .dsd-error").html("您输入的手机号码有误，请重新输入");
                $$("#applyLoan_mobileNo_error").show();
                $$("#applyLoan_mobileNo").focus();
                return;
            }
            $$("#applyLoan_mobileNo_error").hide();


            var addBank_province = $$("#addBank_province").val();
            if (addBank_province == '') {
                $$("#applyLoan_province_error .dsd-error").html("请选择您的居住城市");
                $$("#applyLoan_province_error").show();
                $$("#addBank_province").focus();
                return;
            }
            $$("#applyLoan_province_error").hide();

            var addBank_city = $$("#addBank_city").val();

            var applyLoan_money = $$("#applyLoan_money").val();
            if (applyLoan_money == '') {
                $$("#applyLoan_money_error .dsd-error").html("请输入借款金额");
                $$("#applyLoan_money_error").show();
                $$("#applyLoan_money").focus();
                return;
            } else if (!appFunc.isFloat(applyLoan_money)) {
                $$("#applyLoan_money_error .dsd-error").html("请输入正确的借款金额");
                $$("#applyLoan_money_error").show();
                $$("#applyLoan_money").focus();
                return;
            }
            $$("#applyLoan_money_error").hide();

            var pickerCycle = $$("#picker-cycle").val();
            if (pickerCycle == '') {
                $$("#picker_cycle_error .dsd-error").html("请选择借款期限");
                $$("#picker_cycle_error").show();
                $$("#picker-cycle").focus();
                return;
            }
            $$("#picker_cycle_error").hide();

            var applyLoan_income = $$("#applyLoan_income").val();
            if (applyLoan_income == '') {
                $$("#applyLoan_income_error .dsd-error").html("请输入店铺月均交易额");
                $$("#applyLoan_income_error").show();
                $$("#applyLoan_income").focus();
                return;
            } else if (!appFunc.isFloat(applyLoan_income)) {
                $$("#applyLoan_income_error .dsd-error").html("请输入正确的店铺月均交易额");
                $$("#applyLoan_income_error").show();
                $$("#applyLoan_income").focus();
                return;
            }
            $$("#applyLoan_income_error").hide();

            var applyLoan_randCode = $$("#applyLoan_randCode_value").val();
            if (applyLoan_randCode == '') {
                $$("#applyLoan_randCode_error .dsd-error").html("请输入验证码");
                $$("#applyLoan_randCode_error").show();
                $$("#applyLoan_randCode_value").focus();
                return;
            }
            $$("#applyLoan_randCode_error").hide();

            var sex  = $$("input[name='sex']");
            var gender = 1;
            for(var i = 0;i < sex.length; i++) {
                if(sex[i].checked) {
                    gender = sex[i].value;
                }
            }

            pickerCycle = '1个月' == pickerCycle ? 1: ('3个月' == pickerCycle ? 3 : ('6个月' == pickerCycle ? 6 : ('12个月' == pickerCycle ? 12 : 3)));

            req.callPost({
                url:'borrow/saveBorrowApply.do',
                data:{
                    borrowType:'18',
                    province: addBank_province,
                    city: addBank_city,
                    realName:applyLoan_username,
                    mobileNo:applyLoan_mobileNo,
                    gender:gender,
                    amount: applyLoan_money,
                    timeLimit:pickerCycle,
                    monthlyIncome:applyLoan_income,
                    randCode:applyLoan_randCode,
                    birthday:'1980-01-01',
                    channel:channel
                },
                dataType:'json',
                preloaderTitle:'请稍等，正在申请...',
                success:function(result){
                     if(result.resultCode == 0) {
                     	// try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_success", label: "申请成功", value:$$("#applyLoan_money").val(), custval:"" });} catch (e) {}
                         //额度=月均店铺营业额*比例*申贷期限
                         var startNumber = applyLoan_income * (9/100) * pickerCycle;
                        // var endNumber = applyLoan_income * (10/100) * pickerCycle;
                         var modal = xxdApp.modal({
                             title: '申请成功',
                             text: '<p style="text-align: center;font-size: 12px;">您的借款申请已提交，审批金额预估为：<span style="color: red">'+appFunc.fmoney(startNumber,2)+'</span>元<br>请保持电话畅通，我们将很快与您联系！</p>',
                             afterText:  '<p style="text-align: center;font-size: 12px;">如有问题欢迎致电人工客服<br>4000-169-521<br>（周一至周五9:00-18:00）</p>',
                             buttons: [
                               {
                                 text: '关闭',
                                 bold: true,
                                 onClick:function(){
                                     window.location.reload();
                                 }
                               },
                             ]
                           })
                     } else {
                         xxdApp.alert(result.msg);
                     }
                }
            });
        },

        randCode: function () {
            document.getElementById("applyLoan_randCode").src = "randCode/createVerifyCode.do?" + Math.random();
        },

        initApply: function () {
        	//try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_sex", label: "称调", value: $$("#sex_1").val(), custval: "" });} catch (e) {}
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

                    var defaultCityArray = applyLoanCtrl.getCitys(provinceCodes[0]);

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
                                            var cityArray = applyLoanCtrl.getCitys(provinceCode);
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
                        	// try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_province", label: "居住城市", value: $$("#addBank_provinceAndCity").val(), custval: "" });} catch (e) {}
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
                	// try {XXD_TRACK._trackEvent({category: "webapp_apply_loan", action: "applyLoan_term", label: "借款期限", value: $$("#picker-cycle").val(), custval: "" });} catch (e) {}
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
    return applyLoanCtrl;
});