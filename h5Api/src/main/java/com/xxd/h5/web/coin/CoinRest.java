package com.xxd.h5.web.coin;

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
import com.xxd.h5.web.H5Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 新新币相关接口.
 *
 * @author zhangshengwen
 */
@Component
@Path("/coins")
@Api(value = "新新币接口")
public class CoinRest extends H5Rest {

    private static final Logger logger = LoggerFactory.getLogger(CoinRest.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "账户新新币信息", httpMethod = "GET")
    public Message coins(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            return apiUtil.get(ApiEnum.API_INVESTMENT_PRIZE_COINS, headers);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取账户新新币信息失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取账户新新币信息失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/exchange")
    @ApiOperation(value = "新新币兑换", httpMethod = "POST")
    public Message exchange(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "coinNum", value = "新新币数量", required = true)
            @FormParam(value = "coinNum") @NotNull String xxCoinNumber) {

        // { "data": { "payPW": "c2e0aa597b95f84180bf3989db89fc65", "verifyCode": "2s4e", "money": "100" } }
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
        RequestDTO requestDTO = RequestDTO.create();
        requestDTO.addParameter("xxCoinNumber", xxCoinNumber);

        formUrlEncoded.setBody(requestDTO.transform2JsonString());
        try {
            Message message = apiUtil.post(ApiEnum.API_ACCOUNTCENTER_USER_COIN_EXCHANGE, headers, formUrlEncoded);
            if (message.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("bad request");
            }
            return new SuccessMessage(message.getData());
        } catch (CoreException | ServiceException exception) {
            logger.error("兑换新新币失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("兑换新新币失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/trade")
    @ApiOperation(value = "新新币交易记录", httpMethod = "GET")
    public Message coinTrade(
            @ApiParam(name = "clientId", format = "String", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "page", format = "int", required = true)
            @QueryParam(value = "page") @NotNull int page,
            @ApiParam(name = "pageSize", format = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize,
            @ApiParam(name = "type", value = "0:全部 1:获得 2:兑换", format = "int", required = true)
            @QueryParam(value = "type") @NotNull int type) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("currentPage", page)
                    .addParameter("pageSize", pageSize)
                    .addParameter("type", type);
            return apiUtil.get(ApiEnum.API_INVESTMENT_PRIZE_COINTRADE, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("查看新新币交易记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("查看新新币交易记录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }
}
