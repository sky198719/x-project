/**
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved.
*/
package com.xxdai.util;

import com.xxdai.core.util.cipher.DigestUtil;
import com.xxdai.core.util.lang.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 
 * ClassName: TokenUtil <br/> 
 * Function: token工具类. <br/> 
 * date: 2015年7月31日 上午11:40:49 <br/> 
 * 
 * @author zhaoxiaobo 
 * @version  
 * @since JDK 1.6
 */
public class TokenUtil {

	private static final Log log = LogFactory.getLog(TokenUtil.class);
	
	/**
	 * 
	 * generateToken:(设置令牌). <br/>
	 * 
	 * @author zhaoxiaobo 
	 * @param request
	 * @param tokenName 令牌名称
	 * @return 
	 * @since JDK 1.6 
	 */
	public static String generateToken(HttpServletRequest request,String tokenName){
		String token = null;
		Long time = new Date().getTime();
        try {
        	token = DigestUtil.md5ToHex(
        				String.valueOf(tokenName + time + Math.random())
        			);
        	request.getSession().setAttribute(tokenName, token);
		} catch (Exception e) {
			log.error("令牌生成失败："+tokenName+time+e);
		}
        return token;
	}

	/**
	 * 
	 * validToken:(判断当前token是否有效). <br/>
	 * 
	 * @author zhaoxiaobo 
	 * @param request
	 * @return 
	 * @since JDK 1.6
	 */
	public static boolean validToken(HttpServletRequest request){
		String requestToken = request.getParameter("token");
		String tokenName = request.getParameter("tokenName");
		String sessionToken = (String)request.getSession().getAttribute(tokenName);
        boolean result;
		if(StringUtil.isEmpty(sessionToken) || !requestToken.equals(sessionToken)) {
            result = false;
		} else {
            result = true;
		}
        log.info(String.format("requestToken=%s,reqeustTokenName=%s,sessionToken=%s,validToken=%s",requestToken,tokenName,sessionToken,result));
        return result;
	}
	
	/**
	 * 
	 * removeToken:(销毁令牌). <br/>  
	 * 
	 * @author zhaoxiaobo 
	 * @param request
	 * @since JDK 1.6 
	 */
	public static void removeToken(HttpServletRequest request){
		String tokenName = request.getParameter("tokenName");
		request.getSession().removeAttribute(tokenName);
	}
	
	/**
	 * 
	 * getTokenName:(获取格式化后的token名). <br/>
	 * 
	 * @author zhaoxiaobo 
	 * @param prefix 前缀（行为）
	 * @param id （bw或tr）
	 * @return 
	 * @since JDK 1.6
	 */
	public static String getTokenName(String prefix ,String id){
		if(StringUtil.isNotBlank(id)){
			return prefix+"_"+id+"_TOKEN";
		}else{
			return prefix+"_TOKEN";
		}
	}

    public static String jsonpCallback(HttpServletRequest request,String resultJson){
       String result = resultJson;
       try {
           String jsonpCallback = request.getParameter("jsonpCallback");
           if(jsonpCallback != null && !"".equals(jsonpCallback)){
               result = jsonpCallback + "("+resultJson+")";
           }
       } catch (Exception e) {
           log.error(e);
       }
       return result;
   }
}
