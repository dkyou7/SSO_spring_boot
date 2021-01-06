package com.ktnet.testRes1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@RequiredArgsConstructor
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final CustomLogoutHandler customLogoutHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .mvcMatchers("/","/oauth2/**","/css/**","/log-in","/log-out","/go_login","/loginSuccess","/sign-up","/api/**").permitAll()
                .antMatchers("/v1/users").access("#oauth2.hasScope('read')")
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/log-in")
                .permitAll();
        http.logout()
                .logoutUrl("/log-out")
                .addLogoutHandler(customLogoutHandler)
                .logoutSuccessUrl("/log-out")
                .permitAll();
    }
}