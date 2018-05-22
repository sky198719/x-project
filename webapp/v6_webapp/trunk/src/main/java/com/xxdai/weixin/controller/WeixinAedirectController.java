/**
 * Copyright (c) 2017, www.xinxindai.com All Rights Reserved.
 */
package com.xxdai.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.weixin.ws.WeixinCXFService;
import com.xxdai.http.Message;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.person.model.UserCreditStuffShowInfo;
import com.xxdai.user.service.UserService;
import com.xxdai.util.*;
import com.xxdai.weixin.api.WxServiceImpl;
import com.xxdai.weixin.bean.WeixinAccount;
import com.xxdai.weixin.bean.WeixinUserBind;
import com.xxdai.weixin.bean.result.WxUser;
import com.xxdai.weixin.constants.WeixinConstants;
import com.xxdai.weixin.service.WeixinService;
import com.xxdai.weixin.util.http.SimpleGetRequestExecutor;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

/**
 * 描述
 *
 * @version $Id: WeixinAedirectController.java 2017/2/23 15:59 $
 * @since jdk1.6
 */
@Controller
@RequestMapping("/weixin/view")
public class WeixinAedirectController {
    /**
     * 日志记录器
     */
    private Log log = LogFactory.getLog(WeixinAedirectController.class);

    @Autowired
    private WeixinService weixinService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/reg")
    public String reg(HttpServletRequest request) {
        StringBuffer url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize");
        url.append("?appid=").append(CacheUtil.getCacheValue(WeixinConstants.WEIXIN_FRONT_URL));
        url.append("&redirect_uri=").append('1');
        url.append("&response_type=code&scope=SCOPE&state=STATE#wechat_redirect");
        return url.toString();
    }

