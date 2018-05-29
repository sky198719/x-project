try{
	/**
	 * 加载初始化google Ga
	*/
	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	})(window,document,'script','//www.google-analytics.com/analytics.js','ga');



	/**
	 * 加载初始化GTM
	 */
	(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
	new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
	j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
	'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
	})(window,document,'script','dataLayer','GTM-PRZHDF');
}catch(e){}


/**
 * GrowingIO初始化
 */
function growingIOInits(param){
	var json = {
		userId: param.userId || ""
	};
	try{
		var _vds = _vds || [];
		window._vds = _vds;
		(function(){
			_vds.push(['setAccountId', '900b0a2db8f09121']);
			_vds.push(['enableHT', true]);

			/*********配置用户属性************/
			if (param.userId != null && param.userId != "" && typeof(param.userId) != 'undefined') {
				_vds.push(['setCS1', '用户id', param.userId]);
			}
			/*********配置用户属性************/

			/*********配置页面属性************/
			 // _vds.push(['setPageGroup', 'ProductDetailsPage']);
			 // if(param.describe!=null && param.describe !="" && typeof(param.describe)!='undefined'){
			 // 	_vds.push(['setPS1', param.describe]);
			 // }

			/*********配置页面属性************/
			(function() {
				var vds = document.createElement('script');
				vds.type='text/javascript';
				vds.async = true;
				vds.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'dn-growing.qbox.me/vds.js';
				var s = document.getElementsByTagName('script')[0];
				s.parentNode.insertBefore(vds, s);
			})();
		})();
	}catch(e){}
}

/**
 * GA基础信息部署
 */
function gaInits(userId){
	try{
		var GAuserId = "";
		if(userId !=null && userId !=""){
			GAuserId =userId;
		}else{
			GAuserId = "";
		}

		ga('create', 'UA-55539630-1', 'auto',{'userId': GAuserId});
		//ga('require', 'linkid', 'linkid.js');
		ga('require', 'displayfeatures');
		ga('require', 'ec');
		ga('set', 'dimension2', GAuserId);//用户唯一身份识别ID
		ga("set", "dimension3", "wap")
		ga('set', 'dimension4', document.referrer);
		ga(function(tracker) {
		  ga('set', 'dimension6', tracker.get('clientId'));
		  });
		ga('set', 'transport', 'beacon');
		//虚拟页面
		ga('send', 'pageview',window.location.pathname+window.location.hash);
	}catch(e){}
}

/**
 * GA事件方法
 * @param param
 */
function gaClickEvent_UserId(param){
	var json = {
			property1: param.property1 || "",
			property2: param.property2 || "",
			property3: param.property3 || ""
	  };
	ga('set', 'dimension1', "注册用户");
	ga('send', 'event',json.property1, json.property2, json.property3);
}


/**
 * GA事件方法
 * @param param
 */
function gaClickEvent(param){
		var json = {
				property1: param.property1 || "",
				property2: param.property2 || "",
				property3: param.property3 || ""
		  };
		ga('send', 'event',json.property1, json.property2, json.property3);
}


/**
 * 站内广告banner单击事件
 */
function promo_click(fileName){
		if(fileName == "undefined" || fileName == null ){
			fileName ="";
		}
		ga('ec:addPromo', {
		  'name': fileName //站内广告名称，用来识别一个站内广告，如“_移动端专享9%”
		});

		ga('ec:setAction', 'promo_click');
		ga('send','event','增强电商','站内广告点击',fileName)
}


/**
 * GA产品列表点击 立即抢购或者购买按钮
 * @param param
 */
function product_click(param){
	var json = {
			id: param.id || "",
			name: param.name || "",
			category: param.category || "",
			list: param.list || ""
	  };

	ga('ec:addProduct', {
	  'id':json.id,                        // 标的借款编号
	  'name':json.name,
	/*
	为了区分唯一的产品，产品name参数设置示例如下：
	1、_：_
	2、七天大胜：七天大胜
	3、_：产品名称+日期，如_-201604029期
	4、_：产品名称+日期，如_年年盈 - 2016/4/29，_双季盈 - 2016/4/29，_季季盈 - 2016/4/29
	5、散标直投：散标直投+标号，如散标直投BW201604295010
	6、债权转让：债权转让+编号，如债权转让BT2015102988782
	7、票小宝：票小宝+名称，如票小宝_商业承兑汇票第376期
	*/
	  'category': json.category
	/*
	为了区分产品的不同类别，产品category参数设置示例如下：
	1、_：_/产品年化收益/锁定期限，如_/8%/1天
	2、七天大胜：七天大胜/产品年化收益/锁定期限，如七天大胜/13%/7天
	3、_：_/产品年化收益/锁定期限，如_/15%/31天
	4、_：_/产品年化收益/锁定期限，如_/11%/12个月
	5、散标直投：散标直投/产品年化收益/锁定期限/类型，如散标直投/8%/3个月/车主贷
	6、债权转让：债权转让/产品年化收益/锁定期限/类型，如债权转让/12.24 %/6个月/新商贷
	7、票小宝：票小宝/产品年化收益/锁定期限/，如票小宝/7%/26天
	*/
	});
	ga('ec:setAction', 'click', {
	  'list': json.list          //产品所在列表：首页小试牛刀、首页大显身手，首页生财有道，首页散标直投
	});
	ga('send','event',"增强电商","产品点击",json.name)
}


