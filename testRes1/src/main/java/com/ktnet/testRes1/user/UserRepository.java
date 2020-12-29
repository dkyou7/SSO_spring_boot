package com.ktnet.testRes1.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByMsrl(Long msrl);

    Optional<User> findByUid(String uid);
}