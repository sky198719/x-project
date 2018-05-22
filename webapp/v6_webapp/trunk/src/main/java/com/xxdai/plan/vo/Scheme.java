/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.plan.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述
 *
 * @version $Id: Scheme.java 16776 2015-03-26 07:20:52Z huna $
 * @since jdk1.6
 */
public class Scheme {
    /**
     * 计划ID
     */
    private String schemeId;
    /**
     * 计划标题
     */
    private String pname;
    /**
     * 描述
     */
    private String description;
    /**
     * 预售开始时间
     */
    private Date presaleBegin;
    /**
     * 预售截止时间
     */
    private Date presaleEnd;
    /**
     * 最小历史年化收益率
     */
    private BigDecimal minApr;
    /**
     * 最大历史年化收益率
     */
    private BigDecimal maxApr;
    /**
     * 本计划可投最低等级
     */
    private Integer blevel;
    /**
     * 锁定期限
     */
    private Long closeterm;
    /**
     * 计划总金额
     */
    private BigDecimal account;
    /**
     * 剩余金额
     */
    private BigDecimal remacount;
    /**
     * 最小购买金额
     */
    private BigDecimal leastamount;
    /**
     * 最大购买金额
     */
    private BigDecimal mostamount;
    /**
     * 购买递增金额（步长）
     */
    private BigDecimal step;
    /**
     * 状态，0：待发布，1：预定期，2：封闭期，3：开放期，5：撤销
     */
    private String status;
    /**
     * 定金比例
     */
    private BigDecimal earnestPercent;
    /**
     * 定金上限
     */
    private BigDecimal earnestMax;
    /**
     * 支付截止日期
     */
    private Date presalePayEnd;
    /**
     * 开放加入开始日期
     */
    private Date opensaleBegin;
    /**
     * 开放加入截止日期
     */
    private Date opensaleEnd;
    /**
     * 解约金百分比
     */
    private BigDecimal forfeitpercent;
    /**
     * 添加时间
     */
    private Date createDate;
    /**
     * 添加人所在IP
     */
    private String createIp;
    /**
     * 创建人
     */
    private Long creator;
    /**
     * 修改时间
     */
    private Date modifyDate;
    /**
     * 更新人
     */
    private Long lastModify;

    /**
     * 计划类型。1：新元宝A，2：新元宝双季盈，3：新元宝年年盈
     */
    private Integer type;
    /**
     * 投标范围
     */
    private String tenderDesc;

    private String schemeStatus;

    public String getSchemeStatus() {
        return schemeStatus;
    }

    public void setSchemeStatus(String schemeStatus) {
        this.schemeStatus = schemeStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTenderDesc() {
        return tenderDesc;
    }

    public void setTenderDesc(String tenderDesc) {
        this.tenderDesc = tenderDesc;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPresaleBegin() {
        return presaleBegin;
    }

    public void setPresaleBegin(Date presaleBegin) {
        this.presaleBegin = presaleBegin;
    }

    public Date getPresaleEnd() {
        return presaleEnd;
    }

    public void setPresaleEnd(Date presaleEnd) {
        this.presaleEnd = presaleEnd;
    }

    public BigDecimal getMinApr() {
        return minApr;
    }

    public void setMinApr(BigDecimal minApr) {
        this.minApr = minApr;
    }

    public BigDecimal getMaxApr() {
        return maxApr;
    }

    public void setMaxApr(BigDecimal maxApr) {
        this.maxApr = maxApr;
    }

    public Integer getBlevel() {
        return blevel;
    }

    public void setBlevel(Integer blevel) {
        this.blevel = blevel;
    }

    public Long getCloseterm() {
        return closeterm;
    }

    public void setCloseterm(Long closeterm) {
        this.closeterm = closeterm;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public BigDecimal getRemacount() {
        return remacount;
    }

    public void setRemacount(BigDecimal remacount) {
        this.remacount = remacount;
    }

    public BigDecimal getLeastamount() {
        return leastamount;
    }

    public void setLeastamount(BigDecimal leastamount) {
        this.leastamount = leastamount;
    }

    public BigDecimal getMostamount() {
        return mostamount;
    }

    public void setMostamount(BigDecimal mostamount) {
        this.mostamount = mostamount;
    }

    public BigDecimal getStep() {
        return step;
    }

    public void setStep(BigDecimal step) {
        this.step = step;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getEarnestPercent() {
        return earnestPercent;
    }

    public void setEarnestPercent(BigDecimal earnestPercent) {
        this.earnestPercent = earnestPercent;
    }

    public BigDecimal getEarnestMax() {
        return earnestMax;
    }

    public void setEarnestMax(BigDecimal earnestMax) {
        this.earnestMax = earnestMax;
    }

    public Date getPresalePayEnd() {
        return presalePayEnd;
    }

    public void setPresalePayEnd(Date presalePayEnd) {
        this.presalePayEnd = presalePayEnd;
    }

    public Date getOpensaleBegin() {
        return opensaleBegin;
    }

    public void setOpensaleBegin(Date opensaleBegin) {
        this.opensaleBegin = opensaleBegin;
    }

    public Date getOpensaleEnd() {
        return opensaleEnd;
    }

    public void setOpensaleEnd(Date opensaleEnd) {
        this.opensaleEnd = opensaleEnd;
    }

    public BigDecimal getForfeitpercent() {
        return forfeitpercent;
    }

    public void setForfeitpercent(BigDecimal forfeitpercent) {
        this.forfeitpercent = forfeitpercent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getLastModify() {
        return lastModify;
    }

    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }
}
