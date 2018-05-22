package com.xxdai.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * json操作工具类.
 */
public class JsonUtil {

    /**
     * 属性copy.
     * @param dest 要copy到的目标对象
     * @param orig copy源对象
     * @return
     */
    public static JSONObject copyValues(JSONObject dest, JSONObject orig) {
        if (orig == null || dest == null) return dest;
        for (Map.Entry<String, Object> entry : orig.entrySet()) {
            dest.put(entry.getKey(), entry.getValue());
        }
        return dest;
    }

    /**
     * obj转换成列表.
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(Object obj, Class<T> clazz) {
        if (obj instanceof JSONArray) {
            JSONArray array = (JSONArray) obj;
            List<T> list = Lists.newArrayList();
            for (Iterator<Object> iterator = array.iterator(); iterator.hasNext();) {
                list.add(JSON.parseObject(JSON.toJSONString(iterator.next()), clazz));
            }
            return list;
        } else if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                Object o = entry.getValue();
                if (o instanceof JSONArray) {
                    JSONArray array = (JSONArray) o;
                    List<T> list = Lists.newArrayList();
                    for (Iterator<Object> iterator = array.iterator(); iterator.hasNext();) {
                        list.add(JSON.parseObject(JSON.toJSONString(iterator.next()), clazz));
                    }
                    return list;
                }
            }
        }
       return JSONArray.parseArray(JSON.toJSONString(obj), clazz);
    }

    /**
     * obj实体类.
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObject(Object obj, Class<T> clazz) {
        if (obj instanceof JSONObject) {
            return JSONObject.parseObject(JSON.toJSONString(obj), clazz);
        }
        return null;
    }

    /**
     * 将object转成JSONObject.
     * @param object
     * @return
     */
    public static JSONObject toJSONObject(Object object) {
        if (object instanceof JSONObject)
            return (JSONObject) object;
        return new JSONObject();
    }

    /**
     * 将json字符串转成对象.
     * @param jsonStr
     * @return
     */
    public static JSONObject toJSONObject(String jsonStr) {
        if (StringUtils.isNotBlank(jsonStr)) {
           return JSONObject.parseObject(jsonStr);
        }
        return new JSONObject();
    }

    public static JSONArray toJSONArray(Object object) {
        if (object instanceof JSONArray) {
            return (JSONArray) object;
        }
        return new JSONArray();
    }
}
