package com.ktnet.testRes1.oauth2;

import com.google.gson.Gson;
import com.ktnet.testRes1.login.LoginForm;
import com.ktnet.testRes1.login.LoginService;
import com.ktnet.testRes1.sso.SSOService;
import com.ktnet.testRes1.user.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @Value("${oauth2.clientId}")
    private String clientId;

    @Value("${oauth2.clientSecret}")
    private String secret;

    @Value("${oauth2.callback}")
    private String callback;

    @Value("${oauth2.grantType}")
    private String grantType;

    @Value("${oauth2.token}")
    private String tokenUrl;

    private final Gson gson;

    private final RestTemplate restTemplate;
    private final OAuth2Service oAuth2Service;
    private final SSOService ssoService;
    private final LoginService loginService;

    @PostMapping("/login/Login_create.do")
    public String createLogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes,
                              LoginForm loginForm, Model model, SessionStatus sessionStatus){
        // 1. 아이디 대문자 처리
        loginForm.setUsername(loginForm.getUsertype().toUpperCase());

        // 2. 아이디 저장버튼 클릭했다면 쿠키를 아이디에 저장하자.
        loginService.insert_id_to_cookie(request,response,loginForm);

        // 3. 통합회원인지 아니면 SSO 요청을 보내야 하는지
        if("USER".equals(loginForm.getUsertype())){
            // 인증서 기반 로그인인가? 아이디 패스워드 기반 로그인인가
            loginService.isCertMember(loginForm);

            // 4. ID / PW 비교
            User loginedUser = loginService.login(loginForm);
            if(loginedUser == null){
                attributes.addFlashAttribute("message","정확한 ID/PW를 입력해주세요.");
                return "redirect:/";
            }

            // 5. 휴면계정체크
            if(loginedUser.isDormant()){
                // TODO : 휴면 계정인데 로그인 한 경우이므로, 로직 분석해서 휴면 해제하도록 하자.
            }
            // 6. 미납업체체크

        }else if("NOT_USER".equals(loginForm.getUsertype())){
                // 통합회원이 아닌경우, TradeSign으로 요청을 보낸다.
        }
        return null;
    }

//    @GetMapping("/common/login/Login_sso.do")
//    public String createSSO(HttpServletRequest request, HttpServletResponse, Map<String, Object> commandMap, Model model){
//        ssoService.createSSO(request);
//        return null;
//    }

    @GetMapping("/login")
    public String KAS_login(HttpServletRequest request){
        // ssoprincipal session이 있지만, ss 에서는 로그인이 안되었을 경우
        if(oAuth2Service.isLoginSSO(request)){
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null){
                // sso에도 로그인 요청을 보내주자.
                return "redirect:/common/login/Login_sso.do";
            }
        }
        return "redirect:/";
    }

    @GetMapping(value = "/callback")
    public String callbackSocial(@RequestParam String code) {

        String credentials = clientId + ":" + secret;
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", grantType);
        params.add("redirect_uri", callback);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            OAuthToken oAuthToken = gson.fromJson(response.getBody(), OAuthToken.class);
            String access_token = oAuthToken.getAccess_token();
            return "redirect:/v1/success?access_token="+ access_token;
        }
        return null;
    }

    @GetMapping(value = "/token/refresh")
    public OAuthToken refreshToken(@RequestParam String refreshToken) {

        String credentials = clientId + ":" + secret;
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("refresh_token", refreshToken);
        params.add("grant_type", "refresh_token");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/oauth/token", request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), OAuthToken.class);
        }
        return null;
    }
}