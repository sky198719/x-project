/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved.
 */
package com.xxdai.http;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.exception.ServiceException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;


/**
 * 异步get请求.
 */
public class GetCallable extends RequestCallable {

    private static final Logger logger = LoggerFactory.getLogger(GetCallable.class);

    public JSONObject call() throws Exception {
        JSONObject result = new JSONObject();
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = super.execute();
            HttpEntity entity = httpResponse.getEntity();
            int status = httpResponse.getStatusLine().getStatusCode();
            String data = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            result.put("status", status);
            if (HttpStatus.SC_OK != status) {
                logger.error("invoking remote interface[url:{}] error:{}", this.url, data);
                throw new ServiceException("request error");
            }
            result.put("data", JSONObject.parseObject(data));
        } catch (Exception e) {
            logger.error("invoking remote interface[url:{}] error:", this.url,  e);
            result.put("status", HttpStatus.SC_INTERNAL_SERVER_ERROR);
            throw new ServiceException(e.getMessage());
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                // just ignore it
            }
            try {
                if (this.httpClient != null) {
                    this.httpClient.close();
                }
            } catch (IOException e) {
                // just ignore it
            }
        }
        return result;
    }

    private GetCallable() {
    }

    public GetCallable(String url) throws URISyntaxException {
        super(new HttpGet(url));
        super.url = url;
    }

    public GetCallable(String url, Headers headers) throws URISyntaxException {
        this(url);
        super.headers = headers;
    }

    public GetCallable(String url, Headers headers, QueryStrings queryStrings) throws URISyntaxException {
        this(url, headers);
        super.queryStrings = queryStrings;
    }
}
