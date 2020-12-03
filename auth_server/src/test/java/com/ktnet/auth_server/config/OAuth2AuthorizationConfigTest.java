package com.ktnet.auth_server.config;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class OAuth2AuthorizationConfigTest {

    @Autowired private PasswordEncoder passwordencoder;
    @Test
    public void EncodingTest1() {
        String testSecret = passwordencoder.encode("testSecret1");
        System.out.println("testSecret = " + testSecret);
    }
    @Test
    public void EncodingTest2() {
        String testSecret = passwordencoder.encode("testSecret2");
        System.out.println("testSecret2 = " + testSecret);
    }
    @Test
    public void EncodingTest() {
        String testSecret = passwordencoder.encode("testSecret");
        System.out.println("testSecret = " + testSecret);
    }
}