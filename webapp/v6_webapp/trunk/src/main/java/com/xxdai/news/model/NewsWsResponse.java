/** 
* Copyright (c) 2014, www.xxdai.com All Rights Reserved. 
*/
package com.xxdai.news.model;

import com.xxdai.core.util.http.WSResponse;
/** 
* NewsWsResponse
* 
* @since jdk1.6
* @version $Id: NewsWsResponse.java 9464 2014-11-07 12:23:02Z xiaoying $
*/
public class NewsWsResponse extends WSResponse{
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

	
	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	} 
	
	public int getTotalPages() {
		if(totalSize>0 && pageSize>0){
			if(totalSize%pageSize==0){
				this.totalPages=totalSize/pageSize;
			}else{
				this.totalPages=totalSize/pageSize+1;
			}
		}
		return this.totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}
