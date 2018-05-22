package com.xxdai.popularize.bo;

import java.util.Date;

public class UserActivity implements java.io.Serializable{

	/**
	 *  用户活动关系
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private Long activityId;
	private String uuid;
	private Date joinTime;
	private String serviceNum;
	
	public UserActivity(){}

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
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public String getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}
	
	

}
