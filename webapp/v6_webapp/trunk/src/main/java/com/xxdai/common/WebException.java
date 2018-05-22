/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.common;

/**
 * 描述
 *
 * @version $Id: WebException.java 2015/9/21 20:48 $
 * @since jdk1.6
 */
public class WebException extends Exception {
    private int errorCode;
    public WebException(int errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
