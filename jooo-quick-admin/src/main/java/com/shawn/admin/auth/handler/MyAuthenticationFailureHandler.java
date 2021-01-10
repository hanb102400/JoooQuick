package com.shawn.admin.auth.handler;

import com.shawn.admin.exeption.UserCode;
import com.shawn.jooo.framework.core.request.Requests;
import com.shawn.jooo.framework.core.response.Responses;
import com.shawn.jooo.framework.utils.JSONUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录失败返回处理
 *
 * @author  shawn
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException, ServletException {
        if (Requests.isAjaxRequest(request)) {
            Map msg = new HashMap<>();
            msg.put("code", UserCode.LOGIN_FAILED.getCode());
            msg.put("isLogin", false);
            msg.put("msg", "授权失败");
            Responses.writeJson(response,JSONUtils.toJSONString(msg));
        }
    }
}