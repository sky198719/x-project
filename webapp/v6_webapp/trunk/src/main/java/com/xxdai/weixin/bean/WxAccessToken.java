package com.xxdai.weixin.bean;


import com.alibaba.fastjson.JSONObject;

public class WxAccessToken {

  private String access_token;
  
  private int expires_in;

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

  public static WxAccessToken fromJson(String str) {
      JSONObject json = JSONObject.parseObject(str);
      WxAccessToken token = new WxAccessToken();
      token.setAccess_token(json.getString("access_token"));
      token.setExpires_in(json.getInteger("expires_in"));
      return token;
  }
  
}
