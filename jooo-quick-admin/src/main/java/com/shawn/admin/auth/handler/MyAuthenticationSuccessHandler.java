package com.shawn.admin.auth.handler;

import com.shawn.jooo.framework.core.request.Requests;
import com.shawn.jooo.framework.core.response.Responses;
import com.shawn.jooo.framework.utils.JSONUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功返回处理
 *
 * @author  shawn
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        if (Requests.isAjaxRequest(request)) {
            Map msg = new HashMap<>();
            msg.put("code", 0);
            msg.put("isLogin", true);
            msg.put("msg", "授权通过");
            Responses.writeJson(response,JSONUtils.toJSONString(msg));
        }
    }



}