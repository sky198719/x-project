package com.xxdai.http;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;

import java.nio.charset.Charset;

/**
 * 构架body工具的抽象类.
 */
public abstract class AbstractForm {

    /**
     * 消息默认的编码.
     */
    protected Charset default_charset = Consts.UTF_8;

    /**
     * 消息默认的contentType.
     */
    protected ContentType default_contentType = ContentType.create("application/x-www-form-urlencoded",default_charset);


    /**
     * 设定编码.
     * @param charset
     */
    public AbstractForm setCharset(Charset charset) {
        default_charset = charset;
        return this;
    }

    /**
     * 设定编码.
     * @param charsetName
     */
    public AbstractForm setCharset(String charsetName) {
        if (Charset.isSupported(charsetName)) {
            default_charset = Charset.forName(charsetName);
        }
        return this;
    }

    /**
     * 设定contentType.
     * @param contentType
     */
    public AbstractForm setContentType(ContentType contentType) {
        default_contentType = contentType;
        return this;
    }

    public abstract HttpEntity build();
}
