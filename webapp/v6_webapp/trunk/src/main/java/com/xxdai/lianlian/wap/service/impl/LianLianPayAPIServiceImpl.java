package com.xxdai.lianlian.wap.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.lianlian.share.security.Md5Algorithm;
import com.xxdai.lianlian.share.security.RSAUtil;
import com.xxdai.lianlian.share.util.FuncUtils;
import com.xxdai.lianlian.wap.client.HttpRequestSimple;
import com.xxdai.lianlian.wap.service.LianLianPayAPIService;
import com.xxdai.lianlian.wap.util.YinTongConfigUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service("lianLianPayAPIService")
public class LianLianPayAPIServiceImpl implements LianLianPayAPIService {
    private static final Log log = LogFactory.getLog(LianLianPayAPIServiceImpl.class);
    protected RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();
    CloseableHttpClient httpClient = HttpClients.createDefault();
	@Override
	public String cardBinQuery(String cardNo) throws Exception {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        if (!FuncUtils.isNull(cardNo)) {
            map.put("card_no", cardNo);
        }
        map.put("oid_partner", YinTongConfigUtil.getInstance().getValue("oid_partner"));
        map.put("sign_type", YinTongConfigUtil.getInstance().getValue("sign_type"));
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append("&");
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        String sign_src = sb.toString();
        if (sign_src.startsWith("&")) {
            sign_src = sign_src.substring(1);
        }
        log.info("原始参数字符串:" + sign_src);
        String sign = "";
        if ("RSA".equals(YinTongConfigUtil.getInstance().getValue("sign_type"))) {
            sign = RSAUtil.sign(YinTongConfigUtil.getInstance().getValue("trader_pri_key"), sign_src);
        } else {
            sign_src += "&key=" + YinTongConfigUtil.getInstance().getValue("md5_key");
            try {
                sign = Md5Algorithm.getInstance().md5Digest(sign_src.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                log.error("MD5加密出错");
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("card_no", cardNo);
        jsonObject.put("oid_partner", YinTongConfigUtil.getInstance().getValue("oid_partner"));
        jsonObject.put("sign_type", YinTongConfigUtil.getInstance().getValue("sign_type"));
        jsonObject.put("sign", sign);
        String req_data = JSON.toJSONString(jsonObject);
        log.info("请求参数JSON串:req_data=" + req_data);

        HttpRequestSimple httpClient = new HttpRequestSimple();
        /**
         * 卡bin查询接口 旧：https://yintong.com.cn/traderapi/bankCheck.htm
         * https://yintong.com.cn/traderapi/bankcardbin.htm
         * https://yintong.com.cn/traderapi/bankcardquery.htm
         * 新：https://queryapi.lianlianpay.com/bankcardbin.htm
         */
        String resJson = httpClient.postSendHttp("https://queryapi.lianlianpay.com/bankcardbin.htm", req_data);
        log.info("响应JSON串:" + resJson);
        return resJson;
	}
}
