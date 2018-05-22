/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.person.bo;
// default package


/** 
* 资料类型数据表model类
* 
* @since jdk1.6
* @version $Id: InfoType.java 9464 2014-11-07 12:23:02Z xiaoying $
*/
public class InfoType  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String infoName;
     private Integer mustOrNot;
     private String setter;
     private String setTime;
     private String setIp;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInfoName() {
		return infoName;
	}
	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}
	public Integer getMustOrNot() {
		return mustOrNot;
	}
	public void setMustOrNot(Integer mustOrNot) {
		this.mustOrNot = mustOrNot;
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
}