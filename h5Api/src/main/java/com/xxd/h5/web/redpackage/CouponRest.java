package com.xxd.h5.web.redpackage;

import com.alibaba.fastjson.JSONObject;
import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.HttpHelper;
import com.xxd.common.util.JsonUtil;
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
 * 优惠券相关接口
 * @author zhangshengwen
 */
@Component
@Path("/coupons/")
@Api(value = "优惠券")
public class CouponRest extends H5Rest {

    private static final Logger logger = LoggerFactory.getLogger(CouponRest.class);


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/exchange")
    @ApiOperation(value = "优惠券兑换", httpMethod = "POST")
    public Message registerRedEnvelope(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "couponCode", value = "couponCode", format = "String", required = true)
            @FormParam(value = "couponCode") String couponCode) {

        Headers headers = Headers.createHeaders()
                .addHeader("token", token)
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("code", couponCode);
        try {
            Message exchangeMessage = apiUtil.post(ApiEnum.API_TRADECENTER_COUPON_EXCHANGE, headers, queryStrings);
            JSONObject dataObj = JsonUtil.toJSONObject(exchangeMessage.getData());
            int code = exchangeMessage.getCode();
            String message = dataObj.getString("message");
            if (code != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage(message);
            }
            return new SuccessMessage(dataObj);
        } catch (CoreException | ServiceException exception) {
            logger.error("兑换优惠券失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("兑换优惠券失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


}
