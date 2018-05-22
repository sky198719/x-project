/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 */
package com.xxdai.account.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账户信息
 *
 * @version $Id: Account.java 15447 2015-02-05 08:23:07Z yangzhe $
 * @since jdk1.6
 */
public class Account implements Serializable {

    private Long userId; //	USERID	N	INTEGER	N			用户编号
    private String pcode; //	PCODE	N	CHAR(4)	N			账户编号
    private BigDecimal usable; //	USABLE	N	NUMBER(16,2)	N	0		可用金额
    private BigDecimal frozen; //	FROZEN	N	NUMBER(16,2)	N	0		冻结金额
    private BigDecimal collection; //	COLLECTION	N	NUMBER(16,2)	N	0		待收金额
    private BigDecimal repayment; //	REPAYMENT	N	NUMBER(16,2)	Y	0		待还金额
    private BigDecimal rechargeSum; //	RECHARGESUM	N	NUMBER(16,2)	Y	0		充值累计
    private BigDecimal monthRecharge; //	MONTHRECHARGE	N	NUMBER(16,2)	Y	0		本月充值累计
    private BigDecimal yearRecharge; //	YEARRECHARGE	N	NUMBER(16,2)	Y	0		本年充值累计
    private BigDecimal rechargeFeeSum; //	RECHARGEFEESUM	N	NUMBER(16,2)	Y	0		充值手续费累计
    private BigDecimal monthRechargeFee; //	MONTHRECHARGEFEE	N	NUMBER(16,2)	Y	0		本月充值手续费累计
    private BigDecimal yearRechargeFee; //	YEARRECHARGEFEE	N	NUMBER(16,2)	Y	0		本年充值手续费累计
    private BigDecimal accountTotal; // ACCOUNTTOTAL	N	NUMBER(16,2)	Y	0		账户总资产（可用+冻结+待收-待还）
    private BigDecimal innerInSum;// INNERINSUM	N	NUMBER(16,2)	Y	0		内部转入累计
    private BigDecimal innerOutSum;// INNEROUTSUM	N	NUMBER(16,2)	Y	0		内部传出累计


