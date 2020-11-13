package com.ktnet.testClient1.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Res1Application {

    @Value("${services.auth}")
    private String authService;

    @Value("${oauth.client_id}")
    private String clientId;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public FilterRegistrationBean jwtFilter() {
        logger.info("[testRes1 서버] Res1Application class ================= 필터를 처음 등록합니다.");
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        Map<String,String> temp = new HashMap<String, String>();
        temp.put("oauth.client_id",clientId);
        temp.put("services.auth",authService);
        registrationBean.setInitParameters(Collections.unmodifiableMap(temp));
        registrationBean.addUrlPatterns("/go_auth");

        return registrationBean;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Res1Application.class, args);
    }
}

