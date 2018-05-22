package com.xxd.h5.web.message;

import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.extension.jaxrs.PATCH;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.HttpHelper;
import com.xxd.h5.common.enums.MessageTypeEnum;
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
 * 消息接口.
 * @author zhangshengwen
 */
@Component
@Path("/messages")
@Api(value = "消息接口")
public class MessageRest extends H5Rest {

    private static final Logger logger = LoggerFactory.getLogger(MessageRest.class);

    @GET
    @Path("/system")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "系统消息列表", httpMethod = "GET")
    public Message systemMessageList(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "page", value = "int", required = true)
            @QueryParam(value = "page") @NotNull int currentPage,
            @ApiParam(name = "pageSize", value = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("type", MessageTypeEnum.MESSAGE_SYSTEM.getType())
                    .addParameter("currentPage", currentPage)
                    .addParameter("pageSize", pageSize);
            return apiUtil.get(ApiEnum.API_INVESTMENT_INFO_MESSAGE_LIST, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取系统消息失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取系统消息失败", e);
            return new ErrorMessage(MessageStatus.ERROR);
        }
    }

    @PUT
    @Path("/system/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "单条系统消息置为已读", httpMethod = "PUT")
    public Message messageList(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "id", value = "id", required = true)
            @PathParam(value = "id") @NotNull String id) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());
            QueryStrings queryStrings = QueryStrings.create().setPath("/").addPath(id);
            return apiUtil.patch(ApiEnum.API_INVESTMENT_INFO_SYSTEM_MESSAGE_ALREADY, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("单条系统消息置为已读失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("单条系统消息置为已读失败", e);
            return new ErrorMessage(MessageStatus.ERROR);
        }
    }

    @GET
    @Path("/notice")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "公告消息列表", httpMethod = "GET")
    public Message noticeMessageList(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token")
            @HeaderParam(value = "token") String token,
            @ApiParam(name = "page", value = "int", required = true)
            @QueryParam(value = "page") @NotNull int currentPage,
            @ApiParam(name = "pageSize", value = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("currentPage", currentPage)
                    .addParameter("pageSize", pageSize);
            // 已登录和未登录状态下公告的消息多个消息已读状态字段
            if (StringUtils.isNotBlank(token)) {
                        queryStrings.addParameter("type", MessageTypeEnum.MESSAGE_NOTICE.getType());
                headers.addHeader("token", token).addHeader("User-Agent", HttpHelper.getUserAgent(request));
                return apiUtil.get(ApiEnum.API_INVESTMENT_INFO_MESSAGE_LIST, headers, queryStrings);
            } else {
                return apiUtil.get(ApiEnum.API_INVESTMENT_INFO_NOTICE_LIST, headers, queryStrings);
            }
        } catch (CoreException | ServiceException exception) {
            logger.error("获取公告消息失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取公告消息失败", e);
            return new ErrorMessage(MessageStatus.ERROR);
        }
    }

    @GET
    @Path("/unread-count")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "未读消息数量", httpMethod = "GET")
    public Message unReadMessageCount(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", type = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token) {
        Headers headers = Headers.createHeaders()
                .addHeader("token", token)
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        try {
            return apiUtil.get(ApiEnum.API_INVESTMENT_INFO_MESSAGE_NUM, headers);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取未读消息数量失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取未读消息数量失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/notice/{id}")
    @ApiOperation(value = "单条公告置为已读状态", httpMethod = "PUT")
    public Message messageStatus(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", type = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "id", value = "id", required = true)
            @PathParam(value = "id") @NotNull String id) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            QueryStrings queryStrings = QueryStrings.create()
                    .setPath("/").addPath(id);
                return apiUtil.patch(ApiEnum.API_INVESTMENT_INFO_NOTICE_MESSAGE_ALREADY, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("将消息置为已读失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("将消息置为已读失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/notice/{id}")
    @ApiOperation(value = "公告消息详情", httpMethod = "GET")
    public Message messageDetail(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token")
            @HeaderParam(value = "token") String token,
            @ApiParam(name = "id", type = "long", required = true)
            @PathParam(value = "id") @NotNull long id) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());
            QueryStrings queryStrings = QueryStrings.create()
                    .setPath(id);
            if (StringUtils.isNotBlank(token)) {
                headers.addHeader("token", token).addHeader("User-Agent", HttpHelper.getUserAgent(request));
                return apiUtil.get(ApiEnum.API_INVESTMENT_INFO_MESSAGES_DETAIL_USER, headers, queryStrings);
            } else {
                return apiUtil.get(ApiEnum.API_INVESTMENT_INFO_MESSAGES_DETAIL, headers, queryStrings);
            }
        } catch (CoreException | ServiceException exception) {
            logger.error("获取消息详情失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取消息详情失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "将消息批量置为已读状态", httpMethod = "PATCH")
    public Message messageStatus(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "type", value = "1-通知公告,2-系统消息", required = true)
            @QueryParam(value = "type") @NotNull int type) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("type", type);
            return apiUtil.patch(ApiEnum.API_INVESTMENT_INFO_SYSTEM_MESSAGES_ALREADY, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("将消息批量置为已读失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("将消息批量置为已读失败", e);
            return new ErrorMessage(MessageStatus.ERROR);
        }
    }
}