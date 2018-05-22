/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.common;

import com.xxdai.core.util.http.WSResponse;

/**
 * 描述
 *
 * @version $Id: DataResponse.java 2015/2/8 2:12 $
 * @since jdk1.6
 */
public class DataResponse extends WSResponse {

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
