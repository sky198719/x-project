package com.xxdai.common;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.appro.bo.EmailAppro;
import com.xxdai.appro.bo.MobileAppro;
import com.xxdai.appro.bo.RealNameAppro;
import com.xxdai.appro.bo.VIPAppro;
import com.xxdai.client.CXF_Factory;
import com.xxdai.client.ClientUtil;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.account.ws.AccountQueryCXFService;
import com.xxdai.external.accountcash.ws.AccountCashCXFService;
import com.xxdai.external.accountov.ws.AccountOVQueryCXFService;
import com.xxdai.external.accountrecharge.ws.AccountRechargeCXFService;
import com.xxdai.external.accounttrade.ws.AccountTradeCXFService;
import com.xxdai.external.accquery.ws.AccQueryCXFService;
import com.xxdai.external.activity.ws.ActivityCXFService;
import com.xxdai.external.appro.ws.ApproCXFService;
import com.xxdai.external.borrow.ws.BorrowCountCXFWebService;
import com.xxdai.external.borrowQuery.ws.BorrowQueryCXFService;
import com.xxdai.external.market.ws.MarketCXFService;
import com.xxdai.external.partner.ws.PartnerCXFServcie;
import com.xxdai.external.person.ws.PersonCXFService;
import com.xxdai.external.popularize.ws.PopularizeCFXService;
import com.xxdai.external.redis.ws.RedisMsgCXFService;
import com.xxdai.external.sms.ws.SMSCXFService;
import com.xxdai.external.tender.TenderCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.external.userfundtrade.ws.UserFundTradeCXFService;
import com.xxdai.external.userquery.ws.UserQueryCXFService;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.person.ws.accountCashprohibit.AccountCashprohibitCXFService;
import com.xxdai.person.ws.approquery.ApproQueryCXFService;
import com.xxdai.system.bo.DicCommonVo;
import com.xxdai.system.ws.AreaQueryCXFService;
import com.xxdai.system.ws.DicCXFService;
import com.xxdai.system.ws.SysConfigCXFService;
import com.xxdai.util.Configuration;
import org.apache.log4j.Logger;

import java.util.List;

public class BaseController {

    private static final Logger log = Logger.getLogger(BaseController.class);

    protected UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();

    protected ApproCXFService approCXFService = (ApproCXFService) CXF_Factory.getFactory(ApproCXFService.class, Configuration.getInstance().getValue("webService_url") + "/approWebService").create();

    protected AccountTradeCXFService accountTradeCXFService = (AccountTradeCXFService) CXF_Factory.getFactory(AccountTradeCXFService.class, Configuration.getInstance().getValue("trade_url") + "/accountTradeWebService").create();

