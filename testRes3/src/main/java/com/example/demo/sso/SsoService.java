package com.example.demo.sso;

import com.example.demo.account.Account;
import com.example.demo.account.AccountRepository;
import com.example.demo.account.SignUpForm;
import com.example.demo.property.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SsoService {
    private final AppProperties appProperties;
    private final AccountRepository accountRepository;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8081/api/v1")
            .build();

    public String getTestInfo(){
        String request = "request";

        return webClient.post().uri("/test")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public String isSSO(String sessionId){
        // WebClient webClient = WebClient.builder().baseUrl(appProperties.getHost()).build();
        String result = webClient.post().uri("/isSSO")
                .bodyValue(sessionId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        if(result==null){
            return null;
        }
        return result+"님, 안녕하세요";
    }

    /**
     * 로그인 되어있다면 Y, 안되어있다면 N
     * @param email
     * @return
     */
    public boolean isSSO2(String email){
        //WebClient webClient = WebClient.builder().baseUrl(appProperties.getHost()).build();
        String result = webClient.post().uri("/isLogin")
                .bodyValue(email)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info(result);
        if("Y".equals(result)){
            return true;
        }
        return false;
    }

    /**
     * SSO 로그인 request
     * 인증서버에게 로그인 요청 전송
     * @param account
     */
    public void ssoLogin(Account account) {
        //WebClient webClient = WebClient.builder().baseUrl(appProperties.getHost()).build();
        String result = webClient.post().uri("/login")
                .bodyValue(account.getUsername())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info(result);
    }

    /**
     * SSO 로그아웃 request
     * 인증서버 VID에게 로그아웃 요청 전송
     * @param account
     */
    public void ssoLogout(Account account) {
        //WebClient webClient = WebClient.builder().baseUrl(appProperties.getHost()).build();
        String result = webClient.post().uri("/logout")
                .bodyValue(account.getUsername())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info(result);
    }

    /**
     * 인증서버로 federation 보내기
     * @param account
     */
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

    public void ssoSignUp(Long uid) {
        Account byId = accountRepository.findById(uid).orElseThrow(EntityNotFoundException::new);
        try{
            String result = webClient
                    .post()
                    .uri("/signUp")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(byId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info(result);
        }catch (Exception e){
            log.info("ssoSignUp error" + e);
        }
    }

    public String tokenCheck(String testClient3) {
        try{
            String result = webClient.post().uri("/tokenCheck")
                    .bodyValue(testClient3)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info(result);
            return result;
        }catch (Exception e){
            log.info("tokenCheck error" + e);
        }
        return null;
    }
}