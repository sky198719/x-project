package com.xxdai.newHand.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.common.WebException;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.account.ws.AccountQueryCXFService;
import com.xxdai.external.borrowTrade.ws.BorrowTradeCXFService;
import com.xxdai.external.prod.ws.define.CommProdDefineCXFService;
import com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.newHand.constants.CommProdConstant;
import com.xxdai.newHand.model.CommProdDeploy;
import com.xxdai.plan.constants.AccountConsts;
import com.xxdai.util.Configuration;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.PageUtils;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;
import com.xxdai.ws.util.WSPageResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaihuangqi on 2015/12/25.
 */
@Controller
@RequestMapping(value = "/sevenDays")
public class SevenDaysController {
    /**
     * 日志记录器
     */
    private static final Logger log = Logger.getLogger(SevenDaysController.class);
    /**
     * 账户查询接口
     */
    private AccountQueryCXFService accountQueryCXFService = (AccountQueryCXFService) CXF_Factory.getFactory(AccountQueryCXFService.class, Configuration.getInstance().getValue("trade_url") + "/accountQueryWebService").create();
    /**
     * 用户操作接口
     */
    private UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();

    /**
     * 通用理财产品deploy操作接口
     */
    private final CommProdDeployCXFService commProdDeployCXFService = (CommProdDeployCXFService) CXF_Factory.getFactory(CommProdDeployCXFService.class, Configuration.getInstance().getValue("webService_url") + "/commProdDeployWebservice").create();
    private final CommProdDefineCXFService commProdDefineService = (CommProdDefineCXFService) CXF_Factory.getFactory(CommProdDefineCXFService.class, Configuration.getInstance().getValue("webService_url") + "/commProdDefineWebservice").create();

    /**
     * 投标
     */
    private BorrowTradeCXFService borrowTradeCXFService = (BorrowTradeCXFService) CXF_Factory.getFactory(
            BorrowTradeCXFService.class, Configuration.getInstance().getValue("trade_url") + "/borrowTradeWebService").create();

