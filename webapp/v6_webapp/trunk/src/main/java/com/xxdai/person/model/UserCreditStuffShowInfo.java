package com.xxdai.person.model;

import java.util.Date;

public class UserCreditStuffShowInfo {
	
	private Long id;
	private Long keyid;
	private String pname;
	private Integer score;
	private Integer type;
	private String notes;
	private Integer status;
	private Date addTime;
	private Long creator;
	private String addIp;
	private Integer userStatus;//int(11) NOT NULL COMMENT '状态：0无1有2验证撤销', 
	private String userModifydate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getKeyid() {
		return keyid;
	}
	public void setKeyid(Long keyid) {
		this.keyid = keyid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public String getAddIp() {
		return addIp;
	}
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public String getUserModifydate() {
		return userModifydate;
	}
	public void setUserModifydate(String userModifydate) {
		this.userModifydate = userModifydate;
	}
}
