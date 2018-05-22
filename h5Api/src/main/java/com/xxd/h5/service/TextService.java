package com.xxd.h5.service;

import com.xxd.common.extension.spring.GlobalProperties;
import org.springframework.stereotype.Service;

/**
 * 获取配置文字信息.
 */
@Service
public class TextService extends H5BaseService{

    /**
     * 获取充值页面文案提示.
     * @return
     */
    public String getRechargeText() {
        return GlobalProperties.getProperty("app.text.recharge.tips");
    }

    /**
     * 获取提现页面文案提示.
     * @return
     */
    public String getWithdrawText() {
        return GlobalProperties.getProperty("app.text.withdraw.tips");
    }

}
