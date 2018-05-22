package com.xxdai.seo.model;

import com.xxdai.core.util.http.WSResponse;

public class SeoResponse extends WSResponse {
	
    private Object data;
	private int pageIndex;	

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
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
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    private int pageSize;

    private int totalSize;

    private int totalPages; //总页数

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
