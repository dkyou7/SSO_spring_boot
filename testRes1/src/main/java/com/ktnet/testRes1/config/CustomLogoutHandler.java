package com.ktnet.testRes1.config;

import com.ktnet.testRes1.account.Account;
import com.ktnet.testRes1.account.UserAccount;
import com.ktnet.testRes1.sso.SSOService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final SSOService ssoService;

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        Account account = ((UserAccount)authentication.getPrincipal()).getAccount();
        ssoService.ssoLogout(account);
    }
}
