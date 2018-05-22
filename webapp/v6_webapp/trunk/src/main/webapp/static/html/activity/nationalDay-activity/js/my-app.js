
var myApp = new Framework7({
	modalTitle: '新新贷',
    pushState: true,
	cache: true,
	pushState:true,
	hideNavbarOnPageScroll: true,
});
// Export selectors engine
var $$ = Dom7;

// Add view
var mainView = myApp.addView('.view-main', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true
});

//快捷投标
$$('.express-bid').on('click', function () {
 myApp.modal({
    title:  '<h4>投标确认</h4>'+'<h5 class="mt10">投标金额：12499 元</h5>'+'<h5>红包抵扣： 50 元</h5>'+'<h5>实际支付： 23350 元</h5>',
    text: '<h5><a href="#" data-popup=".popup-agreement" class="open-popup">我同意《资金出借风险提示函》并确认投标</a></h5>'+
		  '<h5>'+
		  	'<input type="password" placeholder="请输入支付密码" class="modal_input">'+
          '</h5>',
    buttons: [
      {
        text: '取消',
        onClick: function() {
          myApp.close()
        }
      },
	  {
        text: '确认投标',
        onClick: function() {
          myApp.alert('投标成功!')
        }
      }
    ]
  })
});
//确认投标
myApp.onPageInit('quick-bid', function (page) {
    $$('.confirm-bid').on('click', function () {
		myApp.modalPassword('确认从您的账户扣除 10575 元用以投标，请输入支付密码','支付确认', function () {
		  myApp.alert('投标成功！','恭喜');
	  });
	});
});
//提现
myApp.onPageInit('drawmoney', function (page) {
    $$('.draw-money').on('click', function () {
		myApp.modalPassword('请输入支付密码','提现确认', function () {
		  myApp.alert('您的提现申请已提交 请耐心等待！','恭喜');
	  });
	});
	$$('.draw-money-alert').on('click', function () {
		myApp.alert('为保障您的资金安全，充值与提现的资金要求同卡进出，根据您的资金记录及资产情况，我们为您计算出了当前银行卡的最大可提现金额','温馨提示');
	});
});
//实名认证
myApp.onPageInit('id-certified', function (page) {
    $$('.id-certified-start').on('click', function () {
		myApp.confirm('张三 身份证号码 310101198309018908','请再次确认您的信息', function () {
		  myApp.alert('认证成功！','恭喜');
	  });
	});
});
//充值
myApp.onPageInit('topup-step2', function (page) {
    $$('.confirm-bid').on('click', function () {
		myApp.modal({
	    title:  '请选择充值结果',
	    text: '已为您打开新窗口并前往连连支付进行充值，请完成后选择操作结果；<br><br>温馨提示：如遇网络延迟，钱款未立即到账，请稍等片刻后再次刷新页面',
	    buttons: [
	      {
	        text: '充值遇到问题',
	        onClick: function() {
	          myApp.alert('type1')
	        }
	      },
	      {
	        text: '充值成功',
	        onClick: function() {
	          myApp.alert('type2')
	        }
	      },
		]
		})
	});
});
//我的收益
myApp.onPageInit('account', function (page) {
	var doughnutData = [
		{
			value: 124500.22,
			color:"#4ca2ff",
			highlight: "#8EC9F3",
			label: "待收本金"
		},
		{
			value: 24500.23,
			color: "#22d6cd",
			highlight: "#6ddbd5",
			label: "待收收益"
		},
		{
			value: 1400.14,
			color: "#ff9292",
			highlight: "#ffa8a8",
			label: "余额"
		},
		{
			value: 5400.15,
			color: "#fbad36",
			highlight: "#ffbf5e",
			label: "冻结"
		}
	];
	$$(document).on('pageInit', function () {
		var ctx = document.getElementById("chart-area").getContext("2d");
		window.myDoughnut = new Chart(ctx).Doughnut(doughnutData, {responsive : true,animateScale : true,animationSteps : 60,percentageInnerCutout : 50});
	  
	})
});
//新元宝
myApp.onPageInit('list-xyb', function (page) {
	var mySwiper = myApp.swiper('.xyb-swiper-container', {
		pagination:'.xyb-swiper-pagination',
		spaceBetween: 50 ,// 50px滑动间隙
		cube: {
		  slideShadows: true,
		  shadow: true,
		  shadowOffset: 20,
		  shadowScale: 0.94
		}
	  });
});
myApp.onPageInit('xyb-bid', function (page) {
	var pickerDevice = myApp.picker({
		input: '#picker-interest',
		cols: [
			{
				textAlign: 'left',
				values: ['收益再投标', '提至新新贷账户']
			}
		]
	});
});
/*新元宝投资记录*/
myApp.onPageInit('bid-history', function (page) {
	$$('.ac-3').on('click', function () {
	  var buttons1 = [
		  {
			  text: '操作',
			  label: true
		  },
		  {
			  text: '支付剩余金额',
			  bold: true,
			  bg: 'purple',
			  color: 'white'
		  },
		  {
			  text: '查看计划详情',
		  },
		  {
			  text: '《新新贷新元宝用户协议》',
		  }
	  ];
	  var buttons2 = [
		  {
			  text: '取消',
			  color: 'red'
		  }
	  ];
	  var groups = [buttons1, buttons2];
	  myApp.actions(groups);
  });
  $$('.ac-2').on('click', function () {
	  var buttons1 = [
		  {
			  text: '操作',
			  label: true
		  },
		  {
			  text: '查看计划详情',
		  },
		  {
			  text: '《新新贷新元宝用户协议》',
		  },
		  {
			  text: '提前退出',
			  onClick: function () {
				myApp.confirm('新元宝计划正在投资锁定期中，提前退出将会产生违约金 2000 元，提交后不可取消，确认要退出吗？','提前退出新元宝', function () {
					myApp.prompt('请输入支付密码', '确认提前退出新元宝' ,function () {
						myApp.alert('提交成功！','提前退出新元宝');
				    });
				});
			  }
		  }
	  ];
	  var buttons2 = [
		  {
			  text: '取消',
			  color: 'red'
		  }
	  ];
	  var groups = [buttons1, buttons2];
	  myApp.actions(groups);
  });
  $$('.trade_ac2').on('click', function () {
	  var buttons1 = [
		  {
			  text: '操作',
			  label: true
		  },
		  {
			  text: '转出此债权',
			  onClick: function () {
				mainView.router.loadPage('quick-trade.html');
			  }
		  },
		  {
			  text: '查看合同',
		  }
	  ];
	  var buttons2 = [
		  {
			  text: '取消',
			  color: 'red'
		  }
	  ];
	  var groups = [buttons1, buttons2];
	  myApp.actions(groups);
  });
});
//热门活动
myApp.onPageInit('new-activity', function (page) {
	$$('.act-ac-3').on('click', function () {
	  var buttons1 = [
		  {
			  text: '分享到朋友圈',
		  },
		  {
			  text: '分享到微博',
		  }
	  ];
	  var buttons2 = [
		  {
			  text: '取消',
			  color: 'red'
		  }
	  ];
	  var groups = [buttons1, buttons2];
	  myApp.actions(groups);
  });
});
//数据分析
myApp.onPageInit('data-analysis', function (page) {
	var lineChartData = {
		labels : ["2013Q4","2014Q1","2014Q2","2014Q3","2014Q4","2015Q1"],
		datasets : [
			{
				label: "季度成交额",
				fillColor : "rgba(32,150,233,0.2)",
				strokeColor : "rgba(32,150,233,1)",
				pointColor : "rgba(52,128,195,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(220,220,220,1)",
				data : [242.8,150.1,271.2,373.5,574.8,229.6]
			}
		]

	}
	$$(document).on('pageInit', function () {
		var ctx = document.getElementById("DataAnalysisA").getContext("2d");
		window.myLine = new Chart(ctx).Line(lineChartData, {
			responsive: true
		});
	  
	})
	var barChartData = {
		labels : ["2014-12","2015-01","2015-02","2015-03","2015-04","2014-12","2015-01","2015-02","2015-03","2015-04"],
		datasets : [
			{
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,0.8)",
				highlightFill : "rgba(151,187,205,0.75)",
				highlightStroke : "rgba(151,187,205,1)",
				data : [30.3,31.4,32.0,32.6,33.2,30.3,31.4,32.0,32.6,33.2]
			}
		]

	}
	$$(document).on('pageInit', function () {
		var ctx = document.getElementById("DataAnalysisB").getContext("2d");
		window.myBar = new Chart(ctx).Bar(barChartData, {
			responsive : true
		});
	})
});