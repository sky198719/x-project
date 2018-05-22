/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/

package com.xxdai.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;

import com.xxdai.core.util.config.ConfigFactory;
import com.xxdai.core.util.lang.StringUtil;

/** 
 * webservice客户端类
 * @since jdk1.6
 * @version
 */
public  class ClientUtil {

	private static final Logger logger=Logger.getLogger(ClientUtil.class);

	/**
	 * 新新宝前端webservice
	 */
	public static final String xxbaoFrontWebservice="xxbaoFrontWebservice";
	
	/**
	 * 用户账户信息查询webservice
	 */
	public static final String accountQueryWebService="accountQueryWebService";
	//AccountQueryCXFService accountQueryCXFService = (AccountQueryCXFService) CXF_Factory.getFactory(AccountQueryCXFService.class,com.xxdai.util.Configuration.getInstance().getValue("trade_url")+"/accountQueryWebService").create();
	
	//投标webservice
	public static final String borrowTradeWebService="borrowTradeWebService";
	
	//我的礼劵webservice
	public static final String coupinCXFService="coupinCXFService";
	//我的新新推广
	public static final String popularizeCFXService="popularizeCFXService";
	//用户密码验证
	public static final String userWebService="userWebService"; 
	public static final String borrowQueryWebService="borrowQueryWebService";
	/**
	 * 从v6_webservice中取的对象
	 * @param url
	 * @param serviceClass
	 * @return
	 */
	public static Object getWebService(Class serviceClass,String serviceName){
		String url=getWebUrl("webService_url");
		if(StringUtil.isBlank(url)){
			logger.error("getwebUrl is null");
			return null;
		}
		String webUrl=url+"/"+serviceName;
		JaxWsProxyFactoryBean factory= CXF_Factory.getFactory(serviceClass, webUrl);
		Object service=factory.create();
		return service;
	}
	
	/**
	 * 获得交易平台的webservice对象
	 * @return
	 */
	public static Object getTradeWebService(Class serviceClass,String serviceName){
		String url=getWebUrl("trade_url");
		if(StringUtil.isBlank(url)){
			logger.error("getTradeWebService is null");
			return null;
		}
		String webUrl=url+"/"+serviceName;
		JaxWsProxyFactoryBean factory= CXF_Factory.getFactory(serviceClass, webUrl);
		Object service=factory.create();
		return service;
	}
	
	
	public static String getWebUrl(String url){
		return ConfigFactory.getConfig("validateConfig").getValue(url);
	}
	
}
