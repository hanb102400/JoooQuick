package com.shawn;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@MapperScan("com.shawn.**.dao")
@ComponentScan("com.shawn")
@EnableTransactionManagement
@SpringBootApplication
public class JoooAdminApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JoooAdminApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(JoooAdminApplication.class, args);
    }

}