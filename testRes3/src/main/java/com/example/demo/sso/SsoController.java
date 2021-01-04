package com.example.demo.sso;

import com.example.demo.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SsoController {

    private final SsoService ssoService;

    @GetMapping("/api/test")
    public String getSsoInfo(){
       return ssoService.getTestInfo();
    }
}
