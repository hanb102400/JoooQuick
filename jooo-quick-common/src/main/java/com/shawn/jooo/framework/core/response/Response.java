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

    public static final int ERROR_CODE = 999;

    public static final String SUCCESS_MSG = "success";

    public static final String FAILURE_MSG = "failed";

    public static final String ERROR_MSG = "error";


    // 响应代码，成功：0
    private int code;

    // 业务失败提示
    private String message;

    // 返回对象
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
