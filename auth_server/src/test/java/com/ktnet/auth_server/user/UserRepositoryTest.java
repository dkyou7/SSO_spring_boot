package com.ktnet.auth_server.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userJpaRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    @Transactional
    @Commit
    public void insertNewUser() {
        User build = User.builder()
                .uid("dkyou7@naver.com")
                .password(passwordEncoder.encode("123"))
                .name("dkyou7")
                .roles(Collections.singletonList("ROLE_USER"))
                .isLogin("N")
                .build();
        userJpaRepo.save(build);

        User adminUser = User.builder()
                .uid("admin@naver.com")
                .password(passwordEncoder.encode("123"))
                .name("유동관")
                .roles(Collections.singletonList("ROLE_ADMIN"))
                .isLogin("N")
                .build();
        userJpaRepo.save(adminUser);
    }
}