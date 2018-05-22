/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.appro.bo;
import java.util.Date;

/**
 * XxdMobileAppro entity. @author MyEclipse Persistence Tools
 */
public class MobileAppro implements java.io.Serializable {

	private Long id;
	private Long userId;
	private String mobileNo;
	private String approCode;
	private String status;
	private Date approDate;
	private String approIp;
	private String approRemark;
	private Date createDate;
	private String createIp;
	private Date modifyDate;
	private Long lastModify;
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
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getApproCode() {
		return approCode;
	}
	public void setApproCode(String approCode) {
		this.approCode = approCode;
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