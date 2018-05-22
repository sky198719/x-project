package com.xxdai.weixin.bean;

/**
 * 微信用户信息类
 * @author zhangyi
 *
 */
public class WeixinUser {
	private Long id;
	/**微信公众号ID*/
	private Long wxAccountId;
	/**微信用户的标识，对当前公众号唯一*/
	private String openId;
	/**微信用户的昵称 */
	private String nickName;
	/**微信用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 */
	private int sex;
	/**微信用户所在城市*/
	private String city;
	/**微信用户所在国家*/
	private String country;
	/**微信用户所在省份*/
	private String province;
	/**微信用户的语言，简体中文为zh_CN*/
	private String language;
	/**微信用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空*/
	private String headimgurl;
	/**微信用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间*/
	private String subscribeTime;
	/**只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。*/
	private String unionid;
	/**微信用户取消关注时间*/
	private String unsubscribeTime;
	/**微信用户关注公众号状态。1，关注，2，取消关注,3,未关注*/
	private int status;
	
	public String getUnsubscribeTime() {
		return unsubscribeTime;
	}
	public void setUnsubscribeTime(String unsubscribeTime) {
		this.unsubscribeTime = unsubscribeTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getWxAccountId() {
		return wxAccountId;
	}
	public void setWxAccountId(Long wxAccountId) {
		this.wxAccountId = wxAccountId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	
}
