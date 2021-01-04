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

    // 로그인 되어있는지 확인하는 로직
    @PostMapping("/isLogin")
    public String findUser2(@RequestBody String email){
        log.info(email);
        User byUid = userService.findByUid(email);
        if("Y".equals(byUid.getIsLogin())){
            return "Y";
        }else{
            return "N";
        }
    }
    @PostMapping("/login")
    public String ssoLogin(@RequestBody String email){
        log.info(email);
        User byUid = userService.findByUid(email);
        log.info("Auth Server에서의 로그인 유무 : " + byUid.getIsLogin());
        userService.updateVidLogIn(byUid);
        return "Y";
//        if(!byUid.isLogin()){
//            userService.updateVidLogIn(byUid);
//            return "Y";
//        }
//        return "N";
    }
    @PostMapping("/logout")
    public String ssoLogout(@RequestBody String email){
        log.info(email);
        User byUid = userService.findByUid(email);
        userService.updateVidLogout(byUid);
        return "Y";
//        if(byUid != null){
//            userService.updateVidLogout(byUid);
//            return "Y";
//        }
//        return "N";
    }
}
