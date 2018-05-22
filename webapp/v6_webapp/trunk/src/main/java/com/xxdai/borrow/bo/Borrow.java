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
 * @version $Id: Borrow.java 9464 2014-11-07 12:23:02Z xiaoying $
 * @since jdk1.6
 */
public class Borrow implements Serializable {

    private Long borrowId; //	BORROWID	N	INTEGER	N			标的id，主键自增
    private Long userId; //	USERID	N	INTEGER	N
    private Integer type; //	TYPE	N	INTEGER	N			标的类型1信用标2推荐标3净值标4秒还标5调剂标6抵押标7新新宝
    private String name; //	NAME	N	VARCHAR2(14)	N			标的名称
    private String content; //	CONTENT	N	VARCHAR2(4000)	N			标的描述
    private Integer use; //	USE	N	INTEGER	N	1		用途1.短期周转;2.生意周转;3.生活周转;4.购物消费;5.创业借款;6.其他借款
    private BigDecimal account; //	ACCOUNT	N	NUMBER(16,2)	N			借款金额
    private BigDecimal apr; //	APR	N	NUMBER(5,3)	N			年化利率
    private Integer timeLimit; //	TIMELIMIT	N	INTEGER	N			借款期限单位月
    private Integer paymentMethod; //	PAYMENTMETHOD	N	INTEGER	N			还款方式1.等额本息;2.付息还本;3.其他还款方式
    private Integer status; //	STATUS	N	INTEGER	N	1		状态借款状态:1.审核中;2.投标中;3.满标复审;4.还款中;5.还款结束;-1.流标;-2.被撤销;-3.审核失败
    private Integer award; //	AWARD	N	INTEGER	N	0		奖励类型:0无奖励1按金额奖励2按比例奖励
    private BigDecimal funds; //	FUNDS	N	NUMBER(8,2)	N			"奖励值：award=1时存放金额award=2时存放百分比（%1时 存放1）"
    private Integer porder; //	porder	N	INTEGER	N	0		借款标显示顺序优先级
    private BigDecimal lowestTender; //	LOWESTTENTDER	N	NUMBER(12,2)	N	50		本次借款最小投标金额，默认范围:[50,account]
    private BigDecimal mostTender; //	MOSTTENDER	N	NUMBER(12,2)	N	0		本次借款最大投标金额 0表示不受限制
    private BigDecimal onceTender; //	ONCETENDER	N	NUMBER(12,2)	N	0		单次投标可投金额上限0表示不限制
    private Date addTime; //	ADDTIME	N	DATE	N	SYSDATE		标的发布时间系统生成
    private Date endTime; //	ENDTIME	N	DATE	Y			最迟投标时间
    private Integer tenderTimes; //	TENDERTIMES	N	INTEGER	N			累计投标次数
    private BigDecimal accounTyes; //	ACCOUNTYES	N	NUMBER(16,2)	N	0		已借到金额范围[0,account]
    private Date successTime; //	SUCCESSTIME	N	DATE	Y			满标时间
    private BigDecimal cashdePosit; //	CASHDEPOSIT	N	NUMBER(12,2)	Y			保证金金额
    private Integer paySnap; //	PAYSNAP	N	INTEGER	N	0		是否发送打款截图0否1是


    public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Integer getUse() {
        return use;
    }

    public void setUse(Integer use) {
        this.use = use;
    }


    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }


    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }


    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }


    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getAward() {
        return award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }


    public BigDecimal getFunds() {
        return funds;
    }

    public void setFunds(BigDecimal funds) {
        this.funds = funds;
    }


    public Integer getPorder() {
        return porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }


    public BigDecimal getLowestTender() {
        return lowestTender;
    }

    public void setLowestTender(BigDecimal lowestTender) {
        this.lowestTender = lowestTender;
    }

    public BigDecimal getMostTender() {
        return mostTender;
    }

    public void setMostTender(BigDecimal mostTender) {
        this.mostTender = mostTender;
    }

    public BigDecimal getOnceTender() {
        return onceTender;
    }

    public void setOnceTender(BigDecimal onceTender) {
        this.onceTender = onceTender;
    }


    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Integer getTenderTimes() {
        return tenderTimes;
    }

    public void setTenderTimes(Integer tenderTimes) {
        this.tenderTimes = tenderTimes;
    }

    public BigDecimal getAccounTyes() {
        return accounTyes;
    }

    public void setAccounTyes(BigDecimal accounTyes) {
        this.accounTyes = accounTyes;
    }


    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }


    public BigDecimal getCashdePosit() {
        return cashdePosit;
    }

    public void setCashdePosit(BigDecimal cashdePosit) {
        this.cashdePosit = cashdePosit;
    }


    public Integer getPaySnap() {
        return paySnap;
    }

    public void setPaySnap(Integer paySnap) {
        this.paySnap = paySnap;
    }
}
