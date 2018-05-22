package com.xxdai.weixin.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.AppConsts;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.weixin.ws.WeixinCXFService;
import com.xxdai.system.ws.SysConfigCXFService;
import com.xxdai.util.Configuration;
import com.xxdai.weixin.bean.WeixinAccount;
import com.xxdai.weixin.constants.WeixinConstants;
import com.xxdai.weixin.util.CacheUtil;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;

import com.xxdai.weixin.bean.WxAccessToken;
import com.xxdai.weixin.bean.result.WxError;
import com.xxdai.weixin.exception.WxErrorException;
import com.xxdai.weixin.util.http.RequestExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WxServiceImpl implements WxService {
    /**
      * 日志记录器
      */
    static Logger log = LoggerFactory.getLogger(WxServiceImpl.class);
	/**
	 * 全局的是否正在刷新Access Token的flag true: 正在刷新 false: 没有刷新
	 */
	protected static final AtomicBoolean GLOBAL_ACCESS_TOKEN_REFRESH_FLAG = new AtomicBoolean(
			false);

	protected final ThreadLocal<Integer> retryTimes = new ThreadLocal<Integer>();

	private static WxServiceImpl service = null;
	private static Object lock  = new Object();
    private static WeixinCXFService weixinCXFService = (WeixinCXFService) CXF_Factory.getFactory(WeixinCXFService.class, Configuration.getInstance().getValue("webService_url") + "/weixinWebService").create();
    private static SysConfigCXFService sysConfigCXFService = (SysConfigCXFService)CXF_Factory.getFactory(SysConfigCXFService.class, Configuration.getInstance().getValue("webService_url") + "/sysConfigWebService").create();
	
	public static WxServiceImpl getInstance() {
		synchronized(lock) {
			if(null == service) {				
				service = new WxServiceImpl();
                      /*
                //从缓存中获取微信服务号的微信号
                String weixinServiceCode = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_SERVICE_CODE);

                log.info("查询微信服务号信息");
                JSONObject paramJson = new JSONObject();
                paramJson.put("accountCode",weixinServiceCode);
                String weixinAccountResp = weixinCXFService.getWeixinAccoiunt(paramJson.toJSONString());
                WSModelResponse resp = JsonUtil.jsonToBean(weixinAccountResp, WSModelResponse.class);

                if (resp.getResultCode() == 0) {
                    WeixinAccount weixinAccount = JSONObject.parseObject(resp.getData().toString(), WeixinAccount.class);
                    WxInMemoryConfigStorage config = new WxInMemoryConfigStorage();
                    config.setAppId(weixinAccount.getAppid());
                    config.setSecret(weixinAccount.getAppsecret());

                    //默认为微信服务号token
                    String token = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_TOKEN);
                    token = "".equals(token) ? "xinxindai" : token;
                    config.setToken(token);
                    service.setWxConfigStorage(config);
                }  else {
                    log.info("获取不到微信公众号【" + weixinServiceCode + "】信息");
                }       */
                return service;
			}  else {
				return service;
			}
		}		
	}
	
	/**
	 * 检查签名
	 */
	public boolean checkSignature(String timestamp, String nonce,
			String signature) {
		try {
            //默认为微信服务号token
            String token = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_TOKEN);
			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			String[] arr = new String[] { token, timestamp, nonce };
			Arrays.sort(arr);
			StringBuilder sb = new StringBuilder();
			for (String a : arr) {
				sb.append(a);
			}
			sha1.update(sb.toString().getBytes());
			byte[] output = sha1.digest();
			return bytesToHex(output).equals(signature);
		} catch (Exception e) {
			return false;
		}
	}

	protected String bytesToHex(byte[] b) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < b.length; j++) {
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
		return buf.toString();
	}

	public void accessTokenRefresh() throws WxErrorException {
		if (!GLOBAL_ACCESS_TOKEN_REFRESH_FLAG.getAndSet(true)) {
			try {
                //微信应用ID
                String appid = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_APPID);
                //微信应用密钥
                String appsecret = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_APPSECRET);
				String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
						+ "&appid="
						+ appid
						+ "&secret="
						+ appsecret;
                log.info("weixin....accessTokenRefresh.url=" + url);
				try {
                    // 执行get请求.
                    HttpClient client = new HttpClient();
                    HttpMethod method = new GetMethod(url);
                    client.executeMethod(method);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
                    StringBuffer responseContent = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                    reader.close();
                    WxError error = WxError.fromJson(responseContent.toString());
                    if (error.getErrcode() != 0) {
                        throw new WxErrorException(error);
                    }
                    WxAccessToken accessToken = WxAccessToken
                            .fromJson(responseContent.toString());

                    //更新全局唯一票据
					/*wxConfigStorage.updateAccessToken(
							accessToken.getAccess_token(),
						accessToken.getExpires_in());    */

                    String weixinServiceCode = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_SERVICE_CODE);

                    //调用远程刷新票据方法
                    JSONObject refreshParams = new JSONObject();
                    refreshParams.put("accountCode", weixinServiceCode);
                    refreshParams.put("accessToken", accessToken.getAccess_token());
                    refreshParams.put("expiresIn", accessToken.getExpires_in());
                    log.info("refreshAccessToken req = "+refreshParams.toJSONString());
                    String refreshStr = weixinCXFService.refreshAccessToken(refreshParams.toJSONString());
                    log.info("refreshAccessToken resp="+refreshStr);
                    WSModelResponse refResponse = JsonUtil.jsonToBean(refreshStr, WSModelResponse.class);
                    if (refResponse.getResultCode() == 0) {
                        log.info("微信公众号唯一票据，更新成功");
                    } else {
                        log.info("微信公众号唯一票据，更新失败");
                    }
                } catch (WxErrorException we){
                    log.error(we.getMessage());
                    throw we;
				} catch (Exception e) {
                    log.error("刷新微信全局唯一票据失败，errorMessage=" + e.getMessage(),e);
					throw new RuntimeException(e);
				} 
			} finally {
				GLOBAL_ACCESS_TOKEN_REFRESH_FLAG.set(false);
			}
		} else {
			// 每隔100ms检查一下是否刷新完毕了
			while (GLOBAL_ACCESS_TOKEN_REFRESH_FLAG.get()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
			// 刷新完毕了，就没他什么事儿了
		}
	}

	/**
	 * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
	 * 
	 * @param executor
	 * @param uri
	 * @param data
	 * @return
	 * @throws WxErrorException
	 */
	public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data)
			throws WxErrorException {
        String accessToken = "";
        String weixinServiceCode = CacheUtil.getCacheValue(WeixinConstants.WEIXIN_SERVICE_CODE);
        JSONObject accessTokenJson  = new JSONObject();
        accessTokenJson.put("weixinServiceCode",weixinServiceCode);
        String accessTokenStr  = weixinCXFService.getAccessToken(accessTokenJson.toJSONString());
        WSModelResponse resp = JsonUtil.jsonToBean(accessTokenStr,WSModelResponse.class);
        if(resp.getResultCode() == AppConsts.WS_RETURN_SUCC) {
            accessToken = resp.getData().toString();
        } else {
            accessTokenRefresh();
        }

		String uriWithAccessToken = uri;
		uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token="
				+ accessToken : "&access_token=" + accessToken;

		try {
			return executor.execute(uriWithAccessToken, data);
		} catch (WxErrorException e) {
			WxError error = e.getError();
			/*
			 * 发生以下情况时尝试刷新access_token 40001
			 * 获取access_token时AppSecret错误，或者access_token无效 42001 access_token超时
			 */
			if (error.getErrcode() == 42001 || error.getErrcode() == 40001) {
				accessTokenRefresh();
				return execute(executor, uri, data);
			}
			/**
			 * -1 系统繁忙, 1000ms后重试
			 */
			if (error.getErrcode() == -1) {
				if (retryTimes.get() == null) {
					retryTimes.set(0);
				}
				if (retryTimes.get() > 4) {
					retryTimes.set(0);
					throw new RuntimeException("微信服务端异常，超出重试次数");
				}
				int sleepMillis = 1000 * (1 << retryTimes.get());
				try {
					log.info("微信系统繁忙，" + sleepMillis + "ms后重试");
					Thread.sleep(sleepMillis);
					retryTimes.set(retryTimes.get() + 1);
					return execute(executor, uri, data);
				} catch (InterruptedException e1) {
					throw new RuntimeException(e1);
				}
			}
			if (error.getErrcode() != 0) {
				throw new WxErrorException(error);
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}
