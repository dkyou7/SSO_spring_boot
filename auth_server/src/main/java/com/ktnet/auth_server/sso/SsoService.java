package com.ktnet.auth_server.sso;

import com.ktnet.auth_server.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SsoService {

    public void federationRequest(Account account){
        WebClient webClient = WebClient.create("http://localhost:8081/api/v1");
        Mono<Account> mono = Mono.just(account); // Mono.just로 이미 존재하는 article 객체를 감쌈

        String result = webClient.post()
                .uri("/user/federation")
                .contentType(MediaType.APPLICATION_JSON)
                .body(mono, Account.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("result: "+ result);
    }
}
