package com.xxd.h5.service.account;

import com.alibaba.fastjson.JSONObject;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.service.H5UrlService;
import com.xxd.h5.service.user.UserService;
import com.xxd.h5.vo.account.AccountRealNameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangshengwen
 */
@Service
public class AccountService extends H5BaseService {

    @Autowired
    H5UrlService h5UrlService;

    @Autowired
    private UserService userService;

    /**
     * 账户相关信息.
     * @param clientId
     * @param token
     * @return
     * @throws CoreException
     * @throws ServiceException
     * @throws Exception
     */
    public Message getAccountInfo(String clientId, String token, String userAgent) throws CoreException, ServiceException, Exception {
        Headers headers = Headers.createHeaders()
                .addHeader("token", token)
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", userAgent);
        Message accountInfoMessage = apiUtil.get(ApiEnum.API_USERCENTER_ACCOUNT_INFO, headers);
        JSONObject accountData = JsonUtil.toJSONObject(accountInfoMessage.getData()).getJSONObject("data");
        AccountRealNameVO vo = JsonUtil.toObject(accountData, AccountRealNameVO.class);


        Message userInfoMessage = apiUtil.get(ApiEnum.API_USERCENTER_USERINFO, headers);
        if (userInfoMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException("bad request");
        }

        JSONObject userInfoJsonObject = JsonUtil.toJSONObject(userInfoMessage.getData()).getJSONObject("data");

        vo.setNickName(userInfoJsonObject.getString("username"));
        vo.setPhoneNum(userInfoJsonObject.getString("mobile"));
        vo.setUserAgreementUrl(h5UrlService.getUserAgreementUrl());
        return new SuccessMessage(vo);
    }
}
