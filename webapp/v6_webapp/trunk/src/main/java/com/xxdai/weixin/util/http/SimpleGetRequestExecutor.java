package com.xxdai.weixin.util.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xxdai.weixin.bean.result.WxError;
import com.xxdai.weixin.exception.WxErrorException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * 简单的GET请求执行器，请求的参数是String, 返回的结果也是String
 */
public class SimpleGetRequestExecutor implements
        RequestExecutor<String, String> {
    /**
     * LOG日志记录器
     */
    protected final Log log = LogFactory.getLog(getClass());

    @Override
    public String execute(String uri, String queryParam)
            throws WxErrorException {
        try {
            if (queryParam != null) {
                if (uri.indexOf('?') == -1) {
                    uri += '?';
                }
                uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
            }
            HttpClient client = new HttpClient();
            log.info("uri="+uri);
            // 执行get请求.
            HttpMethod method = new GetMethod(uri);
            client.executeMethod(method);
            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
            StringBuffer responseContent = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
            String resultContent = responseContent.toString();
           // System.out.println("get resultContent===" + resultContent);
            log.info("get resultContent===" + resultContent);
            WxError error = WxError.fromJson(resultContent);
            if (error.getErrcode() != 0) {
                log.error("解析异常");
                throw new WxErrorException(error);
            }
            return resultContent.toString();
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new WxErrorException("", e);
        }
    }
}
