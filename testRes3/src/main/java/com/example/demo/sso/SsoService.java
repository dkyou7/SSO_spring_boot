package com.example.demo.sso;

import com.example.demo.account.Account;
import com.example.demo.property.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SsoService {
    private final AppProperties appProperties;

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
    public String isSSO(String email){
        // WebClient webClient = WebClient.builder().baseUrl(appProperties.getHost()).build();
        String result = webClient.post().uri("/isSSO")
                .bodyValue(email)
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
     * 인증서버 VID에게 로그인 요청 전송
     * @param account
     */
    public void ssoLogin(Account account) {
        //WebClient webClient = WebClient.builder().baseUrl(appProperties.getHost()).build();
        String result = webClient.post().uri("/login")
                .bodyValue(account.getVid())
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
                .bodyValue(account.getVid())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info(result);
    }
}