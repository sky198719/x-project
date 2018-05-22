package com.xxdai.http;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.nio.charset.Charset;


public class StringForm extends AbstractForm {
    private String body;
    public StringForm(String body) {
        this.body = body;
    }
    public HttpEntity build() {
        return new StringEntity(body, Charset.forName("UTF-8"));
    }
}
