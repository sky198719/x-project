/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.system.bo;

import java.util.Date;

/**
 * 意见反馈选项管理类
 * @since jdk1.6
 * date: 2014年10月8日
 * @version $Id: DicCommonVo.java 9464 2014-11-07 12:23:02Z xiaoying $
 */
public  class FeedbackItemsBo {

	private String id;
	private String name;
	private String status;
	private String priority;
	private String terminalver;
	private Date addtime;
	private String addBy;
	private String updatedBy;
	private Date updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getTerminalver() {
		return terminalver;
	}
	public void setTerminalver(String terminalver) {
		this.terminalver = terminalver;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public String getAddBy() {
		return addBy;
	}
	public void setAddBy(String addBy) {
		this.addBy = addBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}