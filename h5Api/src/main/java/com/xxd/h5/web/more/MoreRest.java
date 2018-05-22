package com.xxd.h5.web.more;

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
 * 了解更多.
 * @author zhangshengwen
 */
@Component
@Path("/more/")
@Api(value = "更多", description = "披露公司相关信息")
public class MoreRest extends H5Rest {

    private static final Logger logger = LoggerFactory.getLogger(MoreRest.class);


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("opinion")
    @ApiOperation(value = "提交意见", httpMethod = "POST")
    public Message opinion(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "contract", value = "联系方式", required = true)
            @FormParam(value = "contract") @NotNull String contract,
            @ApiParam(name = "content", value = "意见", required = true)
            @FormParam(value = "content") @NotNull String content,
            @ApiParam(name = "imgCode", value = "图片验证码")
            @FormParam(value = "imgCode") String imgCode) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        QueryStrings queryStrings = QueryStrings.create().addParameter("kaptchaCode", imgCode);

        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("contract", contract)
                .addParameter("content", content);
        FormUrlEncoded form = FormUrlEncoded.create();
        form.setBody(requestDTO.transform2JsonString());
        try {
            return apiUtil.patch(ApiEnum.API_INVESTMENT_MORE_OPINION, headers, form, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("提交反馈意见失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("提交反馈意见失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("risk-exam")
    @ApiOperation(value = "获取风险评测问题", httpMethod = "GET")
    public Message riskExam(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis());
        try {
            Message riskExamMessage = apiUtil.get(ApiEnum.API_INVESTMENT_MORE_RISKEXAM, headers);
            if (MessageStatus.SUCCESS.getStatus() != riskExamMessage.getCode()) {
                return new ErrorMessage("error");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("riskExams", riskExamMessage.getData());
            return new SuccessMessage(jsonObject);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取风险评测问题失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取风险评测问题失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("risk-exam/result")
    @ApiOperation(value = "获取风险评测结果", httpMethod = "GET")
    public Message riskExamResult(
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
            return apiUtil.get(ApiEnum.API_INVESTMENT_MORE_RISKEXAM_RESULT, headers);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取风险评测问题结果失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取风险评测问题结果失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("risk-exam")
    @ApiOperation(value = "提交风险测评结果", httpMethod = "PUT")
    public Message postRiskExam(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "data", value = "{\n" +
                    "\t  \"answers\": [\n" +
                    "            {\n" +
                    "                \"questionDetailId\": \"A\",\n" +
                    "                \"questionId\": 268\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"questionDetailId\": \"B\",\n" +
                    "                \"questionId\": 269\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"ip\": \"127.0.0.1\"\n" +
                    "}", required = true)
            @FormParam(value = "data") @NotNull String data) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        FormUrlEncoded form = FormUrlEncoded.create();
        form.setBody(RequestDTO.create().wrapperWithData(data));
        try {
            return apiUtil.patch(ApiEnum.API_INVESTMENT_MORE_RISKEXAM_POST_RESULT, headers, form);
        } catch (CoreException | ServiceException exception) {
            logger.error("提交风险评测问题结果失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("提交风险评测问题结果失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }
}
