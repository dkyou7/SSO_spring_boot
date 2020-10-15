package com.ktnet.testClient1.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class Res1Application {

    @Value("${services.auth}")
    private String authService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public FilterRegistrationBean jwtFilter() {
        logger.info("[testRes1 서버] Res1Application class ================= 필터를 처음 등록합니다.");
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.setInitParameters(Collections.singletonMap("services.auth", authService));
        registrationBean.addUrlPatterns("/test");

        return registrationBean;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Res1Application.class, args);
    }
}

