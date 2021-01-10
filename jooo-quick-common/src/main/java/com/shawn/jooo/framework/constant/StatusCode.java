package com.shawn.jooo.framework.constant;

public enum StatusCode {

    ENABLE(0, "正常"),
    DISABLE(1, "停用"),
    PUBLISH(2, "发布"),
    DELETED(-1, "刪除");

    private int code;

    private String info;

    StatusCode(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
