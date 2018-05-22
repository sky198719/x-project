package com.xxdai.weixin.api;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.weixin.ws.WeixinCXFService;
import com.xxdai.util.Configuration;
import com.xxdai.weixin.bean.WeixinAccount;
import com.xxdai.weixin.bean.WxXmlMessage;
import com.xxdai.weixin.bean.WxXmlOutMessage;
import com.xxdai.weixin.bean.WxXmlOutTextMessage;
import com.xxdai.weixin.bean.result.WxUser;
import com.xxdai.weixin.constants.WeixinConstants;
import com.xxdai.weixin.util.CacheUtil;
import com.xxdai.weixin.util.http.SimpleGetRequestExecutor;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信用户关注公众号，消息处理类
 * @author zhangyi
 *
 */

public class WxSubscribeMessageHandler implements WxMessageHandler {
	/**LOG日志记录器*/
	protected final Log log = LogFactory.getLog(getClass());

    private WeixinCXFService weixinCXFService = (WeixinCXFService) CXF_Factory.getFactory(WeixinCXFService.class, Configuration.getInstance().getValue("webService_url") + "/weixinWebService").create();

    public WxSubscribeMessageHandler(){
	}
	@Override
	public WxXmlOutMessage handle(WxXmlMessage wxMessage,
			Map<String, Object> context){		

		WxXmlOutMessage outMsg = null;
        String openId = wxMessage.getFromUserName();
		log.info("openId为"+(wxMessage == null ? "" : openId)+"的微信用户关注，开始关注公众号。");
		try{
			//远程获取当前微信用户信息
			log.info("=============================================");
			log.info("发送请求到微信服务器，查询微信用户信息");
			String uri = "https://api.weixin.qq.com/cgi-bin/user/info";
			String data = "openid="+openId+"&lang=zh_CN";
			String responseContext = WxServiceImpl.getInstance().execute(new SimpleGetRequestExecutor(), uri, data);
			log.info("远程请求内容：\n"+responseContext);
			
			//responseContext = new String(responseContext.getBytes("GBK"), "UTF-8");
			
			WxUser jsonUser = WxUser.fromJson(responseContext);
			log.info("=============================================");
			
			String nickName = jsonUser.getNickname();
			if(nickName != null && !"".equals(nickName)){
				Pattern pattern = Pattern.compile("[^\u4e00-\u9fa5\\w\\x00-\\x7F]*");
				Matcher matcher = pattern.matcher(nickName);
				//替换第一个符合正则的数据
				jsonUser.setNickname(matcher.replaceAll(""));
			}

			if(jsonUser != null && jsonUser.isSubscribe()){
				log.info("==================接收到微信用户关注消息==================");
               String weixinServiceCode = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_SERVICE_CODE);
               JSONObject paramJson = new JSONObject();
               paramJson.put("accountCode",weixinServiceCode);
               String weixinAccountResp = weixinCXFService.getWeixinAccoiunt(paramJson.toJSONString());
               WSModelResponse resp = JsonUtil.jsonToBean(weixinAccountResp, WSModelResponse.class);
                WeixinAccount weixinAccount = JSONObject.parseObject(resp.getData().toString(), WeixinAccount.class);

                JSONObject userJson = new JSONObject();
                userJson.put("wxAccountId",weixinAccount.getId());
                userJson.put("city",jsonUser.getCity());
                userJson.put("country",jsonUser.getCountry());
                userJson.put("headimgurl",jsonUser.getHeadimgurl());
                userJson.put("language",jsonUser.getLanguage());
                userJson.put("nickName",jsonUser.getNickname());
                userJson.put("openId",openId);
                userJson.put("province",jsonUser.getProvince());
                userJson.put("sex",jsonUser.getSex());
                userJson.put("subscribeTime",new Date());
                userJson.put("unionId",jsonUser.getUnionid());
                userJson.put("status",WeixinConstants.WX_USER_STATUS_SUBSCRIBE);

                String subStr = weixinCXFService.subscribeWeixinServiceNo(userJson.toJSONString());
                WSModelResponse subResp = JsonUtil.jsonToBean(subStr, WSModelResponse.class);
                if(subResp.getResultCode() == 0){
                    log.info("微信用户【" + openId + "】关注成功");
                    String toUserName = wxMessage.getToUserName();
                    String fromUserName = wxMessage.getFromUserName();
                    //构建响应微信用户关注消息
                    outMsg = createOutMessage(toUserName,fromUserName);
                } else {
                   log.info(subResp.getDesc());
                }
			} else {
				log.info("获取用户信息失败");
			}
		}catch(Exception e){
			log.error("微信用户关注公众号，消息处理失败", e);		
			WxXmlOutTextMessage outTextMsg = new WxXmlOutTextMessage();
			outTextMsg.setCreateTime(System.currentTimeMillis()/1000);
			outTextMsg.setContent("使用新新贷，财富常在！感谢您关注新新贷官方微信！\n\n更多内容请访问www.xinxindai.com官网或下载“新新贷”APP\n\n客服热线：4000169521");
			outTextMsg.setFromUserName(wxMessage.getToUserName());
			outTextMsg.setToUserName(wxMessage.getFromUserName());
			outTextMsg.setMsgType(WxConsts.XML_MSG_TEXT);			
			outMsg = outTextMsg;
		}
		log.info("处理微信用户关注公众号，返回null");
		return outMsg;
	}

    /**
     * 构建响应微信用户关注消息
     * @param toUserName
     * @param fromUserName
     * @return
     * @throws Exception
     */
	public WxXmlOutMessage createOutMessage(String toUserName,String fromUserName) throws Exception {
		WxXmlOutMessage outMsg = null;
        WxXmlOutTextMessage text = new WxXmlOutTextMessage();
        text.setCreateTime(System.currentTimeMillis()/1000);
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(WxConsts.XML_MSG_TEXT);
        text.setContent("使用新新贷，财富常在！感谢您关注新新贷官方微信！\n\n更多内容请访问www.xinxindai.com官网或下载“新新贷”APP\n\n客服热线：4000169521");
        outMsg = text;
		return outMsg;
	}	
}
