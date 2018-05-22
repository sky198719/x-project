/** 
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 */
package com.xxdai.popularize.bo;

import com.xxdai.core.util.http.WSResponse;

/**
 * @author zhangbaobao
 * @since jdk1.6 date: 2014年10月26日
 */
public class PopularizeWsresponse extends WSResponse {
	private Object data;

	// 总记录数
	private Integer totalCount;

	// 总页数
	private Integer totalPage;

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

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

}
