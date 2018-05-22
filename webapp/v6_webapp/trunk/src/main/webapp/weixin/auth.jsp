<%@ page language="java" contentType="text/html; charset=UTF-8"
         import="com.xxdai.weixin.bean.WxPageAccessToken,
         com.xxdai.weixin.bean.result.WxError,
         com.xxdai.weixin.util.CacheUtil,
         com.xxdai.weixin.util.http.SimpleGetRequestExecutor,
         com.xxdai.weixin.constants.WeixinConstants"
    pageEncoding="UTF-8"%>
<%@ page import="com.xxdai.util.Configuration" %>
<%
    String isShowLogin = request.getParameter("isShowLogin");
    if(isShowLogin != null) {
        request.getSession().setAttribute("isShowLogin",isShowLogin);
    }

    String referer = request.getParameter("referer");
    //System.out.println("referer================" + referer);
    if(referer == null || "".equals(referer)) {
        if(request.getSession().getAttribute("referer") == null) {
            referer = request.getHeader("Referer");
            referer = referer == null ? "/" : referer;
            request.getSession().setAttribute("referer",referer);
        }
    }
    if(referer != null) {
        request.getSession().setAttribute("referer",referer);
    }


String code = request.getParameter("code");
//System.out.println("===============================code="+code);
if(code != null){
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
	WxError wxError = WxError.fromJson(respon);
	if (wxError.getErrcode() != 0) {
		response.sendRedirect(request.getSession().getAttribute("referer").toString());
	}
	WxPageAccessToken pageAccessToken = WxPageAccessToken.fromJson(respon);
	request.getSession().setAttribute(WeixinConstants.WEIXIN_USER_SESSION_OPENID, pageAccessToken.getOpenid());

    response.sendRedirect(Configuration.getInstance().getValue("webapp_url")+"/weixin/view/freeLogin.do");

}

Object authOpenid = request.getSession().getAttribute(WeixinConstants.WEIXIN_USER_SESSION_OPENID);
//System.out.println("=========================authOpenid="+authOpenid);
if(authOpenid == null){
    //微信应用ID
    String appid = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_APPID);
    String REDIRECT_URI = CacheUtil.getCacheValue(WeixinConstants.REDIRECT_URI);
    REDIRECT_URI = java.net.URLEncoder.encode(REDIRECT_URI, "utf-8");
    StringBuffer param = new StringBuffer();
    param.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
    param.append("appid=").append(appid);
    param.append("&redirect_uri=").append(REDIRECT_URI);
    param.append("&response_type=code");
    param.append("&scope=snsapi_base");
    param.append("&state=123");
    param.append("#wechat_redirect");
    //System.out.println(param);
    response.sendRedirect(param.toString());
}
%>