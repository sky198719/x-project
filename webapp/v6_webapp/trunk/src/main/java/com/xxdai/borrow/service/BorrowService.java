package com.xxdai.borrow.service;

import com.xxdai.constant.Constant;
import com.xxdai.http.ApiUtil;
import com.xxdai.http.ErrorMessage;
import com.xxdai.http.Headers;
import com.xxdai.http.Message;
import com.xxdai.util.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowService {
    private Log log = LogFactory.getLog(BorrowService.class);
    protected RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();
    CloseableHttpClient httpClient = HttpClients.createDefault();
    @Autowired
    private ApiUtil apiUtil;

    public Message queryBorrowList(int currentPage, int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/investBiz/queryBorrowList?currentPage=").append(currentPage).append("&pageSize=").append(pageSize);
            return apiUtil.get(url.toString(),headers);
        }catch (Exception e) {
            log.error("getProduct error",e);
            return new ErrorMessage("未知异常");
        }
    }
}
