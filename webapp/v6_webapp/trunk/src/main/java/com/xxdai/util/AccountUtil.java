/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.util;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.common.DataResponse;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.user.ws.UserCXFService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class AccountUtil {
	
    static Logger log = LoggerFactory.getLogger(AccountUtil.class);

    //用户操作接口
    static UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();
    
    public static String checkPayPwd(HttpServletRequest request, long userId, String payPwd) {
		JSONObject resultJson = new JSONObject();
		try{
			if(StringUtil.isBlank(payPwd)){
				resultJson.put("resultCode", "-1");
				resultJson.put("desc", "支付密码为空！");
				return resultJson.toJSONString();
			}
			String browser = request.getHeader("User-Agent");
			browser = browser.length() > 200 ? browser.substring(0,200):browser;
			String authPayPwd=EscapeCode.Encryption(payPwd);
			JSONObject authPwd=new JSONObject();
			authPwd.put("userId", userId);
			authPwd.put("password", authPayPwd);
			authPwd.put("ip", HttpUtil.getRealIpAddr(request));
			authPwd.put("browser", browser);
			log.info("AccountUtil checkPayPassword ----> params:" + authPwd.toJSONString());
			String authStr= userCXFService.checkPayPassword(authPwd.toJSONString());
			log.info("AccountUtil checkPayPassword ----> return:" + authStr);
			DataResponse userResponse = JsonUtil.jsonToBean(authStr, DataResponse.class);
			int resultCode = 0;
	        String desc = "支付密码正确";
	        if (userResponse != null) {
	            switch (userResponse.getResultCode()) {
	                case -1:
	                    resultCode = -30;
	                    desc = "获取支付密码异常！";
	                    break;
	                case -2:
	                    resultCode = -31;
	                    desc = "支付密码错误，请重新输入！";
	                    break;
	                case 220:
	                    resultCode = -32;
	                    desc = "您还未设置支付密码，请设置！";
	                    break;
	                case 230:
	                    resultCode = -33;
	                    desc = "支付密码与登录密码一致！";
	                    break;
	                default:
	            }
	        }
	    	resultJson.put("resultCode", resultCode);
			resultJson.put("desc", desc);
	    }catch(Exception e){
			log.error("AccountUtil checkPayPassword ----> arise exception:" , e);
			//返回页面
			resultJson.put("resultCode", -1);
			resultJson.put("desc", "操作失败，请稍后重试...");
		}
		log.info("AccountUtil checkPayPassword ----> return to page:" + resultJson.toJSONString());
		return resultJson.toJSONString();
	}
	
}
