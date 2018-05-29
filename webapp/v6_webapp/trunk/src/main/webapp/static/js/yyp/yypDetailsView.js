define(['js/utils/date'], function (DateHandle) {
    
    var joinHistory = "";
    var yypDetailsView = {
        init: function (event) {
            
        },
        
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        
        showIntroduction:function(productInfo){
            req.callGet({
                url: GC.getHtmlPath() + 'yyp/yypIntroduction.html?' + GC.getVersion(),
                dataType: 'text',
                indicator:true,
                success: function (result) {
                    $$(".yypDetails #tabContent").html(result);
                    $$(".yypDetails .i_forfeitpercent").html(productInfo.forfeitPercent);
                    yypDetailsView.productDetail(productInfo.productId);
                },
                error: function (result){
                    console.log("获取_产品说明失败,ajax error...");
                    $$(".yypDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无内容...</div>');
                }
            });
        },

        productDetail:function(productId){
            req.callJSON({
                url:'product/productDetail.do',
                data:{
                    pCode:"YYP",
                    pid:productId
                },
                success: function (result) {

                    if(result.code == 200000) {
                        $$(".i_forfeitpercent").html(result.data.productInfo.penaltyApr);
                    }
                }
            });
        },

        showQuestions:function(productInfo){
            req.callGet({
                url: GC.getHtmlPath() + 'yyp/yypQuestions.html?' + GC.getVersion(),
                dataType: 'text',
                indicator:true,
                success: function (result) {
                   $$(".yypDetails #tabContent").html(result);
                   $$(".yypDetails .q_forfeitpercent").html(productInfo.forfeitPercent);
                    $$(".yypDetails .q_tenderStartAmount").html(productInfo.leastInvestAmount);
                    $$(".yypDetails .q_tenderIncreaseRadix").html(productInfo.stepTenderAmount);

                   //yypDetailsView.getCommonInfo(DateHandle.formatDate('yyyy-MM-dd',productInfo.OPENTIME));
                },
                error: function (result){
                    console.log("获取_常见问题失败,ajax error...");
                    $$(".yypDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无内容...</div>');
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
                url: GC.getHtmlPath() + 'yyp/yypJoinHistoryItem.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    var compiledTemplate = t7.compile(result);
                    var output = compiledTemplate({list: param.dataList});

                    joinHistory = joinHistory + output;
                    
                    if(joinHistory!=null && joinHistory != ""){
		        		content = content.replace("joinHistory",joinHistory);
		        		$$(".yypDetails #tabContent").html(content);
		        	}else{
		        		console.log("_暂无加入记录...");
		        		$$(".yypDetails #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
		        	}
                }
            });
        },
        getCommonInfo:function(openDate){
            req.callJSON({
                url: 'yyp/commonInfo.do',
				data: {openDate:openDate},
                indicator: true,
                success: function (result) {
                	try{
	                	if(result.resultCode == 0){
	                		var q_closeTerms = "";
	                		var q_closeTermNumbers = "";
	                		var q_aprs = "";
	                		var termAndAprs = result.termAndAprs;
	                		for (var i=0; i < termAndAprs.length; i++){
	                			q_closeTerms = q_closeTerms + termAndAprs[i].term +"个月，";
	                			q_closeTermNumbers = q_closeTermNumbers + termAndAprs[i].term +"、";
	                			q_aprs = q_aprs + termAndAprs[i].apr + "%，";
	                		}
	                		$$(".yypDetails .q_closeTerms").html(q_closeTerms.substring(0,q_closeTerms.length-1));
	                		$$(".yypDetails .q_closeTermNumbers").html(q_closeTermNumbers.substring(0,q_closeTermNumbers.length-1));
	                		$$(".yypDetails .q_aprs").html(q_aprs.substring(0,q_aprs.length-1));

	                	}else{
	                		xxdApp.addNotification({ title: '温馨提示', message: '获取_基本信息失败，请稍后重试...', hold:3000 });
	                	}
	                }catch (e) {
                		console.log(e.message);
                		xxdApp.addNotification({ title: '温馨提示', message: '获取_基本信息失败，请稍后重试...', hold:3000 });
        			}
                },
                error: function (result){
                	console.log("获取_基本信息失败,ajax error...");
                	xxdApp.addNotification({ title: '温馨提示', message: '获取_基本信息失败，请稍后重试...', hold:3000 });
                }
            });
        }
        
    };
    return yypDetailsView;
});
