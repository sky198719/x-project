/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.account.model;

import com.xxdai.core.util.http.WSResponse;

/** 
 *Market WebService返回类
 *
 * @since jdk1.6
 * @version $Id: MarketWsResponse.java 9464 2014-11-07 12:23:02Z xiaoying $
 */
public class MarketWsResponse extends WSResponse {
	private Object data;
	
	private Integer totalCount;
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
