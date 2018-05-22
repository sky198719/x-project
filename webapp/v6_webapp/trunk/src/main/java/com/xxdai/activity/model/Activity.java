/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.activity.model;

import java.util.Date;
;

/**
 * @author zhangbaobao
 * 活动
 */
public class Activity implements java.io.Serializable{

	/**
	 * 
	 */
	
	private Long id;
	private String activityname;
	private String activitycode;
	private Date starttime;
	private String endtime;
	private Integer activitytype;
	private Integer promotiontype;
	private Integer status;
	private Date onlinestarttime;
	private Date onlineendtime;
	private Date offlinestarttime;
	private Date offlineendtime;
	private Date addtime;
	private Integer addempid;
	private Date modifytime;
	private Integer modifyempid;
	private String activitylogo;
	private Date rewardendtime;

	// Constructors

	/** default constructor */
	public Activity() {
	}

	/** minimal constructor */
	public Activity(Integer activitytype, Integer status) {
		this.activitytype = activitytype;
		this.status = status;
	}

	/** full constructor */
	public Activity(String activityname, String activitycode,
			Date starttime, String endtime, Integer activitytype,
			Integer promotiontype, Integer status, Date onlinestarttime,
			Date onlineendtime, Date offlinestarttime, Date offlineendtime,
			Date addtime, Integer addempid, Date modifytime,
			Integer modifyempid, String activitylogo, Date rewardendtime) {
		this.activityname = activityname;
		this.activitycode = activitycode;
		this.starttime = starttime;
		this.endtime = endtime;
		this.activitytype = activitytype;
		this.promotiontype = promotiontype;
		this.status = status;
		this.onlinestarttime = onlinestarttime;
		this.onlineendtime = onlineendtime;
		this.offlinestarttime = offlinestarttime;
		this.offlineendtime = offlineendtime;
		this.addtime = addtime;
		this.addempid = addempid;
		this.modifytime = modifytime;
		this.modifyempid = modifyempid;
		this.activitylogo = activitylogo;
		this.rewardendtime = rewardendtime;
	}


	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public String getActivityname() {
		return this.activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	
	public String getActivitycode() {
		return this.activitycode;
	}

	public void setActivitycode(String activitycode) {
		this.activitycode = activitycode;
	}


	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	
	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	
	public Integer getActivitytype() {
		return this.activitytype;
	}

	public void setActivitytype(Integer activitytype) {
		this.activitytype = activitytype;
	}

	
	public Integer getPromotiontype() {
		return this.promotiontype;
	}

	public void setPromotiontype(Integer promotiontype) {
		this.promotiontype = promotiontype;
	}

	
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Date getOnlinestarttime() {
		return this.onlinestarttime;
	}

	public void setOnlinestarttime(Date onlinestarttime) {
		this.onlinestarttime = onlinestarttime;
	}

	
	public Date getOnlineendtime() {
		return this.onlineendtime;
	}

	public void setOnlineendtime(Date onlineendtime) {
		this.onlineendtime = onlineendtime;
	}

	
	public Date getOfflinestarttime() {
		return this.offlinestarttime;
	}

	public void setOfflinestarttime(Date offlinestarttime) {
		this.offlinestarttime = offlinestarttime;
	}


	public Date getOfflineendtime() {
		return this.offlineendtime;
	}

	public void setOfflineendtime(Date offlineendtime) {
		this.offlineendtime = offlineendtime;
	}

	
	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	
	public Integer getAddempid() {
		return this.addempid;
	}

	public void setAddempid(Integer addempid) {
		this.addempid = addempid;
	}

	
	public Date getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	
	public Integer getModifyempid() {
		return this.modifyempid;
	}

	public void setModifyempid(Integer modifyempid) {
		this.modifyempid = modifyempid;
	}

	
	public String getActivitylogo() {
		return this.activitylogo;
	}

	public void setActivitylogo(String activitylogo) {
		this.activitylogo = activitylogo;
	}

	
	public Date getRewardendtime() {
		return this.rewardendtime;
	}

	public void setRewardendtime(Date rewardendtime) {
		this.rewardendtime = rewardendtime;
	}
}
