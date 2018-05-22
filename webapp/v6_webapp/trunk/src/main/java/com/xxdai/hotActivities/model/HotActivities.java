package com.xxdai.hotActivities.model;

import java.util.Date;

/**
 * Created by chaihuangqi on 2015/5/26.
 */
public class HotActivities{
    private Integer activityId;
    private String picPath;
    private String topic;
    private Date beginDate;
    private Date endDate;
    private String jumpUrl;
    private String shareUrl;
    private String sharePic;
    private String shareTopic;
    private String status;
    private Integer porder;
    private Integer platform;
    private Date createDate;
    private String createIp;
    private Integer creator;
    private Date modifyDate;
    private String modifyIp;
    private Integer modifier;

    public HotActivities() {

    }

    public HotActivities(String picPath, String topic, Date beginDate, Date endDate, String jumpUrl, String shareUrl, String sharePic, String shareTopic, String status, Integer porder, Integer platform, Date createDate, String createIp, Integer creator) {
        this.picPath = picPath;
        this.topic = topic;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.jumpUrl = jumpUrl;
        this.shareUrl = shareUrl;
        this.sharePic = sharePic;
        this.shareTopic = shareTopic;
        this.status = status;
        this.porder = porder;
        this.platform = platform;
        this.createDate = createDate;
        this.createIp = createIp;
        this.creator = creator;
    }

    public HotActivities(Integer activityId, String picPath, String topic, Date beginDate, Date endDate, String jumpUrl, String shareUrl, String sharePic, String shareTopic, String status, Integer porder, Integer platform, Date createDate, String createIp, Integer creator, Date modifyDate, String modifyIp, Integer modifier) {
        this.activityId = activityId;
        this.picPath = picPath;
        this.topic = topic;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.jumpUrl = jumpUrl;
        this.shareUrl = shareUrl;
        this.sharePic = sharePic;
        this.shareTopic = shareTopic;
        this.status = status;
        this.porder = porder;
        this.platform = platform;
        this.createDate = createDate;
        this.createIp = createIp;
        this.creator = creator;
        this.modifyDate = modifyDate;
        this.modifyIp = modifyIp;
        this.modifier = modifier;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getShareTopic() {
        return shareTopic;
    }

    public void setShareTopic(String shareTopic) {
        this.shareTopic = shareTopic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPorder() {
        return porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
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

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyIp() {
        return modifyIp;
    }

    public void setModifyIp(String modifyIp) {
        this.modifyIp = modifyIp;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }
}
