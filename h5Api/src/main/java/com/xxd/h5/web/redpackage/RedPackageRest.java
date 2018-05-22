package com.xxd.h5.web.redpackage;

import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.util.HttpHelper;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.service.coupon.CouponService;
import com.xxd.h5.vo.coupon.CouponVO;
import com.xxd.h5.web.H5Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 红包相关接口.
 *
 * @author zhangshengwen
 */
@Component
@Path("/red-packages")
@Api(value = "红包接口")
public class RedPackageRest extends H5Rest {

    private static final Logger logger = LoggerFactory.getLogger(RedPackageRest.class);

    @Resource
    CouponService couponService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "红包列表", httpMethod = "GET")
    public Message redPackages(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "status", value = "0.全部,1.可使用,2.已使用,3.已过期", format = "int", defaultValue = "0")
            @QueryParam(value = "status") int status,
            @ApiParam(name = "page", format = "int", required = true)
            @QueryParam(value = "page") @NotNull int currentPage,
            @ApiParam(name = "pageSize", format = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            CouponVO couponVO = couponService.getCouponList(headers, status, currentPage, pageSize);
            return new SuccessMessage(couponVO);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取红包列表失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取红包列表失败", e);
            return new ErrorMessage(MessageStatus.ERROR);
        }
    }

    /**
     * 可用红包
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/can-used")
    @ApiOperation(value = "获取可以使用的红包", httpMethod = "GET")
    public Message redPackage(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "productId", value = "产品ID", format = "String", required = true)
            @QueryParam(value = "productId") @NotNull String productId,
            @ApiParam(name = "amount", value = "购买产品金额", format = "String")
            @QueryParam(value = "amount") String amount,
            @ApiParam(name = "productType", value = "产品类型", format = "String")
            @QueryParam(value = "productType") @NotNull int productType,
            @ApiParam(name = "page", format = "int", required = true)
            @QueryParam(value = "page") @NotNull int currentPage,
            @ApiParam(name = "pageSize", format = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            CouponVO couponVO = couponService.getCanUseCouponList(headers, productId, productType, amount, currentPage, pageSize);
            return new SuccessMessage(couponVO);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取可以使用的红包失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取可以使用的红包失败", e);
            return new ErrorMessage(MessageStatus.ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new")
    @ApiOperation(value = "新手注册红包", httpMethod = "GET")
    public Message registerRedEnvelope(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token) {

        Headers headers = Headers.createHeaders()
                .addHeader("token", token)
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        try {
            Message message = apiUtil.get(ApiEnum.API_INVESTMENT_PRIZE_REDPACKAGES_NEWUSER, headers);
            if (message.getCode() == MessageStatus.SUCCESS.getStatus()) {
                CouponVO vo = couponTransformService.transform(JsonUtil.toJSONArray(message.getData()));
                return new SuccessMessage(vo);
            }
            return new SuccessMessage(new Object());
        } catch (CoreException | ServiceException exception) {
            logger.error("获取新手注册红包失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取新手注册红包失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }
}
