package com.xxd.h5.service.redpackage;

import com.xxd.common.bo.Message;
import com.xxd.common.remoting.http.parameters.Headers;

/**
 * created by xiguoding on 2018/4/26 下午5:16
 */
public interface RewardService {
    /**
     * 我的奖励接口
     *
     * @param headers
     * @param status
     * @param currentPage
     * @param pageSize
     * @return
     */
    Message getMyReward(Headers headers, int status, int currentPage, int pageSize);
}
