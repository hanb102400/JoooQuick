package com.shawn.jooo.api;


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
public class JoooApiApplication  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JoooApiApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(JoooApiApplication.class, args);
    }

}