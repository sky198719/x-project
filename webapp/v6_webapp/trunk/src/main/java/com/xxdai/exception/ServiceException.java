/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.exception;

/**
 * 业务错误
 *
 * @version $Id: ServiceException.java 9464 2014-11-07 12:23:02Z xiaoying $
 * @since jdk1.6
 */
public class ServiceException extends AppFrameException {

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }


    public ServiceException(String msg, int errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public ServiceException(String msg, int errorCode, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

}
