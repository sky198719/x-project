package com.xxdai.weixin.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 微信工具类
 * 
 * @author zhangyi
 * 
 */
public class CommonUtil {
	/**LOG日志记录器*/
	protected static final Log log = LogFactory.getLog(CommonUtil.class);
	
	

	
	public static void createMeun(){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=OcFOLMYJEgNBSI_NHSUFzDaXV419WwwWcNOBbHi31pEDAo5Z4JsFUE5UU00la2VrnK_SRrOzQfwMRvhMebW0OYqOBBr6RgWU8PrB4u7kBnE";
		
		/*JSONObject about = new JSONObject();
		about.put("type", "click");
		about.put("name", "关于我们");
		about.put("key", "关于我们");	*/	
		
	/*	JSONObject ceo = new JSONObject();
		ceo.put("type", "click");
		ceo.put("name", "CEO专栏");
		ceo.put("key", "CEO专栏");		*/
		
		/*JSONObject itender = new JSONObject();
		itender.put("type", "click");
		itender.put("name", "i投微客");
		itender.put("key", "i投微客");*/
		
		/*JSONObject reg = new JSONObject();
		reg.put("type", "click");
		reg.put("name", "九秒注册");
		reg.put("key", "九秒注册");*/
		
	/*	JSONObject contact = new JSONObject();
		contact.put("type", "click");
		contact.put("name", "联系我们");
		contact.put("key", "联系我们");*/
		
		/*JSONObject app = new JSONObject();
		app.put("type", "view");
		app.put("name", "下载APP");
		app.put("url", "http://101.231.117.66/v5_front/weixin/view/downloadApp.html");*/
		
		/*JSONObject acquireGift = new JSONObject();
		acquireGift.put("type", "view");
		acquireGift.put("name", "领取红包");
		acquireGift.put("url", "http://101.231.117.66/v5_front/weixin/view/acquireGift.html");*/
		
		
	/*	JSONArray news_sub_button = new JSONArray();
		news_sub_button.add(about);
		news_sub_button.add(ceo);
		news_sub_button.add(itender);*/
		
		/*JSONObject news = new JSONObject();
		news.put("name", "新新快讯");
		news.put("sub_button", news_sub_button);
		
	/JSONArray guide_sub_button = new JSONArray();
		guide_sub_button.add(reg);		
		guide_sub_button.add(contact);
		
		JSONObject guide = new JSONObject();
		guide.put("name", "快捷指南");
		guide.put("sub_button", guide_sub_button);*/
		
        /*
		JSONObject myGift = new JSONObject();
		myGift.put("type", "view");
		myGift.put("name", "我的红包");
		myGift.put("url", "http://v5test.yongxindai.com/weixincoupon/myGift.html");
		
		JSONArray activity_sub_button = new JSONArray();
		//activity_sub_button.add(acquireGift);
		activity_sub_button.add(myGift);
		
	//	activity_sub_button.add(app);
		JSONObject reg = new JSONObject();
		reg.put("type", "view");
		reg.put("name", "快速注册");
		reg.put("url", "http://v5test.yongxindai.com/weixin/view/reg.html");	
		
		JSONObject activity = new JSONObject();
		activity.put("name", "最新活动");
		activity.put("sub_button", activity_sub_button);
		
		
		JSONObject dow = new JSONObject();
		dow.put("type", "view");
		dow.put("name", "APP下载");
		dow.put("url", "http://v5test.yongxindai.com/weixin/view/downloadApp.html");	
		
		JSONArray button = new JSONArray();
		button.add(reg);
		button.add(dow);
		button.add(activity);
		
		JSONObject json = new JSONObject();
		json.put("button", button);
		
		try{
			
			Builder config = new AsyncHttpClientConfig.Builder();
			NettyAsyncHttpProvider nettyAsyncHttpProvider = new NettyAsyncHttpProvider(config.build());
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient(nettyAsyncHttpProvider);			
			BoundRequestBuilder brb = asyncHttpClient.preparePost(url);
			brb.setBody(json.toString());
			brb.setBodyEncoding("UTF-8");
			Future future = brb.execute();
			NettyResponse nettyResponse = (NettyResponse)future.get();
			System.out.println(nettyResponse.getResponseBody());		
			asyncHttpClient.closeAsynchronously();
		}catch(Exception e){
			
		}      */
	}
	public static void getAccessToken(){
	/*	try{
			Builder config = new AsyncHttpClientConfig.Builder();
			NettyAsyncHttpProvider nettyAsyncHttpProvider = new NettyAsyncHttpProvider(config.build());
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient(nettyAsyncHttpProvider);			
			Future future = asyncHttpClient.prepareGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx2d096773be4003ed&secret=f228e8fa6d4b8d273a6f6a9fc2e47deb").execute();
			NettyResponse nettyResponse = (NettyResponse)future.get();
			System.out.println(nettyResponse.getResponseBody());		
			asyncHttpClient.closeAsynchronously();
		}catch(Exception e){
			e.printStackTrace();
		}   */
	}
	
	public static void getUserList(){
	/*	try{
			Builder config = new AsyncHttpClientConfig.Builder();
			NettyAsyncHttpProvider nettyAsyncHttpProvider = new NettyAsyncHttpProvider(config.build());
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient(nettyAsyncHttpProvider);			
			Future future = asyncHttpClient.prepareGet("https://api.weixin.qq.com/cgi-bin/user/get?access_token=p1L775RYz-SNDCTWWyIG4S3bGVZMGdBLmO86O-E5Vn8AhSMOIMdzhpbqwrmtbTDktGZbuq1dIxwTIS52XOUTqGwyLBHLZw1edh6XPOBB8Po").execute();
			NettyResponse nettyResponse = (NettyResponse)future.get();
			System.out.println(nettyResponse.getResponseBody());		
			asyncHttpClient.closeAsynchronously();
		}catch(Exception e){
			e.printStackTrace();
		}  */
	}
	