    private BigDecimal cashSum; //	CASHSUM	N	NUMBER(16,2)	Y	0		提现累计
    private BigDecimal monthCash; //	MONTHCASH	N	NUMBER(16,2)	Y	0		本月提现累计
    private BigDecimal yearCash; //	YEARCASH	N	NUMBER(16,2)	Y	0		本年提现累计
    private BigDecimal cashFeeSum; //	CASHFEESUM	N	NUMBER(16,2)	Y	0		提现手续费累计
    private BigDecimal monthCashFee; //	MONTHCASHFEE	N	NUMBER(16,2)	Y	0		本月提现手续费累计
    private BigDecimal yearCashFee; //	YEARCASHFEE	N	NUMBER(16,2)	Y	0		本年提现手续费累计
    private BigDecimal tenderSum; //	TENDERSUM	N	NUMBER(16,2)	Y	0		投资累计
    private BigDecimal monthTender; //	MONTHTENDER	N	NUMBER(16,2)	Y	0		本月投资累计
    private BigDecimal yearTender; //	YEARTENDER	N	NUMBER(16,2)	Y	0		本年投资累计
    private BigDecimal tenderFundSum; //	TENDERFUNDSUM	N	NUMBER(16,2)	Y	0		投标奖励累计（投资人）
    private BigDecimal monthTenderFund; //	MONTHTENDERFUND	N	NUMBER(16,2)	Y	0		本月投标奖励累计（投资人）
    private BigDecimal yearTenderFund; //	YEARTENDERFUND	N	NUMBER(16,2)	Y	0		本年投标奖励累计（投资人）
    private BigDecimal borrowFundSum; //	BORROWFUNDSUM	N	NUMBER(16,2)	Y	0		投标奖励累计（借款人）
    private BigDecimal monthBorrowFund; //	MONTHBORROWFUND	N	NUMBER(16,2)	Y	0		本月投标奖励累计（借款人）
    private BigDecimal yearBorrowFund; //	YEARBORROWFUND	N	NUMBER(16,2)	Y	0		本年投标奖励累计（借款人）
    private BigDecimal investReturnSum; //	INVESTRETURNSUM	N	NUMBER(16,2)	Y	0		投资回款累计
    private BigDecimal monthInvestReturn; //	MONTHINVESTRETURN	N	NUMBER(16,2)	Y	0		本月投资回款累计
    private BigDecimal yearInvestReturn; //	YEARINVESTRETURN	N	NUMBER(16,2)	Y	0		本年投资回款累计
    private BigDecimal interestSum; //	INTERESTSUM	N	NUMBER(16,2)	Y	0		利息累计
    private BigDecimal monthInterest; //	MONTHINTEREST	N	NUMBER(16,2)	Y	0		本月利息累计
    private BigDecimal yearInterest; //	YEARINTEREST	N	NUMBER(16,2)	Y	0		本年利息累计
    private BigDecimal interestManagingFeeSum; //	INTERESTMANAGINGFEESUM	N	NUMBER(16,2)	Y	0		收益管理费累计
    private BigDecimal monthInterestManagingFee; //	MONTHINTERESTMANAGINGFEE	N	NUMBER(16,2)	Y	0		本月收益管理费累计
    private BigDecimal yearInterestManagingFee; //	YEARINTERESTMANAGINGFEE	N	NUMBER(16,2)	Y	0		"本年收益管理费累计 本年收益管理费累计"
    private BigDecimal repaymentSum; //	REPAYMENTSUM	N	NUMBER(16,2)	Y	0		还款累计
    private BigDecimal monthRepaymentSum; //	MONTHREPAYMENTSUM	N	NUMBER(16,2)	Y	0		本月还款累计
    private BigDecimal yearRepaymentSum; //	YEARREPAYMENTSUM	N	NUMBER(16,2)	Y	0		本年还款累计
    private BigDecimal loanSum; //	LOANSUM	N	NUMBER(16,2)	Y	0		借款累计
    private BigDecimal monthLoan; //	MONTHLOAN	N	NUMBER(16,2)	Y	0		本月借款累计
    private BigDecimal yearLoan; //	YEARLOAN	N	NUMBER(16,2)	Y	0		本年借款累计
    private BigDecimal loanManagingFeeSum; //	LOANMANAGINGFEESUM	N	NUMBER(16,2)	Y	0		借款管理费累计
    private BigDecimal monthLoanManagingFee; //	MONTHLOANMANAGINGFEE	N	NUMBER(16,2)	Y	0		本月借款管理费累计
    private BigDecimal yearLoanManagingFee; //	YEARLOANMANAGINGFEE	N	NUMBER(16,2)	Y	0		本年借款管理费累计
    private BigDecimal latepayFineSumDebtor; //	LATEPAYFINESUMDEBTOR	N	NUMBER(16,2)	Y	0		逾期罚息累计（投资人）
    private BigDecimal monthLatePayFineDebtor; //	MONTHLATEPAYFINEDEBTOR	N	NUMBER(16,2)	Y	0		本月逾期罚息累计（投资人）
    private BigDecimal yearLatePayFineDebtor; //	YEARLATEPAYFINEDEBTOR	N	NUMBER(16,2)	Y	0		本年逾期罚息累计（投资人）
    private BigDecimal latePayFineSumCreditor; //	LATEPAYFINESUMCREDITOR	N	NUMBER(16,2)	Y	0		逾期罚息累计（借款人）
    private BigDecimal monthLatePayFineCreditor; //	MONTHLATEPAYFINECREDITOR	N	NUMBER(16,2)	Y	0		本月逾期罚息累计（借款人）
    private BigDecimal yearLatePayFineCreditor; //	YEARLATEPAYFINECREDITOR	N	NUMBER(16,2)	Y	0		本年逾期罚息累计（借款人）
    private BigDecimal consumeSum; //	CONSUMESUM	N	NUMBER(16,2)	Y	0		消费累计
    private BigDecimal monthConsume; //	MONTHCONSUME	N	NUMBER(16,2)	Y	0		本月消费累计
    private BigDecimal yearConsume; //	YEARCONSUME	N	NUMBER(16,2)	Y	0		本年消费累计
    private BigDecimal xinCoinExchangeSum; //	XINCOINEXCHANGESUM	N	NUMBER(16,2)	Y	0		新新币兑换累计
    private BigDecimal monthXinCoinExchange; //	MONTHXINCOINEXCHANGE	N	NUMBER(16,2)	Y	0		本月新新币兑换累计
    private BigDecimal yearXinCoinExchange; //	YEARXINCOINEXCHANGE	N	NUMBER(16,2)	Y	0		本年新新币兑换累计
    private BigDecimal rewardSum; //	REWARDSUM	N	NUMBER(16,2)	Y	0		奖励累计
    private BigDecimal monthReward; //	MONTHREWARD	N	NUMBER(16,2)	Y	0		本月奖励累计
    private BigDecimal yearReward; //	YEARREWARD	N	NUMBER(16,2)	Y	0		本年奖励累计
    private BigDecimal otherFeeSum; //	OTHERFEESUM	N	NUMBER(16,2)	Y	0		其他费用累计
    private BigDecimal monthOtherFee; //	MONTHOTHERFEE	N	NUMBER(16,2)	Y	0		本月其他费用累计
    private BigDecimal yearOtherFee; //	YEAROTHERFEE	N	NUMBER(16,2)	Y	0		本年其他费用累计
    private BigDecimal otherMulatiOnneg; //	OTHERMULATIONNEG	N	NUMBER(16,2)	Y	0		其它累计负（调平项）
    private BigDecimal otherMulatiOnpos; //	OTHERMULATIONPOS	N	NUMBER(16,2)	Y	0		其它累计正（调平项）


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

