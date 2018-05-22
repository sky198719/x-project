/** 
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 */
package com.xxdai.news.bo;

import java.util.Date;

/**
 * Ppt
 * 
 * @since jdk1.6
 * @version $Id: NewsPpt.java 9464 2014-11-07 12:23:02Z xiaoying $
 * 
 */

public class NewsPpt  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
     private String pptUrl;
     private String url;
     private int isDisplay;
     private int disOrder;
     private int position;
     private String addPerson;
     private Date addTime;
     private int category;
     private String keepword1;
     private String keepword2;
     private String keepword3;
     private String fileName;


    public NewsPpt() {
    }

    
    public NewsPpt(String pptUrl, String url, int isDisplay, int disOrder, int position, String addPerson, Date addTime, int category, String keepword1, String keepword2, String keepword3,String fileName) {
        this.pptUrl = pptUrl;
        this.url = url;
        this.isDisplay = isDisplay;
        this.disOrder = disOrder;
        this.position = position;
        this.addPerson = addPerson;
        this.addTime = addTime;
        this.category = category;
        this.keepword1 = keepword1;
        this.keepword2 = keepword2;
        this.keepword3 = keepword3;
        this.fileName = fileName;
    }

   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPptUrl() {
        return this.pptUrl;
    }
    
    public void setPptUrl(String pptUrl) {
        this.pptUrl = pptUrl;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public int getIsDisplay() {
        return this.isDisplay;
    }
    
    public void setIsDisplay(int isDisplay) {
        this.isDisplay = isDisplay;
    }
    
    public int getDisOrder() {
        return this.disOrder;
    }
    
    public void setDisOrder(int disOrder) {
        this.disOrder = disOrder;
    }
    
    public int getPosition() {
        return this.position;
    }
    
    public void setPosition(int position) {
        this.position = position;
    }
    
    public String getAddPerson() {
        return this.addPerson;
    }
    
    public void setAddPerson(String addPerson) {
        this.addPerson = addPerson;
    }
    
    public Date getAddTime() {
        return this.addTime;
    }
    
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    
    public int getCategory() {
        return this.category;
    }
    
    public void setCategory(int category) {
        this.category = category;
    }
    
    public String getKeepword1() {
        return this.keepword1;
    }
    
    public void setKeepword1(String keepword1) {
        this.keepword1 = keepword1;
    }
    
    public String getKeepword2() {
        return this.keepword2;
    }
    
    public void setKeepword2(String keepword2) {
        this.keepword2 = keepword2;
    }
    
    public String getKeepword3() {
        return this.keepword3;
    }
    
    public void setKeepword3(String keepword3) {
        this.keepword3 = keepword3;
    }


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}