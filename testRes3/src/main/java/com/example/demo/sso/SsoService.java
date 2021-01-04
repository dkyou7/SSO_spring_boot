package com.example.demo.sso;

import com.example.demo.property.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.print.attribute.standard.Media;
import javax.transaction.Transactional;
import java.net.URL;
import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SsoService {
    private final AppProperties appProperties;

    private WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8081")
            .build();

    public String getTestInfo(){
        String request = "request";
        String block = webClient.post().uri("/api/test")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return block;
    }


    @Autowired
    WebClient.Builder webClientBuilder;

    public String getSSOInfo(){
        WebClient webClient = webClientBuilder.baseUrl(appProperties.getHost()).build();

        Mono<String> helloResult = webClient.get().uri("/api/v1/hello")
                .retrieve()
                .bodyToMono(String.class);
        helloResult.subscribe(log::info);

        Mono<String> isSSO = webClient
                .post()
                .uri("/api/v1/isSSO")
                .body(Mono.just("admin@naver.com"),String.class)
                .retrieve()
                .bodyToMono(String.class);
        isSSO.subscribe(log::info);
        return isSSO.block();
    }
}