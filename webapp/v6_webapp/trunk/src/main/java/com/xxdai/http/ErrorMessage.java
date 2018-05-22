package com.xxdai.http;

/**
 * Errors model.
 */
public class ErrorMessage extends Message {

    public ErrorMessage(String message) {
        super.code = MessageStatus.ERROR.getStatus();
        super.message = message;
        super.data = new Object();
    }

    public ErrorMessage(int code) {
        super.code = code;
        super.message = "";
        super.data = new Object();
    }

    public ErrorMessage(Object data) {
        super.code = MessageStatus.ERROR.getStatus();
        super.message = "error";
        super.data = data;
    }

    public ErrorMessage(int code, Object data) {
        super.code = code;
        super.data = data;
    }

    public ErrorMessage(String message, int code) {
        super.code = code;
        super.message = message;
        super.data = new Object();
    }

    public ErrorMessage(MessageStatus status) {
        super.code = status.getStatus();
        super.message = status.getDesc();
        super.data = new Object();
    }
}
