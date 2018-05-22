package com.xxdai.fuiou.URIList;

public enum PayAPIURIList {

    /**
     * 创建订单
     */
    FUIOU_CREATE_ORDER("/findPay/createOrder.pay"),

    /**
     * 支付地址
     */
    FUIOU_PAY_URL("/timbnew/timb01.pay"),

    /**
     * 银行卡Bin信息查询
     */
    FUIOU_CARD_BIN_QUERY("/findPay/cardBinQuery.pay"),

    /**
     * 商户支付结果查询
     */
    FUIOU_QUERY_ORDER_ID("/findPay/queryOrderId.pay"),

    /**
     * 银行卡信息验证接口
     */
    FUIOU_BANK_CARD_CHECK("/nocardPay/checkCard.pay");

    private String value;

    private PayAPIURIList(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
