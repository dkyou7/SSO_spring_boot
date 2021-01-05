package com.ktnet.auth_server.federation;

import com.ktnet.auth_server.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FederationRepository extends JpaRepository<Federation,Long> {

    Federation findByUidAndGid(String uid, String gid);
}
