package com.xxdai.weixin.bean;

/**
 * 微信账号
 * @author zhangyi
 *
 */
public class WeixinAccount {
	/**主键，自动增长*/
	private int id;
	/**微信公众号Code*/
	private String code;
	/**微信公众号名称*/
	private String name;
	/**微信公众号类型，1：订阅号，2：服务号*/
	private int type;
	/**第三方用户唯一凭证*/
	private String appid;
	/**第三方用户唯一凭证密钥，即appsecret */
	private String appsecret;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	
	
}