    protected UserQueryCXFService userQueryCXFService = (UserQueryCXFService) CXF_Factory.getFactory(UserQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userQueryWebService").create();

    protected PartnerCXFServcie partnerCXFServcie = (PartnerCXFServcie) CXF_Factory.getFactory(PartnerCXFServcie.class, Configuration.getInstance().getValue("webService_url") + "/partnerWebService").create();

    protected AccountRechargeCXFService accountRechargeCXFService = (AccountRechargeCXFService) CXF_Factory.getFactory(AccountRechargeCXFService.class, Configuration.getInstance().getValue("webService_url") + "/accountRechargeWebService").create();

    protected AccountOVQueryCXFService accountOVQueryCXFService = (AccountOVQueryCXFService) CXF_Factory.getFactory(AccountOVQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/accountOVQueryWebService").create();

    protected PersonCXFService personalCXFService = (PersonCXFService) CXF_Factory.getFactory(PersonCXFService.class, Configuration.getInstance().getValue("webService_url") + "/personWebService").create();

    protected AccountCashCXFService accountCashCXFService = (AccountCashCXFService) CXF_Factory.getFactory(AccountCashCXFService.class, Configuration.getInstance().getValue("webService_url") + "/accountCashWebService").create();

    protected AccountOVQueryCXFService accountOVService = (AccountOVQueryCXFService) CXF_Factory.getFactory(AccountOVQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/accountOVQueryWebService").create();

    protected PersonCXFService personalService = (PersonCXFService) CXF_Factory.getFactory(PersonCXFService.class, Configuration.getInstance().getValue("webService_url") + "/personWebService").create();

    protected ApproQueryCXFService approQueryService = (ApproQueryCXFService) CXF_Factory.getFactory(ApproQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/approQueryWebService").create();

    protected BorrowQueryCXFService borrowQueryService = (BorrowQueryCXFService) CXF_Factory.getFactory(BorrowQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/borrowQueryWebService").create();

    protected DicCXFService dicCXFService = (DicCXFService) CXF_Factory.getFactory(DicCXFService.class, Configuration.getInstance().getValue("webService_url") + "/dicWebService").create();

    protected SMSCXFService smsCXFService= (SMSCXFService) CXF_Factory.getFactory(SMSCXFService.class,Configuration.getInstance().getValue("webService_url")+"/smsWebService").create();

    protected BorrowCountCXFWebService borrowCountCXFWebService=(BorrowCountCXFWebService) CXF_Factory.getFactory(BorrowCountCXFWebService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/borrowCountWebService").create();

    protected TenderCXFService tenderService = (TenderCXFService) CXF_Factory.getFactory(TenderCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/tenderWebService").create();

    protected AccountQueryCXFService accountQueryCXFService = (AccountQueryCXFService) CXF_Factory.getFactory(AccountQueryCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("trade_url") + "/accountQueryWebService").create();

    protected MarketCXFService marketCXFService = (MarketCXFService) CXF_Factory.getFactory(MarketCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/marketWebService").create();

    protected PopularizeCFXService popularizeCFXService=(PopularizeCFXService) CXF_Factory.getFactory(PopularizeCFXService.class, Configuration.getInstance().getValue("webService_url") + "/popularizeCFXService").create();

    protected RedisMsgCXFService redisMsgCXFService=(RedisMsgCXFService) CXF_Factory.getFactory(RedisMsgCXFService.class, Configuration.getInstance().getValue("webService_url") + "/redisMsgWebService").create();

    protected SysConfigCXFService sysConfigCXFService= (SysConfigCXFService) CXF_Factory.getFactory(SysConfigCXFService.class,Configuration.getInstance().getValue("webService_url")+"/sysConfigWebService").create();

    protected AreaQueryCXFService areaQueryCXFService = (AreaQueryCXFService) CXF_Factory.getFactory(AreaQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/areaQueryWebService").create();

    protected AccQueryCXFService accQueryCXFService = (AccQueryCXFService) CXF_Factory.getFactory(AccQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/accQueryWebService").create();

    protected UserFundTradeCXFService userFundTradeCXFService = (UserFundTradeCXFService) CXF_Factory.getFactory (UserFundTradeCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userFundTradeWebService").create();

    protected BorrowQueryCXFService borrowQueryCXFService = (BorrowQueryCXFService) ClientUtil.getWebService(BorrowQueryCXFService.class, ClientUtil.borrowQueryWebService);

    protected AccountCashprohibitCXFService accountCashprohibitCXFService = (AccountCashprohibitCXFService) CXF_Factory.getFactory(AccountCashprohibitCXFService.class, Configuration.getInstance().getValue("webService_url") + "/accountCashprohibitWebService").create();
    protected ActivityCXFService activityCXFService = (ActivityCXFService) ClientUtil.getWebService(ActivityCXFService.class, "activityWebService");
    /**
     * 根据用户ID获取用户信息
     */
    protected User queryUserById(Long userId) {
        JSONObject object = new JSONObject();
        object.put("userId", userId);
        String resultStr = userCXFService.getUserById(object.toJSONString());
        DataResponse dataResponse = JsonUtil.jsonToBean(resultStr, DataResponse.class);
        User user = null;
        if (dataResponse.getData() != null) {
            user = JsonUtil.jsonToBean(dataResponse.getData().toString(), User.class);
        }
        return user;
    }

    /**
     * 配置文件
     *
     * @return
     */
    public List getDicCommonTypeList(String code) {
        JSONObject obj = new JSONObject();
        obj.put("code", code);
        String str = dicCXFService.queryCommonValue(obj.toString());
        PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
        List<DicCommonVo> configuations = null;
        if (res.getData() != null) {
            String dataStr = String.valueOf(res.getData());
            configuations = JsonUtil.jsonToList(dataStr, DicCommonVo.class);
        }
        return configuations;
    }

    /**
     * 根据用户ID获取认证信息
     *
     * @param userId
     * @param type
     * @return
     */
    public Object queryInfoByUserId(Long userId, Integer type) {
        // String userIdStr = JsonUtil.beanToJson(userId);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("userId", userId);
        String str;
        PersonResponse res;
        String dataStr;
        switch (type) {
            // RealNameAppro
            case 1:
                str = accountOVService.queryRealNameByUserId(jsonObj.toString());
                res = JsonUtil.jsonToBean(str, PersonResponse.class);
                if(res.getData()!=null){
                    dataStr = String.valueOf(res.getData());
                    return JsonUtil.jsonToBean(dataStr, RealNameAppro.class);
                }else{
                    return null;
                }
                // VIPAppro
            case 2:
                str = accountOVService.queryVIPByUserId(jsonObj.toString());
                res = JsonUtil.jsonToBean(str, PersonResponse.class);
                if(res.getData()!=null){
                    dataStr = String.valueOf(res.getData());
                    return JsonUtil.jsonToBean(dataStr, VIPAppro.class);
                }else{
                    return null;
                }
                // MobileAppro
            case 3:
                str = accountOVService.queryMobileByUserId(jsonObj.toString());
                res = JsonUtil.jsonToBean(str, PersonResponse.class);
                if(res.getData()!=null){
                    dataStr = String.valueOf(res.getData());
                    return JsonUtil.jsonToBean(dataStr, MobileAppro.class);
                }else{
                    return null;
                }
                // EmailAppro
            case 4:
                str = accountOVService.queryEmailByUserId(jsonObj.toString());
                res = JsonUtil.jsonToBean(str, PersonResponse.class);
                if(res.getData()!=null){
                    dataStr = String.valueOf(res.getData());
                    return JsonUtil.jsonToBean(dataStr, EmailAppro.class);
                }else{
                    return null;
                }
            default:
                return null;
        }
    }

}
