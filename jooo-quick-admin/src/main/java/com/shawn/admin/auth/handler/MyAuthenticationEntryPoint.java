package com.shawn.admin.auth.handler;

import com.shawn.jooo.framework.core.request.Requests;
import com.shawn.jooo.framework.core.response.Responses;
import com.shawn.jooo.framework.utils.JSONUtils;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录入口拦截
 *
 * @author shawn
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException, ServletException {
        if (Requests.isAjaxRequest(request)) {
            Map msg = new HashMap<>();
            msg.put("code", 4001);
            msg.put("isLogin", false);
            msg.put("msg", "系统未授权");
            Responses.writeJson(response, JSONUtils.toJSONString(msg), HttpStatus.SC_UNAUTHORIZED);
        } else {
            response.sendRedirect("/login");
        }

    }
}