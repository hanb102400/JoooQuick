package com.shawn.jooo.admin.auth.handler;

import com.shawn.jooo.framework.core.request.Requests;
import com.shawn.jooo.framework.core.response.Responses;
import com.shawn.jooo.framework.utils.JSONUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 登出成功返回处理
 *
 * @author  shawn
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        if (Requests.isAjaxRequest(request)) {
            //登出
            new SecurityContextLogoutHandler().logout(request,response,auth);

            Map msg = new HashMap<>();
            msg.put("code", 0);
            msg.put("isLogin", false);
            msg.put("msg", "退出成功");
            Responses.writeJson(response,JSONUtils.toJSONString(msg));
        }else {
            response.sendRedirect("/logout");
        }
    }
}