    /**
     * 查询七天大胜信息
     */
    @RequestMapping(value = "/selectSevenDaysInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String selectSevenDaysInfo(HttpServletRequest request) {
        log.info("enter selectSevenDaysInfo in SevenDaysController .");
        JSONObject result = new JSONObject();
        //用户信息
        User user = (User) request.getSession().getAttribute("loginUser");
        try {
            result.put("sevenDays", selectSevenDays(user));
            result.put("currentDate", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            result.put("msg", "获取七天大胜信息成功");
            result.put("resultCode", 0);
        } catch (Exception e) {
            log.error("获取七天大胜信息失败", e);
            result.put("resultCode", 200);
            result.put("msg", "获取七天大胜信息失败");
        }
        return result.toJSONString();
    }

    /**
     * 初始化七天大胜购买界面
     */
    @RequestMapping(value = "/init7DaysTradeData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String init7DaysTradeData(HttpServletRequest request) {
        log.info("enter init7DaysTradeData in SevenDaysController .");
        JSONObject result = new JSONObject();
        //用户信息
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            result.put("msg", "会话失效，请重新登录");
            result.put("resultCode", -400);
            return result.toJSONString();
        }
        try {
            //查询默认账户信息
            JSONObject accountJson = new JSONObject();
            accountJson.put("useId", user.getUserId());
            accountJson.put("accountType", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
            String resultAccountStr = accountQueryCXFService.selectAccountByIdAndType(accountJson.toJSONString());
            WSModelResponse accountResponse = JsonUtil.jsonToBean(resultAccountStr, WSModelResponse.class);
            if (accountResponse.getData() != null) {
                JSONObject defaultAccount = JSONObject.parseObject(accountResponse.getData().toString());
                result.put("defaultAccount", defaultAccount);
            }
            result.put("sevenDays", selectSevenDays(user));
        } catch (Exception e) {
            log.error("获取七天大胜信息失败", e);
            result.put("resultCode", 200);
            result.put("msg", "获取七天大胜信息失败");
        }
        return result.toJSONString();
    }

    /**
     * 查询server当前时间，推算合同结算时间及结算日
     */
    @RequestMapping(value = "/getServerDate", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getServerDate(HttpServletRequest request) {
        log.info("enter getServerDate in SevenDaysController .");
        JSONObject result = new JSONObject();
        try {
            Date currentDate = new Date();
            Date endDate = DateUtil.addDate(currentDate, 29);
            Date valueDate = DateUtil.addDate(endDate, 1);
            result.put("resultCode", 0);
            result.put("currentDate", DateUtil.format(currentDate, "yyyy年MM月dd日"));
            result.put("endDate", DateUtil.format(endDate, "yyyy年MM月dd日"));
            result.put("valueDate", DateUtil.format(valueDate, "yyyy年MM月dd日"));
        } catch (Exception e) {
            log.error("获取服务器当前时间失败", e);
            result.put("resultCode", 200);
            result.put("msg", "获取服务器当前时间失败");
        }
        return result.toJSONString();
    }

    /**
     * 七天大胜购买
     */
    @RequestMapping(value = "/sevenDaysTrade", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String sevenDaysTrade(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            //判断当前token是否有效
            if (!TokenUtil.validToken(request)) {
                result.put("result", Constant.TOKEN_INVALID_ERROR);
                result.put("desc", "页面已过期，请重新尝试");
                return result.toString();
            }
            // 销毁token
            TokenUtil.removeToken(request);

            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user == null) {
                log.info("七天大胜购买，未登录，");
                result.put("desc", "您未登录，不能进行下一步操作");
                result.put("result", 200);
                return result.toString();
            }

            //验证支付密码
            String payPwd = request.getParameter("payPwd");
            JSONObject pswJson = new JSONObject();
            pswJson.put("userId", user.getUserId());
            pswJson.put("password", EscapeCode.Encryption(payPwd));
            pswJson.put("ip", HttpTookit.getRealIpAddr(request));
            String browser = request.getHeader("User-Agent");
            browser = browser.length() > 200 ? browser.substring(0, 200) : browser;
            pswJson.put("browser", browser);
            log.info("用户购买七天大胜验证支付密码，请求参数：" + pswJson.toJSONString());
            String resultPsw = userCXFService.checkPayPassword(pswJson.toString());
            log.info("用户购买七天大胜验证支付密码，响应内容：" + resultPsw);
            WSModelResponse pswResp = JsonUtil.jsonToBean(resultPsw, WSModelResponse.class);
            if (pswResp.getResultCode() == -2) {
                result.put("desc", "支付密码错误，请重新输入");
                result.put("result", pswResp.getResultCode());
                return result.toString();
            } else if (pswResp.getResultCode() == 220) {
                result.put("desc", "支付密码为空，请去设置您的支付密码");
                result.put("result", pswResp.getResultCode());
                return result.toString();
            } else if (pswResp.getResultCode() == 230) {
                result.put("desc", "支付密码与登录密码一致，请重设您的支付密码");
                result.put("result", pswResp.getResultCode());
                return result.toString();
            } else if (pswResp.getResultCode() == -1) {
                result.put("desc", "验证支付密码异常，请重新尝试或联系客服");
                result.put("result", pswResp.getResultCode());
                return result.toString();
            }
            String tradeAccount = request.getParameter("tradeAccount");
            String deployId = request.getParameter("deployId");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", user.getUserId());
            param.put("deployId", deployId);
            param.put("tenderMoney", tradeAccount);
            param.put("ip", HttpTookit.getRealIpAddr(request));
            param.put("terminalVer", HttpTookit.getRequestTerminal(request));
            String params = JsonUtil.beanToJson(param);
            log.info("用户购买七天大胜，请求参数：" + params);
            String resultStr = borrowTradeCXFService.tenderCommProdBorrow(params);
            result = JSON.parseObject(resultStr);
            log.info("用户购买七天大胜，响应内容：" + resultStr);
            WSModelResponse resp = JsonUtil.jsonToBean(resultStr, WSModelResponse.class);
            log.info(resp.toJson());
            result.put("desc", resp.getDesc());
            result.put("result", resp.getResultCode());
            result.put("tenderTime", new Date().getTime());
        } catch (Exception e) {
            result.put("desc", "操作异常，请重新尝试或者联系客服");
            result.put("result", -100);
            log.error("用户购买七天大胜失败，" + e.getMessage(), e);
        }
        return result.toJSONString();
    }

    /**
     * 获取七天大胜产品信息
     *
     * @return
     * @throws com.xxdai.common.WebException
     */
    private CommProdDeploy selectSevenDays(User user) throws WebException {
        try {
            JSONObject json = new JSONObject();
            json.put("busiType", CommProdConstant.SEVEN_DAYS_BUSI);
            if (user != null) {
                json.put("curUserId", user.getUserId());
            }
            // 查询理财产品基本信息
            CommProdDeploy sevenDays = null;
            log.info("获取七天大胜产品信息，请求参数：" + json);
            String resultStr = commProdDeployCXFService
                    .queryCommProdDeployFrontInfo(json.toJSONString());
            log.info("获取七天大胜产品信息，请求结果：" + resultStr);
            if (StringUtil.isNotBlank(resultStr)) {
                WSModelResponse response = JsonUtil.jsonToBean(resultStr,
                        WSModelResponse.class);
                if (response.getResultCode() == 0) {
                    sevenDays = JsonUtil.jsonToBean(
                            String.valueOf(response.getData()),
                            CommProdDeploy.class);
                }
            }
            return sevenDays;
        } catch (Exception e) {
            log.error("获取七天大胜产品异常", e);
            throw new WebException(-101, "获取七天大胜产品异常");
        }
    }

    /**
     * 前台 查询投标记录
     * 在投标详情页面
     * 分页查询
     *
     * @return
     */
    @RequestMapping(value = "/tenderInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryTenderInfo(HttpServletRequest request) {
        String deployId = request.getParameter("deployId");
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        if (StringUtil.isBlank(currentPage)) {
            currentPage = "1";
        }
        String resultJson = "";
        Map<String, Object> paraMap = new HashMap<String, Object>(8);
        paraMap.put("deployId", deployId);
        paraMap.put("pageSize", pageSize);
        paraMap.put("currentPage", currentPage);
        String idParam = JsonUtil.beanToJson(paraMap);
       // log.info("查询七天大胜投标记录，请求参数：" + paraMap);
        String tenderStr = commProdDeployCXFService.queryCommProdBorrowTender(idParam);
        //log.info("查询七天大胜投标记录，请求结果：" + tenderStr);
        if (StringUtil.isNotBlank(tenderStr)) {
            WSPageResponse modelResponse = JsonUtil.jsonToBean(tenderStr, WSPageResponse.class);
            if (modelResponse.getResultCode() == 0) {
                if (modelResponse.getData() != null) {
                    PageUtils page = JsonUtil.jsonToBean(String.valueOf(modelResponse.getData()), PageUtils.class);
                    resultJson = JsonUtil.beanToJson(page);
                    request.setAttribute("pageNums", page.getTotalPages());  //总页数
                    request.setAttribute("currentPage", currentPage);//当前页数
                }
            }
        }
        return resultJson;
    }


    /**
     * 前台 查询投标记录
     * 在投标详情页面
     * 分页查询
     *
     * @return
     */
    @RequestMapping(value = "/newTenderInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryNewTenderInfo(HttpServletRequest request) {
        String prodSpell = request.getParameter("prodSpell");
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        if (StringUtil.isBlank(currentPage)) {
            currentPage = "1";
        }
        String resultJson = "";
        Map<String, Object> paraMap = new HashMap<String, Object>(8);
        paraMap.put("prodSpell", prodSpell);
        paraMap.put("pageSize", pageSize);
        paraMap.put("currentPage", currentPage);
        String idParam = JsonUtil.beanToJson(paraMap);
       // log.info("查询七天大胜投标记录，请求参数：" + paraMap);
        String tenderStr = commProdDefineService.queryCommProdBorrowTender(idParam);
       // log.info("查询七天大胜投标记录，请求结果：" + tenderStr);
        if (StringUtil.isNotBlank(tenderStr)) {
            WSPageResponse modelResponse = JsonUtil.jsonToBean(tenderStr, WSPageResponse.class);
            if (modelResponse.getResultCode() == 0) {
                if (modelResponse.getData() != null) {
                    PageUtils page = JsonUtil.jsonToBean(String.valueOf(modelResponse.getData()), PageUtils.class);
                    resultJson = JsonUtil.beanToJson(page);
                    request.setAttribute("pageNums", page.getTotalPages());  //总页数
                    request.setAttribute("currentPage", currentPage);//当前页数
                }
            }
        }
        return resultJson;
    }

    /**
     * 我的理财产品投标信息查询
     *
     * @param request
     * @return
     */

    @RequestMapping(value = "/myTenderQuery", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryMyCommProdTenderInfo(HttpServletRequest request) {

        JSONObject resultJson = new JSONObject();
        //验证是否登录状态
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            log.info("七天大胜购买记录，未登录，");
            resultJson.put("desc", "您未登录，不能进行下一步操作");
            resultJson.put("result", 200);
            return resultJson.toString();
        }
        JSONObject jsonParam = new JSONObject();
        String status = request.getParameter("status");
        jsonParam.put("busiType", CommProdConstant.SEVEN_DAYS_BUSI);
        jsonParam.put("userId", user.getUserId());
        jsonParam.put("status", status);
      //  log.info("查询七天大胜投标记录，请求参数：" + jsonParam);
        String wsResult = commProdDeployCXFService.queryMyCommProdTender(jsonParam.toJSONString());
      //  log.info("查询七天大胜投标记录，请求结果：" + wsResult);
        if (StringUtil.isNotBlank(wsResult)) {
            WSPageResponse wsResponse = JsonUtil.jsonToBean(wsResult, WSPageResponse.class);
            if (wsResponse.getResultCode() == 0) {
                String datastr = String.valueOf(wsResponse.getData());
                PageUtils pageUtils = JsonUtil.jsonToBean(datastr, PageUtils.class);
                resultJson.put("tenderQuery", pageUtils);
            }
        }
        return resultJson.toString();
    }


    @RequestMapping(value = "/jisuan", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String jisuan(HttpServletRequest request) {

        JSONObject resultJson = new JSONObject();

        String apr = request.getParameter("apr");


        BigDecimal result100 = this.thrityDays(new BigDecimal(100),new BigDecimal(apr),new BigDecimal(30));

        BigDecimal result1000 = this.thrityDays(new BigDecimal(1000),new BigDecimal(apr),new BigDecimal(30));

        BigDecimal result5000 = this.thrityDays(new BigDecimal(5000),new BigDecimal(apr),new BigDecimal(30));

        BigDecimal result10000 = this.thrityDays(new BigDecimal(10000),new BigDecimal(apr),new BigDecimal(30));

        String floatApr = request.getParameter("floatApr");
        if(floatApr != null && !"".equals(floatApr) && Float.parseFloat(floatApr) > 0) {
            //System.out.println(result100);
            result100 = result100.add(this.thrityDays(new BigDecimal(100),new BigDecimal(floatApr),new BigDecimal(30)));
            //System.out.println(result100);
            result1000 = result1000.add(this.thrityDays(new BigDecimal(1000),new BigDecimal(floatApr),new BigDecimal(30)));
            result5000 = result5000.add(this.thrityDays(new BigDecimal(5000),new BigDecimal(floatApr),new BigDecimal(30)));
            result10000 = result10000.add(this.thrityDays(new BigDecimal(10000),new BigDecimal(floatApr),new BigDecimal(30)));

        }

        resultJson.put("result100",result100);
        resultJson.put("result1000",result1000);
        resultJson.put("result5000",result5000);
        resultJson.put("result10000",result10000);
        return resultJson.toString();
    }

    public BigDecimal thrityDays(BigDecimal money,BigDecimal apr,BigDecimal num) {
         try {
             BigDecimal temapApr = apr.divide(new BigDecimal(100));
             BigDecimal result = money.multiply(temapApr);
             result = result.divide(new BigDecimal(360),50, BigDecimal.ROUND_HALF_DOWN);
             result = result.multiply(num).setScale(2, BigDecimal.ROUND_DOWN);
             return result;
         }catch (Exception e) {
             e.printStackTrace();
         }
         return new BigDecimal(0);
    }

}
