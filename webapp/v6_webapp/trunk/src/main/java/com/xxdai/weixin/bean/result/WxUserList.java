package com.xxdai.weixin.bean.result;

import com.xxdai.core.util.json.JsonUtil;

import java.util.ArrayList;
import java.util.List;



/**
 * 关注者列表
 *
 *
 */
public class WxUserList {

  protected int total = -1;
  protected int count = -1;
  protected List<String> openids = new ArrayList<String>();
  protected String next_openid;
  public int getTotal() {
    return total;
  }
  public void setTotal(int total) {
    this.total = total;
  }
  public int getCount() {
    return count;
  }
  public void setCount(int count) {
    this.count = count;
  }
  public List<String> getOpenids() {
    return openids;
  }
  public void setOpenids(List<String> openids) {
    this.openids = openids;
  }
  public String getNext_openid() {
    return next_openid;
  }
  public void setNext_openid(String next_openid) {
    this.next_openid = next_openid;
  }
  
  public static WxUserList fromJson(String json) {
   //// return WxGsonBuilder.INSTANCE.create().fromJson(json, WxUserList.class);
      return JsonUtil.jsonToBean(json,WxUserList.class);
  }
}
