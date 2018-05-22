package com.xxdai.person.service;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.constant.Constant;
import com.xxdai.http.ApiUtil;
import com.xxdai.http.Headers;
import com.xxdai.http.Message;
import com.xxdai.util.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Service
public class PersonService {
    private Log log = LogFactory.getLog(PersonService.class);
    @Autowired
    private ApiUtil apiUtil;

    public JSONObject userBandedBankOutSideUse(String token,String userAgent) {
        JSONObject result = new JSONObject();
        try {
            log.info("userBandedBankOutSideUse token=" + token + ",userAgent="+userAgent);
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_V6_WEBAPP")
                    .addHeader("token", token)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("userCenter"));
            url.append("/user/bank/userBandedBankOutSideUse");

            Message msg = apiUtil.get(url.toString(),headers);
            log.info("userCenter user/bank/userBandedBankOutSideUse resp = " + JSONObject.toJSONString(msg));

            if (msg != null && 200000 == msg.getCode()) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",msg.getCode());
                result.put("message","查询不到");
            }
        } catch (Exception e) {
            log.error("userBandedBankOutSideUse error ",e);
            result.put("code",401);
            result.put("message","异常");
        } finally {
            return result;
        }
    }

    public JSONObject userCheckingBankCardInfo(String token,String userAgent) {
        JSONObject result = new JSONObject();
        try {
            log.info("userBandedBankOutSideUse token=" + token + ",userAgent="+userAgent);
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_V6_WEBAPP")
                    .addHeader("token", token)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("userCenter"));
            url.append("/user/bank/userCheckingBankCardInfo");

            Message msg = apiUtil.get(url.toString(),headers);
            log.info("userCenter user/bank/userCheckingBankCardInfo resp = " + JSONObject.toJSONString(msg));

            if (msg != null && 200000 == msg.getCode()) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",400);
                result.put("message","查询不到");
            }
        } catch (Exception e) {
            log.error("userCheckingBankCardInfo error ",e);
            result.put("code",401);
            result.put("message","异常");
        } finally {
            return result;
        }
    }
}
