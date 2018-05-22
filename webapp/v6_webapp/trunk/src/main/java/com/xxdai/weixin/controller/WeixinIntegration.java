package com.xxdai.weixin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.exception.ServiceException;
import com.xxdai.http.ApiUtil;
import com.xxdai.http.Message;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.RedisUtil;
import com.xxdai.util.SpringApplicationUtil;
import com.xxdai.util.TokenUtil;
import com.xxdai.weixin.api.*;
import com.xxdai.weixin.bean.result.WxError;
import com.xxdai.weixin.bean.result.WxUser;
import com.xxdai.weixin.exception.WxErrorException;
import com.xxdai.weixin.util.CacheUtil;
import com.xxdai.weixin.util.SignatureUtil;
import com.xxdai.weixin.util.http.SimpleGetRequestExecutor;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxdai.weixin.bean.WxXmlMessage;
import com.xxdai.weixin.bean.WxXmlOutMessage;
import com.xxdai.weixin.bean.WxXmlOutTextMessage;
import com.xxdai.weixin.constants.WeixinConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 处理微信服务器请求的Servlet URL地址：http://www.xinxindai.com/weixin/api.html
 * 注意：官方文档限制使用80端口哦！
 * @author zhangyi
 *
 */
@Controller
@RequestMapping("/weixin/api")
public class WeixinIntegration {
	
    /**
      * 日志记录器
      */
     Logger log = LoggerFactory.getLogger(WeixinIntegration.class);
	
	private static WxMessageRouter router = new WxMessageRouter();



