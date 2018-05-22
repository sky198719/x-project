package com.xxd.h5.service.redpackage.impl;

import com.alibaba.fastjson.JSONObject;
import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.service.redpackage.RedEnvelopeService;
import com.xxd.h5.vo.redpackage.RedEnvelopes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * created by xiguoding on 2018/4/27 下午3:46
 */
@Service
@Slf4j
public class RedEnvelopeServiceImpl extends H5BaseService implements RedEnvelopeService {
    @Override
    public Message getRedEnvelopes(Headers headers, int status, int currentPage, int pageSize) {
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("status", status)
                .addParameter("currentPage", currentPage)
                .addParameter("pageSize", pageSize == 0 ? 20 : pageSize);

        log.info("get beginnerRedEnvelope start params:{}", JsonUtil.toJSONObject(queryStrings));
        Message beginnerRedEnvelope = apiUtil.get(ApiEnum.API_INVESTMENT_PRIZE_REDPACKAGES, headers, queryStrings);
        log.info("get beginnerRedEnvelope end result:{}", JsonUtil.toJSONObject(beginnerRedEnvelope));
        if (MessageStatus.SUCCESS.getStatus() == beginnerRedEnvelope.getCode()) {
            RedEnvelopes redEnvelopes = JSONObject.parseObject(beginnerRedEnvelope.getData().toString(), RedEnvelopes.class);
            log.info("redEnvelopes:{}", redEnvelopes);
            return new SuccessMessage(redEnvelopes);
        }
        return new ErrorMessage(MessageStatus.ERROR);
    }
}
