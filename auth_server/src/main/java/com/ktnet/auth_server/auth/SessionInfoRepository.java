package com.ktnet.auth_server.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionInfoRepository extends JpaRepository<SessionInfo,Long> {
    SessionInfo findByCode(String code);
}
