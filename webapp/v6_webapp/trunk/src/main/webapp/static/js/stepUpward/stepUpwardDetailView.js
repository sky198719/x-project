define(['chart', 'chartDoughnut','js/utils/date'], function (Chart, Doughnut, DateHandle) {
    
    var joinHistory = "";
    var stepUpwardDetailViewCtrl = {
        init: function (event) {
            
        },
        
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        
        //获取产品说明
        showIntroduction:function(params){
            req.callGet({
                url: GC.getHtmlPath() + 'stepUpward/stepUpwardIntroduction.html?' + GC.getVersion(),
                dataType: 'text',
                indicator:true,
                success: function (result) {
                    $$(".stepUpwardDetail #tabContent").html(result);
					var stepApr = params.plannedAnnualStepRate;
					var minApr = params.plannedAnnualRateFrom;
					var maxApr = params.plannedAnnualRateTo;
					//小数位（精度问题）
					var aprs = [];
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
					
		            minApr = parseFloat(minApr);
		            maxApr = parseFloat(maxApr);
                    $$(".stepUpwardDetail .introductionMinApr").html(minApr);
                    $$(".stepUpwardDetail .introductionStepApr").html(stepApr);
                    $$(".stepUpwardDetail .introductionMaxApr").html(maxApr);
                    
                    //利率表格
                    var interestRateTable = "";
                    var lines = Math.ceil(( maxApr - minApr ) / parseFloat(stepApr)) + 1 ;
                    var pow = Math.pow(10,decimal);
                    for(var i = 1; i <= lines; i++){
                    	var rate = minApr + ( i - 1 ) * parseFloat(stepApr);
                    	if(rate >= maxApr){
			        		rate = maxApr;
			        	}
			        	rate = Math.round(rate * pow) / pow;
                    	if(i == lines){
                    		interestRateTable = interestRateTable + "<tr><td>" + i + "个月及更长时间</td><td>" + rate + "%</td></tr>"
                    	}else{
                    		interestRateTable = interestRateTable + "<tr><td>" + i + "个月</td><td>" + rate + "%</td></tr>"
                    	}
                    }
                    $$(".stepUpwardDetail .interestRateTable").html(interestRateTable);
                    
                    //本金10000元，65天按2个月算,不满下一个月的 都按上一个月算
                    var stepUpwardApr = parseFloat(minApr) + ( 2 - 1 ) * parseFloat(stepApr);
		        	if(stepUpwardApr >= maxApr){
		        		stepUpwardApr = maxApr;
		        	}
                    $$(".stepUpwardDetail .introductionCalcApr").html(stepUpwardApr);
                    $$(".stepUpwardDetail .introductionCalcAmount").html(appFunc.fmoney(Math.floor(stepUpwardApr * 65 * 10000 / 360) / 100, 2));
                    $$(".stepUpwardDetail .introductionTenderStartAmount").html(appFunc.fmoney(params.leastInvestAmount, 0));
                    $$(".stepUpwardDetail .introductionTenderIncreaseRadix").html(params.step);
                    $$(".stepUpwardDetail .introductionTenderMost").html(parseFloat(params.mostInvestAmount)/10000);
                    $$(".stepUpwardDetail .quitIncreaseRadix").html(params.quitStep);
                },
                error: function (result){
                	console.log("获取步步高升产品说明失败,ajax error...");
                	$$(".stepUpwardDetail #tabContent").html('<div style="text-align: center;color:#999999;">暂无内容...</div>');
                }
            });
        },
        
        //获取常见问题
        showQuestions : function(params) {
			req.callGet({
				url : GC.getHtmlPath() + 'stepUpward/stepUpwardQuestions.html?'
						+ GC.getVersion(),
				dataType : 'text',
				indicator : true,
				success : function(result) {
					$$(".stepUpwardDetail #tabContent").html(result);
					var minApr = parseFloat(params.plannedAnnualRateFrom);
					var maxApr = parseFloat(params.plannedAnnualRateTo);
					$$(".stepUpwardDetail .questionsMinApr").html(minApr);
					$$(".stepUpwardDetail .questionsMaxApr").html(maxApr);
                    $$(".stepUpwardDetail .plannedAnnualStepRate").html(params.plannedAnnualStepRate);

					var questionsApr = parseFloat(minApr) + (5 - 1) * parseFloat(params.plannedAnnualStepRate);
					if (questionsApr >= maxApr) {
						questionsApr = maxApr;
					}
					$$(".stepUpwardDetail .questionsApr").html(questionsApr);
					$$(".stepUpwardDetail .questionsTenderStartAmount")
							.html(appFunc.fmoney(params.leastInvestAmount, 0));
				},
				error : function(result) {
					console.log("获取步步高升常见问题失败,ajax error...");
					$$(".stepUpwardDetail #tabContent")
							.html('<div style="text-align: center;color:#999999;">暂无内容...</div>');
				}
			});
		},
        
        // 获取投资记录
        showJoinHistory:function(param){
            var content =  '<div class="list-block  media-list" style="margin-bottom: 0;">'+
		                    '<ul style="font-size:15px;margin:0px 15px;">'+
		                        '<li style="height: 40px;">'+
			                        '<div class="item-content width100" style="display: list-item;overflow: hidden;padding: 10px 10px 10px 0;border-bottom: 2px solid #cccccc;">'+
								           '<span style="float: left;width: 33%;text-align: center;">投资人</span>'+
								           '<span style="float: left;width: 33%;text-align: center;">加入金额</span>'+
								           '<span style="float: right;width: 33%;text-align: center;">加入时间</span>'+
								    '</div>'+
		                        '</li>'+
			                	'joinHistory'+
							'</ul>'+
			            '</div>';
			            
    		if(param.type=="pull"){
    			joinHistory = "";
    		}
    		
    		req.callGet({
                url: GC.getHtmlPath() + 'stepUpward/stepUpwardJoinHistoryItem.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    var compiledTemplate = t7.compile(result);
                    var output = compiledTemplate({list: param.dataList});

                    joinHistory = joinHistory + output;
                    
                    if(joinHistory!=null && joinHistory != ""){
		        		content = content.replace("joinHistory",joinHistory);
		        		$$(".stepUpwardDetail #tabContent").html(content);
		        	}else{
		        		console.log("步步高升暂无加入记录...");
		        		$$(".stepUpwardDetail #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
		        	}
                }
            });
        }
        
    };
    return stepUpwardDetailViewCtrl;
});
