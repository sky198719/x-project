package com.xxdai.http;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 构建contentType为application/x-www-form-urlencoded工具类.
 */
public class FormUrlEncoded extends AbstractForm {

    private EntityBuilder builder = EntityBuilder.create();

    List<BasicNameValuePair> parameters = Lists.newArrayList();

    /**
     * 消息默认的编码.
     */
    private Charset default_charset = Consts.UTF_8;

    /**
     * 添加参数.
     * @param name
     * @param value
     */
    public FormUrlEncoded addParameter(String name, String value) {
        if (StringUtils.isEmpty(name)) return this;
        parameters.add(new BasicNameValuePair(name, value));
        return this;
    }

    /**
     * 添加参数.
     * @param name
     * @param value
     */
    public FormUrlEncoded addParameter(String name, Object value) {
        if (StringUtils.isEmpty(name) || null == value) return this;
        parameters.add(new BasicNameValuePair(name, JSONObject.toJSONString(value)));
        return this;
    }

    /**
     * 以流的方式传递参数.
     * @param string
     * @return
     */
    public FormUrlEncoded setInputStream(final String string) {
        builder.setStream(InputStreamUtils.string2InputStream(string));
        return this;
    }

    /**
     * 以流的方式传递参数.
     * @param stream
     * @return
     */
    public FormUrlEncoded setInputStream(final InputStream stream) {
        builder.setStream(stream);
        return this;
    }


    public FormUrlEncoded setBody(final String text) {
        if (StringUtils.isNotBlank(text)) {
            builder.setText(text);
        }
        return this;
    }
    /**
     * 构建body.
     * @return
     */
    public HttpEntity build() {
        builder.setContentEncoding(default_charset.toString());
        builder.setContentType(this.default_contentType);
        return builder.build();
    }



    /**
     * 创建FormUrlEncoded.
     * @return
     */
    public static FormUrlEncoded create() {
        return new FormUrlEncoded();
    }

    private FormUrlEncoded() {
    }
}
