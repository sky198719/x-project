package com.xxdai.xxb.bo;

// default package

import java.util.Date;

public class LevelLogs implements java.io.Serializable {

	private Long id;
	private Long userId;
	private Date changeTime;
	private String changeIp;
	private Integer changeNum;
	private Integer changeHow;
	private String changeType;
	private String remark;
	private Integer operator;
	private String busiId;

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

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getChangeIp() {
		return changeIp;
	}

	public void setChangeIp(String changeIp) {
		this.changeIp = changeIp;
	}

	public Integer getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(Integer changeNum) {
		this.changeNum = changeNum;
	}

	public Integer getChangeHow() {
		return changeHow;
	}

	public void setChangeHow(Integer changeHow) {
		this.changeHow = changeHow;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public String getBusiId() {
		return busiId;
	}

	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}
}