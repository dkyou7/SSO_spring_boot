package com.ktnet.testClient2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Res2Application {

//    @Value("${services.auth}")
//    private String authService;
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Bean
//    public FilterRegistrationBean jwtFilter() {
//        logger.info("[testRes2 서버] Res1Application class ================= 필터를 처음 등록합니다.");
//        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new JwtFilter());
//        registrationBean.setInitParameters(Collections.singletonMap("services.auth", authService));
//        registrationBean.addUrlPatterns("/go_auth");
//
//        return registrationBean;
//    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Res2Application.class, args);
    }
}

