package com.shawn.jooo.framework.base;

        import com.shawn.jooo.framework.core.response.Response;
        import com.shawn.jooo.framework.core.response.Responses;

/**
 * 基础Controller
 *
 * @author shawn
 */
public abstract class BaseController {


    public static Response success() {
        return Responses.success();
    }

    public static Response success(String msg) {
        return Responses.success();
    }

    public static Response success(int code, String msg) {
        return Responses.success();
    }

    public static <T> Response<T> success(T data) {
        return Responses.success();
    }

    public static <T> Response<T> success(String msg, T data) {
        return Responses.success();
    }

    public static <T> Response<T> success(int code, String msg, T data) {
        return Responses.success(code, msg, data);
    }

    public static Response fail() {
        return Responses.fail();
    }

    public static Response fail(String msg) {
        return Responses.fail(msg);
    }

    public static <T> Response<T> fail(int code, String msg) {
        return Responses.fail(code, msg);
    }

    public static <T> Response<T> fail(int code, String msg, T data) {
        return Responses.fail(code, msg, data);
    }

    public String redirect(String url) {
        return "redirect:" + url;
    }
}
