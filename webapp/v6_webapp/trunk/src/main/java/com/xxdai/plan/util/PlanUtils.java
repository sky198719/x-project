/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.plan.util;

import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.plan.constants.PlanConstant;
import com.xxdai.plan.vo.Scheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 描述
 *
 * @version $Id: PlanUtils.java 12011 2014-12-06 02:40:26Z zhangyi $
 * @since jdk1.6
 */
public class PlanUtils {
    /**
     * 日志记录器
     */
    Logger log = LoggerFactory.getLogger(PlanUtils.class);

    /**
     * 推算优选理财计划所处时间
     *
     * @param scheme
     * @return
     */
    public int schemeStatus(Scheme scheme) {

        //当前时间
        Date currentDate = new Date();

        //预定开始时间
        Date presaleBegin = scheme.getPresaleBegin();
        //判断计划是否处于待发布期
        if(currentDate.compareTo(presaleBegin) == -1){
            log.info("优选理财计划【" + scheme.getPname() + "】，处于待发布期");
            return PlanConstant.PREFER_STATUS_RELEASE;
        }

        //预定截止时间
        Date presaleEnd = scheme.getPresaleEnd();
        if (currentDate.compareTo(presaleBegin) >= 0 && currentDate.compareTo(presaleEnd) == -1) {
            log.info("优选理财计划【" + scheme.getPname() + "】，处于预定期");
            return PlanConstant.PREFER_STATUS_RESERVE;
        }


        //支付截止日期
        Date presalePayEnd = scheme.getPresalePayEnd();
        if (currentDate.compareTo(presaleEnd) >= 0 && currentDate.compareTo(presalePayEnd) == -1) {
            log.info("优选理财计划【" + scheme.getPname() + "】，处于支付期");
            return PlanConstant.PREFER_STATUS_PAY;
        }

        //开放加入开始日期
        Date opensaleBegin = scheme.getOpensaleBegin();
        if(currentDate.compareTo(presaleEnd) == 1 && currentDate.compareTo(opensaleBegin) == -1) {
            log.info("优选理财计划【" + scheme.getPname() + "】，等待进去开放加入期");
             return PlanConstant.PREFER_STATUS_WAIT_OPENJOIN;
        }

        //开放加入截止日期
        Date opensaleEnd = scheme.getOpensaleEnd();
        if (currentDate.compareTo(opensaleBegin) >= 0 && currentDate.compareTo(opensaleEnd) == -1) {
            log.info("优选理财计划【" + scheme.getPname() + "】，处于开放加入期");
            return PlanConstant.PREFER_STATUS_OPENJOIN;
        }

        //锁定期限
        int closeterm = scheme.getCloseterm().intValue();
        //退出时间
        Date lockEndTime = DateUtil.addMonth(opensaleEnd, closeterm);
        if (currentDate.compareTo(opensaleEnd) >= 0 && currentDate.compareTo(lockEndTime) == -1) {
            log.info("优选理财计划【" + scheme.getPname() + "】，处于锁定期");
            return PlanConstant.PREFER_STATUS_LOCK;
        }

        //判断计划是否处于退出
        if(currentDate.compareTo(lockEndTime) == 1){
            log.info("优选理财计划【" + scheme.getPname() + "】，处于退出");
            return PlanConstant.PREFER_STATUS_QUIT;
        }
        return -1;
    }


    /**
    * _类型转换
    * @param scheme
    * @return
    */
   public static String schemeName(Scheme scheme,String split){
       if(scheme == null) {
           return "";
       }
       String pname = PlanConstant.SCHEME_TYPE_NAME_ONE  + split + scheme.getPname();
       if(scheme.getType() == PlanConstant.SCHEME_TYPE_TWO) {
           pname = PlanConstant.SCHEME_TYPE_NAME_TWO + split + scheme.getPname();
       } else if (scheme.getType() == PlanConstant.SCHEME_TYPE_THREE) {
           pname = PlanConstant.SCHEME_TYPE_NAME_THREE + split + scheme.getPname();
       }
       return pname;
   }

}
