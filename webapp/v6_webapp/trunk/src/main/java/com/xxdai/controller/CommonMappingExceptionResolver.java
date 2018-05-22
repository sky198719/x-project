/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.controller;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 统一异常处理resolver
 * @since jdk1.6
 * date: 2014年10月30日
 * @version $Id: CommonMappingExceptionResolver.java 12347 2014-12-07 16:18:00Z tangwensheng $
 */
public class CommonMappingExceptionResolver extends SimpleMappingExceptionResolver {

	private static final Logger logger=Logger.getLogger(CommonMappingExceptionResolver.class);
	
	/**
	 * 处理请求，
	 * 返回到页面、还是ajax弹出2种情况
	 */
	public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		if(ex != null){
			logger.error("url : "+ request.getRequestURL().toString() +" 处理方法 handler 【" +handler+"】    [ "+ ex.getMessage() +" ]");
			 String viewName = determineViewName(ex, request); //处理异常
			 if(viewName != null){
				 //不是ajax请求
				 if(request.getHeader("accept").indexOf("application/json")>=-1 || 
					(request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest")>=-1 )){
					 //返回状态
					 Integer statusCode = determineStatusCode(request, viewName);
					 if(statusCode!=null){
						 applyStatusCodeIfPossible(request, response, statusCode);
					 }
					 
					 return getModelAndView(viewName, ex,request);
				 }else{
					 //ajax请求 目前是不能使用 除非修页面ajax提交方式
					 try {  
		                 PrintWriter writer = response.getWriter();  
		                 writer.write(ex.getMessage());  
		                 writer.flush();  
		             } catch (IOException e) {  
		                 logger.error(e.getMessage());
		             }  
		             return null;  
				 }
			 }else{
				 return null;
			 }
		}
		return null;
		}
	
}
