package com.xxdai.weixin.config;

import java.util.HashMap;
import java.util.Map;

import com.xxdai.weixin.constants.WeixinConstants;



public class WeixinConfig {
	
	private static Map<String,String> valueMap = new HashMap<String,String>();
	
	static{
		valueMap.put(WeixinConstants.TOKEN, "xinxindai");
	}
	
	public static String getValue(String key){
		return valueMap.get(key);
	}
}
