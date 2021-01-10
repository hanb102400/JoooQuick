package com.shawn.admin.cas.handler;

import com.shawn.jooo.framework.core.request.Requests;
import com.shawn.jooo.framework.core.response.Responses;
import com.shawn.jooo.framework.utils.JSONUtils;
import org.apache.http.HttpStatus;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AjaxCasAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException, ServletException {

        String urlEncodedService = this.createServiceUrl(request, response);
        String redirectUrl = this.createRedirectUrl(urlEncodedService);

        if (Requests.isAjaxRequest(request)) {
            Map msg = new HashMap<>();

            msg.put("code", 4001);
            msg.put("isLogin", false);
            msg.put("loginUrl", redirectUrl);
            msg.put("msg", "重定向CAS登录");
            Responses.writeJson(response, JSONUtils.toJSONString(msg),HttpStatus.SC_UNAUTHORIZED);
        } else {
            this.preCommence(request, response);
            response.sendRedirect(redirectUrl);
        }

    }


    private ServiceProperties serviceProperties;
    private String loginUrl;
    private boolean encodeServiceUrlWithSessionId = true;

    public AjaxCasAuthenticationEntryPoint() {
    }

    public void afterPropertiesSet() {
        Assert.hasLength(this.loginUrl, "loginUrl must be specified");
        Assert.notNull(this.serviceProperties, "serviceProperties must be specified");
        Assert.notNull(this.serviceProperties.getService(), "serviceProperties.getService() cannot be null.");
    }


    protected String createServiceUrl(HttpServletRequest request, HttpServletResponse response) {
        return CommonUtils.constructServiceUrl((HttpServletRequest)null, response, this.serviceProperties.getService(), (String)null, this.serviceProperties.getArtifactParameter(), this.encodeServiceUrlWithSessionId);
    }

    protected String createRedirectUrl(String serviceUrl) {
        return CommonUtils.constructRedirectUrl(this.loginUrl, this.serviceProperties.getServiceParameter(), serviceUrl, this.serviceProperties.isSendRenew(), false);
    }

    protected void preCommence(HttpServletRequest request, HttpServletResponse response) {
    }

    public final String getLoginUrl() {
        return this.loginUrl;
    }

    public final ServiceProperties getServiceProperties() {
        return this.serviceProperties;
    }

    public final void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public final void setServiceProperties(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public final void setEncodeServiceUrlWithSessionId(boolean encodeServiceUrlWithSessionId) {
        this.encodeServiceUrlWithSessionId = encodeServiceUrlWithSessionId;
    }

    protected boolean getEncodeServiceUrlWithSessionId() {
        return this.encodeServiceUrlWithSessionId;
    }
}
