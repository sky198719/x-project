/**
 * Copyright (c) 2017, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.util;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.constant.Constant;
import com.xxdai.external.user.ws.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 描述
 *
 * @version $Id: RedisUtil.java 2017/3/30 20:43 $
 * @since jdk1.6
 */
@SuppressWarnings("unchecked")
@Component
public class RedisUtil {
    private static final Log log = LogFactory.getLog(RedisUtil.class);

    @Resource(name = "redisTemplateRetryTimes")
    private RedisTemplate<String, String> redisTemplate;
    //60 * 30
    public static final Long timeout =  1800L;

    //秘钥
    private static final String LOGIN_KEY = "login_key";
    //redis key
    private static final String LOGIN_REDIS_KEY = "_loginkey";

    public boolean setRedis(String key, JSONObject value) {
        try {
            redisTemplate.opsForValue().set("\"" + key + "\"", value.toJSONString(),timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("RedisUtil error setRedis key = " + key, e);
            return false;
        } finally {
            return true;
        }
    }

    public JSONObject getRedisKey(String key) {
        JSONObject obj = null;
        try {
            String str = redisTemplate.opsForValue().get("\"" + key + "\"");
            if (str != null && !"".equals(str)) {
                obj = JSONObject.parseObject(str);
            }
        } catch (Exception e) {
            log.error("RedisUtil error getRedisKey key = " + key, e);
        } finally {
            return obj;
        }
    }


    public boolean setRedisStr(String key,String value,long timeout) {
        try {
            redisTemplate.opsForValue().set("\"" + key + "\"", value,timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("RedisUtil error setRedis key = " + key, e);
            return false;
        } finally {
            return true;
        }
    }

    public String getRedisValueStr(String key) {
        String obj = null;
        try {
            String str = redisTemplate.opsForValue().get("\"" + key + "\"");
            if (str != null && !"".equals(str)) {
                obj = str;
            }
        } catch (Exception e) {
            log.error("RedisUtil error getRedisKey key = " + key, e);
        } finally {
            return obj;
        }
    }

    /**
     * 将用户信息存入redis
     *
     * @param request
     */
    public String putUsertoCache(HttpServletRequest request, User user, HttpServletResponse response) throws Exception {
        String userToken = null;
        try {
            userToken = createToken(request, user.getUserId());
            //存redis的key
            String userKey = user.getUserId() + LOGIN_REDIS_KEY;
            redisTemplate.opsForValue().set("\"" + userKey + "\"", JSONObject.toJSONString(user), 60 * 60 * 24, TimeUnit.SECONDS);

            // redisTemplate.expire(userToken, 900, TimeUnit.SECONDS);
            log.info("redis的key：" + userToken + ",user:" + user + ",json:" + JSONObject.toJSONString(user));
            //将token存进cookie中
            String domain = Configuration.getInstance().getValue("cookie.domain");
            String path = "/";
            CookieUtil.setCookie(response, Constant.USERTOKEN, userToken, path, 60 * 60 * 24, domain);
            CookieUtil.setCookie(response, "ID", user.getUserId().toString(), path, 60 * 60 * 24,domain);
        } catch (Exception e) {
             log.error("redisutil putUsertoCache error ",e);
        } finally {
            return userToken;
        }
    }


    public void setCookieToken(HttpServletRequest request,HttpServletResponse response, String userToken){
         try {
             //将token存进cookie中
             String domain = Configuration.getInstance().getValue("cookie.domain");
             String path = "/";
             CookieUtil.removeDomainCookie(request,response,Constant.USERTOKEN,"",path,domain);
             CookieUtil.setCookie(response, Constant.USERTOKEN, userToken, path, 60 * 60 * 24, domain);
         }catch (Exception e) {
             log.error("setCookieToken error",e);
         }
    }


    /**
     * 生成用户登录token
     *
     * @param request
     * @param userId
     * @return
     */
    private String createToken(HttpServletRequest request, Long userId) throws Exception {
        //request信息
        String requestStr = request.getHeader("Referer") + request.getRemoteAddr() + request.getServerName() + request.getRemoteUser() +
                request.getProtocol() + request.getCharacterEncoding();
        //失效时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date failureTime = DateUtils.addDays(new Date(), 1);
        String failureTimeStr = sdf.format(failureTime);
        //密钥
        String tokenStr = requestStr + "__" + failureTimeStr + "__" + userId.toString();
        String token = DESUtil.encode(LOGIN_KEY, tokenStr);
        return token;
    }

    /**
     * 退出操作删除redis中信息，且删除cookie
     * @param request
     * @param response
     */
    public void removeUserFromRedis( HttpServletRequest request, HttpServletResponse response,User user) {
        //删除redis中的用户信息
        if(user!=null){
            String key = user.getUserId().toString()+LOGIN_REDIS_KEY;
            if(!StringUtils.isBlank(key)){
                redisTemplate.delete("\""+key+"\"");
            }
            //删除用户登录的cookie
            String domain = Configuration.getInstance().getValue("cookie.domain");
            String path = "/";
            CookieUtil.removeDomainCookie(request,response,Constant.USERTOKEN,"",path,domain);
            CookieUtil.removeDomainCookie(request,response,"ID","",path, domain);
        }
    }
}
