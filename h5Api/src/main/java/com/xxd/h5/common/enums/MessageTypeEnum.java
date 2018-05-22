package com.xxd.h5.common.enums;

/**
 * 业务消息的类型.
 */
public enum  MessageTypeEnum {

    /**
     * 通知公告
     */
    MESSAGE_NOTICE(1, "通知公告"),

    /**
     * 系统消息
     */
    MESSAGE_SYSTEM(2, "系统消息");

    /**
     * 消息类型.
     */
    private int type;

    /**
     * 对类型的描述性信息.
     */
    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    MessageTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    MessageTypeEnum() {
    }
}
