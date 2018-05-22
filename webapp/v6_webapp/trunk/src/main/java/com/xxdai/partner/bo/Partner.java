package com.xxdai.partner.bo;

import java.util.Date;

/**
 * Created by chaihuangqi on 2015/10/14.
 */
public class Partner {
    private Long partnerId;

    private String partnerName;

    private String partnerAuth;

    private Long salt;

    private String remark;

    private Integer type;

    private Date createDate;

    private  Long creator;

    private Date modifiedDate;

    private Long modifer;

    private Integer status;

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerAuth() {
        return partnerAuth;
    }

    public void setPartnerAuth(String partnerAuth) {
        this.partnerAuth = partnerAuth;
    }

    public Long getSalt() {
        return salt;
    }

    public void setSalt(Long salt) {
        this.salt = salt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifer() {
        return modifer;
    }

    public void setModifer(Long modifer) {
        this.modifer = modifer;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
