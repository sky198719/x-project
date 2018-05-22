package com.xxdai.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * HTTP工具箱
 *
 * @author leizhimin 2009-6-19 16:36:18
 */
public final class HttpTookit {
    private static Log log = LogFactory.getLog(HttpTookit.class);

    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     *
     * @param url         请求的URL地址
     * @param queryString 请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    public static String doGet(String url, String queryString) {
        String response = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            if (StringUtils.isNotBlank(queryString))
                method.setQueryString(URIUtil.encodeQuery(queryString));
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                response = method.getResponseBodyAsString();
            }
        } catch (URIException e) {
            log.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
        } catch (IOException e) {
            log.error("执行HTTP Get请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        return response;
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url    请求的URL地址
     * @param params 请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, Map<String, String> params) {
        String response = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new PostMethod(url);
        for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext(); ) {

        }
        // 设置Http Post数据
        if (params != null) {
            HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                p.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(p);
        }
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                response = method.getResponseBodyAsString();
            }
        } catch (IOException e) {
            log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }

        return response;
    }

    // ////////////////////////////////////////////////////////////////////////////////
    // ///////////////______________________________________________________________//////////////////
    // --------------java
    public static String sendGet(String url) {
        String msg = "";
        try {
            HttpURLConnection httpURLConnection = ((HttpURLConnection) new URL(url).openConnection());
            msg = creatConnection(url, httpURLConnection);
        } catch (IOException io) {
            log.error("http close" + io);
        }
        System.out.println("信息发送情况1：" + msg);
        return msg;
    }

    // #####################################################################

    private static String creatConnection(String url,
                                          HttpURLConnection httpURLConnection) {
        String msg = "";
        try {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            httpURLConnection = ((HttpURLConnection) new URL(url).openConnection());
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type",
                    "text/html;charset=utf-8");
            msg = receiveMessage(httpURLConnection);
        } catch (IOException io) {
            io.printStackTrace();
            log.error("Http Connect to :" + url + " " + "IOFail!");
        } catch (Exception ex) {
            log.error("Http Connect to :" + url + " " + "Failed" + ex);
        } finally {
            closeConnection(httpURLConnection);
        }
        return msg;
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private static void closeConnection(HttpURLConnection httpURLConnection) {
        try {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (Exception ex) {
        }
    }

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    private static String receiveMessage(HttpURLConnection httpURLConnection) {
        String responseBody = null;
        try {

            InputStream httpIn = httpURLConnection.getInputStream();
            if (httpIn != null) {
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                byte tempByte;
                while (-1 != (tempByte = (byte) httpIn.read()))
                    byteOut.write(tempByte);
                responseBody = new String(byteOut.toByteArray(), "utf-8");
            }
        } catch (IOException ioe) {
            log.error("Http Connect tosss :" + ioe.getLocalizedMessage() + " "
                    + "IOEFail!");
            return null;
        }
        return responseBody;
    }


    public static String getRealIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    }


    public static String getRequestTerminal(HttpServletRequest request) {
        JSONObject terminalVer = new JSONObject();
        terminalVer.put("user-agent", request.getHeader("User-Agent"));
        terminalVer.put("type", "WEB-APP");
        return terminalVer.toJSONString();
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static void invalidateSession(HttpSession session) {
        Enumeration em = session.getAttributeNames();
        while (em.hasMoreElements()) {
            session.removeAttribute(em.nextElement().toString());
        }
    }

    /**
     * @param request
     * @param key
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie cookie = getCookie(request, key);
        return null != cookie ? cookie.getValue() : "";
    }

    public static Cookie getCookie(HttpServletRequest request,String key){
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(key, cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
