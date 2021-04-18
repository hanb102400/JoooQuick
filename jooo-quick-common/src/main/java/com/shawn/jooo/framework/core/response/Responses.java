package com.shawn.jooo.framework.core.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Responses工具类
 *
 * @author shawn
 */
public class Responses {

    private static Logger logger = LoggerFactory.getLogger(Responses.class);

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return sra.getRequest();
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

    public static <T> Response<T> success(String msg, T data) {
        Response<T> response = success();
        response.setMessage(msg);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> success(int code, String msg, T data) {
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

    public static String writeJson(HttpServletResponse response, String json) throws IOException {
        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_JSON.getType());
        response.setCharacterEncoding("utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(json);
        return null;
    }

    public static String writeJson(HttpServletResponse response, String json, int status) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON.getType());
        response.setCharacterEncoding("utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(json);
        return null;
    }

}
