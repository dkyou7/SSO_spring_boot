package com.ktnet.auth_server.user;

import com.ktnet.auth_server.account.AccountService;
import com.ktnet.auth_server.federation.FederationService;
import com.ktnet.auth_server.site.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SiteService siteService;
    private final FederationService federationService;
    private final AccountService accountService;

    @GetMapping("/save")
    public String init(){
        accountService.createDummy();
        userService.createDummy();
        accountService.connect(1L,1L);
        accountService.connect(1L,2L);
        accountService.connect(2L,3L);
        userService.changeUserRoleAdmin(1L);
        siteService.createDummy();
        federationService.createDummy(1L,1L);
        federationService.createDummy(1L,2L);
        federationService.createDummy(2L,1L);
        return "더미유저 생성 완료!";
    }

    @GetMapping("/{id}")
    public void changeUserRoleAdmin(@PathVariable("id") Long id){
        userService.changeUserRoleAdmin(id);
    }
}
