package com.xxd.h5.web.sms;

import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.dto.RequestDTO;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.FormUrlEncoded;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.h5.web.H5Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 短信相关接口.
 *
 * @author zhangshengwen
 */
@Component
@Path("/sms")
@Api(value = "短信接口", description = "短信相关接口")
public class SmsRest extends H5Rest {

    private static final Logger logger = LoggerFactory.getLogger(SmsRest.class);

    @POST
    @Path("send-message")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "发送短信验证码", httpMethod = "POST")
    public Message sendSMS(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "imgcode", value = "图片验证码", required = true)
            @FormParam(value = "imgcode") @NotNull String imgcode,
            @ApiParam(name = "phone", value = "用户手机号", required = true)
            @FormParam(value = "phone") @NotNull String phone,
            @ApiParam(name = "type", value = "type", required = true)
            @FormParam(value = "type") @NotNull String type,
            @ApiParam(name = "scene", value = "scene")
            @FormParam(value = "scene") String scene) {
        // type和scene组合不同的短信模板,参见http://wiki.xxd.com/pages/viewpage.action?pageId=1311010
        Headers headers = Headers.createHeaders()
                .addHeader("clientId",clientId)
                .addHeader("clientTime",System.currentTimeMillis());
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("phone",phone)
                .addParameter("kaptcha",imgcode)
                .addParameter("type",type);
        if (StringUtils.isNotBlank(scene)){
            requestDTO.addParameter("scene",scene);
        }

        formUrlEncoded.setBody(requestDTO.transform2JsonString());
        try{
            return apiUtil.post(ApiEnum.API_USERCENTER_SENDSMS,headers,formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("发送短信验证码失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e){
            logger.error("发送短信验证码失败!",e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @POST
    @Path("send-voice")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "发送语音验证码", httpMethod = "POST")
    public Message sendVoiceSMS(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "imgcode", value = "图片验证码", required = true)
            @FormParam(value = "imgcode") @NotNull String imgcode,
            @ApiParam(name = "phone", value = "用户手机号", required = true)
            @FormParam(value = "phone") @NotNull String phone,
            @ApiParam(name = "busiCode", value = "业务场景", required =true)
            @FormParam(value = "busiCode") String busiCode) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis());
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
        // phone ： 电话号码   busiCode ： 业务场景  kaptchaCode:图片验证码
        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("phone", phone)
                .addParameter("busiCode", busiCode)
                .addParameter("kaptchaCode", imgcode);
        formUrlEncoded.setBody(requestDTO.transform2JsonString());
        try {
            return apiUtil.post(ApiEnum.API_USERCENTER_SEND_VOICE_SMS, headers, formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("发送语音码失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("发送语音码失败!", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("check")
    @ApiOperation(value = "预检查短信验证码", httpMethod = "GET")
    public Message checkSMS(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "smsCode", value = "手机验证码", required = true)
            @QueryParam(value = "smsCode") @NotNull String smsCode,
            @ApiParam(name = "phone", value = "手机号码", required = true)
            @QueryParam(value = "phone") @NotNull String phone
    ) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis());
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create();
        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("smsCode", smsCode)
                .addParameter("phone", phone);
        formUrlEncoded.setBody(requestDTO.transform2JsonString());
        try {
            return apiUtil.post(ApiEnum.API_USERCENTER_CHECKSMS, headers, formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("预检查短信验证码失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("预检查短信验证码失败!", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Path("/check-imgcode")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "校验验证码", httpMethod = "GET")
    public Message coins(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "imgcode", format = "String", required = true)
            @QueryParam(value = "imgcode") @NotNull String imgcode) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());
            QueryStrings queryStrings = QueryStrings.create().addParameter("kaptchaCode", imgcode);
            return apiUtil.get(ApiEnum.API_USERCENTER_CAPTCHA_CHECK, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("校验验证码失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("校验验证码失败", e);
            return new ErrorMessage(MessageStatus.ERROR);
        }
    }
}