    public BigDecimal getRechargeSum() {
        return rechargeSum;
    }

    public void setRechargeSum(BigDecimal rechargeSum) {
        this.rechargeSum = rechargeSum;
    }

    public BigDecimal getMonthRecharge() {
        return monthRecharge;
    }

    public void setMonthRecharge(BigDecimal monthRecharge) {
        this.monthRecharge = monthRecharge;
    }

    public BigDecimal getYearRecharge() {
        return yearRecharge;
    }

    public void setYearRecharge(BigDecimal yearRecharge) {
        this.yearRecharge = yearRecharge;
    }

    public BigDecimal getRechargeFeeSum() {
        return rechargeFeeSum;
    }

    public void setRechargeFeeSum(BigDecimal rechargeFeeSum) {
        this.rechargeFeeSum = rechargeFeeSum;
    }

    public BigDecimal getMonthRechargeFee() {
        return monthRechargeFee;
    }

    public void setMonthRechargeFee(BigDecimal monthRechargeFee) {
        this.monthRechargeFee = monthRechargeFee;
    }

    public BigDecimal getYearRechargeFee() {
        return yearRechargeFee;
    }

    public void setYearRechargeFee(BigDecimal yearRechargeFee) {
        this.yearRechargeFee = yearRechargeFee;
    }

    public BigDecimal getCashSum() {
        return cashSum;
    }

    public void setCashSum(BigDecimal cashSum) {
        this.cashSum = cashSum;
    }

    public BigDecimal getMonthCash() {
        return monthCash;
    }

    public void setMonthCash(BigDecimal monthCash) {
        this.monthCash = monthCash;
    }

    public BigDecimal getYearCash() {
        return yearCash;
    }

    public void setYearCash(BigDecimal yearCash) {
        this.yearCash = yearCash;
    }

