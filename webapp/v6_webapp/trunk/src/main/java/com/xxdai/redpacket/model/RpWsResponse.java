/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.redpacket.model;

import com.xxdai.core.util.http.WSResponse;
/**
 * RpWsResponse
 *
 * @since jdk1.6
 * @version $Id
 */
public class RpWsResponse extends WSResponse{
private Object data;
	
	private int pageIndex; 
	
	private int pageSize; 
	
	private int totalSize;  
	
	private int totalPages; //总页数

	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public int getPageIndex() {
		if(pageIndex==0){
			pageIndex=1;
		}
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		if(pageSize==0){
			pageIndex=20;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalPages() {
		return this.totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	
	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	} 
}
