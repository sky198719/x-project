package com.xxdai.fuiou.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.fuiou.service.PayAPIService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商户支付结果查询
 */
@Controller(value = "FuiouQueryPayResultController")
@RequestMapping(value = "/fuiou")
public class QueryPayResultController {

    private static final Log log = LogFactory.getLog(QueryPayResultController.class);

    @Resource(name = "payAPIService")
    private PayAPIService payAPIService;

    @RequestMapping(value = "/queryPayResult", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryPayResult(@RequestParam(value = "orderNo", required = true) String orderId) {
        log.info("订单号:" + orderId);
        JSONObject returnJson = new JSONObject();
        Map<String, String> resMap;
        try {
            resMap = payAPIService.queryOrderId(orderId);

            if (resMap != null) {
                JSONObject dateJson = new JSONObject();
                for (Object o : resMap.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    dateJson.put(entry.getKey().toString(), entry.getValue());
                }

                returnJson.put("code", "0");
                returnJson.put("data", dateJson);
                returnJson.put("info", "成功");
                return returnJson.toString();
            } else {
                returnJson.put("code", "-9");
                returnJson.put("data", "");
                returnJson.put("info", "验签失败");
                return returnJson.toString();
            }
        } catch (Exception e) {
            log.error("商户支付结果查询错误", e);
            e.printStackTrace();

            returnJson.put("code", "-1");
            returnJson.put("data", "");
            returnJson.put("info", "商户支付结果查询错误");
            return returnJson.toString();
        }
    }

}
