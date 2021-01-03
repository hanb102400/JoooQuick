package com.shawn.auth.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.*;


//@Component
public class MyFilterSecurityInterceptor extends FilterSecurityInterceptor implements Filter {

    /**
     * 拦截器，分别会
     * 1. 调用 MyAccessDecisionManager 校验登录，
     * 2. MyInvocationSecurityMetadataSource 获取权限
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }

    @Autowired
    public void setSecurityMetadataSource(MyFilterSecurityMetadataSource myFilterSecurityMetadataSource) {
        super.setSecurityMetadataSource(myFilterSecurityMetadataSource);
    }

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Component
    public class MyAccessDecisionManager implements AccessDecisionManager {

        @Override
        public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
            if (configAttributes == null || configAttributes.size() == 0) {
                throw new AccessDeniedException("permission denied");
            }
            //遍历基于URL获取的权限信息和用户自身的角色信息进行对比.
            for (ConfigAttribute configAttribute : configAttributes) {
                String needRole = configAttribute.getAttribute();
                System.out.println("decide,needRole:" + needRole + ",authentication=" + authentication);
                //authentication 为CustomUserDetailService中添加的权限信息.
                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    if (authority.getAuthority().equals(needRole)) {
                        return;
                    }
                }
            }
            throw new AccessDeniedException("permission denied");
        }

        @Override
        public boolean supports(ConfigAttribute configAttribute) {
            return true;
        }

        @Override
        public boolean supports(Class<?> aClass) {
            return true;
        }

    }

    @Component
    public class MyFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

        public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
            FilterInvocation fi = (FilterInvocation) object;
            String url = fi.getRequestUrl();
            String httpMethod = fi.getRequest().getMethod();
            List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();
            // Lookup your database (or other source) using this information and populate the
            // list of attributes
            return attributes;
        }

        public Collection<ConfigAttribute> getAllConfigAttributes() {
            return null;
        }

        public boolean supports(Class<?> clazz) {
            return FilterInvocation.class.isAssignableFrom(clazz);
        }
    }
}
