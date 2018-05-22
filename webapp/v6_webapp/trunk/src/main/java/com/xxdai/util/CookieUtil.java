package com.xxdai.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void setCookie(HttpServletResponse response, String name,String value,String path){
		Cookie cookie = new Cookie(name,value);
    	cookie.setPath(path);
    	response.addCookie(cookie);
	}
	/**
	 * 设置cookie的有效时间
	 * @param response
	 * @param name
	 * @param value
	 * @param path
	 * @param maxAge
	 */
	public static void setCookie(HttpServletResponse response, String name,String value,String path,Integer maxAge){
		if(maxAge!=null){
			Cookie cookie = new Cookie(name,value);
	    	cookie.setMaxAge(maxAge);
	    	cookie.setPath(path);
	    	response.addCookie(cookie);
		}else{
			setCookie(response, name, value, path);
		}
	
	}

	public static void setCookie(HttpServletResponse response, String name,String value,String path,Integer maxAge, String domain){
		Cookie cookie = new Cookie(name,value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		cookie.setDomain(domain);
		response.addCookie(cookie);
	}
	
	public static void removeCookie(HttpServletRequest request,HttpServletResponse response,String name,String value, String path){
		Cookie cookie = getCookie(request, name);
		if(cookie!=null){
			cookie.setPath(path);
	        cookie.setMaxAge(0);
	        response.addCookie(cookie);
		}
	}

	public static void removeDomainCookie(HttpServletRequest request,HttpServletResponse response,String name,String value, String path,String domain){
		Cookie cookie = getCookie(request, name);
		if(cookie!=null){
			cookie.setDomain(domain);
			cookie.setPath(path);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

	}
	
	public static Cookie getCookie(HttpServletRequest request,String name){
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if(cookies!=null&& cookies.length>0){
			for(int i=0;i<cookies.length;i++){
//				System.out.println(cookies[i].getName()+"   "+name);
	 			if(cookies[i].getName().equals(name)){
	                return cookies[i];
	 			}
	 		}
		}
		return cookie;
	}

    public static String getCookieValue(HttpServletRequest request,String name){
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if(cookies!=null&& cookies.length>0){
            for(int i=0;i<cookies.length;i++){
                if(cookies[i].getName().equals(name)){
                    cookie = cookies[i];
                    break;
                }
            }
        }
        if(cookie != null) {
            return cookie.getValue();
        }
        return null;
    }
}
