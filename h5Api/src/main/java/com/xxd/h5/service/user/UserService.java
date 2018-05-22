package com.xxd.h5.service.user;

import com.alibaba.fastjson.JSONObject;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.common.enums.MenuTypeStatus;
import com.xxd.h5.service.ApiMenuService;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.service.coupon.CouponService;
import com.xxd.h5.vo.config.MenuVO;
import com.xxd.h5.vo.coupon.CouponVO;
import com.xxd.h5.vo.user.UserInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户Service.
 *
 * @author zhangshengwen
 */
@Service
public class UserService extends H5BaseService {

    @Autowired
    private ApiMenuService apiMenuService;
    @Autowired
    private CouponService couponService;

    public Message getUserInfo(String clientId, String token, String userAgent) throws CoreException, ServiceException, Exception {

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setToken(token);
        // 用户中心-用户信息
        Headers headers = Headers.createHeaders()
                .addHeader("token", token)
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", userAgent);
        Message userInfoMessage = apiUtil.get(ApiEnum.API_USERCENTER_USERINFO, headers);
        if (userInfoMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException(userInfoMessage.getMessage());
        }
        JSONObject userInfoJsonObject = JsonUtil.toJSONObject(userInfoMessage.getData()).getJSONObject("data");

        userInfoVO.setUserId(userInfoJsonObject.getString("userid"));
        userInfoVO.setUserName(userInfoJsonObject.getString("username"));
        userInfoVO.setMobile(userInfoJsonObject.getString("mobile"));
        userInfoVO.setLoginId(userInfoJsonObject.getString("loginId"));
        int hasPayPassword = userInfoJsonObject.getInteger("payPassword");


        Message riskMessage = apiUtil.get(ApiEnum.API_USERCENTER_USER_QUESTION_INFOS, headers);
        if (riskMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException(riskMessage.getMessage());
        }
        JSONObject riskJsonObject = JsonUtil.toJSONObject(riskMessage.getData());

        userInfoVO.setTypeName(riskJsonObject.getString("typeName"));
        userInfoVO.setTestCount(riskJsonObject.getString("testCount"));
        String count = riskJsonObject.getString("count");
        if (StringUtils.isEmpty(count)) {
            userInfoVO.setCount("5");
        } else {
            userInfoVO.setCount(count);
        }
        userInfoVO.setNextTestTime(riskJsonObject.getString("nextTestTime"));

        userInfoVO.setSumCount(riskJsonObject.getString("sumCount"));

        String quota = riskJsonObject.getString("quota");
        userInfoVO.setQuota(quota);


        //  -1--未设置  1--已设置
        if (hasPayPassword == 1) {
            userInfoVO.setHasPayPassword("1");
        }
        userInfoVO.setIconUrl(userInfoJsonObject.getString("headimg"));
        userInfoVO.setRegistDate(userInfoJsonObject.getLong("expiredate"));
        userInfoVO.setInfoPercent(userInfoJsonObject.getString("infoPercent"));
        userInfoVO.setLastLoginTime(userInfoJsonObject.getLong("latestLoginTime"));
        userInfoVO.setVipCode(userInfoJsonObject.getString("vipCode"));
        userInfoVO.setEmail(userInfoJsonObject.getString("email"));
        userInfoVO.setOpenAccountStatus(userInfoJsonObject.getString("isopenaccount"));

        // 用户中心-1.实名认证结果
        Message realNameResultMessage = apiUtil.get(ApiEnum.API_USERCENTER_ACCOUNT_REALNAME_INFO, headers);
        if (realNameResultMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException(realNameResultMessage.getMessage());
        }
        JSONObject realNameJsonObject = JsonUtil.toJSONObject(realNameResultMessage.getData());
        if (realNameJsonObject.getInteger("code") == 0) {
            userInfoVO.setIsRealName("1"); // 用户中心-已实名认证
        } else if (realNameJsonObject.getInteger("code") == -1) {
            userInfoVO.setIsRealName("0"); // 用户中心-未实名认证
            // 用户中心-2.认证状态
            Message realNameStatusMessage = apiUtil.get(ApiEnum.API_USERCENTER_ACCOUNT_REALNAME_STATUS, headers);
            if (realNameStatusMessage != null && realNameStatusMessage.getCode() == MessageStatus.SUCCESS.getStatus()) {
                JSONObject realNameStatusData = JsonUtil.toJSONObject(realNameStatusMessage.getData());
                int code = realNameStatusData.getInteger("code");
                if (code == 0) {
                    String status = realNameStatusData.getJSONObject("data").getString("status");
                    userInfoVO.setRealNameStatus(status);
                }
            }
        }

        // 理财中心-账户-资金总览
        Message assetMessage = apiUtil.get(ApiEnum.API_INVESTMENT_ASSET_OVERVIEW, headers);
        if (assetMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException(assetMessage.getMessage());
        }
        JSONObject assetJsonObject = JsonUtil.toJSONObject(assetMessage.getData());
        String investmentAmount = assetJsonObject.getString("investmentAmount");
        userInfoVO.setInvestmentAmount(investmentAmount);
        try {
            float v = Float.valueOf(quota) - Float.valueOf(investmentAmount);
            String surplusAmount = String.valueOf(v >= 0 ? v : 0);
            userInfoVO.setSurplusAmount(surplusAmount);
        } catch (Exception e) {
            userInfoVO.setSurplusAmount("");
        }
        userInfoVO.setAvailableBalance(assetJsonObject.getString("availableBalance"));
        userInfoVO.setTotalAssets(assetJsonObject.getString("totalAssets"));
        userInfoVO.setAccumulatedIncome(assetJsonObject.getString("accumulatedIncome"));
        userInfoVO.setDueInIncome(assetJsonObject.getString("dueInIncome"));


        // 添加 新新币的数量  优惠券可用的数量
        Message xxbMessage = apiUtil.get(ApiEnum.API_INVESTMENT_PRIZE_COINS, headers);
        if (xxbMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException(xxbMessage.getMessage());
        }
        JSONObject xxbJsonObj = JsonUtil.toJSONObject(xxbMessage.getData());
        userInfoVO.setXxbNum(xxbJsonObj.getString("num"));

        CouponVO couponVO = couponService.getCouponList(headers, 1, 1, 10000);
        userInfoVO.setCouponCanUseNum(couponVO.getTotalCount());


        // 理财中心-风险测评结果
        Message riskExamResultMessage = apiUtil.get(ApiEnum.API_INVESTMENT_MORE_RISKEXAM_RESULT, headers);
        if (riskExamResultMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException(riskExamResultMessage.getMessage());
        }
        JSONObject riskExamResultJsonObject = JsonUtil.toJSONObject(riskExamResultMessage.getData());
        if (riskExamResultJsonObject.getBoolean("status")) {
            userInfoVO.setRiskExamStatus("1");
        }

        // 理财中心-用户自定义菜单
        MenuVO menuVO = apiMenuService.getMenus(clientId, token, userAgent);
        for (MenuVO.Buttons buttons : menuVO.getQuickHelp()) {
            if (buttons != null && buttons.getType() == MenuTypeStatus.MENU_TYPE_CUSTOM.getType()) {
                userInfoVO.setMenus(buttons.getItems());
                break;
            }
        }
        return new SuccessMessage(userInfoVO);
    }


    public boolean isLogin(Headers headers) {
        try {
            Message message = apiUtil.get(ApiEnum.API_USERCENTER_USER_ISLOGIN, headers);
            return message.getCode() == 200000;
        } catch (CoreException e) {
            return false;
        }
    }


    public String getUserIdByToken(Headers headers) {
        Message userInfoMessage = apiUtil.get(ApiEnum.API_USERCENTER_USERINFO, headers);
        if (userInfoMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException(userInfoMessage.getMessage());
        }
        JSONObject userInfoJsonObject = JsonUtil.toJSONObject(userInfoMessage.getData()).getJSONObject("data");

        return userInfoJsonObject.getString("userid");
    }

}
