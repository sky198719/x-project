package com.xxdai.weixin.bean;

public class WeixinAccountAccessToken {
	private int id;
	/**微信公众号ID*/
	private int wxAccountId;
	/**公众号的全局唯一票据*/
	private String access_token;
	/**更新时间*/
	private String updateTime;
	/**凭证有效时间，单位：秒 */
	private int expires_in;
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWxAccountId() {
		return wxAccountId;
	}
	public void setWxAccountId(int wxAccountId) {
		this.wxAccountId = wxAccountId;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	
}