    public BigDecimal getCashFeeSum() {
        return cashFeeSum;
    }

    public void setCashFeeSum(BigDecimal cashFeeSum) {
        this.cashFeeSum = cashFeeSum;
    }

    public BigDecimal getMonthCashFee() {
        return monthCashFee;
    }

    public void setMonthCashFee(BigDecimal monthCashFee) {
        this.monthCashFee = monthCashFee;
    }

    public BigDecimal getYearCashFee() {
        return yearCashFee;
    }

    public void setYearCashFee(BigDecimal yearCashFee) {
        this.yearCashFee = yearCashFee;
    }

    public BigDecimal getTenderSum() {
        return tenderSum;
    }

    public void setTenderSum(BigDecimal tenderSum) {
        this.tenderSum = tenderSum;
    }

    public BigDecimal getMonthTender() {
        return monthTender;
    }

    public void setMonthTender(BigDecimal monthTender) {
        this.monthTender = monthTender;
    }

    public BigDecimal getYearTender() {
        return yearTender;
    }

    public void setYearTender(BigDecimal yearTender) {
        this.yearTender = yearTender;
    }

    public BigDecimal getTenderFundSum() {
        return tenderFundSum;
    }

    public void setTenderFundSum(BigDecimal tenderFundSum) {
        this.tenderFundSum = tenderFundSum;
    }

    public BigDecimal getMonthTenderFund() {
        return monthTenderFund;
    }

    public void setMonthTenderFund(BigDecimal monthTenderFund) {
        this.monthTenderFund = monthTenderFund;
    }

    public BigDecimal getYearTenderFund() {
        return yearTenderFund;
    }

    public void setYearTenderFund(BigDecimal yearTenderFund) {
        this.yearTenderFund = yearTenderFund;
    }

    public BigDecimal getBorrowFundSum() {
        return borrowFundSum;
    }

    public void setBorrowFundSum(BigDecimal borrowFundSum) {
        this.borrowFundSum = borrowFundSum;
    }

    public BigDecimal getMonthBorrowFund() {
        return monthBorrowFund;
    }

    public void setMonthBorrowFund(BigDecimal monthBorrowFund) {
        this.monthBorrowFund = monthBorrowFund;
    }

    public BigDecimal getYearBorrowFund() {
        return yearBorrowFund;
    }

    public void setYearBorrowFund(BigDecimal yearBorrowFund) {
        this.yearBorrowFund = yearBorrowFund;
    }

    public BigDecimal getInvestReturnSum() {
        return investReturnSum;
    }

    public void setInvestReturnSum(BigDecimal investReturnSum) {
        this.investReturnSum = investReturnSum;
    }

    public BigDecimal getMonthInvestReturn() {
        return monthInvestReturn;
    }

    public void setMonthInvestReturn(BigDecimal monthInvestReturn) {
        this.monthInvestReturn = monthInvestReturn;
    }

    public BigDecimal getYearInvestReturn() {
        return yearInvestReturn;
    }

    public void setYearInvestReturn(BigDecimal yearInvestReturn) {
        this.yearInvestReturn = yearInvestReturn;
    }

    public BigDecimal getInterestSum() {
        return interestSum;
    }

    public void setInterestSum(BigDecimal interestSum) {
        this.interestSum = interestSum;
    }

    public BigDecimal getMonthInterest() {
        return monthInterest;
    }

    public void setMonthInterest(BigDecimal monthInterest) {
        this.monthInterest = monthInterest;
    }

    public BigDecimal getYearInterest() {
        return yearInterest;
    }

    public void setYearInterest(BigDecimal yearInterest) {
        this.yearInterest = yearInterest;
    }

    public BigDecimal getInterestManagingFeeSum() {
        return interestManagingFeeSum;
    }

    public void setInterestManagingFeeSum(BigDecimal interestManagingFeeSum) {
        this.interestManagingFeeSum = interestManagingFeeSum;
    }

