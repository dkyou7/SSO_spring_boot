package com.ktnet.testRes1.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    boolean existsByUsername(String username);

    Account findByUsername(String username);

    Account findByVid(String VID);
}
