package com.shawn.admin.config;

import com.shawn.admin.exeption.UserCode;
import com.shawn.auth.filter.LoginFilter;
import com.shawn.auth.service.Md5PasswordEncoder;
import com.shawn.auth.service.MyUserDetailsService;
import com.shawn.jooo.framework.utils.JSONUtils;
import org.apache.http.HttpStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private LoginFilter loginFilter;

    /**
     * 密码加密类
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    /**
     * 设置用户授权校验类
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }



    /**
     * 登录页面和拦截配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //loginFilter.setAuthenticationManager(new ProviderManager(authenticationProvider));
        loginFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());

        http.authorizeRequests(authorize -> authorize
                //设置访问允许的路径
                .antMatchers("/error/*").permitAll()
                //.antMatchers("/user/**").hasRole("USER")
                //而其他的请求都需要认证
                .anyRequest().authenticated()
        )
                //修改Spring Security默认的登陆界面
                //.formLogin(Customizer.withDefaults())
                .formLogin(
                        formLogin -> formLogin.loginPage("/login")
                                .loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password")
                                .successHandler(new MyAuthenticationSuccessHandler())
                                .failureHandler(new MyAuthenticationFailureHandler()).permitAll()
                )
                //csrf
                .csrf(csrf -> csrf.disable())
                //登出
                .logout(logout -> logout.logoutUrl("/logout").permitAll())
                //替换默认的账号密码验证类
                .addFilterBefore(loginFilter,UsernamePasswordAuthenticationFilter.class)
                //未认证不走重定向，进行错误处理
                .exceptionHandling(handing -> handing.authenticationEntryPoint(new MyAuthenticationEntryPoint()));

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 入口重定向。未登录时返回此内容
     */
    static class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            Map msg = new HashMap<>();
            msg.put("code", 4001);
            msg.put("isLogin", false);
            msg.put("msg", "系统未授权");
            PrintWriter out = response.getWriter();
            out.write(JSONUtils.toJSONString(msg));
        }
    }

    /**
     * 登录成功返回
     */
    static class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            if (acceptJson(request)) {
                response.setContentType("application/json;charset=utf-8");
                Map msg = new HashMap<>();
                msg.put("code", 0);
                msg.put("isLogin", true);
                msg.put("msg", "授权通过");
                PrintWriter out = response.getWriter();
                out.write(JSONUtils.toJSONString(msg));
            }
        }
    }

    /**
     * 登录失败返回
     */
    static class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            if (acceptJson(request)) {
                response.setContentType("application/json;charset=utf-8");
                Map msg = new HashMap<>();
                msg.put("code", UserCode.LOGIN_FAILED.getCode());
                msg.put("isLogin", false);
                msg.put("msg", "授权失败");
                PrintWriter out = response.getWriter();
                out.write(JSONUtils.toJSONString(msg));
            }
        }
    }

    static boolean acceptJson(HttpServletRequest request) {
        if (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return true;
        }
        if (request.getHeader("Accept") != null && request.getHeader("Accept").contains(MediaType.APPLICATION_JSON_VALUE)) {
            return true;
        }
        return false;
    }

}