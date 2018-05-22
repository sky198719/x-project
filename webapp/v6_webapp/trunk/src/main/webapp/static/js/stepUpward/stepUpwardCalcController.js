define(['js/utils/date','js/stepUpward/stepUpwardDetailView'], function (DateHandle,stepUpwardDetailView) {
	var productInfo = {};
	var selectedTerm = 0;
	var bankApr = 0.35;
    var stepUpwardCalcCtrl = {
        init: function (event) {
            stepUpwardId = "";
            selectedTerm = 0;
            
            stepUpwardCalcCtrl.bindingEvents();
            if(stepUpwardCalcCtrl.stepUpwardShowOrNot()){
	            stepUpwardCalcCtrl.getProductInfo();
        	}else{
        		$$("#calc").css("background-color", "#cccccc").css("color","#666").addClass("disable");
        		$$("#calc_submitTender").addClass("disable");
        		xxdApp.alert('步步高升产品暂已停售', '提示');
        	}
            
        },
        
        bindingEvents:function(){
            var bindings = [
            	{
                    element: '#calc',
                    event: 'click',
                    handler: stepUpwardCalcCtrl.calc
                },
                {
	                 element: '#calc_submitTender',
	                 event: 'click',
	                 handler: stepUpwardCalcCtrl.submitTender
                }
            ];
            appFunc.bindEvents(bindings);
        },
        
        //产品信息
        getProductInfo:function(){
            req.callJSON({
                url: 'stepUpward/getStepUpwardInfo.do',
                data: {},
                indicator: true,
                success: function (result) {
                	try{
	                    if(result.resultCode == 0){
	                		var resultData = result.data;
	                		productInfo = resultData;
	                		
	                		var tenderButton = $$("#calc_submitTender");
                			//是否已满额
	                		if (parseFloat(resultData.remAccount) == 0 || parseFloat(resultData.remAccount) < parseFloat(resultData.userLowestTender)) {
		                		tenderButton.addClass("disable").html("已抢光");
	                		}else{
			                	tenderButton.removeClass("disable").html("立即抢购");
	                		}
	                		
	                		stepUpwardCalcCtrl.selectTerm();
	                		
	                	}else{
	                		xxdApp.addNotification({
			                    title: '温馨提示',
			                    hold:3000,
			                    message: '获取步步高升产品信息失败，请稍后重试...'
			                });
	                	}
	                 }catch (e) {
                		console.log(e.message);
                		xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: '获取步步高升产品信息失败，请稍后重试...'
                        });
        			}   
                },
                error: function(xhr, type){
                	console.log("获取步步高升产品加入记录失败,ajax error...");
                    xxdApp.addNotification({
                        title: '抱歉',
                        hold: 3000,
                        message: '获取步步高升产品信息失败，请稍后重试...'
                    });
                }
            });
        },
        //期限
        selectTerm:function(){
            var termKeys = [];
            var termValues = [];
//            termKeys[0] = 0;
//            termValues[0] = "选择期限";
            for (var i = 0; i < 36; i++) {
                termKeys[i] = i + 1;
                termValues[i] = i + 1 + "个月";
            }

            var totalWidth = $$(".case1").width();
            var circleWidth = 60 / totalWidth;
            var useWidth = (1 - circleWidth)*100;
            
            var floatapr = productInfo.floatapr;
			var stepApr = productInfo.stepApr;
			var minApr = productInfo.minApr;
			var maxApr = productInfo.maxApr;
			//小数位（精度问题）
			var aprs = [];
			aprs.push(floatapr);
			aprs.push(stepApr);
			aprs.push(minApr);
			aprs.push(maxApr);
			var decimal = 0;
			for(var i=0; i< aprs.length; i++){
				var apr = aprs[i].toString();
				if(apr.indexOf(".") > 0){
					var dec = apr.split(".")[1].length;
					if(dec > decimal){
						decimal = dec;
					}
				}
			}
			var pow = Math.pow(10,decimal);
            
            minApr = parseFloat(minApr) + parseFloat(floatapr);
            maxApr = parseFloat(maxApr) + parseFloat(floatapr);
            
            //默认最低起投金额，展示一个月的利率
            $$(".stepUpwardCalc #tenderAmount").val(productInfo.userLowestTender);
            selectedTerm = 1;
            $$("#selectTerm").val("1个月");
            var suApr = parseFloat(minApr);
        	if(suApr >= maxApr){
        		suApr = maxApr;
        	}
        	$$(".stepUpwardCalc #stepUpwardApr").html(suApr + "%");
            $$(".stepUpwardCalc #stepUpwardApr1").css("width", suApr / maxApr * useWidth + 2 + "%");
        	$$(".stepUpwardCalc #stepUpwardApr2").css("left", (suApr / maxApr) * useWidth + "%");
        	
        	$$(".stepUpwardCalc #bankApr").html(bankApr + "%");
        	$$(".stepUpwardCalc #bankApr1").css("width", bankApr / maxApr * useWidth + 2 + "%");
        	$$(".stepUpwardCalc #bankApr2").css("left", (bankApr / maxApr) * useWidth + "%");
            stepUpwardCalcCtrl.calc();
        	var pickerTerm = xxdApp.picker({
                        input: '#selectTerm',
                        rotateEffect: true,
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
                                values: termValues
                            }
                        ],
                        onChange: function (p, value, displayValue) {
                            for (var i = 0; i <= termValues.length; i++) {
                                if (termValues[i] == value) {
                                	$$("#selectTerm").val(termValues[i]);
                                    selectedTerm = termKeys[i];
                                    break;
                                }
                            }
                            
                            if(selectedTerm != 0){
	                            var stepUpwardApr = parseFloat(minApr) + ( selectedTerm - 1 ) * parseFloat(stepApr);
					        	if(stepUpwardApr >= maxApr){
					        		stepUpwardApr = maxApr;
					        	}
					        	stepUpwardApr = Math.round(stepUpwardApr * pow) / pow;
					        	
					        	$$(".stepUpwardCalc #stepUpwardApr").html(stepUpwardApr + "%");
	                            $$(".stepUpwardCalc #stepUpwardApr1").css("width", stepUpwardApr / maxApr * useWidth + 2 + "%");
					        	$$(".stepUpwardCalc #stepUpwardApr2").css("left", (stepUpwardApr / maxApr) * useWidth + "%");
					        	
					        	$$(".stepUpwardCalc #bankApr").html(bankApr + "%");
					        	$$(".stepUpwardCalc #bankApr1").css("width", bankApr / maxApr * useWidth + 2 + "%");
					        	$$(".stepUpwardCalc #bankApr2").css("left", (bankApr / maxApr) * useWidth + "%");
                            }
                            var tenderAmount = $$(".stepUpwardCalc #tenderAmount").val();
				            if (tenderAmount && appFunc.isFloat(tenderAmount) && tenderAmount > 0) {
					        	stepUpwardCalcCtrl.calc();
				            }else{
				            	$$(".stepUpwardCalc #stepUpwardIncome").html(0);
				            	$$(".stepUpwardCalc #bankIncome").html(0);
				            }
                        },
                        onOpen: function (picker) {
                            picker.container.find('.toolbar-randomize-link').on('click', function () {
                                picker.close();
                            });
                        }
                    });
        	
        },
        
        //计算
        calc:function(){
        	
        	if($(this).hasClass("disable")){
            	return;
            }
        	
        	var tenderAmount = $$(".stepUpwardCalc #tenderAmount").val();
            if (!tenderAmount) {
                xxdApp.alert('请填写金额', '提示');
                return false;
            }
            if (!appFunc.isFloat(tenderAmount)) {
                xxdApp.alert('请正确填写金额', '提示');
                return false;
            }
            if (tenderAmount <= 0) {
                xxdApp.alert('金额须大于零', '提示');
                return false;
            }
        	if(selectedTerm == 0){
        		xxdApp.alert('请选择期限', '提示');
        		return;
        	}
        	if(productInfo == {}){
        		xxdApp.alert('未获取到产品信息，请刷新页面重试', '提示');
        		return;
        	}
        	
        	var floatapr = productInfo.floatapr;
            var minApr = parseFloat(productInfo.minApr) + parseFloat(floatapr);
            var maxApr = parseFloat(productInfo.maxApr) + parseFloat(floatapr);
        	
        	var stepUpwardApr = parseFloat(minApr) + ( selectedTerm - 1 ) * parseFloat(productInfo.stepApr);
        	if(stepUpwardApr >= maxApr){
        		stepUpwardApr = maxApr;
        	}
        	var stepUpwardIncome = parseFloat(tenderAmount) * ( stepUpwardApr / 100 / 12 *  selectedTerm);
        	$$(".stepUpwardCalc #stepUpwardIncome").html(Math.floor(stepUpwardIncome*100) /100);
        	
        	var bankIncome = parseFloat(tenderAmount) * ( bankApr / 100 / 12 *  selectedTerm);
        	$$(".stepUpwardCalc #bankIncome").html(Math.floor(bankIncome * 100) / 100);
        },
        //投资
        submitTender:function(){
            if($(this).hasClass("disable")){
            	//xxdApp.alert('该产品暂不可购买', '抱歉');
            	return;
            }
        	if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            
            var userStepAccount = stepUpwardCalcCtrl.getUserStepUpwardInfo();
    		if(parseFloat(userStepAccount.remaCapitalTotal) >= parseFloat(productInfo.userMostTender)){
            	xxdApp.alert('您的个人可购买额度已满额', '提示');
            	return;
            }
            
            if(productInfo.stepId != null && productInfo.stepId != ""){
	            GS.loadPage('stepUpward/stepUpwardTender.html');
            }else{
            	xxdApp.addNotification({
                    title: '温馨提示',
                    hold:3000,
                    message: '系统异常，请刷新页面重试...'
                });
            }
        },
        getUserStepUpwardInfo:function(){  
        	var userStepAccount = {};
			req.callJSON({
                url: 'stepUpward/getUserStepUpwardInfo.do',
				data: {},               
                dataType: 'json',
                indicator: true,
                async: false,
                success: function (result) {
                	try{
	                	if(result.resultCode == 0){
	                		userStepAccount = result.userStepAccount;
	                	}
	                }catch (e) {
        			}
                },
                error: function (result){
                	console.log("获取用户持有步步高升信息失败,ajax error...");
                }
            });
            return userStepAccount;
		},
        stepUpwardShowOrNot: function(){
        	var showFlag = false;
        	req.callJSON({
                url: 'stepUpward/stepUpwardShowOrNot.do',
                data: {},
                dataType: 'json',
                indicator: true,
                async:false,
                success: function (result) {
                	try{
                		if(result.resultCode != -1){
                			if("Y" == result.resultCode){
                				showFlag = true; 
                			}else{
                				
                			}
                		}else{
	                		console.log("获取步步高升产品展示状态失败，请稍后重试...");
                		}
	                }catch (e) {
                		console.log(e.message);
                		console.log("获取步步高升产品展示状态失败，请稍后重试...");
        			} 
                },
                error: function(xhr, type){
                	console.log("ajax error...");
                	console.log("获取步步高升产品展示状态失败，请稍后重试...");
                }
            });
            return showFlag;
        }
    };
    return stepUpwardCalcCtrl;
});
