package com.xxdai.newHand.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by chaihuangqi on 2015/12/29.
 */
public class CommProdDeploy {
    private String name;	//产品名称
    private Integer prodType;//产品类型
    private String deployId;
    private String prodKindCode;// 产品分类
    private String terms;// 产品期数
    private BigDecimal maxExperience;// 体验最大使用额度
    private Integer days;// 投资期限(天)
    private BigDecimal apr;// 年化利率
    private BigDecimal amount;// 总额
    private String calExpression;// 收益计算公式
    private BigDecimal lowestTender;// 本次借款最小投标金额
    private BigDecimal stepTender;// 加入倍数
    private BigDecimal mostTender;// 本次借款最大投标金额
    private BigDecimal tendSum;//已投金额
    private Date startDate;// 购买开始日期
    private Date endDate;// 购买截止日期
    private Integer status;// 状态
    private Date addTime;
    private Date deployTime;
    private Integer closeTerm;//锁定期

    private Integer isGameOver;//是否已关闭
    private Integer validDays;//投标期限（天）
    private BigDecimal untendSum;//剩余可投金额（元）
    private BigDecimal maxMount;//最大可投金额（元）
    private String stepSum;//投入倍数
    private String curUserCanBuy;//当前用户是否可以购买
    private Date currTime;	//当前时间
    private Integer tenderCount;//投标数
    private BigDecimal floatApr; //活动利率
    private Boolean isNewUser; //是否新用户

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProdType() {
        return prodType;
    }

    public void setProdType(Integer prodType) {
        this.prodType = prodType;
    }

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public String getProdKindCode() {
        return prodKindCode;
    }

    public void setProdKindCode(String prodKindCode) {
        this.prodKindCode = prodKindCode;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public BigDecimal getMaxExperience() {
        return maxExperience;
    }

    public void setMaxExperience(BigDecimal maxExperience) {
        this.maxExperience = maxExperience;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
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

    public String getCalExpression() {
        return calExpression;
    }

    public void setCalExpression(String calExpression) {
        this.calExpression = calExpression;
    }

    public BigDecimal getLowestTender() {
        return lowestTender;
    }

    public void setLowestTender(BigDecimal lowestTender) {
        this.lowestTender = lowestTender;
    }

    public BigDecimal getStepTender() {
        return stepTender;
    }

    public void setStepTender(BigDecimal stepTender) {
        this.stepTender = stepTender;
    }

    public BigDecimal getMostTender() {
        return mostTender;
    }

    public void setMostTender(BigDecimal mostTender) {
        this.mostTender = mostTender;
    }

    public BigDecimal getTendSum() {
        return tendSum;
    }

    public void setTendSum(BigDecimal tendSum) {
        this.tendSum = tendSum;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(Date deployTime) {
        this.deployTime = deployTime;
    }

    public Integer getCloseTerm() {
        return closeTerm;
    }

    public void setCloseTerm(Integer closeTerm) {
        this.closeTerm = closeTerm;
    }

    public Integer getIsGameOver() {
        return isGameOver;
    }

    public void setIsGameOver(Integer isGameOver) {
        this.isGameOver = isGameOver;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public BigDecimal getUntendSum() {
        return untendSum;
    }

    public void setUntendSum(BigDecimal untendSum) {
        this.untendSum = untendSum;
    }

    public BigDecimal getMaxMount() {
        return maxMount;
    }

    public void setMaxMount(BigDecimal maxMount) {
        this.maxMount = maxMount;
    }

    public String getStepSum() {
        return stepSum;
    }

    public void setStepSum(String stepSum) {
        this.stepSum = stepSum;
    }

    public String getCurUserCanBuy() {
        return curUserCanBuy;
    }

    public void setCurUserCanBuy(String curUserCanBuy) {
        this.curUserCanBuy = curUserCanBuy;
    }

    public Date getCurrTime() {
        return currTime;
    }

    public void setCurrTime(Date currTime) {
        this.currTime = currTime;
    }

    public Integer getTenderCount() {
        return tenderCount;
    }

    public void setTenderCount(Integer tenderCount) {
        this.tenderCount = tenderCount;
    }

    public BigDecimal getFloatApr() {
        return floatApr;
    }

    public void setFloatApr(BigDecimal floatApr) {
        this.floatApr = floatApr;
    }

    public Boolean getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(Boolean isNewUser) {
        this.isNewUser = isNewUser;
    }
}
