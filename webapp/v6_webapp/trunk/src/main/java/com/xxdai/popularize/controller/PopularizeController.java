package com.xxdai.popularize.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.ClientUtil;
import com.xxdai.common.BaseController;
import com.xxdai.common.DataResponse;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.constant.PopularizeConstant;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.external.borrowQuery.ws.BorrowQueryCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.person.model.Appro;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.popularize.bo.AllianceUseragreement;
import com.xxdai.popularize.bo.PopularizeWsresponse;
import com.xxdai.popularize.bo.UserActivity;
import com.xxdai.util.Configuration;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.PageUtils;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/popularize")
public class PopularizeController extends BaseController {

    private static final Log log = LogFactory.getLog(PopularizeController.class);
    private final BorrowQueryCXFService borrowQueryCXFService = (BorrowQueryCXFService) ClientUtil.getWebService(BorrowQueryCXFService.class, ClientUtil.borrowQueryWebService);

    /**
     * 查看我的新新推广
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/goMyPopularizeActivity", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String goMyPopularizeActivity(HttpServletRequest request) {
        // 判断用户是否已同意推广协议，如已同意，直接跳到活动页面，否则到同意推广协议页面
        JSONObject useragreeJson = new JSONObject();
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            useragreeJson.put("isPopularizeAgree", "false");
            return useragreeJson.toString();
        }
        try {
            useragreeJson.put("userId", user.getUserId());
            //log.info("查询用户同意新新推广协议，请求参数：" + useragreeJson.toJSONString());
            String userActivityJson = popularizeCFXService.selectIsNotUseragreement(useragreeJson.toString());
            //log.info("查询用户同意新新推广协议，响应内容：" + userActivityJson);
            WSModelResponse rs = JsonUtil.jsonToBean(userActivityJson, WSModelResponse.class);
            if (rs.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                JSONObject json = (JSONObject) rs.getData();
                if (json.getIntValue("status") != PopularizeConstant.ACTIVITY_AGREEMENT_STATUS_AGREE) {
                    useragreeJson.put("isPopularizeAgree", "false");
                    return useragreeJson.toString();
                }
            } else {
                useragreeJson.put("isPopularizeAgree", "false");
                return useragreeJson.toString();
            }
        } catch (Exception e) {
            log.error("查看我的新新推广，异常：" + e.getMessage(), e);
        }
        useragreeJson.put("isPopularizeAgree", "true");
        return useragreeJson.toString();
    }

    /**
     * 同意新新推广协议页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/agreeAgreement", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String agreeAgreement(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        User userObj = (User) request.getSession().getAttribute("loginUser");
        if (userObj == null) {
            result.put("resultCode", -400);
            return result.toJSONString();
        }

        JSONObject param = new JSONObject();
        param.put("userId", userObj.getUserId());

        //log.info("查询用户的认证信息，请求参数：" + param.toJSONString());
        String strs = borrowQueryCXFService.queryAppro(param.toString());
        //log.info("查询用户的认证信息，响应内容：" + strs);
        PersonResponse res = JsonUtil.jsonToBean(strs, PersonResponse.class);
        if (res.getData() == null) {
            String message = "获取不到您的认证信息，请去完善个人资料的相关认证";
            log.info("用户【" + userObj.getUserId() + "】" + message);
            result.put("resultCode", -401);
            result.put("msg", message);
            return result.toJSONString();
        }
        String dataStr = String.valueOf(res.getData());
        Appro appro = JsonUtil.jsonToBean(dataStr, Appro.class);
        // 判断用户是否实名认证
        String mobilestatus = appro.getMobile();
        if (!mobilestatus.equals("1")) {
            String message = "您还没有完成手机认证！请先认证";
            log.info("用户【" + userObj.getUserId() + "】" + message);
            result.put("resultCode", -402);
            result.put("msg", message);
            return result.toJSONString();
        }
        String emailstatus = appro.getEmail();
        if (!emailstatus.equals("1")) {
            String message = "您还没有完成邮箱认证！请先认证";
            log.info("用户【" + userObj.getUserId() + "】" + message);
            result.put("resultCode", -403);
            result.put("msg", message);
            return result.toJSONString();
        }
        String namestatus = appro.getRealName();
        if (!namestatus.equals("1")) {
            String message = "您还没有完成实名认证！请先认证";
            log.info("用户【" + userObj.getUserId() + "】" + message);
            result.put("resultCode", -404);
            result.put("msg", message);
            return result.toJSONString();
        }

        AllianceUseragreement useragreement = new AllianceUseragreement();
        useragreement.setUserid(userObj.getUserId());
        useragreement.setAdddate(new Date());
        useragreement.setStatus(1L);
        useragreement.setAddip(HttpTookit.getRealIpAddr(request));
        String str = JsonUtil.beanToJson(useragreement);
        //log.info("同意新新推广协议，请求参数：" + str);
        String agreementJson = popularizeCFXService.saveUseragreement(str);
        //log.info("同意新新推广协议，响应内容：" + agreementJson);
        WSResponse ws = JsonUtil.jsonToBean(agreementJson, WSResponse.class);
        if (ws.getResultCode() == AppConsts.WS_RETURN_SUCC) {
            result.put("resultCode", ws.getResultCode());
            log.info("同意新新推广协议成功");
        } else {
            log.info("同意新新推广协议失败，原因：" + ws.getDesc() + "，结果码：" + ws.getResultCode());
            result.put("resultCode", -405);
            result.put("msg", ws.getDesc());
        }
        return result.toJSONString();
    }

    /**
     * 查询推广用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectPopularizeUser", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String selectPopularizeUser(HttpServletRequest request) {
        JSONObject userJson = new JSONObject();
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            userJson.put("resultCode", -404);
            return userJson.toJSONString();
        }
        String activityId = getActivityIdByCode(request.getParameter("activityCode"));

        try {
            int pageSize = 5;
            int pageNum = 1;
            String pageNumStr = request.getParameter("currentPage");
            if (StringUtil.isNotEmpty(pageNumStr)) {
                pageNum = Integer.parseInt(pageNumStr);
            }
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            userJson.put("userId", user.getUserId());
            userJson.put("activityId", activityId);
            userJson.put("pageSize", pageSize);
            userJson.put("pageNum", pageNum);
            userJson.put("startTime", startTime);
            userJson.put("endTime", endTime);
            //query invitation info
            String userListJson = popularizeCFXService.querySubordinateInfoByUserId(userJson.toString());
            DataResponse rs = JsonUtil.jsonToBean(userListJson, DataResponse.class);
            if (rs.getResultCode() == PopularizeConstant.XXTG_RESULT_SUCCESS) {
                String popStr = String.valueOf(rs.getData());
                PageUtils page = JsonUtil.jsonToBean(popStr, PageUtils.class);
                if (page.getTotalSize() != 0) {
                    userJson.put("resultCode", 1);
                    userJson.put("invitationData", page);
                    userJson.put("totalPages", page.getTotalPages());
                    userJson.put("totalSize", page.getTotalSize());
                } else {
                    userJson.put("resultCode", 0);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return userJson.toString();
    }

    /**
     * 新新推广人信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/invitationData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String invitationData(HttpServletRequest request) {
        JSONObject userJson = new JSONObject();
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                userJson.put("resultCode", -404);
                 return userJson.toJSONString();
            }
            User user = (User) userObj;
            String activityId = getActivityIdByCode(request.getParameter("activityCode"));
            //query invitation link
            JSONObject jsons = new JSONObject();
            jsons.put("userId", user.getUserId());
            jsons.put("activityId", activityId);
            String userActivityJson = popularizeCFXService.queryUserActivityNum(jsons.toString());
            WSModelResponse resp = JsonUtil.jsonToBean(userActivityJson, WSModelResponse.class);
            if (resp.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                JSONArray array = JSONObject.parseArray(resp.getData().toString());
                JSONObject json = (JSONObject) array.get(0);
                userJson.put("ISLINKPROHIBIT", json.getString("ISLINKPROHIBIT"));
                if ("0".equals(json.getString("ISLINKPROHIBIT"))) {
                    userJson.put("frontUrl", Configuration.getInstance().getValue("front_url"));
                    userJson.put("popularizeCode", json.getString("UUID"));
                    userJson.put("userName",user.getUserName());
                    userJson.put("webappUrl", Configuration.getInstance().getValue("webapp_url"));
                    userJson.put("resultCode", 0);
                } else {
                    userJson.put("msg", "您当前活动的推广链接已被关闭。如想继续开通，请联系客服。");
                    userJson.put("resultCode", -100);
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return userJson.toString();
    }

    /**
     * 查询用户是否参与某活动
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/isJoinActivity", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String isJoinActivity(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("loginUser");
        JSONObject jsons = new JSONObject();
        try {
            jsons.put("userId", user.getUserId());
            String activityId = getActivityIdByCode(request.getParameter("activityCode"));
            jsons.put("activityId", activityId);
            log.info("查询用户参与活动信息，请求参数：" + jsons.toJSONString());
            String userActivityJson = popularizeCFXService.queryUserActivityNum(jsons.toString());
            log.info("查询用户参与活动信息，响应内容：" + userActivityJson);
            WSModelResponse resp = JsonUtil.jsonToBean(userActivityJson, WSModelResponse.class);
            if (resp.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                JSONArray array = JSONObject.parseArray(resp.getData().toString());
                if (array != null && array.size() > 0) {
                    jsons.put("isJoinActivity", "true");
                } else {
                    jsons.put("isJoinActivity", "false");
                }
            }
        } catch (Exception e) {
            log.error("查询参与活动，异常：" + e.getMessage(), e);
        }
        return jsons.toJSONString();
    }

    /**
     * 用户参与活动
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/userJoinActivity", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String userJoinActivity(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            resultJson.put("resultCode", -404);
            return resultJson.toJSONString();
        }
        try {
            String actId = getActivityIdByCode(request.getParameter("activityCode"));
            UserActivity activity = new UserActivity();
            activity.setActivityId(Long.parseLong(actId));
            activity.setJoinTime(new Date());
            activity.setUserId(user.getUserId());
            //  String uuId = String.valueOf(IdWorker.getInstance().nextId());
            String uuId = getRandomChar(5);
            activity.setUuid(uuId);
            String str = JsonUtil.beanToJson(activity);
            log.info("用户参与新新推广活动，请求参数：" + str);
            String activityJson = popularizeCFXService.bindUserAndActivity(str);
            log.info("用户参与新新推广活动，响应内容：" + activityJson);
            WSResponse ws = JsonUtil.jsonToBean(activityJson, WSResponse.class);
            if (ws.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                log.info("参加活动成功");
                resultJson.put("resultCode", ws.getResultCode());
            } else {
                resultJson.put("resultCode", ws.getResultCode());
                resultJson.put("message", ws.getDesc());
                log.info("参与活动失败，原因：" + ws.getDesc());
            }
        } catch (Exception e) {
            log.error("用户参与新新推广活动，异常：" + e.getMessage(), e);
            resultJson.put("resultCode", -500);
            resultJson.put("message", "参与活动失败，请重新尝试或者联系客服");
        }
        return resultJson.toJSONString();
    }

    /**
     * 获得0-9,a-z,A-Z范围的随机数
     *
     * @param length 随机数长度
     * @return String
     */
    private String getRandomChar(int length) {
        JSONObject jsonObject = new JSONObject();
        String code = "";
        char[] chr = {'2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
                'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        do {
            for (int i = 0; i < length; i++) {
                buffer.append(chr[random.nextInt(55)]);
            }
            code = buffer.toString();
            jsonObject.clear();
            jsonObject.put("uuID", code);
            String json = popularizeCFXService.queryUUIDList(jsonObject
                    .toJSONString());
            PopularizeWsresponse wsReponse = JsonUtil.jsonToBean(json,
                    PopularizeWsresponse.class);
            List<Map<String, Object>> list = (List<Map<String, Object>>) wsReponse.getData();
            if (list == null || list.size() == 0) {
                break;
            }
            buffer.setLength(0); //清空buffer
        } while (true);
        return code;
    }

