package com.xxdai.fuiou.service.impl;

import com.xxdai.fuiou.URIList.PayAPIURIList;
import com.xxdai.fuiou.service.PayAPIService;
import com.xxdai.fuiou.util.common.YJPayUtil;
import com.xxdai.fuiou.util.encrypt.Digest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Service("payAPIService")
public class PayAPIServiceImpl implements PayAPIService {

    private static final Log log = LogFactory.getLog(PayAPIServiceImpl.class);

	private ResourceBundle resb = ResourceBundle.getBundle("fuiou");
	// 从配置文件读取富友分配的商户秘钥
	private String md5Key = resb.getString("fuiou.md5_key");
    // 商户账户编号
    private String merchantAccount = resb.getString("fuiou.merchantaccount");
    // 从配置文件读取支付API接口URL前缀
	private String urlPrefix = resb.getString("fuiou.pay_urlprefix");

	@Override
	public Map<String, String> cardBinQuery(String cardNo) throws Exception {
		// 完整的请求地址
		String requestURL = urlPrefix + PayAPIURIList.FUIOU_CARD_BIN_QUERY.getValue();

        List<String> paramList = new ArrayList<String>();
        paramList.add(merchantAccount);
        paramList.add(cardNo);

        StringBuilder paramStr = new StringBuilder();
        for (String s : paramList) {
            paramStr.append(s).append("|");
        }
        paramStr.append(md5Key);

        // 生成MD5签名
        String sign = Digest.signMD5(paramStr.toString());

        // 封装请求参数
        StringBuilder sb = new StringBuilder();
        sb.append("<FM>")
                .append("<MchntCd>")
                    .append(merchantAccount)
                .append("</MchntCd>")
                .append("<Ono>")
                    .append(cardNo)
                .append("</Ono>")
                .append("<Sign>")
                    .append(sign)
                .append("</Sign>")
        .append("</FM>");

        YJPayUtil yjPayUtil = new YJPayUtil();
		// 请求一键支付接口
        String resultStr = yjPayUtil.payAPIRequest(requestURL, sb.toString(), true);
        // 将一键支付返回的结果进行验签，解密并返回
        Map<String, String> resMap = yjPayUtil.checkResult(resultStr);

        List<String> resList = new ArrayList<String>();
        resList.add(resMap.get("Rcd"));

        StringBuilder resStr = new StringBuilder();
        for (String s : resList) {
            resStr.append(s).append("|");
        }
        resStr.append(md5Key);

        // 生成MD5签名
        String resSign = Digest.signMD5(resStr.toString());
        if (resMap.get("Sign").equals(resSign)) {
            log.info("验签成功");
            return resMap;
        } else {
            log.info("验签失败");
            return null;
        }
	}

    @Override
    public String createOrder(BigDecimal moneyOrder) throws Exception {
        // 把元转换为分
        Long moneyOrderFen = moneyOrder.multiply(new BigDecimal("100")).longValue();

        // 完整的请求地址
        String requestURL = urlPrefix + PayAPIURIList.FUIOU_CREATE_ORDER.getValue();

        List<String> paramList = new ArrayList<String>();
        paramList.add(merchantAccount);
        paramList.add(moneyOrderFen + "");

        StringBuilder paramStr = new StringBuilder();
        for (String s : paramList) {
            paramStr.append(s).append("|");
        }
        paramStr.append(md5Key);

        // 生成MD5签名
        String sign = Digest.signMD5(paramStr.toString());

        // 封装请求参数
        StringBuilder sb = new StringBuilder();
        sb.append("<FM>")
                .append("<MchntCd>")
                    .append(merchantAccount)
                .append("</MchntCd>")
                .append("<Amt>")
                    .append(moneyOrderFen)
                .append("</Amt>")
                .append("<Rmk1>")
                    .append("hide")
                .append("</Rmk1>")
                .append("<Sign>")
                    .append(sign)
                .append("</Sign>")
            .append("</FM>");

        YJPayUtil yjPayUtil = new YJPayUtil();
        // 请求一键支付接口
        String resultStr = yjPayUtil.payAPIRequest(requestURL, sb.toString(), true);

        // 将一键支付返回的结果进行验签，解密并返回
        Map<String, String> resMap = yjPayUtil.checkResult(resultStr);

        List<String> resList = new ArrayList<String>();
        resList.add(resMap.get("Rcd"));
        resList.add(resMap.get("OrderId"));

        StringBuilder resStr = new StringBuilder();
        for (String s : resList) {
            resStr.append(s).append("|");
        }
        resStr.append(md5Key);

        // 生成MD5签名
        String resSign = Digest.signMD5(resStr.toString());
        if (resMap.get("Sign").equals(resSign)) {
            log.info("验签成功");
            return resMap.get("OrderId");
        } else {
            log.info("验签失败");
            return null;
        }
    }

