/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved.
 */
package com.xxdai.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.external.inforDiscloure.ws.InforDiscloureCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.fund.model.SemResponse;
import com.xxdai.http.Message;
import com.xxdai.news.bo.NewsPpt;
import com.xxdai.news.model.NewsWsResponse;
import com.xxdai.news.webservice.interfaces.NewsCXFService;
import com.xxdai.user.service.UserService;
import com.xxdai.util.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 首页
 *
 * @version $Id: IndexController.java 2015/1/27 10:46 $
 * @since jdk1.6
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {
    private NewsCXFService newsCXFService = (NewsCXFService) CXF_Factory.getFactory(NewsCXFService.class, Configuration.getInstance().getValue("webService_url") + "/newsWebService").create();
    //信息披露
    private InforDiscloureCXFService inforCXFService = (InforDiscloureCXFService) CXF_Factory.getFactory(InforDiscloureCXFService.class,
            Configuration.getInstance().getValue("webService_url") + "/inforDiscloureWebService").create();


    private static final Logger logger = Logger.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/iregistrations/{uuid}")
    public String iregistrations(@PathVariable("uuid") String uuid, HttpServletRequest request) {
        request.setAttribute("uuid", uuid);
        return "user/iregistrationsRedirect";
    }

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
        try {

            String token = HttpTookit.getCookieValue(request, Constant.USERTOKEN);
            //System.out.println("token======"+token);
            if(token != null && !"".equals(token)) {
                Message msg = userService.beInLogging(token,HttpTookit.getUserAgent(request));
                if(msg != null) {
                    int code = msg.getCode();
                    if(code != 200000) {
                        String domain = Configuration.getInstance().getValue("cookie.domain");
                        CookieUtil.removeDomainCookie(request,response,Constant.USERTOKEN,"","/",domain);
                        request.getSession().removeAttribute("loginUser");
                    } else {
                        Object obj = request.getSession().getAttribute("loginUser");
                        if(obj == null) {
                            User user = userService.getUserInfo(token,HttpTookit.getUserAgent(request));
                            if(user != null) {
                                request.getSession().setAttribute("loginUser",user);
                            }
                        }
                    }
                }
                //System.out.println(JSONObject.toJSONString(msg));
            }

            Object users = session.getAttribute("loginUser");
            if (users != null) {
                request.setAttribute("gauserId", ((User) users).getUserId());
            }
            request.setAttribute("sv", Constant.STATIC_VERSION);
        } catch (Exception e) {
            logger.error("get cache STATIC_VERSION fail");
            request.setAttribute("sv", DateUtil.format(new Date(), "dd"));
        }
        return "index";
    }

    @RequestMapping(value = "/staticVersion", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String staticVersion(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            result.put("sv", Constant.STATIC_VERSION);
        } catch (Exception e) {
            logger.error("get cache STATIC_VERSION fail");
            result.put("sv", DateUtil.format(new Date(), "dd"));
        }
        return result.toJSONString();
    }

    /**
     * 信贷员工专属APP推广连接
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/empapp/{id}")
    public String empApp(@PathVariable("id") String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "popularize/employeeStepTwo";
    }

    @RequestMapping(value = "/empapp")
    public String emppop(HttpServletRequest request) {
        return "popularize/employeeStepOne";
    }

    /**
     * WebApp banner
     */
    @RequestMapping(value = "/indexppt", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String indexppt(HttpServletRequest request) {
        //banner top
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageSize", 10);
        jsonObject.put("pageIndex", 1);
        jsonObject.put("position", 1);
        String bannerType = request.getParameter("bannerType");
        bannerType = bannerType == null ? "6" : bannerType;
        jsonObject.put("bannerType", bannerType);
        jsonArray.add(jsonObject);
        String jsonstr = newsCXFService.queryNewsPptListByFront(jsonArray.toString());
        NewsWsResponse newsWsResponse = JsonUtil.jsonToBean(jsonstr, NewsWsResponse.class);
        if (newsWsResponse.getResultCode() < 0)
            logger.error(newsWsResponse.getDesc());
        else {
            List<NewsPpt> list = JsonUtil.jsonToList(newsWsResponse.getData().toString(), NewsPpt.class);
            jsonObject.clear();
            jsonObject.put("pptToplist", list);
        }
        jsonObject.put("imageUrl", Configuration.getInstance().getValue("image_back_url"));
        return jsonObject.toString();
    }


    @RequestMapping(value = "/setToken", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String setToken(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String name = request.getParameter("name");
            String id = request.getParameter("id");
            //设置令牌
            String tokenName = TokenUtil.getTokenName(name, id);
            String fundToken = TokenUtil.generateToken(request, tokenName);
            result.put("tokenName", tokenName);
            result.put("token", fundToken);
            result.put("resultCode", 0);
            logger.info("setToken = " + result.toJSONString());
        } catch (Exception e) {
            result.put("resultCode", 1);
            logger.error(e.getMessage(), e);
        }
        return result.toJSONString();
    }


    @RequestMapping(value = "/getCache", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getCache(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String key = request.getParameter("key");
            String value = CacheUtil.getCacheValue(key);
            result.put("value", value);
            //logger.info(String.format("key=%s,value=%s", key, value));
            result.put("resultCode", 0);
        } catch (Exception e) {
            result.put("resultCode", 1);
            logger.error(e.getMessage(), e);
        }
        return result.toJSONString();
    }

    @RequestMapping(value = "/currentDate", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String currentDate(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            result.put("resultCode", 0);
            result.put("currentDate", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            result.put("activity_url",Configuration.getInstance().getValue("activity_url"));
        } catch (Exception e) {
            result.put("resultCode", 1);
            logger.error(e.getMessage(), e);
        }
        return TokenUtil.jsonpCallback(request, result.toJSONString());
    }

    @RequestMapping(value = "/dowApp/{channel}")
    public String dowApp(@PathVariable("channel") String channel, HttpServletRequest request) {
        request.setAttribute("channel", channel);
        return "user/dowApp";
    }


    @RequestMapping(value = "/getInforMap", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getInforMap() {
        JSONObject result = new JSONObject();
        try {
            result.put("resultCode", 0);
            result.put("inforMap", inforMap());
        } catch (Exception e) {
            result.put("resultCode", 1);
            logger.error(e.getMessage(), e);
        }
        return result.toJSONString();
    }

    /**
     * 获取信息披露数据
     *
     * @return
     */
    public JSONObject inforMap() {
        JSONObject obj = null;
        String jsonStr = inforCXFService.queryInforList();
        SemResponse semResponse = JsonUtil.jsonToBean(jsonStr, SemResponse.class);
        if (null != semResponse && semResponse.getResultCode() == AppConsts.WS_RETURN_SUCC) {
            obj = JSONObject.parseObject(semResponse.getData().toString());
        }
        return obj;
    }

    @RequestMapping(value = "/shortUrl", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String shortUrl(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String url = request.getParameter("url");
            HttpPost httpost = new HttpPost("http://dwz.cn/create.php");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("url", url));
            httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpResponse response = new DefaultHttpClient().execute(httpost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
            logger.info(jsonStr);
            result.put("resultCode", 0);
            result.put("data", JSONObject.parseObject(jsonStr));
        } catch (Exception e) {
            logger.error(e);
            result.put("resultCode", -1);
        } finally {
            return TokenUtil.jsonpCallback(request, result.toJSONString());
        }
    }

    @RequestMapping(value = "/isOpenOnAccount", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String isOpenOnAccount(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            //用户信息
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                result.put("msg", "会话失效，请重新登录");
                result.put("resultCode", -400);
                return result.toJSONString();
            }

            User user = (User) userObj;


            String token = HttpTookit.getCookieValue(request, Constant.USERTOKEN);

            Message message = userService.isOpenOnAccount(token, HttpTookit.getUserAgent(request), user.getUserId());
            //logger.info("isOpenOnAccount req url = " + JSONObject.toJSONString(message));

            // 处理接口异常情况,转换json报错的情况
            if (message != null && message.getCode() == 200000) {
                JSONObject data = (JSONObject) message.getData();
                if (data.getIntValue("code") == 0) {
                    JSONObject dataOjb = data.getJSONObject("data");

                    int isopenaccount = dataOjb.getIntValue("isopenaccount");
                    result.put("resultCode", isopenaccount);

                }
                result.put("activity_url", Configuration.getInstance().getValue("activity_url"));
            } else {
                result.put("resultCode",1);
            }
        } catch (Exception e) {
            result.put("resultCode", 400);
            logger.error("isOpenOnAccount error", e);
        }
        return result.toJSONString();
    }

    @RequestMapping(value = "/newIndex", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String newIndex(HttpServletRequest request) {
        return "";
    }

    @RequestMapping(value = "/properties", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String properties() {
        JSONObject result = new JSONObject();
        result.put("activity_url", Configuration.getInstance().getValue("activity_url"));
        return result.toJSONString();
    }
}

