package com.example.demo.security;

import com.example.demo.account.Account;
import com.example.demo.account.UserAccount;
import com.example.demo.sso.SsoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final SsoService ssoService;

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        Account account = ((UserAccount)authentication.getPrincipal()).getAccount();
        ssoService.ssoLogout(account);
    }
}
