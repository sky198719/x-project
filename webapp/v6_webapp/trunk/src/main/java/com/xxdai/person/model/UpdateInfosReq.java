package com.xxdai.person.model;

import java.util.Date;

public class UpdateInfosReq {
	
	/**用户名称*/
	private String userName;
	
	/**设置时间*/
	private Date setTime;
	
	/**设置IP*/
	private String setIp;
	
	/**类型实体*/
	private UpdateTypeEntity[] updateTypeEntity;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public UpdateTypeEntity[] getUpdateTypeEntity() {
		return updateTypeEntity;
	}

	public void setUpdateTypeEntity(UpdateTypeEntity[] updateTypeEntity) {
		this.updateTypeEntity = updateTypeEntity;
	}

}
