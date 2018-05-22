/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved.
 */
package com.xxdai.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.rmi.ServerException;

/**
 * 异步post请求.
 */
public class PostCallable extends RequestCallable {

    private static final Logger logger = LoggerFactory.getLogger(PostCallable.class);

    /**
     * body.
     */
    protected AbstractForm abstractForm;

    public JSONObject call() throws Exception {

        JSONObject result = new JSONObject();
        CloseableHttpResponse httpResponse = null;
        try {
            buildBody(abstractForm);
            httpResponse = super.execute();
            HttpEntity entity = httpResponse.getEntity();
            int status = httpResponse.getStatusLine().getStatusCode();
            String data = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            result.put("status", status);
            if (HttpStatus.SC_OK != status) {
                logger.error("invoking remote interface[url:{}] error:{}", this.url, data);
                throw new ServerException("request error");
            }
            result.put("data", JSONObject.parseObject(data));
        } catch (Exception e) {
            logger.error("invoking remote interface[url:{}] error:", this.url, e);
            result.put("status", HttpStatus.SC_INTERNAL_SERVER_ERROR);
            throw new ServerException(e.getMessage());
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

    /**
     * 构建body.
     * @param abstractForm
     */
    protected void buildBody(final AbstractForm abstractForm) {
        if (abstractForm == null) return;
        HttpPost post = (HttpPost) super.httpRequest;
        abstractForm.setContentType(ContentType.APPLICATION_JSON);
        post.setEntity(abstractForm.build());
    }

    private PostCallable() {
    }

    public PostCallable(String url) throws URISyntaxException {
        super(new HttpPost(url));
        super.url = url;
    }

    protected PostCallable(String url, HttpRequestBase httpRequestBase) throws URISyntaxException {
        super(httpRequestBase);
        super.url = url;
    }

    public PostCallable(String url, Headers headers) throws URISyntaxException {
        this(url);
        super.headers = headers;
    }


    public PostCallable(String url, Headers headers, AbstractForm abstractForm) throws URISyntaxException {
        this( url, headers);
        this.abstractForm = abstractForm;
    }

    public PostCallable(String url, Headers headers, AbstractForm abstractForm, QueryStrings queryStrings) throws URISyntaxException {
        this(url, headers, abstractForm);
        super.queryStrings = queryStrings;
    }
}