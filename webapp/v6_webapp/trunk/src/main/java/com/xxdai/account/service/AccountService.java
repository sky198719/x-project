package com.xxdai.account.service;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.http.ApiUtil;
import com.xxdai.http.Headers;
import com.xxdai.http.Message;
import com.xxdai.http.StringForm;
import com.xxdai.user.service.UserService;
import com.xxdai.util.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.Charset;

@Service
public class AccountService {
    private Log log = LogFactory.getLog(UserService.class);

    @Autowired
    private ApiUtil apiUtil;

    public JSONObject initWithdraw(String token,String userAgent) {
        JSONObject result = null;
        try {

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_FRONT_END_H5")
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token",token)
                    .addHeader("User-Agent",userAgent);

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("accountCenter"));
            url.append("/account/withdraw/initWithdraw");

            Message msg = apiUtil.get(url.toString(),headers);

            log.info("accountCenter account/withdraw/initWithdraw resp = " + JSONObject.toJSONString(msg));

            if (200000 == msg.getCode()) {
                JSONObject data = (JSONObject)msg.getData();
                String code = data.getString("code");
                if("0".equals(code)) {
                    result = data.getJSONObject("data");
                }
            }
        } catch (Exception e) {
            log.error("getUser error ",e);
        } finally {
            return result;
        }
    }

    public JSONObject doWithdrawCash(String token, String money,String userAgent){
        JSONObject result = new JSONObject();
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_FRONT_END_H5")
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token",token)
                    .addHeader("User-Agent",userAgent);

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("accountCenter"));
            url.append("/account/withdraw/doWithdrawCash");


            JSONObject reqData = new JSONObject();
            reqData.put("withdrawSource", "webapp");
            reqData.put("withdrawCashAmount", money);
            JSONObject json = new JSONObject();
            json.put("data", reqData);
            StringForm sf = new StringForm(json.toJSONString());
            sf.build();
            log.info("req =" + json);

            Message msg = apiUtil.post(url.toString(),headers,sf);


            log.info("account/withdraw/doWithdrawCash resp = " + JSONObject.toJSONString(msg));

            if (200000 == msg.getCode()) {
                result = (JSONObject)msg.getData();
            } else {
                result.put("code",msg.getCode());
                JSONObject obj = (JSONObject)msg.getData();
                String message = obj.getString("message");
                if(msg.getCode() == 200305) {
                    message = "您的账号在别处已登录，请重新登录";
                }

                result.put("message",message);
            }
        }catch (Exception e) {
            log.error("doWithdrawCash error",e);
            result.put("code",400);
            result.put("message","提现异常，请重新尝试");
        } finally {
            return result;
        }
    }
}
