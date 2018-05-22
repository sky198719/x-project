package com.xxdai.seo.model;

import java.util.Date;


public class SeoLinkExchange implements java.io.Serializable {
    private Integer id;      //id
    private String picAddr;  //图片存储地址
    private String url;      //链接跳转地址
    private String hintText; //hint文本
    private String anchor;     //锚链接文本
    private String seoText;  //seo文本
    private Integer status;     //显示状态（0：不显示，1：显示，2删除）
    private Integer porder;     //显示顺序
    private Integer creator; //创建人ID
    private Date createTime; //创建时间
    private Integer modifier;//最后修改人ID
    private Date modifyTime; //最后修改时间
    private String cateType; //栏目类型：用于显示在第几类页面：1：一类，2：二类：3：三类，9：其他

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicAddr() {
        return picAddr;
    }

    public void setPicAddr(String picAddr) {
        this.picAddr = picAddr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public String getSeoText() {
        return seoText;
    }

    public void setSeoText(String seoText) {
        this.seoText = seoText;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPorder() {
        return porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCateType() {
        return cateType;
    }

    public void setCateType(String cateType) {
        this.cateType = cateType;
    }


}