/**
 * 产品详情浏览
 */
function product_detail(param){
	var json = {
			id: param.id || "",
			name: param.name || "",
			category: param.category || ""
	  };

	ga('ec:addProduct', {
	  'id':json.id,                        // 标的借款编号
	  'name':json.name,
	/*
	为了区分唯一的产品，产品name参数设置示例如下：
	1、_：_
	2、七天大胜：七天大胜
	3、_：产品名称+日期，如_-201604029期
	4、_：产品名称+日期，如_年年盈 - 2016/4/29，_双季盈 - 2016/4/29，_季季盈 - 2016/4/29
	5、散标直投：散标直投+标号，如散标直投BW201604295010
	6、债权转让：债权转让+编号，如债权转让BT2015102988782
	7、票小宝：票小宝+名称，如票小宝_商业承兑汇票第376期
	*/
	  'category': json.category
	/*
	为了区分产品的不同类别，产品category参数设置示例如下：
	1、_：_/产品年化收益/锁定期限，如_/8%/1天
	2、七天大胜：七天大胜/产品年化收益/锁定期限，如七天大胜/13%/7天
	3、_：_/产品年化收益/锁定期限，如_/15%/31天
	4、_：_/产品年化收益/锁定期限，如_/11%/12个月
	5、散标直投：散标直投/产品年化收益/锁定期限/类型，如散标直投/8%/3个月/车主贷
	6、债权转让：债权转让/产品年化收益/锁定期限/类型，如债权转让/12.24 %/6个月/新商贷
	7、票小宝：票小宝/产品年化收益/锁定期限/，如票小宝/7%/26天
	*/
	});
	ga('ec:setAction', 'detail');
	ga('send','event',"增强电商","产品详情浏览",json.name)
}


/**
 * 点击各类产品加入购物车
 */
function add_to_cart(param){
	var json = {
			id: param.id || "",
			name: param.name || "",
			category: param.category || ""
	  };

	ga('ec:addProduct', {
	  'id':json.id,                        // 标的借款编号
	  'name':json.name,
	/*
	为了区分唯一的产品，产品name参数设置示例如下：
	1、_：_
	2、七天大胜：七天大胜
	3、_：产品名称+日期，如_-201604029期
	4、_：产品名称+日期，如_年年盈 - 2016/4/29，_双季盈 - 2016/4/29，_季季盈 - 2016/4/29
	5、散标直投：散标直投+标号，如散标直投BW201604295010
	6、债权转让：债权转让+编号，如债权转让BT2015102988782
	7、票小宝：票小宝+名称，如票小宝_商业承兑汇票第376期
	*/
	  'category': json.category,
	/*
	为了区分产品的不同类别，产品category参数设置示例如下：
	1、_：_/产品年化收益/锁定期限，如_/8%/1天
	2、七天大胜：七天大胜/产品年化收益/锁定期限，如七天大胜/13%/7天
	3、_：_/产品年化收益/锁定期限，如_/15%/31天
	4、_：_/产品年化收益/锁定期限，如_/11%/12个月
	5、散标直投：散标直投/产品年化收益/锁定期限/类型，如散标直投/8%/3个月/车主贷
	6、债权转让：债权转让/产品年化收益/锁定期限/类型，如债权转让/12.24 %/6个月/新商贷
	7、票小宝：票小宝/产品年化收益/锁定期限/，如票小宝/7%/26天
	*/
	 'quantity': 1   //产品数量，固定值1
	});
	ga('ec:setAction', 'add');
	ga('send','event',"增强电商","产品添加",json.name)
}



/**
 * 进入结账流程
 */
