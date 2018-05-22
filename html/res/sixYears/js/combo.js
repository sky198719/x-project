require(['base', 'float', 'allRoll', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs"], function ($, float, allRoll, track, store) {
	//公用变量
    var isLogin = 0; //是否登录 0已登录 1未登录
    var gameTime = 0; //是否开始 0已开始 1未开始 2已结束
    var timer = new Date();
    var myTime = timer.getTime(); //本地时间
    var formToken; //表单token
    var mobileReg = /^(((13[0-9]{1})|(14(0-9){1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //验证手机号码是否合法
    var passwordReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,16}$/; //验证密码是否合法

    //新元宝状体
    var xybNewId;
    var xybNewLeft;

    //时间判断
    var newDay;
    var newHour;

    var gameFlag = 0;

    //读取cookie
    function getCookie(name){
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg)){
            return unescape(arr[2]);
        }else{
            return null;
        }
    }
    var realToken;
    if(getCookie('Token') != '' && getCookie('Token') != undefined && getCookie('Token') != null){
        realToken = getCookie('Token');
    }else{
        realToken = getCookie('userToken');
    }

    //获取formToken
    $.ajax({
        url:'/feapi/users/formToken',
        type:'get',
        dataType:'json',
        success:function(data){
            formToken = data.token;
            //获取图片验证码
            $('#imgUrl').attr('src','/feapi/randCode/createVerifyCode?formtoken=' + formToken + '&v=' + Math.random());
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

    //登录判断
    $.ajax({
        url:'/feapi/users/loginInfo?userToken=' + realToken,
        type:'get',
        dataType:'json',
        success:function(data){
            if(data.code == '200000'){
                if(data.data.status.code == 200){
                    isLogin = 0;
                    //$('#btn3').css('background','url(img/p5.png) 0 0 no-repeat');
                }
            }else{
                isLogin = 1;
                //$('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
            }
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

    //布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);

    //弹出框
    function alertBox(msg,link){
        $('.toast span').html(msg);
        $('.toast').css('margin-left',-$('.toast').width()/2 - 15 + 'px');
        $('.toast').fadeIn(0);
        //$('.outLayer').fadeIn(0);
        if(link == 1){
            setTimeout(function(){
                $('.toast').fadeOut(0);
            },3000);
        }else{
            setTimeout(function(){
                window.location.href = link;
            },3000);
        }
    }

    //活动弹窗
    $('.close').click(function(){
        $('.outLayer').fadeOut(0);
        $('.outShowbox').fadeOut(0);
        $('canvas:last').remove();
    });
    function showBox(obj){
    	$('.outLayer').fadeIn(0);
        $(obj).fadeIn(0);
    }
    function hideBox(obj){
    	$('.outLayer').fadeOut(0);
        $(obj).fadeOut(0);
    }

    //浮层
    $(window).scroll(function(){
        if($('body').scrollTop() >= $(window).height()){
            $('.rightBox').fadeIn(0);
        }else{
            $('.rightBox').fadeOut(0);
        }
        if($('html').scrollTop() >= $(window).height()){
            $('.rightBox').fadeIn(0);
        }else{
            $('.rightBox').fadeOut(0);
        }
    });
    if($('body').scrollTop() >= $(window).height()){
        $('.rightBox').fadeIn(0);
    }else{
        $('.rightBox').fadeOut(0);
    }
    if($('html').scrollTop() >= $(window).height()){
        $('.rightBox').fadeIn(0);
    }else{
        $('.rightBox').fadeOut(0);
    }

    //新元宝
    var xybLeft = 0;
    function xybCheck(term){
        timer = new Date();
        myTime = timer.getTime();
        newDay = timer.getDay();
        newHour = timer.getHours();
        $.ajax({
            url:'/activityCenter/doubleDanActivityBase/getXYBSchemeByTerm?term=' + term,
            type:'get',
            async:false,
            cache:false,
            beforeSend:function(request){
                request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                request.setRequestHeader('clientTime',myTime);
            },
            success:function(data){
                if(data.code == '200000'){
                    xybNewId = data.data.scheme;
                    xybNewLeft = data.data.jiaxibiao;
                    if(data.data.end == true){
                        xybLeft = 1;
                    }else{
                        xybLeft = 0;
                    }
                }
            },
            error:function(){
                alert('网络异常，请重试！');
                return false;
            }
        });
    }
    $.ajax({
        url:'/tradeCenter/XYB/brief',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_FRONT_END');
            request.setRequestHeader('clientTime',myTime);
            request.setRequestHeader('s','s');
        },
        success:function(data){
            if(data.code == '200000'){
                $.each(data.data.items,function(index,item){
                    if(item.frozenPeriod == '6'){
                        //$('#xyb6Rate1').html(item.plannedAnnualRateFrom);
                        //$('#xyb6Float1').html(item.floatingRate);
                    }else if(item.frozenPeriod == '12'){
                        //$('#xyb12Rate1').html(item.plannedAnnualRateFrom);
                        //$('#xyb12Float1').html(item.floatingRate);
                    }
                });
            }
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

	//游戏动画
	//$('#awardList').find('li').eq(0).fadeIn(0);
	var gameIndex = 0;
	var gameT;
	var initGame = 0;
	function gameAnimate(rewardId,obj){
		gameT = setInterval(function(){
			initGame++;
			gameIndex++;
			if(gameIndex >= 7){
				gameIndex = 0;
			}
			$('#awardList').find('li').fadeOut(0).eq(gameIndex).fadeIn(0);
			if(initGame >= 49){
				clearInterval(gameT);
				gameT = setInterval(function(){
					initGame++;
					gameIndex++;
					if(gameIndex >= 7){
						gameIndex = 0;
					}
					$('#awardList').find('li').fadeOut(0).eq(gameIndex).fadeIn(0);
					if(initGame >= 63 && gameIndex == rewardId){
						clearInterval(gameT);
						initGame = 0;
						setTimeout(function(){
							showBox(obj);
							gameFlag = 0;
							setTimeout(function(){
								anim();
							},500);
                            initStatus();
                            if(rewardId == 6){
                                timer = new Date();
                                myTime = timer.getTime();
                                $.ajax({
                                    url:'/activityCenter/anniversaryActivityBase/initial?activityCode=Anniversary-activity',
                                    type:'get',
                                    async:false,
                                    cache:false,
                                    beforeSend:function(request){
                                        request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                                        request.setRequestHeader('clientTime',myTime);
                                        request.setRequestHeader('token',realToken);
                                    },
                                    success:function(data){
                                        if(data.data.data.candleKindNum == 6){
                                            setTimeout(function(){
                                                $('#outShowbox8-1').fadeOut(0);
                                                $('#outShowbox8-2').fadeOut(0);
                                                $('#outShowbox8-3').fadeOut(0);
                                                $('#outShowbox8-4').fadeOut(0);
                                                $('#outShowbox8-5').fadeOut(0);
                                                $('#outShowbox8-6').fadeOut(0);
                                                $('#outShowbox2').fadeIn(0);
                                                $('canvas:last').remove();
                                                setTimeout(function(){
                                                    anim();
                                                },500);
                                            },1500);
                                        }
                                    },
                                    error:function(){
                                        alert('网络异常，请重试！');
                                        return false;
                                    }
                                });
                            }
						},500);
						//gameAnimate(2000,500);
					}
				},150);
			}
		},50);
	}

	//登录
    function loginCheck(){
    	var loginInitCheck;
        $.ajax({
            url:'/feapi/users/loginInfo?userToken=' + realToken,
            type:'get',
            dataType:'json',
            async:false,
            cache:false,
            success:function(data){
                if(data.code == '200000'){
                    if(data.data.status.code == 200){
                        loginInitCheck = 0;
                    }
                }else{
                    loginInitCheck = 1;
                }
            },
            error:function(){
                alert('网络异常，请重试！');
                return false;
            }
        });
        return loginInitCheck;
    }

	//初始化信息
	var remainTimes;
	var candleKind = 0;
	function initStatus(){
		timer = new Date();
        myTime = timer.getTime();
		$.ajax({
	        url:'/activityCenter/anniversaryActivityBase/initial?activityCode=Anniversary-activity',
	        type:'get',
	        async:false,
	        cache:false,
	        beforeSend:function(request){
	            request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
	            request.setRequestHeader('clientTime',myTime);
	            request.setRequestHeader('token',realToken);
	        },
	        success:function(data){
	            $('#annualAccount').html(Math.round(parseFloat(data.data.data.annualAccount)*100)/100);
	            remainTimes = parseInt(data.data.data.LotteryByXYBTimes);
	            $('#gameTime').fadeIn(0);
	            $('#game').fadeIn(0);
	            $('#remainLotteryTimes').html(data.data.data.remainLotteryTimes);
	            $.each(data.data.data.candleKind,function(index,item){
	            	candleKind += 1;
	            	if(item == '蓝色蜡烛'){
	            		$('#candleList #li1').fadeIn(0).attr('class','isActive');
	            	}else if(item == '橙色蜡烛'){
	            		$('#candleList #li2').fadeIn(0).attr('class','isActive');
	            	}else if(item == '红色蜡烛'){
	            		$('#candleList #li3').fadeIn(0).attr('class','isActive');
	            	}else if(item == '紫色蜡烛'){
	            		$('#candleList #li4').fadeIn(0).attr('class','isActive');
	            	}else if(item == '黄色蜡烛'){
	            		$('#candleList #li5').fadeIn(0).attr('class','isActive');
	            	}else if(item == '绿色蜡烛'){
	            		$('#candleList #li6').fadeIn(0).attr('class','isActive');
	            	}
	            });
	            $('#candleKind').html(candleKind);
	            if(data.data.data.isNewUser == false){
	                $('#newReward').fadeOut(0);
	                $('#bg3').css('height','505px');
	                $('#btn2').css('background','url(img/p4-3.png) 0 0 no-repeat');
	                $('#btn2').click(function(){
	                	showBox('#outShowbox1');
	                });
	            }else if(data.data.data.isNewUser == true){
	            	$('#btn2').click(function(){
	            		window.location.href = '/';
	            	});
	                if(data.data.data.annualAccount < 10000){
	                    $('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
	                }else if(data.data.data.annualAccount >= 10000){
	                    $('#btn3').click(function(){
	                        if(data.data.data.alreadyClaimECard == false){
	                        	if(data.data.data.claimECardTotal < 500){
	                        		$.ajax({
	                                    url:'/activityCenter/anniversaryActivityBase/deliverNewUserPrize',
	                                    type:'get',
	                                    async:false,
	                                    cache:false,
	                                    beforeSend:function(request){
	                                        request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
	                                        request.setRequestHeader('clientTime',myTime);
	                                        request.setRequestHeader('token',realToken);
	                                    },
	                                    success:function(data){
	                                    	if(data.code == '200000'){
	                                    		if(data.data.code == 0){
	                                    			showBox('#outShowbox11');
	                                    		}else{
	                                    			alert('网络异常，请重试！');
	                                    		}
	                                    	}
	                                    },
	                                    error:function(){
	                                    	alert('网络异常，请重试！');
	            							return false;
	                                    }
	                                });
	                        	}else{
	                        		showBox('#outShowbox12');
	                        	}
	                        }else if(data.data.data.alreadyClaimECard == true){
	                        	showBox('#outShowbox13');
	                        }
	                    });
	                }
	            }
	        },
	        error:function(){
	            alert('网络异常，请重试！');
	            return false;
	        }
	    });
	}

	//我的奖品列表
	function timeCheck(obj){
		if(obj < 10){
			return '0' + obj;
		}else{
			return obj;
		}
	}
	var currentPage = 1;
	var totalPage = 0;
	var listInit = 0;
	function myReward(currentPage){
		timer = new Date();
        myTime = timer.getTime();
    	$.ajax({
            url:'/activityCenter/anniversaryActivityBase/getMyAwardsList?activityCode=Anniversary-activity&currentPage=' + currentPage + '&pageSize=6',
            type:'get',
            async:false,
            cache:false,
            beforeSend:function(request){
                request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                request.setRequestHeader('clientTime',myTime);
                request.setRequestHeader('token',realToken);
            },
            success:function(data){
            	if(data.data.data.total%6 == 0){
            		totalPage = parseInt(data.data.data.total/6);
            	}else{
            		totalPage = parseInt(data.data.data.total/6) + 1;
            	}
            	var eventTime;
            	var eventMonth;
            	var eventDay;
        		var eventHour;
        		var eventMinute;
        		var eventSecond;
        		$('#myReward').empty();
        		if(data.data.data.list == '' || data.data.data.list == undefined || data.data.data.list == null){
        			showBox('#outShowbox10');
        		}else{
        			$.each(data.data.data.list,function(index,item){
	        			eventTime = new Date(item.addtime);
	        			eventMonth = timeCheck(eventTime.getMonth() + 1);
	        			eventDay = timeCheck(eventTime.getDate());
	            		eventHour = timeCheck(eventTime.getHours());
	            		eventMinute = timeCheck(eventTime.getMinutes());
	            		eventSecond = timeCheck(eventTime.getSeconds());
	            		$('#myReward').append('<li><strong>' + eventMonth + '-' + eventDay + ' ' + eventHour + ':' + eventMinute + ':' + eventSecond + '</strong><span>' + item.prizename + '</span><div></div></li>');
	        		});
	        		showBox('#outShowbox9');
        		}
            },
            error:function(){
            	alert('网络异常，请重试！');
                return false;
            }
        });
	}
	$('#pre').click(function(){
		currentPage--;
		if(currentPage <= 0){
			alert('已经是第一页');
			currentPage = 1;
		}
		myReward(currentPage);
	});
	$('#next').click(function(){
		currentPage++;
		if(currentPage > totalPage){
			alert('已经是最后一页');
			currentPage = totalPage;
		}
		myReward(currentPage);
	});

	//抽奖
	function lottery(){
		timer = new Date();
        myTime = timer.getTime();
		gameFlag = 1;
		$.ajax({
	        url:'/activityCenter/activityBase/lottery?activityCode=Anniversary-activity',
	        type:'post',
	        async:false,
	        cache:false,
	        beforeSend:function(request){
	            request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
	            request.setRequestHeader('clientTime',myTime);
	            request.setRequestHeader('token',realToken);
	        },
	        success:function(data){
	        	if(data.code == '200000'){
	        		if(data.data.code == 0){
	        			if(data.data.data.prizeInfo.prizename == 'Apple iMac一体机'){
	        				gameAnimate(3,'#outShowbox5');
	        			}else if(data.data.data.prizeInfo.prizename == '骆驼户外自动帐篷'){
	        				gameAnimate(4,'#outShowbox6');
	        			}else if(data.data.data.prizeInfo.prizename == '100元京东E卡'){
	        				gameAnimate(2,'#outShowbox4');
	        			}else if(data.data.data.prizeInfo.prizename == '50元红包'){
	        				gameAnimate(5,'#outShowbox7');
	        			}else if(data.data.data.prizeInfo.prizename == '10元红包'){
	        				gameAnimate(1,'#outShowbox3');
	        			}else if(data.data.data.prizeInfo.prizename == '红色蜡烛'){
	        				gameAnimate(6,'#outShowbox8-3');
	        			}else if(data.data.data.prizeInfo.prizename == '蓝色蜡烛'){
	        				gameAnimate(6,'#outShowbox8-1');
	        			}else if(data.data.data.prizeInfo.prizename == '绿色蜡烛'){
	        				gameAnimate(6,'#outShowbox8-6');
	        			}else if(data.data.data.prizeInfo.prizename == '黄色蜡烛'){
	        				gameAnimate(6,'#outShowbox8-5');
	        			}else if(data.data.data.prizeInfo.prizename == '橙色蜡烛'){
	        				gameAnimate(6,'#outShowbox8-2');
	        			}else if(data.data.data.prizeInfo.prizename == '紫色蜡烛'){
	        				gameAnimate(6,'#outShowbox8-4');
	        			}
	        		}else if(data.data.code == 1){
	        			initStatus();
	        			if(remainTimes < 3){
	        				showBox('#outShowbox16');
	        				gameFlag = 0;
	        			}else{
	        				showBox('#outShowbox15');
	        				gameFlag = 0;
	        			}
	        		}else{
	        			alert(data.data.message);
	        			gameFlag = 0;
	        		}
	        	}
	        },
	        error:function(){
	        	alert('网络异常，请重试！');
                return false;
	        }
	    });
	}

	//再来一次
	function gameAgain(obj){
		hideBox(obj);
		if(gameFlag == 0){
    		lottery();
    	}
	}

	$('#box2Btn,#box4Btn,#box5Btn,#box6Btn,#box8Btn1,#box8Btn2,#box8Btn3,#box8Btn4,#box8Btn5,#box8Btn6').click(function(){
		gameAgain($(this).parent('div'));
	});
	$('#box3Btn,#box7Btn').click(function(){
		window.location.href = '/coupon/gomycoupon.html';
	});
	$('#box12Btn,#box13Btn,#box14Btn').click(function(){
		window.location.href = '#bg2';
		$('.outLayer').fadeOut(0);
        $('.outShowbox').fadeOut(0);
	});
	$('#box15Btn').click(function(){
		window.location.href = '/';
	});
	$('#box16Btn').click(function(){
		window.location.href = '/xplan/search/list.html';
	});

    //活动状态
    $.ajax({
        url:'/activityCenter/anniversaryActivityBase/getActivityStatus?activityCode=Anniversary-activity',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
            request.setRequestHeader('clientTime',myTime);
        },
        success:function(data){
            if(data.data.data.activityStatus == -1){
                $('#btn1').css('background','url(img/p3-1.png) 0 0 no-repeat');
                $('#btn2').css('background','url(img/p4-1.png) 0 0 no-repeat');
                $('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
                $('#btn4').css('background','url(img/p6-1.png) 0 0 no-repeat');
                $('#btn5').css('background','url(img/p6-1.png) 0 0 no-repeat');
                /*
                $('#btn4').click(function(){
                	if(loginCheck() == 0){
                		xybCheck(6);
                		if(xybLeft == 0){
                			alertBox('加息活动未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
                		}else{
                            window.location.href = '/xplan/detail/' + xybNewId + '.html';
                		}
                	}else{
                		window.location.href = '/user/ilogin.html';
                	}
                });
                $('#btn5').click(function(){
                	if(loginCheck() == 0){
                		xybCheck(12);
                    	if(xybLeft == 0){
                			alertBox('加息活动未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
                		}else{
                			window.location.href = '/xplan/detail/' + xybNewId + '.html';
                		}
                	}else{
                		window.location.href = '/user/ilogin.html';
                	}
                });
                */
                $('#rewardList').html('活动未开始');
                $('#rule').click(function(){
                	return false;
                });
            }else if(data.data.data.activityStatus == 1){
                $('#btn1').css('background','url(img/p3-2.png) 0 0 no-repeat');
                $('#btn2').css('background','url(img/p4-2.png) 0 0 no-repeat');
                $('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
                $('#btn4').css('background','url(img/p6-2.png) 0 0 no-repeat');
                $('#btn5').css('background','url(img/p6-2.png) 0 0 no-repeat');
                $('#btn6').css('background','url(img/p7-2.png) 0 0 no-repeat');
                /*
                $('#btn4').click(function(){
                	if(loginCheck() == 0){
                		xybCheck(6);
                		if(xybLeft == 0){
                			alertBox('加息活动已结束，购买普通标','/xplan/detail/' + xybNewId + '.html');
                		}else{
                			window.location.href = '/xplan/detail/' + xybNewId + '.html';
                		}
                	}else{
                		window.location.href = '/user/ilogin.html';
                	}
                });
                $('#btn5').click(function(){
                	if(loginCheck() == 0){
                		xybCheck(12);
                    	if(xybLeft == 0){
                			alertBox('加息活动已结束，购买普通标','/xplan/detail/' + xybNewId + '.html');
                		}else{
                			window.location.href = '/xplan/detail/' + xybNewId + '.html';
                		}
                	}else{
                		window.location.href = '/user/ilogin.html';
                	}
                });
                */
                $('#rewardList').html('活动已结束');
                $('#rule').click(function(){
                	return false;
                });
            }else if(data.data.data.activityStatus == 0){
                $('#btn3').css('background','url(img/p5-1.png) 0 0 no-repeat');
            	$('#btn4').click(function(){
            		if(loginCheck() == 0){
	                	xybCheck(6);
	                    //if(newDay == 3 || newDay == 5 || newDay == 2 && newHour >= 9 && newHour <24){
                        if(newDay == 3 || newDay == 5){
                        	if(newHour < 9 && newHour > 0){
                        		if(xybLeft == 0){
		                			window.location.href = '/xplan/detail/' + xybNewId + '.html';
		                		}else{
		                			window.location.href = '/xplan/detail/' + xybNewId + '.html';
		                		}
                        	}else{
                        		if(xybNewLeft == true){
		                            window.location.href = '/xplan/detail/' + xybNewId + '.html';
		                        }else{
		                        	if(xybLeft == 0){
			                			alertBox('已经抢完啦，购买普通标','/xplan/detail/' + xybNewId + '.html');
			                		}else{
			                			window.location.href = '/xplan/detail/' + xybNewId + '.html';
			                		}
		                        }
                        	}
	                    }else{
	                    	if(xybLeft == 0){
	                			alertBox('加息未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
	                		}else{
	                			window.location.href = '/xplan/detail/' + xybNewId + '.html';
	                		}
	                    }
                	}else{
                		window.location.href = '/user/ilogin.html';
                	}
                });
                $('#btn5').click(function(){
                	if(loginCheck() == 0){
		                xybCheck(12);
	                    //if(newDay == 3 || newDay == 5 || newDay == 2 && newHour >= 9 && newHour <24){
                        if(newDay == 3 || newDay == 5){
                        	if(newHour < 9 && newHour > 0){
                        		if(xybLeft == 0){
		                			window.location.href = '/xplan/detail/' + xybNewId + '.html';
		                		}else{
		                			window.location.href = '/xplan/detail/' + xybNewId + '.html';
		                		}
                        	}else{
                        		if(xybNewLeft == true){
		                            window.location.href = '/xplan/detail/' + xybNewId + '.html';
		                        }else{
		                        	if(xybLeft == 0){
			                			alertBox('已经抢完啦，购买普通标','/xplan/detail/' + xybNewId + '.html');
			                		}else{
			                			window.location.href = '/xplan/detail/' + xybNewId + '.html';
			                		}
		                        }
                        	}
	                    }else{
	                    	if(xybLeft == 0){
	                			alertBox('加息未开始，购买普通标','/xplan/detail/' + xybNewId + '.html');
	                		}else{
	                			window.location.href = '/xplan/detail/' + xybNewId + '.html';
	                		}
	                    }
                	}else{
                		window.location.href = '/user/ilogin.html';
                	}
                });
                //获奖列表
                $.ajax({
                    url:'/activityCenter/anniversaryActivityBase/getLatestAwardsList?activityCode=Anniversary-activity',
                    type:'get',
                    async:false,
                    cache:false,
                    beforeSend:function(request){
                        request.setRequestHeader('clientId','XXD_ACTIVITY_H5_PAGE');
                        request.setRequestHeader('clientTime',myTime);
                    },
                    success:function(data){
                        if(data.code == '200000'){
                            $.each(data.data.prize,function(index,item){
                                $('#rewardList ul').append('<li><strong>' + item.username + '</strong><span>' + item.prizename + '</span></li>');
                            });
                            $('#rewardList ul').html($('#rewardList ul').html() + $('#rewardList ul').html());
                            var ulWidth = 0;
                            $.each($('#rewardList ul li'),function(){
                                ulWidth += $(this).width();
                            });
                            $('#rewardList ul').css('width',ulWidth + 'px');
                            if($('#rewardList ul li').length >= 4){
                                var leftMargin = 0;
                                var t = setInterval(function(){
                                    leftMargin--;
                                    if(-leftMargin >= ulWidth/2){
                                        leftMargin = 0;
                                    }
                                    $('#rewardList ul').css('margin-left',leftMargin + 'px');
                                },10);
                            }
                        }
                    },
                    error:function(){
                        alert('网络异常，请重试！');
                        return false;
                    }
                });
                $.ajax({
                    url:'/feapi/users/loginInfo?userToken=' + realToken,
                    type:'get',
                    dataType:'json',
                    success:function(data){
                        if(data.code == '200000'){
                            if(data.data.status.code == 200){
                                isLogin = 0;
                                //获取我的奖品列表
                                $('#rule').click(function(){
                                	currentPage = 1;
									myReward(currentPage);
								});
                                $('#btn1').click(function(){
                                	if(gameFlag == 0){
                                		lottery();
                                	}
                                });
                             	initStatus();
                            }
                        }else{
                            isLogin = 1;
                            $('#rule').click(function(){
                            	window.location.href = '/user/ilogin.html';
                            });
                            $('#btn1').click(function(){
                                window.location.href = '/user/ilogin.html';
                            });
                            $('#btn2').click(function(){
                                window.location.href = '/user/ilogin.html';
                            });
                            $('#btn4').click(function(){
                                window.location.href = '/user/ilogin.html';
                            });
                            $('#btn5').click(function(){
                                window.location.href = '/user/ilogin.html';
                            });
                        }
                    },
                    error:function(){
                        alert('网络异常，请重试！');
                        return false;
                    }
                });
            }
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

//蜡烛效果
setInterval(function(){
	$.each($('#candleList li'),function(){
		$(this).animate({'margin-left':'-0.5px'},500);
		$(this).animate({'margin-left':'-1px'},500);
		$(this).animate({'margin-left':'0px'},500);
		$(this).animate({'margin-left':'0.5px'},500);
		$(this).animate({'margin-left':'0px'},500);
	});
},3000);

//撒纸片
function anim(){
var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var Progress = function () {
  function Progress() {
    var param = arguments.length <= 0 || arguments[0] === undefined ? {} : arguments[0];

    _classCallCheck(this, Progress);

    this.timestamp = null;
    this.duration = param.duration || Progress.CONST.DURATION;
    this.progress = 0;
    this.delta = 0;
    this.progress = 0;
    this.isLoop = !!param.isLoop;

    this.reset();
  }

  Progress.prototype.reset = function reset() {
    this.timestamp = null;
  };

  Progress.prototype.start = function start(now) {
    this.timestamp = now;
  };

  Progress.prototype.tick = function tick(now) {
    if (this.timestamp) {
      this.delta = now - this.timestamp;
      this.progress = Math.min(this.delta / this.duration, 1);

      if (this.progress >= 1 && this.isLoop) {
        this.start(now);
      }

      return this.progress;
    } else {
      return 0;
    }
  };

  _createClass(Progress, null, [{
    key: "CONST",
    get: function get() {
      return {
        DURATION: 1000
      };
    }
  }]);

  return Progress;
}();

var Confetti = function () {
  function Confetti(param) {
    _classCallCheck(this, Confetti);

    this.parent = param.elm || document.body;
    this.canvas = document.createElement("canvas");
    this.ctx = this.canvas.getContext("2d");
    this.width = param.width || this.parent.offsetWidth;
    this.height = param.height || this.parent.offsetHeight;
    this.length = param.length || Confetti.CONST.PAPER_LENGTH;
    this.yRange = param.yRange || this.height * 2;
    this.progress = new Progress({
      duration: param.duration,
      isLoop: false
    });
    this.rotationRange = typeof param.rotationLength === "number" ? param.rotationRange : 10;
    this.speedRange = typeof param.speedRange === "number" ? param.speedRange : 10;
    this.sprites = [];

    this.canvas.style.cssText = ["display: block", "position: fixed", "top: 0", "left: 0", "pointer-events: none", "z-index: 100"].join(";");

    this.render = this.render.bind(this);

    this.build();

    this.parent.append(this.canvas);
    this.progress.start(performance.now());

    requestAnimationFrame(this.render);
  }

  Confetti.prototype.build = function build() {
    for (var i = 0; i < this.length; ++i) {
      var canvas = document.createElement("canvas"),
          ctx = canvas.getContext("2d");

      canvas.width = Confetti.CONST.SPRITE_WIDTH;
      canvas.height = Confetti.CONST.SPRITE_HEIGHT;

      canvas.position = {
        initX: Math.random() * this.width,
        initY: -canvas.height - Math.random() * this.yRange
      };

      canvas.rotation = this.rotationRange / 2 - Math.random() * this.rotationRange;
      canvas.speed = this.speedRange / 2 + Math.random() * (this.speedRange / 2);

      ctx.save();
      ctx.fillStyle = Confetti.CONST.COLORS[Math.random() * Confetti.CONST.COLORS.length | 0];
      ctx.fillRect(0, 0, canvas.width, canvas.height);
      ctx.restore();

      this.sprites.push(canvas);
    }
  };

  Confetti.prototype.render = function render(now) {
    var progress = this.progress.tick(now);

    this.canvas.width = this.width;
    this.canvas.height = this.height;

    for (var i = 0; i < this.length; ++i) {
      this.ctx.save();
      this.ctx.translate(this.sprites[i].position.initX + this.sprites[i].rotation * Confetti.CONST.ROTATION_RATE * progress, this.sprites[i].position.initY + progress * (this.height + this.yRange));
      this.ctx.rotate(this.sprites[i].rotation);
      this.ctx.drawImage(this.sprites[i], -Confetti.CONST.SPRITE_WIDTH * Math.abs(Math.sin(progress * Math.PI * 2 * this.sprites[i].speed)) / 2, -Confetti.CONST.SPRITE_HEIGHT / 2, Confetti.CONST.SPRITE_WIDTH * Math.abs(Math.sin(progress * Math.PI * 2 * this.sprites[i].speed)), Confetti.CONST.SPRITE_HEIGHT);
      this.ctx.restore();
    }

    requestAnimationFrame(this.render);
  };

  _createClass(Confetti, null, [{
    key: "CONST",
    get: function get() {
      return {
        SPRITE_WIDTH: 9,
        SPRITE_HEIGHT: 16,
        PAPER_LENGTH: 100,
        DURATION: 3000,
        ROTATION_RATE: 50,
        COLORS: ["#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5", "#29B6F6", "#26C6DA", "#26A69A", "#66BB6A", "#9CCC65", "#D4E157", "#FFEE58", "#FFCA28", "#FFA726", "#FF7043", "#8D6E63", "#BDBDBD", "#78909C"]
      };
    }
  }]);

  return Confetti;
}();
  var DURATION = 3000,
      LENGTH = 120;

  new Confetti({
    width: window.innerWidth,
    height: window.innerHeight + 30,
    length: LENGTH,
    duration: DURATION
  });
}
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
