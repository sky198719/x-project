/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;

/**
 * 描述
 *
 * @version $Id: MD5Utils.java 2016/9/7 14:54 $
 * @since jdk1.6
 */
public class MD5Utils {

    private static final Log log = LogFactory.getLog(MD5Utils.class);

    /**
     * 生成MD5签名
     */
    public static String MD5Encoder(String source, String charset) {
        StringBuilder result = new StringBuilder();
        try {
            byte[] sourceBytes = source.getBytes(charset);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(sourceBytes);
            byte[] digestBytes = messageDigest.digest();
            for (byte b : digestBytes) {
                int val = ((int) b) & 0xff;
                if (val < 16) {
                    result.append("0");
                }
                result.append(Integer.toHexString(val));
            }
        } catch (Exception e) {
            log.error(String.format("Convert.MD5Encoder() error source = %s, charset = %s", source, charset), e);
        }
        return result.toString();
    }


    public static void main(String [] args ) {
        System.out.println(MD5Encoder("111505"+"9c2a97e985e562762cabd87ea76a9c00","UTF-8"));
    }
}
