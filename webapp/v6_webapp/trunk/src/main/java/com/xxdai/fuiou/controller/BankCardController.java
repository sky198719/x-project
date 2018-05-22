package com.xxdai.fuiou.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.appro.bo.RealNameAppro;
import com.xxdai.common.BaseController;
import com.xxdai.common.DataResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.fuiou.service.PayAPIService;
import com.xxdai.person.bo.BankInfo;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 银行卡信息查询及验证接口
 */
@Controller(value = "FuiouBankCardController")
@RequestMapping(value = "/fuiou")
public class BankCardController extends BaseController {

    private static final Log log = LogFactory.getLog(BankCardController.class);

    @Resource(name = "payAPIService")
    private PayAPIService payAPIService;

    /**
     * 查询银行卡Bin信息
     */
    @RequestMapping(value = "/cardBinQuery", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String cardBinQuery(@RequestParam(value = "cardNo", required = false) String cardNo,
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
                returnJson.put("code", "-1");
                returnJson.put("data", "");
                returnJson.put("info", "用户银行卡不存在");
                return returnJson.toString();
            }
        }

        Map<String, String> resMap;
        try {
            resMap = payAPIService.cardBinQuery(cardNo);

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
            log.error("查询银行卡Bin信息错误", e);
            e.printStackTrace();

            returnJson.put("code", "-2");
            returnJson.put("data", "");
            returnJson.put("info", "查询银行卡Bin信息错误");
            return returnJson.toString();
        }
    }

    /**
     * 银行卡信息验证接口
     */
    @RequestMapping(value = "/bankCardCheck", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String bankCardCheck(@RequestParam(value = "userId", required = false) String userId,
                                @RequestParam(value = "bankId", required = false) Long bankId,
                                @RequestParam(value = "cardNo", required = false) String cardNo) {
        JSONObject returnJson = new JSONObject();
        // 查询实名认证信息
        JSONObject object1 = new JSONObject();
        object1.put("userId", userId);
        log.info("select realNameInfo req param = " + object1.toJSONString());
        String resultStr1 = accountOVQueryCXFService.queryRealNameByUserId(object1.toJSONString());
        log.info("resp = " + resultStr1);
        DataResponse dataResponse1 = JsonUtil.jsonToBean(resultStr1, DataResponse.class);
        RealNameAppro realName = null;
        if (dataResponse1.getData() != null) {
            realName = JsonUtil.jsonToBean(dataResponse1.getData().toString(), RealNameAppro.class);
        }
        if (realName == null || !"1".equals(realName.getStatus())) {
            returnJson.put("code", "-1");
            returnJson.put("data", "");
            returnJson.put("info", "您还未实名认证，请先去认证");
            return returnJson.toString();
        }

        if (StringUtils.isBlank(cardNo)) {
            WSModelResponse bankResp = null;
            BankInfo bankInfo= null;
            try {
                String str = personalCXFService.getUserBankById(String.valueOf(bankId));
                bankResp = JsonUtil.jsonToBean(str,WSModelResponse.class);
                bankInfo = (BankInfo)bankResp.getData();
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                returnJson.put("code", "-2");
                returnJson.put("data", "");
                returnJson.put("info", "查询用户银行卡失败");
                return returnJson.toString();
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


        Map<String, String> resMap;
        try {
            resMap = payAPIService.bankCardCheck(cardNo, "1".equals(realName.getIdCardType()) ? "0" : "1", realName.getIdCardNo(), realName.getRealName());

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
            log.error("银行卡信息验证接口错误", e);
            e.printStackTrace();

            returnJson.put("code", "-2");
            returnJson.put("data", "");
            returnJson.put("info", "银行卡信息验证接口错误");
            return returnJson.toString();
        }
    }

}
