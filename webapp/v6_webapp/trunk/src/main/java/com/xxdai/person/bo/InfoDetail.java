/** 
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 */
package com.xxdai.person.bo;

// default package

/**
 * 资料明细数据表model类
 * 
 * @since jdk1.6
 * @version $Id: InfoDetail.java 9464 2014-11-07 12:23:02Z xiaoying $
 */

public class InfoDetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer attTypeId;
	private String detailName;
	private Integer mustOrNot;
	private Integer isImg;
	private Integer powerWeight;
	private String setter;
	private String setTime;
	private String setIp;
	private String className;
	private Integer classLevel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAttTypeId() {
		return attTypeId;
	}

	public void setAttTypeId(Integer attTypeId) {
		this.attTypeId = attTypeId;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public Integer getMustOrNot() {
		return mustOrNot;
	}

	public void setMustOrNot(Integer mustOrNot) {
		this.mustOrNot = mustOrNot;
	}

	public Integer getIsImg() {
		return isImg;
	}

	public void setIsImg(Integer isImg) {
		this.isImg = isImg;
	}

	public Integer getPowerWeight() {
		return powerWeight;
	}

	public void setPowerWeight(Integer powerWeight) {
		this.powerWeight = powerWeight;
	}

	public String getSetter() {
		return setter;
	}

	public void setSetter(String setter) {
		this.setter = setter;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getClassLevel() {
		return classLevel;
	}

	public void setClassLevel(Integer classLevel) {
		this.classLevel = classLevel;
	}
}