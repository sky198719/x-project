package com.xxdai.weixin.api;

import com.xxdai.weixin.bean.WxAccessToken;

/**
 * 微信客户端配置存储
 * 
 *
 */
public interface WxConfigStorage {

  public void updateAccessToken(WxAccessToken accessToken);

  public void updateAccessToken(String accessToken, int expiresIn);

  public String getAccessToken();

  public String getAppId();

  public String getSecret();

  public String getToken();

  public int getExpiresIn();

}
