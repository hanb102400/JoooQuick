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



    public static <T> Response<T> getDefaultSuccessResponse() {
        Response<T> response = new Response<T>();
        response.setCode(Response.SUCCESS_CODE);
        response.setMessage(Response.SUCCESS_MSG);
        return response;
    }

    public static <T> Response<T> getDefaultSuccessResponse(String msg) {
        Response<T> response = getDefaultSuccessResponse();
        response.setMessage(msg);
        return response;
    }

    public static <T> Response<T> getSuccessResponse(T data) {
        Response<T> response = new Response<T>();
        response.setData(data);
        response.setCode(Response.SUCCESS_CODE);
        response.setMessage(Response.SUCCESS_MSG);
        return response;
    }

    public static <T> Response<T> getDefaultFailureResponse() {
        Response<T> response = new Response<T>();
        response.setCode(Response.FAILURE_CODE);
        response.setMessage(Response.FAILURE_MSG);
        return response;
    }

    public static <T> Response<T> getDefaultFailureResponse(String msg) {
        Response<T> response = getDefaultFailureResponse();
        response.setMessage(msg);
        return response;
    }

    public static <T> Response<T> getDefaultFailureResponse(int code, String msg) {
        Response<T> response = getDefaultFailureResponse();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }

}
