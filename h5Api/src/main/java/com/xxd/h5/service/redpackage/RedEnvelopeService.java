package com.xxd.h5.service.redpackage;

import com.xxd.common.bo.Message;
import com.xxd.common.remoting.http.parameters.Headers;

/**
 * created by xiguoding on 2018/4/27 下午3:45
 */
public interface RedEnvelopeService {
    /**
     * 获取新手红包列表
     *
     * @param headers
     * @param status
     * @param currentPage
     * @param pageSize
     * @return
     */
    Message getRedEnvelopes(Headers headers, int status, int currentPage, int pageSize);
}
