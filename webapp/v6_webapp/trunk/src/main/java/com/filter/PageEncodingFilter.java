/** 
* Copyright (c) 2014, www.xxdai.com All Rights Reserved. 
*/
package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/** 
* PageEncodingFilter
* 
* @since jdk1.6
* @version $Id: $
*/
public class PageEncodingFilter implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding("utf-8");
        chain.doFilter(req, res);
	}

	public void destroy() {

	}
}
