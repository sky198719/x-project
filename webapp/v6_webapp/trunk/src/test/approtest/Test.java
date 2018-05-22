package com.xxdai.approtest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.person.ws.appro.ApproCXFService;
import com.xxdai.util.Configuration;

public class Test {
	 static ApproCXFService approCXFService = (ApproCXFService) CXF_Factory.getFactory(ApproCXFService.class, Configuration.getInstance().getValue("webService_url") + "/approWebService").create();
	public static void main(String[] args) {
	}

	/***
	 * VIP认证审核
	 */
	public static void auditVip(){
		JSONObject object=new JSONObject();
		object.put("userId", "8850");
		object.put("auditType", "1");
		object.put("sendIp", "127.0.0.1");
		object.put("auditType", "1");
		object.put("approUserId", "1");
		object.put("approUserName", "test111");
		JSONArray array=new JSONArray();
		array.add(object);
		String jsonstring=array.toString();
		String resultstring=approCXFService.auditVip(jsonstring);
		System.out.println(resultstring);
	}
	/***
	 * VIP认证申请
	 */
	public static void checkVip(){
		JSONObject object=new JSONObject();
		object.put("userId", "8850");
		object.put("serviceNum", "CD001");
		object.put("sendIp", "127.0.0.1");
		JSONArray array=new JSONArray();
		array.add(object);
		String jsonstring=array.toString();
		String resultstring=approCXFService.checkVip(jsonstring);
		System.out.println(resultstring);
	}
	/***
	 * 实名认证审核
	 */
	public static void auditRealName(){
		JSONObject object=new JSONObject();
		object.put("userId", "8850");
		object.put("auditType", "1");
		object.put("sendIp", "127.0.0.1");
		object.put("idCardNo", "330719196804253671");
		object.put("idCardType", "1");
		object.put("approuserId", "1");
		object.put("approUserName", "test111");
		JSONArray array=new JSONArray();
		array.add(object);
		String jsonstring=array.toString();
		String resultstring=approCXFService.auditRealName(jsonstring);
		System.out.println(resultstring);
	}
	/***
	 * 实名认证申请
	 */
	public static void checkRealName(){
		JSONObject object=new JSONObject();
		object.put("userId", "8850");
		object.put("realname", "周华健");
		object.put("idCardNo", "330719196804253671");
		object.put("sendIp", "127.0.0.1");
		object.put("idCardType", "1");
		object.put("picUp", "picUp");
		object.put("picDown", "picDown");
		JSONArray array=new JSONArray();
		array.add(object);
		String jsonstring=array.toString();
		String resultstring=approCXFService.checkRealName(jsonstring);
		System.out.println(resultstring);
	}
	/***
	 * 邮件认证申请
	 */
	public static void sendEmailMsg(){
		JSONObject object=new JSONObject();
		object.put("userId", "8850");
		object.put("email", "zhouhuarong@xinxindai.com");
		object.put("uuid", "330719196804253672");
		object.put("sendIp", "127.0.0.1");
		object.put("basePath", "/xx");
		JSONArray array=new JSONArray();
		array.add(object);
		String jsonstring=array.toString();
		String resultstring=approCXFService.sendEmailMsg(jsonstring);
		System.out.println(resultstring);
	}
	/***
	 * 邮件认证
	 */
	public static void checkEmailMsg(){
		JSONObject object=new JSONObject();
		object.put("uuid", "330719196804253672");
		object.put("sendIp", "127.0.0.1");
		JSONArray array=new JSONArray();
		array.add(object);
		String jsonstring=array.toString();
		String resultstring=approCXFService.checkEmailMsg(jsonstring);
		System.out.println(resultstring);
	}
	/***
	 * 短信认证
	 */
	public static void checkSmsMsg(){
		JSONObject object=new JSONObject();
		object.put("mobile", "18621546456");
		object.put("sendIp", "127.0.0.1");
		object.put("randCode", "3243");
		object.put("userId", "8850");
		JSONArray array=new JSONArray();
		array.add(object);
		String jsonstring=array.toString();
		String resultstring=approCXFService.checkSmsMsg(jsonstring);
		System.out.println(resultstring);
	}
	/***
	 * 注册时发送短信
	 */
	public static void sendMsgFromReg(){
		JSONObject object=new JSONObject();
		object.put("mobile", "18621546456");
		object.put("sendIp", "127.0.0.1");
		object.put("randCode", "8888");
		JSONArray array=new JSONArray();
		array.add(object);
		String jsonstring=array.toString();
		String resultstring=approCXFService.sendSmsMsgFromReg(jsonstring);
		System.out.println(resultstring);
	}
	/**
	 * 登录后 手机认证
	 */
	public static void sendMsg(){
		JSONObject object=new JSONObject();
		object.put("mobile", "18621546456");
		object.put("sendIp", "127.0.0.1");
		object.put("userId", "8850");
		JSONArray array=new JSONArray();
		array.add(object);
		String jsonstring=array.toString();
		String resultstring=approCXFService.sendSmsMsg(jsonstring);
		System.out.println(resultstring);
	}
	/**
	 * 手机号是否存在
	 */
	public static void checkMobileIsExisting(){
		JSONObject object=new JSONObject();
		object.put("mobile", "18621546456");
		JSONArray array=new JSONArray();
		array.add(object);
		String jsonstring=array.toString();
		String resultstring=approCXFService.checkMobileIsExisting(jsonstring);
		System.out.println(resultstring);
	}
}