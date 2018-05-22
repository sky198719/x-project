package com.xxdai.redpacket.model;

import java.math.BigDecimal;
import java.util.Date;



/**
 *  entity provides the base persistence definition of the XxdRedenvelopeRecord entity. @author MyEclipse Persistence Tools
 */

public class RedenvelopeRecord  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int redEnvelopid;
     private String redCode;
     private BigDecimal faceValue;
     private Date validDate;
     private BigDecimal quota;
     private int userLimit;
     private String platForm;
     private String isOverlay;
     private String name;
     private String source;
     private String isGiven;
     private Long userId;
     private String status;
     private Long creator;
     private Date addTime;
     private String addIp;
     private Long lastModify;
     private Date updateTime;


    // Constructors

    /** default constructor */
    public RedenvelopeRecord() {
    }

	/** minimal constructor */
    public RedenvelopeRecord(String redCode, BigDecimal faceValue, Date validDate, BigDecimal quota, int userLimit, String platForm, String isOverlay, String name, String source, String isGiven, String status, Long creator, Date addTime, String addIp) {
        this.redCode = redCode;
        this.faceValue = faceValue;
        this.validDate = validDate;
        this.quota = quota;
        this.userLimit = userLimit;
        this.platForm = platForm;
        this.isOverlay = isOverlay;
        this.name = name;
        this.source = source;
        this.isGiven = isGiven;
        this.status = status;
        this.creator = creator;
        this.addTime = addTime;
        this.addIp = addIp;
    }
    
    /** full constructor */
    public RedenvelopeRecord(String redCode, BigDecimal faceValue, Date validDate, BigDecimal quota, int userLimit, String platForm, String isOverlay, String name, String source, String isGiven, Long userId, String status, Long creator, Date addTime, String addIp, Long lastModify, Date updateTime) {
        this.redCode = redCode;
        this.faceValue = faceValue;
        this.validDate = validDate;
        this.quota = quota;
        this.userLimit = userLimit;
        this.platForm = platForm;
        this.isOverlay = isOverlay;
        this.name = name;
        this.source = source;
        this.isGiven = isGiven;
        this.userId = userId;
        this.status = status;
        this.creator = creator;
        this.addTime = addTime;
        this.addIp = addIp;
        this.lastModify = lastModify;
        this.updateTime = updateTime;
    }

   
    // Property accessors

    public int getRedEnvelopid() {
        return this.redEnvelopid;
    }
   
    public void setRedEnvelopid(int redEnvelopid) {
        this.redEnvelopid = redEnvelopid;
    }
    public String getRedCode() {
        return this.redCode;
    }
    
    public void setRedCode(String redCode) {
        this.redCode = redCode;
    }
    public BigDecimal getFaceValue() {
        return this.faceValue;
    }
    
    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }
    public Date getValidDate() {
        return this.validDate;
    }
    
    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
    public BigDecimal getQuota() {
        return this.quota;
    }
    
    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }
    public int getUserLimit() {
        return this.userLimit;
    }
    
    public void setUserLimit(int userLimit) {
        this.userLimit = userLimit;
    }
    public String getPlatForm() {
        return this.platForm;
    }
    
    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }
    public String getIsOverlay() {
        return this.isOverlay;
    }
    
    public void setIsOverlay(String isOverlay) {
        this.isOverlay = isOverlay;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getSource() {
        return this.source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    public String getIsGiven() {
        return this.isGiven;
    }
    
    public void setIsGiven(String isGiven) {
        this.isGiven = isGiven;
    }
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getCreator() {
        return this.creator;
    }
    
    public void setCreator(Long creator) {
        this.creator = creator;
    }
    public Date getAddTime() {
        return this.addTime;
    }
    
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    public String getAddIp() {
        return this.addIp;
    }
    
    public void setAddIp(String addIp) {
        this.addIp = addIp;
    }
    public Long getLastModify() {
        return this.lastModify;
    }
    
    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }
    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
   








}