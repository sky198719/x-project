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
import com.xxd.h5.service.redpackage.RateCouponService;
import com.xxd.h5.vo.redpackage.Coupons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * created by xiguoding on 2018/4/28 下午2:06
 */
@Service
@Slf4j
public class RateCouponServiceImpl extends H5BaseService implements RateCouponService {
    @Override
    public Message getRateCoupons(Headers headers, int status, int type, int currentPage, int pageSize) {
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("status", status)
                .addParameter("type", type)
                .addParameter("pageIndex", currentPage)
                .addParameter("pageSize", pageSize == 0 ? 20 : pageSize);
        log.info("get rate-coupons start params:{}", JsonUtil.toJSONObject(queryStrings));
        Message message = apiUtil.get(ApiEnum.API_TRADECENTER_COUPON_LIST, headers, queryStrings);
        log.info("get rate-coupons end result:{}", JsonUtil.toJSONObject(message));
        if (message.getCode() == MessageStatus.SUCCESS.getStatus()) {
            Coupons coupons = JSONObject.parseObject(message.getData().toString(), Coupons.class);
            log.info("couponList:{}", coupons);
            return new SuccessMessage(coupons);
        }
        return new ErrorMessage(MessageStatus.ERROR);
    }
}
