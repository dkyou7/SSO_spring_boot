package com.ktnet.auth_server.user;

import com.ktnet.auth_server.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ClientService clientService;

    @GetMapping("/save")
    public String init(){
        userService.createDummy();
        clientService.createDummy();
        return "더미유저 생성 완료!";
    }
}
