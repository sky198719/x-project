package com.xxdai.http;

import org.apache.http.client.methods.HttpPatch;

import java.net.URISyntaxException;

/**
 * 异步patch请求.
 */
public class PatchCallable extends PostCallable{

    @Override
    protected void buildBody(AbstractForm abstractForm) {
        if (abstractForm == null) return;
        HttpPatch httpPatch = (HttpPatch) super.httpRequest;
        httpPatch.setEntity(abstractForm.build());
    }

    public PatchCallable(String url) throws URISyntaxException {
        super( url, new HttpPatch(url));
    }


    public PatchCallable(String url, Headers headers) throws URISyntaxException {
        this(url);
        super.headers = headers;
    }

    public PatchCallable(String url, Headers headers, AbstractForm abstractForm) throws URISyntaxException {
        this(url, headers);
        super.abstractForm = abstractForm;
    }

    public PatchCallable(String url, Headers headers, AbstractForm abstractForm, QueryStrings queryStrings) throws URISyntaxException {
        this(url, headers, abstractForm);
        super.queryStrings = queryStrings;
    }
}
