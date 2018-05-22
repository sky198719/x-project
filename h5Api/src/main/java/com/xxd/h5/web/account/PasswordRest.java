package com.xxd.h5.web.account;

import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.dto.RequestDTO;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.FormUrlEncoded;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.util.HttpHelper;
import com.xxd.h5.common.enums.PasswordTypeEnum;
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
 *
 * @author zhangshengwen
 * @date 2017/11/22
 */
@Component
@Path("/password")
@Api(value = "账号密码", description = "用户账号密码相关接口")
public class PasswordRest extends H5Rest{

    private static final Logger logger = LoggerFactory.getLogger(PasswordRest.class);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pay/check")
    @ApiOperation(value = "校验支付密码", httpMethod = "GET")
    public Message checkPayPassword(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token令牌", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "password", value = "password", required = true)
            @QueryParam(value = "password") @NotNull String password) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        RequestDTO payPasswordDto = new RequestDTO();
        payPasswordDto.addParameter("payPassword", password);
        FormUrlEncoded payPasswordValidate = FormUrlEncoded.create()
                .setBody(payPasswordDto.transform2JsonString());
        try {
            return apiUtil.post(ApiEnum.API_USERCENTER_VALIDATE_PAY_PASSWORD, headers, payPasswordValidate);
        } catch (CoreException | ServiceException exception) {
            logger.error("校验支付密码失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("校验用户支付密码失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("reset")
    @ApiOperation(value = "重置用户登录密码", httpMethod = "PUT")
    public Message resetLoginPwd(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "phone", value = "手机号", required = true)
            @FormParam(value = "phone") @NotNull String phone,
            @ApiParam(name = "password", value = "新密码", required = true)
            @FormParam(value = "password") @NotNull String password,
            @ApiParam(name = "smsCode", value = "短信验证码", required = true)
            @FormParam(value = "smsCode") @NotNull String smsCode) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis());
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("phone", phone)
                .addParameter("password", password)
                .addParameter("pswType", PasswordTypeEnum.PASSWORD_TYPE_LOGIN.getType())
                .addParameter("smsCode", smsCode);
        formUrlEncoded.setBody(requestDTO.transform2JsonString());
        try {
            return apiUtil.post(ApiEnum.API_USERCENTER_RESETPWD, headers, formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("重置登录密码失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("重置登录密码失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pay/reset")
    @ApiOperation(value = "重置用户支付密码", httpMethod = "PUT")
    public Message resetPayPwd(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "phone", value = "手机号", required = true)
            @FormParam(value = "phone") @NotNull String phone,
            @ApiParam(name = "password", value = "新密码", required = true)
            @FormParam(value = "password") @NotNull String password,
            @ApiParam(name = "smsCode", value = "短信验证码", required = true)
            @FormParam(value = "smsCode") @NotNull String smsCode) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis());
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("phone", phone)
                .addParameter("password", password)
                .addParameter("pswType", PasswordTypeEnum.PASSWORD_TYPE_PAY.getType())
                .addParameter("smsCode", smsCode);
        formUrlEncoded.setBody(requestDTO.transform2JsonString());
        try {
            return apiUtil.post(ApiEnum.API_USERCENTER_RESETPWD, headers, formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("重置支付密码失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("重置支付密码失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "更新用户登录密码", httpMethod = "PUT")
    public Message updateLoginPwd(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token令牌", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "oldPassWord", value = "旧密码", required = true)
            @FormParam(value = "oldPassWord") @NotNull String oldPassWord,
            @ApiParam(name = "newPassWord", value = "新密码", required = true)
            @FormParam(value = "newPassWord") @NotNull String newPassWord) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token);
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("oldPassword", oldPassWord)
                .addParameter("password", newPassWord)
                .addParameter("pswType", PasswordTypeEnum.PASSWORD_TYPE_LOGIN.getType());
        formUrlEncoded.setBody(requestDTO.transform2JsonString());
        try {
            return apiUtil.post(ApiEnum.API_USERCENTER_UPDATEPWD, headers, formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("修改用户登录密码失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("修改用户登录密码失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pay")
    @ApiOperation(value = "更新用户支付密码", httpMethod = "PUT")
    public Message updatePayPwd(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token令牌", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "oldPassWord", value = "旧密码", required = true)
            @FormParam(value = "oldPassWord") @NotNull String oldPassWord,
            @ApiParam(name = "newPassWord", value = "新密码", required = true)
            @FormParam(value = "newPassWord") @NotNull String newPassWord) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("oldPassword", oldPassWord)
                .addParameter("password", newPassWord)
                .addParameter("pswType", PasswordTypeEnum.PASSWORD_TYPE_PAY.getType());
        formUrlEncoded.setBody(requestDTO.transform2JsonString());
        try {
            return apiUtil.post(ApiEnum.API_USERCENTER_UPDATEPWD, headers, formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("修改用户支付密码失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("修改用户支付密码失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pay")
    @ApiOperation(value = "创建用户支付密码", httpMethod = "POST")
    public Message setPayPwd(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "password", value = "password", required = true)
            @FormParam(value = "password") @NotNull String password,
            @ApiParam(name = "token", value = "token令牌", required = true)
            @HeaderParam(value = "token") @NotNull String token) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("password", password);
        formUrlEncoded.setBody(requestDTO.transform2JsonString());
        try {
            return apiUtil.post(ApiEnum.API_USERCENTER_SETPAYPWD, headers, formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("设置用户支付密码失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("设置用户支付密码失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

}
