package com.xxdai.lianlian.wap.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.account.model.RechargeCallbackResultReq;
import com.xxdai.client.CXF_Factory;
import com.xxdai.common.BaseController;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.payrate.ws.PayRateWebService;
import com.xxdai.lianlian.wap.constant.LianlianConstant;
import com.xxdai.lianlian.wap.util.YinTongConfigUtil;
import com.xxdai.lianlian.wap.util.YinTongUtil;
import com.xxdai.pay.bo.PayRate;
import com.xxdai.util.Convert;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Controller
@RequestMapping(value = "/lianlian")
public class PayCallbackController extends BaseController {

    private static final Log log = LogFactory.getLog(PayCallbackController.class);
    private PayRateWebService payrateWebService = (PayRateWebService) CXF_Factory.getFactory(PayRateWebService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/payrateWebService").create();

    /**
     * wap支付结束后返回地址
     */
    @RequestMapping(value = "/fCallback", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ModelAndView fCallback(@RequestParam(value = "res_data", required = false) String res_data) throws IOException {
        log.info("lianlian page call req begin ....");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pay/lianlian/return_merchant");
        log.info("req param = " + res_data);

        // 处理中或者失败
        if (StringUtils.isBlank(res_data)) {
            modelAndView.addObject("status", "-1");
            return modelAndView;
        }

        boolean passSign = YinTongUtil.checkSign(res_data, YinTongConfigUtil.getInstance().getValue("yt_pub_key"), YinTongConfigUtil.getInstance().getValue("md5_key"));
        log.info("checkSign = "  + passSign);
        if (passSign) {
            JSONObject callbackJson = JSONObject.parseObject(res_data);
            String result_pay = callbackJson.getString("result_pay");
            log.info("result_pay = " +  result_pay);
            if ("SUCCESS".equals(result_pay)) {
                log.info("验签通过,check ok");
                String money_order = callbackJson.getString("money_order");
                DecimalFormat df = new DecimalFormat("###,##0.00");
                modelAndView.addObject("pay_amount", df.format(new BigDecimal(money_order)));
                modelAndView.addObject("status", "0");
            }
        } else {
            log.info("验签未通过,check fail");
            return modelAndView;
        }
        return modelAndView;
    }

    /**
     * wap支付异步通知接口
     */
    @RequestMapping(value = "/callback", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String callback(HttpServletRequest request) throws IOException {
        log.info("lianlian sync call req begin ....");
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String result = br.readLine();
        while (result != null) {
            sb.append(result);
            result = br.readLine();
        }
        log.info("sync req param:" + sb);
        boolean passSign = YinTongUtil.checkSign(sb.toString(), YinTongConfigUtil.getInstance().getValue("yt_pub_key"), YinTongConfigUtil.getInstance().getValue("md5_key"));
        log.info("checkSign=" + passSign);
        if (passSign) {
            JSONObject callbackJson = JSONObject.parseObject(sb.toString());
            String result_pay = callbackJson.getString("result_pay");
            log.info("result_pay = " + result_pay);
            if ("SUCCESS".equals(result_pay)) {
                log.info("验签通过,check ok");
                RechargeCallbackResultReq req = new RechargeCallbackResultReq();
                req.setAmt(callbackJson.getString("money_order"));
                req.setBankId("LL");
                req.setFlag("1");
                req.setIp(HttpUtil.getRealIpAddr(request));
                req.setOrderNo(callbackJson.getString("no_order"));
                req.setTrxId(callbackJson.getString("oid_paybill"));
                req.setPayPlatfrom("连连");
                req.setPartnerId(38L);
                req.setSignNo(callbackJson.getString("no_agree"));
                //req.setBankCode(Convert.lianlianToV6BankCode(callbackJson.getString("bank_code")));
                /**
                 * 20161018-使用SOAP接口方式获取bankCode，胡娜
                 */
                JSONObject obj = new JSONObject();
                obj.put("partnerId", LianlianConstant.SYS_PANTAERID_LL);
                obj.put("payCode", callbackJson.getString("bank_code"));
                String payRateStr = payrateWebService.queryPayRateByPidAndPayCode(obj.toJSONString());
                obj = JsonUtil.jsonToBean(payRateStr,JSONObject.class);
                PayRate payRate = obj.getObject("payRate",PayRate.class);
                if(payRate != null){
                    req.setBankCode(payRate.getBankCode());
                }

                log.info("rechargeCallback req param = " + JsonUtil.beanToJson(req));
                String resultStr = accountRechargeCXFService.rechargeCallbackResult(JsonUtil.beanToJson(req));
                log.info("rechargeCallback resp = " + resultStr);
                WSResponse resultRes = JsonUtil.jsonToBean(resultStr, WSResponse.class);
                if (resultRes.getResultCode() == 0) {
                    log.info("成功入库");
                    JSONObject returnJson = new JSONObject();
                    returnJson.put("ret_code", "0000");
                    returnJson.put("ret_msg", "交易成功");
                    return returnJson.toString();
                }
            }
        } else {
            log.info("验签未通过,check fail");
        }
        return null;
    }

}
