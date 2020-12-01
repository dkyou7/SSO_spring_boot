package com.ktnet.auth_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Auth_server extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Auth_server.class, args);
    }

}

