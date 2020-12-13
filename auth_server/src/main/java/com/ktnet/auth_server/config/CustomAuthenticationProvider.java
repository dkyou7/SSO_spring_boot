package com.ktnet.auth_server.config;

import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     AuthenticationProvider 인터페이스는 화면에서 입력한 로그인 정보와 DB에서 가져온 사용자의 정보를 비교해주는 인터페이스이다.
     해당 인터페이스에 오버라이드되는 authenticate() 메서드는 화면에서 사용자가 입력한 로그인 정보를 담고 있는 Authentication 객체를 가지고 있다.
     그리고 DB에서 사용자의 정보를 가져오는 건 UserDetailsService 인터페이스에서 loadUserByUsername() 메서드로 구현했다.
     따라서 authenticate() 메서드에서 loadUserByUsernmae() 메서드를 이용해 DB에서 사용자 정보를 가져와서 Authentication 객체에서
     화면에서 가져온 로그인 정보와 비교하면 된다. AuthenticationProvider 인터페이스는 인증에 성공하면 인증된 Authentication 객체를
     생성하여 리턴하기 때문에 비밀번호, 계정 활성화, 잠금 모든 부분에서 확인이 되었다면 리턴해주도록 하자.

     출처: https://to-dy.tistory.com/87?category=720806 [todyDev]
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        User byUsername = userRepository.findByUid(username);
        //passwordEncoder.matches(raw pass, encode pass)
        if(!passwordEncoder.matches(password,byUsername.getPassword())){
            throw new BadCredentialsException("password is not valid");
        }

        return new UsernamePasswordAuthenticationToken(username,password,byUsername.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
