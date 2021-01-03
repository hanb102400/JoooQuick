package com.shawn.jooo.framework.mybatis.condition;

public enum Direction {
    DESC("desc"),
    ASC("asc");
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    Direction(String code) {
        this.code = code;
    }
}