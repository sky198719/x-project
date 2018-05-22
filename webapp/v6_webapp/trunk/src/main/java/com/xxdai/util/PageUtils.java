/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.util;

import java.util.List;

/** 
* hibernate分页工具类
* 
* @since jdk1.6
* @version $Id: PageUtils.java 9464 2014-11-07 12:23:02Z xiaoying $
*/
public final class PageUtils<T> implements java.io.Serializable{

	private int pageIndex; //当前记录数
	
	private int totalPages; //总页数
	
	private int currentPage;  //当前页数
	
	private int pageSize; //每页记录数
	
	private List<T> resultList; //返回查询结果
	
	/*private List<T> needList;  //处理过的结果*/
	
	
	private int totalSize;  //总记录数 用到的时候自己设置
	
	public int getPageIndex() {
		if(currentPage<=0){
			currentPage=1;
		}
		pageIndex=(currentPage-1)*pageSize;
		pageIndex=pageIndex+1;
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		if(pageSize<=0){
			pageIndex=20;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
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

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	/*public List<T> getNeedList() {
		return needList;
	}

	public void setNeedList(List<T> needList) {
		this.needList = needList;
	}*/
}
