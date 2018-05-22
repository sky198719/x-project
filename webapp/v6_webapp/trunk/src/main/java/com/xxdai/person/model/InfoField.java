package com.xxdai.person.model;

import java.util.Date;

public class InfoField {
	
	private Integer extDetailId;
	private Long userId;
	private Integer extKeyId;
	private String extValue;
	private Date createDate;
	private String createIp;
	private Date  modifyDate;
	
	private String keyName;

	public Integer getExtDetailId() {
		return extDetailId;
	}

	public void setExtDetailId(Integer extDetailId) {
		this.extDetailId = extDetailId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getExtKeyId() {
		return extKeyId;
	}

	public void setExtKeyId(Integer extKeyId) {
		this.extKeyId = extKeyId;
	}

	public String getExtValue() {
		return extValue;
	}

	public void setExtValue(String extValue) {
		this.extValue = extValue;
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

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	
}
