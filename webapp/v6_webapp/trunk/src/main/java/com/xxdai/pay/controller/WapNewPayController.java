package com.xxdai.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.appro.bo.RealNameAppro;
import com.xxdai.common.BaseController;
import com.xxdai.common.DataResponse;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.user.ws.User;
import com.xxdai.http.Message;
import com.xxdai.pay.service.PayService;
import com.xxdai.util.HttpTookit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;

/**
 * wap认证支付接口
 */
@Controller
@RequestMapping("/pay")
public class WapNewPayController extends BaseController {

    private static final Log log = LogFactory.getLog(WapNewPayController.class);

    @Autowired
    private PayService payService;

    @RequestMapping(value = "/submit")
    public ModelAndView paySubmit(HttpServletRequest request,HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        //传入参数交易金额（moneyOrder）
        String payMoney = request.getParameter("moneyOrder");
        if(payMoney == null || "".equals(payMoney)) {
            modelAndView.setViewName("pay/back");
            return modelAndView;
        }

        BigDecimal moneyOrder = new BigDecimal(payMoney);
        log.info(String.format("moneyOrder=%s",moneyOrder));

        modelAndView.setViewName("pay/fuiou/newPay_submit");
        String msg = "抱歉，您的充值申请失败，请重新尝试或者联系客服。";
        try{
            Object object = session.getAttribute("loginUser");
            if(object == null) {
                msg = "您的会话失效，请重新登录";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }
            User user = (User) object;

            // 校验输入的金额
            if (moneyOrder == null) {
                msg = "请输入金额";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }
            if (moneyOrder.compareTo(new BigDecimal("0.01")) < 0) {
                msg = "您的充值金额需要大于0.01元，请返回重新尝试";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }
            moneyOrder = moneyOrder.setScale(2, BigDecimal.ROUND_FLOOR);

            // 查询实名认证信息
            JSONObject object1 = new JSONObject();
            object1.put("userId", user.getUserId());
            log.info("select realNameInfo req param = "  + object1.toJSONString());
            String resultStr1 = accountOVQueryCXFService.queryRealNameByUserId(object1.toJSONString());
            log.info("resp = " + resultStr1);
            DataResponse dataResponse1 = JsonUtil.jsonToBean(resultStr1, DataResponse.class);
            RealNameAppro realName = null;
            if (dataResponse1.getData() != null) {
                realName = JsonUtil.jsonToBean(dataResponse1.getData().toString(), RealNameAppro.class);
            }
            if (realName == null || !"1".equals(realName.getStatus())) {
                msg = "您还未实名认证，请先去认证";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                modelAndView.addObject("code", -1);// 未进行实名认证
                return modelAndView;
            }

            String token = HttpTookit.getCookieValue(request, Constant.USERTOKEN);
            log.info("payAPIService.selectAccountRecharge param : money_order = "  + moneyOrder + ",token="+token);
            Message result = payService.selectAccountRecharge(moneyOrder,token,HttpTookit.getUserAgent(request));
            log.info("result = " + result);

            if(result == null) {
                modelAndView.addObject("msg", "构建充值请求失败，请重新成功尝试");
                modelAndView.addObject("code", 100);// 未进行实名认证
                return modelAndView;
            }

            JSONObject data = (JSONObject)result.getData();

            if(result.getCode() != 200000 || data.getInteger("code") != 0) {
                String msgStr = result.getCode() == 200000 ? data.getString("message") : result.getMessage();
                int msgCode = result.getCode() == 200000 ? data.getInteger("code") : result.getCode();
                modelAndView.addObject("msg", msgStr);
                modelAndView.addObject("code", msgCode);// 未进行实名认证
                return modelAndView;
            }

            JSONObject dData = data.getJSONObject("data");
            String channel = dData.getString("channel");
            if("lianlian".equalsIgnoreCase(channel)) {
                modelAndView.setViewName("pay/lianlian/newPay_submit");
                modelAndView.addObject("req_data",dData.getString("req_data"));
                modelAndView.addObject("requestUrl",dData.getString("requestUrl"));
            } else {
                modelAndView.addObject("mchnt_cd", dData.getString("mchnt_cd"));
                modelAndView.addObject("mchnt_txn_ssn", dData.getString("mchnt_txn_ssn"));
                modelAndView.addObject("login_id", dData.getString("login_id"));
                modelAndView.addObject("amt", dData.getString("amt"));
                modelAndView.addObject("page_notify_url", dData.getString("page_notify_url"));
                modelAndView.addObject("signature",dData.getString("signature"));            // 支付地址
                modelAndView.addObject("payurl", dData.getString("requestUrl"));
                modelAndView.addObject("back_notify_url", dData.getString("back_notify_url"));
            }

            msg = null;

            Map<String,Object> map = modelAndView.getModel();
            StringBuffer logBuf = new StringBuffer();
            logBuf.append("\npaySubmit param :\n");
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                logBuf.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
            }
            log.info(logBuf);
            return modelAndView;
        }catch (Exception e) {
            log.error("Fuiou paySubmit error. " + msg, e);
            modelAndView.addObject("msg", msg);
            return modelAndView;
        }
    }
}
