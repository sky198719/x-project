package com.xxdai.weixin.exception;

public class WeixinException  extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	 /** 异常代码 */
    private Integer errCode;
    
    public WeixinException(String errMsg){
    	super(errMsg);    		
    }
    
    public WeixinException( Integer errCode,String errMsg){
    	super(errMsg);
    	this.errCode = errCode;    	
    }
    
    public String toString(){
    	StringBuffer buf = new StringBuffer();
    	buf.append("\nWeixinException{");
    	buf.append("\n	errCode=").append(errCode);
    	buf.append("\n	errMsg=").append(this.getMessage());
    	buf.append("\n}");
    	return buf.toString();
    }
}
