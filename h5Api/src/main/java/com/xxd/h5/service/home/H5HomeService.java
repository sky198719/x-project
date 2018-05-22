package com.xxd.h5.service.home;

import com.alibaba.fastjson.JSONObject;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.common.enums.BannerTypeEnum;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.service.H5UrlService;
import com.xxd.h5.service.user.UserService;
import com.xxd.h5.vo.home.BannerVo;
import com.xxd.h5.vo.home.HomeDataVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author zhangshengwen
 * @date 2017/11/17
 */
@Service
public class H5HomeService extends H5BaseService {

    @Autowired
    private H5UrlService h5UrlService;

    @Autowired
    private UserService userService;

    public Message getHomeData(String clientId, String token, String userAgent)throws CoreException, ServiceException, Exception{
        HomeDataVo homeDataVo = new HomeDataVo();
        Headers headers = Headers.createHeaders()
                .addHeader("token", token)
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", userAgent);
        Message message;
        boolean login = userService.isLogin(headers);
        if(StringUtils.isNotEmpty(token) && login) {
            message = this.apiUtil.post(ApiEnum.API_INVESTMENT_HOME_INVESTSTATUS, headers);
            if(message.getCode() == MessageStatus.SUCCESS.getStatus()) {
                String investStatus = message.getData().toString();
                if(Boolean.TRUE.toString().equals(investStatus)) {
                    homeDataVo.setInvestStatus("1");
                }
            }
        }

        if(StringUtils.isNotBlank(token) && login) {
            message = this.apiUtil.get(ApiEnum.API_INVESTMENT_INFO_MESSAGE_NUM, headers);
            if(message != null && message.getCode() == MessageStatus.SUCCESS.getStatus()) {
                int unreadAllNum = JsonUtil.toJSONObject(message.getData()).getInteger("unreadAllNum");
                int unreadLastType = JsonUtil.toJSONObject(message.getData()).getInteger("unreadLastType");
                int unreadNoticeNum = JsonUtil.toJSONObject(message.getData()).getInteger("unreadNoticeNum");
                int unreadMessageNum = JsonUtil.toJSONObject(message.getData()).getInteger("unreadMessageNum");
                homeDataVo.setUnreadAllNum(unreadAllNum);
                homeDataVo.setUnreadLastType(unreadLastType);
                homeDataVo.setUnreadNoticeNum(unreadNoticeNum);
                homeDataVo.setUnreadMessageNum(unreadMessageNum);
            }
        }



        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("position", (long) BannerTypeEnum.BANNER_TYPE_START.getType());
        Message banner = this.apiUtil.get(ApiEnum.API_INVESTMENT_HOME_BANNERS, headers, queryStrings);
        if(banner.getCode() == MessageStatus.SUCCESS.getStatus()) {
            List<BannerVo> bannerVOS = JsonUtil.toList(banner.getData(), BannerVo.class);
            if(CollectionUtils.isNotEmpty(bannerVOS)) {
                homeDataVo.setBanners(bannerVOS);
            }
        }

        Message recommedMessage;

        QueryStrings recommedStrings = QueryStrings.create();
        try {
            String userId = userService.getUserIdByToken(headers);
            recommedStrings.setPath("/").addPath(userId);
            recommedMessage = this.apiUtil.get(ApiEnum.API_INVESTMENT_FINANCES_RECOMMED, headers, recommedStrings);
        }catch (Exception e){
            recommedStrings.setPath("/").addPath("0");
            recommedMessage = this.apiUtil.get(ApiEnum.API_INVESTMENT_FINANCES_RECOMMED, headers, recommedStrings);
        }

        if(recommedMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException("bad request");
        } else {
            JSONObject jsonObject = JsonUtil.toJSONObject(recommedMessage.getData());

            HomeDataVo.WonderfulProduct wonderfulProduct = transformService.wonderfulTransform(jsonObject);
            if(null != wonderfulProduct) {
                homeDataVo.setWonderfulProduct(wonderfulProduct);
            }



            String pictureUrl = this.h5UrlService.getBottomPictureUrl();
            homeDataVo.setBottomBanner(new BannerVo(pictureUrl));
            homeDataVo.setRedPackageH5url(this.h5UrlService.getRegisterRedPackageUrl());
            homeDataVo.setQtdsH5url(this.h5UrlService.getQtdsUrl());
            return new SuccessMessage(homeDataVo);
        }
    }



}
