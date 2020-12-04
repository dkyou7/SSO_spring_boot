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

//
//    @GetMapping("/{id}")
//    public void changeUserRoleAdmin(@PathVariable("id") Long id){
//        userService.changeUserRoleAdmin(id);
//    }
}
