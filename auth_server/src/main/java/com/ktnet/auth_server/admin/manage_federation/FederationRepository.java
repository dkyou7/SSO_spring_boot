package com.ktnet.auth_server.admin.manage_federation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FederationRepository extends JpaRepository<Federation,Long> {

    Federation findByUidAndGid(String uid, String gid);

    Federation findByUid(String uid);

    Federation findByKid(String kid);

    Federation findByKidAndGid(String kid, String gid);
}