function CheckOut(param){
	var json = {
			id: param.id || "",
			name: param.name || "",
			category: param.category || ""
	  };
	ga('ec:addProduct', {
	  'id':json.id,                        // 标的借款编号
	  'name':json.name,
	/*
	为了区分唯一的产品，产品name参数设置示例如下：
	1、_：_
	2、七天大胜：七天大胜
	3、_：产品名称+日期，如_-201604029期
	4、_：产品名称+日期，如_年年盈 - 2016/4/29，_双季盈 - 2016/4/29，_季季盈 - 2016/4/29
	5、散标直投：散标直投+标号，如散标直投BW201604295010
	6、债权转让：债权转让+编号，如债权转让BT2015102988782
	7、票小宝：票小宝+名称，如票小宝_商业承兑汇票第376期
	*/
	  'category': json.category,
	/*
	为了区分产品的不同类别，产品category参数设置示例如下：
	1、_：_/产品年化收益/锁定期限，如_/8%/1天
	2、七天大胜：七天大胜/产品年化收益/锁定期限，如七天大胜/13%/7天
	3、_：_/产品年化收益/锁定期限，如_/15%/31天
	4、_：_/产品年化收益/锁定期限，如_/11%/12个月
	5、散标直投：散标直投/产品年化收益/锁定期限/类型，如散标直投/8%/3个月/车主贷
	6、债权转让：债权转让/产品年化收益/锁定期限/类型，如债权转让/12.24 %/6个月/新商贷
	7、票小宝：票小宝/产品年化收益/锁定期限/，如票小宝/7%/26天
	*/
	 'quantity': 1   //产品数量，固定值1
	});
	ga('ec:setAction', 'checkout', {'step':1});
	ga('send','event',"增强电商","结账流程",json.name)
}




/**
 * 交易成功
 * @param param
 */
function transaction(param){
	var json = {
			id: param.id || "",
			name: param.name || "",
			category: param.category || "",
			price: param.price || "",
			tradeId: param.tradeId || "",
			revenue: param.revenue || "",
			coupon: param.coupon || "",
			userId: param.userId || ""
	  };

	ga('ec:addProduct', {
	  'id':json.id,                        // 标的借款编号
	  'name':json.name,
	/*
	为了区分唯一的产品，产品name参数设置示例如下：
	1、_：_
	2、七天大胜：七天大胜
	3、_：产品名称+日期，如_-201604029期
	4、_：产品名称+日期，如_年年盈 - 2016/4/29，_双季盈 - 2016/4/29，_季季盈 - 2016/4/29
	5、散标直投：散标直投+标号，如散标直投BW201604295010
	6、债权转让：债权转让+编号，如债权转让BT2015102988782
	7、票小宝：票小宝+名称，如票小宝_商业承兑汇票第376期
	*/
	  'category': json.category,
	/*
	为了区分产品的不同类别，产品category参数设置示例如下：
	1、_：_/产品年化收益/锁定期限，如_/8%/1天
	2、七天大胜：七天大胜/产品年化收益/锁定期限，如七天大胜/13%/7天
	3、_：_/产品年化收益/锁定期限，如_/15%/31天
	4、_：_/产品年化收益/锁定期限，如_/11%/12个月
	5、散标直投：散标直投/产品年化收益/锁定期限/类型，如散标直投/8%/3个月/车主贷
	6、债权转让：债权转让/产品年化收益/锁定期限/类型，如债权转让/12.24 %/6个月/新商贷
	7、票小宝：票小宝/产品年化收益/锁定期限/，如票小宝/7%/26天
	*/
	 'price': json.price,   //投资的产品金额
	 'quantity': 1   //产品数量，固定值1
	});
	ga('ec:setAction', 'purchase', {          // Transaction details are provided in an actionFieldObject.
	  'id': json.tradeId,                                    //交易ID
	  'affiliation':"",                 // nonFD 非 FD, beFD 是 FD
	  'revenue':json.revenue,                         // 总投资金额，产品投资金额减去红包金额
	//  'tax':"",                                  // 空值
	 // 'shipping':"",                     // 空值
	  'coupon': json.coupon                           // 红包名称，红包100元，红包200元
	});
	ga('set', 'dimension8', "投资成功用户");
	ga('send','event',"增强电商","投资成功",json.name)
	//ga('set', 'dimension9', json.userId);
	//ga('send', 'event'," 已投资", "投资成功", "投资成功");
}
/**
 * 赎回成功
 * @param param
 */
function redemption(param){
	var json = {
		id: param.id || "",
		name: param.name || "",
		category: param.category || "",
		price: param.price || "",
		tradeId: param.tradeId || "",
		affiliation: param.affiliation || ""
	};

	ga('ec:addProduct', {
		'id':json.id,                        // 标的借款编号
		'name':json.name,
		'category': json.category,
		'price': json.price,   //赎回的产品金额
		'quantity': 1   //产品数量，固定值1
	});
	ga('ec:setAction', 'purchase', {          // Transaction details are provided in an actionFieldObject.
		'id': json.tradeId,                                    //交易ID
		'affiliation':json.affiliation                 // nonFD 非 FD, beFD 是 FD
		//'revenue':json.revenue,                         // 收入减去优惠券
		//  'tax':"",                                  // 空值
		// 'shipping':"",                     // 空值
		//'coupon': json.coupon                           // 红包名称，红包100元，红包200元
	});
	ga('set', 'dimension9', "赎回成功用户");
	ga('send','event',"增强电商","赎回成功",json.name)
}

/**
 * User MD5加密
 * @param userId
 * @returns
 */
function Md5UserId(userId){
	if(userId !=null && userId !=""){
		userId = userId+"xxd2016";
		userId =$.md5(userId);
	}else{
		userId = "";
	}
	return userId;
}
