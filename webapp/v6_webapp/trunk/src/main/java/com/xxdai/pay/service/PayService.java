package com.xxdai.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.http.ApiUtil;
import com.xxdai.http.Headers;
import com.xxdai.http.Message;
import com.xxdai.http.StringForm;
import com.xxdai.util.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("payService")
public class PayService {
    private static final Log log = LogFactory.getLog(PayService.class);

    @Autowired
    private ApiUtil apiUtil;


    public Message selectAccountRecharge(BigDecimal money, String token,String userAgent) {

        try {

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_FRONT_END_H5")
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token",token)
                    .addHeader("User-Agent",userAgent);

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("accountCenter"));
            url.append("/account/recharge/doRecharge");

            JSONObject reqData = new JSONObject();
            reqData.put("platformSource", "webapp");
            reqData.put("rechargeAmount", money);
            reqData.put("rechargeType", 14);
            reqData.put("appRequest", 3);
            JSONObject json = new JSONObject();
            json.put("data", reqData);
            StringForm sf = new StringForm(json.toJSONString());
            sf.build();
            log.info("doRecharge req =" + json);

            Message msg = apiUtil.post(url.toString(),headers,sf);

            log.info("doRecharge resp = " + JSONObject.toJSONString(msg));

            return msg;
        } catch (Exception e) {
            log.error("doRecharge error ", e);
        }
        return null;
    }
}
