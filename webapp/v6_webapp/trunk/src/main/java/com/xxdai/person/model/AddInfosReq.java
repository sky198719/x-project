package com.xxdai.person.model;

import java.util.Date;

public class AddInfosReq {
	
	/**用户名称*/
	private String userName;
	
	/**设置时间*/
	private Date setTime;
	
	/**设置IP*/
	private String setIp;	
	
	/**资料类型*/
	private AddTypeEntity[] addTypeEntity;
	
	public AddTypeEntity[] getAddTypeEntity() {
		return addTypeEntity;
	}

	public void setAddTypeEntity(AddTypeEntity[] addTypeEntity) {
		this.addTypeEntity = addTypeEntity;
	}

	public Date getSetTime() {
		return setTime;
	}

	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}

	public String getSetIp() {
		return setIp;
	}

	public void setSetIp(String setIp) {
		this.setIp = setIp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
