/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.http;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

/**
 * 对请求进行抽象.
 */
public abstract class RequestCallable implements Callable {

    /**
     * 1s钟.
     */
    private static final int SECONDS_1 = 5000;

    /**
     * 获取数据超时时间.
     */
    private static int SOCKET_TIMEOUT = 10 * SECONDS_1;

    /**
     * 连接超时时间.
     */
    private static int CONNECT_TIMEOUT = 10 * SECONDS_1;

    /**
     * 请求超时时间.
     */
    private static int REQUEST_TIMEOUT = 10 * SECONDS_1;

    protected RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(SOCKET_TIMEOUT)
            .setConnectTimeout(CONNECT_TIMEOUT)
            .setConnectionRequestTimeout(REQUEST_TIMEOUT)
            .build();

    /**
     * 请求对象.
     */
    protected HttpRequestBase httpRequest;

    /**
     * 请求接口url.
     */
    protected String url;

    /**
     * 请求头.
     */
    protected Headers headers;

    /**
     * queryString,请求url中携带的参数.
     */
    QueryStrings queryStrings;

    /**
     * 默认请求client.
     */
    protected final CloseableHttpClient httpClient = HttpClients.createDefault();


    /**
     * 应用请求设定.
     */
    private void applyRequestConfig() {
        httpRequest.setConfig(requestConfig);
    }

    /**
     * 添加请求头的参数.
     * @param headers
     */
    private void buildHeader(final Headers headers) {
        if( headers != null ) {
            httpRequest.setHeaders(headers.getAllHeaders());
        }

    }

    /**
     * 构建消息头中的参数.
     * @throws URISyntaxException
     */
    private void buildQueryString() throws Exception {
        if (null != queryStrings) {
            httpRequest.setURI(queryStrings.build(this.url));
        }
    }

    /**
     * 执行调用.
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public CloseableHttpResponse execute() throws Exception,
            ClientProtocolException, URISyntaxException {
        buildQueryString(); // 构建URL中的参数.
        applyRequestConfig(); // 应用请求配置项.
        buildHeader(headers); // 构建请求参数.
        return httpClient.execute(httpRequest);
    }

    /**
     * 获取请求配置项.
     * @return
     */
    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    /**
     * 自定义请求配置项.
     * @param requestConfig
     */
    public void setRequestConfig(final RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    public RequestCallable(HttpRequestBase httpRequest) throws URISyntaxException {
        this.httpRequest = httpRequest;
    }

    public RequestCallable() {
    }
}
