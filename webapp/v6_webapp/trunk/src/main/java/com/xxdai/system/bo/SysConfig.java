/**
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved.
*/
package com.xxdai.system.bo;

import java.util.Date;


/***
 *系统配置 实体类
 * @since jdk1.6
 * @version $Id: SysConfig.java 9464 2014-11-07 12:23:02Z xiaoying $
 */

public class SysConfig {

	private String sysKey;
	private String sysKeyValue;
	private String notes;
	private  String reserved1;
	private  String reserved2;
	private  String reserved3;
	private Date addTime;
	private Long addUserId;
	private Date modifyDate;
	private Long lastModify;
	public String getSysKey() {
		return sysKey;
	}
	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}
	public String getSysKeyValue() {
		return sysKeyValue;
	}
	public void setSysKeyValue(String sysKeyValue) {
		this.sysKeyValue = sysKeyValue;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getReserved1() {
		return reserved1;
	}
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	public String getReserved2() {
		return reserved2;
	}
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	public String getReserved3() {
		return reserved3;
	}
	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Long getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
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
