package com.example.demo.sso;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SsoControllerTest {

    @Autowired
    WebClient.Builder builder;

    WebClient webClient;

    @BeforeEach
    public void setUp(){
        webClient = builder.baseUrl("http://localhost:8081/api/v1").build();
    }

    @Test
    @DisplayName("웹클라이언트_GET_테스트")
    public void test1() {
        String result = webClient.get()
                .uri("/test")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        assertThat(result).isEqualTo("api get test success");
    }
    @Test
    @DisplayName("웹클라이언트_GET_테스트2")
    public void test2(){
        WebClient webClient = WebClientSupporter.createWebClient();
        String result = webClient.method(HttpMethod.GET)
                .uri(URI.create("http://localhost:8081/api/v1/test"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        assertThat(result).isEqualTo("api get test success");
    }
    @Test
    @DisplayName("웹클라이언트_GET_테스트3")
    public void test3(){
        WebClient webClient = WebClientSupporter.createWebClientWithBuilder();
        String result = webClient.get()
                .uri("/test")
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 값을 바로 확인하기 위해 block(sync)

        assertThat(result).isEqualTo("api get test success");
    }

    @Test
    public void retrieve() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8081/api/v1");
        Mono<String> result = webClient.get()
                .uri("/test")
                .retrieve()
                .bodyToMono(String.class) // MONO 반환 --> Signal 한번, Next한번
                .doOnNext(data -> System.out.println("next: " + data))
                .doOnError(Throwable::printStackTrace);

        result.subscribe(data -> System.out.println("Subcribe 시작"));

        Thread.sleep(3000);
    }
    @Test
    public void retrieve2() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8081/api/v1");
        Mono<String> result = webClient.get()
                .uri("/test")
                .retrieve()
                .bodyToMono(String.class) // MONO 반환 --> Signal 한번, Next한번
                .doOnNext(data -> System.out.println("next: " + data))
                .doOnError(Throwable::printStackTrace);

        String block = result.block();

        System.out.println("block: "+ block);
    }
    @Test
    public void post_테스트1(){
        WebClient webClient = WebClient.create("http://localhost:8081/api/v1");
        Article article = Article.builder()
                .id(1L)
                .contents("JAVABOM")
                .build();
        Mono<Article> mono = Mono.just(article); // Mono.just로 이미 존재하는 article 객체를 감쌈

        Article result = webClient.post()
                .uri("/article")
                .contentType(MediaType.APPLICATION_JSON)
                .body(mono, Article.class)
                .retrieve()
                .bodyToMono(Article.class)
                .block();
        System.out.println("result: "+ result);
    }
}