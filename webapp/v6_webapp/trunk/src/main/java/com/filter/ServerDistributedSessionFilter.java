/**
 * Copyright (c) 2014, www.xxdai.com All Rights Reserved.
 */
package com.filter;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.lang.CharsetNames;
import com.xxdai.external.user.ws.User;
import com.xxdai.http.Message;
import com.xxdai.session.DistributedSessionFilter;
import com.xxdai.user.service.UserService;
import com.xxdai.util.Configuration;
import com.xxdai.util.CookieUtil;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.SpringApplicationUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * ServerDistributedSessionFilter
 *
 * @version $Id: $
 * @since jdk1.6
 */
public class ServerDistributedSessionFilter extends DistributedSessionFilter {
    private Logger log = Logger.getLogger(getClass());

    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse)response;
        req.setCharacterEncoding("utf-8");
        chain.doFilter(req, resp);
        try {
           String referer = req.getHeader("Referer");
            if (referer != null && (referer.indexOf(request.getServerName()) > 10 || referer.indexOf(request.getServerName()) <= 0)) {
                req.getSession().setAttribute("siteFrom", referer);
                req.getSession().setAttribute("landingPage", getLandingPage(req));
            } else {
                //当referer为空时，优先记录utm_source的登陆页
                String utmStr = "utm_source";
                String qs = req.getQueryString();

                if (qs != null && qs.contains(utmStr)) {
                    req.getSession().setAttribute("landingPage", getLandingPage(req));
                }
            }


            //将登录状态中的用户userid存进redis中去，用于统计用户维度
            //UserIdTemp userIdTemp = new UserIdTemp();
            //userIdTemp.putUserIdToredis(req);

             /*Object obj = req.getSession().getAttribute("loginUser");
            Long userId = null;
            String userName = "";
            if (obj != null && obj instanceof User) {
                User user = (User) obj;
                userId = user.getUserId();
                userName = user.getUserName();
            }
            JSONObject inputObj = new JSONObject();
            inputObj.put("userId", userId);
            inputObj.put("userName", userName);
            String startdate = DateUtil.format(new Date(), DateUtil.FullDateFormat);
            inputObj.put("startdate", startdate);
            SysTranLog.log(req, (HttpServletResponse) response, inputObj);*/
        } catch (Exception e) {
            log.error(((HttpServletRequest) request).getRequestURL()+"\n"+e.getMessage(),e);
        }
    }


    /**
     * 获取着陆页
     *
     * @param request
     * @return
     */
    private String getLandingPage(HttpServletRequest request) {
        StringBuffer landingPage = request.getRequestURL();//目标链接
        try {
            String qs = request.getQueryString();
            if (qs != null) {
                landingPage.append("?").append(java.net.URLDecoder.decode(qs, CharsetNames.UTF8));
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e);
            return landingPage.toString();
        }
        return landingPage.toString();
    }
}