    public BigDecimal getMonthInterestManagingFee() {
        return monthInterestManagingFee;
    }

    public void setMonthInterestManagingFee(BigDecimal monthInterestManagingFee) {
        this.monthInterestManagingFee = monthInterestManagingFee;
    }

    public BigDecimal getYearInterestManagingFee() {
        return yearInterestManagingFee;
    }

    public void setYearInterestManagingFee(BigDecimal yearInterestManagingFee) {
        this.yearInterestManagingFee = yearInterestManagingFee;
    }

    public BigDecimal getRepaymentSum() {
        return repaymentSum;
    }

    public void setRepaymentSum(BigDecimal repaymentSum) {
        this.repaymentSum = repaymentSum;
    }

    public BigDecimal getMonthRepaymentSum() {
        return monthRepaymentSum;
    }

    public void setMonthRepaymentSum(BigDecimal monthRepaymentSum) {
        this.monthRepaymentSum = monthRepaymentSum;
    }

    public BigDecimal getYearRepaymentSum() {
        return yearRepaymentSum;
    }

    public void setYearRepaymentSum(BigDecimal yearRepaymentSum) {
        this.yearRepaymentSum = yearRepaymentSum;
    }

    public BigDecimal getLoanSum() {
        return loanSum;
    }

    public void setLoanSum(BigDecimal loanSum) {
        this.loanSum = loanSum;
    }

    public BigDecimal getMonthLoan() {
        return monthLoan;
    }

    public void setMonthLoan(BigDecimal monthLoan) {
        this.monthLoan = monthLoan;
    }

    public BigDecimal getYearLoan() {
        return yearLoan;
    }

    public void setYearLoan(BigDecimal yearLoan) {
        this.yearLoan = yearLoan;
    }

    public BigDecimal getLoanManagingFeeSum() {
        return loanManagingFeeSum;
    }

    public void setLoanManagingFeeSum(BigDecimal loanManagingFeeSum) {
        this.loanManagingFeeSum = loanManagingFeeSum;
    }

    public BigDecimal getMonthLoanManagingFee() {
        return monthLoanManagingFee;
    }

    public void setMonthLoanManagingFee(BigDecimal monthLoanManagingFee) {
        this.monthLoanManagingFee = monthLoanManagingFee;
    }

    public BigDecimal getYearLoanManagingFee() {
        return yearLoanManagingFee;
    }

    public void setYearLoanManagingFee(BigDecimal yearLoanManagingFee) {
        this.yearLoanManagingFee = yearLoanManagingFee;
    }

    public BigDecimal getLatepayFineSumDebtor() {
        return latepayFineSumDebtor;
    }

    public void setLatepayFineSumDebtor(BigDecimal latepayFineSumDebtor) {
        this.latepayFineSumDebtor = latepayFineSumDebtor;
    }

    public BigDecimal getMonthLatePayFineDebtor() {
        return monthLatePayFineDebtor;
    }

    public void setMonthLatePayFineDebtor(BigDecimal monthLatePayFineDebtor) {
        this.monthLatePayFineDebtor = monthLatePayFineDebtor;
    }

    public BigDecimal getYearLatePayFineDebtor() {
        return yearLatePayFineDebtor;
    }

    public void setYearLatePayFineDebtor(BigDecimal yearLatePayFineDebtor) {
        this.yearLatePayFineDebtor = yearLatePayFineDebtor;
    }

    public BigDecimal getLatePayFineSumCreditor() {
        return latePayFineSumCreditor;
    }

    public void setLatePayFineSumCreditor(BigDecimal latePayFineSumCreditor) {
        this.latePayFineSumCreditor = latePayFineSumCreditor;
    }

    public BigDecimal getMonthLatePayFineCreditor() {
        return monthLatePayFineCreditor;
    }

    public void setMonthLatePayFineCreditor(BigDecimal monthLatePayFineCreditor) {
        this.monthLatePayFineCreditor = monthLatePayFineCreditor;
    }

