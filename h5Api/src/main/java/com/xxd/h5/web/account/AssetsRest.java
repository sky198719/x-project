package com.xxd.h5.web.account;

import com.alibaba.fastjson.JSONObject;
import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.dto.RequestDTO;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.FormUrlEncoded;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.HttpHelper;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.common.enums.RechargeChannelEnum;
import com.xxd.h5.service.H5UrlService;
import com.xxd.h5.service.TextService;
import com.xxd.h5.vo.account.AssetInfoVO;
import com.xxd.h5.vo.account.RechargeInitVO;
import com.xxd.h5.vo.account.WithdrawInitVO;
import com.xxd.h5.web.H5Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author zhangshengwen
 * @date 2017/11/28
 */
@Component
@Path("/assets")
@Api(value = "用户资产", description = "用户资产相关接口")
public class AssetsRest extends H5Rest{

    private static final Logger logger = LoggerFactory.getLogger(AssetsRest.class);

    @Autowired
    H5UrlService h5UrlService;

    @Autowired
    TextService textService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/overview")
    @ApiOperation(value = "资产总览", httpMethod = "GET")
    public Message assetOverview(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        try {
            AssetInfoVO assetInfoVO = new AssetInfoVO();
            Message assetMessage = apiUtil.get(ApiEnum.API_INVESTMENT_ASSET_OVERVIEW, headers);
            if (assetMessage.getCode() == MessageStatus.SUCCESS.getStatus()) {
                assetInfoVO = JsonUtil.toObject(assetMessage.getData(), AssetInfoVO.class);
            }
            Message infoMessage = apiUtil.get(ApiEnum.API_USERCENTER_USERINFO, headers);
            if (infoMessage.getCode() == MessageStatus.SUCCESS.getStatus()) {
                JSONObject jsonObject = JsonUtil.toJSONObject(infoMessage.getData()).getJSONObject("data");
                assetInfoVO.setUserImg(jsonObject.getString("headimg"));
                assetInfoVO.setUserName(jsonObject.getString("username"));
                assetInfoVO.setStatus(Integer.parseInt(jsonObject.getString("status")));
            }
            return new SuccessMessage(assetInfoVO);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取资产总览信息失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取资产总览信息失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/frozen")
    @ApiOperation(value = "冻结金额记录", httpMethod = "GET")
    public Message fundFrozen(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "page", value = "page", required = true)
            @QueryParam(value = "page") @NotNull int page,
            @ApiParam(name = "pageSize", value = "pageSize", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token", token)
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("currentPage", page)
                    .addParameter("pageSize", pageSize);
            return apiUtil.get(ApiEnum.API_INVESTMENT_ASSET_FROZENFUNDS, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("冻结金额记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("冻结金额记录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recharge/init")
    @ApiOperation(value = "充值初始化数据", httpMethod = "GET")
    public Message rechargeInit(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        try {
            // 卡状态
            Message cardStatusMessage = apiUtil.get(ApiEnum.API_USERCENTER_USER_CARD_STATUS, headers);
            if (cardStatusMessage == null || cardStatusMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("error");
            }

            // 银行卡信息
            Message cardInfoMessage = apiUtil.get(ApiEnum.API_USERCENTER_USER_CARD_INFO, headers);
            if (cardInfoMessage == null || cardInfoMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("error");
            }

            RechargeInitVO rechargeInitVO = new RechargeInitVO();
            rechargeInitVO.setCardStatus(JsonUtil.toJSONObject(cardStatusMessage.getData()).getJSONObject("data").getString("existCheckingBankCard"));
            rechargeInitVO.setCardNo(JsonUtil.toJSONObject(cardInfoMessage.getData()).getJSONObject("data").getString("bankaccount"));
            rechargeInitVO.setBankCode(JsonUtil.toJSONObject(cardInfoMessage.getData()).getJSONObject("data").getString("bankcode"));
            rechargeInitVO.setSingleLimit(JsonUtil.toJSONObject(cardInfoMessage.getData()).getJSONObject("data").getString("singleLimit"));
            rechargeInitVO.setDailyLimit(JsonUtil.toJSONObject(cardInfoMessage.getData()).getJSONObject("data").getString("dailyLimit"));
            rechargeInitVO.setMonthlyLimit(JsonUtil.toJSONObject(cardInfoMessage.getData()).getJSONObject("data").getString("monthlyLimit"));
            rechargeInitVO.setTips(textService.getRechargeText());
            return new SuccessMessage(rechargeInitVO);
        } catch (CoreException | ServiceException exception) {
            logger.error("充值初始化报错", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("充值初始化报错", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recharge")
    @ApiOperation(value = "充值", httpMethod = "POST")
    public Message recharge(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "rechargeType", value = "14-快捷充值,13-网银充值", required = true)
            @FormParam(value = "rechargeType") @NotNull int rechargeType,
            @ApiParam(name = "rechargeAmount", value = "充值金额", required = true)
            @FormParam(value = "rechargeAmount") @NotNull String rechargeAmount) {
        try {

            if (RechargeChannelEnum.RECHARGE_CHANNEL_ONLINE_BANING_RECHARGE.getType() != rechargeType
                    && RechargeChannelEnum.RECHARGE_CHANNEL_QUCIK_RECHARGE.getType() != rechargeType) {
                return new ErrorMessage("错误的充值通道");
            }

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token", token)
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));

            FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
            RequestDTO requestDTO = RequestDTO.create();
            requestDTO.addParameter("platformSource", "webapp");
            requestDTO.addParameter("rechargeAmount", rechargeAmount);
            requestDTO.addParameter("rechargeType", rechargeType);
            requestDTO.addParameter("appRequest", 3);
            formUrlEncoded.setBody(requestDTO.transform2JsonString());
            return apiUtil.post(ApiEnum.API_ACCOUNTCENTER_ACCOUNT_RECHARGE, headers, formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("充值失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("充值失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recharge-record")
    @ApiOperation(value = "充值记录", httpMethod = "GET")
    public Message rechargeRecord(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token, @ApiParam(name = "page", value = "页码", required = true)
            @QueryParam(value = "page") @NotNull String page,
            @ApiParam(name = "pageSize", value = "每页显示大小", required = true)
            @QueryParam(value = "pageSize") @NotNull String pageSize,
            @ApiParam(name = "type", value = "类别(0全部、1处理中、2成功、3失败)", required = true)
            @QueryParam(value = "type") @NotNull String type) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("currentPage", page)
                .addParameter("pageSize", pageSize)
                .addParameter("type", type);
        try {
            return apiUtil.get(ApiEnum.API_INVESTMENT_ASSET_RECHARGE_RECORD, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取充值记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取充值记录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/withdraw/init")
    @ApiOperation(value = "提现初始化数据", httpMethod = "GET")
    public Message withdrawInit(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        try {
            // 提现初始化数据
            Message withDrawMessage = apiUtil.get(ApiEnum.API_ACCOUNTCENTER_ACCOUNT_WITHDRAW_INIT, headers);
            if (withDrawMessage == null || withDrawMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("error");
            }
            // 卡状态
            Message cardStatusMessage = apiUtil.get(ApiEnum.API_USERCENTER_USER_CARD_STATUS, headers);
            if (cardStatusMessage == null || cardStatusMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("error");
            }

            // 银行卡信息
            Message cardInfoMessage = apiUtil.get(ApiEnum.API_USERCENTER_USER_CARD_INFO, headers);
            if (cardInfoMessage == null || cardInfoMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("error");
            }

            WithdrawInitVO withdrawInitVO = new WithdrawInitVO();
            withdrawInitVO.setCardStatus(JsonUtil.toJSONObject(cardStatusMessage.getData()).getJSONObject("data").getString("existCheckingBankCard"));
            withdrawInitVO.setCardNo(JsonUtil.toJSONObject(cardInfoMessage.getData()).getJSONObject("data").getString("bankaccount"));
            withdrawInitVO.setBankCode(JsonUtil.toJSONObject(cardInfoMessage.getData()).getJSONObject("data").getString("bankcode"));
            withdrawInitVO.setBalanceAmount(JsonUtil.toJSONObject(withDrawMessage.getData()).getJSONObject("data").getString("balanceAmount"));
            withdrawInitVO.setWithdrawAmount(JsonUtil.toJSONObject(withDrawMessage.getData()).getJSONObject("data").getString("withdrawAmount"));
            withdrawInitVO.setMinAmount(JsonUtil.toJSONObject(withDrawMessage.getData()).getJSONObject("data").getString("minAmount"));
            withdrawInitVO.setUserWithdrawCount(JsonUtil.toJSONObject(withDrawMessage.getData()).getJSONObject("data").getString("userWithdrawCount"));
            withdrawInitVO.setFreeWithdrawCount(JsonUtil.toJSONObject(withDrawMessage.getData()).getJSONObject("data").getString("freeWithdrawCount"));
            withdrawInitVO.setConfigWithdrawCount(JsonUtil.toJSONObject(withDrawMessage.getData()).getJSONObject("data").getString("configWithdrawCount"));
            withdrawInitVO.setIsWhiteCash(JsonUtil.toJSONObject(withDrawMessage.getData()).getJSONObject("data").getInteger("isWhiteCash"));
            withdrawInitVO.setTipH5url(h5UrlService.getWithDrawUrl(token));
            withdrawInitVO.setTips(textService.getWithdrawText());
            return new SuccessMessage(withdrawInitVO);
        } catch (CoreException | ServiceException exception) {
            logger.error("提现初始化失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("提现初始化失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/withdraw")
    @ApiOperation(value = "提现", httpMethod = "POST")
    public Message withdraw(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "withdrawAmount", value = "提现金额", required = true)
            @FormParam(value = "withdrawAmount") @NotNull String withdrawAmount) {
        try {


            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token", token)
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));

            FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
            RequestDTO requestDTO = RequestDTO.create();
            requestDTO.addParameter("withdrawSource", "webapp");
            requestDTO.addParameter("withdrawCashAmount", withdrawAmount);
            formUrlEncoded.setBody(requestDTO.transform2JsonString());
            return apiUtil.post(ApiEnum.API_ACCOUNTCENTER_ACCOUNT_WITHDRAW, headers, formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("提现失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("提现失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/withdraw-trial")
    @ApiOperation(value = "提现试算手续费", httpMethod = "GET")
    public Message withdrawTrial(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "withdrawAmount", value = "提现金额", required = true)
            @QueryParam(value = "withdrawAmount") @NotNull double withdrawAmount) {
        try {


            if (withdrawAmount <= 0) {
                return new ErrorMessage("提现金额必须大于0");
            }

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token", token)
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("withdrawCashAmount", withdrawAmount);
            return apiUtil.get(ApiEnum.API_ACCOUNTCENTER_ACCOUNT_WITHDRAW_TRIAL, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("试算手续费失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("试算手续费失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/withdraw-record")
    @ApiOperation(value = "提现记录", httpMethod = "GET")
    public Message withdrawRecord(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token, @ApiParam(name = "page", value = "页码", required = true)
            @QueryParam(value = "page") @NotNull String page,
            @ApiParam(name = "pageSize", value = "每页显示大小", required = true)
            @QueryParam(value = "pageSize") @NotNull String pageSize,
            @ApiParam(name = "type", value = "类别(0全部、1处理中、2成功、3失败)", required = true)
            @QueryParam(value = "type") @NotNull String type) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("currentPage", page)
                .addParameter("pageSize", pageSize)
                .addParameter("type", type);
        try {
            return apiUtil.get(ApiEnum.API_INVESTMENT_ASSET_WITHDRAW_RECORD, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取提现记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取提现记录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }
}
