/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.popularize.bo;


import java.util.Date;


/**
 * @author zhangbaobao
 * @since jdk1.6
 * date: 2014年10月30日
 */

public class AllianceUseragreement {
	private Long id;
	private Long userid;
	private Long status;
	private Date adddate;
	private String addip;

	// Constructors

	
	public AllianceUseragreement() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getAdddate() {
		return this.adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

}
