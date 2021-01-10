package com.shawn.admin.cas.handler;

import com.shawn.jooo.framework.core.request.Requests;
import com.shawn.jooo.framework.core.response.Responses;
import com.shawn.jooo.framework.utils.JSONUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    private String logoutUrl;

    public AjaxLogoutSuccessHandler(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Assert.notNull(request, "HttpServletRequest required");
        if (Requests.isAjaxRequest(request)) {

            Map msg = new HashMap<>();
            msg.put("code", 0);
            msg.put("isLogin", false);
            msg.put("logoutUrl", logoutUrl);
            msg.put("msg", "退出成功");
            try {
                Responses.writeJson(response, JSONUtils.toJSONString(msg));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                response.sendRedirect(logoutUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
