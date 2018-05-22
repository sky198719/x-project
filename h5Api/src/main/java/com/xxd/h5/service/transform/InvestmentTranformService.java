package com.xxd.h5.service.transform;

import com.alibaba.fastjson.JSONObject;
import com.xxd.common.bo.Message;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.common.constants.Constants;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.service.H5UrlService;
import com.xxd.h5.service.finance.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhangshengwen
 * @date 2017/12/8
 */
@Service
public class InvestmentTranformService extends H5BaseService {

    @Autowired
    FinanceService financeService;

    @Autowired
    H5UrlService h5UrlService;
    /**
     * 产品投资详情.
     *
     * @param clientId
     * @param joinId      投资记录id.
     * @param productType
     * @param token
     * @return
     * @throws CoreException
     * @throws ServiceException
     */
    public Message productInvestDetail(String clientId, String joinId, int productType, String token, String userAgent) throws CoreException, ServiceException {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", userAgent);
        QueryStrings queryStrings = QueryStrings.create().setPath("/").addPath(joinId);
        switch (productType) {
            case Constants.PRODUCT_BBGS_TYPE:
                return fillProtocolUrl(apiUtil.get(ApiEnum.API_INVESTMENT_INVESTS_BBGS, headers, queryStrings), token, joinId);
            case Constants.PRODUCT_QTDS_TYPE:
                return fillProtocolUrl(apiUtil.get(ApiEnum.API_INVESTMENT_INVESTS_QTDS, headers, queryStrings), token, joinId);
            case Constants.PRODUCT_30_DAY_TYPE:
                return fillProtocolUrl(apiUtil.get(ApiEnum.API_INVESTMENT_INVESTS_SSCP30T, headers, queryStrings), token, joinId);
            case Constants.PRODUCT_XYB_TYPE:
                return fillProtocolUrl(apiUtil.get(ApiEnum.API_INVESTMENT_INVESTS_XYB, headers, queryStrings), token, joinId);
            case Constants.PRODUCT_YJDJ_TYPE:
                return fillProtocolUrl(apiUtil.get(ApiEnum.API_INVESTMENT_INVESTS_YJDJ, headers, queryStrings), token, joinId);
            case Constants.PRODUCT_YYP_TYPE:
                return fillProtocolUrl(apiUtil.get(ApiEnum.API_INVESTMENT_INVESTS_YYP, headers, queryStrings), token, joinId);
            case Constants.PRODUCT_BID_TYPE:
                headers.addHeader("token", token);
                Message message = apiUtil.get(ApiEnum.API_INVESTMENT_INVESTS_BID_AND_ZQ, headers, queryStrings);
                if (message != null && MessageStatus.SUCCESS.getStatus() == message.getCode()) {
                    JSONObject jsonObject = JsonUtil.toJSONObject(message.getData());
                    Object objectLabel = jsonObject.get("label");
                    if (objectLabel != null) {
                        String labelName = transformService.transformLabel2LabelName(objectLabel.toString());
                        jsonObject.put("labelName", labelName);
                    }
                }
                return fillProtocolUrl(message, token, joinId);
            default:
                throw new ServiceException("error parameter");
        }
    }

    /**
     * 投资详情加入服务协议的h5页面url.
     *
     * @param message
     * @param token
     * @return
     */
    public Message fillProtocolUrl(Message message, String token, String productJoinId) {
        if (message != null && message.getCode() == MessageStatus.SUCCESS.getStatus()) {
            JSONObject data = JsonUtil.toJSONObject(message.getData());
            // 产品类型
            int productType = financeService.getProductType(data);
            String productId = data.getString("productId");
            // 产品服务协议
            data.put("productProtocolH5url", h5UrlService.getProductProtocol(productType, token, productId, productJoinId));
            // 散标债券合同
            data.put("productContractH5url", h5UrlService.getProductContractUrl());
            if (productType == Constants.PRODUCT_BID_TYPE || productType == Constants.PRODUCT_ZQ_TYPE) {
                // 债券转让协议
                data.put("bidTransferH5url", h5UrlService.getBidTransferUrl(productType, productJoinId, data.getInteger("status")));
            }
        }
        return message;
    }

}
