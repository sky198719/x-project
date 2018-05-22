a<%@ page language="java" contentType="text/html; charset=UTF-8"
         import="com.xxdai.weixin.bean.WxPageAccessToken,
         com.xxdai.weixin.util.CacheUtil,
         com.xxdai.weixin.util.http.SimpleGetRequestExecutor,
         com.xxdai.weixin.constants.WeixinConstants"
         pageEncoding="UTF-8"%>
<%@ page import="com.xxdai.util.Configuration" %>
<%@ page import="com.xxdai.util.RedisUtil" %>
<%@ page import="com.xxdai.util.SpringApplicationUtil" %>
<%
    String code = request.getParameter("code");
    if(code != null){
        try {
            String uri = "https://api.weixin.qq.com/sns/oauth2/access_token?";
            StringBuffer param = new StringBuffer();
            //微信应用ID
            String appid = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_APPID);
            //微信应用密钥
            String appsecret = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_APPSECRET);
            param.append("appid=").append(appid);
            param.append("&secret=").append(appsecret);
            param.append("&code=").append(code);
            param.append("&grant_type=authorization_code");

            SimpleGetRequestExecutor req = new SimpleGetRequestExecutor();
            //System.out.println("请求：\n" + uri + param);
            String respon = req.execute(uri, param.toString());
            //System.out.println("响应：\n" + respon);

            WxPageAccessToken pageAccessToken = WxPageAccessToken.fromJson(respon);

            RedisUtil redisUtil = (RedisUtil) SpringApplicationUtil.getBean("redisUtil");
            redisUtil.setRedisStr(pageAccessToken.getOpenid(),pageAccessToken.getAccess_token(),pageAccessToken.getExpires_in());

            String redirectUrl = request.getParameter("redirectUrl");
            if(null != redirectUrl) {
                if(redirectUrl.indexOf("?") > 0) {
                    redirectUrl += "&key="+pageAccessToken.getOpenid();
                } else {
                    redirectUrl += "?key="+pageAccessToken.getOpenid();
                }
            }
            response.sendRedirect(redirectUrl);
        }catch (Exception e) {
            System.out.println(e);
        }

    } else {
       try {
           String model = request.getParameter("model");
           String redirectUrl = request.getParameter("redirectUrl");
           String appid = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_APPID);
           String REDIRECT_URI = Configuration.getInstance().getValue("webapp_url") + "/weixin/thirdPartyAuth.jsp";
           REDIRECT_URI = java.net.URLEncoder.encode(REDIRECT_URI, "utf-8");
           StringBuffer param = new StringBuffer();
           param.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
           param.append("appid=").append(appid);
           REDIRECT_URI = REDIRECT_URI + "?redirectUrl=" + redirectUrl;

           param.append("&redirect_uri=").append(REDIRECT_URI);

           param.append("&response_type=code");
           if(null != model && "1".equals(model)) {
               param.append("&scope=snsapi_userinfo");
           } else {
               param.append("&scope=snsapi_base");
           }
           param.append("&state=123");
           param.append("#wechat_redirect");
           response.sendRedirect(param.toString());
       }catch (Exception e){
           System.out.println(e);
       }
    }
%>
