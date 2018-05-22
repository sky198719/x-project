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
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 */
public class SimplePostRequestExecutor implements
        RequestExecutor<String, String> {
    /**
     * LOG日志记录器
     */
    protected final Log log = LogFactory.getLog(getClass());

    @Override
    public String execute(String uri, String postEntity)
            throws WxErrorException {
        String responseContent = "";
        try {
            HttpClient client = new HttpClient();
            // 执行get请求.
            HttpMethod method = new GetMethod(uri);
            client.executeMethod(method);
            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
            StringBuffer responseContentStr = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                responseContentStr.append(line);
            }
            reader.close();
            responseContent = responseContentStr.toString();

            WxError error = WxError.fromJson(responseContent);
            if (error.getErrcode() != 0) {
                throw new WxErrorException(error);
            }
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            throw new WxErrorException("", e);
        }
        return responseContent;
    }

}
