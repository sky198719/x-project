package com.xxdai.lianlian.wap.util;

import java.util.ResourceBundle;

public class YinTongConfigUtil {

    private static Object lock = new Object();
    private static YinTongConfigUtil config = null;
    private static ResourceBundle rb = null;
    private static final String CONFIG_FILE = "lianPayConfig";

    private YinTongConfigUtil() {
        rb = ResourceBundle.getBundle(CONFIG_FILE);
    }

    public static YinTongConfigUtil getInstance() {
        synchronized (lock) {
            if (null == config) {
                config = new YinTongConfigUtil();
            }
        }
        return (config);
    }

    public String getValue(String key) {
        return (rb.getString(key));
    }

}
