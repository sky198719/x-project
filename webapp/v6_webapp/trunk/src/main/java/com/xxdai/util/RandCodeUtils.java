package com.xxdai.util;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.exception.AppFrameException;
import com.xxdai.randCode.RandCode;
import com.xxdai.spring.SpringFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandCodeUtils {

    private static final Log log = LogFactory.getLog(RandCodeUtils.class);

    private static RedisTemplate redisTemplate;

    private static RedisTemplate getRedisTemplate() throws AppFrameException {
        if(redisTemplate != null) {
            return redisTemplate;
        }
        synchronized (RedisTemplate.class){
            if(redisTemplate == null) {
                redisTemplate = (RedisTemplate) SpringFactory.getBean("redisTemplateRetryTimes");
                log.info("Creating new redisTemplateRetryTimes...");
            }
            return redisTemplate;
        }
    }

	/**
	 * 获取图片验证码
	 * @return
	 */
	public static JSONObject getVerifyCode(){
		RandCode code = RandCode.getVerifyCodeInstatnce();
		String codeStr = code.getRandCode();
		JSONObject obj = JSONObject.parseObject(codeStr);
		return obj;
	}


	/****
	 * 校验图片验证码
	 * @param inputCode
	 * @param sessionCode
	 * @return
	 */
	public static boolean checkVerifyCode(String inputCode, String sessionCode){
		RandCode code = RandCode.getVerifyCodeInstatnce();
		boolean flag = code.checkCode(inputCode, sessionCode);
		return flag;
	}


	/**
	 * 获取手机验证码
	 * @return
	 */
	public static String getPhoneCode(){
		RandCode code = RandCode.getPhoneCodeInstatnce();
		String randCode = code.getRandCode();
		JSONObject obj = JSONObject.parseObject(randCode);
		if(null!=obj){
			String phoneCode = obj.getString("randCode");
			return phoneCode;
		}else{
			return "";
		}

	}


	/***
	 * 校验手机验证码
	 * @param inputCode
	 * @param sessionCode
	 * @return
	 */
	public static boolean checkPhoneCode(String inputCode, String sessionCode){
		RandCode code = RandCode.getPhoneCodeInstatnce();
		boolean flag = code.checkCode(inputCode, sessionCode);
		return flag;
	}

    /**
     * 获取手机验证码错误次数
     * @param busiCode
     * @return
     */
    public static Long getRetryTimes(String busiCode) {
        try {
            ValueOperations<String, Object> operations = getRedisTemplate().opsForValue();

            if(getRedisTemplate().hasKey(busiCode)) {
                return Long.parseLong(operations.get(busiCode).toString());
            } else {
                return 0L;
            }
        } catch (AppFrameException e) {
            log.error("RandCodeUtils.getRetryTimes error ...", e);
        }
        return 0L;
    }

    /**
     * 记录手机验证码错误次数
     * @param busiCode
     * @param redisTimeout
     */
    public static void setRetryTimes(String busiCode, Long redisTimeout, TimeUnit unit) {
        try {
            ValueOperations<String, Object> operations = getRedisTemplate().opsForValue();
            operations.increment(busiCode, 1);
            getRedisTemplate().expire(busiCode, redisTimeout, unit);
        } catch (AppFrameException e) {
            log.error("RandCodeUtils.setRetryTimes error ...", e);
        }
    }

	//生成指定长度的随机密码（大小写字母+数字） @author aiden at 2016-10-28 12:03:31
	public static String getRandomPwd(int len) {
		Random rand = new Random();
		int cnt = 26 * 2 + 10;
		char[] s = new char[len];
		for (int i = 0; i < len; i++) {
			int v = rand.nextInt(cnt);
			if (v < 10)
				s[i] = (char)('0' + v);
			else if (v < 36)
				s[i] = (char)(v - 10 +  'A');
			else
				s[i] = (char)(v - 36 +  'a');
		}
		return new String(s);
	}

}
