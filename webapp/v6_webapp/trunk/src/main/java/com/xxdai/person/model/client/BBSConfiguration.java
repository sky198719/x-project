package com.xxdai.person.model.client;

import java.io.InputStream;
import java.util.Properties;

public class BBSConfiguration {
	private static  Properties properties;
	static {
	    InputStream in = Client.class.getClassLoader().getResourceAsStream("bbs.properties");
	    properties = new Properties();
	    try {
			properties.load(in);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String get(String key){
		return properties.get(key).toString();
	}
}
