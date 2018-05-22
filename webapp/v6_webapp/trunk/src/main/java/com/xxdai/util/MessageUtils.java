package com.xxdai.util;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class MessageUtils {
	/**
	 * 日志记录器
	 */
	Logger logger = LoggerFactory.getLogger(MessageUtils.class);
	
	//发送方式
	public static int SENDTYPE_SMS = 1;  	//短信
	public static int SENDTYPE_VOICE = 2;	//语音  
	//验证码发送次数限制
    public static int SENDLIMIT_SMS_TIMES_1HOUR = 3;  		 //短信验证码1小时次数
    public static int SENDLIMIT_SMS_TIMES_24HOUR = 5; 		 //短信验证码24小时次数
    public static int SENDLIMIT_VOICE_TIMES_1HOUR = 1441;	 //语音验证码1小时次数(暂无限制)
    public static int SENDLIMIT_VOICE_TIMES_24HOUR = 30;      //语音验证码24小时次数
    
    
	/**
	 * 校验手机验证码短信发送次数
	 * 
	 * @param functionName
	 * @param request
	 */
	public static boolean checkSendSMSCount(String functionName,
			HttpServletRequest request, HttpServletResponse response) {
		boolean result = true;
		// 1小时内最大发送次数
		int MAX_COUNT_1H = 3;
		// 24小时内最大发送次数
		int MAX_COUNT_24H = 10;

		JSONArray jsonArray = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(functionName)) {
				jsonArray = JSONArray.parseArray(cookie.getValue());
                break;
			}
		}
		Long curTime = new Date().getTime();
		if (jsonArray == null) {
			jsonArray = new JSONArray();
		} else {
			int count_1h = 0;
			int count_24h = 0;
			for (int i = 0; i < jsonArray.size(); i++) {
				Long d = jsonArray.getLong(i);
				if (curTime - d < 60 * 60 * 1000) {
					count_1h++;
					count_24h++;
				} else if (curTime - d < 24 * 60 * 60 * 1000) {
					count_24h++;
				}
				if (count_1h >= MAX_COUNT_1H) {
					return false;
				}
				if (count_24h >= MAX_COUNT_24H) {
					return false;
				}
			}
		}

		jsonArray.add(curTime);
		Cookie cookie = new Cookie(functionName, jsonArray.toJSONString()); // 新建Cookie，保存本功能短信发送时间
		cookie.setMaxAge(24 * 60 * 60); // 浏览器cookie失效时间24h
		response.addCookie(cookie);
		return result;
	}
	
	/**
	 * 校验手机验证码发送次数
	 * 
	 * @param functionName
	 * @param request
	 */
	public static int checkVerifyCodeTimeLimit(String functionName,int sendType,
			HttpServletRequest request, HttpServletResponse response) {
		int result = 0; //0:正常发送，1：超过1小时内发送次数限制，2：超过24小时内发送限制
		// 1小时内最大发送次数
		int MAX_COUNT_1H = 0;
		// 24小时内最大发送次数
		int MAX_COUNT_24H = 0;
		
		if (SENDTYPE_SMS == sendType) {
			MAX_COUNT_1H = SENDLIMIT_SMS_TIMES_1HOUR;
			MAX_COUNT_24H = SENDLIMIT_SMS_TIMES_24HOUR;
		}else{
			MAX_COUNT_1H = SENDLIMIT_VOICE_TIMES_1HOUR;
			MAX_COUNT_24H = SENDLIMIT_VOICE_TIMES_24HOUR;
		}
		
		JSONArray jsonArray = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(functionName)) {
				jsonArray = JSONArray.parseArray(cookie.getValue());
				break;
			}
		}
		Long curTime = new Date().getTime();
		if (jsonArray == null) {
			jsonArray = new JSONArray();
		} else {
			int count_1h = 0;
			int count_24h = 0;
			for (int i = 0; i < jsonArray.size(); i++) {
				Long d = jsonArray.getLong(i);
				if (curTime - d < 60 * 60 * 1000) {
					count_1h++;
					count_24h++;
				} else if (curTime - d < 24 * 60 * 60 * 1000) {
					count_24h++;
				}
				if (count_1h >= MAX_COUNT_1H) {
					return 1;
				}
				if (count_24h >= MAX_COUNT_24H) {
					return 2;
				}
			}
		}
		
		jsonArray.add(curTime);
		Cookie cookie = new Cookie(functionName, jsonArray.toJSONString()); // 新建Cookie，保存本功能短信发送时间
		cookie.setMaxAge(24 * 60 * 60); // 浏览器cookie失效时间24h
		response.addCookie(cookie);
		return result;
	}
}
