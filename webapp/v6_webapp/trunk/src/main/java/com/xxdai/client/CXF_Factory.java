package com.xxdai.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

/**
 * CXF_Factory类
 * @description 获取CXF工厂
 * 
 * @author Rocky.J
 * @email rockyjiang.r.j.1987@gmail.com
 * 
 * @date 2013-8-27 下午4:14:55
 */
@SuppressWarnings("unchecked")
public class CXF_Factory {
	@SuppressWarnings("rawtypes")
	public static JaxWsProxyFactoryBean getFactory(Class classify, String url) {
		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, "admin");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		// 指定在调用远程ws之前触发的回调函数WsClinetAuthHandler，其实类似于一个拦截器
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientValidate.class.getName());
		ArrayList list = new ArrayList();
		// 添加cxf安全验证拦截器
		list.add(new SAAJOutInterceptor());
		list.add(new WSS4JOutInterceptor(outProps));

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		// WebServiceSample服务端接口实现类，这里并不是直接把服务端的类copy过来
		factory.setServiceClass(classify);
		// 设置ws访问地址
		factory.setAddress(url);
		factory.getOutInterceptors().addAll(list);
		return factory;
	}
}
