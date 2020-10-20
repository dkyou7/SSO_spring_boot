package com.ktnet.testClient2.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class Res2Application {

    @Value("${services.auth}")
    private String authService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public FilterRegistrationBean jwtFilter() {
        logger.info("[testRes2 서버] Res1Application class ================= 필터를 처음 등록합니다.");
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.setInitParameters(Collections.singletonMap("services.auth", authService));
        registrationBean.addUrlPatterns("/go_auth");

        return registrationBean;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Res2Application.class, args);
    }
}

