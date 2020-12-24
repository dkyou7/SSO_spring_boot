package com.ktnet.auth_server;

import com.ktnet.auth_server.admin.manage_user.SignUpForm;
import com.ktnet.auth_server.user.CustomUserDetailService;
import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithAccountSecurityContextFacotry implements WithSecurityContextFactory<WithAccount> {

    private final UserService accountService;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        String nickname = withAccount.value();

        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname(nickname);
        signUpForm.setEmail(nickname + "@naver.com");
        signUpForm.setPassword("123123123");
        if(nickname.equals("admin_test_account")){
            accountService.processNewAccountAdmin(signUpForm);
        }else{
            accountService.processNewAccount(signUpForm);
        }

        UserDetails principal = customUserDetailService.loadUserByUsername(signUpForm.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
