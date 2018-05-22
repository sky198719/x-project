package com.xxd.h5.web.news;

import com.xxd.common.bo.Message;
import com.xxd.h5.service.news.NewsService;
import com.xxd.h5.web.H5Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * created by xiguoding on 2018/5/15 上午9:23
 */
@RestController
@Path("/news")
@Api(value = "新闻相关接口")
public class News extends H5Rest {
    @Autowired
    private NewsService newsService;

    @GET
    @Path("/getNewsNoticesById")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "新闻管理", httpMethod = "GET")
    public Message getNewsNotices(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "id", value = "id", required = true)
            @QueryParam(value = "id") @NotNull String id) {
        return newsService.getNewsNotices(clientId, id);
    }
}
