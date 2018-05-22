package com.xxdai.http;

import org.apache.http.client.methods.HttpPut;

import java.net.URISyntaxException;

/**
 * 异步put请求.
 */
public class PutCallable extends PostCallable{

    @Override
    protected void buildBody(AbstractForm abstractForm) {
        if (abstractForm == null) return;
        HttpPut httpPut = (HttpPut) super.httpRequest;
        httpPut.setEntity(abstractForm.build());
    }

    protected PutCallable(String url) throws URISyntaxException {
        super(url, new HttpPut(url));
    }

    public PutCallable(String url, Headers headers) throws URISyntaxException {
        this(url);
        super.headers = headers;
    }

    public PutCallable(String url, Headers headers, AbstractForm abstractForm) throws URISyntaxException {
        this(url, headers);
        super.abstractForm = abstractForm;
    }

    public PutCallable(String url, Headers headers, AbstractForm abstractForm, QueryStrings queryStrings) throws URISyntaxException {
        this(url, headers, abstractForm);
        super.queryStrings = queryStrings;
    }
}
