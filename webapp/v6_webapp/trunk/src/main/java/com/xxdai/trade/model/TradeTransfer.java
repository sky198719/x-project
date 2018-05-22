/**
* <p>Title: TradeTransfer.java</p>
* <p>Description: </p>
* <p>Copyright (c) 2014, www.xinxindai.com All Rights Reserved. </p>
* <p>Company: www.xinxindai.com</p>
* @author huna
* @date 2014年9月17日
* @version 1.0
*/
package com.xxdai.trade.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huna
 * @since jdk1.6
 * @version $Id: TradeTransfer.java 15671 2015-02-12 03:46:15Z huna $
 */
public class TradeTransfer {
	/** 转让申请单号 */
    private String requestId;
    /** 责权编号 */
    private String tenderId;
    /** 债权转让发起方式：1用户发起2系统发起 */
    private Integer reqMethod;
    /** 卖出用户编号 */
    private Long userId;
    /** 申请时间 */
    private Date addTime;
    /** 申请ip */
    private String addIp;
    /** 奖励金额0表示不奖励 */
    private BigDecimal funds;
    /** 转让成功手续费率（百分子N）卖方支付 */
    private BigDecimal rate;
    /** 转让手续费 */
    private BigDecimal transFee;
    /** 转让时年化利率 */
    private BigDecimal apr;
    /** 转让金额（成功时填写）*/
    private BigDecimal amount;
    /** 状态1装让中2成功3失败4撤销5过期（系统）*/
    private Integer status;
    /** 备注 */
    private String note;
    
	/** 债权转让申请状态名称 */
	private String statusName ;
	/** 标的编号 */
	private String borrowId ;
	/** 标的名称 */
	private String borrowName;
	/** 标的类型 */
	private Integer borrowType;
	/** 标的还款方式  还款方式1.等额本息;2.付息还本;3.其他还款方式*/
	private Integer paymentMethod;
	/** 显示表标签id (使用字典) */
	private String showLabelId;
	/** 贷款人头像 */
	private String headImg;
	/** 债权当前剩余期数 */
	private Long remainNumber;
	/** 下一还款日 */
	private String nextRepaymentTime;
	/** 年利率 */
	private BigDecimal rateYear;
	/** 待收本息 */
	private BigDecimal repaymentAmount;
	/** 待收利息 */
	private BigDecimal repaymentInterest;
	/** 待收本金 */
	private BigDecimal repayCapital;
	/** 原始投资金额 */
	private BigDecimal effectiveMoney;
	/** 已收期数 */
	private Long receivedNumber;
	/** 债权转让手续费最低收费 */
	private BigDecimal transferLowerFee;
	/** 投标时间 */
	private String borrowTenderTime;
	/** 标的投标状态 */
	private int borrowTenderStatus;
	/** 标的投标状态名称 */
	private String borrowTenderStatusName;
	/** 转让交易日期 */
	private String outTime;
	/** 转让编号 */
	private String packId;
	/** 债权转让时剩余期数 */
	private Long terms;
	
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getTenderId() {
		return tenderId;
	}
	public void setTenderId(String tenderId) {
		this.tenderId = tenderId;
	}
	public Integer getReqMethod() {
		return reqMethod;
	}
	public void setReqMethod(Integer reqMethod) {
		this.reqMethod = reqMethod;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public BigDecimal getFunds() {
		return funds;
	}
	public void setFunds(BigDecimal funds) {
		this.funds = funds;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getTransFee() {
		return transFee;
	}
	public void setTransFee(BigDecimal transFee) {
		this.transFee = transFee;
	}
	public BigDecimal getApr() {
		return apr;
	}
	public void setApr(BigDecimal apr) {
		this.apr = apr;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	public Long getRemainNumber() {
		return remainNumber;
	}
	public void setRemainNumber(Long remainNumber) {
		this.remainNumber = remainNumber;
	}
	public String getNextRepaymentTime() {
		return nextRepaymentTime;
	}
	public void setNextRepaymentTime(String nextRepaymentTime) {
		this.nextRepaymentTime = nextRepaymentTime;
	}
	public BigDecimal getRateYear() {
		return rateYear;
	}
	public void setRateYear(BigDecimal rateYear) {
		this.rateYear = rateYear;
	}
	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}
	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}
	public BigDecimal getRepaymentInterest() {
		return repaymentInterest;
	}
	public void setRepaymentInterest(BigDecimal repaymentInterest) {
		this.repaymentInterest = repaymentInterest;
	}
	public BigDecimal getRepayCapital() {
		return repayCapital;
	}
	public void setRepayCapital(BigDecimal repayCapital) {
		this.repayCapital = repayCapital;
	}
	public BigDecimal getEffectiveMoney() {
		return effectiveMoney;
	}
	public void setEffectiveMoney(BigDecimal effectiveMoney) {
		this.effectiveMoney = effectiveMoney;
	}
	public Long getReceivedNumber() {
		return receivedNumber;
	}
	public void setReceivedNumber(Long receivedNumber) {
		this.receivedNumber = receivedNumber;
	}
	public BigDecimal getTransferLowerFee() {
		return transferLowerFee;
	}
	public void setTransferLowerFee(BigDecimal transferLowerFee) {
		this.transferLowerFee = transferLowerFee;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getBorrowTenderTime() {
		return borrowTenderTime;
	}
	public void setBorrowTenderTime(String borrowTenderTime) {
		this.borrowTenderTime = borrowTenderTime;
	}
	public int getBorrowTenderStatus() {
		return borrowTenderStatus;
	}
	public void setBorrowTenderStatus(int borrowTenderStatus) {
		this.borrowTenderStatus = borrowTenderStatus;
	}
	public String getBorrowTenderStatusName() {
		return borrowTenderStatusName;
	}
	public void setBorrowTenderStatusName(String borrowTenderStatusName) {
		this.borrowTenderStatusName = borrowTenderStatusName;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public Integer getBorrowType() {
		return borrowType;
	}
	public void setBorrowType(Integer borrowType) {
		this.borrowType = borrowType;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getPackId() {
		return packId;
	}
	public void setPackId(String packId) {
		this.packId = packId;
	}
	public Long getTerms() {
		return terms;
	}
	public void setTerms(Long terms) {
		this.terms = terms;
	}
	public Integer getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getShowLabelId() {
		return showLabelId;
	}
	public void setShowLabelId(String showLabelId) {
		this.showLabelId = showLabelId;
	}
}
