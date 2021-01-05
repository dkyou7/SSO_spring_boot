package com.ktnet.testRes1.sso;

import com.ktnet.testRes1.oauth2.ParameterValidator;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
}
