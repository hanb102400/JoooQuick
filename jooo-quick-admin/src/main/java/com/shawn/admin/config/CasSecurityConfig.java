package com.shawn.admin.config;

import com.shawn.admin.auth.handler.MyLogoutSuccessHandler;
import com.shawn.admin.auth.service.MyUserDetailsService;
import com.shawn.admin.cas.handler.AjaxCasAuthenticationEntryPoint;
import com.shawn.admin.cas.handler.AjaxLogoutSuccessHandler;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.annotation.Resource;

@EnableWebSecurity
@ConditionalOnProperty(prefix = "cas", name = "enable", havingValue = "true")
public class CasSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private Environment env;

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

    @Value("${cas.server-url-prefix}")
    private String CAS_SERVER_URL_PREFIX;

    @Value("${cas.server-login-url}")
    private String CAS_SERVER_LOGIN_URL;

    @Value("${cas.server-logout-url}")
    private String CAS_SERVER_LOGOUT_URL;

    @Value("${cas.client-host-url:}")
    private String CAS_CLIENT_HOST_URL;

    @Value("${cas.client-success-url:/login/success}")
    private String CAS_CLIENT_SUCCESS_URL;

    @Value("${cas.client-login-path:/login/cas}")
    private String CAS_CLIENT_LOGIN_PATH;

    @Value("${cas.client-logout-path:/logout/cas}")
    private String CAS_CLIENT_LOGOUT_PATH;


    /**
     * 登录页面和拦截配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorize -> authorize
                //设置访问允许的路径
                .antMatchers("/error/*").permitAll()
                .antMatchers(CAS_CLIENT_LOGIN_PATH).permitAll()
                //.antMatchers("/user/**").hasRole("USER")
                //而其他的请求都需要认证
                .anyRequest().authenticated()
        )
                //修改Spring Security默认的登陆界面
                //.formLogin(Customizer.withDefaults())
                //.formLogin(
                //        formLogin -> formLogin.loginPage("/login")
                //                .loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password")
                //                .successHandler(new MyAuthenticationSuccessHandler())
                //                .failureHandler(new MyAuthenticationFailureHandler()).permitAll()
                //)
                //cors
                //.cors(cors -> { })
                //csrf
                //.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .csrf(csrf -> csrf.disable())
                //登出
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(new MyLogoutSuccessHandler()).permitAll())


                /** cas配置部分 **/
                //拦截处理
                .addFilter(casAuthenticationFilter())
                //替换默认的账号密码验证类
                .addFilterBefore(casLogoutFilter(), LogoutFilter.class)
                .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class)
                //未认证不走重定向，进行错误处理
                .exceptionHandling(handing -> handing.authenticationEntryPoint(casAuthenticationEntryPoint()));

    }


    /**
     * 设置用户授权校验类
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(casAuthenticationProvider());
    }


    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(CAS_CLIENT_HOST_URL + CAS_CLIENT_LOGIN_PATH);
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    @Bean
    public AjaxCasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        AjaxCasAuthenticationEntryPoint casAuthenticationEntryPoint = new AjaxCasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(CAS_SERVER_LOGIN_URL);
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }

    /**
     * 验证ticker，向cas服务器发送验证请求
     */
    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        Cas20ServiceTicketValidator cas20ServiceTicketValidator = new Cas20ServiceTicketValidator(CAS_SERVER_URL_PREFIX);
        cas20ServiceTicketValidator.setEncoding("UTF-8");
        return cas20ServiceTicketValidator;
    }

    @Bean
    public UserDetailsByNameServiceWrapper userDetailsByNameServiceWrapper() {
        UserDetailsByNameServiceWrapper userDetailsByNameServiceWrapper = new UserDetailsByNameServiceWrapper();
        userDetailsByNameServiceWrapper.setUserDetailsService(myUserDetailsService);
        return userDetailsByNameServiceWrapper;
    }


    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setAuthenticationUserDetailsService(userDetailsByNameServiceWrapper());
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
        casAuthenticationProvider.setKey("an_id_for_this_auth_provider_only");
        return casAuthenticationProvider;
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        casAuthenticationFilter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler(CAS_CLIENT_SUCCESS_URL));
        //casAuthenticationFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(CAS_CLIENT_FAILURE_URL));
        return casAuthenticationFilter;
    }


    @Bean
    public LogoutFilter casLogoutFilter() {
        String logoutUrl = CAS_SERVER_LOGOUT_URL + "?service=" + CAS_SERVER_LOGIN_URL;
        //拦截logout
        LogoutFilter logoutFilter = new LogoutFilter(new AjaxLogoutSuccessHandler(logoutUrl), new SecurityContextLogoutHandler());
        //与上面的url是映射关系，可配成其他的
        logoutFilter.setFilterProcessesUrl(CAS_CLIENT_LOGOUT_PATH);
        return logoutFilter;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }


}