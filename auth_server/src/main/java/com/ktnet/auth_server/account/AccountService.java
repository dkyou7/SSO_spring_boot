package com.ktnet.auth_server.account;

import com.ktnet.auth_server.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Long signUp(Account signUpForm) {
        Account account = Account.builder().username(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .nickname(signUpForm.getNickname())
                .gid(signUpForm.getGid())
                .build();
        Account save = accountRepository.save(account);
        return save.getId();
    }

    public Account findByUsername(String email) {
        return accountRepository.findByUsername(email);
    }

    public void logout(String email) {
        Account byUsername = accountRepository.findByUsername(email);
    }

}
