package com.xxdai.weixin.bean;

/**
 * 微信用户与网站用户绑定关系类
 * @author zhangyi
 *
 */
public class WeixinUserBind {
	private Long id;
	/**微信用户ID*/
	private Long wxUserId;
	/**网站用户ID	*/
	private Long userId;
	/**绑定时间*/
	private String bindTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getWxUserId() {
		return wxUserId;
	}
	public void setWxUserId(Long wxUserId) {
		this.wxUserId = wxUserId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBindTime() {
		return bindTime;
	}
	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}
}
