/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.borrow.webservice.entity;

import java.math.BigDecimal;
import java.util.List;

import com.xxdai.borrow.bo.BorrowBillextend;
import com.xxdai.borrow.bo.CompanyInfo;
import com.xxdai.borrow.model.BorrowQuery;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.person.bo.BaseInfo;
import com.xxdai.person.model.UserCreditStuffShowInfo;

/**
 * 标的ws返回结果
 *
 * @version $Id: BorrowQueryWsResponse.java 14724 2015-01-22 03:16:47Z qiansenyi $
 * @since jdk1.6
 */
public class BorrowQueryWsResponse extends WSResponse {

    /**
     * 标的信息
     */
    private BorrowQuery borrowQuery;
    /**
     * 用户基本信息
     */
    private BaseInfo baseInfo;
    /**
     * 用户逾期次数
     */
    private int overdue;
    /**
     * 逾期总金额
     */
    private BigDecimal overdueAccount;
    /**
     * 用户是否有房贷
     */
    private int isHousePurchase;
    /**
     * 用户是否有车贷
     */
    private int isCarPurchase;
    /**
     * 正常还清贷款笔数
     */
    private int accountNomalPay;
    /**
     * 逾期还清贷款笔数
     */
    private int accountLazyPay = 0;
    /**
     * 首次借款时间
     */
    private String borrowFirstTime;
    /**
     * 成功借款次数
     */
    private int borrowCount;

    //就读学校
    private String school;

    //入学时间
    private String entranceTime;

    //学年制
    private int acadermicYearSystem;

    /**
     * 提供材料
     */
    private List<UserCreditStuffShowInfo> userCreditStuffDics;

    /**
     * 票据信息
     */
    private BorrowBillextend borrowBill;

    /**
     * 待还总额
     *
     * @return
     */
    private BigDecimal repaymentSum;

    /**
     * 借款企业信息
     */
    private CompanyInfo companyInfo;

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public BorrowQuery getBorrowQuery() {
        return borrowQuery;
    }

    public void setBorrowQuery(BorrowQuery borrowQuery) {
        this.borrowQuery = borrowQuery;
    }

    public BaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public int getOverdue() {
        return overdue;
    }

    public void setOverdue(int overdue) {
        this.overdue = overdue;
    }

    public int getIsHousePurchase() {
        return isHousePurchase;
    }

    public void setIsHousePurchase(int isHousePurchase) {
        this.isHousePurchase = isHousePurchase;
    }

    public int getIsCarPurchase() {
        return isCarPurchase;
    }

    public void setIsCarPurchase(int isCarPurchase) {
        this.isCarPurchase = isCarPurchase;
    }

    public List<UserCreditStuffShowInfo> getUserCreditStuffDics() {
        return userCreditStuffDics;
    }

    public void setUserCreditStuffDics(
            List<UserCreditStuffShowInfo> userCreditStuffDics) {
        this.userCreditStuffDics = userCreditStuffDics;
    }

    public int getAccountNomalPay() {
        return accountNomalPay;
    }

    public void setAccountNomalPay(int accountNomalPay) {
        this.accountNomalPay = accountNomalPay;
    }

    public int getAccountLazyPay() {
        return accountLazyPay;
    }

    public void setAccountLazyPay(int accountLazyPay) {
        this.accountLazyPay = accountLazyPay;
    }

    public String getBorrowFirstTime() {
        return borrowFirstTime;
    }

    public void setBorrowFirstTime(String borrowFirstTime) {
        this.borrowFirstTime = borrowFirstTime;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(String entranceTime) {
        this.entranceTime = entranceTime;
    }

    public int getAcadermicYearSystem() {
        return acadermicYearSystem;
    }

    public void setAcadermicYearSystem(int acadermicYearSystem) {
        this.acadermicYearSystem = acadermicYearSystem;
    }

    public BorrowBillextend getBorrowBill() {
        return borrowBill;
    }

    public void setBorrowBill(BorrowBillextend borrowBill) {
        this.borrowBill = borrowBill;
    }

    public BigDecimal getRepaymentSum() {
        return repaymentSum;
    }

    public void setRepaymentSum(BigDecimal repaymentSum) {
        this.repaymentSum = repaymentSum;
    }

    public BigDecimal getOverdueAccount(){
        return overdueAccount;
    }

    public void setOverdueAccount(BigDecimal overdueAccount) {
        this.overdueAccount = overdueAccount;
    }
}