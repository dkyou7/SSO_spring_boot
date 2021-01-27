package com.ktnet.auth_server.logincheck;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginCheckRepository extends JpaRepository<LoginCheck,Long> {
    LoginCheck findByKid(String kid);
}
