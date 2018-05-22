package com.xxdai.lianlian.wap.service;

/**
 * 调用连连支付API接口
 */
public interface LianLianPayAPIService {

    /**
     * 银行卡Bin信息查询
     */
    String cardBinQuery(String cardNo) throws Exception;
}
