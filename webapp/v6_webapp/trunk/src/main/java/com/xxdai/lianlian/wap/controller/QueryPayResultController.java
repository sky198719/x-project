package com.xxdai.lianlian.wap.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.lianlian.share.security.Md5Algorithm;
import com.xxdai.lianlian.share.security.RSAUtil;
import com.xxdai.lianlian.share.util.FuncUtils;
import com.xxdai.lianlian.wap.client.HttpRequestSimple;
import com.xxdai.lianlian.wap.util.YinTongConfigUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 商户支付结果查询
 */
@Controller
@RequestMapping(value = "/lianlian")
public class QueryPayResultController {

    private static final Log log = LogFactory.getLog(QueryPayResultController.class);

    @RequestMapping(value = "/queryPayResult", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryPayResult(@RequestParam("orderNo") String orderNo) {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        if (!FuncUtils.isNull(orderNo)) {
            map.put("no_order", orderNo);
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
                sign = Md5Algorithm.getInstance().md5Digest(sign_src.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                log.error("MD5加密出错");
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("no_order", orderNo);
        jsonObject.put("oid_partner", YinTongConfigUtil.getInstance().getValue("oid_partner"));
        jsonObject.put("sign_type", YinTongConfigUtil.getInstance().getValue("sign_type"));
        jsonObject.put("sign", sign);
        String req_data = JSON.toJSONString(jsonObject);
        log.info("请求参数JSON串:req_data=" + req_data);

        HttpRequestSimple httpClient = new HttpRequestSimple();
        /**
         * 20161014-连连支付更换地址
         * 支付查询接口 旧：https://yintong.com.cn/traderapi/orderquery.htm
         * 新：https://queryapi.lianlianpay.com/orderquery.htm
         */
        String resJson = httpClient.postSendHttp("https://queryapi.lianlianpay.com/orderquery.htm", req_data);
        log.info("响应JSON串:" + resJson);
        return resJson;
    }

}
