/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.appro.bo;


import java.util.Date;

public class VIPAppro  implements java.io.Serializable {


     private Long id;
     private Long userId;
     private String status;
     private Date approDate;
     private Integer approUserId;
     private String approUserName;
     private String approIp;
     private String approRemark;
     private String serviceNum;
     private Date createDate;
     private String createIp;
     private Date modifyDate;
     private Long lastModify;
     private String isfee;
     private Date indate;
     
	public String getIsfee() {
		return isfee;
	}
	public void setIsfee(String isfee) {
		this.isfee = isfee;
	}
	public Date getIndate() {
		return indate;
	}
	public void setIndate(Date indate) {
		this.indate = indate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getApproDate() {
		return approDate;
	}
	public void setApproDate(Date approDate) {
		this.approDate = approDate;
	}
	public Integer getApproUserId() {
		return approUserId;
	}
	public void setApproUserId(Integer approUserId) {
		this.approUserId = approUserId;
	}
	public String getApproUserName() {
		return approUserName;
	}
	public void setApproUserName(String approUserName) {
		this.approUserName = approUserName;
	}
	public String getApproIp() {
		return approIp;
	}
	public void setApproIp(String approIp) {
		this.approIp = approIp;
	}
	public String getApproRemark() {
		return approRemark;
	}
	public void setApproRemark(String approRemark) {
		this.approRemark = approRemark;
	}
	public String getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateIp() {
		return createIp;
	}
	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Long getLastModify() {
		return lastModify;
	}
	public void setLastModify(Long lastModify) {
		this.lastModify = lastModify;
	}
}