    public BigDecimal getYearLatePayFineCreditor() {
        return yearLatePayFineCreditor;
    }

    public void setYearLatePayFineCreditor(BigDecimal yearLatePayFineCreditor) {
        this.yearLatePayFineCreditor = yearLatePayFineCreditor;
    }

    public BigDecimal getConsumeSum() {
        return consumeSum;
    }

    public void setConsumeSum(BigDecimal consumeSum) {
        this.consumeSum = consumeSum;
    }

    public BigDecimal getMonthConsume() {
        return monthConsume;
    }

    public void setMonthConsume(BigDecimal monthConsume) {
        this.monthConsume = monthConsume;
    }

    public BigDecimal getYearConsume() {
        return yearConsume;
    }

    public void setYearConsume(BigDecimal yearConsume) {
        this.yearConsume = yearConsume;
    }

    public BigDecimal getXinCoinExchangeSum() {
        return xinCoinExchangeSum;
    }

    public void setXinCoinExchangeSum(BigDecimal xinCoinExchangeSum) {
        this.xinCoinExchangeSum = xinCoinExchangeSum;
    }

    public BigDecimal getMonthXinCoinExchange() {
        return monthXinCoinExchange;
    }

    public void setMonthXinCoinExchange(BigDecimal monthXinCoinExchange) {
        this.monthXinCoinExchange = monthXinCoinExchange;
    }

    public BigDecimal getYearXinCoinExchange() {
        return yearXinCoinExchange;
    }

    public void setYearXinCoinExchange(BigDecimal yearXinCoinExchange) {
        this.yearXinCoinExchange = yearXinCoinExchange;
    }

    public BigDecimal getRewardSum() {
        return rewardSum;
    }

    public void setRewardSum(BigDecimal rewardSum) {
        this.rewardSum = rewardSum;
    }

    public BigDecimal getMonthReward() {
        return monthReward;
    }

    public void setMonthReward(BigDecimal monthReward) {
        this.monthReward = monthReward;
    }

    public BigDecimal getYearReward() {
        return yearReward;
    }

    public void setYearReward(BigDecimal yearReward) {
        this.yearReward = yearReward;
    }

    public BigDecimal getOtherFeeSum() {
        return otherFeeSum;
    }

    public void setOtherFeeSum(BigDecimal otherFeeSum) {
        this.otherFeeSum = otherFeeSum;
    }

    public BigDecimal getMonthOtherFee() {
        return monthOtherFee;
    }

    public void setMonthOtherFee(BigDecimal monthOtherFee) {
        this.monthOtherFee = monthOtherFee;
    }

    public BigDecimal getYearOtherFee() {
        return yearOtherFee;
    }

    public void setYearOtherFee(BigDecimal yearOtherFee) {
        this.yearOtherFee = yearOtherFee;
    }

    public BigDecimal getOtherMulatiOnneg() {
        return otherMulatiOnneg;
    }

    public void setOtherMulatiOnneg(BigDecimal otherMulatiOnneg) {
        this.otherMulatiOnneg = otherMulatiOnneg;
    }

    public BigDecimal getOtherMulatiOnpos() {
        return otherMulatiOnpos;
    }

    public void setOtherMulatiOnpos(BigDecimal otherMulatiOnpos) {
        this.otherMulatiOnpos = otherMulatiOnpos;
    }

    public BigDecimal getAccountTotal() {
        return accountTotal;
    }

    public void setAccountTotal(BigDecimal accountTotal) {
        this.accountTotal = accountTotal;
    }

    public BigDecimal getInnerInSum() {
        return innerInSum;
    }

    public void setInnerInSum(BigDecimal innerInSum) {
        this.innerInSum = innerInSum;
    }

    public BigDecimal getInnerOutSum() {
        return innerOutSum;
    }

    public void setInnerOutSum(BigDecimal innerOutSum) {
        this.innerOutSum = innerOutSum;
    }
}
