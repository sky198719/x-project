/** 
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 */
package com.xxdai.person.model;

import com.xxdai.core.util.http.WSResponse;

/** 
 * 个人资料Web Service返回包装类
 *
 * @since jdk1.6
 * @version $Id: PersonResponse.java 9464 2014-11-07 12:23:02Z xiaoying $
 */
public class PersonResponse extends WSResponse {
 
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

}
