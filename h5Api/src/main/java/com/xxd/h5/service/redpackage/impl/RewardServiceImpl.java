package com.xxd.h5.service.redpackage.impl;

import com.alibaba.fastjson.JSONObject;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.service.redpackage.RewardService;
import com.xxd.h5.vo.redpackage.Coupons;
import com.xxd.h5.vo.redpackage.RedEnvelopes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.xxdai.tools.CodeEnumCollection.DiscountCoupon;

/**
 * created by xiguoding on 2018/4/26 下午5:19
 */
@Service
@Slf4j
public class RewardServiceImpl extends H5BaseService implements RewardService {
    @Override
    public Message getMyReward(Headers headers, int status, int currentPage, int pageSize) {
        log.info("get my reward start params:{}", headers);
        JSONObject coupons = new JSONObject();
        coupons.put("beginnerRedEnvelopeCount", getBeginnerRedEnvelope(headers, status, currentPage, pageSize));
        coupons.put("redEnvelopCount", getRedEnvelope(headers, status, currentPage, pageSize));
        coupons.put("rateCouponCount", getRateCoupon(headers, status, currentPage, pageSize));
        log.info("get my reward end result:{}", coupons);
        return new SuccessMessage(coupons);
    }

    private int getRateCoupon(Headers headers, int status, int currentPage, int pageSize) {
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("status", status)
                .addParameter("type", DiscountCoupon.RAISE_INTEREST_RATE.getCode())
                .addParameter("pageIndex", 1)
                .addParameter("pageSize", currentPage * pageSize == 0 ? 20 : currentPage * pageSize);
        log.info("get rateCoupon start params:{}", JsonUtil.toJSONObject(queryStrings));
        Message rateCoupon = apiUtil.get(ApiEnum.API_TRADECENTER_COUPON_LIST, headers, queryStrings);
        log.info("get rateCoupon end result:{}", JsonUtil.toJSONObject(rateCoupon));
        Coupons coupons = new Coupons();
        if (MessageStatus.SUCCESS.getStatus() == rateCoupon.getCode()) {
            coupons = JSONObject.parseObject(rateCoupon.getData().toString(), Coupons.class);
        }
        return coupons.getTotalSize();
    }

    private int getRedEnvelope(Headers headers, int status, int currentPage, int pageSize) {
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("status", status)
                .addParameter("type", DiscountCoupon.RED_ENVELOPE.getCode())
                .addParameter("pageIndex", 1)
                .addParameter("pageSize", currentPage * pageSize == 0 ? 20 : currentPage * pageSize);
        log.info("get redEnvelope start params:{}", JsonUtil.toJSONObject(queryStrings));
        Message redEnvelope = apiUtil.get(ApiEnum.API_TRADECENTER_COUPON_LIST, headers, queryStrings);
        log.info("get redEnvelope end result:{}", JsonUtil.toJSONObject(redEnvelope));
        Coupons coupons = new Coupons();
        if (MessageStatus.SUCCESS.getStatus() == redEnvelope.getCode()) {
            coupons = JSONObject.parseObject(redEnvelope.getData().toString(), Coupons.class);
        }
        return coupons.getTotalSize();
    }

    private int getBeginnerRedEnvelope(Headers headers, int status, int currentPage, int pageSize) {
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("status", status)
                .addParameter("currentPage", 1)
                .addParameter("pageSize", currentPage * pageSize == 0 ? 20 : currentPage * pageSize);
        log.info("get beginnerRedEnvelope start params:{}", JsonUtil.toJSONObject(queryStrings));
        Message beginnerRedEnvelope = apiUtil.get(ApiEnum.API_INVESTMENT_PRIZE_REDPACKAGES, headers, queryStrings);
        log.info("get beginnerRedEnvelope end result:{}", JsonUtil.toJSONObject(beginnerRedEnvelope));
        if (MessageStatus.SUCCESS.getStatus() == beginnerRedEnvelope.getCode()) {
            return JSONObject.parseObject(beginnerRedEnvelope.getData().toString(), RedEnvelopes.class).getTotalCount();
        }
        return 0;
    }
}
