package com.xxdai.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.HeaderGroup;

/**
 * 封装对Header的操作方法.
 */
public class Headers extends HeaderGroup {

    /**
     * 以键值对的方式添加消息头.
     * @param name
     * @param value
     */
    public Headers addHeader(final String name, final String value) {
        if (StringUtils.isNotEmpty(name)) {
            super.addHeader(new BasicHeader(name, value));
        }
        return this;
    }

    /**
     * 以键值对的方式添加消息头.
     * @param name
     * @param value
     */
    public Headers addHeader(final String name, final long value) {
        this.addHeader(name, Long.toString(value));
        return this;
    }

    /**
     * 根据key值移除消息头.
     * @param name
     */
    public Headers removeHeader(final String name) {
        for (HeaderIterator iterator = super.iterator(name); iterator.hasNext();) {
            Header header = iterator.nextHeader();
            super.removeHeader(header);
        }
        return this;
    }

    /**
     * 创建Headers.
     * @return
     */
    public static Headers createHeaders() {
        return new Headers();
    }

    private Headers() {
    }
}
