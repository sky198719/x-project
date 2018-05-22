define(['chart', 'chartDoughnut','js/utils/date'], function (Chart, Doughnut, DateHandle) {
    
    var joinHistory = "";
    var planDetailsV2View = {
        init: function (event) {
            
        },
        
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        
        //获取产品说明
        showIntroduction:function(params){
            req.callGet({
                url: GC.getHtmlPath() + 'plan/planIntroduction.html?' + GC.getVersion(),
                dataType: 'text',
                indicator:true,
                success: function (result) {
                    $$(".planDetailsV2 #tabContent").html(result);

                    var html = params.apr + "%";
                    if(params.increaseApr != undefined && params.increaseApr != '') {
                        html += "+"+params.increaseApr+"%";
                    }
                    $$(".planDetailsV2 .introductionApr").html(html);

                    $$(".planDetailsV2 .introductionCloseTerm").html(params.closeTerm);
                    $$(".planDetailsV2 .introductionEndTime").html(params.endTime);
                    $$(".planDetailsV2 .introductionForfeitpercent").html(params.forfeitpercent);
                },
                error: function (result){
                	console.log("获取新元宝产品说明失败,ajax error...");
                	$$(".planDetailsV2 #tabContent").html('<div style="text-align: center;color:#999999;">暂无内容...</div>');
                }
            });
        },
        
        //获取常见问题
        showQuestions:function(){
            req.callGet({
                url: GC.getHtmlPath() + 'plan/planQuestions.html?' + GC.getVersion(),
                dataType: 'text',
                indicator:true,
                success: function (result) {
                   $$(".planDetailsV2 #tabContent").html(result);
                },
                error: function (result){
                	console.log("获取新元宝常见问题失败,ajax error...");
                	$$(".planDetailsV2 #tabContent").html('<div style="text-align: center;color:#999999;">暂无内容...</div>');
                }
            });
        },
        
        //获取投资记录
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
                url: GC.getHtmlPath() + 'plan/planJoinHistoryItem.html?' + GC.getVersion(),
                dataType: 'text',
                indicator: true,
                success: function (result) {
                    var compiledTemplate = t7.compile(result);
                    var output = compiledTemplate({list: param.dataList});

                    joinHistory = joinHistory + output;
                    
                    if(joinHistory!=null && joinHistory != ""){
		        		content = content.replace("joinHistory",joinHistory);
		        		$$(".planDetailsV2 #tabContent").html(content);
		        	}else{
		        		console.log("新元宝暂无加入记录...");
		        		$$(".planDetailsV2 #tabContent").html('<div style="text-align: center;color:#999999;">暂无数据...</div>');
		        	}
                }
            });
        },
        
        //设置环形进度条
        setAnnularProgress: function(){
        	/**********环形进度条（start）**********/
            var defaultOpt = {
                trackColor: '#efeff4',
                progressColor: '#6ec84e',
                percent: 0,
                duration: 1500
            }; // 默认选项
            var $target = $$(".planDetailsV2 .progress-ring");
            var color = $target.data('color'); // 颜色
            var percent = parseInt($target.data('percent'), 10); // 百分比
            var duration = parseFloat($target.data('duration'), 10) * 300; // 持续时间
            var trackColor, progressColor;
            if (color && color.split(',').length === 2) {
                var colorSet = color.split(',');
                trackColor = colorSet[0];
                progressColor = colorSet[1];
            } else {
                trackColor = defaultOpt.trackColor;
                progressColor = defaultOpt.progressColor;
            }

            if (!percent)
                percent = defaultOpt.percent;
            if (!duration)
                duration = defaultOpt.duration;

            $target.html('<div class="progress-track"></div><div class="progress-left"></div><div class="progress-right"></div><div class="progress-cover"></div><div class="progress-text"><span class="progress-num">' + percent +'</span><span class="progress-percent">%</span></div>');

            var x = $target.find('.progress-cover').height(); // 触发 Layout
            // http://stackoverflow.com/questions/12088819/css-transitions-on-new-elements

            $target.find('.progress-track, .progress-cover').css('border-color', trackColor);
            $target.find('.progress-left, .progress-right').css('border-color', progressColor);

            $target.find('.progress-left').css({
                'transform': 'rotate(' + percent * 3.6 + 'deg)',
                '-o-transform': 'rotate(' + percent * 3.6 + 'deg)',
                '-ms-transform': 'rotate(' + percent * 3.6 + 'deg)',
                '-moz-transform': 'rotate(' + percent * 3.6 + 'deg)',
                '-webkit-transform': 'rotate(' + percent * 3.6 + 'deg)',
                'transition': 'transform ' + duration + 'ms linear',
                '-o-transition': '-o-transform ' + duration + 'ms linear',
                '-ms-transition': '-ms-transform ' + duration + 'ms linear',
                '-moz-transition': '-moz-transform ' + duration + 'ms linear',
                '-webkit-transition': '-webkit-transform ' + duration + 'ms linear'
            });

            if (percent > 50) {
                var animation = 'toggle ' + (duration * 50 / percent) + 'ms'
                $target.find('.progress-right').css({
                    'opacity': 1,
                    'animation': animation,
                    'animation-timing-function': 'step-end'
                });
                $target.find('.progress-cover').css({
                    'opacity': 0,
                    'animation': animation,
                    'animation-timing-function': 'step-start'
                });
            }
            /**********环形进度条（end）**********/
        }
        
    };
    return planDetailsV2View;
});
