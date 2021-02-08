package com.example.demo.sso;

import com.example.demo.account.Account;
import com.example.demo.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SsoController {

    private final SsoService ssoService;
    private final AccountService accountService;

    @GetMapping("/test")
    public String getSsoInfo(){
       return ssoService.getTestInfo();
    }

    @GetMapping("/isSSO")
    public String isSSOTest(){
        return ssoService.isSSO("testClient3");
    }

    @GetMapping("/isSSO2")
    public String isSSOLogin(){
        boolean authServerIsLogin = ssoService.isSSO2("admin@naver.com");
        if(authServerIsLogin){
            Account byVid = accountService.findByVid("admin@naver.com");
            if(byVid!=null){
                accountService.login(byVid);
                return "로그인 성공!";
            }
        }
        return "로그인 실패!";
    }
}