    /**
     * 接管来自微信服务器的接入请求的方法
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(method = {RequestMethod.GET})
	public @ResponseBody String authGet(HttpServletRequest request, HttpServletResponse response) {
		 log.info("\n微信服务器接入网站服务器开始");
		String result = "";		
		try {
			//设置字符集编码
			request.setCharacterEncoding(WeixinConstants.CHARACTERENCODING_UTF8);	       
	        response.setCharacterEncoding(WeixinConstants.CHARACTERENCODING_UTF8);
	        response.setContentType(WeixinConstants.CONTENTTYPE_TEXT_HTML);        
	        
	        String timestamp = request.getParameter(WeixinConstants.TIMESTAMP);
	        String nonce = request.getParameter(WeixinConstants.NONCE);
	        String signature = request.getParameter(WeixinConstants.SIGNATURE);
	        String echostr = request.getParameter(WeixinConstants.ECHOSTR);
	        
	        StringBuffer buf = new StringBuffer();
	        buf.append("\n接入信息如下：");
	        buf.append("\ntimestamp=").append(timestamp);
	        buf.append("\nnonce=").append(nonce);
	        buf.append("\nsignature=").append(signature);
	        buf.append("\nechostr=").append(echostr);
	        log.info(buf.toString());	
	       
	        //微信服务器，验证URL有效性
	        if(WxServiceImpl.getInstance().checkSignature(timestamp, nonce, signature)){
	        	result =  echostr;
	        	log.info("接入成功。。。");
	        } else {
	        	log.info("接入失败。。。");
	        }
		}catch(Exception e){
			log.error("微信服务器接入请求失败", e);
			  
		}
		log.info("微信服务器接入网站服务器结束。。。");
		return result;
	}	
	
	/**
	 * 接管来自微信服务器的推送请求，文本消息、图片消息、语音消息、菜单事件等等<br>
	 * 最后把回复返回给微信服务器，让其推送给客户微信客户端<br>
	 * @param requestBody
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public @ResponseBody String post(@RequestBody String requestBody,HttpServletResponse response) {
		log.info("接收到微信服务器推送消息");
		log.info(requestBody);		
		WxXmlOutMessage outMessage = null;
		WxXmlMessage inMessage = null;
		try {
			if(router.getRules() != null && router.getRules().size() == 0){
				log.info("初始化消息处理规则。。。。");
				router.rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE).handler(new WxSubscribeMessageHandler()).end();
				router.rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_UNSUBSCRIBE).handler(new WxUnSubscribeMessageHandler()).end();
                router.rule().async(false).msgType(WxConsts.XML_MSG_TEXT).handler(new WxTransferCustomerMessageHandler()).end();
			}
			inMessage = WxXmlMessage.fromXml(requestBody);
			outMessage = router.route(inMessage);
		} catch (Exception e) {
			log.error("处理微信服务器推送消息失败 ", e);			  
			e.printStackTrace();
			if(inMessage != null){
				WxXmlOutTextMessage outTextMessage = new WxXmlOutTextMessage();
				outTextMessage.setToUserName(inMessage.getFromUserName());
				outTextMessage.setFromUserName(inMessage.getToUserName());
				outTextMessage.setCreateTime(System.currentTimeMillis() / 1000L);
				outTextMessage.setContent("服务器处理你的操作出现异常，请重新尝试或联系客服");
				outMessage = outTextMessage;
			}			
		}
		String result = outMessage == null ? "" : outMessage.toXml();
		log.info("返回给微信服务器消息：\n"+result);	
		return result;
	}

	@RequestMapping(value = "/getJsSignature", produces = {"application/json;charset=UTF-8"})
	public @ResponseBody String getJsSignature(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			String url = request.getParameter("url");

			RedisUtil redisUtil = (RedisUtil) SpringApplicationUtil.getBean("redisUtil");
			String cacheTicket = redisUtil.getRedisValueStr(WeixinConstants.JSAPI_TICKET);
			if(cacheTicket != null) {
				log.info("get cache ");
				Map map = SignatureUtil.sign(cacheTicket,url);
				result.put("appId", CacheUtil.getCacheValue(WeixinConstants.WEIXIN_APPID));
				result.put("timestamp",map.get("timestamp"));
				result.put("nonceStr",map.get("nonceStr"));
				result.put("signature",map.get("signature"));
				result.put("url",url);
			} else {
				log.info("reload getticket");
				String uri = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
				String data = "type=jsapi";
				String responseContext = WxServiceImpl.getInstance().execute(new SimpleGetRequestExecutor(), uri, data);
				log.info("远程请求内容：\n"+responseContext);
				JSONObject ticketObj = JSONObject.parseObject(responseContext);
				if(ticketObj != null && ticketObj.getString("ticket") != null) {
					String ticket = ticketObj.getString("ticket");
					redisUtil.setRedisStr(WeixinConstants.JSAPI_TICKET,ticket,WeixinConstants.JSAPI_EXPIRES_IN);
					Map map = SignatureUtil.sign(ticket,url);
					result.put("appId", CacheUtil.getCacheValue(WeixinConstants.WEIXIN_APPID));
					result.put("timestamp",map.get("timestamp"));
					result.put("nonceStr",map.get("nonceStr"));
					result.put("signature",map.get("signature"));
					result.put("url",url);
				}else {
					result.put("code",1);
					result.put("info","获取不到");
				}
			}
		}catch (Exception e) {
			log.error("getJsSignature error",e);
			result.put("code",400);
			result.put("info","获取页面签名异常");
		}finally {
			return result.toJSONString();
		}
	}

	@RequestMapping(value = "/thirdParthUserInfo", produces = {"application/json;charset=UTF-8"})
	public @ResponseBody String thirdParthUserInfo(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			String key = request.getParameter("key");
			if(key == null || "".equals(key)) {
				log.info("thirdParthUserInfo key is null");
				result.put("code",100);
				result.put("info","key不能为空");
				return TokenUtil.jsonpCallback(request,result.toJSONString());
			}

			RedisUtil redisUtil = (RedisUtil) SpringApplicationUtil.getBean("redisUtil");
			String page_access_token = redisUtil.getRedisValueStr(key);
			if(page_access_token == null || "".equals(page_access_token)) {
				log.info("page_access_token is null");
				result.put("code",100);
				result.put("info","请先进行页面鉴权");
				return TokenUtil.jsonpCallback(request,result.toJSONString());
			}

			//远程获取当前微信用户信息
			log.info("=============================================");
			log.info("发送请求到微信服务器，查询微信用户信息");

			HttpClient client = new HttpClient();
			// 执行get请求.
			HttpMethod method = new GetMethod("https://api.weixin.qq.com/sns/userinfo?access_token="+page_access_token+"&openid="+key+"&lang=zh_CN");
			client.executeMethod(method);
			BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
			StringBuffer responseContent = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				responseContent.append(line);
			}
			reader.close();
			String resultContent = responseContent.toString();
			// System.out.println("get resultContent===" + resultContent);
			log.info("get resultContent===" + resultContent);
			WxError error = WxError.fromJson(resultContent);
			if (error.getErrcode() != 0) {
				log.error("解析异常");
				throw new WxErrorException(error);
			}

			method.releaseConnection();
			JSONObject user = JSONObject.parseObject(resultContent);
			result.put("code",1);
			JSONObject dataJson = new JSONObject();
			dataJson.put("nickname",user.get("nickname"));
			dataJson.put("headimgurl",user.getString("headimgurl"));
			result.put("data",dataJson);

		}catch (Exception e) {
			log.error("thirdParthUserInfo error",e);
			result.put("code",400);
			result.put("info","异常");
		}finally {
			return TokenUtil.jsonpCallback(request,result.toJSONString());
		}
	}
}
