package com.shawn.jooo.framework.core.request;

import com.shawn.jooo.framework.core.validate.Preconditions;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Requests工具类，请求参数校验
 *
 * @author shawn
 */
public class Requests {

    public static String checkParameter(String paramName) {
        String value = getParameter(paramName);
        return Preconditions.checkNotEmpty(value, new IllegalArgumentException());
    }

    public static String checkParameter(String paramName, @Nullable String errorMessage) {
        String value = getParameter(paramName);
        return Preconditions.checkNotEmpty(value, new IllegalArgumentException(errorMessage));
    }

    public static String checkParameter(String paramName, @Nullable RuntimeException ex) {
        String value = getParameter(paramName);
        return Preconditions.checkNotEmpty(value, new IllegalArgumentException(ex));
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return sra.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return sra.getResponse();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return true;
        }
        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest")) {
            return true;
        }
        return false;
    }
}
