package com.example.demo.sso;

import org.springframework.web.reactive.function.client.WebClient;

public class WebClientSupporter {
    public static WebClient createWebClient(){
        // reactor.netty.http.client.HttpClient의 기본 세팅을 따른다
        return WebClient.create();
    }

    public static WebClient createWebClient(final String url){
        // reactor.netty.http.client.HttpClient의 기본 세팅을 따른다
        return WebClient.create(url);
    }

    /**
     * 구체적인 옵션을 사용하고 싶다면 builder()를 사용하는 것이 적합함.
     */
    public static WebClient createWebClientWithBuilder(){
        return WebClient.builder()
                .baseUrl("http://localhost:8081/api/v1")
                .build();
    }
}
