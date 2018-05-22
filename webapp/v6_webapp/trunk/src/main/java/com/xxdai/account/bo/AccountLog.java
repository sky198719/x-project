/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.account.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金日志
 *
 * @version $Id: AccountLog.java 9688 2014-11-11 09:48:31Z wangyuxiang $
 * @since jdk1.6
 */
public class AccountLog implements Serializable {

    private Long id; //	ID	N	NUMBER(16)	N			主键自增
    private Long userId; //	USERID	N	INTEGER	N			用户编号
    private String pcode; //	PCODE	N	CHAR(4)	N			账户编号
    private BigDecimal usable; //	USABLE	N	NUMBER(16,2)	N	0		可用金额
    private BigDecimal frozen; //	FROZEN	N	NUMBER(16,2)	N	0		冻结金额
    private BigDecimal collection; //	COLLECTION	N	NUMBER(16,2)	N	0		待收金额
    private BigDecimal repayment; //	REPAYMENT	N	NUMBER(16,2)	Y	0		待还金额
    private String addTime; //	ADDTIME	N	DATE	N			操作时间
    private Date busiTime;//    BUSITIME	N	DATE	N			业务时间
    private String operatorType;//  OPERATORTYPE	N	VARCHAR2(50)	N			操作类型
    private Integer moneyType;//     MONEYTYPE	N	INTEGER	N			资金类型（转出，转入，内部转出，内部转入，冻结，解冻）
    private BigDecimal workMoney; //    WORKMONEY	N	NUMBER(16,2)	N			变动金额
    private String busiId; //BUSIID	N	VARCHAR2(40)	Y			业务单号
    private BigDecimal accountTotal; //账户总资产
    private String remark; //备注
    private String addIp; //添加ip
    private String username;
  

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
        return id;
    }

    public BigDecimal getAccountTotal() {
        return accountTotal;
    }

    public void setAccountTotal(BigDecimal accountTotal) {
        this.accountTotal = accountTotal;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public BigDecimal getUsable() {
        return usable;
    }

    public void setUsable(BigDecimal usable) {
        this.usable = usable;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public BigDecimal getCollection() {
        return collection;
    }

    public void setCollection(BigDecimal collection) {
        this.collection = collection;
    }

    public BigDecimal getRepayment() {
        return repayment;
    }

    public void setRepayment(BigDecimal repayment) {
        this.repayment = repayment;
    }


    public Date getBusiTime() {
        return busiTime;
    }

    public void setBusiTime(Date busiTime) {
        this.busiTime = busiTime;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    public BigDecimal getWorkMoney() {
        return workMoney;
    }

    public void setWorkMoney(BigDecimal workMoney) {
        this.workMoney = workMoney;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
