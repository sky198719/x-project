/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.common;

import com.xxdai.client.CXF_Factory;
import com.xxdai.external.accounttrade.ws.AccountTradeCXFService;
import com.xxdai.external.appro.ws.ApproCXFService;
import com.xxdai.external.borrowApply.ws.BorrowApplyCXFService;
import com.xxdai.external.popularize.ws.PopularizeCFXService;
import com.xxdai.external.redis.ws.RedisMsgCXFService;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.external.partner.ws.PartnerPromotionCXFService;
import com.xxdai.external.userquery.ws.UserQueryCXFService;
import com.xxdai.person.ws.approquery.ApproQueryCXFService;
import com.xxdai.util.Configuration;

/**
 * 描述
 *
 * @version $Id: BaseService.java 2015/12/9 11:42 $
 * @since jdk1.6
 */
public class BaseService {

    protected UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();

    protected ApproCXFService approCXFService = (ApproCXFService) CXF_Factory.getFactory(ApproCXFService.class, Configuration.getInstance().getValue("webService_url") + "/approWebService").create();

    protected AccountTradeCXFService accountTradeCXFService = (AccountTradeCXFService) CXF_Factory.getFactory(AccountTradeCXFService.class, Configuration.getInstance().getValue("trade_url") + "/accountTradeWebService").create();

    protected PopularizeCFXService popularizeCFXService=(PopularizeCFXService) CXF_Factory.getFactory(PopularizeCFXService.class, Configuration.getInstance().getValue("webService_url") + "/popularizeCFXService").create();

    protected RedisMsgCXFService redisMsgCXFService=(RedisMsgCXFService) CXF_Factory.getFactory(RedisMsgCXFService.class, Configuration.getInstance().getValue("webService_url") + "/redisMsgWebService").create();

    protected PartnerPromotionCXFService partnerPromotionCXFService = (PartnerPromotionCXFService) CXF_Factory.getFactory(PartnerPromotionCXFService.class, Configuration.getInstance().getValue("webService_url") + "/partnerPromotionWebService").create();

    protected ApproQueryCXFService approQueryService = (ApproQueryCXFService) CXF_Factory.getFactory(ApproQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/approQueryWebService").create();

    protected BorrowApplyCXFService borrowApplyCXFService = (BorrowApplyCXFService) CXF_Factory.getFactory(BorrowApplyCXFService.class, Configuration.getInstance().getValue("webService_url") + "/borrowApplyWebService").create();

    protected UserQueryCXFService userQueryCXFService = (UserQueryCXFService) CXF_Factory.getFactory(UserQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userQueryWebService").create();

}
