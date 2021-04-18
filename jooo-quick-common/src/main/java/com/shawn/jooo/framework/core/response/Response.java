package com.shawn.jooo.framework.core.response;

import java.io.Serializable;


/**
 * Response返回结果封装
 *
 * @author shawn
 */
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int SUCCESS_CODE = 0;

    public static final int FAILURE_CODE = 1000;

    public static final int ERROR_CODE = 9999;

    public static final String SUCCESS_MSG = "success";

    public static final String FAILURE_MSG = "failed";

    public static final String ERROR_MSG = "error";


    /**
     * 返回码：0代表成功
     */
    private int code;

    /**
     * 返回提示
     */
    private String message;

    /**
     * 消息体
     */
    private T data;

    public Response() {

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
