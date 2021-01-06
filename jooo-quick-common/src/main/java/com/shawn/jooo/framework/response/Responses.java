package com.shawn.jooo.framework.response;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * Responses工具类
 *
 * @author shawn
 */
public class Responses {

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return sra.getResponse();
    }


    public static Response success() {
        Response response = new Response();
        response.setCode(Response.SUCCESS_CODE);
        response.setMessage(Response.SUCCESS_MSG);
        return response;
    }

    public static Response success(String msg) {
        Response response = success();
        response.setMessage(msg);
        return response;
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setCode(Response.SUCCESS_CODE);
        response.setMessage(Response.SUCCESS_MSG);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> success(int code, String msg) {
        Response<T> response = success();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }

    public static <T> Response<T> success(String msg,T data) {
        Response<T> response = success();
        response.setMessage(msg);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> success(int code,String msg,T data) {
        Response<T> response = success();
        response.setCode(code);
        response.setMessage(msg);
        response.setData(data);
        return response;
    }

    public static Response fail() {
        Response response = new Response();
        response.setCode(Response.FAILURE_CODE);
        response.setMessage(Response.FAILURE_MSG);
        return response;
    }

    public static Response fail(String msg) {
        Response response = fail();
        response.setMessage(msg);
        return response;
    }

    public static <T> Response<T> fail(int code, String msg) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }

    public static <T> Response<T> fail(int code, String msg, T data) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMessage(msg);
        response.setData(data);
        return response;
    }

}
