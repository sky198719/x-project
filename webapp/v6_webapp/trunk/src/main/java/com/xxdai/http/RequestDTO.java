package com.xxdai.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * 构建符合后端接口规则的请求body,如下结构:
 * <code>
 * {
 *     data : {
 *         "" : "",
 *         ...
 *     }
 * }
 * </code>
 */
public class RequestDTO {

    JSONObject parameters = new JSONObject();

    public static RequestDTO create() {
        return new RequestDTO();
    }

    /**
     * 添加参数.
     * @param name
     * @param value
     * @return
     */
    public RequestDTO addParameter(String name, long value) {
        this.addParameter(name, String.valueOf(value));
        return this;
    }

    /**
     * 添加参数.
     * @param name
     * @param value
     * @return
     */
    public RequestDTO addParameter(String name, double value) {
        this.addParameter(name, String.valueOf(value));
        return this;
    }

    /**
     * 添加参数.
     * @param name
     * @param value
     * @return
     */
    public RequestDTO addParameter(String name, String value) {
        if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(value)) {
            parameters.put(name, value);
        }
        return this;
    }

    public JSONObject transform2JsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", parameters);
        return jsonObject;
    }

    public String transform2JsonString() {
        return transform2JsonObject().toJSONString();
    }

    /**
     * 将字符串转成json对象,并包一层data.
     * input:
     * <code>
     *     {
     *         "": "",
     *         "": "",
     *         ...
     *     }
     * </code>
     * output
     * <code>
     *     {
     *         data: {
     *              "": "",
     *              "": "",
     *              ...
     *         }
     *     }
     * </code>
     */
    public String wrapperWithData(String jsonStr) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", JsonUtil.toJSONObject(jsonStr));
        return jsonObject.toJSONString();

    }

    public String wrapperWithData(Object object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", JsonUtil.toJSONObject(object));
        return jsonObject.toJSONString();
    }

    public String wrapperWithData(JSONObject jsonObject) {
        jsonObject.put("data", jsonObject);
        return jsonObject.toJSONString();
    }

}
