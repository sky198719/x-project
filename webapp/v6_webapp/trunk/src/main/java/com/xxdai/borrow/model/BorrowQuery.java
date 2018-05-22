package com.xxdai.borrow.model;

import java.math.BigDecimal;
import java.util.Date;


public class BorrowQuery {
    /**
     * 主键ID
     */
    private String borrowId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 借款标类型：自动 BORROW_TYPE
     */
    private Integer type;
    private String borrowType;
    /**
     * 借款标标题
     */
    private String name;
    /**
     * 借款描述（借款用途、借款资质、本人的简要信息）
     */
    private String content;
    /**
     * 借款用途： 1-短期周转； 2-生意周转； 3-生活周转； 4-购物消费； 5-创业消费； 6-其他借款；
     */
    private Integer use;
    /**
     * 借款金额
     */
    private BigDecimal account;
    /**
     * 借款年利率
     */
    private Double apr;
    /**
     * 借款期限
     */
    private Integer timeLimit;
    /**
     * 借款还款形式： 1-等额本息； 2-付息还本； 3-其他还款方式
     */
    private Integer paymentMethod;
    /**
     * 借款状态： BORROW_STATUS
     */
    private Integer status;
    private String borrowStatus;
    /**
     * 借款奖励： 0-否； 1-奖励金额；2-奖励比例
     */
    private Integer award;
    /**
     * award为1：存储奖励金额，award为2：存储奖励比例
     */
    private Double funds;
    /**
     * 借款标显示顺序优先级，默认优先级最低为0
     */
    private Integer order;
    /**
     * 本次借款已经借到的金额，范围[0,account]
     */
    private BigDecimal accountYes;
    /**
     * 本次借款的借款审核进度
     */
    private Integer approId;
    /**
     * 本次借款最小投标金额，默认范围：[50,account]
     */
    private BigDecimal lowestTender;
    /**
     * 累计借款最大投标金额，0-不受限制可投全部金额；
     */
    private BigDecimal mostTender;
    /**
     * 本次借款累计投标次数，默认记为0；
     */
    private Integer tenderTimes;
    /**
     * 一次性投标最多可投金额限制， 0表示为不限制，最多可以投全部金额；
     */
    private BigDecimal onceTender;
    /**
     * 借款发布时间
     */
    private Date addTime;
    /**
     * 借款投标结束时间
     */
    private Date endTime;
    /**
     * 借款满标时间
     */
    private Date successTime;
    /**
     * 保证金金额
     */
    private BigDecimal depositMoney;
    /**
     * 是否发送打款截图  0未发送   1已发送   default 0
     */
    private Integer is_paysnapsent;

    private BigDecimal repaymentAccount;

    private BigDecimal repaymentYesAccount;
    /**
     * 信用等级
     */
    private String creditLevel;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String headImg;
    /**
     * 所在省份
     */
    private String province;
    /**
     * 所在城市
     */
    private String city;

    /**
     * 昵称
     */
    private String nickName;

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
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

    public Double getApr() {
        return apr;
    }

    public void setApr(Double apr) {
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

    public Double getFunds() {
        return funds;
    }

    public void setFunds(Double funds) {
        this.funds = funds;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public BigDecimal getAccountYes() {
        return accountYes;
    }

    public void setAccountYes(BigDecimal accountYes) {
        this.accountYes = accountYes;
    }

    public Integer getApproId() {
        return approId;
    }

    public void setApproId(Integer approId) {
        this.approId = approId;
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

    public Integer getTenderTimes() {
        return tenderTimes;
    }

    public void setTenderTimes(Integer tenderTimes) {
        this.tenderTimes = tenderTimes;
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

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public BigDecimal getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(BigDecimal depositMoney) {
        this.depositMoney = depositMoney;
    }

    public Integer getIs_paysnapsent() {
        return is_paysnapsent;
    }

    public void setIs_paysnapsent(Integer is_paysnapsent) {
        this.is_paysnapsent = is_paysnapsent;
    }

    public BigDecimal getRepaymentAccount() {
        return repaymentAccount;
    }

    public void setRepaymentAccount(BigDecimal repaymentAccount) {
        this.repaymentAccount = repaymentAccount;
    }

    public BigDecimal getRepaymentYesAccount() {
        return repaymentYesAccount;
    }

    public void setRepaymentYesAccount(BigDecimal repaymentYesAccount) {
        this.repaymentYesAccount = repaymentYesAccount;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
