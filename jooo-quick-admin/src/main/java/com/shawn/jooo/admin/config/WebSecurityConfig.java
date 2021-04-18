package com.shawn.jooo.admin.config;

import com.shawn.jooo.admin.auth.filter.LoginFilter;
import com.shawn.jooo.admin.auth.handler.MyAuthenticationEntryPoint;
import com.shawn.jooo.admin.auth.handler.MyAuthenticationFailureHandler;
import com.shawn.jooo.admin.auth.handler.MyAuthenticationSuccessHandler;
import com.shawn.jooo.admin.auth.handler.MyLogoutSuccessHandler;
import com.shawn.jooo.admin.auth.service.Md5PasswordEncoder;
import com.shawn.jooo.admin.auth.service.MyUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@EnableWebSecurity
@ConditionalOnProperty(prefix="cas", name = "enable", havingValue = "false", matchIfMissing = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private Md5PasswordEncoder md5PasswordEncoder;

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private LoginFilter loginFilter;

    /**
     * AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 设置用户授权校验类
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(md5PasswordEncoder);
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
                //cors
                //.cors(cors -> { })
                //csrf
                //.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .csrf(csrf -> csrf.disable())
                //登出
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(new MyLogoutSuccessHandler()).permitAll())
                //替换默认的账号密码验证类
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
                //未认证不走重定向，进行错误处理
                .exceptionHandling(handing -> handing.authenticationEntryPoint(new MyAuthenticationEntryPoint()));

    }


}