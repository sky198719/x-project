package com.xxdai.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class ApiUtil {

    @Resource(name = "threadPool")
    ThreadPoolTaskExecutor threadPool;

    /**
     * get请求指定后台api
     *
     * @param url
     * @return
     */
    public Message get(final String url) {
        return get(url, null);
    }

    /**
     *
     * @param url
     * @param headers
     * @return
     */
    public Message get(final String url, Headers headers) {
        return this.get(url, headers, null);
    }

    /**
     *
     * @param url
     * @param headers
     * @param queryStrings
     * @return
     */
    public Message get(final String url, Headers headers, QueryStrings queryStrings) {
        try {
            Future<JSONObject> future = threadPool.submit(new GetCallable(url, headers, queryStrings));
            return transform2BizMessage(future.get());
        } catch (URISyntaxException e) {
            // should be recoding
        } catch (InterruptedException e) {
            // just ignore it
        } catch (ExecutionException e) {
            // just ignore it
        }
        return null;
    }

    /**
     * post方式请求指定后台api.
     * @param url
     * @param headers
     *      请求头.
     * @return
     */
    public Message post(final String url, final Headers headers){
        return this.post(url, headers, null, null);
    }

    /**
     * post方式请求指定后台api.
     * @param url
     * @param headers
     * 		请求头.
     * @param AbstractForm
     *      消息体.
     * @return
     */
    public Message post(final String url, final Headers headers, final AbstractForm AbstractForm) {
        return this.post(url, headers, AbstractForm, null);
    }

    /**
     * @param url
     * @param headers
     *      请求头.
     * @param queryStrings
     *      queryString.
     * @return
     */
    public Message post(final String url, final Headers headers, final QueryStrings queryStrings) {
        return this.post(url, headers, null, queryStrings);
    }

    /**
     * post方式请求指定后台api.
     * @param url`
     * @param headers
     *      请求头.
     * @param AbstractForm
     *      请求消息体.
     * @param queryStrings
     *      url中携带queryString.
     * @return
     */
    public Message post(final String url, final Headers headers, final AbstractForm AbstractForm, QueryStrings queryStrings) {
        try {
            Future<JSONObject> future = threadPool.submit(new PostCallable(url, headers, AbstractForm, queryStrings));
            return transform2BizMessage(future.get());
        } catch (URISyntaxException e) {
            // should be recoding
        } catch (InterruptedException e) {
            // just ignore it
        } catch (ExecutionException e) {
            // just ignore it
        }
        return null;
    }

    public Message patch(final String url, final Headers headers) {
        return this.patch(url, headers, null, null);
    }

    public Message patch(final String url, final Headers headers, final QueryStrings queryStrings) {
        return this.patch(url, headers, null, queryStrings);
    }


    public Message patch(final String url, final Headers headers, final AbstractForm AbstractForm) {
        return this.patch(url, headers, AbstractForm, null);
    }

    public Message patch(final String url, final Headers headers, final AbstractForm AbstractForm, QueryStrings queryStrings) {
        try {
            Future<JSONObject> future = threadPool.submit(new PatchCallable(url, headers, AbstractForm, queryStrings));
            return transform2BizMessage(future.get());
        } catch (URISyntaxException e) {
            // should be recoding
        } catch (InterruptedException e) {
            // just ignore it
        } catch (ExecutionException e) {
            // just ignore it
        }
        return null;
    }

    public Message put(final String url, final Headers headers) {
        return this.put(url, headers, null, null);
    }

    public Message put(final String url, final Headers headers, final QueryStrings queryStrings) {
        return this.put(url, headers, null, queryStrings);
    }

    public Message put(final String url, final Headers headers, final AbstractForm AbstractForm) {
        return this.put(url, headers, AbstractForm, null);
    }

    public Message put(final String url, final Headers headers, final AbstractForm AbstractForm, QueryStrings queryStrings) {
        try {
            Future<JSONObject> future = threadPool.submit(new PutCallable(url, headers, AbstractForm, queryStrings));
            return transform2BizMessage(future.get());
        } catch (URISyntaxException e) {
            // should be recoding
        } catch (InterruptedException e) {
            // just ignore it
        } catch (ExecutionException e) {
            // just ignore it
        }
        return null;
    }

    /**
     * 将后端的接口数据转换成业务相关的Message.
     * @return Message 业务信息.
     */
    public static Message transform2BizMessage(JSONObject jsonObject) {
        if (HttpStatus.SC_OK == jsonObject.getInteger("status")) {
            JSONObject data = jsonObject.getJSONObject("data");
            int code = data.getInteger("code");
            if (MessageStatus.SUCCESS.getStatus() == data.getInteger("code")) {
                Object obj = data.get("data");
                if (obj instanceof JSONObject) {
                    return new SuccessMessage(data.getJSONObject("data"));
                }
                return new SuccessMessage(data.get("data"));
            } else {
                return new ErrorMessage(code, data);
            }
        }
        return new ErrorMessage(MessageStatus.ERROR);
    }
}