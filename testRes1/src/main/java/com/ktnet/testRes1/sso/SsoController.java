package com.ktnet.testRes1.sso;

import com.ktnet.testRes1.account.Account;
import com.ktnet.testRes1.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SsoController {

    private final SSOService ssoService;
    private final AccountService accountService;

    @GetMapping("/test")
    public String getSsoInfo(){
       return ssoService.getTestInfo();
    }

    @GetMapping("/isSSO")
    public String isSSOTest(){
        return ssoService.isSSO("admin@naver.com");
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
