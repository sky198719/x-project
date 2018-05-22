package com.xxdai.util;

import java.util.ResourceBundle;

 
/**
 * @author rocky.J
 *
 */
public class YeepayConfiguration {
	

	private static Object lock              = new Object();
	private static YeepayConfiguration config     = null;
	private static ResourceBundle rb        = null;
	private static final String CONFIG_FILE = "merchantInfo";
	
	private YeepayConfiguration() {
		rb = ResourceBundle.getBundle(CONFIG_FILE);
	}
	
	public static YeepayConfiguration getInstance() {
		synchronized(lock) {
			if(null == config) {
				config = new YeepayConfiguration();
			}
		}
		return (config);
	}
	
	public String getValue(String key) {
		return (rb.getString(key));
	}
}
