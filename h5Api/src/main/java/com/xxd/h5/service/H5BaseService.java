package com.xxd.h5.service;

import com.xxd.common.extension.spring.SpringApplicationUtil;
import com.xxd.common.util.ApiUtil;
import com.xxd.h5.service.transform.CouponTransformService;
import com.xxd.h5.service.transform.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

/**
 *
 * @author zhangshengwen
 * @date 2017/11/17
 */
@Service
public class H5BaseService {


    @Context
    protected HttpServletRequest request;

    @Context
    protected HttpServletResponse response;

    @Autowired
    protected ApiUtil apiUtil;

    @Autowired
    protected TransformService transformService;

    @Autowired
    protected CouponTransformService couponTransformService;

}