    /**
     * 根据活动代码查询活动id
     *
     * @param activityCode
     * @return
     */
    private String getActivityIdByCode(String activityCode) {
        JSONObject param = new JSONObject();
        param.put("activityCode", activityCode);
        String activityResult = popularizeCFXService.selectActivityByCode(param.toString());
        WSModelResponse resp = JsonUtil.jsonToBean(activityResult, WSModelResponse.class);
        if (resp.getResultCode() == AppConsts.WS_RETURN_SUCC) {
            JSONObject json = (JSONObject) resp.getData();
            return json.getString("id");
        }
        return "";
    }


    /**
     * 推广码绑定用户
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/binding", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String binding(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("loginUser");
        JSONObject resultJson = new JSONObject();
        Long userId = user.getUserId();
        try {
            String popularizeCode = request.getParameter("popularizeCode");

            // 2.判断是否是邀请码
            // 绑定用户 查询是否是推广编号
            resultJson.put("userId", userId);
            resultJson.put("uuId", popularizeCode);
            //该推广码是否已被禁止
            log.info("查询推广连接是否被禁止推广，请求参数：" + resultJson.toJSONString());
            String strProhibit = popularizeCFXService.selectUserActivityLinkProhibit(resultJson.toJSONString());
            log.info("查询推广连接是否被禁止推广，响应内容：" + strProhibit);
            WSModelResponse respProhibit = JsonUtil.jsonToBean(strProhibit, WSModelResponse.class);
            if (respProhibit.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                Boolean isProbit = JsonUtil.jsonToBean(respProhibit.getData().toString(), Boolean.class);
                if (isProbit) {
                    resultJson.put("msg", "您使用的推广连接，已失效！");
                    return resultJson.toString();
                }
            }

            String isresult = popularizeCFXService.selectUserActivityByuuid(resultJson.toJSONString());
            PopularizeWsresponse pss = JsonUtil.jsonToBean(isresult, PopularizeWsresponse.class);
            int activityId = 0;
            JSONObject res = new JSONObject();
            if (pss.getResultCode() == 1) {
                JSONObject obj = JSONObject.parseObject(pss.getData().toString());
                activityId = obj.getInteger("activityId");
            }
            res.put("activityId", activityId);
            res.put("userId", Integer.parseInt(userId.toString()));
            String Lag = popularizeCFXService.selectActivityUserProhibitIsStop(res.toJSONString());
            PopularizeWsresponse pps = JsonUtil.jsonToBean(Lag, PopularizeWsresponse.class);
            if (pps.getResultCode() == 0) {
                if (Integer.parseInt(pps.getData().toString()) > 0) {
                    //说明用户已被禁止推广该活动
                    resultJson.put("msg", "抱歉，您已被被禁止推广该活动了!请联系客服");
                    return resultJson.toString();
                }
            }
            String useractivity = popularizeCFXService.selectUserActivityByuuid(resultJson.toJSONString());
            PopularizeWsresponse ps = JsonUtil.jsonToBean(useractivity, PopularizeWsresponse.class);
            if (ps.getResultCode() == Constant.XXTG_RESULT_SUCCESS) {
                // 是邀请码,1.判定是否已绑定过用户 2.是否是自己的邀请码 3.是否是自己的推广用户  4.执行绑定用户操作 传入useId
                //1.验证是否用户已绑定过上级
                String isBind = popularizeCFXService.selectCountUserSuperiorMappingByUserId(resultJson.toJSONString());
                PopularizeWsresponse psbind = JsonUtil.jsonToBean(isBind, PopularizeWsresponse.class);
                if (psbind.getResultCode() == Constant.XXTG_RESULT_SUCCESS) {
                    if (Integer.parseInt(psbind.getData().toString()) > 0) {
                        // 已绑定过用户了，不能被多人推广!
                        resultJson.put("msg", "亲!你已绑定过用户了，不能被多人推广哦!");
                        return resultJson.toJSONString();
                    }
                }
                //2.是否是自己的邀请码
                String bindSelf = popularizeCFXService.selectUserActivityByUUIDAndUserId(resultJson.toJSONString());
                PopularizeWsresponse psself = JsonUtil.jsonToBean(bindSelf, PopularizeWsresponse.class);
                if (psself.getResultCode() != Constant.XXTG_RESULT_SUCCESS) {
                    //是自己的邀请码！自己不能邀请自己
                    resultJson.put("msg", "亲，自己不能邀请自己哦!");
                    return resultJson.toJSONString();
                }
                //3.检测绑定用户是否比上级参与活动晚
                resultJson = new JSONObject();
                resultJson.put("userId", userId);
                resultJson.put("uuId", popularizeCode);
                String str = popularizeCFXService.selectUserWithAddTimeAndSupJoinTime(resultJson.toJSONString());
                PopularizeWsresponse pw = JsonUtil.jsonToBean(str, PopularizeWsresponse.class);
                if (pw.getResultCode() == Constant.XXTG_RESULT_SUCCESS) {
                    int cnt = Integer.parseInt(pw.getData().toString());
                    if (cnt <= 0) {
                        //
                        resultJson.put("msg", "亲，注册时间不能比上级用户参与活动的时间晚");
                        return resultJson.toJSONString();
                    }
                }

                //推广注册，执行活动动作
                JSONObject param = new JSONObject();
                param.put("uuId", popularizeCode);
                param.put("userId", user.getUserId());
                param.put("activityCode", PopularizeConstant.ACTIVITY_CONSORTIUM_EXTENSION);
                param.put("ip", HttpUtil.getRealIpAddr(request));
                log.info("推广注册，请求参数：" + param.toJSONString());
                String popularizeStr = popularizeCFXService.popularizeRegistration(param.toJSONString());
                log.info("推广注册，响应内容：" + popularizeStr);
                WSModelResponse resp = JsonUtil.jsonToBean(popularizeStr, WSModelResponse.class);
                if (resp.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                    resultJson.put("msg", "2");
                }

                return resultJson.toJSONString();
            } else {
                resultJson.put("msg", "输入有误，请核对后请重新输入");
                return resultJson.toJSONString();
            }
        } catch (Exception e) {
            log.error("领取礼劵或绑定用户失败，异常：" + e.getMessage(), e);
        }
        return resultJson.toJSONString();
    }
}

