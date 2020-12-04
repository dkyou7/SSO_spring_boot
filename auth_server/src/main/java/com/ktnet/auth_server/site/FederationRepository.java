package com.ktnet.auth_server.site;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FederationRepository extends JpaRepository<Federation,String> {
    Federation findbyUserId(String userId);
}
