/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.appro.bo;

import java.util.Date;

/**
 *邮箱认证类 
 * @since jdk1.6
 * @version $Id: EmailAppro.java 9464 2014-11-07 12:23:02Z xiaoying $
 */

public class EmailAppro  implements java.io.Serializable {


     private Long id;
     private Long userId;
     private String email;
     private String uuid;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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