	public static void sendMessage(){
	/*	try{
			Builder config = new AsyncHttpClientConfig.Builder();
			NettyAsyncHttpProvider nettyAsyncHttpProvider = new NettyAsyncHttpProvider(config.build());
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient(nettyAsyncHttpProvider);			
			BoundRequestBuilder brb = asyncHttpClient.preparePost("https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=OQga7lijZP7E7wbmND0CyrdoQlLI_l5p1PdnSuNaWObfinmi4kCOTjIm-C5ftr2rabPQQpyp4_ZDa3H4-8W2oSAWiUG7B769brvvPvyJiyc");
		    JSONObject json = new JSONObject();
		    JSONArray touserJson = new JSONArray();
		    touserJson.add("oVIbjs6Qnfaxs_zESFm9SN11c9Ss");
		    touserJson.add("oVIbjs3Lwf32AN5botSqtvHE94Ns");
		    touserJson.add("oVIbjs23FK0aJg7KlZq1gaBs_bX8");
		    touserJson.add("oVIbjs4OML3i7vlY-MpiYCsbKJr8");
		    touserJson.add("oVIbjs4swvtAwNXG29maOdspf1Iw");
		    touserJson.add("oVIbjs0sfRAN7poLAwiumr5TBrgk");
		    touserJson.add("oVIbjs8GgYbpPGsYNEYgozAMaZ4o");
		    touserJson.add("oVIbjsyeaqiuXMfpjAEBlOmMpL-Q");
		    touserJson.add("oVIbjs4gqrKnB5qV-JVqyjdu3hSI");
		    touserJson.add("oVIbjs-bcP3AQEogZJpUM2JCq_W8");
		    json.put("touser", touserJson);		  
		    json.put("msgtype", "text");
		    JSONObject textJson = new JSONObject();		    
		    textJson.put("content", "群发");
		    json.put("text", textJson);
			brb.setBody(json.toString());
			brb.setBodyEncoding("UTF-8");
			Future future = brb.execute();
			NettyResponse nettyResponse = (NettyResponse)future.get();
			System.out.println(nettyResponse.getResponseBody());		
			asyncHttpClient.closeAsynchronously();
		}catch(Exception e){
			e.printStackTrace();
		}      */
	}
	
	
	public static void uploadnews(){
	/*	try{
			Builder config = new AsyncHttpClientConfig.Builder();
			NettyAsyncHttpProvider nettyAsyncHttpProvider = new NettyAsyncHttpProvider(config.build());
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient(nettyAsyncHttpProvider);
			BoundRequestBuilder brb  = asyncHttpClient.preparePost("https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=OQga7lijZP7E7wbmND0CyrdoQlLI_l5p1PdnSuNaWObfinmi4kCOTjIm-C5ftr2rabPQQpyp4_ZDa3H4-8W2oSAWiUG7B769brvvPvyJiyc");
			JSONObject json = new JSONObject();
			JSONObject articlesJSON = new JSONObject();
			json.put("articles", articlesJSON);
			brb.setBody(json.toString());
			brb.setBodyEncoding("UTF-8");
			Future future = brb.execute();
			NettyResponse nettyResponse = (NettyResponse)future.get();
			System.out.println(nettyResponse.getResponseBody());		
			asyncHttpClient.closeAsynchronously();
		}catch(Exception e){
			e.printStackTrace();
		}   */
	}
	
	public static void deleteMeun(){
		/*try{
			Builder config = new AsyncHttpClientConfig.Builder();
			NettyAsyncHttpProvider nettyAsyncHttpProvider = new NettyAsyncHttpProvider(config.build());
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient(nettyAsyncHttpProvider);
			Future future = asyncHttpClient.prepareGet("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=zUMbRYFkL52zbEkmoVb1MziyxXG-y21zBnZLqq1Z6KKwB-jOJlVy9Ggd_naoQq3LLMDIX_kN2RoVhMdY4b79s-sjMgKeL63Rvhis76-nHQE").execute();
			NettyResponse nettyResponse = (NettyResponse)future.get();
			System.out.println(nettyResponse.getResponseBody());		
			asyncHttpClient.closeAsynchronously();
		}catch(Exception e){
			e.printStackTrace();
		}  */
	}
  
	//
	public static void main(String[] args) throws UnsupportedEncodingException{
		//getAccessToken();
		//getUserList();
		//createMeun();
		//sendMessage();
		//deleteMeun();
		//http%3A%2F%2F2.changshawow.sinaapp.com%2Fdd.php
		//http%3A%2F%2F2.changshawow.sinaapp.com%2Fdd.php
		/*String str  = "Jack????";
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < str.length();i++){
			boolean bool = String.valueOf(str.charAt(i)).matches("[0-9a-zA-Z\u4e00-\u9fa5]*");
			if(bool){
				sb.append(str.charAt(i));
			}
		}
		
		System.out.println(sb.toString());*/
		
		try{
			String time = "2015-01-12 19:08:35";
			
			SimpleDateFormat fat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = fat.parse(time);
			System.out.println(date.getTime());
		}catch(Exception e){
			
		}
	}
}
