define(function(){
    var choosedNo1 = "";
    var choosedNo2 = "";
    var confirmNo1 = "";
    var confirmNo2 = "";
    var teams=[];
    var eurocupWebappCtrl = {
        init: function () {
             var binding = [
                 {
                     element: '.eurocupWebapp .goDetail',
                     event: 'click',
                     handler: eurocupWebappCtrl.goDetail
                 },
                 {
	                 element: '.voteNo1',
	                 event: 'click',
	                 handler: eurocupWebappCtrl.vote
                 },
                 {
	                 element: '.voteNo2',
	                 event: 'click',
	                 handler: eurocupWebappCtrl.vote
                 }
             ];
            appFunc.bindEvents(binding);
            
            eurocupWebappCtrl.setTeamData();
        },
        goDetail:function(){
        	var closeTerm = $$(this).data("closeTerm");
            console.log(closeTerm);
        	if(closeTerm == undefined || closeTerm == null || closeTerm == ""){
        		xxdApp.alert("系统异常，请稍后重试", '抱歉');
        		return ;
        	}
            req.callJSON({
                url: 'xplan/getLatestSchemeId.do',
                data:{
                	"closeTerm": closeTerm
                },
                indicator: true,
                success: function (result) {
                	try{
	                    if (result.schemeInfo != null && result.schemeInfo != "") {
	                        var schemeInfo = result.schemeInfo;
	                        var planId = schemeInfo.SCHEMEID;
	                        GS.loadPage('plan/planDetailsV2_act.html?previousPage=eurocupWebapp' + '&planId=' + planId);
	                    }else{
	                    	xxdApp.alert("系统异常，请稍后重试", '抱歉');
	                    }
	                 }catch (e) {
                		console.log(e.message);
                		xxdApp.alert("系统异常，请稍后重试", '抱歉');
        			}   
                },
                error: function(xhr, type){
                	console.log("获取新元宝"+ closeTerm +"个月产品信息失败,ajax error...");
                	xxdApp.alert("系统异常，请稍后重试", '抱歉');
                }
            });
        },
        setTeamData:function(){
        	req.callJSON({
                url: 'eurocup/teamVoteData.do',
                indicator: true,
                success: function (result) {
                	try{
	                    if (result.resultCode == 0) {
	                    	eurocupWebappCtrl.setTeamList(result.teamData);
	                    }else if(result.resultCode == 400){
	                    	xxdApp.loginScreen();
	                    }else{
	                    	xxdApp.alert(result.resultDesc, '抱歉');
	                    }
	                 }catch (e) {
                		console.log(e.message);
                		xxdApp.alert("系统异常，请稍后重试", '抱歉');
        			}   
                },
                error: function(xhr, type){
                	xxdApp.alert("系统异常，请稍后重试", '抱歉');
                }
            });
        },
        setTeamList:function(teamData){
        	req.callGet({
                url: GC.getHtmlPath() + 'activity/eurocupTeamListItem.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    var compiledTemplate = t7.compile(result);
                    
                    for (var i=0;i<teamData.length;i++){
                    	teamData[i].level=1;
                    	teams.push(teamData[i].id);
                    }
                    var output1 = compiledTemplate({list: teamData});
                    $$(".eurocupWebapp .teamlist1").html(output1);
                    
                    for (var i=0;i<teamData.length;i++){
                    	teamData[i].level=2;
                    }
                    var output2 = compiledTemplate({list: teamData});

                    $$(".eurocupWebapp .teamlist2").html(output2);
                    
                    if (!appFunc.isLogin()) {
		        		$$(".eurocupWebapp .p_no1_team").on('click', eurocupWebappCtrl.chooseTeam); 
		        		$$(".eurocupWebapp .p_no2_team").on('click', eurocupWebappCtrl.chooseTeam); 
		        	}else{
		        		eurocupWebappCtrl.setMyVote();
		        	}
                }
            });
        },
        setMyVote:function(){
        	req.callJSON({
                url: 'eurocup/myVote.do',
                indicator: true,
                success: function (result) {
                	try{
	                    if (result.resultCode == 0) {
	                    	var binding=[];
	                    	if(result.championId != null){
								eurocupWebappCtrl.setSelectedStatus("no1_team",result.championId);
								$$(".eurocupWebapp .voteNo1").removeClass("eurocup_no1_enable_btn").removeClass("eurocup_no2_enable_btn").addClass("eurocup_disable_btn").data("voting","true");
	                    	}else{
	                    		$$(".eurocupWebapp .p_no1_team").on('click', eurocupWebappCtrl.chooseTeam); 
	                    	}
							if(result.runnerId != null){
	                    		eurocupWebappCtrl.setSelectedStatus("no2_team",result.runnerId);	 
	                    		$$(".eurocupWebapp .voteNo2").removeClass("eurocup_no1_enable_btn").removeClass("eurocup_no2_enable_btn").addClass("eurocup_disable_btn").data("voting","true");
	                    	}else{
	                    		$$(".eurocupWebapp .p_no2_team").on('click', eurocupWebappCtrl.chooseTeam); 
	                    	}
	                    }else if(result.resultCode == 400){
                        	xxdApp.loginScreen();
	                    }else{
	                    	xxdApp.alert("系统异常，请稍后重试", '抱歉');
	                    }
	                 }catch (e) {
                		console.log(e.message);
                		xxdApp.alert("系统异常，请稍后重试", '抱歉');
        			}   
                },
                error: function(xhr, type){
                	xxdApp.alert("系统异常，请稍后重试", '抱歉');
                }
            });
        },
        vote:function(){
        	var voteButton = $$(this);
        	var voteFlag = voteButton.hasClass("eurocup_disable_btn");
        	var voting = voteButton.data("voting");
        	if(voteFlag || voting == "true"){
        		return;
        	}
        	
//        	if(!eurocupWebappCtrl.checkActivityStatus()){
//        		return;
//        	}
        	
        	if (!appFunc.isLogin()) {
        		xxdApp.alert("请先登录后再进行竞猜~", '提示',function(){
        			xxdApp.loginScreen();
        		});
                return;
        	}else {
        		var voteType = voteButton.data("voteType");
        		if(voteType == 1){
        			if(choosedNo1 == undefined || choosedNo1 == null || choosedNo1 == ""){
		        		xxdApp.alert("您还没有选择球队，请选择一支球队参与竞猜哦！", '提示');
		        		return ;
		        	}else{
		        		confirmNo1 = choosedNo1;
		        		confirmNo2 = "";
		        	}
		        }else{
		        	if(choosedNo2 == undefined || choosedNo2 == null || choosedNo2 == ""){
		        		xxdApp.alert("您还没有选择球队，请选择一支球队参与竞猜哦！", '提示');
		        		return ;
		        	}else{
		        		confirmNo1 = "";
		        		confirmNo2 = choosedNo2;
		        	}
		        }
		        
		        voteButton.data("voting","true");
        		req.callPost({
                    url: "eurocup/vote.do",
                    data: {
                        choosedNo1: confirmNo1,
                        choosedNo2: confirmNo2
                    },
                    dataType: 'json',
                    timeout: 15000,
                    indicator: true,
                    success: function (data) {
                        voteButton.data("voting","false");
                        if(data.resultCode == 0){
                        	xxdApp.alert(data.desc, '提示',function(){
                        		if(voteType == 1){
                        			voteButton.removeClass("eurocup_no1_enable_btn").addClass("eurocup_disable_btn").data("voting","true");	
                        			$$(".eurocupWebapp .p_no1_team").off('click', eurocupWebappCtrl.chooseTeam); 
						        }else{
						        	voteButton.removeClass("eurocup_no2_enable_btn").addClass("eurocup_disable_btn").data("voting","true");
						        	$$(".eurocupWebapp .p_no2_team").off('click', eurocupWebappCtrl.chooseTeam); 
						        }
                        	});
                        }else if(data.resultCode == 400){
                        	xxdApp.loginScreen();
	                    }else{
	                    	xxdApp.alert(data.desc, '提示',function (){
	                    		//"只有投资新元宝才有机会参与竞猜哦！"；
	                    		if(data.desc.indexOf("投资新元宝") > 0){
	                    			$$(".eurocupWebapp").scrollTop(1170, 300, null);
	                    		} 
	                    	});
	                    }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    	voteButton.data("voting","false");
                    }
                });
		        
        	}
        },
        chooseTeam:function(){
        	if (!appFunc.isLogin()) {
        		xxdApp.alert("请先登录后再进行竞猜~", '提示',function(){
        			xxdApp.loginScreen();
        		});
                return;
        	}
        	var teamId = $$(this).data("teamId");
        	var voteType = $$(this).data("voteType");
        	if(teamId == undefined || teamId == null || teamId == ""){
        		xxdApp.alert("系统异常，请稍后重试", '抱歉');
        		return ;
        	}else{
        		if(voteType == "1"){
        			choosedNo1 = teamId;
	        		eurocupWebappCtrl.setSelectedStatus("no1_team",teamId);
        		}else if(voteType == "2"){
        			choosedNo2 = teamId;
	        		eurocupWebappCtrl.setSelectedStatus("no2_team",teamId);
        		}else{
        			xxdApp.alert("系统异常，请稍后重试", '抱歉');
        			return ;
        		}
        	}
        },
        setSelectedStatus:function(level,team){
//        	var teams=["1","2","3","4"];
        	for(var i = 0; i < teams.length; i++){
        		var teamSpan = $$(".eurocupWebapp ." + level + teams[i]);
        		if(teams[i] == team){
        			teamSpan.removeClass("eurocup_default_circle").removeClass("eurocup_special_circle").addClass("eurocup_special_circle");
        		}else{
        			teamSpan.removeClass("eurocup_default_circle").removeClass("eurocup_special_circle").addClass("eurocup_default_circle");
        		}
        	}
        },
        checkActivityStatus:function(){
        	var flag = false;
        	req.callJSON({
                url: "eurocup/activityStatus.do",
                dataType: 'json',
                indicator: true,
                async: false,
                success: function (data) {
                    if(data != true){
                    	xxdApp.alert("活动未开始或已经结束", '抱歉');
                    	flag = false;
                    }else{
                    	flag = true;
                    }
                }
            });
            return flag;
        }
    };
    return eurocupWebappCtrl;
});