package com.ktnet.auth_server.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userJpaRepo;
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public UserDetails loadUserByUsername(String name) {
//        User user = userJpaRepo.findByUid(name).orElseThrow(() -> new UsernameNotFoundException("user is not exists"));
        User user = userJpaRepo.findByUid(name);
        if(user==null){
            throw new UsernameNotFoundException(name);
        }
        detailsChecker.check(user);
        return user;
    }
}