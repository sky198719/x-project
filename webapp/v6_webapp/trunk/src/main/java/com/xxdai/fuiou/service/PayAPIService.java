package com.xxdai.fuiou.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 调用富友支付API接口
 */
public interface PayAPIService {

    /**
     * 银行卡Bin信息查询
     */
    Map<String, String> cardBinQuery(String cardNo) throws Exception;

    /**
     * 创建订单
     */
    String createOrder(BigDecimal moneyOrder) throws Exception;

    /**
     * 商户支付结果查询
     */
    Map<String, String> queryOrderId(String orderId) throws Exception;

    /**
     * 银行卡信息验证接口
     */
    Map<String, String> bankCardCheck(String cardNo, String idCardType, String idCardNo, String realName) throws Exception;

}
