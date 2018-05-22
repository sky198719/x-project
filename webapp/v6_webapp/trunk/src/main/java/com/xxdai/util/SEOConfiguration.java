package com.xxdai.util;

import java.util.ResourceBundle;

 
/**
 * @author rocky.J
 *
 */
public class SEOConfiguration {
	

	private static Object lock              = new Object();
	private static SEOConfiguration config     = null;
	private static ResourceBundle rb        = null;
	private static final String CONFIG_FILE = "SEOdefault";
	
	private SEOConfiguration() {
		rb = ResourceBundle.getBundle(CONFIG_FILE);
	}
	
	public static SEOConfiguration getInstance() {
		synchronized(lock) {
			if(null == config) {
				config = new SEOConfiguration();
			}
		}
		return (config);
	}
	
	public String getValue(String key) {
		return (rb.getString(key));
	}
}
