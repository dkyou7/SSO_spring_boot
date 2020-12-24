package com.ktnet.auth_server.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUid(String email);

    boolean existsByUid(String email);

    boolean existsByName(String nickname);

    void deleteByUid(String email);
}
