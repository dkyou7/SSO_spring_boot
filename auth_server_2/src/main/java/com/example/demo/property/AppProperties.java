package com.example.demo.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("sso")
public class AppProperties {
    private String clientId;
    private String clientSecret;
    private String callback;
    private String grantType;
    private String token;
    private String host;
}