/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.weixin.api;

import com.xxdai.weixin.bean.WxXmlMessage;
import com.xxdai.weixin.bean.WxXmlOutMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * 描述
 *
 * @version $Id: WxTransferCustomerMessageHandler.java 2015/3/20 17:05 $
 * @since jdk1.6
 */
public class WxTransferCustomerMessageHandler  implements WxMessageHandler {
    /**LOG日志记录器*/
   	protected final Log log = LogFactory.getLog(getClass());

    public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context) {
        String openId = wxMessage.getFromUserName();
       	log.info("openId为"+(wxMessage == null ? "" : openId)+"的微信用户发送消息");
        WxXmlOutMessage outMsg = null;
        try{
            outMsg = new WxXmlOutMessage();
            outMsg.setToUserName(wxMessage.getFromUserName());
            outMsg.setFromUserName(wxMessage.getToUserName());
            outMsg.setCreateTime(wxMessage.getCreateTime());
            outMsg.setMsgType("transfer_customer_service");
        }catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return outMsg;
    }
}
