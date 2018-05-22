/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved.
 */
package com.xxdai.person.bo;

import java.util.Date;

/**
 * 用户类
 * 
 * @version $Id: User.java 9464 2014-11-07 12:23:02Z xiaoying $
 * @since jdk1.6
 */
public class User implements java.io.Serializable {

	// Fields

	private Long userId;
	private String userName;
	private String email;
	private String mobile;
	private String headImg;
	private String password;
	private String payPassword;
	private String regsource;
	private String region;
	private String referer;
	private Date expireDate;
	private String status;
	private Date addTime;
	private String addIp;
	private Date modifyDate;
	private String userNameMD5;
	private Integer securityQid;
	private String securityans;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getRegsource() {
		return regsource;
	}

	public void setRegsource(String regsource) {
		this.regsource = regsource;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getUserNameMD5() {
		return userNameMD5;
	}

	public void setUserNameMD5(String userNameMD5) {
		this.userNameMD5 = userNameMD5;
	}

	public Integer getSecurityQid() {
		return securityQid;
	}

	public void setSecurityQid(Integer securityQid) {
		this.securityQid = securityQid;
	}

	public String getSecurityans() {
		return securityans;
	}

	public void setSecurityans(String securityans) {
		this.securityans = securityans;
	}

}