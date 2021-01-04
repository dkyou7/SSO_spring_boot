package com.ktnet.auth_server.sso;

import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SsoController {

    private final UserService userService;

    @PostMapping("/test")
    public String apiTest(@RequestBody String testMsg){
        return "api test success";
    }

    @PostMapping("/isSSO")
    public String findUser(@RequestBody String email){
        log.info(email);
        User byUid = userService.findByUid(email);
        return byUid.getName();
    }
    @PostMapping("/isSSO2")
    public String findUser2(@RequestBody String email){
        log.info(email);
        User byUid = userService.findByUid(email);
        if(byUid.isLogin()){
            return "Y";
        }else{
            return "N";
        }
    }
}
