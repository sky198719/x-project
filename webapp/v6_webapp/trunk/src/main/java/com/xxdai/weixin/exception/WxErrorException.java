package com.xxdai.weixin.exception;

import com.xxdai.weixin.bean.result.WxError;

public class WxErrorException extends Exception {

  private static final long serialVersionUID = -6357149550353160810L;
  
  private WxError error;
  
  public WxErrorException(String message,Throwable t){
	  super(message,t);
  }
  
  public WxErrorException(WxError error) {
    super(error.toString());
    this.error = error;
  }

  public WxError getError() {
    return error;
  }

 
}
