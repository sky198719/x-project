package com.xxd.h5.web.account;

import com.alibaba.fastjson.JSONObject;
import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.constants.GlobalConstants;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.FormUrlEncoded;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.MultipartFormData;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.CodecUtil;
import com.xxd.common.util.HttpHelper;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.service.account.AccountService;
import com.xxd.h5.web.H5Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * app用户资产相关restful接口.
 * @author zhangshengwen
 */

@Component
@Path("/accounts")
@Api(value = "账户", description = "账户相关接口")
public class AccountRest extends H5Rest {

    private static final Logger logger = LoggerFactory.getLogger(AccountRest.class);

    @Autowired
    AccountService accountService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "用户账户信息", httpMethod = "GET")

    public Message accountInfo(
            @ApiParam(name = "clientId", value = "clientId", format = "String", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token) {
        try {
            return accountService.getAccountInfo(clientId, token, HttpHelper.getUserAgent(request));
        } catch (CoreException | ServiceException exception) {
            logger.error("获取用户账户信息失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取用户账户信息失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    // =========================== 资产相关接口 ==============================

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/trades")
    @ApiOperation(value = "交易记录", httpMethod = "GET")
    public Message tradeRecord(
            @ApiParam(name = "clientId", value = "clientId", format = "String", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "page", value = "page", required = true)
            @QueryParam(value = "page") @NotNull int page,
            @ApiParam(name = "pageSize", value = "pageSize", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize,
            @ApiParam(name = "tradeType", value = "资金类别 0全部、1投标、2借款、3还款、4充值、5提现、6奖励、7收款、8冻结、9其他")
            @QueryParam(value = "tradeType") @NotNull String tradeType) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token", token)
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("currentPage", page)
                    .addParameter("pageSize", pageSize)
                    .addParameter("type", tradeType);
            return apiUtil.get(ApiEnum.API_INVESTMENT_ASSET_TRANRECORD, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取交易记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取交易记录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }




    // =========================== 实名认证 ==============================
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/approve")
    @ApiOperation(value = "实名认证", httpMethod = "POST")
    public Message approve(
            @ApiParam(name = "clientId", value = "clientId", format = "String", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "realName", value = "realName", format = "String", required = true)
            @FormParam(value = "realName") @NotNull String realName,
            @ApiParam(name = "idCardNumber", value = "idCardNumber", format = "String", required = true)
            @FormParam(value = "idCardNumber") @NotNull String idCardNumber,
            @ApiParam(name = "cardType", value = "1:身份证，2:澳门特别行政区护照，3:台湾居民往来大陆通行证，4:澳门特别行政区护照", format = "int", required = true)
            @FormParam(value = "cardType") @NotNull String cardType) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            JSONObject data = new JSONObject();
            JSONObject dataBody = new JSONObject();
            data.put("data", dataBody);
            dataBody.put("realName", realName);
            dataBody.put("idCardNumber", idCardNumber);
            dataBody.put("cardType", cardType);
            FormUrlEncoded form = FormUrlEncoded.create().setBody(data.toJSONString());
            return apiUtil.post(ApiEnum.API_USERCENTER_USER_APPRO_AUTOREALNAME, headers, form);
        } catch (CoreException | ServiceException exception) {
            logger.error("用户实名认证失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("用户实名认证失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/approve/manual")
    @ApiOperation(value = "人工实名认证", httpMethod = "POST", consumes = "multipart/form-data")
    public Message approveManual(
            @ApiParam(name = "clientId", value = "clientId", format = "String", required = true)
            @HeaderParam(value = "clientId") String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") String token,
            @ApiParam(name = "realName", value = "真实姓名", format = "String", required = true)
            @FormDataParam(value = "realName") String realName,
            @ApiParam(name = "idCardNumber", value = "身份证号码", format = "String", required = true)
            @FormDataParam(value = "idCardNumber") String idCardNumber,
            @ApiParam(name = "cardType", value = "身份证类型", format = "int", defaultValue = "1", required = true)
            @FormDataParam(value = "cardType") int cardType,
            @ApiParam(name = "positivePic", value = "身份证正面照片", format = "file", required = true)
            @FormDataParam(value = "positivePic") InputStream positivePic,
            @FormDataParam(value = "positivePic") FormDataContentDisposition positivePicDisposition,
            @ApiParam(name = "negativePic", value = "身份证反面照片", format = "String", required = true)
            @FormDataParam(value = "negativePic") InputStream negativePic,
            @FormDataParam(value = "negativePic") FormDataContentDisposition negativePicPicDisposition) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("bizCode", GlobalConstants.FILECENTER_BIZCODE)
                    .addParameter("fileDir", GlobalConstants.FILECENTER_FILEDIR_PREFIX + CodecUtil.md5Hex(token));

            // 上传身份证正面
            MultipartFormData positivePicForm = MultipartFormData.create()
                    .addParameter("file", positivePic, positivePicDisposition.getFileName());
            Message positivePicMessage = apiUtil.post(ApiEnum.API_FILECENTER_FILES, headers, positivePicForm, queryStrings);

            if (positivePicMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage(MessageStatus.NETWORK_ERROR.getStatus(), "上传身份证照片失败");
            }

            // 上传身份证反面
            MultipartFormData negativePicForm = MultipartFormData.create()
                    .addParameter("file", negativePic, negativePicPicDisposition.getFileName());
            Message negativePicMessage = apiUtil.post(ApiEnum.API_FILECENTER_FILES, headers, negativePicForm, queryStrings);
            if (negativePicMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage(MessageStatus.NETWORK_ERROR.getStatus(), "上传身份证照片失败");
            }

            JSONObject positiveJsonObject = JsonUtil.toJSONObject(positivePicMessage.getData());
            JSONObject negativeJsonObject = JsonUtil.toJSONObject(negativePicMessage.getData());
            if (positiveJsonObject == null || negativeJsonObject == null) {
                return new ErrorMessage(MessageStatus.NETWORK_ERROR.getStatus(), "上传身份证照片失败");
            }

            String positivePicID = positiveJsonObject.getString("fileId");
            String negativePicID = negativeJsonObject.getString("fileId");

            //{ "data": { "realName": "王刚", "idCardNumber": "59466ce0191505196fd7eb6fa6590ae4", "cardType": "1", "positivePicID": "111", "negativePicID": "222" } }


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", new JSONObject());
            JSONObject data = jsonObject.getJSONObject("data");
            data.put("realName", realName);
            data.put("idCardNumber", idCardNumber);
            data.put("cardType", cardType);
            data.put("positivePicID", positivePicID);
            data.put("negativePicID", negativePicID);

            headers.addHeader("token", token)
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            FormUrlEncoded formUrlEncoded = FormUrlEncoded.create()
                    .setBody(jsonObject.toJSONString());
            return apiUtil.post(ApiEnum.API_USERCENTER_USER_APPRO_MANUALREALNAME, headers, formUrlEncoded);
        } catch (CoreException | ServiceException exception) {
            logger.error("人工实名认证失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("人工实名认证失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    // =========================== 开户 ==============================
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/fuyou")
    @ApiOperation(value = "获取跳转到富有开户页的链接", httpMethod = "GET")
    public Message approve(
            @ApiParam(name = "clientId", value = "clientId", format = "String", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));

            return apiUtil.post(ApiEnum.API_USERCENTER_USER_OPENACCOUNT_MOBILE, headers);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取跳转到富有开户页链接失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取跳转到富有开户页链接失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/confirm")
    @ApiOperation(value = "确认已开户", httpMethod = "PUT")
    public Message confirmOpenAccount(
            @ApiParam(name = "clientId", value = "clientId", format = "String", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));

            return apiUtil.post(ApiEnum.API_USERCENTER_CONFIRM_OPEN_ACCOUNT, headers);
        } catch (CoreException | ServiceException exception) {
            logger.error("确认开户失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("确认开户失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }
}
