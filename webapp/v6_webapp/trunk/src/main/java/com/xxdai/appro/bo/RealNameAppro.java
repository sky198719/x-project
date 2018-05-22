/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.appro.bo;

import java.util.Date;

public class RealNameAppro implements java.io.Serializable {


     private Long id;
     private Long userId;
     private String idCardType;
     private String idCardNo;
     private String realName;
     private String pic_up;
     private String pic_down;
     private String status;
     private Date approDate;
     private Integer approUserId;
     private String approUserName;
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
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPic_up() {
		return pic_up;
	}
	public void setPic_up(String pic_up) {
		this.pic_up = pic_up;
	}
	public String getPic_down() {
		return pic_down;
	}
	public void setPic_down(String pic_down) {
		this.pic_down = pic_down;
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