package com.xxd.h5.web.user;

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
import com.xxd.h5.common.utils.CookieHelper;
import com.xxd.h5.service.user.UserService;
import com.xxd.h5.web.H5Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 用户相关restful服务.
 *
 * @author zhangshengwen
 */
@Component
@Path("/users")
@Api(value = "用户接口", description = "用户相关接口")
public class UserRest extends H5Rest {

    private static final Logger logger = LoggerFactory.getLogger(UserRest.class);

    @Autowired
    private UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login")
    @ApiOperation(value = "登陆", httpMethod = "POST")
    public Message login(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "userName", value = "userName", required = true)
            @FormParam(value = "userName") @NotNull String userName,
            @ApiParam(name = "password", value = "password", required = true)
            @FormParam(value = "password") @NotNull String password) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("userName", userName)
                .addParameter("password", password);
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create()
                .setBody(requestDTO.transform2JsonString());
        Message loginMessage = null;

        try {
            loginMessage = apiUtil.post(ApiEnum.API_USERCENTER_LOGIN, headers, formUrlEncoded);
            if (loginMessage == null || loginMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("登录失败");
            }
            int loginStatus = JsonUtil.toJSONObject(loginMessage.getData()).getInteger("code");
            if (0 != loginStatus) {
                return loginMessage;
            }
            String token = JsonUtil.toJSONObject(loginMessage.getData()).getString("data");

            CookieHelper.addCookie(response, new Cookie(CookieHelper.USER_TOKEN, token.trim()));

            return new SuccessMessage(new SuccessMessage(0, loginMessage.getMessage(), userService.getUserInfo(clientId, token, HttpHelper.getUserAgent(request)).getData()));
        } catch (CoreException | ServiceException exception) {
            logger.error("登录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("登录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("logout")
    @ApiOperation(value = "登出", httpMethod = "DELETE")
    public Message logOut(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token) throws Exception {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        Message logOutMessage = null;
        try {
            logOutMessage = apiUtil.post(ApiEnum.API_USERCENTER_LOGINOUT, headers);
            if (logOutMessage == null || logOutMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("登出失败");
            }
            return logOutMessage;
        } catch (CoreException | ServiceException exception) {
            logger.error("登出失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("登出失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login-status")
    @ApiOperation(value = "获取用户登陆状态", httpMethod = "GET")
    public Message loginStatus(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token令牌", required = true)
            @HeaderParam(value = "token") @NotNull String token) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        try {
            return apiUtil.get(ApiEnum.API_USERCENTER_USER_ISLOGIN, headers);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取用户登陆状态失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取用户登陆状态失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("register")
    @ApiOperation(value = "注册", httpMethod = "POST")
    public Message register(
            @ApiParam(name = "clientId", value = "cliendtId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "userName", value = "userName")
            @FormParam(value = "userName") String userName,
            @ApiParam(name = "password", value = "password", required = true)
            @FormParam(value = "password") @NotNull String password,
            @ApiParam(name = "phone", value = "phone", required = true)
            @FormParam(value = "phone") @NotNull String phone,
            @ApiParam(name = "smsCode", value = "smsCode", required = true)
            @FormParam(value = "smsCode") @NotNull String smsCode,
            @ApiParam(name = "channel", value = "渠道")
            @FormParam(value = "channel") String channel) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("phone", phone)
                .addParameter("password", password)
                .addParameter("code", smsCode);

        if (StringUtils.isNotEmpty(channel)){
            requestDTO.addParameter("channel", channel);
        }
        if (StringUtils.isBlank(userName)) {
            userName = phone;
        }
        requestDTO.addParameter("userName", userName);

        formUrlEncoded.setBody(requestDTO.transform2JsonString());
        Message registerMessage = null;
        try {
            // 检测用户名唯一性
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("username", userName);
            Message checkUniqueMessage = apiUtil.get(ApiEnum.API_USERCENTER_CHECKUNIQUE, headers, queryStrings);
            if (checkUniqueMessage == null || checkUniqueMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("注册失败");
            }
            if (0 != JsonUtil.toJSONObject(checkUniqueMessage.getData()).getInteger("code")) {
                return checkUniqueMessage;
            }

            // 发起注册
            registerMessage = apiUtil.post(ApiEnum.API_USERCENTER_REGISTER, headers, formUrlEncoded);
            if (registerMessage == null || registerMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("注册失败");
            }
            int registerStatus = JsonUtil.toJSONObject(registerMessage.getData()).getInteger("code");
            if (0 != registerStatus) {
                return registerMessage;
            }
            String token = JsonUtil.toJSONObject(registerMessage.getData()).getString("data");
            CookieHelper.addCookie(response, new Cookie(CookieHelper.USER_TOKEN, token.trim()));
            return new SuccessMessage(new SuccessMessage(0, registerMessage.getMessage(), userService.getUserInfo(clientId, token, HttpHelper.getUserAgent(request)).getData()));
        } catch (CoreException | ServiceException exception) {
            logger.error("用户注册失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("用户注册失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("check/unique")
    @ApiOperation(value = "检查用户名是否唯一", httpMethod = "GET")
    public Message checkUsernameUnique(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "username", value = "用户名", required = true)
            @QueryParam(value = "username") @NotNull String username) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis());
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("username", username);
        try {
            return apiUtil.get(ApiEnum.API_USERCENTER_CHECKUNIQUE, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("检查用户名是否唯一失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("检查用户名是否唯一失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bind/mobile")
    @ApiOperation(value = "绑定、更新绑定手机号", httpMethod = "POST")
    public Message bindMobile(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token令牌", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "smsCode", value = "手机验证码", required = true)
            @FormParam(value = "smsCode") @NotNull String smsCode,
            @ApiParam(name = "phone", value = "新手机号码", required = true)
            @FormParam(value = "phone") @NotNull String phone) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("smsCode", smsCode)
                .addParameter("phone", phone);
        formUrlEncoded.setBody(requestDTO.transform2JsonString());
        try {
            return apiUtil.post(ApiEnum.API_USERCENTER_BINDMOBILE, headers, formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("绑定手机号失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("绑定手机号失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("unbind/mobile")
    @ApiOperation(value = "解除绑定手机号", httpMethod = "POST")
    public Message unBindMobile(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token令牌", required = true)
            @HeaderParam(value = "token") @NotNull String token) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        try {
            return apiUtil.post(ApiEnum.API_USERCENTER_UNBINDMOBILE, headers);
        } catch (CoreException | ServiceException exception) {
            logger.error("解除绑定手机号失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("解除绑定手机号失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("info")
    @ApiOperation(value = "获取用户信息", httpMethod = "GET")
    public Message getUserInfo(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token令牌", required = true)
            @HeaderParam(value = "token") @NotNull String token) {
        try {
            return userService.getUserInfo(clientId, token, HttpHelper.getUserAgent(request));
        } catch (CoreException | ServiceException exception) {
            logger.error("获取用户信息失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取用户信息失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

}
