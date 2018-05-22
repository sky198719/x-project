package com.xxdai.lianlian.wap.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.common.BaseController;
import com.xxdai.common.DataResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.lianlian.share.security.Md5Algorithm;
import com.xxdai.lianlian.share.security.RSAUtil;
import com.xxdai.lianlian.share.util.FuncUtils;
import com.xxdai.lianlian.wap.client.HttpRequestSimple;
import com.xxdai.lianlian.wap.util.YinTongConfigUtil;
import com.xxdai.person.bo.BankInfo;
import org.apache.commons.lang3.StringUtils;
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
 * 银行卡卡BIN查询
 */
@Controller
@RequestMapping(value = "/lianlian")
public class BankCardCheckController extends BaseController {

    private static final Log log = LogFactory.getLog(BankCardCheckController.class);

    @RequestMapping(value = "/bankCardCheck", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String bankCardCheck(@RequestParam(value = "cardNo", required = false) String cardNo,
                                @RequestParam(value = "bankId", required = false) Long bankId) {
        JSONObject returnJson = new JSONObject();
        if (StringUtils.isBlank(cardNo)) {
            JSONObject object = new JSONObject();
            object.put("bankId", bankId);
            String resultStr = personalCXFService.queryBankInfoByUserId(object.toJSONString());
            DataResponse dataResponse2 = JsonUtil.jsonToBean(resultStr, DataResponse.class);
            BankInfo bankInfo = null;
            if (dataResponse2.getData() != null) {
                bankInfo = JsonUtil.jsonToBean(dataResponse2.getData().toString(), BankInfo.class);
            }
            if (bankInfo != null) {
                cardNo = bankInfo.getBankAccount();
            } else {
                returnJson.put("code", "-4");
                returnJson.put("data", "");
                returnJson.put("info", "用户银行卡不存在");
                return returnJson.toString();
            }
        }

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

        JSONObject callbackJson = JSON.parseObject(resJson);
        String ret_code = callbackJson.getString("ret_code");
        String card_no = callbackJson.getString("card_no");
        String bank_code = callbackJson.getString("bank_code");
        String bank_name = callbackJson.getString("bank_name");
        String card_type = callbackJson.getString("card_type"); // 2-储蓄卡 3-信用卡

        if ("0".equals(ret_code)) {
            if ("3".equals(card_type)) {
                returnJson.put("code", "-3");
                returnJson.put("data", "");
                returnJson.put("info", "请使用储蓄卡充值");
                return returnJson.toString();
            }
            JSONObject bankJson = new JSONObject();
            bankJson.put("card_no", card_no);
            bankJson.put("bank_code", bank_code);
            bankJson.put("bank_name", bank_name);
            bankJson.put("card_type", card_type);

            returnJson.put("code", "0");
            returnJson.put("data", bankJson);
            returnJson.put("info", "银行卡号正确");
            return returnJson.toString();
        }
        returnJson.put("code", "-1");
        returnJson.put("data", "");
        returnJson.put("info", "银行卡号不正确");
        return returnJson.toString();
    }

}