    @Override
    public Map<String, String> queryOrderId(String orderId) throws Exception {
        // 完整的请求地址
        String requestURL = urlPrefix + PayAPIURIList.FUIOU_QUERY_ORDER_ID.getValue();

        List<String> paramList = new ArrayList<String>();
        paramList.add(merchantAccount);
        paramList.add(orderId);

        StringBuilder paramStr = new StringBuilder();
        for (String s : paramList) {
            paramStr.append(s).append("|");
        }
        paramStr.append(md5Key);

        // 生成MD5签名
        String sign = Digest.signMD5(paramStr.toString());

        // 封装请求参数
        StringBuilder sb = new StringBuilder();
        sb.append("<FM>")
                .append("<MchntCd>")
                    .append(merchantAccount)
                .append("</MchntCd>")
                .append("<OrderId>")
                    .append(orderId)
                .append("</OrderId>")
                .append("<Sign>")
                    .append(sign)
                .append("</Sign>")
                .append("</FM>");

        YJPayUtil yjPayUtil = new YJPayUtil();
        // 请求一键支付接口
        String resultStr = yjPayUtil.payAPIRequest(requestURL, sb.toString(), true);

        // 将一键支付返回的结果进行验签，解密并返回
        Map<String, String> resMap = yjPayUtil.checkResult(resultStr);

        List<String> resList = new ArrayList<String>();
        resList.add(resMap.get("Rcd"));

        StringBuilder resStr = new StringBuilder();
        for (String s : resList) {
            resStr.append(s).append("|");
        }
        resStr.append(md5Key);

        // 生成MD5签名
        String resSign = Digest.signMD5(resStr.toString());
        if (resMap.get("Sign").equals(resSign)) {
            log.info("验签成功");
            return resMap;
        } else {
            log.info("验签失败");
            return null;
        }
    }

    @Override
    public Map<String, String> bankCardCheck(String cardNo, String idCardType, String idCardNo, String realName) throws Exception {
        // 完整的请求地址
        String requestURL = urlPrefix + PayAPIURIList.FUIOU_BANK_CARD_CHECK.getValue();

        List<String> paramList = new ArrayList<String>();
        paramList.add(merchantAccount);
        paramList.add(cardNo);
        paramList.add(idCardType);

        StringBuilder paramStr = new StringBuilder();
        for (String s : paramList) {
            paramStr.append(s).append("|");
        }
        paramStr.append(md5Key);

        // 生成MD5签名
        String sign = Digest.signMD5(paramStr.toString());

        // 封装请求参数
        StringBuilder sb = new StringBuilder();
        sb.append("<FM>")
                .append("<MchntCd>")
                    .append(merchantAccount)
                .append("</MchntCd>")
                .append("<Ono>")
                    .append(cardNo)
                .append("</Ono>")
                .append("<OCerTp>")
                    .append(idCardType)
                .append("</OCerTp>")
                .append("<Onm>")
                    .append(realName)
                .append("</Onm>")
                .append("<OCerNo>")
                    .append(idCardNo)
                .append("</OCerNo>")
                .append("<Sign>")
                    .append(sign)
                .append("</Sign>")
                .append("</FM>");

        YJPayUtil yjPayUtil = new YJPayUtil();
        // 请求一键支付接口
        String resultStr = yjPayUtil.payAPIRequest(requestURL, sb.toString(), true);

        // 将一键支付返回的结果进行验签，解密并返回
        Map<String, String> resMap = yjPayUtil.checkResult(resultStr);

        List<String> resList = new ArrayList<String>();
        resList.add(resMap.get("Rcd"));

        StringBuilder resStr = new StringBuilder();
        for (String s : resList) {
            resStr.append(s).append("|");
        }
        resStr.append(md5Key);

        // 生成MD5签名
        String resSign = Digest.signMD5(resStr.toString());
        if (resMap.get("Sign").equals(resSign)) {
            log.info("验签成功");
            return resMap;
        } else {
            log.info("验签失败");
            return null;
        }
    }

}
