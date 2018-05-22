/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.borrow.bo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述
 *
 * @version $Id: BorrowRepayment.java 9464 2014-11-07 12:23:02Z xiaoying $
 * @since jdk1.6
 */
public class BorrowRepayment implements Serializable {

    private String repaymentId; // 	REPAYMENTID	N	NUMBER(6)	N			待还编号
    private String borrowId; // 	BORROWID	N	INTEGER	N			借款标ID
    private Long userId; // 	USEDID	N	INTEGER	N			用户编号
    private Integer porder; // 	PORDER	N	INTEGER	N	1		还款的期数，从1开始计数，相同borrowId下不可重复（新新宝是当期购买人的顺序号）
    private BigDecimal repaymentAccount; // 	REPAYMENTACCOUNT	N	NUMBER(16,2)	N			本期还款总额
    private BigDecimal repaymentInterest; // 	REPAYMENTINTEREST	N	NUMBER(16,2)	N			本期还款利息
    private BigDecimal repaymentCapital; // 	REPAYMENTCAPITAL	N	NUMBER(16,2)	N			本期还款本金
    private Date repaymentTime; // 	REPAYMENTTIME	N	DATE	Y			应还款时间，记录生成同时生成
    private Integer status; // 	STATUS	N	INTEGER	N	0		还款状态，默认值0:0.未还款;1.已还款2.提前还
    private Date repaymentYesTime; // 	REPAYMENTYESTIME	N	DATE	Y			实际还款时间，若还款状态为0，则为空
    private BigDecimal repaymentYesAccount; // 	REPAYMENTYESACCOUNT	N	NUMBER(16,2)	Y	0		实际还款金额，若还款状态为0，则为0
    private BigDecimal repaymentYesInterest; // 	REPAYMENTYESINTEREST	N	NUMBER(16,2)	Y	0		实际已支付利息，若还款状态为0，则为0
    private BigDecimal repaymentYesCapital; // 	REPAYMENTYESCAPITAL	N	NUMBER(16,2)	Y	0		时间已归还本金，若还款状态为0，则为0
    private Integer webStatus; // 	WEBSTATUS	N	INTEGER	Y	0		系统垫付状态，默认值0:0.未垫付;1.已垫付
    private Date webTime; // 	WEBTIME	N	DATE	Y			垫付时间，若垫付状态为0，则为空
    private Integer laterDays; // 	LATERDAYS	N	INTEGER	Y	0		本条还款记录逾期天数，默认未逾期，值为0
    private BigDecimal laterInterest; // 	LATERINTEREST	N	NUMBER(16,2)	Y	0		本条还款记录逾期罚息，默认未逾期，值为0
    private BigDecimal penalSum; // 	PENALSUM	N	NUMBER(16,2)	Y	0		本条还款记录提前还款违约金，默认无，值为0
    private Integer isPayment; // 	ISPAYMENT	N	INTEGER	Y	0		是否缴纳罚息OR违约金，默认值0:0.未缴纳;1.已缴纳;2.无需缴纳
    private Date addTime; // 	ADDTIME	N	DATE	N			时间，默认系统时间
    private String addIp; // 	ADDIP	N	VARCHAR2(50)	N			IP地址，默认系统自动获取

   
 

  
    public String getRepaymentId() {
		return repaymentId;
	}

	public void setRepaymentId(String repaymentId) {
		this.repaymentId = repaymentId;
	}

	  
    public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

  
    public Integer getPorder() {
        return porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

  
    public BigDecimal getRepaymentAccount() {
        return repaymentAccount;
    }

    public void setRepaymentAccount(BigDecimal repaymentAccount) {
        this.repaymentAccount = repaymentAccount;
    }

  
    public BigDecimal getRepaymentInterest() {
        return repaymentInterest;
    }

    public void setRepaymentInterest(BigDecimal repaymentInterest) {
        this.repaymentInterest = repaymentInterest;
    }

    
    public BigDecimal getRepaymentCapital() {
        return repaymentCapital;
    }

    public void setRepaymentCapital(BigDecimal repaymentCapital) {
        this.repaymentCapital = repaymentCapital;
    }

   
    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

   
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

   
    public Date getRepaymentYesTime() {
        return repaymentYesTime;
    }

    public void setRepaymentYesTime(Date repaymentYesTime) {
        this.repaymentYesTime = repaymentYesTime;
    }

    
    public BigDecimal getRepaymentYesAccount() {
        return repaymentYesAccount;
    }

    public void setRepaymentYesAccount(BigDecimal repaymentYesAccount) {
        this.repaymentYesAccount = repaymentYesAccount;
    }

    
    public BigDecimal getRepaymentYesInterest() {
        return repaymentYesInterest;
    }

    public void setRepaymentYesInterest(BigDecimal repaymentYesInterest) {
        this.repaymentYesInterest = repaymentYesInterest;
    }

  
    public BigDecimal getRepaymentYesCapital() {
        return repaymentYesCapital;
    }

    public void setRepaymentYesCapital(BigDecimal repaymentYesCapital) {
        this.repaymentYesCapital = repaymentYesCapital;
    }

  
    public Integer getWebStatus() {
        return webStatus;
    }

    public void setWebStatus(Integer webStatus) {
        this.webStatus = webStatus;
    }

  
    public Date getWebTime() {
        return webTime;
    }

    public void setWebTime(Date webTime) {
        this.webTime = webTime;
    }

    
    public Integer getLaterDays() {
        return laterDays;
    }

    public void setLaterDays(Integer laterDays) {
        this.laterDays = laterDays;
    }

    
    public BigDecimal getLaterInterest() {
        return laterInterest;
    }

    public void setLaterInterest(BigDecimal laterInterest) {
        this.laterInterest = laterInterest;
    }

    
    public BigDecimal getPenalSum() {
        return penalSum;
    }

    public void setPenalSum(BigDecimal penalSum) {
        this.penalSum = penalSum;
    }

    
    public Integer getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(Integer isPayment) {
        this.isPayment = isPayment;
    }

   
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

  
    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp;
    }
}
