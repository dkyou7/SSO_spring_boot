package com.ktnet.auth_server.user;

import com.ktnet.auth_server.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userJpaRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void insertNewUser() {
        userJpaRepo.save(User.builder()
                .uid("test@naver.com")
                .password(passwordEncoder.encode("123"))
                .name("happydaddy")
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }
}