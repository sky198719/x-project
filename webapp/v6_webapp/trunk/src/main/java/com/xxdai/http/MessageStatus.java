package com.xxdai.http;

/**
 * the status of message.
 */
public enum MessageStatus {

    SUCCESS(200000, "操作成功"),
    ERROR_PARAM(200400, "参数错误"),
    NO_PERMISSION(200403, "没有权限"),
    DATA_NOT_EXIST(200406, "数据不存在"),
    DATA_ERROR(200407, "数据错误或不匹配"),
    METHOD_NOT_ALLOW(200600, "请求方法不支持"),
    OPERATION_REPET(200602, "重复操作"),
    ERROR(100500, "未知异常"),
    NETWORK_ERROR(100600, "网络异常"),
    DATABASE_ERROR(100700, "数据库操作异常"),
    SERVICE_STOPED(100900, "服务已关闭");

    private int status;

    private String desc;

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    private MessageStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}
