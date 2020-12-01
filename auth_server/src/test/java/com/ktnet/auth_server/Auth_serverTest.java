package com.ktnet.auth_server;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class Auth_serverTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void contextLoads() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.info(">>>>>>>>>>>>>>>>> "+passwordEncoder.encode("pass"));
    }
}