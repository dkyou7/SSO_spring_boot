package com.ktnet.testClient2.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUid(String username);

    User findByMsrl(Long msrl);
}