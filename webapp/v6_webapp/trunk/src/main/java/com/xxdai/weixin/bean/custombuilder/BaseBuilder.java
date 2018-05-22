package com.xxdai.weixin.bean.custombuilder;


public class BaseBuilder<T> {
  protected String msgtype;
  protected String toUser;

  public T toUser(String toUser) {
    this.toUser = toUser;
    return (T) this;
  }
    /*
  public WxCustomMessage build() {
    WxCustomMessage m = new WxCustomMessage();
    m.setMsgtype(this.msgtype);
    m.setTouser(this.toUser);
    return m;
  }      */
}
