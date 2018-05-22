package com.xxd.h5.service.redpackage;

import com.xxd.common.bo.Message;
import com.xxd.common.remoting.http.parameters.Headers;

/**
 * created by xiguoding on 2018/4/28 下午2:05
 */
public interface RateCouponService {
    /**
     * 获取红包、加息券
     *
     * @param headers
     * @param status
     * @param type
     * @param currentPage
     * @param pageSize
     * @return
     */
    Message getRateCoupons(Headers headers, int status, int type, int currentPage, int pageSize);
}