    @RequestMapping(value = "/getKey", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getKey(HttpServletRequest request) {
        String key = request.getParameter("key");
        String value = com.xxdai.weixin.util.CacheUtil.getCacheValue(key);
        return value;
    }

    @RequestMapping(value = "/isFreeLogin")
    public
    @ResponseBody
    String isFreeLogin(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            Object openid = request.getSession().getAttribute(WeixinConstants.WEIXIN_USER_SESSION_OPENID);
            if (openid == null) {
                result.put("resultCode", 1);
                result.put("msg", "openid 不存在");
            } else {
                result.put("resultCode", 0);
                result.put("msg", "openid存在");
            }
        } catch (Exception e) {
            result.put("resultCode", 404);
            result.put("msg", "异常");
        } finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/setWeixinUser", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String setWeixinUser(HttpServletRequest request,HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            //获取session中的user信息
            Object userObj = request.getSession().getAttribute(Constant.LOGINUSER);
            if(userObj != null) {
                //log.info("已登录，无需设置登录信息");
                result.put("resultCode", 400);
                result.put("msg", "已登录，无需设置登录信息");
                return result.toJSONString();
            }
            // 微信用户OPENID
            Object openid = request.getSession().getAttribute(WeixinConstants.WEIXIN_USER_SESSION_OPENID);
            if (openid == null) {
                log.info("自动绑定用户，获取不到openid");
                result.put("resultCode", 404);
                result.put("msg", "获取不到openid");
                return result.toJSONString();
            }

            WSModelResponse getwxUserBindResp = weixinService.getWeixinUserBind(openid.toString());
            if (getwxUserBindResp.getResultCode() == 0 && getwxUserBindResp.getData() != null) {
                WeixinUserBind wxUserBind = JsonUtil.jsonToBean(getwxUserBindResp.getData().toString(), WeixinUserBind.class);
                PersonResponse userResp = weixinService.getUser(wxUserBind.getUserId());
                if (userResp.getData() != null) {
                    User user = JsonUtil.jsonToBean(userResp.getData().toString(), User.class);
                    this.setToken(user,openid.toString(),request,response);

                    log.info("已绑定新新贷账户【" + user.getUserName() + "】");
                }
                result.put("resultCode", 0);
                result.put("msg", "成功");
            } else {
                result.put("resultCode", 405);
                result.put("msg", "还未绑定");
            }
        } catch (Exception e) {
            log.error(e);
            result.put("resultCode", 400);
            result.put("msg", "异常");
        } finally {
            return result.toJSONString();
        }
    }

    public void setToken(User user, String openId, HttpServletRequest request, HttpServletResponse response) {
        try {
            Message msg = userService.loginWeixin(openId, HttpTookit.getUserAgent(request));
            System.out.println(msg);
            if(msg != null && msg.getCode() == Constant.SUCCESS) {
                Object obj = msg.getData();
                if(obj != null) {
                    JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(obj));
                    String token = data.getString("data");
                    if(token != null && !"".equals(token)) {
                        request.getSession().setAttribute(Constant.LOGINUSER, user);
                        request.getSession().setAttribute(Constant.IS_WEIXIN_AUTOLOGIN,"true");

                        RedisUtil redisUtil = (RedisUtil) SpringApplicationUtil.getBean("redisUtil");
                        redisUtil.setCookieToken(request,response,token);
                    }
                }
            }
        }catch (Exception e) {
            log.error("setToken error",e);
        }
    }

    @RequestMapping(value = "/autoUserBind", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String autoUserBind(HttpServletRequest request,HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            // 微信用户OPENID
            Object openid = request.getSession().getAttribute(WeixinConstants.WEIXIN_USER_SESSION_OPENID);
            if (openid == null) {
                log.info("自动绑定用户，获取不到openid");
                result.put("resultCode", 404);
                result.put("msg", "获取不到openid");
                return result.toJSONString();
            }

            WSModelResponse getwxUserBindResp = weixinService.getWeixinUserBind(openid.toString());
            if (getwxUserBindResp.getResultCode() == 0 && getwxUserBindResp.getData() != null) {
                WeixinUserBind wxUserBind = JsonUtil.jsonToBean(getwxUserBindResp.getData().toString(), WeixinUserBind.class);
                PersonResponse userResp = weixinService.getUser(wxUserBind.getUserId());
                if (userResp.getData() != null) {
                    User user = JsonUtil.jsonToBean(userResp.getData().toString(), User.class);
                    this.setToken(user,openid.toString(),request,response);

                    log.info("已绑定新新贷账户【" + user.getUserName() + "】");
                }
                result.put("resultCode", 405);
                result.put("msg", "已绑定其他微信号");
                return result.toJSONString();
            }

            //获取session中的user信息
            Object userObj = request.getSession().getAttribute(Constant.LOGINUSER);
            if (userObj == null) {
                log.info("当前没有登录，返回");
                result.put("resultCode", 406);
                result.put("msg", "当前没有登录");
                return result.toJSONString();
            }
            User user = (User) userObj;

            //获取微信用户信息
            WSModelResponse wxUserResp = weixinService.getWeixinUser(openid.toString());
            if (wxUserResp.getData() == null) {
                log.info("还未关注微信公众号");

                boolean bool = weixinService.saveWeixinUser(openid.toString());
                if (!bool) {
                    result.put("resultCode", 405);
                    result.put("msg", "还未关注微信公众号");
                    return result.toJSONString();
                }
            }

            WSModelResponse getwxUserBindRespStr = weixinService.getWeixinUserBindByUserId(user.getUserId());
            if (getwxUserBindRespStr.getResultCode() == 0 && getwxUserBindRespStr.getData() != null) {
                log.info("您的新新贷账户【" + user.getUserName() + "】，已绑定其他微信号，如有疑问，请联系客服");
                result.put("resultCode", 407);
                result.put("msg", "已绑定其他微信号");
                return result.toJSONString();
            }

            WSModelResponse wxUserBindResp = weixinService.weixinUserBind(openid.toString(), user.getUserId());
            if (wxUserBindResp != null && wxUserBindResp.getResultCode() == 0) {
                log.info("微信用户和网站用户，自动绑定成功");
                result.put("resultCode", 0);
                result.put("msg", "绑定成功");
            } else {
                result.put("resultCode", 1);
                result.put("msg", "绑定失败");
            }
        } catch (Exception e) {
            log.error(e);
            result.put("resultCode", 400);
            result.put("msg", "异常");
        } finally {
            return result.toJSONString();
        }
    }

    /**
     * 自动操作，微信用户和网站用户的绑定
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/freeLogin")
    public String freeLogin(HttpServletRequest request,HttpServletResponse response) {
        String referer = "/";
        try {
            Object isShowLogin = request.getSession().getAttribute("isShowLogin");
            Object refererObj = request.getSession().getAttribute("referer");
            referer = refererObj == null ? "/" : refererObj.toString();
            if(isShowLogin != null) {
                referer = referer + (referer.indexOf("?") == -1 ?  "?": "&") + "isShowLogin="+isShowLogin.toString();
            }
            request.getSession().removeAttribute("referer");
            // 微信用户OPENID
            Object openid = request.getSession().getAttribute(WeixinConstants.WEIXIN_USER_SESSION_OPENID);
            if (openid == null) {
                log.info("自动绑定用户，获取不到openid");
                return "redirect:" + referer;
            }

            //获取微信用户信息
            WSModelResponse wxUserResp = weixinService.getWeixinUser(openid.toString());
            if (wxUserResp.getData() != null) {
                //检查当前用户是否已绑定微信用户
                WSModelResponse getwxUserBindResp = weixinService.getWeixinUserBind(openid.toString());
                if (getwxUserBindResp.getResultCode() == 0 && getwxUserBindResp.getData() != null) {
                    WeixinUserBind wxUserBind = JsonUtil.jsonToBean(getwxUserBindResp.getData().toString(), WeixinUserBind.class);
                    PersonResponse userResp = weixinService.getUser(wxUserBind.getUserId());
                    if (userResp.getData() != null) {
                        User user = JsonUtil.jsonToBean(userResp.getData().toString(), User.class);
                        this.setToken(user,openid.toString(),request,response);

                        log.info("已绑定新新贷账户【" + user.getUserName() + "】");
                    }
                    return "redirect:" + referer;
                }
            }
        } catch (Exception e) {
            log.error("微信用户和网站用户，自动绑定失败", e);
        } finally {
            return "redirect:" + referer;
        }
    }
}
