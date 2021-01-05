package com.ktnet.testRes1.sso;

import com.ktnet.testRes1.account.Account;
import com.ktnet.testRes1.oauth2.ParameterValidator;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SSOService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8081/api/v1")
            .build();

    /**
     * 1. missing input 존재하는지 여부 체크
     * 2. 세션이 존재하는지 여부 체크
     * @param request
     * @return SSORspData(code, message)
     */
    public SSORspData ssoGetLoginData(HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        SSORspData ssoRspData = new SSORspData();
        hashMap.put("request",request);
        String validatedRequest = ParameterValidator.validateInput(hashMap);

        // 1. missing input이 존재한다면
        if(validatedRequest != null) {
            return null;
        }
        // 2. 세션이 존재하는지 여부 체크
        String ssoPrincipal = (String) request.getSession().getAttribute("SSOPRINCIPAL");
        if(ssoPrincipal != null){
            ssoRspData.setCodeMessage(0,"SSO_SUCCESS");
        }else{
            ssoRspData.setCodeMessage(-1,"SSO_FAIL");
        }
        return ssoRspData;
    }

    public void createSSO(HttpServletRequest request) {
        SSORspData ssoRspData = this.ssoGetLoginData(request);

    }

    public void getUserInfo(String access_token) {
        String block = webClient.post().uri("/userInfo")
                .bodyValue(access_token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info(block);
    }
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
