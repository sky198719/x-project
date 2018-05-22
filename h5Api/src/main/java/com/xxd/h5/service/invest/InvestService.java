package com.xxd.h5.service.invest;

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
import com.xxd.h5.vo.coupon.CouponVO;
import com.xxd.h5.vo.invest.InvestRecordVo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author zhangshengwen
 * @date 2018/1/19
 */
@Service
public class InvestService extends H5BaseService {


    public Message sortInvestRecordByDate(Headers headers, QueryStrings queryStrings) {

        Message message = apiUtil.get(ApiEnum.API_TRADECENTER_MYINVESTMENT_PRODUCTS, headers, queryStrings);

        if (message.getCode() != MessageStatus.SUCCESS.getStatus()) {
            return new ErrorMessage(message.getMessage());
        }
        InvestRecordVo investRecordVo = null;
        try {
            investRecordVo = JsonUtil.toObject(JsonUtil.toJSONObject(message.getData()), InvestRecordVo.class);
            List<InvestRecordVo.Item> items = investRecordVo.getItems();
            Collections.sort(items, new DateSort());
            return new SuccessMessage(investRecordVo);
        }catch (Exception e){
            return new SuccessMessage(new JSONObject());
        }
    }

    /**
     * 自定义时间排序
     */
    class DateSort implements Comparator<InvestRecordVo.Item> {
        @Override
        public int compare(InvestRecordVo.Item o1, InvestRecordVo.Item o2) {

            return Long.valueOf(o1.getAddDate()) >= Long.valueOf(o2.getAddDate()) ? 1 : 0;
        }
    }


}
