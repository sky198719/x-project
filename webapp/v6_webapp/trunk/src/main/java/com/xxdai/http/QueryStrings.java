package com.xxdai.http;

import com.xxdai.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * queryString,请求url中携带的参数工具类.
 */
public class QueryStrings {

    /**
     * 存储queryString参数.
     */
    private Map<String, String> parameters = new HashMap<String, String>();

    /**
     * 存储path参数
     */
    private String path = "";

    /**
     * 返回工具类实例.
     * @return
     * @throws URISyntaxException
     */
    public static QueryStrings createQueryStrings() {
        return new QueryStrings();
    }

    /**
     * 向url中添加参数.
     *
     * @param name
     * @param value
     * @return
     */
    public QueryStrings addParameter(String name, String value) {
        if (StringUtils.isNotBlank(name)) {
            this.parameters.put(name, value);
        }
        return this;
    }

    /**
     * 向url中添加参数.
     *
     * @param name
     * @param value
     * @return
     */
    public QueryStrings addParameter(String name, long value) {
        this.addParameter(name, String.valueOf(value));
        return this;
    }

    /**
     * 向url中添加参数.
     *
     * @param name
     * @param value
     * @return
     */
    public QueryStrings addParameter(String name, double value) {
        return this.addParameter(name, Double.toString(value));
    }

    /**
     * 设定path中的参数.
     * @param path
     * @return
     */
    public QueryStrings setPath(long path) {
        this.setPath(String.valueOf(path));
        return this;
    }

    /**
     * 设定path中的参数.
     * @param path
     * @return
     */
    public QueryStrings setPath(String path) {
        if (StringUtils.isNotBlank(path)) {
            this.path = path;
        }
        return this;
    }

    /**
     * 向path中追加参数.
     * @param path
     * @return
     */
    public QueryStrings addPath(String path) {
        if (StringUtils.isNotBlank(path)) {
            StringBuffer sb = new StringBuffer();
            sb.append(this.path);
            sb.append(path);
            this.path = sb.toString();
        }
        return this;
    }


    /**
     * 构建URI.
     *
     * @return
     * @throws ServiceException
     */
    public URI build(String uri) throws ServiceException {
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }
            if (StringUtils.isNotBlank(path)) {
                uriBuilder.setPath(uriBuilder.getPath() + path);
            }
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new ServiceException("error build uri", e.getCause());
        }
    }
}
