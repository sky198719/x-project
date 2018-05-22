package com.xxdai.weixin.util;

import java.util.ResourceBundle;

public class WeixinConfiguration {
	private static Object lock              = new Object();
	private static WeixinConfiguration config     = null;
	private static ResourceBundle rb        = null;
	private static final String CONFIG_FILE = "weixinConfig";
	
	private WeixinConfiguration() {
		rb = ResourceBundle.getBundle(CONFIG_FILE);
	}
	
	public static WeixinConfiguration getInstance() {
		synchronized(lock) {
			if(null == config) {
				config = new WeixinConfiguration();
			}
		}
		return (config);
	}
	
	public String getValue(String key) {
		try{
			return (rb.getString(key));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "0";
	}
	
	public String getValue(String key,String defaults){
		try{
			return rb.getString(key) == null || "".equals(rb.getString(key)) ? defaults : rb.getString(key); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return defaults;		
	}
}
