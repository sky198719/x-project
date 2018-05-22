package com.xxdai.weixin.bean.result;


import com.alibaba.fastjson.JSONObject;
import com.xxdai.core.util.json.JsonUtil;

/**
 * 微信用户信息
 * 
 *
 */
public class WxUser {

  protected boolean subscribe;
  protected String openid;
  protected String nickname;
  protected int sex;
  protected String language;
  protected String city;
  protected String province;
  protected String country;
  protected String headimgurl;
  protected long subscribe_time;
  protected String unionid;
  
  public boolean isSubscribe() {
    return subscribe;
  }
  public void setSubscribe(boolean subscribe) {
    this.subscribe = subscribe;
  }
  public String getOpenid() {
    return openid;
  }
  public void setOpenid(String openid) {
    this.openid = openid;
  }
  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
  public int getSex() {
    return sex;
  }
  public void setSex(int sex) {
    this.sex = sex;
  }
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getProvince() {
    return province;
  }
  public void setProvince(String province) {
    this.province = province;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getHeadimgurl() {
    return headimgurl;
  }
  public void setHeadimgurl(String headimgurl) {
    this.headimgurl = headimgurl;
  }
  public long getSubscribe_time() {
    return subscribe_time;
  }
  public void setSubscribe_time(long subscribe_time) {
    this.subscribe_time = subscribe_time;
  }
  public String getUnionid() {
    return unionid;
  }
  public void setUnionid(String unionid) {
    this.unionid = unionid;
  }
  
  public static WxUser fromJson(String str) {
      //{"subscribe":1,"openid":"ooCuns9NV0vmfYYgDHJeJ7GJC1Qg","nickname":"Jack","sex":1,"language":"zh_CN","city":"è?????","province":"?????·","country":"??????","headimgurl":"","subscribe_time":1416655997,"remark":""
      WxUser user = JsonUtil.jsonToBean(str,WxUser.class);
      return user;
  }
  
}
