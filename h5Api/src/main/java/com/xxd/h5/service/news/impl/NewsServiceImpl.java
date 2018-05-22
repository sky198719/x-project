package com.xxd.h5.service.news.impl;

import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.service.news.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * created by xiguoding on 2018/5/15 上午11:53
 */
@Service
@Slf4j
public class NewsServiceImpl extends H5BaseService implements NewsService {
    @Override
    public Message getNewsNotices(String clientId, String id) {
        log.info("getNewsNotices >>>>> start param:{}", id);
        QueryStrings queryStrings = QueryStrings.create()
                .addPath("/").addPath(id);
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis());
        try {
            log.info("gateway getNewsNotices start param:{}", JsonUtil.toJSONObject(queryStrings));
            Message result = apiUtil.get(ApiEnum.API_INVESTMENT_GETNEWSNOTICESBYID, headers, queryStrings);
            log.info("gateway getNewsNotices end result:{}", JsonUtil.toJSONObject(result));
            return result;
        } catch (CoreException | ServiceException exception) {
            log.error("获取新闻管理失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            log.error("获取新闻管理失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }
}
