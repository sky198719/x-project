/** 
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 */
package com.xxdai.person.bo;

// default package

/**
 * 资料条目数据表model类
 * 
 * @since jdk1.6
 * @version $Id: Infos.java 9464 2014-11-07 12:23:02Z xiaoying $
 */
public class Infos implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer infoDetailId;
	private String infoValue;
	private Integer addScore;
	private String setTime;
	private String setIp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getInfoDetailId() {
		return infoDetailId;
	}

	public void setInfoDetailId(Integer infoDetailId) {
		this.infoDetailId = infoDetailId;
	}

	public String getInfoValue() {
		return infoValue;
	}

	public void setInfoValue(String infoValue) {
		this.infoValue = infoValue;
	}

	public Integer getAddScore() {
		return addScore;
	}

	public void setAddScore(Integer addScore) {
		this.addScore = addScore;
	}

	public String getSetTime() {
		return setTime;
	}

	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}

	public String getSetIp() {
		return setIp;
	}

	public void setSetIp(String setIp) {
		this.setIp = setIp;
	}
}