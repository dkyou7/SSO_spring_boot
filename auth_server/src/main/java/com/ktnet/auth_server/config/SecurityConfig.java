package com.ktnet.auth_server.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider authenticationProvider;

    /**
     * 등록된 유저 정보
     * .withUser("dkyou7@naver.com")
     * .password("123")
     * .roles("USER");
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .mvcMatchers("/oauth/**","/oauth2/callback","/h2-console","/login").permitAll()
                .mvcMatchers("/admin/**").authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll();
    }
}
