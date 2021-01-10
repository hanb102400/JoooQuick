package com.shawn.jooo.framework.constant;


public enum ErrorCode {
    SUCCESS(0, "SUCCESS"),
    FAILED(1000, "FAILED"),
    ERROR_BAD_TIME(1001, "时间校验失败"),
    ERROR_BAD_PARAM(1002, "参数错误"),
    ERROR_BAD_SIGN(1003, "签名校验失败"),
    ERROR_BAD_VERSION(1004, "版本错误");

    private int code;

    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
