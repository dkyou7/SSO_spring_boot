package com.ktnet.auth_server.gid;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GidRepository extends JpaRepository<Gid,Long> {
    Gid findByTitle(String title);
}
