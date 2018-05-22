package com.xxdai.weixin.api;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.weixin.ws.WeixinCXFService;
import com.xxdai.util.Configuration;
import com.xxdai.weixin.bean.WeixinUser;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.xxdai.weixin.bean.WxXmlMessage;
import com.xxdai.weixin.bean.WxXmlOutMessage;

/**
 * 微信用户取消关注公众号，消息处理类
 *
 * @author zhangyi
 */

public class WxUnSubscribeMessageHandler implements WxMessageHandler {
    /**
     * LOG日志记录器
     */
    protected final Log log = LogFactory.getLog(getClass());

    private WeixinCXFService weixinCXFService = (WeixinCXFService) CXF_Factory.getFactory(WeixinCXFService.class, Configuration.getInstance().getValue("webService_url") + "/weixinWebService").create();

    public WxUnSubscribeMessageHandler() {
    }

    @Override
    public WxXmlOutMessage handle(WxXmlMessage wxMessage,
                                  Map<String, Object> context) {
        log.info("openId为" + (wxMessage == null ? "" : wxMessage.getFromUserName()) + "的微信用户，取消关注公众号");
        try {

            JSONObject param = new JSONObject();
            param.put("openId", wxMessage.getFromUserName());
            String unSubStr = weixinCXFService.unSubscribeWeixinServiceNo(param.toJSONString());
            WSModelResponse resp = JsonUtil.jsonToBean(unSubStr, WSModelResponse.class);

            if (resp.getResultCode() == 0) {
                log.info("微信用户取消关注，信息更新成功");
            } else {
                log.info("微信用户取消关注，信息更新失败，errorMsg = " + resp.getDesc());
            }
        } catch (Exception e) {
            log.error("微信用户取消关注公众号，消息处理失败", e);
        }
        log.info("处理微信用户取消关注公众号，返回null");
        return null;
    }
}
