package com.xxd.h5.service.news;

import com.xxd.common.bo.Message;
import com.xxd.common.remoting.http.parameters.Headers;

/**
 * created by xiguoding on 2018/5/15 上午11:52
 */
public interface NewsService {
    /**
     * 新闻管理接口
     *
     * @param clientId
     * @param id
     * @return
     */
    Message getNewsNotices(String clientId, String id);
}
