package com.ktnet.auth_server.sso;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SsoController {

    @PostMapping("/test")
    public String apiTest(@RequestBody String testMsg){
        return "api test success";
    }
}
